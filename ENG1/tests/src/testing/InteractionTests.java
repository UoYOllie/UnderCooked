package testing;

import com.badlogic.gdx.utils.Array;
import cooks.Cook;
import food.FoodItem;
import interactions.InputKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import stations.*;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class InteractionTests {


    @Test
    // Relates to the FR_PUT_FOOD_DOWN, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_ITEM requirement
    public void TestPutDownItem(){
        //This test works by making a counter and spawning the chef near the counter. Then we add lettuce to the chefs stack and ask him to put it down. We check the chef has no items after attempting put them down
        //The final assertion checks that the counter actually holds the item by checking the food stack of the counter
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        CounterStation testStation = new CounterStation(rectangle);
        testStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.lettuce);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assertTrue(cook.foodStack.size() == 0,"Error: cannot put items of preparation stations");
        assertEquals(testStation.stationFoodStack.peekStack(), FoodItem.FoodID.lettuce,"Error: Counter does not have lettuce after lettuce is given to it");
    }

    @Test
    // Relates to the FR_GET_FOOD, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_ITEM requirement
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
    // Relates to the FR_PUT_FOOD_DOWN, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, UR_ITEM and UR_STATION requirementS
    public void TestStackOnPreparationStation(){
        //This test exercises the fact that counters can only hold one item. A chef and a counter are spawned, then the chef is given a lettuce and a tomato. Afterwards, the chef is given the command to put an item down twice. The test passes if only one thing was put on the counter
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
    // Relates to the FR_GET_FOOD, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_ITEM requirement
    public void TestPlayerItemStack(){
        //This test makes sure the chef has a stack. We make a chef and give him a lettuce and a tomato. He then is told to put down then pick up. If the tomato is the top of the stack, cooks use stacks.
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_COMPLETE_PREP and UR_STATION requirements
    public void TestPreparationStationCutLettuce(){
        //This tests that the player can cut lettuce on the preparation station.
        //This is done by making a chef with lettuce and spawning a preparation station on top of him. Then we ask him to interact with the station and wait till progress is 100% done.
        //Afterwards, we ask him to pick up the result and make sure its chopped lettuce
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_COMPLETE_PREP and UR_STATION requirements
    public void TestPreparationStationCutTomato(){
        //This tests that the player can cut a tomato on the preparation station.
        //This is done by making a chef with a tomato and spawning a preparation station on top of him. Then we ask him to interact with the station and wait till progress is 100% done.
        //Afterwards, we ask him to pick up the result and make sure it is a chopped tomato
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_COMPLETE_PREP and UR_STATION requirements
    public void TestPreparationStationCutOnion(){
        //This tests that the player can cut an onion on the preparation station.
        //This is done by making a chef with an onion and spawning a preparation station on top of him. Then we ask him to interact with the station and wait till progress is 100% done.
        //Afterwards, we ask him to pick up the result and make sure it is a chopped onion
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_COMPLETE_PREP and UR_STATION requirements
    public void TestPreparationStationCookMeat(){
        //This tests that the player can fry meat on the preparation station.
        //This is done by making a chef with a raw meat and spawning a preparation station on top of him. Then we ask him to interact with the station and wait till progress is 100% done.
        //Afterwards, we ask him to pick up the result and make sure it is fried onion
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_COMPLETE_PREP and UR_STATION requirements
    public void TestPreparationStationSetsStateToFinished(){
        //This tests that a preparation station is set to state FINISHED after an interaction.
        //This is done by making a chef fry meat then checking the stations state.
        //This passes if the state is Finished
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_COMPLETE_PREP and UR_STATION requirements
    public void TestPreparationStationAddsResultBackWhenFinishedUse(){
        //This is the same as the previous frying meat test, but tests that the player can also use the USE button to pick up the result.
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirements
    public void TestBinStationGetsRidOfItem(){
        //This tests that a bin gets rid of an item when used. We spawn a cook and a bin on top of each other. Then we give the chef meat.
        //Afterwards, we ask the chef to put the meat down, this then passes if the cook has nothing in his stack.
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirements
    public void TestBinStationWorksWithEmptyStack(){
        //This is the same as the previous test, but makes sure that nothing weird happens if players attempt to interact with the bin with both the foodStack and dishStack empty
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirements
    public void TestBinStationWorksWithEmptyFoodStackButHaveADishStack(){
        //This tests that a player can bin items in their dishStack using the bin.
        //We add items to a cooks dishStack then ask him to put them in the bin in one interaction
        //This passes if both the cooks stacks are size 0
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
    // Relates to the FR_COUNTER, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_STATION requirement
    public void TestCounterCanHoldMultiple(){
        //This test makes sure counters are multi-use. They shouldn't hold more than one item, but we test that after a use, they can be used again.
        //We add to the counter then pop the stack to make sure the item held is correct. This passes if 3 separate items can be added to the counter in three separate occasions
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
    // Relates to the FR_GET_FOOD, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_ITEM requirement
    public void TestPantryLettuce(){
        //This tests that a player can take lettuce from the lettuce pantry
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
    // Relates to the FR_GET_FOOD, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_ITEM requirement
    public void TestPantryTomato(){
        //This tests that a player can take a tomato from the tomato pantry
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
    // Relates to the FR_GET_FOOD, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_ITEM requirement
    public void TestPantryOnion(){
        //This tests that a player can take an onion from the onion pantry
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
    // Relates to the FR_GET_FOOD, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_ITEM requirement
    public void TestPantryMeat(){
        //This tests that a player can take meat from the meat pantry
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
    // Relates to the FR_GET_FOOD, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_ITEM requirement
    public void TestPantryPotato(){
        //This tests that a player can take a potato from the potato pantry
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
    // Relates to the FR_GET_FOOD, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_ITEM requirement
    public void TestPantryDough(){
        //This tests that a player can take dough from the dough pantry
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
    // Relates to the FR_GET_FOOD, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_ITEM requirement
    public void TestPantryBun(){
        //This tests that a player can take bun from the bun pantry
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
    // Relates to the FR_GET_FOOD, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_ITEM requirement
    public void TestPantryColeslaw(){
        //This tests that a player can take coleslaw from the coleslaw pantry
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.coleslaw);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.coleslaw, "Picking up a bun while no bottom bun is your stack should make it a bottom bun. It currently does not");
    }

    @Test
    // Relates to the FR_GET_FOOD, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_ITEM requirement
    public void TestPantryBeans(){
        //This tests that a player can take beans from the beans pantry
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.bakedBeans);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.bakedBeans, "Picking up a bun while no bottom bun is your stack should make it a bottom bun. It currently does not");
    }

    @Test
    // Relates to the FR_GET_FOOD, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS and UR_ITEM requirement
    public void TestPantryCheese(){
        //This tests that a player can take cheese from the cheese pantry
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        Pantry testPantry = new Pantry(rectangle);
        testPantry.setItem(FoodItem.FoodID.cheese);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testPantry.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        testPantry.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.peekStack() == FoodItem.FoodID.cheese, "Picking up a bun while no bottom bun is your stack should make it a bottom bun. It currently does not");
    }


    @Test
    // Relates to the FR_GET_FOOD, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, and UR_ITEM requirement
    public void TestUsePantryAndLettuceChop(){
        //This just tests that you can press USE and use the pantry
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_COMPLETE_PREP and UR_STATION  requirements
    public void TestBakingStationPotatoToBakedPotato(){
        //This tests that a player can bake a potato
        //We spawn a cook and a baking station at the same spot, give the cook a potato then ask him to interact with the station.
        //We then wait till the progress is done, then ask the cook to pick it up. This passes if the cook has a cooked potato at then end.
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_COMPLETE_PREP and UR_STATION  requirements
    public void TestBakingStationDoughToDoughCook(){
        //This tests that a player can bake dough
        //We spawn a cook and a baking station at the same spot, give the cook raw dough then ask him to interact with the station.
        //We then wait till the progress is done, then ask the cook to pick it up. This passes if the cook has cooked dough at then end.
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationMakePlainSalad(){
        //This tests that the player can make a plain salad at an assembly station with chopped lettuce
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    // The following tests to do with making recipes in the assembly station test whether, given the right ingredients, the assembly station returns the expected outcome to the cooks dish stack
    public void TestAssemblyStationMakeTomatoSalad(){
        //This tests that the player can make a tomato salad at an assembly station with chopped lettuce and a chopped tomato
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationMakeOnionSalad(){
        //This tests that the player can make onion salad at an assembly station with chopped lettuce and chopped onion
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationMakeOnionTomatoSalad(){
        //This tests that the player can make onion tomato salad at an assembly station with chopped lettuce, chopped tomato and chopped onion
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationMakePlainPizza(){
        //This tests that a player can make a plain pizza with cheese, tomato sauce and cooked dough
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationMakePlainPotato(){
        //This tests that a player can make a plain potato using a cooked potato.
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationMakeBeansPotato(){
        //This tests that a player can make a beans potato using a cooked potato and cooked beans.
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.foodStack.addStack(FoodItem.FoodID.beansCooked);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.beansCooked);
        foodID.add(FoodItem.FoodID.potatoCook);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a Beans potato");
    }

    @Test
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationMakeColeslawPotato(){
        //This tests that a player can make a coleslaw potato using a cooked potato and coleslaw.
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationMakeCheesePotato(){
        //This tests that a player can make a cheese potato using a cooked potato and cheese.
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationMakeCheeseBeansPotato(){
        //This tests that a player can make a cheese and beans potato using a cooked potato, cheese and cooked beans.
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.foodStack.addStack(FoodItem.FoodID.cheese);
        cook.foodStack.addStack(FoodItem.FoodID.beansCooked);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.cheese);
        foodID.add(FoodItem.FoodID.beansCooked);
        foodID.add(FoodItem.FoodID.potatoCook);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a cheese beans potato");
    }

    @Test
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationMakeBeansColeslawPotato(){
        //This tests that a player can make beans,coleslaw potato using a cooked potato, cooked beans and coleslaw.
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.foodStack.addStack(FoodItem.FoodID.coleslaw);
        cook.foodStack.addStack(FoodItem.FoodID.beansCooked);
        for (int x = cook.foodStack.size(); x >= 0; x--){
            assemblyStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        }
        assemblyStation.interact(cook, InputKey.InputTypes.USE);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        Array<FoodItem.FoodID> foodID = new Array<>();
        foodID.add(FoodItem.FoodID.coleslaw);
        foodID.add(FoodItem.FoodID.beansCooked);
        foodID.add(FoodItem.FoodID.potatoCook);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a beans coleslaw potato");
    }

    @Test
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationMakeBeansColeslawCheesePotato(){
        //This tests that a player can make beans,coleslaw and cheese potato using a cooked potato,coleslaw, cooked beans and cheese.
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.potatoCook);
        cook.foodStack.addStack(FoodItem.FoodID.coleslaw);
        cook.foodStack.addStack(FoodItem.FoodID.beansCooked);
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
        foodID.add(FoodItem.FoodID.beansCooked);
        foodID.add(FoodItem.FoodID.potatoCook);
        foodID.add(FoodItem.FoodID.plate);
        assertTrue(cook.dishStack.getStack().equals(foodID), "Error: Assembly Station does not give the right outcome when given the items to make a beans coleslaw cheese potato");
        foodID.clear();
    }

    @Test
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationPlainBurger(){
        //This tests that a player can make a plain burger using cooked meat and 2 buns.
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationLettuceBurger(){
        //This tests that a player can make a lettuce burger using cooked meat,chopped lettuce and 2 buns.
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
        //These two lines are here since the player stack is limited to 3 items, so we have to do it separately
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationOnionBurger(){
        //This tests that a player can make an onion burger using cooked meat,chopped onion and 2 buns.
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationTomatoBurger(){
        //This tests that a player can make a tomato burger using cooked meat,chopped tomato and 2 buns.
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationLettuceTomatoBurger(){
        //This tests that a player can make a lettuce,tomato burger using cooked meat,chopped lettuce,chopped tomato and 2 buns.
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationLettuceOnionBurger(){
        //This tests that a player can make a lettuce,onion burger using cooked meat,chopped lettuce, chopped onion and 2 buns.
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationTomatoOnionBurger(){
        //This tests that a player can make a tomato,onion burger using cooked meat,chopped tomato,chopped onion and 2 buns.
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationLettuceTomatoOnionBurger(){
        //This tests that a player can make a lettuce,tomato,onion burger using cooked meat,chopped lettuce,chopped tomato,chopped onion and 2 buns.
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationTestMultipleRecipes(){
        //This tests that an assembly station can be used multiple times on separate occasions.
        //It passes if both a plain burger and plain salad have been made by the end
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationHoldsFood(){
        //This tests that you can put things on and take thing of the assembly station
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
    // Relates to the FR_USE_STATION, FR_ITEMS_INTERACT, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ASSEMBLE and UR_STATION requirements
    public void TestAssemblyStationHoldsNothing(){
        //This ensures that nothing weird happens if you try to PICK UP an item on an assembly station when it has no items
        //This passes if nothing happens from the interaction
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        ArrayList<Rectangle> testList = new ArrayList<>();
        AssemblyStation assemblyStation = new AssemblyStation(rectangle);
        testList.add(assemblyStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        assemblyStation.interact(cook, InputKey.InputTypes.PICK_UP);
        assertTrue(cook.foodStack.size() == 0, "Error:The Assembly does not return the right food stack when given nothing to hold, that is then picked up by the player");
    }

    @Test
    // Relates to the FR_USE_STATION, FR_ITEM_INTERACTIONS, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_ASSEMBLE and UR_STATION requirements
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
    // Relates to the FR_USE_STATION, FR_ITEM_INTERACTIONS, FR_INTERACTION, FR_ITEMS_EFFECTS, FR_ITEM_CHANGE, FR_FOOD_FAIL, FR_ASSEMBLE and UR_STATION requirement
    public void TestPreparationStationGivesWaste(){
        //This tests that when you do an incorrect interaction on a preperation station, it results in waste
        Rectangle rectangle = new Rectangle((1500 * 1/8f),(1200 * 1/8f),20,20);
        PreparationStation testStation = new PreparationStation(rectangle);
        testStation.SetTestFlag(1);
        testStation.setID(Station.StationID.cut);
        ArrayList<Rectangle> testList = new ArrayList<>();
        testList.add(testStation.getRectangle());
        Cook cook = new Cook(1500, 1200, 20, 20);
        cook.foodStack.addStack(FoodItem.FoodID.beansCooked);
        testStation.interact(cook, InputKey.InputTypes.PUT_DOWN);
        assertEquals(testStation.stationFoodStack.peekStack(), FoodItem.FoodID.waste,"Error: wrong interaction at a preparation station should result in waste");
        testStation.SetTestFlag(0);
    }

}
