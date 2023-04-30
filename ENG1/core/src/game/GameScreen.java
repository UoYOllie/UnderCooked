package game;

import Shop.Gold;
import Shop.ShopItem;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.*;
import cooks.Cook;
import cooks.CustomerController;
import cooks.CustomerNew;
//import customers.Customer;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import cooks.GameEntity;
//import customers.CustomerController;
import customers.RepPoints;
import food.DishStack;
import food.FoodItem;
import food.FoodStack;
import helper.*;
import interactions.InputKey;
import interactions.Interactions;
import stations.CookInteractable;
//import stations.ServingStation;
import stations.ServingStation;
import stations.Station;

import java.util.ArrayList;
import java.util.Comparator;

import static helper.Constants.PPM;

/** A {@link ScreenAdapter} containing certain elements of the game. */
public class GameScreen extends ScreenAdapter {
    private int holdzoomcounter;
    private OrthographicCamera camera;
    private int delay;

    private long previousSecond = 0;
    //private long lastCustomerSecond = 0;
    //private long nextCustomerSecond = 0;
    public int secondsPassed = 0, minutesPassed = 0, hoursPassed = 0;
    private GameHud gameHud;
    private InstructionHud instructionHUD;
    private SpriteBatch batch;
    private ShapeRenderer shape;
    private ScreenController screenController;
    // private ShapeRenderer shapeRenderer;
    private World world;
    Vector3 posCamera;
    private Box2DDebugRenderer box2DDebugRenderer;

    private SuperMapSuperRenderer orthogonalTiledMapRenderer;
    public MapHelper mapHelper;
    //public Array<Station> servingStationNewList;
    private Array<CookInteractable> interactables;
    private CollisionHelper collisionHelper;
    public ArrayList<GameEntity> gameEntities;
    private DrawQueueComparator drawQueueComparator;
    //private Array<ServingStation> servingStations;
    private float xOffset = 1500;
    private float yOffset = 1200;

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
    //public ArrayList<CustomerNew> customersToServe;

    private int freeze;
    private boolean EnableAutoZoom;
    private float ZoomSecondCounter;
    private boolean forcewin;

    private OrthographicCamera backgroundCamera;
    private SpriteBatch bgBatch;
    public Preferences prefs = Gdx.app.getPreferences("My Preferences");
    private float Cookswapstage;
    private boolean Loading;

