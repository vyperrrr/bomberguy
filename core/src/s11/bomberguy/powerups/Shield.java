package s11.bomberguy.powerups;

import com.badlogic.gdx.graphics.Texture;

public class Shield extends PowerUp{
    private static final Texture SHIELD_Texture = new Texture("assets/powerUps/shield.png");
    public Shield(float x, float y) {
        super(x, y);
        super.setTexture(SHIELD_Texture);
    }
}
