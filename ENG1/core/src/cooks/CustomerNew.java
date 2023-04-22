package cooks;

//import Shop.MindControl;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import food.DishStack;
import food.FoodItem;
import food.FoodStack;
import food.Recipe;
import game.GameScreen;
import game.GameSprites;
import helper.Constants;
import helper.CustomerCollisionHelper;
import helper.NewCollisionHelper;
import interactions.InputKey;
import interactions.Interactions;
import stations.CookInteractable;
import stations.Station;

import java.util.ArrayList;
import java.util.Random;

/**
 * A Customer requests a dish to be served to them by the cook.
 */
public class CustomerNew extends GameEntity {

    /** The position of the CustomerNew. */
    public Vector2 position;

    /** The Sprite of the CustomerNew. */
    public Sprite sprite;

    /** The name of the recipe being requested. */
    public String request;

    public boolean Stillhere;
    public float waittime;

    public DishStack dishStack;

    public Rectangle customerInteractor;

    private GameScreen gameScreen;
    private Vector2 stationPosition;
    private Vector2 destination;
    public int customerStatus;


    /** The Constructor for CustomerNew. */
    public CustomerNew(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.sprite = GameSprites.getInstance().getSprite(GameSprites.SpriteID.CUSTOMER, "0");
        //this.position = new Vector2(x, y);
        this.customerInteractor = new Rectangle(x - 4 * Constants.UnitScale, y - 4 * Constants.UnitScale,
                                                width + 1f, height + 1f);
        this.request = Recipe.randomRecipe();
        this.dishStack = new DishStack();

        //Waittime in seconds
        Random rd = new Random();
        this.waittime = 150 + rd.nextFloat()*150;
        this.Stillhere = true;
        //

        //this.x = this.position.x;
        //this.y = this.position.y;

        this.x = x;
        this.y = y;

        this.stationPosition = new Vector2(x, y);
        this.destination = new Vector2(this.x, Constants.customerSplitPoint);
        this.customerStatus = 0;
    }

    public void setStationPosition(float endX, float endY) {
        this.stationPosition.x = endX;
        this.stationPosition.y = endY;
    }

    public void setDestination(float endX, float endY) {
        this.destination.x = endX;
        this.destination.y = endY;
    }

    /**
     * Helper method to return the sign of a value.
     * */
    public int sign(float value) {
        if (value >= 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public void setGameScreen(GameScreen g)
    {
        this.gameScreen = g;
    }

    public void Hypnotise() //Change their mind powerup
    {
        String temp = Recipe.randomRecipe();
        if(this.request != temp)
        {
            this.request = temp;
        }
        else {
            Hypnotise();
        }
    }

    public void HangOnYourFoodIsComing() //Makes them wait longer
    {
        this.waittime = 300;
    }
    public void DecreasePatience(){
        this.waittime = waittime-1f;
    }

    public void StormOut()
    {
        this.gameScreen.Reputation.Negative();
        Leave();
    }
    public void Success() //Gets their dish, this is called
    {
        this.gameScreen.Reputation.Positive();
        this.gameScreen.gold.addBalance(50);
        Leave();
    }

    private void Leave() //This function will be called when a customer leaves
    {
        this.Stillhere = false;
        System.out.println("Im leaving, bye");
        //Implement walk off
        //implement removement from array
        // from laura to morgan : i might make these move from a 'customersToServe' to 'servedCustomers' array
    }

    public void customerInteract(ArrayList<Station> mapStations) {
        for (Station currentStation : mapStations) {
            if (Intersector.overlaps(this.customerInteractor, currentStation.rectangle)){
                currentStation.customerInteract(this);
            }
        }
    }

    @Override
    public void update(float delta) {

        if (this.customerStatus == 0) {

            System.out.println("I am travelling down the corridor");

            if (this.y > destination.y) {
                this.y -= Constants.UnitScale;
            } else {
                this.setDestination(stationPosition.x, stationPosition.y);
                this.customerStatus += 1;
            }

        } else if (this.customerStatus == 1) {

            Boolean x_ready = false, y_ready = false;

            if (this.y > stationPosition.y) { this.y -= Constants.UnitScale;
            } else { x_ready = true;}

            if (this.x < stationPosition.x) { this.x += Constants.UnitScale;
            } else { y_ready = true;}

            if (x_ready && y_ready) {
                this.customerStatus += 1;
            }

        } else if (this.customerStatus == 2) {
            System.out.println("i am waiting to be served");
        }

        // Updates Interaction box (again change 1/8f to a const)
        this.customerInteractor.x = x - 1 / 8f;
        this.customerInteractor.y = y - 1 / 8f;
    }

    /**
     * Renders the CustomerNew at their position.
     * @param batch The SpriteBatch to be rendered.
     */
    public void render(SpriteBatch batch) {

        sprite = GameSprites.getInstance().getSprite(GameSprites.SpriteID.CUSTOMER, "0");

        //sprite.setPosition(position.x-sprite.getWidth()/2, position.y-sprite.getHeight()/2);
        sprite.setPosition(this.x-sprite.getWidth()/2, this.y-sprite.getHeight()/2);
        this.sprite.setSize(6,5.7f);

        sprite.draw(batch);
        renderFood(batch);
    }

    /**
     * Renders the {@link FoodStack} of the {@link Cook} visually.
     * @param batch The {@link SpriteBatch} that the {@link Cook} will render using.
     */
    private void renderFood(SpriteBatch batch) {

        Array<FoodItem.FoodID> dishList = dishStack.getStack();
        float xOffset = 0F, yOffset = 0F;
        float drawX = x, drawY = y;

        // Draw each FoodItem in DishList.
        for (int i = dishList.size-1 ; i >= 0 ; i--) {
            Sprite foodSprite = GameSprites.getInstance().getSprite(GameSprites.SpriteID.FOOD, String.valueOf(dishList.get(i)));
            foodSprite.setScale(0.8f*Constants.UnitScale);
            foodSprite.setPosition((drawX - foodSprite.getWidth() / 3 + xOffset - 3.5f * Constants.UnitScale) - 1.15f,
                    (drawY - foodSprite.getHeight() * 0.33f + yOffset * Constants.UnitScale) - 4.3f);
            foodSprite.draw(batch);
            drawY += FoodItem.foodHeights.get(dishList.get(i)) * 0.18F;
        }
    }

    /**
     * Getter for the x-position of CustomerNew.
     * @return The x-position of the customer.
     */
    public float getX() {
        return x;
    }

    /**
     * Getter for the y-position of CustomerNew.
     * @return The y-position of the customer.
     */
    public float getY() {
        return y;
    }

    /**
     * Getter to get the name of the request of the CustomerNew.
     * @return The RecipeName of the customer's request.
     */
    public String getRequestName() {
        return request;
    }


    // unused abstract methods

    @Override
    public void renderShape(ShapeRenderer shape) {
    }

}