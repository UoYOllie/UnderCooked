package food;

import com.badlogic.gdx.utils.Array;
import food.FoodItem.FoodID;

/** The DishStack of the customer, designed to carry all the items in an assembled
 * dish in order. Items cannot be popped or added to the DishStack, only the entire
 * DishStack can be manipulated.*/
public class DishStack {

    /** The array containing the items of the DishStack.*/
    private Array<FoodID> dishStack;

    /** Constructor for an empty dishStack. */
    public DishStack() {
        this.dishStack = new Array<FoodID>();
        //this(new Array<FoodID>());
    }

    /** Getter for Array<FoodID> of the entire stack.*/
    public Array<FoodID> getStack() {
        return dishStack;
    }

    /** Setter for Array<FoodID> of the entire stack.
     * @param inputStack the Array of FoodID to transform into a DishStack.
     * */
    public void setStack(Array<FoodID> inputStack) {
        this.dishStack = inputStack;
    }

    /** Setter for Array<FoodID> of the entire stack, adding a plate to the bottom.
     * @param inputStack the Array of FoodID to transform into a DishStack.
     * */
    public void setStackPlate(Array<FoodID> inputStack) {
        this.dishStack = inputStack;
        this.dishStack.add(FoodID.plate);
    }

    /** Method to return the size of the DishStack.*/
    public int size() {
        return dishStack.size;
    }

    /** Method to empty the DishStack.*/
    public void clearStack() {
        dishStack.clear();
    }

    /** Method to return an Array<FoodID> containing the same FoodID as the DishStack
     * without pointing to the original DishStack.
     * @return Array<FoodID> The items that were in the DishStack, ordered.*/
    public Array<FoodID> getStackCopy() {

        Array<FoodID> newFoodStack = new Array<>();

        for (FoodID item : this.dishStack) {
            newFoodStack.add(item);
        }

        return newFoodStack;

    }

    /** Method to determine whether a DishStack is empty.
     * @return true If the DishStack is empty, false otherwise.*/
    public boolean empty() {

        if (this.dishStack.size > 0) {
            return false;
        } else {
            return true;
        }
    }

}
