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

import java.util.Random;

public class CustomerControllerNew {

    //private MapHelper mapHelper;
    private Array<Station> servingStations;
    private Array<CustomerNew> customers;


    public CustomerControllerNew(MapHelper mapHelper) {
        //this.mapHelper = mapHelper;
        this.servingStations = mapHelper.getServingStationNewList();
        this.customers = makeCustomers();
    }

    public Array<Station> getServingStations(MapHelper mapHelper) {
        return mapHelper.getServingStationNewList();
    }

    private Array<CustomerNew> makeCustomers() {
        Array<CustomerNew> allCustomers = new Array<>();
        for (Station station : servingStations) {
            CustomerNew customer = new CustomerNew(station.getX(), station.getY(), 3.34f, 1);
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    public Array<CustomerNew> getCustomers() {
        return this.customers;
    }

}
