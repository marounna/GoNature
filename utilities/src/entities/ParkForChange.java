package entities;

import java.io.Serializable;

public class ParkForChange implements Serializable {
    private static final long serialVersionUID = 1L;

    private String parkName;
    private String dwellBefore;
    private String dwellAfter;
    private String maxCapacityBefore;
    private String maxCapacityAfter;

    // Constructor
    public ParkForChange(String parkName, String dwellBefore, String dwellAfter, String maxCapacityBefore, String maxCapacityAfter) {
        this.parkName = parkName;
        this.dwellBefore = dwellBefore;
        this.dwellAfter = dwellAfter;
        this.maxCapacityBefore = maxCapacityBefore;
        this.maxCapacityAfter = maxCapacityAfter;
    }

    // Getters and Setters
    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getDwellBefore() {
        return dwellBefore;
    }

    public void setDwellBefore(String dwellBefore) {
        this.dwellBefore = dwellBefore;
    }

    public String getDwellAfter() {
        return dwellAfter;
    }

    public void setDwellAfter(String dwellAfter) {
        this.dwellAfter = dwellAfter;
    }

    public String getMaxCapacityBefore() {
        return maxCapacityBefore;
    }

    public void setMaxCapacityBefore(String maxCapacityBefore) {
        this.maxCapacityBefore = maxCapacityBefore;
    }

    public String getMaxCapacityAfter() {
        return maxCapacityAfter;
    }

    public void setMaxCapacityAfter(String maxCapacityAfter) {
        this.maxCapacityAfter = maxCapacityAfter;
    }
}
