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
}
