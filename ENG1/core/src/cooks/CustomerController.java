package cooks;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import game.GameScreen;
import helper.Constants;
import stations.Station;

/**
 * Used by GameScreen to manage all customers in the game.
 * Continually generates customers to empty serving stations (either endlessly or up to
 * a limit, depending on scenario or endless mode).
 */
public class CustomerController {

    /** The current instance of GameScreen.*/
    private GameScreen gameScreen;

    /** The ArrayList of all customers currently in the game.*/
    private ArrayList<CustomerNew> customers;

    /** The ArrayList of all ServingStations in the game, used for generating customers.*/
    private ArrayList<Station> servingStations;

    /** The Map linking each station to exactly one customer who has not been served.*/
    private Map<Station, CustomerNew> stationCustomerMap;

    /** The mode of the game being played, either "scenario" or "endless".*/
    private String mode;

    /** The difficulty of the game being played, either 1 (easy), 2 (medium), or 3 (hard).*/
    private int difficulty;

    /** The constructor of CustomerController.
     * @param gameScreen the instance of GameScreen for the current game.
     * */
    public CustomerController(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.customers = new ArrayList<>();
        this.servingStations = gameScreen.mapHelper.getServingStationNewList();
        this.stationCustomerMap = new HashMap<Station, CustomerNew>();
        initialiseStationCustomerMap();
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
     * Method to check whether all customers have been served in Scenario Mode.
     * @return true if all customers in the scenario have been served.
     * */
    private boolean allCustomersServed() {
        return (this.mode == "scenario" && this.customers.size() > 4);
    }

    /**
     * Method to remove a customer from stationCustomerMap, after they have been saved.
     * @param station the ServingStation to remove the customer from.
     * */
    public void removeCustomer(Station station) {
        this.stationCustomerMap.put(station, null);
    }

    /** Method to initialise StationCustomerMap to map each ServingStation to a null customer. */
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

        // Check if all customers have been served, if yes return.
        if (allCustomersServed()) {
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

                // LAURA GET RID OF THIS LATER
                if (customers.size() == 2) {
                    newCustomer.customerToTest = true;
                    System.out.println("testing " + newCustomer + " with destination " + station);
                }

                // Add the new customer to customers and stationCustomerMap, then return the new Customer.
                this.stationCustomerMap.put(station, newCustomer);
                this.customers.add(newCustomer);
                return newCustomer;
            }
        }

        // Returns null if all servingStations were busy.
        return null;
    }

}
