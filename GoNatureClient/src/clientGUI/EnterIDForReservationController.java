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


public class EnterIDForReservationController {
	

    @FXML
    private TextField idField;

    @FXML
    private Button BackBtn;

    @FXML
    private Button NextBtn;
    //go back to the previous screen
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
    //move to new reservation screen, saving userId on table external_users in db
    @FXML
    void ClickNextBtn(ActionEvent event) {
    	String userId=idField.getText();
    	try {
			  FXMLLoader loader = new FXMLLoader();
	          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			  loader.setLocation(getClass().getResource("/clientGUI/NewReservationController.fxml")); // Update the path to your FXML
			  Parent previousScreen = loader.load();
			  Scene scene = new Scene(previousScreen);
			  stage.setScene(scene);
			  // Show the updated stage
			  stage.show();
			  } catch (Exception e) {
			      e.printStackTrace();}
    	
    }
	
	
	
}
