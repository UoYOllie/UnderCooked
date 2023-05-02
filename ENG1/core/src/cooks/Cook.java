//DC
package cooks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import food.FoodItem;
import game.GameScreen;
import game.GameSprites;
import static helper.Constants.PPM;

import food.FoodStack;
import food.DishStack;
import food.FoodItem.FoodID;
import helper.Constants;
import helper.NewCollisionHelper;
import interactions.InputKey;
import interactions.Interactions;
import stations.CookInteractable;
import stations.Station;

import java.util.ArrayList;

/** A {@link GameEntity} that the player controls to interact with the game. */

public class Cook extends GameEntity {
    public boolean lockmovement;


    /** The cook's current sprite. */
    private Sprite sprite;
    /** The control arrow sprite. */
    private Sprite controlSprite;
    private GameSprites gameSprites;
    //private CookInteractor cookInteractor;
    // private GameScreen gameScreen;
    /** The direction this cook is facing. */
    public Facing dir;
    /** The cook's stack of things, containing all the items they're holding. Index 0 = Top Item */
    public FoodStack foodStack;
    public DishStack dishStack;

    /** The WASD/movement inputs currently being made.
     * Note that if S and D are being input at the same time, then
     * inputs = { Facing.RIGHT, Facing.DOWN }
     */
    public Array<Facing> inputs;

    private GameScreen gameScreen;
    public boolean activateBluggus; //Determines if this cook is a Chef Bluggus

    /**
     * Rectangle for cook's interaction area.
     * We use this area to determine if the chef is capable of interacting with objects in the world
     *
     * Note: Rectangles are actually OP, I love them so much. Rectangles will handle anything from collisions to making
     * sure your hands are in the right place, we should use them more :)))
     */
    public Rectangle cookInteractor; //The rectangle that interacts with stations

    public float movement_speed = 0.6765f; //The speed of the cook
    //The colour of the cook - if a colour was to be implemented to distinguish cooks.
    public String Colour;



    /**
     * Sets the speed for the player chef ???
     * There are question marks there because interestingly, that is *not* what this does.
     *
     * No Params (????)
     */

    /** All possible directions the cook can be facing. */
    public enum Facing {
        RIGHT,
        LEFT,
        UP,
        DOWN,
        NONE
    }

    /**
     * Cook constructor
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Cook(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.lockmovement = false;
        this.dir = Facing.DOWN;
        this.speed = 10f;
        this.Colour = "Blank";
        // this.gameScreen = gameScreen;
        this.gameSprites = GameSprites.getInstance();
        this.controlSprite = gameSprites.getSprite(GameSprites.SpriteID.COOK,"CONTROL");

        this.activateBluggus = false;

        // Initialize FoodStack
        this.foodStack = new FoodStack();
        this.foodStack2 = new FoodStack();
        this.dishStack = new DishStack();

        // Input array, with the order of inputs the user has in what direction.
        // The oldest button pressed is the one used. Pressing the opposite key removes them.
        this.inputs = new Array<>();

        // Set the sprite
        this.setSprite();

        // Defines the bounds for interaction box, using 1/8f as the unit-scale because its not a const rn.
        cookInteractor = new Rectangle(x - 4 * Constants.UnitScale, y - 4 * Constants.UnitScale, width + 1f, height + 1f);

    }

    /** Responsible for processing user input information into {@link #inputs}, {@link #velX} and {@link #velY}. */
    /**
     * @param mapObstacles
     */
    public void userInput(ArrayList<Rectangle> mapObstacles) {
        velX = 0F;
        velY = 0F;
        if(this.lockmovement == false) {
            if (Interactions.isPressed(InputKey.InputTypes.COOK_RIGHT)) {
                velX = movement_speed;
                if (!inputs.contains(Facing.RIGHT, true)) {
                    inputs.add(Facing.RIGHT);
                }
            } else {
                inputs.removeValue(Facing.RIGHT, true);
            }
            if (Interactions.isPressed(InputKey.InputTypes.COOK_LEFT)) {
                velX = -movement_speed;
                if (!inputs.contains(Facing.LEFT, true)) {
                    inputs.add(Facing.LEFT);
                }
            } else {
                inputs.removeValue(Facing.LEFT, true);
            }
            if (Interactions.isPressed(InputKey.InputTypes.COOK_UP)) {
                velY = movement_speed;
                if (!inputs.contains(Facing.UP, true)) {
                    inputs.add(Facing.UP);
                }
            } else {
                inputs.removeValue(Facing.UP, true);
            }
            if (Interactions.isPressed(InputKey.InputTypes.COOK_DOWN)) {
                velY = -movement_speed;
                if (!inputs.contains(Facing.DOWN, true)) {
                    inputs.add(Facing.DOWN);
                }
            } else {
                inputs.removeValue(Facing.DOWN, true);
            }

            //Looks for potential collisions
            Rectangle newPlayerRectangle = new Rectangle(this.x + velX, this.y, this.width, this.height);
            for (Rectangle obstacle : mapObstacles) {
                if (Intersector.overlaps(obstacle, newPlayerRectangle)) {
                    if (velX < 0) {
                        newPlayerRectangle.x = obstacle.x + obstacle.width;
                    } else if (velX > 0) {
                        newPlayerRectangle.x = obstacle.x - newPlayerRectangle.width;
                    }
                }
            }

            newPlayerRectangle.y += velY;

            for (Rectangle obstacle : mapObstacles) {
                if (Intersector.overlaps(obstacle, newPlayerRectangle)) {
                    if (velY < 0) {
                        newPlayerRectangle.y = obstacle.y + obstacle.height;
                    } else if (velY > 0) {
                        newPlayerRectangle.y = obstacle.y - newPlayerRectangle.height;
                    }
                }
            }

            setDir();

            this.rectangle = newPlayerRectangle;
            this.x = rectangle.x;
            this.y = rectangle.y;
        }
    }

