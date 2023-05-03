package stations;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import players.Cook;
import food.DishStack;
import food.FoodStack;
import game.GameSprites;
import interactions.InputKey;

/**
 * The default Station class used as a parent for other stations.
 * */
public class Station extends CookInteractable {

    /** Indicates whether the station is currently locked or usable. */
    private Boolean Enabled = true;
    public Boolean isABluggusPrison;
    public FoodStack stationFoodStack;
    public DishStack stationDishStack;
    public boolean Locked;

    /** IDs of all types of PreparationStation. */
    public enum StationID {
        fry,
        cut,
        bake,
        assembly,
        serving,
        counter,
        bin;
    }

    StationID stationID;
    public boolean inUse;
    GameSprites gameSprites;

    private int PropertyID;

    /**
     * Constructor for Station.
     * @param rectangle The station's interaction rectangle.
     */
    public Station(Rectangle rectangle) {
        super(rectangle);
        inUse = false;
        isABluggusPrison = false;
        this.gameSprites = GameSprites.getInstance();
        this.setPropertyID(-1);
        this.stationDishStack = new DishStack();
        this.stationFoodStack = new FoodStack();
        this.Locked = false;
    }

    /** The setter for the PropertyID. */
    public void setPropertyID(int x){this.PropertyID = x;}

    /** The getter for the PropertyID. */
    public int getPropertyID(){return this.PropertyID;}

    /** Setter for the stationID of the station. */
    public void setID(StationID stationID) {
        this.stationID = stationID;
    }

    /** Setter for enabled to false.*/
    public void SetEnFalse() { this.Enabled = false; }

    /** Getter for Enabled boolean valie.*/
    public Boolean getEnabled() { return this.Enabled; }

    /** Disable the station.*/
    public void Disable() { this.Enabled = false; }

    /** Enable the station.*/
    public void Enable() { this.Enabled = true; }

    /** The method to control interactions between a cook and the AssemblyStation.
     *
     * @param cook The cook currently interacting with the AssemblyStation.
     * @param inputType The input received from the cook currently interacting.
     */
    public void interact(Cook cook, InputKey.InputTypes inputType) { }

    /**
     * An update function used by the GameScreen if the Station needs updating.
     * This is only used by PreparationStation.
     * @param delta The time between frames as a float.
     */
    @Override
    public void update(float delta) { }

    /**
     * The function used to render items onto the Station.
     * @param batch the SpriteBatch to be rendered.
     */
    @Override
    public void render(SpriteBatch batch) { }

    /**
     * The function used to render the progress bars.
     * It is only used by the PreparationStation.
     * @param shape The ShapeRenderer used to render.
     */
    @Override
    public void renderShape(ShapeRenderer shape) { }
}

