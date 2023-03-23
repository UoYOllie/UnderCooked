package cooks;

import food.FoodStack;
import game.GameScreen;

public class Bluggus extends Cook{
    public FoodStack foodStack2;
    public Bluggus(float x, float y, float width, float height, GameScreen g) {
        super(x, y, width, height,g);
        this.foodStack2 = new FoodStack();
        System.out.println("Bluggus is being born today");
    }
}
