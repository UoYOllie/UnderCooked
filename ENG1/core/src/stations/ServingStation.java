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


public class ServingStation extends Station {


    public Customer customer;
    private Integer testFlag = 0;

    public ServingStation(Rectangle rectangle) {
        super(rectangle);
        stationDishStack = new DishStack();
        //customer = null;
    }

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

    @Override
    public void customerInteract(Customer customer) {
        this.customer = customer;
        Array<FoodItem.FoodID> plateless = stationDishStack.getStackCopy();
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

    public int getTestFlag(){
        return testFlag;
    }

    public void setTestFlag(int flag){
        testFlag = flag; // 0 if not testing, 1 if testing
    }

    public DishStack getServedDishStack(){
        return stationDishStack;
    }


}
