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

    public <T extends Sprite> void addExplosion(Explosion e) {
        AtomicBoolean canPutDown = new AtomicBoolean(true);
        ArrayList<Sprite> needToRemove = new ArrayList<>();

        this.collidables.forEach( sprite -> {
            if(sprite.getBoundingRectangle().overlaps(e.getBoundingRectangle())) {
                if ((sprite instanceof Wall)) {
                    canPutDown.set(false);
                } else {
                    needToRemove.add(sprite);

                    if(sprite instanceof Crate){

                    }

                }
            }
        });

        if (canPutDown.get()){
            collidables.add(e);
            collidables.removeAll(needToRemove);
        }

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                collidables.remove(e);
            }
        }, 1);

    }


    public <T extends Sprite> void removeCollidable(T collidableObject) {
        collidables.remove(collidableObject);
    }

}
