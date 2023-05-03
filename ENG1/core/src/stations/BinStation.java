package stations;

import com.badlogic.gdx.math.Rectangle;
import players.Cook;
import interactions.InputKey;

/** The Bin Station Class.
 * Contains what happens when the station is interacted with.
 */
public class BinStation extends Station {

    /**
     * The constructor for the BinStation.
     * @param rectangle The collision and interaction area of the Station.
     */
    public BinStation(Rectangle rectangle) {
        super(rectangle);
    }

    /**
     * The interact method for the BinStation.
     *
     * Takes the top item from the Cook's FoodStack if they use the PUT_DOWN key.
     * @param cook The cook that interacted with the BinStation.
     * @param inputType The type of input the player made with the BinStation.
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
