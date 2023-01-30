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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import game.ScreenController.ScreenID;
import helper.Constants;
import interactions.InputKey;
import interactions.Interactions;

public class MenuScreen extends ScreenAdapter {

    private ScreenController screenController;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Sprite sprite;

    private Viewport viewport;
    private Stage stage;
    private Texture BackGroundIMG;
    public MenuScreen(ScreenController screenController, OrthographicCamera orthographicCamera) {
        this.screenController = screenController;
        this.camera = orthographicCamera;
        this.batch = screenController.getSpriteBatch();

        viewport = new FitViewport(Constants.V_Width, Constants.V_Height, camera);
        stage = new Stage(viewport, batch);
        this.BackGroundIMG = new Texture("Maps/StartMenuBackGround.png");
        this.sprite = new Sprite(BackGroundIMG);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label welcomeLabel = new Label("UNDERCOOKED", font);
        table.add(welcomeLabel).expandX();
        table.row();

        Label startLabel = new Label(String.format("PRESS %s TO START",Interactions.getKeyString(InputKey.InputTypes.START_GAME).toUpperCase()), font);
        table.add(startLabel).expandX();
        table.row();

        Label instructionLabel = new Label(String.format("PRESS %s FOR INSTRUCTIONS",Interactions.getKeyString(InputKey.InputTypes.INSTRUCTIONS).toUpperCase()), font);
        table.add(instructionLabel).expandX();
        table.row();

        Label creditLabel = new Label(String.format("PRESS %s TO VIEW CREDITS",Interactions.getKeyString(InputKey.InputTypes.CREDITS).toUpperCase()), font);
        table.add(creditLabel).expandX();
        table.row();

        Label quitLabel = new Label(String.format("PRESS %s TO QUIT",Interactions.getKeyString(InputKey.InputTypes.QUIT).toUpperCase()), font);
        table.add(quitLabel).expandX();

        welcomeLabel.setFontScale(4);
        stage.addActor(table);



    }

    /**This function runs every (frame???)*/
    public void update() {
        Interactions.updateKeys();

        // Set the screen to the gameplay screen
        if (Interactions.isJustPressed(InputKey.InputTypes.START_GAME)) {
            screenController.setScreen(ScreenID.GAME);
            ((GameScreen) screenController.getScreen(ScreenID.GAME)).startGame(5);
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

    @Override
    public void render(float delta) {

        this.update();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        sprite.draw(batch);
        batch.end();
        stage.draw();





    }


}
