package clientGUI;

import java.io.IOException;
import java.util.ArrayList;

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

public class NewReservationForGuideController {

    @FXML
    private Button backBtn;

    @FXML
    private DatePicker date;

    @FXML
    private Button paymentBtn;

    @FXML
    private ComboBox<String> numberVisitorsCombo;

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
    
    public static int numberofvisitors;
    public static int flagG=0;
    public static String orderdetails="";

    public static ArrayList<String> parknames = new ArrayList<>();

	    @FXML //moving back to user menu
	    void ClickOnBack(ActionEvent event) throws IOException {
	    	flagG=0;
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        SwitchScreen.changeScreen(stage,"/resources/UserMenuController.fxml"
	        		,"/resources/UserMenuController.css");
	    }

	    @FXML //setting the fields data into orderdetails string and moving to the payment screen
	    void ClickForPayment(ActionEvent event) throws IOException {
	    	numberofvisitors=Integer.parseInt(numberVisitorsCombo.getValue());
	    	orderdetails+="Park name: "+ parkNameCombo.getValue().toString();
	    	orderdetails+="\nNumber of visitors: "+numberofvisitors;
	    	orderdetails+="\nDate: "+ date.getValue().toString();
	    	orderdetails+="\nTime: "+ timeCombo.getValue().toString();
	    	orderdetails+="\nFirst name: "+ textFirstName.getText();
	    	orderdetails+="\nLast name: "+ textLastName.getText();
	    	orderdetails+="\nTelephone: "+ textPhone.getText();
	        orderdetails+="\nEmail: " +textEmail.getText();
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        SwitchScreen.changeScreen(stage,"/resources/PaymentController.fxml"
	        		,"/resources/PaymentController.css");

	    }
	    
	    @FXML //initialize the screen with park names and time combo fields
	    private void initialize() {
	    	flagG=1;
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
	    // update available time from db
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
	    // getting park names from db and adding it into array list
	    public static ArrayList<String> getParkNames() {
	        ArrayList<String> parkName = new ArrayList<>();
	        for (Park park : LoginController.parks) {
	            parkName.add(park.getParkString()); 
	        }
	        return parkName;
	    }
	
	
}