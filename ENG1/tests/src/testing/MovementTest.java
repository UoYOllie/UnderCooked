package testing;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import cooks.Cook;
import game.Boot;
import game.GameScreen;
import game.ScreenController;
import interactions.InputKey;
import org.junit.Test;
import org.junit.runner.RunWith;


import static interactions.Interactions.keysPressed;
import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class MovementTest {

    private Boot boot = Boot.getInstance();
    @Test
    public void TestChefMoveLeft(){
        OrthographicCamera camera = new OrthographicCamera();
        ScreenController screenController = new ScreenController(boot, camera);
        screenController.setScreen(ScreenController.ScreenID.GAME);
        GameScreen gameScreentest = new GameScreen(screenController,camera);
        Cook cook = new Cook(1500, 1200, 20, 20);
        gameScreentest.addCook(cook);
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        assertTrue(cook.getX()==1499);
    }

    public void TestChefMoveUp(){
        OrthographicCamera camera = new OrthographicCamera();
        ScreenController screenController = new ScreenController(boot, camera);
        screenController.setScreen(ScreenController.ScreenID.GAME);
        GameScreen gameScreentest = new GameScreen(screenController,camera);
        Cook cook = new Cook(1500, 1200, 20, 20);
        gameScreentest.addCook(cook);
        keysPressed.add(InputKey.InputTypes.COOK_UP);
        assertTrue(cook.getY()==1201);
    }

    public void TestChefMoveUpLeft(){
        OrthographicCamera camera = new OrthographicCamera();
        ScreenController screenController = new ScreenController(boot, camera);
        screenController.setScreen(ScreenController.ScreenID.GAME);
        GameScreen gameScreentest = new GameScreen(screenController,camera);
        Cook cook = new Cook(1500, 1200, 20, 20);
        gameScreentest.addCook(cook);
        keysPressed.add(InputKey.InputTypes.COOK_UP);
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        assertTrue(cook.getX()==1499);
        assertTrue(cook.getY()==1201);
    }

    public void TestChefMoveUpRight(){
        OrthographicCamera camera = new OrthographicCamera();
        ScreenController screenController = new ScreenController(boot, camera);
        screenController.setScreen(ScreenController.ScreenID.GAME);
        GameScreen gameScreentest = new GameScreen(screenController,camera);
        Cook cook = new Cook(1500, 1200, 20, 20);
        gameScreentest.addCook(cook);
        keysPressed.add(InputKey.InputTypes.COOK_UP);
        keysPressed.add(InputKey.InputTypes.COOK_RIGHT);
        assertTrue(cook.getX()==1501);
        assertTrue(cook.getY()==1201);
    }

    public void TestChefMoveDownLeft(){
        OrthographicCamera camera = new OrthographicCamera();
        ScreenController screenController = new ScreenController(boot, camera);
        screenController.setScreen(ScreenController.ScreenID.GAME);
        GameScreen gameScreentest = new GameScreen(screenController,camera);
        Cook cook = new Cook(1500, 1200, 20, 20);
        gameScreentest.addCook(cook);
        keysPressed.add(InputKey.InputTypes.COOK_DOWN);
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        assertTrue(cook.getX()==1499);
        assertTrue(cook.getY()==1199);
    }

    public void TestChefMoveDownRight(){
        OrthographicCamera camera = new OrthographicCamera();
        ScreenController screenController = new ScreenController(boot, camera);
        screenController.setScreen(ScreenController.ScreenID.GAME);
        GameScreen gameScreentest = new GameScreen(screenController,camera);
        Cook cook = new Cook(1500, 1200, 20, 20);
        gameScreentest.addCook(cook);
        keysPressed.add(InputKey.InputTypes.COOK_DOWN);
        keysPressed.add(InputKey.InputTypes.COOK_RIGHT);
        assertTrue(cook.getX()==1501);
        assertTrue(cook.getY()==1199);
    }

    public void TestChefMoveDown(){
        OrthographicCamera camera = new OrthographicCamera();
        ScreenController screenController = new ScreenController(boot, camera);
        screenController.setScreen(ScreenController.ScreenID.GAME);
        GameScreen gameScreentest = new GameScreen(screenController,camera);
        Cook cook = new Cook(1500, 1200, 20, 20);
        gameScreentest.addCook(cook);
        keysPressed.add(InputKey.InputTypes.COOK_DOWN);
        assertTrue(cook.getY()==1199);
    }

    public void TestChefMoveRight(){
        OrthographicCamera camera = new OrthographicCamera();
        ScreenController screenController = new ScreenController(boot, camera);
        screenController.setScreen(ScreenController.ScreenID.GAME);
        GameScreen gameScreentest = new GameScreen(screenController,camera);
        Cook cook = new Cook(1500, 1200, 20, 20);
        gameScreentest.addCook(cook);
        keysPressed.add(InputKey.InputTypes.COOK_RIGHT);
        assertTrue(cook.getX()==1501);
    }

    //TEST SWITCH CHEFS
}
