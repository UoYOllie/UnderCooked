package cooks;

//import Shop.MindControl;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
    public Sprite bubbleSprite;

    /** The name of the recipe being requested. */

    public boolean Stillhere;
    public float waittime;

    public DishStack dishStack;
    public String request;
    public Rectangle customerInteractor;

    private GameScreen gameScreen;
    public Vector2 stationPosition;
    public Vector2 destination;
    private int customerStatus; //SAVE
    private int difficulty;
    public int entryStatus;
    private Array<Vector2> customerPoints;
    public boolean customerToTest;

    public String offtopoint;
    private boolean readyX = false;
    private boolean readyY = false;


    /** The Constructor for CustomerNew. */
    public CustomerNew(float x, float y, float width, float height) {
        super(x, y, width, height);//
        String offtopoint = "A";
        this.sprite = GameSprites.getInstance().getSprite(GameSprites.SpriteID.CUSTOMER, "customer_bluggus");
        this.bubbleSprite = GameSprites.getInstance().getSprite(GameSprites.SpriteID.CUSTOMER, "speech_bubble");
        //this.position = new Vector2(x, y);
        this.customerInteractor = new Rectangle(x - 4 * Constants.UnitScale, y - 4 * Constants.UnitScale,
                                                width + 1f, height + 1f);
        this.request = Recipe.randomRecipe();
        this.dishStack = new DishStack();
        this.customerStatus = 0;
        this.customerToTest = false;

        this.waittime = 200;
        this.Stillhere = true;

        this.x = x;
        this.y = y;

        // used for customer movement:
        this.stationPosition = new Vector2(x, y);
        moveToPoint("B");
        this.destination = Constants.customerPointB;
        this.entryStatus = 0;
        this.customerPoints = setCustomerPoints();

    }

    public void setRequest(String x)
    {
        this.request = x;
    }

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

    public void setStationPosition(float endX, float endY) {
        this.stationPosition.x = endX;
        this.stationPosition.y = endY;
    }

    public void setDestination(float endX, float endY) {
        //System.out.println("i am going to: " + endX + "," + endY);
        this.destination.x = endX;
        this.destination.y = endY;
    }

    public void setGameScreen(GameScreen g)
    {
        this.gameScreen = g;
    }

    public int getCustomerStatus() {return this.customerStatus;}

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
    public void Success(Station station) //Gets their dish, this is called
    {
        this.gameScreen.getCustomerController().removeCustomer(station);
        gameScreen.getCustomerController().TotalCustomersServed++;
        this.gameScreen.Reputation.Positive();
        this.gameScreen.gold.addBalance(50);
        Leave();
    }

    private void Leave() //This function will be called when a customer leaves
    {
        this.Stillhere = false;
        System.out.println("Im leaving, bye");
        this.customerStatus = 2;
        //Implement walk off
        //implement removement from array
        //gameScreen.getCustomerController().TotalCustomersServed++;
    }

    public void customerInteract(ArrayList<Station> mapStations) {
        for (Station currentStation : mapStations) {
            if (Intersector.overlaps(this.customerInteractor, currentStation.rectangle)){
                currentStation.customerInteract(this);
            }
        }
    }

    // all methods controlling customer movement

    public Array<Vector2> setCustomerPoints() {
        Array<Vector2> customerPoints = new Array<>();
        customerPoints.add(Constants.customerPointC);
        customerPoints.add(Constants.customerPointD);
        customerPoints.add(Constants.customerPointE);
        customerPoints.add(Constants.customerPointF);
        customerPoints.add(stationPosition);
        return customerPoints;
    }

    public void move_left_down(Vector2 nextDestination) {

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

    public void enterCustomer() {

//        for (int i=0; i<5; i++) {
//            if (this.entryStatus == i) {
//                System.out.println(this + " moving to " + customerPoints.get(i));
//                move_left_down(customerPoints.get(i));
//            }
//        }

        if (this.entryStatus == 0) {
            if (this.customerToTest == true) {
                //System.out.println("entry status 0 moving to point B" + destination.x + "," + destination.y);
            }
            moveToPoint("C");
            setDestination(Constants.customerPointC.x,Constants.customerPointC.y);
            move_left_down(Constants.customerPointC);
        }
        if (this.entryStatus == 1) {
            if (this.customerToTest == true) {
               // System.out.println(this + "entry status 1 moving to point C");
            }
            moveToPoint("D");
            setDestination(Constants.customerPointD.x,Constants.customerPointD.y);
            move_left_down(Constants.customerPointD);
        }
        if (this.entryStatus == 2) {
            if (this.customerToTest == true) {
            //    System.out.println("entry status 2 moving to point D");
            }
            moveToPoint("E");
            setDestination(Constants.customerPointE.x,Constants.customerPointE.y);
            move_left_down(Constants.customerPointE);
        }
        if (this.entryStatus == 3) {
            if (this.customerToTest == true) {
            //    System.out.println("entry status 3 moving to point E");
            }
            moveToPoint("F");
            setDestination(Constants.customerPointF.x,Constants.customerPointF.y);
            move_left_down(Constants.customerPointF);
        }
        if (this.entryStatus == 4) {
            if (this.customerToTest == true) {
            //    System.out.println("entry status 4 moving to point F");
            }
            setDestination(stationPosition.x,stationPosition.y);
            move_left_down(stationPosition);
        }

        if (this.entryStatus == 5) {
            if (this.customerToTest == true) {
              //  System.out.println("entry status 5 moving to station" + this.station);
            }

            this.readyX = false;
            this.readyY = false;

            if (this.y > stationPosition.y) { this.y -= Constants.UnitScale;
            } else { this.readyY = true; }

            if (this.x < stationPosition.x) { this.x += Constants.UnitScale;
            } else { this.readyX = true;}

            if (this.readyX && this.readyY) {
                destination.x = this.x - 88f;
                this.customerStatus += 1;
                this.entryStatus += 1;
            }
        }
    }

    public void move_right_up(Vector2 nextDestination) {

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

    public void servedCustomerLeaves() {

//        if (this.x > destination.x) { this.x -= Constants.UnitScale;
//        } else {
//            this.customerStatus += 1;
//        }
        System.out.println("Entry status "+entryStatus);
        if(entryStatus==6) {
            moveToPoint("F Hybrid");
            setDestination(stationPosition.x-30f,stationPosition.y);
            move_left_down(new Vector2(stationPosition.x-30f,stationPosition.y));
        }
        if(entryStatus==7) {
            this.entryStatus=4;
        }
        if (this.entryStatus == 1) {
            if (this.customerToTest == true) {
                //System.out.println("entry status 0 moving to point B" + destination.x + "," + destination.y);
            }
            moveToPoint("C");
            setDestination(Constants.customerPointC.x,Constants.customerPointC.y);
            move_right_up(Constants.customerPointC);
        }
        if (this.entryStatus == 2) {
            if (this.customerToTest == true) {
                // System.out.println(this + "entry status 1 moving to point C");
            }
            moveToPoint("D");
            setDestination(Constants.customerPointD.x,Constants.customerPointD.y);
            move_right_up(Constants.customerPointD);
        }
        if (this.entryStatus == 3) {
            if (this.customerToTest == true) {
                //    System.out.println("entry status 2 moving to point D");
            }
            moveToPoint("E");
            setDestination(Constants.customerPointE.x,Constants.customerPointE.y);
            move_right_up(Constants.customerPointE);
        }
        if (this.entryStatus == 4) {
            if (this.customerToTest == true) {
                //    System.out.println("entry status 3 moving to point E");
            }
            moveToPoint("F");
            setDestination(Constants.customerPointF.x,Constants.customerPointF.y);
            move_right_up(Constants.customerPointF);
        }
//        if (this.entryStatus == 5) {
//            if (this.customerToTest == true) {
//                //    System.out.println("entry status 4 moving to point F");
//            }
//            setDestination(stationPosition.x,stationPosition.y);
//            move_right_up(stationPosition);
//        }

        if (this.entryStatus == 0) {
            if (this.customerToTest == true) {
                //  System.out.println("entry status 5 moving to station" + this.station);
            }
            moveToPoint("A");
            setDestination(Constants.customerPointA.x,Constants.customerPointA.y);
            move_right_up(Constants.customerPointA);
        }
        if (this.entryStatus == -1) {
            if (this.customerToTest == true) {
                //  System.out.println("entry status 5 moving to station" + this.station);
            }
            moveToPoint("DELETION ZONE");
//            setDestination(Constants.customerPointA.x+3f,Constants.customerPointA.y+12f);
            setDestination(Constants.customerPointA.x,Constants.customerPointA.y);
            move_right_up(Constants.customerPointA);
        }
        if (this.entryStatus == -2) {
            gameScreen.gameEntities.remove(this);
        }

    }

    @Override
    public void update(float delta) {

        switch (customerStatus) {
            case (0): // Customer is coming down the corridor.
                enterCustomer();
                break;
            case (1): // Customer is waiting at the serving station.
                //System.out.println("I am ready to be served now!");
                break;
            case (2): // Customer goes to eat in the dining area.
                //System.out.println("I have been served now!");
                servedCustomerLeaves();
                break;
            case (3): // Customer is eating in the dining area.
                servedCustomerLeaves();
                //System.out.println("i am eating now yay");
                break;
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

        //sprite = GameSprites.getInstance().getSprite(GameSprites.SpriteID.CUSTOMER, "customer_bluggus");
        //bubbleSprite = GameSprites.getInstance().getSprite(GameSprites.SpriteID.CUSTOMER, "speech_bubble");

        //sprite.setPosition(position.x-sprite.getWidth()/2, position.y-sprite.getHeight()/2);
        sprite.setPosition(this.x-sprite.getWidth()/2, this.y-sprite.getHeight()/2);
        this.sprite.setSize(6,5.7f);
        //bubbleSprite.setPosition(this.x-8.5f, this.y-bubbleSprite.getHeight()/2);
        //this.bubbleSprite.setSize(6,5.7f);

        sprite.draw(batch);
        //bubbleSprite.draw(batch);
        renderFood(batch);
        renderBubble(batch);
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

    /**
     * @param x
     * @return the x value
     */
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
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

    public int getStatus()
    {
        return this.customerStatus;
    }

    public void setStatus(int x){
        this.customerStatus = x;
    }

    public void setCustomerStatus(Integer customerStatus){
        this.customerStatus = customerStatus;
    }

    public void moveToPoint(String x)
    {
        System.out.println(this+" moving to point "+x+"------"+this.destination+"------ ("+this.x+","+this.y+")");
        this.offtopoint = x;
    }

}