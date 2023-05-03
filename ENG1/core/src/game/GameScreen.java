package game;

import shop.Gold;
import shop.ShopItem;
import com.badlogic.gdx.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.*;
import players.Cook;
import players.CustomerController;
import players.Customer;
import java.nio.file.Files;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import players.GameEntity;
import reputation.RepPoints;
import food.FoodItem;
import food.FoodStack;
import helper.*;
import interactions.InputKey;
import interactions.Interactions;
import stations.CookInteractable;

import stations.Station;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

/** A {@link ScreenAdapter} containing certain elements of the game. */
public class GameScreen extends ScreenAdapter {
    public Array<Customer> letsremove;
    private String filepath;
    private int holdzoomcounter;
    private OrthographicCamera camera;

    private long previousSecond = 0;
    public int secondsPassed = 0, minutesPassed = 0, hoursPassed = 0;
    private GameHud gameHud;
    private InstructionHud instructionHUD;
    private SpriteBatch batch;
    private ShapeRenderer shape;
    private ScreenController screenController;
    private World world;
    Vector3 posCamera;

    private SuperMapSuperRenderer orthogonalTiledMapRenderer;
    public MapHelper mapHelper;
    //public Array<Station> servingStationNewList;
    private Array<CookInteractable> interactables;
    public ArrayList<GameEntity> gameEntities;
    private DrawQueueComparator drawQueueComparator;
    //private Array<ServingStation> servingStations;

    public Gold gold;
    public RepPoints Reputation;

    private float initalzoom;
    private float zoomincrements;

    //Objects
    public Array<Cook> unusedcooks;
    public Array<Cook> cooks;
    public Cook cook;

    private boolean readytorezoooom;


    public int cookIndex;
    //private CustomerController customerController;
    private CustomerController customerController;
    //private int customersToServe;
    //public ArrayList<Customer> customersToServe;

    private int freeze;
    private boolean EnableAutoZoom;
    private float ZoomSecondCounter;
    private boolean forcewin;

    private OrthographicCamera backgroundCamera;
    public static SpriteBatch bgBatch;
    public Preferences prefs = Gdx.app.getPreferences("My Preferences");
    private float Cookswapstage;
    private boolean Loading;

    /**
     * This class initializes all the variables used for the GameScreen
     * @param screenController - It takes a screenController as input which helps the set-up process of the screen
     * @param camera - the camera is used to follow the chefs dynamically
     */
    public GameScreen(ScreenController screenController, OrthographicCamera camera)//Constructor, reset rebuildings constructor
    {
        this.letsremove = new Array<Customer>();
        this.filepath = "OutercookedSaveData.json";

        this.readytorezoooom = false;
        this.Loading = false;
        this.holdzoomcounter = 0;
        this.posCamera = camera.position;
        this.Cookswapstage = 0;
        this.zoomincrements = 1/100f;
        this.initalzoom = 1f;
        this.forcewin = false;
        this.previousSecond = TimeUtils.millis();
        //this.lastCustomerSecond = -1;
        //this.nextCustomerSecond = -1;
        this.cooks = new Array<>();
        this.unusedcooks = new Array<>();

        this.interactables = new Array<>();
        this.gold = new Gold();
        this.gold.setBalance(0);
        this.Reputation = new RepPoints();
        this.freeze = 0;
        this.EnableAutoZoom = true;
        this.ZoomSecondCounter = 2f;

        // UPDATE
        // this.collisionHelper = CollisionHelper.getInstance();

        this.cookIndex = -1;
        this.camera = camera;
        this.camera.zoom = 1/20f;
        this.screenController = screenController;
        this.batch = screenController.getSpriteBatch();
        this.shape = screenController.getShapeRenderer();
        this.gameEntities = new ArrayList<>();
        this.drawQueueComparator = new DrawQueueComparator();
        //this.customerController = new CustomerController(this);

        this.world = new World(new Vector2(0,0), false);
        //this.box2DDebugRenderer = new Box2DDebugRenderer();

        // UPDATED
        // this.mapHelper = MapHelper.getInstance();
        this.mapHelper = new MapHelper(this);
        //this.customerControllerNew = new CustomerControllerNew(this);
        //this.servingStationNewList = this.mapHelper.getServingStationNewList();
        //ln(servingStationNewList);


        // this.mapHelper.setGameScreen(this);
        this.orthogonalTiledMapRenderer = mapHelper.setupMap();
        this.gameHud = new GameHud(batch, this);
        this.instructionHUD = new InstructionHud(batch);
        addInteractable(this.mapHelper.getMapStations());
        this.customerController = new CustomerController(this);


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

        //this.customersToServe = new ArrayList<>();
        //this.addCustomersNew(customerControllerNew.getCustomers());

        this.cook = cooks.get(0);
        this.gameEntities.addAll(mapHelper.getMapStations());

        this.backgroundCamera = new OrthographicCamera();
        this.bgBatch = new SpriteBatch();
        this.bgBatch.setProjectionMatrix(backgroundCamera.combined);
        setCook((cookIndex + 1) % cooks.size);
        this.gameHud.updateloading("");

    }

