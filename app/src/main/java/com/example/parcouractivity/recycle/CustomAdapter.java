package com.example.parcouractivity.recycle;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcouractivity.R;
import com.example.parcouractivity.bean.ListeParcours;
import com.google.rpc.context.AttributeContext;

public class CustomAdapter extends RecyclerView.Adapter<ListeViewHolder> {

    private ListeParcours[] localDataSet;
    @NonNull
    @Override
    public ListeViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_parcours, parent, false);

        return new ListeViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ListeViewHolder holder, int position) {
        holder.txtDistance.setText(localDataSet[position].getTxtDistance());
        holder.txtLevel.setText(localDataSet[position].getTxtLevel());
        holder.txtNomParcours.setText(localDataSet[position].getTxtDistance());
        Resources res = null;
//        holder.imgAvatar.setImageDrawable(res.getDrawable(localDataSet[position].getImgavatar());
//        holder.imgLevel.setImageResource(res.getDrawable(localDataSet[position].getImgLevel());

    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }


    public CustomAdapter(ListeParcours[] dataSet) {
        localDataSet = dataSet;
    }
}
