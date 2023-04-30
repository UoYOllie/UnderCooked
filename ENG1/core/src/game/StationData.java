package game;

import com.badlogic.gdx.utils.Array;
import food.DishStack;
import food.FoodItem;
import food.FoodStack;

public class StationData
{
    public int StationPropertyID = -1;
    public FoodStack HeldFood = new FoodStack();
    public DishStack stationdishstack = new DishStack();
    public boolean lock = false;
    public boolean Enabled;
}
