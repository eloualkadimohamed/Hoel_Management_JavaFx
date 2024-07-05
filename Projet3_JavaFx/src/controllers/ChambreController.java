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
import java.util.ResourceBundle;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Chambre;
import model.Reservation;

/**
 *
 * @author Hp
 */
public class ChambreController implements Initializable {
    
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    
    @FXML
    private TableColumn<Chambre, Boolean> tabAvailability;

    @FXML
    private TableColumn<Chambre, Integer> tabNumch;
    @FXML
    private TableColumn<Chambre, String> tabType;
    @FXML
    private TableColumn<Chambre, String> tabPrix;

    @FXML
    private TableView<Chambre> tableau;
    
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
    private TextField txtAvailability;

    @FXML
    private TextField txtNumch;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtPrix;
    
    private Parent fxml;
    public ChambreController() {
        this.tabNumch = new TableColumn<>();
        this.txtNumch = new TextField();
        this.tabAvailability=new TableColumn<>();
        this.tabPrix = new TableColumn<>();
        this.txtPrix = new TextField();
        this.txtAvailability = new TextField();
    }
    public ChambreController(TableColumn<Chambre, Integer> tabNumch, TableColumn<Chambre, String> tabType, TextField txtAvailability, TextField txtNumch, TextField txtType) {
        this.tabNumch = tabNumch;
        this.tabType = tabType;
        this.txtAvailability = txtAvailability;
        this.txtNumch = txtNumch;
        this.txtType = txtType;
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
    void ajouter() {
        if(checkEmptyFields()) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Les Champs sont vide !!!",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }else {
            String numch = txtNumch.getText();
            String availability = txtAvailability.getText();
            String prix = txtPrix.getText();
            String type= txtType.getText();
            PreparedStatement ps;
            String query = "INSERT INTO chambre(numch,prix,type,availability) VALUES(?,?,?,?)";
            try {
                ps = ConnexionDB.getConnection().prepareStatement(query);
                ps.setString(1,numch);
                ps.setString(2,prix);
                ps.setString(3,type);
                ps.setString(4,availability);
                if(!checkUser()) {
                    if(ps.executeUpdate()!=0) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Votre chambre est Créer avec Succés !!!",javafx.scene.control.ButtonType.CLOSE);
                        alert.showAndWait();
                        txtNumch.setText("");
                        txtAvailability.setText("");
                        txtPrix.setText("");
                        txtAvailability.requestFocus();
                    }else {
                        Alert alert = new Alert(Alert.AlertType.ERROR,"Votre Compte n'est pas Créer !!!",javafx.scene.control.ButtonType.OK);
                        alert.showAndWait();
                    }
                }
                showChambre();
            }catch(Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Problem dans la base de donnee !!!",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
            }

        }
    }

    @FXML
    void modifier() {
        if(txtNumch.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"numch Pas Inserer Pour Modifier !!!",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }else {
            PreparedStatement ps,ps2;
            ResultSet rs;
            String query = "SELECT numch FROM chambre WHERE numch = ?";
            try  {
                ps = ConnexionDB.getConnection().prepareStatement(query);
                ps.setString(1,txtNumch.getText());
                rs = ps.executeQuery();
                if(rs.next()) {
                    String mod = "UPDATE chambre SET availability = ? , prix = ?, type = ? WHERE numch = ?";
                    try  {
                        ps2 = ConnexionDB.getConnection().prepareStatement(mod);
                        ps2.setString(1, txtAvailability.getText());
                        ps2.setString(2, txtPrix.getText());
                        ps2.setString(3, txtType.getText());
                        ps2.setString(4, txtNumch.getText());
                        ps2.execute();
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Le Compte est Modifer avec Succés !!!",javafx.scene.control.ButtonType.CLOSE);
                        alert.showAndWait();
                        txtNumch.setText("");
                        txtAvailability.setText("");
                        txtPrix.setText("");
                        txtType.setText("");
                        txtAvailability.requestFocus();
                    }catch(Exception ee) {
                        Alert alert = new Alert(Alert.AlertType.ERROR,ee + "No connection pour deuxieme !!!",javafx.scene.control.ButtonType.OK);
                        alert.showAndWait();
                    }
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"N'existe pas !!!",javafx.scene.control.ButtonType.OK);
                    alert.showAndWait();
                }
                showChambre();
            }catch(Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"No connection !!!",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    @FXML
    void supprimer() {
        if(txtNumch.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"numch Pas Inserer Pour Supprimer !!!",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }else {
            PreparedStatement ps,ps2;
            ResultSet rs;
            String query = "SELECT numch FROM chambre WHERE numch = ?";
            try  {
                ps = ConnexionDB.getConnection().prepareStatement(query);
                ps.setString(1,txtNumch.getText());
                rs = ps.executeQuery();
                if(rs.next()) {
                    String sup = "DELETE FROM chambre WHERE numch = ? ";
                    try  {
                        ps2 = ConnexionDB.getConnection().prepareStatement(sup);
                        ps2.setString(1, txtNumch.getText());
                        ps2.execute();
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La chambre est Supprimer avec Succés !!!",javafx.scene.control.ButtonType.CLOSE);
                        alert.showAndWait();
                        txtNumch.setText("");
                        txtAvailability.setText("");
                        txtPrix.setText("");
                        txtType.setText("");
                        txtAvailability.requestFocus();
                    }catch(Exception ee) {
                        Alert alert = new Alert(Alert.AlertType.ERROR,"Non connexion !!!",javafx.scene.control.ButtonType.OK);
                        alert.showAndWait();
                    }
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Non connexion !!!",javafx.scene.control.ButtonType.OK);
                    alert.showAndWait();
                }
                showChambre();
            }catch(Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Non connexion !!!",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
            }
        }
    }
    
    
    
    
    private boolean checkUser() {
        PreparedStatement ps;
        ResultSet rs;
        boolean test = false;
        String query = "SELECT numch FROM chambre WHERE numch = ?";
        try  {
            ps = ConnexionDB.getConnection().prepareStatement(query);
            ps.setString(1,txtNumch.getText());
            rs = ps.executeQuery();
            if(rs.next()) {
                test = true;
                Alert alert = new Alert(Alert.AlertType.ERROR,"Ces Informations sont deja existe !!!",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
                txtNumch.setText("");
                txtAvailability.setText("");
                txtPrix.setText("");
                txtNumch.requestFocus();
            }
        }catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"No Connexion !!!",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }
        return test;
    }
    
    private boolean checkEmptyFields() {
        return (txtPrix.getText().equals("")||txtType.getText().equals(""));
    }
    //  ========================================================================================= Tableau 
    public ObservableList<Chambre> data = FXCollections.observableArrayList();
    public void showChambre() {
        tableau.getItems().clear();
        String sql = "SELECT * FROM Chambre";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                data.add(new Chambre(rs.getInt("numch"),rs.getString("availability"),rs.getString("prix"),rs.getString("type")));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        tabNumch.setCellValueFactory(new PropertyValueFactory<Chambre,Integer>("numch"));
        tabAvailability.setCellValueFactory(new PropertyValueFactory<Chambre,Boolean>("availability"));
        tabPrix.setCellValueFactory(new PropertyValueFactory<Chambre,String>("prix"));
        tabType.setCellValueFactory(new PropertyValueFactory<Chambre,String>("type"));
        tableau.setItems(data);
        txtNumch.setText("");
        txtAvailability.setText("");
        txtPrix.setText("");
        txtAvailability.requestFocus();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnexionDB.getConnection();
        showChambre();
    }
    
}
