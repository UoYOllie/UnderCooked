package cooks;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import helper.Constants;
import helper.MapHelper;
import sun.jvm.hotspot.debugger.win32.coff.COFFLineNumber;

import static helper.Constants.PPM;
import static java.lang.Math.abs;

/** The class for any object that can exist in the game world. */
public abstract class GameEntity {

    /** X and Y position coordinates.*/
    public float x,y;
    /** X and Y velocity of this GameEntity.*/
    public float velX,velY;
    /** Speed of this GameEntity. */
    public float speed;
    /** The width and height in pixels of this GameEntity.*/
    public  float width,height;
    /** The body of this GameEntity. */
    // protected Body body;

    //UPDATE;
    public Rectangle rectangle;

    private float coolY;

    public GameEntity(float x, float y, float width, float height)
    {
        //this.y = body.getPosition().y * PPM; UPDATE TO:
        this.x = x * 1/8f;
        this.y = y * 1/8f;

        this.rectangle = new Rectangle(this.x, this.y, width, height);

        // may be able to remove these two later
        this.width = width;
        this.height = height;

        // these might be moved to the Cook, doesn't need to be in the abstract class
        // since stations are never going to use them
        this.velX = 0;
        this.velY = 0;
        this.speed = 0;

        this.coolY = y - 4 * Constants.UnitScale;
    }

    public GameEntity(Rectangle rectangle) {
        this.rectangle = rectangle;
		this.x = rectangle.x;
		this.y = rectangle.y;
        this.coolY = y - 4 * Constants.UnitScale;
    }

    /**
     * The update function, used to update the {@link GameEntity}.
     * @param delta The time between frames as a float.
     */
    // from laura: could this be moved to cook ?? it's not being used by any other children.
    public abstract void update(float delta);


    /**
     * The render function, used to render the {@link GameEntity}.
     * @param batch The {@link SpriteBatch} used to render.
     */
    public abstract void render(SpriteBatch batch);

//    /**
//     * The debug render function, used to render the {@link GameEntity}'s
//     * debug visuals.
//     * @param batch The {@link SpriteBatch} used to render.
//     */
//    public abstract void renderDebug(SpriteBatch batch);


    /**
     * The render function, used to render the {@link GameEntity}.
     * @param shape The {@link ShapeRenderer} used to render.
     */
    public abstract void renderShape(ShapeRenderer shape);

    /**
     * A getter to get the {@link GameEntity}'s {@link #x} coordinate.
     * @return {@code float} : The {@link GameEntity}'s {@link #x}.
     */
    public float getX() {
        return x;
    }

    public float getWidth(){return this.width;}

    public float getHeight() {
        return this.height;
    }

    /**
     * A getter to get the {@link GameEntity}'s {@link #y} coordinate.
     * @return {@code float} : The {@link GameEntity}'s {@link #y}.
     */
    public float getY() {
        return y;
    }

    public float getCoolY() {
        return coolY;
    }

    /**
     * A getter to get this Game Entity's rectangle.
     * @return {@code Rectangle} : The {@link GameEntity}'s {@link #rectangle}
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setY(float x){this.y = x;}
}

