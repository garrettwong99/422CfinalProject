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
    ObjectOutputStream toServer = null;
    ObjectInputStream fromServer = null;

    static private BufferedReader reader;
    static private PrintWriter writer;

    static private String userName;

    public static void setUserName(String name) {
        userName = name;
    }

    public static void sendtoServer(String message){
        writer.println(message);
        writer.flush();
    }

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ClientLogin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        setUpNetworking();
    }



    private void setUpNetworking() throws Exception{
        @SuppressWarnings("resource")
        Socket sock = new Socket("localhost", 5000);
        InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
        reader = new BufferedReader(streamReader);
        writer = new PrintWriter(sock.getOutputStream());
        System.out.println("networking established");
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
    class IncomingReader implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
