package com.example.ic_hackathon.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ic_hackathon.LoginActivity;
import com.example.ic_hackathon.R;
import com.example.ic_hackathon.databinding.FragmentContactsBinding;
import com.example.ic_hackathon.databinding.FragmentMyProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MyProfileFragment extends Fragment {

    FragmentMyProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyProfileBinding.inflate(inflater);

        binding.profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();
            }
        });

        return binding.getRoot();
    }
}