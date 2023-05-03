package food;

import com.badlogic.gdx.utils.Array;
import players.Cook;
import food.FoodItem.FoodID;

/**
 * A class to mimic the behaviour of a FIFO Stack for FoodItems to be used by
 * the cook, stations and recipes.
 */
public class FoodStack {

    /** The cook's stack containing all the items they're holding. Index 0 = Top Item.*/
    private Array<FoodID> foodStack;

    /** The constructor for an empty FoodStack.*/
    public FoodStack() {
        this(new Array<FoodID>());
    }

    /** The constructor of a FoodStack already containing items.
     * @param foods A list of ingredients to add to the foodStack. Index 0 = Top.
     */
    public FoodStack(FoodID... foods) {
        this(new Array<FoodID>());
        for (FoodID foodID : foods) {
            foodStack.add(foodID);
        }
    }

    /**
     * The constructor of a FoodStack from an array of food items.
     * @param foodStack The LibGDX Array of FoodIDs which will become a FoodStack.
     */
    public FoodStack(Array<FoodID> foodStack) {
        this.foodStack = foodStack;
    }

    /** The setter for the FoodStack.*/
    public void setStack(Array<FoodID> newStack) {
        foodStack = newStack;
    }

    /** The getter of the FoodStack.*/
    public Array<FoodID> getStack() {
        return foodStack;
    }

    /**
    * Get the item at the top of the stack without removing it.
    * @return The item at the top of the foodStack OR `null` if there's no items in it.
    */
    public FoodID peekStack() {
        System.out.println("peek");
        try {
            return foodStack.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
    * Get the item at the top of the stack and remove it if there is an item in the foodStack.
    * @return The item at the top of the foodStack OR `null` if there's no items in it.
    */
    public FoodID popStack() {
      try {
          return foodStack.removeIndex(0);
      } catch (IndexOutOfBoundsException e) {
          return null;
      }
    }

    /**
    * Add an item to the top of the stack.
    * @param newFood The item to add to the top of the foodStack.
    */
    public void addStack(FoodID newFood) {
        if(this.size()<3) {
            foodStack.insert(0, newFood);
        }
    }

    /**
     * Add an item to the top of the stack with a different limit to the norm.
     * Used in cases like CounterStation, where only one item can be placed.
     *
     * @param newFood the FoodID to be added.
     * @param limit the maximum number of items that can be added to the stack.
     * */
    public void addStackLimited(FoodID newFood, int limit) {
        if(this.size()<limit) {
            foodStack.insert(0, newFood);
        }
    }

    /** Method to indicate whether a FoodStack is empty.
     * @return true if empty, false if not. */
    public boolean empty() {
        if (this.foodStack.size > 0) {
            return false;
        } else {
            return true;
        }
    }

    /** Removes all items from the stack.*/
    public void clearStack() {
        foodStack.clear();
    }

    /**
    * Returns the number of items on the foodStack.
    * @return Size of the foodStack
    */
    public int size() { return foodStack.size; }

    /**
    * Get a string of the foodStack.
    * @return Returns a string containing the items in the foodStack. Index 0 = Top FoodItem
    */
    public String toString() {
        return foodStack.toString();
    }

    /** Return a copy of the Array<FoodID> stored by the FoodStack.
     * Used to avoid pointing to the original stack when duplicating a FoodStack.
     *
     * @return Array<FoodID> A copy of the FoodStack's Array<FoodID>.*/
    public Array<FoodID> getStackCopy() {

        Array<FoodID> newFoodStack = new Array<>();
        for (FoodID item : this.foodStack) {
            newFoodStack.add(item);
        }
        return newFoodStack;

    }
}
