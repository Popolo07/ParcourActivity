package com.example.parcouractivity.bean;

import org.osmdroid.util.GeoPoint;

import java.util.Date;

public class MyPoint {
    Long order;
    Date timeStamp;
    GeoPoint geoPoint;
    String type;
    String adresse;
    String Altitude;
    String parcours;
    String parcoursid;

    public MyPoint() {
    }

    public String getParcours() {
        return parcours;
    }

    public void setParcours(String parcours) {
        this.parcours = parcours;
    }

    public String getParcoursid() {
        return parcoursid;
    }

    public void setParcoursid(String parcoursid) {
        this.parcoursid = parcoursid;
    }

    public MyPoint(Long order, Date timeStamp, GeoPoint geoPoint, String type, String adresse, String altitude, String parcours, String parcoursid) {
        this.order = order;
        this.timeStamp = timeStamp;
        this.geoPoint = geoPoint;
        this.type = type;
        this.adresse = adresse;
        Altitude = altitude;
        this.parcours = parcours;
        this.parcoursid = parcoursid;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getAltitude() {
        return Altitude;
    }

    public void setAltitude(String altitude) {
        Altitude = altitude;
    }

    public MyPoint(Date timeStamp, GeoPoint geoPoint, String type, String altitude, String adresse, long order) {
        this.timeStamp = timeStamp;
        this.geoPoint = geoPoint;
        this.type = type;
        this.type = altitude;
        this.order = order;
        this.adresse = adresse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }



    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }


}
