package scripts.MontsFlax.states;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;

import scripts.MontsFlax.utils.Variables;
import scripts.api.Conditions;
import scripts.api.helpers.BankHelper;

public class Bank {
	
	private int failToWithdraw = 0;
	
	public void bank() {
		
		if(Player.isMoving()) {
			General.sleep(150,200);
		} else if(Banking.openBank() && Timing.waitCondition(Conditions.IS_BANK_OPEN_CONDITION, 1200)) {
			if(needsToDeposit()) {
				depositFlax();
			}
				withdrawFlax();	
		}
	}
	
	private boolean withdrawFlax() {
		Variables.get().status = "Withdrawing flax";
		if(BankHelper.waitUntilBankLoaded()) {
			if(Banking.withdraw(0, "Flax")) {
				failToWithdraw = 0;
				return Timing.waitCondition(Conditions.inventoryContainsCondition("Flax"), 1500);
			} else {
				failToWithdraw();
			}
		}
		return false;
	}
	
	private boolean depositFlax() {
		Variables.get().status = "Depositng flax";
		if(Banking.depositAll() > 0) {
			return Timing.waitCondition(Conditions.inventorySizeCondition(0),1500);
		}
		return false;
	}
		
	private void failToWithdraw() {
		failToWithdraw++;
		
		if(failToWithdraw > 2) {
			General.println("Out of materials, Ending script!");
			Variables.get().hasMats = false;
		}
	}

	private boolean needsToDeposit() {
		return Inventory.getCount("Bow string") > 0;
	}
	
	public boolean needToBank() {
		return Inventory.getCount("Flax") == 0;
	}
	
	public boolean isInBank() {
		return Banking.isInBank();
	}

}
