package s11.bomberguy.explosives;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import s11.bomberguy.Collidables;
import s11.bomberguy.mapElements.Crate;
import s11.bomberguy.mapElements.Wall;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Bomb extends Sprite {
    private int range;
    private Timer timer;
    private static final int BOMB_WIDTH = 12;
    private static final int BOMB_HEIGHT = 12;
    private static final Texture BOMB_TEXTURE = new Texture("assets/bomb.png");

    private Boolean exploded = false;

    public Bomb(float x, float y, int range) {
        super.setTexture(BOMB_TEXTURE);
        super.setBounds(x,y, BOMB_WIDTH,BOMB_HEIGHT);

        this.range = range;
        timer = new Timer();

    }

    /**
     * <p> Draws the bomb to the screen. </p>
     */
    public void render(SpriteBatch batch)
    {
        batch.draw(this.getTexture(),this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    /**
     * <p> Creates explosion from bomb. </p>
     */
    public void explode()
    {
        if(!this.exploded){
            Explosion explosion = new Explosion(this.getX(), this.getY(), 0, this.range);

            Collidables collidables = Collidables.getInstance();

            collidables.addCollidable(explosion);

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    collidables.removeCollidable(explosion);
                }
            }, 2);
            this.exploded = true;
        }
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public Boolean getExploded() {
        return exploded;
    }
}
