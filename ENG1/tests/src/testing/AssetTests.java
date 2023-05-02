package testing;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import static org.testng.Assert.*;

@RunWith(GdxTestRunner.class)
public class AssetTests {

	public static InputStream assetList = Gdx.files.internal("assets.txt").read();

	// This relates to the UR_GRAPHICS requirement as well as the non_functional requirement of NFR_GRAPHIC
	// The following are tests to do with checking that the assets in the cooks folder still remain
	@Test
	public void testAssets() throws FileNotFoundException {
		System.out.println();
		Scanner fileReader = new Scanner(assetList);
		while (fileReader.hasNextLine()) {
			String data = fileReader.nextLine();
			String message = String.format("This test will only pass if %s is present", data);
			assertTrue(Gdx.files.internal(data).exists(), message);
		}

	}
}






















