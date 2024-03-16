package clientGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import client.ClientUI;
import clientGUI.NewReservationForUserController;

public class PaymentController {
	
	
    @FXML
    private Button backBtn;

    @FXML
    private Button confirmPaymentBtn;
    
    @FXML
    private Label discountLabel;

    @FXML
    private TextArea orderDetailsArea;
    
	public int visitorsnumber=0;

	public double discount=1.0;
	public double totalprice;
	public String type;
	String msg="";

    @FXML
    void clickOnBack(ActionEvent event) throws IOException {
    	orderDetailsArea.clear();
    	msg="";
    	FXMLLoader loader = new FXMLLoader();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        switch(type) {
        	case "g":
	        	loader = new FXMLLoader(getClass().getResource("/clientGUI/NewReservationForGuideController.fxml"));
	        	break;
        	case "c":
	        	loader = new FXMLLoader(getClass().getResource("/clientGUI/NewReservationForUserController.fxml"));
	        	break;
        	/*case "ec":
        	  case "eg":
	        	loader = new FXMLLoader(getClass().getResource("/clientGUI/EmployeeMenuController.fxml"));
	        	break;*/
        }
		Pane root = loader.load();
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("/clientGUI/OrderFrame.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clickOnPayNow(ActionEvent event) {
    	if (type.equals("g")) {
    		totalprice=totalprice*0.88;
    	}
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Payment Information");
        alert.setHeaderText(null); // You can set a header text here
        alert.setContentText("Total price for payment is: "+totalprice);
        // Show the Alert and wait for user response
        alert.showAndWait();
    	
    }
    
    @FXML
    void clickOnPayInPark(ActionEvent event) {

    }
    @FXML//initialize the order details textArea field with the order details from the last screen
    private void initialize() {
    	orderDetailsArea.clear();
    	if (NewReservationForUserController.flagC==1) {//if its personal order (booked in advance)
    		type="c";
    		discount=0.85;
    		visitorsnumber=NewReservationForUserController.numberofvisitors;
    		msg+=NewReservationForUserController.orderdetails;
    		NewReservationForUserController.orderdetails="";
        	msg+="\nFull price before discount: " + 50*visitorsnumber;
        	msg+="\nDiscount: 15%";
    	}
    	else if(NewReservationForGuideController.flagG==1) {//if its group order (booked in advance)
    		type="g";
    		discountLabel.setText("Click on pay now for extra 12% discount");
    		discount=0.75;
    		visitorsnumber=NewReservationForGuideController.numberofvisitors-1;
    		msg+=NewReservationForGuideController.orderdetails;
    		NewReservationForGuideController.orderdetails="";
        	msg+="\nFull price before discount: " + 50*visitorsnumber;
        	msg+="\nDiscount: 25%";
        	
    	}
    	/*else if(NewReservationForEmployeeController.flagE==1){//if its personal / family order (not booked in advance)
    	    type="ec;
    		visitorsnumber=NewReservationForEmployeeController.numberofvisitors;}
    	else //if its group order (not booked in advance)
    		type="eg";
    		discount=0.9;
    		visitorsnumber=NewReservationForEmployeeGroupController.numberofvisitors;}*/
    	totalprice=50*discount*visitorsnumber;
    	msg+="\nTotal price after discount: " + totalprice;
    	orderDetailsArea.setText(msg);
    }
    

}
