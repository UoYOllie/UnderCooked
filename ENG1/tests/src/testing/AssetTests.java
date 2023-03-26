package testing;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class AssetTests {

    // They all relate to the UR_GRAPHIC requirement
    // The following are tests to do with checking that the assets in the cooks folder still remain
    @Test
    public void testChefHoldDownAssetExists(){
        assertTrue(Gdx.files.internal("cooks/hold down.png").exists(), "This test will only pass if hold down.png is present");
    }

    @Test
    public void testChefHoldLeftAssetExists(){
        assertTrue(Gdx.files.internal("cooks/hold left.png").exists(), "This test will only pass if hold left.png is present");
    }

    @Test
    public void testChefHoldRightAssetExists(){
        assertTrue(Gdx.files.internal("cooks/hold right.png").exists(), "This test will only pass if hold right.png is present");
    }

    @Test
    public void testChefHoldUpAssetExists(){
        assertTrue(Gdx.files.internal("cooks/hold up.png").exists(), "This test will only pass if hold up.png is present");
    }
    @Test
    public void testChefControlAssetExists(){
        assertTrue(Gdx.files.internal("cooks/control.png").exists(), "This test will only pass if control.png is present");
    }

    @Test
    public void testChefNormalDownAssetExists(){
        assertTrue(Gdx.files.internal("cooks/normal down.png").exists(), "This test will only pass if normal down.png is present");
    }

    @Test
    public void testChefNormalLeftAssetExists(){
        assertTrue(Gdx.files.internal("cooks/normal left.png").exists(), "This test will only pass if normal left.png is present");
    }

    @Test
    public void testChefNormalRightAssetExists(){
        assertTrue(Gdx.files.internal("cooks/normal right.png").exists(), "This test will only pass if normal right.png is present");
    }

    @Test
    public void testChefNormalUpAssetExists(){
        assertTrue(Gdx.files.internal("cooks/normal up.png").exists(), "This test will only pass if normal up.png is present");
    }

    @Test
    public void testChefTestDummyAssetExists(){
        assertTrue(Gdx.files.internal("cooks/testdummy.png").exists(),"This test will only pass if testdummy.png exists");
    }

    @Test
    public void testPlayerAtlasAssetExists(){
        assertTrue(Gdx.files.internal("cooks/cook atlas.tpproj").exists(), "This test will only pass if cook atlas.tpproj is present");
    }

    //The following are tests to do with checking that assets in the customers folder remain
    @Test
    public void testCustomerAssetExists(){
        assertTrue(Gdx.files.internal("customers/Customer.png").exists(),"This test will only pass if Customer.png exists in the customers folder");
        assertTrue(Gdx.files.internal("customers/customer.tpproj").exists(),"This tests that customer.tpproj exists");
    }

    //The following test that all assets in the atlas folder exist

    @Test
    public void testAtlasCustomerAssetExists(){
        assertTrue(Gdx.files.internal("atlas/customer.png").exists(),"This test will only pass if customer.png exists in the atlas folder");
        assertTrue(Gdx.files.internal("atlas/customer.atlas").exists(),"This will only pass if customer.atlas exists");
    }

    @Test
    public void testAtlasCookAssetExists(){
        assertTrue(Gdx.files.internal("atlas/player.png").exists(),"This test will only pass if player.png exists in the atlas folder");
        assertTrue(Gdx.files.internal("atlas/player.atlas").exists(),"This will only pass if customer.atlas exists");
    }

    @Test
    public void testAtlasFoodAssetExists(){
        assertTrue(Gdx.files.internal("atlas/food.png").exists(),"This test will only pass if food.png exists in the atlas folder");
        assertTrue(Gdx.files.internal("atlas/food.atlas").exists(),"This will only pass if food.atlas exists");
    }

    @Test
    public void testAtlasStationAssetExists(){
        assertTrue(Gdx.files.internal("atlas/station.png").exists(),"This test will only pass if station.png exists in the atlas folder");
        assertTrue(Gdx.files.internal("atlas/station.atlas").exists(),"This will only pass if station.atlas exists");
    }

    //The following test that all assets in the Individual_Stations folder exist

    @Test
    public void testBinAssetExists(){
        assertTrue(Gdx.files.internal("Individual_Stations/bin.png").exists(),"This will only pass if bin.png exists");
    }

    @Test
    public void testCuttingStationAssetExists(){
        assertTrue(Gdx.files.internal("Individual_Stations/Cutting_Station.png").exists(),"This will only pass if CuttingStation.png exists");
    }

    @Test
    public void FryerAssetExists(){
        assertTrue(Gdx.files.internal("Individual_Stations/Fryer.png").exists(),"This will only pass if Fryer.png exists");
    }

    @Test
    public void testStationsTpprojAssetExists(){
        assertTrue(Gdx.files.internal("Individual_Stations/stations.tpproj").exists(),"This will only pass if stations.tpproj exists");
    }

    @Test
    public void testTableAssetExists(){
        assertTrue(Gdx.files.internal("Individual_Stations/Table.png").exists(),"This will only pass if Table.png exists");
    }
    @Test
    public void testTableWithChairsAssetExists(){
        assertTrue(Gdx.files.internal("Individual_Stations/TableWithChairs.png").exists(),"This will only pass if TableWithChairs.png exists");
    }

    //The following test that all assets in the Maps folder exist

    @Test
    public void testStartMenuBackgroundAssetExists(){
        assertTrue(Gdx.files.internal("Maps/StartMenuBackground.png").exists(),"This will only pass if StartMenuBackground.png exists");
    }

    @Test
    public void testStationsPngAssetExists(){
        assertTrue(Gdx.files.internal("Maps/Stations.png").exists(),"This will only pass if Stations.png exists");
    }

    @Test
    public void testStationsTsxAssetExists(){
        assertTrue(Gdx.files.internal("Maps/Stations.tsx").exists(),"This will only pass if Stations.tsx exists");
    }

    @Test
    public void testStationsXcfAssetExists(){
        assertTrue(Gdx.files.internal("Maps/Stations.xcf").exists(),"This will only pass if Stations.xcf exists");
    }

    @Test
    public void testStationsMapPngAssetExists(){
        assertTrue(Gdx.files.internal("Maps/StationsMap.png").exists(),"This will only pass if StationsMap.png exists");
    }

    @Test
    public void testStationsMapTmxAssetExists(){
        assertTrue(Gdx.files.internal("Maps/StationsMap.tmx").exists(),"This will only pass if StationsMap.tmx exists");
    }

    @Test
    public void testStationsMap1AssetExists(){
        assertTrue(Gdx.files.internal("Maps/StationsMap1.tmx").exists(),"This will only pass if StationsMap1.tmx exists");
    }

    @Test
    public void testStationsMap2AssetExists(){
        assertTrue(Gdx.files.internal("Maps/StationsMap2.tmx").exists(),"This will only pass if StationsMap2.tmx exists");
    }

    //This is the section of tests that deal with the foods asset folder

    @Test
    public void testFoodBottomBunAssetExists(){
        assertTrue(Gdx.files.internal("foods/BottomBun.png").exists(),"This test will only pass if BottomBun.png exists in the food folder");
    }

    @Test
    public void testFoodBurgerAssetExists(){
        assertTrue(Gdx.files.internal("foods/Burger.png").exists(),"This test will only pass if Burger.png exists in the food folder");
    }

    @Test
    public void testFoodBurger_bunsAssetExists(){
        assertTrue(Gdx.files.internal("foods/Burger_Buns.png").exists(),"This test will only pass if Burger_Bun.png exists in the food folder");
    }

    @Test
    public void testFoodBurger_BunsUpscaledAssetExists(){
        assertTrue(Gdx.files.internal("foods/Burger_BunsUpscaled.png").exists(),"This test will only pass if Burger_BunsUpscaled.png exists in the food folder");
    }

    @Test
    public void testFoodFoodsAtlasAssetExists(){
        assertTrue(Gdx.files.internal("foods/foods atlas.tpproj").exists(),"This test will only pass if foods atlas.tpproj exists in the food folder");
    }

    @Test
    public void testFoodLettuceAssetExists(){
        assertTrue(Gdx.files.internal("foods/Lettuce.png").exists(),"This test will only pass if Lettuce.png exists in the food folder");
    }

    @Test
    public void testFoodLettuceChopAssetExists(){
        assertTrue(Gdx.files.internal("foods/LettuceChop.png").exists(),"This test will only pass if LettuceChop.png exists in the food folder");
    }

    @Test
    public void testFoodLettuceUpscaledAssetExists(){
        assertTrue(Gdx.files.internal("foods/LettuceUpscaled.png").exists(),"This test will only pass if LettuceUpscaled.png exists in the food folder");
    }

    @Test
    public void testFoodMargheritaPizaaAssetExists(){
        assertTrue(Gdx.files.internal("foods/MargheritaPizza.png").exists(),"This test will only pass if MargheritaPizza.png exists in the food folder");
    }

    @Test
    public void testFoodMeatAssetExists(){
        assertTrue(Gdx.files.internal("foods/Meat.png").exists(),"This test will only pass if Meat.png exists in the food folder");
    }

    @Test
    public void testFoodMeatFriedAssetExists(){
        assertTrue(Gdx.files.internal("foods/MeatFried.png").exists(),"This test will only pass MeatFried.png exists in the food folder");
    }

    @Test
    public void testFoodOnionAssetExists(){
        assertTrue(Gdx.files.internal("foods/Onion.png").exists(),"This test will only pass if Onion.png exists in the food folder");
    }

    @Test
    public void testFoodOnionChopAssetExists(){
        assertTrue(Gdx.files.internal("foods/OnionChop.png").exists(),"This test will only pass if OnionChop.png exists in the food folder");
    }

    @Test
    public void testFoodOnionUpscaledAssetExists(){
        assertTrue(Gdx.files.internal("foods/OnionUpscaled.png").exists(),"This test will only pass if OnionUpscaled.png exists in the food folder");
    }

    @Test
    public void testFoodTomatoAssetExists(){
        assertTrue(Gdx.files.internal("foods/Tomato.png").exists(),"This test will only pass if Tomato.png exists in the food folder");
    }

    @Test
    public void testFoodTomatoChopAssetExists(){
        assertTrue(Gdx.files.internal("foods/TomatoChop.png").exists(),"This test will only pass if TomatoChop.png exists in the food folder");
    }

    @Test
    public void testFoodTomatoUpscaeldAssetExists(){
        assertTrue(Gdx.files.internal("foods/TomatoUpscaled.png").exists(),"This test will only pass if TomatoUpscaled.png exists in the food folder");
    }

    @Test
    public void testFoodTopBunAssetExists(){
        assertTrue(Gdx.files.internal("foods/TopBun.png").exists(),"This test will only pass if TopBun.png exists in the food folder");
    }
    //These are the tests to do with the sprites.xcfs folder
    @Test
    public void testBurgerxcfAssetExists(){
        assertTrue(Gdx.files.internal("Sprites/xcfs/Burger.xcf").exists(), "This test failed because Burger.xcf doesn't exist");
    }@Test
    public void testBurger_BunsxcfAssetExists(){
        assertTrue(Gdx.files.internal("Sprites/xcfs/Burger_Buns.xcf").exists(), "This test failed because Burger_Buns.xcf doesn't exist");
    }@Test
    public void testCutting_StationxcfAssetExists(){
        assertTrue(Gdx.files.internal("Sprites/xcfs/Cutting_Station.xcf").exists(), "This test failed because Cutting_Station doesn't exist");
    }@Test
    public void testFryerxcfAssetExists(){
        assertTrue(Gdx.files.internal("Sprites/xcfs/Fryer.xcf").exists(), "This test failed because Fryer.xcf doesn't exist");
    }@Test
    public void testLettucexcfAssetExists(){
        assertTrue(Gdx.files.internal("Sprites/xcfs/Lettuce.xcf").exists(), "This test failed because Lettuce.xcf doesn't exist");
    }@Test
    public void testMargheritaPizzaxcfAssetExists(){
        assertTrue(Gdx.files.internal("Sprites/xcfs/MargheritaPizza.xcf").exists(), "This test failed because MargheritaPizza.xcf doesn't exist");
    }@Test
    public void testMeatxcfAssetExists(){
        assertTrue(Gdx.files.internal("Sprites/xcfs/Meat.xcf").exists(), "This test failed because Meat.xcf doesn't exist");
    }@Test
    public void testOnionxcfAssetExists(){
        assertTrue(Gdx.files.internal("Sprites/xcfs/Onion.xcf").exists(), "This test failed because Onion.xcf doesn't exist");
    }@Test
    public void testTablexcfAssetExists(){
        assertTrue(Gdx.files.internal("Sprites/xcfs/Table.xcf").exists(), "This test failed because Tables.xcf doesn't exist");
    }@Test
    public void testTomatoxcfAssetExists(){
        assertTrue(Gdx.files.internal("Sprites/xcfs/Tomato.xcf").exists(), "This test failed because Tomato.xcf doesn't exist");
    }

    // This will test the files in the general asset folder to make sure they exist

    @Test
    public void testBadLogicAssetExists(){
        assertTrue(Gdx.files.internal("badlogic.jpg").exists(),"The test will only pass if badlogic.jpg exists in the asset folder");
    }

    @Test
    public void testCreditsAssetExists(){
        assertTrue(Gdx.files.internal("CREDITS.txt").exists(),"The test will only pass if CREDITS.txt exists in the asset folder");
    }

    // This will test the files in the other folder to make sure they exist
    @Test
    public void testIconExists(){
        assertTrue(Gdx.files.internal("other/icon.png").exists(),"The test will only pass if icon.png exists in the asset folder");
    }

// The following will test the files in the OJ assets folder to make sure they exist
    @Test
    public void testcookingoggAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/audio/cooking.ogg").exists(), "The test failed because cooking.ogg doesn't exist");
    }

    @Test
    public void testfailurewavAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/audio/failure.wav").exists(), "The test failed because failure.wav doesn't exist");
    }

    @Test
    public void testpick_upwavAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/audio/pick_up.wav").exists(), "The test failed because pick_up.wav doesn't exist");
    }

    @Test
    public void testput_downwavAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/audio/put_down.wav").exists(), "The test failed because put_down.wav doesn't exist");
    }

    @Test
    public void testsuccesswavAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/audio/success.wav").exists(), "The test failed because success.wav doesn't exist");
    }

    @Test
    public void testprogress_bubble_1AssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/bubbles/progress_bubble_1.png").exists(), "The test failed because progressbubble1.png doesn't exist");
    }

    @Test
    public void testsimple_bubbleAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/bubbles/simple_bubble.png").exists(), "The test failed because simplebubble.png doesn't exist");
    }

    @Test
    public void testsimple_bubble_with_itemAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/bubbles/simple_bubble_with_item.png").exists(), "The test failed because simplebubblewithitem.png doesn't exist");
    }
    @Test
    public void testblugguspackAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/characters/bluggus.pack").exists(), "The test failed because bluggus.pack doesn't exist");
    }
    @Test
    public void testblugguspngAssetExists(){
        assertTrue(Gdx.files.internal("OJAssets/characters/bluggus.png").exists(), "The test failed because bluggus.png doesn't exist");
    }

    @Test
    public void testcuttingatlasAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/indicators/cutting/cutting.atlas").exists(), "The test failed because cutting.atlas doesn't exist");
    }

    @Test
    public void testcuttingpngAssetExists() {
    assertTrue(Gdx.files.internal("OJAssets/indicators/cutting/cutting.png").exists(), "The test failed because cutting.png doesn't exist");
    }

    @Test
    public void testGearAtlasAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/indicators/gear/gear.atlas").exists(), "The test failed because gear.atlas doesn't exist in the gear folder");
    }

    @Test
    public void testGearPngAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/indicators/gear/gear.png").exists(), "The test failed because gear.png doesn't exist in the gear folder");
    }

    @Test
    public void testMeatAtlasAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/indicators/patty forming/meat.atlas").exists(), "The test failed because meat.atlas doesn't exist in the patty forming folder");
    }

    @Test
    public void testMeatPngAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/indicators/patty forming/meat.png").exists(), "The test failed because meat.png doesn't exist in the patty forming folder");
    }

    //These tests test whether the assets in MorgansMap exist

    @Test
    public void testCounterBLTopPngAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/Counters/counter_BL_top.png").exists(), "The test failed because counter_BL_top.png doesn't exist in the counters folder");
    }

    @Test
    public void testCounterBRTopPngAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/Counters/counter_BR_top.png").exists(), "The test failed because counter_BR_top.png doesn't exist in the counters folder");
    }

    @Test
    public void testCounterCentrePngAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/Counters/counter_centre.png").exists(), "The test failed because counter_centre.png doesn't exist in the counters folder");
    }

    @Test
    public void testCounterLeftAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/Counters/counter_left.png").exists(), "The test failed because counter_left.png doesn't exist in the counters folder");
    }

    @Test
    public void testCounterRightAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/Counters/counter_right.png").exists(), "The test failed because counter_right.png doesn't exist in the counters folder");
    }

    @Test
    public void testCounterSideLeftAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/Counters/counter_side_left.png").exists(), "The test failed because counter_side_left.png doesn't exist in the counters folder");
    }

    @Test
    public void testCounterSideRightAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/Counters/counter_side_right.png").exists(), "The test failed because counter_side_right.png doesn't exist in the counters folder");
    }

    @Test
    public void testCounterTLBottomAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/Counters/counter_TL_bottom.png").exists(), "The test failed because counter_TL_bottom.png doesn't exist in the counters folder");
    }

    @Test
    public void testCounterTLTopAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/Counters/counter_TL_top.png").exists(), "The test failed because counter_TL_top.png doesn't exist in the counters folder");
    }
}



