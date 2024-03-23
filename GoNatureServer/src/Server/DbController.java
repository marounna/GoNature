package Server;


import java.util.ArrayList;

import entities.Park;
import javafx.css.PseudoClass;
import logic.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


//commit by Adar 15/3 time 9.20

public class DbController {
    public static Order order;
    
    
    //connect to MySQL
    @SuppressWarnings("unused")
	public static Connection createDbConnection() {
  	  try 
  		{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            System.out.println("EchoServer> Driver definition succeed");
        } catch (Exception ex) {
        	/* handle the error*/
        	System.out.println("EchoServer> Driver definition failed ");
        	 }
        try 
        {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/gonaturedb?serverTimezone=IST","root","Aa123456");
          
            System.out.println("SQL connection succeed");
            return conn;
            //createTableCourses(conn);
     	} catch (SQLException ex) 
     	    {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            }
        return null;
    }

    // Method to load an order from the database
    public static Order loadOrder(Connection conn, String order_number) {
        System.out.println("dbController> Order number = " + order_number);
        String sql = "SELECT * FROM orders WHERE OrderNumber = ?";

        ArrayList<String> orderDetails = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, order_number);
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("Select * was executed");
                if (rs.next()) {
                    orderDetails.add(rs.getString("OrderId"));
                    orderDetails.add(rs.getString("ParkName"));
                    orderDetails.add(rs.getString("UserId"));
                    orderDetails.add((rs.getString("DateOfVisit")));
                    orderDetails.add(rs.getString("TimeOfVisit"));
                    orderDetails.add((rs.getString("NumberOfVisitors")));
                }
                pstmt.close();
            }
        } catch (SQLException e) {
            System.out.println("Error loading order: " + e.getMessage());
        }

        
        try {
            // Ensure orderDetails has all necessary elements
            if (orderDetails.size() == 6) {
                order = new Order(orderDetails.get(0), orderDetails.get(1), orderDetails.get(2), orderDetails.get(3), orderDetails.get(4), orderDetails.get(5),orderDetails.get(6));
                //System.out.println("dbController> " + order);
                return order;
            } else {
                System.out.println("dbController> Invalid orderDetails retrieved from database.");
            }
        } catch (Exception e) {
            System.out.println("dbController> Error creating Order object: " + e.getMessage());
            e.printStackTrace();
        }
		return null;
    }
 

    public static int searchOrder(Connection conn, String order_number) {
        String sql = "SELECT * FROM orders WHERE OrderId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, order_number);
            try (ResultSet rs = pstmt.executeQuery()) {
                // Check if the order exists
                if (rs.next()) {
                    System.out.println("dbController> Order exists.");
                    pstmt.close();
                    return 1; // Order found
                } else {
                    System.out.println("dbController> Order does not exist.");
                    pstmt.close();
                    return 0; // Order not found     
                }
                
            }
        } catch (SQLException e) {
            System.out.println("dbController> Error searching for order: " + e.getMessage());
        }
        return 0; // Return 0 or appropriate error code/value in case of exception
    }
    
    


    // Method to update an order in the database
    public static int updateOrder(Connection conn, String[] msg) {
        if (msg.length != 7) {
            System.out.println("dbController> Invalid message format for updating order.");
            return 0;
        }
        String sql = "UPDATE orders SET ParkName = ?,UserId=? TimeOfVisit = ?, NumberOfVisitors = ?, IsConfirmed = ?, IsVisit = ?, IsCanceled = ?, TotalPrice = ?, IsInWaitingList = ? WHERE OrderId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, msg[2]); // ParkName
            pstmt.setString(2, msg[3]); // UserId
            pstmt.setString(3, msg[4]); // TimeOfVisit
            pstmt.setString(4, msg[5]); // NumberOfVisitors
            pstmt.setString(5, msg[6]); // IsConfirmed
            pstmt.setString(6, msg[7]); // IsVisit
            pstmt.setString(7, msg[8]); // IsCanceled
            pstmt.setString(8, msg[9]); // TotalPrice
            pstmt.setString(9, msg[10]); // IsInWaitingList
            pstmt.setString(10, msg[1]); // OrderId
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("dbController> Order updated successfully.");
                return 1;
            } else {
                System.out.println("dbController> Order not found or no change made.");
            }
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error updating order: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing integer from message: " + e.getMessage());
        }
        return 0;
    }

    public static int searchUser(Connection conn, String username, String password) {
        String sql = "SELECT * FROM users WHERE Username = ? AND Password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    //System.out.println("DbController> User exists.");
                    EchoServer.is_logged = rs.getString("IsLogged");
                    EchoServer.type = rs.getString("TypeUser");
                    // Debugging output
                    System.out.println("Logged: " + EchoServer.is_logged + ", Type: " + EchoServer.type);
                    return 1;
                } else {
                    System.out.println("DbController> User does not exist.");
                    return 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("DbController> Error searching for user: " + e.getMessage());
        }
        return 0; // Return 0 or appropriate error code/value in case of exception
    }


	public static int userLogin(Connection conn, String username) {
		String sql = "UPDATE users SET IsLogged = ? WHERE Username = ?";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, "1");
	        pstmt.setString(2, username);
	        
	        int rowsAffected = pstmt.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("dbController> login succeed, rows affected: " + rowsAffected);
	            return 1;
	        } else {
	            System.out.println("dbController> login failed. No rows affected.");
	            return 0;
	        }
	    } catch (SQLException e) {
	        System.out.println("dbController> Error updating user login status: " + e.getMessage());
	    }
	    return 0;
	}

	public static int userLogout(Connection conn, String username) {
		String sql = "UPDATE users SET IsLogged = ? WHERE Username = ?";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, "0");
	        pstmt.setString(2, username);
	        int rowsAffected = pstmt.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("dbController> logout succeed, rows affected: " + rowsAffected);
	            return 1;
	        } else {
	            System.out.println("dbController> logout failed. No rows affected.");
	            return 0;
	        }
	    } catch (SQLException e) {
	        System.out.println("dbController> Error updating user logout status: " + e.getMessage());
	    }
	    return 0;
	}

	/*public static String getParkNames(Connection conn, String names) {
		String sql="SELECT Parkname FROM park";
		String parks ="";
		int i=0;
	    try (PreparedStatement pstmt = conn.prepareStatement(sql);
	           ResultSet rs = pstmt.executeQuery()) {
	           while (rs.next()) {
	        	   	i++;	
        		    parks += rs.getString("Parkname")+" ";
        		    //System.out.println(parks);
	           }
	    }catch (SQLException e) {
	        System.out.println("DbController> Error fetching park names: " + e.getMessage());}    
        return i+" "+ parks; 
    }*/


	public static ArrayList<Park> park(Connection conn) {
         	ArrayList<Park> parks = new ArrayList<>();
	        String query = "SELECT * FROM park;";
	        try (Statement stmt = conn.createStatement(); 
	             ResultSet rs = stmt.executeQuery(query)) { // try-with-resources for auto closing
	            
	            while (rs.next()) {
	                String parkName = rs.getString("Parkname");
	                String capacityOfVisitors = rs.getString("CapacityOfVisitors");
	                String pricePerPerson = rs.getString("PricePerPerson");
	                String availableSpot = rs.getString("AvailableSpot");
	                String visitTimeLimit = rs.getString("visitTimeLimit");
	                String parkManagerId = rs.getString("ParkMangerId");
	                
	                // Assuming the Park constructor matches these fields
	                Park park = new Park(parkName, capacityOfVisitors, pricePerPerson, availableSpot, visitTimeLimit, parkManagerId);
	                parks.add(park);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

		return parks;
	}

	public static int checkPrice(Connection conn,String parkname) {
        String sql = "SELECT PricePerPerson FROM park WHERE ParkName = ?"; 
        int price=0;
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setString(1, parkname);
		        try(ResultSet rs = pstmt.executeQuery()) {
		           if (rs.next()) {	
		        	  price=Integer.parseInt(rs.getString("PricePerPerson"));
		           }
		    }catch (SQLException e) {
		        System.out.println("DbController> Error fetching price per person: " + e.getMessage());}    
	    } catch (SQLException e2) {
			e2.printStackTrace();}   
	    System.out.println("dbController> price per prson is: " + price);
        return price;
    }

	public static int discountCheck(Connection conn, String discounttype) {
        String sql = "SELECT SalePercentage FROM sales WHERE SaleType = ?";
        int discount=0;
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setString(1, discounttype);
		        try(ResultSet rs = pstmt.executeQuery()) {
		           if (rs.next()) {	
		        	  discount=Integer.parseInt(rs.getString("SalePercentage"));
		           }
		    }catch (SQLException e) {
		        System.out.println("DbController> Error fetching sales: " + e.getMessage());}    
	    } catch (SQLException e2) {
			e2.printStackTrace();}
	    System.out.println("dbcontroller> discount: " + discount);
	    return discount;
    }

	public static int checkAvailable(Connection conn, String parkname, String numberofvisitors, String date, String time) {
		System.out.println("------------------------------------------------------------------------------------------------------");
		int dwell=Integer.parseInt(checkDwell(conn, parkname));
		System.out.println("dwell is: "+ dwell);
		String[] st= new String[dwell];
		int flag=0;
		for (int i=8;i<20-dwell;i++) {
			if (time.contains(""+i)) {//inserting into st array the amount of dwell hours of 
				for(int j=0;j<dwell;j++)
					st[j]="t"+(j+i);
			}
		}
		String sql="SELECT * FROM park_used_capacity_total WHERE Parkname = ? AND date = ?";
        int[] numberofsignedvisitors= new int[dwell];
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setString(1, parkname);
				pstmt.setString(2, date);
		        try(ResultSet rs = pstmt.executeQuery()) {
		           if (rs.next()) {	
		        	   for(int k=0;k<dwell;k++) {
			        	   numberofsignedvisitors[k]=rs.getInt(st[k]);
			        	   System.out.println("DbController> number of signed people on capacity tables: "+numberofsignedvisitors[k]);
		        	   }
		           }
		    }catch (SQLException e) {
		        System.out.println("DbController> Error fetching capacity total in checkAvailable: " + e.getMessage());}    
	    } catch (SQLException e2) {
			e2.printStackTrace();}
	    
	    //System.out.println("dbcontroller> number of signed visitors: " + numberofsigned);
	    int capacity=0;
	    String sql2="SELECT CapacityOfVisitors FROM park WHERE Parkname = ?";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql2)){
				pstmt.setString(1, parkname);
		        try(ResultSet rs = pstmt.executeQuery()) {
		           if (rs.next()) {	
		        	  capacity=Integer.parseInt(rs.getString("CapacityOfVisitors"));
		           }
		    }catch (SQLException e) {
		        System.out.println("DbController> Error fetching capacity total: " + e.getMessage());}    
	    } catch (SQLException e2) {
			e2.printStackTrace();}
	    for(int t=0;t<dwell;t++) {
	    	if(numberofsignedvisitors[t]+Integer.parseInt(numberofvisitors)>capacity)
	    		return 0;
	    }
	    return 1;
	}
	
	
