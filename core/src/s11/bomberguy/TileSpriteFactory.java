package s11.bomberguy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import s11.bomberguy.mapElements.Crate;
import s11.bomberguy.mapElements.Wall;

public class TileSpriteFactory {

    public static Sprite createSpriteFromTile(TiledMap tiledMap, TiledMapTileLayer layer, int col, int row) {
        // Get the cell at the specified column and row
        TiledMapTileLayer.Cell cell = layer.getCell(col, row);

        // Check if the cell is not empty
        if (cell != null) {
            // Get the tile texture region
            Texture texture = cell.getTile().getTextureRegion().getTexture();

            // Create a sprite using the tile texture
            Sprite sprite = new Sprite(texture);

            // Set the position of the sprite based on the tile coordinates
            float x = col * layer.getTileWidth();
            float y = row * layer.getTileHeight();
            sprite.setPosition(x, y);

            // Optionally, you can set the size of the sprite to match the tile size
            sprite.setSize(layer.getTileWidth(), layer.getTileHeight());

            return sprite;
        }

        // Return null if the cell is empty
        return null;
    }

    public static <T extends Sprite> T createCrateOrWall(TiledMap tiledMap, TiledMapTileLayer layer, int col, int row){
        // Get the cell at the specified column and row
        TiledMapTileLayer.Cell cell = layer.getCell(col, row);

        // Check if the cell is not empty
        if (cell != null) {
            Texture texture = cell.getTile().getTextureRegion().getTexture();

            float x = col * layer.getTileWidth();
            float y = row * layer.getTileHeight();

            if(cell.getTile().getId() > 400){
                Crate crate = new Crate(x,y,texture, layer.getTileWidth(), layer.getTileHeight(),col,row);
                return (T) crate;
            }

            Wall wall = new Wall(x,y,texture, layer.getTileWidth(), layer.getTileHeight());
            return (T) wall;
        }

        // Return null if the cell is empty
        return null;
    }
}
