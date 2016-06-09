package scripts.Smither.states;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Banking;

import scripts.Smither.utils.Variables;

public class SwitchStates {
	
	 enum State { 
		WALK_TO_BANK, 
		WALK_TO_FURNACE,  
		SMITH_BALLS,
		BANK,
		SLEEP
	}
	 
	 private static Bank bank = new Bank();
	 private static Furnace furnace = new Furnace();
	
	public static void run() {
		
		switch (state()) {
		
		case BANK:
			Variables.get().status = "Banking";
			bank.bank();
			break;		
			
		case WALK_TO_BANK:
			Variables.get().status = "Walking to bank";
			bank.walkToBank();
			break;
			
		case WALK_TO_FURNACE:
			Variables.get().status = "Walking to furnace";
			furnace.walkToFurnace();
			break;
			
		case SMITH_BALLS:
			Variables.get().status = "Preparing to Smith";
			furnace.smithBalls();
			break;

		case SLEEP:
        	 	Variables.get().status = "Smithing Cannonballs";         
        	 	while(furnace.isSmithing()) {
        	 	General.sleep(100,200);        
                	if(Variables.get().abc_util.shouldLeaveGame() && Mouse.isInBounds()) {
                   		Variables.get().abc_util.leaveGame();                 
			}
			else if(Mouse.isInBounds()){
				Variables.get().abc_util.performTimedActions();
                }
            }
            break;
		}
		General.sleep(100,200);
	}

	private static State state() {

		if (bank.needToBank()) {		
			if (Banking.isInBank()) {	
				return State.BANK;
			} else {
				return State.WALK_TO_BANK;		
			}
		} else { // Inventory has bars		
			if (!furnace.isAtFurnace()) {	
				return State.WALK_TO_FURNACE;
			} else if(furnace.isSmithing()) {
				return State.SLEEP;
			} else {
				return State.SMITH_BALLS;
			}
		}				
	}
}
