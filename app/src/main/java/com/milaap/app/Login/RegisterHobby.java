package com.milaap.app.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.milaap.app.Main.MainActivity;
import com.milaap.app.Model.Emoji;
import com.milaap.app.Utils.Session;
import com.milaap.app.datingapp.R;
import com.milaap.app.Utils.HttpParse;
import com.milaap.app.Utils.User;

import java.util.HashMap;


/**
 * DatingApp
 * https://github.com/quintuslabs/DatingApp
 * Created on 25-sept-2018.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */

public class RegisterHobby extends AppCompatActivity {
    private static final String TAG = "RegisterHobby";

    //User Info
    User userInfo;
    String password;

    private Context mContext;
    private Button hobbiesContinueButton;
    private Button sportsSelectionButton;
    private Button travelSelectionButton;
    private Button musicSelectionButton;
    private Button fishingSelectionButton;


    private String append = "";
    String HttpURL = "https://msquarebharat.com/milaap/app/userregistration.php";
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult ;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_hobby);
        mContext = RegisterHobby.this;

        Log.d(TAG, "onCreate: started");

        Intent intent = getIntent();
        userInfo = (User) intent.getSerializableExtra("classUser");
        password = intent.getStringExtra("password");
        session = new Session(this);

        initWidgets();

        init();
    }

    private void initWidgets() {
        sportsSelectionButton = findViewById(R.id.sportsSelectionButton);
        travelSelectionButton = findViewById(R.id.travelSelectionButton);
        musicSelectionButton = findViewById(R.id.musicSelectionButton);
        fishingSelectionButton = findViewById(R.id.fishingSelectionButton);
        hobbiesContinueButton = findViewById(R.id.hobbiesContinueButton);

        // Initially all the buttons needs to be grayed out so this code is added, on selection we will enable it later
        sportsSelectionButton.setAlpha(.5f);
        sportsSelectionButton.setBackgroundColor(Color.GRAY);

        travelSelectionButton.setAlpha(.5f);
        travelSelectionButton.setBackgroundColor(Color.GRAY);

        musicSelectionButton.setAlpha(.5f);
        musicSelectionButton.setBackgroundColor(Color.GRAY);

        fishingSelectionButton.setAlpha(.5f);
        fishingSelectionButton.setBackgroundColor(Color.GRAY);


        sportsSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sportsButtonClicked();
            }
        });

        travelSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                travelButtonClicked();
            }
        });

        musicSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicButtonClicked();
            }
        });

        fishingSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fishingButtonClicked();
            }
        });


    }

    public void sportsButtonClicked() {
        // this is to toggle between selection and non selection of button
        if (sportsSelectionButton.getAlpha() == 1.0f) {
            sportsSelectionButton.setAlpha(.5f);
            sportsSelectionButton.setBackgroundColor(Color.GRAY);
            userInfo.setSports(false);
        } else {
            sportsSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
            sportsSelectionButton.setAlpha(1.0f);
            userInfo.setSports(true);
        }
    }

    public void travelButtonClicked() {
        // this is to toggle between selection and non selection of button
        if (travelSelectionButton.getAlpha() == 1.0f) {
            travelSelectionButton.setAlpha(.5f);
            travelSelectionButton.setBackgroundColor(Color.GRAY);
            userInfo.setTravel(false);
        } else {
            travelSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
            travelSelectionButton.setAlpha(1.0f);
            userInfo.setTravel(true);

        }

    }

    public void musicButtonClicked() {
        // this is to toggle between selection and non selection of button
        if (musicSelectionButton.getAlpha() == 1.0f) {
            musicSelectionButton.setAlpha(.5f);
            musicSelectionButton.setBackgroundColor(Color.GRAY);
            userInfo.setMusic(false);
        } else {
            musicSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
            musicSelectionButton.setAlpha(1.0f);
            userInfo.setMusic(true);

        }

    }

    public void fishingButtonClicked() {
        // this is to toggle between selection and non selection of button
        if (fishingSelectionButton.getAlpha() == 1.0f) {
            fishingSelectionButton.setAlpha(.5f);
            fishingSelectionButton.setBackgroundColor(Color.GRAY);
            userInfo.setFishing(false);
        } else {
            fishingSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
            fishingSelectionButton.setAlpha(1.0f);
            userInfo.setFishing(true);

        }

    }

    public void init() {
        hobbiesContinueButton.setOnClickListener(v -> {
        UserRegisterFunction( userInfo.getSex(),userInfo.getPreferSex(),userInfo.getUser_id(),userInfo.getPhone_number(),
                userInfo.getEmail(), userInfo.getUsername(),String.valueOf(userInfo.isSports()),String.valueOf(userInfo.isSports()),String.valueOf(userInfo.isMusic()),
                String.valueOf(userInfo.isFishing()), userInfo.getDescription(),userInfo.getDateOfBirth(),userInfo.getProfileImageUrl(),String.valueOf(userInfo.getLatitude()),String.valueOf(userInfo.getLongtitude()), password,userInfo.getPlace());
        });
    }


    //----------------------------------------Firebase----------------------------------------
    //registration
    public void UserRegisterFunction(final String sex,final String preferSex,final String user_id, final String phone_number,final String email,final String username,final String sport,final String travel,final String music,final String fish,final String description,final String dateOfBirth,final String profileImageUrl,final String latitude, final String longtitude,final String password, final String place){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(RegisterHobby.this,"Registering...",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);


                Log.d(TAG, "onPostExecute: response"+httpResponseMsg);
                if (httpResponseMsg.equals("Registration Successfully")){
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                       session.setmobile(userInfo.getPhone_number());
                   Emoji emoji = new Emoji("0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0");
                    reference.child("Gifts").child(userInfo.getPhone_number()).setValue(emoji);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();

                     Toast.makeText(RegisterHobby.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                    //   uploadImage();
                    //finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterHobby.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("sex",params[0]);
                hashMap.put("user_id",params[1]);
                hashMap.put("phone_number",params[2]);
                hashMap.put("email",params[3]);
                hashMap.put("username",params[4]);
                hashMap.put("sports",params[5]);
                hashMap.put("travel",params[6]);
                hashMap.put("music",params[7]);
                hashMap.put("fishing",params[8]);
                hashMap.put("description",params[9]);
                hashMap.put("preferSex",params[10]);
                hashMap.put("dateOfBirth",params[11]);
                hashMap.put("profileImageUrl",params[12]);
                hashMap.put("latitude",params[13]);
                hashMap.put("longtitude",params[14]);
                hashMap.put("password",params[15]);
                hashMap.put("place",params[16]);


                Log.d(TAG, "doInBackground: "+params[6]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(sex,user_id,phone_number,email,username,sport,travel,music,fish,description,preferSex,dateOfBirth,profileImageUrl,latitude, longtitude, password,place);
    }

}
