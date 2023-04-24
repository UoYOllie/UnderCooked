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

public class BluggusModeActivate extends Station {
    private GameScreen gameScreen;
    private ShopItem item;

    public BluggusModeActivate(Rectangle rectangle, GameScreen g) {
        super(rectangle);
        this.gameScreen = g;
        this.item = gameScreen.Powerup_ChefBluggusMode;
    }

    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        System.out.println(gameScreen.gold.getBalance());
        if (gameScreen.gold.getBalance() - item.cost >= 0) {
            System.out.println(gameScreen.gold.getBalance());
            gameScreen.gold = item.buy(gameScreen.gold);
            cook.MakeIntoBluggus();
            System.out.println(gameScreen.gold.getBalance());
        }
    }
}

