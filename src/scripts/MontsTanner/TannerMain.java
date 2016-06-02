package scripts.MontsTanner;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;

import scripts.MontsTanner.states.SwitchStates;
import scripts.MontsTanner.utils.Variables;

@ScriptManifest(authors = { "Montreal176" }, category = "Money Making", name = "MontsTanner")

public class TannerMain extends Script implements Starting, Painting {

	@Override
	public void run() {
		
		Gui gui = new Gui();
		gui.setVisible(true);
		
		while(gui.isVisible()) {
			General.sleep(100,120);
		}
		
		while (Variables.get().hasMats) {
			General.sleep(100,250);
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
		
		@Override
		public void onPaint(Graphics g) {
				
				long runTime = System.currentTimeMillis() - Variables.get().startTime;
		
				g.setFont(font);	
				g.setColor(Color.GREEN);
				
				g.drawString("Runtime: " + Timing.msToString(runTime), 4,305);
				g.drawString("Status: " + Variables.get().status, 4, 335);
		}
}
