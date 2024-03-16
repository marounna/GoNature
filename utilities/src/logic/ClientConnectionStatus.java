package logic;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClientConnectionStatus implements Comparable<ClientConnectionStatus>{

	public String ip;
	public String host;
	public String status; 
	public final String startTime;
	/**
	* Constructs a ClientConnectionStatus object with the provided parameters.
	*
	* @param ip       the IP address of the client
	* @param host     the host of the client
	* @param status   the status of the client's connection
	*/
	public ClientConnectionStatus(String ip, String host, String status){
		
		LocalTime currentTime = LocalTime.now();

	    //Define the format of the time string
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	    this.startTime = currentTime.format(formatter);
        this.ip = ip;
        this.host = host; 
        this.status = status;
        
    }
	
	public ClientConnectionStatus(String ip, String host, String status,String startTime){
        this.ip = ip;
        this.host = host; 
        this.status = status;
        this.startTime=startTime;
	}
	
	
	
	/*public static void WriteToFile(String data) {
		System.out.println("this is WriteFile");
		String content = data;
		try (FileWriter writer = new FileWriter("tableData.txt");
		     BufferedWriter bw = new BufferedWriter(writer)) {
		    bw.write(content);   
		} catch (IOException e) {
		    System.err.format("IOException: %s%n", e);
		}
	}
	
	public static String ReadFromFile() {
		System.out.println("this is ReadFile");
        StringBuilder contentBuilder = new StringBuilder();
        // Read the file content
        try (BufferedReader reader = new BufferedReader(new FileReader("tableData.txt"))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                contentBuilder.append(currentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Clear the file content
        try (FileWriter writer = new FileWriter("tableData.txt")) {
            writer.write(""); // Writing an empty string to overwrite the file
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(contentBuilder.toString());
        return contentBuilder.toString();
    }*/
	
	/**
	* Returns the IP address of the client.
	*
	* @return the IP address of the client
	*/
	public String getIp() {
		return ip;
	}
	/**
	* Sets the IP address of the client.
	*
	* @param ip the IP address to be set
	*/
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	* Returns the host of the client.
	*
	* @return the host of the client
	*/
	public String getHost() {
		return host;
	}
	/**
	* Sets the host of the client.
	*
	* @param host the host to be set
	*/
	public void setHost(String host) {
		this.host = host;
	}
	/**
	* Returns the status of the client's connection.
	*
	* @return the status of the client's connection
	*/
	public String getStatus() {
		return status;
	}
	/**
	* Sets the status of the client's connection.
	*
	* @param status the status to be set
	*/
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	* Returns the start time of the client's connection.
	*
	* @return the start time of the client's connection
	*/
	public String getStartTime() {
		return startTime;
	}
	/**
	* Calculates the hash code of the object based on the host and IP address.
	*
	* @return the hash code of the object
	*/
	// create a hashcode using Object hash method using the host and ip strings
	@Override
	public int hashCode() {
		return Objects.hash(this.host, this.ip);
	}
	/**
	* Checks if two objects are equal by comparing their host and IP address.
	*
	* @param obj the object to compare
	* @return true if the objects are equal, false otherwise
	*/
	// check if two objects are equal
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		ClientConnectionStatus other = (ClientConnectionStatus) obj;
		return Objects.equals(this.host, other.host) && Objects.equals(this.ip, other.ip);
	}
	/**
	* Returns a string representation of the object.
	*
	* @return a string representation of the object
	*/
	@Override
	public String toString() {
		return "[ip=" + getIp() + ", host=" + getHost() + ", status=" + getStatus() + "]";
	}
	/**
	* Compares this object with another ClientConnectionStatus object based on IP addresses.
	*
	* @param client the ClientConnectionStatus object to compare
	* @return a negative integer if this object is less than the other object,
	*         zero if they are equal, a positive integer if this object is greater than the other object
	*/
	@Override
	public int compareTo(ClientConnectionStatus client) {
		return this.getIp().compareTo(client.getIp());
	}
	public StringProperty ipProperty() {
		return new SimpleStringProperty(ip);
	}
	public StringProperty hostProperty() {
		return new SimpleStringProperty(host);
	}
	public StringProperty statusProperty() {
		return new SimpleStringProperty(status);
	}	
	public StringProperty startTimeProperty() {
		return new SimpleStringProperty(startTime);
	}

}

