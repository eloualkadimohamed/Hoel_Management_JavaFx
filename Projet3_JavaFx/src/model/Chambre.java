/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Hp
 */
public class Chambre {
    int numch;
    String availability;
    String type;
    String prix;
    public Chambre() {
        super();
    }

    public Chambre(int numch, String availability, String prix,String type) {
        this.numch = numch;
        this.availability = availability;
        this.prix = prix;
        this.type=type;
    }

    public int getNumch() {
        return numch;
    }

    public void setNumch(int numch) {
        this.numch = numch;
    }

    public String getAvailability() {
        return availability;
    }

    public void setavAilability(String availability) {
        this.availability = availability;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
}
