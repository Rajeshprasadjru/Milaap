package com.milaap.app.Login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.Listener;
import com.milaap.app.datingapp.R;
import com.milaap.app.Utils.GPS;
import com.milaap.app.Utils.User;

import java.util.Arrays;
import java.util.Collections;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */

public class RegisterBasicInfo extends AppCompatActivity  implements AdapterView.OnItemSelectedListener, Listener {
    private static final String TAG = "RegisterActivity";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS =1 ;
    GPS gps;
    private Context mContext;
    private String email, username, password, mobile, place;
    private EditText mEmail, mPassword, mUsername,mMobile;
    private TextView loadingPleaseWait;
    private Button btnRegister;
    private String append = "";
    EasyWayLocation easyWayLocation;
    private TextView location, latLong, diff;
    private Double lati, longi;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String[] country = {"State",
            "Andhra Pradesh",
            "Arunachal Pradesh",
            "Assam",
            "Bihar",
            "Chhattisgarh",
            "Goa",
            "Gujarat",
            "Haryana",
            "Himachal Pradesh",
            "Jammu and Kashmir",
            "Jharkhand",
            "Karnataka",
            "Kerala",
            "Madhya Pradesh",
            "Maharashtra",
            "Manipur",
            "Meghalaya",
            "Mizoram",
            "Nagaland",
            "Odisha",
            "Punjab",
            "Rajasthan",
            "Sikkim",
            "Tamil Nadu",
            "Telangana",
            "Tripura",
            "Uttarakhand",
            "Uttar Pradesh",
            "West Bengal",
            "Andaman and Nicobar Islands",
            "Chandigarh",
            "Dadra and Nagar Haveli",
            "Daman and Diu",
            "Delhi",
            "Lakshadweep",
            "Puducherry"};
    String[] countrry = {"USA","Bhutan","Pakistan","Bangladesh","Afghanistan"};
       Spinner spin;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerbasic_info);
        mContext = RegisterBasicInfo.this;
        Log.d(TAG, "onCreate: started");
        spin = (Spinner) findViewById(R.id.input_place);
        gps = new GPS(getApplicationContext());
        spin.setOnItemSelectedListener(this);
        initWidgets();
        init();
      //  Arrays.sort(country);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        easyWayLocation = new EasyWayLocation(this, false,false,this);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant

            return;
        }
    }

    private void init() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                email = mEmail.getText().toString();
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();
                mobile = mMobile.getText().toString();
                Log.d("Location==>", lati + "   " + longi);
                if (checkInputs(email, username, password,mobile)) {

                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant

                        return;
                    } else {
                        Location location = gps.getLocation();
                        double latitude = 37.349642;
                        double longtitude = -121.938987;
                        if (location != null) {
                            latitude = location.getLatitude();
                            longtitude = location.getLongitude();
                        }


                        Log.d("Location==>", lati + "   " + longi);


                        Intent intent = new Intent(RegisterBasicInfo.this, RegisterGender.class);
                        User user = new User(mobile, mobile, email, username, false, false, false, false, "", "", "", "", "", lati, longi,place,"");
                        intent.putExtra("password", password);
                        intent.putExtra("classUser", user);
                        startActivity(intent);
                    }
                    //find geo location
                    //find geo location

                }
            }
        });
    }

    private boolean checkInputs(String email, String username, String password, String mobile) {
        Log.d(TAG, "checkInputs: checking inputs for null values.");
        if (email.equals("") || username.equals("") || password.equals("") || mobile.equals("")) {
            Toast.makeText(mContext, "All fields must be filed out.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Below code checks if the email id is valid or not.
        if (!email.matches(emailPattern)) {
            Toast.makeText(getApplicationContext(), "Invalid email address, enter valid email id and click on Continue", Toast.LENGTH_SHORT).show();
            return false;

        }


        return true;
    }

    private void initWidgets() {
        Log.d(TAG, "initWidgets: initializing widgets");
        mEmail = findViewById(R.id.input_email);
        mUsername = findViewById(R.id.input_username);
        btnRegister = findViewById(R.id.btn_register);
        mPassword = findViewById(R.id.input_password);
        mMobile = findViewById(R.id.input_mobile);
        mContext = RegisterBasicInfo.this;

    }

    public void onLoginClicked(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         place= country[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void locationOn() {

    }

    @Override
    public void currentLocation(Location location) {
        StringBuilder data = new StringBuilder();
        lati= location.getLatitude();
        longi= location.getLongitude();
        data.append(location.getLatitude());
        data.append(" , ");
        data.append(location.getLongitude());
        Log.d(TAG, "currentLocation: "+lati+" "+longi);
        Toast.makeText(mContext, ""+location, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void locationCancelled() {

    }
    @Override
    protected void onResume() {
        super.onResume();
        easyWayLocation.startLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        easyWayLocation.endUpdates();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! do the
                // calendar task you need to do.

            } else {
                Toast.makeText(mContext, " You cant register without permission", Toast.LENGTH_SHORT).show();
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return;

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }
}
