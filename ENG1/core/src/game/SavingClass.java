package game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import cooks.Cook;
import food.FoodItem;

public class SavingClass {
    private int gold;
    private int reputation;



    //Cooks and customers
    //------------------------------------------------------------------------------------
    /**
     * for cooks and customers:
     * --------------------------
     * 0 = Unused cook
     * 1 = Used cook
     * 2 = Spawned Customer
     */
    private Array<Integer> cooks; //See above ^^^^
    private Array<Array<Float>> cookscoords; //If not spawned, (0,0)
    private Array<Array<Integer>> cookstack1;
    private Array<Array<Integer>> cookstack2;
    private Array<Array<Integer>> cookdishstack;
    private Array<Boolean> cookisbluggus; //if chef is bluggus or not
    private Array<Float> cookspeed;
    private Array<String> colour; //allocates the sprite
    private Array<Float> waitimes; //-1 for cooks
    private Array<String> requests; //norequest = cooks
    //------------------------------------------------------------------------------------






    public SavingClass(GameScreen g)
    {
        SaveGoldRep(g);

    }


    //Run for saving cook and customer data data
    private void PersonData(Cook cook,Integer persontype)
    {
        this.cooks.add(persontype);
        Array<Float> coords = new Array<Float>();
        coords.add(cook.getX());
        coords.add(cook.getY());
        this.cookscoords.add(coords);
    }
    private void SaveCooksAndCustomers(GameScreen gameScreen)
    {

    }

    //Run for saving station data
    private String SaveStations()
    {
        String json = "";
        return  json;
    }

    //Run for saving rep and money data
    private void SaveGoldRep(GameScreen gameScreen)
    {
        //Get User Gold
        this.gold = gameScreen.gold.getBalance();
        //get reputation
        this.reputation = gameScreen.Reputation.getPoints();
    }
}
