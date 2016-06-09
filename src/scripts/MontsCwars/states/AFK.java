package scripts.MontsCwars.states;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSInterface;

import scripts.MontsCwars.utils.Variables;
import scripts.api.helpers.InterfacesHelper;

public class AFK {
	
	public void afkGame() {
		long runTime = System.currentTimeMillis() - Variables.get().startTime;
		
		while(System.currentTimeMillis() - Variables.get().startTime - General.random(30000, 90000) <= runTime && Player.getPosition().getPlane() == 2) {
			General.sleep(250,500);
			if(Variables.get().abc_util.shouldLeaveGame() && Mouse.isInBounds()) {
				 General.println("Leaving game");
                 Variables.get().abc_util.leaveGame();                 
				}
				else if(Mouse.isInBounds()){
					Variables.get().abc_util.performTimedActions();
              }
		}	
		DynamicClicking.clickRSTile(Variables.get().afkArea.getRandomTile(), 1);
	}
	
	public void afkPortal() {
		if(Player.getPosition().getY() > 9000) {
			General.sleep(250,500);						
			Timing.waitCondition(new Condition() { //Sleep until out y position returns to
				@Override
				public boolean active() {
					General.sleep(100,200);
					
					if(Variables.get().abc_util.shouldLeaveGame() && Mouse.isInBounds()) {
						 General.println("Leaving game");
		                  Variables.get().abc_util.leaveGame();                 
						}
						else if(Mouse.isInBounds()){
							Variables.get().abc_util.performTimedActions();
		               }
					
					return enteredGame();
				}
			}, 2000000);
		}
	}
	
	public boolean enteredGame() {
		if(Player.getPosition().getY() < 4000) {
			return true;
		}
		if(InterfacesHelper.interfaceOpen(2000, 219)) {
			RSInterface child = Interfaces.get(219,0).getChild(1);
			if(child != null && child.click()) {
				General.sleep(3000,4000);
				return Player.getPosition().getY() < 400;
			}
		}
		return false;
	}
}
