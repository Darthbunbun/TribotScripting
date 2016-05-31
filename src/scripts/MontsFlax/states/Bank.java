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
	
	private FlaxWalker walk = new FlaxWalker();
	
	public void bank() {
		
		if(Player.isMoving()) {
			General.sleep(150,200);
		}
		
		if(isInBank()) {
			if(Banking.openBank() && Timing.waitCondition(Conditions.IS_BANK_OPEN_CONDITION, 3000)) {
				if(needsToDeposit()) {
					depositFlax();
				}
				withdrawFlax();	
			} 
		} else {
			walk.walkToBank();
		}
	}
	
	private boolean withdrawFlax() {
		Variables.get().status = "Withdrawing flax";
		if(BankHelper.waitUntilBankLoaded() && Banking.withdraw(0, "Flax")) {
			failToWithdraw = 0;
			return Timing.waitCondition(Conditions.inventoryContainsCondition("Flax"), 3000);
		}
		failToWithdraw();
		return false;
	}
	
	private boolean depositFlax() {
		Variables.get().status = "Depositng flax";
		if(Banking.depositAll() > 0) {
			return Timing.waitCondition(Conditions.inventorySizeCondition(0), 3000);
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
