package com.example.parcouractivity.bean;

import org.osmdroid.util.GeoPoint;

import java.util.Date;
import java.util.HashMap;

public class Trajet {
    long order;
    GeoPoint geoPoint;
    int altitude;
    Date timestamp;
    String parcours;
    String parcoursid;

    public Trajet() {
    }

    public Trajet(long order, GeoPoint geoPoint, int altitude, Date timestamp, String parcours, String parcoursid) {
        this.order = order;
        this.geoPoint = geoPoint;
        this.altitude = altitude;
        this.timestamp = timestamp;
        this.parcours = parcours;
        this.parcoursid = parcoursid;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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
}
