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
    static Server server;
    static boolean needsUpdate = false;

    static class TimerforItems extends TimerTask{
        @Override
        public void run() {
            AuctionItems.decrimentTime();
            Server.needsUpdate = true;
        }
    }
    public static void main (String [] args) {

        TimerforItems itemTimer = new TimerforItems();
        Timer t = new Timer();
        t.schedule(itemTimer,10000,1000);
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
            int startnum = 0;
            while(s.hasNextLine()){
                p = s.nextLine();
                pack = p.split(",");
                a = new AuctionItems(pack[0],pack[1],Integer.parseInt(pack[2]),startnum,Integer.parseInt(pack[3]));
                AuctionItems.addItem(a);
                startnum++;
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
                            if(o instanceof String){
                                String s = (String)o;
                                if(s.equals("request")){
                                    for(int i = 0; i < AuctionItems.getItemSize(); i++){
                                        setChanged();
                                        writer.reset();
                                        notifyObservers(AuctionItems.getItem(i));
                                    }
                                }
                                else if(s.equals("init")){
                                    System.out.println("init detected");
                                    for(BidCaller b: BidCaller.getClients()){
                                        setChanged();
                                        writer.reset();
                                        notifyObservers(b);
                                    }
                                }
                            }
                            if(o instanceof Bid){
                                System.out.println("Bid detected");
                                Bid b = (Bid)o;
                                System.out.println(b.getItem().getItemName());
                                System.out.println(b.getNewBid());
                                AuctionItems.updateItem((Bid)o);
                                for(int i = 0; i < AuctionItems.getItemSize(); i++){
                                    setChanged();
                                    writer.reset();
                                    notifyObservers(AuctionItems.getItem(i));
                                }
                            }
                            if(o instanceof BidCaller){
                                System.out.println("BidCaller detected");
                                bc = (BidCaller)o;
                                System.out.println(bc.getUserName());
                                BidCaller.addClient(bc);
                                for(BidCaller b: BidCaller.getClients()){
                                    setChanged();
                                    writer.reset();
                                    notifyObservers(b);
                                }
                                for(int i = 0; i < AuctionItems.getItemSize(); i++){
                                    setChanged();
                                    writer.reset();
                                    notifyObservers(AuctionItems.getItem(i));
                                }
                            }
                        }
                    }
                }
                catch (IOException | ClassNotFoundException e) {
                    //remove observable
                }
        }
    } // end of class ClientHandler
}
