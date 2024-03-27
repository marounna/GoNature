package clientGUI;


import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import client.ClientUI;
import common.StaticClass;
import common.SwitchScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmployeeMenuController {
	
        public static String username;
	    public static boolean islogout =false;
	    @FXML
	    private Button logoutBtn;
	    
	    @FXML
	    private Label amountLabel;

	    @FXML
	    private Button checkAmountBtn;

	    @FXML
	    private Button checkOrderBtn;

	    @FXML
	    private Label checkOrderLabel;

	    @FXML
	    private Button guideReservationBtn;

	    @FXML
	    private TextField numberVisitorsTxt;

	    @FXML
	    private TextField orderIDTxt;

	    @FXML
	    private Button reservationBtn;

	    @FXML
	    void ClickOnCheckOrder(ActionEvent event) {
	    	String OrderId = orderIDTxt.getText();
	    	String numberofvisiter = numberVisitorsTxt.getText();
	    	ClientUI.chat.accept("orderexistYarden "+ OrderId);
	    	try {
		    	if (StaticClass.istheorderexist == 0) {
		    	    checkOrderLabel.setText("no order found");

		    	} 
		    	else {
		    	    // Since an order exists, check the amount of people
		    	    ClientUI.chat.accept("checkamountofpeople " + OrderId + " " + numberofvisiter);


		    	if (StaticClass.amoutgreaterthenorder == 0) {
		    	        checkOrderLabel.setText("amount of people is bigger then registered in the order");

		    	    } else {
		    	        // Update the table only if the amount of people is correct
		    	        ClientUI.chat.accept("UpdateTable " + OrderId + " " + numberofvisiter + " " + StaticClass.typeacc);

		    	        checkOrderLabel.setText("ENJOY YOUR VISIT");
		    	    }
		    	}
	    	} catch (Exception e) {
	    	    e.printStackTrace(); // or handle the exception appropriately
	    	}


	    }


	    @FXML
	    void ClickOnGuideReservation(ActionEvent event) {
	    	StaticClass.reservationtype="group";
	    	StaticClass.discounttype="casual_group";
	    }

	    @FXML
	    void ClickOnNewReservation(ActionEvent event) {
	    	StaticClass.reservationtype="customer";
	    	StaticClass.discounttype="casual_personal";
	    }

	    @FXML
	    void clickOnCheckAmount(ActionEvent event) {
	    	ClientUI.chat.accept("userId "+StaticClass.username);
	    	ClientUI.chat.accept("amountInPark "+StaticClass.userid);
	    	LocalTime now = LocalTime.now();	    	// Define the closing and opening times
	    	LocalTime openingTime = LocalTime.of(8, 0); // 08:00 AM
	    	LocalTime closingTime = LocalTime.of(20, 0); // 08:00 PM

	    	// Check if current time is before opening or after closing
	    	if (now.isBefore(openingTime)|| now.isAfter(closingTime)) {
	    	    // Park is closed
	    		amountLabel.setText("The park is closed.");
	    	} else {
		    	String formattedTime = now.format(DateTimeFormatter.ofPattern("HH:mm"));
		    	String labelText = String.format("The amount of people in the park is %s according to time %s", StaticClass.amountinparkyarden, formattedTime);
		    	amountLabel.setText(labelText);
	    	}

	    }
	    
	    @FXML
	    void clickOnLogout(ActionEvent event) throws IOException {
	    	String message="logout "+StaticClass.username;
			try {
				ClientUI.chat.accept(message);
				System.out.println("UserMenuController> request Sent to server");
			}catch (Exception e){
				System.out.println("UserMenuController> Logout failed");
			}
			if(StaticClass.islogout) {
				StaticClass.islogout=false;
	      		SwitchScreen.changeScreen(event,"/clientGUI/LoginController.fxml","/resources/LoginController.css");

			}
	    }

}


