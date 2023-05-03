package food;

import helper.Constants;

import java.util.HashMap;

/** A class that controls how FoodIDs are used and handled. */
public class FoodItem {

    /** IDs of all the different possible types of food ingredients.*/
    public enum FoodID {
        lettuce,
        lettuceChop,
        tomato,
        tomatoChop,
        onion,
        onionChop,
        meat,
        meatCook,
        bun,
        plate,
        potato,
        potatoCook,
        bakedBeans,
        cheese,
        coleslaw,
        dough,
        doughCook,
        tomatoSauce,
        menu,
        teacup,
        burger,
        pizza,
        salad,
        jacketPotato,
        beansCooked,
        waste,
        lock,

        /** Default */
        none
    }

    /** A dict of the pixel height for each food. Used when rendering the FoodStack.*/
    public static final HashMap<FoodID, Float> foodHeights = new HashMap<>();

    static {
        foodHeights.put(FoodID.plate, 12F * Constants.UnitScale);
        foodHeights.put(FoodID.lettuce, 20F * Constants.UnitScale);
        foodHeights.put(FoodID.lettuceChop, 4F * Constants.UnitScale);
        foodHeights.put(FoodID.tomato, 20F * Constants.UnitScale);
        foodHeights.put(FoodID.tomatoChop, 5.8F * Constants.UnitScale);
        foodHeights.put(FoodID.onion, 20F * Constants.UnitScale);
        foodHeights.put(FoodID.onionChop, 5.8F * Constants.UnitScale);
        foodHeights.put(FoodID.meat, 8F * Constants.UnitScale);
        foodHeights.put(FoodID.meatCook, 8F * Constants.UnitScale);
        foodHeights.put(FoodID.bun, 20F * Constants.UnitScale);
        foodHeights.put(FoodID.potato, 12F * Constants.UnitScale);
        foodHeights.put(FoodID.potatoCook, 12F * Constants.UnitScale);
        foodHeights.put(FoodID.bakedBeans, 12F * Constants.UnitScale);
        foodHeights.put(FoodID.cheese, 12F * Constants.UnitScale);
        foodHeights.put(FoodID.coleslaw, 12F * Constants.UnitScale);
        foodHeights.put(FoodID.dough, 12F * Constants.UnitScale);
        foodHeights.put(FoodID.doughCook, 12F * Constants.UnitScale);
        foodHeights.put(FoodID.tomatoSauce, 12F * Constants.UnitScale);
        foodHeights.put(FoodID.menu, 12F * Constants.UnitScale);
        foodHeights.put(FoodID.teacup, 12F * Constants.UnitScale);
        foodHeights.put(FoodID.burger, 12F * Constants.UnitScale);
        foodHeights.put(FoodID.pizza, 12F * Constants.UnitScale);
        foodHeights.put(FoodID.salad, 12F * Constants.UnitScale);
        foodHeights.put(FoodID.jacketPotato, 12F * Constants.UnitScale);
        foodHeights.put(FoodID.beansCooked, 12F * Constants.UnitScale);
        foodHeights.put(FoodID.waste, 12F * Constants.UnitScale);
        foodHeights.put(FoodID.lock, 12F * Constants.UnitScale);
    }

    /** A register of each food name as a string and the FoodID corresponding to it. */
    public static final HashMap<String, FoodID> foods = new HashMap<>();
    static {
        foods.put("plate", FoodID.plate);
        foods.put("lettuce", FoodID.lettuce);
        foods.put("lettuceChop", FoodID.lettuceChop);
        foods.put("tomato", FoodID.tomato);
        foods.put("tomatoChop", FoodID.tomatoChop);
        foods.put("onion", FoodID.onion);
        foods.put("onionChop", FoodID.onionChop);
        foods.put("meat", FoodID.meat);
        foods.put("meatCook", FoodID.meatCook);
        foods.put("bun", FoodID.bun);
        foods.put("potato", FoodID.potato);
        foods.put("potatoCook", FoodID.potatoCook);
        foods.put("bakedBeans", FoodID.bakedBeans);
        foods.put("cheese", FoodID.cheese);
        foods.put("coleslaw", FoodID.coleslaw);
        foods.put("dough", FoodID.dough);
        foods.put("doughCook", FoodID.doughCook);
        foods.put("tomatoSauce", FoodID.tomatoSauce);
        foods.put("menu", FoodID.menu);
        foods.put("teacup", FoodID.teacup);
        foods.put("burger", FoodID.burger);
        foods.put("pizza", FoodID.pizza);
        foods.put("salad", FoodID.salad);
        foods.put("jacketPotato", FoodID.jacketPotato);
        foods.put("beansCooked", FoodID.beansCooked);
        foods.put("waste", FoodID.waste);
        foods.put("lock", FoodID.lock);

    }
}

