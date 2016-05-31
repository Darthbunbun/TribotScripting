package scripts.api.helpers;

import org.tribot.api2007.Interfaces;
import org.tribot.api.General;
import org.tribot.api.Timing;

public class InterfacesHelper {

	public static boolean interfaceOpen(int i, int j) {
        long t = System.currentTimeMillis();
        while(Timing.timeFromMark(t) < i) {
                if(Interfaces.isInterfaceValid(j)) {
                	General.sleep(600,1000);
                        return true;
                }
        }
        return false;
    }
}