/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import application.ConnexionDB;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Reservation;

/**
 *
 * @author Hp
 */
public class ReservationController implements Initializable {
    
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    
    @FXML
    private TableColumn<Reservation, Date> tabDE;

    @FXML
    private TableColumn<Reservation, Date> tabDS;

    @FXML
    private TableColumn<Reservation, Integer> tabId;

    @FXML
    private TableColumn<Reservation, Integer> tabChambreNum;

    @FXML
    private TableColumn<Reservation, String> tabClientCin;
    

    @FXML
    private TableView<Reservation> tableau;
    
    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnMenu;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private AnchorPane ras;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtChambreNum;

    @FXML
    private TextField txtClientCin;
    
    private Parent fxml;
    //  ====================================== Tableau 
    public ObservableList<Reservation> data = FXCollections.observableArrayList();
    public ReservationController() {
        this.tabChambreNum = new TableColumn<>();
        this.txtChambreNum = new TextField();
        this.tabClientCin=new TableColumn<>();
        this.tabId = new TableColumn<>();
        this.tabDE = new TableColumn<>();
        this.txtClientCin = new TextField();
        this.txtId = new TextField();
    }
    public ReservationController(TableColumn<Reservation, Integer> tabChambreNum, TableColumn<Reservation, String> tabClientCin, TextField txtChambreNum, TextField txtClientCin) {
        this.tabChambreNum = tabChambreNum;
        this.tabClientCin = tabClientCin;
        this.txtChambreNum = txtChambreNum;
        this.txtClientCin = txtClientCin;
    }

    public void showReservation() {
        tableau.getItems().clear();
        String sql = "SELECT * FROM reservation";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                data.add(new Reservation(rs.getInt("idr"),rs.getString("clientcin"),rs.getInt("chambrenum"),rs.getDate("date_entree"),rs.getDate("date_sortie")));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        tabId.setCellValueFactory(new PropertyValueFactory<Reservation,Integer>("idr"));
        tabClientCin.setCellValueFactory(new PropertyValueFactory<Reservation,String>("clientCin"));
        tabChambreNum.setCellValueFactory(new PropertyValueFactory<Reservation,Integer>("chambreNum"));
        tabDE.setCellValueFactory(new PropertyValueFactory<Reservation,Date>("date_entree"));
        tabDS.setCellValueFactory(new PropertyValueFactory<Reservation,Date>("date_sortie"));
        tableau.setItems(data);
        txtId.setText("");
        txtClientCin.setText("");
        txtChambreNum.setText("");
        txtClientCin.requestFocus();
    }
    
    @FXML
    void openMenu() throws IOException {
        ras.getScene().getWindow().hide();
        Stage menu = new Stage();
        fxml = FXMLLoader.load(getClass().getResource("/interfaces/Menu.fxml"));
        Scene scene = new Scene(fxml);
        menu.setScene(scene);
        menu.show();
    }
    

    @FXML
    void supprimer() {
        if(txtId.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Id Pas Insere Pour Supprimer !!!",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }else {
            PreparedStatement ps,ps2,test;
            ResultSet rs,rest;
            String query = "SELECT idr,chambrenum FROM reservation WHERE idr = ?";
            try  {
                ps = ConnexionDB.getConnection().prepareStatement(query);
                ps.setString(1,txtId.getText());
                rs = ps.executeQuery();
                if(rs.next()) {
                    // etta --->  1
                            PreparedStatement pss;
                            String updt = "UPDATE chambre SET availability = 0 WHERE numch = ?";
                            pss = ConnexionDB.getConnection().prepareStatement(updt);
                            pss.setInt(1, rs.getInt("chambrenum"));
                            if(pss.executeUpdate()==0) {
                                Alert alert = new Alert(Alert.AlertType.ERROR,"Erreur Pour change l'etat !!!",javafx.scene.control.ButtonType.OK);
                                alert.showAndWait();
                            }
                        //--------------------------
                    String sup = "DELETE FROM reservation WHERE idr = ? ";
                    try  {
                        ps2 = ConnexionDB.getConnection().prepareStatement(sup);
                        ps2.setString(1, txtId.getText());
                        ps2.execute();
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Le Compte est Supprimé avec Succés !!!",javafx.scene.control.ButtonType.CLOSE);
                        alert.showAndWait();
                        txtId.setText("");
                        txtClientCin.setText("");
                        txtChambreNum.setText("");
                        txtClientCin.requestFocus();
                    }catch(Exception ee) {
                        Alert alert = new Alert(Alert.AlertType.ERROR,"Erreur dans le changement de l'availabilité !!!",javafx.scene.control.ButtonType.OK);
                        alert.showAndWait();
                    }
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"n'existe pas !!!",javafx.scene.control.ButtonType.OK);
                    alert.showAndWait();
                }
                showReservation();
            }catch(Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"No connection !!!",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
            }
        }
    }
    
    
    
    private boolean checkEmptyFields() {
        return (txtClientCin.getText().equals("")||txtChambreNum.getText().equals(""));
    }
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnexionDB.getConnection();
        showReservation();
    }
    
}
