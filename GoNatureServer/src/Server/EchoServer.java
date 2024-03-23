package Server;

import java.io.*;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import Server.DbController;
import entities.Park;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.ClientConnectionStatus;
import logic.Message;
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
        try {
        System.out.println("EchoServer> Message received: " + (String)msg + " from " + client);
        String returnmsg="";
        String message = (String) msg.toString();
        System.out.println("EchoServer> " + message);
        String[] result = message.split(" ");
        System.out.println(result[0]);
        if (result.length < 1) {
            handleErrorMessage(client, "Invalid message format");
            return;
        }
        Connection conn = DbController.createDbConnection();
        if(result[0].equals("park")) {
        	System.out.println("its park!~~~~~~~~~~~~~~~~");
        	result[0]="park";}
        if (msg instanceof Message) {
    		Message msgObject=((Message)msg);
    		String command=msgObject.getCommand();
    		Object payLoad=msgObject.getPayload();
    		System.out.println("EchoServer>Command and payload recived from client");
    		System.out.println("EchoServer>"+((Message)msg).getCommand()+","+((Message)msg).getPayload());
    		//getUsageReportByYearAndMonth
    		switch (command) {
			case "getTotalVisitorsByYearAndMonth": {
				try {
					if(payLoad instanceof ArrayList) {
						ArrayList<Object> monthYear=(ArrayList<Object>)payLoad;
						int year=(int)monthYear.remove(2);
		    			int month=(int)monthYear.remove(1);
		    			String parkNname=(String)monthYear.remove(0);
		    			int[] arr=getTotalVisitorsByYearAndMonth(conn,parkNname,month,year);
		    			if(arr!=null) {
		    			Message res=new Message(((Message)msg).getCommand(), arr);
						client.sendToClient(res);
		    			}
		    			else
		    			{
		    				  handleErrorMessage(client, "returned null integer[] from db ");
		    			}
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			}
			////getParksMangedByParkManger
			case "getUsageReportByYearAndMonth": {
				try {
					if(payLoad instanceof ArrayList) {
						ArrayList<Object> monthYear=(ArrayList<Object>)payLoad;
						int year=(int)monthYear.remove(2);
		    			int month=(int)monthYear.remove(1);
		    			String parkNname=(String)monthYear.remove(0);
		    			int[][] arr=getUsageReportByYearAndMonth(conn,parkNname,month,year);
		    			if(arr!=null) {
		    			Message res=new Message(((Message)msg).getCommand(), arr);
						client.sendToClient(res);
		    			}
		    			else
		    			{
		    				  handleErrorMessage(client, "returned null integer[][]  from db ");
		    			}
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;		
			}
			case "getParkVisitTimeLimit":{
				int parkVisitTimeLimit=DbController.getParkVisitTimeLimit(conn,(String)payLoad);//by parkName
				Message res=new Message(((Message)msg).getCommand(), parkVisitTimeLimit);
				client.sendToClient(res);
				break;
			}
			case "getParkMaxCapacity":{
				int getParkMaxCapacity=DbController.getParkMaxCapacity(conn,(String)payLoad);//by parkName
				Message res=new Message(((Message)msg).getCommand(), getParkMaxCapacity);
				client.sendToClient(res);
				break;
			}
			case "getGroupTimeEntryVisitors":{
				@SuppressWarnings("unchecked")
				ArrayList<Object> data=(ArrayList<Object>)payLoad;
				LocalDate chosenDate=(LocalDate)data.remove(1);
    			String parkNname=(String)data.remove(0);
    			int[] GroupTimeEntryVisitors=DbController.getGroupTimeEntryVisitors(conn,parkNname,chosenDate);
    			Message res=new Message(((Message)msg).getCommand(), GroupTimeEntryVisitors);
    			client.sendToClient(res);
				break;
			}
			case "getIndTimeEntryVisitors":{
				ArrayList<Object> data=(ArrayList<Object>)payLoad;
				LocalDate chosenDate=(LocalDate)data.remove(1);
    			String parkNname=(String)data.remove(0);
    			int[] IndTimeEntryVisitors=DbController.getIndTimeEntryVisitors(conn,parkNname,chosenDate);
    			Message res=new Message(((Message)msg).getCommand(), IndTimeEntryVisitors);
    			client.sendToClient(res);
				break;
			}
			default:
				handleErrorMessage(client, "Invalid command in instance of message");
				throw new IllegalArgumentException("Unexpected value: " + command);
			}
    	} 
        else {
        switch (result[0]) {
			case "getParksMangedByParkManger": {
					String[] arr=getParksMangedByParkManger(conn,result[1]);
					System.out.println("EchoServer>getParksMangedByParkManger");
	    			if(arr!=null) {
	    				for(int i=0;i<arr.length;i++) {
	    					System.out.println("EchoSrver> "+arr[i]);
	    				}
	    			Message res=new Message("getParksMangedByParkManger", arr);
					client.sendToClient(res);
	    			}
	    			else
	    			{
	    				handleErrorMessage(client, "returned null String[]  from db ");
	    			}
				break;
			}
        	case "connect":
            	String Ip = client.toString() + " " ;
            	String[] clientIp=Ip.split(" ");
            	String hostIp= getHostIp();
            	ClientConnectionStatus thisClient=clientConnection(clientIp[0],hostIp);
            	updateClientConnect(thisClient);
            	sendToClient(client,"Connected succeed");
            	break;
        	case "disconnect":
            	String Ip1 = client.toString() + " " ;
            	String[] clientIp1=Ip1.split(" ");
            	String hostIp1= getHostIp();
            	ClientConnectionStatus thisClient1=clientDisconnection(clientIp1[0],hostIp1);
            	updateClientConnect(thisClient1);
            	sendToClient(client,"Disconnected succeed");
            	break;	
            	
            case "updateOrder":
                int update =updateOrderDetails(conn,result[1],result[2],result[3],result[4],result[5],result[6],result[7],result[8]) ;
                if(update==1) {
                    if(DbController.needvisaalert.equals("yes")) {
                    	sendToClient(client, "updateOrder visaCredit");
                    	DbController.needvisaalert="no";
                    }
                    sendToClient(client, "updateOrder succeed");
                } 
                else if (update==10) {
                    sendToClient(client, "updateOrder waitinglist");
                }
                else {
                    sendToClient(client, "updateOrder failed");
                }
                //need to finish those if blocks so chat client will receive the visa value, and to change on dbcontroller
                //the total calculation, (when the total is less than zero, need to plus instead minus
                break;
            case "orderExist":
                if (result.length < 2) {
                    handleErrorMessage(client, "Invalid message format");
                    return;
                }
                if (orderExist(result[1],conn) == 1) {
                    sendToClient(client, "orderExist succeed");
                } else {
                    sendToClient(client, "OrderExist failed");
                }
                break;
            case "loadOrder":
                if (result.length < 2) {
                    handleErrorMessage(client, "Invalid message format");
                    return;
                }
                ArrayList<String> order = DbController.loadOrder(conn, result[1]);  
                if(order!=null) {
                	returnmsg="loadOrder "+order.get(0)+" "+ order.get(1) + " " +
                order.get(2)+" "+ order.get(3)+" "+order.get(4)+" "+order.get(5)+" "
                +order.get(6);
                	sendToClient(client,returnmsg);
                	return;}
                else {
                sendToClient(client, "loadOrder failed");
                break;
                }
            case "userExist":
                if (result.length < 2) {
                    handleErrorMessage(client, "Invalid message format");
                    return;
                }
                if (userExist(result[1],result[2],conn) == 1) {
                    sendToClient(client, "userExist succeed "+ is_logged + " " + type);
                } else {
                    sendToClient(client, "userExist failed");
                }
                break;     
            case "login":
                if (userLogin(result[1],result[2],conn) == 1) {
                    sendToClient(client, "login succeed "+ result[1]);
                } else {
                    sendToClient(client, "login failed");
                }
                break;
            case "logout":
            	  if (userLogout(result[1],conn) == 1) {
                      sendToClient(client, "logout succeed");
                  } else {
                      sendToClient(client, "logout failed");
                  }
                  break;
            case "park":
            	System.out.println("echoserver> its park case");
            	ArrayList<Park> parks = new ArrayList<>();
            	parks = Park(conn);
            	Message payload = new Message("park", parks);
				try {
					client.sendToClient(payload);
				} catch (IOException e) {
					e.printStackTrace();
				}
      		    break;
            case "priceCheck":
            	int price = parkPrice(conn,result[1]);
            	sendToClient(client, "priceCheck "+price);
            	break;
            	
            case "checkDiscount":
            	int parkdiscount = checkDiscount(conn,result[1]);
            	sendToClient(client, "checkDiscount "+parkdiscount);
            	break;
            case "checkAvailability":
            	int avaiable=checkAvailability(conn,result[1],result[2],result[3],result[4]);
            	sendToClient(client, "checkAvailability "+avaiable);
            	
            	break;
            case "waitingList":
            	int waitingList = enterWaitingList(conn,result[1],result[2],result[3],result[4],result[5],result[6],result[7]);
            	sendToClient(client, "waitingList "+waitingList);
            	break;
            case "maxNumberOrder":
            	int max=DbController.checkMax(conn);
            	sendToClient(client, "maxNumberOrder "+max );
            	break;	
            case "saveOrder":
            	int save = saveOrder(conn,result[1],result[2],result[3],result[4],result[5],result[6],result[7],result[8], result[9],result[10]);
            	sendToClient(client, "saveOrder " + save );
            	break;
            case "dwellTime":
            	String dwell=DbController.checkDwell(conn, result[1]);
            	sendToClient(client, "dwellTime " + dwell);
            	break;
            case "loadOrderForApproveTable":
            	ArrayList<Order> ordersForApproveTable = new ArrayList<>();
            	ordersForApproveTable = loadOrderForApproveTable(conn,result[1]);
            	Message payload1 = new Message("loadOrderForApproveTable", ordersForApproveTable);
			try {
				client.sendToClient(payload1);
			} catch (IOException e) {
				e.printStackTrace();
			}
            	break;
            case "loadOrderForWaitingTable": 
            	ArrayList<Order> ordersForWaitingListTable = new ArrayList<>();
            	ordersForWaitingListTable = loadOrderForWaitingListTable(conn,result[1]);
            	Message payload2 = new Message("loadOrderForWaitingTable", ordersForWaitingListTable);
			try {
				client.sendToClient(payload2);
			} catch (IOException e) {
				e.printStackTrace();
			}
				break;
            case "userId":
            	String userid=DbController.checkUserId(conn,result[1]);
            	sendToClient(client, "userId "+ userid);
            	break;
            case "deleteOrder":
            	String delete = DbController.DeleteOrder(conn, result[1],result[2],result[3]);
            	sendToClient(client, "deleteOrder "+delete);
            	break;
            case "updateWaitingList":
            	DbController.updateWaitingList(conn,result[1]);
            	sendToClient(client, "updateWaitingList succeed");
            	break;
            case "updateGuideRole":
            	try {
            		boolean isSucessfull = DbController.updateGuideRole(conn, result[1]);
            		Message payloadforupdaterole = new Message("updateRole", isSucessfull);
            		client.sendToClient(payloadforupdaterole);
				} catch (Exception e) {
					e.printStackTrace(); 
				}
            	break;
            default:
                handleErrorMessage(client, "Invalid command");
        }
        }
        }    catch(IOException e) {
       	 handleErrorMessage(client, "Invalid command");
       	 e.printStackTrace();
       	}
    }
    
    
	private String[] getParksMangedByParkManger(Connection conn, String username) {
		System.out.println("EchoServersending sql to db username="+username);
		try {
			return DbController.getParksMangedByParkManger(conn,username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private int[][] getUsageReportByYearAndMonth(Connection conn, String parkNname, int month, int year) {
		System.out.println("EchoServersending sql to db month="+month+" year="+year);
		try {
			return DbController.getUsageByYearAndMonth(conn,parkNname,month,year);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private int[] getTotalVisitorsByYearAndMonth(Connection conn,String parkName,int month, int year) {
		System.out.println("EchoServersending sql to db month="+month+" year="+year);
		
		try {
			return DbController.getTotalVisitorsByYearAndMonth(conn,parkName,month,year);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
    

	private ArrayList<Order> loadOrderForWaitingListTable(Connection conn, String userid) {
		return DbController.loadOrderForWaitingListTable(conn,userid);
	}

	private ArrayList<Order> loadOrderForApproveTable(Connection conn, String username) {
		return DbController.loadOrderForApproveTable(conn,username);
	}

	private int saveOrder(Connection conn, String parkname, String username, String date, String time,
			String numberofvisitors,String orderId,String totalprice, String typeacc,String reservationtype,String dwelltime) {
			int saveorder=DbController.saveOrder(conn,parkname,username,date,time,numberofvisitors,orderId,totalprice, typeacc,reservationtype,dwelltime);
			return saveorder;
	}

	private int enterWaitingList(Connection conn, String parkname, String username, String date, String time,
			String numberofvisitors,String orderId,String totalprice) {
		int waitinglist=DbController.waitingList(conn,parkname,username,date,time,numberofvisitors,orderId,totalprice);
		return waitinglist;
	}

	private int checkAvailability(Connection conn, String parkname, String numberofvisitors,String date,String time) {
		int check=DbController.checkAvailable(conn,parkname,numberofvisitors,date,time);
		return check;
	}

	private int checkDiscount(Connection conn ,String discounttype) {
		int discount=DbController.discountCheck(conn,discounttype);
		return discount;
	}

	private int parkPrice(Connection conn ,String parkname) {
		int parkprice=DbController.checkPrice(conn,parkname);
		return parkprice;	
	}


	private int updateOrderDetails( Connection conn,String orderid,String parkname, String date, String time, String numberofvisitors, String discounttype
			,String typeacc,String reservationtype) {
		int update = DbController.updateOrder(conn, orderid,parkname,date,time,numberofvisitors,discounttype,typeacc,reservationtype);
		return update;
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
		  //System.out.println("echo server userExist method");
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
	
	private ArrayList<Park> Park(Connection conn) {
		return DbController.park(conn);
	}

	/*public static int updateOrderDetails(String[] orderdetails) {
		int update=0;
		Connection conn = DbController.createDbConnection();
		System.out.println("EchoServer> Sending the data to dbc ");
		update=DbController.updateOrder(conn,orderdetails);
		return update;
	}*/

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