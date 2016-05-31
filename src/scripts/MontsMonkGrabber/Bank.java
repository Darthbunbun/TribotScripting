package scripts.MontsMonkGrabber;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;

import scripts.MontsMonkGrabber.utils.Variables;
import scripts.api.Conditions;

public class Bank {
	
	public GetRobes getRobes = new GetRobes();

	public void goToBank() {
		if(getRobes.isAtRobes()) {
			if(getRobes.openDoor() && getRobes.climbLadder()) {
				Timing.waitCondition(Conditions.planeCondition(0), 3000);
			}
		} else if(Walking.walkPath(Variables.get().bankPath)) {
			Timing.waitCondition(Conditions.IN_BANK_CONDITION, 20000);	
		}
	}
	
	public void bank() {
		
		while(Player.isMoving()) {
			General.sleep(250,300);
		}
		
		if(!Banking.isInBank()) {
			goToBank();
		} else {
			if(Banking.openBank() && Timing.waitCondition(Conditions.IS_BANK_OPEN_CONDITION, 3000) && Banking.depositAll() > 0) {
				Timing.waitCondition(Conditions.inventorySizeCondition(0), 2000);
			}
		}
	}
	
	public boolean needsToBank() {
		return Inventory.isFull();
	}

}
