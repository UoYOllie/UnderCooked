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
    private ArrayList<Station> servingStations;
    private ArrayList<CustomerNew> customers;
    private GameScreen gameScreen;
    private Map<Station, CustomerNew> stationCustomerMap;
    private String mode;
    private int difficulty;


    public CustomerControllerNew(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.servingStations = gameScreen.mapHelper.getServingStationNewList();
        this.stationCustomerMap = new HashMap<Station, CustomerNew>();
        initialiseStationCustomerMap();
        //this.customers = spawnCustomers();
       // this.customers = initialiseCustomers();
        this.customers = new ArrayList<>();
        //this.scenarioMode = false;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    private boolean noMoreCustomers() {
        return (this.mode == "scenario" && this.customers.size() > 4);
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

        if (noMoreCustomers()) {return null;}

        for (Map.Entry<Station, CustomerNew> entry : stationCustomerMap.entrySet()) {

            Station station = entry.getKey();
            CustomerNew customer = entry.getValue();

            if (customer == null) {

                //System.out.println("new customer spawned, destination " + station);
                CustomerNew newCustomer = new CustomerNew(Constants.customerPointA.x, Constants.customerPointA.y, 3.34f, 3f);
                newCustomer.setStationPosition(station.getX(), station.getY());
                newCustomer.setStation(station);
                newCustomer.setDifficulty(difficulty);
                newCustomer.setGameScreen(this.gameScreen);

                if (customers.size() == 2) {
                    newCustomer.customerToTest = true;
                    System.out.println("testing " + newCustomer + " with destination " + station);
                }

                this.stationCustomerMap.put(station, newCustomer);
                this.customers.add(newCustomer);

                return newCustomer;
            }
        }

        return null;

    }

    public ArrayList<CustomerNew> getCustomers() {
        return this.customers;
    }

}
