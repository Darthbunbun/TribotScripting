package scripts.Smither;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.interfaces.MouseActions;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;

import scripts.Smither.gui.MainGui;
import scripts.Smither.states.SwitchStates;
import scripts.Smither.utils.Variables;

@ScriptManifest(authors = { "Montreal176" }, category = "Smithing", name = "CannonballSmither")

public class Main extends Script implements Painting, Starting, MessageListening07, MouseActions {
	
	private boolean paintEnabled;
	
	@Override
	public void run() {
		
		MainGui gui = new MainGui();
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
		Variables.get().startingExp = Skills.getXP(SKILLS.SMITHING);
		Variables.get().startLevel = Skills.getCurrentLevel(SKILLS.SMITHING);
		Variables.get().startTime = Timing.currentTimeMillis();	
		General.println("Thanks for choosing CannonballSmither by Montreal176!");
		paintEnabled = true;
	}
	
	private Image getImage(String url) {
		try {
			return ImageIO.read(new URL("http://i.imgur.com/GK9j0xD.png"));
		} catch(IOException e) {
			return null;
		}
	}
		private final Image img = getImage("your_img_url");
		
		Font font = new Font("Verdana",Font.PLAIN, 14);

		Point mousePos = new Point();

	public void onPaint(Graphics g) {
		
		if(paintEnabled) {

			Graphics2D gg = (Graphics2D)g;
			gg.drawImage(img,0,304,null);
			
			
			long runTime = System.currentTimeMillis() - Variables.get().startTime;
			int cBallsMade = Variables.get().cannonballsMade;
	        long cBallsPerHour =  Math.round(cBallsMade / (runTime / 3600000.0));
			int expGain = Skills.getXP(SKILLS.SMITHING) - Variables.get().startingExp;
			int currentLevel = Skills.getActualLevel(SKILLS.SMITHING);
			int gainedLvl = currentLevel - Variables.get().startLevel;
			int xpPerHour = (int) (expGain * 3600000d /runTime);
			
			g.setFont(font);	
			g.setColor(Color.WHITE);
			
			g.drawString("Runtime: " + Timing.msToString(runTime), 160, 370);
			g.drawString("Cannonballs Made: " + cBallsMade + "(" + cBallsPerHour + ")", 160, 390);
			g.drawString("Current Level : " + currentLevel + "(" + gainedLvl + ")", 160, 410);
			g.drawString("XP Gained: " + expGain + "(" + xpPerHour + ")", 160, 430);
			g.drawString("Status: " + Variables.get().status, 160, 450);
		}
	}
	
	@Override
	public void serverMessageReceived(String message) {
		
		if(message.contains("You remove the cannonballs from the mould")) {
			Variables.get().cannonballsMade +=4 ;
		}	
	}
	
	//Hiding paint when it's clicked.    
	@Override
	public void mouseClicked(Point click, int button, boolean isBot) {
		Rectangle hideZone = new Rectangle(0,304,520,170);
		if(!isBot && button == 1 && hideZone.contains(click) ) {
			paintEnabled = (!paintEnabled);
		}	
	}

	//Unused Methods -- However they had to be imported.
		
	@Override
	public void clanMessageReceived(String arg0, String arg1) {}

	@Override
	public void duelRequestReceived(String arg0, String arg1) {}

	@Override
	public void personalMessageReceived(String arg0, String arg1) {}

	@Override
	public void playerMessageReceived(String arg0, String arg1) {}

	@Override
	public void tradeRequestReceived(String arg0) {}

	@Override
	public void mouseDragged(Point arg0, int arg1, boolean arg2) {}

	@Override
	public void mouseMoved(Point arg0, boolean arg1) {}

	@Override
	public void mouseReleased(Point arg0, int arg1, boolean arg2) {}

}