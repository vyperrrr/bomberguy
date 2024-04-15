package s11.bomberguy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import s11.bomberguy.characters.Character;
import s11.bomberguy.characters.Monster;
import s11.bomberguy.characters.Player;
import s11.bomberguy.explosives.Explosion;
import s11.bomberguy.mapElements.Crate;
import s11.bomberguy.powerups.*;

import java.util.ArrayList;

public class GameScreen implements Screen {
    private final float SCREEN_WIDTH = 960;
    private final float  SCREEN_HEIGHT = 640;
    private final GameModel model;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final OrthogonalTiledMapRenderer tiledMapRenderer;
    private final BitmapFont font;
    private final GlyphLayout glyph;
    private int overFor = 0;


    // game provides initialized data
    public GameScreen(GameModel model) {
        this.model = model;
        this.camera = new OrthographicCamera();

        // Set the camera viewport to match the map dimensions
        this.camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT); // Map dimensions in pixels

        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.glyph = new GlyphLayout();

        // Calculate the unit scale to fit the map onto the screen
        float unitScale = 1f; // Start with 1:1 mapping
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        if (SCREEN_WIDTH * unitScale > screenWidth || SCREEN_HEIGHT * unitScale > screenHeight) {
            // Adjust unit scale if the map is too large for the screen
            unitScale = Math.min(screenWidth / SCREEN_WIDTH, screenHeight / SCREEN_HEIGHT);
        }

        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(model.getTiledMap(), unitScale, batch);
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (model.isOver() || overFor > 0) { overFor ++; }
        // Run for a bit longer with only the last player
        if (overFor > 480) {
            model.determineCurrentRoundWinner();
            model.setScreen(new SummaryScreen(model));
        }


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
        drawExplosions();
        destroyCrates();
        drawPowerUps();

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
        int alive = 0;
        ArrayList<Player> players = model.getPlayers();
        for (Player player : players) {
            if (player.isAlive()) {
                alive++;
            }
        }
        if (alive <= 1) {
            model.setOver();
        }
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
        model.getPlayers().forEach(player -> player.getActiveBombs().forEach(bomb -> {
            if(!bomb.getExploded()){
                bomb.render(batch);
            }
        }));
    }

    public void drawExplosions(){
        model.getCollidables().getCollidables().forEach(sprite -> {
            if(sprite instanceof Explosion){
                ((Explosion) sprite).render(batch);
            }
        }
        );
    }

    public void drawPowerUps(){
        model.getCollidables().getCollidables().forEach(sprite -> {
                    if(sprite instanceof PowerUp){
                        ((PowerUp) sprite).render(batch);
                    }
                }
        );
    }

    public void destroyCrates()
    {
        ArrayList<Crate> cratesToDestroy = new ArrayList<>();
        model.getCollidables().getCollidables().forEach(sprite -> {
                    if(sprite instanceof Crate){
                        cratesToDestroy.add(((Crate) sprite).destroy(batch, model.getTiledMap()));
                    }
                }
        );
        cratesToDestroy.forEach(crateToDestroy -> {
            model.getCollidables().removeCollidable(crateToDestroy);
            if(crateToDestroy != null){
                model.getCollidables().addCollidable(new Shield(crateToDestroy.getX(), crateToDestroy.getY()));
            }
        });
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
