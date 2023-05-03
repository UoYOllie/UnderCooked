package stations;

import shop.ShopItem;
import com.badlogic.gdx.math.Rectangle;
import players.Cook;
import game.GameScreen;
import interactions.InputKey;

public class FreezeTimeStation extends Station{
    private GameScreen gameScreen;
    private ShopItem item;
    public FreezeTimeStation(Rectangle rectangle, GameScreen g) {
        super(rectangle);
        this.gameScreen = g;
        this.item = gameScreen.timefreeze;
    }

    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        if(gameScreen.gold.getBalance()-item.cost>=0) {
            gameScreen.gold = item.buy(gameScreen.gold);
            gameScreen.FreezeTime();
        }
    }

}
