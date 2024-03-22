package s11.bomberguy;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class Collidables {

    private static Collidables instance = null;

    private final ArrayList<Sprite> collidables;

    private Collidables() {
        collidables = new ArrayList<>();
    }

    public static Collidables getInstance() {
        if (instance == null) {
            instance = new Collidables();
        }
        return instance;
    }

    public ArrayList<Sprite> getCollidables() {
        return collidables;
    }

    public <T extends Sprite> void addCollidables(ArrayList<T> collidableObjects) {
        collidables.addAll(collidableObjects);
    }

    public <T extends Sprite> void removeCollidables(ArrayList<T> collidableObjects) {
        collidables.removeAll(collidableObjects);
    }

    public <T extends Sprite> void addCollidable(T collidableObject) {
        collidables.add(collidableObject);
    }

    public <T extends Sprite> void removeCollidable(T collidableObject) {
        collidables.remove(collidableObject);
    }
}
