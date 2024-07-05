/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 *
 * @author Hp
 */
public class MenuController implements Initializable {
    
    @FXML
    private Button btnChambre;

    @FXML
    private Button btnClient;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnReservation;

    @FXML
    private Button btnEmployee;
    
    @FXML
    private AnchorPane ras;
    
    private Parent fxml;

    @FXML
    void openChambre() throws IOException {
        ras.getScene().getWindow().hide();
        Stage menu = new Stage();
        fxml = FXMLLoader.load(getClass().getResource("/interfaces/GestionChambres.fxml"));
        Scene scene = new Scene(fxml);
        menu.setScene(scene);
        menu.show();
    }

    @FXML
    void openEmployee() throws IOException {
        ras.getScene().getWindow().hide();
        Stage menu = new Stage();
        fxml = FXMLLoader.load(getClass().getResource("/interfaces/GestionEmployee.fxml"));
        Scene scene = new Scene(fxml);
        menu.setScene(scene);
        menu.show();
    }

    
    @FXML
    void exit() throws IOException {
        ras.getScene().getWindow().hide();
        Stage menu = new Stage();
        fxml = FXMLLoader.load(getClass().getResource("/interfaces/Login.fxml"));
        Scene scene = new Scene(fxml);
        menu.setScene(scene);
        menu.show();
    }
    
    
    

    @FXML
    void openReservation() throws IOException {
        ras.getScene().getWindow().hide();
        Stage menu = new Stage();
        fxml = FXMLLoader.load(getClass().getResource("/interfaces/GestionReservation.fxml"));
        Scene scene = new Scene(fxml);
        menu.setScene(scene);
        menu.show();
    }

    @FXML
    void openClient() throws IOException {
        ras.getScene().getWindow().hide();
        Stage menu = new Stage();
        fxml = FXMLLoader.load(getClass().getResource("/interfaces/GestionClients.fxml"));
        Scene scene = new Scene(fxml);
        menu.setScene(scene);
        menu.show();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
