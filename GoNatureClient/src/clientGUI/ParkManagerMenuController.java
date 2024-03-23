package clientGUI;
import client.ClientUI;
import common.StaticClass;
import common.SwitchScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import logic.Message;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ParkManagerMenuController { 
	
    @FXML
    private Button dwellOkBtn;

    @FXML
    private TextField dwellTimeText;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button maxCapacityOkBtn;

    @FXML
    private TextField maxCapacityText;

    @FXML
    private ComboBox<String> parkNameCombo;

    @FXML
    private Button totalReportBtn;

    @FXML
    private Button usageReportBtn;
	public static String[] parks;
	
    @FXML
    public void initialize() {
    	System.out.println("park manager initialize");
    	String[] parks=getParksMangedByParkManger(StaticClass.username);
    	if(parks==null)
    		System.out.println("No parks found!");
    	else {
    		parkNameCombo.getItems().addAll((String[])parks);
    	}
    		
    	
    }
    
    private String[] getParksMangedByParkManger(String username) {
		String msg="getParksMangedByParkManger " + username;
		ClientUI.chat.accept(msg);	
    	return ParkManagerMenuController.parks;
	}

    @FXML
    void clickOnLogout(ActionEvent event) {
    	String message="logout "+StaticClass.username;
		try {
			ClientUI.chat.accept(message);
			System.out.println("ParkManagerMenuController> request Sent to server");
		}catch (Exception e){
			System.out.println("ParkManagerMenuController> Logout failed");
		}
		if(StaticClass.islogout) {
			StaticClass.islogout=false;
      		SwitchScreen.changeScreen(event,"/clientGUI/LoginController.fxml","/resources/LoginController.css");

		}
    }

    @FXML
    void ClickOnLogoutBtn(ActionEvent event) {

    }

    @FXML
    void ClickOnOkDwell(ActionEvent event) {

    }

    @FXML
    void clickOnOkMaxCapacity(ActionEvent event) {

    }

    @FXML
    void clickOnTotalBtn(ActionEvent event) {

    }

    @FXML
    void clickOnUsageReport(ActionEvent event) {

    }


}




