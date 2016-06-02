package scripts.MontsTanner.utils;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;

import scripts.api.antiban.PersistantABCUtil;

public class Variables {

	private static Variables vars;

	public static Variables get()
	{
		return vars == null ? vars = new Variables() : vars;
	}
	
	public static void reset() 
	{
		vars = null;
	}
	
	public long startTime;
	public String status;
	public String leather;
	public String hide;
	public String tannerName = "Ellis";
	public int coinsToMake;
	public int child;
	
	public RSArea tanArea = new RSArea(new RSTile(3273,3189,0), new RSTile(3276,3194,0));
	public RSTile tanTile = new RSTile(3275,3191),
				  bankTile = new RSTile(3269,3167,0);
	
		
	public boolean hasMats = true; 
	public PersistantABCUtil abc_util = new PersistantABCUtil();
	public DPathNavigator dPath = new DPathNavigator();
	public boolean superEnergy;
	
}
