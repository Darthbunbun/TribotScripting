package scripts.MontsTanner.states;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;

import scripts.MontsTanner.utils.Variables;
import scripts.api.Conditions;
import scripts.api.helpers.BankHelper;


public class Bank {

	private int failToWithdraw = 0;
	public int GP_ID = 995;
	private String hide = Variables.get().hide;
	private String leather = Variables.get().leather;
	
	public void walkToBank() {
		if(WebWalking.walkToBank()) {
			Timing.waitCondition(Conditions.IN_BANK_CONDITION, 15000);
		}
	}

	public void bank() {

		if(Player.isMoving()) {
			General.sleep(150,250);
		}	
		
		if(!Banking.isInBank()) {
			walkToBank();
		} else {
			//Using super energy
			if(Variables.get().superEnergy && Potion.needsSuperEnergy() && Banking.openBank()) {
				if(Timing.waitCondition(Conditions.IS_BANK_OPEN_CONDITION, 3000) && Banking.depositAllExcept(GP_ID) > 0) {
					if(withdrawSuperEnergy() && Banking.close()) {
						Potion.drinkPot();
						
						//Cannot find super energy, and since we've already deposited let's withdraw hides
					} else if(needToWithdraw()) {
						withdrawMats();
					}
				}
				//Not using super energy potions
			} else if(Banking.openBank() && Timing.waitCondition(Conditions.IS_BANK_OPEN_CONDITION, 3000)) { 
				if(needsToDeposit()) {
					depositLeather();
				}
				withdrawMats();
			}
		}	
	}
	
	public void depositLeather() {
		Variables.get().status = "Depositing Leather";
		
		if(Banking.depositAllExcept(GP_ID) > 0) {
			Timing.waitCondition(Conditions.inventorySizeCondition(1), 3000);
		}
	}

	public void withdrawMats() {	
		Variables.get().status = "Withdrawing Hides";
		
		if(BankHelper.waitUntilBankLoaded()) {	
			if(hasMoney()) {
				if(Banking.withdraw(0, hide)) {			
					failToWithdraw = 0;
					Timing.waitCondition(Conditions.inventoryContainsCondition(hide), 3000);			
				} else {
					failToWithdraw();
				}	
			} else {
				if(Banking.withdraw(0, GP_ID)) {
					Timing.waitCondition(Conditions.inventoryContainsCondition(GP_ID), 3000);
				} else {
					General.println("Cannot find coins, ending script");
					Variables.get().hasMats = false;			
				}
			}
		}
	}
	
	public void failToWithdraw() {
		failToWithdraw++;
		
		if(failToWithdraw > 2) {
			Login.logout();
			Variables.get().hasMats = false;
		}
	}
	
	public boolean withdrawSuperEnergy() {	
		RSItem[] i = Banking.find(Filters.Items.nameContains("Super energy"));
	    
	   if(i.length > 0 && Banking.withdrawItem(i[0], 1)) { //We found and withdrew potions
		   return true;
	   }
	   return false;
	}
	
	public boolean needsToDeposit() {
		return Inventory.getCount(leather) > 0 || Inventory.getCount("Vial") > 0;
	}
	
	public boolean needToWithdraw() {
		return Inventory.getCount(hide) < 1;
	}
	
	public boolean hasMoney() {
		return Inventory.getCount(GP_ID) > Variables.get().coinsToMake;
	}
	
	public boolean needToBank() {
		return Inventory.getCount(hide) < 1
				|| Inventory.getCount(GP_ID) < Variables.get().coinsToMake;
	}
}

