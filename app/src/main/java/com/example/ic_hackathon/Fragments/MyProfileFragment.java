package com.example.ic_hackathon.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.ic_hackathon.LoginActivity;
import com.example.ic_hackathon.Model.UserModel;
import com.example.ic_hackathon.R;
import com.example.ic_hackathon.databinding.FragmentContactsBinding;
import com.example.ic_hackathon.databinding.FragmentMyProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfileFragment extends Fragment {

    FragmentMyProfileBinding binding;
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyProfileBinding.inflate(inflater);

        database = FirebaseDatabase.getInstance();
        binding.profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();
            }
        });

        database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    UserModel model = snapshot.getValue(UserModel.class);
                    binding.userName.setText(model.getUsername());
                    binding.heightVal.setText(model.getHeight());
                    binding.weightVal.setText(model.getWeight());
                    binding.textView2.setText(model.getAge());
                    binding.bloodSign.setText(model.getBlood().substring(model.getBlood().length()-1));
                    binding.bloodGrpVal.setText(model.getBlood().substring(0, model.getBlood().length()-1));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Glide.with(binding.profileImageView.getContext()).load(R.drawable.ic_undraw_male_avatar_323b).placeholder(R.drawable.ic_undraw_male_avatar_323b)
                .into(binding.profileImageView);

        return binding.getRoot();
    }
}