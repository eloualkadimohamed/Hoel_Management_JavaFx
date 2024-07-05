/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Hp
 */
public class Client {
    String cinC;
    String nom;
    String email;
    String password;
    int chambreId;
    
    public Client() {
        super();
    }

    public Client(String cinC, String nom, String email, String password, int chamreId) {
        this.cinC = cinC;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.chambreId = chamreId;

    }

    public String getCinC() {
        return cinC;
    }

    public void setCinC(String id) {
        this.cinC = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getChambreId() {
        return chambreId;
    }

    public void setChambreId(int chambreId) {
        this.chambreId = chambreId;
    }

    
}
