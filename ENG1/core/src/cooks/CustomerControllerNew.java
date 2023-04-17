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
        this.customers = makeCustomers();
    }

    public ArrayList<Station> getServingStations(MapHelper mapHelper) {
        return mapHelper.getServingStationNewList();
    }

    private Array<CustomerNew> makeCustomers() {
        Array<CustomerNew> allCustomers = new Array<>();
        System.out.println("these are the serving stations");
        for (Station station : servingStations) {
            CustomerNew customer = new CustomerNew(station.getX(), station.getY(), 3.34f, 1);
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    private Array<CustomerNew> makeCustomersTest() {
        Array<CustomerNew> allCustomers = new Array<>();
        //CustomerNew customer = new CustomerNew(1995.975f,2855.9087f,3.34f, 1);

        CustomerNew customerTest1 = new CustomerNew(1941.5f, 2800.0505f, 3.34f, 1);
        allCustomers.add(customerTest1);
        return allCustomers;
    }

    public Array<CustomerNew> getCustomers() {
        return this.customers;
    }

}
