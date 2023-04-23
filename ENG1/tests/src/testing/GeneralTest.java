package testing;

import Shop.Gold;
import Shop.ShopItem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import cooks.*;
import customers.Customer;
import customers.CustomerController;
import food.FoodItem;
import food.Recipe;
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
public class GeneralTest {

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

    /*
    @Test
    public void testGameEntityGetWidth(){
        GameEntity gameEntity = new GameEntity(20,21,22,23){

            @Override
            public void update(float delta) {

            }

            @Override
            public void render(SpriteBatch batch) {

            }

            @Override
            public void renderDebug(SpriteBatch batch) {

            }

            @Override
            public void renderShape(ShapeRenderer shape) {

            }

            @Override
            public void renderShapeDebug(ShapeRenderer shape) {

            }
        };
        assertTrue(gameEntity.getWidth() == gameEntity.width);
    }

    @Test
    public void testGameEntityGetHeight(){
        GameEntity gameEntity = new GameEntity(20,21,22,23){

            @Override
            public void update(float delta) {

            }

            @Override
            public void render(SpriteBatch batch) {

            }

            @Override
            public void renderDebug(SpriteBatch batch) {

            }

            @Override
            public void renderShape(ShapeRenderer shape) {

            }

            @Override
            public void renderShapeDebug(ShapeRenderer shape) {

            }
        };
        assertTrue(gameEntity.getHeight() == gameEntity.height);
    }

    @Test
    public void testGameEntityGetX(){
        GameEntity gameEntity = new GameEntity(20,21,22,23){

            @Override
            public void update(float delta) {

            }

            @Override
            public void render(SpriteBatch batch) {

            }

            @Override
            public void renderDebug(SpriteBatch batch) {

            }

            @Override
            public void renderShape(ShapeRenderer shape) {

            }

            @Override
            public void renderShapeDebug(ShapeRenderer shape) {

            }
        };
        assertTrue(gameEntity.x == gameEntity.x);
    }

    @Test
    public void testGameEntityGetY(){
        GameEntity gameEntity = new GameEntity(20,21,22,23){

            @Override
            public void update(float delta) {

            }

            @Override
            public void render(SpriteBatch batch) {

            }

            @Override
            public void renderDebug(SpriteBatch batch) {

            }

            @Override
            public void renderShape(ShapeRenderer shape) {

            }

            @Override
            public void renderShapeDebug(ShapeRenderer shape) {

            }
        };
        assertTrue(gameEntity.y == gameEntity.y);
    }

    @Test
    public void testGameEntityGetRectangle(){
        GameEntity gameEntity = new GameEntity(20,21,22,23){

            @Override
            public void update(float delta) {

            }

            @Override
            public void render(SpriteBatch batch) {

            }

            @Override
            public void renderDebug(SpriteBatch batch) {

            }

            @Override
            public void renderShape(ShapeRenderer shape) {

            }

            @Override
            public void renderShapeDebug(ShapeRenderer shape) {

            }
        };
        assertTrue(gameEntity.getRectangle() == gameEntity.rectangle);
    }
    **/
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

    @Test
    public void TestServingStationGetCustomerX(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
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
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        assertTrue(testStation.getX() == testStation.rectangle.x + 32,"GetX no longer returns the x position of the serving station when no customers are assigned");
    }

    // Relates to the FR_SERVE requirement
    @Test
    public void TestServingStationGetYWithoutCustomer(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.testFlag = 1;
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        assertTrue(testStation.getY() == testStation.rectangle.y + 96,"GetY no longer returns the y position of the serving station when no customers are assigned");
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
    // Relates to the FR_DISH_SERVE requirement
    public void TestServingStationGetAndSetCustomer(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        customer.randomRecipe();
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        assertTrue(testStation.getCustomer() == customer,"The get/set customer function for servingStation is broken");
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
    public void testsgetMapObstacles(){
        PreparationStation preparationStation = new PreparationStation(new Rectangle(1,2,3,4));
        ArrayList<Rectangle> finalList = new ArrayList<>();
        finalList.add(preparationStation.getRectangle());
        MapHelper helper = new MapHelper(finalList,new ArrayList<Station>(),new ArrayList<Station>());
        assertEquals(helper.getMapObstacles(), finalList , "Error: The MapHelper utility function getMapObstacles does not return the right list");
    }

    @Test
    public void testsgetMapStations(){
        PreparationStation preparationStation = new PreparationStation(new Rectangle(1,2,3,4));
        ArrayList<Station> finalList = new ArrayList<>();
        finalList.add(preparationStation);
        MapHelper helper = new MapHelper(new ArrayList<Rectangle>(),finalList,new ArrayList<Station>());
        assertEquals(helper.getMapStations(), finalList , "Error: The MapHelper utility function getMapStations does not return the right list");
    }

    @Test
    public void testsgetServingStationNewList(){
        ServingStationNew servingStationNew = new ServingStationNew(new Rectangle(1,2,3,4));
        ArrayList<Station> finalList = new ArrayList<>();
        finalList.add(servingStationNew);
        MapHelper helper = new MapHelper(new ArrayList<Rectangle>(),new ArrayList<Station>(),finalList);
        assertEquals(helper.getServingStationNewList(), finalList , "Error: The MapHelper utility function getServingStationsNew does not return the right list");
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
}
