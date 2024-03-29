package com.example.ic_hackathon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ic_hackathon.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    ActivityLoginBinding binding;
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
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            hideStatus();

        binding.buttoncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = binding.phoneEdittext.getText().toString().trim();

                if(!number.equals("")){

                    Intent intent = new Intent(LoginActivity.this,OtpActivity.class);
                    intent.putExtra("number","+91"+binding.phoneEdittext.getText());
                    startActivity(intent);
                }

                else {

                    Toast.makeText(LoginActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}