package com.example.parcouractivity.bean;

import androidx.annotation.NonNull;

import com.example.parcouractivity.EnregisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class User {
    String id;
    String nom;
    String prenom;
    String pseudo;
    String email;
    String lienavatar;
    String password;

    public User() {

    }


    public User(String id,String nom, String prenom, String pseudo, String email, String lienavatar, String password) {
        this.id = id;

        this.nom = nom;
        this.password = nom;
        this.prenom = prenom;
        this.pseudo = pseudo;
        this.email = email;
        this.lienavatar = lienavatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLienavatar() {
        return lienavatar;
    }

    public void setLienavatar(String lienavatar) {
        this.lienavatar = lienavatar;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
