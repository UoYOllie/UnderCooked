package testing;

import shop.Gold;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import players.*;
import food.FoodItem;
import helper.Constants;
import helper.MapHelper;
import helper.Util;
import interactions.InputKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import stations.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static shop.Gold.gold;
import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class GeneralTests {
    // The following tests don't directly relate to requirements, instead testing helper functions such as getters and setters.
    @Test
    // Checks that the Util class can format time correctly and find the distance between two points
    public void testUtilClass(){
        String finalString = Util.formatTime(0,10,1);
        assertEquals(finalString, "10:01");
        finalString = Util.formatTime(10,65,65);
        assertEquals(finalString,"10:65:65");
        float finalNum = Util.distancePoints(1,1,3,3);
        float testAgainst = (float) Math.sqrt(8);
        assertEquals(finalNum,testAgainst);
    }

    // Relates to the FR_SPEND_MONEY and FR_EARN_MONEY requirements
    @Test
    // Tests the gold setter method works
    public void TestGoldSetandGetBalance(){
        Gold gold = new Gold();
        gold.setBalance(36);
        assertTrue(gold.Balance == 36, "gold.setBalance does not set gold.Balance to the correct value");
    }

    @Test
    // Tests the gold getter method works
    public void TestGoldGetInstance(){
        assertTrue(Gold.getInstance()  == gold , "gold.getInstance does not return the correct value");
    }

    @Test
    // Tests the method to increase the player's gold
    public void TestGoldAddBalance(){
        Gold gold = new Gold();
        gold.Balance = 1;
        gold.addBalance(2);
        assertEquals(gold.Balance,3, "gold.addBalance doesn't add the correct amount to gold.Balance");
    }

    //The following tests ensure the robustness of the FoodStack class by testing utility functions and ensuring the fail cases work
    @Test
    public void TestFoodStackPeekStackCatchIndexOutOfBounds(){
        Cook cook = new Cook(1,1,1,1);
        assertTrue(cook.foodStack.peekStack() == null,"Error: IndexOutOfBounds is not working on foodStacks peekStack");
    }

    @Test
    public void TestFoodStackPopStackCatchIndexOutOfBounds(){
        Cook cook = new Cook(1,1,1,1);
        assertTrue(cook.foodStack.popStack() == null,"Error: IndexOutOfBounds is not working on foodStacks popStack");
    }

    @Test
    public void TestFoodStackSetStack(){
        Cook cook = new Cook(1,1,1,1);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.lettuceChop);
        foodID.add(FoodItem.FoodID.plate);
        cook.foodStack.setStack(foodID);
        assertTrue(cook.foodStack.popStack() == FoodItem.FoodID.lettuceChop, "Error: FoodStacks setStack is not setting the stack properly");
        assertTrue(cook.foodStack.popStack() == FoodItem.FoodID.plate,"Error: FoodStacks setStack is not setting the stack properly");
        assertTrue(cook.foodStack.size() == 0, "Error: FoodStacks setStack is not setting the stack properly");
    }

    @Test
    public void TestFoodStackToString(){
        Cook cook = new Cook(1,1,1,1);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.lettuceChop);
        foodID.add(FoodItem.FoodID.plate);
        cook.foodStack.setStack(foodID);
        String FoodString = cook.foodStack.toString();
        assertEquals(FoodString,"[lettuceChop, plate]","Error; FoodStack's toString no longer returns the correct string");
    }

    //The following are general tests for the utility functions in the cook class
    @Test
    // Tests that the cook.getBlocked method returns the correct value
    public void TestGetBlocked(){
        Cook cook = new Cook(1,1,1,1);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.lettuceChop);
        foodID.add(FoodItem.FoodID.plate);
        cook.dishStack.setStack(foodID);
        assertTrue(cook.getBlocked());
        cook.dishStack.clearStack();
        assertFalse(cook.getBlocked());
    }

    @Test
    // Tests that the cook.update method correctly updates the cooks position
    public void TestCookUpdate(){
        Cook cook = new Cook(1,1,1,1);
        cook.update(0);
        assertEquals(cook.x, 1/8f);
        assertEquals(cook.y, 1/8f);
        assertEquals(cook.cookInteractor.x , 0);
        assertEquals(cook.cookInteractor.y, 0);
    }

    @Test
    public void TestChefFoodRelativeX(){
        // Tests that the cook.foodRelativeX method returns the expected value for the given cook's direction
        Cook cook = new Cook(1,1,1,1);
        assertEquals(cook.foodRelativeX(Cook.Facing.RIGHT),13F * 1/8f);
        assertEquals(cook.foodRelativeX(Cook.Facing.LEFT),13F * 1/8f);
        assertEquals(cook.foodRelativeX(Cook.Facing.UP),13 * 1/8f);
    }

    @Test
    // Tests that the cook.foodRelativeY method returns the expected value for the given cook's direction
    public void TestChefFoodRelativeY(){
        Cook cook = new Cook(1,1,1,1);
        assertEquals(cook.foodRelativeY(Cook.Facing.RIGHT),-24F * 1/8f);
        assertEquals(cook.foodRelativeY(Cook.Facing.LEFT),-24F * 1/8f);
        assertEquals(cook.foodRelativeY(Cook.Facing.UP),-14F * 1/8f);
        assertEquals(cook.foodRelativeY(Cook.Facing.DOWN),-25F * 1/8f);
        assertEquals(cook.foodRelativeY(Cook.Facing.NONE),0F * 1/8f);
    }

    @Test
    // Tests that the cook.opposite method returns the expected value for the given cook's direction
    public void TestCookOpposite(){
        Cook cook = new Cook(1,1,1,1);
        assertEquals(cook.opposite(Cook.Facing.UP), Cook.Facing.DOWN);
        assertEquals(cook.opposite(Cook.Facing.DOWN), Cook.Facing.UP);
        assertEquals(cook.opposite(Cook.Facing.LEFT), Cook.Facing.RIGHT);
        assertEquals(cook.opposite(Cook.Facing.RIGHT), Cook.Facing.LEFT);
        assertEquals(cook.opposite(Cook.Facing.NONE), Cook.Facing.NONE);
    }

    @Test
    // Tests that the cook.rotate90c method returns the expected value for the given cook's direction
    public void TestCookRotate90C(){
        Cook cook = new Cook(1,1,1,1);
        assertEquals(cook.rotate90c(Cook.Facing.UP), Cook.Facing.RIGHT);
        assertEquals(cook.rotate90c(Cook.Facing.RIGHT), Cook.Facing.DOWN);
        assertEquals(cook.rotate90c(Cook.Facing.DOWN), Cook.Facing.LEFT);
        assertEquals(cook.rotate90c(Cook.Facing.LEFT), Cook.Facing.UP);
        assertEquals(cook.opposite(Cook.Facing.NONE), Cook.Facing.NONE);
    }

    @Test
    // Tests that the cook.setDir method correctly sets the cooks direction
    public void TestSetDir(){
        //Testing that there is no opposites in the cook input
        Cook cook = new Cook(1,2,3,4);
        cook.inputs.add(Cook.Facing.LEFT);
        cook.inputs.add(Cook.Facing.DOWN);
        cook.setDir();
        assertEquals(cook.dir, Cook.Facing.DOWN,"Error: Cook's setDir method does not set to the correct direction when no opposite directions appear in the inputs");

        //Testing case when there are opposites for the next and a 90 degree rotation of the next
        cook.inputs.clear();
        cook.inputs.add(Cook.Facing.LEFT);
        cook.inputs.add(Cook.Facing.UP);
        cook.inputs.add(Cook.Facing.RIGHT);
        cook.setDir();
        assertEquals(cook.dir, Cook.Facing.UP,"Error: Cook's setDir method does not set to the correct direction when opposite directions appear in the inputs");

        //Testing case when there are opposites for the next and a 90 degree rotation for the opposite of the next
        cook.inputs.clear();
        cook.inputs.add(Cook.Facing.LEFT);
        cook.inputs.add(Cook.Facing.DOWN);
        cook.inputs.add(Cook.Facing.RIGHT);
        cook.setDir();
        assertEquals(cook.dir, Cook.Facing.DOWN,"Error: Cook's setDir method does not set to the correct direction when opposite directions appear in the inputs");

    }

    @Test
    // Tests that the player stack is limited to 3 items
    public void TestHoldItems(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.lettuce);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.size() == 3, "The chef is able to hold more than 3 items at once");
    }

    @Test
    // Tests that the cook.setColour method correctly sets the cook's colour
    public void TestCookSetColour(){
        Cook cook = new Cook(1,2,3,4);
        cook.setColour("Blue");
        assertEquals(cook.Colour, "Blue","Error: the Setter for cooks colour is not setting the colour correctly");
    }

    @Test
    // Tests that the test.getKey method returns the correct key
    public void TestGetInputKey(){
        InputKey test = new InputKey(InputKey.InputTypes.INSTRUCTIONS, Input.Keys.I);
        assertTrue(test.getKey() == Input.Keys.I, "The getKey function is broken/returns wrong key");
    }

    @Test
    // Tests that the test.getType method returns the correct type
    public void TestGetInputType(){
        InputKey test = new InputKey(InputKey.InputTypes.INSTRUCTIONS, Input.Keys.I);
        assertTrue(test.getType() == InputKey.InputTypes.INSTRUCTIONS,"The getType function is broken/returns wrong type");
    }

    //The following tests exercise the utility functions in the MapHelper class

    @Test
    // Tests that the helper.getMapObstacles method returns the correct list of preparation stations
    public void testSetMapObstacles(){
        PreparationStation preparationStation = new PreparationStation(new Rectangle(1,2,3,4));
        ArrayList<Rectangle> finalList = new ArrayList<>();
        finalList.add(preparationStation.getRectangle());
        MapHelper helper = new MapHelper(finalList,new ArrayList<Station>(),new ArrayList<Station>());
        assertEquals(helper.getMapObstacles(), finalList , "Error: The MapHelper utility function getMapObstacles does not return the right list");
    }

    @Test
    // Tests that the helper.getMapStations method returns the correct list of preparation stations
    public void testsGetMapStations(){
        PreparationStation preparationStation = new PreparationStation(new Rectangle(1,2,3,4));
        ArrayList<Station> finalList = new ArrayList<>();
        finalList.add(preparationStation);
        MapHelper helper = new MapHelper(new ArrayList<Rectangle>(),finalList,new ArrayList<Station>());
        assertEquals(helper.getMapStations(), finalList , "Error: The MapHelper utility function getMapStations does not return the right list");
    }

    @Test
    // Tests that the helper.getServingStationList method returns the correct list of serving stations
    public void testsGetServingStationList(){
        ServingStation ServingStation = new ServingStation(new Rectangle(1,2,3,4));
        ArrayList<Station> finalList = new ArrayList<>();
        finalList.add(ServingStation);
        MapHelper helper = new MapHelper(new ArrayList<Rectangle>(),new ArrayList<Station>(),finalList);
        assertEquals(helper.getServingStationList(), finalList , "Error: The MapHelper utility function getServingStationsNew does not return the right list");
    }

    //The following tests the code in Customer that isn't to do with the gameplay (gameplay test includes storming out etc)

    @Test
    // Tests that the Customer method setStationPosition sets a station's x and y coordinates to the given values
    public void testSetStationPosition(){
        Customer customer = new Customer(1,2,3,4); //Each number is different in order to test getters easier
        customer.setStationPosition(100,200);
        assertEquals(customer.stationPosition.x ,100);
        assertEquals(customer.stationPosition.y ,200);
    }

    @Test
    // Checks that the Customer method setDestination sets a customer's x and y coordinates to the given values
    public void testSetDestination(){
        Customer customer = new Customer(1,2,3,4); //Each number is different in order to test getters easier
        customer.setDestination(100,200);
        assertEquals(customer.destination.x ,100);
        assertEquals(customer.destination.y ,200);
    }

    @Test
    // Tests that the Customer method DecreasePatience decrements the customers wait time by the correct amount
    public void testDecreasePatience(){
        Customer customer = new Customer(1,2,3,4);
        customer.waittime = 100;
        customer.DecreasePatience();
        assertEquals(customer.waittime, 100 - 1f,"Error: The customers patience is not decremented properly when Decrease patience is called");
    }

    @Test
    // Tests that the Customer method getX returns the customer's x position
    public void testCustomerGetX(){
        Customer customer = new Customer(1,2,3,4);
        assertEquals(customer.getX(),1,"Error: Customer getX does not get x");
    }

    @Test
    // Tests that the Customer method getY returns the customer's y position
    public void testCustomerGetY(){
        Customer customer = new Customer(1,2,3,4);
        assertEquals(customer.getY(),2,"Error: Customer getY does not get y");
    }

    @Test
    // Tests that the Customer method getRequestName returns the correct recipe
    public void testCustomerGetRequestName(){
        Customer customer = new Customer(1,2,3,4);
        customer.request = ("Plain Burger");
        assertEquals(customer.getRequestName(),"Plain Burger","Error: Customer getRequestName does not get the right recipe");
    }

    @Test
    // Tests that the Customer method moveLeftDown sets the x and y coordinates to the correct values according to their position relative to their destination
    public void testCustomerMoveLeftDown(){
        //First we will test the first if and the second if, to do with x and y
        Customer customer = new Customer(20,20,3,4);
        customer.destination.x = 1;
        customer.destination.y = 1;
        customer.moveLeftDown(new Vector2(100,200));
        assertEquals(customer.x, 20 - Constants.UnitScale,"Error: when customer's x position is higher then the customer.destination's x position, and moveLeftDown is called, the x position is not updated correctly");
        assertEquals(customer.y, 20 - Constants.UnitScale,"Error: when customer's y position is higher then the customer.destination's y position, and moveLeftDown is called, the y position is not updated correctly");

        //Now testing if the else's work and hence if the final if works
        customer.x = 20;
        customer.y = 20;
        customer.destination.x = 100;
        customer.destination.y = 100;
        customer.moveLeftDown(new Vector2(100,200));
        assertEquals(customer.destination.x, 100,"Error: Customer's moveLeftDown class does not set the x position of the customer correctly if both flags are met");
        assertEquals(customer.destination.y, 200,"Error: Customer's moveLeftDown class does not set the x position of the customer correctly if both flags are met");
    }

    //The following test the ServingStation's customerInteract method for the menu and teacup change power ups
    @Test
    // Tests that giving a customer a teacup will reset their wait time and puts a teacup in their inventory, and giving them a menu will cause them to change their request
    public void testServingStationCustomerInteract(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);

        Customer customer = new Customer(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customer;
        customer.request = "Plain Burger";
        customer.waittime = 50;

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        //First we test the teacup - it should reset the customers waiting time after use
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.teacup);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customer);
        assertEquals(customer.waittime, 300,"Error: Serving a teacup does not reset the customers wait-time");
        assertEquals(customer.dishStack.getStack().peek(), FoodItem.FoodID.teacup,"Error: Customer does not have a teacup in their inventory after they are served one");

        //Next we check the menu, which should force the customer to request a different item after use
        cook.foodStack.addStack(FoodItem.FoodID.menu);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customer);
        assertFalse(customer.request == "Plain Burger","Error: Giving the menu to the customer does not make them choose a different request");
    }

    //The following test the logic for the CustomerController class

    @Test
    // Tests that the getMode method returns the correct game mode (endless or scenario)
    public void TestSetAndGetMode(){
        CustomerController customerController = new CustomerController();
        customerController.setMode("scenario");
        assertEquals(customerController.getMode(), "scenario", "getMode does not return the correct game mode");
    }

    @Test
    // Tests that the getDifficulty method returns the correct game difficulty (easy, medium or hard as represented by the integers 1, 2, 3 respectively)
    public void TestSetAndGetDifficulty(){
        CustomerController customerController = new CustomerController();
        customerController.setDifficulty(3);
        assertEquals(customerController.getDifficulty(), 3, "getDifficulty does not return the correct game difficulty");
    }

    @Test
    // Tests that the getCustomers method returns the correct array of customers
    public void TestGetCustomers(){
        CustomerController customerController = new CustomerController();
        Customer customer = new Customer(1,2,3,4);
        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers.add(customer);
        customerController.customers.add(customer);
        assertEquals(customerController.getCustomers(), customers, "getCustomers does not return the correct array of customers");
    }

    @Test
    // Tests that there is a limit on the number of customers that can place an order at once
    public void TestMaxCustomersReached(){
        //Testing fails when supposed to
        CustomerController customerController = new CustomerController();
        customerController.setMode("endless");
        assertFalse(customerController.maxCustomersReached());

        //Testing works when supposed to
        customerController.setMode("scenario");
        Customer customer1 = new Customer(1,2,3,4);
        Customer customer2 = new Customer(1,2,3,4);
        Customer customer3 = new Customer(1,2,3,4);
        Customer customer4 = new Customer(1,2,3,4);
        Customer customer5 = new Customer(1,2,3,4);
        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);
        customers.add(customer5);
        customerController.customers = customers;
        assertTrue(customerController.maxCustomersReached(), "the maxCustomersReached method does not provide a customer limit");
    }

    @Test
    // Tests that the wonScenario method returns true if there are no customers left to be served
    public void TestWonScenario(){
        //First we test the win scenario, no customers left
        CustomerController customerController = new CustomerController();
        customerController.setMode("scenario");
        customerController.setcustomersLeft(0);
        assertTrue(customerController.wonScenario(), "wonScenario does not return true in scenario mode if there are no customers left to be served");

        //Now we test endless
        customerController.setMode("endless");
        customerController.setcustomersLeft(1);
        assertFalse(customerController.wonScenario(), "wonScenario does not return true in endless mode if there is a customer left waiting to be served");

        //next test scenario where the mode is scenario and customersLeft is > 0
        customerController.setMode("scenario");
        customerController.setcustomersLeft(3);

        Customer customer1 = new Customer(1,2,3,4);
        customer1.setCustomerStatus(2);
        Customer customer2 = new Customer(1,2,3,4);
        customer2.setCustomerStatus(2);
        Customer customer3 = new Customer(1,2,3,4);
        customer3.setCustomerStatus(2);
        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customerController.customers = customers;
        customerController.wonScenario();
        assertEquals(customerController.getcustomersLeft(), 0, "there are customers left after winning the game in scenario mode");
    }

    @Test
    // Tests the removeCustomer method
    public void TestRemoveCustomer(){
        CustomerController customerController = new CustomerController();
        Map<Station, Customer> stationCustomerMap = new HashMap<Station,Customer>();
        PreparationStation preparationStation = new PreparationStation(new Rectangle());
        Customer customer = new Customer(1,2,3,4);
        stationCustomerMap.put(preparationStation,customer);
        customerController.setStationCustomerMap(stationCustomerMap);
        customerController.removeCustomer(preparationStation);
        assertNull(stationCustomerMap.get(preparationStation), "removeCustomer does not remove the customer from the station in the hashmap");
    }

    @Test
    // Tests that the initialiseStationCustomerMap method creates a hashmap with all the serving stations
    public void TestInitialiseStationCustomerMap(){
        CustomerController customerController = new CustomerController();
        ServingStation servingStation1 = new ServingStation(new Rectangle());
        ServingStation servingStation2 = new ServingStation(new Rectangle());
        ArrayList<Station> arrayList = new ArrayList<Station>();

        arrayList.add(servingStation1);
        arrayList.add(servingStation2);

        customerController.setServingStations(arrayList);
        customerController.initialiseStationCustomerMap();

        Map<Station, Customer> stationCustomerMap = new HashMap<Station,Customer>();
        stationCustomerMap.put(servingStation1,null);
        stationCustomerMap.put(servingStation2,null);

        assertEquals(customerController.getStationCustomerMap(),stationCustomerMap, "getStationCustomerMap doesn't create the correct hashmap with all the serving stations");
    }

    @Test
    // Tests that the addCustomer method doesn't add a customer if there are already more than 4 customers present
    public void TestAddCustomerCustomersPresent(){
        CustomerController customerController = new CustomerController();
        customerController.setMode("scenario");
        ArrayList<Customer> customers = new ArrayList<Customer>();
        Customer customer1 = new Customer(1,2,3,4);
        Customer customer2 = new Customer(1,2,3,4);
        Customer customer3 = new Customer(1,2,3,4);
        Customer customer4 = new Customer(1,2,3,4);
        Customer customer5 = new Customer(1,2,3,4);
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);
        customers.add(customer5);
        customerController.customers = customers;
        assertNull(customerController.addCustomer(), "The addCustomer method should return null when there are already 5 customers present, but doesn't");
    }

    @Test
    public void TestAddCustomerCustomerPresent(){
        CustomerController customerController = new CustomerController();
        PreparationStation preparationStation = new PreparationStation(new Rectangle());
        Customer customer = new Customer(1, 2, 3, 4);
        Map<Station, Customer> stationCustomerMap = new HashMap<Station,Customer>();
        stationCustomerMap.put(preparationStation,customer);
        customerController.setStationCustomerMap(stationCustomerMap);
        assertNull(customerController.addCustomer());
    }

    @Test
    public void TestGetStationKey(){
        CustomerController customerController = new CustomerController();
        ServingStation servingStation1 = new ServingStation(new Rectangle(1,2,3,4));
        Map<Station, Customer> stationCustomerMap = new HashMap<Station,Customer>();
        stationCustomerMap.put(servingStation1,new Customer(1,2,3,4));
        customerController.setStationCustomerMap(stationCustomerMap);
        assertEquals(customerController.getStationKey(stationCustomerMap,2),servingStation1);
    }
}
