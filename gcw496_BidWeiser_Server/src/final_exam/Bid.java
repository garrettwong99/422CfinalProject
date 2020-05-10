package final_exam;

// Copy-paste this file at the top of every file you turn in.
/*
 * EE422C Final Project submission by
 * Replace <...> with your actual data.
 * <Garrett Wong>
 * <gcw496>
 * <16295>
 * Spring 2020
 */

import java.io.Serializable;

public class Bid implements Serializable {
    private static final long serialVersionUID = 123;
    private BidCaller bidCaller;
    private AuctionItems Item;
    private int newBid;

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
