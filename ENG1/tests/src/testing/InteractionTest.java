package testing;

import com.badlogic.gdx.Gdx;
import cooks.Cook;
import food.FoodItem;
import interactions.InputKey;
import interactions.Interactions;
import org.junit.Test;
import org.junit.runner.RunWith;
import stations.BinStation;
import stations.PreparationStation;
import stations.Station;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;
import java.util.ArrayList;

import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class InteractionTest {

    @Test
    public void TestPutDownItem(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation testStation = new PreparationStation(rectangle);
        testStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuce);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assertTrue(cook.foodStack.size() == 0);
    }

    @Test
    public void TestPickUpItem(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation testStation = new PreparationStation(rectangle);
        testStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuce);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.lettuce);
    }

    @Test
    public void TestStackOnPreparationStation(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation testStation = new PreparationStation(rectangle);
        testStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuce);
        cook.foodStack.addStack(FoodItem.FoodID.tomato);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assertTrue(cook.foodStack.size() == 1);
    }

    @Test
    public void TestPlayerItemStack(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation testStation = new PreparationStation(rectangle);
        testStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuce);
        cook.foodStack.addStack(FoodItem.FoodID.tomato);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.interact(cook,InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.tomato);
    }

    @Test
    public void TestPreparationStationCutLettuce(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation testStation = new PreparationStation(rectangle);
        testStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuce);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        while (testStation.progress < 100){
            testStation.interact(cook, InputKey.InputTypes.USE);
            testStation.update(1);
        }
        testStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.lettuceChop);
    }

    @Test
    public void TestPreparationStationCutTomato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation testStation = new PreparationStation(rectangle);
        testStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.tomato);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        while (testStation.progress < 100){
            testStation.interact(cook, InputKey.InputTypes.USE);
            testStation.update(1);
        }
        testStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.tomatoChop);
    }

    @Test
    public void TestPreparationStationCutOnion(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation testStation = new PreparationStation(rectangle);
        testStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.onion);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        while (testStation.progress < 100){
            testStation.interact(cook, InputKey.InputTypes.USE);
            testStation.update(1);
        }
        testStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.onionChop);
    }

    @Test
    public void TestPreparationStationCookMeat(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation testStation = new PreparationStation(rectangle);
        testStation.setID(Station.StationID.fry);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.meat);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        while (testStation.progress < 100){
            testStation.interact(cook, InputKey.InputTypes.USE);
            testStation.update(1);
        }
        testStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.meatCook);
    }

    @Test
    public void TestBinStationGetsRidOfItem(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        BinStation testStation = new BinStation(rectangle);
        testStation.setID(Station.StationID.bin);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.meat);
        testStation.interact(cook, InputKey.InputTypes.USE);
        assertTrue(cook.foodStack.size() == 0);
        cook.foodStack.addStack(FoodItem.FoodID.meat);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assertTrue(cook.foodStack.size() == 0);
    }
}
