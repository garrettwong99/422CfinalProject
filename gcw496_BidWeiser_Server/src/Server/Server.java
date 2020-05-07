package Server;

/*
 * Author: Vallath Nandakumar and the EE 422C instructors.
 * Data: April 20, 2020
 * This starter code assumes that you are using an Observer Design Pattern and the appropriate Java library
 * classes.  Also using Message objects instead of Strings for socket communication.
 * See the starter code for the Chat Program on Canvas.  
 * This code does not compile.
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.*;


public class Server extends Observable {
    private static List<String> users = new ArrayList<String>();
    static Server server;
    public static void main (String [] args) {
        server = new Server();
        //server.populateItems(); load up the files
        server.SetupNetworking();
    }

    private void SetupNetworking() {
        int port = 5000;
        try {
            ServerSocket ss = new ServerSocket(port);
            while (true) {
                Socket clientSocket = ss.accept();
                ClientObserver writer = new ClientObserver(clientSocket.getOutputStream());
                Thread t = new Thread(new ClientHandler(clientSocket, writer));
                t.start();
                addObserver(writer);
                System.out.println("got a connection");
            }
        } catch (IOException e) {}
    }

    class ClientHandler implements Runnable {
        private  ObjectInputStream reader;
        private ClientObserver writer; // See Canvas. Extends ObjectOutputStream, implements Observer
        Socket clientSocket;

        public ClientHandler(Socket clientSocket, ClientObserver writer) {
            Socket sock = clientSocket;
            this.writer = writer;
            try {
                InputStream is = sock.getInputStream();
                reader = new ObjectInputStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            BidCaller bc;
                try {
                    while (true){
                        bc = (BidCaller)reader.readObject();
                        if(bc != null){
                            System.out.print("recieved");
                            System.out.println(bc.getUserName());
                            setChanged();
                            notifyObservers(bc);
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }


        }
    } // end of class ClientHandler
}
