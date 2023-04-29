package stations;

import com.badlogic.gdx.math.Rectangle;
import cooks.Cook;
import interactions.InputKey;

/** The Bin Station Class.
 * Contains what happens when the station is interacted with.
 */
public class BinStation extends Station {

    /**
     * The constructor for the {@link BinStation}.
     * @param rectangle The collision and interaction area of the {@link Station}.
     */
    public BinStation(Rectangle rectangle) {
        super(rectangle);
    }

    /**
     * The interact function for the {@link BinStation}.
     *
     * This takes the top item from the {@link Cook}'s {@link food.FoodStack}
     * if they use either the {@link InputKey.InputTypes#USE} or
     * {@link InputKey.InputTypes#PUT_DOWN} keys.
     * @param cook The cook that interacted with the {@link CookInteractable}.
     * @param inputType The type of {@link InputKey.InputTypes} the player made with
     *                  the {@link CookInteractable}.
     */
    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        cook.lockmovement = false;

        if (inputType == InputKey.InputTypes.PUT_DOWN) {
            if (cook.foodStack.size() > 0 && cook.dishStack.empty()) {
                cook.foodStack.popStack();
            } else if (cook.dishStack.size() > 0 && cook.foodStack.empty()) {
                cook.dishStack.clearStack();
            }
        }
    }
}
