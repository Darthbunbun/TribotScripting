package scripts.MontsTanner;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Starting;

import scripts.MontsTanner.states.SwitchStates;
import scripts.MontsTanner.utils.Variables;

@ScriptManifest(authors = { "Montreal176" }, category = "Money Making", name = "MontsTanner")

public class TannerMain extends Script implements Starting {

	@Override
	public void run() {
		
		Gui gui = new Gui();
		gui.setVisible(true);
		
		while(gui.isVisible()) {
			General.sleep(100,120);
		}
		
		while (Variables.get().hasMats) {
			General.sleep(100,200);
			SwitchStates.run();
		}	
	}
	
	@Override
	public void onStart() {
	
		Variables.reset();
		println("Thanks for choosing MontsTanner! Please report any script bugs in the script's thread." );
		Variables.get().startTime = Timing.currentTimeMillis();	
	}
		Font font = new Font("Verdana",Font.PLAIN, 14);
		
		public void onPaint(Graphics g) {

				
				long runTime = System.currentTimeMillis() - Variables.get().startTime;
				int leatherMade = Variables.get().leatherMade;
		        long leatherPerHour =  Math.round(leatherMade / (runTime / 3600000.0));

				
				g.setFont(font);	
				g.setColor(Color.WHITE);
				
				g.drawString("Runtime: " + Timing.msToString(runTime), 4,335);
				g.drawString("Cannonballs Made: " + leatherMade + "(" + leatherPerHour + ")", 4, 320);
				g.drawString("Status: " + Variables.get().status, 4, 305);
		}

}
