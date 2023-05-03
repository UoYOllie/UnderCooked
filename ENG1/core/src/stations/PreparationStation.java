package stations;

import shop.ShopItem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import players.Cook;
import food.FoodItem;
import game.GameScreen;
import game.GameSprites;
import helper.Constants;
import interactions.InputKey;
import interactions.Interactions;

/**
 * The {@link PreparationStation} class, where the {@link Cook}
 * process {@link FoodItem}s into different {@link FoodItem}s to prepare
 * them to make a {@link food.Recipe}.
 */
public class PreparationStation extends Station {

    public Interactions.InteractionResult interaction;
    private int stepNum;
    public StationState state;

    private int usingchef;
    private GameScreen gameScreen;
    private int TestFlag = 0; //Normal mode = 0 , Test Mode = 1
    private ShopItem item;

    /**
     * The constructor for the {@link PreparationStation}.
     * @param rectangle The collision and interaction area of the {@link PreparationStation}.
     */
    public PreparationStation(Rectangle rectangle) {
        super(rectangle);
        this.usingchef = 0;
    }

    /**
     * Constructor using gamescreen
     * @param rectangle
     * @param g
     */
    public PreparationStation(Rectangle rectangle,GameScreen g) {
        super(rectangle);
        this.gameScreen = g;
        this.usingchef = 0;
        this.item = gameScreen.BuyableStation;
    }

    /**
     * An update function to be used by the {@link game.GameScreen}
     *
     * <br>It updates the {@link #progress} of the {@link interactions.Interactions.InteractionResult}
     * until it requires a {@link InputKey.InputTypes#USE} from the {@link Cook}
     * when the current {@code step} of the {@link interactions.Interactions.InteractionResult}
     * is reached.
     *  delta The time between frames as a float.
     */
    public void setGameScreen(GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
    }

    /**
     * Updates the current state of the station to allow progression.
     * @param delta The time between frames as a float.
     */
    @Override
    public void update(float delta) {
        if (TestFlag == 0) {
            if (progress < 100) {
                gameScreen.cooks.get(usingchef).lockmovement = true;
            } else {
                gameScreen.cooks.get(usingchef).lockmovement = false;
            }
        }
        if (inUse) {
            if (progress < 100) {
                float[] steps = interaction.getSteps();
                progress = Math.min(progress + interaction.getSpeed() * delta, 100);
                if (stepNum < steps.length) {
                    // -1 instant case
                    if (interaction.getSpeed() == -1) {
                        progress = steps[stepNum];
                        state = StationState.NEED_USE;
                    } else {
                        if (progress >= steps[stepNum]) {
                            progress = steps[stepNum];
                            state = StationState.NEED_USE;
                        } else {
                            state = StationState.PREPARING;
                        }
                    }
                } else {
                    if (interaction.getSpeed() == -1) {
                        progress = 100;
                        state = StationState.FINISHED;
                    } else {
                        state = StationState.PREPARING;
                    }
                }
            } else {
                state = StationState.FINISHED;
            }
        }
    }

    /**
     * The function used to render the {@link PreparationStation}.
     *
     * <br>When no item is on top, it renders the {@link PreparationStation}'s
     * identifying {@link Sprite}.
     * <br>When there is an item on top, it renders the {@link interactions.Interactions.InteractionResult}'s
     * {@link #progress} bar to completion, as well as the {@link FoodItem}
     * on the {@link PreparationStation}.
     * @param batch The {@link SpriteBatch} used to render.
     */
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        // If in use, render the appropriate foodItem on the Station.
        if (this.Locked) {
            Sprite renderLock;
            renderLock = gameSprites.getSprite(GameSprites.SpriteID.FOOD, "lock");
            renderLock.setScale(Constants.UnitScale);
            renderLock.setPosition(x - 1/3f * renderLock.getWidth(),y - 0.354f * renderLock.getHeight());
            renderLock.draw(batch);
        }
        if (stationFoodStack.size() == 1 && stationFoodStack.getStack().get(0) == FoodItem.FoodID.waste) {
            Sprite renderWaste = gameSprites.getSprite(GameSprites.SpriteID.FOOD, "waste");
            renderWaste.setScale(Constants.UnitScale);
            renderWaste.setPosition(x - 1/3f * renderWaste.getWidth(),y - 0.354f * renderWaste.getHeight());
            renderWaste.draw(batch);
        }

