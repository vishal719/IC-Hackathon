package com.example.ic_hackathon.Adapter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ic_hackathon.EmergencyDetailsActivity;
import com.example.ic_hackathon.Model.EmergencyModel;
import com.example.ic_hackathon.R;
import com.google.android.gms.maps.MapFragment;

import java.util.ArrayList;

public class EmergencyContactAdapter extends RecyclerView.Adapter<EmergencyContactAdapter.emergencyContactViewHolder> {

    ArrayList<EmergencyModel> list;

    public EmergencyContactAdapter(ArrayList<EmergencyModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public emergencyContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emergency_row_view, parent, false);
        return new emergencyContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull emergencyContactViewHolder holder, int position) {

        EmergencyModel model = list.get(position);

        holder.number.setText(model.getNumber());
        holder.name.setText(model.getName());
        Glide.with(holder.pic.getContext()).load(model.getPic()).placeholder(R.drawable.ic_undraw_male_avatar_323b).into(holder.pic);


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(holder.itemView.getContext(), EmergencyDetailsActivity.class);
                intent.putExtra("number", model.getNumber());
                intent.putExtra("name", model.getName());
                intent.putExtra("loc", model.getLocation());
                intent.putExtra("pic", model.getPic());
                intent.putExtra("image", model.getImage_then());
                intent.putExtra("msg", model.getMsg1());
                view.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class emergencyContactViewHolder extends RecyclerView.ViewHolder{

        TextView name, number;
        ImageView pic;
        ConstraintLayout layout;

        public emergencyContactViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.emerg_name);
            number = itemView.findViewById(R.id.emerg_number);
            pic = itemView.findViewById(R.id.emerg_image);
            layout = itemView.findViewById(R.id.call_conatiner);
        }
    }
}
