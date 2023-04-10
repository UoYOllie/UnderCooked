//package cooks;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//import com.badlogic.gdx.math.Intersector;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.Body;
//import com.badlogic.gdx.physics.box2d.World;
//import com.badlogic.gdx.utils.Array;
//import food.FoodItem;
//import game.GameScreen;
//import game.GameSprites;
//import static helper.Constants.PPM;
//
//import food.FoodStack;
//import food.DishStack;
//import food.FoodItem.FoodID;
//import helper.Constants;
//import helper.NewCollisionHelper;
//import interactions.InputKey;
//import interactions.Interactions;
//import stations.CookInteractable;
//import stations.Station;
//
//import java.util.ArrayList;
//
///** A {@link GameEntity} that the player controls to interact with the game. */
//public class CustomerNew extends GameEntity {
//
//
//    /** The cook's current sprite. */
//    private Sprite sprite;
//    /** The control arrow sprite. */
//    private Sprite controlSprite;
//    private GameSprites gameSprites;
//    public DishStack dishStack;
//
//    /**
//     * Rectangle for cook's interaction area.
//     * We use this area to determine if the chef is capable of interacting with objects in the world
//     *
//     * Note: Rectangles are actually OP, I love them so much. Rectangles will handle anything from collisions to making
//     * sure your hands are in the right place, we should use them more :)))
//     */
//    private Rectangle cookInteractor;
//
//
//    /**
//     * Cook Constructor.
//     * @param x : X value that the Cook will spawn at.
//     * @param y : Y Value that the Cook will spawn at
//     * @param width : Width of the player's hitbox.
//     * @param height : Height of the player's hitbox (Not the height of the player sprite).
//     */
//    public CustomerNew(float x, float y, float width, float height) {
//        super(x, y, width, height);
//        // this.gameScreen = gameScreen;
//        this.gameSprites = GameSprites.getInstance();
//
//        this.dishStack = new DishStack();
//
//
//        // Set the sprite
//        this.setSprite();
//
//        // Defines the bounds for interaction box, using 1/8f as the unit-scale because its not a const rn.
//        cookInteractor = new Rectangle(x - 4 * Constants.UnitScale, y - 4 * Constants.UnitScale, width + 1f, height + 1f);
//    }
//
//
//    /**
//     * The update function for the {@link Cook}, which updates the {@link Cook}'s
//     * {@link #x} and {@link #y} values, and updates the position of the
//     * {@link Cook}'s {@link CookInteractor}.
//     * @param delta The time between frames as a float.
//     */
//    @Override
//    public void update(float delta) {
//        //y = body.getPosition().y*PPM;
//        x = rectangle.x;
//        y = rectangle.y;
//
//        // Updates Interaction box (again change 1/8f to a const)
//        this.cookInteractor.x = x - 1/8f;
//        this.cookInteractor.y = y - 1/8f;
//    }
//
//    /**
//     * Update the current {@link Sprite} of the {@link Cook}.
//     */
//    private void setSprite() {
//        // Set up sprite string
//        String spriteName = "";
//        // If holding something, add "h" to the start of the sprite name.
//        if (dishStack.size() >0) {
//            spriteName += "h";
//        }
//        sprite = gameSprites.getSprite(GameSprites.SpriteID.CUSTOMER);
//    }
//
//    /**
//     * Render the {@link Cook} and their {@link FoodStack}.
//     * @param batch The {@link SpriteBatch} that the {@link Cook} will render using.
//     */
//    @Override
//    public void render(SpriteBatch batch) {
//        //sprite.setPosition(position.x-sprite.getWidth()/2, position.y-sprite.getHeight()/2);
//        sprite.draw(batch);
//    }
//
//    public void renderFood(SpriteBatch batch) {}
//
//    @Override
//    public void renderDebug(SpriteBatch batch) { }
//
//    @Override
//    public void renderShape(ShapeRenderer shape) { }
//
//}
