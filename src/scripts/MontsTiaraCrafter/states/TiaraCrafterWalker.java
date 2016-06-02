package scripts.MontsTiaraCrafter.states;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSTile;

import scripts.MontsTiaraCrafter.utils.Variables;
import scripts.api.Conditions;

public class TiaraCrafterWalker {
	
	private Altar altar = new Altar();
	
	private RSTile[] altarPath = Variables.get().altarPath;
	
	public boolean walkToBank() {
	RSTile[] reversedPath = new RSTile[altarPath.length];
		
		for(int i = altarPath.length-1; i >= 0; i--) {
			reversedPath[altarPath.length - 1 - i] = altarPath[i];
		}		
		if(Walking.walkPath(reversedPath)) {
			 return Timing.waitCondition(Conditions.IN_BANK_CONDITION, 15000);
		}
		return false;
	}
	
	public boolean walkToAltar() {
		if(Walking.walkPath(altarPath)) {
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(500,1000);
					return altar.isNearAltar();
				}
			}, 15000);
		}
		return false;
	}
}
