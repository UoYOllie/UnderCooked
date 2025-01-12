package testing;

import shop.Gold;
import shop.ShopItem;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import players.Cook;
import players.Customer;
import reputation.RepPoints;
import food.FoodItem;
import interactions.InputKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import stations.*;

import java.util.ArrayList;

import static interactions.Interactions.keysPressed;
import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class GameplayTests {

    // The following 2 tests are for robustness. The first tests that servingStations are multi-use. The second tests that failing recipes work
    // All of them relate to the UR_GAMEPLAY requirement

    @Test
    // Relates to the FR_SERVE, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServesMultipleCustomers(){
        //First we test the servingStation is cleared after the customer interacts with a correct recipe. We will use this to make sure the customer is finished with the station and then test with a separate customer and different order
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Tomato Onion Salad";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customer);
        assertTrue(testStation.getServedDishStack().getStack().isEmpty(),"Error: ServingStation is not cleared after a customer takes the correct recipe");

        //Now test with separate customer
        Customer customer2 = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer2;
        customer2.request = "Plain Potato";
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customer2);
        assertTrue(testStation.getServedDishStack().getStack().isEmpty(),"Error: ServingStation cannot be used by more than one customer (when served one after the other)");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationDoesNotAcceptWrongRecipes(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Plain Burger";

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
        testStation.customerInteract(customer);
        assertFalse(testStation.getServedDishStack().getStack().isEmpty(),"Error: ServingStation is cleared after a customer rejects an incorrect recipe");
        assertFalse(customer.dishStack.getStack() == finalCustomersRecipe,"Error: Customer takes a recipe even if it is wrong");
        assertEquals(testStation.getServedDishStack().getStack(),finalCustomersRecipe,"Error: The servingStation is cleared even if a wrong recipe is served");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }


    // The following 19 tests check that serving a specific recipe will result in the player's food stack emptying and it being accepted by the customer
    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerOnionTomatoSalad(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Tomato Onion Salad";

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
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        // We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if it's correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerLettuceTomatoSalad(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Tomato Salad";

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
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerLettuceOnionSalad(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Onion Salad";

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
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerPlainBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Plain Burger";

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
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerLettuceBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Lettuce Burger";

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
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerOnionBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Onion Burger";

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
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerTomatoBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Tomato Burger";

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
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }
    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerLettuceTomatoBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Lettuce Tomato Burger";

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
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerLettuceOnionBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Lettuce Onion Burger";

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
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerTomatoOnionBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Tomato Onion Burger";

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
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerLettuceTomatoOnionBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Lettuce Tomato Onion Burger";

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
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerPlainPotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Plain Potato";

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
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerBeansPotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Beans Potato";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.beansCooked);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerColeslawPotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Coleslaw Potato";

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
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerCheesePotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Cheese Potato";

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
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerBeansCheesePotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Beans Cheese Potato";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.cheese);
        cook.foodStack.addStack(FoodItem.FoodID.beansCooked);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerBeansColeslawPotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Beans Coleslaw Potato";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.coleslaw);
        cook.foodStack.addStack(FoodItem.FoodID.beansCooked);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();

        Array<FoodItem.FoodID> finalCustomersExpectedRecipe = new Array<FoodItem.FoodID>();
        for (FoodItem.FoodID items: cook.dishStack.getStack()){
            finalCustomersExpectedRecipe.add(items);
        }

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerColeslawCheesePotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Coleslaw Cheese Potato";

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
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }
    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerBeansColeslawCheesePotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        assemblyStation.setID(Station.StationID.assembly);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Beans Coleslaw Cheese Potato";

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.coleslaw);
        cook.foodStack.addStack(FoodItem.FoodID.beansCooked);
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
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    @Test
    // Relates to the FR_SERVE_CHECK, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestServingStationServeCustomerPlainPizza(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        testStation.setTestFlag(1);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Plain Pizza";

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
        testStation.customerInteract(customer);

        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //We then test that the customer has taken the order. This will tell us if the game has registered the recipe as correct as the customer only picks it up if its correct
        assertEquals(customer.dishStack.getStack(),finalCustomersExpectedRecipe,"Error: Customer has got the wrong items when served the correct dish");

        testStation.setTestFlag(0);
        assertEquals(testStation.getTestFlag(),0,"Error: Serving Station's test flag is not returned to 0 after testing is finished");
    }

    // The following test checks that a shop item can be bought and the money will be deducted from the player's gold
    // Relates to the FR_SPEND_MONEY requirement
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

    // The following test checks that using the speed powerup correctly changes the selected cook's movement speed
    // Relates to the FR_POWER_UPS requirements
    @Test
    public void TestSpeedPowerUp(){
        Cook cook = new Cook(1500,1200,20,20);
        Float StartMovement = cook.movement_speed;
        Rectangle rectangle = new Rectangle((1500 * 1/8f), (1200*1/8f),20,20);
        SpeedPowerup speedPowerup = new SpeedPowerup(rectangle);
        ArrayList<Rectangle> testList = new ArrayList<>();
        speedPowerup.setSpeed(cook);
        assertEquals(StartMovement + 0.21f,cook.movement_speed,"ERROR: The speed power up isn't changing the default movement speed of the chef");
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        cook.userInput(testList);
        assertEquals(cook.getX(),(1500 * 1/8f) - (StartMovement + 0.21f));
    }

    // The following test checks that using more than one speed powerup increases the player's movement speed by the correct amount
    // Relates to the FR_POWER_UPS requirements
    @Test
    public void TestMultipleSpeedPowerUp(){
        Cook cook = new Cook(1500,1200,20,20);
        Float StartMovement = cook.movement_speed;
        Rectangle rectangle = new Rectangle((1500 * 1/8f), (1200*1/8f),20,20);
        SpeedPowerup speedPowerup = new SpeedPowerup(rectangle);
        ArrayList<Rectangle> testList = new ArrayList<>();
        speedPowerup.setSpeed(cook);
        assertEquals(StartMovement + 0.21f,cook.movement_speed,"ERROR: The speed power up isn't changing the default movement speed of the chef");
        speedPowerup.setSpeed(cook);
        assertEquals(StartMovement + 0.42f,cook.movement_speed,"ERROR: Using multiple speed powerups doesn't change the chef's movement speed to the correct value");
        keysPressed.clear();
        keysPressed.add(InputKey.InputTypes.COOK_LEFT);
        cook.userInput(testList);
        assertEquals(cook.getX(),(1500 * 1/8f) - (StartMovement + 0.42f));
    }

    // The following tests the Hypnotise power up, making sure it changes the customer's order
    // Relates to the FR_POWER_UPS requirement
    @Test
    public void TestCustomerHypnotise(){
        Customer customer = new Customer(1,2,3,4); //Each number is different in order to test getters easier
        customer.request = ("Plain Burger");
        customer.Hypnotise();
        assertFalse(customer.request.equals("Plain Burger"),"Error: Hypnotising a customer does not make them change their order");
        assertFalse(customer.request.isEmpty(),"Error: Hypnotising a customer empties there request constructor");
    }

    //The following tests another power up which forces the customer to wait longer. It ensures the wait time is set to the correct amount
    // Relates to the FR_POWER_UP requirement
    @Test
    public void TestCustomerHangOnYourFoodIsComing(){
        Customer customer = new Customer(1,2,3,4); //Each number is different in order to test getters easier
        customer.waittime = 10;
        customer.HangOnYourFoodIsComing();
        assertEquals(customer.waittime, 300,"Error: Using the \"wait longer \" power up does not set the customers wait time to 300");
    }

    // The following tests make sure the bluggus power up correctly changes the player into bluggus
    // Relates to the FR_POWER_UPS requirement
    @Test
    public void TestMakeIntoBluggus(){
        Cook cook = new Cook(1,2,3,4);
        cook.MakeIntoBluggus();
        assertEquals(cook.activateBluggus,true,"Error: Bluggus power up does not change you into bluggus");
    }

    @Test
    // This tests that items can be moved between two food stacks
    // Relates to the FR_STACK_DISPLAY requirement
    public void TestMoveStack(){
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
    //This test relates to the FR_REPUTATION_POINTS requirement
    @Test
    // Relates to the FR_REPUTATION_POINTS requirement
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
