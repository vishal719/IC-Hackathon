package com.example.ic_hackathon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.ic_hackathon.Model.UserModel;
import com.example.ic_hackathon.databinding.ActivityUserDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserDetailsActivity extends AppCompatActivity {


    FirebaseDatabase database;
    ActivityUserDetailsBinding binding;

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void hideStatus(){
        /* To make the status bar transparent*/

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            hideStatus();

        database = FirebaseDatabase.getInstance();

        binding.buttoncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!binding.inputAge.getText().toString().trim().equals("") && !binding.inputBlood.getText().toString().trim().equals("") &&
                        !binding.inputHeight.getText().toString().trim().equals("") && !binding.inputWeight.getText().toString().trim().equals("")){

                    UserModel model = new UserModel();

                    database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).child("age").setValue(binding.inputAge.getText().toString().trim());
                    database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).child("blood").setValue(binding.inputBlood.getText().toString().trim());
                    database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).child("height").setValue(binding.inputHeight.getText().toString().trim());
                    database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).child("weight").setValue(binding.inputWeight.getText().toString().trim());

                    Intent intent = new Intent(UserDetailsActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();

                }
                else{



                }
            }
        });

    }
}