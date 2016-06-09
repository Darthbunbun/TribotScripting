package scripts.MontsTiaraCrafter.states;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;

import scripts.MontsTiaraCrafter.utils.Variables;
import scripts.api.helpers.ACamera;
import scripts.api.interaction.ItemOnObject;

public class Altar {
	
	private ACamera aCamera = new ACamera();
	
	public void enterRuins() {
		if(ItemOnObject.useItemOnObject("Use","Mysterious ruins", Variables.get().talismanNeeded)) {
			isInAltarCondition();
		}
	}
	
	public boolean exitPortal() {
		RSObject[] portal = Objects.findNearest(15, Filters.Objects.nameContains("Portal"));
		
		if(!portal[0].isOnScreen() || Player.getPosition().distanceTo(portal[0]) > 6) {
			aCamera.turnToTile(portal[0].getPosition());
			Walking.walkTo(portal[0].getPosition());
		}
		if(portal.length > 0 && DynamicClicking.clickRSObject(portal[0], "Use")) {
			return !isInAltarCondition();
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
