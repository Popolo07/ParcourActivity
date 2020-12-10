package com.example.parcouractivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parcouractivity.bean.User;
import com.example.parcouractivity.nosql.FireCallBack;
import com.example.parcouractivity.nosql.MyFireStore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {


    EditText edtPasswordConf;
    ImageView imgAvatar;
    EditText edtPasswordUser;
    EditText edtPseudoUser;
    EditText edtNomUser;
    EditText edtPrenomUser;
    EditText edtEmailUser;
    String sMail= new String();


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MyFireStore myFireStore = new MyFireStore(db);
//    private DocumentReference noteRef =  db.collection("user").document("Ma première note");
    private CollectionReference colUsers = db.collection("user");
    private Query queryMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        imgAvatar = (ImageView) findViewById(R.id.imgAvatar);
        edtPseudoUser = (EditText) findViewById(R.id.edtPseudoUser);
        edtNomUser = (EditText) findViewById(R.id.edtNomUser);
        edtPasswordUser = (EditText) findViewById(R.id.edtPasswordUser);
        edtPrenomUser = (EditText) findViewById(R.id.edtPrenomUser);
        edtPasswordConf = (EditText) findViewById(R.id.edtPasswordConf);
        edtEmailUser = (EditText) findViewById(R.id.edtEmailUser);
// initilisation de la base connexion au cloud firestore



    }
    public void onClick(View v) {
        String pass = new String(String.valueOf(edtPasswordUser.getText()));
        String conf = new String(String.valueOf(edtPasswordConf.getText()));
        String smail = new String(String.valueOf(edtEmailUser.getText()));

        User user = new User();
        Log.i("INFOPAUL","mess (" +edtPasswordUser.getText() + ") (" + edtPasswordConf.getText()+")");
        sendToast("mess" +edtPasswordUser.getText() + " " + edtPasswordConf.getText() );

        if (edtNomUser.length() == 0  ) {
            sendToast("Nom de l'utilisateur vide");
        }
        else if(edtPrenomUser.length() == 0) {
            sendToast("Prenom de l'utilisateur vide");
        }
        else if(edtEmailUser.length() == 0) {
            sendToast("Mail de l'utilisateur vide");
        }
        else if(edtPasswordUser.length() == 0) {
            sendToast("Password de l'utilisateur vide");
        }
        else if(edtPseudoUser.length() == 0) {
            sendToast("Pseudo de l'utilisateur vide");
        }
        else if(edtPasswordConf.length() == 0) {
            sendToast("Confirmation du Password de l'utilisateur vide");
        }
        else if(pass.equals(conf)) {
            Log.i("INFOPAUL", "on va lire avec ce mail (" + smail + ") ");
            myFireStore.readDataUser(new FireCallBack() {
                 @Override
                 public void onCallBack(User user) {
                     if (user.getId().equals("0")) {
                         Log.i("INFOPAUL", "smail en retour (" + smail + ") ");
                         user.setNom(String.valueOf(edtNomUser.getText()));
                         user.setPrenom(String.valueOf(edtPrenomUser.getText()));
                         user.setPseudo(String.valueOf(edtPseudoUser.getText()));
                         user.setEmail(String.valueOf(edtEmailUser.getText()));
                         user.setPassword(String.valueOf(edtPasswordUser.getText()));
                         user.setLienavatar("lien");
                         Log.i("INFOPAUL", "on va enregistrer avec ce mail (" + smail + ") ");
                         EnregistrerFireBase(user);

                     }
                     else {
                         sendToast("Votre mail existe déja dans la base ");
                         Log.i("INFOPAUL", "mail exist mess pas = (" + sMail + ") (" + conf + ")");
                     }

                 }
             }, smail);

        }
        else {
            sendToast("Votre mot de passe et le mot de passe confirmé sont différent");
            Log.i("INFOPAUL","mess pas = (" +pass + ") (" + conf+")");
        }
    }
    public void EnregistrerFireBase(User user ) {
        // Create a new user with a first and last name
        Map<String, Object> userfirebase = new HashMap<>();
        userfirebase.put("nom", user.getNom());
        userfirebase.put("prenom", user.getPrenom());
        userfirebase.put("pseudo", user.getPseudo());
        userfirebase.put("email", user.getEmail());
        userfirebase.put("password", user.getPassword());

// Add a new document with a generated ID
        db.collection("user")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("INFOPAUL", "DocumentSnapshot added with ID: " + documentReference.getId());
                        user.setId(documentReference.getId());
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        intent.putExtra("userid", user.getId());
                        intent.putExtra("pseudo", user.getPseudo());
                        intent.putExtra("lienavatar", user.getLienavatar());
                        intent.putExtra("nom", user.getNom());
                        intent.putExtra("prenom", user.getPrenom());
                        intent.putExtra("mail", user.getEmail());
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("INFOPAUL", "Error adding document", e);
                    }
                });

    }

    private void sendToast(String message ){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }

}