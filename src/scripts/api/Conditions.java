package scripts.api;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.Login.STATE;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSTile;

import scripts.api.helpers.BankHelper;


public class Conditions {
	
	public static final Condition IN_BANK_CONDITION = inBankCondition();
	public static final Condition IS_BANK_OPEN_CONDITION = isBankOpenCondition();

	public static Condition inBankCondition() {
		return new Condition() {		
			@Override
			public boolean active() {
				General.sleep(100,200);
				return Banking.isInBank();
			}
		};		
	}
	
	public static Condition isBankOpenCondition() {
		return new Condition() {		
			@Override
			public boolean active() {
				General.sleep(100,200);
				return Banking.isBankScreenOpen();
			}
		};		
	}
	
	public static Condition isBankLoadedCondition(){
		return new Condition() {		
			@Override
			public boolean active() {
				General.sleep(100,200);
				return BankHelper.areBankItemsLoaded();
			}
		};	
	}
	
	public static Condition inventoryContainsCondition(String name) {
		return new Condition() {	
			@Override
			public boolean active() {
				General.sleep(100,200);
				return Inventory.getCount(name) > 0;
			}
		};
	}
	
	public static Condition inventoryContainsCondition(int id) {
		return new Condition() {
			@Override
			public boolean active() {
				General.sleep(100,200);
				return Inventory.getCount(id) > 0;
			}
		};
	}
	
	public static Condition inventoryChangedCondition(String item) {
		final int items = Inventory.getCount(item) ;
				
		return new Condition() {
			@Override
			public boolean active() {
				General.sleep(100,200);
				return items != Inventory.getCount(item);
			}
		};
	}
	
	public static Condition inventorySizeCondition(int size) {
		return new Condition() {
			@Override
			public boolean active() {
				General.sleep(100,200);
				return Inventory.getAll().length == size;
			}
		};
	}
	
	public static Condition isInAreaCondition(final RSArea area) {
		return new Condition() {
			@Override 
			public boolean active() {
				General.sleep(100,200);
				return area.contains(Player.getPosition());
			}
		};		
	}
	
	public static Condition isNearTileCondition(final RSTile tile) {
		return new Condition() {
			@Override
			public boolean active() {
				General.sleep(100,200);
				return Player.getPosition().distanceTo(tile) < 2;
			}
		};
	}
	
	public static Condition isOnTile(final RSTile tile) {
		return new Condition() {
			@Override
			public boolean active() {
				General.sleep(100,200);
				return Player.getPosition().equals(tile);
			}
		};
	}
	
	public static Condition isUpTextCondition(final String str) {
		return new Condition() {
			@Override 
			public boolean active() {
				General.sleep(100,200);
				return Game.isUptext(str);
			}
		};	
	}
	
	public static Condition interfaceUpCondition(final int parent) {
		RSInterface i = Interfaces.get(parent);
		return new Condition() {
			@Override
			public boolean active(){
				General.sleep(100,200);
				return i != null && !i.isHidden();
			}
		};
	}
	
	public static Condition interfaceNotUpCondition(final int parent) {
		RSInterface i = Interfaces.get(parent);
		return new Condition() {
			@Override
			public boolean active(){
				General.sleep(100,200);
				return i == null;
			}
		};
	}
	
	public static Condition isAnimatingCondition() {
		return new Condition() {
			@Override
			public boolean active(){
				General.sleep(100,200);
				return Player.getAnimation() != -1;
			}
		};
	}
	
	public static Condition inGameCondition() {
		return new Condition() {
			@Override
			public boolean active(){
				General.sleep(100,200);
				return Login.getLoginState() == STATE.INGAME && Game.getGameState() == 30;
			}
		};
	}
	
	public static Condition planeCondition(int plane) {
		return new Condition() {
			@Override
			public boolean active(){
				General.sleep(100,200);
				return Player.getPosition().getPlane() == plane;
			}
		};
	}
}