        if (inUse) {
            Sprite renderItem;
            if (progress < 100) {
                renderItem = gameSprites.getSprite(GameSprites.SpriteID.FOOD,this.stationFoodStack.getStack().get(0).toString());
            } else {
                renderItem = gameSprites.getSprite(GameSprites.SpriteID.FOOD,interaction.getResult().toString());
            }
            renderItem.setScale(Constants.UnitScale);
			//tln(renderItem.getWidth());
            renderItem.setPosition(x - 1/3f * renderItem.getWidth(),y - 0.354f * renderItem.getHeight());
            renderItem.draw(batch);
        }
    }

    /**
     * All the possible states a station can be in
     */

    public enum StationState {
        PREPARING,
        NEED_USE,
        FINISHED
    }

    /**
     * The function used to render the {@link PreparationStation}.
     *
     * <br>It draws the {@link #progress} bar of the
     * {@link interactions.Interactions.InteractionResult}.
     * @param shape The {@link ShapeRenderer} used to render.
     */
    public void renderShape(ShapeRenderer shape) {
        if (TestFlag == 0) {
            if (progress < 100) {
//                tln("+++++++++++++++Locking+++++++++++++++++");
                gameScreen.cooks.get(usingchef).lockmovement = true;
            }
        }
        // Render the progress bar when inUse
        if (inUse) {
            float rectX = x,
                  rectY = y + 40 * Constants.UnitScale,
                  rectWidth = 48 * Constants.UnitScale,
                  rectHeight = 10 * Constants.UnitScale;
            // Black bar behind
            shape.rect(rectX, rectY, rectWidth, rectHeight, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);
            // Now the progress bar.
            float progressWidth = rectWidth-4 * Constants.UnitScale;
            Color progressColor = Color.SKY;
            // If preparation is done, show as green.
            switch (state) {
                case NEED_USE:
                    progressColor = Color.YELLOW;
                    break;
                case FINISHED:
                    progressColor = Color.GREEN;
                    break;
                default:
                    break;
            }
            shape.rect(rectX+ 2 * Constants.UnitScale,rectY + 2 * Constants.UnitScale,progress/100 * progressWidth,rectHeight - 4 * Constants.UnitScale,progressColor,progressColor,progressColor,progressColor);
        }
    }

    /**
     * The interact function for the {@link ServingStation}.
     *
     * <br>This allows the {@link Cook} to put a valid {@link FoodItem} onto the
     * {@link PreparationStation}, and start a process of changing it from the
     * {@link FoodItem} to the {@link interactions.Interactions.InteractionResult} {@link FoodItem}.
     * @param cook The cook that interacted with the {@link CookInteractable}.
     * @param inputType The type of {@link InputKey.InputTypes} the player made with
     *                  the {@link CookInteractable}.
     */
    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        if(Locked){
            if (TestFlag == 0) {
                if ((this.getEnabled()) && (gameScreen.gold.getBalance() - item.cost >= 0)) {
                    gameScreen.gold = item.buy(gameScreen.gold);
                    this.Locked = false;
                }
            }
        }
        else{
            if (TestFlag == 0) {
                this.usingchef = gameScreen.cookIndex;
                gameScreen.cooks.get(usingchef).lockmovement = true;

                if (inUse) {
                    gameScreen.cooks.get(usingchef).lockmovement = true;
                } else {
                    gameScreen.cooks.get(usingchef).lockmovement = false;
                }
                if (cook.getBlocked() == true) {
                    return;
                }
            }

            // If the Cook is holding a food item, and they use the "Put down" control...
            if (cook.foodStack.size() > 0 && inputType == InputKey.InputTypes.PUT_DOWN) {
                if (TestFlag == 0) {
                    gameScreen.cooks.get(usingchef).lockmovement = true;
                }
                // Start by getting the possible interaction result
                Interactions.InteractionResult newInteraction = interactions.Interactions.interaction(cook.foodStack.peekStack(), stationID);
                // If it's null, just stop here.
                if (newInteraction == null) {
                    cook.foodStack.popStack();
                    this.stationFoodStack.addStack(FoodItem.FoodID.waste);
                    return;
                }

                // Check to make sure the station isn't inUse.
                if (!inUse) {
                    // Set the current interaction, and put this station inUse
                    this.stationFoodStack.addStack(cook.foodStack.popStack());
                    interaction = newInteraction;
                    stepNum = 0;
                    progress = 0;
                    inUse = true;
                    state = StationState.PREPARING;

                    // If the speed is -1, immediately set the progress to the first step.
                    float[] steps = interaction.getSteps();
                    if (steps.length > 0) {
                        if (interaction.getSpeed() == -1) {
                            progress = steps[0];
                            state = StationState.NEED_USE;
                        }
                    }
                }
            }

            else if (!inUse && stationFoodStack.size() > 0) {
                if ((inputType == InputKey.InputTypes.PICK_UP)) {
                    cook.foodStack.addStack(stationFoodStack.popStack());
                    return;
                }
            }

            // The other two inputs require the station being inUse.
            else if (inUse) {
                // If the user instead uses the "Pick Up" option, check if the station is inUse
                if ((inputType == InputKey.InputTypes.PICK_UP) && ((TestFlag == 1) || (gameScreen.cooks.get(usingchef).lockmovement == false))) {
                    if (TestFlag == 0) {
                        gameScreen.cooks.get(usingchef).lockmovement = false;
                    }
                    inUse = false;
                    // If it is done, pick up the result instead of the foodItem
                    if (progress >= 100) {
                        cook.foodStack.addStack(interaction.getResult());
                        return;
                    }
                    // Take the item from the Station, and change it to not being used
                    cook.foodStack.addStack(this.stationFoodStack.getStack().get(0));
                    return; // Return as it the Station is no longer inUse
                }
                if ((inputType == InputKey.InputTypes.PICK_UP) && (cook.lockmovement == false)) {
                    if (TestFlag == 0) {
                        gameScreen.cooks.get(usingchef).lockmovement = false;
                    }
                    inUse = false;
                    // If it is done, pick up the result instead of the foodItem
                    if (progress >= 100) {
                        cook.foodStack.addStack(interaction.getResult());
                        return;
                    }
                    // Take the item from the Station, and change it to not being used
                    cook.foodStack.addStack(this.stationFoodStack.getStack().get(0));
                    return; // Return as it the Station is no longer inUse
                }
                if ((inputType == InputKey.InputTypes.PICK_UP) && ((TestFlag == 1) || (gameScreen.cooks.get(usingchef).lockmovement == true))) {
                    // If progress >= 100, then take the result of the preparation.
                    if (progress >= 100) {
                        if (TestFlag == 0) {
                            gameScreen.cooks.get(usingchef).lockmovement = false;
                        }
                        inUse = false;
                        cook.foodStack.addStack(interaction.getResult());
                        return;
                    }
                }
                // Otherwise, check if the user is trying to use the Station.
                if (inputType == InputKey.InputTypes.USE) {
                    // If progress >= 100, then take the result of the preparation.
                    if (progress >= 100) {
                        if (TestFlag == 0) {
                            gameScreen.cooks.get(usingchef).lockmovement = false;
                        }
                        inUse = false;
                        cook.foodStack.addStack(interaction.getResult());
                        return;
                    }
                    // If currently at a step, move to the next step.
                    float[] steps = interaction.getSteps();
                    if (stepNum < steps.length) {
                        if (progress >= steps[stepNum]) {
                            progress = steps[stepNum];
                            stepNum += 1;
                        }
                    }

                }
            }
        }

    }


    /**
     * Used for testing purposes, to test code automatically without requiring manual testing
     * @param testFlag
     */
    public void SetTestFlag(int testFlag){
        this.TestFlag = testFlag;
    }

}
