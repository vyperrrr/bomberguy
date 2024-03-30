package s11.bomberguy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import s11.bomberguy.characters.Monster;
import s11.bomberguy.characters.Player;
import s11.bomberguy.explosives.Explosion;
import s11.bomberguy.mapElements.Crate;

public class GameScreen implements Screen {
    private final GameModel model;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final OrthogonalTiledMapRenderer tiledMapRenderer;


    // game provides initialized data
    public GameScreen(GameModel model) {
        this.model = model;
        this.camera = new OrthographicCamera();

        // Set the camera viewport to match the map dimensions
        this.camera.setToOrtho(false, 960, 640); // Map dimensions in pixels

        this.batch = new SpriteBatch();

        // Calculate the unit scale to fit the map onto the screen
        float unitScale = 1f; // Start with 1:1 mapping
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        if (960 * unitScale > screenWidth || 640 * unitScale > screenHeight) {
            // Adjust unit scale if the map is too large for the screen
            unitScale = Math.min(screenWidth / 960, screenHeight / 640);
        }

        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(model.getTiledMap(), unitScale, batch);
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Set the clear color to grey (R, G, B, Alpha)
        Gdx.gl.glClearColor(128, 128, 128, 1);
        // Clear the screen with the specified color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        // Begin draw
        batch.begin();

        // Draw content
        movePlayers();
        drawPlayers();
        moveMonsters();
        drawMonsters();
        placeBombs();
        drawBombs();
        drawCrates();
        drawWalls();
        drawExplosions();

        // End draw
        batch.end();
    }

    public void drawPlayers()
    {
        // Render players
        model.getPlayers().forEach(player -> {
            if(player.isAlive()){
                player.render(batch);
            }else{
                model.getCollidables().removeCollidable(player);
            }
        });
    }

    public void drawMonsters()
    {
        // Render players
        model.getMonsters().forEach(monster -> {
            if(monster.isAlive()){
                monster.render(batch);
            }
        });
    }

    public void movePlayers()
    {
        // Need to pass collidables to move, only players for now
        model.getPlayers().forEach(Player::move);
    }

    public void moveMonsters()
    {
        model.getMonsters().forEach(Monster::move);
    }

    public void placeBombs()
    {
        // Render bombs
        model.getPlayers().forEach(Player::placeBomb);
    }

    public void drawBombs()
    {
        // Render bombs
        model.getPlayers().forEach(player -> player.getActiveBombs().forEach(bomb -> bomb.render(batch)));
    }

    public void drawExplosions(){
        model.getCollidables().getCollidables().stream().forEach( sprite -> {
            if(sprite instanceof Explosion){
                ((Explosion) sprite).render(batch);
            }
        }  );
    }

    public void drawCrates(){
        model.getCollidables().getCollidables().forEach(sprite -> {
            if(sprite instanceof Crate){
                ((Crate) sprite).render(batch, model.getTiledMap());
            }
        }
        );
    }

    public void drawWalls(){
        model.getWalls().forEach(wall -> wall.render(batch));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