//creating an order and mark the waiting list field as "YES" (which means it on waiting list)
	public static int waitingList(Connection conn, String parkname, String username, String date, String time,
			String numberofvisitors,String orderid,String totalprice) {
		int ordernumber=Integer.parseInt(orderid);
		ordernumber++;
		System.out.println("new order number: "+ ordernumber);
		String userid="";
		String sqlusers="SELECT * FROM users WHERE Username = ?";
	    try (PreparedStatement pstmt = conn.prepareStatement(sqlusers)){
				pstmt.setString(1, username);
		        try(ResultSet rs = pstmt.executeQuery()) {
			           if (rs.next()) {	
			        	   userid=rs.getString("UserId");
			           }
			    }catch (SQLException e) {
			        System.out.println("DbController> Error fetching orders: " + e.getMessage());}    
		    } catch (SQLException e2) {
				e2.printStackTrace();}
	    String sqlorders = "INSERT INTO orders (OrderId, ParkName, UserId, DateOfVisit, "
	    		+ "TimeOfVisit, NumberOfVisitors, IsConfirmed, IsVisit, IsCanceled, TotalPrice, IsInWaitingList) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    try (PreparedStatement pstmt = conn.prepareStatement(sqlorders)){
				pstmt.setInt(1, ordernumber);
				pstmt.setString(2, parkname);
				pstmt.setString(3, userid);
				pstmt.setString(4, date);
				pstmt.setString(5, time);
				pstmt.setString(6, numberofvisitors);
				pstmt.setString(7, "NO");
				pstmt.setString(8, "NO");
				pstmt.setString(9, "NO");
				pstmt.setString(10, totalprice);
				pstmt.setString(11, "YES");
			    pstmt.executeUpdate();
				System.out.println("insert into orders succeed~~~~");
				return 1;
	    } catch (SQLException e) {
			e.printStackTrace();
		}
	    return 0;
	}

	public static int checkMax(Connection conn) {//checking the max order number 
		int max=0;
		String sql="SELECT MAX(OrderId) FROM orders";
        try (Statement stmt = conn.createStatement(); 
        		  ResultSet rs = stmt.executeQuery(sql)){ 
        			if (rs.next()) {
        				max=rs.getInt("MAX(OrderId)");
        			}  
        } catch (SQLException e) {

			e.printStackTrace();
		}
        System.out.println("max order id is: " +max);
		return max;
	}
