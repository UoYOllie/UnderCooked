package players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Random;

import food.FoodItem;
import food.Recipe;
import food.DishStack;
import stations.Station;
import game.GameScreen;
import game.GameSprites;
import helper.Constants;

/**
 * A Customer requests a dish to be served to them by the cook.
 * They have a patience timer, and will storm out if this runs out
 * before they are served.
 */
public class Customer extends GameEntity {

    /** The current instance of gameScreen.*/
    private GameScreen gameScreen;

    /** The difficulty of the current game being played. */
    private int difficulty;

    /** The Sprite of the Customer.*/
    public Sprite sprite;

    /** The Sprite of the Customer's request bubble displaying their request.*/
    public Sprite bubbleSprite;

    /** The name of the recipe being requested.*/
    public String request;

    /** The customer's dishStack holds the dish they have been correctly served.*/
    public DishStack dishStack;

    /** The customer's interaction rectangle detects stations to interact with.*/
    public Rectangle customerInteractor;

    /** The position of the station the customer will go to.*/
    public Vector2 stationPosition;

    /** The position the customer is currently travelling towards.*/
    public Vector2 destination;

    /** The status corresponds to a stage of the customer's cycle (entering, waiting, etc.)*/
    private int customerStatus;

    /** The entry status corresponds to a stage of the customer's entrance down the corridor.*/
    public int entryStatus;

    /** Boolean stating whether a customer has reached the destination x-position.*/
    private boolean readyX = false;

    /** Boolean stating whether a customer has reached the destination y-position.*/
    private boolean readyY = false;

    /** The impatience timer of the customer, set to a random value within the difficulty's range.*/
    public float waittime;

    /** The Constructor for Customer. */
    public Customer(float x, float y, float width, float height) {

        super(x, y, width, height);
        this.x = x; this.y = y;
        this.customerInteractor = new Rectangle(x - 4 * Constants.UnitScale, (y) - 4 * Constants.UnitScale,
                width + 1f, height+1f);

        this.sprite = GameSprites.getInstance().getSprite(GameSprites.SpriteID.CUSTOMER, "customer_bluggus");
        this.bubbleSprite = GameSprites.getInstance().getSprite(GameSprites.SpriteID.CUSTOMER, "speech_bubble");

        this.request = Recipe.randomRecipe();
        this.dishStack = new DishStack();

        this.customerStatus = 0;
        this.entryStatus = 0;
        this.stationPosition = new Vector2(x, y);
        this.destination = Constants.customerPointB;

        this.waittime = 100f;
    }

    /** Setter for customer's GameScreen.
     * @param g the current instance of gameScreen.*/
    public void setGameScreen(GameScreen g) { this.gameScreen = g; }

    /** Setter for the customer's x-position.*/
    public void setX(float x) {
        this.x = x;
    }

    /** Setter for the customer's y-position.*/
    public void setY(float y) {
        this.y = y;
    }

    /** Getter for the x-position of Customer.*/
    public float getX() { return x; }

    /** Getter for the y-position of Customer.*/
    public float getY() { return y; }

    /** Setter for the customer's status.*/
    public void setCustomerStatus(Integer customerStatus){
        this.customerStatus = customerStatus;
    }

    /** Getter for customer's status.*/
    public int getCustomerStatus() {return this.customerStatus;}

    /** Getter to get the name of the request of the Customer.*/
    public String getRequestName() { return request; }

    /** Setter for customer's destined station position.
     *
     * @param endX the x-position of the customer's destined station.
     * @param endY the y-position of the customer's destined station.
     * */
    public void setStationPosition(float endX, float endY) {
        this.stationPosition.x = endX;
        this.stationPosition.y = endY;
    }

    /** Setter for customer's destination.
     *
     * @param endX the final x-position to reach.
     * @param endY the final y-position to reach.
     * */
    public void setDestination(float endX, float endY) {
        this.destination.x = endX;
        this.destination.y = endY;
    }

