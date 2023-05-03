package testing;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import players.Cook;
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
public class MovementTests {

    private Boot boot = Boot.getInstance();
    @Test
    // Relates to the FR_MOVE and UR_CHEF requirements
    public void TestChefMoveLeft(){
        //This test works by spawning a chef at the given coordinates and seeing if he moves left the expected amount when the player presses left
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
        //This test works by spawning a chef at the given coordinates and seeing if he moves up the expected amount when the player presses up
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
        //This test works by spawning a chef at the given coordinates and seeing if he moves up and left the expected amount when the player presses up and left
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
        //This test works by spawning a chef at the given coordinates and seeing if he moves up and right the expected amount when the player presses up and right
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
        //This test works by spawning a chef at the given coordinates and seeing if he moves down and left the expected amount when the player presses down and left
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
        //This test works by spawning a chef at the given coordinates and seeing if he moves down and right the expected amount when the player presses down and right
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
        //This test works by spawning a chef at the given coordinates and seeing if he moves right and up the expected amount when the player presses right and up
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
        //This test works by spawning a chef at the given coordinates and seeing if he moves right and down the expected amount when the player presses right and down
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
        //This test works by spawning a chef at the given coordinates and seeing if he moves left and up the expected amount when the player presses left and up
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
        //This test works by spawning a chef at the given coordinates and seeing if he moves left and down the expected amount when the player presses left and down
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
        //This test works by spawning a chef at the given coordinates and seeing if he moves down the expected amount when the player presses down
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
        //This test works by spawning a chef at the given coordinates and seeing if he moves right the expected amount when the player presses right
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
        //This test makes multiple chefs and puts them under the same controller, then presses the button allocated for swapping. We test that controls have swapped to the next chef in the controller
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
