package scripts.MontsCwars.states;

import org.tribot.api2007.Player;

import scripts.MontsCwars.utils.Variables;
import scripts.api.helpers.InterfacesHelper;

public class SetState {
	
	private static EnterPortal enter = new EnterPortal();
	private static AFK afk = new AFK();
	private static WalkToAfk walkAfk = new WalkToAfk();
	
	public enum State {
		ENTER_PORTAL,
		GET_TO_SPOT,
		AFK_PORTAL,
		AFK_GAME,
		INTERFACE
	}
	
	public static void run() {
		
		switch(state()) {
		
		case ENTER_PORTAL:
			Variables.get().status = "Entering portal";
			enter.enterPortal();
			Variables.get().abc_util.generateTrackers();
			break;
		case AFK_PORTAL:
			Variables.get().status = "Waiting for game";
			afk.afkPortal();
			break;
		case AFK_GAME:
			Variables.get().status = "Afking during game";
			afk.afkGame();
			break;
		case GET_TO_SPOT:
			Variables.get().status = "Walking to afk spot";
			walkAfk.walk();
			break;
		case INTERFACE:
			Variables.get().status = "Finished a game";
			Variables.get().numGames++;
			break;
		}
	}
	
	public static State state() {
		if(InterfacesHelper.interfaceOpen(2000, 219)) {
			return State.INTERFACE;
		}
		if(enter.isAtCwars()) {
			return State.ENTER_PORTAL;
		} else if(Player.getPosition().getY() > 9000) {
			return State.AFK_PORTAL;		
		} else {
			if(Variables.get().afkArea.contains(Player.getPosition())) {
				return State.AFK_GAME;
			} else {
				return State.GET_TO_SPOT;
			}
		}
	}
}
