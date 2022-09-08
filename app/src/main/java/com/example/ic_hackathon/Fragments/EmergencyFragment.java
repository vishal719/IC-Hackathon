package com.example.ic_hackathon.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ic_hackathon.Adapter.EmergencyContactAdapter;
import com.example.ic_hackathon.Adapter.EmergencyNearbyAdapter;
import com.example.ic_hackathon.Model.EmergencyModel;
import com.example.ic_hackathon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmergencyFragment extends Fragment {

    ArrayList<EmergencyModel> list= new ArrayList<>();
    ArrayList<EmergencyModel> list2= new ArrayList<>();
    EmergencyContactAdapter adapter1;
    EmergencyNearbyAdapter adapter2;
    FirebaseDatabase database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emergency, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.notificationToday);
        RecyclerView recyclerView2 = view.findViewById(R.id.notificationEarlier);
        adapter1 = new EmergencyContactAdapter(list);

        database = FirebaseDatabase.getInstance();


         adapter1.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter1);

        database.getReference().child("Contacts_emergency").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().toString())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    for (DataSnapshot snapshot1 : snapshot.getChildren()){

                        EmergencyModel model = snapshot1.getValue(EmergencyModel.class);

                        list.add(model);
                        adapter1.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter2 = new EmergencyNearbyAdapter(list2);


        adapter2.notifyDataSetChanged();

        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView2.setAdapter(adapter2);

        return view;
    }
}