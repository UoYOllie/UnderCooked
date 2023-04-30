package food;

import java.util.HashMap;
import java.util.Random;
import com.badlogic.gdx.utils.Array;

import food.FoodItem.FoodID;

/**
 * Contains all the Recipes for burgers, salads, jacket potatoes, and pizzas.
 *
 * Each recipe is represented by a FoodStack. A FoodStack is valid if it contains the
 * same elements (in any order) as the recipe FoodStack.
 *
 * Also contains a Helper method for generating a customer's random recipe.
 *
 * @requirements FR_SERVE_CHECK
 */
public class Recipe {

    /** An array containing the names of all valid recipes, used for generating random recipes easily. */
    private static final Array<String> recipeNames = new Array<>();

    /** A HashMap mapping the name of each recipe to its valid FoodStack. */
	public static final HashMap<String, FoodStack> recipes = new HashMap<>();
    public static final Array<FoodID> toppings = new Array<>();

    static {

        // BURGERS
        generateRecipe("Plain Burger", new FoodStack(FoodID.bun, FoodID.meatCook, FoodID.bun));
        generateRecipe("Lettuce Burger", new FoodStack(FoodID.bun, FoodID.lettuceChop, FoodID.meatCook, FoodID.bun));
        generateRecipe("Onion Burger", new FoodStack(FoodID.bun, FoodID.onionChop, FoodID.meatCook, FoodID.bun));
        generateRecipe("Tomato Burger", new FoodStack(FoodID.bun, FoodID.tomatoChop, FoodID.meatCook, FoodID.bun));
        generateRecipe("Lettuce Tomato Burger", new FoodStack(FoodID.bun, FoodID.tomatoChop, FoodID.lettuceChop, FoodID.meatCook, FoodID.bun));
        generateRecipe("Lettuce Onion Burger", new FoodStack(FoodID.bun, FoodID.onionChop, FoodID.lettuceChop, FoodID.meatCook, FoodID.bun));
        generateRecipe("Tomato Onion Burger", new FoodStack(FoodID.bun, FoodID.onionChop, FoodID.tomatoChop, FoodID.meatCook, FoodID.bun));
        generateRecipe("Lettuce Tomato Onion Burger", new FoodStack(FoodID.bun, FoodID.onionChop, FoodID.tomatoChop, FoodID.lettuceChop, FoodID.meatCook, FoodID.bun));

        // SALADS
        generateRecipe("Plain Salad", new FoodStack(FoodID.lettuceChop));
        generateRecipe("Tomato Salad", new FoodStack(FoodID.tomatoChop, FoodID.lettuceChop));
        generateRecipe("Onion Salad", new FoodStack(FoodID.onionChop, FoodID.lettuceChop));
        generateRecipe("Tomato Onion Salad", new FoodStack(FoodID.onionChop, FoodID.tomatoChop, FoodID.lettuceChop));

        // JACKET POTATOES
        generateRecipe("Plain Potato", new FoodStack(FoodID.potatoCook));
        generateRecipe("Beans Potato", new FoodStack(FoodID.bakedBeans, FoodID.potatoCook));
        generateRecipe("Coleslaw Potato", new FoodStack(FoodID.coleslaw, FoodID.potatoCook));
        generateRecipe("Cheese Potato", new FoodStack(FoodID.cheese, FoodID.potatoCook));
        generateRecipe("Beans Cheese Potato", new FoodStack(FoodID.cheese, FoodID.bakedBeans, FoodID.potatoCook));
        generateRecipe("Beans Coleslaw Potato", new FoodStack(FoodID.coleslaw, FoodID.bakedBeans, FoodID.potatoCook));
        generateRecipe("Coleslaw Cheese Potato", new FoodStack(FoodID.cheese, FoodID.coleslaw, FoodID.potatoCook));
        generateRecipe("Beans Coleslaw Cheese Potato", new FoodStack(FoodID.cheese, FoodID.coleslaw, FoodID.beansCooked, FoodID.potatoCook));

        // PIZZA - i'll add some more interesting combos later down the line
        generateRecipe("Plain Pizza", new FoodStack(FoodID.cheese, FoodID.tomatoSauce, FoodID.doughCook));
        generateRecipe("Pepperoni Pizza", new FoodStack(FoodID.cheese, FoodID.pepperoni, FoodID.tomatoSauce, FoodID.doughCook));
        generateRecipe("Onion Pizza", new FoodStack(FoodID.cheese, FoodID.onionChop, FoodID.tomatoSauce, FoodID.doughCook));

        // POWERUPS
        //generateRecipe("Teacup", new FoodStack(FoodID.teacup));

        // TOPPINGS
        toppings.add(FoodID.lettuceChop);
        toppings.add(FoodID.tomatoChop);
        toppings.add(FoodID.onionChop);
        toppings.add(FoodID.cheese);
        toppings.add(FoodID.bakedBeans);
        toppings.add(FoodID.coleslaw);
        toppings.add(FoodID.pepperoni);

    }

