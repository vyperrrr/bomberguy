package s11.bomberguy.characters;

import org.junit.jupiter.api.Test;
import s11.bomberguy.GameSetup;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameSetupTest {
    @Test
    public void testSetAndGetPlayerNum() {
        int initialPlayerNum = GameSetup.getPlayerNum();
        GameSetup.setPlayerNum(3);
        assertAll(
                () -> assertEquals(2, initialPlayerNum, "Player number should be 2"),
                () -> assertEquals(3, GameSetup.getPlayerNum(), "Player number should be 3")
        );
    }

    @Test
    public void testSetAndGetMapNum() {
        int initialMapNum = GameSetup.getMapNum();
        GameSetup.setMapNum(2);
        assertAll(
                () -> assertEquals(1, initialMapNum, "Map number should be 1"),
                () -> assertEquals(2, GameSetup.getMapNum(), "Map number should be 2")
        );
    }

    @Test
    public void testSetAndGetRounds() {
        int initialRoundNum = GameSetup.getRounds();
        GameSetup.setRounds(2);
        assertAll(
                () -> assertEquals(1, initialRoundNum, "Round number should be 1"),
                () -> assertEquals(2, GameSetup.getRounds(), "Round number should be 2")
        );
    }
}