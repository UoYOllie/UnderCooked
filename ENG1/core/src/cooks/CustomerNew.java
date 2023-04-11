package cooks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import food.Recipe;
import game.GameSprites;
import helper.Constants;

/**
 * A Customer requests a dish to be served to them by the cook.
 */
public class CustomerNew extends GameEntity {

    /** The position of the Customer. */
    public Vector2 position;

    /** The Sprite of the Customer. */
    public Sprite sprite;

    /** The name of the recipe being requested. */
    public String request;

    /** The interaction rectangle of the customer. */
    public Rectangle rectangle;

   // public GameSprites gameSprites;

    public CustomerNew(float x, float y, float width, float height) {
        super(x, y, width, height);
        //this.gameSprites = GameSprites.getInstance();
        this.sprite = GameSprites.getInstance().getSprite(GameSprites.SpriteID.CUSTOMER, "UP");
        this.position = Constants.customerSpawn;
        this.request = Recipe.randomRecipe();
    }

    public String randomRecipe() {
        this.request = Recipe.randomRecipe();
        return request;
    }

    @Override
    public void update(float delta) {
    }

    /**
     * Renders the {@link Customer} at their {@link #position}.
     * @param batch The {@link SpriteBatch} of the game.
     */
    public void render(SpriteBatch batch) {
        setSprite();
        sprite.setPosition(position.x-sprite.getWidth()/2, position.y-sprite.getHeight()/2);
        this.sprite.setSize(5,8);
        sprite.draw(batch);
        System.out.println("rendering the customer");
    }

    @Override
    public void renderShape(ShapeRenderer shape) {
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

    private void setSprite() {
        sprite = GameSprites.getInstance().getSprite(GameSprites.SpriteID.COOK, "UP");
    }

}