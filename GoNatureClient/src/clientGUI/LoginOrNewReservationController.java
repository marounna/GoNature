package clientGUI;

import java.io.IOException;

import client.ClientUI;
import common.ScreenSwitcher;
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
    	String newScreenPath="/clientGUI/LoginController.fxml";
    	new ScreenSwitcher().changeScreen(event, newScreenPath);
    }
    
    //move to enterID screen
    @FXML
    void NewReservationBtnAction(ActionEvent event) throws IOException {
    	String newScreenPath="/clientGUI/EnterIDForReservatiovController.fxml";
    	new ScreenSwitcher().changeScreen(event, newScreenPath);

    }
    
    @FXML
    void ClickOnExitButton(ActionEvent event) {
    	ClientUI.chat.accept("100");
    	System.exit(0);
    }

}
