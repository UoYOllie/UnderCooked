package testing;

import com.badlogic.gdx.Gdx;
import cooks.Cook;
import food.FoodItem;
import interactions.InputKey;
import interactions.Interactions;
import org.junit.Test;
import org.junit.runner.RunWith;
import stations.PreparationStation;
import stations.Station;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class InteractionTest {

    @Test
    public void TestLettuceCut(){
        Rectangle rectangle = new Rectangle();
        PreparationStation testStation = new PreparationStation(rectangle);
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuce);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);

        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.lettuceChop);
    }
}