//saving in DB the order details
	public static int saveOrder(Connection conn, String parkname, String username, String date, String time,
			String numberofvisitors, String orderId, String totalprice, String typeacc,String reservationtype,String dwelltime) {
		int ordernumber=Integer.parseInt(orderId);
		ordernumber++;
		String userid="";
		String sqlusers="SELECT * FROM users WHERE Username = ?";
	    try (PreparedStatement pstmt = conn.prepareStatement(sqlusers)){
				pstmt.setString(1, username);
		        try(ResultSet rs = pstmt.executeQuery()) {
			           if (rs.next()) {	
			        	   userid=rs.getString("UserId");
			           }
			    }catch (SQLException e) {
			        System.out.println("DbController> Error fetching orders: " + e.getMessage());}    
		    } catch (SQLException e2) {
				e2.printStackTrace();}
	    String sql = "INSERT INTO orders (OrderId, ParkName, UserId, DateOfVisit, "
	    		+ "TimeOfVisit, NumberOfVisitors, IsConfirmed, IsVisit, IsCanceled, TotalPrice, IsInWaitingList) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)){//insert into orders table the order details
				pstmt.setInt(1, ordernumber);
				pstmt.setString(2, parkname);
				pstmt.setString(3, userid);
				pstmt.setString(4, date);
				pstmt.setString(5, time);
				pstmt.setString(6, numberofvisitors);
				pstmt.setString(7, "YES");
				pstmt.setString(8, "NO");
				pstmt.setString(9, "NO");
				pstmt.setString(10, totalprice);
				pstmt.setString(11, "NO");
			    pstmt.executeUpdate();
			    updateTotalTables(conn,parkname,date,time,numberofvisitors, typeacc,reservationtype,dwelltime,"+");
			    return 1;
	    } catch (SQLException e) {
			e.printStackTrace();
		}
	   return 0; 
	}

	private static void updateTotalTables(Connection conn, String parkname, String date, String time,
			String numberofvisitors, String typeaccount,String resevationtype,String dwelltime, String sign) {
		String t="t";
		int tfield=0;
		int ordertime = 0;
		int numbervisitors=Integer.parseInt(numberofvisitors);
		String sql = "";
		int dwell=Integer.parseInt(dwelltime);
		int rowexistindb=0;
		for (int i=8; i<20;i++) {
			if(time.contains(""+i)) {
				tfield=i;
				ordertime=i;
			}
		}
		switch(typeaccount) {// checking if there is any orders at all on this date and park
		case "customer":
		case "guest":
			sql="SELECT * FROM park_used_capacity_individual WHERE Parkname = ? AND date= ? " ;
			break;
		case "guide":
			sql="SELECT * FROM park_used_capacity_groups WHERE Parkname = ? AND date= ? " ;
			break;
		case "park employee":
			if (resevationtype.equals("customer"))
				sql="SELECT * FROM park_used_capacity_individual  WHERE Parkname = ? AND date= ? " ;
			else sql="SELECT * FROM park_used_capacity_groups  WHERE Parkname = ? AND date= ? " ;
			break;	
		}
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setString(1, parkname);
				pstmt.setString(2, date);
		        try(ResultSet rs = pstmt.executeQuery()) {
			           if (rs.next()) {	
			        	   rowexistindb=1;//means there is a row on db with the park name and date
			           }
			    }catch (SQLException e) {
			        System.out.println("DbController> Error fetching capacity total: " + e.getMessage());}    
		    } catch (SQLException e2) {
				e2.printStackTrace();}
	    if(rowexistindb==1) {//means there is a row on db with the park name and date
			switch(typeaccount) {//updating table values with the new order number of visitors 
				case "customer":
				case "guest":
					sql="UPDATE park_used_capacity_individual SET " + t +tfield+ "= "+t+tfield  + sign +" ?" ;
					for(int i=0;i<dwell-1;i++) {
						t="t";
						tfield++;
						sql+=", " + t +tfield+ " = "+t+tfield+sign+"  ?" ;
					}
					sql+="  WHERE Parkname = ? AND date= ? " ;
					System.out.println("dbcontroller sql query: " + sql);
					break;
				case "guide":
					sql="UPDATE park_used_capacity_groups SET " + t +tfield+ "= "+t+tfield  + sign +" ?" ;
					for(int i=0;i<dwell-1;i++) {
						t="t";
						tfield++;
						sql+=", " + t +tfield+ " = "+t+tfield+sign+"  ?" ;
					}
					sql+="  WHERE Parkname = ? AND date= ? " ;

					break;
				case "park employee":
					if (resevationtype.equals("customer")) {
						sql="UPDATE park_used_capacity_individual SET " + t +tfield+ "= "+t+tfield  + sign +" ?" ;
						for(int i=0;i<dwell-1;i++) {
							t="t";
							tfield++;
							sql+=", " + t +tfield+ " = "+t+tfield+sign+"  ?" ;
						}
						sql+="  WHERE Parkname = ? AND date= ? " ;}
					else { sql="UPDATE park_used_capacity_groups SET " + t +tfield+ "= "+t+tfield  + sign +" ?" ;
					for(int i=0;i<dwell-1;i++) {
						t="t";
						tfield++;
						sql+=", " + t +tfield+ " = "+t+tfield+sign+"  ?" ;
					}
					sql+="  WHERE Parkname = ? AND date= ? " ;}
					break;		
			}
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			    pstmt.setInt(1, numbervisitors);
			    pstmt.setInt(2, numbervisitors);
			    pstmt.setInt(3, numbervisitors);
			    pstmt.setInt(4, numbervisitors);
			    pstmt.setString(5, parkname);
			    pstmt.setString(6, date);

			    // Execute the update using executeUpdate()
			    int affectedRows = pstmt.executeUpdate();
			    if (affectedRows > 0) {
			        System.out.println("Update capacity succeeded");
			    } else {
			        System.out.println("No rows were updated");
			    }
			} catch (SQLException e) {
			    System.out.println("DbController> Error fetching capacity tables: " + e.getMessage());
			}
			//updating the total capacity table
			tfield=ordertime;
			sql="UPDATE park_used_capacity_total SET " + t +tfield+ "= "+t+tfield  + sign +" ?" ;
			for(int i=0;i<dwell-1;i++) {
				t="t";
				tfield++;
				sql+=", " + t +tfield+ " = "+t+tfield+sign+"  ?" ;
			}
			sql+="  WHERE Parkname = ? AND date= ? " ;
			System.out.println("dbcontroller total sql query: "+ sql);
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			    pstmt.setInt(1, numbervisitors);
			    pstmt.setInt(2, numbervisitors);
			    pstmt.setInt(3, numbervisitors);
			    pstmt.setInt(4, numbervisitors);
			    pstmt.setString(5, parkname);
			    pstmt.setString(6, date);

			    // Execute the update using executeUpdate()
			    int affectedRows = pstmt.executeUpdate();
			    if (affectedRows > 0) {
			        System.out.println("Update total capacity table succeeded");
			    } else {
			        System.out.println("No rows were updated");
			    }
			} catch (SQLException e) {
			    System.out.println("DbController> Error fetching capacity total table: " + e.getMessage());
			}
			
	    }
	    	
		
	}

	
	
	public static String checkDwell(Connection conn, String parkname) {// checking how many hours can visitor visit in park
		String dwell ="";
		String sql ="SELECT visitTimeLimit FROM park WHERE Parkname= ?";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setString(1, parkname);
		        try(ResultSet rs = pstmt.executeQuery()) {
			           if (rs.next()) {	
			        	   dwell=rs.getString("visitTimeLimit");
			           }
			    }catch (SQLException e) {
			        System.out.println("DbController> Error fetching capacity total: " + e.getMessage());}    
		    } catch (SQLException e2) {
				e2.printStackTrace();}
		return dwell;
	}

	public static ArrayList<Order> loadOrderForApproveTable(Connection conn, String userid) {
        ArrayList<Order> approveOrderList = new ArrayList<>();
        String sql = "SELECT orderId, parkName, dateOfVisit, timeOfVisit, numberOfVisitors FROM orders WHERE userId = ? AND IsInWaitingList = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, userid);
            pstmt.setString(2, "NO"); 
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order(
                        rs.getString("orderId"),
                        rs.getString("parkName"),
                        rs.getString("dateOfVisit"),
                        rs.getString("timeOfVisit"),
                        rs.getString("numberOfVisitors")
                    );
                    approveOrderList.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return approveOrderList;
    }

	public static ArrayList<Order> loadOrderForWaitingListTable(Connection conn, String userid) {
        ArrayList<Order> waitingListOrdersList = new ArrayList<>();
        String sql = "SELECT orderId, parkName, dateOfVisit, timeOfVisit, numberOfVisitors FROM orders WHERE userId = ? AND IsInWaitingList = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, userid);
            pstmt.setString(2, "YES"); 
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order(
                        rs.getString("orderId"),
                        rs.getString("parkName"),
                        rs.getString("dateOfVisit"),
                        rs.getString("timeOfVisit"),
                        rs.getString("numberOfVisitors")
                    );
                    waitingListOrdersList.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return waitingListOrdersList;
	}

	public static String checkUserId(Connection conn, String username) {
		System.out.println("the user name is: " + username);
		String sql = "SELECT UserId FROM Users WHERE username = ?";
		 String userid="";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
		    pstmt.setString(1, username); 
		    ResultSet rs = pstmt.executeQuery();
		    if (rs.next()) {
		        userid = rs.getString("UserId");
		        System.out.println("user id is: " + userid);
		        System.out.println("The UserId is: " + userid);
		    } else {
		        System.out.println("No user found with the provided username.");
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
	}
		return userid;
	}

	public static String getamountinpark(Connection conn, String userid) {
	    System.out.println("========"+userid);
	    String sql = "SELECT park_name FROM park_workers WHERE idpark_workers = ?";
	    String parkName = "";
	    LocalDateTime now = LocalDateTime.now();
	    String currentDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    String currentHourColumn = "t" + now.getHour();
	    int currentCapacity = 0;
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, userid);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            parkName = rs.getString("park_name");
	            System.out.println("Park Name is: " + parkName);
	        } else {
	            System.out.println("No park found with the provided idpark_workers.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    String sql1 = "SELECT " + currentHourColumn + " FROM park_used_capacity_total WHERE Parkname = ? AND date = ?";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql1)) {
	        pstmt.setString(1, parkName);
	        pstmt.setString(2, currentDate);
	        
	        ResultSet rs1 = pstmt.executeQuery();

	        if (rs1.next()) {
	            currentCapacity = rs1.getInt(currentHourColumn);
	            System.out.println("Current capacity for " + parkName + " at " + currentHourColumn + " is: " + currentCapacity);
	        } else {
	            System.out.println("No capacity data found for " + parkName + " at " + currentHourColumn + " on " + currentDate);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    System.out.println(String.valueOf(currentCapacity)+"-----currentHourColumn-----"+currentHourColumn+"----currentDate------"+currentDate);
	    return String.valueOf(currentCapacity);
	    	    
	    

	}

	public static int checkamountofpeople(Connection conn, String orderId, String amountofpeople) {
	    String sql = "SELECT NumberOfVisitors FROM orders WHERE OrderId = ?";
	    String numberOfVisitors="";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, orderId); // Set the OrderId parameter
	        
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            numberOfVisitors = rs.getString("NumberOfVisitors");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    if(Integer.parseInt(numberOfVisitors)>=Integer.parseInt(amountofpeople)) {
	    	return 1;
	    }
	    return 0;
	}
//updateTotalTables(Connection conn, String parkname, String date, String time,
	//String numberofvisitors, String typeaccount,String resevationtype,String dwelltime, String sign)
	public static void updateyardentable(Connection conn, String OrderId, String numberofvisiter, String typeacc) {
	    String sql2 = "UPDATE orders SET isVisit = 'YES' WHERE OrderId = ?";

	    try (PreparedStatement pstmt = conn.prepareStatement(sql2)) {
	        // Set the value for the userID parameter in the query
	        pstmt.setString(1, OrderId);
	        
	        // Execute the update
	        int affectedRows = pstmt.executeUpdate();

	        // Check if the update was successful
	        if (affectedRows > 0) {
	            System.out.println("The isVisit field was successfully updated to 'YES' for OrderId: " + OrderId);
	        } else {
	            System.out.println("No record found with OrderId: " + OrderId);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error updating the isVisit field: " + e.getMessage());
	        e.printStackTrace();
	    }
		   String sql1 = "SELECT TypeUser FROM gonaturedb.orders AS orders JOIN gonaturedb.users AS users ON orders.userid = users.userid WHERE orders.OrderId = ?";
		   String typeUser = null;		   
		    try (PreparedStatement pstmt = conn.prepareStatement(sql1)) {
		        pstmt.setString(1, OrderId);  // Setting the OrderId parameter

		        try (ResultSet rs = pstmt.executeQuery()) {
		            if (rs.next()) {
		                typeUser = rs.getString("TypeUser");  // Retrieve the TypeUser
		            } else {
		                System.out.println("No matching user found for the provided OrderId.");
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    System.out.println(typeUser+"=============================================sss");
		String sql = "SELECT ParkName, dateOfVisit, timeOfVisit, NumberOfVisitors FROM orders WHERE OrderId = ?";
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, OrderId);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                // Extract data from result set and store in OrderInfo object
	                String parkName = rs.getString("ParkName");
	                String dateOfVisit = rs.getString("dateOfVisit");
	                String timeOfVisit = rs.getString("timeOfVisit");
	                String numberOfVisitors = rs.getString("NumberOfVisitors");
	                String dwllString= checkDwell(conn, parkName);
	                int amount= Integer.valueOf(numberOfVisitors)-Integer.valueOf(numberofvisiter);	        	  
	                updateTotalTables(conn, parkName, dateOfVisit, timeOfVisit,Integer.toString(amount), "park employee" ,typeUser ,dwllString, "-");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	}






	
}


	
	
    

