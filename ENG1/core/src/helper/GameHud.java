package helper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
//import customers.Customer;
import food.FoodStack;
import game.GameScreen;

// import java.awt.*;

/** Responsible for displaying information above the gameplay GameScreen. */
public class GameHud extends Hud {
    /** The label with the current amount of time played. */
    Label timeLabel;
//    /** The label with the number of {@link Customer}s left to serve.  */
//    Label CustomerLabel;
    Label reputation;
    Label Loading;
    Label goldLabel;
    Label CustomerServed;
    private GameScreen gameScreen;
    /** The {@link SpriteBatch} of the GameHud. Use for drawing {@link food.Recipe}s. */
    /** The {@link FoodStack} that the {@link GameHud} should render. */


    /**
     * The GameHud constructor.
     * @param batch The {@link SpriteBatch} to render
     * @param gameScreen The {@link GameScreen} to render the GameHud on
     */
    public GameHud(SpriteBatch batch, GameScreen gameScreen)
    {
        super(batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        timeLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.ORANGE));
        reputation = new Label("",new Label.LabelStyle(new BitmapFont(),Color.FIREBRICK));
        goldLabel = new Label("",new Label.LabelStyle((new BitmapFont()),Color.GOLD));
        Loading = new Label("", font);
        CustomerServed = new Label("",new Label.LabelStyle((new BitmapFont()),Color.SCARLET));
        Loading.setFontScale(10);
        timeLabel.setFontScale(1);
        reputation.setFontScale(1);
        goldLabel.setFontScale(1);
        CustomerServed.setFontScale(1);
        updateTime(0,0,0);
        updateReputation(0);
        updateGold(0);
        this.gameScreen = gameScreen;

//        CustomerLabel = new Label("CUSTOMERS LEFT:", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
//
//        table.add(CustomerLabel).expandX().padTop(80).padRight(60);
        table.add(timeLabel).expandX().padTop(80).padLeft(60);
        table.add(reputation).expandX().padTop(80).padRight(60);
        table.add(goldLabel).expandX().padTop(80).padRight(60);
        table.row();
        table.add(CustomerServed).expandY().padBottom(0).padLeft(70);
        table.row();
        table.add(Loading).expandX().padTop(100).padLeft(150);
    }


    /**
     * Update the Timer
     * @param hoursPassed The number of hours passed
     * @param minutesPassed The number of minutes passed
     * @param secondsPassed The number of seconds passed
     */
    public void updateTime(int hoursPassed, int minutesPassed, int secondsPassed)
    {
        timeLabel.setText("TIMER: " + String.format(Util.formatTime(hoursPassed,minutesPassed,secondsPassed)));
    }

    public void updateReputation(int Reputation){
        reputation.setText("REPUTATION: " + String.format(String.valueOf(Reputation)));
    }
    public void updateloading(String val){
        this.Loading.setText(String.format(val));
    }

    public void updateGold(int gold){
        goldLabel.setText("GOLD: " + String.format(String.valueOf(gold)));
    }

    public void updateCustomerServed(int served){
        if((gameScreen.getCustomerController().getMode().equals("endless"))) {
            CustomerServed.setText("Customers Served: " + String.format(String.valueOf(served)));
        }
        else
        {
            CustomerServed.setText("");
        }
    }
}

