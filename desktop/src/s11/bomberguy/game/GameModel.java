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
import s11.bomberguy.mapElements.Crate;
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
    private int roundNum;
    private int currentRound;
    private HashMap<String, Integer> playerToWinCount;
    private HashMap<Integer, String> roundToWinner;
    private boolean isOver = false;

    /**
     * <p> Initializes the GameModel:
     * <ul>
     *     <li>Gets playerNum, roundNum, mapNum from GameSetup</li>
     *     <li>Generates players and monsters</li>
     *     <li>Loads the walls and crates</li>
     *     <li>Sets up the screen for rendering</li>
     * </ul>
     * </p>
     */
    @Override
    public void create() {
        // Initialize players
        players = new ArrayList<>();
        monsters = new ArrayList<>();
        playerToWinCount = new HashMap<>();
        roundToWinner = new HashMap<>();

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

        //this.generatePowerUps();
        this.everyCrateHasPowerUp();
        initScoreboard();
    }

    private void generatePowerUps() {
        ArrayList<Crate> crates = collidables.getCrates();
        Collections.shuffle(crates);
        int powerUpNum = 0;
        double percentage = 0.5;

        while(powerUpNum < crates.size() * percentage){
            crates.get(powerUpNum).setPowerUpInside(true);
            powerUpNum++;
        }
    }

    /**
     * <p> Generates powerUps inside every crate (Intended for testing purposes) </p>
     */
    private void everyCrateHasPowerUp(){
        this.collidables.getCrates().forEach( crate -> crate.setPowerUpInside(true));
    }

    /**
     * <p> Adds the walls and crates from the map to the collidables instance </p>
     */
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
                            Sprite sprite = TileSpriteFactory.createCrateOrWall(tiledLayer, col, row);
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

    /**
     * <p> Generates and spawns the (correct number of) players to the right spawn locations.  </p>
     */
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
                        players.add(new Player("Játékos "+(currentPlayerNum+1),tileX, tileY, 20, 20, 100, Controls.getControls().get(currentPlayerNum)));
                        currentPlayerNum++; // Increment the number of players added
                    }
                }
            }
        }
    }

    /**
     * <p> Generates and spawns monsters to the spawn points predefined on each map </p>
     */
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

    /**
     * <p> Resets the Game state, including the player, the monsters, the collidables and the map itself </p>
     */
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

        //Reset powerups
        //this.generatePowerUps();
        this.everyCrateHasPowerUp();
    }

    /**
     * <p> Initializes the scoreboard to 0 points for each player </p>
     */
    public void initScoreboard()
    {
        playerToWinCount.put("Játékos 1", 0);
        playerToWinCount.put("Játékos 2", 0);
        playerToWinCount.put("Játékos 3", 0);
    }

    /**
     * <p> Returns with the winner of the last round </p>
     * @return the name of the winner of the last round, or "Döntetlen" if it is a draw
     */
    public String determineRoundWinner()
    {
        Optional<Player> roundWinner = players.stream().filter(Character::isAlive).collect(Collectors.collectingAndThen(Collectors.toList(), list -> list.size() > 1 ? Optional.empty() : list.stream().findFirst()));

        if(roundWinner.isPresent())
            return roundWinner.get().getName();
        return "Döntetlen";
    }

    /**
     * <p> Returns with the winner of the game </p>
     * @return the name of the winner of the game, or "Döntetlen" if it is a draw
     */
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

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public Collidables getCollidables() {
        return collidables;
    }

    public void setOver() { isOver = true; }

    public boolean isOver() {
        return isOver;
    }

    public int getRoundNum() {
        return roundNum;
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

    public HashMap<String, Integer> getPlayerToWinCount() {
        return playerToWinCount;
    }

    /**
     * <p> Adds to the collidables a powerUp of random king at the specified coordinates </p>
     * @param x the x coordinate of the new powerUp
     * @param y the y coordinate of the new powerUp

     */
    public void putDownRandomPowerUp(float x, float y) {
        Random random = new Random();

        switch (random.nextInt(8)){
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
