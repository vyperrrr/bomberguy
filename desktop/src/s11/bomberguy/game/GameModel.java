package s11.bomberguy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import s11.bomberguy.*;
import s11.bomberguy.characters.Character;
import s11.bomberguy.characters.Monster;
import s11.bomberguy.characters.Player;
import s11.bomberguy.powerups.*;

import java.util.*;
import java.util.stream.Collectors;


public class GameModel extends Game {

    // Initialize with dummy data
    private ArrayList<Player> players;
    private ArrayList<Monster> monsters;
    private AssetManager assetManager;
    private TiledMap tiledMap;
    private Collidables collidables;
    private int playerNum;
    private int roundNum;
    private int currentRound;

    private HashMap<String, Integer> playerToWinCount;
    private HashMap<Integer, String> roundToWinner;
    private boolean isOver = false;

    // Data passed by GUI
    public GameModel() {
    }

    @Override
    public void create() {
        // Initialize players
        players = new ArrayList<>();
        monsters = new ArrayList<>();
        playerToWinCount = new HashMap<>();
        roundToWinner = new HashMap<>();

        playerNum = GameSetup.getPlayerNum();
        roundNum = GameSetup.getRounds();
        currentRound = 1;

        // Add collidable objects to collidables list
        this.collidables = Collidables.getInstance();

        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader());
        assetManager.load("assets/maps/map" + GameSetup.getMapNum() + ".tmx", TiledMap.class);
        assetManager.load("assets/maps/testMap.tmx", TiledMap.class);
        assetManager.finishLoading();

        tiledMap = assetManager.get("assets/maps/map" + GameSetup.getMapNum() + ".tmx", TiledMap.class);

        // Generate players
        generatePlayers();
        generateMonsters();

        collidables.addCollidables(players);
        collidables.addCollidables(monsters);

        setCollidableMapLayers();

        setScreen(new GameScreen(this));

        this.generatePowerUps();
    }
    private void generatePowerUps() {
        Random random = new Random();

        int crateNum = collidables.getCrates().size();
        int powerUpNum = 0;

        while(powerUpNum < crateNum / 3){
            int randomCrateIndex = random.nextInt(crateNum-1);
            if(!collidables.getCrates().get(randomCrateIndex).isPowerUpInside()){
                collidables.getCrates().get(randomCrateIndex).setPowerUpInside(true);
                powerUpNum++;
            }
        }
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

    public void generatePlayers()
    {
        MapLayer playerSpawnLayer = tiledMap.getLayers().get("Player spawn");
        if (playerSpawnLayer instanceof TiledMapTileLayer) {
            TiledMapTileLayer tiledLayer = (TiledMapTileLayer) playerSpawnLayer;
            int playerNum = GameSetup.getPlayerNum();
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
                        players.add(new Player("Játékos "+(currentPlayerNum+1),tileX, tileY, 24, 24, 100, Controls.getControls().get(currentPlayerNum)));
                        currentPlayerNum++; // Increment the number of players added
                    }
                }
            }
        }
    }

    public void generateMonsters()
    {
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
    }

    public void resetGame() {
        TimerManager.disposeAllTasks();
        players.clear();
        monsters.clear();
        collidables.clearCollidables();

        // Reset other game-related variables
        isOver = false;

        // Reload the original tiled map file
        assetManager.unload("assets/maps/map" + GameSetup.getMapNum() + ".tmx");
        assetManager.load("assets/maps/map" + GameSetup.getMapNum() + ".tmx", TiledMap.class);
        assetManager.finishLoading();

        // Get the reloaded tiled map
        tiledMap = assetManager.get("assets/maps/map" + GameSetup.getMapNum() + ".tmx", TiledMap.class);

        // Regenerate players and monsters
        generatePlayers();
        generateMonsters();

        // Reset collidables
        collidables.addCollidables(players);
        collidables.addCollidables(monsters);

        // Reset collidable map layers
        setCollidableMapLayers();
    }

    public String determineRoundWinner()
    {
        Optional<Player> roundWinner = players.stream().filter(Character::isAlive).collect(Collectors.collectingAndThen(Collectors.toList(), list -> list.size() > 1 ? Optional.empty() : list.stream().findFirst()));

        if(roundWinner.isPresent())
            return roundWinner.get().getName();
        return "Döntetlen";
    }

    public String determineGameWinner() {
        // Assuming playerToWinCount is already populated
        String winner = null;
        int max = Integer.MIN_VALUE;
        boolean hasDuplicate = false;

        for (String playerName : playerToWinCount.keySet()) {
            int wins = playerToWinCount.get(playerName);
            if (wins > max) {
                max = wins;
                winner = playerName;
                hasDuplicate = false; // Reset hasDuplicate flag
            } else if (wins == max) {
                hasDuplicate = true; // Found duplicate
            }
        }

        return hasDuplicate ? "Döntetlen" : winner;
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

    public void setOver() { isOver = true; }

    public boolean isOver() {
        return isOver;
    }

    public int getRoundNum() {
        return roundNum;
    }

    public void setRoundNum(int roundNum) {
        this.roundNum = roundNum;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public HashMap<Integer, String> getRoundToWinner() {
        return roundToWinner;
    }

    public void setRoundToWinner(HashMap<Integer, String> roundToWinner) {
        this.roundToWinner = roundToWinner;
    }

    public HashMap<String, Integer> getPlayerToWinCount() {
        return playerToWinCount;
    }
    public void putDownRandomPowerUp(float x, float y) {
        Random random = new Random();
        switch (random.nextInt(7)){
            case 1:
                collidables.addCollidable(new BonusBomb(x,y));
                break;
            case 2:
                collidables.addCollidable(new BoxPlacement(x,y));
                break;
            case 3:
                collidables.addCollidable(new Detonator(x,y));
                break;
            case 4:
                collidables.addCollidable(new ExplosionRangeUp(x,y));
                break;
            case 5:
                collidables.addCollidable(new Ghost(x,y));
                break;
            case 6:
                collidables.addCollidable(new RollerSkates(x,y));
                break;
            case 7:
                collidables.addCollidable(new Shield(x,y));
                break;
        }
    }
}
