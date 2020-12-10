package com.example.parcouractivity.nosql;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.parcouractivity.bean.MyPoint;
import com.example.parcouractivity.bean.Parcours;
import com.example.parcouractivity.bean.Trajet;
import com.example.parcouractivity.bean.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MyFireStore {
    FirebaseFirestore db;
    User user;
    Parcours parcour;
    MyPoint mypoint;

    public MyFireStore(FirebaseFirestore db) {
        this.db = db;
        this.user = new User();
        this.parcour = new Parcours();
    }

    public void readCtlConnect(FireCallBack fireCallBack, String email){


        this.db.collection("user")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            User callBackUser = new User();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                callBackUser.setId(document.getId());
                                callBackUser.setEmail((String) document.getData().get("email"));
                                callBackUser.setPassword((String) document.getData().get("password"));
                                callBackUser.setLienavatar((String) document.getData().get("lienavatar"));
                                callBackUser.setNom((String) document.getData().get("nom"));
                                callBackUser.setPrenom((String) document.getData().get("prenom"));
                                callBackUser.setPseudo((String) document.getData().get("pseudo"));
                            }
//                            callBackUser.setId("0");
                            fireCallBack.onCallBack(callBackUser);
                        }
                        else {
                            Log.w("ERROR", "fail lors de la lecture ");
                        }
                    }

                });
    }

    public void readDataUser(FireCallBack fireCallBack, String email){


        this.db.collection("user")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            User callBackUser = new User();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                callBackUser.setId(document.getId());
                                callBackUser.setEmail((String) document.getData().get("email"));
                                callBackUser.setPassword((String) document.getData().get("password"));
                                callBackUser.setLienavatar((String) document.getData().get("lienavatar"));
                                callBackUser.setNom((String) document.getData().get("nom"));
                                callBackUser.setPrenom((String) document.getData().get("prenom"));
                                callBackUser.setPseudo((String) document.getData().get("pseudo"));
                            }
                            callBackUser.setId("0");
                            fireCallBack .onCallBack(callBackUser);
                        }
                        else {
                            Log.w("ERROR", "fail lors de la lecture ");
                        }
                    }

                });
    }
    public void readDataParcours(FireCallBack fireCallBack, String userid){


        this.db.collection("parcours")
  //              .whereEqualTo("userid", userid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            User callBackUser = new User();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                callBackUser.setId(document.getId());
                                callBackUser.setEmail((String) document.getData().get("email"));
                                callBackUser.setPassword((String) document.getData().get("password"));
                                callBackUser.setLienavatar((String) document.getData().get("lienavatar"));
                                callBackUser.setPassword((String) document.getData().get("nom"));
                                callBackUser.setPassword((String) document.getData().get("prenom"));
                                callBackUser.setPseudo((String) document.getData().get("pseudo"));
                            }
                            callBackUser.setId("0");

                            fireCallBack.onCallBack(callBackUser);
                        }
                        else {
                            Log.w("ERROR", "fail lors de la lecture ");
                        }
                    }

                });
    }
    public void EnregistrerParcours(ParcoursCallBack fireCallBack, Parcours parcours, User user) {
        // Create a new user with a first and last name
        Map<String, Object> userfirebase = new HashMap<>();
        userfirebase.put("user", parcours.getUser());
        userfirebase.put("userid",  parcours.getUserId());
        userfirebase.put("description", parcours.getDescription());
        userfirebase.put("lienimage", parcours.getLientimage());
        userfirebase.put("niveau", parcours.getNiveau());
        userfirebase.put("nom", parcours.getNom());
        userfirebase.put("timestamp", parcours.getTimestamp());

// Add a new document with a generated ID
        db.collection("parcours")
                .add(parcours)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("INFOPAUL", "DocumentSnapshot added with ID: " + documentReference.getId());
                        parcours.setParcourid(documentReference.getId());

                        fireCallBack.onCallBack(parcours);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("INFOPAUL", "Error adding document", e);
                    }
                });

    }
    public void EnregistrerMyPoint(MyPointCallBack fireCallBack, MyPoint mypoints, Parcours parcours, User user) {
        // Create a new user with a first and last name
        Map<String, Object> userfirebase = new HashMap<>();
        userfirebase.put("user", parcours.getUser());
        userfirebase.put("userid",  parcours.getUserId());
        userfirebase.put("description", parcours.getDescription());
        userfirebase.put("lienimage", parcours.getLientimage());
        userfirebase.put("niveau", parcours.getNiveau());
        userfirebase.put("nom", parcours.getNom());
        userfirebase.put("timestamp", parcours.getTimestamp());

// Add a new document with a generated ID
        db.collection("point")
                .add(parcours)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("INFOPAUL", "DocumentSnapshot added with ID: " + documentReference.getId());
                        parcours.setParcourid(documentReference.getId());

                        fireCallBack.onCallBack(mypoints);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("INFOPAUL", "Error adding document", e);
                    }
                });

    }
    public void EnregistrerTrajet(TrajetCallBack fireCallBack, Trajet trajet, Parcours parcours, User user) {
        // Create a new user with a first and last name
        Map<String, Object> userfirebase = new HashMap<>();
        userfirebase.put("user", parcours.getUser());
        userfirebase.put("userid",  parcours.getUserId());
        userfirebase.put("description", parcours.getDescription());
        userfirebase.put("lienimage", parcours.getLientimage());
        userfirebase.put("niveau", parcours.getNiveau());
        userfirebase.put("nom", parcours.getNom());
        userfirebase.put("timestamp", parcours.getTimestamp());

// Add a new document with a generated ID
        db.collection("geopoints")
                .add(parcours)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("INFOPAUL", "DocumentSnapshot added with ID: " + documentReference.getId());
                        parcours.setParcourid(documentReference.getId());

                        fireCallBack.onCallBack(trajet);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("INFOPAUL", "Error adding document", e);
                    }
                });

    }
}
