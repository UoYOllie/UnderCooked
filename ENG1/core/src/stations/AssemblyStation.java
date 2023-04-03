package stations;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import cooks.Cook;
import food.FoodItem;
import food.FoodStack;
import game.GameSprites;
import helper.Constants;
import interactions.InputKey;
import interactions.Interactions;

public class AssemblyStation extends Station {

    public FoodStack stationFoodStack;

    public AssemblyStation(Rectangle rectangle) {
        super(rectangle);
        stationFoodStack = new FoodStack();
    }

    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {

        System.out.println("Im interacting with an assembly station");

        if (cook.foodStack.size() > 0 && inputType == InputKey.InputTypes.PUT_DOWN) {
            stationFoodStack.addStackUnlimited(cook.foodStack.popStack());
            return;
        }

        if (stationFoodStack.size() > 0 && inputType == InputKey.InputTypes.PICK_UP) {
            if (stationFoodStack.getMergeStatus()) {
                FoodStack tempStack = stationFoodStack;
                // If the above doesn't apply, then just swap the stacks.
                stationFoodStack = cook.foodStack;
                cook.foodStack = tempStack;
            } else {
                cook.foodStack.addStackUnlimited(stationFoodStack.popStack());
            }
            return;
        }

        if (inputType == InputKey.InputTypes.USE) {
              if (food.Recipe.assembleRecipe(stationFoodStack)) {
                  System.out.println("that is a valid recipe!");
                  stationFoodStack.mergeStack();
              }

        }
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
        /*if (foodStack.size() > 0) {
            foodStack.popStack();
        }*/
        for (int i = foodList.size-1 ; i >= 0 ; i--) {
            Sprite foodSprite = gameSprites.getSprite(GameSprites.SpriteID.FOOD, String.valueOf(foodList.get(i)));
            Float drawInc = FoodItem.foodHeights.get(foodList.get(i));
            if (drawInc == null) {
                drawY += 5F * Constants.UnitScale;
                continue;
            }
            foodSprite.setScale(2F * Constants.UnitScale);
            foodSprite.setPosition(drawX-foodSprite.getWidth()/3 + xOffset * Constants.UnitScale,drawY - foodSprite.getHeight() * 4/15f +yOffset* Constants.UnitScale);
            // setPosition(x - 1/3f * renderItem.getWidth(),y - 4/15f * renderItem.getHeight()
            foodSprite.draw(batch);
            drawY += drawInc;
        }
    }

}
