package s11.bomberguy.powerups;

import com.badlogic.gdx.graphics.Texture;

public class BoxPlacement extends PowerUp{
    private static final Texture BOX_PLACEMENT_TEXTURE = new Texture("assets/powerUps/box-placement.png");
    public BoxPlacement(float x, float y) {
        super(x, y);
        super.setTexture(BOX_PLACEMENT_TEXTURE);
    }
}
