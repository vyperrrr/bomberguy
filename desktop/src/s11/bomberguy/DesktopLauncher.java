package s11.bomberguy;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import s11.bomberguy.game.GameModel;
import s11.bomberguy.gui.MenuPanel;


import java.util.ArrayList;

public class DesktopLauncher {
	public static void main (String[] arg) {
		launchMenu();
	}

	//Launches GUI
	public static void launchMenu()
	{
		new MenuPanel();
	}

	//Launches Game window
	public static void launchGame()
	{
		Lwjgl3ApplicationConfiguration config = Config.getConfig();
		ArrayList<PlayerControl> controls = Controls.getControls();
		GameSetup setup =  GameSetup.getGameSetup();
		// Fails of bad folder structure
		// GameModel model = new GameModel(controls, setup);
		GameModel model = new GameModel(controls, setup.getPlayerNum());
		new Lwjgl3Application(model, config);
	}
}
