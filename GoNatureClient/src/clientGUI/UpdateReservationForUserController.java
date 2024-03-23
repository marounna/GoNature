package clientGUI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import client.ClientUI;
import common.StaticClass;
import common.SwitchScreen;
import entities.Park;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;
import logic.Order;


public class UpdateReservationForUserController {

    @FXML
    private Button backBtn;

    @FXML
    private DatePicker date;

    @FXML
    private TextField numberOfVisitors;

    @FXML
    private ComboBox<String> parkNameCombo;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField textEmail;

    @FXML
    private ComboBox<String> timeCombo;

    @FXML
    private Label guidelabel;
    
    @FXML
    void ClickForSave(ActionEvent event) {
        LocalDate selectedDate = date.getValue();
        LocalDate currentDate = LocalDate.now();
        guidelabel.setStyle("-fx-text-fill: red;");
        // Assuming your timeCombo has values in "HH:mm" format
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime selectedTime = LocalTime.parse(timeCombo.getValue(), timeFormatter);
        LocalTime currentTime = LocalTime.now();

        if (selectedDate == null || selectedDate.isBefore(currentDate) || (selectedDate.isEqual(currentDate) && selectedTime.isBefore(currentTime))) {
            guidelabel.setText("Please enter valid date and time");
            if(StaticClass.typeacc.equals("guide")&&Integer.parseInt(numberOfVisitors.getText())>15) {
	    			guidelabel.setText("Cannot enter more than 15 visitors");
	    	}
        }
    	else {
	    	StaticClass.updatetowaitinglist=0;
	    	StaticClass.o1.setDate(""+date.getValue());
	    	StaticClass.o1.setNumberOfVisitors(numberOfVisitors.getText());
	    	StaticClass.o1.setParkName(parkNameCombo.getValue());
	    	StaticClass.o1.setTimeOfVisit(timeCombo.getValue());
	    	ClientUI.chat.accept("updateOrder " +StaticClass.orderid + " " + StaticClass.o1.getParkName() +" "+ 
	    			StaticClass.o1.getDate() + " "+StaticClass.o1.getTimeOfVisit() + " "+StaticClass.o1.getNumberOfVisitors()+" "+StaticClass.discounttype + " "+
	    			StaticClass.typeacc+ " "+ StaticClass.reservationtype);
	    	System.out.println("update waiting list = "+StaticClass.updatetowaitinglist + "\n\n\n\nupdate visa = "+ StaticClass.visa);
	    	if(StaticClass.updatetowaitinglist==1) {
	        	Alert alertpayment = new Alert(AlertType.INFORMATION);
	        	alertpayment.setTitle("waiting list");
	        	alertpayment.setHeaderText(null);
	        	alertpayment.setContentText("The park is fully booked, do you want to enter watitng list?");
	
	        	// Create custom ButtonTypes
	        	ButtonType okButton = new ButtonType("Confirm", ButtonData.OK_DONE);
	        	ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	
	        	// Set the ButtonTypes to the alert
	        	alertpayment.getButtonTypes().setAll(okButton, cancelButton);
	
	        	// Show the alert and wait for a response
	        	Optional<ButtonType> result = alertpayment.showAndWait();
	        	if (result.isPresent()) {
	        		if (result.get() == okButton) {
	        		   	ClientUI.chat.accept("updateWaitingList " +StaticClass.orderid);
				   		SwitchScreen.changeScreen(event,"/clientGUI/UserMenuController.fxml"
				    			,"/resources/UserMenuController.css");
	        		}		
	    	    }	
			}else if(StaticClass.visa==1){
					StaticClass.visa=0;
		        	Alert alertvisa = new Alert(AlertType.INFORMATION);
		        	alertvisa.setTitle("payment differences");
		        	alertvisa.setHeaderText(null);
		        	alertvisa.setContentText("You will receive a visa credit for the price differences");
		
		        	// Create custom ButtonTypes
		        	ButtonType okVisaButton = new ButtonType("Confirm", ButtonData.OK_DONE);
		        	ButtonType cancelVisaButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		
		        	// Set the ButtonTypes to the alert
		        	alertvisa.getButtonTypes().setAll(okVisaButton, cancelVisaButton);
		        	// Show the alert and wait for a response
		        	Optional<ButtonType> result = alertvisa.showAndWait();
		        	
		        	if (result.isPresent()) {
		        		if (result.get() == okVisaButton) {
					   		SwitchScreen.changeScreen(event,"/clientGUI/UserMenuController.fxml"
					    			,"/resources/UserMenuController.css");
		        		}		
		    	    }
			}
			else {
		   		SwitchScreen.changeScreen(event,"/clientGUI/UserMenuController.fxml"
		    			,"/resources/UserMenuController.css");
			}
		}		
    }

    @FXML
    void ClickOnBack(ActionEvent event) {
	    SwitchScreen.changeScreen(event,"/clientGUI/UserMenuController.fxml"
	        			,"/resources/UserMenuController.css");

    }
    

	public void loadOrder(Order o1) {
		this.parkNameCombo.setValue(StaticClass.o1.getParkName());
		this.timeCombo.setValue(StaticClass.o1.getTimeOfVisit());
        this.numberOfVisitors.setText(StaticClass.o1.getNumberOfVisitors());
        this.textEmail.setText(StaticClass.o1.getEmail());
        String dateString = StaticClass.o1.getDate();
        LocalDate localDate = null;
        // Ensure dateString is not null or empty to avoid parsing errors
        if(dateString != null && !dateString.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adjust the pattern to match your date format
            localDate = LocalDate.parse(dateString, formatter);
        }
        this.date.setValue(localDate);
		
	}
	
	
    @FXML  //initialize the new reservation screen  date numberOfVisitors parkNameCombo textEmail timeCombo
    private void initialize() {
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
