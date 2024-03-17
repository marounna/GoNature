package clientGUI;

import java.io.IOException;

import client.ClientUI;
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
    private Button ExitButton;
	
    @FXML
    private Button LoginBtn;

    @FXML
    private Button NewReservationBtn;

    //move to login screen
    @FXML
    void LoginBtnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SwitchScreen.changeScreen(stage,"/resources/LoginController.fxml","/resources/LoginController.css");
    }
    
    //move to enterID screen
    @FXML
    void NewReservationBtnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SwitchScreen.changeScreen(stage,"/resources/EnterIDForReservationController.fxml"
        		,"/resources/EnterIDForReservationController.css");
    }
    
    @FXML
    void ClickOnExitButton(ActionEvent event) {
    	ClientUI.chat.accept("disconnect");
    	System.exit(0);
    }

}
