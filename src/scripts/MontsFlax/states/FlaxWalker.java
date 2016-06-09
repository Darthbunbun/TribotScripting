package scripts.MontsFlax.states;

import org.tribot.api.Timing;
import org.tribot.api2007.Game;
import org.tribot.api2007.Options;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.util.DPathNavigator;

import scripts.MontsFlax.utils.Constants;
import scripts.MontsFlax.utils.Variables;
import scripts.api.Conditions;

public class FlaxWalker {
	
	/**
	 * Noticed WebWalking misclicking the lumbridge bank so attempting to use dPath and WebWalk together
	 */
	
	DPathNavigator dPath = new DPathNavigator();

	public boolean walkToWheel() {
		
		if (!Game.isRunOn() && Game.getRunEnergy() > Variables.get().abc_util.generateRunActivation()) {
			Options.setRunOn(true);
		}
		
		
		if(WebWalking.walkTo(Constants.WHEEL_AREA.getRandomTile())) {
			return Timing.waitCondition(Conditions.isInAreaCondition(Constants.WHEEL_AREA), 5000);
		}
		return false;
	}

	public boolean walkToBank() {
		
		if (!Game.isRunOn() && Game.getRunEnergy() > Variables.get().abc_util.generateRunActivation()) {
			Options.setRunOn(true);
		}
		
		if(WebWalking.walkTo(Constants.BANK_TILE)) {
			return Timing.waitCondition(Conditions.IN_BANK_CONDITION, 5000);
		}
		return false;
	}
}