    /**
     * Checks for something it can interact with, if i can interact with a station,
     * it will run the interact command on that station.
     * @param mapStations
     */
    public void userInteract(ArrayList<Station> mapStations){

        for (InputKey inputKey : Interactions.getInputKeys(Interactions.InputID.COOK_INTERACT)) {
            if (Gdx.input.isKeyJustPressed(inputKey.getKey())) {
                NewCollisionHelper checker = new NewCollisionHelper(gameScreen,this,mapStations);
                CookInteractable station = checker.NearbyStation(cookInteractor);
                if(station!=null) {
                    station.interact(this, inputKey.getType());
                }

            }
        }

    }


    /**
     * Checks if this cook's dishstack size is greater than 1
     * @return true if > 0
     * @return false otherwise
     */
    public boolean getBlocked() {
        if (this.dishStack.size() > 0) {
            return true;
        } else {
            return false;
        }
    }



    /**
     * The update function for the {@link Cook}, which updates the {@link Cook}'s
     * {@link #x} and {@link #y} values, and updates the position of the
     * {@link Cook}'s {@link CookInteractor}.
     * @param delta The time between frames as a float.
     */
    @Override
    public void update(float delta) {
        //y = body.getPosition().y*PPM;
        x = rectangle.x;
        y = rectangle.y;

        // Updates Interaction box (again change 1/8f to a const)
        this.cookInteractor.x = x - 1/8f;
        this.cookInteractor.y = y - 1/8f;
    }

    /**
     * Update the current {@link Sprite} of the {@link Cook}.
     */
    private void setSprite() {
        // Set up sprite string
        String spriteName = "";
        // If holding something, add "h" to the start of the sprite name.
//        if (foodStack.size() > 0 || dishStack.size() >0) {
//            spriteName += "h";
//        }
        sprite = gameSprites.getSprite(GameSprites.SpriteID.COOK, spriteName + dir);
    }

    /**
     * Render the {@link Cook} and their {@link FoodStack}.
     * @param batch The {@link SpriteBatch} that the {@link Cook} will render using.
     */
    @Override
    public void render(SpriteBatch batch) {
        setSprite();
        sprite.setPosition(x-width/8-0.5f,y-height/8); // -2.5 for a similar reason to the below one
        this.sprite.setSize(5,8);

        // If the cook is looking anywhere but down, draw the food first
        if (dir != Facing.DOWN) {
            renderFood(batch);
            sprite.draw(batch);
            //System.out.println("rendering the chef!");
        } else {
            sprite.draw(batch);
            renderFood(batch);
        }
    }

