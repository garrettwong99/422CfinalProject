package Server;

import java.util.HashMap;

public class AuctionItems {
    private static HashMap<String,AuctionItems> ItemsforSale = new HashMap<>();

    private String itemDescription;
    private String itemName;
    private int  highestBid;
    private String highestBidder;
    private int Time;
}