    /**
     * This class deals with resting the GameScreen after the player uses the reset functionality
     * @param cooksforgame The cooks currently controlled by the player in the game.
     * @param unusedcooksforgame The cooks the player hasn't bought yet
     * @param customersforgame All the current customers in the game
     * @param stationsforgame All the stations in the game
     */
    public void reset(Array<Cook> cooksforgame,Array<Cook> unusedcooksforgame,ArrayList<Customer>customersforgame,ArrayList<StationData>stationsforgame)
    {
        this.readytorezoooom = true;
        this.gameHud = new GameHud(batch, this);
        this.gameHud.updateloading("Loading");
        this.Loading = true;
        holdzoomcounter = 0;
        this.posCamera = camera.position;
        this.Cookswapstage = 0;
        this.zoomincrements = 1/100f;
        this.initalzoom = 1f;
        this.forcewin = false;
        this.previousSecond = TimeUtils.millis();
        //this.lastCustomerSecond = -1;
        //this.nextCustomerSecond = -1;
        this.cooks = new Array<>();
        this.unusedcooks = new Array<>();

        this.interactables = new Array<>();
        this.gold = new Gold();
        this.gold.setBalance(0);
        this.Reputation = new RepPoints();
        this.freeze = 0;
        this.EnableAutoZoom = true;
        this.ZoomSecondCounter = 2f;

        // UPDATE
        // this.collisionHelper = CollisionHelper.getInstance();
        this.cookIndex = -1;
//        this.camera = camera;
//        this.camera.zoom = 1/10f;
        this.screenController = screenController;
        this.batch = screenController.getSpriteBatch();
        this.shape = screenController.getShapeRenderer();
        this.gameEntities = new ArrayList<>();
        this.drawQueueComparator = new DrawQueueComparator();
        //this.customerController = new CustomerController(this);

        this.world = new World(new Vector2(0,0), false);
        //this.box2DDebugRenderer = new Box2DDebugRenderer();

        // UPDATED
        // this.mapHelper = MapHelper.getInstance();
        this.mapHelper = new MapHelper(this);
        //this.customerControllerNew = new CustomerControllerNew(this);
        //this.servingStationNewList = this.mapHelper.getServingStationNewList();
        //ln(servingStationNewList);

        // this.mapHelper.setGameScreen(this);
        this.orthogonalTiledMapRenderer = mapHelper.setupMap();
        this.instructionHUD = new InstructionHud(batch);
        addInteractable(this.mapHelper.getMapStations());

        this.customerController = new CustomerController(this);


        if(cooksforgame.size == 0) {
            this.camera.zoom = 1f;
            Cook GlibbertOrange = new Cook(2041 * 8f, 2814 * 8f, 3.34f, 1); //width will need adjusting when sprites updated
            GlibbertOrange.setColour("Orange");
            this.addCook(GlibbertOrange);
            Cook GlibbertBlue = new Cook(2045 * 8f, 2814 * 8f, 3.34f, 1); //width will need adjusting when sprites updated
            GlibbertBlue.setColour("Blue");
            this.addCook(GlibbertBlue);
            Cook GlibbertGreen = new Cook(2049 * 8f, 2814 * 8f, 3.34f, 1); //width will need adjusting when sprites updated
            GlibbertGreen.setColour("Green");
            this.addCook(GlibbertGreen);

            Cook Buy1 = new Cook((2031.1f) * 8f, 2853 * 8f, 3.34f, 1); //width will need adjusting when sprites updated
            Buy1.setColour("Purple");
            this.addSpareCook(Buy1);
            Cook Buy2 = new Cook((2031.1f + 12f) * 8f, 2853 * 8f, 3.34f, 1); //width will need adjusting when sprites updated
            Buy2.setColour("Black");
            this.addSpareCook(Buy2);
            Cook Buy3 = new Cook((2031.1f - 92f) * 8f, 2853 * 8f, 3.34f, 1); //width will need adjusting when sprites updated
            Buy3.setColour("White");
            this.addSpareCook(Buy3);
            Cook Buy4 = new Cook((2031.1f - 104f) * 8f, 2853 * 8f, 3.34f, 1); //width will need adjusting when sprites updated
            Buy4.setColour("Red");
            this.addSpareCook(Buy4);
        }
        else
        {
            for(Cook c:cooksforgame)
            {
                this.addCook(c);
            }
            for(Cook c:unusedcooksforgame)
            {
                this.addSpareCook(c);
            }

            int counter = 0;
            for(Customer c:customersforgame)
            {
                addCustomer(customerController.addSavedCustomer(c.x, c.y, c.stationPosition.x, c.stationPosition.y));
                customerController.customers.get(counter).request = c.request;
                customerController.customers.get(counter).waittime = c.waittime;
                customerController.customers.get(counter).setCustomerStatus(c.getCustomerStatus());
                customerController.customers.get(counter).stationPosition = c.stationPosition;


                counter++;
            }

            ArrayList<Station> newmapStations = new ArrayList<>();
            counter=0;
            for(StationData newstation:stationsforgame)
            {
                Station n = mapHelper.mapStations.get(newstation.StationPropertyID);
                //
                n.stationFoodStack.setStack(newstation.HeldFood.getStackCopy());
//                n.stationDishStack.setStack(newstation.stationdishstack.getStackCopy());;
                Array<FoodItem.FoodID> nd = new Array<FoodItem.FoodID>();
                for(FoodItem.FoodID f:newstation.stationdishstack.getStack())
                {

                    nd.add(f);
                }
                if(nd.size>0)
                {
                    n.stationDishStack.setStackPlate(nd);
                }
                else {
                    n.stationDishStack.setStack(nd);
                }
                //
                n.Locked = newstation.lock;
                if(newstation.Enabled){
                    n.Enable();
                }
                else
                {
                    n.Disable();
                }

//                if(n.isABluggusPrison&&n.Enabled)
//                {
//                    n.interact(new Cook(0,0,1,1), InputKey.InputTypes.USE);
//                }
                newmapStations.add(n);
//                ln("******************************************");
//                ln(mapHelper.mapStations.get(newstation.StationPropertyID).stationFoodStack.toString());
//                ln("******************************************");
            }
            mapHelper.mapStations = newmapStations;
//            this.customerController.customers = customersforgame;
        }

        //this.customersToServe = new ArrayList<>();
        //this.addCustomersNew(customerControllerNew.getCustomers());
        this.cook = cooks.get(0);
        this.gameEntities.addAll(mapHelper.getMapStations());
        setCook((cookIndex + 1) % cooks.size);
        this.Loading = false;
    }

