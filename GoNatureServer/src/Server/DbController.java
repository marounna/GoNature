package Server;


import java.util.ArrayList;

import logic.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




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
                    orderDetails.add(rs.getString("ParkName"));
                    orderDetails.add((rs.getString("OrderNumber")));
                    orderDetails.add(rs.getString("TimeOfVisit"));
                    orderDetails.add((rs.getString("NumberOfVisitors")));
                    orderDetails.add((rs.getString("TelephoneNumber")));
                    orderDetails.add(rs.getString("Email"));
                }
                pstmt.close();
            }
        } catch (SQLException e) {
            System.out.println("Error loading order: " + e.getMessage());
        }

        
        try {
            // Ensure orderDetails has all necessary elements
            if (orderDetails.size() == 6) {
                order = new Order(orderDetails.get(0), orderDetails.get(1), orderDetails.get(2), orderDetails.get(3), orderDetails.get(4), orderDetails.get(5));
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
        String sql = "SELECT * FROM orders WHERE OrderNumber = ?";
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
    public static int updateOrder(Connection conn, String[] orderdetails) {
        // Assuming the message format is "orderNumber,parkName,timeOfVisit,numberOfVisitors,telephoneNumber, email"
        String[] details = orderdetails;

        if (details.length != 7) {
            System.out.println("dbController> Invalid message format for updating order.");
            return 0;
        }
        String sql = "UPDATE orders SET ParkName = ?, TimeOfVisit = ?, NumberOfVisitors = ?, TelephoneNumber = ?, Email = ? WHERE OrderNumber = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, details[1]); // ParkName
            pstmt.setString(2, details[3]); // TimeOfVisit
            pstmt.setString(3, details[4]); // NumberOfVisitors
            pstmt.setString(4, details[5]); // TelephoneNumber
            pstmt.setString(5, details[6]); // Email
            pstmt.setString(6, details[2]); // OrderNumber
            
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
                    System.out.println("DbController> User exists.");
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
	
    
}
