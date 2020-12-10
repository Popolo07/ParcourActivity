package com.example.parcouractivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.parcouractivity.bean.Parcoursss;
import com.example.parcouractivity.bean.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreerParcourActivity extends FragmentActivity {
    FloatingActionButton flbtn;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    Parcoursss parcours = new Parcoursss();
    User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_parcour);

        Bundle intent = getIntent().getExtras();
        user.setId(intent.getString("userid"));
        user.setPassword(intent.getString("pseudo"));
        user.setNom(intent.getString("nom"));
        user.setPrenom(intent.getString("prenom"));
        user.setLienavatar(intent.getString("lienvavatar"));
        user.setEmail(intent.getString("email"));
        parcours.setId(intent.getString("parcoursid"));
        parcours.setNom(intent.getString("parcoursnom"));
        parcours.setDescription(intent.getString("parcoursdes"));


        fragmentManager = this.getSupportFragmentManager();
        MapFragment mf = MapFragment.newInstance(intent);

        SpeedFragment sf = SpeedFragment.newInstance(intent);
        openFragment(mf,sf,"frag");
    }

    public void openFragment (Fragment mapFrag,Fragment speedFrag, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.header_holder, speedFrag);
        transaction.replace(R.id.body_holder, mapFrag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }
}