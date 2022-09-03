package com.example.ic_hackathon;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ic_hackathon.Adapter.AddContactsAdapter;
import com.example.ic_hackathon.Adapter.ContactsAdapter;
import com.example.ic_hackathon.Model.ContactsModels;
import com.example.ic_hackathon.databinding.ActivityAddContactsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddContactsActivity extends AppCompatActivity {

    ActivityAddContactsBinding binding;
    ArrayList<ContactsModels> list;
    ArrayList<ContactsModels> searchlist;
    AddContactsAdapter adapter;
    public static TextView btnDownload;
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
        binding = ActivityAddContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            hideStatus();

        btnDownload = (TextView) findViewById(R.id.buttoncontinue);


        list = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{
                    Manifest.permission.READ_CONTACTS},100);

        }else{

            getContactList();

            LinearLayoutManager manager = new LinearLayoutManager(AddContactsActivity.this, LinearLayoutManager.VERTICAL, false);
            binding.addContactsList.setLayoutManager(manager);
            adapter = new AddContactsAdapter(list);
            binding.addContactsList.setAdapter(adapter);
        }

        binding.searchIndividual.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().isEmpty()){
                    binding.addContactsList.setAdapter(new AddContactsAdapter(list));

                }
                else{
                    searchUser(s.toString().trim());
                }
                adapter.notifyDataSetChanged();
            }
        });

        binding.contactBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void searchUser(String s) {

        searchlist = new ArrayList<>();
        for(int i = 0 ; i < list.size(); i++){

            ContactsModels models = list.get(i);
            if(models.getName().toLowerCase().startsWith(s.toLowerCase())){

                searchlist.add(models);
            }
            adapter.notifyDataSetChanged();

        }
        binding.addContactsList.setAdapter(new AddContactsAdapter(searchlist));

    }


    private void getContactList() {

        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+"ASC";

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Uri uri1 = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?";
                Cursor cursor1 = getContentResolver().query(uri1, null, selection, new String[]{id}, null);

                if(cursor1.moveToNext()){

                    String number = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    ContactsModels contactsModels = new ContactsModels();
                    contactsModels.setName(name);
                    contactsModels.setNumber(number);
                    list.add(contactsModels);
                    cursor1.close();
                }
            }

            cursor.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){

            getContactList();
        }
        else{
            Toast.makeText(this, "Permission denied ", Toast.LENGTH_SHORT).show();
        }
    }
}