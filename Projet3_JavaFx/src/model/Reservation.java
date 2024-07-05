/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Hp
 */
public class Reservation {
    int idr;
    String clientcin;
    int chambrenum;
    Date date_entree;
    Date date_sortie;

    
    public Reservation() {
        super();
    }
    public Reservation(int idr,String clientcin,int chambrenum,Date date_entree,Date date_sortie) {
        this.idr = idr;
        this.clientcin = clientcin;
        this.chambrenum = chambrenum;
        this.date_entree = date_entree;
        this.date_sortie = date_sortie;
    }

    public int getId() {
        return idr;
    }

    public void setId(int idr) {
        this.idr = idr;
    }

    public String getClientcin() {
        return clientcin;
    }

    public void setClientcin(String clientcin) {
        this.clientcin = clientcin;
    }

    public int getChambrenum() {
        return chambrenum;
    }

    public void setChambrenum(int chambrenum) {
        this.chambrenum = chambrenum;
    }

    public Date getDate_entree() {
        return date_entree;
    }

    public void setDate_entree(Date date_entree) {
        this.date_entree = date_entree;
    }

    public Date getDate_sortie() {
        return date_sortie;
    }

    public void setDate_sortie(Date date_sortie) {
        this.date_sortie = date_sortie;
    }

    
}
