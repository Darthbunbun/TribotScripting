package scripts.MontsTiaraCrafter.states;

import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;

import scripts.MontsTiaraCrafter.utils.Variables;
import scripts.api.Conditions;

public class Bank {
	
	private int failToWithdraw;
	
	public void bank() {
		if(Banking.openBank() && Timing.waitCondition(Conditions.IS_BANK_OPEN_CONDITION, 3000)) {
			if(depositTiaras()) {
				withdrawMats();
			} else {
				withdrawMats();
			}
		}
	}

	private boolean depositTiaras() {
		if(Inventory.getAll().length > 0 && Inventory.getCount(Variables.get().talismanNeeded) == 0) {
			return Banking.depositAll() > 0 && Timing.waitCondition(Conditions.inventorySizeCondition(0),3000);
		}
		return false;
	}
	
	private void withdrawMats() {
		if(needToWithdraw()  && Banking.withdraw(14, Variables.get().talismanNeeded) && inventoryHas(Variables.get().talismanNeeded)) {
			if(needToWithdraw() && Banking.withdraw(14, Variables.get().tiara)) {
				failToWithdraw = 0;
				 inventoryHas(Variables.get().tiara);
			} else {
				failToWithdraw();
			}
		}
		failToWithdraw();
	}
	
	private boolean failToWithdraw() {
		failToWithdraw++;
		
		if(failToWithdraw > 2) {
			Login.logout();
			Variables.get().hasMats = false;
			return true;
		}
		
	return false;
	}
	
	public boolean needToWithdraw() {
		return Inventory.getCount(Variables.get().talismanNeeded) == 0 || Inventory.getCount(Variables.get().tiara) == 0;
	}
	
	public boolean needToBank() {
		return Inventory.getCount(Variables.get().talismanNeeded) == 0;
	}
	
	public boolean isInBank() {
		return Banking.isInBank();
	}
	
	//Only using this to cut down on length of withdraw methods
	private boolean inventoryHas(String str) {
		return Timing.waitCondition(Conditions.inventoryContainsCondition(str), 2000);
	}

}
