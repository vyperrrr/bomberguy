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

    public Crate(Player owner, float x, float y){
        super.setTexture(CRATE_TEXTURE);
        super.setBounds(x - (x % 32), y - (y % 32), 32, 32);
        this.owner = owner;
    }

    public Crate destroy(SpriteBatch batch, TiledMap map){
        Collidables collidables = Collidables.getInstance();

        boolean willCollide = false;
        Sprite collidedWith = null;
        for (Sprite collidable : collidables.getCrates()) {
            if (this != collidable && this.getBoundingRectangle().overlaps(collidable.getBoundingRectangle())) {
                willCollide = true;
                collidedWith = collidable;
                break;
            }
        }

        if(willCollide && collidedWith instanceof Explosion){
            System.out.println("belefutott");
            System.out.println(this.getMapX()+" "+this.getMapY());
            MapGroupLayer groupLayer = (MapGroupLayer) map.getLayers().get("collidables");
            if(groupLayer == null) System.out.println("Nem talált");
            MapLayer layer = groupLayer.getLayers().get("Boxes");
            if(layer instanceof TiledMapTileLayer){
                System.out.println("halo");
                TiledMapTileLayer.Cell cell = ((TiledMapTileLayer) layer).getCell(this.getMapX(),this.getMapY());
                // Temporarily placed here
                if (owner != null) {
                    owner.setPlacedBoxes(owner.getPlacedBoxes() - 1);
                }
                if(cell != null) {
                    System.out.println("SZIA");
                    ((TiledMapTileLayer) layer).setCell(this.getMapX(), this.getMapY(), null);
                    // Temporarily commented out
                    //if (owner != null) {
                    //    owner.setPlacedBoxes(owner.getPlacedBoxes() - 1);
                    //}
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
