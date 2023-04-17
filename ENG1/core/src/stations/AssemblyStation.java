package stations;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import cooks.Cook;
import food.FoodItem;
import food.FoodItem.FoodID;
import food.FoodStack;
import food.DishStack;
import food.Recipe;
import game.GameSprites;
import helper.Constants;
import interactions.InputKey;

/** Station to assemble items into dishes.

 * Up to six FoodItems can be picked up and put down on the station.
 * If the AssemblyStation's FoodStack makes up a valid recipe when interacted with,
 * they are turned into an assembled DishStack which can be picked up by cooks.

 * @requirements FR_ASSEMBLE
 * */
public class AssemblyStation extends Station {

    /** The Assembly Station's FoodStack of FoodItem to assemble. */
    private FoodStack stationFoodStack;

    /** The Assembly Station's DishStack of FoodItem assembled in order according to a recipe. */
    private final DishStack stationDishStack;

    /** Constructor for AssemblyStation.
     * Initialises interaction Rectangle and empty FoodStack and DishStack.

     * @param rectangle The station's interaction rectangle. */
    public AssemblyStation(Rectangle rectangle) {
        super(rectangle);
        stationFoodStack = new FoodStack();
        stationDishStack = new DishStack();
    }

    /***
     * A helper method to test if both the cook's DishStack and the station's DishStack
     * are empty. Used in the interact method to check interactions are valid.

     * @param cook The cook currently interacting with the AssemblyStation.
     * @return true if both DishStacks are empty, false otherwise.
     */
    private boolean allDishStacksEmpty(Cook cook) {
        return cook.dishStack.size() == 0 && stationDishStack.size() == 0;
    }

    /** The method to control interactions between a cook and the AssemblyStation.

     * PUT_DOWN puts the cook's FoodItem down onto the AssemblyStation.
     * PICK_UP picks up a FoodItem or DishStack depending on what is on the AssemblyStation.
     * USE assembles the AssemblyStation's FoodStack into a DishStack when the recipe is valid.

     * @param cook The cook currently interacting with the AssemblyStation.
     * @param inputType The input received from the cook currently interacting.
     */
    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {

        switch (inputType) {

            case PUT_DOWN:
                // Put down a FoodItem from the cook's FoodStack to the station's FoodStack.
                if (!cook.foodStack.empty() && stationFoodStack.size() < 6
                        && allDishStacksEmpty(cook)) {
                    stationFoodStack.addStackLimited(cook.foodStack.popStack(), 6);
                }
                break;


            case PICK_UP:
                // Pick up a FoodItem from the station's FoodStack to the cook's FoodStack.
                if (cook.foodStack.size() < 3 && !stationFoodStack.empty()
                        && allDishStacksEmpty(cook)) {
                    cook.foodStack.addStack(stationFoodStack.popStack());
                }
                // Pick up the station's entire DishStack onto the cook's entire DishStack.
                if (cook.foodStack.empty() && stationFoodStack.empty()
                        && cook.dishStack.empty () && !stationDishStack.empty()) {
                    cook.dishStack.setStack(stationDishStack.getStackCopy());
                    stationDishStack.clearStack();
                }
                break;

            case USE:
                if (allDishStacksEmpty(cook) && food.Recipe.validRecipe(stationFoodStack) != null) {
                    FoodStack tempStack = Recipe.validRecipe(stationFoodStack);
                    stationDishStack.setStackPlate(tempStack.getStackCopy());
                    stationFoodStack.clearStack();
                }
        }
    }

    /** Method to render items onto the AssemblyStation.

     * Renders each FoodItem of the FoodStack into its own box on the AssemblyStation, and the final DishStack
     * into the big box on the end.

     * @param batch the SpriteBatch to be rendered.
     */
    @Override
    public void render(SpriteBatch batch) {

        // Render the FoodStack onto the AssemblyStation.
        Array<FoodItem.FoodID> foodList = stationFoodStack.getStack();
        float xOffset = 0F, yOffset = 0F;
        float drawX = x-1.1f, drawY = y;

        // Draw each FoodItem in foodList.
        for (int i = foodList.size-1 ; i >= 0 ; i--) {

            Sprite foodSprite = gameSprites.getSprite(GameSprites.SpriteID.FOOD, String.valueOf(foodList.get(i)));
            foodSprite.setScale(0.6f*Constants.UnitScale);
            foodSprite.setPosition(drawX-foodSprite.getWidth()/3 + xOffset * Constants.UnitScale,
                                    drawY - foodSprite.getHeight() * 0.354f);
            foodSprite.draw(batch);

            // Render the next item to the right of the current item.
            xOffset += 16f;
        }

        // Render the DishStack onto the AssemblyStation.
        Array<FoodID> dishList = stationDishStack.getStack();
        xOffset = 0F;
        drawX = x;
        drawY = y;

        // Draw each FoodItem in dishList.
        for (int i = dishList.size-1 ; i >= 0 ; i--) {

            Sprite foodSprite = gameSprites.getSprite(GameSprites.SpriteID.FOOD, String.valueOf(dishList.get(i)));
            foodSprite.setScale(Constants.UnitScale);
            foodSprite.setPosition(drawX-foodSprite.getWidth()/3 + (xOffset + 95.2f) * Constants.UnitScale,
                                    drawY - foodSprite.getHeight() * 0.33f +yOffset* Constants.UnitScale);
            foodSprite.draw(batch);

            // Render the next item slightly above the current item (as a stack).
            drawY += FoodItem.foodHeights.get(dishList.get(i)) * 0.25F;
        }
    }
}
