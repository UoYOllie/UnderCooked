package testing;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import cooks.Cook;
import game.Boot;
import game.GameScreen;
import game.ScreenController;
import interactions.InputKey;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.ArrayList;

import static interactions.Interactions.keysPressed;
import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class MovementTest {

    private Boot boot = Boot.getInstance();
    @Test
    // Relates to the FR_MOVE and UR_CHEF requirements
    public void TestChefMoveLeft(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f) - cook.movement_speed;
        assertTrue(cook.getX()==final_move,"Error: moving left does not work properly");
    }

    @Test
    // Relates to the FR_MOVE and UR_CHEF requirements
    public void TestChefMoveUp() {
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_UP);
        cook.userInput(testList);
        float final_move = (1200 * 1 / 8f) + cook.movement_speed;
        assertTrue(cook.getY() == final_move, "Error: moving up does not work properly");
    }
    @Test
    // Relates to the FR_MOVE and UR_CHEF requirements
    public void TestChefMoveUpLeft(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_UP);
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f) - cook.movement_speed;
        assertTrue(cook.getX()==final_move,"Error:Moving up then left has cause the x coordinate of the player to be wrong");
        final_move = (1200 * 1/8f) + cook.movement_speed;
        assertTrue(cook.getY()==final_move,"Error:Moving up then left has cause the y coordinate of the player to be wrong");
    }

    @Test
    // Relates to the FR_MOVE and UR_CHEF requirements
    public void TestChefMoveUpRight(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_UP);
        keysPressed.add(InputKey.InputTypes.COOK_RIGHT);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f) + cook.movement_speed;
        assertTrue(cook.getX()==final_move,"Error:Moving up then right has cause the x coordinate of the player to be wrong");
        final_move = (1200 * 1/8f) + cook.movement_speed;
        assertTrue(cook.getY()==final_move,"Error:Moving up then left has cause the y coordinate of the player to be wrong");
    }

    @Test
    // Relates to the FR_MOVE and UR_CHEF requirements
    public void TestChefMoveDownLeft(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_DOWN);
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f) - cook.movement_speed;
        assertTrue(cook.getX()==final_move,"Error:Moving down then left has cause the x coordinate of the player to be wrong");
        final_move = (1200 * 1/8f) - cook.movement_speed;
        assertTrue(cook.getY()==final_move,"Error:Moving up then left has cause the y coordinate of the player to be wrong");
    }

    @Test
    // Relates to the FR_MOVE and UR_CHEF requirements
    public void TestChefMoveDownRight(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_DOWN);
        keysPressed.add(InputKey.InputTypes.COOK_RIGHT);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f) + cook.movement_speed;
        assertTrue(cook.getX()==final_move,"Error:Moving down then right has cause the x coordinate of the player to be wrong");
        final_move = (1200 * 1/8f) - cook.movement_speed;
        assertTrue(cook.getY()==final_move,"Error:Moving down then right has cause the y coordinate of the player to be wrong");
    }

    @Test
    // Relates to the FR_MOVE and UR_CHEF requirements
    public void TestChefMoveRightUp(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_RIGHT);
        keysPressed.add(InputKey.InputTypes.COOK_UP);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f) + cook.movement_speed;
        assertTrue(cook.getX()==final_move,"Error:Moving right then up has cause the x coordinate of the player to be wrong");
        final_move = (1200 * 1/8f) + cook.movement_speed;
        assertTrue(cook.getY()==final_move,"Error:Moving right then up has cause the y coordinate of the player to be wrong");
    }

    @Test
    // Relates to the FR_MOVE and UR_CHEF requirements
    public void TestChefMoveRightDown(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_RIGHT);
        keysPressed.add(InputKey.InputTypes.COOK_DOWN);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f) + cook.movement_speed;
        assertTrue(cook.getX()==final_move,"Error:Moving right then down has cause the x coordinate of the player to be wrong");
        final_move = (1200 * 1/8f) - cook.movement_speed;
        assertTrue(cook.getY()==final_move,"Error:Moving right then down has cause the y coordinate of the player to be wrong");
    }

    @Test
    // Relates to the FR_MOVE and UR_CHEF requirements
    public void TestChefMoveLeftUp(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        keysPressed.add(InputKey.InputTypes.COOK_UP);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f) - cook.movement_speed;
        assertTrue(cook.getX()==final_move,"Error:Moving left then up has cause the x coordinate of the player to be wrong");
        final_move = (1200 * 1/8f) + cook.movement_speed;
        assertTrue(cook.getY()==final_move,"Error:Moving left then up has cause the y coordinate of the player to be wrong");
    }

    @Test
    // Relates to the FR_MOVE and UR_CHEF requirements
    public void TestChefMoveLeftDown(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        keysPressed.add(InputKey.InputTypes.COOK_DOWN);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f) - cook.movement_speed;
        assertTrue(cook.getX()==final_move,"Error:Moving left then down has cause the x coordinate of the player to be wrong");
        final_move = (1200 * 1/8f) - cook.movement_speed;
        assertTrue(cook.getY()==final_move,"Error:Moving left then down has cause the y coordinate of the player to be wrong");
    }

    @Test
    // Relates to the FR_MOVE and UR_CHEF requirements
    public void TestChefMoveDown(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_DOWN);
        cook.userInput(testList);
        float final_move = (1200 * 1/8f) - cook.movement_speed;
        assertTrue(cook.getY()==final_move,("Error: moving down does not work properly"));
    }

    @Test
    // Relates to the FR_MOVE and UR_CHEF requirements
    public void TestChefMoveRight(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_RIGHT);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f) + cook.movement_speed;
        assertTrue(cook.getX()==final_move,("Error: moving right does not work properly"));
    }

    @Test
    // Relates to the FR_SWITCH_CHEF and UR_CHEF requirements
    public void TestChefSwitch(){
        Gdx.gl20 = Gdx.gl;
        OrthographicCamera camera = new OrthographicCamera();
        ScreenController screenController = new ScreenController(boot,camera);
        Cook cook = new Cook(1500,1200,20,20);
        Cook cook2 = new Cook(1400,1200,20,20);
        screenController.setScreen(ScreenController.ScreenID.GAME);
        GameScreen gameScreen = new GameScreen(screenController,camera);
        gameScreen.addCook(cook);
        gameScreen.addCook(cook2);
        gameScreen.setCook(0);
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_SWAP);
        gameScreen.update(0);
        assertTrue(gameScreen.cooks.pop() == cook2);
    }
}
