//DC
package customers;

public class RepPoints {
    private int Points;

    /**
     * Constructor
     * Sets inital points to 3 as stated in requirements
     */
    public RepPoints(){
        this.Points = 3;
    }

    /**
     * Sets point
     * sets this.points to x
     * Also checks if points>5 then sets point only up to a maximum of 5
     * @param x
     */
    public void setPoints(int x)
    {
        if(x>=5)
        {
            this.Points = 5;
        }
        else if(x>=0)
        {
            this.Points = x;
        }
    }

    /**
     * Reduces this.point by 1 when ran.
     * Used when customer runs StormOut()
     * Only used when customers leave without food
     */
    public void Negative()
    {
        this.Points = this.Points - 1;
    }

    /**
     * Increases this.points by 1
     * Used when cutomer gets their food
     * Used when customer runs Success function before leaving
     */
    public void Positive()
    {
        this.Points++;
        if(this.Points>=5)
        {
            this.Points = 5;
        }
    }

    /**
     * getter for this.points
     * @return current reputation points
     */
    public int getPoints(){
        return this.Points;
    }
}
