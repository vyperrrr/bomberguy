package s11.bomberguy;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Config {
    private static Lwjgl3ApplicationConfiguration config = null;

    private Config() {}

    public static Lwjgl3ApplicationConfiguration getConfig() {
        if (config == null) {
            config = new Lwjgl3ApplicationConfiguration();
            configure();
        }
        return config;
    }

    private static void configure() {
        config.setForegroundFPS(60);
        config.setTitle("BomberGuy");
        config.setMaximized(true);
    }
}
