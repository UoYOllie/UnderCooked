package game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import cooks.Cook;
import cooks.CustomerNew;
import food.FoodItem;
import food.FoodStack;

import java.util.ArrayList;

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





    private void setAttributes()
    {
        this.cooks = new Array<Integer>();
        this.cookscoords = new Array<Array<Float>>();
        this.cookstack1 = new Array<Array<Integer>>();
        this.cookstack2 = new Array<Array<Integer>>();
        this.cookdishstack = new Array<Array<Integer>>();
        this.cookisbluggus = new Array<Boolean>();
        this.cookspeed = new Array<Float>();
        this.colour = new Array<String>();
        this.waitimes = new Array<Float>();
        this.requests = new Array<String>();
    }
    public SavingClass(GameScreen g)
    {
        setAttributes();

        SaveGoldRep(g);
        SaveCooksAndCustomers(g);

    }


    //Run for saving cook and customer data data
    private void CookData(Cook cook,Integer persontype)
    {
//        private Array<Integer> cooks; //See above ^^^^
        this.cooks.add(persontype);

//        private Array<Array<Float>> cookscoords; //If not spawned, (0,0)
        Array<Float> coords = new Array<Float>();
        coords.add(cook.getX());
        coords.add(cook.getY());
        this.cookscoords.add(coords);

//        private Array<Array<Integer>> cookstack   ;

        Array<FoodItem.FoodID> tempstack = new Array<FoodItem.FoodID> ();

        Array<Integer> stack1items = new Array<Integer>();
        tempstack = cook.foodStack.getStackCopy();
        for(FoodItem.FoodID f:tempstack)
        {
            stack1items.add(f.ordinal());
        }
        this.cookstack1.add(stack1items);

        Array<Integer> stack2items = new Array<Integer>();

//        private Array<Array<Integer>> cookstack2;

        tempstack = cook.foodStack2.getStackCopy();
        for(FoodItem.FoodID f:tempstack)
        {
            stack2items.add(f.ordinal());
        }
        this.cookstack2.add(stack2items);

//        private Array<Array<Integer>> cookdishstack;
        Array<Integer> dishy = new Array<Integer>();
        tempstack = cook.dishStack.getStackCopy();
        for(FoodItem.FoodID f:tempstack)
        {
            dishy.add(f.ordinal());
        }
        this.cookdishstack.add(dishy);

//        private Array<Boolean> cookisbluggus; //if chef is bluggus or not
        this.cookisbluggus.add(cook.activateBluggus);
//        private Array<Float> cookspeed;
        this.cookspeed.add(cook.movement_speed);
//        private Array<String> colour; //allocates the sprite
        this.colour.add(cook.Colour);
//        private Array<Float> waitimes; //-1 for cooks
        this.waitimes.add(-1f);
//        private Array<String> requests; //norequest = cooks
        this.requests.add("norequest");
    }
    private void CustomerData(CustomerNew customer, Integer persontype)
    {
//        private Array<Integer> cooks; //See above ^^^^
        this.cooks.add(persontype);
//        private Array<Array<Float>> cookscoords; //If not spawned, (0,0)
        Array<Float> coords = new Array<Float>();
        coords.add(customer.getX());
        coords.add(customer.getY());
        this.cookscoords.add(coords);
//        private Array<Array<Integer>> cookstack1;
        Array<Integer> s = new Array<Integer>();
        s.add(-1);
        this.cookstack1.add(s);
//        private Array<Array<Integer>> cookstack2;
        this.cookstack2.add(s);
//        private Array<Array<Integer>> cookdishstack;
        Array<FoodItem.FoodID> tempstack = new Array<FoodItem.FoodID> ();
        Array<Integer> dishy = new Array<Integer>();
        tempstack = customer.dishStack.getStackCopy();
        for(FoodItem.FoodID f:tempstack)
        {
            dishy.add(f.ordinal());
        }
        this.cookdishstack.add(dishy);
//        private Array<Boolean> cookisbluggus; //if chef is bluggus or not
        this.cookisbluggus.add(false);
//        private Array<Float> cookspeed;
        this.cookspeed.add(-1f); //Check with laura
//        private Array<String> colour; //allocates the sprite
        this.colour.add("Blank");
//        private Array<Float> waitimes; //-1 for cooks
        this.waitimes.add(customer.waittime);
//        private Array<String> requests; //norequest = cooks
        this.requests.add(customer.request);
    }
    private void SaveCooksAndCustomers(GameScreen gameScreen)
    {
        Array<Cook> cookarray = gameScreen.cooks;
        Array<Cook> unusedcookarray = gameScreen.unusedcooks;
        ArrayList<CustomerNew> customers = gameScreen.customersToServe;

        for(Cook c:cookarray)
        {
            CookData(c,1);
        }
        for(Cook c:unusedcookarray)
        {
            CookData(c,0);
        }
        for(CustomerNew c:customers)
        {
            CustomerData(c,2);
        }

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
