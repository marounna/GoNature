package clientGUI;

import java.io.IOException;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.Order;
import common.ChatIF;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;

import javax.print.DocFlavor.URL;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class OrderFormController  {
	private Order o;
    @FXML
    private ComboBox<String> parkNameField;  // Assuming the ComboBox contains Strings
    @FXML
    private TextField orderNumberField;
    @FXML
    private TextField timeOfVisitField;
    @FXML
    private ComboBox<String> numberOfVisitorsField;  // Assuming the ComboBox contains Strings
    @FXML
    private TextField telephoneNumberField;
    @FXML
    private TextField emailField;

    @FXML
    private Button Cancelbtn;

    @FXML
    private Button Savebtn;
    
    @FXML
    private void initialize() {
        parkNameField.getItems().addAll("Safari", "Gan-Tanahi", "Ya'ar-Hakofim", "Hay-Park","Mitspe-Tat-Yami");
        numberOfVisitorsField.getItems().addAll("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15");
    }

    @FXML
    void CancelAction(ActionEvent event) {
		try {
		  // Load the FXML for the previous screen
		  FXMLLoader loader = new FXMLLoader();
		  loader.setLocation(getClass().getResource("/clientGUI/OrderFrame.fxml")); // Update the path to your FXML
		  Parent previousScreen = loader.load();
		  // Get the current stage from the event source
		  Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		  // Set the scene for the stage with the root of the previous screen
		  Scene scene = new Scene(previousScreen);
		  scene.getStylesheets().add(getClass().getResource("/clientGUI/OrderFrame.css").toExternalForm());
		  stage.setScene(scene);
          
		  // Optionally, set the title for the stage
		  stage.setTitle("Enter Order Number");
		
		  // Show the updated stage
		  stage.show();
		  } catch (Exception e) {
		      e.printStackTrace();
		  }
    }

    @FXML
    void SaveAction(ActionEvent event) {
    	System.out.println("OrderFormCtl> Trying to Save");
		String message="updateOrderDetails ";//used to help the server to handle message
		message += parkNameField.getValue() + " ";
		message += orderNumberField.getText()+ " ";
		message += timeOfVisitField.getText() + " ";
        message += numberOfVisitorsField.getValue() + " ";
        message += telephoneNumberField.getText() + " ";
        message += emailField.getText();
        System.out.println("OrderFormCtl> message = "+message);
        try {
        	 ClientUI.chat.accept(message);//Send Msg TO Server
		} catch (Exception e) {
			// TODO: handle exception
		}	
    }
    //set values into the Order object
    public void loadOrder(Order o1) {
    	System.out.println("OrderFormControler>"+o1.toString());
    	this.o=o1;
    	this.parkNameField.setValue(o.getParkName());
    	this.orderNumberField.setText(o.getOrderNumber());
    	this.timeOfVisitField.setText(o.getTimeOfVisit());
    	this.numberOfVisitorsField.setValue(o.getNumberOfVisitors());
    	this.telephoneNumberField.setText(o.getTelephoneNumber());
    	this.emailField.setText(o.getEmail());
    }
}  