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
import jdk.internal.org.jline.utils.DiffHelper;

/**
 * The {@link MenuScreen}, which provides the player with
 * a few options of inputs, which do different things.
 * One of which is to change to the {@link GameScreen} and
 * play the game.
 */
public class MenuScreen extends ScreenAdapter {

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
    public MenuScreen(ScreenController screenController, OrthographicCamera orthographicCamera) {
        this.screenController = screenController;
        this.camera = orthographicCamera;
        this.batch = screenController.getSpriteBatch();

        viewport = new FitViewport(Constants.V_Width, Constants.V_Height, camera);

        stage = new Stage(viewport, batch);
        this.backgroundSprite = spaceBackground;

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Image welcomeLabel = new Image(new Texture("menu/title.png"));
        table.add(welcomeLabel).expandX();
        table.row();

        Label scenarioLabel = new Label(String.format("PRESS %s TO START SCENARIO MODE",Interactions.getKeyString(InputKey.InputTypes.START_SCENARIO).toUpperCase()), font);
        table.add(scenarioLabel).expandX();
        table.row();

        Label endlessLabel = new Label(String.format("PRESS %s TO START ENDLESS MODE",Interactions.getKeyString(InputKey.InputTypes.START_ENDLESS).toUpperCase()), font);
        table.add(endlessLabel).expandX();
        table.row();

        Label instructionLabel = new Label(String.format("PRESS %s FOR INSTRUCTIONS",Interactions.getKeyString(InputKey.InputTypes.INSTRUCTIONS).toUpperCase()), font);
        table.add(instructionLabel).expandX();
        table.row();

        Label creditLabel = new Label(String.format("PRESS %s TO VIEW CREDITS",Interactions.getKeyString(InputKey.InputTypes.CREDITS).toUpperCase()), font);
        table.add(creditLabel).expandX();
        table.row();

        Label quitLabel = new Label(String.format("PRESS %s TO QUIT",Interactions.getKeyString(InputKey.InputTypes.QUIT).toUpperCase()), font);
        table.add(quitLabel).expandX();


        stage.addActor(table);

    }



    /**
     * Check for user input every frame and act on specified inputs.
     * @param delta The time between frames as a float.
     */
    public void update(float delta) {
        Interactions.updateKeys();

        if (Interactions.isJustPressed(InputKey.InputTypes.START_SCENARIO)) {
            screenController.setScreen(ScreenID.DIFFICULTY);
            screenController.setMode("scenario");
        }
        else if (Interactions.isJustPressed(InputKey.InputTypes.START_ENDLESS)) {
            screenController.setScreen(ScreenID.DIFFICULTY);
            screenController.setMode("endless");
        }
        // Set the screen to the instructions screen
        else if (Interactions.isJustPressed(InputKey.InputTypes.INSTRUCTIONS)) {
            screenController.setScreen(ScreenID.INSTRUCTIONS);
        }
        else if (Interactions.isJustPressed(InputKey.InputTypes.CREDITS)) {
            ((CreditsScreen)screenController.getScreen(ScreenID.CREDITS)).setPrevScreenID(ScreenID.MENU);
            screenController.setScreen(ScreenID.CREDITS);
        }
        else if (Interactions.isJustPressed(InputKey.InputTypes.QUIT)) {
            Gdx.app.exit();
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
