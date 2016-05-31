package scripts.api.interaction;

import org.tribot.api.DynamicClicking;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;

/**
 * @author Montreal176
 */
public class ClickObject {
    
    //Interact by ID
    public static boolean interact(final int id, final String action) {
    	RSObject[] object = Objects.findNearest(15, Filters.Objects.idEquals(id));
    	
    	if(object != null) {		
    		return interact(object[0], action);
    	} 
    	return false;   	
    }   
    //Interact by name
    public static boolean interact(final String name, final String action) {  	
    	RSObject[] object = Objects.findNearest(15, Filters.Objects.nameContains("name"));
    	
    	if(object != null) {
    		return interact(object[0], action);
    	}
    	return false;
    }
    
    public static boolean interact(final RSObject object, final String action) {  	
    	if(!object.isOnScreen() && Player.getPosition().distanceTo(object) > 6) { 		
    		WebWalking.walkTo(object.getPosition());
    		
    	} else {
    		return DynamicClicking.clickRSObject(object,action);
    	}
    	return false;
    }

}