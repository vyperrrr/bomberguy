package s11.bomberguy.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import s11.bomberguy.Collidables;
import s11.bomberguy.explosives.Explosion;

import java.util.Random;

public class Monster extends Character{
    private static final Texture MONSTER_TEXTURE = new Texture("assets/monster.png");

    /*
    direction in which the monster is headed
    1-up
    2-down
    3-left
    4-right
     */
    private int direction = 1;
    private final Random random = new Random();
    public Monster(float x, float y, float width, float height, float moveSpeed) {
        super(MONSTER_TEXTURE, x, y, width, height, moveSpeed);
    }
    /**
     * <p> Makes the Monster move, it turns if it
     * would hit something and there is a small chance for random turns. </p>
     * <p>Also checks collision with bomb, in this case the Monster dies.</p>
     */
    public void move(){
        if (this.isAlive){
            float newX = getX();
            float newY = getY();

            // Get Collidables instance
            Collidables collidables = Collidables.getInstance();

            // Calculate the new position based on direction
            if (this.direction == 3) {
                newX -= moveSpeed * Gdx.graphics.getDeltaTime();
            }
            if (this.direction == 4) {
                newX += moveSpeed * Gdx.graphics.getDeltaTime();
            }
            if (this.direction == 1) {
                newY += moveSpeed * Gdx.graphics.getDeltaTime();
            }
            if (this.direction == 2) {
                newY -= moveSpeed * Gdx.graphics.getDeltaTime();
            }

            // Check if the new position will cause collision with any collidable
            boolean willCollide = false;
            Sprite collidedWith = null;
            for (Sprite collidable : collidables.getCollidables()) {
                if (this != collidable && monsterCollidesWith(collidable, newX, newY)) {
                    willCollide = true;
                    collidedWith = collidable;
                    break;
                }
            }

            if(willCollide && collidedWith instanceof Explosion){
                this.isAlive=false;
            }else if(!willCollide){
                setPosition(newX, newY);
                if((random.nextInt(1000)+1) >= 990){
                    this.newDirection();
                }

            }else{
                this.newDirection();
            }
        }
    }

    /**
     * <p> Checks if there is a collision of the provided collidable with the provided coordinates </p>
     * @param collidable the Sprite to check potential collision with
     * @param newX the X coordinate to check collision with
     * @param newY the Y coordinate to check collision with
     * @return whether collidable collides with the coordinates
     */
    private <T extends Sprite> boolean monsterCollidesWith(T collidable, float newX, float newY) {
        float oldX = getX();
        float oldY = getY();

        setX(newX);
        setY(newY);

        boolean result = getBoundingRectangle().overlaps(collidable.getBoundingRectangle());

        setX(oldX);
        setY(oldY);

        //If the monster collides with a player, the player dies

        if(result && collidable instanceof Player){
            if(!((Player) collidable).getShielded()){
                ((Player) collidable).isAlive = false;
                return false;
            }
        }

        return result;
    }

    /**
     * <p> Turns the Monster to a random direction. </p>
     */
    private void newDirection(){
        int tempDir = random.nextInt(4)+1;
        while(this.direction == tempDir){
            this.direction = random.nextInt(4)+1;
        }
    }

}
