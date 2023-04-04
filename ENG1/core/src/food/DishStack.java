package food;

import com.badlogic.gdx.utils.Array;
import food.FoodItem.FoodID;

public class DishStack {

    private Array<FoodID> dishStack;

    /** Constructor for an empty dishStack. */
    public DishStack() {
        this(new Array<FoodID>());
    }

    /** Constructor for a dishStack taking an array of items as a parameter.*/
    public DishStack(Array<FoodID> inputStack) {
        dishStack = inputStack;
    }

    /** Getter for Array<FoodID> of the entire stack.*/
    public Array<FoodID> getStack() {
        return dishStack;
    }

    /** Setter for Array<FoodID> of the entire stack.*/
    public void setStack(Array<FoodID> inputStack) { dishStack = inputStack;}

    public void clearStack() {
        dishStack.clear();
    }

}
