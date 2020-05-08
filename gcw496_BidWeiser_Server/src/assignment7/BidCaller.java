package assignment7;

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

    public BidCaller(String n){
        this.userName = n;
    }
    public String getUserName() {
        return userName;
    }


}
