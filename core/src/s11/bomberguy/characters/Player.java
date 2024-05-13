package s11.bomberguy.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Timer;
import s11.bomberguy.Collidables;
import s11.bomberguy.PlayerControl;
import s11.bomberguy.TimerManager;
import s11.bomberguy.explosives.Bomb;
import s11.bomberguy.explosives.Explosion;
import s11.bomberguy.mapElements.Crate;
import s11.bomberguy.mapElements.Wall;
import s11.bomberguy.powerups.*;

import java.util.ArrayList;
import java.util.function.Function;


public class Player extends Character {
    private final String name;
    PlayerControl controls;
    private int BOMB_COUNT;
    private int MAX_BOMB_COUNT;
    private int EXPLOSION_RANGE = 2;
    private final ArrayList<Bomb> activeBombs;
    private boolean BOMB_COLLISION_FLAG;
    private final ArrayList<String> activePowerUps;
    private Boolean isShielded = false;
    private Boolean isGhosted = false;
    private Boolean hasDetonator = false;
    private int placeableBoxes = 0;
    private int placedBoxes = 0;
    private Crate recentCrate = null;

    private static final Texture PLAYER_TEXTURE = new Texture("assets/players/player.png");
    private Texture SHIELD_TEXTURE = new Texture("assets/powerups/circle.png");
    private Texture GHOST_TEXTURE = new Texture("assets/powerups/red-circle.png");
    private final BitmapFont font = new BitmapFont();
    private final GlyphLayout layout = new GlyphLayout();

    public Player(String name, float x, float y, float width, float height, float moveSpeed, PlayerControl controls) {
        super(PLAYER_TEXTURE, x, y, width, height, moveSpeed);
        this.name = name;
        this.activePowerUps = new ArrayList<>();
        this.BOMB_COUNT = 1; //Temporarily
        this.MAX_BOMB_COUNT = 1;
        this.BOMB_COLLISION_FLAG = false;
        this.activeBombs = new ArrayList<>();
        this.controls = controls;
        this.setPlayerTexture(this.name);
        font.getData().setScale(0.55f); // Set font scale to 2 (doubles the size)
        font.setColor(Color.BLACK);
    }

    /**
     * <p> Sets the given player's texture to a predefined value. </p>
     * @param name used to identify the player
     */
    private void setPlayerTexture(String name){
        switch (name){
            case "Játékos 2":
                this.setTexture(new Texture("assets/players/player2.png"));
                break;
            case "Játékos 3":
                this.setTexture(new Texture("assets/players/player3.png"));
                break;
        }
    }
    /**
     * <p> Draws the player texture (or ghost / shield) and name to the map. </p>
     */
    public void render(SpriteBatch batch)
    {
        batch.draw(this.getTexture(), this.getX(), this.getY(), this.getWidth(), this.getHeight());

        layout.setText(font, this.name);

        this.font.draw(batch, this.name, getX()+this.getWidth()/2-layout.width/2, getY() + getHeight() + 10);

        if(this.isShielded){
            batch.draw(SHIELD_TEXTURE, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }

        if(this.isGhosted){
            batch.draw(GHOST_TEXTURE, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }

    }

    /**
     * <p> Moves the player on the map when the movement keys are being pressed down. Also handles:
     *  <ul>
     *    <li>Box placement Power Up</li>
     *    <li>Ghost Power Up</li>
     *    <li>Explosion damage</li>
     *    <li>Power Up collection</li>
     *  </ul>
     * </p>
     * @param map the map the player is located on
     */
    public void move(TiledMap map) {
        float newX = getX();
        float newY = getY();

        // Get Collidables instance
        Collidables collidables = Collidables.getInstance();

        // Calculate the new position based on input
        //only if the player is alive
        if(this.isAlive){
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
            if (Gdx.input.isKeyJustPressed(controls.getExtraButton()) && placeableBoxes - placedBoxes > 0 && recentCrate==null) {
                ++placedBoxes;

                MapGroupLayer groupLayer = (MapGroupLayer) map.getLayers().get("collidables");

                MapLayer layer = groupLayer.getLayers().get("Boxes");
                if(layer instanceof TiledMapTileLayer){
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    cell.setTile(new StaticTiledMapTile(new TextureRegion(new Texture("assets/crate.png"))));
                    ((TiledMapTileLayer) layer).setCell( (int)(getX()/32), (int)(getY()/32), cell);


                }

                recentCrate = new Crate(this, getX(), getY(), (int)(getX()/32), (int)(getY()/32));
                collidables.addCollidable(recentCrate);
            }
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
        if(!willCollide || isGhosted || (recentCrate != null && collidedWith == recentCrate)) {
            setPosition(newX, newY);
        }

        if (!willCollide) {
            recentCrate = null;
        }

        if(willCollide && collidedWith instanceof Bomb && BOMB_COLLISION_FLAG)
            setPosition(newX, newY);
        else
            BOMB_COLLISION_FLAG = false;

        //if collides with a monster, it's es hora de dormir mimir amimir (it dies)---
        if(willCollide && collidedWith instanceof Monster && !isShielded ){
            this.isAlive=false;
        }

        //if collides with an explosion, it dies
        if(willCollide && collidedWith instanceof Explosion && !isShielded){
            this.isAlive=false;
        }

        if(willCollide && collidedWith instanceof PowerUp ){
            this.powerUpInteraction(collidedWith);
        }
    }

    /**
     * <p> Checks if there is a collision of the provided collidable with the provided coordinates </p>
     * @param collidable the Sprite to check potential collision with
     * @param newX the X coordinate to check collision with
     * @param newY the Y coordinate to check collision with
     * @return whether collidable collides with the coordinates
     */
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

    /**
     * <p> Places a bomb on the map if the player can do so </p>
     */
    public void placeBomb(){

        if(Gdx.input.isKeyJustPressed(controls.getBombButton())  && this.isAlive)
        {
            // Get Collidables instance
            Collidables collidables = Collidables.getInstance();

            if(BOMB_COUNT > 0){
                BOMB_COUNT--;

                // Create new bomb that receives player coordinates
                //Bomb bomb = new Bomb( this.getX() , this.getY(), this.EXPLOSION_RANGE);

                Function<Float, Float> center = x -> (float) ((((int) (x / 32.0)) * 32) + 8);

                Bomb bomb = new Bomb( center.apply(getX()) , center.apply(getY()), this.EXPLOSION_RANGE);

                // Explode and remove bomb from active bombs after 4 seconds...
                bomb.getTimer().scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        if(!bomb.getExploded()){
                            bomb.explode();
                            activeBombs.remove(bomb);
                            collidables.removeCollidable(bomb);
                            BOMB_COLLISION_FLAG = false;

                            if((BOMB_COUNT) < MAX_BOMB_COUNT){
                                BOMB_COUNT++;
                            }
                        }
                    }
                }, 4);

                // Add to active bombs
                BOMB_COLLISION_FLAG = true;
                activeBombs.add(bomb);
                collidables.addCollidable(bomb);
            }else if(this.hasDetonator){
                for(Bomb bomb : this.activeBombs){
                    bomb.explode();
                }
                collidables.getCollidables().removeAll(activeBombs);
                this.activeBombs.clear();
                this.BOMB_COUNT = this.MAX_BOMB_COUNT;
            }
        }
    }
    /**
     * <p> Essentially activates the Power Up </p>
     * <p> Makes the appropriate changes based on the Power Up to the Player </p>
     * @param powerUp the collected Power Up
     */
    private void powerUpInteraction(Sprite powerUp){
        if(powerUp instanceof RollerSkates){
            if(!this.activePowerUps.contains("RollerSkate")){
                this.moveSpeed *= 1.25f;
                this.activePowerUps.add("RollerSkate");
            }
        }

        if(powerUp instanceof BonusBomb){
            this.MAX_BOMB_COUNT++;
            this.BOMB_COUNT++;
        }

        if(powerUp instanceof ExplosionRangeUp){
            this.EXPLOSION_RANGE++;
        }

        if(powerUp instanceof Shield){
            this.activateShield();
        }

        if(powerUp instanceof Ghost){
            this.activateGhost();
        }

        if(powerUp instanceof Detonator){
            this.hasDetonator = true;
        }

        if(powerUp instanceof BoxPlacement){
            placeableBoxes += 3;
        }

        Collidables collidables = Collidables.getInstance();
        collidables.removeCollidable(powerUp);
    }

