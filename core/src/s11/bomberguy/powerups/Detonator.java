package s11.bomberguy.powerups;

import com.badlogic.gdx.graphics.Texture;

public class Detonator extends PowerUp{
    private static final Texture DETONATOR_TEXTURE = new Texture("assets/powerUps/detonator.png");
    public Detonator(float x, float y) {
        super(x, y);
        super.setTexture(DETONATOR_TEXTURE);
    }
}
