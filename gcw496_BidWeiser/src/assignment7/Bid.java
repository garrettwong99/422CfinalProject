package assignment7;

import java.io.Serializable;

public class Bid implements Serializable {
    private AuctionItems Item;
    private int newBid;

    public Bid(AuctionItems i, int newBid){
        this.Item = i;
        this.newBid = newBid;
    }

}
