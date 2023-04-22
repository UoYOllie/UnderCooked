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
        this.gameScreen = gameScreen;
        this.servingStations = gameScreen.mapHelper.getServingStationNewList();
        this.customers = spawnCustomers();
    }

//    private Array<CustomerNew> spawnCustomers() {
//
//        Array<CustomerNew> allCustomers = new Array<>();
//
//        for (Station station : servingStations) {
//
//            if (allCustomers.size < 5) {
//                CustomerNew customer = new CustomerNew(station.getX()-1.3f, station.getY()+4f, 3.34f, 1);
//                customer.setGameScreen(this.gameScreen);
//                allCustomers.add(customer);
//            }
//        }
//
//        return allCustomers;
//    }

    private Array<CustomerNew> spawnCustomers() {

        Array<CustomerNew> allCustomers = new Array<>();

        for (Station station : servingStations) {

            if (allCustomers.size < 5) {
                System.out.println("serving station at " + station.getX() + "," + station.getY());
                //CustomerNew customer = new CustomerNew(1912f-1.3f, 2813.9171f+4f, 3.34f, 2);
                CustomerNew customer = new CustomerNew(Constants.customerSpawnPoint.x, Constants.customerSpawnPoint.y, 3.34f, 2);
                customer.setStationPosition(station.getX()-1.3f, station.getY()+4f);
                //customer.setDestination(customer.x, Constants.customerSplitPoint);
                customer.setGameScreen(this.gameScreen);
                allCustomers.add(customer);
            }

        }

        return allCustomers;
    }

    public Array<CustomerNew> getCustomers() {
        return this.customers;
    }

}
