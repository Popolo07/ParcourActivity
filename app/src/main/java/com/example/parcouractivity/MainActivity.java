package com.example.parcouractivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btnParcours;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db =  FirebaseFirestore.getInstance();

        btnParcours = findViewById(R.id.btnParcour);

        btnParcours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = (String) btnParcours.getText();

                Intent intent = new Intent(MainActivity.this,MenuActivity.class );
                intent.putExtra("titre",txt);
                startActivity(intent);

            }
        });
    }
//    public void addUser() {
//// Create a new user with a first and last name
//        Map<String, Object> user = new HashMap<>();
//        user.put("first", "Ada");
//        user.put("last", "Lovelace");
//        user.put("born", 1815);
//
//// Add a new document with a generated ID
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("INFOPAUL", "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("INFOPAUL", "Error adding document", e);
//                    }
//                });
//    }
//
//
//    public User getUser() {
//        User user = null;
//        db.collection("Users")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("INFOPAUL", document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.w("INFOPAUL", "Error getting documents.", task.getException());
//                        }
//                    }
//                });
//        return user;
//    }
}