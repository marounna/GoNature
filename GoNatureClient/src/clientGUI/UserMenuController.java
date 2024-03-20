package clientGUI;

import java.io.IOException;


import client.ChatClient;
import client.ClientUI;
import common.StaticClass;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.Order;

public class UserMenuController {

	   @FXML
	    private TableView<Order> ApprovedOrdersTableField;

	    @FXML
	    private TableView<Order> WaitingListTable;

	    @FXML
	    private TableColumn<Order, String> approvedDate;

	    @FXML
	    private TableColumn<Order, String> approvedNumberVisitors;

	    @FXML
	    private TableColumn<Order, String> approvedOrderId;

	    @FXML
	    private TableColumn<Order, String> approvedParkName;

	    @FXML
	    private TableColumn<Order, String> approvedTime;

	    @FXML
	    private TableColumn<Order, String> approvedUpdate;

	    @FXML
	    private TableColumn<Order, String> cancelApprovedBtn;

	    @FXML
	    private TableColumn<Order, String> cancelWaitingList;

	    @FXML
	    private TableColumn<Order, String> dateWaitingList;

	    @FXML
	    private TableColumn<Order, String> numberVisitorsWaitingList;

	    @FXML
	    private TableColumn<Order, String> orderIdWaitingList;

	    @FXML
	    private TableColumn<Order, String> parkNameWaitingList;

	    @FXML
	    private TableColumn<Order, String> timeWaitingList;
	    
	    @FXML
	    private Button logoutBtn;

	    @FXML
	    private Button newReservationBtn;
	    

	    @FXML //user logs out, moving to login screen
	    void ClickOnLogOut(ActionEvent event) throws IOException {
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
        		SwitchScreen.changeScreen(stage,"/resources/LoginController.fxml"
        				,"/resources/LoginController.css");
			}
	    }

	    @FXML //moving to new reservation screen
	    void ClickOnNewReservation(ActionEvent event) throws IOException {
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        System.out.println("----------test---- "+ StaticClass.typeacc);
	        switch (StaticClass.typeacc) {
	        	case "customer":
	        		SwitchScreen.changeScreen(stage,"/resources/NewReservationForUserController.fxml"
	        				,"/resources/NewReservationForUserController.css");
	    	        break;
	        	case "guide":
	        		SwitchScreen.changeScreen(stage,"/resources/NewReservationForGuideController.fxml"
	        				,"/resources/NewReservationForGuideController.css");
	    	        break;
	        }	
	    }
	    @FXML//initialize the order details textArea field with the order details from the last screen
	    private void initialize() {
	    	StaticClass.ordersforapprovetable.clear();
	    	StaticClass.ordersforwaitingtable.clear();
	    	if(!StaticClass.username.equals("guest")) {
	    		ClientUI.chat.accept("userId " + StaticClass.username);}
	    	ClientUI.chat.accept("loadOrderForApproveTable " + StaticClass.userid);
	        approvedOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
	        approvedParkName.setCellValueFactory(new PropertyValueFactory<>("parkName"));
	        approvedDate.setCellValueFactory(new PropertyValueFactory<>("date"));
	        approvedTime.setCellValueFactory(new PropertyValueFactory<>("timeOfVisit"));
	        approvedNumberVisitors.setCellValueFactory(new PropertyValueFactory<>("numberOfVisitors"));
	        ApprovedOrdersTableField.setItems(FXCollections.observableArrayList(StaticClass.ordersforapprovetable));
	    	StaticClass.ordersforapprovetable.clear();
	    	ClientUI.chat.accept("loadOrderForWaitingTable " + StaticClass.userid);
	    	orderIdWaitingList.setCellValueFactory(new PropertyValueFactory<>("orderId"));
	        parkNameWaitingList.setCellValueFactory(new PropertyValueFactory<>("parkName"));
	        dateWaitingList.setCellValueFactory(new PropertyValueFactory<>("date"));
	        timeWaitingList.setCellValueFactory(new PropertyValueFactory<>("timeOfVisit"));
	        numberVisitorsWaitingList.setCellValueFactory(new PropertyValueFactory<>("numberOfVisitors"));
	        WaitingListTable.setItems(FXCollections.observableArrayList(StaticClass.ordersforwaitingtable));
	    }

}