    /**
     * Puts each corresponding recipeName and FoodStack into the recipes HashMap.
     *
     * @param recipeName The name of recipe, comprised of its type and toppings (eg. "Lettuce Burger").
     * @param validFoodStack The FoodStack containing the elements needed for the recipe.
     */
	private static void generateRecipe(String recipeName, FoodStack validFoodStack) {
		recipes.put(recipeName, validFoodStack);
        recipeNames.add(recipeName);
	}

    /**
     * Helper method to check whether an array of food items contains a particular item.
     *
     * @param myList An array of FoodIDs to check.
     * @param element The FoodID being searched for.
     *
     * @return Boolean
     */
    public static boolean containsFood(Array<FoodID> myList, FoodID element) {

        for(FoodID foodID : myList) {
            if (foodID.equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to check if an input FoodStack matches the desired recipe.
     *
     * @param foodStack The input foodStack to be checked.
     * @param recipeName The name of the target recipe.
     *
     * @return true if the inputFoodStack matches the target recipe, false otherwise.
     */
    public static boolean matchesRecipe(FoodStack foodStack, String recipeName) {

        Array<FoodID> validFoodStackArray = recipes.get(recipeName).getStack();
        Array<FoodID> inputFoodStackArray = foodStack.getStack();

        if (validFoodStackArray.size != inputFoodStackArray.size) {
            return false;
        }

        for (int i=0; i < validFoodStackArray.size; i++) {
            if (!containsFood(inputFoodStackArray, validFoodStackArray.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean matchesRecipeArray(Array<FoodID> inputFoodStackArray, String recipeName) {

        Array<FoodID> validFoodStackArray = recipes.get(recipeName).getStack();
        //Array<FoodID> inputFoodStackArray = foodStack.getStack();

        if (validFoodStackArray.size != inputFoodStackArray.size) {
            return false;
        }

        for (int i=0; i < validFoodStackArray.size; i++) {
            if (!containsFood(inputFoodStackArray, validFoodStackArray.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method to check whether a FoodStack is a valid recipe.
     * COULD PROBABLY COMBINE matchesRecipe AND validRecipe IF TIME ALLOWS.
     *
     * @return The ordered FoodStack of the recipe if it is valid, null otherwise. */
    public static FoodStack validRecipe(FoodStack inputFoodStack) {

        for (String recipeName : recipeNames) {
            if (matchesRecipe(inputFoodStack, recipeName)) {
                return recipes.get(recipeName);
            }
        }

        return null;
    }


    public static Array<FoodID> getCustomerRequestBubble(String request) {

        Array<FoodID> allItems = getRecipe(request).getStackCopy();
        Array<FoodID> bubbleItems = new Array<>();

        // Add a base item to the request bubble.
        if (containsFood(allItems, FoodID.meatCook)) {
            bubbleItems.add(FoodID.burger);
        } else if (containsFood(allItems, FoodID.lettuceChop)
                && !containsFood(allItems, FoodID.meatCook)) {
            bubbleItems.add(FoodID.salad);
        } else if (containsFood(allItems, FoodID.doughCook)) {
            bubbleItems.add(FoodID.pizza);
        } else if (containsFood(allItems, FoodID.potatoCook)) {
            bubbleItems.add(FoodID.jacketPotato);
        }

        // Add the toppings to the requestBubble.

        for (FoodID topping : toppings) {
            if (containsFood(allItems, topping)) {

                // LettuceChop is not a topping if the request is a salad:
                if (!(topping == FoodID.lettuceChop && containsFood(bubbleItems, FoodID.salad))) {
                    bubbleItems.add(topping);
                }
            }
        }

        return bubbleItems;

    }


    /** Helper method to choose a random recipe for the customer to order. */
    public static String randomRecipe() {
         Random random = new Random();
         return recipeNames.get(random.nextInt(recipeNames.size));
         //return ("Beans Potato");
    }

    public static FoodStack getRecipe(String recipeName) {
        return recipes.get(recipeName);
    }
}
