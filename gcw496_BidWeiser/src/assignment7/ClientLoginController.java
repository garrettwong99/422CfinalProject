
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
    @FXML private Button Login;

    private boolean usernameTaken = false;

    public void SwitchScene(ActionEvent event) throws IOException {
        String name = userName.getText();
        if(name.equals(""))
            setUserName.setText("please enter in a valid username");
        else{
            for(int i = 0; i<BidCaller.getClients().size() ; i++){
                if(BidCaller.getClients().get(i).getUserName().equals(name)){
                    setUserName.setText("Sorry this username is already taken");
                    usernameTaken = true;
                }
            }
            if (!usernameTaken){
                Client.controller2.setUserName(name);
                Client.setUserName(name);
                BidCaller bc = new BidCaller(name);
                Client.sendToServer(bc);
                Client.bidCaller = bc;
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(Client.scene2);
                window.show();
            }
            usernameTaken = false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Client.sendToServer("init");
        //while(BidCaller.getClients().isEmpty()){}

    }
}
