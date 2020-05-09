package assignment7;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.io.IOException;
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

    static class TimerforItems extends TimerTask {
        @Override
        public void run() {
            sendToServer("request");
            System.out.println("test");
        }
    }

    public static void setUserName(String name) {
        userName = name;
    }

    public static void sendToServer(Object message){
        try {
            toServer.reset();
            toServer.writeUnshared(message);
            //toServer.flush();
        } catch (IOException e) {
            System.out.println("Did not send");
        }
        //toServer.close();
    }

    public static void closeSocket() throws IOException {
        sock.close();
    }

    @Override
    public void start(Stage stage) throws Exception{
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

        setUpNetworking();

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
                    if( o instanceof AuctionItems){
                        AuctionItems b = (AuctionItems)o;
                        for (AuctionItems a : AuctionItems.getAuctionList()){
                            if (a.getItemNumber() == b.getItemNumber()){                // if item already in list, just update it
                                a.setHighestBid(b.getHighestBid());
                                a.setTime(b.getTime());
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

                            AuctionItems.printItems();
                            inlist = false;
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
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
