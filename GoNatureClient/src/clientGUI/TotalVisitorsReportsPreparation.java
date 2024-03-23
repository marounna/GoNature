package clientGUI;
import java.io.IOException;
import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import common.SwitchScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import logic.Message;

public class TotalVisitorsReportsPreparation { 

    public static int[] res;
    
    @FXML
    private ComboBox<String>parkComboBox;

	@FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private ComboBox<Integer> monthComboBox;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label  totalLabel;

	
    @FXML
    public void initialize() {
    	
    	//this should be a parks that managed by the specific parkmanager
    	parkComboBox.getItems().addAll(ParkManagerMenuController.parks);
    	//set upcompobox year and month
    	yearComboBox.getItems().addAll(2024, 2023);
    	for (int i = 1; i <= 12; i++) {
            monthComboBox.getItems().add(i);
        }
    }
    
    
    @FXML
    void ClickOnBackBtn(ActionEvent event) {
    	SwitchScreen.changeScreen(event,"/clientGUI/ParkManagerMenuController.fxml","/clientGUI/ParkManagerMenuController.css");
    }
    
    @FXML
    void prepareReport(ActionEvent event) {
    	String parkName =parkComboBox.getValue();
        int year = yearComboBox.getValue();
        int month = monthComboBox.getValue();

        int result[]= getTotalVisitorsReport(parkName,month,year);
       
        if(result!=null) {
        int groups = result[0];
        int individuals= result[1];
        int total=individuals+groups;
        // Update PieChart data
        pieChart.getData().clear();
        pieChart.getData().add(new PieChart.Data("Individuals "+individuals, individuals));
        pieChart.getData().add(new PieChart.Data("Groups"+groups, groups));

        // Update total label
        totalLabel.setText("Total: " + total);
        }else {
        totalLabel.setText("No data found! " );
        }
    }


	private int[] getTotalVisitorsReport(String parkName, int month, int year) {
		ArrayList<Object> dataToSend=new ArrayList<>();
		dataToSend.add(parkName);
		dataToSend.add(month);
		dataToSend.add(year);
		Message msg=new Message("getTotalVisitorsByYearAndMonth", dataToSend);
		ClientUI.chat.acceptObj(msg);		
		return TotalVisitorsReportsPreparation.res;//static parameter will be updated from handleMessagFromServer
	}
    



}