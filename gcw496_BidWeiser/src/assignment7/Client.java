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
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.io.IOException;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;


public class Client extends Application {
    ClientLoginController controller1;
    static ClientBiddingController controller2;
    static ObjectOutputStream toServer = null;
    static ObjectInputStream fromServer = null;
    static Socket sock;
    static Scene scene2;
    static private String userName;
    static BidCaller bidCaller;

    /*
     * TimerforItems extends the TimerTask class and calls a request
     * to the server periodically every second to update the remaining
     * time for each item.
     * Each item has a specific timeout time specified in the csv file
     */
    static class TimerforItems extends TimerTask {
        @Override
        public void run() {
            sendToServer("request");
            //System.out.println("request");
        }
    } // end of Timer for items

    /*
     * setUserName sets the userName for this Client
     */
    public static void setUserName(String name) {
        userName = name;
    }

    /*
     * sendToServer takes any object and sends it to the server
     * with the Object Output Stream toServer
     * @params Object message
     */
    public static void sendToServer(Object message){
        try {
            toServer.reset();
            toServer.writeUnshared(message);
            toServer.flush();
        } catch (IOException e) {
        }
    } // end of sendTOServer

    /*
     closes the Client server connection
     */
    public static void closeSocket() throws IOException {
        sock.close();
    }

    /*
     * closes the socket and exits the program gracefully
     */
    public static void exit() throws IOException{
        sock.close();
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void start(Stage stage) throws Exception{
        setUpNetworking();
        FXMLLoader l = new FXMLLoader();
        Parent root = l.load(getClass().getResource("ClientLogin.fxml").openStream());
        controller1 = l.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        FXMLLoader l2 = new FXMLLoader();
        Parent root2 = l2.load(getClass().getResource("ClientBidding.fxml").openStream());
        controller2 = l2.getController();
        scene2 = new Scene(root2);
    }

    private void setUpNetworking(){
        @SuppressWarnings("resource")
        int port = 5000;
        try{
            sock = new Socket("localhost", port);
            toServer = new ObjectOutputStream((sock.getOutputStream()));
            fromServer = new ObjectInputStream(sock.getInputStream());
            System.out.println("networking established");
            Thread readerThread = new Thread(new IncomingReader());
            readerThread.start();
        } catch (IOException e){
            System.out.println("break");
        }
    }

    class IncomingReader implements Runnable{
        public void run() {
            boolean inlist = false;
            Object o;
            try {
                while ((o = fromServer.readObject()) != null) {
                    if(o instanceof BidCaller){
                        BidCaller.addClient((BidCaller)o);
                        System.out.println("newBidCaller");
                    }
                    if( o instanceof AuctionItems){
                        AuctionItems b = (AuctionItems)o;
                        for (AuctionItems a : AuctionItems.getAuctionList()){
                            // if item already in list, just update it
                            if (a.getItemNumber() == b.getItemNumber()){
                                if(a.isSold() && a.getBidcaller()!=null){
                                    try{
                                        if (bidCaller.getboughtItems().contains(a)){}
                                        else if (a.getBidcaller().getUserName().equals(bidCaller.getUserName())){
                                            bidCaller.addBoughtItem(a);
                                            System.out.println("I won " + a.getItemName());
                                        }
                                    }catch(NullPointerException e){}
                                }
                                a.setHighestBid(b.getHighestBid());
                                a.setBidcaller(b.getBidcaller());
                                a.setTime(b.getTime());
                                a.setSold(b.isSold());
                                Platform.runLater(()->{
                                    controller2.updateGUI();
                                });
                                inlist = true;
                            }
                        }
                        if(inlist){}
                        else {
                            AuctionItems.addItem(b);
                            Platform.runLater(()->{
                                controller2.populateListView(b);
                            });
                            inlist = false;
                        }
                    }

                }
            }catch(ClassNotFoundException ex){
                System.out.println("cant find");
            }catch(SocketException ex){
                System.out.println("socket closed");
            }catch (IOException ex) {
                System.out.println("server closed");
                try {
                    Client.exit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        TimerforItems itemTimer = new TimerforItems();
        Timer t = new Timer();
        t.schedule(itemTimer,5000,1000);
        launch(args);
    }
}
