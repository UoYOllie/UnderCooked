package stations;

import shop.ShopItem;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import players.Cook;
import food.FoodItem;
import game.GameScreen;
import interactions.InputKey;

/**
 * Dispenses teacups and "change your mind" items
 */
public class PowerupPantry extends Pantry{
    private GameScreen gameScreen;
    private ShopItem item;

    /**
     * Constructor used to set the item for dispensing
     * Enables the pantry
     * @param rectangle
     * @param g
     */
    public PowerupPantry(Rectangle rectangle, GameScreen g) {
        super(rectangle);
        this.gameScreen = g;
        this.rectangle = rectangle;
        Enable();
        this.item = gameScreen.Powerup_Items;
    }

    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        if((this.getEnabled())&&(gameScreen.gold.getBalance()-item.cost>=0) ){
            gameScreen.gold = item.buy(gameScreen.gold);
            // Do not allow interactions with the pantry if the chef is holding a dish.
            if (cook.getBlocked() == true) { return; }

            // Take an item from the pantry if the input is to pick up.
            if (inputType == InputKey.InputTypes.PICK_UP || inputType == InputKey.InputTypes.USE) {
                if(cook.foodStack.empty()) {
                    Array<FoodItem.FoodID> temp = new Array<FoodItem.FoodID>();
                    temp.add(foodDispensed);
                    cook.dishStack.setStack(temp);
                }
            }
        }
    }
}
