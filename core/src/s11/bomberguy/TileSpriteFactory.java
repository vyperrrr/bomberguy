package s11.bomberguy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import s11.bomberguy.mapElements.Crate;
import s11.bomberguy.mapElements.Wall;

public class TileSpriteFactory {
    /**
     * <p> Checks col,row position of the layer, and if the cell is a crate, creates a Crate instance from it,
     * if it is a wall it creates a Wall instance from.</p>
     * @param layer the layer on which to check for crate or wall
     * @param col the cell of the row to check
     * @param row the row of the layer to check
     * @return T type Sprite if the cell is a crate or a wall, otherwise null
     */
    public static Sprite createCrateOrWall(TiledMapTileLayer layer, int col, int row){
        // Get the cell at the specified column and row
        TiledMapTileLayer.Cell cell = layer.getCell(col, row);

        // Check if the cell is not empty
        if (cell != null) {
            Texture texture = cell.getTile().getTextureRegion().getTexture();

            float x = col * layer.getTileWidth();
            float y = row * layer.getTileHeight();

            if(cell.getTile().getId() > 400){
                return new Crate(x,y,texture, layer.getTileWidth(), layer.getTileHeight(),col,row);
            }

            return new Wall(x,y,texture, layer.getTileWidth(), layer.getTileHeight());
        }

        // Return null if the cell is empty
        return null;
    }
}
