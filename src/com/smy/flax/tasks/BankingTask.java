package com.smy.flax.tasks;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.MethodProvider;

import com.smy.flax.Task;
import com.smy.flax.Utils;
import org.osbot.rs07.utility.ConditionalSleep;

public class BankingTask extends Task{
	
	private Area BANK_AREA = new Area(new Position(3208, 3220, 2),
			new Position(3209, 3218, 2));

	public BankingTask(MethodProvider api) {
		super(api);
	}

	@Override
	public boolean canProcess() {
		if(BANK_AREA.contains(api.myPlayer()))
			if(!api.inventory.contains("Flax"))
				if(api.myPosition().getZ() == 2)
					if(!Utils.stopScript)
			return true;
		
		return false;
	}

	@SuppressWarnings("static-access")
	@Override
	public void process() throws InterruptedException {
		Entity bank = api.objects.closest("Bank booth");
		
		if(bank == null) return;

		Utils.isSpinning = false;
		
		if(api.bank.open()){
			new ConditionalSleep(MethodProvider.random(700, 1400)){
				@Override
				public boolean condition() throws InterruptedException {
					return api.bank.isOpen();
				}
			}.sleep();

			Anti_Pattern_Deposit();
			Take_Items();
			Close_Bank();
		}
	}
	
	@SuppressWarnings("static-access")
	private void Anti_Pattern_Deposit()
	{
		if(!api.inventory.contains("Bow string"))
			return;
		
		int rnd = api.random(0, 100);
		
		if(rnd > 10) {
			if(api.bank.depositAll()){
				new ConditionalSleep(1000){
					@Override
					public boolean condition() throws InterruptedException {
						return api.inventory.isEmpty();
					}
				}.sleep();
			}
		} else {
			if(api.bank.depositAll("Bow string")){
				new ConditionalSleep(1000){
					@Override
					public boolean condition() throws InterruptedException {
						return !api.inventory.contains("Bow string");
					}
				}.sleep();
			}
		}
	}
	
	private void Take_Items()
	{
		if(api.getBank().contains("Flax")) {
			if(api.getBank().withdraw("Flax", 28)){
				new ConditionalSleep(1000){
					@Override
					public boolean condition() throws InterruptedException {
						return api.inventory.contains("Flax");
					}
				}.sleep();
			}
		} else {
			Utils.stopScript = true;
		}
	}
	
	@SuppressWarnings("static-access")
	private void Close_Bank()
	{
		int rnd = api.random(0, 100);
		if(rnd < 11)
			api.bank.close();
	}
}
