package s11.bomberguy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

public class SummaryScreen implements Screen {
    GameModel model;
    private Stage stage;
    private Table table;
    private BitmapFont font;
    private Array<String> roundNumbers;
    private Array<String> winners;

    public SummaryScreen(GameModel model)
    {
        this.model = model;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true); // Table will take the whole stage
        stage.addActor(table);

        font = new BitmapFont(); // You can load your own font here if needed
        font.getData().setScale(2); // Set font scale to 2 (doubles the size)
        font.setColor(Color.BLACK); // Set font color

        roundNumbers = new Array<>();
        winners = new Array<>();

        // Add some sample data
        roundNumbers.add("Round 1");
        winners.add("Player A");
        roundNumbers.add("Round 2");
        winners.add("Player B");
        roundNumbers.add("Round 3");
        winners.add("Player A");
        // Add more round numbers and winners as needed

        populateTable();
    }

    private void populateTable() {
        table.add(new Label("Round Number", new Label.LabelStyle(font, Color.BLACK))).padRight(100);
        table.add(new Label("Winner", new Label.LabelStyle(font, Color.BLACK))).row();

        for (int i = 0; i < roundNumbers.size; i++) {
            table.add(new Label(roundNumbers.get(i), new Label.LabelStyle(font, Color.BLACK))).padRight(100);
            table.add(new Label(winners.get(i), new Label.LabelStyle(font, Color.BLACK))).row();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        // Dispose of resources when the screen is hidden or switched
        stage.dispose();
        font.dispose();
    }

    // Other methods from the Screen interface
    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {}
}
