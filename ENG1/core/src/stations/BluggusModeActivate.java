package stations;

import shop.ShopItem;
import com.badlogic.gdx.math.Rectangle;
import players.Cook;
import game.GameScreen;
import interactions.InputKey;

import java.util.ArrayList;

/**
 * Chef Bluggus Mode Powerup
 * Allows the cook using it to hold 2 foodstacks (total of 6 items at a time,
 * but still just one dish)
 */
public class BluggusModeActivate extends Station {
    private GameScreen gameScreen;
    private ShopItem item;
    private int placementofentity;
    private Cook NewBluggusSpawn;
    private ArrayList<Float> coordsofbluggy;

    /**
     * Constructor
     * Sets the interaction rectangle to allow the powerup
     * Creates a sprite to sit within the cage.
     * Enables the powerup
     * @param rectangle
     * @param g
     */
    public BluggusModeActivate(Rectangle rectangle, GameScreen g) {
        super(rectangle);
        this.isABluggusPrison = true;
        this.gameScreen = g;
        this.item = gameScreen.Powerup_ChefBluggusMode;
        Enable();
        coordsofbluggy = new ArrayList<Float>();
        coordsofbluggy.add(rectangle.x+7.5f);
        coordsofbluggy.add(rectangle.y+2.6f);
        NewBluggusSpawn = new Cook(coordsofbluggy.get(0) * 8f, coordsofbluggy.get(1) * 8f, 3.34f, 1);
        NewBluggusSpawn.activateBluggus = true;
        placementofentity = g.gameEntities.size();
        g.gameEntities.add(NewBluggusSpawn);
    }

    /**
     * The interaction method, where if the user has enough gold,
     * it will change the users sprite to be a Cook version of bluggus
     * @param cook The cook currently interacting with the AssemblyStation.
     * @param inputType The input received from the cook currently interacting.
     */
    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        if((getEnabled())&&(cook.activateBluggus==false)) {
            if (gameScreen.gold.getBalance() - item.cost >= 0) {
                gameScreen.gold = item.buy(gameScreen.gold);
                cook.MakeIntoBluggus();
                Disable();
            }
        }
    }

    /**
     * Disables the station so he cannot be resued, then
     * removes the entity from inside the cage
     */
    @Override
    public void Disable()
    {
        SetEnFalse();
        gameScreen.gameEntities.remove(NewBluggusSpawn);
    }
}

