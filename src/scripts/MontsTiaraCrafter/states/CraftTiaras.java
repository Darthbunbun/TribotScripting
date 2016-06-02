package scripts.MontsTiaraCrafter.states;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Camera;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Walking;

import scripts.MontsTiaraCrafter.utils.Variables;
import scripts.api.Conditions;
import scripts.api.interaction.ItemOnObject;

public class CraftTiaras {
	
	public void craftTiara() {
		
		if(GameTab.getOpen() != TABS.INVENTORY) {
			GameTab.open(TABS.INVENTORY);
		} else {		
			while(Inventory.getCount(Variables.get().talismanNeeded) > 0) {
				General.sleep(250,500);
				RSObject[] altar = Objects.find(35, Filters.Objects.nameContains("Altar"));
				int count = Inventory.getAll().length;
		
				if(!altar[0].isClickable()) {
					Walking.walkTo(altar[0].getPosition());
				}
				
				Camera.turnToTile(altar[0].getPosition());
				
				if(ItemOnObject.useItemOnObject("Use","Altar", Variables.get().talismanNeeded)) {
					Timing.waitCondition(Conditions.inventorySizeCondition(count-1), 5000);
					Variables.get().tiarasMade += 1;
				}
			}
		}
	}
}