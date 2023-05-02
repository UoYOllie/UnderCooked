package helper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * A class for variables that remain Constant
 * that are needed all across the code.
 */
public class Constants {

    /** Pixels Per Metre. */
    public static final float PPM = 32.0f; //old
    /** The ViewPort / Window Width. */
    public static final int V_Width = 960;
    /** The ViewPort / Window Height. */
    public static final int V_Height = 640;

    /** The {Customer} default spawn position */
    public static final Vector2 customerSpawn= new Vector2(2012.0625f, 2855.9087f);
    /** The location that the {@link food.Recipe} being checked is rendered. */
    public static final float RECIPE_X = 928F, RECIPE_Y = 608F;

    //New Constants
    public static final float CookWidth = 3.34f;
    public static final float CookHeight = 1f;
    public static final float UnitScale = 1/8f;

    public static final Vector2 customerSpawnPoint = new Vector2(1915f, 2869.0442f);
    public static final Vector2 customerToStationPoint = new Vector2(1915f, 2817.9171f);
    public static final float customerSplitPoint = 2817.9171f;

    // customer travelling down the corridor
    public static final Vector2 customerPointA = new Vector2 (1978.075f, 2915.9f);
    public static final Vector2 customerPointB = new Vector2 (1978.075f, 2913f);
    public static final Vector2 customerPointC = new Vector2 (1939f, 2913f);
    public static final Vector2 customerPointD = new Vector2 (1939.4f, 2872f);
    public static final Vector2 customerPointE = new Vector2 (1915f, 2872f);
    public static final Vector2 customerPointF = new Vector2 (1915f, 2817.9171f);

    public static final Array<Vector2> customerPoints = new Array<>();

}
