package scripts.MontsCwars.utils;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.api.antiban.PersistantABCUtil;

public class Variables {
	
	private static Variables vars;

	public static Variables get(){
		return vars == null ? vars = new Variables() : vars;
	}
	
	public static void reset() {
		vars = null;
	}
	
	public String portal;
	public long startTime;
	public PersistantABCUtil abc_util = new PersistantABCUtil();
	public int numGames;
	public int numGamesGui;
	public String status;
	
	public RSArea afkArea;
	
	// Sara area  new RSArea(new RSTile(2423,3079,2), new RSTile(2431,3072,2));

}
