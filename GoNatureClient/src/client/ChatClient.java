// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import client.*;
import clientGUI.EmployeeMenuController;
import clientGUI.LoginController;
import clientGUI.NewReservationForGuideController;
import clientGUI.NewReservationForUserController;
import clientGUI.UserMenuController;
import common.ChatIF;
import common.StaticClass;
import entities.Park;
import logic.Message;
import logic.Order;
import ocsf.client.AbstractClient;

import java.io.*;
import java.util.ArrayList;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
@SuppressWarnings("unused")
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  //public static Order o1 = new Order(null, null, null, null, null, null);
  public static boolean awaitResponse = false;
  public int type=0;
  //public static String typeacc;
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
	 
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    //openConnection();
  }

  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */

  @SuppressWarnings("unchecked")





public void handleMessageFromServer(Object msg1) 
  {
	  String msg= " ";
	  Message payloadMessage = null;
	  if(msg1 instanceof Message) {
		  System.out.println("it workkkkkkkkkkkkkkkkkkkkk");
		  payloadMessage =(Message) msg1;
		  msg=payloadMessage.getCommand();
	  }
	  else {
		  msg = (String)msg1;
	  }
	  System.out.println("--> handleMessageFromServer");
      System.out.println("ChatClient> Message received: " + (String)msg);
      String message = (String) msg.toString();
      System.out.println("chatClient> " + message);
      String[] result = message.split(" ");
	  awaitResponse = false;
	  switch (result[0]) {
		  case "loadOrder":
			  StaticClass.o1.setOrderId(result[1]);
			  StaticClass.o1.setParkName(result[2]);
			  StaticClass.o1.setUserId(result[3]);
			  StaticClass.o1.setDate(result[4]);
			  StaticClass.o1.setTimeOfVisit(result[5]);
			  StaticClass.o1.setNumberOfVisitors(result[6]);
			  StaticClass.o1.setEmail(result[7]);
			  System.out.println("this is my test " + StaticClass.o1.toString());
			  break;
		  case "userExist":
			  if(result[1].equals("succeed")) {
				  StaticClass.isexist=true;
				  StaticClass.typeacc=result[3];
				  if(result[3].equals("park")||result[3].equals("department")||result[3].equals("service"))
					  StaticClass.typeacc += " " + result[4];				  		

				  if(result[2].equals("1"))
					  StaticClass.islogged=true;}
			  else StaticClass.isexist=false;
			  break;
		  case "login":
			  if(result[1].equals("succeed")) {
				  StaticClass.username=result[2];
				  StaticClass.isexist=true;
				  if (result[2].equals("guide"))
					  type=1;
			  }
			  else StaticClass.isexist=false;
			  break;
		  case "logout":
			  if(result[1].equals("succeed")) {
				  StaticClass.islogout=true;
			  	  if(type==1) type=0;}
			  else StaticClass.islogout=false;
			  break;
		  case "park":
			  ArrayList<Park> msgList = (ArrayList<Park>) payloadMessage.getPayload() ;
			  System.out.println("----------"+ msg +"-------------" + msgList);
			  StaticClass.parks.addAll(msgList);
			  break;
		  case "priceCheck":
			  StaticClass.parkprice=Integer.parseInt(result[1]);
			  break; 
		  case "checkDiscount":
			  StaticClass.discount=Integer.parseInt(result[1]);
			  break;
		  case "checkAvailability":
			  if (result[1].equals("1"))
				  StaticClass.available=1;
			  System.out.println("chatclient> avaiable = "+ StaticClass.available);
			  break;
          case "maxNumberOrder":
        	  StaticClass.maxorderid=result[1];
        	  break;
          case "waitingList":
        	  if(result[1].equals("1")) {
        		  System.out.println("Enter waiting list succeed");
				  StaticClass.available=0;}
    		  System.out.println("Enter waiting list failed");
    		  break;
          case "dwellTime":
        	  StaticClass.dwelltime=result[1];
        	  break;
          case "saveOrder":
        	  StaticClass.available=0;
        	  break;
          case "loadOrderForApproveTable":
			  ArrayList<Order> orderforapprovetable = (ArrayList<Order>) payloadMessage.getPayload() ;
			  StaticClass.ordersforapprovetable.addAll(orderforapprovetable);
			  break;
          case "loadOrderForWaitingTable":  
			  ArrayList<Order> ordersforwaitinglisttable = (ArrayList<Order>) payloadMessage.getPayload() ;
			  StaticClass.ordersforwaitingtable.addAll(ordersforwaitinglisttable);
			  break;
          case "userId":
        	  StaticClass.userid=result[1];
        	  break;
          case "deleteOrder":
        	  if(result[1].equals("succeed"))
        		  System.out.println("ChatClient> delete order succeed");
        	  else {
        		  System.out.println("ChatClient> delete order failed");
        	  }
    		  break;
          case "updateOrder":
        	  if(result[1].equals("waitinglist")) {
        		  StaticClass.updatetowaitinglist=1;
        	  }
        	  else if(result[1].equals("visaCredit")) {
        		  StaticClass.visa=1;
        	  	System.out.println("chatClient> visa= "+StaticClass.visa);}
        	  
        	  break;
      	  
        	  
	  }
	  
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  
  public void handleMessageFromClientUI(String message)  
  {
    try
    {
    	openConnection();//in order to send more than one message
       	awaitResponse = true;
    	sendToServer(message);
		// wait for response
		while (awaitResponse) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
    catch(IOException e)
    {
    	e.printStackTrace();
      clientUI.display("Could not send message to server: Terminating client."+ e);
      quit();
    }
  }

  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class