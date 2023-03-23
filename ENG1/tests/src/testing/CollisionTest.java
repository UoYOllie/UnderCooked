package testing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import cooks.Cook;
import game.Boot;
import interactions.InputKey;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static interactions.Interactions.keysPressed;
import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class CollisionTest {

    private Boot boot = Boot.getInstance();
    @Test
    // Relates to the FR_CHEF_COLLISIONS requirement
    public void TestChefMoveLeftCollision(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        Rectangle rectangle = new Rectangle((1500 * 1/8f) - 20,1200 * 1/8f,20,20);
        testList.add(rectangle);
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f);
        assertTrue(cook.getX()==final_move,"Error: moving left into a collidable object does not work");
    }

    @Test
    // Relates to the FR_CHEF_COLLISIONS requirement
    public void TestChefMoveRightCollision(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        Rectangle rectangle = new Rectangle((1500 * 1/8f) + 20,1200 * 1/8f,20,20);
        testList.add(rectangle);
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_RIGHT);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f);
        assertTrue(cook.getX()==final_move,"Error: moving right into a collidable object does not work");
    }

    @Test
    // Relates to the FR_CHEF_COLLISIONS requirement
    public void TestChefMoveUpCollision(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f) + 20,20,20);
        testList.add(rectangle);
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_UP);
        cook.userInput(testList);
        float final_move = (1200 * 1/8f);
        assertTrue(cook.getY()==final_move,"Error: moving up into a collidable object does not work properly");
    }

    @Test
    // Relates to the FR_CHEF_COLLISIONS requirement
    public void TestChefMoveDownCollision(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f) - 20,20,20);
        testList.add(rectangle);
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_DOWN);
        cook.userInput(testList);
        float final_move = (1200 * 1/8f);
        assertTrue(cook.getY()==final_move,"Error: moving down into a collidable object does not work properly");
    }



    @Test
    // Relates to the FR_CHEF_COLLISIONS requirement
    public void TestChefMoveSlideLeftUp(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        Rectangle rectangle = new Rectangle((1500 * 1/8f) - 20,(1200 * 1/8f),20,20);
        testList.add(rectangle);
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        keysPressed.add(InputKey.InputTypes.COOK_UP);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f);
        float final_move2 = (1200 * 1/8f) + 1;
        assertTrue(cook.getX()==final_move,"Error: moving left into a collidable object does not work properly");
        assertTrue(cook.getY()==final_move2,"Error: movement up while up against a wall of the left isn't working");
    }

    @Test
    // Relates to the FR_CHEF_COLLISIONS requirement
    public void TestChefMoveSlideLeftDown(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        Rectangle rectangle = new Rectangle((1500 * 1/8f) - 20,(1200 * 1/8f),20,20);
        testList.add(rectangle);
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        keysPressed.add(InputKey.InputTypes.COOK_DOWN);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f);
        float final_move2 = (1200 * 1/8f) - 1;
        assertTrue(cook.getX()==final_move,"Error: moving left into a collidable object does not work properly");
        assertTrue(cook.getY()==final_move2,"Error: moving down while up against a collidable wall on the left isn't working");
    }

    @Test
    // Relates to the FR_CHEF_COLLISIONS requirement
    public void TestChefMoveSlideRightUp(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        Rectangle rectangle = new Rectangle((1500 * 1/8f) + 21,(1200 * 1/8f),20,20);
        testList.add(rectangle);
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_RIGHT);
        keysPressed.add(InputKey.InputTypes.COOK_UP);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f) + 1;
        float final_move2 = (1200 * 1/8f) + 1;
        assertTrue(cook.getX()==final_move,"Error: moving right into a collidable object does not work properly");
        assertTrue(cook.getY()==final_move2,"Error: moving up while against a collidable wall on the right isn't working");
    }

    @Test
    // Relates to the FR_CHEF_COLLISIONS requirement
    public void TestChefMoveSlideRightDown(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        Rectangle rectangle = new Rectangle((1500 * 1/8f) + 21,(1200 * 1/8f),20,20);
        testList.add(rectangle);
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_RIGHT);
        keysPressed.add(InputKey.InputTypes.COOK_DOWN);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f) + 1;
        float final_move2 = (1200 * 1/8f) - 1;
        assertTrue(cook.getX()==final_move,"Error: moving right into a collidable object does not work properly");
        assertTrue(cook.getY()==final_move2,"Error: Moving down while up against a collidable wall on the right isn't working");
    }

    @Test
    // Relates to the FR_CHEF_COLLISIONS requirement
    public void TestChefMoveSlideUpRight(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f) + 21,20,20);
        testList.add(rectangle);
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_UP);
        keysPressed.add(InputKey.InputTypes.COOK_RIGHT);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f) + 1;
        float final_move2 = (1200 * 1/8f) + 1;
        assertTrue(cook.getX()==final_move,"Error: moving up into a collidable object does not work properly");
        assertTrue(cook.getY()==final_move2,"Error: Moving right while against a collidable wall above the player isn't working");
    }

    @Test
    // Relates to the FR_CHEF_COLLISIONS requirement
    public void TestChefMoveSlideUpLeft(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f) + 21,20,20);
        testList.add(rectangle);
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_UP);
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f) - 1;
        float final_move2 = (1200 * 1/8f) + 1;
        assertTrue(cook.getX()==final_move,"Error: moving up into a collidable object does not work properly");
        assertTrue(cook.getY()==final_move2,"Error: Moving left whilst against a wall above the player isn't working");
    }

    @Test
    // Relates to the FR_CHEF_COLLISIONS requirement
    public void TestChefMoveSlideDownRight(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f) - 21,20,20);
        testList.add(rectangle);
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_DOWN);
        keysPressed.add(InputKey.InputTypes.COOK_RIGHT);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f) + 1;
        float final_move2 = (1200 * 1/8f) - 1;
        assertTrue(cook.getX()==final_move,"Error: moving down into a collidable object does not work properly");
        assertTrue(cook.getY()==final_move2,"Error: moving right whilst against a wall below the player isn't working");
    }

    @Test
    // Relates to the FR_CHEF_COLLISIONS requirement
    public void TestChefMoveSlideDownLeft(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f) - 21,20,20);
        testList.add(rectangle);
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_DOWN);
        keysPressed.add(InputKey.InputTypes.COOK_RIGHT);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f) - 1;
        float final_move2 = (1200 * 1/8f) - 1;
        assertTrue(cook.getX()==final_move,"Error, moving down into a collidable object does not work properly");
        assertTrue(cook.getY()==final_move2,"Error, moving left whilst against a wall below the player isn't working");
    }
}
