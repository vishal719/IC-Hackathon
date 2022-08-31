package com.example.ic_hackathon.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ic_hackathon.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    int[] arr = new int[5];
    int i=0;
    int count = 0;
    int flag1=0,flag2=0,flag3=0,flag4=0;
    private String[] emj = new String[12];
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater);

        database  = FirebaseDatabase.getInstance();
        emj[0] = "accident";
        emj[1] = "injury";
        emj[2] = "gas";
        emj[3] = "car";

        binding.acciCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                for(int a=0;a<10;a++)
//                {
//                    if(arr[a]==1)
//                        count++;
//                }

                if(flag1==0) {
//                        Toast.makeText(ChooseSportsActivity.this, "count="+count, Toast.LENGTH_SHORT).show();


                    if(count<4) {
                        arr[1]=1;
                        count++;
                        flag1++;
                        binding.acci.setVisibility(View.GONE);
                        binding.acciTick.setVisibility(View.VISIBLE);

                    }
                }
                else if(flag1==1) {
                    flag1--;
                    count--;
                    arr[1]=0;
                    binding.acci.setVisibility(View.VISIBLE);
                    binding.acciTick.setVisibility(View.GONE);
//                        count=0;

                }

            }

        });
        binding.injCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                for(int a=0;a<10;a++)
//                {
//                    if(arr[a]==1)
//                        count++;
//                }

                if(flag2==0) {
//                        Toast.makeText(ChooseSportsActivity.this, "count="+count, Toast.LENGTH_SHORT).show();


                    if(count<4) {
                        arr[2]=1;
                        count++;
                        flag2++;
                        binding.inj.setVisibility(View.GONE);
                        binding.injTick.setVisibility(View.VISIBLE);

                    }
                }
                else if(flag2==1) {
                    flag2--;
                    count--;
                    arr[2]=0;
                    binding.inj.setVisibility(View.VISIBLE);
                    binding.injTick.setVisibility(View.GONE);
//                        count=0;

                }

            }

        });

        binding.gasCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                for(int a=0;a<10;a++)
//                {
//                    if(arr[a]==1)
//                        count++;
//                }

                if(flag3==0) {
//                        Toast.makeText(ChooseSportsActivity.this, "count="+count, Toast.LENGTH_SHORT).show();


                    if(count<4) {
                        arr[3]=1;
                        count++;
                        flag3++;
                        binding.gas.setVisibility(View.GONE);
                        binding.gasTick.setVisibility(View.VISIBLE);

                    }
                }
                else if(flag3==1) {
                    flag3--;
                    count--;
                    arr[3]=0;
                    binding.gas.setVisibility(View.VISIBLE);
                    binding.gasTick.setVisibility(View.GONE);
//                        count=0;

                }

            }

        });

        binding.carCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                for(int a=0;a<10;a++)
//                {
//                    if(arr[a]==1)
//                        count++;
//                }

                if(flag4==0) {
//                        Toast.makeText(ChooseSportsActivity.this, "count="+count, Toast.LENGTH_SHORT).show();


                    if(count<4) {
                        arr[4]=1;
                        count++;
                        flag4++;
                        binding.car.setVisibility(View.GONE);
                        binding.carTick.setVisibility(View.VISIBLE);

                    }
                }
                else if(flag4==1) {
                    flag4--;
                    count--;
                    arr[4]=0;
                    binding.car.setVisibility(View.VISIBLE);
                    binding.carTick.setVisibility(View.GONE);
//                        count=0;

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
                    if(arr[a]==1)
                        counter++;
                }
                if(counter ==5){

                    ArrayList<Integer> pos = new ArrayList<>();

                    for(int i = 1; i<=4; i++){

                        if(arr[i] == 1){
                            pos.add(i-1);
                        }
                    }


                    /*database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).child("sports_1")
                            .setValue(emj[pos.get(0)]);
                    database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).child("sports_2")
                            .setValue(emj[pos.get(1)]);
                    database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).child("sports_3")
                            .setValue(emj[pos.get(2)]);
                    database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).child("sports_4")
                            .setValue(emj[pos.get(3)]);
                    database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).child("sports_5")
                            .setValue(emj[pos.get(4)]);*/

                    Log.d( "TAG", emj[pos.get(0)]+ " "+ emj[pos.get(1)]+" "+emj[pos.get(2)]+" "+emj[pos.get(3)]
                            +" "+emj[pos.get(4)]);
/*

                    else if (pos.size() == 3){
                        database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).child("sports_1")
                                .setValue(sports[pos.get(0)]);
                        database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).child("sports_2")
                                .setValue(sports[pos.get(1)]);
                        database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).child("sports_2")
                                .setValue(sports[pos.get(2)]);
                    }

                    else if (pos.size() == 2){
                        database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).child("sports_1")
                                .setValue(sports[pos.get(0)]);
                        database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).child("sports_2")
                                .setValue(sports[pos.get(1)]);
                    }

                    else if (pos.size() == 1){
                        database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).child("sports_1")
                                .setValue(sports[pos.get(0)]);

                    }*/
                   /* database.getReference().child("Incomplete_login").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();

                    Intent intent = new Intent(ChooseSportsActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();*/
                }
                else{

                    Toast.makeText(getContext(), "Choose 5 sports", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });


        return binding.getRoot();
    }
}


