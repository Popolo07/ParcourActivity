package com.example.parcouractivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parcouractivity.bean.User;
import com.example.parcouractivity.nosql.FireCallBack;
import com.example.parcouractivity.nosql.MyFireStore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


//import com.google.api.ApiFuture;


public class EnregisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTxtmailAddress;
    EditText editTxtNumberPassword;
    Button btnSinscrire;
    Button btnConnexion;
    Button btnMotDePassOublier;
    Button btnGmail;
    Button btnFacebook;
    String sMail, sPass;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MyFireStore myFireStore = new MyFireStore(db);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregister);

        editTxtmailAddress = (EditText)findViewById(R.id.editTxtmailAddress);
        editTxtNumberPassword = (EditText)findViewById(R.id.editTxtNumberPassword);
        btnSinscrire = (Button)findViewById(R.id.btnSinscrire);
        btnConnexion = (Button) findViewById(R.id.btnConnexion);
        btnMotDePassOublier = (Button) findViewById(R.id.btnMotDePassOublier);
        btnGmail = (Button)findViewById(R.id.btnGmail);
        btnFacebook = (Button)findViewById(R.id.btnFacebook);


        btnConnexion.setOnClickListener(this);
        btnMotDePassOublier.setOnClickListener(this);
        btnGmail.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);
        btnSinscrire.setOnClickListener(this);

    }
    public void onClick(View v) {
        boolean err = false;
        List<String> sMess= new ArrayList<String>();

//        Log.i("INFOPAUL ", "je click " +v.getTag().toString() + " mail base "+  sMail + "pass Saisie " +sMess.size());

        if (editTxtmailAddress.length() ==0 ) {
            sMess.add("\nSaisie du mail obligatoire ");
        }
        if (editTxtNumberPassword.length() ==0 ){
            sMess.add( "\nSaisie du password obligatoire");
        }

        String sTmpMail = String.valueOf(editTxtmailAddress.getText());
        String sTmpPass = String.valueOf(editTxtNumberPassword.getText());
        switch (v.getTag().toString()) {
            case "connexion":
                    if (sMess.size()==0 ) {
                        Log.i("INFOPAUL ", "connexion maile Saisie " + sTmpMail +  "pass Saisie " + sTmpPass );
                        myFireStore.readCtlConnect(new FireCallBack() {
                            @Override
                            public void onCallBack(User user) {
                                Log.i("INFOPAUL ", "mail retour " + user.getEmail() + "pass retour " + user.getPassword());
                                if ((user.getEmail().equals(sTmpMail)) && (user.getPassword().equals(sTmpPass))) {

                                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                    intent.putExtra("userid", user.getId());
                                    intent.putExtra("pseudo", user.getPseudo());
                                    intent.putExtra("lienavatar", user.getLienavatar());
                                    intent.putExtra("nom", user.getNom());
                                    intent.putExtra("prenom", user.getPrenom());
                                    intent.putExtra("email", user.getEmail());
                                    startActivity(intent);
                                } else
                                    sMess.add("\nVotre mail et votre mot de passe sont intouvable");
                                Log.i("INFOPAUL ", "retour et message de fin  " + sMess);
                                sendToast(sMess);

                            }
                        }, sTmpMail);
                    }
                    else {
                        sendToast(sMess);
                    }
                    break;

            case "passeoublier":
                Log.i("INFOPAUL ", "passeoublier") ;
                sendToast("Un mail vous à été envoyer");
                break;
            case "gmail":
                Log.i("INFOPAUL ", "gmail") ;
                sendToast("Se connecter avec votre compte Gmail");
                break;
            case "facebook":
                Log.i("INFOPAUL ", "facebook") ;
                sendToast("Se connecter avec votre compte Facebook");
                break;
            case "inscription":
                Log.i("INFOPAUL ", "inscription") ;
                sendToast("Vous allez etre diriger vers la page d'inscription");


                Intent intent = new Intent(this, UserActivity.class);
                startActivity(intent);
                break;
        }
        Log.i("INFOPAUL ", "il se passe rien "  +sMess);
        sendToast(sMess);
    }

    private void startActivities(Intent intent) {
    }

    private void sendToast(String message ){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }
    private void sendToast(List<String> mess ){
        String message = new String();
        for (String s:mess) {
            message+=s;
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }
//
//    private void readData(FireCallBack fireCallBack,String email){
//        sMail="";
//        sPass="";
//        User callBackUser = new User();
//        db.collection("user")
//                .whereEqualTo("email", email)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                callBackUser.setId(document.getId());
//                                callBackUser.setEmail((String) document.getData().get("email"));
//                                callBackUser.setPassword((String) document.getData().get("password"));
//                                callBackUser.setLienavatar((String) document.getData().get("lienavatar"));
//                                callBackUser.setPassword((String) document.getData().get("nom"));
//                                callBackUser.setPassword((String) document.getData().get("prenom"));
//                                callBackUser.setPseudo((String) document.getData().get("pseudo"));
//                            }
//                            fireCallBack .onCallBack(callBackUser);
//                        }
//                        else {
//                            sMail = "";
//                            sPass = "";
//                        }
//                    }
//
//                });
//
//    }
//    private interface FireCallBack {
//        void onCallBack(User user);
//    }
}
