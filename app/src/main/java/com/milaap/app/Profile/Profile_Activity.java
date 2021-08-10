package com.milaap.app.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.milaap.app.datingapp.R;
import com.milaap.app.Utils.PulsatorLayout;
import com.milaap.app.Utils.Session;
import com.milaap.app.Utils.TopNavigationViewHelper;
import com.milaap.app.datingapp.databinding.ActivityProfileBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class Profile_Activity extends AppCompatActivity {
    private static final String TAG = "Profile_Activity";
    private static final int ACTIVITY_NUM = 3;
    static boolean active = false;
    ActivityProfileBinding binding;
    private Context mContext = Profile_Activity.this;
    private ImageView imagePerson;
    private TextView name;
    Session session;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: create the page");
        super.onCreate(savedInstanceState);
        binding= ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PulsatorLayout mPulsator = findViewById(R.id.pulsator);
        mPulsator.start();

        setupTopNavigationView();
         session = new Session(this);
        imagePerson = findViewById(R.id.circle_profile_image);
        name = findViewById(R.id.profile_name);

        binding.profileName.setText(session.getusename());
        Log.d(TAG, "onCreate: username"+ session.getusename());
        ImageButton edit_btn = findViewById(R.id.edit_profile);
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_Activity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        ImageButton settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_Activity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        String imgg= session.getprofilepic();
        String[] arrayString = imgg.split(",");

        String email = arrayString[0];
        // String title = arrayString[1];
        email=  email.replaceAll("\"", "");
        email=  email.replaceAll("\\[", "");
        email=  email.replaceAll("]", "");
        String iurl= "https://msquarebharat.com/milaap/image/banner_top/"+email;
        Log.d(TAG, "getView: imgurl "+email);
        Glide.with(mContext).load(iurl).into(binding.circleProfileImage);
        binding.thistorybtn.setOnClickListener(v -> {
            startActivity(new Intent(this, TransactionHistory.class));
        });
        binding.redeembtn.setOnClickListener(v -> {
            startActivity(new Intent(this, ReedeemActivity.class));
        });
        getJSON();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: resume to the page");

    }


    private void setupTopNavigationView() {
        Log.d(TAG, "setupTopNavigationView: setting up TopNavigationView");
      //  BottomNavigationViewEx tvEx = findViewById(R.id.topNavViewBar);
        BottomNavigationView tvEx = findViewById(R.id.bottomNavigationView);
        TopNavigationViewHelper.setupTopNavigationView(tvEx);
        TopNavigationViewHelper.enableNavigation(mContext, tvEx);
        Menu menu = tvEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    private void getJSON() {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d(TAG, "onPostExecute: "+s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://msquarebharat.com/milaap/app/getuserdetail.php");

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    Log.d(TAG, "doInBackground:  id"+ session.getmobile());
                    String data  = URLEncoder.encode("id", "UTF-8") + "=" +
                            URLEncoder.encode(session.getmobile(), "UTF-8");

//                    data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
//                            URLEncoder.encode(password, "UTF-8");
                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                    wr.write( data );
                    wr.flush();
                    StringBuilder sb = new StringBuilder();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                        Log.d(TAG, "doInBackground: "+json);
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] heroes = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            binding.balanvetv.setText(obj.getString("wallet"));
//            session.setprofilepic(obj.getString("profilepic"));
//            session.setusename(obj.getString("username"));
//            session.setdesignation(obj.getString("designation"));
//            session.setmobile(obj.getString("usermobileno"));
//            session.setemail(obj.getString("useremail"));
//            session.setwebsite(obj.getString("website"));
//            session.setrdate(obj.getString("rdate"));
//            session.setpdate(obj.getString("pdate"));
//            session.setpstatus(obj.getString("pstatus"));
//            session.setptype(obj.getString("ptype"));
//            session.setamount(obj.getString("amount"));
//            session.setsedate(obj.getString("sedate"));
//            session.setprofilepic(obj.getString("profilepic"));



        }
    }

}
