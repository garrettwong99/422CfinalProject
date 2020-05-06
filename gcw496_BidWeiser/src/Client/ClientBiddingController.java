package Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientBiddingController implements Initializable {
    @FXML private Label UserName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setUserName(String text){
        UserName.setText(text);
    }
}
