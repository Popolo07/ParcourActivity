package com.example.parcouractivity.bean;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class Parcoursss {
    String Id;
    String userId;
    String nom;
    String description;
    String lientimage;
    String niveau;
    MyPoint pointDepart;
    MyPoint pointFin;
    ArrayList<MyPoint> listEtape;
    ArrayList<GeoPoint> trajet;
    ArrayList<Parcoursss> listparcour;

    public ArrayList<Parcoursss> getListparcour() {
        return listparcour;
    }

    public void setListparcour(ArrayList<Parcoursss> listparcour) {
        this.listparcour = listparcour;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getLientimage() {
        return lientimage;
    }

    public void setLientimage(String lientimage) {
        this.lientimage = lientimage;
    }

    public Parcoursss(String userId, String nom, String description, String lienimage, String niveau, MyPoint pointDepart, MyPoint pointFin, ArrayList<MyPoint> listEtape, ArrayList<GeoPoint> trajet) {
        this.userId = userId;
        this.nom = nom;
        this.description = description;
        this.niveau = niveau;
        this.lientimage = lienimage;
        this.pointDepart = pointDepart;
        this.pointFin = pointFin;
        this.listEtape = listEtape;
        this.trajet = trajet;
    }

    public Parcoursss(String userId, String nom, String niveau, MyPoint pointDepart, MyPoint pointFin, ArrayList<MyPoint> listEtape, ArrayList<GeoPoint> trajet) {
        this.userId = userId;
        this.nom = nom;
        this.niveau = niveau;
        this.pointDepart = pointDepart;
        this.pointFin = pointFin;
        this.listEtape = listEtape;
        this.trajet = trajet;
    }



    public Parcoursss() {
    }

    public Parcoursss(String userId, String nom, MyPoint pointDepart, MyPoint pointFin, ArrayList<MyPoint> listEtape, ArrayList<GeoPoint> trajet) {
        this.userId = userId;
        this.nom = nom;
        this.pointDepart = pointDepart;
        this.pointFin = pointFin;
        this.listEtape = listEtape;
        this.trajet = trajet;
    }
    public String getUserId() {
        return userId;
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

    public MyPoint getPointDepart() {
        return pointDepart;
    }

    public void setPointDepart(MyPoint pointDepart) {
        this.pointDepart = pointDepart;
    }

    public MyPoint getPointFin() {
        return pointFin;
    }

    public void setPointFin(MyPoint pointFin) {
        this.pointFin = pointFin;
    }

    public ArrayList<MyPoint> getListEtape() {
        return listEtape;
    }

    public void setListEtape(ArrayList<MyPoint> listEtape) {
        this.listEtape = listEtape;
    }

    public ArrayList<GeoPoint> getTrajet() {
        return trajet;
    }

    public void setTrajet(ArrayList<GeoPoint> trajet) {
        this.trajet = trajet;
    }


}
