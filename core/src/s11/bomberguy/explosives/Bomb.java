package s11.bomberguy.explosives;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import s11.bomberguy.Collidables;

import java.sql.Time;

public class Bomb extends Sprite {
    private int range;
    private Timer timer;
    private static final int BOMB_WIDTH = 12;
    private static final int BOMB_HEIGHT = 12;
    private static final Texture BOMB_TEXTURE = new Texture("assets/bomb.png");

    public Bomb(float x, float y) {
        super.setTexture(BOMB_TEXTURE);
        super.setBounds(x,y, BOMB_WIDTH,BOMB_HEIGHT);

        this.range = 2;
        timer = new Timer();

    }

    public void render(SpriteBatch batch)
    {
        batch.draw(this.getTexture(),this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public void explode()
    {
        Explosion explosion = new Explosion(this.getX(), this.getY(), 0, this.range);

        Collidables collidables = Collidables.getInstance();
        collidables.addExplosion(explosion);

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


}