    /**
     * Setter for difficulty, additionally sets waittime to correspond to difficulty.
     * Waittime sets to random float in range 200-300 (hard), 250-350 (medium), 300-400 (easy).
     *
     * @param difficulty The difficulty level of the game may be 1 (easy), 2 (medium), or 3 (hard).
     * */
    public void setDifficulty(int difficulty) {

        this.difficulty = difficulty;
        Random rd = new Random();
        this.waittime += rd.nextFloat()*100;

        if (this.difficulty == 1) {
            this.waittime += 100;
        }
        if (this.difficulty == 2) {
            this.waittime += 50;
        }
    }

    /** Decrease the customer's patience by 1 second in each frame.*/
    public void DecreasePatience(){
        this.waittime = waittime-1f;
    }

    /**
     * Called after the customer is successfully served their request.
     * Update customerStatus to 2 (leaving).
     * Gain a reputation point and 50 gold.
     * Remove the customer from stationCustomerMap in customerController.
     *
     * @param station The station the customer was served at.
     * */
    public void Success(Station station) {
        this.customerStatus = 2;
        this.gameScreen.Reputation.Positive();
        this.gameScreen.gold.addBalance(50);
        this.gameScreen.getCustomerController().removeCustomer(station);
        gameScreen.getCustomerController().TotalCustomersServed++;
    }

    /**
     * Called after the customer runs out of patience.
     * Update the customer's status to 2 (leaving) and Stillhere is set to false.
     * Lose a reputation point in gameScreen.
     * */
    public void StormOut() {
        this.customerStatus = 2;
        this.gameScreen.Reputation.Negative();
    }

    /**
     * The customer's interaction method, continually called by GameScreen in each frame.
     * If the customer is interacting with a serving station, it will call the serving station's
     * customer interaction method.
     *
     * @param mapStations The ArrayList of all the stations in the game.
     * */
    public void customerInteract(ArrayList<Station> mapStations) {
        for (Station currentStation : mapStations) {
            if (Intersector.overlaps(this.customerInteractor, currentStation.rectangle)){
                currentStation.customerInteract(this);
            }
        }
    }

    /** Power up method to change a customer's mind about what to order.*/
    public void Hypnotise() {
        String temp = Recipe.randomRecipe();
        if(this.request != temp) {
            this.request = temp;
        } else {
            Hypnotise();
        }
    }

    /** Power up method to extend a customer's patience timer after giving them tea.*/
    public void HangOnYourFoodIsComing() {
        switch (difficulty) {
            case (1):
                this.waittime = 400;
                break;
            case (2):
                this.waittime = 350;
                break;
            case (3):
                this.waittime = 300;
        }
    }

    /**
     * Helper method for a customer to move diagonally left and down from their
     * current position, used when they are entering the restaurant.
     *
     * @param nextDestination The destination to set after the customer reaches
     *                        their current destination.
     * */
    public void moveLeftDown(Vector2 nextDestination) {

        this.readyX = false;
        this.readyY = false;

        if (this.y > this.destination.y) { this.y -= Constants.UnitScale;
        } else { this.readyY = true; }

        if (this.x > this.destination.x) { this.x -= Constants.UnitScale;
        } else { this.readyX = true; }

        if (this.readyX && this.readyY) {
            setDestination(nextDestination.x, nextDestination.y);
            this.entryStatus += 1;
        }
    }

    /** Helper method for a customer to move diagonally right and up from their
     * current position, used when they are exiting the restaurant.
     *
     * @param nextDestination The destination to set after the customer reaches
     *                        their current destination.
     * */
    public void moveRightUp(Vector2 nextDestination) {

        this.readyX = false;
        this.readyY = false;

        if (this.x < this.destination.x) { this.x += Constants.UnitScale;
        } else { this.readyX = true; }

        if (this.y < this.destination.y) { this.y += Constants.UnitScale;
        } else { this.readyY = true; }


        if (this.readyX && this.readyY) {
            setDestination(nextDestination.x, nextDestination.y);
            this.entryStatus -= 1;
        }
    }

