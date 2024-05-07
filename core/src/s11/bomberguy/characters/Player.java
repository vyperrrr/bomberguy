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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import s11.bomberguy.Collidables;
import s11.bomberguy.PlayerControl;
import s11.bomberguy.TimerManager;
import s11.bomberguy.explosives.Bomb;
import s11.bomberguy.explosives.Explosion;
import s11.bomberguy.mapElements.Crate;
import s11.bomberguy.mapElements.Wall;
import s11.bomberguy.powerups.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Function;


public class Player extends Character {
    private String name;
    private Timer timer;
    PlayerControl controls;
    private int BOMB_COUNT;
    private int MAX_BOMB_COUNT;
    private int EXPLOSION_RANGE = 2;
    private ArrayList<Bomb> activeBombs;
    private boolean BOMB_COLLISION_FLAG;

    private ArrayList<Explosion> explosions;
    private ArrayList<String> activePowerUps;
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
        this.timer = new Timer();
        this.setPlayerTexture(this.name);
        font.getData().setScale(0.55f); // Set font scale to 2 (doubles the size)
        font.setColor(Color.BLACK);
    }

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

    public void render(SpriteBatch batch)
    {
        batch.draw(this.getTexture(), this.getX(), this.getY(), this.getWidth(), this.getHeight());

        layout.setText(font, (CharSequence) this.name);

        this.font.draw(batch, this.name, getX()+this.getWidth()/2-layout.width/2, getY() + getHeight() + 10);

        if(this.isShielded){
            batch.draw(SHIELD_TEXTURE, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }

        if(this.isGhosted){
            batch.draw(GHOST_TEXTURE, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }

    }

    // Sprites are collidable
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
            System.out.println("Es hora de dormir");
        }

        //if collides with an explosion, it dies
        if(willCollide && collidedWith instanceof Explosion && !isShielded){
            this.isAlive=false;
            System.out.println("die");
        }

        if(willCollide && collidedWith instanceof PowerUp ){
            this.powerUpInteraction(collidedWith);
        }
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

    public void placeBomb(){

        if(Gdx.input.isKeyJustPressed(controls.getBombButton())  && this.isAlive)
        {
            // Get Collidables instance
            Collidables collidables = Collidables.getInstance();

            if(BOMB_COUNT > 0){
                BOMB_COUNT--;

                // Create new bomb that receives player coordinates
                //Bomb bomb = new Bomb( this.getX() , this.getY(), this.EXPLOSION_RANGE);

                Function<Float, Float> center = x ->{
                    float result = (((int) (x / 32.0)) * 32) + 8;
                    return result;
                };

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

    private void powerUpInteraction(Sprite powerUp){
        if(powerUp instanceof RollerSkates){
            if(!this.activePowerUps.contains("RollerSkate")){
                this.moveSpeed *= 1.25;
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

                if(collidedWith != null && (collidedWith instanceof Crate || collidedWith instanceof Wall)){
                    isAlive= false;
                }

                GHOST_TEXTURE = new Texture("assets/powerups/red-circle.png");
            }
        };

        TimerManager.scheduleTask(warnPlayer, duration-3);
        TimerManager.scheduleTask(deactivateGhost, duration);

    }

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

    public ArrayList<String> getActivePowerUps() {
        return activePowerUps;
    }

    public Boolean getShielded() {
        return isShielded;
    }

    public void setActivePowerUps(ArrayList<String> activePowerUps) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getPlacedBoxes() {
        return placedBoxes;
    }

    public void setPlacedBoxes(int placedBoxes) {
        this.placedBoxes = placedBoxes;
    }
}
