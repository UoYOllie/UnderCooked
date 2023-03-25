package Shop;

public class ShopItem{
    public String name;
    public int cost;

    public ShopItem(String n,int cost)
    {
        this.name = n;
        this.cost = cost;
    }


    //USER RELATED
    public Gold buy(Gold cash){
        if(cash.getBalance()>=cost)
        {
            cash.setBalance(cash.getBalance()-cost);
        }
        return cash;

    }

}