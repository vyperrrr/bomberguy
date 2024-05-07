package s11.bomberguy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Timer;
import com.sun.tools.javac.util.Pair;
import s11.bomberguy.explosives.Explosion;
import s11.bomberguy.mapElements.Crate;
import s11.bomberguy.mapElements.Wall;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Collidables {

    private static Collidables instance = null;

    private final ArrayList<Sprite> collidables;

    private Collidables() {
        collidables = new ArrayList<>();
    }

    /**
     * <p> Creates / returns already existing signleton instance </p>
     * @return singleton Collidables instance
     */
    public static Collidables getInstance() {
        if (instance == null) {
            instance = new Collidables();
        }
        return instance;
    }

    public ArrayList<Sprite> getCollidables() {
        return collidables;
    }

    /**
     * <p> Adds the collidableObjects to the collidables ArrayList of the instance. </p>
     * @param collidableObjects Sprite objects to add to the Collidables collidables ArrayList.
     */
    public <T extends Sprite> void addCollidables(ArrayList<T> collidableObjects) {
        collidables.addAll(collidableObjects);
    }

    /**
     * <p> Removes the collidableObjects from the collidables ArrayList of the instance. </p>
     * <p> If any of the elements of the collidableObject isn't in the collidables just ignores it. </p>
     * @param collidableObjects Sprite objects to remove from the Collidables collidables ArrayList.
     */
    public <T extends Sprite> void removeCollidables(ArrayList<T> collidableObjects) {
        collidables.removeAll(collidableObjects);
    }

    /**
     * <p> Adds the collidableObject to the collidables ArrayList of the instance. </p>
     * @param collidableObject Sprite object to add to the Collidables collidables ArrayList.
     */
    public <T extends Sprite> void addCollidable(T collidableObject) {
        collidables.add(collidableObject);
    }

    /**
     * <p> Removes the collidableObject from the collidables ArrayList of the instance. </p>
     * <p> If collidableObject isn't in the collidables just ignores it. </p>
     * @param collidableObject Sprite object to remove from the Collidables collidables ArrayList.
     */
    public <T extends Sprite> void removeCollidable(T collidableObject) {
        collidables.remove(collidableObject);
    }

    /**
     * <p> Returns an ArrayList of the Crate objects present in the instance's collidables. </p>
     * @return ArrayList containing the instance's Crate objects in the collidables.
     */
    public ArrayList<Crate> getCrates(){
        ArrayList<Crate> crates = new ArrayList<>();

        for(Sprite sprite : this.collidables){
            if(sprite instanceof Crate){
                crates.add( (Crate) sprite);
            }
        }

        return crates;
    }

    /**
     * <p> Sets tge insstance's collidables to an empty ArrayList </p>
     */
    public void clearCollidables() {
        this.collidables.clear();
    }
}
