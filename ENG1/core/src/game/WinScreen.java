package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import helper.Constants;
import helper.Util;
import interactions.InputKey;
import interactions.Interactions;

/**
 * The {@link GameOverScreen}, which shows once the player
 * has finished the game. It provides the player with the
 * option to {@link InputKey.InputTypes#QUIT} or to {@link InputKey.InputTypes#RESET_GAME}.
 */
public class WinScreen extends ScreenAdapter {
    private Viewport viewport;
    private ScreenController screenController;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage stage;

    private Label timeLabel;

    /**
     * The constructor for the {@link GameOverScreen}.
     * @param screenController The {@link ScreenController} of the {@link ScreenAdapter}.
     * @param orthographicCamera The {@link OrthographicCamera} that the game should use.
     */
    public WinScreen(ScreenController screenController, OrthographicCamera orthographicCamera) {

        this.screenController = screenController;
        this.camera = orthographicCamera;
        this.batch = screenController.getSpriteBatch();

        viewport = new FitViewport(Constants.V_Width, Constants.V_Height, camera);
        stage = new Stage(viewport, batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("YOU WON THE GAME!", font);
        gameOverLabel.setFontScale(3);
        table.add(gameOverLabel).expandX();

        table.row();

        timeLabel = new Label(screenController.getEndTime(), font);
        timeLabel.setFontScale(2);
        table.add(timeLabel);

        table.row();

        Label extraText = new Label(String.format("To restart, press %s.", Interactions.getKeyString(InputKey.InputTypes.RESET_GAME)), font);
        extraText.setFontScale(1);
        table.add(extraText);

        table.row();

        Label quitText = new Label(String.format("To quit, press %s.", Interactions.getKeyString(InputKey.InputTypes.QUIT)), font);
        quitText.setFontScale(1);
        table.add(quitText);

        stage.addActor(table);

    }


    /**
     * Check for user input every frame and act on specified inputs.
     * @param delta The time between frames as a float.
     */
    public void update(float delta) {
        // Check for input.
        Interactions.updateKeys();
        if (Interactions.isJustPressed(InputKey.InputTypes.RESET_GAME)) {
            screenController.resetGameScreen();
            screenController.setScreen(ScreenController.ScreenID.MENU);
        }
        else if (Interactions.isJustPressed(InputKey.InputTypes.QUIT)) {
            Gdx.app.exit();
        }
    }

    /**
     * The function used to render the {@link GameOverScreen}.
     *
     * <br>Draws the {@link #stage} of the {@link GameOverScreen},
     * which contains all the text as {@link Label}s.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        stage.draw();

        this.update(delta);
    }
}
