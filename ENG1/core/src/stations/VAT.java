package stations;

import shop.ShopItem;
import com.badlogic.gdx.math.Rectangle;
import players.Cook;
import game.GameScreen;
import interactions.InputKey;

public class VAT extends Station{
    private GameScreen gameScreen;
    private int PrisonerNumber;
    private ShopItem item;
    public VAT(Rectangle rectangle,String Person, GameScreen g)
    {
        super(rectangle);
        Enable();
        this.gameScreen = g;
        this.PrisonerNumber = Integer.parseInt(Person);
        this.item = gameScreen.BuyablePeople;
    }
    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        if((this.getEnabled())&&(gameScreen.gold.getBalance()-item.cost>=0) ){
            gameScreen.gold = item.buy(gameScreen.gold);
            gameScreen.SpareToNotSpare(PrisonerNumber);
            SetEnFalse();

        }

    }

    @Override
    public void Disable()
    {
        SetEnFalse();
        Cook newcook = gameScreen.unusedcooks.get(this.PrisonerNumber-1);
        gameScreen.gameEntities.remove(newcook);
    }


}
