package cooks;

import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.bcel.internal.Const;
import game.GameScreen;
import helper.Constants;
import stations.Station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Used by GameScreen to manage all customers in the game.
 * Continually generates customers to empty serving stations (either endlessly or up to
 * a limit, depending on scenario or endless mode).
 */
public class CustomerController {

    /** The current instance of GameScreen.*/
    private GameScreen gameScreen;

    /** The ArrayList of all customers currently in the game.*/
    public ArrayList<CustomerNew> customers;

    /** The ArrayList of all s in the game, used for generating customers.*/
    private ArrayList<Station> servingStations;

    /** The Map linking each station to exactly one customer who has not been served.*/
    private Map<Station, CustomerNew> stationCustomerMap;

    /** The mode of the game being played, either "scenario" or "endless".*/
    private String mode;

    /** The difficulty of the game being played, either 1 (easy), 2 (medium), or 3 (hard).*/
    private int difficulty;

    private int interval;
    private int group_size;
    private int customers_left;

    public int TotalCustomersServed;

    /** The constructor of CustomerController.
     * @param gameScreen the instance of GameScreen for the current game.
     * */
    public CustomerController(GameScreen gameScreen) {

        this.gameScreen = gameScreen;
        this.customers = new ArrayList<>();

        this.servingStations = gameScreen.mapHelper.getServingStationList();
        this.stationCustomerMap = new HashMap<Station, CustomerNew>();
        initialiseStationCustomerMap();

        this.interval = 1;
        this.group_size = 1;
        this.customers_left = 4;
        this.TotalCustomersServed = 0;
    }

    /** Setter for mode.*/
    public void setMode(String mode) {
        this.mode = mode;
    }

    /** Setter for difficulty.*/
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /** Getter for customers.*/
    public ArrayList<CustomerNew> getCustomers() {
        return this.customers;
    }

    /**
     * Helper method to check whether all customers have been served in Scenario Mode.
     * @return true if all customers in the scenario have been served.
     * */
    private boolean maxCustomersReached() {
        return (this.mode == "scenario" && this.customers.size() > 4);
    }

    public boolean wonScenario() {


        //if (customers.size() == 0) { return false;}

        if (this.mode == "scenario" && this.customers_left > 0) {

            for (CustomerNew customer : customers) {
                if (customer.getCustomerStatus() == 2) {
                    this.customers_left -= 1;
                    customer.setStatus(customer.getStatus() + 1);
                    System.out.println("customers left " + customers_left);
                }
            }
        } else if (this.mode == "scenario" || this.customers_left == 0) {
            return true;
        }

        return false;
    }

    /**
     * Method to remove a customer from stationCustomerMap, after they have been saved.
     * @param station the  to remove the customer from.
     * */
    public void removeCustomer(Station station) {
        this.stationCustomerMap.put(station, null);
    }

    /** Method to initialise StationCustomerMap to map each  to a null customer. */
    public void initialiseStationCustomerMap() {

        for (Station station : servingStations) {
            this.stationCustomerMap.put(station, null);
        }

    }

    /**
     * Method used by GameScreen to add a customer to stationCustomerMap, only if the customer
     * corresponding to the ServingStation is null.
     * @return the Customer that has been added, or null if all servingStations are busy.
     * */
    public CustomerNew addCustomer() {

        //System.out.println("group size " + group_size);

        interval -= 1;
        System.out.println("interval " + interval);
        System.out.println("group size " + group_size);

        // Check if all customers have been served, if yes return.
        // Also checks if the current interval is a multiple of 10, new customers
        // are spawned every 10 seconds.
        if ((this.mode == "scenario" && this.customers.size() > 4) || !(this.interval==0)) {
            System.out.println("no customer added this time!");
            return null;
        }

        // Iterate through each servingStation key in stationCustomerMap.
        for (Map.Entry<Station, CustomerNew> entry : stationCustomerMap.entrySet()) {

            Station station = entry.getKey();
            CustomerNew customer = entry.getValue();

            // If there is no customer at the station, create a new one.
            if (customer == null) {

                CustomerNew newCustomer = new CustomerNew(Constants.customerPointA.x, Constants.customerPointA.y, 3.34f, 3f);

                // Set station position, difficulty, and gameScreen attributes for the Customer.
                newCustomer.setStationPosition(station.getX(), station.getY());
                newCustomer.setDifficulty(difficulty);
                newCustomer.setGameScreen(this.gameScreen);

                // Add the new customer to customers and stationCustomerMap, then return the new Customer.
                this.stationCustomerMap.put(station, newCustomer);
                this.customers.add(newCustomer);

                if (this.mode == "endless" && this.customers.size() > 5) {
                    if (group_size > 1) {
                        group_size -= 1;
                        interval = 1;
                        //System.out.println("group_size " + group_size);
                    } else {
                        Random rd = new Random();
                        this.group_size = rd.nextInt(1, 4);
                        this.interval = 10;
                        //System.out.println("group size " + group_size);
                    }
                } else {
                    //System.out.println("group size " + group_size);
                    this.interval = 10;
                }

                System.out.println("added customer " + newCustomer);
                return newCustomer;
            }
        }

        System.out.println("no customer added!");
        interval = 10;
        return null;
    }

    private Station getStationKey(Map<Station, CustomerNew> stationCustomerMap, float station_y) {

        for (Map.Entry<Station, CustomerNew> entry : stationCustomerMap.entrySet()) {
            Station station = entry.getKey();
            if(station!=null) {
                if (Math.round(station.getY()) == Math.round(station_y)) {
                    return station;
                }
            }
        }

        return null;
    }

    public CustomerNew addSavedCustomer(float x,float y, float station_x, float station_y) {

        if (this.stationCustomerMap.isEmpty()) {
            initialiseStationCustomerMap();
        }

        CustomerNew newCustomer = new CustomerNew(x*Constants.UnitScale, y*Constants.UnitScale, 3.34f, 3f);

        newCustomer.setStationPosition(station_x, station_y);
        newCustomer.setDifficulty(1);
        newCustomer.setGameScreen(this.gameScreen);

        // Add the new customer to customers and stationCustomerMap, then return the new Customer.
        this.stationCustomerMap.put(getStationKey(stationCustomerMap, station_y*Constants.UnitScale), newCustomer);
        this.customers.add(newCustomer);
        return newCustomer;
    }

    public String getMode() {
        return  this.mode;
    }

    public int getDifficulty() {
        return this.difficulty;
    }
}
