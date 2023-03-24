package helper;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import cooks.Cook;
import game.GameScreen;
import stations.CookInteractable;
import java.awt.geom.Rectangle2D;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import cooks.Cook;
import game.GameScreen;
import stations.CookInteractable;

import java.awt.*;

import static helper.Constants.CookHeight;
import static helper.Constants.CookWidth;

public class NewCollisionHelper {
    /** The GameScreen to do collision-helping on. */
    protected GameScreen gameScreen;
    private Rectangle CookRectangle;

    public NewCollisionHelper(GameScreen gameScreen, Cook cook){
        setGameScreen(gameScreen);
        CookRectangle = new Rectangle(cook.getX(),cook.getY(),CookWidth,CookHeight);
    }

    public void setGameScreen(GameScreen gameScreen) {
        System.out.println("Initalising Gamescreen");
        this.gameScreen = gameScreen;
        System.out.println(this.gameScreen);
    }

    //Array<CookInteractable>
    public CookInteractable NearbyStation() {
        Array<CookInteractable> found = new Array<>();
        for (CookInteractable object : gameScreen.getInteractables()) {
            if (CookRectangle.overlaps(object.getRectangle())) {
                System.out.println("Cook Overlaps: " + object);
                found.add(object);
            }
        }

        //
        System.out.println(found);
        CookInteractable closest = null;
        if(found.size>1) {
            float closestDist = distRectToInteractable(this.CookRectangle, found.get(0));
            closest = found.get(0);
            for (int i = 1; i < found.size; i++) {
                float dist = distRectToInteractable(this.CookRectangle, found.get(i));
                if (dist < closestDist) {

                    closestDist = dist;
                    closest = found.get(i);
                }
            }
        }
        else if(found.size==1){
            closest = found.get(0);
        }
        return closest;
    }

    private float distRectToInteractable(Rectangle rect, CookInteractable station) {
        return Util.distancePoints(rect.x-rect.getWidth()/2,
                rect.y-rect.getHeight()/2,
                station.getRectangle().x,
                station.getRectangle().y);
    }


}
