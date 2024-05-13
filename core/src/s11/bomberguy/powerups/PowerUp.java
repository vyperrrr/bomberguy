package s11.bomberguy.powerups;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PowerUp extends Sprite {
    private static final int POWERUP_SIZE = 12;

    public PowerUp(float x, float y){
        super.setBounds(x, y, POWERUP_SIZE,POWERUP_SIZE);
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(this.getTexture(),this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
