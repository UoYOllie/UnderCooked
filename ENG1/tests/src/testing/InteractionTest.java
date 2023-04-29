package testing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import cooks.Cook;
import food.FoodItem;
import food.FoodStack;
import food.Recipe;
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
    // Relates to the FR_PUT_FOOD_DOWN requirement
    public void TestPutDownItem(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        CounterStation testStation = new CounterStation(rectangle);
        testStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuce);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assertTrue(cook.foodStack.size() == 0,"Error: cannot put items of preparation stations");
    }

    @Test
    // Relates to the FR_GET_FOOD requirement
    public void TestPickUpItem(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        CounterStation testStation = new CounterStation(rectangle);
        testStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuce);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.lettuce,"Error: cannot pick up items from a station");
    }

    @Test
    // Relates to the FR_PUT_FOOD_DOWN requirement
    public void TestStackOnPreparationStation(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation testStation = new PreparationStation(rectangle);
        testStation.setID(Station.StationID.cut);
        testStation.SetTestFlag(1);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuce);
        cook.foodStack.addStack(FoodItem.FoodID.tomato);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assertTrue(cook.foodStack.size() == 1, "Error: Preparation stations can now hold " + cook.foodStack.size() + " items (if zero, that is max. Greater than 1 means at least 0) instead of the previous 1.");
        testStation.SetTestFlag(0);
    }

    @Test
    // Relates to the FR_GET_FOOD requirement
    public void TestPlayerItemStack(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        CounterStation testStation = new CounterStation(rectangle);
        testStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuce);
        cook.foodStack.addStack(FoodItem.FoodID.tomato);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.interact(cook,InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.tomato,"Error: picking up an item from a station no longer puts it at the top of the stack");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestPreparationStationCutLettuce(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation testStation = new PreparationStation(rectangle);
        testStation.SetTestFlag(1);
        testStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuce);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        while (testStation.progress < 100){
            testStation.update(1);
        }
        testStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.lettuceChop, "Error:The process of chopping lettuce no longer results in chopped lettuce at the end. PreperationStation is therefore broken");
        testStation.SetTestFlag(0);
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestPreparationStationCutTomato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation testStation = new PreparationStation(rectangle);
        testStation.SetTestFlag(1);
        testStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.tomato);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        while (testStation.progress < 100){
            testStation.update(1);
        }
        testStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.tomatoChop, "Error:The process of cutting tomatoes no longer results in chopped tomatoes at the end. PreperationStation is therefore broken");
        testStation.SetTestFlag(0);
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestPreparationStationCutOnion(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation testStation = new PreparationStation(rectangle);
        testStation.SetTestFlag(1);
        testStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.onion);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        while (testStation.progress < 100){
            testStation.update(1);
        }
        testStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.onionChop,"Error:The process of chopping no longer results in chopped onions at the end. PreperationStation is therefore broken");
        testStation.SetTestFlag(0);
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestPreparationStationCookMeat(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation testStation = new PreparationStation(rectangle);
        testStation.SetTestFlag(1);
        testStation.setID(Station.StationID.fry);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.meat);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        while (testStation.progress < 100){
            testStation.update(1);
        }
        testStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.meatCook,"Error:The process of cooking meat no longer results in cooked meat at the end. PreperationStation is therefore broken");
        testStation.SetTestFlag(0);
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestPreparationStationSetsStatetoFinished(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation testStation = new PreparationStation(rectangle);
        testStation.SetTestFlag(1);
        testStation.setID(Station.StationID.fry);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        testStation.progress = 100;
        testStation.inUse = true;
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.meat);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.update(1);
        assertEquals(testStation.state, PreparationStation.StationState.FINISHED);
        testStation.SetTestFlag(0);
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestPreparationStationAddsResultBackWhenFinishedUse(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation testStation = new PreparationStation(rectangle);
        testStation.SetTestFlag(1);
        testStation.setID(Station.StationID.fry);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.meat);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        testStation.progress = 100;
        testStation.inUse = true;
        testStation.interact(cook, InputKey.InputTypes.USE);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.meatCook);
        testStation.SetTestFlag(0);
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestBinStationGetsRidOfItem(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        BinStation testStation = new BinStation(rectangle);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.meat);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assertTrue(cook.foodStack.size() == 0,"Error:Using a bin using the PUT_DOWN input type does not work");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestBinStationWorksWithEmptyStack(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        BinStation testStation = new BinStation(rectangle);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.clearStack();
        cook.dishStack.clearStack();
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assertTrue(cook.foodStack.size() == 0,"Error:Using a bin with an empty dishStack and foodStack does not result in an empty food stack");
        assertTrue(cook.dishStack.size() == 0,"Error:Using a bin with an empty dishStack and foodStack does not result in an empty dish stack");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestBinStationWorksWithEmptyFoodStackButHaveADishStack(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        BinStation testStation = new BinStation(rectangle);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.clearStack();
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.lettuceChop);
        foodID.add(FoodItem.FoodID.plate);
        cook.dishStack.setStack(foodID);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assertTrue(cook.foodStack.size() == 0,"Error:Using a bin with an empty dishStack and foodStack does not result in an empty food stack");
        assertTrue(cook.dishStack.size() == 0,"Error:Using a bin with an empty dishStack and foodStack does not result in an empty dish stack");
    }

    @Test
    // Relates to the FR_COUNTER requirement
    public void TestCounterCanHoldMultiple(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        CounterStation testStation = new CounterStation(rectangle);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.meat);
        cook.foodStack.addStack(FoodItem.FoodID.lettuce);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assertTrue(testStation.stationFoodStack.popStack() == FoodItem.FoodID.onionChop, "Error: counter no longer holds a single item correctly");
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assertTrue(testStation.stationFoodStack.popStack() == FoodItem.FoodID.lettuce,"Error: counter no longer holds a single item correctly after another item has been taken off");
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assertTrue(testStation.stationFoodStack.popStack()== FoodItem.FoodID.meat,"Error: counter no longer holds a single item correctly after another 2 items have been taken off");
        assertTrue(cook.foodStack.size() == 0,"Error: popping an item from a counter does not remove it from the counter");
    }

    @Test
    // Relates to the FR_GET_FOOD requirement
    public void PantryTestLettuce(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.lettuce);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.lettuce, "Picking up lettuce from a lettuce pantry currently does not result on it being placed at the top of the player stack (or possibly at all)");
    }

    @Test
    // Relates to the FR_GET_FOOD requirement
    public void PantryTestTomato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.tomato);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.tomato,"Picking up a tomato from a tomato pantry currently does not result on it being placed at the top of the player stack (or possibly at all)");
    }


    @Test
    // Relates to the FR_GET_FOOD requirement
    public void PantryTestOnion(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.onion);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.onion,"Picking up an onion from a onion pantry currently does not result on it being placed at the top of the player stack (or possibly at all)");
    }

    @Test
    // Relates to the FR_GET_FOOD requirement
    public void PantryTestMeat(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.meat);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.meat, "Picking up meat from a meat pantry currently does not result on it being placed at the top of the player stack (or possibly at all)");
    }
    @Test
    // Relates to the FR_GET_FOOD requirement
    public void PantryTestPotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.potato);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.potato, "Picking up a potato from a potato pantry currently does not result on it being placed at the top of the player stack (or possibly at all)");
    }
    @Test
    // Relates to the FR_GET_FOOD requirement
    public void PantryTestDough(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.dough);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.dough, "Picking up a dough from a dough pantry currently does not result on it being placed at the top of the player stack (or possibly at all)");
    }

    @Test
    // Relates to the FR_GET_FOOD requirement
    public void PantryTestBun(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.bun);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.bun, "Picking up a bun while no bottom bun is your stack should make it a bottom bun. It currently does not");
    }


    @Test
    // Relates to the FR_GET_FOOD requirement
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
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestBakingStationPotatoToBakedPotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation preparationStation = new PreparationStation(rectangle);
        preparationStation.SetTestFlag(1);
        preparationStation.setID(Station.StationID.bake);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(preparationStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.potato);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        while (preparationStation.progress < 100){
            preparationStation.update(1);
        }
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.potatoCook, "Error:The process of baking potatoes no longer results in chopped tomatoes at the end. PreperationStation is therefore broken");
        preparationStation.SetTestFlag(0);
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestBakingStationDoughToDoughCook(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation preparationStation = new PreparationStation(rectangle);
        preparationStation.SetTestFlag(1);
        preparationStation.setID(Station.StationID.bake);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(preparationStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.dough);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        while (preparationStation.progress < 100){
            preparationStation.update(1);
        }
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.doughCook, "Error:The process of baking dough no longer results in chopped tomatoes at the end. PreperationStation is therefore broken");
        preparationStation.SetTestFlag(0);
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationMakePlainSalad(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.lettuceChop);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a Plain salad");
        foodID.clear();
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    //The following tests to do with making recipes in the assembly station test whether, given the right ingredients, the assembly station returns the expected outcome to the cooks dish stack
    public void TestAssemblyStationMakeTomatoSalad(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.tomatoChop);
        foodID.add(FoodItem.FoodID.lettuceChop);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a Tomato salad");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationMakeOnionSalad(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.onionChop);
        foodID.add(FoodItem.FoodID.lettuceChop);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a Onion salad");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationMakeOnionTomatoSalad(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.onionChop);
        foodID.add(FoodItem.FoodID.tomatoChop);
        foodID.add(FoodItem.FoodID.lettuceChop);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a Tomato Onion salad");
    }
    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationMakePlainPizza(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.cheese);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoSauce);
        cook.foodStack.addStack(FoodItem.FoodID.doughCook);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.cheese);
        foodID.add(FoodItem.FoodID.tomatoSauce);
        foodID.add(FoodItem.FoodID.doughCook);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a Plain pizza");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationMakePepperoniPizza(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.cheese);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoSauce);
        cook.foodStack.addStack(FoodItem.FoodID.doughCook);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        //These two lines are here since the player stack is limited to 3 items so we have to do it separately
        cook.foodStack.addStack(FoodItem.FoodID.pepperoni);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);

        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.cheese);
        foodID.add(FoodItem.FoodID.pepperoni);
        foodID.add(FoodItem.FoodID.tomatoSauce);
        foodID.add(FoodItem.FoodID.doughCook);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a Pepperoni pizza");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationMakeOnionPizza(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.cheese);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoSauce);
        cook.foodStack.addStack(FoodItem.FoodID.doughCook);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        //These two lines are here since the player stack is limited to 3 items so we have to do it separately
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);

        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.cheese);
        foodID.add(FoodItem.FoodID.onionChop);
        foodID.add(FoodItem.FoodID.tomatoSauce);
        foodID.add(FoodItem.FoodID.doughCook);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make an onion pizza");
    }
    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationMakePlainPotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.potatoCook);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a Plain potato");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationMakeBeansPotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.foodStack.addStack(FoodItem.FoodID.bakedBeans);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.bakedBeans);
        foodID.add(FoodItem.FoodID.potatoCook);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a Beans potato");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationMakeColeslawPotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.foodStack.addStack(FoodItem.FoodID.coleslaw);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.coleslaw);
        foodID.add(FoodItem.FoodID.potatoCook);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a coleslaw potato");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationMakeCheesePotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.foodStack.addStack(FoodItem.FoodID.cheese);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.cheese);
        foodID.add(FoodItem.FoodID.potatoCook);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a cheese potato");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationMakeCheeseBeansPotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.foodStack.addStack(FoodItem.FoodID.cheese);
        cook.foodStack.addStack(FoodItem.FoodID.bakedBeans);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.cheese);
        foodID.add(FoodItem.FoodID.bakedBeans);
        foodID.add(FoodItem.FoodID.potatoCook);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a cheese beans potato");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationMakeBeansColeslawPotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.foodStack.addStack(FoodItem.FoodID.coleslaw);
        cook.foodStack.addStack(FoodItem.FoodID.bakedBeans);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.coleslaw);
        foodID.add(FoodItem.FoodID.bakedBeans);
        foodID.add(FoodItem.FoodID.potatoCook);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a beans coleslaw potato");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationMakeBeansColeslawCheesePotato(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.foodStack.addStack(FoodItem.FoodID.coleslaw);
        cook.foodStack.addStack(FoodItem.FoodID.bakedBeans);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        //These two lines are here since the player stack is limited to 3 items so we have to do it separately
        cook.foodStack.addStack(FoodItem.FoodID.cheese);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);

        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.cheese);
        foodID.add(FoodItem.FoodID.coleslaw);
        foodID.add(FoodItem.FoodID.bakedBeans);
        foodID.add(FoodItem.FoodID.potatoCook);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a beans coleslaw cheese potato");
        foodID.clear();
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationPlainBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.meatCook);
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a plain burger");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationLettuceBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        //These two lines are here since the player stack is limited to 3 items so we have to do it separately
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);


        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.lettuceChop);
        foodID.add(FoodItem.FoodID.meatCook);
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a lettuce burger");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationOnionBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        // These two lines are here since the player stack is limited to 3 items, so we have to do it separately
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);


        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.onionChop);
        foodID.add(FoodItem.FoodID.meatCook);
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a onion burger");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationTomatoBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        //These two lines are here since the player stack is limited to 3 items so we have to do it separately
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);


        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.tomatoChop);
        foodID.add(FoodItem.FoodID.meatCook);
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a tomato burger");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationLettuceTomatoBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        //These 5 lines are here since the player stack is limited to 3 items so we have to do it separately
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }

        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.tomatoChop);
        foodID.add(FoodItem.FoodID.lettuceChop);
        foodID.add(FoodItem.FoodID.meatCook);
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a lettuce tomato burger");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationLettuceOnionBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        //These 5 lines are here since the player stack is limited to 3 items so we have to do it separately
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }

        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.onionChop);
        foodID.add(FoodItem.FoodID.lettuceChop);
        foodID.add(FoodItem.FoodID.meatCook);
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a lettuce onion burger");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationTomatoOnionBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        //These 5 lines are here since the player stack is limited to 3 items so we have to do it separately
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }

        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.onionChop);
        foodID.add(FoodItem.FoodID.tomatoChop);
        foodID.add(FoodItem.FoodID.meatCook);
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a tomato onion burger");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationLettuceTomatoOnionBurger(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        //These Three lines are here since the player stack is limited to 3 items so we have to do it separately
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }

        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.onionChop);
        foodID.add(FoodItem.FoodID.tomatoChop);
        foodID.add(FoodItem.FoodID.lettuceChop);
        foodID.add(FoodItem.FoodID.meatCook);
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a lettuce tomato onion burger");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationTestMultipleRecipes(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        ServingStation servingStation = new ServingStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        testList.add(servingStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        cook.foodStack.addStack(FoodItem.FoodID.bun);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.meatCook);
        foodID.add(FoodItem.FoodID.bun);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a plain burger");

        servingStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        foodID.clear();
        foodID.add(FoodItem.FoodID.lettuceChop);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station Is not handling multiple recipes correctly");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationHoldsFood(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.lettuceChop, "Error:The Assembly does not return the right food stack when given items to hold, that are then picked up by the player");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationHoldsNothing(){
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.size() == 0, "Error:The Assembly does not return the right food stack when given nothing to hold, that is then picked up by the player");
    }

    @Test
    // Relates to the FR_USE_STATION and FR_INTERACTION requirements
    public void TestAssemblyStationWrongRecipe(){
        //This tests whether a wrong recipe is handled correctly in the assembly station
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.size() == 2 && cook.dishStack.size() == 0, "Error:The Assembly does not return the right food stack when given the wrong recipe, that is then picked up by the player");
    }
    @Test
    // Relates to the FR_USE_STATION requirement
    public void TestFailCaseChoppingChoppedItem(){
        //This tests whether the player is able to chop already chopped items (not meant to be able to)
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation preparationStation = new PreparationStation(rectangle);
        preparationStation.SetTestFlag(1);
        preparationStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(preparationStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        preparationStation.interact(cook, InputKey.InputTypes.USE);
        preparationStation.update(1);
        assertFalse(preparationStation.progress > 0, "Error: Using a preparation station with an already chopped lettuce results in its progress value changing");
        assertFalse(preparationStation.inUse, "Error: Preparation station is able to be used with an already chopped lettuce");
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.lettuceChop, "Error: Trying to chop an already chopped lettuce results in a different food item being returned");

        cook.foodStack.clearStack();
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        preparationStation.interact(cook, InputKey.InputTypes.USE);
        preparationStation.update(1);
        assertFalse(preparationStation.progress > 0, "Error: Using a preparation station with an already chopped tomato results in its progress value changing");
        assertFalse(preparationStation.inUse, "Error: Preparation station is able to be used with an already chopped tomato");
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.tomatoChop, "Error: Trying to chop an already chopped tomato results in a different food item being returned");

        cook.foodStack.clearStack();
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        preparationStation.interact(cook, InputKey.InputTypes.USE);
        preparationStation.update(1);
        assertFalse(preparationStation.progress > 0, "Error: Using a preparation station with an already chopped onion results in its progress value changing");
        assertFalse(preparationStation.inUse, "Error: Preparation station is able to be used with an already chopped onion");
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.onionChop, "Error: Trying to chop an already chopped onion results in a different food item being returned");
        preparationStation.SetTestFlag(0);
    }

    @Test
    // Relates to the FR_USE_STATION requirement
    public void TestFailCaseChoppingBakedItem(){
        //This tests whether the player is able to chop baked items other than the ones we want them to be able to chop
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation preparationStation = new PreparationStation(rectangle);
        preparationStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(preparationStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.doughCook);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        preparationStation.interact(cook, InputKey.InputTypes.USE);
        preparationStation.update(1);
        assertFalse(preparationStation.progress > 0, "Error: Using a preparation station with cooked dough results in its progress value changing");
        assertFalse(preparationStation.inUse, "Error: Preparation station is able to be used with cooked dough");
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.doughCook, "Error: Trying to chop cooked dough results in a different food item being returned");

        cook.foodStack.clearStack();
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        preparationStation.interact(cook, InputKey.InputTypes.USE);
        preparationStation.update(1);
        assertFalse(preparationStation.progress > 0, "Error: Using a preparation station with a cooked potato results in its progress value changing");
        assertFalse(preparationStation.inUse, "Error: Preparation station is able to be used with a cooked potato");
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.potatoCook, "Error: Trying to chop a cooked potato results in a different food item being returned");
    }

    @Test
    // Relates to the FR_USE_STATION requirement
    public void TestFailCaseChoppingFriedItem(){
        // This checks whether the player is able to chop fried items that we don't want them to be able to
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation preparationStation = new PreparationStation(rectangle);
        preparationStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(preparationStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        preparationStation.interact(cook, InputKey.InputTypes.USE);
        preparationStation.update(1);
        assertFalse(preparationStation.progress > 0, "Error: Using a preparation station with cooked meat results in its progress value changing");
        assertFalse(preparationStation.inUse, "Error: Preparation station is able to be used with cooked meat");
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.meatCook, "Error: Trying to chop cooked meat results in a different food item being returned");
    }

    @Test
    // Relates to the FR_USE_STATION requirement
    public void TestFailCaseFryingFriedItem(){
        //This checks if the player is able to fry fried items that we don't intend them to
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation preparationStation = new PreparationStation(rectangle);
        preparationStation.setID(Station.StationID.fry);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(preparationStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        preparationStation.interact(cook, InputKey.InputTypes.USE);
        preparationStation.update(1);
        assertFalse(preparationStation.progress > 0, "Error: Using a preparation station with cooked meat results in its progress value changing");
        assertFalse(preparationStation.inUse, "Error: Preparation station is able to be used with cooked meat");
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.meatCook, "Error: Trying to fry cooked meat results in a different food item being returned");
    }

    @Test
    // Relates to the FR_USE_STATION requirement
    public void TestFailCaseFryingChoppedItem(){
        //This test will check whether chopped items we don't want the player to be able to fry are fry-able
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation preparationStation = new PreparationStation(rectangle);
        preparationStation.setID(Station.StationID.fry);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(preparationStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        preparationStation.interact(cook, InputKey.InputTypes.USE);
        preparationStation.update(0);
        assertFalse(preparationStation.progress > 0, "Error: Using a preparation station with a chopped lettuce results in its progress value changing");
        assertFalse(preparationStation.inUse, "Error: Preparation station is able to \"fry\" an already chopped lettuce");
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.lettuceChop, "Error: Trying to fry a chopped lettuce results in a different food item being returned");

        cook.foodStack.clearStack();
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        preparationStation.interact(cook, InputKey.InputTypes.USE);
        preparationStation.update(0);
        assertFalse(preparationStation.progress > 0, "Error: Using a preparation station with a chopped onion results in its progress value changing");
        assertFalse(preparationStation.inUse, "Error: Preparation station is able to \"fry\" an already chopped onion");
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.onionChop, "Error: Trying to fry a chopped onion results in a different food item being returned");
    }

    @Test
    // Relates to the FR_USE_STATION requirement
    public void TestFailCaseFryingBakedItem(){
        //This test makes sure the player isn't able to fry baked items that we don't intend them to fry
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation preparationStation = new PreparationStation(rectangle);
        preparationStation.setID(Station.StationID.fry);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(preparationStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        preparationStation.interact(cook, InputKey.InputTypes.USE);
        preparationStation.update(1);
        assertFalse(preparationStation.progress > 0, "Error: Using a preparation station with a cooked potato results in its progress value changing");
        assertFalse(preparationStation.inUse, "Error: Preparation station is able to \"fry\" an already cooked potato");
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.potatoCook, "Error: Trying to fry an already cooked potato results in a different food item being returned");

        cook.foodStack.clearStack();
        cook.foodStack.addStack(FoodItem.FoodID.doughCook);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        preparationStation.interact(cook, InputKey.InputTypes.USE);
        preparationStation.update(1);
        assertFalse(preparationStation.progress > 0, "Error: Using a preparation station with cooked dough results in its progress value changing");
        assertFalse(preparationStation.inUse, "Error: Preparation station is able to \"fry\" already cooked dough");
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.doughCook, "Error: Trying to fry cooked dough results in a different food item being returned");
    }

    @Test
    // Relates to the FR_USE_STATION requirement
    public void TestFailCaseBakingBakedItem(){
        //This test makes sure that players can't bake items twice, that they aren't supposed to be able to.
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation preparationStation = new PreparationStation(rectangle);
        preparationStation.setID(Station.StationID.bake);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(preparationStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        preparationStation.interact(cook, InputKey.InputTypes.USE);
        preparationStation.update(1);
        assertFalse(preparationStation.progress > 0, "Error: Using a preparation station with a cooked potato results in its progress value changing");
        assertFalse(preparationStation.inUse, "Error: Preparation station is able to \"bake\" an already cooked potato");
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.potatoCook, "Error: Trying to bake a cooked potato results in a different food item being returned");

        cook.foodStack.clearStack();
        cook.foodStack.addStack(FoodItem.FoodID.doughCook);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        preparationStation.interact(cook, InputKey.InputTypes.USE);
        preparationStation.update(1);
        assertFalse(preparationStation.progress > 0, "Error: Using a preparation station with cooked dough results in its progress value changing");
        assertFalse(preparationStation.inUse, "Error: Preparation station is able to \"bake\" already cooked dough");
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.doughCook, "Error: Trying to bake cooked dough results in a different food item being returned");
    }

    @Test
    // Relates to the FR_USE_STATION requirement
    public void TestFailCaseBakingChoppedItem(){
        //This test makes sure the player isn't able to bake chopped items, unless its intended
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation preparationStation = new PreparationStation(rectangle);
        preparationStation.setID(Station.StationID.bake);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(preparationStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuceChop);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        preparationStation.interact(cook, InputKey.InputTypes.USE);
        preparationStation.update(1);
        assertFalse(preparationStation.progress > 0, "Error: Using a preparation station with a chopped lettuce results in its progress value changing");
        assertFalse(preparationStation.inUse, "Error: Preparation station is able to \"bake\" already chopped lettuce");
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.lettuceChop, "Error: Trying to bake an already chopped lettuce results in a different food item being returned");

        cook.foodStack.clearStack();
        cook.foodStack.addStack(FoodItem.FoodID.tomatoChop);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        preparationStation.interact(cook, InputKey.InputTypes.USE);
        preparationStation.update(1);
        assertFalse(preparationStation.progress > 0, "Error: Using a preparation station with a chopped tomato results in its progress value changing");
        assertFalse(preparationStation.inUse, "Error: Preparation station is able to \"bake\" already chopped tomato");
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.tomatoChop, "Error: Trying to bake an already chopped tomato results in a different food item being returned");

        cook.foodStack.clearStack();
        cook.foodStack.addStack(FoodItem.FoodID.onionChop);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        preparationStation.interact(cook, InputKey.InputTypes.USE);
        preparationStation.update(1);
        assertFalse(preparationStation.progress > 0, "Error: Using a preparation station with a chopped onion results in its progress value changing");
        assertFalse(preparationStation.inUse, "Error: Preparation station is able to \"bake\" already chopped onion");
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.onionChop, "Error: Trying to bake an already chopped onion results in a different food item being returned");
    }

    @Test
    // Relates to the FR_USE_STATION requirement
    public void TestFailCaseBakingFriedItem(){
        //This test makes sure the player isn't able to bake fried items, unless its intended
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation preparationStation = new PreparationStation(rectangle);
        preparationStation.setID(Station.StationID.bake);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(preparationStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.meatCook);
        preparationStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        preparationStation.interact(cook, InputKey.InputTypes.USE);
        preparationStation.update(1);
        assertFalse(preparationStation.progress > 0, "Error: Using a preparation station with cooked meat results in its progress value changing");
        assertFalse(preparationStation.inUse, "Error: Preparation station is able to \"bake\" already cooked meat");
        preparationStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.meatCook, "Error: Trying to bake already cooked meat results in a different food item being returned");
    }


}
