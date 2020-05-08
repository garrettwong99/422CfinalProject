package assignment7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.io.IOException;


public class Client extends Application {
    ClientLoginController controller1;
    static ClientBiddingController controller2;
    static ObjectOutputStream toServer = null;
    static ObjectInputStream fromServer = null;
    static Socket sock;
    static Scene scene2;

    static private String userName;

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
            Object o;
            try {
                while ((o = fromServer.readObject()) != null) {
                    if( o instanceof AuctionItems){
                        AuctionItems.addItem((AuctionItems)o);
                        controller2.populateListView((AuctionItems)o);
                        AuctionItems.printItems();
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
