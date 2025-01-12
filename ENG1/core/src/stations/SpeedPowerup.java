package stations;

import shop.ShopItem;
import com.badlogic.gdx.math.Rectangle;
import players.Cook;
import game.GameScreen;
import interactions.InputKey;

/**
 * Speed powerup class
 */
public class SpeedPowerup extends Station {
    private  GameScreen gameScreen;
    private ShopItem item;

    /**
     * Constructor using gamescreen
     * @param rectangle
     * @param g
     */
    public SpeedPowerup(Rectangle rectangle, GameScreen g){

        super(rectangle);
        this.gameScreen = g;
        this.item = gameScreen.Powerup_Speed;
    }

    /**
     * Constructor not using gamescreen
     * @param rectangle
     */
    public SpeedPowerup(Rectangle rectangle){
        super(rectangle);
        this.item = new ShopItem("Speed",30);
    }

    /**
     * Sets speed
     * @param cook
     * @return returns the cook with new speed
     */
    public Cook setSpeed(Cook cook){
        cook.movement_speed = cook.movement_speed + 0.21f;
        return cook;
    }

    /**
     * Takes the current cook and increases its speed and increases it by and increment
     * of 0.21
     * Only allows this to happen 3 times
     * @param cook The cook currently interacting with the AssemblyStation.
     * @param inputType The input received from the cook currently interacting.
     */
    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        if(cook.movement_speed<0.6765f+(0.21f*3)) {
            if (gameScreen.gold.getBalance() - item.cost >= 0) {
                gameScreen.gold = item.buy(gameScreen.gold);
                gameScreen.cook = setSpeed(cook);
            }
        }

    }
}
