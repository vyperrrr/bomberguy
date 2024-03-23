package s11.bomberguy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Timer;
import s11.bomberguy.Collidables;
import s11.bomberguy.GameSetup;
import s11.bomberguy.characters.Monster;
import s11.bomberguy.characters.Player;
import s11.bomberguy.mapElements.Crate;
import s11.bomberguy.mapElements.Wall;
import s11.bomberguy.PlayerControl;
import s11.bomberguy.TileSpriteFactory;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;


public class GameModel extends Game {

    // Initialize with dummy data
    private Timer timer;
    private final GameSetup setup;
    private ArrayList<Player> players;
    private ArrayList<Monster> monsters;
    private ArrayList<Crate> crates;
    private ArrayList<Wall> walls;
    private final ArrayList<PlayerControl> controls;

    private AssetManager assetManager;
    private TiledMap tiledMap;


    // Data passed by GUI
    public GameModel(ArrayList<PlayerControl> controls, GameSetup setup) {
        this.controls = controls;
        this.setup = setup;
    }

    @Override
    public void create() {
        // Initialize players
        players = new ArrayList<>();
        monsters = new ArrayList<>();
        crates = new ArrayList<>();
        walls = new ArrayList<>();

        // For testing reasons
        Random random = new Random();

        // Generate players
        IntStream.range(0, setup.getPlayerNum()).forEach(i -> players.add(new Player(random.nextInt(300), random.nextInt(300), 16, 16, 100, controls.get(i))));

        // Add collidable objects to collidables list
        Collidables collidables = Collidables.getInstance();

        collidables.addCollidables(players);
        collidables.addCollidables(monsters);
        collidables.addCollidables(crates);
        collidables.addCollidables(walls);

        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader());
        assetManager.load("assets/maps/map1.tmx", TiledMap.class);
        assetManager.finishLoading();

        tiledMap = assetManager.get("assets/maps/map1.tmx", TiledMap.class);

        setCollidableMapLayers();

        setScreen(new GameScreen(this));
    }

    public void setCollidableMapLayers()
    {
        Collidables collidables = Collidables.getInstance();
        // Get the group layer (folder) named "collidables"
        MapLayer groupLayer = tiledMap.getLayers().get("collidables");

        // Check if the layer is a group layer (folder)
        if (groupLayer instanceof MapGroupLayer) {
            MapGroupLayer group = (MapGroupLayer) groupLayer;

            // Iterate through the child layers of the group layer
            for (MapLayer childLayer : group.getLayers()) {
                // Check if the child layer is a TiledMapTileLayer (or any other specific type if needed)
                if (childLayer instanceof TiledMapTileLayer) {
                    TiledMapTileLayer tiledLayer = (TiledMapTileLayer) childLayer;

                    // Iterate through the tiles of the tiled layer
                    for (int row = 0; row < tiledLayer.getHeight(); row++) {
                        for (int col = 0; col < tiledLayer.getWidth(); col++) {
                            Sprite sprite = TileSpriteFactory.createSpriteFromTile(tiledMap, tiledLayer, col, row);
                            if (sprite != null) {
                                // Add the sprite to your sprite batch or render it as needed
                                collidables.addCollidable(sprite);
                            }
                        }
                    }
                }
            }
        }
    }


    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public ArrayList<Crate> getCrates() {
        return crates;
    }

    public void setCrates(ArrayList<Crate> crates) {
        this.crates = crates;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<Wall> walls) {
        this.walls = walls;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public void setTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }
}