    /**
     * Renders the red arrow to show user which cook they are currently using
     * @param batch
     */
    public void renderControlArrow(SpriteBatch batch) {
        controlSprite.setSize(3,1.5f);
        controlSprite.setPosition((x-controlSprite.getWidth()/8+0.66f),
                y-controlSprite.getHeight()/8 + sprite.getHeight()+1);
        controlSprite.draw(batch);
    }

    /**
     * Rendering using the {@link ShapeRenderer}. Unused.
     * @param shape The {@link ShapeRenderer} used to draw.
     */
    @Override
    public void renderShape(ShapeRenderer shape) {

    }


    /** Return the X pixel offset from the cook's position that the cook's FoodStack requires for rendering.*/
    /**
     *
     * @param dir
     * @return X pixel offset
     */
    public float foodRelativeX(Cook.Facing dir) {
        switch (dir) {
            case RIGHT:
                return 38F * Constants.UnitScale;
            case LEFT:
                return -13F * Constants.UnitScale;
            default:
                return 13F * Constants.UnitScale;
        }
    }

    /** Return the Y pixel offset from the cook's position that the cook's FoodStack requires for rendering.*/
    /**
     * @param dir
     * @return Y pixel offset
     */
    public float foodRelativeY(Cook.Facing dir) {
        switch (dir) {
            case UP:
                return -14F * Constants.UnitScale;
            case DOWN:
                return -25F * Constants.UnitScale;
            case LEFT:
            case RIGHT:
                return -24F * Constants.UnitScale;
            default:
                return 0F * Constants.UnitScale;
        }
    }

    /**
     * Renders the {@link FoodStack} of the {@link Cook} visually.
     * @param batch The {@link SpriteBatch} that the {@link Cook} will render using.
     */
    private void renderFood(SpriteBatch batch) {
        // Loop through the items in the food stack.
        // It is done from the end of the stack to the start because the stack's top is
        // at 0, and the bottom at the end.
        Array<FoodID> foodList = foodStack.getStack();
        float xOffset = foodRelativeX(dir), yOffset = foodRelativeY(dir);
        // Get offset based on direction.

        float drawX = x, drawY = y - 12 * Constants.UnitScale;
        for (int i = foodList.size-1 ; i >= 0 ; i--) {
            Sprite foodSprite = gameSprites.getSprite(GameSprites.SpriteID.FOOD, String.valueOf(foodList.get(i)));
            Float drawInc = FoodItem.foodHeights.get(foodList.get(i));
            if (drawInc == null) {
                drawY += 5F;
                continue;
            }
            foodSprite.setScale(2 * Constants.UnitScale);
            foodSprite.setPosition(drawX-foodSprite.getWidth()/2+xOffset,drawY+yOffset);
            foodSprite.draw(batch);
            drawY += drawInc;
        }

        Array<FoodID> foodList2 = foodStack2.getStack();

        xOffset = foodRelativeX(dir);
        yOffset = foodRelativeY(dir);

        drawX = x - 8f;
        drawY = y - 12 * Constants.UnitScale;

        for (int i = foodList2.size-1 ; i >= 0 ; i--) {
            Sprite foodSprite = gameSprites.getSprite(GameSprites.SpriteID.FOOD, String.valueOf(foodList2.get(i)));
            Float drawInc = FoodItem.foodHeights.get(foodList2.get(i));
            if (drawInc == null) {
                drawY += 5F;
                continue;
            }
            foodSprite.setScale(2 * Constants.UnitScale);
            foodSprite.setPosition(drawX-foodSprite.getWidth()/2+xOffset,drawY+yOffset);
            foodSprite.draw(batch);
            drawY += drawInc;
        }


        // render DishStack

        Array<FoodID> dishList = dishStack.getStack();
        xOffset = foodRelativeX(dir);
        yOffset = foodRelativeY(dir);
        // Get offset based on direction.

        drawX = x;
        drawY = y - 12 * Constants.UnitScale;

        for (int i = dishList.size-1 ; i >= 0 ; i--) {
            Sprite foodSprite = gameSprites.getSprite(GameSprites.SpriteID.FOOD, String.valueOf(dishList.get(i)));
            Float drawInc = FoodItem.foodHeights.get(dishList.get(i)) * 0.5F;
            if (drawInc == null) {
                drawY += 5F;
                continue;
            }
            foodSprite.setScale(2 * Constants.UnitScale);
            foodSprite.setPosition(drawX-foodSprite.getWidth()/2+xOffset,drawY+yOffset);
            foodSprite.draw(batch);
            drawY += drawInc;
        }
    }

