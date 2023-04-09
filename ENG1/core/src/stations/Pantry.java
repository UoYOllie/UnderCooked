package stations;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import cooks.Cook;
import food.FoodItem.FoodID;
import interactions.InputKey;

/**
 * Allows Cook to take the corresponding FoodItem from the pantry.
 * There is no limit on the number of items taken from the pantry.
 */
public class Pantry extends Station {

    /** The food dispensed by the instance of pantry.*/
    FoodID foodDispensed;

    /**
     * Constructor for a pantry.
     * @param rectangle The collision and interaction area of the pantry.
     */
    public Pantry(Rectangle rectangle) {
        super(rectangle);
    }

    /**
     * Assigns a food item that will be dispensed by the pantry.
     * @param foodID the foodID of the item to be assigned.
     */
    public void setItem(FoodID foodID) {
        this.foodDispensed = foodID;
    }

    /**
     * Method to allow a Cook to interact with the Pantry.
     * A Cook will pick up an item if they use the PICK_UP key (up arrow).
     *
     * @param cook The Cook that is currently interacting with the parameter.
     * @param inputType The input from the Cook.
     */
    public void interact(Cook cook, InputKey.InputTypes inputType) {

        // Do not allow interactions with the pantry if the chef is holding a dish.
        if (cook.getBlocked() == true) { return; }

        // Take an item from the pantry if the input is to pick up.
        if (inputType == InputKey.InputTypes.PICK_UP || inputType == InputKey.InputTypes.USE) {
            cook.foodStack.addStack(foodDispensed);
        }
    }

    /**
     * Unused method to update the pantry.
     * @param delta The time between frames.
     */
    @Override
    public void update(float delta) { }

    /**
     * Unused method to render items onto the Pantry.
     * @param batch The SpriteBatch to render.
     */
    @Override
    public void render(SpriteBatch batch) { }

    /**
     * Unused method to render items onto the Pantry's debug visuals.
     * @param batch The SpriteBatch to render.
     */
    @Override
    public void renderDebug(SpriteBatch batch) { }

//    /**
//     * Unused method to render the Pantry.
//     * @param shape The shape to be rendered.
//     */
//    @Override
//    public void renderShape(ShapeRenderer shape) { }


//    /**
//     * The function used to render the {@link Pantry}'s
//     * debug visuals.
//     * It is unused.
//     * @param shape The {@link ShapeRenderer} used to render.
//     */
//    @Override
//    public void renderShapeDebug(ShapeRenderer shape) { }
}
