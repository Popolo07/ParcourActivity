package com.example.parcouractivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ValiderFragment extends Fragment {
    Button btnValider;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    public ValiderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_valider, container, false);
        btnValider =view.findViewById(R.id.btnValider);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retourListeTrajet(0);
            }
        });

        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void retourListeTrajet (long id) {
        Intent intent = new Intent(getActivity() ,ParcourActivity.class);
        intent.putExtra("trajet",id);
        startActivity (intent);
    }

}