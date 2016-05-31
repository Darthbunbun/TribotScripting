package scripts.MontsFlax.states;

import org.tribot.api.Timing;
import org.tribot.api2007.WebWalking;
import scripts.MontsFlax.utils.Constants;
import scripts.api.Conditions;

public class FlaxWalker {
	
	
	public void walkToWheel() {
		
		if(WebWalking.walkTo(Constants.WHEEL_TILE)) {
			Timing.waitCondition(Conditions.isInAreaCondition(Constants.WHEEL_AREA), 5000);
		}
	}

	public void walkToBank() {
		
		if(WebWalking.walkTo(Constants.BANK_TILE)) {
			Timing.waitCondition(Conditions.inBankCondition(), 5000);
		}
	}
}
