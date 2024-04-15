package s11.bomberguy.powerups;

import com.badlogic.gdx.graphics.Texture;

public class RollerSkates extends PowerUp{
    private static final Texture Roller_Skate_Texture = new Texture("assets/powerUps/roller-skate.png");


    public RollerSkates(float x, float y) {
        super(x, y);
        super.setTexture(Roller_Skate_Texture);
    }
}
