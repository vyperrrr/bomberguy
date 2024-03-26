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
import s11.bomberguy.characters.Character;
import s11.bomberguy.characters.Monster;
import s11.bomberguy.characters.Player;
import s11.bomberguy.explosives.Explosion;
import s11.bomberguy.mapElements.Crate;
import s11.bomberguy.mapElements.Wall;
import s11.bomberguy.PlayerControl;
import s11.bomberguy.TileSpriteFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;


public class GameModel extends Game {

    // Initialize with dummy data
    private Timer timer;
    private final GameSetup setup;
    private ArrayList<Player> players;
    private ArrayList<Monster> monsters;
    private final ArrayList<PlayerControl> controls;
    private AssetManager assetManager;
    private TiledMap tiledMap;
    private Collidables collidables;


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

        // For testing reasons
        Random random = new Random();

        //Generate monsters


        // Add collidable objects to collidables list
        this.collidables = Collidables.getInstance();

        collidables.addCollidables(players);
        collidables.addCollidables(monsters);

        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader());
        assetManager.load("assets/maps/map1.tmx", TiledMap.class);
        assetManager.load("assets/maps/testMap.tmx", TiledMap.class);
        assetManager.finishLoading();

        //tiledMap = assetManager.get("assets/maps/testMap.tmx", TiledMap.class);
        tiledMap = assetManager.get("assets/maps/map1.tmx", TiledMap.class);

        // Generate players
        MapLayer playerSpawnLayer = tiledMap.getLayers().get("Player spawn");
        if (playerSpawnLayer instanceof TiledMapTileLayer) {
            TiledMapTileLayer tiledLayer = (TiledMapTileLayer) playerSpawnLayer;
            int playerNum = setup.getPlayerNum();
            int currentPlayerNum = 0; // Track the number of players added
            // Iterate over each cell in the tiled layer
            for (int y = 0; y < tiledLayer.getHeight(); y++) {
                for (int x = 0; x < tiledLayer.getWidth(); x++) {
                    // Check if the number of players added equals playerNum
                    if (currentPlayerNum >= playerNum) {
                        break; // Exit the loop if the limit is reached
                    }
                    // Get the cell at the current position
                    TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                    if (cell != null) {
                        // Retrieve the position of the tile
                        float tileX = x * tiledLayer.getTileWidth();
                        float tileY = y * tiledLayer.getTileHeight();
                        // Create a new Player instance and pass the position
                        players.add(new Player(tileX, tileY, 24, 24, 100, controls.get(currentPlayerNum)));
                        currentPlayerNum++; // Increment the number of players added
                    }
                }
            }
        }

        MapLayer monsterSpawnLayer = tiledMap.getLayers().get("Monster spawn");
        if (monsterSpawnLayer instanceof TiledMapTileLayer) {
            TiledMapTileLayer tiledLayer = (TiledMapTileLayer) monsterSpawnLayer;
            // Iterate over each cell in the tiled layer
            for (int y = 0; y < tiledLayer.getHeight(); y++) {
                for (int x = 0; x < tiledLayer.getWidth(); x++) {
                    // Check if the number of players added equals playerNum
                    // Get the cell at the current position
                    TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                    if (cell != null) {
                        // Retrieve the position of the tile
                        float tileX = x * tiledLayer.getTileWidth();
                        float tileY = y * tiledLayer.getTileHeight();
                        // Create a new Player instance and pass the position
                        monsters.add(new Monster(tileX, tileY, 24, 24, 50));
                    }
                }
            }
        }

        setCollidableMapLayers();

        setScreen(new GameScreen(this));
    }

    public void setCollidableMapLayers()
    {
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
                            Sprite sprite = TileSpriteFactory.createCrateOrWall(tiledMap, tiledLayer, col, row);
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

    public Collidables getCollidables() {
        return collidables;
    }

    public void setCollidables(Collidables collidables) {
        this.collidables = collidables;
    }
}
