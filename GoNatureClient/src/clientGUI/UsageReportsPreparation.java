package clientGUI;
import java.util.ArrayList;

import client.ClientUI;
import common.SwitchScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import logic.Message;

public class UsageReportsPreparation { 
	
	public static int[][] res;

    @FXML
    private ComboBox<String>parkComboBox;

	@FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private ComboBox<Integer> monthComboBox;
    
    @FXML
    private ScatterChart<String, Number> scatterChart;


    @FXML
    private Label  totalLabel;

	
    @FXML
    public void initialize() {
    	
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
        String parkName = parkComboBox.getValue();
        int year = yearComboBox.getValue();
        int month = monthComboBox.getValue();

        int[][] result = getUsageReport(parkName, month, year);

      if(result!=null) {
		// Clear previous data from scatter chart
        scatterChart.getData().clear();

        // Set up y-axis range
        NumberAxis yAxis = (NumberAxis) scatterChart.getYAxis();
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Day");
        yAxis.setLabel("Hour");
        
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(7); 
        yAxis.setUpperBound(20); 
        yAxis.setTickUnit(1); // Tick unit of 1 hour
        scatterChart.setTitle("Usage report of park "+parkName);
        
        scatterChart.setPrefSize(600, 400);
        scatterChart.setAnimated(false); // Disable animation for better performance
        scatterChart.setLegendVisible(false); // Hide legend
        scatterChart.setHorizontalGridLinesVisible(false); // Hide horizontal grid lines

        // Add data to scatter chart
        for (int i = 0; i < 31; i++) {
        	 String day = String.valueOf(i + 1);
            for (int j = 0; j < 12; j++) {
                if (result[i][j] == 1) {
                    XYChart.Series<String, Number> series = new XYChart.Series<>();
                    series.getData().add(new XYChart.Data<>(day, j + 8)); // Days start from 1, hours start from 8
                    scatterChart.getData().add(series);
                }
            }
        }

        System.out.println("UsageReport>End");
}
      else {
    	  System.out.println("UsageReports>data was null");
      }
    }

    

	private int[][] getUsageReport(String parkName, int month, int year) {
		ArrayList<Object> dataToSend=new ArrayList<>();
		dataToSend.add(parkName);
		dataToSend.add(month);
		dataToSend.add(year);
		Message msg=new Message("getUsageReportByYearAndMonth", dataToSend);
		ClientUI.chat.acceptObj(msg);		
		return UsageReportsPreparation.res;//static parameter will be updated from handleMessagFromServer
	}



}
