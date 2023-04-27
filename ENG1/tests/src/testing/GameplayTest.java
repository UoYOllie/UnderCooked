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
import org.junit.runner.RunWith;
import stations.*;

import java.util.ArrayList;

import static interactions.Interactions.keysPressed;
import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class GameplayTest {

    //The following 2 tests are for robustness. The first tests that servingStations are multi-use. The second tests that failing recipes work
    @Test
    public void TestServingStationServesMultipleCustomers(){
        //First we test the servingStation is cleared after the customer interacts with a correct recipe. We will use this to make sure the customer is finished with the station and then test with a separate customer and different order
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Tomato Onion Salad";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);
        assertTrue(testStation.getServedDishStack().getStack().isEmpty(),"Error: ServingStation is not cleared after a customer takes the correct recipe");

        //Now test with separate customer
        CustomerNew customerNew2 = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew2;
        customerNew2.request = "Plain Potato";
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew2);
        assertTrue(testStation.getServedDishStack().getStack().isEmpty(),"Error: ServingStation cannot be used by more than one customer (when served one after the other)");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    public void TestServingStationDoesNotAcceptWrongRecipes(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Plain Burger";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        Array<FoodItem.FoodID> finalCustomersRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);
        assertFalse(testStation.getServedDishStack().getStack().isEmpty(),"Error: ServingStation is cleared after a customer rejects an incorrect recipe");
        assertFalse(customerNew.dishStack.getStack() == finalCustomersRecipe,"Error: Customer takes a recipe even if it is wrong");
        assertEquals(testStation.getServedDishStack().getStack(),finalCustomersRecipe,"Error: The servingStation is cleared even if a wrong recipe is served");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_DISH_SERVE requirement
    public void TestServingStationServeCustomerOnionTomatoSalad(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Tomato Onion Salad";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    public void TestServingStationServeCustomerLettuceTomatoSalad(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Tomato Salad";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerLettuceOnionSalad(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Onion Salad";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerPlainBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
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
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeWrongOrder(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Plain Burger";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if it's correct
        assertNotEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the right items when served the incorrect dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerLettuceBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Lettuce Burger";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerOnionBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Onion Burger";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerTomatoBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Tomato Burger";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }
    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerLettuceTomatoBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Lettuce Tomato Burger";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerLettuceOnionBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Lettuce Onion Burger";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerTomatoOnionBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Tomato Onion Burger";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerLettuceTomatoOnionBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Lettuce Tomato Onion Burger";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerPlainPotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Plain Potato";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerBeansPotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Beans Potato";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bakedBeans);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerColeslawPotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Coleslaw Potato";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.coleslaw);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerCheesePotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Cheese Potato";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.cheese);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerBeansCheesePotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Beans Cheese Potato";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.cheese);
        cook.foodStack.addStack(FoodItem.FoodID.bakedBeans);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerBeansColeslawPotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Beans Coleslaw Potato";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.coleslaw);
        cook.foodStack.addStack(FoodItem.FoodID.bakedBeans);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerColeslawCheesePotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Coleslaw Cheese Potato";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.cheese);
        cook.foodStack.addStack(FoodItem.FoodID.coleslaw);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }
    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerBeansColeslawCheesePotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Beans Coleslaw Cheese Potato";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.coleslaw);
        cook.foodStack.addStack(FoodItem.FoodID.bakedBeans);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        cook.foodStack.addStack(FoodItem.FoodID.cheese);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerPlainPizza(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Plain Pizza";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.cheese);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoSauce);
        cook.foodStack.addStack(FoodItem.FoodID.doughCook);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerPepperoniPizza(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Pepperoni Pizza";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.pepperoni);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoSauce);
        cook.foodStack.addStack(FoodItem.FoodID.doughCook);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        cook.foodStack.addStack(FoodItem.FoodID.cheese);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE requirement
    public void TestServingStationServeCustomerOnionPizza(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Onion Pizza";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoSauce);
        cook.foodStack.addStack(FoodItem.FoodID.doughCook);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        cook.foodStack.addStack(FoodItem.FoodID.cheese);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customerNew.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }


    // Relates to the FR_SPEND MONEY requirement
    @Test
    public void TestBuyShopItem(){
        ShopItem shopItem = new ShopItem("testItem",100);
        assertTrue(shopItem.name == "testItem");
        assertTrue(shopItem.cost == 100);
        Gold gold = new Gold();
        gold.setBalance(101);
        shopItem.buy(gold);
        assertTrue(gold.Balance == 1);
    }

    // Relates to the FR_POWER_UPS requirements
    @Test
    public void TestSpeedPowerUp(){
        Cook cook = new Cook(1500,1200,20,20);
        Float StartMovement = cook.movement_speed;
        Rectangle rectangle = new Rectangle((1500 * 1/8f), (1200*1/8f),20,20);
        SpeedPowerup speedPowerup = new SpeedPowerup(rectangle);
        ArrayList<Rectangle> testList = new ArrayList<>();
        speedPowerup.setSpeed(cook);
        assertEquals(StartMovement + 0.42f,cook.movement_speed,"ERROR: The speed power up isn't changing the default movement speed of the chef");
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        cook.userInput(testList);
        assertEquals(cook.getX(),(1500 * 1/8f) - (StartMovement + 0.42f));
    }

    // Relates to the FR_POWER_UPS requirements
    @Test
    public void TestMultipleSpeedPowerUps(){
        Cook cook = new Cook(1500,1200,20,20);
        Float StartMovement = cook.movement_speed;
        Rectangle rectangle = new Rectangle((1500 * 1/8f), (1200*1/8f),20,20);
        SpeedPowerup speedPowerup = new SpeedPowerup(rectangle);
        ArrayList<Rectangle> testList = new ArrayList<>();
        speedPowerup.setSpeed(cook);
        assertEquals(StartMovement + 0.42f,cook.movement_speed,"ERROR: The speed power up isn't changing the default movement speed of the chef");
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        cook.userInput(testList);
        assertEquals(cook.getX(),(1500 * 1/8f) - (StartMovement + 0.42f),"ERROR: movement with the speed power up is not correct");
        cook.x = (1500 * 1/8f);
        speedPowerup.setSpeed(cook);
        assertEquals(StartMovement + (0.42f * 2),cook.movement_speed,"ERROR: The speed power up isn't changing the default movement speed of the chef");
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        cook.userInput(testList);
        assertEquals(cook.getX(),(1500 * 1/8f) - (StartMovement + (0.42f * 2)),"ERROR: movement with two speed power up is not correct");
    }

    //The following tests the Hypnotise power up
    @Test
    public void testCustomerNewHypnotise(){
        CustomerNew customerNew = new CustomerNew(1,2,3,4); //Each number is different in order to test getters easier
        customerNew.request = ("Plain Burger");
        customerNew.Hypnotise();
        assertFalse(customerNew.request.equals("Plain Burger"),"Error: Hypnotising a customer does not make them change their order");
        assertFalse(customerNew.request.isEmpty(),"Error: Hypnotising a customer empties there request constructor");
    }

    //The following tests another power up which forces the customer to wait longer
    @Test
    public void testCustomerHangOnYourFoodIsComing(){
        CustomerNew customerNew = new CustomerNew(1,2,3,4); //Each number is different in order to test getters easier
        customerNew.waittime = 10;
        customerNew.HangOnYourFoodIsComing();
        assertEquals(customerNew.waittime, 300,"Error: Using the \"wait longer \" power up does not set the customers wait time to 300");
    }

    //This tests the logic behind a customer leaving when being served
    @Test
    public void testCustomerNewServedCustomerLeave(){
        //Checking the if -> x > destination x
        CustomerNew customerNew = new CustomerNew(1,2,3,4);
        customerNew.destination.x = 0;
        customerNew.servedCustomerLeaves();
        assertEquals(customerNew.x, 1 - Constants.UnitScale,"Error: CustomerNew's servedCustomerLeaves is not updating the x position correctly");

        //Checking the else -> x <= destination x
        customerNew.destination.x = 2;
        customerNew.setCustomerStatus(0);
        customerNew.servedCustomerLeaves();
        assertEquals(customerNew.getCustomerStatus(), 1,"Error: CustomerNew's customerStatus is not being incremented correctly if x <= destination x");
    }

    //The following tests make sure the bluggus power up
    @Test
    public void testMakeIntoBluggus(){
        Cook cook = new Cook(1,2,3,4);
        cook.MakeIntoBluggus();
        assertEquals(cook.activateBluggus,true,"Error: Bluggus power up does not change you into bluggus");
    }

    @Test
    public void testMoveStack(){
        //Testing moving foodStack2 moving items to foodStack1
        Cook cook = new Cook(1,2,3,4);
        cook.MakeIntoBluggus();
        cook.foodStack2.addStack(FoodItem.FoodID.bakedBeans);
        cook.moveStacks();
        assertEquals(cook.foodStack.peekStack(), FoodItem.FoodID.bakedBeans,"Error: moving items from foodStack2 to foodStack1 does not work");

        //Testing moving foodStack1 items to foodStack2
        cook.foodStack.addStack(FoodItem.FoodID.cheese);
        cook.moveStacks();
        assertEquals(cook.foodStack2.peekStack(), FoodItem.FoodID.cheese,"Error: moving items from foodStack1 to foodStack2 does not work");
    }

    //The following tests the logic behind gaining and losing reputation
    @Test
    public void TestRepPoints(){
        //First, I will test that RepPoints can be decremented
        RepPoints repPoints = new RepPoints();
        repPoints.Negative();
        assertEquals(repPoints.getPoints(),2,"Error: you can not decrement the reputation points using Negative");

        //Then,I will check you can decrement multiple times and for robustness
        for (int i = 0; i < 2; i++){
            repPoints.Negative();
        }
        assertEquals(repPoints.getPoints(),0,"Error: Using Negative on RepPoints only decrements the reputation once");

        //Now I will check gaining points works
        repPoints.Positive();
        assertEquals(repPoints.getPoints(),1,"Error: Positive does not increase the reputation points");

        //RepPoints are meant to be capped at a max of 5 points, so we will test this works
        for (int i = 0; i < 10; i++){
            repPoints.Positive();
        }
        assertEquals(repPoints.getPoints(),5,"Error: Points don't get capped at 5 maximum points");
    }
}
