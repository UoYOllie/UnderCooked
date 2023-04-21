package game;

import Shop.Gold;
import Shop.ShopItem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Rectangle;
import cooks.Cook;
import cooks.CustomerControllerNew;
import cooks.CustomerNew;
import customers.Customer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import cooks.GameEntity;
import customers.CustomerController;
import customers.RepPoints;
import helper.*;
import interactions.InputKey;
import interactions.Interactions;
import stations.CookInteractable;
import stations.ServingStation;
import stations.ServingStationNew;
import stations.Station;

import java.util.ArrayList;
import java.util.Comparator;

import static helper.Constants.PPM;

/** A {@link ScreenAdapter} containing certain elements of the game. */
public class GameScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    private int delay;

    private long previousSecond = 0, lastCustomerSecond = 0, nextCustomerSecond = 0;
    private int secondsPassed = 0, minutesPassed = 0, hoursPassed = 0;
    private GameHud gameHud;
    private InstructionHud instructionHUD;
    private SpriteBatch batch;
    private ShapeRenderer shape;
    private ScreenController screenController;
    // private ShapeRenderer shapeRenderer;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    public MapHelper mapHelper;
    //public Array<Station> servingStationNewList;
    private Array<CookInteractable> interactables;
    private CollisionHelper collisionHelper;
    private ArrayList<GameEntity> gameEntities;
    private DrawQueueComparator drawQueueComparator;
    //private Array<ServingStation> servingStations;
    private float xOffset = 1500;
    private float yOffset = 1200;

    public Gold gold;
    public RepPoints Reputation;

    //Objects
    public Array<Cook> unusedcooks;
    public Array<Cook> cooks;
    public Cook cook;

    private int cookIndex;
    private CustomerController customerController;
    private CustomerControllerNew customerControllerNew;
    private int customersToServe;
    public CustomerNew customerTest;
    public Array<CustomerNew> customerTestList;

    private int freeze;

    /**
     * The constructor for the {@link GameScreen}.
     * @param screenController The {@link ScreenController} of the {@link ScreenAdapter}.
     * @param camera The {@link OrthographicCamera} that the game should use.
     */
    public GameScreen(ScreenController screenController, OrthographicCamera camera)
    {
        this.previousSecond = TimeUtils.millis();
        this.lastCustomerSecond = -1;
        this.nextCustomerSecond = -1;
        this.cooks = new Array<>();
        this.unusedcooks = new Array<>();

        this.interactables = new Array<>();
        this.gold = new Gold();
        this.gold.setBalance(1000); //for testing purposes ONLY
        this.Reputation = new RepPoints();
        this.freeze = 0;

        // UPDATE
        // this.collisionHelper = CollisionHelper.getInstance();
        this.collisionHelper = new CollisionHelper();

        this.collisionHelper.setGameScreen(this);
        this.cookIndex = -1;
        this.camera = camera;
        this.camera.zoom = 1/20f;
        this.screenController = screenController;
        this.batch = screenController.getSpriteBatch();
        this.shape = screenController.getShapeRenderer();
        this.gameEntities = new ArrayList<>();
        this.drawQueueComparator = new DrawQueueComparator();
        this.customerController = new CustomerController(this);

        this.world = new World(new Vector2(0,0), false);
        //this.box2DDebugRenderer = new Box2DDebugRenderer();

        // UPDATED
        // this.mapHelper = MapHelper.getInstance();
        this.mapHelper = new MapHelper(this);
        //this.customerControllerNew = new CustomerControllerNew(this);
        //this.servingStationNewList = this.mapHelper.getServingStationNewList();
        //System.out.println(servingStationNewList);


        // this.mapHelper.setGameScreen(this);
        this.orthogonalTiledMapRenderer = mapHelper.setupMap();
        this.gameHud = new GameHud(batch, this);
        this.instructionHUD = new InstructionHud(batch);
        addInteractable(this.mapHelper.getMapStations());
        this.customerControllerNew = new CustomerControllerNew(this);


        Cook GlibbertOrange = new Cook(2041*8f, 2814*8f, 3.34f, 1); //width will need adjusting when sprites updated
        this.addCook(GlibbertOrange);
        Cook GlibbertBlue = new Cook(2045*8f, 2814*8f, 3.34f, 1); //width will need adjusting when sprites updated
        this.addCook(GlibbertBlue);
        Cook GlibbertGreen = new Cook(2049*8f, 2814*8f, 3.34f, 1); //width will need adjusting when sprites updated
        this.addCook(GlibbertGreen);

        Cook Buy1 = new Cook((2031.1f)*8f, 2853*8f, 3.34f, 1); //width will need adjusting when sprites updated
        this.addSpareCook(Buy1);
        Cook Buy2 = new Cook((2031.1f+12f)*8f, 2853*8f, 3.34f, 1); //width will need adjusting when sprites updated
        this.addSpareCook(Buy2);
        Cook Buy3 = new Cook((2031.1f-92f)*8f, 2853*8f, 3.34f, 1); //width will need adjusting when sprites updated
        this.addSpareCook(Buy3);
        Cook Buy4 = new Cook((2031.1f-104f)*8f, 2853*8f, 3.34f, 1); //width will need adjusting when sprites updated
        this.addSpareCook(Buy4);

//        CustomerNew customerTest = new CustomerNew(1995.975f*8f,2855.9087f*8f,3.34f, 1);
//        System.out.println("customer test spawned");
//        System.out.println(customerTest.getX());
//        System.out.println(customerTest.getY());
//        this.gameEntities.add(customerTest);

        this.customerTestList = new Array<>();
//        //CustomerNew customerTest1 = new CustomerNew(2012.0625f, 2855.9087f, 3.34f, 1);
//        CustomerNew customerTest1 = new CustomerNew(1941.5f, 2800.0505f, 3.34f, 1);
//        System.out.println("customer test spawned");
//        System.out.println(customerTest1.getX());
//        System.out.println(customerTest1.getY());
//        System.out.println(customerTest1.getWidth());
//        this.addCustomerNew(customerTest1);
//        this.customerTest = customerTestList.get(0);

        this.addCustomersNew(customerControllerNew.getCustomers());
        //System.out.println(servingStationNewList);

        this.cook = cooks.get(0);
        this.gameEntities.addAll(mapHelper.getMapStations());
    }

    /**
     * Update the game's values, {@link GameEntity}s and so on.
     * @param delta The time between frames as a float.
     */
    public void update(float delta)
    {
		if (Gdx.input.isKeyPressed(Input.Keys.L)){
			System.out.println(this.cook.getX());
			System.out.println(this.cook.getY());
		}

        // First thing, update all inputs
        Interactions.updateKeys();

        long diffInMillis = TimeUtils.timeSinceMillis(previousSecond)-(freeze*1000);
        if (diffInMillis >= 1000) {
            for(CustomerNew customer:customerTestList) //Dealing with leaving
            {
                customer.DecreasePatience();
                if((customer.waittime<=0)&&(customer.Stillhere ==true))
                {
                    System.out.println(customer + " is now leaving");
                    customer.StormOut(); //When customer wants to leave
                }
            }
            previousSecond += 1000;
            secondsPassed += 1;
            if (secondsPassed >= 60) {
                secondsPassed = 0;
                minutesPassed += 1;
                if (minutesPassed >= 60) {
                    minutesPassed = 0;
                    hoursPassed += 1;
                }
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.EQUALS)){
            if (camera.zoom >= 1/20f){
                camera.zoom -= 1/100f;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.MINUS)){
            if (camera.zoom <= 0.5f) {
                camera.zoom += 1/100f;
            }
        }
        gameHud.updateTime(hoursPassed, minutesPassed, secondsPassed);
        cameraUpdate();
        orthogonalTiledMapRenderer.setView(camera);
        batch.setProjectionMatrix(camera.combined);
        shape.setProjectionMatrix(camera.combined);
        for (Cook thisCook : cooks) {
            if (thisCook == cook) {
                thisCook.userInput(mapHelper.getMapObstacles());
                thisCook.userInteract(mapHelper.getMapStations());
            }
        }
        // laura
        for (CustomerNew customer : customerTestList) {
            customer.customerInteract(mapHelper.getMapStations());
        }

        if(Interactions.isJustPressed(InputKey.InputTypes.COOK_SWAP)) {
            setCook((cookIndex + 1) % cooks.size);
        }
        this.cook.update(Gdx.graphics.getDeltaTime());

        // Spawning code to spawn a customer after an amount of time.
        if(TimeUtils.millis() >= nextCustomerSecond)
        {
            int recipeComplexity = customerController.addCustomer();
            if (recipeComplexity == -1) {
                // If customer couldn't be added, then wait 2 seconds.
                nextCustomerSecond += 2000;
            } else {
                // Wait longer if the recipe has more steps.
                lastCustomerSecond = TimeUtils.millis();
                nextCustomerSecond += 1000 * Math.floor(9 + 5.4F * Math.log(recipeComplexity - 0.7));
            }
            //System.out.println("i just spawned a customer");
        }

        if(Interactions.isJustPressed(InputKey.InputTypes.PAUSE))
        {
            System.out.println("Im Pausing");
            screenController.pauseGameScreen();
        }
        world.step(1/60f,6,2);
        for (GameEntity entity : gameEntities) {
            entity.update(delta);
        }
    }

    /**
     * Update the {@link #camera}.
     */
    private void cameraUpdate()
    {
        camera.position.set(new Vector3(this.cook.getX(),this.cook.getY(),0));

        camera.update();
    }

    /**
     * The next frame of the game.
     * @param delta The time between frames as a float.
     */
    @Override
    public void render(float delta)
    {

        this.update(delta);

        renderGame(delta);

        if(customersToServe <= customerController.getCustomersServed())
        {
            screenController.setScreen((ScreenController.ScreenID.GAMEOVER));
            ((GameOverScreen) screenController.getScreen(ScreenController.ScreenID.GAMEOVER)).setTime(hoursPassed,minutesPassed,secondsPassed);
        }
    }

    /**
     * Render the {@link GameScreen}. It is a separate function to
     * allow rendering of the game from the {@link PauseScreen}.
     * @param delta The time between frames as a float.
     */
    public void renderGame(float delta) {

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        orthogonalTiledMapRenderer.render();
        batch.begin();

        gameEntities.sort(drawQueueComparator);

        for (GameEntity entity : gameEntities) {
            entity.render(batch);
            if (entity == cook) {
                ((Cook) entity).renderControlArrow(batch);
            }
            //entity.renderDebug(batch);
        }

        batch.end();

        shape.begin(ShapeRenderer.ShapeType.Filled);

        for (GameEntity entity : gameEntities) {
            entity.renderShape(shape);
            // entity.renderShapeDebug(shape);
        }

        shape.end();
        //box2DDebugRenderer.render(world, camera.combined.scl(PPM));

        gameHud.render();
        instructionHUD.render();

    }

    /**
     * A {@link Comparator} used to compare the Y height of two
     * {@link GameEntity}s.
     * If it is negative, then the left entity is higher.
     * If it is positive, then the right entity is higher.
     * If it is 0, then both are at the same height.
     */
    public class DrawQueueComparator implements Comparator<GameEntity> {

        @Override
        public int compare(GameEntity o1, GameEntity o2) {
            float o1Y = o1.getY(),
                  o2Y = o2.getY();
            if (o1Y > o2Y) {
                return -1;
            } else if (o2Y > o1Y) {
                return 1;
            }
            return 0;
        }
    }

    /**
     * Get the world that the game is using.
     * @return {@link World} : The {@link GameScreen}'s {@link World}.
     */
    public World getWorld()
    {
        return world;
    }

    /**
     * Sets the currently active {@link #cook} that the game is using.
     * @param cookIndex The index of {@link #cook} in the {@link #cooks} array.
     * @return {@link Cook} : The {@link Cook} that the game has swapped to.
     */
    public Cook setCook(int cookIndex)
    {
        if (cookIndex < 0 || cookIndex > cooks.size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.cook = cooks.get(cookIndex);
        this.cookIndex = cookIndex;
        return this.cook;
    }

    public void addCustomersNew(Array<CustomerNew> customers) {

        for (CustomerNew customer : customers) {
            gameEntities.add(customer);
            customerTestList.add(customer);
        }
    }

    /**
     * Adds a new {@link Cook} to the {@link #cooks} {@link Array} for the game to swap between.
     * @param newCook The {@link Cook} to be added to the {@link Array}.
     * @return {@code int} : The index of the new cook in the cooks array.
     */
    public int addCook(Cook newCook) {
        gameEntities.add(newCook);
        cooks.add(newCook);
        return cooks.size-1;
    }
    public int addSpareCook(Cook newCook) {
        gameEntities.add(newCook);
        unusedcooks.add(newCook);
        return cooks.size-1;
    }

    public void SpareToNotSpare(int n)
    {
        int index = n-1;
        Cook newcook = unusedcooks.get(index);
        Rectangle newPlayerRectangle = new Rectangle(newcook.x, newcook.y-10, newcook.width, newcook.height);
        newcook.rectangle = newPlayerRectangle;
        newcook.x = newcook.rectangle.x;
        newcook.y = newcook.rectangle.y;
        cooks.add(newcook);

    }


    /**
     * Updates the {@link GameHud} with the correct number of {@link Customer}s.
     * @param customerCount The {@code int} number to set the number of
     *                      {@link Customer}s to.
     */
    public void setCustomerHud(int customerCount) {
        gameHud.setCustomerCount(customersToServe - customerCount);
    }

    /**
     * Returns the number of customers remaining before the game is finished.
     * @return {@code int} : The value of {@link CustomerController#getCustomersLeft()}.
     */
    public int getCustomerCount() {
        return customerController.getCustomersLeft();
    }

    /**
     * A getter to get the {@link #previousSecond}.
     * <br>The {@link #previousSecond} is used for the timer, by checking when the previous
     * second was so that the game can check if it has been another second or not.
     * @return {@code long} : The {@link #previousSecond}.
     */
    public long getPreviousSecond() {
        return previousSecond;
    }

    /**
     * A setter to set the {@link #previousSecond} to the {@code long} provided.
     * @param newSecond What to set the {@link #previousSecond} to as a {@code long}.
     */
    public void setPreviousSecond(long newSecond) {
        previousSecond = newSecond;
    }

    /**
     * A getter to get the {@link #nextCustomerSecond}.
     * <br>The {@link #nextCustomerSecond} is used for spawning the
     * {@link Customer}s after a short delay.
     * @return {@code long} : The {@link #nextCustomerSecond}.
     */
    public long getNextCustomerSecond() {
        return nextCustomerSecond;
    }

    /**
     * A setter to set the {@link #nextCustomerSecond} to the {@code long} provided.
     * @param newSecond What to set the {@link #nextCustomerSecond} to as a {@code long}.
     */
    public void setNextCustomerSecond(long newSecond) {
        nextCustomerSecond = newSecond;
    }

    /**
     * {@link #interactables} getter. Contains all the {@link #interactables} in the {@link GameScreen}.
     * @return {@link Array}&lt;{@link CookInteractable}&gt; : {@link #interactables}.
     */
    public Array<CookInteractable> getInteractables() {
        return interactables;
    }

    /**
     * Adds a {@link CookInteractable} that a {@link Cook} can interact with to {@link #interactables}
     *                         should be able to interact with.
     */
    public void addInteractable(ArrayList<Station> stations) {
        for (Station s : stations) {
            interactables.add(s);
        }
    }

    /**
     * Adds a game entity to the GameScreen to be rendered and updated.
     * @param entity The {@link GameEntity} to be added.
     */
    public void addGameEntity(GameEntity entity) {
        gameEntities.add(entity);
    }

    /**
     * Intermediate function to allow the {@link MapHelper} to add
     * the {@link ServingStation}s to the {@link CustomerController}.
     * @param station The {@link ServingStation} to add to the {@link CustomerController}.
     */
    public void addServingStation(ServingStation station) { customerController.addServingStation(station); }
    /** Reset the game variables, map and world. */
    public void reset() {
        // Reset all variables
        secondsPassed = 0;
        minutesPassed = 0;
        hoursPassed = 0;
        cooks.clear();
        gameEntities.clear();
        interactables.clear();
        mapHelper.dispose();
        customerController.clearServingStations();
        dispose();
        // UPDATE
        //mapHelper = MapHelper.newInstance();
        //mapHelper.setGameScreen(this);
        this.mapHelper = new MapHelper(this);

        world.dispose();
        this.world = new World(new Vector2(0,0), false);
        this.orthogonalTiledMapRenderer = mapHelper.setupMap();
        cookIndex = -1;
    }

    /**
     * A variable for setting up the game when it starts.
     * @param customers The number of customers that need to be
     *                  served in the game to finish.
     */
    public void startGame(int customers) {
        secondsPassed = 0;
        minutesPassed = 0;
        hoursPassed = 0;

        previousSecond = TimeUtils.millis();
        lastCustomerSecond = TimeUtils.millis();
        nextCustomerSecond = TimeUtils.millis()+2000;

        gameHud.setRecipe(null);
        customersToServe = customers;
        customerController.setCustomersLeft(customers);
        customerController.setCustomersServed(0);
        customerController.addCustomer();
        setCustomerHud(customers);
        gameHud.setCustomerCount(customers);
    }

    /**
     * A getter for the {@link CustomerController} of the
     * game.
     * @return {@link CustomerController} : The {@link CustomerController}
     *                                      for the game.
     */
    public CustomerController getCustomerController() {
        return this.customerController;
    }

    /**
     * Getter to get the {@link GameHud}.
     * @return {@link GameHud} : The game's {@link GameHud}.
     */
    public GameHud getGameHud() {
        return gameHud;
    }
    public InstructionHud getInstructionHUD() {
        return instructionHUD;
    }

    public CollisionHelper getCollisionHelper() {
        return collisionHelper;
    }

    //-------------------------------------
    //Morgan's Shop Section
    //-------------------------------------
    //public Gold gold;

    //These are all the powerups:
    public ShopItem Powerup_Speed = new ShopItem("Speed",30); //increase current chef's movement seed
    public ShopItem Powerup_Items = new ShopItem("PowerItem",50);//give user a cup of tea, place onto stack
    public ShopItem Powerup_ChefBluggusMode = new ShopItem("Bluggus",80); //Transforms chef to bluggus to have bonus stack.
    public ShopItem timefreeze = new ShopItem("time",100);
//    public ShopItem Powerup_5 = new ShopItem("-",30);
    public ShopItem BuyablePeople = new ShopItem("NewChef",25);

    public ShopItem BuyableStation = new ShopItem("Station",10);

    public void FreezeTime()
    {
        this.freeze = this.freeze + 10;
        System.out.println("time deduction is "+this.freeze);
    }



    //-------------------------------------
}


