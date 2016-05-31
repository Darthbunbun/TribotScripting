package scripts.Smither.utils;

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
	
	public int cannonballsMade;
	public int startingExp;
	public long startTime;
	public String status;
	public int startLevel;		
	public String task;
	
	public boolean hasMats = true; //We have steel bars.
	public RSTile furnaceTile;
	public RSArea furnaceArea;
	public String location;
	public PersistantABCUtil abc_util = new PersistantABCUtil();

}
