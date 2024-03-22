package s11.bomberguy.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Timer;
import s11.bomberguy.Collidables;
import s11.bomberguy.PlayerControl;
import s11.bomberguy.explosives.Bomb;
import s11.bomberguy.powerups.PowerUp;
import com.badlogic.gdx.Gdx;
import s11.bomberguy.Collidables;

import java.util.ArrayList;
public class Player extends Character {
    PlayerControl controls;
    private ArrayList<PowerUp> activePowerUps;
    private int BOMB_COUNT;
    private ArrayList<Bomb> activeBombs;

    private boolean BOMB_COLLISION_FLAG;

    private static final Texture PLAYER_TEXTURE = new Texture("assets/badlogic.jpg");

    public Player(float x, float y, float width, float height, float moveSpeed, PlayerControl controls) {
        super(PLAYER_TEXTURE, x, y, width, height, moveSpeed);
        this.activePowerUps = new ArrayList<>();
        this.BOMB_COUNT = 1; //Temporarily
        this.BOMB_COLLISION_FLAG = false;
        this.activeBombs = new ArrayList<>();
        this.controls = controls;
    }

    // Sprites are collidable
    public void move() {
        float newX = getX();
        float newY = getY();

        // Get Collidables instance
        Collidables collidables = Collidables.getInstance();

        // Calculate the new position based on input
        if (Gdx.input.isKeyPressed(controls.getLeftButton())) {
            newX -= moveSpeed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(controls.getRightButton())) {
            newX += moveSpeed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(controls.getUpButton())) {
            newY += moveSpeed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(controls.getDownButton())) {
            newY -= moveSpeed * Gdx.graphics.getDeltaTime();
        }

        // Check if the new position will cause collision with any collidable
        boolean willCollide = false;
        Sprite collidedWith = null;
        for (Sprite collidable : collidables.getCollidables()) {
            if (this != collidable && collidesWith(collidable, newX, newY)) {
                willCollide = true;
                collidedWith = collidable;
                break;
            }
        }

        // Update the position only if there won't be a collision
        if(!willCollide)
            setPosition(newX, newY);

        if(willCollide && collidedWith instanceof Bomb && BOMB_COLLISION_FLAG)
            setPosition(newX, newY);
        else
            BOMB_COLLISION_FLAG = false;

    }

    // Method to check if the player will collide with a collidable at the specified position
    private <T extends Sprite> boolean collidesWith(T collidable, float newX, float newY) {
        float oldX = getX();
        float oldY = getY();

        setX(newX);
        setY(newY);

        boolean result = getBoundingRectangle().overlaps(collidable.getBoundingRectangle());

        setX(oldX);
        setY(oldY);

        return result;
    }

    public void placeBomb()
    {
        if(Gdx.input.isKeyPressed(controls.getBombButton()) && BOMB_COUNT > 0)
        {
            BOMB_COUNT--;

            // Create new bomb that receives player coordinates
            Bomb bomb = new Bomb(this.getX(), this.getY());

            // Get Collidables instance
            Collidables collidables = Collidables.getInstance();

            // Explode and remove bomb from active bombs after 4 seconds...
            bomb.getTimer().scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    bomb.explode();
                    activeBombs.remove(bomb);
                    collidables.removeCollidable(bomb);
                    BOMB_COLLISION_FLAG = false;
                    BOMB_COUNT++;
                }
            }, 4);

            // Add to active bombs
            BOMB_COLLISION_FLAG = true;
            activeBombs.add(bomb);
            collidables.addCollidable(bomb);
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
