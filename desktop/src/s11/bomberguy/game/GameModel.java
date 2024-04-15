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
    private boolean isOver = false;

    // Data passed by GUI
    public GameModel() {
    }

    @Override
    public void create() {
        // Initialize players
        players = new ArrayList<>();
        monsters = new ArrayList<>();

        // For testing reasons
        Random random = new Random();

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
                        players.add(new Player(tileX, tileY, 24, 24, 100, Controls.getControls().get(currentPlayerNum)));
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

    public Player determineRoundWinner()
    {
        Optional<Player> roundWinner = players.stream().filter(Character::isAlive).collect(Collectors.collectingAndThen(Collectors.toList(), list -> list.size() > 1 ? Optional.empty() : list.stream().findFirst()));

        Player winner = roundWinner.orElse(null);
        if(winner != null)
            winner.setRoundsWon(winner.getRoundsWon()+1);

        return winner;
    }

    public Player determineGameWinner()
    {
        Comparator<Player> roundsWonComparator = Comparator
                .comparingInt(Player::getRoundsWon)
                .reversed(); // To get the maximum rounds won first

        Optional<Player> maxPlayer = players.stream()
                .sorted(roundsWonComparator)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> {
                            int maxWins = list.get(0).getRoundsWon(); // Get the maximum number of wins
                            long count = list.stream().filter(player -> player.getRoundsWon() == maxWins).count();
                            return count > 1 ? Optional.empty() : list.stream().findFirst();
                        }
                ));

        return maxPlayer.orElse(null);
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
}
