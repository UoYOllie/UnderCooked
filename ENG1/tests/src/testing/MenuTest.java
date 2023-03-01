package testing;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import game.Boot;
import game.MenuScreen;
import game.ScreenController;
import org.junit.Test;
import org.junit.runner.RunWith;

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
        // keysPressed.add(Input.Keys.ENTER);

    }
}
