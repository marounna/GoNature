package clientGUI;

import java.io.IOException;
import java.util.Optional;

import client.ChatClient;
import client.ClientUI;
import common.StaticClass;
import common.SwitchScreen;
import javafx.application.Platform;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


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
	    	StaticClass.orderalert=1;
	    	System.out.println("order alert= "+ StaticClass.orderalert);
	    	String message="logout "+StaticClass.username;
			try {
				ClientUI.chat.accept(message);
				System.out.println("UserMenuController> request Sent to server");
			}catch (Exception e){
				System.out.println("UserMenuController> Logout failed");
			}
			if(StaticClass.islogout) {
				StaticClass.islogout=false;
		        //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        		SwitchScreen.changeScreen(event,"/clientGUI/LoginController.fxml"
        				,"/resources/LoginController.css");
			}
	    }

	    @FXML //moving to new reservation screen
	    void ClickOnNewReservation(ActionEvent event) throws IOException {
	       //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        System.out.println("----------test---- "+ StaticClass.typeacc);
	        switch (StaticClass.typeacc) {
	        	case "customer":
	        		SwitchScreen.changeScreen(event,"/clientGUI/NewReservationForUserController.fxml"
	        				,"/resources/NewReservationForUserController.css");
	    	        break;
	        	case "guide":
	        		SwitchScreen.changeScreen(event,"/clientGUI/NewReservationForGuideController.fxml"
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
                                	/*if(StaticClass.typeacc.equals("guide")||(StaticClass.typeacc.equals("park employee")&&StaticClass.reservationtype.equals("eg"))) {
    	                    			loader = new FXMLLoader(getClass().getResource("/clientGUI/UpdateReservationForGuideController.fxml"));
                                	}
                                	else {*/
                                	loader = new FXMLLoader(getClass().getResource("/clientGUI/UpdateReservationForUserController.fxml"));
									Pane root = null;
									try {
										root = loader.load();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
	                                Stage stage = (Stage) ApprovedOrdersTableField.getScene().getWindow();
	                                UpdateReservationForUserController updateusercontroller = loader.getController();
	                                updateusercontroller.loadOrder(StaticClass.o1);
                                    Scene scene = new Scene(root);
                                    scene.getStylesheets().add(getClass().getResource("/resources/UpdateReservationForUserController.css").toExternalForm());
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
                            	//ClientUI.chat.accept("maxNumberOrder");
                            	if (result.isPresent()) {
                            		if (result.get() == okButton) {
                            			ClientUI.chat.accept("deleteOrder "+StaticClass.orderid+" "+ StaticClass.typeacc+" "+StaticClass.reservationtype);
                            	        //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    		        	SwitchScreen.changeScreen(event,"/clientGUI/UserMenuController.fxml"
                            		        			,"/resources/UserMenuController.css");

                            	        }
                            			
                            		}
	                        });
	                    }
	                }
	            };
	            return cell;
	        });
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
                            	if(StaticClass.typeacc.equals("guide")) {
	                    			//loader = new FXMLLoader(getClass().getResource("/clientGUI/UpdateReservationForGuideController.fxml"));
                            		StaticClass.updatelabel="guide";//changing the label variable for the label on update screen 
                            	}
                            	loader = new FXMLLoader(getClass().getResource("/clientGUI/UpdateReservationForUserController.fxml"));
								Pane root = null;
								try {
									root = loader.load();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
                                Stage stage = (Stage) ApprovedOrdersTableField.getScene().getWindow();
                                UpdateReservationForUserController updateusercontroller = loader.getController();
                                updateusercontroller.loadOrder(StaticClass.o1);
                                Scene scene = new Scene(root);
                                scene.getStylesheets().add(getClass().getResource("/resources/UpdateReservationForUserController.css").toExternalForm());
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
                        	//ClientUI.chat.accept("maxNumberOrder");
                        	if (result.isPresent()) {
                        		if (result.get() == okButton) {
                        			ClientUI.chat.accept("deleteOrder "+StaticClass.orderid+" "+ StaticClass.typeacc+" "+StaticClass.reservationtype);
                        	        //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                		        	SwitchScreen.changeScreen(event,"/clientGUI/UserMenuController.fxml"
                        		        			,"/resources/UserMenuController.css");

                        	        }
                        			
                        		}
                        	
                            
                     
                        });
                    }
                }
            };
            return cell;
        });
	       if(StaticClass.orderalert==1) {
	        	StaticClass.orderalert=0;
	        	Platform.runLater(() -> {
	        	   checkOrdersForAlerts();
	        	});
	        }
	        
	        
	    }

	    
	    
	    private void checkOrdersForAlerts() {
	    	LocalDate nowDate = LocalDate.now(); // Current date
	    	LocalTime nowTime = LocalTime.now(); // Current time

	    	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm"); // Adjust this to match your time format

	    	for (Order order : StaticClass.ordersforapprovetable) {
	    	    try {
	    	        LocalDate orderDate = LocalDate.parse(order.getDate(), dateFormatter);
	    	        LocalTime orderTime = LocalTime.parse(order.getTimeOfVisit(), timeFormatter); // Assuming you have getTimeOfVisit method

	    	        // Check if the order date is tomorrow
	    	        if (orderDate.isEqual(nowDate.plusDays(1))) {
	                    // Alert the user about the order scheduled for tomorrow
	                    Alert alertdate = new Alert(AlertType.INFORMATION);
	                    alertdate.setTitle("Order Reminder");
	                    alertdate.setHeaderText(null);
	                    alertdate.setContentText("Order No. " + order.getOrderId() + " date is tomorrow.\nPlease confirm the order up to two hours from now.");
	                    alertdate.showAndWait();
	                } else if (orderDate.isEqual(nowDate)&& orderTime.isBefore(nowTime)) {
	                	ClientUI.chat.accept("deleteOrder "+order.getOrderId()+" "+StaticClass.typeacc+ " "+ StaticClass.reservationtype);
	                    System.out.println("UserMenuController> Order No. " + order.getOrderId() + " has passed its date and time and has been deleted.");
	                }
	            } catch (DateTimeParseException e) {
	                System.err.println("Error parsing date or time from order: " + e.getMessage());
	            }
	        }
	    	for (Order order : StaticClass.ordersforwaitingtable) {
	    	    try {
	    	        LocalDate orderDate = LocalDate.parse(order.getDate(), dateFormatter);
	    	        LocalTime orderTime = LocalTime.parse(order.getTimeOfVisit(), timeFormatter); // Assuming you have getTimeOfVisit method

	    	         if (orderDate.isEqual(nowDate)&& orderTime.isBefore(nowTime)) {
	                	ClientUI.chat.accept("deleteOrder "+order.getOrderId()+" "+StaticClass.typeacc+ " "+ StaticClass.reservationtype);
	                    System.out.println("UserMenuController> Order No. " + order.getOrderId() + " has passed its date and time and has been deleted.");
	                }
	            } catch (DateTimeParseException e) {
	                System.err.println("Error parsing date or time from order: " + e.getMessage());
	            }
	        }
	    }

}
