package scripts.MontsFlax.states;

import org.tribot.api.Timing;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Objects;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.util.DPathNavigator;

import scripts.MontsFlax.utils.Constants;
import scripts.api.Conditions;

public class FlaxWalker {
	
	/**
	 * Noticed WebWalking misclicking the lumbridge bank so attempting to use dPath and WebWalk together
	 */
	
	DPathNavigator dPath = new DPathNavigator();

	public boolean walkToWheel() {
		
		if(WebWalking.walkTo(Constants.WHEEL_AREA.getRandomTile())) {
			return Timing.waitCondition(Conditions.isInAreaCondition(Constants.WHEEL_AREA), 5000);
		}
		return false;
	}

	public boolean walkToBank() {
		
		if(WebWalking.walkTo(Constants.BANK_TILE)) {
			return Timing.waitCondition(Conditions.IN_BANK_CONDITION, 7000);
		}
		return false;
	}
}
