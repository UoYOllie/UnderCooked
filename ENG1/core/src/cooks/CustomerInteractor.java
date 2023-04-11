package cooks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import game.GameScreen;
import helper.CollisionHelper;
import interactions.InputKey;
import stations.CookInteractable;

/**
 * The Cook's in-game Collision and Detection Class
 */
public class CustomerInteractor {

    /** The X and Y Coordinates of this {@link CookInteractor}. */
    public float x,y;
    /** The size of this {@link CookInteractor}. */
    public float size;
    /** The rectangle responsible for collision in {@link CookInteractor}. */
    public Rectangle collision;
    /** The Collision Helper Singleton. */
    protected CollisionHelper ch;

    private GameScreen gameScreen;

    /**
     * CookInteractor Constructor.
     * @param x The {@link #x} coordinate of the {@link CookInteractor}.
     * @param y The {@link #y} coordinate of the {@link CookInteractor}.
     * @param size The size of CookInteractor's {@link GameEntity}.
     */
    public CustomerInteractor(float x, float y, float size, GameScreen g) {
        System.out.println("---------------------");
        System.out.println(x);
        System.out.println(y);
        System.out.println(size);
        System.out.println("---------------------");
        this.size = size;
        this.x = x;
        this.y = y;
        this.collision = new Rectangle(x* 1/8f,y* 1/8f,3.34f,4);
        System.out.println("Collision rectangle loading:");
        System.out.println(this.collision);
        //this.ch = g.getCollisionHelper();
        this.gameScreen = g;
    }

    public CustomerInteractor(float x, float y, float size) {
        this.size = size;
        this.x = x;
        this.y = y;
        this.collision = new Rectangle(x,y,size,size);
        this.ch = new CollisionHelper();
    }




    /**
     * Update the Position of the CookInteractor to the next x, y position
     * and given direction
     * @param x New X position
     * @param y New Y position
     * @param dir New direction
     */
    public void updatePosition(float x, float y, Cook.Facing dir) {

        this.x = x;
        this.y = y;

        this.collision.x = this.x - collision.width/2;
        this.collision.y = this.y - collision.height/2;
    }

    /**
     * Check for any collisions the CookInteractor has made
     * @param cook The cook
     * @param inputType The enum constant of the input made
     */
    public void checkCollisions(Cook cook, InputKey.InputTypes inputType) {
        CookInteractable interactStation = ch.getInteract(cook, collision);
        System.out.println(interactStation);
        if (interactStation != null) {
            System.out.println("Station found");
            interactStation.interact(cook, inputType);
            System.out.println("interacting");

        }
    }

    /**
     * The debug render function, used to render the {@link CookInteractor}'s
     * collision box.
     * @param shape The {@link ShapeRenderer} used to render.
     */
    public void renderDebug(ShapeRenderer shape) {
        shape.set(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.BLACK);
        shape.rect(collision.x,collision.y,size,size);
        shape.setColor(Color.WHITE);
        shape.set(ShapeRenderer.ShapeType.Filled);
    }

}