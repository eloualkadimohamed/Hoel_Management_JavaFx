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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;

/**
 *
 * @author Hp
 */
public class EmployeeController implements Initializable {
    
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    @FXML
    private TableColumn<Employee, String> tabCinE;

    @FXML
    private TableColumn<Employee, String> tabNom;

    @FXML
    private TableColumn<Employee, String> tabEmail;
    

    @FXML
    private TableView<Employee> tableau;

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
    private TextField txtCinE;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtEmail;

    
    private Parent fxml;
    public EmployeeController() {
        this.tabCinE = new TableColumn<>();
        this.txtCinE = new TextField();
        this.tabEmail=new TableColumn<>();
        this.tabNom = new TableColumn<>();
        this.txtEmail = new TextField();
        this.txtNom = new TextField();
    }
    public EmployeeController(TableColumn<Employee, String> tabCinE, TableColumn<Employee, String> tabEmail, TableColumn<Employee, String> tabNom, TextField txtCinE, TextField txtEmail, TextField txtNom) {
        this.tabCinE = tabCinE;
        this.tabEmail = tabEmail;
        this.tabNom = tabNom;
        this.txtCinE = txtCinE;
        this.txtEmail = txtEmail;
        this.txtNom = txtNom;
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
            String cinC = txtCinE.getText();
            String nom = txtNom.getText();
            String email = txtEmail.getText();

            
                PreparedStatement ps;
                String query = "INSERT INTO employee(cinE,nomE,emailE) VALUES(?,?,?)";
                try {
                    ps = ConnexionDB.getConnection().prepareStatement(query);
                    ps.setString(1,cinC);
                    ps.setString(2,nom);
                    ps.setString(3,email);
//                    ps.setString(4,chambre);
                    if(!checkEmployee()) {
                        if(ps.executeUpdate()!=0) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Votre Compte est Créer avec Succés !!!",javafx.scene.control.ButtonType.CLOSE);
                            alert.showAndWait();
                            txtCinE.setText("");
                            txtNom.setText("");
                            txtEmail.setText("");
                            txtNom.requestFocus();
                        }else {
                            Alert alert = new Alert(Alert.AlertType.ERROR,"Votre Compte n'est pas Créer !!!",javafx.scene.control.ButtonType.OK);
                            alert.showAndWait();
                        }
                    }
                    showEmployee();
                }catch(Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,e +"Problem dans la base de donnee !!!",javafx.scene.control.ButtonType.OK);
                    alert.showAndWait();
                }
            
        }
    }

    @FXML
    void modifier() {
        if(txtCinE.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"cinC Pas Inserer Pour Modifier !!!",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }else {
            PreparedStatement ps,ps2;
            ResultSet rs;
            String query = "SELECT cinE FROM employee WHERE cinE = ?";
            try  {
            ps = ConnexionDB.getConnection().prepareStatement(query);
            ps.setString(1,txtCinE.getText());
            rs = ps.executeQuery();
            if(rs.next()) {
                String mod = "UPDATE employee SET nomE = ?,emailE = ? WHERE cinE = ?";
                try  {
                    ps2 = ConnexionDB.getConnection().prepareStatement(mod);
                    ps2.setString(1, txtNom.getText());
                    ps2.setString(2, txtEmail.getText());
                    ps2.setString(3, txtCinE.getText());
                    ps2.execute();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Le Compte est Modifer avec Succés !!!",javafx.scene.control.ButtonType.CLOSE);
                    alert.showAndWait();
                    txtCinE.setText("");
                    txtNom.setText("");
                    txtEmail.setText("");
                    txtNom.requestFocus();
                }catch(Exception ee) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"No connection pour deuxieme !!!",javafx.scene.control.ButtonType.OK);
                    alert.showAndWait();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"N'existe pas !!!",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
            }
            showEmployee();
            }catch(Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"No connection !!!",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    @FXML
    void supprimer() {
        if(txtCinE.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"cinE Pas Inserer Pour Supprimer !!!",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }else {
            PreparedStatement ps,ps2;
            ResultSet rs;
            String query = "SELECT cinE FROM employee WHERE cinE = ?";
            try  {
            ps = ConnexionDB.getConnection().prepareStatement(query);
            ps.setString(1,txtCinE.getText());
            rs = ps.executeQuery();
            if(rs.next()) {
                String sup = "DELETE FROM employee WHERE cinE = ?";
                try  {
                    ps2 = ConnexionDB.getConnection().prepareStatement(sup);
                    ps2.setString(1, txtCinE.getText());
                    ps2.execute();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Le Compte est Supprimer avec Succés !!!",javafx.scene.control.ButtonType.CLOSE);
                    alert.showAndWait();
                    txtCinE.setText("");
                    txtNom.setText("");
                    txtEmail.setText("");
                    txtNom.requestFocus();
                }catch(Exception ee) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Non connexion !!!",javafx.scene.control.ButtonType.OK);
                    alert.showAndWait();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Non connexion !!!",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
            }
            showEmployee();
        }catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Non connexion !!!",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }
        }
    }
    
    
    private boolean checkEmptyFields() {
        return (txtNom.getText().equals("")||txtEmail.getText().equals(""));
    }
    private boolean checkEmployee() {
        PreparedStatement ps;
        ResultSet rs;
        boolean test = false;
        String query = "SELECT nomE,emailE FROM employee WHERE nomE = ? AND emailE = ?";
        try  {
            ps = ConnexionDB.getConnection().prepareStatement(query);
            ps.setString(1,txtNom.getText());
            ps.setString(2,txtEmail.getText());
            rs = ps.executeQuery();
            if(rs.next()) {
                test = true;
                Alert alert = new Alert(Alert.AlertType.ERROR,"Ces informations existent !!!",javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
                txtCinE.setText("");
                txtNom.setText("");
                txtEmail.setText("");
                txtNom.requestFocus();
            }
        }catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"No Connexion !!!",javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        }
        return test;
    }
    //  ========================================================================================= Tableau 
    public ObservableList<Employee> data = FXCollections.observableArrayList();
    public void showEmployee() {
        tableau.getItems().clear();
        String sql = "SELECT * FROM employee";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                data.add(new Employee(rs.getString("cinE"),rs.getString("nomE"),rs.getString("emailE")));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        tabCinE.setCellValueFactory(new PropertyValueFactory<Employee,String>("cinE"));
        tabNom.setCellValueFactory(new PropertyValueFactory<Employee,String>("nom"));
        tabEmail.setCellValueFactory(new PropertyValueFactory<Employee,String>("email"));
        tableau.setItems(data);
        txtCinE.setText("");
        txtNom.setText("");
        txtEmail.setText("");
        txtNom.requestFocus();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = ConnexionDB.getConnection();
        showEmployee();
    }
    
}
