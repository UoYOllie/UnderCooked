package helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import cooks.Cook;
import food.FoodItem;
import game.GameScreen;
import stations.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static helper.Constants.PPM;

/** The {@link MapHelper} class helps by setting up the map
 * of the game, and providing the {@link OrthogonalTiledMapRenderer}
 * which is used to draw the {@link TiledMap}.*/
public class MapHelper {
    private GameScreen gameScreen;
    private TiledMap tiledMap;
    private ArrayList<Rectangle> mapObstacles;
    private ArrayList<Station> mapStations;


    /**
     * Constructor for MapHelper.
     * Initialises arrayLists mapObstacles and mapStations.
     */
    public MapHelper() {
        mapObstacles = new ArrayList<>();
        mapStations = new ArrayList<>();
    }

    /**
     * IN CHARGE OF RENDERING THE MAP
     * Sets up the map by loading the StationsMap tilemap, and then using
     * the function to parse and
     * load it into the game.
     * @return The {@link OrthogonalTiledMapRenderer} used to render the Tilemap.
     *
     */
    public OrthogonalTiledMapRenderer setupMap()
    {
        //IGNORE
        tiledMap = new TmxMapLoader().load("MorgansMap/AWholeNewWorld.tmx"); //<---PUT MAP FILE
        parseMapObjects(tiledMap); //<--keep it for now
        return new OrthogonalTiledMapRenderer(tiledMap, 1/8f);
    }

//    /**
//     * Creates a Static {@link Body} added to the map that is used
//     * to stop the {@link Cook} from moving through certain places.
//     * @param polygonMapObject
//     */

//    private void createStaticBody(PolygonMapObject polygonMapObject)
//    {
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.StaticBody;
//        Body body = gameScreen.getWorld().createBody(bodyDef);
//        Shape shape = createPolygonShape(polygonMapObject);
//        body.createFixture(shape, 1000);
//        shape.dispose();
//    }



//    /**
//     * Creates a Polygon{@link Shape} using the {@link PolygonMapObject}.
//     * <br>It is used to create the {@link Shape} for the
//     * {@link #createStaticBody(PolygonMapObject)} function for
//     * the {@link Body}'s {@link com.badlogic.gdx.physics.box2d.Fixture}.
//     * @param polygonMapObject
//     * @return
//     */
//    private Shape createPolygonShape(PolygonMapObject polygonMapObject)
//    {
//        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
//        Vector2[] worldVertices = new Vector2[vertices.length / 2];
//        for(int i = 0;i<vertices.length / 2;i++)
//        {
//            Vector2 current = new Vector2(vertices[i * 2]/PPM, vertices[i*2+1]/PPM);
//            worldVertices[i] = current;
//        }
//        PolygonShape shape = new PolygonShape();
//        shape.set(worldVertices);
//        return shape;
//
//    }



//    /**
//     * Makes a {@link Body} using a {@link Rectangle} as a base.
//     * @param rectangle The {@link Rectangle} for the {@link Body}'s {@link Shape}.
//     * @param isStatic If true, then the {@link Body} is stationary.
//     *                 If false, then the {@link Body} is not stationary.
//     * @return {@link Body} : The {@link Body} created using {@link BodyHelper}.
//     */
//    public static Body makeBody(Rectangle rectangle, boolean isStatic)
//    {

//        return BodyHelper.createBody(rectangle.x + rectangle.getWidth() /2, rectangle.y +rectangle.getHeight()/2,rectangle.getWidth(), rectangle.getHeight(),isStatic, INSTANCE.gameScreen.getWorld());
//    }



