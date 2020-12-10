package com.example.parcouractivity.recycle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcouractivity.R;
import com.example.parcouractivity.bean.ListeParcours;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

public class ListeViewHolder extends RecyclerView.ViewHolder {
    CustomAdapter mAdapter;
    ImageView imgAvatar;
    ImageView imgLevel;
    TextView txtDistance;
    TextView txtLevel;
    TextView txtNomParcours;
    ListeParcours listeParcours;
    FirestoreRecyclerAdapter mFirestoreRecyclerAdapter;

    public ListeViewHolder(@NonNull  View itemView, CustomAdapter adapter) {

        super(itemView);
        imgAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
        imgLevel = (ImageView) itemView.findViewById(R.id.iv_avatar);
        txtLevel = (TextView) itemView.findViewById(R.id.tv_level);
        txtDistance = (TextView) itemView.findViewById(R.id.tv_nbkilometre);
        txtNomParcours = (TextView) itemView.findViewById(R.id.tv_nomparcour);
        listeParcours = new ListeParcours();
        listeParcours.setTxtNomParcour((String) txtNomParcours.getText());
        listeParcours.setTxtLevel((String) txtLevel.getText());
        listeParcours.setTxtDistance((String) txtDistance.getText());
        listeParcours.setImgavatar((String) imgAvatar.getResources().toString());
        listeParcours.setImgLevel((String) imgLevel.getResources().toString());
        this.mAdapter = adapter;
    }
    public ListeViewHolder(@NonNull  View itemView ) {

        super(itemView);
        imgAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
        imgLevel = (ImageView) itemView.findViewById(R.id.iv_avatar);
        txtLevel = (TextView) itemView.findViewById(R.id.tv_level);
        txtDistance = (TextView) itemView.findViewById(R.id.tv_nbkilometre);
        txtNomParcours = (TextView) itemView.findViewById(R.id.tv_nomparcour);
        listeParcours = new ListeParcours();
        listeParcours.setTxtNomParcour((String) txtNomParcours.getText());
        listeParcours.setTxtLevel((String) txtLevel.getText());
        listeParcours.setTxtDistance((String) txtDistance.getText());
        listeParcours.setImgavatar((String) imgAvatar.getResources().toString());
        listeParcours.setImgLevel((String) imgLevel.getResources().toString());
    }
}
