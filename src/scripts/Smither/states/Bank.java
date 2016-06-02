package scripts.Smither.states;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;

import scripts.Smither.utils.Constants;
import scripts.Smither.utils.Variables;
import scripts.api.Conditions;
import scripts.api.helpers.BankHelper;

public class Bank {

	private int failToWithdraw;
	
	public void walkToBank() {	
		if(WebWalking.walkToBank()) {					
			Timing.waitCondition(Conditions.IN_BANK_CONDITION, 5000);
		}
	}
	
	public void bank() {
		
		if(Player.isMoving()) {
			General.sleep(150,200);
		}
		
		if(Inventory.getCount(Constants.MOULD) == 0) {
			if(Banking.openBank()) {
				Banking.depositAll();
				Banking.withdraw(1, Constants.MOULD);
			}
		} 
		
		if(needSecondItem()) {
			withdrawOres();
		} else {
			withdrawBars();
		}
	}
	
	public void withdrawOres() { //Ores -> Cannonballs

	    if(Banking.openBank() && Timing.waitCondition(Conditions.IS_BANK_OPEN_CONDITION, 3000)) {
	    	if(Banking.depositAllExcept(Constants.MOULD) > 0 ) {
	    		Timing.waitCondition(Conditions.inventorySizeCondition(1), 2000);
	    	}
	    	
	    	if(BankHelper.waitUntilBankLoaded()) { 		
	    		if(Banking.withdraw(9, Constants.IRON_ORE)) {	
	    			failToWithdraw = 0;	
	    			Timing.waitCondition(Conditions.inventoryContainsCondition(Constants.IRON_ORE), 3000);
	    			
	    			if(Banking.withdraw(0,Constants.COAL)) {
	    				Timing.waitCondition(Conditions.inventoryContainsCondition(Constants.COAL), 3000);
	    			}
	    		} else {
	    			failToWithdraw();
	    		}
	    	} 	
	    }	
	}
	
	public void withdrawBars() { //Bars -> Cannonballs

	    if(Banking.openBank() && Timing.waitCondition(Conditions.IS_BANK_OPEN_CONDITION, 3000)) {
	    	if(Banking.depositAllExcept(Constants.MOULD) > 0 ) {
	    		Timing.waitCondition(Conditions.inventorySizeCondition(1), 2000);
	    	}

	    	if(BankHelper.waitUntilBankLoaded()) { 	    		
	    		if(Banking.withdraw(0, Constants.BAR)) {
	    			failToWithdraw = 0;
	    			Timing.waitCondition(Conditions.inventoryContainsCondition(Constants.BAR), 3000);
	    		} else {
	    			failToWithdraw();
	    		}
	    	} 	
	    }
	}
	
	public void failToWithdraw() {		
		failToWithdraw++;		
		
		if(failToWithdraw > 2) {
			Variables.get().hasMats = false;
			General.println("Out of bars");	
		}
	}
	
	public boolean needSecondItem() {
		return Variables.get().task.equals("ORE");
	}
	    
	public boolean needToBank() {
		//If inventory has no bars, no coal and no iron, or no mould, we need to bank
		return (Inventory.getCount(Constants.BAR) == 0 && (Inventory.getCount(Constants.COAL) == 0) 
				&& Inventory.getCount(Constants.IRON_ORE) == 0) || Inventory.getCount(Constants.MOULD) == 0;
	}
}
