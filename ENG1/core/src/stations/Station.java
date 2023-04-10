package stations;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import cooks.Cook;
import game.GameScreen;
import game.GameSprites;
import interactions.InputKey;

/**
 * The default Station class used as a parent for other stations.
 * */
public class Station extends CookInteractable {

    /** Indicates whether the station is currently locked or usable. */
    public Boolean Enabled = true;

    /** IDs of all types of PreparationStation. */
    public enum StationID {
        /** Frying Station. */
        fry,
        /** Cutting Station. */
        cut,
        /** Baking Station. */
        bake,
        /** Assembly Station. */
        assembly;
    }

    StationID stationID;
    public boolean inUse;
    GameSprites gameSprites;
    //public Cook CurrentCook;

    /**
     * Constructor for Station.
     * @param rectangle The station's interaction rectangle.
     */
    public Station(Rectangle rectangle) {
        super(rectangle);
        inUse = false;
        this.gameSprites = GameSprites.getInstance();
    }

    /**
     * Sets the stationID of the station.
     * This is only used by PreparationStation.
     */
    public void setID(StationID stationID) {
        this.stationID = stationID;
    }

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

//    /**
//     * The function used to render the {@link Station}'s
//     * debug visuals.
//     * @param batch The {@link SpriteBatch} used to render.
//     */
//    @Override
//    public void renderDebug(SpriteBatch batch) { }


//    /**
//     * The function used to render the {@link Station}'s
//     * debug visuals.
//     * @param shape The {@link ShapeRenderer} used to render.
//     */
//    @Override
//    public void renderShapeDebug(ShapeRenderer shape) {
//
//    }
}
