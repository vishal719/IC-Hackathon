package com.example.ic_hackathon;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ic_hackathon.Model.UserModel;
import com.example.ic_hackathon.databinding.ActivityInputDetailsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InputDetailsActivity extends AppCompatActivity {

    ActivityInputDetailsBinding binding;
    FirebaseDatabase database;

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
        binding = ActivityInputDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            hideStatus();

        database = FirebaseDatabase.getInstance();

        binding.SetProfileDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    if(!binding.inputName.getText().toString().equals("")){

                        database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                SharedPreferences preferences = getSharedPreferences("pictureuri", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                String photouriString = preferences.getString(FirebaseAuth.getInstance().getUid(), "");

                                uploadToFirebase(Uri.parse(photouriString));

                                String curr = new SimpleDateFormat("dd  MMM  yyyy").format(new Date());

                                UserModel model = new UserModel(FirebaseAuth.getInstance().getUid(),binding.inputName.getText().toString(),photouriString,"");
                                snapshot.getRef().setValue(model);

                                database.getReference().child("Incomplete_login").child(FirebaseAuth.getInstance().getUid()).removeValue();

                                Intent intent = new Intent(InputDetailsActivity.this, UserDetailsActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else{

                        Toast.makeText(InputDetailsActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    }


            }
        });


    }

    public void uploadToFirebase(Uri uri){
        StorageReference sref = FirebaseStorage.getInstance().getReference();
        StorageReference fileRef = sref.child("Images").child((FirebaseAuth.getInstance().getUid())).child("pic_2");
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        database.getReference().child("User_details").child(currentUser.getUid()).child("photo").setValue(uri.toString());
                        Toast.makeText(InputDetailsActivity.this,"Profile picture updated",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InputDetailsActivity.this,"Uploading Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

}