package stations;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import players.Cook;
import players.Customer;
import food.FoodItem;
import food.DishStack;
import game.GameSprites;
import helper.Constants;
import interactions.InputKey;

import static food.Recipe.*;

/** Station to serve dishes to customers.
 *
 * One DishStack can be put down on the station at a time.
 * If there is a customer interacting with the station, and the customer's
 * request matches the DishStack on the station, the customer will take the DishStack.
 */
public class ServingStation extends Station {

    /** Test flag is used to separate the class from GameScreen and allow testing.*/
    private Integer testFlag = 0;

    /** Constructor for ServingStation.
     * Initialises interaction Rectangle and empty DishStack.

     * @param rectangle The station's interaction rectangle. */
    public ServingStation(Rectangle rectangle) {
        super(rectangle);
        stationDishStack = new DishStack();
    }

    /** Getter for the DishStack on the station.*/
    public DishStack getServedDishStack(){
        return stationDishStack;
    }

    /** The method to control interactions between a cook and the ServingStation.

     * PUT_DOWN puts the cook's DishStack down onto the ServingStation.
     * PICK_UP picks up a DishStack from the ServingStation that has not been taken by a customer.

     * @param cook The cook currently interacting with the ServingStation.
     * @param inputType The input received from the cook currently interacting.
     */
    public void interact(Cook cook, InputKey.InputTypes inputType) {

        switch (inputType) {
            case PUT_DOWN:
                if (!cook.dishStack.empty() && stationDishStack.empty()) {
                    this.stationDishStack.setStack(cook.dishStack.getStackCopy());
                    cook.dishStack.clearStack();
                }
                break;
            case PICK_UP:
                if (!stationDishStack.empty() && cook.dishStack.empty()) {
                    cook.dishStack.setStack(stationDishStack.getStackCopy());
                    stationDishStack.clearStack();
                }
                break;
        }
    }

    /** The method to control interactions between a customer and the ServingStation.
     * If the customer is colliding with the servingStation, it will continually interact.
     *
     * If the DishStack on the ServingStation matches the customer's request, the DishStack will be taken
     * by the customer. The customer will also accept PowerUp DishStacks and the methods corresponding to the powerups
     * will be called in the customer.
     *
     * @param customer The cook currently interacting with the ServingStation.
     */
    @Override
    public void customerInteract(Customer customer) {

        Array<FoodItem.FoodID> plateless = stationDishStack.getStackCopy();

        // POWERUPS
        Array<FoodItem.FoodID> teacup_item = new Array<FoodItem.FoodID>();
        Array<FoodItem.FoodID> menu_item = new Array<FoodItem.FoodID>();
        teacup_item.add(FoodItem.FoodID.teacup);
        menu_item.add(FoodItem.FoodID.menu);

        if (plateless.size > 0) {
            if(plateless.get(0) == teacup_item.get(0)){
                System.out.println(customer + "will leave in "+ customer.waittime);
                customer.HangOnYourFoodIsComing();
                System.out.println(customer + "now will leave in "+ customer.waittime);
                customer.dishStack.setStack(teacup_item);
                stationDishStack.clearStack();
            }
            else if(plateless.get(0) == menu_item.get(0)){
                System.out.println(customer + "is rethinking");
                customer.Hypnotise();
                stationDishStack.clearStack();
            }
            else {
                plateless.removeIndex(plateless.size - 1);
            }
        }

        if (matchesRecipeArray(plateless, customer.request)) {
            System.out.println("This was correct for me");
            customer.dishStack.clearStack();
            customer.dishStack.setStack(stationDishStack.getStackCopy());
            stationDishStack.clearStack();
            if (testFlag == 0) {
                customer.Success(this);
            }
        }
    }

    /** Method to render a dishStack onto the ServingStation.
     * @param batch the SpriteBatch to be rendered.
     */
    @Override
    public void render(SpriteBatch batch) {

        Array<FoodItem.FoodID> dishList = stationDishStack.getStack();
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

    /** Getter for the TestFlag.*/
    public int getTestFlag(){ return testFlag; }

    /** Setter for the TestFlag.*/
    public void setTestFlag(int flag){
        testFlag = flag; // 0 if not testing, 1 if testing
    }
}
