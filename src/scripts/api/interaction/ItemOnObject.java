package scripts.api.interaction;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;

public class ItemOnObject extends ClickObject{
	
	
	public static boolean useItemOnObject(String action, String name, String itemName) {
		RSObject[] object = Objects.findNearest(10, Filters.Objects.nameContains(name));
		if(object != null) {
			interact(object[0], action,  itemName,  name);
		}
		return false;	
	}

	public static boolean interact(RSObject object, String action, String itemName, String name) {
		RSItem[] items = Inventory.find(itemName);
		
		if(!object.isClickable()) {
			Camera.turnToTile(object.getPosition());
		} 
		if(items.length > 0) {
			if(itemName == null) {
				itemName = items[0].getDefinition().getName();
			}
			
			String upText = Game.getUptext();
			
			if(upText == null || !upText.contains(itemName)) {
				if(items[0].click("Use")) {
					
					General.sleep(600,800);
					
					upText = Game.getUptext();
					
					if(upText != null && upText.contains(itemName)) {
						return Clicking.click("Use " + itemName + " -> " + name, object);
					}
				}
			} else { //Bar is already selected
				
				return Clicking.click("Use " + itemName + " -> " + name, object);
			}
		} 
		return false;	
	}
}