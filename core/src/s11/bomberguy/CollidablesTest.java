package s11.bomberguy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CollidablesTest {

    @Test
    void getInstance() {
        assertAll(
                () -> assertEquals(Collidables.getInstance(), Collidables.getInstance(), "Not the same instance!"),
                () -> assertNotNull(Collidables.getInstance(), "Instance is Null!")
        );
    }

    @Test
    void getCollidables() {
        assertNotNull(Collidables.getInstance().getCollidables(), "Collidables array is Null!");
    }

    @Test
    void addCollidables() {
         ArrayList<Sprite> collidables = Collidables.getInstance().getCollidables();
         Sprite sprite = new Sprite();
         collidables.add(sprite);
         ArrayList<Sprite> tmp = new ArrayList<>();
         tmp.add(sprite);
         Collidables.getInstance().addCollidables(tmp);
         assertIterableEquals(collidables, Collidables.getInstance().getCollidables(), "No element added to array!");
    }

    @Test
    void removeCollidables() {
        Sprite sprite = new Sprite();
        Collidables.getInstance().addCollidable(sprite);
        ArrayList<Sprite> sprites = new ArrayList<>();
        sprites.add(sprite);
        Collidables.getInstance().removeCollidables(sprites);
        assertFalse(Collidables.getInstance().getCollidables().contains(sprite));
    }

    @Test
    void addCollidable() {
        Sprite sprite = new Sprite();
        Collidables.getInstance().addCollidable(sprite);
        assertTrue(Collidables.getInstance().getCollidables().contains(sprite));
    }

    @Test
    void removeCollidable() {
        Sprite sprite = new Sprite();
        Collidables.getInstance().addCollidable(sprite);
        Collidables.getInstance().removeCollidable(sprite);
        assertFalse(Collidables.getInstance().getCollidables().contains(sprite));
    }

    @Test
    void clearCollidables() {
        Collidables.getInstance().addCollidable(new Sprite());
        Collidables.getInstance().clearCollidables();
        assertEquals(0, Collidables.getInstance().getCollidables().size());
    }
}