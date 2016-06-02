package scripts.MontsTiaraCrafter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;

import scripts.MontsTiaraCrafter.states.SelectState;
import scripts.MontsTiaraCrafter.utils.Variables;


public class Main extends Script implements Starting, Painting {
	
	@Override
	public void run() {
		
		TiaraCrafterGui gui = new TiaraCrafterGui();
		gui.setVisible(true);
		
		while(gui.isVisible()) {
			General.sleep(100,120);
		}	
		
		while (Variables.get().hasMats) {
			General.sleep(100,200);
			SelectState.run();
		}
	}
	

	@Override
	public void onStart() {
		Variables.reset();
		Variables.get().startTime = System.currentTimeMillis();
		Variables.get().startingLevel = Skills.getCurrentLevel(SKILLS.RUNECRAFTING);
		Variables.get().status = "Initializing";
		General.println("Thanks for choosing Monts Tiara Crafter!");
	}
	
	Font font = new Font("Verdana",Font.PLAIN, 14);

	@Override
	public void onPaint(Graphics g) {
		
		long runTime = System.currentTimeMillis() - Variables.get().startTime;
		int tiarasMade = Variables.get().tiarasMade;
        long tiarasPerHour =  Math.round(tiarasMade / (runTime / 3600000.0));
		int currentLevel = Skills.getActualLevel(SKILLS.RUNECRAFTING);
		int gainedLvl = currentLevel - Variables.get().startingLevel;
		
		g.setFont(font);	
		g.setColor(Color.WHITE);
		
		g.drawString("Runtime: " + Timing.msToString(runTime), 4, 290);
		g.drawString("Tiaras made: " + tiarasMade + "(" + tiarasPerHour + ")", 4, 305);
		g.drawString("Current Level : " + currentLevel + "(" + gainedLvl + ")", 4, 320);
		g.drawString("Status: " + Variables.get().status, 4, 335);		
	}
}