    /**
     * <p> Activates the ghost Power Up </p>
     */
    private void activateGhost() {
        this.isGhosted = true;
        this.activePowerUps.add("Ghost");
        System.out.println("Ghost active");
        int duration = 5;

        Player tempPlayer = this;

        Timer.Task warnPlayer = new Timer.Task() {
            @Override
            public void run() {
                GHOST_TEXTURE = new Texture("assets/powerups/yellow-circle.png");
            }
        };

        Timer.Task deactivateGhost = new Timer.Task() {
            @Override
            public void run() {
                isGhosted = false;
                ArrayList<String> ghostList = new ArrayList<>();
                ghostList.add("Ghost");
                activePowerUps.removeAll(ghostList);
                System.out.println("Ghost over");

                Collidables collidables = Collidables.getInstance();

                Sprite collidedWith = null;
                for (Sprite collidable : collidables.getCollidables()) {
                    if (tempPlayer != collidable && collidesWith(collidable, getX(), getY())) {
                        collidedWith = collidable;
                        break;
                    }
                }

                if((collidedWith instanceof Crate || collidedWith instanceof Wall)){
                    isAlive= false;
                }

                GHOST_TEXTURE = new Texture("assets/powerups/red-circle.png");
            }
        };

        TimerManager.scheduleTask(warnPlayer, duration-3);
        TimerManager.scheduleTask(deactivateGhost, duration);

    }

    /**
     * <p> Activates the shield Power Up </p>
     */
    private void activateShield(){
        this.isShielded = true;
        this.activePowerUps.add("Shield");
        System.out.println("Shield active");
        int duration = 7;

        Timer.Task warnPlayer = new Timer.Task() {
            @Override
            public void run() {
                SHIELD_TEXTURE = new Texture("assets/powerups/green-circle.png");
            }
        };

        Timer.Task deactivateShield = new Timer.Task() {
            @Override
            public void run() {
                isShielded = false;
                ArrayList<String> shieldList = new ArrayList<>();
                shieldList.add("Shield");
                activePowerUps.removeAll(shieldList);
                System.out.println("Shield over");
                SHIELD_TEXTURE = new Texture("assets/powerups/circle.png");
            }
        };

        TimerManager.scheduleTask(warnPlayer, duration-3);
        TimerManager.scheduleTask(deactivateShield, duration);
    }

    public Boolean getShielded() {
        return isShielded;
    }

    public ArrayList<Bomb> getActiveBombs() {
        return activeBombs;
    }

    public String getName() {
        return name;
    }

    public int getPlacedBoxes() {
        return placedBoxes;
    }

    public void setPlacedBoxes(int placedBoxes) {
        this.placedBoxes = placedBoxes;
    }
}
