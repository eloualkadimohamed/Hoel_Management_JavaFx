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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Client;

/**
 *
 * @author Hp
 */
public class ClientsController implements Initializable {
    
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    @FXML
    private TableColumn<Client, Integer> tabCinC;

    @FXML
    private TableColumn<Client, String> tabNom;

    @FXML
    private TableColumn<Client, String> tabPasse;

    @FXML
    private TableColumn<Client, String> tabEmail;

    @FXML
    private TableColumn<Client, Integer> tabChambreId;

    @FXML
    private TableView<Client> tableau;

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
    private TextField txtCinC;

    @FXML
    private TextField txtNom;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtChambreId;
    
    private Parent fxml;
    public ClientsController() {
        this.tabCinC = new TableColumn<>();
        this.txtCinC = new TextField();
        this.tabEmail=new TableColumn<>();
        this.tabNom = new TableColumn<>();
        this.tabChambreId = new TableColumn<>();
        this.txtChambreId = new TextField();
        this.txtEmail = new TextField();
        this.txtNom = new TextField();
    }
    public ClientsController(TableColumn<Client, Integer> tabCinC, TableColumn<Client, String> tabEmail, TableColumn<Client, Integer> tabChambreId) {
        this.tabCinC = tabCinC;
        this.tabEmail = tabEmail;
        this.tabChambreId = tabChambreId;
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
            String nom = txtNom.getText();
            String cinC = txtCinC.getText();
            String email = txtEmail.getText();
            String mtPasse = txtPassword.getText();
            String chambreId = txtChambreId.getText();
                PreparedStatement ps;
                String query = "INSERT INTO client(cinC,nom,email,password,chambreId) VALUES(?,?,?,?,?)";
                try {
                    ps = ConnexionDB.getConnection().prepareStatement(query);
                    ps.setString(1,cinC);
                    ps.setString(2,nom);
                    ps.setString(3,email);
                    ps.setString(4,mtPasse);
                    ps.setString(5,chambreId);
                    if(!checkUser()) {
                        if(ps.executeUpdate()!=0) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Votre Compte est Créer avec Succés !!!",javafx.scene.control.ButtonType.CLOSE);
                            alert.showAndWait();
                            txtCinC.setText("");
                            txtNom.setText("");
                            txtEmail.setText("");
                            txtPassword.setText("");
                            txtChambreId.setText("");
                            txtNom.requestFocus();
                        }else {
                            Alert alert = new Alert(Alert.AlertType.ERROR,"Votre Compte n'est pas Créer !!!",javafx.scene.control.ButtonType.OK);
                            alert.showAndWait();
                        }
                    }
                    showClients();
                }catch(Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,e+"Problem dans la base de donnee !!!",javafx.scene.control.ButtonType.OK);
                    alert.showAndWait();
                }
        }}

    @FXML
    void modifier() {
        if(txtCinC.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"cin Pas Insere Pour Modifier !!!",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }else {
            PreparedStatement ps,ps2;
            ResultSet rs;
            String query = "SELECT cinC FROM client WHERE cinC = ?";
            try  {
            ps = ConnexionDB.getConnection().prepareStatement(query);
            ps.setString(1,txtCinC.getText());
            rs = ps.executeQuery();
            if(rs.next()) {
                String mod = "UPDATE client SET nom = ?,email = ?,password = ?,chambreId = ? WHERE cinC = ?";
                try  {
                    ps2 = ConnexionDB.getConnection().prepareStatement(mod);
                    ps2.setString(1, txtNom.getText());
                    ps2.setString(2, txtEmail.getText());
                    ps2.setString(3, txtPassword.getText());
                    ps2.setString(4, txtChambreId.getText());
                    ps2.setString(5, txtCinC.getText());
                    ps2.execute();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Le Compte est Modifé avec Succés !!!",javafx.scene.control.ButtonType.CLOSE);
                    alert.showAndWait();
                    txtCinC.setText("");
                    txtNom.setText("");
                    txtEmail.setText("");
                    txtPassword.setText("");
                    txtChambreId.setText("");
                    txtNom.requestFocus();
                }catch(Exception ee) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Error !!!",javafx.scene.control.ButtonType.OK);
                    alert.showAndWait();
                }
            }else {
                 Alert alert = new Alert(Alert.AlertType.ERROR,"N'existe pas !!!",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
            }
            showClients();
        }catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"No connection !!!",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }
        }
    }

    @FXML
    void supprimer() {
        if(txtCinC.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"cin Pas Inserer Pour Supprimer !!!",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }else {
            PreparedStatement ps,ps2;
            ResultSet rs;
            String query = "SELECT cinC FROM client WHERE cinC = ?";
            try  {
            ps = ConnexionDB.getConnection().prepareStatement(query);
            ps.setString(1,txtCinC.getText());
            rs = ps.executeQuery();
            if(rs.next()) {
                String sup = "DELETE FROM client WHERE cinC = ?";
                try  {
                    ps2 = ConnexionDB.getConnection().prepareStatement(sup);
                    ps2.setString(1, txtCinC.getText());
                    ps2.execute();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Le Compte est Supprimer avec Succés !!!",javafx.scene.control.ButtonType.CLOSE);
                    alert.showAndWait();
                    txtCinC.setText("");
                    txtNom.setText("");
                    txtEmail.setText("");
                    txtPassword.setText("");
                    txtChambreId.setText("");
                    txtNom.requestFocus();
                }catch(Exception ee) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Non connexion !!!",javafx.scene.control.ButtonType.OK);
                    alert.showAndWait();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Non connexion !!!",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
            }
                showClients();
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
        String query = "SELECT nom,email FROM client WHERE nom = ? AND email = ?";
        try  {
            ps = ConnexionDB.getConnection().prepareStatement(query);
            ps.setString(1,txtNom.getText());
            ps.setString(2,txtEmail.getText());
            rs = ps.executeQuery();
            if(rs.next()) {
                test = true;
                Alert alert = new Alert(Alert.AlertType.ERROR,"Ces Informations sont deja existe !!!",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
                txtCinC.setText("");
                txtNom.setText("");
                txtEmail.setText("");
                txtPassword.setText("");
                txtChambreId.setText("");
                txtNom.requestFocus();
            }
        }catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"No Connexion !!!",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }
        return test;
    }
    
    private boolean checkEmptyFields() {
        return (txtNom.getText().equals("")||txtEmail.getText().equals("")||txtPassword.equals("")||txtChambreId.getText().equals(""));
    }
    
    public ObservableList<Client> data = FXCollections.observableArrayList();
    public void showClients() {
        tableau.getItems().clear();
        String sql = "SELECT * FROM client";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                data.add(new Client(rs.getString("cinC"),rs.getString("nom"),rs.getString("email"),rs.getString("password"),rs.getInt("chambreId")));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        tabCinC.setCellValueFactory(new PropertyValueFactory<Client,Integer>("cinC"));
        tabNom.setCellValueFactory(new PropertyValueFactory<Client,String>("nom"));
        tabEmail.setCellValueFactory(new PropertyValueFactory<Client,String>("email"));
        tabPasse.setCellValueFactory(new PropertyValueFactory<Client,String>("password"));
        tabChambreId.setCellValueFactory(new PropertyValueFactory<Client,Integer>("chambreId"));
        tableau.setItems(data);
        txtCinC.setText("");
        txtNom.setText("");
        txtEmail.setText("");
        txtChambreId.setText("");
        txtNom.requestFocus();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnexionDB.getConnection();
        showClients();
    }
    
}
