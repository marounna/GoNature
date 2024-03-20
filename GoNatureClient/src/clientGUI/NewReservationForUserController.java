package clientGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import client.ChatClient;
import client.ClientUI;
import common.StaticClass;
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
    private Button paymentBtn;

    @FXML
    private TextField textEmail;

    @FXML
    private ComboBox<String> timeCombo;


	    @FXML
	    void ClickOnBack(ActionEvent event) throws IOException {
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        switch (StaticClass.typeacc) {
		        case "guide":
		        case "customer":
		        	SwitchScreen.changeScreen(stage,"/resources/UserMenuController.fxml"
		        			,"/resources/UserMenuController.css");
		        	break;
		        case "guest":
		    		SwitchScreen.changeScreen(stage,"/resources/EnterIDForReservationController.fxml"
		    				,"/resources/EnterIDForReservationController.css");
		    		break;
	        }

	    }
	    @FXML
	    void ClickForPayment(ActionEvent event) throws IOException {
	    	ClientUI.chat.accept("priceCheck " + parkNameCombo.getValue().toString());
	    	
	    	StaticClass.o1.setParkName(parkNameCombo.getValue().toString());//inserting the order data
	    	StaticClass.o1.setDate(date.getValue().toString());
	    	StaticClass.o1.setNumberOfVisitors(""+numberOfVisitors.getText());
	    	StaticClass.o1.setTimeOfVisit(timeCombo.getValue().toString());
	    	StaticClass.o1.setEmail(textEmail.getText());
	    	
	    	//string for the textArea on the next screen
	    	StaticClass.numberofvisitors=Integer.parseInt(numberOfVisitors.getText());
	    	StaticClass.orderdetails+="Park name: "+ parkNameCombo.getValue().toString();
	    	StaticClass.orderdetails+="\nNumber of visitors: "+StaticClass.numberofvisitors;
	    	StaticClass.orderdetails+="\nDate: "+ date.getValue().toString();
	    	StaticClass.orderdetails+="\nTime: "+ timeCombo.getValue().toString();
	    	StaticClass.orderdetails+="\nEmail: " +textEmail.getText();
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		SwitchScreen.changeScreen(stage,"/resources/PaymentController.fxml","/resources/PaymentController.css");
	    }
	    
	    @FXML  //initialize the new reservation screen
	    private void initialize() {
	    	//StaticClass.flagC=1;
	    	StaticClass.parks.clear();
	        ClientUI.chat.accept("park");
	        parkNameCombo.getItems().addAll(getParkNames());
	        parkNameCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
	            updateAvailableTimes(newValue);
	        });
	        // Default time slots for when no park is selected or if a park allows all-day visits
	        timeCombo.getItems().addAll("8:00", "9:00", "10:00", "11:00", "12:00", "13:00",
	        		"14:00", "15:00", "16:00", "17:00", "18:00", "19:00");
	    }
	    //getting  hours from DB
	    private void updateAvailableTimes(String parkName) {
	        // Find the selected park
	        Park selectedPark = StaticClass.parks.stream().filter(park -> park.getParkString().equals(parkName)).findFirst()
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
	    
	    //getting park names from DB
	    public static ArrayList<String> getParkNames() {
	        ArrayList<String> parkName = new ArrayList<>();
	        for (Park park : StaticClass.parks) {
	            parkName.add(park.getParkString()); // Assuming getParkString() returns the park's name
	        }
	        return parkName;
	    }
	    	    		
}

