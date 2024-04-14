package s11.bomberguy.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class SummaryScreen implements Screen {
    GameModel model;
    private Stage stage;
    private Table table;
    private BitmapFont font;
    private ArrayList<String> roundLabels;
    private ArrayList<String> winners;

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
        font.getData().setScale(4); // Set font scale to 2 (doubles the size)
        font.setColor(Color.BLACK); // Set font color

        roundLabels = new ArrayList<>();
        winners = new ArrayList<>();

        // Add some sample data
        roundLabels.add("Round 1");
        winners.add("Player A");
        roundLabels.add("Round 2");
        winners.add("Player B");
        roundLabels.add("Round 3");
        winners.add("Player A");
        // Add more round numbers and winners as needed

        populateTable();
    }

    private void populateTable() {
        // Clear any existing content in the table
        table.clear();

        // Add round number and winner labels in a single row
        Label roundNumberHeader = new Label("Round Number", new Label.LabelStyle(font, Color.BLACK));
        roundNumberHeader.setAlignment(Align.center); // Align the text to the center

        Label winnerHeader = new Label("Winner", new Label.LabelStyle(font, Color.BLACK));
        winnerHeader.setAlignment(Align.center); // Align the text to the center

        // Add the labels to the table
        table.add(roundNumberHeader).padRight(100);
        table.add(winnerHeader).row();

        // Add round numbers and winners
        for(int i = 0; i < winners.size(); i++) {
            Label roundLabel = new Label("Round " + (i + 1), new Label.LabelStyle(font, Color.BLACK));
            roundLabel.setAlignment(Align.center); // Align the text to the center

            Label winnerLabel = new Label(winners.get(i), new Label.LabelStyle(font, Color.BLACK));
            winnerLabel.setAlignment(Align.center); // Align the text to the center

            // Add the labels to the table
            table.add(roundLabel).padRight(100);
            table.add(winnerLabel).row();
        }

        // Add an empty row for spacing
        table.row().padTop(20f);

        // Add the button in a separate row
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        TextButton button = new TextButton("Next round", buttonStyle);

        // Adjust the font size of the button label
        button.getLabel().setFontScale(3f); // 3 times bigger
        button.getLabel().setColor(Color.BLACK);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle button click event
                System.out.println("Button clicked!");
            }
        });

        // Add the button to the table
        table.add(button).colspan(2).center().padTop(20f); // Add button spanning both columns and centered
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
