/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student;
 import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class connectionn {
public static Connection getConnection() {
        Connection connexion = null;
        try {
            connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/basededonnes", "root", "0000");
        } 
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "CONNECTION FAILED");
        }
        return connexion;
    }

    public static void main(String[] args) {
        Connection connection = connectionn.getConnection();
        if (connection != null) {
            System.out.println("Connected to database.");
            // Vous pouvez ajouter ici des opérations à effectuer avec la connexion
        } else {
            System.out.println("Failed to connect to database.");
        }
    }
    
            }
