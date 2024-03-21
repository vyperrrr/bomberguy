package s11.bomberguy.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import s11.bomberguy.PlayerControl;
import s11.bomberguy.explosives.Bomb;
import s11.bomberguy.powerups.PowerUp;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
public class Player extends Character {
    PlayerControl controls;
    private ArrayList<PowerUp> activePowerUps;
    private int BOMB_COUNT;
    private ArrayList<Bomb> activeBombs;

    private static final Texture PLAYER_TEXTURE = new Texture("assets/badlogic.jpg");

    public Player(float x, float y, float width, float height, float moveSpeed, PlayerControl controls) {
        super(PLAYER_TEXTURE, x, y, width, height, moveSpeed);
        this.activePowerUps = new ArrayList<>();
        this.BOMB_COUNT = 1; //Temporarily
        this.activeBombs = new ArrayList<>();
        this.controls = controls;
    }
    
    public void move() {
        if (Gdx.input.isKeyPressed(controls.getLeftButton())) {
            this.setX(this.getX() - moveSpeed * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(controls.getRightButton())) {
            this.setX(this.getX() + moveSpeed * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(controls.getUpButton())) {
            this.setY(this.getY() + moveSpeed * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(controls.getDownButton())) {
            this.setY(this.getY() - moveSpeed * Gdx.graphics.getDeltaTime());
        }
    }

    public void placeBomb()
    {
        if(Gdx.input.isKeyPressed(controls.getBombButton()) && BOMB_COUNT > 0)
        {
            BOMB_COUNT--;

            // Create new bomb that receives player coordinates
            Bomb bomb = new Bomb(this.getX(), this.getY());

            // Explode and remove bomb from active bombs after 4 seconds...
            bomb.getTimer().scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    bomb.explode();
                    activeBombs.remove(bomb);
                    BOMB_COUNT++;
                }
            }, 4);

            // Add to active bombs
            activeBombs.add(bomb);
        }
    }

    public ArrayList<PowerUp> getActivePowerUps() {
        return activePowerUps;
    }

    public void setActivePowerUps(ArrayList<PowerUp> activePowerUps) {
        this.activePowerUps = activePowerUps;
    }

    public int getBombCount() {
        return BOMB_COUNT;
    }

    public void setBombCount(int BOMB_COUNT) {
        this.BOMB_COUNT = BOMB_COUNT;
    }

    public ArrayList<Bomb> getActiveBombs() {
        return activeBombs;
    }

    public void setActiveBombs(ArrayList<Bomb> activeBombs) {
        this.activeBombs = activeBombs;
    }
}
