package com.smy.flax;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.smy.flax.tasks.AntiBanTask;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import com.smy.flax.tasks.BankingTask;
import com.smy.flax.tasks.MoveTask;
import com.smy.flax.tasks.SpinningTask;

@ScriptManifest(author = "benas512", info = "Flax Spinner", logo = "", name = "Flax Spinner", version = 1.0)
public class Flax extends Script{

	private ArrayList<Task> task = new ArrayList<>();

	private boolean canStart;
	
	@Override
	public void onStart()
	{
		canStart = true;
		
		task.add(new BankingTask(this));
		task.add(new MoveTask(this));
		task.add(new SpinningTask(this));
		task.add(new AntiBanTask(this));
		
		Utils.currentXp = getSkills().getExperience(Skill.CRAFTING);
	}

	@Override
	public int onLoop() throws InterruptedException
	{
		if(canStart)
		{
			Utils.latestXp = getSkills().getExperience(Skill.CRAFTING);
			if(Utils.latestXp > Utils.currentXp)
			{
				Utils.timeOnXpUpdate = System.currentTimeMillis()
						+ random(3487, 7851);
				Utils.currentXp = Utils.latestXp;
			}
			for(Task t : task)
			{
				if(Utils.stopScript)
					stop();
				t.run();
			}
		}
			
		return random(600, 1000);
	}
	
	public void onPaint(Graphics2D g){
		int x = getMouse().getPosition().x;
		int y = getMouse().getPosition().y;
		
		g.setColor(Color.cyan);
		g.drawLine(0, y, 765, y);
		g.drawLine(x, 0, x, 503);
	}
}