    /**
     * Return the opposite direction to the input direction.
     * @param direction The input direction you want an opposite for.
     * @return The opposite direction to the input.
     */
    public Facing opposite(Facing direction) {
        switch(direction) {
            case UP:
                return Facing.DOWN;
            case DOWN:
                return Facing.UP;
            case RIGHT:
                return Facing.LEFT;
            case LEFT:
                return Facing.RIGHT;
            default:
                return Facing.NONE;
        }
    }

    /**
     * Return the direction 90 degrees clockwise to the input direction.
     * @param direction The input direction you want a 90c rotation for.
     * @return The 90c rotation direction to the input.
     */
    public Facing rotate90c(Facing direction) {
        switch(direction) {
            case UP:
                return Facing.RIGHT;
            case DOWN:
                return Facing.LEFT;
            case RIGHT:
                return Facing.DOWN;
            case LEFT:
                return Facing.UP;
            default:
                return Facing.NONE;
        }
    }

    /**
     * A function to find where the {@link Cook} should be
     * facing depending on the order of inputs, the latest
     * being prioritized, and ignoring any inputs that are
     * input with their opposite.
     *
     * For example, pressing {Left, Up, Right} in the same
     * order. Right is prioritized as it is the newest input,
     * but the opposite Left was pressed, so Up is the
     * final choice of direction.
     */
    private void setDir() {
        // If the size of inputs is 0, just return and change nothing.
        if (inputs.size == 0) { return; }

        // Possible next direction is the direction that was just input
        Facing possibleNext = inputs.get(inputs.size-1);
        Facing possibleOpp = opposite(possibleNext);
        // If there is the opposite input...
        if (inputs.contains(possibleOpp, true)) {
            // Now check that the same does not apply to the other directions.
            boolean hasPossibleRotated = inputs.contains(rotate90c(possibleNext), true),
                    hasOppRotated = inputs.contains(rotate90c(possibleOpp),true);
            if (hasPossibleRotated ^ hasOppRotated) {
                // If it doesn't, set the direction to the one that is there.
                if (hasPossibleRotated) {
                    dir = rotate90c(possibleNext);
                } else {
                    dir = rotate90c(possibleOpp);
                }
            }
            // If both or neither of them are there, then change nothing.
        } else {
            // If the opposite isn't there, it's fine to switch.
            dir = possibleNext;
        }
    }

    /**
     * This is used for when a chef goes chef bluggus mode as a powerup, to allow the cook
     * to hold <= 6 Food items
     */
    public FoodStack foodStack2;

    /**
     * Turns cook to chef bluggus
     * Used by powerup
     */
    public void MakeIntoBluggus()
    {
        this.activateBluggus = true;
    }

    /**
     * Used by Chef Bluggus powerups to actively move items between the 2 stacks the cook can hold
     * This allows the cook to always to only use stack 1.
     */
    public void moveStacks()
    {
        if((this.foodStack.size() > 1)&&(this.foodStack2.size() < 3)) {
            this.foodStack2.addStack(foodStack.peekStack());
            this.foodStack.popStack();
        }
        else if((this.foodStack.size() == 0)&&(this.foodStack2.size() > 0)){
            this.foodStack.addStack(foodStack2.peekStack());
            this.foodStack2.popStack();
        }
    }

    /**
     * Takes a string of a colour
     * @param c the colour of to set
     * Sets the colour of this cook to @param c
     */
    public void setColour(String c)
    {
        this.Colour = c;
    }
}
