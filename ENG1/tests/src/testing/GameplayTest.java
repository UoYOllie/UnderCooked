package testing;

import Shop.Gold;
import Shop.ShopItem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import cooks.Cook;
import cooks.CustomerNew;
import customers.Customer;
import customers.CustomerController;
import food.DishStack;
import food.FoodItem;
import food.Recipe;
import helper.Constants;
import interactions.InputKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import stations.ServingStation;
import stations.ServingStationNew;
import stations.SpeedPowerup;
import stations.Station;

import java.util.ArrayList;

import static interactions.Interactions.keysPressed;
import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class GameplayTest {

    /**
    @Test
    public void TestServingStationCanHaveCustomer(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStationNew testStation = new ServingStationNew(rectangle);
        CustomerNew customer = new CustomerNew(1,1,1,1);
        customer.request = "Onion Tomato Salad";
        testStation.customer = customer;
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        assertTrue(testStation.getCustomer() == customer,"Error: ServingStation can no longer have a customer assigned to them ");
        assertFalse(testStation.hasCustomer(),"Error: ServingStation can no longer have a customer assigned to them ");
    }
    **/


    @Test
    // Relates to the FR_DISH_SERVE requirement
    public void TestServingStationServeCustomerOnionTomatoSalad(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStationNew testStation = new ServingStationNew(rectangle);
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        customer.request = "Onion Tomato Salad";
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());
        cook.foodStack.clearStack();
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        assertFalse(testStation.customer != null, "Error: The serving station does not get rid of the customer after they are served");
    }

    /*
    @Test
    // Relates to the FR_DISH_SERVE requirement
    public void TestServingStationServeCustomerLettuceTomatoSalad(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        CustomerController customerController = new CustomerController();
        customerController.testFlag = 1;
        customerController.customers.add(customer);
        customer.request = "Lettuce Tomato Salad";
        testStation.customerController = customerController;
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        testStation.interact(cook, InputKey.InputTypes.USE);
        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        assertFalse(testStation.customer != null, "Error: The serving station does not get rid of the customer after they are served");
        testStation.testFlag = 0;
        customerController.testFlag = 0;
    }

    @Test
    // Relates to the FR_DISH_SERVE requirement
    public void TestServingStationServeCustomerLettuceOnionSalad(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        CustomerController customerController = new CustomerController();
        customerController.testFlag = 1;
        customerController.customers.add(customer);
        customer.request = "Lettuce Onion Salad";
        testStation.customerController = customerController;
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        testStation.interact(cook, InputKey.InputTypes.USE);
        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        assertFalse(testStation.customer != null, "Error: The serving station does not get rid of the customer after they are served");
        testStation.testFlag = 0;
        customerController.testFlag = 0;
    }

    @Test
    // Relates to the FR_DISH_SERVE requirement
    public void TestServingStationServeCustomerPlainBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        CustomerController customerController = new CustomerController();
        customerController.testFlag = 1;
        customerController.customers.add(customer);
        customer.request = "Plain Burger";
        testStation.customerController = customerController;
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bottomBun);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.topBun);
        assertTrue(Recipe.matchesRecipe(cook.foodStack,customer.getRequestName()),"Error: correct recipes are not being accepted due to Recipe.matchesRecipe");
        testStation.interact(cook, InputKey.InputTypes.USE);
        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        assertFalse(testStation.customer != null, "Error: The serving station does not get rid of the customer after they are served");
        testStation.testFlag = 0;
        customerController.testFlag = 0;
    }

    @Test
    // Relates to the FR_DISH_SERVE requirement
    public void TestServingStationServeWrongOrder(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        CustomerController customerController = new CustomerController();
        customerController.testFlag = 1;
        customerController.customers.add(customer);
        customer.request = "Onion Burger";
        testStation.customerController = customerController;
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bottomBun);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.topBun);
        assertFalse(Recipe.matchesRecipe(cook.foodStack,customer.getRequestName()),"Error: Recipe is saying it is equal to completely different recipe");
        testStation.interact(cook, InputKey.InputTypes.USE);
        assertFalse(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        assertTrue(testStation.customer != null, "Error: The serving station is getting rid of customers even if a wrong order is sent");
        testStation.testFlag = 0;
        customerController.testFlag = 0;
    }

    @Test
    // Relates to the FR_DISH_SERVE requirement
    public void TestServingStationServeCustomerLettuceBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        CustomerController customerController = new CustomerController();
        customerController.testFlag = 1;
        customerController.customers.add(customer);
        customer.request = "Lettuce Burger";
        testStation.customerController = customerController;
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bottomBun);
        cook.foodStack.addStack(FoodItem.FoodID.topBun);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        assertTrue(Recipe.matchesRecipe(cook.foodStack,customer.getRequestName()),"Error: correct recipes are not being accepted due to Recipe.matchesRecipe");
        testStation.interact(cook, InputKey.InputTypes.USE);
        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        assertFalse(testStation.customer != null, "Error: The serving station does not get rid of the customer after they are served");
        testStation.testFlag = 0;
        customerController.testFlag = 0;
    }

    @Test
    // Relates to the FR_DISH_SERVE requirement
    public void TestServingStationServeCustomerOnionBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        CustomerController customerController = new CustomerController();
        customerController.testFlag = 1;
        customerController.customers.add(customer);
        customer.request = "Onion Burger";
        testStation.customerController = customerController;
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bottomBun);
        cook.foodStack.addStack(FoodItem.FoodID.topBun);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        assertTrue(Recipe.matchesRecipe(cook.foodStack,customer.getRequestName()),"Error: correct recipes are not being accepted due to Recipe.matchesRecipe");
        testStation.interact(cook, InputKey.InputTypes.USE);
        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        assertFalse(testStation.customer != null, "Error: The serving station does not get rid of the customer after they are served");
        testStation.testFlag = 0;
        customerController.testFlag = 0;
    }

    @Test
    // Relates to the FR_DISH_SERVE requirement
    public void TestServingStationServeCustomerTomatoBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        CustomerController customerController = new CustomerController();
        customerController.testFlag = 1;
        customerController.customers.add(customer);
        customer.request = "Tomato Burger";
        testStation.customerController = customerController;
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bottomBun);
        cook.foodStack.addStack(FoodItem.FoodID.topBun);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        assertTrue(Recipe.matchesRecipe(cook.foodStack,customer.getRequestName()),"Error: correct recipes are not being accepted due to Recipe.matchesRecipe");
        testStation.interact(cook, InputKey.InputTypes.USE);
        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        assertFalse(testStation.customer != null, "Error: The serving station does not get rid of the customer after they are served");
        testStation.testFlag = 0;
        customerController.testFlag = 0;
    }

    @Test
    // Relates to the FR_DISH_SERVE requirement
    public void TestServingStationServeCustomerLettuceTomatoBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        CustomerController customerController = new CustomerController();
        customerController.testFlag = 1;
        customerController.customers.add(customer);
        customer.request = "Lettuce Tomato Burger";
        testStation.customerController = customerController;
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bottomBun);
        cook.foodStack.addStack(FoodItem.FoodID.topBun);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        assertTrue(Recipe.matchesRecipe(cook.foodStack,customer.getRequestName()),"Error: correct recipes are not being accepted due to Recipe.matchesRecipe");
        testStation.interact(cook, InputKey.InputTypes.USE);
        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        assertFalse(testStation.customer != null, "Error: The serving station does not get rid of the customer after they are served");
        testStation.testFlag = 0;
        customerController.testFlag = 0;
    }

    @Test
    // Relates to the FR_DISH_SERVE requirement
    public void TestServingStationServeCustomerLettuceOnionBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        CustomerController customerController = new CustomerController();
        customerController.testFlag = 1;
        customerController.customers.add(customer);
        customer.request = "Lettuce Onion Burger";
        testStation.customerController = customerController;
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bottomBun);
        cook.foodStack.addStack(FoodItem.FoodID.topBun);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        assertTrue(Recipe.matchesRecipe(cook.foodStack,customer.getRequestName()),"Error: correct recipes are not being accepted due to Recipe.matchesRecipe");
        testStation.interact(cook, InputKey.InputTypes.USE);
        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        assertFalse(testStation.customer != null, "Error: The serving station does not get rid of the customer after they are served");
        testStation.testFlag = 0;
        customerController.testFlag = 0;
    }

    @Test
    // Relates to the FR_DISH_SERVE requirement
    public void TestServingStationServeCustomerTomatoOnionBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        CustomerController customerController = new CustomerController();
        customerController.testFlag = 1;
        customerController.customers.add(customer);
        customer.request = "Tomato Onion Burger";
        testStation.customerController = customerController;
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bottomBun);
        cook.foodStack.addStack(FoodItem.FoodID.topBun);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        assertTrue(Recipe.matchesRecipe(cook.foodStack,customer.getRequestName()),"Error: correct recipes are not being accepted due to Recipe.matchesRecipe");
        testStation.interact(cook, InputKey.InputTypes.USE);
        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        assertFalse(testStation.customer != null, "Error: The serving station does not get rid of the customer after they are served");
        testStation.testFlag = 0;
        customerController.testFlag = 0;
    }

    @Test
    // Relates to the FR_DISH_SERVE requirement
    public void TestServingStationServeCustomerLettuceTomatoOnionBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        CustomerController customerController = new CustomerController();
        customerController.testFlag = 1;
        customerController.customers.add(customer);
        customer.request = "Lettuce Tomato Onion Burger";
        testStation.customerController = customerController;
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bottomBun);
        cook.foodStack.addStack(FoodItem.FoodID.topBun);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        assertTrue(Recipe.matchesRecipe(cook.foodStack,customer.getRequestName()),"Error: correct recipes are not being accepted due to Recipe.matchesRecipe");
        testStation.interact(cook, InputKey.InputTypes.USE);
        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        assertFalse(testStation.customer != null, "Error: The serving station does not get rid of the customer after they are served");
        testStation.testFlag = 0;
        customerController.testFlag = 0;
    }

     **/

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
        customerNew.customerStatus = 0;
        customerNew.servedCustomerLeaves();
        assertEquals(customerNew.customerStatus, 1,"Error: CustomerNew's customerStatus is not being incremented correctly if x <= destination x");
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
}
