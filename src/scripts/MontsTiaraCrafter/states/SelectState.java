package scripts.MontsTiaraCrafter.states;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Options;

import scripts.MontsTiaraCrafter.utils.Variables;
import scripts.MontsTiaraCrafter.states.Altar;
import scripts.MontsTiaraCrafter.states.Bank;
import scripts.MontsTiaraCrafter.states.CraftTiaras;
import scripts.MontsTiaraCrafter.states.TiaraCrafterWalker;


public class SelectState {
	
	private enum State {
		WALK_TO_BANK,
		WALK_TO_ALTAR,
		BANK,
		ENTER_ALTAR,
		LEAVE_ALTAR,
		CRAFT_TIARAS
	}
	
	private static Bank bank = new Bank();
	private static Altar altar = new Altar();
	private static CraftTiaras craftTiara = new CraftTiaras();
	private static TiaraCrafterWalker walk = new TiaraCrafterWalker();
	

	public static void run() {
		
		if (!Game.isRunOn() && Game.getRunEnergy() > Variables.get().abc_util.generateRunActivation()) {
			Options.setRunOn(true);
		}
		
		switch(state()) {
		
		case WALK_TO_BANK:
			Variables.get().status = "Walking to bank";
			walk.walkToBank();
			break;
		case WALK_TO_ALTAR:
			Variables.get().status = "Walking to altar";
			walk.walkToAltar();
			break;
		case BANK:
			Variables.get().status = "Banking";
			bank.bank();
			break;
		case ENTER_ALTAR:
			Variables.get().status = "Entering altar";
			altar.enterRuins();
			break;
		case LEAVE_ALTAR:
			Variables.get().status = "Exiting the portal";
			altar.exitPortal();
			break;
		case CRAFT_TIARAS:
			Variables.get().status = "Crafting tiaras";
			craftTiara.craftTiara();
			break;
		}
		General.sleep(250,300);
	}
	
	private static State state() {
		if(bank.needToBank()) {
			if(altar.isInAltar()) {
				return State.LEAVE_ALTAR;
			}
			if(bank.isInBank()) {
				return State.BANK;
			}
			return State.WALK_TO_BANK;
		} else {
			if(altar.isInAltar()) {
				return State.CRAFT_TIARAS;
			} else if(altar.isNearAltar()) {
				return State.ENTER_ALTAR;
			} else {
				return State.WALK_TO_ALTAR;
			}
		}
	}
}
