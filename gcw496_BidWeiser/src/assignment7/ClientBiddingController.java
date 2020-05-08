package assignment7;

import javafx.collections.ObservableList;
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
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

public class ClientBiddingController implements Initializable {
    @FXML private Label UserName;
    @FXML private Button History;
    @FXML private Button LogOut;

    @FXML private Label Itemf;
    @FXML private Label Descriptionf;
    @FXML private Label CurrentHighestBidf;
    @FXML private Label TimeRemainingf;
    @FXML private TextField NewBidf;
    @FXML private Button BIDf;

    @FXML private ListView AuctionItemList;

    public void SwitchScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientLogin.fxml"));
        Parent clinetLoginParent = loader.load();
        ClientLoginController controller2 = loader.getController();
        Scene clientLoginScene = new Scene(clinetLoginParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(clientLoginScene);
        window.show();
    }

    public void setItemtoBid(){
        Object o = AuctionItemList.getSelectionModel().getSelectedItem();
        AuctionItems item = (AuctionItems)o;
        //System.out.println(item);
        Itemf.setText(item.getItemName());
        Descriptionf.setText(item.getItemDescription());

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void populateListView(AuctionItems a){
        AuctionItemList.getItems().add(a);
    }
    public void setUserName(String text){
        UserName.setText(text);
    }

}
