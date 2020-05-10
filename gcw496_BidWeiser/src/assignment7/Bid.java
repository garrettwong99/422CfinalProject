package assignment7;

import java.io.Serializable;

public class Bid implements Serializable {
    private static final long serialVersionUID = 123;
    private AuctionItems Item;
    private int newBid;
    private BidCaller bidCaller;


    public Bid(AuctionItems i, int newBid, BidCaller b){
        this.Item = i;
        this.newBid = newBid;
        this.bidCaller = b;

    }

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

    public BidCaller getBidCaller(){
        return bidCaller;
    }

}
