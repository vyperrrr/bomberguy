package s11.bomberguy;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import s11.bomberguy.game.GameModel;
import s11.bomberguy.gui.MenuPanel;

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
		new Lwjgl3Application(new GameModel(), Config.getConfig());
	}
}
