package game;

import com.badlogic.gdx.utils.Array;
import food.DishStack;
import food.FoodItem;
import food.FoodStack;

/**
 * Stores the data collected for a station in the loading process
 */
public class StationData
{
    public int StationPropertyID = -1;
    public FoodStack HeldFood = new FoodStack();
    public DishStack stationdishstack = new DishStack();
    public boolean lock = false;
    public boolean Enabled;
}
