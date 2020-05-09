package assignment7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.Serializable;


public class AuctionItems implements Serializable{
    private static final long serialVersionUID = 12345;
    //private static HashMap<Integer,AuctionItems> ItemMap = new HashMap<Integer, AuctionItems>();
        private static List<AuctionItems> Items = new ArrayList<AuctionItems>();

    private int itemNumber;
    private String itemName;
    private String itemDescription;
    private int  highestBid;
    private int time;
    private boolean sold;
    private BidCaller bidcaller;

    public AuctionItems(String n, String d, int t, int intemNumb){
        this.itemNumber = intemNumb;
        this.itemName = n;
        this.itemDescription = d;
        this.highestBid = 0;
        this.time = t;
        this.sold = false;
        this.bidcaller = null;
    }
    public static void addItem(AuctionItems a){
        Items.add(a);
    }
    public static int getItemSize(){
        return Items.size();
    }
    public static AuctionItems getItem(int i){
        return Items.get(i);
    }

    public static List<AuctionItems> getAuctionList(){
        return Items;
    }

    public static void printItems(){
        for(AuctionItems a : Items){
            System.out.println(a.itemName);
            //System.out.println(a.itemDescription);
        }
    }

    public static void updateItem(Bid b){
        for(AuctionItems i :Items){
            if(i.getItemName().equals(b.getItem().getItemName())){
                i.highestBid = b.getNewBid();
            }
        }
    }

    public static void decrimentTime(){
        for(AuctionItems a : Items){
            int time = a.getTime();
            time--;
            a.setTime(time);
        }
    }

    @Override
    public String toString() {
        return itemName;
    }
    public int getItemNumber(){
        return itemNumber;
    }
    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getHighestBid() {
        return highestBid;
    }

    public int getTime() {
        return time;
    }

    public boolean isSold() {
        return sold;
    }

    public BidCaller getBidcaller() {
        return bidcaller;
    }

    public static void setItems(List<AuctionItems> items) {
        Items = items;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public void setBidcaller(BidCaller bidcaller) {
        this.bidcaller = bidcaller;
    }
    public void setHighestBid(int highestBid) {
        this.highestBid = highestBid;
    }
    public void setTime(int time) {
        this.time = time;
    }
}