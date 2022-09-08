package com.example.ic_hackathon.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ic_hackathon.Model.EmergencyModel;
import com.example.ic_hackathon.R;

import java.util.ArrayList;

public class EmergencyNearbyAdapter extends RecyclerView.Adapter<EmergencyNearbyAdapter.emergencyNearbyViewHolder> {

    ArrayList<EmergencyModel> list;

    public EmergencyNearbyAdapter(ArrayList<EmergencyModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public emergencyNearbyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emergency_row_view2, parent, false);
        return new emergencyNearbyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull emergencyNearbyViewHolder holder, int position) {

        EmergencyModel model = list.get(position);
        holder.number.setText(model.getNumber());
        holder.name.setText(model.getName());
        Glide.with(holder.pic.getContext()).load(model.getPic()).placeholder(R.drawable.ic_undraw_male_avatar_323b).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class emergencyNearbyViewHolder extends RecyclerView.ViewHolder{

        TextView name, number;
        ImageView pic;
        CardView mic;

        public emergencyNearbyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.emerg_name2);
            number = itemView.findViewById(R.id.emerg_number2);
            pic = itemView.findViewById(R.id.emerg_image2);
            mic = itemView.findViewById(R.id.mic2);
        }
    }

}
