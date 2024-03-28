package s11.bomberguy;

public class GameSetup {
    private static GameSetup gameSetup;

    private int playerNum = 2;
    private int mapNum = 1;
    private int rounds = 1;

    private GameSetup() {}
    public static GameSetup getGameSetup() {
        if (gameSetup == null) {
            gameSetup = new GameSetup();
        }
        return gameSetup;
    }

    public static void setPlayerNum(int playerNum) {
        if (gameSetup == null) {
            gameSetup = new GameSetup();
        }
        gameSetup.playerNum = playerNum;
    }

    public static void setMapNum(int mapNum) {
        if (gameSetup == null) {
            gameSetup = new GameSetup();
        }
        gameSetup.mapNum = mapNum;
    }

    public static void setRounds(int rounds) {
        if (gameSetup == null) {
            gameSetup = new GameSetup();
        }
        gameSetup.rounds = rounds;
    }

    /* Intended way:
    public static int getPlayerNum() {
        return gameSetup.playerNum;
    }
    */
    public int getPlayerNum() {
        return playerNum;
    }

    public int getMapNum() {
        return mapNum;
    }

    public int getRounds() {
        return rounds;
    }
}
