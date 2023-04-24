package cooks;

import food.FoodStack;
import game.GameScreen;

// from laura - i changed this to BluggusChef because customers are also bluggus :)
public class BluggusChef extends Cook{
    public FoodStack foodStack2;
    public BluggusChef(float x, float y, float width, float height, GameScreen g) {
        super(x, y, width, height);
        this.foodStack2 = new FoodStack();
        System.out.println("Bluggus Chef is being born today");
    }

//    public void moveStacks()
//    {
//        if((this.foodStack.size() == 3)&&(this.foodStack2.size() == 0)){
//            this.foodStack2.setStack(this.foodStack.getStackCopy());
//            this.foodStack.clearStack();
//        }
//    }

}
