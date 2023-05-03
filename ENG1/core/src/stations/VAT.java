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

    /**
     * Constructor
     * Defines what chef will be added to the chamber
     * Enables this station for use.
     * @param rectangle
     * @param Person
     * @param g
     */
    public VAT(Rectangle rectangle,String Person, GameScreen g)
    {
        super(rectangle);
        Enable();
        this.gameScreen = g;
        this.PrisonerNumber = Integer.parseInt(Person);
        this.item = gameScreen.BuyablePeople;
    }

    /**
     * Allows the current cook to add another chef to the current avaliable cooks if
     * they have enough gold to purchase it
     * @param cook The cook currently interacting with the AssemblyStation.
     * @param inputType The input received from the cook currently interacting.
     */
    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        if((this.getEnabled())&&(gameScreen.gold.getBalance()-item.cost>=0) ){
            gameScreen.gold = item.buy(gameScreen.gold);
            gameScreen.SpareToNotSpare(PrisonerNumber);
            SetEnFalse();
        }

    }

    /**
     * Disables this station from being used again and removes the entity inside the chamber
     */
    @Override
    public void Disable()
    {
        SetEnFalse();
        Cook newcook = gameScreen.unusedcooks.get(this.PrisonerNumber-1);
        gameScreen.gameEntities.remove(newcook);
    }


}
