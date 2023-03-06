package game;

import com.badlogic.gdx.Input;
import interactions.OurInputProcessor;

public class GameInputProcessor extends OurInputProcessor {
    @Override
    public boolean keyDown (int keycode) {
        switch (keycode){
            case Input.Keys.SPACE:
                //INTERACT
                break;
            case Input.Keys.TAB:
                //setCook((cookIndex + 1) % cooks.size);
                break;
            case Input.Keys.UP:
                //Pick up
                break;
            case Input.Keys.DOWN:
                break;
        }
        return true;
    }
}
