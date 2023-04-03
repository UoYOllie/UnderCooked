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

    static {
        // BURGERS
        generateRecipe("Plain Burger", new FoodStack(FoodID.topBun, FoodID.meatCook, FoodID.bottomBun));
        generateRecipe("Lettuce Burger", new FoodStack(FoodID.topBun, FoodID.meatCook, FoodID.lettuceChop, FoodID.bottomBun));
        generateRecipe("Onion Burger", new FoodStack(FoodID.topBun, FoodID.meatCook, FoodID.onionChop, FoodID.bottomBun));
        generateRecipe("Tomato Burger", new FoodStack(FoodID.topBun, FoodID.meatCook, FoodID.tomatoChop, FoodID.bottomBun));
        generateRecipe("Lettuce Tomato Burger", new FoodStack(FoodID.topBun, FoodID.meatCook, FoodID.lettuceChop, FoodID.tomatoChop, FoodID.bottomBun));
        generateRecipe("Lettuce Onion Burger", new FoodStack(FoodID.topBun, FoodID.meatCook, FoodID.lettuceChop, FoodID.onionChop, FoodID.bottomBun));
        generateRecipe("Tomato Onion Burger", new FoodStack(FoodID.topBun, FoodID.meatCook, FoodID.tomatoChop, FoodID.onionChop, FoodID.bottomBun));
        generateRecipe("Lettuce Tomato Onion Burger", new FoodStack(FoodID.topBun, FoodID.meatCook, FoodID.lettuceChop, FoodID.tomatoChop, FoodID.onionChop, FoodID.bottomBun));

        // SALADS
        generateRecipe("Plain Salad", new FoodStack(FoodID.lettuceChop));
        generateRecipe("Tomato Salad", new FoodStack(FoodID.lettuceChop, FoodID.tomatoChop));
        generateRecipe("Onion Salad", new FoodStack(FoodID.lettuceChop, FoodID.onionChop));
        generateRecipe("Tomato Onion Salad", new FoodStack(FoodID.lettuceChop, FoodID.tomatoChop, FoodID.onionChop));
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
     * Checks if an input FoodStack matches the desired recipe.
     *
     * @param foodStack The input foodStack to be checked.
     * @param recipeName The name of the target recipe.
     *
     * @return Boolean
     */
    public static boolean matchesRecipe(FoodStack foodStack, String recipeName) {

        Array<FoodID> validFoodStackArray = recipes.get(recipeName).getStack();
        Array<FoodID> inputFoodStackArray = foodStack.getStack();

        if (validFoodStackArray.size != inputFoodStackArray.size) {
            return false;
        }
        for (FoodID validItem : validFoodStackArray) {
            if (!containsFood(inputFoodStackArray, validItem)) {
                return false;
            }
        }
        return true;
    }

    /** Helper method to choose a random recipe for the customer to order. */
    public static String randomRecipe() {
         Random random = new Random();
         return recipeNames.get(random.nextInt(recipeNames.size));
    }

    public static FoodStack getRecipe(String recipeName) {
        return recipes.get(recipeName);
    }
}
