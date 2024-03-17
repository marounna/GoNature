package logic;

public class Order {
	    private String parkName;
	    private String orderNumber;
	    private String timeOfVisit; 
	    private String numberOfVisitors;
	    private String telephoneNumber;
	    private String email;

	    public Order(String parkName, String orderNumber, String timeOfVisit, String numberOfVisitors2, String telephoneNumber,String Email) {
	        
	    	this.parkName = parkName;
	        this.orderNumber = orderNumber;
	        this.timeOfVisit = timeOfVisit;
	        this.numberOfVisitors = numberOfVisitors2;
	        this.telephoneNumber = telephoneNumber;
	        this.email=Email;
	    }

	    // Getters
	    public String getParkName() {
	        return parkName;
	    }

	    public String getOrderNumber() {
	        return orderNumber;
	    }

	    public String getTimeOfVisit() {
	        return timeOfVisit;
	    }

	    public String getNumberOfVisitors() {
	        return numberOfVisitors;
	    }

	    public String getTelephoneNumber() {
	        return telephoneNumber;
	    }
	    public String getEmail() {
	    	return email;
	    }

	    // Setters
	    public void setParkName(String parkName) {
	    	if(parkName!=null)
	        this.parkName = parkName;
	    	else
	    		this.parkName="";
	    		
	    }

	    public void setOrderNumber(String orderNumber) {
	        if(orderNumber!=null)
	        	this.orderNumber = orderNumber;
		    	else
		    		this.orderNumber="";
	    }

	    public void setTimeOfVisit(String timeOfVisit) {      
	        if(timeOfVisit!=null)
	        	this.timeOfVisit = timeOfVisit;
		    	else
		    		this.timeOfVisit="";
	    }

	    public void setNumberOfVisitors(String numberOfVisitors) {
	        if(numberOfVisitors!=null)
	        	this.numberOfVisitors = numberOfVisitors;
		    	else
		    		this.numberOfVisitors="";
	    }

	    public void setTelephoneNumber(String telephoneNumber) {
	        if(telephoneNumber!=null)
	        	this.telephoneNumber = telephoneNumber;
		    	else
		    		this.telephoneNumber="";
	    }
	    public void setEmail(String Email) {
	        if(Email!=null)
	        	this.email = Email;
		    	else
		    		this.email="";
	    }
	    
	    @Override
	    public String toString() {
	        return String.format("%s %s %s %s %s %s\n",parkName,orderNumber,timeOfVisit,numberOfVisitors,telephoneNumber,email);
	    	//return String.format("%s fix me later i am in Order class tosting method ",parkName);
	    }
}