    /**
     * The constructor for the {@link GameScreen}.
     * @param screenController The {@link ScreenController} of the {@link ScreenAdapter}.
     * @param camera The {@link OrthographicCamera} that the game should use.
     */
    public GameScreen(ScreenController screenController, OrthographicCamera camera)//Constructor, reset rebuildings constructor
    {
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
        this.gold.setBalance(1000);
        this.Reputation = new RepPoints();
        this.freeze = 0;
        this.EnableAutoZoom = true;
        this.ZoomSecondCounter = 2f;

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
        //this.customerController = new CustomerController(this);

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

    public void reset(Array<Cook> cooksforgame,Array<Cook> unusedcooksforgame,ArrayList<CustomerNew>customersforgame,ArrayList<StationData>stationsforgame)
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
        this.gold.setBalance(1000);
        this.Reputation = new RepPoints();
        this.freeze = 0;
        this.EnableAutoZoom = true;
        this.ZoomSecondCounter = 2f;

        // UPDATE
        // this.collisionHelper = CollisionHelper.getInstance();
        this.collisionHelper = new CollisionHelper();

        this.collisionHelper.setGameScreen(this);
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
        //System.out.println(servingStationNewList);

        // this.mapHelper.setGameScreen(this);
        this.orthogonalTiledMapRenderer = mapHelper.setupMap();
        this.instructionHUD = new InstructionHud(batch);
        addInteractable(this.mapHelper.getMapStations());

        this.customerController = new CustomerController(this);


        if(cooksforgame.size == 0) {
            this.camera.zoom = 1f;
            System.out.println("cOOKS ARE NULL");
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
            System.out.println("cOOKS ARE in ere");
            System.out.print(cooksforgame);
            for(Cook c:cooksforgame)
            {
                System.out.print(c);
                this.addCook(c);
            }
            for(Cook c:unusedcooksforgame)
            {
                this.addSpareCook(c);
            }

            int counter = 0;
            for(CustomerNew c:customersforgame)
            {
                addCustomer(customerController.addSavedCustomer(c.x,c.y, c.stationPosition.x, c.stationPosition.y));
                customerController.customers.get(counter).request = c.request;
                customerController.customers.get(counter).waittime = c.waittime;
                customerController.customers.get(counter).setStatus(c.getStatus());
                customerController.customers.get(counter).stationPosition = c.stationPosition;


                counter++;
            }

            ArrayList<Station> newmapStations = new ArrayList<>();
            counter=0;
            for(StationData newstation:stationsforgame)
            {
                Station n = mapHelper.mapStations.get(newstation.StationPropertyID);
                n.stationFoodStack = newstation.HeldFood;
                n.stationDishStack = newstation.stationdishstack;
                n.Locked = newstation.lock;
                mapHelper.mapStations.set(newstation.StationPropertyID,n);
            }
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
                System.out.print("winning215");
                this.initalzoom = 2f;
                this.zoomincrements = 1/100f;
                this.Loadgame();
            }
            System.out.println("Loading....");
            System.out.println(this.cook.getX());
            System.out.println(this.cook.getY());
        }
        else {
            this.gameHud.updateloading("");

        }
        this.posCamera = camera.position;
//        System.out.println("Rep Points: "+this.Reputation.getPoints());
		if (Gdx.input.isKeyPressed(Input.Keys.K)){
            System.out.println("Saving....");
            this.gameHud.updateloading("Loading");
//			System.out.println(this.cook.getX());
//			System.out.println(this.cook.getY());
            this.Savegame();
            this.gameHud.updateloading("");
		}
        if (Gdx.input.isKeyPressed(Input.Keys.L)){
//            screenController.setScreen(ScreenController.ScreenID.LOADING);
            this.gameHud.updateloading("Loading...");
            this.Loading = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)){
//            this.forcewin = true;
            System.out.print("Forcing win");
//            this.Reputation.setPoints(0);
            System.out.println(this.SaveText);
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

//            for(CustomerNew customer:customersToServe) //Dealing with leaving
//            {
//                customer.DecreasePatience();
//                if((customer.waittime<=0)&&(customer.Stillhere ==true))
//                {
//                    System.out.println(customer + " is now leaving");
//                    customer.StormOut(); //When customer wants to leave
//                }
//            }

            // SWITCH TO CUSTOMER CONTROLLER instead of customersServed
            for(CustomerNew customer:customerController.getCustomers()) //Dealing with leaving
            {
                customer.DecreasePatience();
                if((customer.waittime<=0)&&(customer.Stillhere ==true))
                {
                    System.out.println(customer + " is now leaving");
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
//                System.out.println("Moving stacks");
                thisCook.moveStacks();
            }
        }

        // SWITCH TO CUSTOMER CONTROLLER NEW
        for (CustomerNew customer : customerController.getCustomers()) {
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
            System.out.println("Im Pausing");
            screenController.pauseGameScreen();
        }
        world.step(1/60f,6,2);
        for (GameEntity entity : gameEntities) {
            entity.update(delta);
        }

        if((customerController.wonScenario()) ||(this.forcewin)) {
            System.out.println("game is won!");
            screenController.setEndTime(Util.formatTime(hoursPassed,minutesPassed,secondsPassed));
            if ((camera.zoom < initalzoom)&&(camera.zoom < 1000f)){
                camera.zoom += zoomincrements;
                initalzoom = initalzoom + 1/4f*initalzoom;
                zoomincrements = 18/17f*zoomincrements;
            }
            else {
                System.out.print("winning215");
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
                System.out.print("Sucks to lose");
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

    public void addCustomer(CustomerNew customer) {
//        System.out.print("__________________G"+customer);
        if (customer != null) {
            gameEntities.add(customer);
        }

    }

//    public void addCustomersNew(Array<CustomerNew> customers) {
//
//        for (CustomerNew customer : customers) {
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
    //public void setCustomerHud(int customerCount) {
        //gameHud.setCustomerCount(customersToServe - customerCount);
    //}

//    /**
//     * Returns the number of customers remaining before the game is finished.
//     * @return {@code int} : The value of {@link CustomerController#getCustomersLeft()}.
//     */
//    public int getCustomerCount() {
//        return customerController.getCustomersLeft();
//    }

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

//    /**
//     * A getter to get the {@link #nextCustomerSecond}.
//     * <br>The {@link #nextCustomerSecond} is used for spawning the
//     * {@link Customer}s after a short delay.
//     * @return {@code long} : The {@link #nextCustomerSecond}.
//     */
//    public long getNextCustomerSecond() {
//        return nextCustomerSecond;
//    }

//    /**
//     * A setter to set the {@link #nextCustomerSecond} to the {@code long} provided.
//     * @param newSecond What to set the {@link #nextCustomerSecond} to as a {@code long}.
//     */
//    public void setNextCustomerSecond(long newSecond) {
//        nextCustomerSecond = newSecond;
//    }

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

//    /**
//     * Intermediate function to allow the {@link MapHelper} to add
//     * the {@link ServingStation}s to the {@link CustomerController}.
//     * @param station The {@link ServingStation} to add to the {@link CustomerController}.
//     */
//    public void addServingStation(ServingStation station) { customerController.addServingStation(station); }

    /** Reset the game variables, map and world. */
//    public void reset() {
//        // Reset all variables
//        secondsPassed = 0;
//        minutesPassed = 0;
//        hoursPassed = 0;
////        cooks.clear();
////        gameEntities.clear();
////        interactables.clear();
////        mapHelper.dispose();
////        customerController.clearServingStations();
////        dispose();
//
//        // UPDATE
//        //mapHelper = MapHelper.newInstance();
//        //mapHelper.setGameScreen(this);
//        this.mapHelper = new MapHelper(this);
//
//        world.dispose();
//        this.world = new World(new Vector2(0,0), false);
//        this.orthogonalTiledMapRenderer = mapHelper.setupMap();
//        cookIndex = -1;
//    }

    /**
     * A variable for setting up the game when it starts.
     *  customers The number of customers that need to be
     *                  served in the game to finish.
     */
    public void startGame(String mode, int difficulty) {

        customerController.setMode(mode);
        customerController.setDifficulty(difficulty);

        System.out.println(mode);
        System.out.println(difficulty);
        secondsPassed = 0;
        minutesPassed = 0;
        hoursPassed = 0;

        previousSecond = TimeUtils.millis();
        //lastCustomerSecond = TimeUtils.millis();
        //nextCustomerSecond = TimeUtils.millis()+2000;

        //gameHud.setRecipe(null);
        //customersToServe = CustomerControllerNew.getcustomers;
        //this.addCustomersNew(customerControllerNew.getCustomers());
        //customersToServe.addAll(customerControllerNew.getCustomers());
        //gameEntities.addAll(new ArrayList<GameEntity>(customerControllerNew.getCustomers()));


////        customerController.setCustomersLeft(customers);
////        customerController.setCustomersServed(0);
////        customerController.addCustomer();
//        //setCustomerHud(customers);
//        gameHud.setCustomerCount(customers);
        Savegame();
    }

//    /**
//     * A getter for the {@link CustomerController} of the
//     * game.
//     * @return {@link CustomerController} : The {@link CustomerController}
//     *                                      for the game.
//     */
//    public CustomerController getCustomerController() {
//        return this.customerController;
//    }

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
    //Save Game
    //-------------------------------------
    private String SaveText; //REMOVE
    private  Json json = new Json();
    public void Savegame()
    {
        SavingClass save = new SavingClass(this);
        this.SaveText = json.toJson(save);
//        System.out.println(json.prettyPrint(save));
    }


    public void Loadgame() //Change ton have no param
    {
//        SavingClass newsave = new SavingClass(GameScreen);
//        StoredFile.fromJson(SavingClass newsave);
        JsonValue root = new JsonReader().parse(this.SaveText);
        System.out.println(root);
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
//        System.out.println(held_coords.get(0).get(0).get(1));//Takes person Takes x or y Takes value
//        System.out.println(held_coords.get(0).get(0));
//        System.out.println(held_coords.get(0));
//        JsonValue held_x = held_coords.get(0).get(0).get(1);
//        System.out.println(held_x.asFloat());


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
        ArrayList<CustomerNew> customersforgame = new ArrayList<CustomerNew>();
        ArrayList<StationData> stationsforgame = new ArrayList<StationData>();
        JsonValue held_x;
        JsonValue held_y;
        float x,y;
        int count = 0;
        for(JsonValue t:held_cook)
        {
            int type = held_cook.getInt(count);
            System.out.println(type + " is the chef type");
            held_x = held_coords.get(count).get(0).get(1);
            held_y = held_coords.get(count).get(1).get(1);
            x = held_x.asFloat();
            y = held_y.asFloat();
            //System.out.println(held_dishstack.get(0)+"<-------------------------------------");
            Array<Integer> newdishstack = new Array<Integer>();
            Array<Integer> s1 = new Array<Integer>();
            Array<Integer> s2 = new Array<Integer>();


            if(held_dishstack.get(count)!=null) {
                for (JsonValue placeindishstack : held_dishstack.get(count)) {
                    System.out.println("SIZE OF ARRAY OF DISTACK "+placeindishstack);
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
                CustomerNew newc = new CustomerNew(x*8f,y*8f,20,20);
                newc.dishStack.setStack(tempdish);
                float _wait_ = held_wait.getFloat(count);
                String _req_ = held_req.getString(count);
                newc.waittime = _wait_;
                newc.request = _req_;
                newc.setStatus(held_sts.getInt(count));
                customersforgame.add(newc);




            }
            else //must be cook
            {
                if(held_stack1.get(count)!=null) {
                    for (JsonValue placeinstack : held_stack1.get(count)) {
                        //System.out.println("SIZE OF ARRAY OF DISTACK "+placeindishstack);
                        s1.add(placeinstack.get(1).asInt());
                    }
                }
                if(held_stack2.get(count)!=null) {
                    for (JsonValue placeinstack2 : held_stack2.get(count)) {
                        //System.out.println("SIZE OF ARRAY OF DISTACK "+placeindishstack);
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
                //System.out.print("food id values: "+ value);
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


        //stations
//        this.StationPropertyID = new Array<Integer>();
//        this.HeldFood = new Array<Array<Integer>>();
//        this.stationdishstack = new Array<Array<Integer>>();
//        this.lockedStation = new Array<Boolean>();
        JsonValue held_SID = root.get("StationPropertyID");
        JsonValue held_SFOOOOD = root.get("HeldFood");
        JsonValue held_SDishyStacky = root.get("stationdishstack");
        JsonValue held_LockedStatus = root.get("lockedStation");
        count = 0;


        for(JsonValue ID:held_SID)
        {

            StationData sd = new StationData();
            sd.StationPropertyID = held_SID.getInt(count);

            FoodStack fs = new FoodStack();
            if(held_SFOOOOD.get(count)!=null) {
                for (JsonValue placeinstack : held_SFOOOOD.get(count)) {
                    //System.out.println("SIZE OF ARRAY OF DISTACK "+placeindishstack);
                    FoodItem.FoodID value = FoodItem.FoodID.values()[(placeinstack.get(1).asInt())];
                    fs.addStack(value);
                }

            }
            sd.HeldFood = fs;

            fs = new FoodStack();
            if(held_SFOOOOD.get(count)!=null) {
                for (JsonValue placeinstack : held_SFOOOOD.get(count)) {
                    //System.out.println("SIZE OF ARRAY OF DISTACK "+placeindishstack);
                    FoodItem.FoodID value = FoodItem.FoodID.values()[(placeinstack.get(1).asInt())];
                    fs.addStack(value);
                }

            }
            Array<FoodItem.FoodID> ds = fs.getStackCopy();
            sd.stationdishstack.setStack(ds);

            sd.lock = held_LockedStatus.getBoolean(count);
            System.out.println("k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-");
            System.out.println(sd.lock);
            System.out.println(sd.stationdishstack);
            System.out.println(sd.HeldFood);
            System.out.println(sd.StationPropertyID);
            System.out.println("k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-");

            stationsforgame.add(sd);
            count++;
        }

//        System.out.println("k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-");
//        System.out.println(stationsforgame);
//        System.out.println("k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-k-");
        reset(cooksforgame,unusedcooksforgame,customersforgame,stationsforgame);
//        System.out.println(root);

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
        customerController.setMode("scenario");
        customerController.setDifficulty(root.getInt("difficulty"));

    }
}


