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
		FXMLLoader loader = new FXMLLoader();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		loader = new FXMLLoader(getClass().getResource("/clientGUI/LoginController.fxml"));
		Pane root = loader.load();
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("/clientGUI/OrderFrame.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    
    //move to enterID screen
    @FXML
    void NewReservationBtnAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		loader = new FXMLLoader(getClass().getResource("/clientGUI/EnterIDForReservationController.fxml"));
		Pane root = loader.load();
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("/clientGUI/OrderFrame.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void ClickOnExitButton(ActionEvent event) {
    	ClientUI.chat.accept("disconnect");
    	System.exit(0);
    }

}
