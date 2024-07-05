  /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import application.ConnexionDB;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class LoginController implements Initializable {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    
    
    @FXML
    private Button btnConnecter;

    @FXML
    private Button btnExit;

    @FXML
    private Label lblErreurInfo;

    @FXML
    private Label lblOublie;

    @FXML
    private TextField txtNom;

    @FXML
    private PasswordField txtPasse;
    
    @FXML
    private AnchorPane ras;
    
    private Parent fxml;
    
    @FXML
    void exit() {
        System.exit(0);
    }

    @FXML
    void openMenu() throws IOException {
        String nom = txtNom.getText();
        String passe = txtPasse.getText();
        String sql = "SELECT username,password FROM admin WHERE username = ? AND password = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, nom);
            ps.setString(2, passe);
            rs = ps.executeQuery();
            if(rs.next()) {
                ras.getScene().getWindow().hide();
                Stage menu = new Stage();
                fxml = FXMLLoader.load(getClass().getResource("/interfaces/Menu.fxml"));
                Scene scene = new Scene(fxml);
                menu.setScene(scene);
                menu.show();
            }
            else {
                Alert alert = new Alert(AlertType.ERROR,"Les Informations Ne Sont Pas Correcte !!!",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
                txtNom.setText("");
                txtPasse.setText("");
                txtNom.requestFocus();
            }
            
        } catch (SQLException ex) {
            System.err.println("Erreur : "+ex);
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
            conn = ConnexionDB.getConnection();
    }
    
}
