package com.example.ic_hackathon.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ic_hackathon.Adapter.ContactsAdapter;
import com.example.ic_hackathon.AddContactsActivity;
import com.example.ic_hackathon.Model.ContactsModels;
import com.example.ic_hackathon.R;
import com.example.ic_hackathon.databinding.FragmentContactsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ContactsFragment extends Fragment {

    FragmentContactsBinding binding;
    ContactsAdapter adapter;
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContactsBinding.inflate(inflater, container, false);

        ArrayList<ContactsModels> list = new ArrayList<>();
        database = FirebaseDatabase.getInstance();

        database.getReference().child("Contacts").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    for (DataSnapshot snapshot1 : snapshot.getChildren()){

                        ContactsModels models = snapshot1.getValue(ContactsModels.class);

                        list.add(models);

                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.contactsList.setLayoutManager(manager);
        adapter = new ContactsAdapter(list);
        binding.contactsList.setAdapter(adapter);

        binding.addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), AddContactsActivity.class));
            }
        });

        return binding.getRoot();
    }
}