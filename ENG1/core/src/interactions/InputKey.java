package interactions;

import players.Cook;
import game.GameScreen;
import game.ScreenController;

/**
 * The class responsible for mapping keys on the
 * keyboard to enum constants of equivalent meaning.
 */
public class InputKey {

    /** All the different inputs available in the game. */
    public enum InputTypes {

        /** Start the game in endless mode. */
        START_ENDLESS,

        /** Start the game in scenario mode. */
        START_SCENARIO,

        /** Resetting the game. */
        RESET_GAME,

        /** Opening the InstructionScreen. */
        INSTRUCTIONS,

        /** Pausing the game. */
        PAUSE,

        /** Unpausing the game. */
        UNPAUSE,

        /** Opening the credits. */
        CREDITS,

        /** Quitting the game. */
        QUIT,

        /** Load a saved game */
        LOAD,

         /** Save a loaded game. */
        SAVE,

        /** Setting the difficulty of the game. */
        EASY,
        MEDIUM,
        HARD,

        /** Cook interactions.
        Put down an item onto a station. */
        PUT_DOWN,
        /** Pick up an item from a station or pantry. */
        PICK_UP,
        /** Use the station in front of the Cook. */
        USE,

        // COOK_MOVEMENT
        /** Player moving up. */
        COOK_UP,
        /** Player moving right. */
        COOK_RIGHT,
        /** Player moving down. */
        COOK_DOWN,
        /** Playter moving left. */
        COOK_LEFT,

        // COOK_MISC
        /** Swapping between the {@link Cook} in the {@link GameScreen}. */
        COOK_SWAP
    }

    /** The key on the keyboard, represented as an int. */
    private int key;

    /** The enum constant which is representing the key above. */
    private InputTypes inputType;

    /**
     * The InputKey Constructor
     * @param inputType The InputTypes enum constant.
     * @param key The key for the InputTypes input.
     */
    public InputKey(InputTypes inputType, int key) {
        this.key = key;
        this.inputType = inputType;
    }

    /** Getter for the key's integer. */
    public int getKey() {
        return key;
    }

    /** Getter for the InputTypes enum constants. */
    public InputTypes getType() {
        return inputType;
    }
}
