package helper;

import com.badlogic.gdx.math.Intersector;
import players.Cook;
import game.GameScreen;
import stations.CookInteractable;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import stations.Station;

import java.util.ArrayList;

import static helper.Constants.CookHeight;
import static helper.Constants.CookWidth;

public class CollisionHelper {
    /** The GameScreen to do collision-helping on. */
    protected GameScreen gameScreen;
    private Rectangle CookRectangle;

    private ArrayList<Station> mapStations;

    /**
     * Constructor to create game enviroment ready to detect collisions
     * @param gameScreen
     * @param cook
     * @param mapStations
     */
    public CollisionHelper(GameScreen gameScreen, Cook cook, ArrayList<Station> mapStations){
        setGameScreen(gameScreen);
        CookRectangle = new Rectangle(cook.getX(),cook.getY(),CookWidth,CookHeight);
        this.mapStations = mapStations;
    }

    /**
     * Sets the gameScreen
     * @param gameScreen
     */
    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    /**
     * Find any station that is overlapping
     * If multiple, it finds the one closest to the users rectangle
     * @param cookInteractor
     * @return closest station
     */
    public CookInteractable NearbyStation(Rectangle cookInteractor) {
        Array<CookInteractable> found = new Array<>();
        for(Station object : mapStations){
            if (Intersector.overlaps(object.getRectangle(), cookInteractor)){
                if(object.getEnabled()) {
                    found.add(object);
                }
            }
        }

        //
        //tln(found);
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

    /**
     * Function to find the distance between 2 rectangles:
     * @param rect
     * @param station
     * @return float value of distance
     */
    private float distRectToInteractable(Rectangle rect, CookInteractable station) {
        return Util.distancePoints(rect.x-rect.getWidth()/2,
                rect.y-rect.getHeight()/2,
                station.getRectangle().x,
                station.getRectangle().y);
    }
}
