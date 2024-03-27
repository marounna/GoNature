package clientGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import logic.Message;


import java.io.IOException;
import java.util.Optional;
import javafx.stage.Stage;
import client.ChatClient;
import client.ClientUI;
import common.ChatIF;
import common.StaticClass;
import common.SwitchScreen;

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
	int flag=0;
	String simulation="";

    @FXML
    void clickOnBack(ActionEvent event) throws IOException {
    	orderDetailsArea.clear();
    	StaticClass.orderdetails="";
       // Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        switch(StaticClass.typeacc) {
        	case "guide":
        		SwitchScreen.changeScreen(event,"/clientGUI/NewReservationForGuideController.fxml"
        				,"/resources/NewReservationForGuideController.css");
	        	break;
        	case "customer":
        	case "guest":
        		SwitchScreen.changeScreen(event,"/clientGUI/NewReservationForUserController.fxml"
        				,"/resources/NewReservationForUserController.css");
	        	break;
        	case "park employee":
        		if(StaticClass.reservationtype.equals("eg"))//eg= employee group
            		SwitchScreen.changeScreen(event,"/clientGUI/NewReservationForGuideController.fxml"
            				,"/resources/NewReservationForGuideController.css");
        		else {
            		SwitchScreen.changeScreen(event,"/clientGUI/NewReservationForUserController.fxml"
            				,"/resources/NewReservationForUserController.css");
				}
	        	break;
        }
    }

    @FXML// user decide to pay now
    void clickOnPayNow(ActionEvent event) {
    	StaticClass.orderdetails="";
    	if (StaticClass.typeacc.equals("guide")&&flag==1) {//if its a guide and booked order, getting 12% discount 
    		flag=0;
    		totalprice=totalprice*0.88;
    	}
    	Alert alertpayment = new Alert(AlertType.INFORMATION);
    	alertpayment.setTitle("Payment Information");
    	alertpayment.setHeaderText(null);
    	alertpayment.setContentText("Total price for payment is: " + totalprice);

    	// Create custom ButtonTypes
    	ButtonType okButton = new ButtonType("Confirm", ButtonData.OK_DONE);
    	ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

    	// Set the ButtonTypes to the alert
    	alertpayment.getButtonTypes().setAll(okButton, cancelButton);

    	// Show the alert and wait for a response
    	Optional<ButtonType> result = alertpayment.showAndWait();
    	ClientUI.chat.accept("maxNumberOrder");
    	if (result.isPresent()) {
    		if (result.get() == okButton) {
    	    	//Message msg = new Message("checkAvailability", StaticClass.o1);
    	    	ClientUI.chat.accept("checkAvailability " + StaticClass.o1.getParkName() + " " + StaticClass.o1.getNumberOfVisitors()+ " "
    	    		+StaticClass.o1.getDate() + " " + StaticClass.o1.getTimeOfVisit());
    	    	if(StaticClass.available==0) {
    	        	Alert alertwaitinglist = new Alert(AlertType.INFORMATION);
    	        	alertwaitinglist.setTitle("Waiting list");
    	        	alertwaitinglist.setHeaderText(null);
    	        	alertwaitinglist.setContentText("The park is fully booked, do you want to enter watitng list?");
    	        	ButtonType okWaitingListButton = new ButtonType("Confirm", ButtonData.OK_DONE);
    	        	ButtonType cancelWaitingListButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    	        	alertwaitinglist.getButtonTypes().setAll(okWaitingListButton, cancelWaitingListButton);
    	        	Optional<ButtonType> resultWaitingList = alertwaitinglist.showAndWait();
    	        	if (resultWaitingList.isPresent()) {
    	        	    if (resultWaitingList.get() == okWaitingListButton) {//enter into waiting list 
    	        	    	ClientUI.chat.accept("maxNumberOrder");
    	        	    	System.out.println("PaymentController> type account = "+ StaticClass.typeacc);
    	    	    		if(StaticClass.typeacc.equals("guest")) {
    	    	    			ClientUI.chat.accept("waitingList " + StaticClass.o1.getParkName() + " " +StaticClass.userid + " " + StaticClass.o1.getDate() + " " +
        	        	    			StaticClass.o1.getTimeOfVisit() + " " +StaticClass.o1.getNumberOfVisitors()+" " + (StaticClass.maxorderid) + " " +(""+totalprice)+" "+StaticClass.o1.getEmail()
        	        	    			+" "+StaticClass.typeacc);
    	    	    		}
    	    	    		else {
    	        	    	ClientUI.chat.accept("waitingList " + StaticClass.o1.getParkName() + " " +StaticClass.username + " " + StaticClass.o1.getDate() + " " +
    	        	    			StaticClass.o1.getTimeOfVisit() + " " +StaticClass.o1.getNumberOfVisitors()+" " + (StaticClass.maxorderid) + " " +(""+totalprice)+" "+StaticClass.o1.getEmail()
    	        	    			+" "+StaticClass.typeacc);}
    	        	    }
    	        	}
    	    	}
    	    	else {
    	    		ClientUI.chat.accept("dwellTime " + StaticClass.o1.getParkName());
    	    		if(StaticClass.typeacc.equals("guest")) {
    	    			ClientUI.chat.accept("saveOrder " + StaticClass.o1.getParkName() + " " +StaticClass.userid + " " + StaticClass.o1.getDate() + " " +
    	    	    			StaticClass.o1.getTimeOfVisit() + " " +StaticClass.o1.getNumberOfVisitors()+" " + (StaticClass.maxorderid) + " " +(""+totalprice) + " "
    	    	    			+StaticClass.typeacc+" "+ StaticClass.reservationtype+" " +StaticClass.dwelltime+" "+StaticClass.o1.getEmail());
    	    		}
    	    		else {
    	    			ClientUI.chat.accept("saveOrder " + StaticClass.o1.getParkName() + " " +StaticClass.username + " " + StaticClass.o1.getDate() + " " +
    	    			StaticClass.o1.getTimeOfVisit() + " " +StaticClass.o1.getNumberOfVisitors()+" " + (StaticClass.maxorderid) + " " +(""+totalprice) + " "
    	    			+StaticClass.typeacc+" "+ StaticClass.reservationtype+" " +StaticClass.dwelltime+" "+StaticClass.o1.getEmail());
    	    		}
    	    	
    	    	}
    	    	
	        	Alert alertsimulation = new Alert(AlertType.INFORMATION);
	        	alertsimulation.setTitle("Simulation");
	        	alertsimulation.setHeaderText(null);
	        	simulation+="\nTotal price after discount: " + totalprice;
	        	String combinedText = "Order Details:\n" + simulation;
	        	alertsimulation.setContentText(combinedText);
	        	ButtonType okSimulationButton = new ButtonType("OK", ButtonData.OK_DONE);
	        	alertsimulation.getButtonTypes().setAll(okSimulationButton);
	        	Optional<ButtonType> resultWaitingList = alertsimulation.showAndWait();
	        	if (resultWaitingList.isPresent()) {
	        	    if (resultWaitingList.get() ==okSimulationButton) {
		    	    	switch(StaticClass.typeacc) {
		        	    	case "customer":
		        	    	case "guide":
		        	    	case "guest":
		        	    		SwitchScreen.changeScreen(event, "/clientGUI/UserMenuController.fxml"
		        	    				,"/resources/UserMenuController.css");
		        	    		break;
		        	    	case "park employee":
		        	    		SwitchScreen.changeScreen(event, "/clientGUI/EmployeeMenuController.fxml"
		        	    				,"/resources/EmployeeMenuController.css");
		    	    	}
	        	    }
	        	}
	      } else if (result.get() == cancelButton) {
	      }
    	}
    	
    }
    
    
    
    @FXML//initialize the order details textArea field with the order details from the last screen
    private void initialize() {
    	orderDetailsArea.clear();
    	switch (StaticClass.typeacc) {//if its personal order (booked in advance)
    		case "customer":
    		case "guest":
    			ClientUI.chat.accept("checkDiscount personal");
	        	break;
    		case "guide":
    			flag=1;//mark that its a guide user and its not casual order, so getting extra 12% discount
    			ClientUI.chat.accept("checkDiscount group");
    			discountLabel.setText("Click on pay now to get extra 12% discount!");
    			StaticClass.numberofvisitors--;
    			break;
    		case "park employee":
    			if(StaticClass.reservationtype.equals("ec")) {
        			ClientUI.chat.accept("checkDiscount casual_personal");}
    			else {
    				ClientUI.chat.accept("checkDiscount casual_group");}
    			break;
    	}
    	System.out.println(StaticClass.orderdetails);
    	msg+=StaticClass.orderdetails+"\nTotal price before discount: " +StaticClass.numberofvisitors*StaticClass.parkprice ;
    	msg+="\nDiscount: "+ (int)StaticClass.discount+"%";
    	simulation = msg;
    	totalprice=StaticClass.parkprice*(1-(0.01*StaticClass.discount))*StaticClass.numberofvisitors;
    	msg+="\nTotal price after discount: " + totalprice;
    	orderDetailsArea.setText(msg);
    }
    

}















/*else if(StaticClass.flagG==1) {//if its group order (booked in advance)
	type="g";
	flag=1;
	discountLabel.setText("Click on pay now for extra 12% discount");
	discount=0.75;
	visitorsnumber=StaticClass.numberofvisitors-1;
	msg+=StaticClass.orderdetails;
	StaticClass.orderdetails="";
	msg+="\nFull price before discount: " + 50*visitorsnumber;
	msg+="\nDiscount: 25%";
	*/
//}
/*else if(NewReservationForEmployeeController.flagE==1){//if its personal / family order (not booked in advance)
    type="ec;
	visitorsnumber=NewReservationForEmployeeController.numberofvisitors;}
else //if its group order (not booked in advance)
	type="eg";
	discount=0.9;
	visitorsnumber=NewReservationForEmployeeGroupController.numberofvisitors;}*/
