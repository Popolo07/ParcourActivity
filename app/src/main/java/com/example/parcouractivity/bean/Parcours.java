package com.example.parcouractivity.bean;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.Date;

public class Parcours {
    String parcourid;
    String user;
    String userId;
    String nom;
    String description;
    String lientimage;
    String niveau;
    Date timestamp;

    public Parcours() {
    }

    public String getParcourid() {
        return parcourid;
    }

    public void setParcourid(String parcourid) {
        this.parcourid = parcourid;
    }

    public Parcours(String userId, String nom, String description, String lientimage, String niveau, Date timestramp) {
        this.userId = userId;
        this.nom = nom;
        this.description = description;
        this.lientimage = lientimage;
        this.niveau = niveau;
        this.timestamp = timestramp;
    }

    public String getUser() {
        return user;
    }

    public String getUserId() {
        return userId;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLientimage() {
        return lientimage;
    }

    public void setLientimage(String lientimage) {
        this.lientimage = lientimage;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestramp) {
        this.timestamp = timestramp;
    }
}
