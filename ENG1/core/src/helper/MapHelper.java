package helper;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import food.FoodItem;
import game.GameScreen;
import game.SuperMapSuperRenderer;
import stations.*;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

/** The {@link MapHelper} class helps by setting up the map
 * of the game, and providing the {@link OrthogonalTiledMapRenderer}
 * which is used to draw the {@link TiledMap}.*/
public class MapHelper {
    private GameScreen gameScreen;
    private TiledMap tiledMap;
    private ArrayList<Rectangle> mapObstacles;
    public ArrayList<Station> mapStations;
    private ArrayList<Station> ServingStationList;


    /**
     * Constructor for MapHelper.
     * Initialises arrayLists mapObstacles and mapStations.
     */
    public MapHelper(GameScreen g) {
        mapObstacles = new ArrayList<>();
        mapStations = new ArrayList<>();
        ServingStationList = new ArrayList<>();
        this.gameScreen = g;
    }

    public MapHelper(ArrayList<Rectangle> obstacles,ArrayList<Station> stations,ArrayList<Station> ServingStation) {
        mapObstacles = obstacles;
        mapStations = stations;
        ServingStationList = ServingStation;
    }

    /**
     * IN CHARGE OF RENDERING THE MAP
     * Sets up the map by loading the StationsMap tilemap, and then using
     * the function to parse and
     * load it into the game.
     * @return The {@link OrthogonalTiledMapRenderer} used to render the Tilemap.
     *
     */
    public SuperMapSuperRenderer setupMap()
    {
        //IGNORE
        tiledMap = new TmxMapLoader().load("MorgansMap/AWholeNewWorld.tmx"); //<---PUT MAP FILE
        parseMapObjects(tiledMap); //<--keep it for now
        return new SuperMapSuperRenderer(tiledMap, 1/8f);
    }


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
        }

        // INTERACTION OBJECTS

        // Get all objects from the interaction layer.
        MapObjects interactionObjects = map.getLayers().get("Interaction Layer").getObjects();

        // Go through every object in the interaction layer and add an instance of the
        // corresponding station to the mapStations ArrayList.
        for (RectangleMapObject rectangleMapObject :
                interactionObjects.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = rectangleMapObject.getRectangle();
            Rectangle newRectangle = new Rectangle(rectangle.x*1/8f, ((rectangle.y) * 1/8f),
                    rectangle.width * 1/8f, rectangle.height*1/8f);
            String stationName = rectangleMapObject.getName();
            // Check which instance of Station and add the corresponding object to mapStations.
            switch(stationName) {
                case "BinStation":
                    BinStation binStation = new BinStation(newRectangle);
                    mapStations.add(binStation);
                    break;
                case "CounterStation":
                    CounterStation cs = new CounterStation(newRectangle);
                    mapStations.add(cs);
                    break;
                case "PreparationStation":
                    String t = rectangleMapObject.getProperties().get("type").toString();
                    PreparationStation prepStation = new PreparationStation(newRectangle,gameScreen);
                    switch(t) {
                        case "cut":
                            prepStation.setID(Station.StationID.cut);
                            break;
                        case "fry":
                            prepStation.setID(Station.StationID.fry);
                            break;
                        case "bake":
                            prepStation.setID(Station.StationID.bake);
                            break;
                    }
                    mapStations.add(prepStation);
                    break;
                case "AssemblyStation":
                    AssemblyStation as = new AssemblyStation(newRectangle);
                    mapStations.add(as);
                    break;
                case "ServingStation":
                    ServingStation ss = new ServingStation(newRectangle);
                    mapStations.add(ss);
                    ServingStationList.add(ss);
                    break;
                case "SpeedPowerup":
                    SpeedPowerup sp = new SpeedPowerup(newRectangle,gameScreen);
                    mapStations.add(sp);
                    break;
                case "VAT":
                    VAT v = new VAT(newRectangle,(rectangleMapObject.getProperties().get("Person")).toString(),gameScreen);
                    mapStations.add(v);
                    break;
                case "Pantry":
                    Pantry pantry = new Pantry(newRectangle);
                    pantry.setItem(FoodItem.foods.get(rectangleMapObject.getProperties().get("Food"))); // lmao hashmap is sus
                    mapStations.add(pantry);
                    break;
                case "poweritem":
                    PowerupPantry powerpantry = new PowerupPantry(newRectangle,gameScreen);
                    powerpantry.setItem(FoodItem.foods.get(rectangleMapObject.getProperties().get("Food"))); // lmao hashmap is sus

                    mapStations.add(powerpantry);
                    break;
                case "Freeze":
                    FreezeTimeStation fts = new FreezeTimeStation(newRectangle,gameScreen);
                    mapStations.add(fts);
                    break;
                case "BluggusMode":
                    BluggusModeActivate bma = new BluggusModeActivate(newRectangle,gameScreen);
                    mapStations.add(bma);
                    break;
                case "Locked":
                    String ts = rectangleMapObject.getProperties().get("type").toString();
                    PreparationStation lockedprep = new PreparationStation(newRectangle,gameScreen);
                    switch(ts) {
                        case "cut":
                            lockedprep.setID(Station.StationID.cut);
                            break;
                        case "fry":
                            lockedprep.setID(Station.StationID.fry);
                            break;
                        case "bake":
                            lockedprep.setID(Station.StationID.bake);
                            break;
                    }
                    lockedprep.Locked = true;
                    mapStations.add(lockedprep);
                    break;


            }
            int i = 0;
            for(Station s:mapStations)
            {
                s.setPropertyID(i);
                i++;
            }

        }
    }


    public ArrayList<Rectangle> getMapObstacles() {
        return mapObstacles;
    }

    public ArrayList<Station> getMapStations() {
        return mapStations;
    }


    public ArrayList<Station> getServingStationList() {return ServingStationList;}

}
