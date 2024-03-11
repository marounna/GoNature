package clientGUI;
import javafx.scene.control.Button;
import java.io.IOException;

import javax.print.DocFlavor.URL;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class OrderFrameController {

    @FXML
    private TextField orderIdField;
    @FXML
    private Button exitButton;
    @FXML
    private Button OKbtn;
    @FXML
    void exitAction(ActionEvent event) {
    	//ClientUI.chat.accept("100 nothing");
    	System.exit(0);
    }
    
    @FXML
    void handleOkAction(ActionEvent event) throws Exception {
        String orderId = orderIdField.getText();
		FXMLLoader loader = new FXMLLoader();
		if(orderId.isEmpty())
		{
			System.out.println("You must enter an id number");	
		}
		else {
			int i=2;
			String message = i+" orderExist" +" "+ orderId;
			i++;
			try {
				ClientUI.chat.accept(message);
			}catch (Exception e){
				System.out.println("OrderFrame> Order is not exist");
			}
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			loader = new FXMLLoader(getClass().getResource("/clientGUI/OrderForm.fxml"));
			Pane root = loader.load();
			OrderFormController orderForm1 = loader.getController();
			try {
				String loadMessage= i+" loadOrder " + orderId ;
			ClientUI.chat.accept(loadMessage);//Send Msg TO Server
			System.out.println("OrderFrameContoler> request Sent to server");
			orderForm1.loadOrder(ChatClient.o1);
			}catch(Exception e) {
				System.out.println(e+" OrderFrameContoler> OrderForm1 is not good");
			}
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/clientGUI/OrderFrame.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
		}
    } 
}

