package testing;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
@RunWith(GdxTestRunner.class)
public class AssetTests {
    @Test
    public void testChefAssetExists(){
        assertTrue("This test will only pass if hold down.png", Gdx.files.internal("hold down.png").exists());
    }
}
