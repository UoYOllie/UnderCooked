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
    public void TestChefMoveLeftCollision(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        Rectangle rectangle = new Rectangle((1500 * 1/8f) - 20,1200 * 1/8f,20,20);
        testList.add(rectangle);
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f);
        assertTrue(cook.getX()==final_move);
    }

    @Test
    public void TestChefMoveRightCollision(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        Rectangle rectangle = new Rectangle((1500 * 1/8f) + 20,1200 * 1/8f,20,20);
        testList.add(rectangle);
        keysPressed.add(InputKey.InputTypes.COOK_RIGHT);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f);
        assertTrue(cook.getX()==final_move);
    }

    @Test
    public void TestChefMoveUpCollision(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f) + 20,20,20);
        testList.add(rectangle);
        keysPressed.add(InputKey.InputTypes.COOK_UP);
        cook.userInput(testList);
        float final_move = (1200 * 1/8f);
        assertTrue(cook.getY()==final_move);
    }

    @Test
    public void TestChefMoveDownCollision(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f) - 20,20,20);
        testList.add(rectangle);
        keysPressed.add(InputKey.InputTypes.COOK_DOWN);
        cook.userInput(testList);
        float final_move = (1200 * 1/8f);
        assertTrue(cook.getY()==final_move);
    }


    /**
    @Test
    public void TestChefMoveUpLeftCollision(){
        Cook cook = new Cook(1500, 1200, 20, 20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        Rectangle rectangle = new Rectangle((1500 * 1/8f) - 20,(1200 * 1/8f) + 20,20,20);
        testList.add(rectangle);
        keysPressed.add(InputKey.InputTypes.COOK_UP);
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        cook.userInput(testList);
        float final_move = (1500 * 1/8f) + 20;
        float final_move2 = (1200 * 1/8f);
        assertTrue(cook.getX()==final_move);
        assertTrue(cook.getY()==final_move2);
    }
    **/

}
