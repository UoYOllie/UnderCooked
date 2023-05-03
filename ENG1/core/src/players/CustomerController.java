package players;

import game.GameScreen;
import helper.Constants;
import stations.Station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/** Used by GameScreen to manage all customers in the game.
 * Continually generates customers to empty serving stations (either endlessly or up to
 * a limit, depending on scenario or endless mode).
 */
public class CustomerController {

    /** The current instance of GameScreen.*/
    private GameScreen gameScreen;

    /** The ArrayList of all customers currently in the game.*/
    public ArrayList<Customer> customers;

    /** The ArrayList of all s in the game, used for generating customers.*/
    private ArrayList<Station> servingStations;

    /** The Map linking each station to exactly one customer who has not been served.*/
    private Map<Station, Customer> stationCustomerMap;

    /** The mode of the game being played, either "scenario" or "endless".*/
    private String mode;

    /** The difficulty of the game being played, either 1 (easy), 2 (medium), or 3 (hard).*/
    private int difficulty;

    /** The time since the last customer was spawned (in seconds).*/
    private int interval;
    
    /** The size of the group entering at the moment.*/
    private int groupSize;

    /** The number of customers left to serve, if playing scenario mode.*/
    private int customersLeft;

    /** The number of customers that have been served in endless mode.*/
    public int TotalCustomersServed;

    /** The constructor of CustomerController.
     * @param gameScreen the instance of GameScreen for the current game.
     * */
    public CustomerController(GameScreen gameScreen) {

        this.gameScreen = gameScreen;
        this.customers = new ArrayList<>();

        this.servingStations = gameScreen.mapHelper.getServingStationList();
        this.stationCustomerMap = new HashMap<Station, Customer>();
        initialiseStationCustomerMap();

        this.interval = 1;
        this.groupSize = 1;
        this.customersLeft = 4;
        this.TotalCustomersServed = 0;
    }

    /** Constructor of CustomerController without the use of gameScreen, used for testing only.*/
    public CustomerController() {

        this.customers = new ArrayList<>();

        this.servingStations = new ArrayList<Station>();
        this.stationCustomerMap = new HashMap<Station, Customer>();
        initialiseStationCustomerMap();

        this.interval = 1;
        this.groupSize = 1;
        this.customersLeft = 4;
        this.TotalCustomersServed = 0;
    }

    /** Getter for customers.*/
    public ArrayList<Customer> getCustomers() {
        return this.customers;
    }

    /** Setter for servingStations.*/
    public void setServingStations(ArrayList<Station> array){ this.servingStations = array; }

    /** Setter for stationCustomerMap*/
    public void setStationCustomerMap(Map<Station,Customer> map){
        this.stationCustomerMap = map;
    }

    /** Setter for stationCustomerMap*/
    public Map getStationCustomerMap(){
        return this.stationCustomerMap;
    }

    /** Setter for mode.*/
    public void setMode(String mode) {
        this.mode = mode;
    }

    /** Getter for mode.*/
    public String getMode() { return  this.mode; }

    /** Setter for difficulty.*/
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /** Getter for difficulty.*/
    public int getDifficulty() { return this.difficulty; }

    /** Getter for customersLeft.*/
    public int getcustomersLeft() { return  this.customersLeft;}

    /** Setter for customersLeft.*/
    public void setcustomersLeft(int left) { customersLeft = left;}

    /** Method to initialise StationCustomerMap to map each  to a null customer. */
    public void initialiseStationCustomerMap() {

        for (Station station : servingStations) {
            station.y = station.y;
            this.stationCustomerMap.put(station, null);
        }
    }

