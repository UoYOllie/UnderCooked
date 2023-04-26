package customers;

public class RepPoints {
    private int Points;

    public RepPoints(){
        this.Points = 3;
    }

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


    public void Negative()
    {
        this.Points = this.Points - 1;
    }

    public void Positive()
    {
        this.Points++;
        if(this.Points>=5)
        {
            this.Points = 5;
        }
    }

    public int getPoints(){
        return this.Points;
    }
}
