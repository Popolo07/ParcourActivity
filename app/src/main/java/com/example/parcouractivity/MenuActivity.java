package com.example.parcouractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.parcouractivity.bean.User;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnScenario;
    Button btnExercice;
    Button btnParcours;
    Button bntProfil;
    Button btnMesDocuments;
    User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);


        Bundle intent = getIntent().getExtras();
        user.setId(intent.getString("userid"));
        user.setPseudo(intent.getString("pseudo"));
        user.setNom(intent.getString("nom"));
        user.setPrenom(intent.getString("prenom"));
        user.setLienavatar(intent.getString("lienvavatar"));
        user.setEmail(intent.getString("email"));

        btnScenario = (Button) findViewById(R.id.btnScenario);
        btnExercice = (Button) findViewById(R.id.btnExercice);
        btnParcours = (Button) findViewById(R.id.btnParcours);
        bntProfil = (Button) findViewById(R.id.bntProfil);
        btnMesDocuments = (Button) findViewById(R.id.btnMesDocuments);

        btnScenario.setOnClickListener(this);
        btnExercice.setOnClickListener(this);
        btnParcours.setOnClickListener(this);
        bntProfil.setOnClickListener(this);
        btnMesDocuments.setOnClickListener(this);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnScenario:

                sendToast("");
                break;
            case R.id.btnExercice:
                sendToast("");
                break;
            case R.id.btnParcours:
                sendToast("");


                intent = new Intent(this,ParcourActivity.class);

                intent.putExtra("userid", user.getId());
                intent.putExtra("pseudo", user.getPseudo());
                intent.putExtra("lienavatar", user.getLienavatar());
                intent.putExtra("nom", user.getNom());
                intent.putExtra("prenom", user.getPrenom());
                intent.putExtra("email", user.getEmail());
                startActivity(intent);
                break;
            case R.id.bntProfil:
                sendToast("");
                break;

            case R.id.btnMesDocuments:

  //              intent = new Intent(this,MenuActivity.class);
  //              startActivity(intent);
                break;
        }

    }


    private void sendToast(String s) {
    }
}