package scripts.MontsFlax.states;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.Options;

import scripts.MontsFlax.utils.Variables;
import scripts.api.helpers.InterfacesHelper;

public class GetState {
	
	private Bank bank = new Bank();
	private Spin spin = new Spin();
	private FlaxWalker walk = new FlaxWalker();
	
	public enum State {
		BANK,
		WALK_TO_WHEEL,
		USE_WHEEL,
		SELECT_FLAX,
		WALK_TO_BANK,
		SLEEP
	}
	
	public void run() {
		
		if (!Game.isRunOn() && Game.getRunEnergy() > Variables.get().abc_util.generateRunActivation()) {
			Options.setRunOn(true);
		}
		
		switch(state()) {
		
		case BANK:
			Variables.get().status = "Banking";
			bank.bank();
			break;
		case WALK_TO_WHEEL:
			Variables.get().status = "Walking to wheel";
			walk.walkToWheel();
			break;
		case WALK_TO_BANK:
			Variables.get().status = "Walking to bank";
			walk.walkToBank();
			break;
		case USE_WHEEL:
			Variables.get().status = "Using Wheel";
			spin.spinWheel();
			break;
		case SELECT_FLAX:
			Variables.get().status = "Selecting X";
			spin.flaxInterface();
			break;
		case SLEEP:
			Variables.get().status = "Making flax";	
			 while(spin.isCrafting()) {
				 General.sleep(100,200);        
				 if(Variables.get().abc_util.shouldLeaveGame() && Mouse.isInBounds()) {
					 Variables.get().abc_util.leaveGame();                 
				 } else if(Mouse.isInBounds()){
					Variables.get().abc_util.performTimedActions();
				 }
			 }
			 break;
		}
		General.sleep(100,200);
	}
	
	public State state() {
		if(bank.needToBank() && bank.isInBank()) {
			return State.BANK;
		} else if(bank.needToBank() && !bank.isInBank()) {
			return State.WALK_TO_BANK;
		} else {
			if(spin.isAtFlax()) {
				if(InterfacesHelper.interfaceOpen(1500, 459)) {
					return State.SELECT_FLAX;
				} else if(spin.isCrafting()) {
					return State.SLEEP;
				} else {
					return State.USE_WHEEL;
				}
			}
				return State.WALK_TO_WHEEL;
		}
	}
}
