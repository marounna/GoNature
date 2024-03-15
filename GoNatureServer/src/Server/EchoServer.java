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

	public static String is_logged="";
	public static String type;
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
        String returnmsg="";
        String message = (String) msg.toString();
        System.out.println("EchoServer> " + message);
        String[] result = message.split(" ");
        System.out.println(result[0]);
        if (result[0].equals("1")) {
        	String Ip = client.toString() + " " ;
        	String[] clientIp=Ip.split(" ");
        	String hostIp= getHostIp();
        	ClientConnectionStatus thisClient=clientConnection(clientIp[0],hostIp);
        	updateClientConnect(thisClient);
        	sendToClient(client,"Connected succeed");
        	return;
        }
        else if (result[0].equals("100")) {
        	String Ip = client.toString() + " " ;
        	String[] clientIp=Ip.split(" ");
        	String hostIp= getHostIp();
        	ClientConnectionStatus thisClient=clientDisconnection(clientIp[0],hostIp);
        	updateClientConnect(thisClient);
        	sendToClient(client,"Disconnected succeed");
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
        Connection conn = DbController.createDbConnection();
        switch (details[0]) {
            case "updateOrderDetails":
                if (updateOrderDetails(details,conn) == 1) {
                    sendToClient(client, "updateOrderDetails succeed");
                } else {
                    sendToClient(client, "updateOrderDetails failed");
                }
                break;
            case "orderExist":
                if (details.length < 2) {
                    handleErrorMessage(client, "Invalid message format");
                    return;
                }
                if (orderExist(details[1],conn) == 1) {
                    sendToClient(client, "orderExist succeed");
                } else {
                    sendToClient(client, "OrderExist failed");
                }
                break;
            case "loadOrder":
                if (details.length < 2) {
                    handleErrorMessage(client, "Invalid message format");
                    return;
                }
                Order order = DbController.loadOrder(conn, details[1]);
                if(order!=null) {
                	returnmsg="loadOrder "+order.toString();
                	sendToClient(client,returnmsg);
                	return;}
                else {
                sendToClient(client, "loadOrder failed");
                break;
                }
            case "userExist":
                if (details.length < 2) {
                    handleErrorMessage(client, "Invalid message format");
                    return;
                }
                if (userExist(details[1],details[2],conn) == 1) {
                    sendToClient(client, "userExist succeed "+ is_logged + " " + type);//-----------------------------------------------
                } else {
                    sendToClient(client, "userExist failed");
                }
                break;     
            case "login":
                if (userLogin(details[1],details[2],conn) == 1) {
                    sendToClient(client, "login succeed "+ details[1]);
                } else {
                    sendToClient(client, "login failed");
                }
                break;
            case "logout":
            	  if (userLogout(details[1],conn) == 1) {
                      sendToClient(client, "logout succeed");
                  } else {
                      sendToClient(client, "logout failed");
                  }
                  break;
            case "parkNames":
			String parknames="";
			try {
				parknames = DbController.getParkNames(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            	sendToClient(client,"parkNames " +  parknames);
            default:
                handleErrorMessage(client, "Invalid command");
        }
    }
    
    

	private int updateOrderDetails(String[] details, Connection conn) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int userLogout(String username, Connection conn) {
		int logout=DbController.userLogout(conn,username);
		return logout;
	}

	private int userLogin(String username,String password, Connection conn) {
		int login=DbController.userLogin(conn,username);
		return login;
	}

	private int userExist(String username, String password, Connection conn) {
		  System.out.println("echo server userExist method");
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
		System.out.println(client.ip +" Connected succsessfully!");
	}
	
	private ClientConnectionStatus clientDisconnection(String clientIp, String hostIp) {
		ClientConnectionStatus clientStatus = new ClientConnectionStatus(clientIp,hostIp,"Disconnected");
		return clientStatus;	
	}

	private ClientConnectionStatus clientConnection(String clientIp, String hostIp) {
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


	public static int orderExist(String order, Connection conn) {
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
