package testing;

import Shop.Gold;
import Shop.ShopItem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import cooks.Cook;
import cooks.CustomerNew;
import customers.RepPoints;
import food.DishStack;
import food.FoodItem;
import food.Recipe;
import helper.Constants;
import interactions.InputKey;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import stations.ServingStationNew;
import stations.SpeedPowerup;
import stations.Station;

import java.util.ArrayList;

import static interactions.Interactions.keysPressed;
import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class GameplayTest {

    //The following 2 tests are for robustness. The first tests that servingStations are multi-use. The second tests that failing recipes work

    public void TestServingStationServeCustomerPlainBurger() {
        Rectangle rectangle = new Rectangle((1500 * 1 / 8f), (1200 * 1 / 8f), 20, 20);
        ServingStationNew testStation = new ServingStationNew(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        CustomerNew customerNew = new CustomerNew(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Plain Burger";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items : cook.dishStack.getStack()) {
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(), finalCustomersExpectedRecipe, "Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(), 0, "Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }
}

