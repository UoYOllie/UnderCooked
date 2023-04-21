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


    /** The Constructor for CustomerNew. */
    public CustomerNew(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.sprite = GameSprites.getInstance().getSprite(GameSprites.SpriteID.CUSTOMER, "0");
        this.position = new Vector2(x, y);
        this.customerInteractor = new Rectangle(x - 4 * Constants.UnitScale, y - 4 * Constants.UnitScale,
                                                width + 1f, height + 1f);
        this.request = Recipe.randomRecipe();
        this.dishStack = new DishStack();

        //Waittime in seconds
        Random rd = new Random();
        this.waittime = 150 + rd.nextFloat()*150;
        this.Stillhere = true;
        //

        this.x = this.position.x;
        this.y = this.position.y;


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
    private void Success() //Gets their dish, this is called
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
    }

    public void customerInteract(ArrayList<Station> mapStations) {
        CustomerCollisionHelper checker = new CustomerCollisionHelper(gameScreen,this, mapStations);
        CookInteractable station = checker.NearbyStation(customerInteractor);

        for (Station station1 : mapStations) {
            if (Intersector.overlaps(this.customerInteractor, station1.rectangle)){
                //System.out.println("we gonna be interacting bois :)");
                station = station1;
                station.customerInteract(this);
                break;
            }
        }

        //System.out.println("G" + station);

//        for (InputKey inputKey : Interactions.getInputKeys(Interactions.InputID.COOK_INTERACT)) {
//            if (Gdx.input.isKeyJustPressed(inputKey.getKey())) {
//                //System.out.println(mapStations.toString());
//                CustomerCollisionHelper checker = new CustomerCollisionHelper(gameScreen,this,mapStations);
//                CookInteractable station = checker.NearbyStation(customerInteractor);
//                System.out.println(station);
//                if(station!=null) {
//                    System.out.println("customer is interacting with serving station");
//                    station.customerInteract(this, inputKey.getType());
//                }
//
//            }
//        }
    }

    @Override
    public void update(float delta) {
        //y = body.getPosition().y*PPM;
        //x = rectangle.x;
        //y = rectangle.y;

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

        sprite.setPosition(position.x-sprite.getWidth()/2, position.y-sprite.getHeight()/2);
        this.sprite.setSize(6,5.7f);

        sprite.draw(batch);
        renderFood(batch);
        //System.out.println("rendering a customer!");
    }

    /**
     * Renders the {@link FoodStack} of the {@link Cook} visually.
     * @param batch The {@link SpriteBatch} that the {@link Cook} will render using.
     */
    private void renderFood(SpriteBatch batch) {

        Array<FoodItem.FoodID> dishList = dishStack.getStack();
        float xOffset = 0F, yOffset = 0F;
        float drawX = x, drawY = y - 12 * Constants.UnitScale;
        // Get offset based on direction.

        drawX = x;
        drawY = y - 12 * Constants.UnitScale;

        for (int i = dishList.size-1 ; i >= 0 ; i--) {
            Sprite foodSprite = GameSprites.getInstance().getSprite(GameSprites.SpriteID.FOOD, String.valueOf(dishList.get(i)));
            Float drawInc = FoodItem.foodHeights.get(dishList.get(i)) * 0.5F;
            if (drawInc == null) {
                drawY += 5F;
                continue;
            }
            foodSprite.setScale(2 * Constants.UnitScale);
            foodSprite.setPosition(drawX-foodSprite.getWidth()/2+xOffset,drawY+yOffset);
            foodSprite.draw(batch);
            drawY += drawInc;
        }
    }

    /**
     * Getter for the x-position of CustomerNew.
     * @return The x-position of the customer.
     */
    public float getX() {
        return position.x;
    }

    /**
     * Getter for the y-position of CustomerNew.
     * @return The y-position of the customer.
     */
    public float getY() {
        return position.y;
    }

    /**
     * Getter to get the name of the request of the CustomerNew.
     * @return The RecipeName of the customer's request.
     */
    public String getRequestName() {
        return request;
    }


//    private void setSprite() {
//        sprite = GameSprites.getInstance().getSprite(GameSprites.SpriteID.COOK, "UP");
//    }


    // unused abstract methods

    @Override
    public void renderShape(ShapeRenderer shape) {
    }

}