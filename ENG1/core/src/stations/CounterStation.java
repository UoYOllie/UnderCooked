package stations;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import players.Cook;
import food.DishStack;
import food.FoodItem;
import food.FoodStack;
import game.GameSprites;
import helper.Constants;
import interactions.InputKey;

/**
 * The CounterStation class, where the cook
 * can place down the FoodItems that they are holding.
 */
public class CounterStation extends Station {

    /**
     * The constructor for the CounterStation. It sets up the
     * FoodStack for the CounterStation.
     * @param rectangle The collision and interaction area of the PreparationStation.
     */
    public CounterStation(Rectangle rectangle) {
        super(rectangle);
        stationFoodStack = new FoodStack();
        stationDishStack = new DishStack();
    }

    /**Helper method to determine whether both the station and the cook's
     * dishStacks are empty, used to allow interactions with the FoodStack.
     *
     * @param cook The cook interacting with the CounterStation.
     * @return true if both DishStacks are empty (interaction is allowed),
     *          or false otherwise. */
    private boolean allDishStacksEmpty(Cook cook) {
        return cook.dishStack.size() == 0 && stationDishStack.size() == 0;
    }

    /**
     * The interact method for the CounterStation.
     *
     * When the player uses the PUT_DOWN key, the top item from the cook's foodStack is popped and added
     * onto the counter, if the counter is empty.
     * When the player uses the PICK_UP key, the item on the CounterStation is popped and added to the cook's FoodStack,
     * if their stack is under the limit.
     *
     * @param cook The cook that interacted with the CounterStation.
     * @param inputType The type of input the player made with the CounterStation.
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
                System.out.println("PICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUP");
                System.out.println(this.stationFoodStack);
//                System.out.println(stationFoodStack.empty());
                System.out.println((cook.foodStack.size() < 3 && !stationFoodStack.empty()));
                System.out.println(allDishStacksEmpty(cook));

                // Pick up a FoodItem from the station's FoodStack to the cook's FoodStack.
                if (cook.foodStack.size() < 3 && !stationFoodStack.empty()
                        && allDishStacksEmpty(cook)) {
                    cook.foodStack.addStack(stationFoodStack.popStack());
                    System.out.println(this.stationFoodStack);
                }
                // Pick up the station's entire DishStack onto the cook's entire DishStack.
                if (cook.foodStack.empty() && stationFoodStack.empty()
                        && cook.dishStack.empty () && !stationDishStack.empty()) {
                    cook.dishStack.setStack(stationDishStack.getStackCopy());
                    stationDishStack.clearStack();
                }
                System.out.println("PICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUPPICKUP");

                break;


        }
    }

    /**
     * The function used to render food onto the CounterStation.
     * @param batch The SpriteBatch to render.
     */
    @Override
    public void render(SpriteBatch batch) {

        // render the FoodStack onto the CounterStation
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
