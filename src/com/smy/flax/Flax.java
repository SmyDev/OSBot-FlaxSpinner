package com.smy.flax;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import com.smy.flax.tasks.AntiBanTask;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import com.smy.flax.tasks.BankingTask;
import com.smy.flax.tasks.MoveTask;
import com.smy.flax.tasks.SpinningTask;

import javax.imageio.ImageIO;
import javax.rmi.CORBA.Util;

@ScriptManifest(author = "benas512", info = "Flax Spinner", logo = "", name = "Flax Spinner", version = 1.0)
public class Flax extends Script{

	private ArrayList<Task> task = new ArrayList<>();

	private BufferedImage flax_img;

	GUI gui;
	
	@Override
	public void onStart()
	{
		gui = new GUI();

		task.add(new BankingTask(this));
		task.add(new MoveTask(this));
		task.add(new SpinningTask(this));
		task.add(new AntiBanTask(this));
		
		Utils.currentXp = getSkills().getExperience(Skill.CRAFTING);
		getImages();
	}

	@Override
	public int onLoop() throws InterruptedException
	{
		if(Utils.stopScript){		/*It's only for GUI, because sometimes Utils.canStart might be false*/
			switch (Utils.onFinish){
				case 0: 	/*Log out*/
					stop(true);
					break;
				case 1:		/*Go to ge*/
					Area ge = new Area(3161, 3487, 3168, 3483);
					walking.webWalk(ge);
					stop(true);
					break;
			}
		}

		if(Utils.canStart && !Utils.stopScript)
		{
			Utils.latestXp = getSkills().getExperience(Skill.CRAFTING);
			if(Utils.latestXp > Utils.currentXp)
			{
				Utils.timeOnXpUpdate = System.currentTimeMillis()
						+ random(3000, 8000);
				Utils.currentXp = Utils.latestXp;
			}
			for(Task t : task)
			{
				t.run();
			}
		}
			
		return random(600, 1000);
	}
	
	public void onPaint(Graphics2D g){
		int x = getMouse().getPosition().x;
		int y = getMouse().getPosition().y;

		/*Mouse draw*/
		g.setColor(Color.cyan);
		g.drawLine(0, y, 765, y);
		g.drawLine(x, 0, x, 503);
		/************/

		/*Box Draw*/
		g.setColor(new Color(0,0,0, 0.7f));
		g.fillRect(1, 250,515,95);
		/**********/

		/*Draw flax image*/
		g.drawImage(flax_img, 400,257, 113, 75, null);
		/****************/

		/*Info draw*/
		int hours = (int)(( System.currentTimeMillis() - Utils.startTime) / (1000*60*60)) % 24;
		int minutes = (int)((System.currentTimeMillis() - Utils.startTime) / (1000*60)) % 60;
		int seconds = (int)((System.currentTimeMillis() - Utils.startTime) / 1000) % 60;
		g.setColor(Color.white);
		if(Utils.canStart)
			g.drawString("Run time: " +
					hours + ":" +
					minutes + ":" +
					seconds
					,8,265);
		else
			g.drawString("Run time: 00:00:00" ,8,260);
		/***********/
	}

	private void getImages(){
		try{
			flax_img = ImageIO.read(getClass().getResourceAsStream("/flax_img.png"));
		} catch (IOException ex){
			log("Failed to get an image: " + ex.toString());
		}
	}

	public void onExit(){
		gui.dispose();
	}
}
