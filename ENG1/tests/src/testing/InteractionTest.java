package testing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import cooks.Cook;
import customers.Customer;
import food.FoodItem;
import food.FoodStack;
import interactions.InputKey;
import interactions.Interactions;
import org.junit.Test;
import org.junit.runner.RunWith;
import stations.*;
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

    @Test
    public void TestCounterCanHoldMultiple(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        CounterStation testStation = new CounterStation(rectangle);
        testStation.setID(Station.StationID.counter);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.meat);
        cook.foodStack.addStack(FoodItem.FoodID.lettuce);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        for (int x = 0; x < 3; x++){
            testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        assertTrue(testStation.foodStack.popStack() == FoodItem.FoodID.meat);
        assertTrue(testStation.foodStack.popStack() == FoodItem.FoodID.lettuce);
        assertTrue(testStation.foodStack.popStack()== FoodItem.FoodID.onionChop);
        assertTrue(cook.foodStack.size() == 0);
    }

    @Test
    public void PantryTestLettuce(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.lettuce);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.lettuce);
    }

    @Test
    public void PantryTestTomato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.tomato);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.tomato);
    }


    @Test
    public void PantryTestOnion(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.onion);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.onion);
    }

    @Test
    public void PantryTestMeat(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.meat);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.meat);
    }

    @Test
    public void PantryTestbottomBun(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.bun);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.bottomBun);
    }

    @Test
    public void PantryTestTopBun(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.bun);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.topBun);
    }

    @Test
    public void PantryTestTopBunThenBottom(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.bun);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.topBun);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.bottomBun,"If the stack has a top bun, and you pick up a bun from the pantry, it should be a bottom bun. This isn't currently the case");
    }

    @Test
    public void TestUsePantryandLettuceChop(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.lettuceChop);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.USE);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.lettuceChop,"Either the item wasn't chopped lettuce or you can no longer use the USE button to interact with the pantry");
    }

    @Test
    public void TestServingStationGetAndSetCustomer(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        customer.randomRecipe();
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        assertTrue(testStation.getCustomer() == customer,"The get/set customer function for servingStation is broken");
    }

    @Test
    public void TestServingStationServeCustomerOnionBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ServingStation testStation = new ServingStation(rectangle);
        testStation.setID(Station.StationID.serving);
        Sprite sprite = new Sprite();
        Customer customer = new Customer(sprite);
        customer.request = ("Onion Burger");
        testStation.setCustomer(customer);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.topBun);
        cook.foodStack.addStack(FoodItem.FoodID.bottomBun);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        testStation.interact(cook, InputKey.InputTypes.USE);
        assertTrue(cook.foodStack.size() == 0, "The cook food stack is not emptied after serving a request");
        //assertFalse(testStation.hasCustomer());
    }

    @Test
    public void TestGetInputKey(){
        InputKey test = new InputKey(InputKey.InputTypes.INSTRUCTIONS, Input.Keys.I);
        assertTrue(test.getKey() == Input.Keys.I, "The getKey function is broken/returns wrong key");
    }

    @Test
    public void TestGetInputType(){
        InputKey test = new InputKey(InputKey.InputTypes.INSTRUCTIONS, Input.Keys.I);
        assertTrue(test.getType() == InputKey.InputTypes.INSTRUCTIONS,"The getType function is broken/returns wrong type");
    }
}
