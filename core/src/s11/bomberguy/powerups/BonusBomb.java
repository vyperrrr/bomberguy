package s11.bomberguy.powerups;

import com.badlogic.gdx.graphics.Texture;

public class BonusBomb extends PowerUp{

    private static final Texture Bonus_Bomb_Texture = new Texture("assets/powerUps/bonus-bomb.png");
    public BonusBomb(float x, float y) {
        super(x, y);
        super.setTexture(Bonus_Bomb_Texture);
    }
}
