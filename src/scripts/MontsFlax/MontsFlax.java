package scripts.MontsFlax;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;

import scripts.MontsFlax.states.GetState;
import scripts.MontsFlax.utils.Variables;

@ScriptManifest(authors = { "Montreal176" }, category = "Crafting", name = "MontsFlaxSpinner")


public class MontsFlax extends Script implements Painting, Starting {
	
	private GetState getState = new GetState();
	
	@Override
	public void run() {
		while(Variables.get().hasMats) {
			General.sleep(250,350);		
			getState.run();	
		}
		
	}

	@Override
	public void onStart() {
		Variables.reset();
		Variables.get().status = "initializing";
		Variables.get().startTime = System.currentTimeMillis();
		Variables.get().startingExp = Skills.getXP(SKILLS.CRAFTING);	
		Variables.get().startingLevel = Skills.getCurrentLevel(SKILLS.CRAFTING);
		General.println("Thank's for choosing MontsFlaxSpinner!");
	}
	
	Font font = new Font("Verdana",Font.PLAIN, 14);

	@Override
	public void onPaint(Graphics g) {
		
		long runTime = System.currentTimeMillis() - Variables.get().startTime;
		int expGain = Skills.getXP(SKILLS.CRAFTING) - Variables.get().startingExp;
		int level = Skills.getCurrentLevel(SKILLS.CRAFTING);
		int levelGain = level - Variables.get().startingLevel;
		int xpPerHour = (int) (expGain * 3600000d /runTime);
		int stringsMade = Variables.get().stringsMade = expGain / 15;
		int stringsPerHour = (int) (stringsMade * 3600000d /runTime);
		
		g.setFont(font);	
		g.setColor(Color.GREEN);
		
		g.drawString("Runtime: " + Timing.msToString(runTime), 4,275);
		g.drawString("Bowstrings made: " +  stringsMade + "(" + stringsPerHour + ")", 4, 290);
		g.drawString("Level : " + level + "(" + levelGain + ")", 4, 305);
		g.drawString("Exp Gain: " + expGain + "(" + xpPerHour + ")", 4, 320);
		g.drawString("Status: " + Variables.get().status, 4, 335);
	}
}
