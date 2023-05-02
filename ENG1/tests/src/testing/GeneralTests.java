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
    public void testCookInteractorUpdatePosition(){
        CookInteractor cookInteractor = new CookInteractor(20,20,20);
        Cook cook = new Cook(20,20,20,20);
        cook.dir = Cook.Facing.UP;
        cookInteractor.updatePosition(20,20,cook.dir);
        assertTrue(cookInteractor.x == 20 + cookInteractor.relativeX(cook.dir));
        assertTrue(cookInteractor.y == 20 + cookInteractor.relativeY(cook.dir));
    }

    @Test
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
    public void testUtilClass(){
        String finalString = Util.formatTime(0,10,1);
        assertEquals(finalString, "10:01");
        finalString = Util.formatTime(10,65,65);
        assertEquals(finalString,"10:65:65");
        float finalNum = Util.distancePoints(1,1,3,3);
        float testAgainst = (float) Math.sqrt(8);
        assertEquals(finalNum,testAgainst);
    }

    //Relates to the FR_SPEND_MONEY and FR_EARN_MONEY requirements
    @Test
    public void TestGoldSetandGetBalance(){
        Gold gold = new Gold();
        gold.setBalance(36);
        assertTrue(gold.Balance == 36);
    }

    @Test
    public void TestGoldGetInstance(){
        assertTrue(Gold.getInstance()  == gold );
    }

    @Test
    public void TestGoldAddBalance(){
        Gold gold = new Gold();
        gold.Balance = 1;
        gold.addBalance(2);
        assertEquals(gold.Balance,3);
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
        Cook cook = new Cook(1,1,1,1);
        assertEquals(cook.foodRelativeX(Cook.Facing.RIGHT),38F * 1/8f);
        assertEquals(cook.foodRelativeX(Cook.Facing.LEFT),-13F * 1/8f);
        assertEquals(cook.foodRelativeX(Cook.Facing.UP),13 * 1/8f);
    }

    @Test
    public void TestChefFoodRelativeY(){
        Cook cook = new Cook(1,1,1,1);
        assertEquals(cook.foodRelativeY(Cook.Facing.RIGHT),-24F * 1/8f);
        assertEquals(cook.foodRelativeY(Cook.Facing.LEFT),-24F * 1/8f);
        assertEquals(cook.foodRelativeY(Cook.Facing.UP),-14F * 1/8f);
        assertEquals(cook.foodRelativeY(Cook.Facing.DOWN),-25F * 1/8f);
        assertEquals(cook.foodRelativeY(Cook.Facing.NONE),0F * 1/8f);
    }

    @Test
    public void TestCookOpposite(){
        Cook cook = new Cook(1,1,1,1);
        assertEquals(cook.opposite(Cook.Facing.UP), Cook.Facing.DOWN);
        assertEquals(cook.opposite(Cook.Facing.DOWN), Cook.Facing.UP);
        assertEquals(cook.opposite(Cook.Facing.LEFT), Cook.Facing.RIGHT);
        assertEquals(cook.opposite(Cook.Facing.RIGHT), Cook.Facing.LEFT);
        assertEquals(cook.opposite(Cook.Facing.NONE), Cook.Facing.NONE);
    }

    @Test
    public void TestCookRotate90C(){
        Cook cook = new Cook(1,1,1,1);
        assertEquals(cook.rotate90c(Cook.Facing.UP), Cook.Facing.RIGHT);
        assertEquals(cook.rotate90c(Cook.Facing.RIGHT), Cook.Facing.DOWN);
        assertEquals(cook.rotate90c(Cook.Facing.DOWN), Cook.Facing.LEFT);
        assertEquals(cook.rotate90c(Cook.Facing.LEFT), Cook.Facing.UP);
        assertEquals(cook.opposite(Cook.Facing.NONE), Cook.Facing.NONE);
    }

    @Test
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

    //The following test tests the players stack is limited to 3 items
    @Test
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
    public void TestCookSetColour(){
        Cook cook = new Cook(1,2,3,4);
        cook.setColour("Blue");
        assertEquals(cook.Colour, "Blue","Error: the Setter for cooks colour is not setting the colour correctly");
    }

    //These test the utility function of getInputKey and getInputType
    @Test
    // TODO: add reference to requirement
    public void TestGetInputKey(){
        InputKey test = new InputKey(InputKey.InputTypes.INSTRUCTIONS, Input.Keys.I);
        assertTrue(test.getKey() == Input.Keys.I, "The getKey function is broken/returns wrong key");
    }

    @Test
    // TODO: add reference to requirement
    public void TestGetInputType(){
        InputKey test = new InputKey(InputKey.InputTypes.INSTRUCTIONS, Input.Keys.I);
        assertTrue(test.getType() == InputKey.InputTypes.INSTRUCTIONS,"The getType function is broken/returns wrong type");
    }

    //The following tests exercise the utility functions in the MapHelper class

    @Test
    public void testSetMapObstacles(){
        PreparationStation preparationStation = new PreparationStation(new Rectangle(1,2,3,4));
        ArrayList<Rectangle> finalList = new ArrayList<>();
        finalList.add(preparationStation.getRectangle());
        MapHelper helper = new MapHelper(finalList,new ArrayList<Station>(),new ArrayList<Station>());
        assertEquals(helper.getMapObstacles(), finalList , "Error: The MapHelper utility function getMapObstacles does not return the right list");
    }

    @Test
    public void testsGetMapStations(){
        PreparationStation preparationStation = new PreparationStation(new Rectangle(1,2,3,4));
        ArrayList<Station> finalList = new ArrayList<>();
        finalList.add(preparationStation);
        MapHelper helper = new MapHelper(new ArrayList<Rectangle>(),finalList,new ArrayList<Station>());
        assertEquals(helper.getMapStations(), finalList , "Error: The MapHelper utility function getMapStations does not return the right list");
    }

    @Test
    public void testsgetServingStationList(){
        ServingStation ServingStation = new ServingStation(new Rectangle(1,2,3,4));
        ArrayList<Station> finalList = new ArrayList<>();
        finalList.add(ServingStation);
        MapHelper helper = new MapHelper(new ArrayList<Rectangle>(),new ArrayList<Station>(),finalList);
        assertEquals(helper.getServingStationList(), finalList , "Error: The MapHelper utility function getServingStationsNew does not return the right list");
    }

    //The following test exercises the CustomerInteractor's updatePosition
    @Test
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
    public void testSetStationPosition(){
        CustomerNew customerNew = new CustomerNew(1,2,3,4); //Each number is different in order to test getters easier
        customerNew.setStationPosition(100,200);
        assertEquals(customerNew.stationPosition.x ,100);
        assertEquals(customerNew.stationPosition.y ,200);
    }

    @Test
    public void testSetDestination(){
        CustomerNew customerNew = new CustomerNew(1,2,3,4); //Each number is different in order to test getters easier
        customerNew.setDestination(100,200);
        assertEquals(customerNew.destination.x ,100);
        assertEquals(customerNew.destination.y ,200);
    }

    @Test
    public void testDecreasePatience(){
        CustomerNew customerNew = new CustomerNew(1,2,3,4);
        customerNew.waittime = 100;
        customerNew.DecreasePatience();
        assertEquals(customerNew.waittime, 100 - 1f,"Error: The customers patience is not decremented properly when Decrease patience is called");
    }

    @Test
    public void testCustomerNewGetX(){
        CustomerNew customerNew = new CustomerNew(1,2,3,4);
        assertEquals(customerNew.getX(),1,"Error: CustomerNew getX does not get x");
    }

    @Test
    public void testCustomerNewGetY(){
        CustomerNew customerNew = new CustomerNew(1,2,3,4);
        assertEquals(customerNew.getY(),2,"Error: CustomerNew getY does not get y");
    }

    @Test
    public void testCustomerNewGetRequestName(){
        CustomerNew customerNew = new CustomerNew(1,2,3,4);
        customerNew.request = ("Plain Burger");
        assertEquals(customerNew.getRequestName(),"Plain Burger","Error: CustomerNew getRequestName does not get the right recipe");
    }

    @Test
    public void testCustomerNewCustomerInteract(){
        CustomerNew customerNew = new CustomerNew(1,2,3,4);
        PreparationStation preparationStation = new PreparationStation(new Rectangle(1,2,3,4));
        ArrayList<Station> testList = new ArrayList<>();
        testList.add(preparationStation);
        customerNew.customerInteract(testList);
        //TODO: when interact is done, finish this
    }

    @Test
    public void testCustomerNewSetCustomerPoints(){
        CustomerNew customerNew = new CustomerNew(1,2,3,4);
        Array<Vector2> customerPoints = new Array<>();
        customerPoints.add(Constants.customerPointC);
        customerPoints.add(Constants.customerPointD);
        customerPoints.add(Constants.customerPointE);
        customerPoints.add(Constants.customerPointF);
        customerPoints.add(customerNew.stationPosition);
        assertEquals(customerPoints, customerNew.setCustomerPoints());
    }

    @Test
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

}
