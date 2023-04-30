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

import java.util.ArrayList;

public class BluggusModeActivate extends Station {
    private GameScreen gameScreen;
    private ShopItem item;
    private int placementofentity;
    private Cook NewBluggusSpawn;
    private ArrayList<Float> coordsofbluggy;

    public BluggusModeActivate(Rectangle rectangle, GameScreen g) {
        super(rectangle);
        this.gameScreen = g;
        this.item = gameScreen.Powerup_ChefBluggusMode;
        this.Enabled = true;
        coordsofbluggy = new ArrayList<Float>();
        coordsofbluggy.add(rectangle.x+7.5f);
        coordsofbluggy.add(rectangle.y+2.6f);
        NewBluggusSpawn = new Cook(coordsofbluggy.get(0) * 8f, coordsofbluggy.get(1) * 8f, 3.34f, 1);
        placementofentity = g.gameEntities.size();
        System.out.print(placementofentity + "<|||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        g.gameEntities.add(NewBluggusSpawn);
    }

    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        System.out.print(this.Enabled + "Is what is is <,,e,ge,geuhgeieg");
        if((Enabled)&&(cook.activateBluggus==false)) {
            System.out.println(gameScreen.gold.getBalance());
            if (gameScreen.gold.getBalance() - item.cost >= 0) {
                System.out.println(gameScreen.gold.getBalance());
                gameScreen.gold = item.buy(gameScreen.gold);
                cook.MakeIntoBluggus();
                gameScreen.gameEntities.remove(NewBluggusSpawn);
                System.out.println(gameScreen.gold.getBalance());
//                System.out.println(gameScreen.gameEntities.+"PWOOOOOOOSH");
            }
            this.Enabled = false;
            System.out.print(this.Enabled + "Is what is is <,,e,ge,geuhgeieg");
        }
    }
}

