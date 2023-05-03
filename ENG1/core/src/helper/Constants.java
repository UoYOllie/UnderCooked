package helper;

import com.badlogic.gdx.math.Vector2;

/** A class for variables that remain Constant
 * and are used across the code.
 */
public class Constants {

    /** The ViewPort / Window Width. */
    public static final int V_Width = 960;

    /** The ViewPort / Window Height. */
    public static final int V_Height = 640;

    /** The width of the Cook. */
    public static final float CookWidth = 3.34f;

    /** The height of the Cook. */
    public static final float CookHeight = 1f;

    /** The universal unit scale for the Map. */
    public static final float UnitScale = 1/8f;


    /** The coordinates of the points that a customer visits when entering and exiting the restaurant. */
    public static final Vector2 customerPointA = new Vector2 (1978.075f, 2915.9f);
    public static final Vector2 customerPointB = new Vector2 (1978.075f, 2913f);
    public static final Vector2 customerPointC = new Vector2 (1939f, 2913f);
    public static final Vector2 customerPointD = new Vector2 (1939.4f, 2872f);
    public static final Vector2 customerPointE = new Vector2 (1915f, 2872f);
    public static final Vector2 customerPointF = new Vector2 (1915f, 2817.9171f);
}
