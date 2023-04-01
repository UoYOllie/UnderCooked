package testing;

import Shop.Gold;
import Shop.ShopItem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import cooks.Cook;
import customers.Customer;
import customers.CustomerController;
import food.FoodItem;
import food.Recipe;
import interactions.InputKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import stations.ServingStation;
import stations.SpeedPowerup;
import stations.Station;

import java.util.ArrayList;

import static interactions.Interactions.keysPressed;
import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class GameplayTest {

    @Test
    // Relates to the FR_DISH_SERVE requirement
    public void TestServingStationServeCustomerOnionTomatoSalad(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        CustomerController customerController = new CustomerController();
        customerController.testFlag = 1;
        customerController.customers.add(customer);
        customer.request = "Onion Tomato Salad";
        testStation.customerController = customerController;
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        testStation.interact(cook, InputKey.InputTypes.USE);
        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        assertFalse(testStation.customer != null, "Error: The serving station does not get rid of the customer after they are served");
        testStation.testFlag = 0;
        customerController.testFlag = 0;
    }

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

    @Test
    public void TestServingStationGetCustomerX(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        CustomerController customerController = new CustomerController();
        customerController.testFlag = 1;
        customerController.customers.add(customer);
        testStation.customerController = customerController;
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        assertTrue(testStation.getCustomerX() == testStation.rectangle.x + 32,"Get customers x position no longer returns the x position of the station they are assigned to");
    }

    @Test
    public void TestServingStationGetCustomerY(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        CustomerController customerController = new CustomerController();
        customerController.testFlag = 1;
        customerController.customers.add(customer);
        testStation.customerController = customerController;
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        assertTrue(testStation.getCustomerY() == testStation.rectangle.y + 96,"Get customers y position no longer returns the y position of the station they are assigned to");
    }

    @Test
    public void TestServingStationGetXWithCustomer(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        CustomerController customerController = new CustomerController();
        customerController.testFlag = 1;
        customerController.customers.add(customer);
        testStation.customerController = customerController;
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        assertTrue(testStation.getX() == customer.getX(),"GetX no longer returns the x position of the customer they are assigned to");
    }

    @Test
    public void TestServingStationGetYWithCustomer(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        CustomerController customerController = new CustomerController();
        customerController.testFlag = 1;
        customerController.customers.add(customer);
        testStation.customerController = customerController;
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        assertTrue(testStation.getY() == customer.getY(),"GetY no longer returns the y position of the customer they are assigned to");
    }

    @Test
    public void TestServingStationGetXWithoutCustomer(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        assertTrue(testStation.getX() == testStation.rectangle.x + 32,"GetX no longer returns the x position of the serving station when no customers are assigned");
    }

    @Test
    public void TestServingStationGetYWithoutCustomer(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        testStation.setID(Station.StationID.serving);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        assertTrue(testStation.getY() == testStation.rectangle.y + 96,"GetY no longer returns the y position of the serving station when no customers are assigned");
    }

    @Test
    public void TestGoldSetandGetBalance(){
        Gold gold = new Gold();
        gold.setBalance(36);
        assertTrue(gold.Balance == 36);
    }

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
}
