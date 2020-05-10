package final_exam;
// Copy-paste this file at the top of every file you turn in.
/*
 * EE422C Final Project submission by
 * Replace <...> with your actual data.
 * <Garrett Wong>
 * <gcw496>
 * <16295>
 * Spring 2020
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientBiddingController implements Initializable {
    @FXML private Label UserName;
    @FXML private Button History;
    @FXML private Button LogOut;
    @FXML private Label Itemf;
    @FXML private Label Availabilityf;
    @FXML private Label ItemWonf;
    @FXML private Label Descriptionf;
    @FXML private Label CurrentHighestBidderf;
    @FXML private Label CurrentHighestBidf;
    @FXML private Label minPricef;
    @FXML private Label TimeRemainingf;
    @FXML private Label errormessagef;
    @FXML private TextField NewBidf;
    @FXML private Button BIDf;


    @FXML private ListView AuctionItemList;
    private static AuctionItems currentTabItem;
    private static boolean hasSelectedItem = false;

    /*
     * This is linked to the button @ FXML logout . Original brings you back to login
     * now it exits gracefully
     */
    public void SwitchScene(ActionEvent event) throws IOException {
        Client.exit();
        /*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientLogin.fxml"));
        Parent clinetLoginParent = loader.load();
        ClientLoginController controller2 = loader.getController();
        Scene clientLoginScene = new Scene(clinetLoginParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(clientLoginScene);
        window.show();
         */
    }

    /*
     * THis is linked to the TexField @ FXML NewBidf
     * this takes the the item selected, and brings the info up for the client
     */
    public void setItemtoBid(){
        Object o = AuctionItemList.getSelectionModel().getSelectedItem();
        AuctionItems item = (AuctionItems)o;
        currentTabItem = item;
        hasSelectedItem = true;
        Itemf.setText(item.getItemName());
        Descriptionf.setText(item.getItemDescription());
        CurrentHighestBidf.setText(String.valueOf(item.getHighestBid()));
        TimeRemainingf.setText(String.valueOf(item.getTime()));
    }

    /*
     * updates the GUI from the item selected
     * updates every second as the clock counts down
     */
    public void updateGUI(){
        if(hasSelectedItem){
            if(Client.bidCaller.getboughtItems().contains(currentTabItem)){
                ItemWonf.setText("This item is yours!");
            }
            else{
                ItemWonf.setText("");
            }
            if(currentTabItem.isSold()){
                Availabilityf.setText("This item is no longer available");
            }
            else{
                Availabilityf.setText("This item is still available");
            }
            CurrentHighestBidf.setText(String.valueOf(currentTabItem.getHighestBid()));
            TimeRemainingf.setText(String.valueOf(currentTabItem.getTime()));
            minPricef.setText(String.valueOf(currentTabItem.getMinPrice()));
            if (currentTabItem.getBidcaller() == null){
                CurrentHighestBidderf.setText("No bids");
            }
            else{
                CurrentHighestBidderf.setText(currentTabItem.getBidcaller().getUserName());
            }
        }
    }

    /*
     * Linked to @FXML Bidf
     * sets error message for invalid bids and sends valid bids to the server
     */
    public void newBid(){
        Object o = AuctionItemList.getSelectionModel().getSelectedItem();
        AuctionItems itemWanted = (AuctionItems)o;
        String bidAmountString = NewBidf.getText();
        if(itemWanted.isSold()){
            errormessagef.setText("Bid Closed");
        }
        else {
            try {
                if (bidAmountString.equals("")) {
                    errormessagef.setText("empty amount");
                } else if (Integer.parseInt(bidAmountString) < itemWanted.getMinPrice()) {
                    errormessagef.setText("min price = $" + String.valueOf(itemWanted.getMinPrice()));
                } else if (Integer.parseInt(bidAmountString) <= itemWanted.getHighestBid()) {
                    errormessagef.setText("Too Low");
                } else {
                    int bidAmount = Integer.parseInt(bidAmountString);
                    System.out.println(bidAmount);
                    Bid b = new Bid(itemWanted, bidAmount, Client.bidCaller);
                    System.out.println(b.getItem().getItemName());
                    System.out.println(b.getNewBid());
                    Client.sendToServer(b);
                    errormessagef.setText("");
                }
            } catch (NumberFormatException e) {
                errormessagef.setText("Not an amount");
            } catch (NullPointerException e) {
                errormessagef.setText("Select item");
            }
        }
    }

    /*
     * Linked to @FXML history takes you to a separate screen with the client's purchase history
     */
    public void changeToHistory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientHistory.fxml"));
        Parent ClientHistoryParent = loader.load();
        ClientHistoryController controller = loader.getController();
        Scene historyscene = new Scene(ClientHistoryParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(historyscene);
        window.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /*
     * populate the ListVIew
     */
    public void populateListView(AuctionItems a){
        AuctionItemList.getItems().add(a);
    }

    /*
     * setes the username
     */
    public void setUserName(String text){
        UserName.setText(text);
    }

}
