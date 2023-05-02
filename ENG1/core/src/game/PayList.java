package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import game.ScreenController.ScreenID;
import helper.Constants;
import interactions.InputKey;
import interactions.Interactions;

/**
 * A {@link ScreenAdapter} that is used when the game is paused.
 * It renders the {@link GameScreen} behind it, so the user can still
 * see the game.
 */
public class PayList extends ScreenAdapter {
    private ScreenController screenController;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Viewport viewport;
    private Stage stage;
    private GameScreen gameScreen;
    private ShapeRenderer shape;

    /**
     * The constructor for the {@link PayList}.
     * @param screenController The {@link ScreenController} of the {@link ScreenAdapter}.
     * @param orthographicCamera The {@link OrthographicCamera} that the game should use.
     */
    public PayList(ScreenController screenController, OrthographicCamera orthographicCamera) {
        this.screenController = screenController;
        this.camera = orthographicCamera;
        this.batch = screenController.getSpriteBatch();
        this.gameScreen = ((GameScreen) screenController.getScreen(ScreenID.GAME));
        this.shape = screenController.getShapeRenderer();
        shape.setAutoShapeType(true);

        viewport = new FitViewport(Constants.V_Width, Constants.V_Height, camera);
        stage = new Stage(viewport, batch);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        String[] strLabels = new String[] {
            "Costs",
                "--------------------",
                "Stations = $10",
                "New Cooks = $25",
                "--------------------",
                "POWERUPS:",
                "- 1.) Speed = $30",
                "- 2.) Teacup (Increase Wait Time) = $50",
                "- 3.) Menu (Change Request) = $50",
                "- 4.) Freeze Timer = $100",
                "- 5.) Chef Bluggus Mode = $80"
        };
        /* OLD CODE
        Label pauseLabel = new Label("PAUSED", font);
        table.add(pauseLabel).expandX();
        table.row();

        Label continueLabel = new Label(String.format("Press %s to continue",Interactions.getKeyString(InputKey.InputTypes.UNPAUSE)), font);
        table.add(continueLabel).expandX();
        table.row();

        Label instructionsLabel = new Label(String.format("Press %s for instructions",Interactions.getKeyString(InputKey.InputTypes.INSTRUCTIONS)), font);
        table.add(instructionsLabel).expandX();
        table.row();

        Label resetLabel = new Label(String.format("Press %s to reset",Interactions.getKeyString(InputKey.InputTypes.RESET_GAME)), font);
        table.add(resetLabel).expandX();
        table.row();
        */

        /** Contains the Labels objects for the PauseScreen */
        Label[] lblLabels = new Label[strLabels.length];

        for (int j = 0; j < lblLabels.length; j++) {
            String strLabel = strLabels[j];
            lblLabels[j] = new Label(String.format(strLabel), font);
            table.add(lblLabels[j]).expandX();
            table.row();
        }

        // pauseLabel.setFontScale(4);
        lblLabels[0].setFontScale(4);
        stage.addActor(table);
    }

    /**
     * Check for user input every frame and act on specified inputs.
     * @param delta The time between frames as a float.
     */
    public void update(float delta) {
        Interactions.updateKeys();
        // Check if the Unpause key was pressed.
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            screenController.playGameScreen();
            return;
        }
    }

    /**
     * The function used to render the {@link PayList}.
     *
     * <br>Draws the {@link GameScreen} underneath using the
     * {@link GameScreen#renderGame(float)} function, and then
     * renders the {@link PayList} over it.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

        gameScreen.renderGame(delta);

        shape.begin(ShapeRenderer.ShapeType.Filled);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shape.setColor(0,0,0,0.5F);
        shape.rect(0,0, Constants.V_Width,Constants.V_Height);
        shape.setColor(Color.WHITE);
        shape.end();

        stage.draw();

        this.update(delta);

    }
}
