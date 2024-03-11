package clientGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class NewReservationController {
	

    @FXML
    private TextField idField;

    @FXML
    private Button BackBtn;

    @FXML
    private Button NextBtn;

    @FXML
    void ClickBackBtn(ActionEvent event) {
		try {
			  FXMLLoader loader = new FXMLLoader();
	          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			  loader.setLocation(getClass().getResource("/clientGUI/LoginOrNewReservation.fxml")); // Update the path to your FXML
			  Parent previousScreen = loader.load();
			  Scene scene = new Scene(previousScreen);
			  stage.setScene(scene);
			  // Show the updated stage
			  stage.show();
			  } catch (Exception e) {
			      e.printStackTrace();}
    }

    @FXML
    void ClickNextBtn(ActionEvent event) {

    }
	
	
	
}
