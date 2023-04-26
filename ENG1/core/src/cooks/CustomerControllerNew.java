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
//import stations.ServingStation;
import stations.ServingStationNew;
import stations.Station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CustomerControllerNew {

    //private MapHelper mapHelper;
    private ArrayList<Station> servingStations;
    private ArrayList<CustomerNew> customers;
    private GameScreen gameScreen;
    private Map<Station, CustomerNew> stationCustomerMap;

    public CustomerControllerNew(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.servingStations = gameScreen.mapHelper.getServingStationNewList();
        this.stationCustomerMap = new HashMap<Station, CustomerNew>();
        initialiseStationCustomerMap();
        //this.customers = spawnCustomers();
       // this.customers = initialiseCustomers();
        this.customers = new ArrayList<>();
    }



    public ArrayList<CustomerNew> initialiseCustomers() {

        ArrayList<CustomerNew> initialCustomers = new ArrayList<>();


        for (Station station : servingStations) {

            System.out.println("serving station at: " + station.getX() + "," + station.getY());

            CustomerNew customer = new CustomerNew(Constants.customerPointA.x, Constants.customerPointA.y, 3.34f, 2f);
            customer.setStationPosition(station.getX(), station.getY());
            customer.setGameScreen(this.gameScreen);

            initialCustomers.add(customer);
            stationCustomerMap.put(station, customer);

        }

        return initialCustomers;
    }

    public ArrayList<CustomerNew> updateCustomers() {

        ArrayList<CustomerNew> addedCustomers = new ArrayList<>();

        for (Map.Entry<Station, CustomerNew> entry : stationCustomerMap.entrySet()) {

            Station station = entry.getKey();
            CustomerNew customer = entry.getValue();

            if (customer == null) {
                System.out.println("new customer spawned");
                CustomerNew newCustomer = new CustomerNew(Constants.customerPointA.x, Constants.customerPointA.y, 3.34f, 3f);
                newCustomer.setStationPosition(station.getX(), station.getY());
                newCustomer.setGameScreen(this.gameScreen);

                System.out.println("stationCustomerMap original: " + this.stationCustomerMap);

                this.stationCustomerMap.put(station, newCustomer);
                this.customers.add(newCustomer);
                addedCustomers.add(newCustomer);
            }

        }

        return addedCustomers;

    }

    public void removeCustomer(Station station) {
        this.stationCustomerMap.put(station, null);
    }

    public void initialiseStationCustomerMap() {

        for (Station station : servingStations) {
            this.stationCustomerMap.put(station, null);
        }
    }

    public CustomerNew addCustomer() {

        for (Map.Entry<Station, CustomerNew> entry : stationCustomerMap.entrySet()) {

            Station station = entry.getKey();
            CustomerNew customer = entry.getValue();

            if (customer == null) {
                System.out.println("new customer spawned");
                CustomerNew newCustomer = new CustomerNew(Constants.customerPointA.x, Constants.customerPointA.y, 3.34f, 3f);
                newCustomer.setStationPosition(station.getX(), station.getY());
                newCustomer.setGameScreen(this.gameScreen);

                System.out.println("stationCustomerMap original: " + this.stationCustomerMap);

                this.stationCustomerMap.put(station, newCustomer);
                this.customers.add(newCustomer);

                return newCustomer;
            }
        }

        return null;

    }

    public Map<Station, CustomerNew> getStationCustomerMap() {
        return this.stationCustomerMap;
    }

    private Array<CustomerNew> spawnCustomers() {

        Array<CustomerNew> allCustomers = new Array<>();

        for (Station station : servingStations) {

            if (allCustomers.size < 5) {
                CustomerNew customer = new CustomerNew(Constants.customerPointA.x, Constants.customerPointA.y, 3.34f, 2);
                customer.setStationPosition(station.getX()-1.3f, station.getY()+4f);
                customer.setGameScreen(this.gameScreen);
                allCustomers.add(customer);
            }

        }

        return allCustomers;
    }

    public ArrayList<CustomerNew> getCustomers() {
        return this.customers;
    }

}
