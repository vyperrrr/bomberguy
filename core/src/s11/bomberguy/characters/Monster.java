package s11.bomberguy.characters;

import com.badlogic.gdx.graphics.Texture;

public class Monster extends Character{
    private static final Texture MONSTER_TEXTURE = new Texture("assets/badlogic.jpg");
    public Monster(float x, float y, float width, float height, float moveSpeed) {
        super(MONSTER_TEXTURE, x, y, width, height, moveSpeed);
    }
}
