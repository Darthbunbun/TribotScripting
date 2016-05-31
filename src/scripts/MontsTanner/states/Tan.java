package scripts.MontsTanner.states;

import org.tribot.api.DynamicClicking;
import org.tribot.api.Timing;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

import scripts.MontsTanner.utils.Variables;
import scripts.api.Conditions;

public class Tan {
	
	private final RSTile tanTile = Variables.get().tanTile;
	
	public void walkToTanner() {
		if(Variables.get().dPath.traverse(tanTile)) {
			Timing.waitCondition(Conditions.isInAreaCondition(Variables.get().tanArea), 15000);
		}
	}
	
	public void tanHides() {
		
		RSNPC[] tanner = NPCs.find(Variables.get().tannerName);
		
		if(!tanner[0].isClickable()) {
			Camera.turnToTile(tanTile);
		}
		
		if(tanner.length > 0 && DynamicClicking.clickRSNPC(tanner[0], "Trade")) {
			Timing.waitCondition(Conditions.interfaceUpCondition(324), 3000);
		}	
		
		RSInterface tanInterface = Interfaces.get(324, Variables.get().child);
		
		if(tanInterface != null && tanInterface.click("Tan All")) {
			Variables.get().leatherMade += Inventory.getCount(Variables.get().leather);
			Timing.waitCondition(Conditions.inventoryContainsCondition(Variables.get().leather), 3000);
		}
	}
	
	public boolean isAtTanner() {
		return Variables.get().tanArea.contains(Player.getPosition());
	}
}
