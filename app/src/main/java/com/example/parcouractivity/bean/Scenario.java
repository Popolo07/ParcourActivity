package com.example.parcouractivity.bean;

import java.util.ArrayList;

public class Scenario {
    String userID;
    String titre;
    ArrayList<Exercice> lstExercie;

    public Scenario() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public ArrayList<Exercice> getLstExercie() {
        return lstExercie;
    }

    public void setLstExercie(ArrayList<Exercice> lstExercie) {
        this.lstExercie = lstExercie;
    }
}