    /**
     * Method used by GameScreen to add a customer to stationCustomerMap, only if the customer
     * corresponding to the ServingStation is null.
     * @return the Customer that has been added, or null if all servingStations are busy.
     * */
    public Customer addCustomer() {

        interval -= 1;
        System.out.println("interval " + interval);
        System.out.println("group size " + groupSize);

        // Check if all customers have been served, if yes return.
        // Also checks if the current interval is a multiple of 10, new customers
        // are spawned every 10 seconds.
        if ((this.mode == "scenario" && this.customers.size() > 4) || !(this.interval==0)) {
            System.out.println("no customer added this time!");
            return null;
        }

        // Iterate through each servingStation key in stationCustomerMap.
        for (Map.Entry<Station, Customer> entry : stationCustomerMap.entrySet()) {

            Station station = entry.getKey();
            Customer customer = entry.getValue();

            // If there is no customer at the station, create a new one.
            if (customer == null) {

                Customer newCustomer = new Customer(Constants.customerPointA.x, Constants.customerPointA.y, 3.34f, 3f);

                // Set station position, difficulty, and gameScreen attributes for the Customer.

                System.out.println(station.getX());
                System.out.println(station.getY());
                newCustomer.setStationPosition(station.getX(), station.getCoolY());
                newCustomer.setDifficulty(difficulty);
                newCustomer.setGameScreen(this.gameScreen);

                // Add the new customer to customers and stationCustomerMap, then return the new Customer.
                this.stationCustomerMap.put(station, newCustomer);
                this.customers.add(newCustomer);

                if (this.mode == "endless" && this.customers.size() > 5) {
                    if (groupSize > 1) {
                        groupSize -= 1;
                        interval = 1;
                    } else {
                        Random rd = new Random();
                        this.groupSize = rd.nextInt(1, 4);
                        this.interval = 10;
                    }
                } else {
                    this.interval = 10;
                }
                return newCustomer;
            }
        }
        interval = 10;
        return null;
    }

    /**
     * Method to remove a customer from stationCustomerMap, after they have been saved.
     * @param station the  to remove the customer from.
     * */
    public void removeCustomer(Station station) {
        this.stationCustomerMap.put(station, null);
    }

    /** Helper method to check whether all customers have been served in Scenario Mode.
     * @return true if all customers in the scenario have been served.
     * */
    public boolean maxCustomersReached() {
        return (this.mode == "scenario" && this.customers.size() > 4);
    }

    /** Method to determine whether a game has been won in scenario mode.
     *
     * Checks if playing in scenario mode, then checks if any customers have been served
     * within that frame and deccreases customersLeft if it has.
     * Checks if no customers are left.
     *
     * @return true If the game has been won, false otherwise.*/
    public boolean wonScenario() {

        if (this.mode == "scenario" && this.customersLeft > 0) {
            for (Customer customer : customers) {
                if (customer.getCustomerStatus() == 2) {
                    this.customersLeft -= 1;
                    customer.setCustomerStatus(customer.getCustomerStatus() + 1);
                    System.out.println("customers left " + customersLeft);
                }
            }
        } else if (this.mode == "scenario" || this.customersLeft == 0) {
            return true;
        }

        return false;
    }

    /** Helper method to obtain the Station key of stationCustomerMap from its position.
     *
     * @param stationCustomerMap The existing stationCustomerMap.
     * @param station_y The y-coordinate of the station being searched for.
     * @return Station The object Station from the stationCustomerMap.
     * */
    public Station getStationKey(Map<Station, Customer> stationCustomerMap, float station_y) {

        for (Map.Entry<Station, Customer> entry : stationCustomerMap.entrySet()) {
            Station station = entry.getKey();
            if(station!=null) {
                if (Math.round(station.getY()) == Math.round(station_y)) {
                    return station;
                }
            }
        }
        return null;
    }

    /** Method to add customers back into the game when loading after a save.
     * Adds the customers back into stationCustomerMap and customers.
     *
     * @param x The x-coordinate of the customer before saving.
     * @param y The y-coordinate of the customer before saving.
     * @param station_x The x-coordinate of the customer's destined station before saving.
     * @param station_y The y-coordinate of the customer's destined station before saving.
     *
     * @return Customer The new customer for GameScreen to add into gameScreen.
     * */
    public Customer addSavedCustomer(float x,float y, float station_x, float station_y) {

        if (this.stationCustomerMap.isEmpty()) {
            initialiseStationCustomerMap();
        }

        Customer newCustomer = new Customer(x*Constants.UnitScale, y*Constants.UnitScale, 3.34f, 3f);

        newCustomer.setStationPosition(station_x, station_y);
        newCustomer.setDifficulty(1);
        newCustomer.setGameScreen(this.gameScreen);

        // Add the new customer to customers and stationCustomerMap, then return the new Customer.
        this.stationCustomerMap.put(getStationKey(stationCustomerMap, station_y*Constants.UnitScale), newCustomer);
        this.customers.add(newCustomer);
        return newCustomer;
    }

}
