package testing;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class AssetTests {

    // They all relate to the UR_GRAPHICS requirement
    // The following are tests to do with checking that the assets in the cooks folder still remain
    @Test
    public void testChefHoldDownAssetExists() {
        assertTrue(Gdx.files.internal("cooks/hold down.png").exists(), "This test will only pass if hold down.png is present");
    }

    @Test
    public void testChefHoldLeftAssetExists() {
        assertTrue(Gdx.files.internal("cooks/hold left.png").exists(), "This test will only pass if hold left.png is present");
    }

    @Test
    public void testChefHoldRightAssetExists() {
        assertTrue(Gdx.files.internal("cooks/hold right.png").exists(), "This test will only pass if hold right.png is present");
    }

    @Test
    public void testChefHoldUpAssetExists() {
        assertTrue(Gdx.files.internal("cooks/hold up.png").exists(), "This test will only pass if hold up.png is present");
    }

    @Test
    public void testChefControlAssetExists() {
        assertTrue(Gdx.files.internal("cooks/control.png").exists(), "This test will only pass if control.png is present");
    }

    @Test
    public void testChefNormalDownAssetExists() {
        assertTrue(Gdx.files.internal("cooks/normal down.png").exists(), "This test will only pass if normal down.png is present");
    }

    @Test
    public void testChefNormalLeftAssetExists() {
        assertTrue(Gdx.files.internal("cooks/normal left.png").exists(), "This test will only pass if normal left.png is present");
    }

    @Test
    public void testChefNormalRightAssetExists() {
        assertTrue(Gdx.files.internal("cooks/normal right.png").exists(), "This test will only pass if normal right.png is present");
    }

    @Test
    public void testChefNormalUpAssetExists() {
        assertTrue(Gdx.files.internal("cooks/normal up.png").exists(), "This test will only pass if normal up.png is present");
    }

    @Test
    public void testChefTestDummyAssetExists() {
        assertTrue(Gdx.files.internal("cooks/testdummy.png").exists(), "This test will only pass if testdummy.png exists");
    }

    @Test
    public void testPlayerAtlasAssetExists() {
        assertTrue(Gdx.files.internal("cooks/cook atlas.tpproj").exists(), "This test will only pass if cook atlas.tpproj is present");
    }

    //The following are tests to do with checking that assets in the customers folder remain
    @Test
    public void testCustomerAssetExists() {
        assertTrue(Gdx.files.internal("customers/Customer.png").exists(), "This test will only pass if Customer.png exists in the customers folder");
        assertTrue(Gdx.files.internal("customers/customer.tpproj").exists(), "This tests that customer.tpproj exists");
    }

    //The following test that all assets in the atlas folder exist

    @Test
    public void testAtlasCustomerAssetExists() {
        assertTrue(Gdx.files.internal("atlas/customer.png").exists(), "This test will only pass if customer.png exists in the atlas folder");
        assertTrue(Gdx.files.internal("atlas/customer.atlas").exists(), "This will only pass if customer.atlas exists");
    }

    @Test
    public void testAtlasCookAssetExists() {
        assertTrue(Gdx.files.internal("atlas/player.png").exists(), "This test will only pass if player.png exists in the atlas folder");
        assertTrue(Gdx.files.internal("atlas/player.atlas").exists(), "This will only pass if customer.atlas exists");
    }

    @Test
    public void testAtlasFoodAssetExists() {
        assertTrue(Gdx.files.internal("atlas/food.png").exists(), "This test will only pass if food.png exists in the atlas folder");
        assertTrue(Gdx.files.internal("atlas/food.atlas").exists(), "This will only pass if food.atlas exists");
    }

    @Test
    public void testAtlasStationAssetExists() {
        assertTrue(Gdx.files.internal("atlas/station.png").exists(), "This test will only pass if station.png exists in the atlas folder");
        assertTrue(Gdx.files.internal("atlas/station.atlas").exists(), "This will only pass if station.atlas exists");
    }

    //The following test that all assets in the Individual_Stations folder exist

    @Test
    public void testBinAssetExists() {
        assertTrue(Gdx.files.internal("Individual_Stations/bin.png").exists(), "This will only pass if bin.png exists");
    }

    @Test
    public void testCuttingStationAssetExists() {
        assertTrue(Gdx.files.internal("Individual_Stations/Cutting_Station.png").exists(), "This will only pass if CuttingStation.png exists");
    }

    @Test
    public void FryerAssetExists() {
        assertTrue(Gdx.files.internal("Individual_Stations/Fryer.png").exists(), "This will only pass if Fryer.png exists");
    }

    @Test
    public void testStationsTpprojAssetExists() {
        assertTrue(Gdx.files.internal("Individual_Stations/stations.tpproj").exists(), "This will only pass if stations.tpproj exists");
    }

    @Test
    public void testTableAssetExists() {
        assertTrue(Gdx.files.internal("Individual_Stations/Table.png").exists(), "This will only pass if Table.png exists");
    }

    @Test
    public void testTableWithChairsAssetExists() {
        assertTrue(Gdx.files.internal("Individual_Stations/TableWithChairs.png").exists(), "This will only pass if TableWithChairs.png exists");
    }

    //The following test that all assets in the Maps folder exist

    @Test
    public void testStartMenuBackgroundAssetExists() {
        assertTrue(Gdx.files.internal("Maps/StartMenuBackground.png").exists(), "This will only pass if StartMenuBackground.png exists");
    }

    @Test
    public void testStationsPngAssetExists() {
        assertTrue(Gdx.files.internal("Maps/Stations.png").exists(), "This will only pass if Stations.png exists");
    }

    @Test
    public void testStationsTsxAssetExists() {
        assertTrue(Gdx.files.internal("Maps/Stations.tsx").exists(), "This will only pass if Stations.tsx exists");
    }

    @Test
    public void testStationsXcfAssetExists() {
        assertTrue(Gdx.files.internal("Maps/Stations.xcf").exists(), "This will only pass if Stations.xcf exists");
    }

    @Test
    public void testStationsMapPngAssetExists() {
        assertTrue(Gdx.files.internal("Maps/StationsMap.png").exists(), "This will only pass if StationsMap.png exists");
    }

    @Test
    public void testStationsMapTmxAssetExists() {
        assertTrue(Gdx.files.internal("Maps/StationsMap.tmx").exists(), "This will only pass if StationsMap.tmx exists");
    }

    @Test
    public void testStationsMap1AssetExists() {
        assertTrue(Gdx.files.internal("Maps/StationsMap1.tmx").exists(), "This will only pass if StationsMap1.tmx exists");
    }

    @Test
    public void testStationsMap2AssetExists() {
        assertTrue(Gdx.files.internal("Maps/StationsMap2.tmx").exists(), "This will only pass if StationsMap2.tmx exists");
    }

    //This is the section of tests that deal with the foods asset folder

    @Test
    public void testFoodBottomBunAssetExists() {
        assertTrue(Gdx.files.internal("foods/BottomBun.png").exists(), "This test will only pass if BottomBun.png exists in the food folder");
    }

    @Test
    public void testFoodBurgerAssetExists() {
        assertTrue(Gdx.files.internal("foods/Burger.png").exists(), "This test will only pass if Burger.png exists in the food folder");
    }

    @Test
    public void testFoodBurger_bunsAssetExists() {
        assertTrue(Gdx.files.internal("foods/Burger_Buns.png").exists(), "This test will only pass if Burger_Bun.png exists in the food folder");
    }

    @Test
    public void testFoodBurger_BunsUpscaledAssetExists() {
        assertTrue(Gdx.files.internal("foods/Burger_BunsUpscaled.png").exists(), "This test will only pass if Burger_BunsUpscaled.png exists in the food folder");
    }

    @Test
    public void testFoodFoodsAtlasAssetExists() {
        assertTrue(Gdx.files.internal("foods/foods atlas.tpproj").exists(), "This test will only pass if foods atlas.tpproj exists in the food folder");
    }

    @Test
    public void testFoodLettuceAssetExists() {
        assertTrue(Gdx.files.internal("foods/Lettuce.png").exists(), "This test will only pass if Lettuce.png exists in the food folder");
    }

    @Test
    public void testFoodLettuceChopAssetExists() {
        assertTrue(Gdx.files.internal("foods/LettuceChop.png").exists(), "This test will only pass if LettuceChop.png exists in the food folder");
    }

    @Test
    public void testFoodLettuceUpscaledAssetExists() {
        assertTrue(Gdx.files.internal("foods/LettuceUpscaled.png").exists(), "This test will only pass if LettuceUpscaled.png exists in the food folder");
    }

    @Test
    public void testFoodMargheritaPizaaAssetExists() {
        assertTrue(Gdx.files.internal("foods/MargheritaPizza.png").exists(), "This test will only pass if MargheritaPizza.png exists in the food folder");
    }

    @Test
    public void testFoodMeatAssetExists() {
        assertTrue(Gdx.files.internal("foods/Meat.png").exists(), "This test will only pass if Meat.png exists in the food folder");
    }

    @Test
    public void testFoodMeatFriedAssetExists() {
        assertTrue(Gdx.files.internal("foods/MeatFried.png").exists(), "This test will only pass MeatFried.png exists in the food folder");
    }

    @Test
    public void testFoodOnionAssetExists() {
        assertTrue(Gdx.files.internal("foods/Onion.png").exists(), "This test will only pass if Onion.png exists in the food folder");
    }

    @Test
    public void testFoodOnionChopAssetExists() {
        assertTrue(Gdx.files.internal("foods/OnionChop.png").exists(), "This test will only pass if OnionChop.png exists in the food folder");
    }

    @Test
    public void testFoodOnionUpscaledAssetExists() {
        assertTrue(Gdx.files.internal("foods/OnionUpscaled.png").exists(), "This test will only pass if OnionUpscaled.png exists in the food folder");
    }

    @Test
    public void testFoodTomatoAssetExists() {
        assertTrue(Gdx.files.internal("foods/Tomato.png").exists(), "This test will only pass if Tomato.png exists in the food folder");
    }

    @Test
    public void testFoodTomatoChopAssetExists() {
        assertTrue(Gdx.files.internal("foods/TomatoChop.png").exists(), "This test will only pass if TomatoChop.png exists in the food folder");
    }

    @Test
    public void testFoodTomatoUpscaeldAssetExists() {
        assertTrue(Gdx.files.internal("foods/TomatoUpscaled.png").exists(), "This test will only pass if TomatoUpscaled.png exists in the food folder");
    }

    @Test
    public void testFoodTopBunAssetExists() {
        assertTrue(Gdx.files.internal("foods/TopBun.png").exists(), "This test will only pass if TopBun.png exists in the food folder");
    }

    //These are the tests to do with the sprites.xcfs folder
    @Test
    public void testBurgerxcfAssetExists() {
        assertTrue(Gdx.files.internal("Sprites/xcfs/Burger.xcf").exists(), "This test failed because Burger.xcf doesn't exist");
    }

    @Test
    public void testBurger_BunsxcfAssetExists() {
        assertTrue(Gdx.files.internal("Sprites/xcfs/Burger_Buns.xcf").exists(), "This test failed because Burger_Buns.xcf doesn't exist");
    }

    @Test
    public void testCutting_StationxcfAssetExists() {
        assertTrue(Gdx.files.internal("Sprites/xcfs/Cutting_Station.xcf").exists(), "This test failed because Cutting_Station doesn't exist");
    }

    @Test
    public void testFryerxcfAssetExists() {
        assertTrue(Gdx.files.internal("Sprites/xcfs/Fryer.xcf").exists(), "This test failed because Fryer.xcf doesn't exist");
    }

    @Test
    public void testLettucexcfAssetExists() {
        assertTrue(Gdx.files.internal("Sprites/xcfs/Lettuce.xcf").exists(), "This test failed because Lettuce.xcf doesn't exist");
    }

    @Test
    public void testMargheritaPizzaxcfAssetExists() {
        assertTrue(Gdx.files.internal("Sprites/xcfs/MargheritaPizza.xcf").exists(), "This test failed because MargheritaPizza.xcf doesn't exist");
    }

    @Test
    public void testMeatxcfAssetExists() {
        assertTrue(Gdx.files.internal("Sprites/xcfs/Meat.xcf").exists(), "This test failed because Meat.xcf doesn't exist");
    }

    @Test
    public void testOnionxcfAssetExists() {
        assertTrue(Gdx.files.internal("Sprites/xcfs/Onion.xcf").exists(), "This test failed because Onion.xcf doesn't exist");
    }

    @Test
    public void testTablexcfAssetExists() {
        assertTrue(Gdx.files.internal("Sprites/xcfs/Table.xcf").exists(), "This test failed because Tables.xcf doesn't exist");
    }

    @Test
    public void testTomatoxcfAssetExists() {
        assertTrue(Gdx.files.internal("Sprites/xcfs/Tomato.xcf").exists(), "This test failed because Tomato.xcf doesn't exist");
    }

    // This will test the files in the general asset folder to make sure they exist

    @Test
    public void testBadLogicAssetExists() {
        assertTrue(Gdx.files.internal("badlogic.jpg").exists(), "The test will only pass if badlogic.jpg exists in the asset folder");
    }

    @Test
    public void testCreditsAssetExists() {
        assertTrue(Gdx.files.internal("CREDITS.txt").exists(), "The test will only pass if CREDITS.txt exists in the asset folder");
    }

    // This will test the files in the other folder to make sure they exist
    @Test
    public void testIconExists() {
        assertTrue(Gdx.files.internal("other/icon.png").exists(), "The test will only pass if icon.png exists in the asset folder");
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
    public void testblugguspngAssetExists() {
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

    @Test
    public void testBinPNGAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/Extras/bin.png").exists(), "The test failed because bin.png doesn't exist in the Extras folder of Tiles in MorgansMap");
    }

    @Test
    public void testCookingTopPNGAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/Extras/CookingTop.png").exists(), "The test failed because CookingTop.png doesn't exist in the Extras folder of Tiles in MorgansMap");
    }

    @Test
    public void testBeamAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/Beam.png").exists(), "The test failed because Beam.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testBottomVATAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/BottomVAT.png").exists(), "The test failed because BottomVAT.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testCurvyMetalAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/CurvyMetal.png").exists(), "The test failed because CurvyMetal.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testCurvyMetalFlippedAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/CurvyMetalflipped.png").exists(), "The test failed because CurvyMetalflipped.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testDinosaurAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/Dinosaur.png").exists(), "The test failed because Dinosaur.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testDomeAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/Dome.png").exists(), "The test failed because Dome.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testDomeCounterAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/DomeCounter.png").exists(), "The test failed because DomeCounter.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testDomeCounterInvAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/DomeCounterInv.png").exists(), "The test failed because DomeCounterInv.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testEmptyVATAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/EmptyVAT.png").exists(), "The test failed because EmptyVAT.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testEmptyVATxcfAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/EmptyVAT.xcf").exists(), "The test failed because EmptyVAT.xcf doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testLarchAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/Larch.png").exists(), "The test failed because Larch.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testMetalBlockAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/MetalBlock.png").exists(), "The test failed because MetalBlock.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testMetalBlockxcfAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/MetalBlock.xcf").exists(), "The test failed because MetalBlock.xcf doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testMetalBlock2AssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/MetalBlock2.png").exists(), "The test failed because MetalBlock2.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testMetalBlock2flippedAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/MetalBlock2flipped.png").exists(), "The test failed because MetalBlock2flipped.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testMetalBlockflippedAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/MetalBlockflipped.png").exists(), "The test failed because MetalBlockflipped.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testMetalSlope2AssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/MetalSlope2.png").exists(), "The test failed because MetalSlope2.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testMetalSlope2flippedExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/MetalSlope2flipped.png").exists(), "The test failed because MetalSlope2flipped.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testOuterCookedAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/OuterCooked.tmx").exists(), "The test failed because OuterCooked.tmx doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testPurpadurpleAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/Purpadurple.png").exists(), "The test failed because Purpadurple.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testRarchAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/Rarch.png").exists(), "The test failed because Rarch.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testServingStationAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/ServingStation.png").exists(), "The test failed because ServingStation.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testShopAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/Shop.png").exists(), "The test failed because Shop.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testSignDiningAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/SignDining.png").exists(), "The test failed because SignDining.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testSignFridgeAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/SignFridge.png").exists(), "The test failed because SignFridge.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testSignPantryAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/SignPantry.png").exists(), "The test failed because SignPantry.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testSpaceMEGAtileAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/SpaceMEGAtile.png").exists(), "The test failed because SpaceMEGAtile.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testStarsAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/Stars.png").exists(), "The test failed because Stars.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testTileFloorMFAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/TileFloorMF.png").exists(), "The test failed because TileFloorMF.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testTopVATAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/TopVAT.png").exists(), "The test failed because TopVAT.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testWindowStarshipAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/Window-Starship.png").exists(), "The test failed because Window-Starship.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testWindowStarshipWithBluggyAssetExists() {
        assertTrue(Gdx.files.internal("MorgansMap/Tiles/MFPack/Window-Starship-withbluggy.png").exists(), "The test failed because Window-Starship-withbluggy.png doesn't exist in the MFPack folder of Tiles in MorgansMap");
    }

    @Test
    public void testAssemblerpngAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/Assembler.png").exists(), "The test failed because assembler.png doesn't exist in the map folder");

    }

    @Test
    public void testBakingAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/baking.png").exists(), "The test failed because baking.png doesn't exist in the map folder");

    }

    @Test
    public void testBox_AltAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/Box_Alt.png").exists(), "The test failed because Box_Alt.png doesn't exist in the map folder");

    }

    @Test
    public void testBrickWallBottomAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/brick_wall_bottom.png").exists(), "The test failed because brick_wall_bottom.png doesn't exist in the map folder");

    }

    @Test
    public void testBrickWallTopAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/brickWallTop.png").exists(), "The test failed because brickWallTop.png doesn't exist in the map folder");

    }

    @Test
    public void testCookingTopAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/CookingTop.png").exists(), "The test failed because CookingTop.png doesn't exist in the map folder");

    }

    @Test
    public void testCornerAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/corner.png").exists(), "The test failed because corner.png doesn't exist in the map folder");

    }

    @Test
    public void testCounterA3CornerAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/Counter-A3-Corner.png").exists(), "The test failed because Counter-A3-Corner.png doesn't exist in the map folder");

    }

    @Test
    public void testCounterA3EndAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/Counter-A3-End.png").exists(), "The test failed because Counter-A3-End.png doesn't exist in the map folder");

    }

    @Test
    public void testCounterA3FaceDownAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/Counter-A3-Face_Down.png").exists(), "The test failed because Counter-A3-Face_Down.png doesn't exist in the map folder");

    }

    @Test
    public void testCounterA3FaceRightAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/Counter-A3-Face_Right.png").exists(), "The test failed because Counter-A3-Face_Right.png doesn't exist in the map folder");

    }

    @Test
    public void testCustomerCounterAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/customerCounter.png").exists(), "The test failed because customerCounter.png doesn't exist in the map folder");

    }

    @Test
    public void testCustomerCounterEndAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/customerCounterEnd.png").exists(), "The test failed because customerCounterEnd.png doesn't exist in the map folder");

    }

    @Test
    public void testDarkGrainedWoodFullTextureAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/Dark Grained Wood Full Texture.png").exists(), "The test failed because Dark Grained Wood Full Texture.png doesn't exist in the map folder");

    }

    @Test
    public void testFoggyAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/foggy.png").exists(), "The test failed because foggy.png doesn't exist in the map folder");

    }

    @Test
    public void testGrasspathAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/grasspath.png").exists(), "The test failed because grasspath.png doesn't exist in the map folder");

    }

    @Test
    public void testGrasstestAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/grasstest.png").exists(), "The test failed because grasstest.png doesn't exist in the map folder");

    }

    @Test
    public void testJustBrickAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/justbrick.png").exists(), "The test failed because justbrick.png doesn't exist in the map folder");

    }

    @Test
    public void testMarbleEndAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/marble-end.png").exists(), "The test failed because marble-end.png doesn't exist in the folder");

    }

    @Test
    public void testMarblesideAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/marbleside.png").exists(), "The test failed because marbleside.png doesn't exist in the map folder");

    }

    @Test
    public void testMarbleSideAltAssetExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/marblesidealt.png").exists(), "The test failed because marblesidealt.png doesn't ");
    }

    @Test
    public void testNewBrickTestAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/newbricktest.png").exists(), "The test failed because newbricktest.png doesn't exist in the map folder");

    }

    @Test
    public void testNewBrickTopAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/newbricktop.png").exists(), "The test failed because newbricktop.png doesn't exist in the map folder");
    }

    @Test
    public void testNewCounterTestAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/newcountertest.png").exists(), "The test failed because newcountertest.png doesn't exist in the map folder");
    }

    @Test
    public void testOvenLOWERpAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/OvenLower.png").exists(), "The test failed because OvenLower.png doesn't exist in the map folder");
    }

    @Test
    public void testOvenTopAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/OvenTop.png").exists(), "The test failed because OvenTop.png doesn't exist in the map folder");
    }

    @Test
    public void testPathementTestAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/Pathement Test.png").exists(), "The test failed because Pathement Test.png doesn't exist in the map folder");
    }

    @Test
    public void testRoadMiddleAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/road middle.png").exists(), "The test failed because road middle.png doesn't exist in the map folder");
    }

    @Test
    public void testRoadChunkAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/roadchunk.png").exists(), "The test failed because roadchunk.png doesn't exist in the map folder");
    }

    @Test
    public void testTileImagesAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/Tile Images.tsx").exists(), "The test failed because Tile Images.tsx doesn't exist in the map folder");
    }

    @Test
    public void testTileTestAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/tile test.png").exists(), "The test failed because tile test.png doesn't exist in the map folder");
    }

    @Test
    public void testTileTesAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/tile_tes.png").exists(), "The test failed because tile_tes.png doesn't exist in the map folder");
    }

    @Test
    public void testTileTest3AssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/tile_test3.png").exists(), "The test failed because tile_test3.png doesn't exist in the map folder");
    }

    @Test
    public void testTileTestBlueAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/tile_test_blue.png").exists(), "The test failed because tile_test_blue.png doesn't exist in the map folder");
    }

    @Test
    public void testVoidAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/void.png").exists(), "The test failed because void.png doesn't exist in the map folder");
    }

    @Test
    public void testVoidCappedAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/void_capped.png").exists(), "The test failed because void_capped.png doesn't exist in the map folder");
    }

    @Test
    public void testWallAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/wall.png").exists(), "The test failed because wall.png doesn't exist in the map folder");
    }

    @Test
    public void testWallBottomAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/wall_bottom.png").exists(), "The test failed because wall_bottom.png doesn't exist in the map folder");
    }

    @Test
    public void testWallSideAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/wall_side.png").exists(), "The test failed because wall_side.png doesn't exist in the map folder");
    }

    @Test
    public void testWallPartAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/wallPart.png").exists(), "The test failed because wallPart.png doesn't exist in the map folder");
    }

    @Test
    public void testWallTopAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/wallTop.png").exists(), "The test failed because wallTop.png doesn't exist in the map folder");
    }

    @Test
    public void testWindowedbottomAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/windowedbottom.png").exists(), "The test failed because windowedbottom.png doesn't exist in the map folder");
    }

    @Test
    public void testWindowedtopAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/windowedtop.png").exists(), "The test failed because windowedtop.png doesn't exist in the map folder");
    }

    @Test
    public void testWMDDispenserAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/WMD dispenser.png").exists(), "The test failed because WMD dispenser.png doesn't exist in the map folder");
    }

    @Test
    public void testWoodFloorAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/WoodFloor.png").exists(), "The test failed because WoodFloor.png doesn't exist in the map folder");
    }

    @Test
    public void testWoodFloor2AssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/WoodFloor2.png").exists(), "The test failed because WoodFloor2.png doesn't exist in the map folder");
    }

    @Test
    public void testWoodFloorNewAssetsExists() {
        assertTrue(Gdx.files.internal("OJAssets/map/WoodFloorNew.png").exists(), "The test failed because WoodFloorNew.png doesn't exist in the map folder");
    }

    @Test
    public void testCounterBLTopAssestsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Counters/counter_BL_top.png").exists(), "The test failed because counter_bl_top.png doesn't exist in the map2 folder");
    }

    @Test
    public void testCounterBRTopAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Counters/counter_BR_top.png").exists(), "The test failed because counter_br_top.png doesn't exist in the map2 folder");
    }

    @Test
    public void testCounterCentreAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Counters/counter_centre.png").exists(), "The test failed because counter_centre.png doesn't exist in the map2 folder");
    }

    @Test
    public void testCounterLeftAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Counters/counter_left.png").exists(), "The test failed because counter_left.png doesn't exist in the map2 folder");
    }

    @Test
    public void testCounterRightAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Counters/counter_right.png").exists(), "The test failed because counter_right.png doesn't exist in the map2 folder");

    }

    @Test
    public void testCounterSideLeftAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Counters/counter_side_left.png").exists(), "The test failed because counter_side_left.png doesn't exist in the map2 folder");
    }

    @Test
    public void testCounterSideRightAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Counters/counter_side_right.png").exists(), "The test failed because counter_side_right.png doesn't exist in the map2 folder");
    }

    @Test
    public void testCounterTLBottomAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Counters/counter_TL_Bottom.png").exists(), "The test failed because counter_TL_Bottom.png doesn't exist in the map2 folder ");
    }

    @Test
    public void testCounterTLTopAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Counters/counter_TL_top.png").exists(), "The test failed because counter_TL_top.png doesn't exist in the map2 folder");
    }

    @Test
    public void testCounterTRBottomAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Counters/counter_TR_bottom.png").exists(), "The test failed because counter_TR_bottom.png doesn't exist in the map2 folder");
    }

    @Test
    public void testCounterTRTopAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Counters/counter_TR_top.png").exists(), "The test failed because counter_TR_top.png doesn't exist in the map2 folder");
    }

    @Test
    public void testServingAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Counters/serving.png").exists(), "The test failed because serving.png doesn't exist in the map2 folder");
    }

    @Test
    public void testServingCounterAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Counters/serving_counter.png").exists(), "The test failed because serving_counter.png doesn't exist in the map2 folder");
    }

    @Test
    public void testServingTestAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Counters/serving_test.png").exists(), "The test failed because serving_test.png doesn't exist in the map2 folder");
    }

    @Test
    public void testGrasspngAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Setting/grass.png").exists(), "The test failed because grass.png doesn't exist in the map2 folder");
    }

    @Test
    public void testGrassPathAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Setting/grass_path.png").exists(), "The test failed because grass_path.png doesn't exist in the map2 folder");
    }

    @Test
    public void testRoadMiddlepngAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Setting/road middle.png").exists(), "The test failed because road middle.png doesn't exist in the map2 folder");
    }

    @Test
    public void testRoadChunkpngAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Setting/roadchunk.png").exists(), "The test failed because roadchunk.png doesn't exist in the map2 folder");
    }
    @Test
    public void testAssemblerAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Stations/assembler.png").exists(), "The test failed because assembler.png doesn't exist in the map2 folder");
    }

    @Test
    public void testBakingAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Stations/baking.png").exists(), "The test failed because baking.png doesn't exist in the map2 folder");
    }

    @Test
    public void testBakingTopAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Stations/baking_top.png").exists(), "The test failed because baking_top.png doesn't exist in the map2 folder");
    }

    @Test
    public void testBinAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Stations/bin.png").exists(), "The test failed because bin.png doesn't exist in the map2 folder");
    }

    @Test
    public void testCookerLowerAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Stations/cooker_lower.png").exists(), "The test failed because cooker_lower.png doesn't exist in the map2 folder");
    }

    @Test
    public void testCookingTopAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Stations/CookingTop.png").exists(), "The test failed because CookingTop.png doesn't exist in the map2 folder");
    }

    @Test
    public void testCuttingAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Stations/cutting.png").exists(), "The test failed because cutting.png doesn't exist in the map2 folder");
    }

    @Test
    public void testDispenserAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Stations/dispenser.png").exists(), "The test failed because dispenser.png doesn't exist in the map2 folder");
    }

    @Test
    public void testDispenserCentreAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Stations/dispenser_centre.png").exists(), "The test failed because dispenser_centre.png doesn't exist in the map2 folder");
    }

    @Test
    public void testDispenserTopAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Stations/dispenser_top.png").exists(), "The test failed because dispenser_top.png doesn't exist in the map2 folder");
    }

    @Test
    public void testBunDispenserAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Stations/Dispensers/bun_dispenser.png").exists(), "The test failed because bun_dispenser.png doesn't exist in the map2 folder");
    }

    @Test
    public void testLettuceDispenserAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Stations/Dispensers/lettuce_dispenser.png").exists(), "The test failed because lettuce_dispenser.png doesn't exist in the map2 folder");
    }

    @Test
    public void testMeatAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Stations/Dispensers/meat.png").exists(), "The test failed because meat.png doesn't exist in the map2 folder");
    }

    @Test
    public void testMeatDispenserAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Stations/Dispensers/meat_dispenser.png").exists(), "The test failed because meat_dispenser.png doesn't exist in the map2 folder");
    }

    @Test
    public void testOnionDispenserAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Stations/Dispensers/onion_dispenser.png").exists(), "The test failed because onion_dispenser.png doesn't exist in the map2 folder");
    }

    @Test
    public void testTomatoDispenserAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Stations/Dispensers/tomato_dispenser.png").exists(), "The test failed because tomato_dispenser.png doesn't exist in the map2 folder");
    }

    @Test
    public void testBottomLowerAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Walls/bottom_lower.png").exists(), "The test failed because bottom_lower.png doesn't exist in the map2 folder");
    }

    @Test
    public void testBottomLowerWindowAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Walls/bottom_lower_window.png").exists(), "The test failed because bottom_lower_window.png doesn't exist in the map2 folder");
    }

    @Test
    public void testBottomUpperAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Walls/bottom_upper.png").exists(), "The test failed because bottom_upper.png doesn't exist in the map2 folder");
    }

    @Test
    public void testBottomUpperWindowAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Walls/bottom_upper_window.png").exists(), "The test failed because bottom_upper_window.png doesn't exist in the map2 folder");
    }

    @Test
    public void testPlainBricksAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Walls/plain_bricks.png").exists(), "The test failed because plain_bricks.png doesn't exist in the map2 folder");
    }

    @Test
    public void testBubbleAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/bubble.png").exists(), "The test failed because bubble.png doesn't exist in the map2 folder");
    }

    @Test
    public void testLevel1AssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/Level1.tmx").exists(), "The test failed because Level1.tmx doesn't exist in the map2 folder");
    }

    @Test
    public void testTileAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/map2/tile.png").exists(), "The test failed because tile.png doesn't exist in the map2 folder");
    }

    @Test
    public void testExitSelectedAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/menu_assets/exit_selected.png").exists(), "The test failed because exit_selected.png doesn't exist in the menu_assets folder");
    }

    @Test
    public void testExitUnselectedassetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/menu_assets/exit_unselected.png").exists(), "The test failed because exit_unselected.png doesn't exist in the menu_assets folder");
    }

    @Test
    public void testFinaltimeAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/menu_assets/finaltime.png").exists(), "The test failed because finaltime.png doesn't exist in the menu_assets folder");
    }

    @Test
    public void testHowtoplayAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/menu_assets/how_to_play.png").exists(), "The test failed because how_to_play.png doesn't exist in the menu_assets folder");
    }

    @Test
    public void testHowtoplaySelectedAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/menu_assets/howtoplay_selected.png").exists(), "The test failed because howtoplay_selected.png doesn't exist in the menu_assets folder");
    }

    @Test
    public void testHowtoplayUnselectedAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/menu_assets/howtoplay_unselected.png").exists(), "The test failed because howtoplay_unselected.png doesn't exist in the menu_assets folder");
    }

    @Test
    public void testMocktitleAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/menu_assets/mocktitle.png").exists(), "The test failed because mocktitle.png doesn't exist in the menu_assets folder");
    }

    @Test
    public void testPlaySelectedAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/menu_assets/play_selected.png").exists(), "The test failed because play_selected.png doesn't exist in the menu_assets folder");
    }

    @Test
    public void testPlayUnselectedAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/menu_assets/play_unselected.png").exists(), "The test failed because play_unselected.png doesn't exist in the menu_assets folder");
    }

    @Test
    public void testTabSpaceAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/menu_assets/tab space.png").exists(), "The test failed because tab space.png doesn't exist in the menu_assets folder");
    }

    @Test
    public void testUpdownAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/menu_assets/updown.png").exists(), "The test failed because updown.png doesn't exist in the menu_assets folder");
    }

    @Test
    public void testWsadAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/menu_assets/wsad.png").exists(), "The test failed because wsad.png doesn't exist in the menu_assets folder");
    }

    @Test
    public void testBottomVATAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/MFPack/BottomVAT.png").exists(), "The test failed because BottomVAT.png doesn't exist in the MFPack folder");
    }

    @Test
    public void testBeamAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/MFPack/Beam.png").exists(), "The test failed because Beam.png doesn't exist in the MFPack folder");
    }

    @Test
    public void testCurvyMetalAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/MFPack/CurvyMetal.png").exists(), "The test failed because CurvyMetal.png doesn't exist in the MFPack folder");
    }

    @Test
    public void testCurvyMetalFlippedAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/MFPack/CurvyMetalflipped.png").exists(), "The test failed because CurvyMetalflipped.png doesn't exist in the MFPack folder");
    }

    @Test
    public void testChefAtlasAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/pack textures/chef.atlas").exists(), "The test failed because chef.atlas doesn't exist in the pack textures folder");
    }

    @Test
    public void testChefPackassetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/pack textures/chef.pack").exists(), "The test failed because chef.pack doesn't exist in the pack textures folder");
    }

    @Test
    public void testChefAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/pack textures/chef.png").exists(), "The test failed because chef.png doesn't exist in the pack textures folder");
    }

    @Test
    public void testChef2AtlasAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/pack textures/chef2.atlas").exists(), "The test failed because chef2.atlas doesn't exist in the pack textures folder");
    }

    @Test
    public void testChef2AssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/pack textures/chef2.png").exists(), "The test failed because chef2.png doesn't exist in the pack textures folder");
    }

    @Test
    public void testChef3AssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/pack textures/chef3.png").exists(), "The test failed because chef3.png doesn't exist in the pack textures folder");
    }

    @Test
    public void testDinosaurAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/MFPack/Dinosaur.png").exists(), "The test failed because Dinosaur.png doesn't exist in the MFPack folder");
    }

    @Test
    public void testDomeAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/MFPack/Dome.png").exists(), "The test failed because Dome.png doesn't exist in the MFPack folder");
    }

    @Test
    public void testDomeCounterAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/MFPack/DomeCounter.png").exists(), "The test failed because DomeCounter.png doesn't exist in the MFPack folder");
    }

    @Test
    public void testDomeCounterInvAssetsExists(){
        assertTrue(Gdx.files.internal("OJAssets/MFPack/DomeCounterInv.png").exists(), "The test failed because DomeCounterInv.png doesn't exist in the MFPack folder");
    }


}




















