package s11.bomberguy.powerups;

import com.badlogic.gdx.graphics.Texture;

public class ExplosionRangeUp extends PowerUp{

    private static final Texture Explosion_Range_Texture = new Texture("assets/powerUps/explosion-range.png");
    public ExplosionRangeUp(float x, float y) {
        super(x, y);
        super.setTexture(Explosion_Range_Texture);
    }
}
