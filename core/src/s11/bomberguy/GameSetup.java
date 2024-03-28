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
        getGameSetup().playerNum = playerNum;
    }

    public static void setMapNum(int mapNum) {
        getGameSetup().mapNum = mapNum;
    }

    public static void setRounds(int rounds) {
        getGameSetup().rounds = rounds;
    }

    public static int getPlayerNum() {
        return getGameSetup().playerNum;
    }

    public static int getMapNum() {
        return getGameSetup().mapNum;
    }

    public static int getRounds() {
        return getGameSetup().rounds;
    }
}
