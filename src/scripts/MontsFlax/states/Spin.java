package scripts.MontsFlax.states;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSInterfaceChild;

import scripts.MontsFlax.utils.Constants;
import scripts.MontsFlax.utils.Variables;
import scripts.api.Conditions;
import scripts.api.helpers.InterfacesHelper;
import scripts.api.interaction.ClickObject;

public class Spin {
	
	public boolean spinWheel() {
		
	if(ClickObject.interact("Spinning wheel", "Spin")) {
			return true;
		}
		return false;
	}
	
	public boolean flaxInterface() {
		RSInterfaceChild flax = Interfaces.get(459,91);			
	
		if(flax != null) {
			if(Inventory.getCount("Flax") <= 10) {
				flax.click("Make 10");
				Variables.get().abc_util.generateTrackers();
				
			} else if(flax.click("X")) {
				General.sleep(250,500);
				if(InterfacesHelper.interfaceOpen(2000, 162)) {
					typeAmount();
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean typeAmount() {
		int rand = General.random(1, 50);		
		if(rand <= 10) {
			Keyboard.typeSend("28");
			Variables.get().abc_util.generateTrackers();
			return true;
			
		} else if(rand <= 20) {
			Keyboard.typeSend("33");
			Variables.get().abc_util.generateTrackers();
			return true;
			
		} else {
			Keyboard.typeSend("111");
			Variables.get().abc_util.generateTrackers();
			return true;
		}
	}

	public boolean isCrafting() {
		if(Inventory.getCount("Flax") == 0 || !Player.getPosition().equals(Constants.WHEEL_TILE)
		|| NPCChat.getMessage() != null) {
			return false;
		} else {
			return Timing.waitCondition(Conditions.isAnimatingCondition(), 1500);
		}
	}
	
	public boolean isAtFlax() {
		return Constants.WHEEL_AREA.contains(Player.getPosition());
	}
}
