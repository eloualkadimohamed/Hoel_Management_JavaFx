/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Hp
 */
public class Employee {
    String cinE;
    String nom;
    String email;
    
    public Employee() {
        super();
    }

    public Employee(String cinE, String nom, String email) {
        this.cinE = cinE;
        this.nom = nom;
        this.email = email;
    }

    public String getCinE() {
        return cinE;
    }

    public void setCinE(String cinE) {
        this.cinE = cinE;
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


    
}
