package s11.bomberguy.explosives;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import s11.bomberguy.Collidables;
import s11.bomberguy.mapElements.Wall;
import s11.bomberguy.mapElements.Crate;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Explosion extends Sprite {


    private int direction;
    /*
    direction in which the explosion will spread
    0-every direction
    1-up
    2-down
    3-left
    4-right
     */
    private int range;
    private Timer timer;
    private static final int EXPLOSION_WIDTH = 32;
    private static final int EXPLOSION_HEIGHT = 32;
    private static final Texture EXPLOSION_TEXTURE = new Texture("assets/explosion.jpg");

    public Explosion(float x, float y, int direction, int range) {
        super.setTexture(EXPLOSION_TEXTURE);
        super.setBounds((x - (x % 32)), (y - (y % 32)), EXPLOSION_WIDTH, EXPLOSION_HEIGHT);
        this.timer = new Timer();
        this.range = range;
        this.direction = direction;
        this.timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                spread();
            }
        }, 1);
        this.timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                stop();
            }
        }, 2);
    }

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
                    explosionsToAdd.add(new Explosion(directions.get(this.direction - 1)[0], directions.get(this.direction - 1)[1], 1, this.range - 1));

            }

            collidables.addCollidables(explosionsToAdd);
        }
    }

    // WALL_HIT_FLAG - next explosion will hit a wall if true
    // CRATE_HIT_FLAG - next explosion will hit a crate if true
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

    private void stop() {
        Collidables collidables = Collidables.getInstance();
        collidables.removeCollidable(this);
    }

    public void render(SpriteBatch batch) {
        batch.draw(this.getTexture(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
