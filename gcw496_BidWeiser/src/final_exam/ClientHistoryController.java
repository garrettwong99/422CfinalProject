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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientHistoryController implements Initializable {
    @FXML private Label usernamef;
    @FXML private Button returntoBidf;
    @FXML private TableView<AuctionItems> tableview;
    @FXML private TableColumn<AuctionItems, String> itemName;
    @FXML private TableColumn<AuctionItems, String>  itemDescription;
    @FXML private TableColumn<AuctionItems, Integer>  itemPrice;




    public void changetoBidding(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientBidding.fxml"));
        Parent ClientBiddingParent = loader.load();
        ClientBiddingController controller = loader.getController();
        Scene biddingScene = new Scene(ClientBiddingParent);
        for(AuctionItems e: AuctionItems.getAuctionList()){
            controller.populateListView(e);
        }
        Client.controller2 =controller;
        controller.setUserName(Client.bidCaller.getUserName());
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(biddingScene);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernamef.setText(Client.bidCaller.getUserName());

        itemName.setCellValueFactory(new PropertyValueFactory<AuctionItems,String>("itemName"));
        itemDescription.setCellValueFactory(new PropertyValueFactory<AuctionItems,String>("itemDescription"));
        itemPrice.setCellValueFactory(new PropertyValueFactory<AuctionItems, Integer>("highestBid"));

        tableview.setItems(getItems());
    }

    public ObservableList<AuctionItems> getItems(){
        ObservableList<AuctionItems> itemsbought = FXCollections.observableArrayList();
        for(AuctionItems e: Client.bidCaller.getboughtItems()){
            itemsbought.add(e);
        }
        return itemsbought;
    }
}
