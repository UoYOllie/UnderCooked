package stations;

import com.badlogic.gdx.physics.bullet.collision._btMprSimplex_t;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import cooks.Cook;
import game.GameSprites;
import interactions.InputKey;


public class SpeedPowerup extends Station {
    public Cook currentcook;
    public SpeedPowerup(Rectangle rectangle){
        super(rectangle);
    }

    @Override
    public void interact(Cook cook, InputKey.InputTypes inputType) {
        cook.setSpeed();
        currentcook = cook;


    }
}
