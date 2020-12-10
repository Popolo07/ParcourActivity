package com.example.parcouractivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.parcouractivity.bean.ListeParcours;
import com.example.parcouractivity.bean.Parcours;
import com.example.parcouractivity.bean.Parcoursss;
import com.example.parcouractivity.bean.User;
import com.example.parcouractivity.nosql.MyFireStore;
import com.example.parcouractivity.nosql.ParcoursCallBack;
import com.example.parcouractivity.recycle.ListeViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;

public class ParcourActivity extends AppCompatActivity {
    FloatingActionButton flParcour;
    EditText txtNomParcour;
    Button btnValiderRef;
    Button btnAnnuler;

//    FirebaseFirestore db = FirebaseFirestore.getInstance();
    AlertDialog alertDialog;
    RadioGroup radioGroupLevel;
    EditText txtCommentaire;
    RadioButton radioFacile;
    RadioButton radioInter;
    RadioButton radioDifficile;
    RecyclerView mRecyclerView;
    FirestoreRecyclerAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MyFireStore myFireStore = new MyFireStore(db);
    User user = new User();
    Parcours parcours = new Parcours();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcour);
        flParcour = (FloatingActionButton) findViewById(R.id.flparcour);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

// recupération de l'user
        Bundle intent = getIntent().getExtras();
        user.setId(intent.getString("userid"));
        user.setPseudo(intent.getString("pseudo"));
        user.setNom(intent.getString("nom"));
        user.setPrenom(intent.getString("prenom"));
        user.setLienavatar(intent.getString("lienvavatar"));
        user.setEmail(intent.getString("email"));

// Preparation du Query
        Log.i("INFOPAUL", "userid");
        Query query = FirebaseFirestore.getInstance()
                .collection("parcours")
 //               .orderBy("timestamp")
                .whereEqualTo("user", user.getId())
                .limit(50);

        FirestoreRecyclerOptions<ListeParcours> options = new FirestoreRecyclerOptions.Builder<ListeParcours>()
                .setQuery(query, ListeParcours.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<ListeParcours, ListeViewHolder>(options) {
            @Override
            public void onBindViewHolder(ListeViewHolder holder, int position, ListeParcours model) {

            }

            @Override
            public ListeViewHolder onCreateViewHolder(ViewGroup group, int i) {
                // Using a custom layout called R.layout.message for each item, we create a new instance of the viewholder
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.item_parcours, group, false);

                return new ListeViewHolder(view);
            }
        };
//Final step, where "mRecyclerView" is defined in your xml layout as
//the recyclerview

        mRecyclerView.setAdapter(adapter);


        flParcour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(ParcourActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.refparcour_layout,null);
                txtNomParcour = (EditText) mView.findViewById(R.id.txtNomParcour);
                txtCommentaire = (EditText) mView.findViewById(R.id.txtCommentaire);
                btnValiderRef = (Button) mView.findViewById(R.id.btnValiderRef);
                btnAnnuler = (Button) mView.findViewById(R.id.btnCancel);
                radioGroupLevel = (RadioGroup) mView.findViewById(R.id.radioGroupLevel);
                radioFacile = (RadioButton) mView.findViewById(R.id.radioFacile);
                radioInter = (RadioButton) mView.findViewById(R.id.radioInter);
                radioDifficile  = (RadioButton) mView.findViewById(R.id.radioDifficile);


                alert.setView(mView);
                alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);

                radioGroupLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        Log.i("PAULINFO", "RadioButton "+ group.getCheckedRadioButtonId()+ " : "+ checkedId);
                    }
                });
                btnAnnuler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { alertDialog.dismiss(); }
                });
                btnValiderRef.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txt = "Creer un parcours";
                        parcours.setUserId("/user/id?" +user.getId());
                        parcours.setUser(user.getId());
                        parcours.setNom(txtNomParcour.getText().toString());
                        parcours.setNiveau(getNiveau(radioGroupLevel));
                        parcours.setDescription(txtCommentaire.getText().toString());
                        parcours.setTimestamp(new Date());
                        myFireStore.EnregistrerParcours(new ParcoursCallBack() {
                            @Override
                            public void onCallBack(Parcours parcours) {
                                Intent intent = new Intent(ParcourActivity.this,CreerParcourActivity.class);

                                intent.putExtra("userid", user.getId());
                                intent.putExtra("pseudo", user.getPseudo());
                                intent.putExtra("lienavatar", user.getLienavatar());
                                intent.putExtra("nom", user.getNom());
                                intent.putExtra("prenom", user.getPrenom());
                                intent.putExtra("email", user.getEmail());
                                intent.putExtra("parcoursid",parcours.getParcourid());
                                intent.putExtra("parcoursnom",parcours.getNom());
                                intent.putExtra("parcoursdes",parcours.getDescription());
                                startActivity(intent);

                                alertDialog.dismiss();

                            }
                        }, parcours, user );

                    }
                });
                alertDialog.show();
            }

        });
    }
    private String getNiveau(RadioGroup group) {
        String sRep = new String("Facile");
        int checkedRadioId = group.getCheckedRadioButtonId();

        if(checkedRadioId == R.id.radioFacile) {
            sRep = radioFacile.getText().toString();
        }
        else if(checkedRadioId == R.id.radioInter ) {
            sRep= radioInter.getText().toString();
        }
        else if (checkedRadioId == R.id.radioDifficile) {
            sRep= radioDifficile.getText().toString();
        }
        return sRep;
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

//    public void addUser(){
//        // Create a new user with a first and last name
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
//    public void listeUser () {
//        db.collection("users")
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
//    }


}