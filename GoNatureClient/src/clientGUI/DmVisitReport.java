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

public class DmVisitReport { 

    public static int[] resGroup;
    public static int[] resInd;
    public static int parkVisitTimeLimit;
    public static int parkCapacityOfVisitors;
    

    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private Label totalLabel;

    @FXML
    private ComboBox<String> parkComboBox;

    @FXML
    private DatePicker datePicker;
	
    @FXML
    public void initialize() {

    	parkComboBox.getItems().addAll(DmMenuController.parks); 
    }
    
    
    @FXML
    void ClickOnBackBtn(ActionEvent event) {
    	SwitchScreen.changeScreen(event, "/clientGUI/DmMenuController.fxml","/resources/DmMenuController.css");
    }
    
    
    @SuppressWarnings("unchecked")
	@FXML
    void prepareReport(ActionEvent event) {
    	System.out.println("prepareReport");
    	System.out.println(parkComboBox.getValue());
    	System.out.println(datePicker.getValue().toString());
    	//////Collecting Data
    	LocalDate chosenDate=datePicker.getValue();
    	String parkName = parkComboBox.getValue();
    	int[] indTimeEntryVisitors=getIndTimeEntryVisitors(parkName,chosenDate);
    	int[] groupTimeEntryVisitors=getGroupTimeEntryVisitors(parkName,chosenDate);
    	
    	int stayTime=getParkVisitTimeLimit(parkName);
    	int ParkMaxCapacity=getParkMaxCapacity(parkName);
    	///Calculate the stay time of each array
    	int[] indTimeEntryVisitorsAndStayTime=calculateStayTime(indTimeEntryVisitors,stayTime);
    	int[] groupTimeEntryVisitorsAndStayTime=calculateStayTime(groupTimeEntryVisitors,stayTime);
    	
    	printIntArray(indTimeEntryVisitors, "indTimeEntryVisitors");
    	printIntArray(indTimeEntryVisitorsAndStayTime, "indTimeEntryVisitorsAndStayTime");
    	///////////////////////////////////////////////////////////////////////////////////////
    	///making the graph
    	lineChart.getData().clear();
    	System.out.println("graph making");

        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(7);
        xAxis.setUpperBound(20);
        xAxis.setTickUnit(1); // Set tick unit to 1 hour
        
        NumberAxis yAxies = (NumberAxis) lineChart.getYAxis();
        //yAxies.setAutoRanging(false);
        yAxies.setLowerBound(0);
        yAxies.setUpperBound(ParkMaxCapacity);
        yAxies.setTickUnit(2); // Set tick unit to 2 visitors
         

         XYChart.Series<Number, Number> indSeries = new XYChart.Series<>();
         for (int i = 0; i < indTimeEntryVisitorsAndStayTime.length; i++) {
             indSeries.getData().add(new XYChart.Data<>(i + 8, indTimeEntryVisitorsAndStayTime[i]));
         }
         indSeries.setName("Individual Visitors");

         XYChart.Series<Number, Number> groupSeries = new XYChart.Series<>();
         for (int i = 0; i < groupTimeEntryVisitorsAndStayTime.length; i++) {
             groupSeries.getData().add(new XYChart.Data<>(i + 8, groupTimeEntryVisitorsAndStayTime[i]));
         }
         groupSeries.setName("Group Visitors");

         lineChart.getData().addAll(indSeries, groupSeries);
         System.out.println("graph making end");

    	
    }


	private int[] calculateStayTime(int[] arr, int stayTime) {
		int[] res=new int[arr.length+1];
		
		for(int i=0;i<arr.length-stayTime+1;i++) {
			for(int j=0;j<stayTime;j++) {
				res[i+j]+=arr[i];
			}
		}
		for(int i=arr.length-stayTime+1;i<arr.length;i++) {
			for(int j=i;j<res.length;j++) {
				res[j]+=arr[i];
			}
				
		}
		//res[arr.length]+=arr[arr.length-1];	
		return res;
	}


	private int getParkVisitTimeLimit(String parkName) {
		Message msg=new Message("getParkVisitTimeLimit", parkName);
		ClientUI.chat.acceptObj(msg);
		return DmVisitReport.parkVisitTimeLimit;
		//return 3;
	}
	
	private int getParkMaxCapacity(String parkName) {
		Message msg=new Message("getParkMaxCapacity", parkName);
		ClientUI.chat.acceptObj(msg);
		return DmVisitReport.parkCapacityOfVisitors;
		//return 50;
	}


	private int[] getGroupTimeEntryVisitors(String parkName, LocalDate chosenDate) {
		ArrayList<Object> dataToSend=new ArrayList<>();
		dataToSend.add(parkName);
		dataToSend.add(chosenDate);
		Message msg=new Message("getGroupTimeEntryVisitors", dataToSend);
		ClientUI.chat.acceptObj(msg);
		//return new int[]{2, 3, 4, 5, 6, 7, 8, 4, 9, 2, 2, 1};
		return DmVisitReport.resGroup;
		
	}


	private int[] getIndTimeEntryVisitors(String parkName, LocalDate chosenDate) {
		ArrayList<Object> dataToSend=new ArrayList<>();
		dataToSend.add(parkName);
		dataToSend.add(chosenDate);
		Message msg=new Message("getIndTimeEntryVisitors", dataToSend);
		ClientUI.chat.acceptObj(msg);
		//return new int[]{0, 0, 2, 3, 3, 1, 5, 0, 3, 2, 1, 1};
		return DmVisitReport.resInd;
	}

	
	public  void printIntArray(int[] arr,String name) {
        System.out.print(name+": [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }



}