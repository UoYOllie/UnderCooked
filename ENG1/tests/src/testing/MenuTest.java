package testing;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import game.*;
import interactions.InputKey;
import org.junit.Test;
import org.junit.runner.RunWith;

import static interactions.Interactions.keysPressed;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class MenuTest {
    private Boot boot;

    // main menu
    @Test
    public void testStartGame() {
        OrthographicCamera camera = new OrthographicCamera();
        ScreenController screenController = new ScreenController(boot, camera);
        MenuScreen menuScreen = new MenuScreen(screenController, camera);

        screenController.setScreen(ScreenController.ScreenID.MENU);
        keysPressed.add(InputKey.InputTypes.START_GAME);
        assertEquals(InputKey.InputTypes.START_GAME, );

    }

    public void testCreditsMenu() {
        OrthographicCamera camera = new OrthographicCamera();
        ScreenController screenController = new ScreenController(boot, camera);
        screenController.setScreen(ScreenController.ScreenID.MENU);
        keysPressed.add(InputKey.InputTypes.CREDITS);
        assertEquals(screenController.getScreen(ScreenController.ScreenID.CREDITS), CreditsScreen.prevScreenID);

    }
}
