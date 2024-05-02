package s11.bomberguy.mapElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import s11.bomberguy.Collidables;
import s11.bomberguy.characters.Player;
import s11.bomberguy.explosives.Explosion;

public class Crate extends Sprite {
    private boolean isAlive = true;
    private boolean powerUpInside = false;
    private Player owner = null;
    private int mapX;
    private int mapY;
    private static final Texture CRATE_TEXTURE = new Texture("assets/crate.png");


    public Crate(float x, float y, int pixelSize){
        super.setTexture(CRATE_TEXTURE);
        super.setBounds(x, y, pixelSize, pixelSize);
    }

    public Crate(float x, float y, Texture texture, float width, float height, int mapX, int mapY){
        super.setTexture(texture);
        super.setBounds(x, y, width, height);
        this.mapX = mapX;
        this.mapY = mapY;
    }

    public Crate(Player owner, float x, float y, int mapX, int mapY) {
        super.setTexture(CRATE_TEXTURE);
        super.setBounds(x - (x % 32), y - (y % 32), 32, 32);
        this.mapX = mapX;
        this.mapY = mapY;
        this.owner = owner;
    }

    public Crate destroy(SpriteBatch batch, TiledMap map){
        Collidables collidables = Collidables.getInstance();

        boolean willCollide = false;
        Sprite collidedWith = null;
        for (Sprite collidable : collidables.getCollidables()) {
            if (this != collidable && this.getBoundingRectangle().overlaps(collidable.getBoundingRectangle())) {
                willCollide = true;
                collidedWith = collidable;
                break;
            }
        }

        if(willCollide && collidedWith instanceof Explosion){
            MapGroupLayer groupLayer = (MapGroupLayer) map.getLayers().get("collidables");
            MapLayer layer = groupLayer.getLayers().get("Boxes");
            if(layer instanceof TiledMapTileLayer){
                TiledMapTileLayer.Cell cell = ((TiledMapTileLayer) layer).getCell(this.getMapX(),this.getMapY());

                if(cell != null) {
                    ((TiledMapTileLayer) layer).setCell(this.getMapX(), this.getMapY(), null);
                    if (owner != null) {
                        owner.setPlacedBoxes(owner.getPlacedBoxes() - 1);
                    }
                }
            }

            return this;
        }
        return null;
    }

    public boolean isAlive() {
        return isAlive;
    }
    public Player getOwner() {
        return owner;
    }

    private void spawnPowerUp(){

    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public boolean isPowerUpInside() {
        return powerUpInside;
    }

    public void setPowerUpInside(boolean powerUpInside) {
        this.powerUpInside = powerUpInside;
    }
}
