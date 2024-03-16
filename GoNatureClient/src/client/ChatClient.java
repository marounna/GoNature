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
  public static Order o1 = new Order(null, null, null, null, null, null);
  public static boolean awaitResponse = false;
  public int type=0;
  public static String typeacc;
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
			  o1.setParkName(result[0]);
			  o1.setOrderNumber(result[1]);
			  o1.setTimeOfVisit(result[2]);
			  o1.setNumberOfVisitors(result[3]);
			  o1.setTelephoneNumber(result[4]);
			  o1.setEmail(result[5]);
			  break;
		  case "userExist":
			  if(result[1].equals("succeed")) {
				  LoginController.isexist=true;
				  typeacc=result[3];
				  if(result[3].equals("park")||result[3].equals("department")||result[3].equals("service"))
				  	  typeacc += " " + result[4];				  		

				  if(result[2].equals("1"))
					  LoginController.islogged=true;}
			  else LoginController.isexist=false;
			  break;
		  case "login":
			  System.out.println("login case chat client");
			  if(result[1].equals("succeed")) {
				  LoginController.isexist=true;
				  if (result[2].equals("guide"))
					  type=1;
				  switch (typeacc) {
					  case "guide":
					  case "customer":
						  UserMenuController.username=result[2];
						  break;
					  case "park employee":
						  EmployeeMenuController.username=result[2];
						  break;
					  /*case "department manager":
						  DmMenuController.username=result[2];
						  break;
					  case "service employee":
						  ServiceEmployeeMenuController.username=result[2];
						  break;*/
				  }
			  }
			  else LoginController.isexist=false;
			  break;
		  case "logout":
			  if(result[1].equals("succeed")) {
				  switch (typeacc) {
				  case "guide":
				  case "customer":
					  UserMenuController.islogout=true;
					  break;
				  case "park employee":
					  EmployeeMenuController.islogout=true;
					  break;
				  /*case "department manager":
					  DmMenuController.username.islogout=true;
					  break;
				  case "service employee":
					  ServiceEmployeeMenuController.islogout=true;
					  break;*/
				  }
			  	  if(type==1) type=0;}
			  else UserMenuController.islogout=false;
			  break;
		  case "parkNames":
			  if (type==1) {
				  for(int i=0; i<Integer.parseInt(result[1]);i++) {
					  System.out.println("chatClient> guide park names");
					  NewReservationForGuideController.parknames.add(result[i+2]);}
			  }
			  else {	  
				  System.out.println("chatClient> user park names");
				  for(int i=0; i<Integer.parseInt(result[1]);i++)
					  NewReservationForUserController.parknames.add(result[i+2]);}
			  break;
		  case "park":
			  ArrayList<Park> msgList = (ArrayList<Park>) payloadMessage.getPayload() ;
			  System.out.println("----------"+ msg +"-------------" + msgList);
			  LoginController.parks.addAll(msgList);
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