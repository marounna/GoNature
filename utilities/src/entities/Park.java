package entities;

import java.io.Serializable;

public class Park implements Serializable {
	private static final long serialVersionUID = 1L;
	private String parkString;
    private String maxCapacity;
    private String pricePerPerson; // Price for each visitor
    private String availableSpots; // Number of spots available for visitors
    private String visitTimeLimit;
    private String parkMangerId;

    // Constructor
    public Park(String name, String maxCapacity, String pricePerPerson,
                String availableSpots, String visitTimeLimit, String parkMangerId) {
        this.parkString = name;
        this.maxCapacity = maxCapacity;
        this.pricePerPerson = pricePerPerson;
        this.availableSpots = availableSpots;
        this.visitTimeLimit = visitTimeLimit;
        this.parkMangerId = parkMangerId;
    }

    public String getParkString() {
		return parkString;
	}




	public void setParkString(String parkString) {
		this.parkString = parkString;
	}




	public String getMaxCapacity() {
		return maxCapacity;
	}




	public void setMaxCapacity(String maxCapacity) {
		this.maxCapacity = maxCapacity;
	}




	public String getPricePerPerson() {
		return pricePerPerson;
	}




	public void setPricePerPerson(String pricePerPerson) {
		this.pricePerPerson = pricePerPerson;
	}




	public String getAvailableSpots() {
		return availableSpots;
	}




	public void setAvailableSpots(String availableSpots) {
		this.availableSpots = availableSpots;
	}




	public int getVisitTimeLimit() {
		return Integer.parseInt(visitTimeLimit);
	}




	public void setVisitTimeLimit(String visitTimeLimit) {
		this.visitTimeLimit = visitTimeLimit;
	}




	public String getParkMangerId() {
		return parkMangerId;
	}




	public void setParkMangerId(String parkMangerId) {
		this.parkMangerId = parkMangerId;
	}

	public int getvisiterinpark() {
		return Integer.parseInt(maxCapacity)-Integer.parseInt(availableSpots);
	}




    @Override
    public String toString() {
        return "Park{" +
                "name='" + parkString + '\'' +
                ", maxCapacity='" + maxCapacity + '\'' +
                ", pricePerPerson='" + pricePerPerson + '\'' +
                ", availableSpots='" + availableSpots + '\'' +
                ", visitTimeLimit='" + visitTimeLimit + '\'' +
                ", parkMangerId='" + parkMangerId + '\'' +
                '}';
    }

}

