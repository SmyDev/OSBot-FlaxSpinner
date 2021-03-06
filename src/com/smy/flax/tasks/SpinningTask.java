package com.smy.flax.tasks;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;

import com.smy.flax.Task;
import com.smy.flax.Utils;
import org.osbot.rs07.utility.ConditionalSleep;

public class SpinningTask extends Task{
	
	private Area SPIN_AREA = new Area(new Position(3209, 3213, 1),
			new Position(3210, 3214, 1));

	public SpinningTask(MethodProvider api) {
		super(api);
	}

	@Override
	public boolean canProcess() {
		if(!api.myPlayer().isAnimating() && api.inventory.contains("Flax")
				&& SPIN_AREA.contains(api.myPlayer())
				&& System.currentTimeMillis() >= Utils.timeOnXpUpdate
				&& !Utils.stopScript)
			return true;
		return false;
	}

	@Override
	public void process() throws InterruptedException {
		RS2Object wheel = api.objects.closest("Spinning wheel");
		
		if(api.map.canReach(wheel)){
			
			if(api.getInventory().interact("Use", "Flax"))
			{
				new ConditionalSleep(1500){
					@Override
					public boolean condition() throws InterruptedException {
						return api.inventory.isItemSelected();
					}
				}.sleep();

				wheel.interact("Use");

				MethodProvider.sleep(MethodProvider.random(1400, 1600));
			}
			
			RS2Widget inter = api.getWidgets().get(270, 14, 38);

			if(inter != null){
				inter.interact("Make");
				Utils.isSpinning = true;
				MethodProvider.sleep(MethodProvider.random(800, 1400));
			}
		}
	}
}
