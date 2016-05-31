package scripts.MontsFlax.utils;

import scripts.api.antiban.PersistantABCUtil;

public class Variables {
	
	private static Variables vars;

	public static Variables get(){
		return vars == null ? vars = new Variables() : vars;
	}
	
	public static void reset() {
		vars = null;
	}
	
	//Paint variables
	public String status;
	public long startTime;
	public int stringsMade;
	public int startingExp;
	public int startingLevel;
	
	//Script variables
	public boolean hasMats = true;
	public PersistantABCUtil abc_util = new PersistantABCUtil();
	

}
