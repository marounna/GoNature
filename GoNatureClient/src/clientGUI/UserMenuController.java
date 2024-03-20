package clientGUI;

import java.io.IOException;
import java.util.Optional;

import client.ChatClient;
import client.ClientUI;
import common.StaticClass;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
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
	    private TableColumn<Order, String> updateWaitingList;   
	    
	    @FXML
	    private TableColumn<Order, String> cancelWaitingList;
	    
	    
	    
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
        		SwitchScreen.changeScreen(stage,"/clientGUI/LoginController.fxml"
        				,"/resources/LoginController.css");
			}
	    }

	    @FXML //moving to new reservation screen
	    void ClickOnNewReservation(ActionEvent event) throws IOException {
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        System.out.println("----------test---- "+ StaticClass.typeacc);
	        switch (StaticClass.typeacc) {
	        	case "customer":
	        		SwitchScreen.changeScreen(stage,"/clientGUI/NewReservationForUserController.fxml"
	        				,"/resources/NewReservationForUserController.css");
	    	        break;
	        	case "guide":
	        		SwitchScreen.changeScreen(stage,"/clientGUI/NewReservationForGuideController.fxml"
	        				,"/resources/NewReservationForGuideController.css");
	    	        break;
	        }   
	    }

	    
	    
	    @FXML//initialize the order details textArea field with the order details from the last screen
	    private void initialize() {
	    	StaticClass.ordersforapprovetable.clear();
	    	StaticClass.ordersforwaitingtable.clear();
	    	if(!StaticClass.typeacc.equals("guest")) {
	    		ClientUI.chat.accept("userId " + StaticClass.username);}
	    	ClientUI.chat.accept("loadOrderForApproveTable " + StaticClass.userid);
	        approvedOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
	        approvedParkName.setCellValueFactory(new PropertyValueFactory<>("parkName"));
	        approvedDate.setCellValueFactory(new PropertyValueFactory<>("date"));
	        approvedTime.setCellValueFactory(new PropertyValueFactory<>("timeOfVisit"));
	        approvedNumberVisitors.setCellValueFactory(new PropertyValueFactory<>("numberOfVisitors"));
	        ApprovedOrdersTableField.setItems(FXCollections.observableArrayList(StaticClass.ordersforapprovetable));
	            approvedUpdate.setCellFactory(col -> {
	                Button updateButton = new Button("Update");
	                TableCell<Order, String> cell = new TableCell<Order, String>() {
	                    @Override
	                    public void updateItem(String item, boolean empty) {
	                        super.updateItem(item, empty);
	                        if (empty) {
	                            setGraphic(null);
	                        } else {
	                            setGraphic(updateButton);
	                            updateButton.setOnAction(event -> {
	                                Order order = getTableView().getItems().get(getIndex());
	                                StaticClass.orderid=order.getOrderId();
	                        		FXMLLoader loader = new FXMLLoader();                       		
                                	ClientUI.chat.accept("loadOrder "+StaticClass.orderid);
                                	if(StaticClass.typeacc.equals("guide")||(StaticClass.typeacc.equals("park employee")&&StaticClass.reservationtype.equals("eg"))) {
    	                    			loader = new FXMLLoader(getClass().getResource("/clientGUI/UpdateReservationForGuideController.fxml"));
                                	}
                                	else {
                                		loader = new FXMLLoader(getClass().getResource("/clientGUI/UpdateReservationForUserController.fxml"));}
									Pane root = null;
									try {
										root = loader.load();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	                                Stage stage = (Stage) ApprovedOrdersTableField.getScene().getWindow();

	                                switch (StaticClass.typeacc) {
		                                case "guest":
		                                case "customer":
			                                try {
				                                UpdateReservationForUserController updateusercontroller = loader.getController();
				                                updateusercontroller.loadOrder(StaticClass.o1);
			                                }
			                                catch (Exception e) {

											}
		                                	break;
		                                case "guide":
			                                UpdateReservationForGuideController updateguidecontroller = loader.getController();
			                                updateguidecontroller.loadOrder(StaticClass.o1);
                                			break;

	                                }
                                    Scene scene = new Scene(root);
                                    //scene.getStylesheets().add(getClass().getResource("/resources/UpdateReservationForUserController.css").toExternalForm());
                                    stage.setScene(scene);
                                    stage.show();
	                            
                        		
                	           });
	                        }
	                    }
	                };
	                return cell;
	            });
	        cancelApprovedBtn.setCellFactory(col -> {
	            Button cancelButton = new Button("X");
	            cancelButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
	            TableCell<Order, String> cell = new TableCell<Order, String>() {
	                @Override
	                public void updateItem(String item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (empty) {
	                        setGraphic(null);
	                    } else {
	                        setGraphic(cancelButton);
	                        cancelButton.setOnAction(event -> {
                                Order order = getTableView().getItems().get(getIndex());
                                StaticClass.orderid=order.getOrderId();
                            	Alert alertpayment = new Alert(AlertType.INFORMATION);
                            	alertpayment.setTitle("Cancel Reservation");
                            	alertpayment.setHeaderText(null);
                            	alertpayment.setContentText("Click OK to cancel order No. "+StaticClass.orderid);
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
                            			ClientUI.chat.accept("deleteOrder "+StaticClass.orderid+" "+ StaticClass.typeacc+" "+StaticClass.reservationtype);
                            	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    		        	SwitchScreen.changeScreen(stage,"/clientGUI/UserMenuController.fxml"
                            		        			,"/resources/UserMenuController.css");

                            	        }
                            			
                            		}
                            	
                                
	                     
	                        });
	                    }
	                }
	            };
	            return cell;
	        });
	        // Set the data for the table view
	        ApprovedOrdersTableField.setItems(FXCollections.observableArrayList(StaticClass.ordersforapprovetable));
	    	StaticClass.ordersforapprovetable.clear();
	    	
	    	
	    	
	    	//updateWaitingList  cancelWaitingList
	    	ClientUI.chat.accept("loadOrderForWaitingTable " + StaticClass.userid);
	    	orderIdWaitingList.setCellValueFactory(new PropertyValueFactory<>("orderId"));
	        parkNameWaitingList.setCellValueFactory(new PropertyValueFactory<>("parkName"));
	        dateWaitingList.setCellValueFactory(new PropertyValueFactory<>("date"));
	        timeWaitingList.setCellValueFactory(new PropertyValueFactory<>("timeOfVisit"));
	        numberVisitorsWaitingList.setCellValueFactory(new PropertyValueFactory<>("numberOfVisitors"));
	        WaitingListTable.setItems(FXCollections.observableArrayList(StaticClass.ordersforwaitingtable));
	        updateWaitingList.setCellFactory(col -> {
                Button updateButton = new Button("Update");
                TableCell<Order, String> cell = new TableCell<Order, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(updateButton);
                            updateButton.setOnAction(event -> {
                                Order order = getTableView().getItems().get(getIndex());
                                StaticClass.orderid=order.getOrderId();
                        		FXMLLoader loader = new FXMLLoader();                       		
                            	ClientUI.chat.accept("loadOrder "+StaticClass.orderid);
                            	if(StaticClass.typeacc.equals("guide")||(StaticClass.typeacc.equals("park employee")&&StaticClass.reservationtype.equals("eg"))) {
	                    			loader = new FXMLLoader(getClass().getResource("/clientGUI/UpdateReservationForGuideController.fxml"));
                            	}
                            	else {
                            		loader = new FXMLLoader(getClass().getResource("/clientGUI/UpdateReservationForUserController.fxml"));}
								Pane root = null;
								try {
									root = loader.load();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
                                Stage stage = (Stage) ApprovedOrdersTableField.getScene().getWindow();

                                switch (StaticClass.typeacc) {
	                                case "guest":
	                                case "customer":
		                                try {
			                                UpdateReservationForUserController updateusercontroller = loader.getController();
			                                updateusercontroller.loadOrder(StaticClass.o1);
		                                }
		                                catch (Exception e) {

										}
	                                	break;
	                                case "guide":
		                                UpdateReservationForGuideController updateguidecontroller = loader.getController();
		                                updateguidecontroller.loadOrder(StaticClass.o1);
                            			break;

                                }
                                Scene scene = new Scene(root);
                                //scene.getStylesheets().add(getClass().getResource("/resources/UpdateReservationForUserController.css").toExternalForm());
                                stage.setScene(scene);
                                stage.show();
                            
                    		
            	           });
                        }
                    }
                };
                return cell;
            });
	        cancelWaitingList.setCellFactory(col -> {
            Button cancelButton = new Button("X");
            cancelButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            TableCell<Order, String> cell = new TableCell<Order, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(cancelButton);
                        cancelButton.setOnAction(event -> {
                            Order order = getTableView().getItems().get(getIndex());
                            StaticClass.orderid=order.getOrderId();
                        	Alert alertpayment = new Alert(AlertType.INFORMATION);
                        	alertpayment.setTitle("Cancel Reservation");
                        	alertpayment.setHeaderText(null);
                        	alertpayment.setContentText("Click OK to cancel order No. "+StaticClass.orderid);
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
                        			ClientUI.chat.accept("deleteOrder "+StaticClass.orderid+" "+ StaticClass.typeacc+" "+StaticClass.reservationtype);
                        	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                		        	SwitchScreen.changeScreen(stage,"/clientGUI/UserMenuController.fxml"
                        		        			,"/resources/UserMenuController.css");

                        	        }
                        			
                        		}
                        	
                            
                     
                        });
                    }
                }
            };
            return cell;
        });
	        
	        
	        
	    }


}
