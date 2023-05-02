package helper;

import com.badlogic.gdx.math.Intersector;
import cooks.Customer;
import game.GameScreen;
import stations.CookInteractable;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import stations.Station;

import java.util.ArrayList;

import static helper.Constants.CookHeight;
import static helper.Constants.CookWidth;

public class CustomerCollisionHelper {
    /** The GameScreen to do collision-helping on. */
    protected GameScreen gameScreen;
    private Rectangle CustomerRectangle;

    private ArrayList<Station> mapStations;

    public CustomerCollisionHelper(GameScreen gameScreen, Customer customer, ArrayList<Station> mapStations){
        setGameScreen(gameScreen);
        CustomerRectangle = new Rectangle(customer.getX(),customer.getY(),CookWidth,CookHeight);
        this.mapStations = mapStations;
    }

    public void setGameScreen(GameScreen gameScreen) {
        //System.out.println("Initalising Gamescreen");
        this.gameScreen = gameScreen;
        //System.out.println(this.gameScreen);
    }

    //Array<CookInteractable>
    public CookInteractable NearbyStation(Rectangle customerInteractor) {
        Array<CookInteractable> found = new Array<>();
        for(Station object : mapStations){
            if (Intersector.overlaps(object.getRectangle(), customerInteractor)){
                //System.out.println("Customer Overlaps: " + object);
                if(object.getEnabled()) {
                    found.add(object);
                }
            }
        }

        //
        //System.out.println(found);
        CookInteractable closest = null;
        if(found.size>1) {
            float closestDist = distRectToInteractable(this.CustomerRectangle, found.get(0));
            closest = found.get(0);
            for (int i = 1; i < found.size; i++) {
                float dist = distRectToInteractable(this.CustomerRectangle, found.get(i));
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
