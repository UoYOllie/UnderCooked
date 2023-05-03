package stations;

import com.badlogic.gdx.math.Rectangle;
import players.Cook;
import players.Customer;
import players.GameEntity;
import interactions.InputKey;

/** The CookInteractable Class. This is basically stations and pantries.*/
public abstract class CookInteractable extends GameEntity {
    /** The rectangle representing the collision of this interactable. */
    protected Rectangle interactRect;
    public float progress;

    /**
     * Calls the parent function, {@link GameEntity}, and sets the
     * {@link #interactRect} of the {@link CookInteractable} used for
     * Checking if the {@link CookInteractable} is overlapping the {@link CookInteractable}.
     * @param rect The collision and interaction area of the {@link CookInteractable}.
     */
    public CookInteractable(Rectangle rect) {
        super(rect);
        this.interactRect = rect;
        this.progress = 100;
    }

    /**
     * Get the interaction {@link Rectangle}, which is used to check
     * the overlaps against another {@link CookInteractable}.
     * @return The {@link Rectangle} of the {@link CookInteractable}.
     */
    public Rectangle getRectangle() {
        return this.interactRect;
    }

    /**
     * Allows a {@link Cook} to interact with the {@link CookInteractable}.
     * @param cook The cook that interacted with the {@link CookInteractable}.
     * @param inputType The type of {@link InputKey.InputTypes} the player made with
     *                  the {@link CookInteractable}.
     */
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        System.out.println("Im interacting with:");
        cook.lockmovement = false;
        //System.out.println("Interaction at x=" + body.getPosition().x*PPM + ", y=" + body.getPosition().x*PPM);
    }

    public void customerInteract(Customer customer) {
        //System.out.println("customer is interacting with:");
    }


}
