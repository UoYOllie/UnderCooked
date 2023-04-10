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

public class ServingStationNew extends Station {

    private DishStack servedDishStack;
    private DishStack targetDishStack;
    //public Customer customer;

    public ServingStationNew(Rectangle rectangle) {
        super(rectangle);
        servedDishStack = new DishStack();
        targetDishStack = new DishStack();
    }

    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {

        if (cook.dishStack.size() > 0 && inputType == InputKey.InputTypes.PUT_DOWN) {
            this.servedDishStack.setStack(cook.dishStack.getStackCopy());
            cook.dishStack.clearStack();
            return;
        }

        if (servedDishStack.size() > 0 && inputType == InputKey.InputTypes.PICK_UP) {
            cook.dishStack.setStack(this.servedDishStack.getStackCopy());
            this.servedDishStack.clearStack();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        Array<FoodItem.FoodID> dishList = servedDishStack.getStack();
        float xOffset = 0F, yOffset = 0F;
        float drawX = x, drawY = y;


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
