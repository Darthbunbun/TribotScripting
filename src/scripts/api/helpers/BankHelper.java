package scripts.api.helpers;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;

import scripts.api.Conditions;

public class BankHelper {
	
	public static boolean waitUntilBankLoaded() {
		 return Timing.waitCondition(Conditions.isBankLoadedCondition(), General.random(3000, 4000));
	}
	
	public static boolean areBankItemsLoaded() {        
		return getCurrentBankSpace() == Banking.getAll().length;
	}
	
	static int getCurrentBankSpace() {	
		RSInterface amount = Interfaces.get(12,5);	
		if(amount != null) {	     
			String txt = amount.getText();	      
			if(txt != null) {	          
				try {	             
					int toInt = Integer.parseInt(txt);	             
					if(toInt > 0)	                 
						return toInt;	       
					} catch(NumberFormatException e) {	           
						return -1;	          
					}	     
				}	
			}        
		return -1;
	}
}
