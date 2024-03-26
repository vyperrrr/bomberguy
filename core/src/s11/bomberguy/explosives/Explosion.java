package s11.bomberguy.explosives;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.sun.tools.javac.util.Pair;
import s11.bomberguy.Collidables;

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
    private static final int EXPLOSION_HEIGHT= 32;
    private static final Texture EXPLOSION_TEXTURE = new Texture("assets/explosion.jpg");

    public Explosion(float x, float y, int direction, int range){
        super.setTexture(EXPLOSION_TEXTURE);
        super.setBounds((x - (x % 32) ),(y - (y % 32)), EXPLOSION_WIDTH, EXPLOSION_HEIGHT);
        this.timer = new Timer();
        this.range = range;
        this.direction = direction;
        this.timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                spread();
            }
        }, 1);
    }

    private void spread(){
        if(this.range > 0){
            Collidables collidables = Collidables.getInstance();
            if(this.direction == 0){
                collidables.addExplosion(new Explosion(this.getX(), this.getY()+this.getHeight(), 1, this.range-1));
                collidables.addExplosion(new Explosion(this.getX(), this.getY()-this.getHeight(), 2, this.range-1));
                collidables.addExplosion(new Explosion(this.getX()-this.getWidth(), this.getY(), 3, this.range-1));
                collidables.addExplosion(new Explosion(this.getX()+this.getWidth(), this.getY(), 4, this.range-1));
            }

            if(direction == 1){
                collidables.addExplosion(new Explosion(this.getX(), this.getY()+this.getHeight(), 1, this.range-1));
            }

            if(direction == 2){
                collidables.addExplosion(new Explosion(this.getX(), this.getY()-this.getHeight(), 2, this.range-1));
            }

            if(direction == 3){
                collidables.addExplosion(new Explosion(this.getX()-this.getWidth(), this.getY(), 3, this.range-1));
            }

            if(direction == 4){
                collidables.addExplosion(new Explosion(this.getX()+this.getWidth(), this.getY(), 4, this.range-1));
            }
        }


    };

    public void render(SpriteBatch batch)
    {
        batch.draw(this.getTexture(),this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
