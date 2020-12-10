package com.example.parcouractivity.bean;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class ListeParcours {
    String userId;
    String imgavatar;
    String txtNomParcour;
    String txtDistance;
    String imgLevel;
    String txtLevel;
    private Date mTimestamp;

    public ListeParcours() {
    }


    public ListeParcours(String userId, String imgavatar, String txtNomParcour, String txtDistance, String imgLevel, String txtLevel) {
        this.userId = userId;
        this.imgavatar = imgavatar;
        this.txtNomParcour = txtNomParcour;
        this.txtDistance = txtDistance;
        this.imgLevel = imgLevel;
        this.txtLevel = txtLevel;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImgavatar() {
        return imgavatar;
    }

    public void setImgavatar(String imgavatar) {
        this.imgavatar = imgavatar;
    }

    public String getTxtNomParcour() {
        return txtNomParcour;
    }

    public void setTxtNomParcour(String txtNomParcour) {
        this.txtNomParcour = txtNomParcour;
    }

    public String getTxtDistance() {
        return txtDistance;
    }

    public void setTxtDistance(String txtDistance) {
        this.txtDistance = txtDistance;
    }

    public String getImgLevel() {
        return imgLevel;
    }

    public void setImgLevel(String imgLevel) {
        this.imgLevel = imgLevel;
    }

    public String getTxtLevel() {
        return txtLevel;
    }

    public void setTxtLevel(String txtLevel) {
        this.txtLevel = txtLevel;
    }
    @ServerTimestamp
    public Date getTimestamp() { return mTimestamp; }

    public void setTimestamp(Date timestamp) { mTimestamp = timestamp; }
}
