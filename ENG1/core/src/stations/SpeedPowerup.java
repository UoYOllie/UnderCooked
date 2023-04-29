package stations;

import Shop.ShopItem;
import com.badlogic.gdx.physics.bullet.collision._btMprSimplex_t;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import cooks.Cook;
import game.GameScreen;
import game.GameSprites;
import interactions.InputKey;


public class SpeedPowerup extends Station {
    private  GameScreen gameScreen;
    private ShopItem item;
    private int counter;
    public SpeedPowerup(Rectangle rectangle, GameScreen g){

        super(rectangle);
        this.gameScreen = g;
        this.item = gameScreen.Powerup_Speed;
        this.counter =0;
    }

    public SpeedPowerup(Rectangle rectangle){
        super(rectangle);
        this.item = new ShopItem("Speed",30);
        this.counter =0;
    }

    public Cook setSpeed(Cook cook){
        cook.movement_speed = cook.movement_speed + 0.42f;
        return cook;
    }
    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        System.out.println(gameScreen.gold.getBalance());
        if(cook.movement_speed<0.6765f+(0.42f*3)) {
            if (gameScreen.gold.getBalance() - item.cost >= 0) {
                System.out.println(gameScreen.gold.getBalance());
                gameScreen.gold = item.buy(gameScreen.gold);
                gameScreen.cook = setSpeed(cook);
                System.out.println(gameScreen.gold.getBalance());
                counter++;
            }
        }

    }
}
