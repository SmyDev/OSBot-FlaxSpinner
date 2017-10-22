package com.smy.flax.tasks;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.MethodProvider;

import com.smy.flax.Task;
import com.smy.flax.Utils;

public class MoveTask extends Task{
	
	private Area BANK_AREA = new Area(new Position(3207, 3220, 2),
			new Position(3209, 3218, 2));
	
	private Area SPIN_AREA = new Area(new Position(3209, 3213, 1),
			new Position(3210, 3214, 1));

	public MoveTask(MethodProvider api) {
		super(api);
	}

	@Override
	public boolean canProcess() {
		if(!Utils.stopScript)
			return true;
		return false;
	}

	@Override
	public void process() {
		if(!api.inventory.contains("Flax")
				&& !BANK_AREA.contains(api.myPlayer()))
		{
			api.walking.webWalk(BANK_AREA);
			return;
		}
		
		if(!api.inventory.contains("Bow String")
				&& api.inventory.contains("Flax")
				&& !SPIN_AREA.contains(api.myPlayer()))
		{
			api.walking.webWalk(SPIN_AREA);
			return;
		}
			
	}

}
