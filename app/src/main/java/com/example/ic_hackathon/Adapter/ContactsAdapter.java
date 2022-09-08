package com.example.ic_hackathon.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ic_hackathon.Model.ContactsModels;
import com.example.ic_hackathon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.vieholder>{

    ArrayList<ContactsModels> list;
    FirebaseDatabase database;

    public ContactsAdapter(ArrayList<ContactsModels> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public vieholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row_view, parent, false);
        return new vieholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vieholder holder, int position) {

        ContactsModels models = list.get(position);

        Glide.with(holder.pic.getContext()).load(models.getPic()).placeholder(R.drawable.ic_undraw_male_avatar_323b).into(holder.pic);
        holder.name.setText(models.getName());
        holder.number.setText(models.getNumber());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", models.getNumber(), null));
                view.getContext().startActivity(intent);
            }
        });

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopup(v, holder ,models);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class vieholder extends RecyclerView.ViewHolder{

        TextView name, number;
        ImageView pic, del;
        ConstraintLayout item;

        public vieholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.contact_name);
            number = itemView.findViewById(R.id.contact_number);
            pic = itemView.findViewById(R.id.contact_image);
            item = itemView.findViewById(R.id.call_conatiner);
            del = itemView.findViewById(R.id.call_menu);
        }
    }

    @SuppressLint("RestrictedApi")
    public void showPopup(View v, vieholder holder, ContactsModels models) {
        @SuppressLint("RestrictedApi") MenuBuilder menuBuilder = new MenuBuilder(v.getContext());
        MenuInflater inflater = new MenuInflater(v.getContext());
        inflater.inflate(R.menu.navigation_draawer, menuBuilder);
        @SuppressLint("RestrictedApi") MenuPopupHelper optionsMenu = new MenuPopupHelper(v.getContext(), menuBuilder, holder.del);
        database = FirebaseDatabase.getInstance("https://ic-hackathon-bb7b1-default-rtdb.asia-southeast1.firebasedatabase.app/");
        menuBuilder.setCallback(new MenuBuilder.Callback() {
            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.delete:

                        Toast.makeText(v.getContext(), models.getNumber().trim(), Toast.LENGTH_SHORT).show();
                        database.getReference().child("Contacts").child(FirebaseAuth.getInstance().getUid()).child(models.getNumber().trim()).removeValue();

                        return true;

                    default:
                        return false;
                }
            }

            @Override
            public void onMenuModeChange(MenuBuilder menu) {}
        });

        optionsMenu.setGravity(Gravity.END);
        optionsMenu.show();
    }
}
