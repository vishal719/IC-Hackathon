package com.example.ic_hackathon.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ic_hackathon.Model.ContactsModels;
import com.example.ic_hackathon.Model.EmergencyModel;
import com.example.ic_hackathon.databinding.FragmentHomeBinding;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    int[] arr = new int[5];
    int i=0;
    int count = 0;
    int flag1=0,flag2=0,flag3=0,flag4=0;
    private String[] emj = new String[4];
    FirebaseDatabase database;
    CancelAlertFragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater);

        database  = FirebaseDatabase.getInstance();
        
        //setting text for different buttons
        emj[0] = "I had an accident!";
        emj[1] = "I have an injury!";
        emj[2] = "I am out of gas!";
        emj[3] = "My car broke down!";

        
        //setting actions toclick on different buttons
        binding.acciCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(flag1==0) {


                    if(count<4) {
                        arr[0]=1;
                        count++;
                        flag1++;
                        binding.acci.setVisibility(View.GONE);
                        binding.acciTick.setVisibility(View.VISIBLE);

                    }
                }
                else if(flag1==1) {
                    flag1--;
                    count--;
                    arr[0]=0;
                    binding.acci.setVisibility(View.VISIBLE);
                    binding.acciTick.setVisibility(View.GONE);

                }

            }

        });
        binding.injCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(flag2==0) {


                    if(count<4) {
                        arr[1]=1;
                        count++;
                        flag2++;
                        binding.inj.setVisibility(View.GONE);
                        binding.injTick.setVisibility(View.VISIBLE);

                    }
                }
                else if(flag2==1) {
                    flag2--;
                    count--;
                    arr[1]=0;
                    binding.inj.setVisibility(View.VISIBLE);
                    binding.injTick.setVisibility(View.GONE);

                }

            }

        });

        binding.gasCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(flag3==0) {


                    if(count<4) {
                        arr[2]=1;
                        count++;
                        flag3++;
                        binding.gas.setVisibility(View.GONE);
                        binding.gasTick.setVisibility(View.VISIBLE);

                    }
                }
                else if(flag3==1) {
                    flag3--;
                    count--;
                    arr[2]=0;
                    binding.gas.setVisibility(View.VISIBLE);
                    binding.gasTick.setVisibility(View.GONE);

                }

            }

        });

        binding.carCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(flag4==0) {

                    if(count<4) {
                        arr[3]=1;
                        count++;
                        flag4++;
                        binding.car.setVisibility(View.GONE);
                        binding.carTick.setVisibility(View.VISIBLE);

                    }
                }
                else if(flag4==1) {
                    flag4--;
                    count--;
                    arr[3]=0;
                    binding.car.setVisibility(View.VISIBLE);
                    binding.carTick.setVisibility(View.GONE);

                }

            }

        });

        binding.emerjencyClick.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {



                int counter =0;
                for(int a=0;a<5;a++)
                {
                    Log.d("array: ", "arr of :"+a+" is "+String.valueOf(arr[a]));
                    if(arr[a]==1){
                        counter = a;
                        break;
                    }
                }
                Toast.makeText(getContext(),""+ emj[counter], Toast.LENGTH_SHORT).show();
                int finalCounter = counter;

                fragment = new CancelAlertFragment();
                Bundle bundle = new Bundle();
                bundle.putString("msg",emj[finalCounter] );
                fragment.setArguments(bundle);
                fragment.show(getParentFragmentManager(), fragment.getTag());



                return true;
            }
        });


        return binding.getRoot();
    }




}


