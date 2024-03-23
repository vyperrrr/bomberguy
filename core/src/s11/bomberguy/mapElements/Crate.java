package s11.bomberguy.mapElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Crate extends Sprite {
    private boolean powerUpInside;
    private static final Texture CRATE_TEXTURE = new Texture("assets/crate.png");


    public Crate(float x, float y, int pixelSize){
        super.setTexture(CRATE_TEXTURE);
        super.setBounds(x, y, pixelSize, pixelSize);
    }

    public void render(SpriteBatch batch){
        batch.draw(this.getTexture(),this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    private void spawnPowerUp(){

    }
}
