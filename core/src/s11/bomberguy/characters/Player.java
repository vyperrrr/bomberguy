package s11.bomberguy.characters;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import s11.bomberguy.PlayerControl;
import s11.bomberguy.explosives.Bomb;
import s11.bomberguy.powerups.PowerUp;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;


public class Player extends Character {

    PlayerControl controls;

    private int bombCount;
    private ArrayList<PowerUp> activePowerUps;
    private ArrayList<Bomb> activeBombs;

    public Player(Texture texture, float x, float y, float width, float height, float moveSpeed, PlayerControl controls) {
        super(texture, x, y, width, height, moveSpeed);
        this.controls = controls;
        System.out.println(controls.toString());
    }

    public void move() {
        if (Gdx.input.isKeyPressed(controls.getLeftButton())) {
            x -= moveSpeed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(controls.getRightButton())) {
            x += moveSpeed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(controls.getUpButton())) {
            y += moveSpeed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(controls.getUpButton())) {
            y -= moveSpeed * Gdx.graphics.getDeltaTime();
        }
    }
}
