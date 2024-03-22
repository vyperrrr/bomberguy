package s11.bomberguy.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Character extends Sprite {
    protected boolean isAlive;
    protected float moveSpeed;

    public Character(Texture texture, float x, float y, float width, float height, float moveSpeed) {
        super.setTexture(texture);
        super.setBounds(x,y,width,height);
        this.isAlive = true;
        this.moveSpeed = moveSpeed;
    }

    // Used to draw player to screen
    public void render(SpriteBatch batch)
    {
        batch.draw(this.getTexture(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    // Check if collides
    public <T extends Sprite> boolean collidesWith(T otherSprite) {
        return this.getBoundingRectangle().overlaps(otherSprite.getBoundingRectangle());
    }
}
