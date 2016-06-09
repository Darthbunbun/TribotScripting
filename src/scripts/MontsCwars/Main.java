package scripts.MontsCwars;

import java.awt.Graphics;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;

import scripts.MontsCwars.states.SetState;
import scripts.MontsCwars.utils.Variables;

public class Main extends Script implements Starting, Painting {
	
	@Override
	public void run() {
		
		CwarsGui gui = new CwarsGui();
		gui.setVisible(true);
		
		while(gui.isVisible()) {
			General.sleep(250,300);
		}
		
		while(Variables.get().numGames < Variables.get().numGamesGui) {
			General.sleep(250,300);
			SetState.run();
		}	
	}


	@Override
	public void onStart() {
		Variables.reset();
		Variables.get().startTime = System.currentTimeMillis();
	}

	@Override
	public void onPaint(Graphics g) {
		long runTime = System.currentTimeMillis() - Variables.get().startTime;
		int gamesPlayed = Variables.get().numGames;
		int gamesRemaining = Variables.get().numGamesGui - gamesPlayed;
		
		g.drawString("Runtime: " + Timing.msToString(runTime), 4, 290);
		g.drawString("Games played: " + gamesPlayed, 4, 305);
		g.drawString("Games remaining " + gamesRemaining, 4, 320);
		g.drawString("Status: " + Variables.get().status, 4, 335);		
		
	}
}
