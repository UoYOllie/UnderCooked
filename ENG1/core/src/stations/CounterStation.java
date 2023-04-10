package stations;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import cooks.Cook;
import food.DishStack;
import food.FoodItem;
import food.FoodStack;
import food.Recipe;
import game.GameSprites;
import helper.Constants;
import interactions.InputKey;

/**
 * The {@link CounterStation} class, where the {@link Cook}
 * can place down the {@link FoodItem}s that they are holding.
 */
public class CounterStation extends Station {

    public FoodStack stationFoodStack;
    public DishStack stationDishStack;

    /**
     * The constructor for the {@link CounterStation}. It sets up the
     * {@link FoodStack} for the {@link CounterStation}.
     * @param rectangle The collision and interaction area of the {@link PreparationStation}.
     */
    public CounterStation(Rectangle rectangle) {
        super(rectangle);
        stationFoodStack = new FoodStack();
        stationDishStack = new DishStack();
    }

    private boolean allDishStacksEmpty(Cook cook) {
        return cook.dishStack.size() == 0 && stationDishStack.size() == 0;
    }

    /**
     * The function that allows a {@link Cook} to interact with the {@link CounterStation}.
     *
     * <br><br>If the {@link Cook} interacts using {@link InputKey.InputTypes#USE},
     * they will swap stacks with the {@link CounterStation}.
     *
     * <br><br>If the {@link Cook} interacts using {@link InputKey.InputTypes#PICK_UP},
     * they will take the top {@link FoodItem} of the {@link FoodStack}
     * from {@link CounterStation} and put it on their own {@link FoodStack}.
     *
     * <br><br>If the {@link Cook} interacts using {@link InputKey.InputTypes#PUT_DOWN},
     * they will take the top {@link FoodItem} of their {@link FoodStack}
     * put it on the {@link CounterStation}'s {@link FoodStack}.
     * @param cook The cook that interacted with the {@link CookInteractable}.
     * @param inputType The type of {@link InputKey.InputTypes} the player made with
     *                  the {@link CookInteractable}.
     */
    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        System.out.println("Im interacting with a counter");
        System.out.println(rectangle.getX());
        System.out.println(rectangle.getY());
        System.out.println(rectangle.getWidth());
        System.out.println(rectangle.getHeight());

        switch (inputType) {

            case PUT_DOWN:
                // Put down a FoodItem from the cook's FoodStack to the station's FoodStack.
                if (!cook.foodStack.empty() && stationFoodStack.size() < 1
                        && allDishStacksEmpty(cook)) {
                    stationFoodStack.addStackLimited(cook.foodStack.popStack(), 1);
                }
                // Put the cook's DishStack down onto the station's DishStack.
                if (cook.foodStack.empty() && stationFoodStack.empty()
                        && !cook.dishStack.empty() && stationDishStack.empty()) {
                    stationDishStack.setStack(cook.dishStack.getStackCopy());
                    cook.dishStack.clearStack();
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

        }

//        // If Cook is using the put down input, put down the item in the top of their stack
//        if (cook.foodStack.size() > 0 && inputType == InputKey.InputTypes.PUT_DOWN) {
//            // Take it from the cook, and add it to this counter's stack.
//            foodStack.addStack(cook.foodStack.popStack());
//            return;
//        }
//        // If Cook is using the pick up input, pick up the item on the top of this stack
//        if (foodStack.size() > 0 && inputType == InputKey.InputTypes.PICK_UP) {
//            // Take it from the cook, and add it to this counter's stack.
//            cook.foodStack.addStack(foodStack.popStack());
//            return;
//        }
//        // Otherwise swap the items on the use input
//        if (inputType == InputKey.InputTypes.USE) {
//            FoodStack tempStack = foodStack;
//            // If the above doesn't apply, then just swap the stacks.
//            foodStack = cook.foodStack;
//            cook.foodStack = tempStack;
//        }
    }

    /**
     * The function used to render the {@link PreparationStation}.
     *
     * <br>This function renders the {@link FoodStack} in a similar way
     * as the {@link Cook} renders their own.
     * @param batch The {@link SpriteBatch} used to render.
     */
    @Override
    public void render(SpriteBatch batch) {
        // Render using the same method as the Cook does.
        // Loop through the items in the food stack.
        // It is done from the end of the stack to the start because the stack's top is
        // at 0, and the bottom at the end.
        Array<FoodItem.FoodID> foodList = stationFoodStack.getStack();
        float xOffset = 0F, yOffset = 0F;
        // Get offset based on direction.

        float drawX = x, drawY = y;
        for (int i = foodList.size-1 ; i >= 0 ; i--) {
            Sprite foodSprite = gameSprites.getSprite(GameSprites.SpriteID.FOOD, String.valueOf(foodList.get(i)));
            Float drawInc = FoodItem.foodHeights.get(foodList.get(i));
            if (drawInc == null) {
                drawY += 5F * Constants.UnitScale;
                continue;
            }
            foodSprite.setScale(Constants.UnitScale);
            foodSprite.setPosition(x - 1/3f * foodSprite.getWidth(),y - 0.354f * foodSprite.getHeight());
            foodSprite.draw(batch);
            drawY += drawInc;
        }

        // Render the DishStack onto the CounterStation.
        Array<FoodItem.FoodID> dishList = stationDishStack.getStack();
        xOffset = 0F;
        drawX = x;
        drawY = y;

        // Draw each FoodItem in dishList.
        for (int i = dishList.size-1 ; i >= 0 ; i--) {

            Sprite foodSprite = gameSprites.getSprite(GameSprites.SpriteID.FOOD, String.valueOf(dishList.get(i)));
            foodSprite.setScale(Constants.UnitScale);
            foodSprite.setPosition(drawX-foodSprite.getWidth()/3 + xOffset * Constants.UnitScale,
                    drawY - foodSprite.getHeight() * 0.33f +yOffset* Constants.UnitScale);
            foodSprite.draw(batch);

            // Render the next item slightly above the current item (as a stack).
            drawY += FoodItem.foodHeights.get(dishList.get(i)) * 0.25F;
        }
    }
}
