package scripts.MontsTiaraCrafter.states;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Game;
import org.tribot.api2007.Options;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSTile;

import scripts.MontsTiaraCrafter.utils.Variables;
import scripts.api.Conditions;
import scripts.api.helpers.ACamera;

public class TiaraCrafterWalker {
	
	private Altar altar = new Altar();
	
	private RSTile[] altarPath = Variables.get().altarPath;
	private ACamera aCamera = new ACamera();
	
	public boolean walkToBank() {
		
		RSTile[] reversedPath = new RSTile[altarPath.length];
		
		if (!Game.isRunOn() && Game.getRunEnergy() > Variables.get().abc_util.generateRunActivation()) {
			Options.setRunOn(true);
		}
		
		for(int i = altarPath.length-1; i >= 0; i--) {
			reversedPath[altarPath.length - 1 - i] = altarPath[i];
		}
		
		if(Walking.walkPath(reversedPath)) {
			aCamera.turnToTile(Player.getPosition());
			 return Timing.waitCondition(Conditions.IN_BANK_CONDITION, 15000);
		}
		return false;
	}
	
	public boolean walkToAltar() {
		
		if (!Game.isRunOn() && Game.getRunEnergy() > Variables.get().abc_util.generateRunActivation()) {
			Options.setRunOn(true);
		}
		
		if(Walking.walkPath(altarPath)) {
			aCamera.turnToTile(Player.getPosition());
			
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
