package players;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import helper.Constants;

import static java.lang.Math.abs;

/** The class for any object that can exist in the game world. */
public abstract class GameEntity {

    /** The x-coordinate and y-coordinate of this GameEntity.*/
    public float x,y;

    /** The X and Y velocities of this GameEntity.*/
    public float velX,velY;

    /** Speed of this GameEntity. */
    public float speed;

    /** The width and height in pixels of this GameEntity.*/
    public  float width,height;

    /** The interaction rectangle of this GameEntity.*/
    public Rectangle rectangle;

    /** The y-coordinate to fetch the y-offset without updating the entity's coordinates. */
    private float coolY;

    /** The constructor of game entity using manual coordinates and dimensions.
     *
     * @param x The x-coordinate of the GameEntity.
     * @param y The y-coordinate of the GameEntity.
     * @param width The width of the GameEntity.
     * @param height The height of the GameEntity.
     * */
    public GameEntity(float x, float y, float width, float height)
    {
        this.x = x * 1/8f;
        this.y = y * 1/8f;
        this.width = width;
        this.height = height;
        this.rectangle = new Rectangle(this.x, this.y, width, height);

        this.velX = 0;
        this.velY = 0;
        this.speed = 0;

        this.coolY = y - 4 * Constants.UnitScale;
    }

    /** The constructor of the GameEntity using a rectangle.
     *
     * @param rectangle containing the x, y, width, and height information.
     * */
    public GameEntity(Rectangle rectangle) {
        this.rectangle = rectangle;
		this.x = rectangle.x;
		this.y = rectangle.y;
        this.coolY = y - 4 * Constants.UnitScale;
    }

    /** Getter for the x-coordinate.*/
    public float getX() { return x; }

    /** Getter for the y-coordinate.*/
    public float getY() { return y; }

    /** Setter for the y-coordinate.*/
    public void setY(float x){this.y = x;}

    /** Getter for the offset Y used for serving customers.*/
    public float getCoolY() { return coolY; }

    /** Getter for the width.*/
    public float getWidth(){return this.width;}

    /** Getter for the height.*/
    public float getHeight() { return this.height; }

    /** Getter for the GameEntity's rectangle.*/
    public Rectangle getRectangle() { return rectangle; }

    /**
     * The update function, used to update the GameEntity.
     * @param delta The time between frames as a float.
     */
    public abstract void update(float delta);


    /** The render method used to render the GameEntity.
     * @param batch The SpriteBatch to be rendered.
     */
    public abstract void render(SpriteBatch batch);


    /** The render method used to render the GameEntity's shape where there
     * is a timer to display.
     * @param shape The ShapeRenderer used to render.
     */
    public abstract void renderShape(ShapeRenderer shape);
}

