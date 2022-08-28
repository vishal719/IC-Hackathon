package com.example.ic_hackathon.Fragments;

import android.content.Intent;
import android.os.Bundle;

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

import java.util.ArrayList;


public class ContactsFragment extends Fragment {

    FragmentContactsBinding binding;
    ContactsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContactsBinding.inflate(inflater, container, false);

        ArrayList<ContactsModels> list = new ArrayList<>();
        list.add(new ContactsModels(R.drawable.ic_undraw_male_avatar_323b, "Garry", "+91236547998"));
        list.add(new ContactsModels(R.drawable.ic_undraw_male_avatar_323b, "Garry", "+91236547998"));
        list.add(new ContactsModels(R.drawable.ic_undraw_male_avatar_323b, "Garry", "+91236547998"));
        list.add(new ContactsModels(R.drawable.ic_undraw_male_avatar_323b, "Garry", "+91236547998"));
        list.add(new ContactsModels(R.drawable.ic_undraw_male_avatar_323b, "Garry", "+91236547998"));
        list.add(new ContactsModels(R.drawable.ic_undraw_male_avatar_323b, "Garry", "+91236547998"));


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