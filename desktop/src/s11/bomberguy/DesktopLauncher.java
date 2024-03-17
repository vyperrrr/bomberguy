package s11.bomberguy;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import s11.bomberguy.gui.MenuPanel;
import s11.bomberguy.Controls;

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
		new Lwjgl3Application(new GameModel(Controls.getControls()), config);
	}
}
