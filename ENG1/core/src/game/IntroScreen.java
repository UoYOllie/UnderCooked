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
public class IntroScreen extends ScreenAdapter {

    private ScreenController screenController;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Viewport viewport;
    private Stage stage;
    private Texture backgroundSprite;

    public static Texture spaceBackground = new Texture("menu/space-bg.png");
    private String mode;

    private Integer difficulty;


    /**
     * The constructor for the {@link MenuScreen}.
     * @param screenController The {@link ScreenController} of the {@link ScreenAdapter}.
     * @param orthographicCamera The {@link OrthographicCamera} that the game should use.
     */
    public IntroScreen(ScreenController screenController, OrthographicCamera orthographicCamera, String mode, Integer difficulty) {
        this.screenController = screenController;
        this.camera = orthographicCamera;
        this.batch = screenController.getSpriteBatch();

        viewport = new FitViewport(Constants.V_Width, Constants.V_Height, camera);

        stage = new Stage(viewport, batch);
        this.backgroundSprite = spaceBackground;
        this.mode = mode;
        this.difficulty = difficulty;

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Image welcomeLabel = new Image(new Texture("menu/title.png"));
        table.add(welcomeLabel).expandX();
        table.row();

        Label PiazzaLabel = new Label(String.format("PIAZZA BUILDING"), font);
        table.add(PiazzaLabel).expandX();
        table.row();

        Label YearLabel = new Label("YEAR:3999", font);
        table.add(YearLabel).expandX();
        table.row();

        Label IncidentLabel = new Label("The Piazza Panic Incident", font);
        table.add(IncidentLabel).expandX();
        table.row();

        Label UseLabel = new Label(String.format("PRESS %s TO START",Interactions.getKeyString(InputKey.InputTypes.USE).toUpperCase()), font);
        table.add(UseLabel).expandX();
        table.row();

        stage.addActor(table);

    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setDifficulty(Integer difficulty){
        this.difficulty = difficulty;
    }



    /**
     * Check for user input every frame and act on specified inputs.
     * @param delta The time between frames as a float.
     */
    public void update(float delta) {
        Interactions.updateKeys();

        if (Interactions.isJustPressed(InputKey.InputTypes.USE)) {
            screenController.setScreen(ScreenID.GAME);
            ((GameScreen) screenController.getScreen(ScreenID.GAME)).startGame(this.mode, difficulty);
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