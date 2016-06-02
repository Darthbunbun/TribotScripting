package scripts.Smither.states;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSObject;

import scripts.Smither.utils.Constants;
import scripts.Smither.utils.Variables;
import scripts.api.Conditions;
import scripts.api.helpers.InterfacesHelper;
import scripts.api.interaction.ClickObject;
import scripts.api.interaction.ItemOnObject;

public class Furnace {
	RSObject furnace;

	private RSInterfaceChild makeAll;
	
	public void walkToFurnace() {
		
		if(WebWalking.walkTo(Variables.get().furnaceTile)) {
			Timing.waitCondition(Conditions.isInAreaCondition(Variables.get().furnaceArea), 5000);
		}
	}

	public void smithBalls() {
		
		while(Player.isMoving()) {
			General.sleep(200,500);
		}
		
		switch(Variables.get().task) {
		
		case "BARS":
			smithBars();
			break;
		case "ORE":
			smeltOres();
			break;
		}
	}

	public void smithBars() {

		ItemOnObject.useItemOnObject("Use","Furnace","Steel bar");
		General.sleep(400,500);
		
		if(InterfacesHelper.interfaceOpen(2000,Constants.CANNONBALL_PARENT)) {		
			makeAll = Interfaces.get(Constants.CANNONBALL_PARENT, Constants.CANNONBALL_CHILD); //Make All interface
			
			if(makeAll != null && makeAll.click("Make All")) {
				Variables.get().abc_util.generateTrackers();
					
				Timing.waitCondition(new Condition() { //Checking to smith so we can sleep and generate ABC
					@Override
					public boolean active() {
						General.sleep(200,300);
						return isSmithing();
					}
				}, General.random(250, 500));
			} else {  
				General.sleep(1000,1500);        
			}
		}
	}
	
	public void smeltOres() {
		
		if(ClickObject.interact("Furnace", "Smelt Furnace")) {
			if(InterfacesHelper.interfaceOpen(2000, Constants.STEEL_PARENT)) {
				makeAll = Interfaces.get(Constants.STEEL_PARENT, Constants.STEEL_CHILD);
				
				if(makeAll != null && makeAll.click("Smelt 10")) {
					
					Timing.waitCondition(new Condition() { //Checking to smith so we can sleep and generate ABC
						@Override
						public boolean active() {
							General.sleep(200,300);
							return isSmithing();
						}
					}, General.random(250, 500));
				}
			}	
		}
		if(Inventory.getCount(Constants.COAL) == 0) { //Only checking for coal because, if no coal we cant smith bars.
			smithBars();
		}
	}
	
	public boolean isSmithing() {
		
		if(((Inventory.getCount(Constants.BAR) == 0) && Inventory.getCount(Constants.COAL) == 0) 
				|| !Player.getPosition().equals(Variables.get().furnaceTile) 
				|| NPCChat.getMessage() != null) {
			return false;
		} else {
			//Check if player is animating, or inventory changed
			return Timing.waitCondition(Conditions.isAnimatingCondition(), 1500)
					|| Timing.waitCondition(Conditions.inventoryChangedCondition(Constants.BAR),5000); 
		}
	}
	
	public boolean isAtFurnace() {
		return Variables.get().furnaceArea.contains(Player.getPosition());
	}
}
