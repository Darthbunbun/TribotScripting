package scripts.MontsCwars.states;

import org.tribot.api.Timing;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Walking;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;

import scripts.MontsCwars.utils.Variables;
import scripts.api.Conditions;

public class WalkToAfk {
	
	public void walk() {
		RSObject[] ladder = Objects.find(15, Filters.Objects.actionsContains("Climb-up"));
		
		if(ladder.length > 0 && ladder[0].click("Climb-up")) {
			if( Timing.waitCondition(Conditions.planeCondition(2), 3000) && Walking.walkTo(Variables.get().afkArea.getRandomTile())) {
				Timing.waitCondition(Conditions.isInAreaCondition(Variables.get().afkArea), 5000);
			}
		}		
	}
}