    /**
     * Update the game's values, {@link GameEntity}s and so on.
     * @param delta The time between frames as a float.
     */
    private int countcycles = 0;

    public void update(float delta)
    {
        if(readytorezoooom)
        {
            if (camera.zoom >4f){
                camera.zoom -= 1f;
            }
            else if (camera.zoom >1f){
                if((camera.zoom -= 1/100f)<1/10f)
                {
                    this.camera.zoom = 1/100f;
                }
                else {
                    camera.zoom -= 1 / 100f;
                }
            }
            else if (camera.zoom >1/10f){
                if((camera.zoom -= 1/1000f)<1/10f)
                {
                    this.camera.zoom = 1/1000f;
                }
                else {
                    camera.zoom -= 1 / 100f;
                }
            }
            else {
                this.camera.zoom = 1/10f;
                this.readytorezoooom = false;
                zoomincrements = 1/100f;
                initalzoom = 2f;
            }

        }
        if(this.Loading)
        {
            this.gameHud.updateloading("Loading...");
            if ((camera.zoom < initalzoom)&&(camera.zoom < 40f)){
                camera.zoom += zoomincrements;
                initalzoom = initalzoom + 1/4f*initalzoom;
                zoomincrements = 20/17f*zoomincrements;
            }
            else if(countcycles==0){
                this.initalzoom = 2f;
                this.zoomincrements = 1/100f;
                this.Loadgame();
            }
        }
        else {
            this.gameHud.updateloading("");

        }
        this.posCamera = camera.position;
//        ln("Rep Points: "+this.Reputation.getPoints());
		if (Gdx.input.isKeyPressed(Input.Keys.K)){
            this.gameHud.updateloading("Loading");
//			ln(this.cook.getX());
//			ln(this.cook.getY());
            this.Savegame();
            this.gameHud.updateloading("");
		}
        if (Gdx.input.isKeyPressed(Input.Keys.L)){
            this.gameHud.updateloading("Loading...");
            this.Loading = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)){

            this.Reputation.setPoints(0);

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
            screenController.paylistscreen();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.T)){

            this.gold.setBalance(1000000);

        }

        // First thing, update all inputs
        Interactions.updateKeys();

        long diffInMillis = TimeUtils.timeSinceMillis(previousSecond)-(freeze*1000);
        if(EnableAutoZoom) {
            if (camera.zoom >= 1 / 10f) {
                camera.zoom -= 1 / 100f;
            }
            else {
                this.EnableAutoZoom = false;
            }
        }

        if (diffInMillis >= 1000) {
            if(ZoomSecondCounter==0) {
                this.EnableAutoZoom = false;
                ZoomSecondCounter = ZoomSecondCounter-1f;
            }
            else if(ZoomSecondCounter>0) {
                ZoomSecondCounter = ZoomSecondCounter-1f;
            }

//            for(Customer customer:customersToServe) //Dealing with leaving
//            {
//                customer.DecreasePatience();
//                if((customer.waittime<=0)&&(customer.Stillhere ==true))
//                {
//                 m.out.println(customer + " is now leaving");
//                    customer.StormOut(); //When customer wants to leave
//                }
//            }

            // SWITCH TO CUSTOMER CONTROLLER instead of customersServed
            for(Customer customer:customerController.getCustomers()) //Dealing with leaving
            {
                customer.DecreasePatience();
                if(customer.waittime<=0 && customer.getCustomerStatus()==2) {
                    customer.StormOut(); //When customer wants to leave
                }
            }

            //gameEntities.add(customerControllerNew.addCustomer());

            addCustomer(customerController.addCustomer());



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
        gameHud.updateReputation(Reputation.getPoints());
        gameHud.updateGold(gold.getBalance());
        gameHud.updateCustomerServed(this.customerController.TotalCustomersServed);
        cameraUpdate();
        orthogonalTiledMapRenderer.setView(camera);
        batch.setProjectionMatrix(camera.combined);
        shape.setProjectionMatrix(camera.combined);
        for (Cook thisCook : cooks) {
            if (thisCook == cook) {
                thisCook.userInput(mapHelper.getMapObstacles());
                thisCook.userInteract(mapHelper.getMapStations());
            }
            //Chef Bluggus Mode
            if(thisCook.activateBluggus == true)
            {
//                ln("Moving stacks");
                thisCook.moveStacks();
            }
        }

        // SWITCH TO CUSTOMER CONTROLLER NEW
        for (Customer customer : customerController.getCustomers()) {
            //customer.moveTo(customer.endX, customer.endY);
            customer.customerInteract(mapHelper.getMapStations());
            customer.update(Gdx.graphics.getDeltaTime());
        }

        if((Interactions.isJustPressed(InputKey.InputTypes.COOK_SWAP))||(this.Cookswapstage>0)) {
            if(Cookswapstage==0) {
                Cookswapstage++;
            }
            else if(Cookswapstage==1){
                if (camera.zoom <= 1/4f) {
                    camera.zoom += 1 / 100f;
                }
                else {
                    Cookswapstage++;
                }
            }
            else if((Cookswapstage==2)){ //FOR TRASITION
                this.holdzoomcounter++;
                if(this.holdzoomcounter>10) {
                    Cookswapstage++;
                    this.holdzoomcounter =0;
                }
            }
            else if((Cookswapstage==4)){ //FOR TRASITION
                this.holdzoomcounter++;
                if(this.holdzoomcounter>10) {
                    Cookswapstage++;
                    this.holdzoomcounter =0;
                }
            }
            else if(Cookswapstage==5){
                if (camera.zoom >= 1 / 10f) {
                    camera.zoom -= 1 / 100f;
                }
                else {
                    Cookswapstage=0;
                }
            }
            else if(Cookswapstage==3){
                setCook((cookIndex + 1) % cooks.size);
                Cookswapstage++;
            }
        }

        this.cook.update(Gdx.graphics.getDeltaTime());

        if(Interactions.isJustPressed(InputKey.InputTypes.PAUSE))
        {
            screenController.pauseGameScreen();
        }
        world.step(1/60f,6,2);
        for (GameEntity entity : gameEntities) {
            entity.update(delta);
        }
        for (GameEntity entity : letsremove) {
            gameEntities.remove(entity);
        }

        if((customerController.wonScenario()) ||(this.forcewin)) {
            screenController.setEndTime(Util.formatTime(hoursPassed,minutesPassed,secondsPassed));
            if ((camera.zoom < initalzoom)&&(camera.zoom < 1000f)){
                camera.zoom += zoomincrements;
                initalzoom = initalzoom + 1/4f*initalzoom;
                zoomincrements = 18/17f*zoomincrements;
            }
            else {
                this.initalzoom = 2f;
                this.zoomincrements = 1/100f;
                screenController.winGame();
            }
        }

        //Checking Reputation
        if(this.Reputation.getPoints() == 0){
            if ((camera.zoom < initalzoom)&&(camera.zoom < 1000f)){
                camera.zoom += zoomincrements;
                initalzoom = initalzoom + 1/4f*initalzoom;
                zoomincrements = 18/17f*zoomincrements;
            }
            else {
                screenController.setScreen(ScreenController.ScreenID.GAMEOVER);
            }
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

//        if(customersToServe <= customerController.getCustomersServed())
//        {
//            screenController.setScreen((ScreenController.ScreenID.GAMEOVER));
//            ((GameOverScreen) screenController.getScreen(ScreenController.ScreenID.GAMEOVER)).setTime(hoursPassed,minutesPassed,secondsPassed);
//        }
    }

    /**
     * Render the {@link GameScreen}. It is a separate function to
     * allow rendering of the game from the {@link PauseScreen}.
     * @param delta The time between frames as a float.
     */
    public void renderGame(float delta) {



        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        backgroundCamera.position.x = 0;
        backgroundCamera.position.y = 0;

        bgBatch.begin();
        bgBatch.draw(MenuScreen.spaceBackground, -1, -1, 2.3f, 2);
        bgBatch.end();
        orthogonalTiledMapRenderer.render();

        batch.begin();

        gameEntities.sort(drawQueueComparator);

        for (GameEntity entity : gameEntities) {
            entity.render(batch);
        }

        batch.end();

        shape.begin(ShapeRenderer.ShapeType.Filled);




        orthogonalTiledMapRenderer.getBatch().begin();

        orthogonalTiledMapRenderer.renderTileLayer(
                (TiledMapTileLayer) orthogonalTiledMapRenderer.getMap().getLayers().get("VatTop")
        );

        orthogonalTiledMapRenderer.getBatch().end();


        batch.begin();

        gameEntities.sort(drawQueueComparator);

        for (GameEntity entity : gameEntities) {
            if (entity == cook) {
                entity.render(batch);
            }
        }

        batch.end();


        orthogonalTiledMapRenderer.getBatch().begin();

        orthogonalTiledMapRenderer.renderTileLayer(
                (TiledMapTileLayer) orthogonalTiledMapRenderer.getMap().getLayers().get("Front2")
        );
        orthogonalTiledMapRenderer.renderTileLayer(
                (TiledMapTileLayer) orthogonalTiledMapRenderer.getMap().getLayers().get("Windows")
        );
        orthogonalTiledMapRenderer.renderTileLayer(
                (TiledMapTileLayer) orthogonalTiledMapRenderer.getMap().getLayers().get("FrontWalls")
        );
        orthogonalTiledMapRenderer.getBatch().end();


        batch.begin();
        for (GameEntity entity : gameEntities) {
            entity.renderShape(shape);
            if (entity == cook) {
                ((Cook) entity).renderControlArrow(batch);
            }
        }
        batch.end();
        shape.end();

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

    public void addCustomer(Customer customer) {
//        ("__________________G"+customer);
        if (customer != null) {
            gameEntities.add(customer);
        }

    }

//    public void addCustomersNew(Array<Customer> customers) {
//
//        for (Customer customer : customers) {
//            gameEntities.add(customer);
//            customersToServe.add(customer);
//        }
//    }

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

    /**
     * Function takes an index n and removed the cook(n) from used cooks
     * and places
     * @param n
     */
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
     * A variable for setting up the game when it starts.
     *  customers The number of customers that need to be
     *                  served in the game to finish.
     */
    public void startGame(String mode, int difficulty) {

        customerController.setMode(mode);
        customerController.setDifficulty(difficulty);

        secondsPassed = 0;
        minutesPassed = 0;
        hoursPassed = 0;

        previousSecond = TimeUtils.millis();
    }

    public CustomerController getCustomerController() { return this.customerController;}

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



    //-------------------------------------
    //Morgan's Shop Section
    //-------------------------------------

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
    }



    //-------------------------------------
    //Save Game
    //-------------------------------------
    private String SaveText; //REMOVE
    private  Json json = new Json();

    /**
     * Creates a new instance of SaveClass
     * This class is then converting into a JSON file to be saved
     */
    public void Savegame()
    {
        SavingClass save = new SavingClass(this);
        this.SaveText = json.toJson(save);
        WriteSaveFile();
        this.SaveText = "";
    }


    /**
     * Procedure run, when L is pressed
     * This functions loads the current save stored in this.
     * This converts the json string stored, into the required data. It then runs a reset() of
     * the game using the arrays of cook and customer types.
     * After all the users preferences such as difficulty are then set to the save file's version
     * Then all current stats (E.G Time on timer) is then set back to what it was.
     */
    public void Loadgame() //Change ton have no param
    {
//        SavingClass newsave = new SavingClass(GameScreen);
//        StoredFile.fromJson(SavingClass newsave);
        ReadSaveFile();
        JsonValue root = new JsonReader().parse(this.SaveText);
        //cooks
        JsonValue held_cook = root.get("cooks");
        JsonValue held_coords = root.get("cookscoords");
        JsonValue held_stack1 = root.get("cookstack1");
        JsonValue held_stack2 = root.get("cookstack2");
        JsonValue held_dishstack = root.get("cookdishstack");
        JsonValue held_bluggy = root.get("cookisbluggus");
        JsonValue held_speed = root.get("cookspeed");
        JsonValue held_colour = root.get("colour");
        JsonValue held_wait = root.get("waitimes");
        JsonValue held_req = root.get("requests");
        JsonValue held_sts = root.get("Status");

        Array<Integer> cooks = new Array<Integer>();
        Array<Array<Float>> cookscoords = new Array<Array<Float>>(); //If not spawned, (0,0)
        Array<Array<Integer>> cookstack1 = new Array<Array<Integer>>();
        Array<Array<Integer>> cookstack2 = new Array<Array<Integer>>();
        Array<Array<Integer>> cookdishstack = new Array<Array<Integer>>();
        Array<Boolean> cookisbluggus = new Array<Boolean>(); //if chef is bluggus or not
        Array<Float> cookspeed = new Array<Float>();
        Array<String> colour = new Array<String>(); //allocates the sprite
        Array<Float> waitimes = new Array<Float>(); //-1 for cooks
        Array<String> requests = new Array<String>(); //norequest = cooks

        //Arrayys to hold all data
        Array<Cook> cooksforgame = new Array<Cook>();
        Array<Cook> unusedcooksforgame = new Array<Cook>();
        ArrayList<Customer> customersforgame = new ArrayList<Customer>();
        ArrayList<StationData> stationsforgame = new ArrayList<StationData>();
        JsonValue held_x;
        JsonValue held_y;
        float x,y;
        int count = 0;
        for(JsonValue t:held_cook)
        {
            int type = held_cook.getInt(count);
            held_x = held_coords.get(count).get(0).get(1);
            held_y = held_coords.get(count).get(1).get(1);
            x = held_x.asFloat();
            y = held_y.asFloat();
            //ln(held_dishstack.get(0)+"<-------------------------------------");
            Array<Integer> newdishstack = new Array<Integer>();
            Array<Integer> s1 = new Array<Integer>();
            Array<Integer> s2 = new Array<Integer>();


            if(held_dishstack.get(count)!=null) {
                for (JsonValue placeindishstack : held_dishstack.get(count)) {
                    newdishstack.add(placeindishstack.get(1).asInt());
                }

            }
            Array<FoodItem.FoodID> tempdish = new Array<FoodItem.FoodID>();
            for(int item:newdishstack)
            {
                FoodItem.FoodID value = FoodItem.FoodID.values()[item];
                tempdish.add(value);
            }

            if(type==2) //Customer
            {
                Customer newc = new Customer(x*8f,y*8f,20,20);
                newc.dishStack.setStack(tempdish);
                float _wait_ = held_wait.getFloat(count);
                String _req_ = held_req.getString(count);
                newc.waittime = _wait_;
                newc.request = _req_;
                newc.setCustomerStatus(held_sts.getInt(count));
                customersforgame.add(newc);




            }
            else //must be cook
            {
                if(held_stack1.get(count)!=null) {
                    for (JsonValue placeinstack : held_stack1.get(count)) {
                        //ln("SIZE OF ARRAY OF DISTACK "+placeindishstack);
                        s1.add(placeinstack.get(1).asInt());
                    }
                }
                if(held_stack2.get(count)!=null) {
                    for (JsonValue placeinstack2 : held_stack2.get(count)) {
                        //ln("SIZE OF ARRAY OF DISTACK "+placeindishstack);
                        s1.add(placeinstack2.get(1).asInt());
                    }
                }
                float _speed_ = held_speed.getFloat(count);
                String _colour_ = held_colour.getString(count);
                boolean _isbluggus_ = held_bluggy.getBoolean(count);

                Cook toAdd = new Cook(x*8f,y*8f,3.34f,1);
                toAdd.movement_speed = _speed_;
                toAdd.setColour(_colour_);
                toAdd.dishStack.setStack(tempdish);
                if (_isbluggus_) {
                    toAdd.MakeIntoBluggus();
                }
                //("food id values: "+ value);
                for(int item:s1)
                {
                    FoodItem.FoodID value = FoodItem.FoodID.values()[item];
                    toAdd.foodStack.addStack(value);
                }
                for(int item:s2)
                {
                    FoodItem.FoodID value = FoodItem.FoodID.values()[item];
                    toAdd.foodStack2.addStack(value);
                }


                if(type==1) //Used cook
                {
                    cooksforgame.add(toAdd);
                }
                else if(type==0) //not used chef
                {
                    unusedcooksforgame.add(toAdd);
                }
            }
            count++; //increment person
        }
        JsonValue held_SID = root.get("StationPropertyID");
        JsonValue held_SFOOOOD = root.get("HeldFood");
        JsonValue held_SDishyStacky = root.get("stationdishstack");
        JsonValue held_LockedStatus = root.get("lockedStation");
        JsonValue held_enab = root.get("Enabled");
        count = 0;


        for(JsonValue ID:held_SID)
        {

            StationData sd = new StationData();
            sd.StationPropertyID = held_SID.getInt(count);

            FoodStack fs = new FoodStack();
            if(held_SFOOOOD.get(count)!=null) {
                for (JsonValue placeinstack : held_SFOOOOD.get(count)) {
                    //ln("SIZE OF ARRAY OF DISTACK "+placeindishstack);
                    FoodItem.FoodID value = FoodItem.FoodID.values()[(placeinstack.get(1).asInt())];
                    fs.addStack(value);
                }

            }
            sd.HeldFood = fs;

            fs = new FoodStack();
            if(held_SDishyStacky.get(count)!=null) {
                for (JsonValue placeinstack : held_SDishyStacky.get(count)) {
                    FoodItem.FoodID value = FoodItem.FoodID.values()[(placeinstack.get(1).asInt())];
                    fs.addStack(value);
                }

            }
            sd.stationdishstack.setStack(fs.getStackCopy());

            sd.lock = held_LockedStatus.getBoolean(count);
            sd.Enabled = held_enab.getBoolean(count);
            stationsforgame.add(sd);
            count++;
        }

         reset(cooksforgame,unusedcooksforgame,customersforgame,stationsforgame);

        //Gold and Reputation
        int held_Gold = root.getInt("gold");
        this.gold.setBalance(held_Gold);
        int held_Reputation = root.getInt("reputation");
        this.Reputation.setPoints(held_Reputation);
        //Timer
        int held_seconds = root.getInt("seconds");
        int held_minutes = root.getInt("minutes");
        int held_hours = root.getInt("hours");
        this.secondsPassed = held_seconds;
        this.minutesPassed = held_minutes;
        this.hoursPassed = held_hours;

        //Mode and difficulty
        customerController.setMode(root.getString("Mode"));
        customerController.setDifficulty(root.getInt("Difficulty"));
        customerController.TotalCustomersServed = root.getInt("HowManyHaveBeenServed");
        gameHud.updateCustomerServed(this.customerController.TotalCustomersServed);


    }

    /**
     * Writes this.SaveText that holds the saved data to the filepath also stored.
     * This filepath always stays the same and rewrites the file everytime.
     */
    private void WriteSaveFile()
    {
        try (PrintWriter output = new PrintWriter(new FileWriter(this.filepath))) {
            output.write(this.SaveText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Procedure
     * Sets the save file stored to the new string got from getnewString(),
     * then.
     * Will catch any exception
     */
    private void ReadSaveFile(){
        try{
            this.SaveText = getnewString();
        }
        catch (IOException e)
        {

        }

    }

    /**
     * Reads a file and converts all data to a single string
     * @return the new string from the stored file path
     * @throws IOException
     */
    private String getnewString() throws IOException {
        return new String(Files.readAllBytes(Paths.get(this.filepath)));
    }



}


