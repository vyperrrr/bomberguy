package s11.bomberguy.powerups;

import com.badlogic.gdx.graphics.Texture;

public class Ghost extends PowerUp{
    private static final Texture GHOST_TEXTURE = new Texture("assets/powerUps/ghost.png");
    public Ghost(float x, float y) {
        super(x, y);
        super.setTexture(GHOST_TEXTURE);
    }
}
