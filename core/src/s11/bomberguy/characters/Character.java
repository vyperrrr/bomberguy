package s11.bomberguy.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Character extends Sprite {
    protected Boolean isAlive;
    protected double moveSpeed;
    protected int direction;

    protected void move() {
    }

}
