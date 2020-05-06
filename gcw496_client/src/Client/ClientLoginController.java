package Client;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientLoginController implements Initializable{
    //@FXML
    public void SwitchScene(ActionEvent event) throws IOException {

        Parent clientBiddingParent = FXMLLoader.load(getClass().getResource("ClientBidding.fxml"));
        Scene clientBiddingScene = new Scene(clientBiddingParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(clientBiddingScene);
        window.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
