package Server;

import java.io.Serializable;
public class BidCaller implements Serializable{

    private String userName;
    public BidCaller(String n){
        this.userName = n;
    }
    public String getUserName() {
        return userName;
    }

}
