package testing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import game.*;
import interactions.InputKey;
import org.junit.Test;
import org.junit.runner.RunWith;

import static interactions.Interactions.keysPressed;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class MenuTests {
    private Boot boot = Boot.getInstance();

    // The following tests relate to UR_MENU, they don't work due to limitations in the headless backend so have been tested manually
    @Test
    // Relates to the UR_MENU requirement
    public void testStartEndlessGame() {
        //This test doesn't work, but the idea behind it was to set the screen to the main menu, then press the button to select endless mode and see if the screen ID was moved to the difficulty screen
        Gdx.gl20 = Gdx.gl;
        OrthographicCamera camera = new OrthographicCamera();
        ScreenController screenController = new ScreenController(boot, camera);
        screenController.setScreen(ScreenController.ScreenID.MENU);
        keysPressed.add(InputKey.InputTypes.START_ENDLESS);
        assertEquals(screenController.getScreen(ScreenController.ScreenID.DIFFICULTY), boot.getScreen());
    }

    @Test
    // Relates to the UR_MENU requirement
    public void testCreditsMenu() {
        //This test doesn't work, but the idea behind it was to set the screen to the main menu, then press the button to select credits mode and see if the screen ID was moved to the credits screen
        Gdx.gl20 = Gdx.gl;
        OrthographicCamera camera = new OrthographicCamera();
        ScreenController screenController = new ScreenController(boot, camera);
        screenController.setScreen(ScreenController.ScreenID.MENU);
        keysPressed.add(InputKey.InputTypes.CREDITS);
        assertEquals(screenController.getScreen(ScreenController.ScreenID.CREDITS), boot.getScreen());

    }
}