    /**
     * IN CHARGE OF LINKING OBJECTS IN THE MAP TO THE CODE
     * Go through the object layers on TiledMap (Collision and Interaction layers)
     * and add these to the mapObstacles and mapStations ArrayLists.
     *
     * @param map - the TiledMap
     */
    private void parseMapObjects(TiledMap map) {

        // COLLISION OBJECTS

        // Get all objects from the collision layer.
        MapObjects obstacleObjects = map.getLayers().get("Collision Layer").getObjects();

        // Go through every object in the collision layer and add to
        // the mapObstacles ArrayList.
        for (RectangleMapObject rectangleMapObject :
                obstacleObjects.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = rectangleMapObject.getRectangle();
            Rectangle newRectangle = new Rectangle(rectangle.x*1/8f, rectangle.y * 1/8f,
                                    rectangle.width * 1/8f, rectangle.height*1/8f);

            mapObstacles.add(newRectangle);
            System.out.println(newRectangle.x);
            System.out.println(newRectangle.y);
            System.out.println(newRectangle.width);
            System.out.println(newRectangle.height);
            System.out.println(":)");
        }

        // INTERACTION OBJECTS

        // Get all objects from the interaction layer.
        MapObjects interactionObjects = map.getLayers().get("Interaction Layer").getObjects();

        // Go through every object in the interaction layer and add an instance of the
        // corresponding station to the mapStations ArrayList.
        for (RectangleMapObject rectangleMapObject :
                interactionObjects.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = rectangleMapObject.getRectangle();
            Rectangle newRectangle = new Rectangle(rectangle.x, rectangle.y,
                                                rectangle.width*PPM,
                                                rectangle.height*PPM);
            String stationName = rectangleMapObject.getName();

            // Check which instance of Station and add the corresponding object to mapStations.
            switch(stationName) {
                case "BinStation":
                    mapStations.add(new BinStation(newRectangle));
                    break;
                case "CounterStation":
                    mapStations.add(new CounterStation(newRectangle));
                    break;
                case "PreparationStation":
                    mapStations.add(new PreparationStation(newRectangle));
                    break;
                case "ServingStation":
                    mapStations.add(new ServingStation(newRectangle));
                    break;
            }
        }
    }




//    {
//        for(MapObject mapObject:mapObjects)
//        {
//            if(mapObject instanceof PolygonMapObject)
//            {
//                createStaticBody((PolygonMapObject) mapObject);
//            }
//
//            if(mapObject instanceof RectangleMapObject)
//            {
//                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
//                String rectangleName = mapObject.getName();
//
//                if(rectangleName.equals("CookStart"))
//                {
//                    Body body = makeBody(rectangle, false);
//                    int cookInd = gameScreen.addCook(new Cook(rectangle.getWidth(), rectangle.getHeight(), body, gameScreen));
//                    gameScreen.setCook(cookInd);
//                    continue;
//                }
//
//                if(rectangleName.equals("Cook"))
//                {
//                    Body body = makeBody(rectangle, false);
//                    gameScreen.addCook(new Cook(rectangle.getWidth(), rectangle.getHeight(), body, gameScreen));
//                    continue;
//                }
//
//                Rectangle normalRect = new Rectangle(rectangle);
//                normalRect.setX(normalRect.getX() * PPM);
//                normalRect.setY(normalRect.getY() * PPM);
//
//                if(rectangleName.startsWith("Station")) {
//                    // Stations
//                    rectangleName = rectangleName.substring("Station".length()).toLowerCase();
//                    Station station;
//                    switch(rectangleName) {
//                        case "cut":
//                            station = new PreparationStation(rectangle);
//                            station.setID(Station.StationID.cut);
//                            gameScreen.addGameEntity(station);
//                            break;
//                        case "fry"://change to cooking
//                            station = new PreparationStation(rectangle);
//                            station.setID(Station.StationID.fry);
//                            gameScreen.addGameEntity(station);
//                            break;
//                        case "counter":
//                            station = new CounterStation(rectangle);
//                            station.setID(Station.StationID.counter);
//                            gameScreen.addGameEntity(station);
//                            break;
//                        case "bin":
//                            station = new BinStation(rectangle);
//                            station.setID(Station.StationID.bin);
//                            break;
//                        case "serving": //Convert to assembly??
//                            station = new ServingStation(rectangle);
//                            station.setID(Station.StationID.serving);
//                            gameScreen.addGameEntity(station);
//                            gameScreen.addServingStation((ServingStation) station);
//                            ((ServingStation) station).setGameScreen(gameScreen);
//                            break;
//                            //add baking
//                            //add assembly/new serving
//                        default:
//                            station = new Station(rectangle);
//                            station.setID(Station.StationID.none);
//                            break;
//                    }
//                    gameScreen.addInteractable(station);
//                }
//
//                if (rectangleName.startsWith("Pantry")) {
//                    // Pantries
//                    rectangleName = rectangleName.substring("Pantry".length());
//                    Pantry pantry = new Pantry(rectangle);
//                    gameScreen.addInteractable(pantry);
//                    switch(rectangleName) {
//                        case "Lettuce":
//                            pantry.setItem(FoodItem.FoodID.lettuce);
//                            break;
//                        case "Tomato":
//                            pantry.setItem(FoodItem.FoodID.tomato);
//                            break;
//                        case "Onion":
//                            pantry.setItem(FoodItem.FoodID.onion);
//                            break;
//                        case "Meat":
//                            pantry.setItem(FoodItem.FoodID.meat);
//                            break;
//                        case "Bun":
//                            pantry.setItem(FoodItem.FoodID.bun);
//                            break;
//                        default:
//                            pantry.setItem(FoodItem.FoodID.none);
//                            break;
//                    }
//                }
//            }
//        }
//    }

    public ArrayList<Rectangle> getMapObstacles() {
        return mapObstacles;
    }

    public ArrayList<Station> getMapStations() {
        return mapStations;
    }

    /** Disposes of loaded tiledMap textures when no longer required. */


    public void dispose() {

        tiledMap.dispose();
    }

}
