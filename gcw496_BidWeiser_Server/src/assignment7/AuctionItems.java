package assignment7;
// Copy-paste this file at the top of every file you turn in.
/*
 * EE422C Final Project submission by
 * Replace <...> with your actual data.
 * <Garrett Wong>
 * <gcw496>
 * <16295>
 * Spring 2020
 */
import java.util.ArrayList;
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
    private int minPrice;
    private boolean sold;
    private BidCaller bidcaller;

    public AuctionItems(String n, String d, int t, int intemNumb, int minPrice){
        this.itemNumber = intemNumb;
        this.itemName = n;
        this.minPrice = minPrice;
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
        }
    }

    public static void updateItem(Bid b){
        for(AuctionItems i :Items){
            if(i.getItemNumber() == b.getItem().getItemNumber()){
                i.highestBid = b.getNewBid();
                i.bidcaller = b.getBidCaller();
                System.out.println(i.bidcaller.getUserName());
            }
        }
    }

    public static void decrimentTime(){
        for(AuctionItems a : Items){
            if (!a.sold){
                if (a.getTime()>0){
                    int time = a.getTime();
                    time--;
                    a.setTime(time);
                }
                if (a.getTime() == 0){
                    a.sold = true;
                    System.out.println(a.getItemName() + " is Sold!");
                }
            }
        }
    }

    @Override
    public String toString() {
        return itemName;
    }
    public int getMinPrice() {
        return minPrice;
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