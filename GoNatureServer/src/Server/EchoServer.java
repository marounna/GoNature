package Server;

import java.io.*;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Server.DbController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.ClientConnectionStatus;
import logic.Order;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * This class overrides some of the methods in the abstract superclass to provide
 * specific functionality for an order management server.
 */
public class EchoServer extends AbstractServer {
    // Class variables
    final public static int DEFAULT_PORT = 5555;
    
    //static DbController dbcontroller = new DbController();
    public static ObservableList<ClientConnectionStatus> clientsList = FXCollections.observableArrayList();

    public  String dbCMessage="";
    // Constructor
    public EchoServer(int port) {
        super(port);
    }

    // Instance methods
    /**
     * This method handles any messages received from the client.
     * 
     * @param msg The message received from the client.
     * @param client The connection from which the message originated.
     */
    public void handleMessageFromClient(Object msg, ConnectionToClient client) {
        System.out.println("EchoServer> Message received: " + (String)msg + " from " + client);
        String message = (String) msg.toString();
        System.out.println("EchoServer> " + message);
        String[] result = message.split(" ");
       // System.out.println(result[0]);
        if (result[0].equals("1")) {
        	String Ip = client.toString() + " " ;
        	String[] clientIp=Ip.split(" ");
        	String hostIp= getHostIp();
        	ClientConnectionStatus thisClient=clientConnection(clientIp[0],hostIp);
        	updateClientConnect(thisClient);
        	sendToClient(client,"Connected successfully");
        	return;
        }
        else if (result[0].equals("100")) {
        	String Ip = client.toString() + " " ;
        	String[] clientIp=Ip.split(" ");
        	String hostIp= getHostIp();
        	ClientConnectionStatus thisClient=clientDisconnection(clientIp[0],hostIp);
        	updateClientConnect(thisClient);
        	sendToClient(client,"Disconnected successfully");
        	return;
        }
        String [] details=new String[result.length-1];
        for(int i=0; i<details.length;i++) {
        	details[i]=result[i+1];

        }   
        if (details.length < 1) {
            handleErrorMessage(client, "Invalid message format");
            return;
        }
        //System.out.println(details[1]);
        switch (details[0]) {
            case "updateOrderDetails":
                if (updateOrderDetails(details) == 1) {
                    sendToClient(client, "Order was successfully updated into the database!");
                } else {
                    sendToClient(client, "Order failed to be updated into the database!");
                }
                break;
            case "orderExist":
                if (details.length < 2) {
                    handleErrorMessage(client, "Invalid message format");
                    return;
                }
                if (orderExist(details[1]) == 1) {
                    sendToClient(client, "Order exists");
                } else {
                    sendToClient(client, "Order does not exist");
                }
                break;
            case "loadOrder":
                if (details.length < 2) {
                    handleErrorMessage(client, "Invalid message format");
                    return;
                }
                Connection conn = DbController.createDbConnection();
                Order order = DbController.loadOrder(conn, details[1]);
                if(order!=null) {
                	sendToClient(client,order.toString());
                	return;}
                else {
                sendToClient(client, "Failed to load order");
                break;
                }
            case "userExist":
                if (details.length < 2) {
                    handleErrorMessage(client, "Invalid message format");
                    return;
                }
                if (userExist(details[1],details[2]) == 1) {
                    sendToClient(client, "User exists");
                } else {
                    sendToClient(client, "User does not exist");
                }
                break;
                
            case "login":
                if (userLogin(details[1]) == 1) {
                    sendToClient(client, "User login successfully");
                } else {
                    sendToClient(client, "User login failed");
                }
                break;
            	
            default:
                handleErrorMessage(client, "Unknown command");
        }
    }
    
    

	private int userLogin(String userID) {
		Connection conn = DbController.createDbConnection();
		int login=DbController.userLogin(conn,userID);
		return login;
	}

	private int userExist(String username, String password) {
		  Connection conn = DbController.createDbConnection();
		  int exist=DbController.searchUser(conn,username,password);
		  if (exist==1) {return 1;} 
		  else {return 0;}
	}

	public void updateClientConnect(ClientConnectionStatus thisClient) {
		ClientConnectionStatus client = new ClientConnectionStatus(thisClient.ip, thisClient.host, thisClient.status);
		if(clientsList.indexOf(client) == -1 ) {
			clientsList.add(client);
		}	
		else {
			clientsList.remove(clientsList.indexOf(client));
			clientsList.add(client);
		}
		//ClientConnectionStatus.WriteToFile(client.ip+ " " +client.host+" "+client.status+ " "+ client.startTime+ " "  );
		System.out.println(client.ip +" Connected succsessfully!");
	}
	
	private ClientConnectionStatus clientDisconnection(String clientIp, String hostIp) {
		ClientConnectionStatus clientStatus = new ClientConnectionStatus(clientIp,hostIp,"Disconnected");
		return clientStatus;	
	}

	

	private ClientConnectionStatus clientConnection(String clientIp, String hostIp) {
		//System.out.println("test from client connection");
		ClientConnectionStatus clientStatus = new ClientConnectionStatus(clientIp,hostIp,"Connected");
		return clientStatus;	
	}

	private void sendToClient(ConnectionToClient client, String message) {
        try {
            client.sendToClient(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleErrorMessage(ConnectionToClient client, String errorMessage) {
        sendToClient(client, errorMessage);
    }


public static int orderExist(String order) {
	  Connection conn = DbController.createDbConnection();
	  int exist=DbController.searchOrder(conn,order);

	  if (exist==1) {return 1;} 
	  else {return 0;}
}

public static int updateOrderDetails(String[] orderdetails) {
	int update=0;
	Connection conn = DbController.createDbConnection();
	System.out.println("EchoServer> Sending the data to dbc ");
	update=DbController.updateOrder(conn,orderdetails);
	return update;
}

    protected void serverStarted() {
        System.out.println("EchoServer> Server listening for connections on port " + getPort());
    }

    protected void serverStopped() {
        System.out.println("EchoServer> Server has stopped listening for connections.");
    }
    
	public static String getHostIp() {
		try {
			InetAddress localHost= InetAddress.getLocalHost();
			return localHost.getHostAddress();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String getClientIp(ConnectionToClient client) {
	    return client.getInetAddress().getHostAddress();
	}
}
