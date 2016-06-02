package scripts.MontsTanner.states;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

import scripts.MontsTanner.utils.Variables;
import scripts.api.Conditions;

public class Tan {
	
	private final RSTile tanTile = Variables.get().tanTile;
	
	public boolean walkToTanner() {
		if(Variables.get().dPath.traverse(tanTile)) {
			return Timing.waitCondition(Conditions.isInAreaCondition(Variables.get().tanArea), 15000);
		}
		return false;
	}
	
	public void tanHides() {
		
		RSNPC[] tanner = NPCs.find(Variables.get().tannerName);
		
		if(Player.isMoving()) {
			General.sleep(250,500);
		}
		
		if(!tanner[0].isClickable()) {
			Camera.turnToTile(tanTile);
		}
		
		if(tanner.length > 0 && DynamicClicking.clickRSNPC(tanner[0], "Trade")) {
			Timing.waitCondition(Conditions.interfaceUpCondition(324), 3000);
		}	
		
		RSInterface tanInterface = Interfaces.get(324, Variables.get().child);
		
		if(tanInterface != null && tanInterface.click("Tan All")) {
			Timing.waitCondition(Conditions.inventoryContainsCondition(Variables.get().leather), 3000);
		}
	}
	
	
	public boolean isAtTanner() {
		return Variables.get().tanArea.contains(Player.getPosition());
	}
}
