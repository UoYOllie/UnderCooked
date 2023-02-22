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

    //The following are tests to do with checking that assets in the customers folder remain
    @Test
    public void testCustomerAssetExists(){
        assertTrue(Gdx.files.internal("customers/Customer.png").exists(),"This test will only pass if Customer.png exists in the customers folder");
    }

    //The following test that all assets in the atlas folder exist

    @Test
    public void testAtlasCustomerAssetExists(){
        assertTrue(Gdx.files.internal("atlas/customer.png").exists(),"This test will only pass if customer.png exists in the atlas folder");
    }

    @Test
    public void testAtlasCookAssetExists(){
        assertTrue(Gdx.files.internal("atlas/cook.png").exists(),"This test will only pass if cook.png exists in the atlas folder");
    }

    @Test
    public void testAtlasFoodAssetExists(){
        assertTrue(Gdx.files.internal("atlas/food.png").exists(),"This test will only pass if food.png exists in the atlas folder");
    }
}
