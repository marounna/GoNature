package clientGUI;
import java.time.LocalDate;
import java.util.ArrayList;

import client.ClientUI;
import common.SwitchScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import logic.Message;

public class DmCancelledReport { 
    

    @FXML
    private Label headerLabel;

    @FXML
    private ComboBox<String> parkComboBox;
    
    @FXML
    private ComboBox<String>  reportTypeComboBox;
   

    @FXML
    private DatePicker datePicker;
    
    String reportType1="Cancelled";
    String reportType2="Not Cancelled but Not visited";
    
	
    @FXML
    public void initialize() {
    	
    	parkComboBox.getItems().addAll("All Area"+DmMenuController.parks); 
    	reportTypeComboBox.getItems().addAll(new String[]{reportType1,reportType2});
    }
    
    
    @FXML
    void ClickOnBackBtn(ActionEvent event) {
    	SwitchScreen.changeScreen(event,"/clientGUI/DmMenuController.fxml", "/resources/DmMenuController.css");
    }
    
    
    @SuppressWarnings("unchecked")
	@FXML
    void prepareReport(ActionEvent event) {
    	System.out.println("prepareReport");
    	String parkName=parkComboBox.getValue();
    	String reportType=reportTypeComboBox.getValue();
    	LocalDate pickedDate=datePicker.getValue();
    	
    	if("All Area".equals(parkName)) {
    		
    	}else {
	    	if(reportType1.equals(reportType)){
	    		int res=prepareReportType1(parkName,pickedDate);
	    	}
	    	if(reportType2.equals(reportType)){
	    		int res=prepareReportType2(parkName,pickedDate);
	    	}
    	}
    }
    
    //cancelled orders report
    private int prepareReportType1(String parkName, LocalDate pickedDate) {
		// TODO Auto-generated method stub
	return 0;	
	}

  //NOT cancelled orders BUT  Not Visited report
	private int prepareReportType2(String parkName, LocalDate pickedDate) {
		// TODO Auto-generated method stub
		return 0;	
	}


	


}