    /**
     * Entrance code for customer, continually called while customer enters (customerStatus = 0).
     * Moves the customer left and down along set points of the corridor. 
     * */
    public void enterCustomer() {

        switch (entryStatus) {
            case(0):
                setDestination(Constants.customerPointC.x,Constants.customerPointC.y);
                moveLeftDown(Constants.customerPointC);
                break;
            case (1):
                setDestination(Constants.customerPointD.x,Constants.customerPointD.y);
                moveLeftDown(Constants.customerPointD);
                break;
            case (2):
                setDestination(Constants.customerPointE.x,Constants.customerPointE.y);
                moveLeftDown(Constants.customerPointE);
                break;
            case (3):
                setDestination(Constants.customerPointF.x,Constants.customerPointF.y);
                moveLeftDown(Constants.customerPointF);
                break;
            case (4):
                setDestination(stationPosition.x,stationPosition.y+3f);
                moveLeftDown(new Vector2(stationPosition.x,stationPosition.y+3f));
                break;
            case (5):
                this.readyX = false;
                this.readyY = false;

                if (this.y > stationPosition.y+3f) { this.y -= Constants.UnitScale;
                } else { this.readyY = true; }

                if (this.x < stationPosition.x) { this.x += Constants.UnitScale;
                } else { this.readyX = true;}

                if (this.readyX && this.readyY) {
                    destination.x = this.x - 88f;
                    this.customerStatus += 1;
                    this.entryStatus += 1;
                }
                break;
        }
    }

    /**
     * Exit code for customer, continually called while customer leaves (customerStatus = 2).
     * Moves the customer right and up along set points of the corridor. 
     * */
    public void exitCustomer() {

        switch (entryStatus) {
            case (7):
                System.out.println("i am stuck at 7");
                this.entryStatus=4;
            case(6):
                System.out.println("i am stuck at 6");
                setDestination(stationPosition.x-30f,stationPosition.y);
                moveLeftDown(new Vector2(stationPosition.x-30f,stationPosition.y));
                break;
            case(5):
                this.entryStatus -= 1;
                System.out.println("i am stuck at 5");
                break;
            case(4):
                System.out.println("i am stuck at 4");
                setDestination(Constants.customerPointF.x,Constants.customerPointF.y);
                moveRightUp(Constants.customerPointF);
                break;
            case(3):
                System.out.println("i am stuck at 3");
                setDestination(Constants.customerPointE.x,Constants.customerPointE.y);
                moveRightUp(Constants.customerPointE);
                break;
            case(2):
                System.out.println("i am stuck at 2");
                setDestination(Constants.customerPointD.x,Constants.customerPointD.y);
                moveRightUp(Constants.customerPointD);
                break;
            case(1):
                System.out.println("i am stuck at 1");
                setDestination(Constants.customerPointC.x,Constants.customerPointC.y);
                moveRightUp(Constants.customerPointC);
                break;
            case(0):
                System.out.println("i am stuck at 0");
                setDestination(Constants.customerPointA.x,Constants.customerPointA.y);
                moveRightUp(Constants.customerPointA);
                break;
            case (-1):
                System.out.println("i am stuck at -1");
                setDestination(Constants.customerPointA.x+3,Constants.customerPointA.y+12f);
                moveRightUp(new Vector2(Constants.customerPointA.x+3,Constants.customerPointA.y+12f));
            case (-2):
                System.out.println("i am stuck at -2");
                gameScreen.letsremove.add(this);
                break;
        }
    }

