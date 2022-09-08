package com.example.ic_hackathon;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ic_hackathon.databinding.ActivityEmergencyDetailsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapView;


public class EmergencyDetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    MapView mapView;
    private double lng = 0.0;
    private double lat = 0.0;
    ActivityEmergencyDetailsBinding binding;
    Marker marker;
    int mark = 0;
    double latitude = 23.0707;
    double longitude = 80.09;


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
    public void hideStatus() {
        /* To make the status bar transparent*/

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmergencyDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            hideStatus();

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
        supportMapFragment.getMapAsync(this);


        String location = getIntent().getStringExtra("loc");
        Toast.makeText(EmergencyDetailsActivity.this, "loc " + location, Toast.LENGTH_SHORT).show();

/*
        if (!location.equals("")) {

            int i = location.indexOf(",");
            lat = Double.parseDouble(location.substring(0, i));
            lng = Double.parseDouble(location.substring(i + 1));
            Log.d("lat", lat + ", " + lng);

            LatLng loc = new LatLng(lat, lng);

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 14));


*/
/*            if (mark == 1) {
                marker.remove();
                marker = map.addMarker(new MarkerOptions().position(loc).title("Selected Location"));
            } else {
                mark = 1;
                marker = map.addMarker(new MarkerOptions().position(loc).title("Selected Location"));

            }*//*

        }
*/


        binding.emergName4.setText(getIntent().getStringExtra("name"));
        binding.emergNumber4.setText(getIntent().getStringExtra("number"));
        Glide.with(binding.imageThen.getContext()).load(getIntent().getStringExtra("image")).into(binding.imageThen);
        Glide.with(binding.emergImage4.getContext()).load(getIntent().getStringExtra("pic")).placeholder(R.drawable.ic_undraw_male_avatar_323b).into(binding.emergImage4);


    }


    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        LatLng location = new LatLng(latitude, longitude);
//        map.addMarker(new MarkerOptions().position(location).title("Maharastra"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 3));
    }
}