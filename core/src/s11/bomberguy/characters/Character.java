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

    /**
     * <p> Renders the character to the screen. </p>
     * @param batch the game's SpriteBatch, used to draw to the screen.
     */
    public void render(SpriteBatch batch)
    {
        batch.draw(this.getTexture(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    /**
     * <p> Checks if there is a collision of the Character with the provided otherSprite </p>
     * @param otherSprite the Sprite to check potential collision with
     * @return whether the Character collides with the given otherSprite
     */
    public <T extends Sprite> boolean collidesWith(T otherSprite) {
        return this.getBoundingRectangle().overlaps(otherSprite.getBoundingRectangle());
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
}
