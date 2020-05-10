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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BidCaller implements Serializable{
    private static final long serialVersionUID = 15678;
    private  List<AuctionItems> boughtItems = new ArrayList<AuctionItems>();
    private static List<BidCaller> allClients = new ArrayList<BidCaller>();

    public static void addClient(BidCaller c){
        allClients.add(c);
    }

    private String userName;
    private int walletamount;

    public BidCaller(String n){
        this.userName = n;
    }
    public String getUserName() {
        return userName;
    }

    public int getWalletamount(){
        return walletamount;
    }
    public List<AuctionItems> getboughtItems(){
        return boughtItems;
    }

    public void addBoughtItem(AuctionItems a){
        boughtItems.add(a);
    }

    public static List<BidCaller> getClients(){
        return allClients;
    }
}



