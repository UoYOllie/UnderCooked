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

public class AssemblyStation extends Station {

    private FoodStack stationFoodStack;
    private DishStack stationDishStack;

    public AssemblyStation(Rectangle rectangle) {
        super(rectangle);
        stationFoodStack = new FoodStack();
        stationDishStack = new DishStack();
    }

    private boolean cookHolding(Cook cook) {
        if (cook.dishStack.size() == 0
            && cook.foodStack.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {

        if (inputType == InputKey.InputTypes.PUT_DOWN) {

            // Put down a FoodItem from the cook's FoodStack to the station's FoodStack.
            if (!cook.foodStack.empty() && cook.dishStack.empty()
                    && stationFoodStack.size() < 6 && stationDishStack.empty()) {
                stationFoodStack.addStackLimited(cook.foodStack.popStack(), 6);
                return;
            }
        }

        if (inputType == InputKey.InputTypes.PICK_UP) {

            // Pick up a FoodItem from the station's FoodStack to the cook's FoodStack.
            if (cook.foodStack.size() < 3 && cook.dishStack.empty()
                    && !stationFoodStack.empty() && stationDishStack.empty()) {
                cook.foodStack.addStack(stationFoodStack.popStack());
                return;
            }

            // Pick up the station's entire DishStack onto the cook's entire DishStack.
            if (cook.foodStack.empty() && cook.dishStack.empty()
                    && stationFoodStack.empty () && !stationDishStack.empty()) {
                cook.dishStack.setStack(stationDishStack.getStackCopy());
                stationDishStack.clearStack();
                return;
            }
        }


        if (inputType == InputKey.InputTypes.USE) {

            // If the recipe is valid, assemble the station's FoodStack into the station's DishStack.
            if (cook.dishStack.empty() && stationDishStack.empty()
                    && food.Recipe.validRecipe(stationFoodStack)){
                stationFoodStack = Recipe.orderStack(stationFoodStack);
                stationDishStack.setStackPlate(stationFoodStack.getStackCopy());
                stationFoodStack.clearStack();
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        Array<FoodItem.FoodID> foodList = stationFoodStack.getStack();
        float xOffset = 0F, yOffset = 0F;
        float drawX = x-1.1f, drawY = y;

        for (int i = foodList.size-1 ; i >= 0 ; i--) {
            Sprite foodSprite = gameSprites.getSprite(GameSprites.SpriteID.FOOD, String.valueOf(foodList.get(i)));
            foodSprite.setScale(0.6f*Constants.UnitScale);
            foodSprite.setPosition(drawX-foodSprite.getWidth()/3 + xOffset * Constants.UnitScale,drawY - foodSprite.getHeight() * 0.354f);
            foodSprite.draw(batch);
            xOffset += 16f;
        }


        Array<FoodID> dishList = stationDishStack.getStack();
        xOffset = 0F;
        yOffset = 0F;

        drawX = x;
        drawY = y;

        for (int i = dishList.size-1 ; i >= 0 ; i--) {
            Sprite foodSprite = gameSprites.getSprite(GameSprites.SpriteID.FOOD, String.valueOf(dishList.get(i)));
            Float drawInc = FoodItem.foodHeights.get(dishList.get(i)) * 0.25F;
            if (drawInc == null) {
                drawY += 5F * Constants.UnitScale;
                continue;
            }
            // change size of rendered food here:
            foodSprite.setScale(Constants.UnitScale);

            foodSprite.setPosition(drawX-foodSprite.getWidth()/3 + (xOffset + 95.2f) * Constants.UnitScale,
                    drawY - foodSprite.getHeight() * 0.33f +yOffset* Constants.UnitScale);
            foodSprite.draw(batch);
            drawY += drawInc;
        }
    }

}
