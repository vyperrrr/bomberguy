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
import s11.bomberguy.characters.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class SummaryScreen implements Screen {
    GameModel model;
    private Stage stage;
    private Table table;
    private BitmapFont font;
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

        HashMap<Integer, String> roundToWinner = model.getRoundToWinner();
        HashMap<String, Integer> playerToWinCount = model.getPlayerToWinCount();

        // Add data
        String roundWinner = model.determineRoundWinner();

        roundToWinner.put(model.getCurrentRound(), roundWinner);

        if (playerToWinCount.containsKey(roundWinner) && !roundWinner.equals("Döntetlen")) {
            // Key already exists, increment its value
            playerToWinCount.put(roundWinner, playerToWinCount.get(roundWinner) + 1);
        } else {
            // Key not present, simply put it in the map
            playerToWinCount.put(roundWinner, 1);
        }

        populateTable();

        if(model.getCurrentRound() != model.getRoundNum())
        {
            model.setCurrentRound(model.getCurrentRound()+1);
        }
    }

    private void populateTable() {
        // Clear any existing content in the table
        table.clear();

        // Add round number and winner labels in a single row
        Label roundNumberHeader = new Label("Kör száma", new Label.LabelStyle(font, Color.BLACK));
        roundNumberHeader.setAlignment(Align.center); // Align the text to the center

        Label winnerHeader = new Label("Kör nyertese", new Label.LabelStyle(font, Color.BLACK));
        winnerHeader.setAlignment(Align.center); // Align the text to the center

        // Add the labels to the table
        table.add(roundNumberHeader).padRight(100);
        table.add(winnerHeader).row();

        HashMap<Integer, String> roundToWinner = model.getRoundToWinner();

        // Add round numbers and winners
        roundToWinner.forEach((key, value) -> {
            Label roundLabel = new Label("Kör " + (key), new Label.LabelStyle(font, Color.BLACK));
            roundLabel.setAlignment(Align.center); // Align the text to the center

            Label winnerLabel = new Label(value, new Label.LabelStyle(font, Color.BLACK));
            winnerLabel.setAlignment(Align.center); // Align the text to the center

            // Add the labels to the table
            table.add(roundLabel).padRight(100);
            table.add(winnerLabel).row();
        });

        // Add an empty row for spacing
        table.row().padTop(20f);

        if(model.getCurrentRound() == model.getRoundNum())
        {
            String labelText = "Gyöztes "+model.determineGameWinner();
            if(model.determineGameWinner() == "Döntetlen")
                labelText = "Döntetlen";

            Label winLabel = new Label(labelText, new Label.LabelStyle(font, Color.BLACK));
            winLabel.setAlignment(Align.center);

            table.add(winLabel).colspan(2).center().padTop(20f);
        }


        if(model.getCurrentRound() != model.getRoundNum()) {
            // Add the button in a separate row
            TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
            buttonStyle.font = font;
            TextButton button = new TextButton("Következö kör", buttonStyle);

            // Adjust the font size of the button label
            button.getLabel().setFontScale(3f); // 3 times bigger
            button.getLabel().setColor(Color.BLACK);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Handle button click event
                    model.resetGame();
                    model.setScreen(new GameScreen(model));
                }
            });

            // Add the button to the table
            table.add(button).colspan(2).center().padTop(20f); // Add button spanning both columns and centered
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
