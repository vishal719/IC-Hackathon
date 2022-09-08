package com.example.ic_hackathon.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ic_hackathon.Model.ContactsModels;
import com.example.ic_hackathon.Model.EmergencyModel;
import com.example.ic_hackathon.Model.UserModel;
import com.example.ic_hackathon.R;
import com.example.ic_hackathon.databinding.FragmentCancelAlertBinding;
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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CancelAlertFragment extends BottomSheetDialogFragment {

    FragmentCancelAlertBinding binding;
    private LocationRequest locationRequest;
    FirebaseDatabase database;
    RequestQueue requestQueue;
    private String url = "https://fcm.googleapis.com/fcm/send";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCancelAlertBinding.inflate(inflater);

        Bundle bundle = getArguments();
        database  = FirebaseDatabase.getInstance();

        requestQueue = Volley.newRequestQueue(getContext());

        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.counter.setText(""+millisUntilFinished / 1000);


            }

            public void onFinish() {
                database.getReference().child("Contacts").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){


                            locationRequest = LocationRequest.create();
                            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                            locationRequest.setInterval(5000);
                            locationRequest.setFastestInterval(2000);
//                            getCurrentLocation(snapshot, bundle.getString("msg"));

                            //////////////////////////////////////////////////////////////

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                                    if (isGPSEnabled()) {

                                        LocationServices.getFusedLocationProviderClient(getContext())
                                                .requestLocationUpdates(locationRequest, new LocationCallback() {
                                                    @Override
                                                    public void onLocationResult(@NonNull LocationResult locationResult) {
                                                        super.onLocationResult(locationResult);

                                                        LocationServices.getFusedLocationProviderClient(getContext())
                                                                .removeLocationUpdates(this);

                                                        if (locationResult != null && locationResult.getLocations().size() >0){

                                                            int index = locationResult.getLocations().size() - 1;
                                                            double latitude = locationResult.getLocations().get(index).getLatitude();
                                                            double longitude = locationResult.getLocations().get(index).getLongitude();


                                                            String location = (latitude +","+ longitude);
                                                            database.getReference().child("Contacts_emergency").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                                                    for (DataSnapshot snapshot2: snapshot.getChildren()){

                                                                        ContactsModels models = snapshot2.getValue(ContactsModels.class);
                                                                        EmergencyModel emergencyModel = new EmergencyModel("Anthoony",FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()
                                                                                ,"",bundle.getString("msg"),"", "","",location,"fafasf");
                                                                        snapshot1.getRef().child(models.getNumber()).push().setValue(emergencyModel);


                                                                    }

                                                                    dismiss();

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });


                                                        }
                                                    }

                                                }, Looper.getMainLooper());

                                    } else {
                                        turnOnGPS();
                                    }

                                } else {
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                                }
                            }

                            else{

                                if (isGPSEnabled()) {

                                    LocationServices.getFusedLocationProviderClient(getContext())
                                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                                @Override
                                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                                    super.onLocationResult(locationResult);

                                                    LocationServices.getFusedLocationProviderClient(getContext())
                                                            .removeLocationUpdates(this);

                                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                                        int index = locationResult.getLocations().size() - 1;
                                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                                        double longitude = locationResult.getLocations().get(index).getLongitude();


                                                        String location = (latitude +","+ longitude);
                                                        database.getReference().child("Contacts_emergency").addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                                                for (DataSnapshot snapshot2: snapshot.getChildren()){

                                                                    ContactsModels models = snapshot2.getValue(ContactsModels.class);
                                                                    EmergencyModel emergencyModel = new EmergencyModel("Anthoony",models.getNumber(),"",bundle.getString("msg"),
                                                                            "", "","",location,"fafasf");
                                                                    snapshot1.getRef().child(models.getNumber()).push().setValue(emergencyModel);


                                                                }

                                                                dismiss();

                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });


                                                    }
                                                }
                                            }, Looper.getMainLooper());
                                } else {
                                    turnOnGPS();
                                }
                            }





                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

        }.start();

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });

        return binding.getRoot();
    }

    private void getCurrentLocation(DataSnapshot snapshot, String msg) {


    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }
    private void turnOnGPS() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);


                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(getActivity(), 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });
    }

    private void sendNotification(String msg) {

        database.getReference().child("User_details").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        UserModel model = snapshot.getValue(UserModel.class);
                        String name = model.getUsername();

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("to","/topics/"+model.getUserid());
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("title", name);
                            jsonObject1.put("body",msg);
                            jsonObject1.put("profileImage", model.getPhoto());

                            JSONObject jsonObject2 = new JSONObject();
                            jsonObject2.put("userID",FirebaseAuth.getInstance().getUid());
                            jsonObject2.put("type","sms");

                            jsonObject.put("notification",jsonObject1);
                            jsonObject.put("data",jsonObject2);

                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String , String> map = new HashMap<>();
                                    map.put("content_type", "application/json");
                                    map.put("authorization","key=AAAANwenYVw:APA91bFsYkn27dWFNynO1Z9vZeUBUd3ytkT9HkHH_fpeoqPb77h3Tq2WVJFXJjvs3KQJxb7NcuVBsBZDOIM1Qgr5bGNtcQKUGq8j5LoxFAaNis0Z8_Ik2B6rDxSlGhKpRbSDwcc22w8w");

                                    return map;
                                }
                            };
                            requestQueue.add(request);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }


}