package testing;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class AssetTests {
    @Test
    public void testChefAssetExists(){
        assertTrue(Gdx.files.internal("hold down.png").exists(), "This test will only pass if hold down.png");
    }

    @Test
}
