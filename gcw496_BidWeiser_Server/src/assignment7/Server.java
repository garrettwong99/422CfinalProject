package assignment7;

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
        server.populateItems();
        server.SetupNetworking();
    }
    /*
     * This method will take a csv file with Auction items
     * descriptions and the time limit, it will copy the
     * contents into Items ArrayList<AuctionItems>
     */
    private void populateItems() {
        try {
            Scanner s = new Scanner(new File("./src/AuctionList"));
            String p;
            String [] pack;
            AuctionItems a;
            while(s.hasNextLine()){
                p = s.nextLine();
                pack = p.split(",");
                a = new AuctionItems(pack[0],pack[1],Integer.parseInt(pack[2]));
                AuctionItems.addItem(a);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found. Need to Debug");
        }
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
            Object o;
            BidCaller bc;
                try {
                    while (true){
                        o = reader.readObject();
                        if(o != null){
                            if(o instanceof Bid){
                                System.out.println("integer detected");
                            }
                            if(o instanceof BidCaller){
                                System.out.println("BidCaller detected");
                                bc = (BidCaller)o;
                                System.out.println(bc.getUserName());
                                BidCaller.addClient(bc);
                                for(int i = 0; i < AuctionItems.getItemSize(); i++){
                                    setChanged();
                                    writer.reset();
                                    notifyObservers(AuctionItems.getItem(i));
                                }
                            }
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }


        }
    } // end of class ClientHandler
}
