package com.example.ic_hackathon.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ic_hackathon.AddContactsActivity;
import com.example.ic_hackathon.Model.ContactsModels;
import com.example.ic_hackathon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddContactsAdapter extends RecyclerView.Adapter<AddContactsAdapter.viewHolder>{

    ArrayList<ContactsModels> list;
    ArrayList<ContactsModels> Flist = new ArrayList<>();
    FirebaseDatabase database;

    public AddContactsAdapter(ArrayList<ContactsModels> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_contact_row_view, parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        ContactsModels models = list.get(position);
        holder.number.setText(models.getNumber());
        holder.name.setText(models.getName());
        database = FirebaseDatabase.getInstance();

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(holder.checkBox.isChecked()){

                    Flist.add(models);
                }
            }
        });

        Glide.with(holder.pic.getContext()).load(models.getPic()).placeholder(R.drawable.ic_undraw_male_avatar_323b).into(holder.pic);

        AddContactsActivity.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database.getReference().child("Contacts").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(int i = 0; i< Flist.size(); i++){

                            snapshot.getRef().push().setValue(Flist.get(i));

                        }

                        Toast.makeText(holder.itemView.getContext(), "Done", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        TextView name, number;
        ImageView pic;
        CheckBox checkBox;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.add_contact_name);
            pic = itemView.findViewById(R.id.add_contact_image);
            number = itemView.findViewById(R.id.add_contact_number);
            checkBox = itemView.findViewById(R.id.add_box);

        }
    }
}
