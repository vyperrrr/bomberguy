package s11.bomberguy.powerups;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

public class PowerUp extends Sprite {
    private Timer timer = new Timer();
    private static final int POWERUP_SIZE = 12;

    public PowerUp(float x, float y){
        super.setBounds(x, y, POWERUP_SIZE,POWERUP_SIZE);
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(this.getTexture(),this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
