/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application;

import java.sql.*;

public class ConnexionDB {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management","root","Contemporain2003$");
            System.out.println("Connexion reussite .");
        } catch (SQLException ex) {
            System.err.println("Connexion echouee !!");
            ex.printStackTrace();
        }
        return conn;
    }
}
