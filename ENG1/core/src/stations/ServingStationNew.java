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
        //customer = null;
    }

    //public boolean hasCustomer() {
        //return customer == null;
   // }

    //public CustomerNew getCustomer() {
        //return customer;
    //}

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
//            case USE:
//                System.out.println("i am using the serving station");
//                System.out.println(customer);
//                System.out.println("^^^^^^^");
//                if (!servedDishStack.empty() && this.customer!=null) {
//                    System.out.println("i will take the dish!");
//                    this.customer.dishStack.setStack(servedDishStack.getStackCopy());
//                    servedDishStack.clearStack();
//                    System.out.println("the customer's dishstack:");
//                    System.out.println(customer.dishStack.getStack());
//                    System.out.println("the station's dishstack:");
//                    System.out.println(servedDishStack.getStack());
//                }
        }
    }

    @Override
    public void customerInteract(CustomerNew customer) {
        System.out.println("i am inside laura's method");
        this.customer = customer;
        System.out.println(customer);
        if (!servedDishStack.empty() && customer.dishStack.empty()) {
            System.out.println("i will take the dish!");
            customer.dishStack.setStack(servedDishStack.getStackCopy());
            servedDishStack.clearStack();
            System.out.println("the customer's dishstack:");
            System.out.println(customer.dishStack.getStack());
            System.out.println("the station's dishstack:");
            System.out.println(servedDishStack.getStack());
        } else {
            System.out.println("i will not take the dish!");
        }
    }

//    @Override
//    public void customerInteract(CustomerNew customer, InputKey.InputTypes inputType) {
//
//        switch (inputType) {
//            case USE:
//                System.out.println("customer is interacting with the serving station");
//                if (!servedDishStack.empty() && customer.dishStack.empty()) {
//                    System.out.println("i will take the dish!");
//                    customer.dishStack.setStack(servedDishStack.getStackCopy());
//                    this.servedDishStack.clearStack();
//                }
//                break;
//        }
//    }

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
