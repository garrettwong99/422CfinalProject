package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.io.IOException;


public class Client extends Application {
    ClientLoginController controller;
    ObjectOutputStream toServer = null;
    ObjectInputStream fromServer = null;

    static private String userName;

    public static void setUserName(String name) {
        userName = name;
    }
/*
    public static void sendtoServer(Object message) throws IOException {
        toServer.writeObject(message);
    }
*/

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader l = new FXMLLoader();
        Parent root = l.load(getClass().getResource("ClientLogin.fxml").openStream());
        controller = l.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //controller.FUNCTION = this;

        setUpNetworking();

    }

    private void setUpNetworking(){
        @SuppressWarnings("resource")
        int port = 5000;
        try{
            Socket sock = new Socket("localhost", port);
            System.out.println("hello world");
            fromServer = new ObjectInputStream(sock.getInputStream());
            //toServer = new ObjectOutputStream(new PrintStream(sock.getOutputStream()));
            //toServer.flush();


            System.out.println("networking established");
            Thread readerThread = new Thread(new IncomingReader());
            readerThread.start();
        } catch (IOException e){
            System.out.println("break");
        }
    }

    class IncomingReader implements Runnable
        ObjectOutputStream toServer;
        ObjectInputStream fromServer;
        public IncomingReader(Socket sock){
            try {
                toServer = new ObjectOutputStream(sock.getOutputStream());
                //fromServer = new ObjectInputStream(sock.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            Object message;
            try {
                while ((message = fromServer.readObject()) != null) {
                    //System.out.println(message.toString());
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
