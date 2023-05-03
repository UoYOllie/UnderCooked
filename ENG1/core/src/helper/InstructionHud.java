package helper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import interactions.InputKey;
import interactions.Interactions;

/** Responsible for displaying the controls on the GameScreen. */
public class InstructionHud extends Hud{

    Label instructionsLabel;

    /**
     * The constructor for the InstructionHud.
     * @param batch The SpriteBatch to render on this Hud.
     */
    public InstructionHud(SpriteBatch batch) {
        super(batch);
        instructionsLabel = new Label(String.format("Press %s to USE \nPress %s to COLLECT \nPress %s to PUT DOWN \nPress %s to SAVE \nPress %s to LOAD\nPress P to view costs for items",
                Interactions.getKeyString(InputKey.InputTypes.USE),
                Interactions.getKeyString(InputKey.InputTypes.PICK_UP),
                Interactions.getKeyString(InputKey.InputTypes.PUT_DOWN),
                Interactions.getKeyString(InputKey.InputTypes.SAVE),
                Interactions.getKeyString(InputKey.InputTypes.LOAD)
                ), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(instructionsLabel).expandX().padTop(110).padRight(480);
    }

    /** Renders the Hud with the game's controls. */
    @Override
    public void render() {
        super.render();
    }


}
