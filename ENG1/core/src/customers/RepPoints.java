package customers;

public class RepPoints {
    private int Points;

    public RepPoints(){
        this.Points = 3;
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
}
