package s11.bomberguy.mapElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Wall extends Sprite {
    private static final Texture WALL_TEXTURE = new Texture("assets/wall.png");

    public Wall(float x, float y, int pixelSize){
        super.setTexture(WALL_TEXTURE);
        super.setBounds(x, y, pixelSize, pixelSize);
    }

    public void render(SpriteBatch batch){
        batch.draw(this.getTexture(),this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
