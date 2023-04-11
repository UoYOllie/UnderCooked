package testing;

import Shop.Gold;
import Shop.ShopItem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import cooks.Cook;
import cooks.CookInteractor;
import cooks.GameEntity;
import customers.Customer;
import customers.CustomerController;
import food.FoodItem;
import food.Recipe;
import helper.Util;
import interactions.InputKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import stations.Pantry;
import stations.PreparationStation;
import stations.ServingStation;
import stations.Station;

import java.util.ArrayList;

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
}
