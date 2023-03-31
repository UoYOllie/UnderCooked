package food;

import java.util.HashMap;
import java.util.Random;
import com.badlogic.gdx.utils.Array;

import food.FoodItem.FoodID;

/** Contains all the Recipes.
 *
 * NEW CLASS CREATED BY LAURA
 * I don't want to completely destroy the game by refactoring Recipe.java off the bat
 * so testing things here first.
 *
 */
public class Recipes {

    private static Array<String> recipeNames = new Array<>();
    public static final HashMap<String, FoodStack> recipes = new HashMap<>();

    static {
        // Recipe combinations - i want to automate this but it's lower priority as long as it currently works

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

    private static void generateRecipe(String recipeName, FoodStack validFoodStack) {
        recipes.put(recipeName, validFoodStack);
        recipeNames.add(recipeName);
    }

    public static boolean containsFood(Array<FoodID> myList, FoodID element) {

        for(FoodID foodID : myList) {
            if (foodID.equals(element)) {
                return true;
            }
        }
        return false;
    }

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

    // the getRecipeOption seems to only apply for the multiple recipes thing that i got rid of
    // it'll probably have repercussions later but leaving it for now
    public static String randomRecipe() {
        Random random = new Random();
        return recipeNames.get(random.nextInt(recipeNames.size));
    }
}
