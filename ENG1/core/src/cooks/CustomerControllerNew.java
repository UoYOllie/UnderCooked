package cooks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import cooks.Cook;
import food.Recipe;
import game.GameScreen;
import game.GameSprites;
import helper.Constants;
import helper.MapHelper;
import stations.ServingStation;
import stations.ServingStationNew;
import stations.Station;

import java.util.ArrayList;
import java.util.Random;

public class CustomerControllerNew {

    //private MapHelper mapHelper;
    private ArrayList<Station> servingStations;
    private Array<CustomerNew> customers;
    private GameScreen gameScreen;

    public CustomerControllerNew(GameScreen gameScreen) {
        //this.mapHelper = mapHelper;
        this.gameScreen = gameScreen;
        this.servingStations = gameScreen.mapHelper.getServingStationNewList();
        this.customers = spawnCustomers();
    }

    private Array<CustomerNew> spawnCustomers() {

        Array<CustomerNew> allCustomers = new Array<>();

        for (Station station : servingStations) {

            if (allCustomers.size < 5) {
                CustomerNew customer = new CustomerNew(station.getX(), station.getY(), 3.34f, 1);
                allCustomers.add(customer);
            }
        }

        return allCustomers;
    }

    public Array<CustomerNew> getCustomers() {
        return this.customers;
    }

}
