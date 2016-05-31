 package scripts.MontsTanner.states;

import org.tribot.api.General;
import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;

public class Potion {
		
	public static void drinkPot() {
		Filter<RSItem> superPots = Filters.Items.nameContains("Super energy");

		RSItem[] potion = Inventory.find(superPots);		
		
		if(!GameTab.TABS.INVENTORY.isOpen()) {
			GameTab.TABS.INVENTORY.open();
		}
			
		if(potion.length > 0) {	
			while(potion[0].click("Drink")) {
				General.sleep(1500,2000); //Sleep to make sure potion drinks.
			}
		}
	}
	
	public static boolean needsSuperEnergy() {
		return Game.getRunEnergy() < 20;
	}
}
