package com.example.parcouractivity.bean;

import java.util.Date;

public class Exercice {
    String userId;
    String titre;
    String desciption;
    String Type;
    Date dtDebut;
    Date dtFin;
    String LienMedia;

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public Exercice() {
    }

    public Exercice(String userId, String titre, String desciption, String type, Date dtDebut, Date dtFin, String lienMedia) {
        this.userId = userId;
        this.titre = titre;
        this.desciption = desciption;
        Type = type;
        this.dtDebut = dtDebut;
        this.dtFin = dtFin;
        LienMedia = lienMedia;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLienMedia() {
        return LienMedia;
    }

    public void setLienMedia(String lienMedia) {
        LienMedia = lienMedia;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Date getDtDebut() {
        return dtDebut;
    }

    public void setDtDebut(Date dtDebut) {
        this.dtDebut = dtDebut;
    }

    public Date getDtFin() {
        return dtFin;
    }

    public void setDtFin(Date dtFin) {
        this.dtFin = dtFin;
    }
}
