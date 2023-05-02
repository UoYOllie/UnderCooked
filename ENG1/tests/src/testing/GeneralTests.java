package testing;

import Shop.Gold;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import cooks.*;
import food.FoodItem;
import helper.Constants;
import helper.MapHelper;
import helper.Util;
import interactions.InputKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import stations.*;

import java.util.ArrayList;

import static Shop.Gold.gold;
import static interactions.Interactions.keysJustPressed;
import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class GeneralTests {
    // The following tests don't directly relate to requirements, instead testing helper functions such as getters and setters.


    @Test
    // Checks that the Cook.dir can be set to different values for the cook's x value
    public void testCookInteractorRelativeX(){
        CookInteractor cookInteractor = new CookInteractor(20,20,20);
        Cook cook = new Cook(20,20,20,20);
        cook.dir = Cook.Facing.RIGHT;
        assertTrue(cookInteractor.relativeX(cook.dir) == 38.4F);
        cook.dir = Cook.Facing.LEFT;
        assertTrue(cookInteractor.relativeX(cook.dir) == -38.4F);
        cook.dir = Cook.Facing.UP;
        assertTrue(cookInteractor.relativeX(cook.dir) == 0F);
    }

    @Test
    // Checks that the Cook.dir can be set to different values for the cook's y value
    public void testCookInteractorRelativeY(){
        CookInteractor cookInteractor = new CookInteractor(20,20,20);
        Cook cook = new Cook(20,20,20,20);
        cook.dir = Cook.Facing.UP;
        assertTrue(cookInteractor.relativeY(cook.dir) == 25.6F);
        cook.dir = Cook.Facing.DOWN;
        assertTrue(cookInteractor.relativeY(cook.dir) == -25.6F);
        cook.dir = Cook.Facing.LEFT;
        assertTrue(cookInteractor.relativeY(cook.dir) == 12.8F);
        cook.dir = Cook.Facing.RIGHT;
        assertTrue(cookInteractor.relativeY(cook.dir) == 12.8F);
        cook.dir = Cook.Facing.NONE;
        assertTrue(cookInteractor.relativeY(cook.dir) == 0F);
    }

    @Test
    // Checks that the cook interactor updates the cook's position correctly
    public void testCookInteractorUpdatePosition(){
        CookInteractor cookInteractor = new CookInteractor(20,20,20);
        Cook cook = new Cook(20,20,20,20);
        cook.dir = Cook.Facing.UP;
        cookInteractor.updatePosition(20,20,cook.dir);
        assertTrue(cookInteractor.x == 20 + cookInteractor.relativeX(cook.dir));
        assertTrue(cookInteractor.y == 20 + cookInteractor.relativeY(cook.dir));
    }

    @Test
    // Checks that the cook interactor allows use inputs to be detected
    public void testCookInteractorCheckCollision(){
        CookInteractor cookInteractor = new CookInteractor((1500 * 1/8f),(1200 * 1/8f),20);
        Cook cook = new Cook((1500 * 1/8f),(1200 * 1/8f),20,20);
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation testStation = new PreparationStation(rectangle);
        testStation.setID(Station.StationID.fry);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        cook.foodStack.addStack(FoodItem.FoodID.meat);
        cookInteractor.checkCollisions(cook, InputKey.InputTypes.USE);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.meatCook);
    }

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
    // Tests the cook.userInteract method allows the cook to interact with a station
    public void TestCookUserInteract(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        BinStation testStation = new BinStation(rectangle);
        ArrayList<Rectangle> testList = new ArrayList<>();
        ArrayList<Station> stationList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        stationList.add(testStation);
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        keysJustPressed.add(InputKey.InputTypes.PUT_DOWN);
        cook.userInteract(stationList);
        assertEquals(cook.foodStack.size(), 0);
        keysJustPressed.clear();
    }

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

    @Test
    // Tests that the customer interactor method updatePosition by ensuring the x and y position get updated accordingly
    public void testUpdatePosition(){
        CustomerInteractor customerInteractor = new CustomerInteractor(20,30,100);
        customerInteractor.updatePosition(10,20, Cook.Facing.DOWN);
        assertEquals(customerInteractor.x,10,"Error: CustomerInteractor's updatePosition does not properly update the x position");
        assertEquals(customerInteractor.y,20,"Error: CustomerInteractor's updatePosition does not properly update the y position");
        assertEquals(customerInteractor.collision.x, (10-(100/2)),"Error: CustomerInteractor's updatePosition does not properly update the collision's x position");
        assertEquals(customerInteractor.collision.y, (20-(100/2)),"Error: CustomerInteractor's updatePosition does not properly update the collision's y position");
    }

    //The following tests the code in CustomerNew that isn't to do with the gameplay (gameplay test includes storming out etc)

    @Test
    // Tests that the CustomerNew method setStationPosition sets a station's x and y coordinates to the given values
    public void testSetStationPosition(){
        CustomerNew customerNew = new CustomerNew(1,2,3,4); //Each number is different in order to test getters easier
        customerNew.setStationPosition(100,200);
        assertEquals(customerNew.stationPosition.x ,100);
        assertEquals(customerNew.stationPosition.y ,200);
    }

    @Test
    // Checks that the CustomerNew method setDestination sets a customer's x and y coordinates to the given values
    public void testSetDestination(){
        CustomerNew customerNew = new CustomerNew(1,2,3,4); //Each number is different in order to test getters easier
        customerNew.setDestination(100,200);
        assertEquals(customerNew.destination.x ,100);
        assertEquals(customerNew.destination.y ,200);
    }

    @Test
    // Tests that the CustomerNew method DecreasePatience decrements the customers wait time by the correct amount
    public void testDecreasePatience(){
        CustomerNew customerNew = new CustomerNew(1,2,3,4);
        customerNew.waittime = 100;
        customerNew.DecreasePatience();
        assertEquals(customerNew.waittime, 100 - 1f,"Error: The customers patience is not decremented properly when Decrease patience is called");
    }

    @Test
    // Tests that the CustomerNew method getX returns the customer's x position
    public void testCustomerNewGetX(){
        CustomerNew customerNew = new CustomerNew(1,2,3,4);
        assertEquals(customerNew.getX(),1,"Error: CustomerNew getX does not get x");
    }

    @Test
    // Tests that the CustomerNew method getY returns the customer's y position
    public void testCustomerNewGetY(){
        CustomerNew customerNew = new CustomerNew(1,2,3,4);
        assertEquals(customerNew.getY(),2,"Error: CustomerNew getY does not get y");
    }

    @Test
    // Tests that the CustomerNew method getRequestName returns the correct recipe
    public void testCustomerNewGetRequestName(){
        CustomerNew customerNew = new CustomerNew(1,2,3,4);
        customerNew.request = ("Plain Burger");
        assertEquals(customerNew.getRequestName(),"Plain Burger","Error: CustomerNew getRequestName does not get the right recipe");
    }

    @Test
    // Tests that the CustomerNew method setCustomerPoints correctly sets the customerPoints to the given value
    public void testCustomerNewSetCustomerPoints(){
        CustomerNew customerNew = new CustomerNew(1,2,3,4);
        Array<Vector2> customerPoints = new Array<>();
        customerPoints.add(Constants.customerPointC);
        customerPoints.add(Constants.customerPointD);
        customerPoints.add(Constants.customerPointE);
        customerPoints.add(Constants.customerPointF);
        customerPoints.add(customerNew.stationPosition);
        assertEquals(customerPoints, customerNew.setCustomerPoints(), "Error: CustomerNew setCustomerPoints does not set the correct value to the customerPoints array");
    }

    @Test
    // Tests that the CustomerNew method move_left_down sets the x and y coordinates to the correct values according to their position relative to their destination
    public void testCustomerNewMoveLeftDown(){
        //First we will test the first if and the second if, to do with x and y
        CustomerNew customerNew = new CustomerNew(20,20,3,4);
        customerNew.destination.x = 1;
        customerNew.destination.y = 1;
        customerNew.move_left_down(new Vector2(100,200));
        assertEquals(customerNew.x, 20 - Constants.UnitScale,"Error: when customer's x position is higher then the customer.destination's x position, and moveLeftDown is called, the x position is not updated correctly");
        assertEquals(customerNew.y, 20 - Constants.UnitScale,"Error: when customer's y position is higher then the customer.destination's y position, and moveLeftDown is called, the y position is not updated correctly");

        //Now testing if the else's work and hence if the final if works
        customerNew.x = 20;
        customerNew.y = 20;
        customerNew.destination.x = 100;
        customerNew.destination.y = 100;
        customerNew.move_left_down(new Vector2(100,200));
        assertEquals(customerNew.destination.x, 100,"Error: CustomerNew's moveLeftDown class does not set the x position of the customer correctly if both flags are met");
        assertEquals(customerNew.destination.y, 200,"Error: CustomerNew's moveLeftDown class does not set the x position of the customer correctly if both flags are met");
    }

    @Test
    // Tests that the customer's status is set to 1 when they enter the game
    public void testCustomerNewEnterCustomer(){
        CustomerNew customerNew = new CustomerNew(100,400,3,4);
        customerNew.stationPosition.x = 1;
        customerNew.stationPosition.y = 1;
        customerNew.setCustomerStatus(0);
        customerNew.enterCustomer();
        assertEquals(customerNew.getCustomerStatus(), 1);
    }

    //The following test the ServingStation's customerInteract method for the menu and teacup change power ups
    @Test
    // Tests that giving a customer a teacup will reset their wait time and puts a teacup in their inventory, and giving them a menu will cause them to change their request
    public void testServingStationCustomerInteract(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);

        CustomerNew customerNew = new CustomerNew(rectangle.x,rectangle.y, rectangle.width,rectangle.height);
        testStation.customer = customerNew;
        customerNew.request = "Plain Burger";
        customerNew.waittime = 50;

        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());

        //First we test the teacup - it should reset the customers waiting time after use
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.teacup);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);
        assertEquals(customerNew.waittime, 300,"Error: Serving a teacup does not reset the customers wait-time");
        assertEquals(customerNew.dishStack.getStack().peek(), FoodItem.FoodID.teacup,"Error: Customer does not have a teacup in their inventory after they are served one");

        //Next we check the menu, which should force the customer to request a different item after use
        cook.foodStack.addStack(FoodItem.FoodID.menu);
        cook.dishStack.setStackPlate(cook.foodStack.getStackCopy());

        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.customerInteract(customerNew);
        assertFalse(customerNew.request == "Plain Burger","Error: Giving the menu to the customer does not make them choose a different request");
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
        CustomerNew customerNew = new CustomerNew(1,2,3,4);
        ArrayList<CustomerNew> customerNews = new ArrayList<CustomerNew>();
        customerNews.add(customerNew);
        customerController.customers.add(customerNew);
        assertEquals(customerController.getCustomers(), customerNews, "getCustomers does not return the correct array of customers");
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
        CustomerNew customerNew1 = new CustomerNew(1,2,3,4);
        CustomerNew customerNew2 = new CustomerNew(1,2,3,4);
        CustomerNew customerNew3 = new CustomerNew(1,2,3,4);
        CustomerNew customerNew4 = new CustomerNew(1,2,3,4);
        CustomerNew customerNew5 = new CustomerNew(1,2,3,4);
        ArrayList<CustomerNew> customerNews = new ArrayList<CustomerNew>();
        customerNews.add(customerNew1);
        customerNews.add(customerNew2);
        customerNews.add(customerNew3);
        customerNews.add(customerNew4);
        customerNews.add(customerNew5);
        customerController.customers = customerNews;
        assertTrue(customerController.maxCustomersReached(), "the maxCustomersReached method does not provide a customer limit");
    }

    @Test
    // Tests that the wonScenario method returns true if there are no customers left to be served
    public void TestWonScenario(){
        //First we test the win scenario, no customers left
        CustomerController customerController = new CustomerController();
        customerController.setMode("scenario");
        customerController.setCustomers_left(0);
        assertTrue(customerController.wonScenario(), "wonScenario does not return true in scenario mode if there are no customers left to be served");

        //Now we test endless
        customerController.setMode("endless");
        customerController.setCustomers_left(1);
        assertFalse(customerController.wonScenario(), "wonScenario returns true in endless mode if there are customers left to be served (expected false)");
    }

}
