package clientGUI;


import java.io.IOException;

import client.ClientUI;
import common.StaticClass;
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

	    }

	    @FXML
	    void ClickOnGuideReservation(ActionEvent event) {
	    	StaticClass.reservationtype="group";
	    }

	    @FXML
	    void ClickOnNewReservation(ActionEvent event) {
	    	StaticClass.reservationtype="customer";
	    }

	    @FXML
	    void clickOnCheckAmount(ActionEvent event) {

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
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	      		SwitchScreen.changeScreen(stage,"/clientGUI/LoginController.fxml","/resources/LoginController.css");

			}
	    }

}


