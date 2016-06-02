package scripts.MontsTiaraCrafter.utils;

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
	
	//paint variables
	public int tiarasMade;
	public long startTime;
	public String status;
	public int startingLevel;

	public String tiaraToCraft;
	public String talismanNeeded;
	public String tiara = "Tiara";
	
	public boolean hasMats = true;
	
	public PersistantABCUtil abc_util = new PersistantABCUtil();

	
	public RSTile[] altarPath;

}
