package testing;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class AssetTests {

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
    public void testCookAtlasAssetExists(){
        assertTrue(Gdx.files.internal("cooks/cook atlas.tpproj").exists(), "This test will only pass if cook atlas.tpproj is present");
    }

    //The following are tests to do with checking that assets in the customers folder remain
    @Test
    public void testCustomerAssetExists(){
        assertTrue(Gdx.files.internal("customers/Customer.png").exists(),"This test will only pass if Customer.png exists in the customers folder");
    }

    //The following test that all assets in the atlas folder exist

    @Test
    public void testAtlasCustomerAssetExists(){
        assertTrue(Gdx.files.internal("atlas/customer.png").exists(),"This test will only pass if customer.png exists in the atlas folder");
        assertTrue(Gdx.files.internal("atlas/customer.atlas").exists(),"This will only pass if customer.atlas exists");
    }

    @Test
    public void testAtlasCookAssetExists(){
        assertTrue(Gdx.files.internal("atlas/cook.png").exists(),"This test will only pass if cook.png exists in the atlas folder");
        assertTrue(Gdx.files.internal("atlas/cook.atlas").exists(),"This will only pass if customer.atlas exists");
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
}
