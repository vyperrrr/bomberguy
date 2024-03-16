package s11.bomberguy.characters;

import s11.bomberguy.explosives.Bomb;
import s11.bomberguy.powerups.PowerUp;

import java.util.ArrayList;

public class Player extends Character {
    private int bombCount;
    private ArrayList<PowerUp> activePowerUps;
    private ArrayList<Bomb> activeBombs;
    @Override
    protected void move() {
        super.move();
    }
}
