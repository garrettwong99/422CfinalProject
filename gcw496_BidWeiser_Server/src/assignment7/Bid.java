package assignment7;

import java.io.Serializable;

public class Bid implements Serializable {
    private static final long serialVersionUID = 123;

    public AuctionItems getItem() {
        return Item;
    }

    public void setItem(AuctionItems item) {
        Item = item;
    }

    public int getNewBid() {
        return newBid;
    }

    public void setNewBid(int newBid) {
        this.newBid = newBid;
    }

    private AuctionItems Item;
    private int newBid;

    public Bid(AuctionItems i, int newBid){
        this.Item = i;
        this.newBid = newBid;
    }

}
