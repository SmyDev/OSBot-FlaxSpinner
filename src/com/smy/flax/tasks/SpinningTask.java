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

	@SuppressWarnings("static-access")
	@Override
	public void process() throws InterruptedException {
		RS2Object wheel = api.objects.closest(14889);
		
		if(api.map.canReach(wheel)){
			
			api.getInventory().interact("Use", "Flax");
			
			if(api.inventory.isItemSelected())
			{
				wheel.interact("Use");
				
				api.sleep(api.random(1000, 1500));
			}
			
			RS2Widget inter = api.getWidgets().get(270, 14, 38);

			new ConditionalSleep(2000){
				@Override
				public boolean condition() throws InterruptedException {
					return inter != null;
				}
			}.sleep();

			inter.interact("Make");
			Utils.isSpinning = true;
		}
	}
}