    /**
     * Update method to be called in each frame by gameScreen.
     * Action depends on customerStatus, will be 0 (entering), 1 (waiting), or 2 (leaving).
     * @param delta The time between frames as a float.
     * */
    @Override
    public void update(float delta) {

        switch (customerStatus) {
            case (0): // Customer is coming down the corridor.
                enterCustomer();
                break;
            case (1): // Customer is waiting at the serving station.
                break;
            case (2): // Customer goes to eat in the dining area.
                exitCustomer();
                break;
        }

        // Updates Interaction box (again change 1/8f to a const)
        this.customerInteractor.x = x - Constants.UnitScale;
        this.customerInteractor.y = y - Constants.UnitScale;
    }

    /**
     * Renders the Customer at their position.
     * @param batch The SpriteBatch to be rendered.
     */
    public void render(SpriteBatch batch) {

        // Render the customer's sprite.
        sprite.setPosition(this.x-sprite.getWidth()/2, this.y-sprite.getHeight()/2);
        this.sprite.setSize(6,5.7f);
        sprite.draw(batch);

        // Render the customer's request and dishStack.
        renderFood(batch);
        renderBubble(batch);
    }

    /**
     * Renders the dishStack of the customer onto them.
     * @param batch The SpriteBatch to render.
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

    /** Render the customer's request as a bubble while they wait.
     * Will render the plain item to the left, followed by all toppings. */
    private void renderBubble(SpriteBatch batch) {

        if (customerStatus == 1) {

            bubbleSprite.setPosition(this.x - 8.5f, this.y - bubbleSprite.getHeight() / 2);
            // 75, 30
            this.bubbleSprite.setSize(7.5f, 4f);
            bubbleSprite.draw(batch);

            Array<FoodItem.FoodID> requestList = Recipe.getCustomerRequestBubble(request);
            float xOffset = -8.5f, yOffset = +15F;
            float drawX = x, drawY = y;

            // Draw each FoodItem in RequestList.
            for (int i = 0; i < requestList.size; i++) {
                Sprite foodSprite = GameSprites.getInstance().getSprite(GameSprites.SpriteID.FOOD, String.valueOf(requestList.get(i)));
                foodSprite.setScale(0.8f * Constants.UnitScale);
                foodSprite.setPosition((drawX - foodSprite.getWidth() / 3 + xOffset - 3.5f * Constants.UnitScale) - 1.15f,
                        (drawY - foodSprite.getHeight() * 0.33f + yOffset * Constants.UnitScale) - 4.3f);
                foodSprite.draw(batch);
                drawX += 1.7f;
            }

        }

    }

    /** Render the customer's impatience timer as a rectangle on the bubble while they wait.
     * @param shape the ShapeRenderer object to render.
     * */
    @Override
    public void renderShape(ShapeRenderer shape) {

        // Render the progress bar when inUse
        if (customerStatus == 1) {
            float rectX = x - 8f,
                    rectY = y - 1.6f,
                    rectWidth = 1f,
                    rectHeight = 10 * Constants.UnitScale;
            if (difficulty==1) {
                rectWidth = 8f;
                rectX = x - 8f;
            }
            else if (difficulty==2) {
                rectWidth = 7f;
                rectX = x - 7.7f;
            }
            else {
                rectWidth = 6f;
                rectX = x - 7.4f;
            }

            // Black bar behind
            shape.rect(rectX, rectY, rectWidth*0.75f, rectHeight*0.75f, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);
            // Now the progress bar.
            float progressWidth = (waittime*0.02f) -2f * Constants.UnitScale;
            Color progressColor = Color.FOREST;
            if (100 < this.waittime && this.waittime <= 200) {
                progressColor = Color.YELLOW;
            } if (0 < this.waittime && this.waittime <= 100) {
                progressColor = Color.ORANGE;
            } if (this.waittime == 0) {
                progressColor = Color.RED;
            }
            shape.rect(rectX+ 1.5f * Constants.UnitScale,rectY + 1.5f * Constants.UnitScale, progressWidth*0.75f,(rectHeight - 4 * Constants.UnitScale)*0.75f,progressColor,progressColor,progressColor,progressColor);
        }
    }

}