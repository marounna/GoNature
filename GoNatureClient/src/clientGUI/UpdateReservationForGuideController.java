package clientGUI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import client.ClientUI;
import common.StaticClass;
import common.SwitchScreen;
import entities.Park;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Order;


public class UpdateReservationForGuideController {

    @FXML
    private Button backBtn;

    @FXML
    private DatePicker date;

    @FXML
    private ComboBox<String> numberOfVisitors;

    @FXML
    private ComboBox<String> parkNameCombo;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField textEmail;

    @FXML
    private ComboBox<String> timeCombo;

    @FXML
    void ClickForSave(ActionEvent event) {

    }

    @FXML
    void ClickOnBack(ActionEvent event) {
        //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    SwitchScreen.changeScreen(event,"/clientGUI/UserMenuController.fxml"
	        			,"/resources/UserMenuController.css");

    }
    

	public void loadOrder(Order o1) {
		this.parkNameCombo.setValue(StaticClass.o1.getParkName());
		this.timeCombo.setValue(StaticClass.o1.getTimeOfVisit());
        this.numberOfVisitors.setValue(StaticClass.o1.getNumberOfVisitors());
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
