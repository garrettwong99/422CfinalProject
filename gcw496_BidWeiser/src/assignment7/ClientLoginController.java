
package assignment7;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientLoginController implements Initializable{
    @FXML private Label setUserName;
    @FXML private TextField userName;
    public void SwitchScene(ActionEvent event) throws IOException {
        String name = userName.getText();
        if(name.equals(""))
            setUserName.setText("please enter in a valid username");
        else{

            Client.controller2.setUserName(name);
            Client.setUserName(name);
            BidCaller bc = new BidCaller(name);
            Client.sendToServer(bc);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(Client.scene2);
            window.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
