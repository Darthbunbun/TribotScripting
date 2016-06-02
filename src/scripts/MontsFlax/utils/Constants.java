package scripts.MontsFlax.utils;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public class Constants {
	
	public static final RSTile	BANK_TILE = new RSTile(3208,3220,2),
								WHEEL_TILE = new RSTile(3209,3213,1),
								BANK_STAIRS_TILE = new RSTile(3205,3209,2);
	
	
	public static final RSArea WHEEL_AREA = new RSArea(new RSTile(3208,3216,1), new RSTile(3211,3212,1));

}
