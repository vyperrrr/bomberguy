package s11.bomberguy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import s11.bomberguy.characters.Monster;
import s11.bomberguy.characters.Player;
import s11.bomberguy.explosives.Explosion;
import s11.bomberguy.mapElements.Crate;
import s11.bomberguy.powerups.*;

import java.util.ArrayList;
import java.util.function.Function;

public class GameScreen implements Screen {
    private final float SCREEN_WIDTH = 960;
    private final float  SCREEN_HEIGHT = 640;
    private final GameModel model;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final OrthogonalTiledMapRenderer tiledMapRenderer;
    private final BitmapFont font;
    private int overFor = 0;
    private final Stage stage;
    private final Table table;

    // game provides initialized data
    public GameScreen(GameModel model) {
        this.model = model;
        this.camera = new OrthographicCamera();

        // Set the camera viewport to match the map dimensions
        this.camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT); // Map dimensions in pixels

        this.batch = new SpriteBatch();

        this.font = new BitmapFont();
        font.getData().setScale(1);
        font.setColor(Color.BLACK);

        // Table for scoreboard
        this.table = new Table();
        table.padTop(5);

        // Stage for table
        stage = new Stage();
        Viewport viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        stage.setViewport(viewport);
        Gdx.input.setInputProcessor(stage);

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

    /**
     * <p> Draws the state of the game to the screen.</p>
     * @param delta Time since last refresh
     */
    @Override
    public void render(float delta) {
        if (model.isOver() || overFor > 0) { overFor ++; }
        // Run for a bit longer with only the last player
        if (overFor > 480) {
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
        drawScoreboard();
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

        stage.act(delta);
        stage.draw();
    }

    /**
     * <p> Draws the current score of each player to the top of the window.</p>
     */
    public void drawScoreboard() {
        // Clear the table first
        table.clear();

        // Set the table to fill the stage
        table.setFillParent(true);

        // Iterate through player scores and add them to the table
        model.getPlayerToWinCount().forEach((key, value) -> {
            Label playerNameLabel = new Label(key+":", new Label.LabelStyle(font, Color.BLACK));
            Label playerWinCountLabel = new Label(value.toString(), new Label.LabelStyle(font, Color.BLACK));
            table.add(playerNameLabel).padRight(10);
            table.add(playerWinCountLabel).padRight(20);
        });

        // Since the table is set to fill the parent, we adjust its position to align it at the top
        table.align(Align.top);

        // Add the table to the stage only if it's not already added
        if (!stage.getActors().contains(table, true)) {
            stage.addActor(table);
        }
    }

    /**
     * <p> Draws the players to the screen.</p>
     */
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

    /**
     * <p> Draws the monsters to the screen.</p>
     */
    public void drawMonsters()
    {
        // Render players
        model.getMonsters().forEach(monster -> {
            if(monster.isAlive()){
                monster.render(batch);
            }else{
                model.getCollidables().removeCollidable(monster);
            }
        });
    }

    /**
     * <p> Checks if player movement keys are pressed down, and moves the player if they are.</p>
     */
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
        model.getPlayers().forEach(x -> x.move(model.getTiledMap()));
    }
    /**
     * <p> Calls the monsters movement method.</p>
     */
    public void moveMonsters()
    {
        model.getMonsters().forEach(Monster::move);
    }

    /**
     * <p> Places a bomb under the player if they pressed the corresponding button.</p>
     */
    public void placeBombs()
    {
        // Render bombs
        model.getPlayers().forEach(Player::placeBomb);
    }

    /**
     * <p> Draws the bombs to the screen.</p>
     */
    public void drawBombs()
    {
        // Render bombs
        model.getPlayers().forEach(player -> player.getActiveBombs().forEach(bomb -> {
            if(!bomb.getExploded()){
                bomb.render(batch);
            }
        }));
    }

    /**
     * <p> Draws the explosions to the screen. </p>
     */
    public void drawExplosions(){
        model.getCollidables().getCollidables().forEach(sprite -> {
            if(sprite instanceof Explosion){
                ((Explosion) sprite).render(batch);
            }
        }
        );
    }

    /**
     * <p> Draws the icons representing the powerUps. </p>
     */
    public void drawPowerUps(){
        model.getCollidables().getCollidables().forEach(sprite -> {
                    if(sprite instanceof PowerUp){
                        ((PowerUp) sprite).render(batch);
                    }
                }
        );
    }

    /**
     * <p> Checks if there are crates to be removed from the game and removes them.</p>
     */
    public void destroyCrates()
    {
        ArrayList<Crate> cratesToDestroy = new ArrayList<>();
        model.getCollidables().getCollidables().forEach(sprite -> {
                    if(sprite instanceof Crate){
                        cratesToDestroy.add(((Crate) sprite).destroy(model.getTiledMap()));
                    }
                }
        );

        Function<Float, Float> center = x -> (float) ((((int) (x / 32.0)) * 32) + 8);

        cratesToDestroy.forEach(crateToDestroy -> {
            model.getCollidables().removeCollidable(crateToDestroy);
            if(crateToDestroy != null && crateToDestroy.isPowerUpInside()){
                model.putDownRandomPowerUp(center.apply(crateToDestroy.getX()), center.apply(crateToDestroy.getY()));
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
