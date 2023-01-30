package helper;

import com.badlogic.gdx.math.Vector2;

/**
 * A class for variables that remain Constant
 * that are needed all across the code.
 */
public class Constants {

    /** Pixels Per Metre. */
    public static final float PPM = 32.0f;
    /** The ViewPort / Window Width. */
    public static final int V_Width = 960;
    /** The ViewPort / Window Height. */
    public static final int V_Height = 640;

    /** The scale of the {@link customers.Customer}s.*/
    public static  final float customerScale = 0.2f;
    /** The {@link customers.Customer} default spawn position */
    public static final Vector2 customerSpawn= new Vector2(425,470);
}
