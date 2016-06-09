package scripts.MontsCwars.states;

import org.tribot.api.DynamicClicking;
import org.tribot.api.Timing;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Objects;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;

import scripts.MontsCwars.utils.Variables;
import scripts.api.Conditions;

public class EnterPortal {
	
	public void enterPortal() {
		
		RSObject[] portal = Objects.find(25, Filters.Objects.nameContains(Variables.get().portal));
		
		if(portal.length > 0 && DynamicClicking.clickRSObject(portal[0], 1)) { 
			Timing.waitCondition(Conditions.yCoordCondition(9000), 5000);
			Variables.get().abc_util.generateTrackers();
		} else {
			Camera.turnToTile(portal[0].getPosition());
		}
	}
	
	public boolean isAtCwars() {
		RSObject[] guthixPortal = Objects.find(15, Filters.Objects.nameContains("Guthix"));
		
		if(guthixPortal.length > 0) {
			return true;
		}
		return false;
	}
}
