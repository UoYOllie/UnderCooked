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

    public FoodStack stationFoodStack;
    public DishStack stationDishStack;

    public AssemblyStation(Rectangle rectangle) {
        super(rectangle);
        stationFoodStack = new FoodStack();
        stationDishStack = new DishStack();
    }

    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {

        if (cook.foodStack.size() > 0 && inputType == InputKey.InputTypes.PUT_DOWN) {
            stationFoodStack.addStackUnlimited(cook.foodStack.popStack());
            return;
        }

        if (inputType == InputKey.InputTypes.PICK_UP) {
            if (stationFoodStack.size() > 0) {
                cook.foodStack.addStack(stationFoodStack.popStack());
                return;
            } else if (stationDishStack.size() > 0) {
                cook.dishStack.setStack(stationDishStack.getStackCopy());
                stationDishStack.clearStack();
                System.out.println("this is the cook's DishStack:");
                System.out.println(cook.dishStack.getStack());
                System.out.println("this is the station's DishStack:");
                System.out.println(stationDishStack.getStack());
                return;
            } else {
                return;
            }
        }


        if (inputType == InputKey.InputTypes.USE) {
              if (food.Recipe.validRecipe(stationFoodStack)) {
                  System.out.println("this is a valid recipe!");
                  stationFoodStack = Recipe.orderStack(stationFoodStack);
                  stationDishStack.setStackPlate(stationFoodStack.getStackCopy());
                  stationFoodStack.clearStack();
                  System.out.println("this is the station's FoodStack:");
                  System.out.println(stationFoodStack.getStack());
                  System.out.println("this is the station's DishStack:");
                  System.out.println(stationDishStack.getStack());
              } else {
                  System.out.println("that is not a valid recipe!!!");
              }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        Array<FoodItem.FoodID> foodList = stationFoodStack.getStack();
        float xOffset = 0F, yOffset = 0F;
        float drawX = x, drawY = y;

        for (int i = foodList.size-1 ; i >= 0 ; i--) {
            Sprite foodSprite = gameSprites.getSprite(GameSprites.SpriteID.FOOD, String.valueOf(foodList.get(i)));
            Float drawInc = FoodItem.foodHeights.get(foodList.get(i));
            if (drawInc == null) {
                drawY += 5F * Constants.UnitScale;
                continue;
            }
            foodSprite.setScale(2F * Constants.UnitScale);
            foodSprite.setPosition(drawX-foodSprite.getWidth()/3 + xOffset * Constants.UnitScale,drawY - foodSprite.getHeight() * 4/15f +yOffset* Constants.UnitScale);
            foodSprite.draw(batch);
            drawY += drawInc;
        }


        Array<FoodID> dishList = stationDishStack.getStack();
        xOffset = 0F;
        yOffset = 0F;

        drawX = x;
        drawY = y;

        for (int i = dishList.size-1 ; i >= 0 ; i--) {
            Sprite foodSprite = gameSprites.getSprite(GameSprites.SpriteID.FOOD, String.valueOf(dishList.get(i)));
            Float drawInc = FoodItem.foodHeights.get(dishList.get(i)) * 0.5F;
            if (drawInc == null) {
                drawY += 5F * Constants.UnitScale;
                continue;
            }
            foodSprite.setScale(2F * Constants.UnitScale);
            foodSprite.setPosition(drawX-foodSprite.getWidth()/3 + xOffset * Constants.UnitScale,
                    drawY - foodSprite.getHeight() * 4/15f +yOffset* Constants.UnitScale);
            foodSprite.draw(batch);
            drawY += drawInc;
        }
    }

}
