package clientGUI;

import java.io.IOException;

import client.ClientUI;
import common.SwitchScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginOrNewReservationController {
	
    @FXML
    private Button LoginBtn;

    @FXML
    private Button NewReservationBtn;

    //move to login screen
    @FXML
    void LoginBtnAction(ActionEvent event) throws IOException {
        SwitchScreen.changeScreen(event,"/clientGUI/LoginController.fxml","/resources/LoginController.css");
    }
    
    //move to enterID screen
    @FXML
    void NewReservationBtnAction(ActionEvent event) throws IOException {
        SwitchScreen.changeScreen(event,"/clientGUI/EnterIDForReservationController.fxml"
        		,"/resources/EnterIDForReservationController.css");
    }
    
    @FXML
    void clickOnClose(ActionEvent event) {
    	ClientUI.chat.accept("disconnect");
    	System.exit(0);
    }

}
