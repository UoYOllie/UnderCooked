package customers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import cooks.GameEntity;
import food.FoodStack;
import food.Recipe;
import game.GameScreen;
import game.GameSprites;
import helper.Constants;

/**
 * A Customer requests a dish to be served to them by the cook.
 */
public class Customer {

    /** The position of the Customer. */
    public Vector2 position;

    /** The Sprite of the Customer. */
    public Sprite sprite;

    /** The name of the recipe being requested. */
    public String request;

    public Customer() {
        this.sprite = GameSprites.getInstance().getSprite(GameSprites.SpriteID.CUSTOMER, "DOWN");
        this.position = Constants.customerSpawn;
        this.request = Recipe.randomRecipe();
    }

    /**
     * The constructor for the Customer.
     * Randomly picks a recipe for the Customer to request.
     * @param sprite The Sprite of the customer.
     */
    public Customer(Sprite sprite) {
        this.sprite = sprite;
        this.position = Constants.customerSpawn;
        this.request = Recipe.randomRecipe();
    }

    /**
     * Another constructor for the {@link Customer}, with a specified position.
     * <br>Randomly picks out a {@link Recipe} as a request.
     * @param sprite The {@link Sprite} of the {@link Customer}.
     * @param position A {@link Vector2} position of the {@link Customer}.
     */
    public Customer(Sprite sprite, Vector2 position) {
        this(sprite);
        this.position = position;
    }

    public String randomRecipe() {
        this.request = Recipe.randomRecipe();
        return request;
    }

    /**
     * Renders the {@link Customer} at their {@link #position}.
     * @param batch The {@link SpriteBatch} of the game.
     */
    public void render(SpriteBatch batch) {
        sprite.setPosition(position.x-sprite.getWidth()/2, position.y-sprite.getHeight()/2);
        sprite.draw(batch);
    }

    /**
     * Getter for the {@code x} position of the {@link Customer}.
     * @return {@code float} : The {@code x} of the {@link Customer}.
     */
    public float getX() {
        return position.x;
    }

    /**
     * Getter for the {@code y} position of the {@link Customer}.
     * @return {@code float} : The {@code y} of the {@link Customer}.
     */
    public float getY() {
        return position.y;
    }

    /**
     * Getter to get the name of the request of the {@link Customer}.
     * @return {@link String} : The name of the {@link Customer}'s
     *                          {@link Recipe} request.
     */
    public String getRequestName() {
        return request;
    }
}
