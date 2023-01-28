package interactions;

import java.util.HashMap;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import food.FoodItem.FoodID;
import stations.Station.StationID;

public class Interactions {
    /// Since interactions and interaction() don't need to be in FoodItem.java or Station.java to work, it may not be very suitable there

    /** A HashMap containing how each FoodItem's FoodID, via a station of StationID, can convert to another foodID.*/
    private static final HashMap<String, InteractionResult> interactions = new HashMap<>();
    static {
        interactions.put(InteractionKey(FoodID.lettuce, StationID.cut), new InteractionResult(FoodID.lettuceChop,new float[] {25,50,75},-1));
        interactions.put(InteractionKey(FoodID.tomato, StationID.cut), new InteractionResult(FoodID.tomatoChop,new float[] {25,50,75},-1));
        interactions.put(InteractionKey(FoodID.onion, StationID.cut), new InteractionResult(FoodID.onionChop,new float[] {25,50,75},-1));
        interactions.put(InteractionKey(FoodID.meat, StationID.fry), new InteractionResult(FoodID.meatCook,new float[] {50},0.5F));
    }

    public static class InteractionResult {
        /**
         * @param result -
         *               The resulting FoodID
         * @param steps -
         *              The steps in a range of 0 - 100 of the process where input is required
         * @param speed -
         *              The speed of which the progress bar fills up for a station. -1 is instant.
         */
        private FoodID result;
        private float[] steps;
        private float speed;
        public InteractionResult(FoodID result, float[] steps, float speed) {
            this.result = result;
            this.steps = steps;
            this.speed = speed;
        }

        public FoodID getResult() { return result; }
        public float[] getSteps() { return steps; }
        public float getSpeed() { return speed; }
    }

    /** The different IDs of interaction. Used to get the Arrays. */
    public enum InputID {
        INTERACT
    }


    /** A HashMap containing all different forms of user inputs. These can easily
     * be changed / modified as needed from here, instead of searching through the
     * code.
     *
     * The InputKeys returned can then be looped through, and checked using the appropriate
     * Gdx.input.isKeyPressed function.
     *
     * This means dynamic key changing can be added, if you change this function from static,
     * and multiple keys can be assigned to one control.
     * */
    private static final HashMap<InputID, Array<InputKey>> inputs = new HashMap<>();
    static {
        inputs.put(InputID.INTERACT, new Array<>(new InputKey[]{
                new InputKey(InputKey.InputTypes.USE, Input.Keys.K),
                new InputKey(InputKey.InputTypes.PICK_UP, Input.Keys.J),
                new InputKey(InputKey.InputTypes.PUT_DOWN, Input.Keys.L)
        }));
    }

    public static Array<InputKey> getInputKeys(InputID inputID) {
        return inputs.get(inputID);
    }

    /**
    * Convert a FoodItem's foodID into another foodID using station of stationID.
    * @param foodID : The FoodID of the input ingredient.
    * @param stationID : The StationID of the station being used.
    * @return FoodID of the new ingredient, OR null if the station cannot interact with this foodID.
    */
    public static InteractionResult interaction(FoodID foodID, StationID stationID) {
        InteractionResult newResult = interactions.get(InteractionKey(foodID, stationID));
        return newResult;
    };

    private static String InteractionKey(FoodID foodID, StationID stationID) {
        return String.format("%s-%s", foodID.ordinal(), stationID.ordinal());
    }
}
