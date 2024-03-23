package s11.bomberguy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import s11.bomberguy.characters.*;

import java.util.ArrayList;

public class GameScreen implements Screen {
    private GameModel model;
    private OrthographicCamera camera;
    private SpriteBatch batch;


    // game provides initialized data
    public GameScreen(GameModel model) {
        this.model = model;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
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
        // Begin draw
        batch.begin();

        // Draw content
        movePlayers();
        drawPlayers();
        placeBombs();
        drawBombs();
        drawCrates();
        drawWalls();

        // End draw
        batch.end();
    }

    public void drawPlayers()
    {
        // Render players
        model.getPlayers().forEach(player -> {
            player.render(batch);
        });
    }

    public void movePlayers()
    {
        // Need to pass collidables to move, only players for now
        model.getPlayers().forEach(Player::move);
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

    public void drawCrates(){
        model.getCrates().forEach(crate -> crate.render(batch));
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
