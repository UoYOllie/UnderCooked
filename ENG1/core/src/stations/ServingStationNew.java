package stations;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import cooks.Cook;
import cooks.CustomerNew;
import food.FoodItem;
import food.DishStack;
import game.GameSprites;
import helper.Constants;
import interactions.InputKey;


public class ServingStationNew extends Station {

    private DishStack servedDishStack;
    public CustomerNew customer;

    public ServingStationNew(Rectangle rectangle) {
        super(rectangle);
        servedDishStack = new DishStack();
        customer = null;
    }

    public boolean hasCustomer() {
        return customer == null;
    }

    public CustomerNew getCustomer() {
        return customer;
    }

    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {

        switch (inputType) {
            case PUT_DOWN:
                if (!cook.dishStack.empty() && servedDishStack.empty()) {
                    this.servedDishStack.setStack(cook.dishStack.getStackCopy());
                    cook.dishStack.clearStack();
                }
                break;
            case PICK_UP:
                if (!servedDishStack.empty() && cook.dishStack.empty()) {
                    cook.dishStack.setStack(servedDishStack.getStackCopy());
                    servedDishStack.clearStack();
                }
                break;
        }
    }

    @Override
    public void render(SpriteBatch batch) {

        Array<FoodItem.FoodID> dishList = servedDishStack.getStack();
        float xOffset = 0F, yOffset = 0F;
        float drawX = x, drawY = y;

        // Draw each FoodItem in DishList.
        for (int i = dishList.size-1 ; i >= 0 ; i--) {
            Sprite foodSprite = gameSprites.getSprite(GameSprites.SpriteID.FOOD, String.valueOf(dishList.get(i)));
            foodSprite.setScale(Constants.UnitScale);
            foodSprite.setPosition(drawX-foodSprite.getWidth()/3 + xOffset - 3.5f * Constants.UnitScale,
                    drawY - foodSprite.getHeight() * 0.33f +yOffset* Constants.UnitScale);
            foodSprite.draw(batch);
            drawY += FoodItem.foodHeights.get(dishList.get(i)) * 0.25F;
        }
    }

}
