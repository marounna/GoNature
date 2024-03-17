package clientGUI;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ParkManagerReportViewer { 
    //maroun commmit Making Report 16/03/2024 10:00
    @FXML
    private BarChart<String, Number> barCHART;

    @FXML
    public void initialize() {
        Object[][] individualTimeVisitArray = {
                {"8:00", 15},{"9:00", 30},{"10:00", 9}, {"11:00", 15},
                {"12:00", 30}, {"13:00", 9},{"14:00", 5}, {"15:00", 7},
                {"16:00", 9}, {"17:00", 6},{"18:00", 30},{"19:00", 10}
        };

        Object[][] groupTimeVisitArray = {
                {"8:00", 15},{"9:00", 30},{"10:00", 4}, {"11:00", 22},
                {"12:00", 10}, {"13:00", 6},{"14:00", 15}, {"15:00", 34},
                {"16:00", 6}, {"17:00", 15},{"18:00", 10},{"19:00", 10}
        };

        XYChart.Series<String, Number> individualSeries = new XYChart.Series<>();
        individualSeries.setName("Individual Visits");

        XYChart.Series<String, Number> groupSeries = new XYChart.Series<>();
        groupSeries.setName("Group Visits");

        for (Object[] data : individualTimeVisitArray) {
            individualSeries.getData().add(new XYChart.Data<>((String)data[0], (int)data[1]));
        }

        for (Object[] data : groupTimeVisitArray) {
            groupSeries.getData().add(new XYChart.Data<>((String)data[0], (int)data[1]));
        }

        barCHART.getData().addAll(individualSeries, groupSeries);

        
        CategoryAxis xAxis = (CategoryAxis) barCHART.getXAxis();
        xAxis.setLabel("Time");

        NumberAxis yAxis = (NumberAxis) barCHART.getYAxis();
        yAxis.setLabel("Number of Visits");
    }
}
