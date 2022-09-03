package com.example.ic_hackathon;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class InputDetailsActivity extends AppCompatActivity {

    ActivityInputDetailsBinding binding;
    FirebaseDatabase database;
    Uri photouri;

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

        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);

        database = FirebaseDatabase.getInstance();

        binding.myPhotoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                ImagePicker.with(InputDetailsActivity.this)
                        .crop(1,1)//Crop image(Optional), Check Customization for more option
                        .compress(100)		//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(480,480)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .createIntent(new Function1<Intent, Unit>() {
                            @Override
                            public Unit invoke(Intent Intent) {

                                startForProfileImageResult.launch(Intent );
                                return null;
                            }
                        });
*/
            }
        });

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == 111){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length>0){

            }
            else
                Toast.makeText(this, "Grant Storage Permissions for full functionality", Toast.LENGTH_SHORT).show();
        }
    }
    ActivityResultLauncher startForProfileImageResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {

                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent intent = result.getData();
                        if (intent != null) {
                            Uri uri2 = intent.getData();
                            binding.myPhotoLoad.setImageURI(uri2);
                            photouri = uri2;
                            binding.myPhoto.setVisibility(View.INVISIBLE);
                            SharedPreferences preferences3 = getSharedPreferences("pictureuri", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor3 = preferences3.edit();
                            editor3.remove(FirebaseAuth.getInstance().getUid());
                            editor3.putString(FirebaseAuth.getInstance().getUid(), String.valueOf(photouri));
                            editor3.apply();

                        }
                    }
                }
            });


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