package s11.bomberguy.explosives;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import s11.bomberguy.Collidables;
import s11.bomberguy.TimerManager;
import s11.bomberguy.mapElements.Wall;
import s11.bomberguy.mapElements.Crate;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Explosion extends Sprite {


    private final int direction;
    /*
    direction in which the explosion will spread
    0-every direction
    1-up
    2-down
    3-left
    4-right
     */
    private final int range;
    private static final int EXPLOSION_WIDTH = 32;
    private static final int EXPLOSION_HEIGHT = 32;
    private static final Texture EXPLOSION_TEXTURE = new Texture("assets/explosion.jpg");

    public Explosion(float x, float y, int direction, int range) {
        super.setTexture(EXPLOSION_TEXTURE);
        super.setBounds((x - (x % 32)), (y - (y % 32)), EXPLOSION_WIDTH, EXPLOSION_HEIGHT);
        this.range = range;
        this.direction = direction;
        Timer.Task spread = new Timer.Task() {
            @Override
            public void run() {
                spread();
            }
        };

        Timer.Task stop = new Timer.Task()
        {
            @Override
            public void run() {
                stop();
            }
        };

        TimerManager.scheduleTask(spread, 0.2f);
        TimerManager.scheduleTask(stop, 1);
    }

    /**
     * <p>  Creates new explosions in the available directions. </p>
     */
    private void spread() {
        if (this.range > 0) {
            Collidables collidables = Collidables.getInstance();

            ArrayList<float[]> directions = new ArrayList<>();
            directions.add(new float[]{this.getX(), this.getY() + this.getHeight()});
            directions.add(new float[]{this.getX(), this.getY() - this.getHeight()});
            directions.add(new float[]{this.getX() - this.getWidth(), this.getY()});
            directions.add(new float[]{this.getX() + this.getWidth(), this.getY()});

            ArrayList<Explosion> explosionsToAdd = new ArrayList<>();

            // Spread in all directions
            if (direction == 0) {
                directions.forEach(direction -> {
                    boolean[] flags = filter(direction[0],direction[1]);
                    // If the next explosion is going to hit a crate don't spread after wards
                    int nextRange = flags[1] ? 0 : this.range-1;
                    if(!flags[0])
                        explosionsToAdd.add(new Explosion(direction[0], direction[1], directions.indexOf(direction) + 1, nextRange));
                });
            } else
            // Spread in one direction
            {
                float directionX = directions.get(this.direction - 1)[0];
                float directionY = directions.get(this.direction - 1)[1];
                boolean[] flags = filter(directionX,directionY);

                if(!flags[0])
                    explosionsToAdd.add(new Explosion(directions.get(this.direction - 1)[0], directions.get(this.direction - 1)[1], direction, this.range - 1));

            }

            collidables.addCollidables(explosionsToAdd);
        }
    }

    // WALL_HIT_FLAG - next explosion will hit a wall if true
    // CRATE_HIT_FLAG - next explosion will hit a crate if true
    /**
     * <p> Checks if there is a collision with any crate / wall on the provided coordinates </p>
     * @return index 0: Collision with wall on the coordinates, index 1: Collision with crate on the coordinates.
     */
    private boolean[] filter(float directionX, float directionY)
    {
        AtomicBoolean WALL_HIT_FLAG = new AtomicBoolean(false);
        AtomicBoolean CRATE_HIT_FLAG = new AtomicBoolean(false);

        Collidables.getInstance().getCollidables().forEach(collidable -> {
            if(collidable.getX() == directionX && collidable.getY() == directionY && collidable instanceof Wall)
                WALL_HIT_FLAG.set(true);
            if(collidable.getX() == directionX && collidable.getY() == directionY && collidable instanceof Crate)
                CRATE_HIT_FLAG.set(true);
        });

        return new boolean[]{WALL_HIT_FLAG.get(), CRATE_HIT_FLAG.get()};
    }

    /**
     * <p> Removes the Explosion from collidables </p>
     */
    private void stop() {
        Collidables collidables = Collidables.getInstance();
        collidables.removeCollidable(this);
    }

    /**
     * <p> Renders the explosion to the screen </p>
     * @param batch the SpriteBatch used for rendering
     */
    public void render(SpriteBatch batch) {
        batch.draw(this.getTexture(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
