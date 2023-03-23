package Shop;

public class ShopItem{
    public String name;
    private Boolean inuse;
    private int cost;

    public ShopItem(String n,int cost)
    {
        this.name = n;
        this.inuse = false;
        this.cost = cost;
    }


    //USER RELATED
    public Gold buy(Gold cash){
        if((inuse==false)&&(cash.getBalance()>=cost))
        {
            inuse = true;
            cash.setBalance(cash.getBalance()-cost);
        }
        return cash;

    }

}