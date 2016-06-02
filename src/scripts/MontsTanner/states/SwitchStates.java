package scripts.MontsTanner.states;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Game;
import org.tribot.api2007.Options;

import scripts.MontsTanner.states.Bank;
import scripts.MontsTanner.utils.Variables;

public class SwitchStates {
	
	enum State {
		WALK_TO_BANK,
		WALK_TO_TANNER,
		BANK,
		TAN
	}
	
	private static Bank bank = new Bank();
	private static Tan tan = new Tan();
	
	public static void run() {
		
		if (!Game.isRunOn() && Game.getRunEnergy() > Variables.get().abc_util.generateRunActivation()) {
			Options.setRunOn(true);
		}
		
		switch(state()) {
		
		case WALK_TO_BANK:
			Variables.get().status = "Walking to bank";
			bank.walkToBank();
			Variables.get().abc_util.performTimedActions();
			break;
		case WALK_TO_TANNER:
			Variables.get().status = "Walking to tanner";
			tan.walkToTanner();
			Variables.get().abc_util.performTimedActions();
			break;
		case TAN:
			Variables.get().status = "Tanning hides";
			tan.tanHides();
			break;
		case BANK:
			Variables.get().status = "Banking";
			bank.bank();
			break;
		}		
		General.sleep(100,200);

	}
	
	private static State state() {
		if(bank.needToBank()) {
			if(!Banking.isInBank()) {
				return State.WALK_TO_BANK;
			} else {
				return State.BANK;
			}
		} else {
			if(tan.isAtTanner()) {
				return State.TAN;
			} else {
				return State.WALK_TO_TANNER;
			}
		}
	}
}
