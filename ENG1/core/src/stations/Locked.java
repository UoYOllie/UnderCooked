package stations;

import Shop.ShopItem;
import com.badlogic.gdx.math.Rectangle;
import com.sun.org.apache.xpath.internal.operations.Bool;
import game.GameScreen;
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

public class Locked extends Station{
    private GameScreen gameScreen;
    private String type;
    private Rectangle rectangle;
    private ShopItem item;

    public Locked(Rectangle rectangle, GameScreen g, String type) {
        super(rectangle);
        this.gameScreen = g;
        this.type = type;
        this.rectangle = rectangle;
        this.Enabled = true;
        this.item = gameScreen.BuyableStation;
        this.Locked = true;
    }

    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        System.out.println(gameScreen.gold.getBalance());
        if((this.Enabled)&&(gameScreen.gold.getBalance()-item.cost>=0) ){
            System.out.println(type);
            String t = this.type;
            gameScreen.gold = item.buy(gameScreen.gold);
            PreparationStation prepStation = new PreparationStation(rectangle);
            switch(t) {
                case "cut":
                    prepStation.setID(Station.StationID.cut);
                    break;
                case "fry":
                    prepStation.setID(Station.StationID.fry);
                    break;
                case "bake":
                    prepStation.setID(Station.StationID.bake);
                    break;
            }
            gameScreen.mapHelper.mapStations.add(prepStation);
            this.Enabled = false;
            System.out.println(gameScreen.gold.getBalance());


        }
    }
}
