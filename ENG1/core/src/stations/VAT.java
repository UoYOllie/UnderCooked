package stations;

import java.awt.*;

import Shop.Gold;
import Shop.ShopItem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import cooks.Cook;
import game.GameScreen;
import game.GameSprites;
import interactions.InputKey;
import interactions.Interactions;

public class VAT extends Station{
    private GameScreen gameScreen;
    private int PrisonerNumber;
    private ShopItem item;
    public VAT(Rectangle rectangle,String Person, GameScreen g)
    {
        super(rectangle);
        this.Enabled = true;
        this.gameScreen = g;
        this.PrisonerNumber = Integer.parseInt(Person);
        this.item = gameScreen.BuyablePeople;
    }
    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        if((this.Enabled)&&(gameScreen.gold.getBalance()-item.cost>=0) ){
            System.out.println(gameScreen.gold.getBalance());
            gameScreen.gold = item.buy(gameScreen.gold);
            gameScreen.SpareToNotSpare(PrisonerNumber);
            this.Enabled = false;
            System.out.println(gameScreen.gold.getBalance());

        }

    }


}