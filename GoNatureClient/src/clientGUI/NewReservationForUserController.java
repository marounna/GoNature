package clientGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import client.ChatClient;
import client.ClientUI;
import entities.Park;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NewReservationForUserController {

    @FXML
    private Button backBtn;

    @FXML
    private DatePicker date;

    @FXML
    private TextField numberOfVisitors;

    @FXML
    private ComboBox<String> parkNameCombo;

    @FXML
    private TextField textPhone;

    @FXML
    private ComboBox<String> timeCombo;

    @FXML
    private TextField textEmail;

    @FXML
    private TextField textFirstName;

    @FXML
    private TextField textLastName;
    
    @FXML
    private Button paymentBtn;


    public static int numberofvisitors;
    public static String orderdetails="";
    public static ArrayList<String> parknames = new ArrayList<>();
    public static int flagC=0;
    



	    @FXML
	    void ClickOnBack(ActionEvent event) throws IOException {

	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        switch (ChatClient.typeacc) {
		        case "guide":
		        case "customer":
		        	//loader = new FXMLLoader(getClass().getResource("/clientGUI/UserMenuController.fxml"));
		        	SwitchScreen.changeScreen(stage,"/clientGUI/UserMenuController.fxml"
		        			,"/clientGUI/UserMenuController.css");
		        	break;
		        case "guest":
		    		//loader = new FXMLLoader(getClass().getResource("/clientGUI/EnterIDForReservationController.fxml"));
		    		SwitchScreen.changeScreen(stage,"/clientGUI/EnterIDForReservationController.fxml"
		    				,"/clientGUI/EnterIDForReservationController.css");
		    		break;
	        }

	    }
	    @FXML
	    void ClickForPayment(ActionEvent event) throws IOException {
	    	numberofvisitors=Integer.parseInt(numberOfVisitors.getText());
	    	orderdetails+="Park name: "+ parkNameCombo.getValue().toString();
	    	orderdetails+="\nNumber of visitors: "+numberofvisitors;
	    	orderdetails+="\nDate: "+ date.getValue().toString();
	    	orderdetails+="\nTime: "+ timeCombo.getValue().toString();
	    	orderdetails+="\nFirst name: "+ textFirstName.getText();
	    	orderdetails+="\nLast name: "+ textLastName.getText();
	    	orderdetails+="\nTelephone: "+ textPhone.getText();
	        orderdetails+="\nEmail: " +textEmail.getText();
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		SwitchScreen.changeScreen(stage,"/clientGUI/PaymentController.fxml","/clientGUI/PaymentController.css");
	    }
	    
	    @FXML
	    private void initialize() {

	    	flagC=1;

	    	LoginController.parks.clear();
	        ClientUI.chat.accept("park");
	        parkNameCombo.getItems().addAll(getParkNames());

	        parkNameCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
	            updateAvailableTimes(newValue);
	        });
	        // Default time slots for when no park is selected or if a park allows all-day visits
	        timeCombo.getItems().addAll("8:00", "9:00", "10:00", "11:00", "12:00", "13:00",
	        		"14:00", "15:00", "16:00", "17:00", "18:00", "19:00");
	        parknames.clear();
	    }

	    private void updateAvailableTimes(String parkName) {
	        // Find the selected park
	        Park selectedPark = LoginController.parks.stream().filter(park -> park.getParkString().equals(parkName)).findFirst()
	        		.orElse(null);
	        if (selectedPark != null) {
	            int visitLimitHours = selectedPark.getVisitTimeLimit();

	            // Clear previous time slots
	            timeCombo.getItems().clear();

	            // Add time slots based on visit limit
	            for (int hour = 8; hour < 20; hour++) {
	                if (hour + visitLimitHours <= 20) {
	                    timeCombo.getItems().add(String.format("%02d:00", hour));
	                }
	            }
	        }
	    }

	    public static ArrayList<String> getParkNames() {
	        ArrayList<String> parkName = new ArrayList<>();
	        for (Park park : LoginController.parks) {
	            parkName.add(park.getParkString()); // Assuming getParkString() returns the park's name
	        }
	        return parkName;
	    }
	    	    		
}

