package scripts.MontsTiaraCrafter.states;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Objects;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;

import scripts.MontsTiaraCrafter.utils.Variables;
import scripts.api.interaction.ItemOnObject;

public class Altar {
	
	public void enterRuins() {
		if(ItemOnObject.useItemOnObject("Use","Mysterious ruins", Variables.get().talismanNeeded)) {
			isInAltarCondition();
		}
	}
	
	public boolean exitPortal() {
		RSObject[] portal = Objects.findNearest(15, Filters.Objects.nameContains("Portal"));

		if(portal.length > 0 && portal[0].isOnScreen() && DynamicClicking.clickRSObject(portal[0], "Use")) {
			return !isInAltarCondition();
		} else {
			Camera.turnToTile(portal[0].getPosition());
		}
		return false;	
	}
	
	public boolean isInAltar() {
		RSObject[] portal = Objects.findNearest(15, Filters.Objects.nameContains("Portal"));
		
		if(portal.length > 0) {
			return true;
		}
		return false;		
	}
	
	public boolean isNearAltar() {
		RSObject[] ruins = Objects.findNearest(5, "Mysterious ruins");
		
		if(ruins.length > 0) {
			return true;
		}
		return false;
	}
	
	private boolean isInAltarCondition() {
		
		return Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				General.sleep(250,300);
				return isInAltar();
			}
		}, 3000);
	}
}
