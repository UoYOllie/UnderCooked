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

import game.ScreenController.ScreenID;
import helper.Constants;
import interactions.InputKey;
import interactions.Interactions;

/**
 * The {@link InstructionScreen}, which provides the
 * player with instructions on how to play the game.
 */
public class InstructionScreen extends ScreenAdapter {

    private ScreenID prevScreenID = ScreenID.MENU;
    private OrthographicCamera camera;
    private ScreenController screenController;
    private FitViewport viewport;
    private Stage stage;
    private SpriteBatch batch;

    /**
     * The constructor for the {@link PauseScreen}.
     * @param screenController The {@link ScreenController} of the {@link ScreenAdapter}.
     * @param orthographicCamera The {@link OrthographicCamera} that the game should use.
     */
    public InstructionScreen(ScreenController screenController, OrthographicCamera orthographicCamera) {
        this.screenController = screenController;
        this.camera = orthographicCamera;
        this.batch = screenController.getSpriteBatch();

        viewport = new FitViewport(Constants.V_Width, Constants.V_Height, camera);
        stage = new Stage(viewport, batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("Instructions", font);
        gameOverLabel.setFontScale(3);
        table.add(gameOverLabel).expandX();

        table.row();

        String[] instructions = new String[] {

                "",
                String.format("The bin allows you to dispose of items you no longer need.",
                        Interactions.getKeyString(InputKey.InputTypes.USE),
                        Interactions.getKeyString(InputKey.InputTypes.PUT_DOWN)),
                "RECIPES:-",
                "Plain Salad- chop lettuce, bring it to the assembly station, put it in a plate and then serve it to the customer.",
                "Tomato Salad- chop tomato, chop lettuce, bring it to the assembly station, put it in a plate and then serve it to the customer.",
                "Onion Salad- chop onion, chop lettuce, bring it to the assembly station, put it in a plate and then serve it to the customer.",
                "Plain Burger- Take two buns from the pantry, meat from the fridge, cook the meat, " +
                        "\n place all the items on the assembly station, chop salad and then serve the customer",
                "Lettuce Burger- Take two buns from the pantry, meat from the fridge, cook the meat, " +
                        "\n place all the items on the assembly station, chop lettuce and then serve the customer",
                "Tomato Onion Burger- Take two buns from the pantry, meat from the fridge, cook the meat, " +
                        "\n place all the items on the assembly station, chop tomato, chop onion and serve customer.",
                "Plain Potato- Take jacket potatoes from the pantry, coleslaw from the bottom left of the fridge, " +
                        "\n cook the potato, place all the items on the assembly station, serve the customer.",
                "Beans Potato- Take jacket potatoes and canned beans from pantry, coleslaw from the bottom left of the fridge, " +
                        "\n cook the potato and beans, place all the items on the assembly station, serve the customer.",
                "Coleslaw Potato- Take jacket potatoes from pantry, coleslaw from the bottom left of the fridge, " +
                        "\n cook the potato, place all the items on the assembly station, serve the customer.",


                "\n POWERUPS:-",
                "Speed- makes the cook you control move faster.",
                "Teacup- Increases the customer's wait time.",
                "Menu- Changes the request of customer",
                "Freeze Timer- Temporarily freezes time so that customer can wait for more time.",
                "Chef Bluggus Mode- It changes to chef Bluggus.",

                "",
                "Your goal is to successfully give every customer the food they request, and the game will end once you do.",
                "",

        };

        for (String instruction : instructions) {
            Label instLabel = new Label(instruction, font);
            table.add(instLabel).expandX();
            table.row();
        }

        Label extraText = new Label("To go back, press I", font);
        extraText.setFontScale(1.5F);
        table.add(extraText);

        stage.addActor(table);
    }

    /**
     * Check for user input every frame and act on specified inputs.
     * @param delta The time between frames as a float.
     */
    public void update(float delta) {
        // Check for input.
        Interactions.updateKeys();
        if (Interactions.isJustPressed(InputKey.InputTypes.INSTRUCTIONS)) {
            screenController.setScreen(prevScreenID);
        }
    }

    /**
     * The function used to render the {@link PauseScreen}.
     *
     * <br>Draws the {@link #stage} of the {@link PauseScreen},
     * which contains all the text as {@link Label}s.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        this.update(delta);
    }

    /**
     * Sets the variable {@link #prevScreenID} to the input,
     * which allows the {@link PauseScreen} to return the
     * player to the screen they opened it from.
     * @param scID The {@link ScreenController.ScreenID} of the previous {@link ScreenAdapter}.
     */
    public void setPrevScreenID(ScreenID scID) {
        prevScreenID = scID;
    }
}
