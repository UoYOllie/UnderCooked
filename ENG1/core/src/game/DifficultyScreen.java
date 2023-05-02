package game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import game.ScreenController.ScreenID;
import helper.Constants;
import interactions.InputKey;
import interactions.Interactions;

/**
 * The {@link MenuScreen}, which provides the player with
 * a few options of inputs, which do different things.
 * One of which is to change to the {@link GameScreen} and
 * play the game.
 */
public class DifficultyScreen extends ScreenAdapter {

    private ScreenController screenController;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Viewport viewport;
    private Stage stage;
    private Texture backgroundSprite;

    public static Texture spaceBackground = new Texture("menu/space-bg.png");



    /**
     * The constructor for the {@link MenuScreen}.
     * @param screenController The {@link ScreenController} of the {@link ScreenAdapter}.
     * @param orthographicCamera The {@link OrthographicCamera} that the game should use.
     */
    public DifficultyScreen(ScreenController screenController, OrthographicCamera orthographicCamera) {
        this.screenController = screenController;
        this.camera = orthographicCamera;
        this.batch = screenController.getSpriteBatch();

        viewport = new FitViewport(Constants.V_Width, Constants.V_Height, camera);

        stage = new Stage(viewport, batch);
        this.backgroundSprite = spaceBackground;
        //this.mode = mode;

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Image welcomeLabel = new Image(new Texture("menu/title.png"));
        table.add(welcomeLabel).expandX();
        table.row();

        Label textLabel = new Label(String.format("The higher the difficulty, the less patient the customers will be!"), font);
        table.add(textLabel).expandX();
        table.row();

        Label easyLabel = new Label(String.format("PRESS %s FOR EASY",Interactions.getKeyString(InputKey.InputTypes.EASY).toUpperCase()), font);
        table.add(easyLabel).expandX();
        table.row();

        Label mediumLabel = new Label(String.format("PRESS %s FOR MEDIUM",Interactions.getKeyString(InputKey.InputTypes.MEDIUM).toUpperCase()), font);
        table.add(mediumLabel).expandX();
        table.row();

        Label hardLabel = new Label(String.format("PRESS %s FOR HARD",Interactions.getKeyString(InputKey.InputTypes.HARD).toUpperCase()), font);
        table.add(hardLabel).expandX();
        table.row();

        stage.addActor(table);

    }




    /**
     * Check for user input every frame and act on specified inputs.
     * @param delta The time between frames as a float.
     */
    public void update(float delta) {
        Interactions.updateKeys();

        if (Interactions.isJustPressed(InputKey.InputTypes.EASY)) {
            screenController.setScreen(ScreenID.INTRO);
            screenController.setDifficulty(1);
        }
        else if (Interactions.isJustPressed(InputKey.InputTypes.MEDIUM)) {
            screenController.setScreen(ScreenID.INTRO);
            screenController.setDifficulty(2);
        }
        else if (Interactions.isJustPressed(InputKey.InputTypes.HARD)) {
            screenController.setScreen(ScreenID.INTRO);
            screenController.setDifficulty(3);
        }
    }

    /**
     * The function used to render the {@link MenuScreen}.
     *
     * <br>Draws the {@link #stage} of the {@link MenuScreen},
     * which contains all the text as {@link Label}s.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        camera.zoom = 1f;
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(this.backgroundSprite, 0, 0, Constants.V_Width, Constants.V_Height);
        batch.end();
        stage.draw();
        this.update(delta);
    }
}