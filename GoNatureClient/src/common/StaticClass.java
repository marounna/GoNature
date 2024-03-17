package common;

import java.util.ArrayList;

import entities.Park;
import javafx.scene.Node;
import javafx.stage.Stage;
import logic.Order;

public class StaticClass {
    public static String username;
    public static boolean islogout =false;
    public static Order o1 = new Order(null, null, null, null, null, null,null);
    public static String typeacc;
	public static boolean isexist;
	public static boolean islogged;
    //public static ArrayList<String> parknames = new ArrayList<>();
    public static ArrayList<Park> parks = new ArrayList<>();
    public static int numberofvisitors;
    public static int flagG=0;//----------------
    public static int flagC=0;//----------------
    public static String orderdetails="";
    public static String reservationtype;
    public static double discount;
    public static int parkprice;
	public static int available=0;
    

}
