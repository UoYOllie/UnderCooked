package Shop;

public class Gold {

    public int Balance;

    public Gold(){
        setBalance(0);
    }
    
    public void setBalance(int x){
        this.Balance = x;
    }

    public int getBalance(){
        return this.Balance;
    }

	public static Gold gold;

	public static Gold getInstance(){
		return gold;
	}

}
