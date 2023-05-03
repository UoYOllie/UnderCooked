package stations;

import shop.ShopItem;
import com.badlogic.gdx.math.Rectangle;
import players.Cook;
import game.GameScreen;
import interactions.InputKey;

/**
 * Freeze timer power up
 */
public class FreezeTimeStation extends Station{
    private GameScreen gameScreen;
    private ShopItem item;

    /**
     * Constructor for the station to allow time freeze
     * @param rectangle
     * @param g
     */
    public FreezeTimeStation(Rectangle rectangle, GameScreen g) {
        super(rectangle);
        this.gameScreen = g;
        this.item = gameScreen.timefreeze;
    }

    /**
     * Interaction cause the timer to freeze, whilst allows the rest of the
     * mechanics to still work. If the user has enough gold that is.
     * @param cook The cook currently interacting with the AssemblyStation.
     * @param inputType The input received from the cook currently interacting.
     */
    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        if(gameScreen.gold.getBalance()-item.cost>=0) {
            gameScreen.gold = item.buy(gameScreen.gold);
            gameScreen.FreezeTime();
        }
    }

}
