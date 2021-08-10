package com.milaap.app.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.milaap.app.Main.MainActivity;
import com.milaap.app.Model.Emoji;
import com.milaap.app.datingapp.R;
import com.milaap.app.Utils.HttpParse;
import com.milaap.app.Utils.Session;
import com.milaap.app.Utils.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;


/**
 * DatingApp
 * https://github.com/quintuslabs/DatingApp
 * Created on 25-sept-2018.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class Login extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private Context mContext;
    private EditText mEmail, mPassword;
    String finalResult ;
    String HttpURLl = "https://msquarebharat.com/milaap/app/adminloginn.php";
    String HttpURL = "https://msquarebharat.com/bima/app/adminlogin.php";
    //String HttpURL = "http://localhost/sspl/adminlogin.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    public static final String UserEmail = "";
    private Session session;//global variable

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = findViewById(R.id.input_email);
        mPassword = findViewById(R.id.input_password);
        session = new Session(this);
        mContext = Login.this;


        init();
    }

    private boolean isStringNull(String string) {
        Log.d(TAG, "isStringNull: checking string if null.");

        return string.equals("");
    }

    //----------------------------------------Firebase----------------------------------------

    private void init() {
        //initialize the button for logging in
        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to log in");

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (isStringNull(email) || isStringNull(password)) {
                    Toast.makeText(mContext, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    UserLogin(email, password);
//                    Intent intent = new Intent(Login.this, MainActivity.class);
//                    startActivity(intent);
                }
            }
        });

        TextView linkSignUp = findViewById(R.id.link_signup);
        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to register screen");
                Intent intent = new Intent(Login.this, RegisterBasicInfo.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public void onBackPressed() {

    }
    public void UserLogin(final String email, final String password){

        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Login.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                if(httpResponseMsg.equalsIgnoreCase("Data Matched")){
                    session.setmobile(email);

                    setdata();


                }
                else{

                    //Toast.makeText(LoginActivity.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                    Toast.makeText(Login.this, "Mobile No not Registered", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("username",params[0]);

                hashMap.put("password",params[1]);

                finalResult = httpParse.postRequest(hashMap, HttpURLl);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(email,password);
    }
    private void setdata() {
        getJSON("https://msquarebharat.com/milaap/app/getuserdetail.php");
    }
    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d(TAG, "onPostExecute: "+s);
               // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                    //


                    //
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);

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
            // heroes[i] = obj.getString("name");
            session.setusename(obj.getString("username"));
            session.setdesignation(obj.getString("description"));
            session.setmobile(obj.getString("phone_number"));
            Log.d(TAG, "loadIntoListView:  user mobile number"+session.getmobile());
            session.setemail(obj.getString("email"));
            session.setwebsite(obj.getString("preferSex"));
            session.setrdate(obj.getString("dateOfBirth"));
            session.setpdate(obj.getString("latitude"));
//            session.setpstatus(obj.getString("pstatus"));
//            session.setptype(obj.getString("ptype"));
            session.setamount(obj.getString("sex"));
//            session.setsedate(obj.getString("sedate"));
            session.setprofilepic(obj.getString("profileImageUrl"));

            User user = new User(obj.getString("user_id"),obj.getString("phone_number"),obj.getString("email"),obj.getString("username"),obj.getString("sports").equals("true"), obj.getString("travel").equals("true"), obj.getString("music").equals("true"), obj.getString("fishing").equals("true"),obj.getString("description"),obj.getString("sex"),obj.getString("preferSex"),obj.getString("dateOfBirth"),obj.getString("profileImageUrl"),Double.parseDouble(obj.getString("latitude")),Double.parseDouble(obj.getString("longitude")),obj.getString("place"),obj.getString("wallet"));
//            user.setSex(obj.getString("sex"));
//            user.setUser_id(obj.getString("user_id"));
//            user.setPhone_number(obj.getString("phone_number"));
//            user.setEmail(obj.getString("email"));
//            user.setUsername(obj.getString("username"));
//            user.setSports(obj.getString("sports").equals("true"));
//            user.setTravel(obj.getString("travel").equals("true"));
//            user.setMusic(obj.getString("music").equals("true"));
//            user.setFishing(obj.getString("fishing").equals("true"));
//
//            user.setDescription(obj.getString("description"));
//            user.setPreferSex(obj.getString("preferSex"));
//            user.setDateOfBirth(obj.getString("dateOfBirth"));
//            user.setProfileImageUrl(obj.getString("profileImageUrl"));
//            user.setLatitude((obj.getString("latitude")));
//            user.setLongtitude((obj.getString("longitude")));
            Log.d(TAG, "loadIntoListView: user detail"+user);
          //  user.setP(obj.getString("password"));
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();

//            Log.d(TAG, "loadIntoListView: "+obj.getString("profilepic"));
//            Log.d(TAG, "loadIntoListView: "+obj.getString("username"));
//            Log.d(TAG, "loadIntoListView: "+obj.getString("designation"));
//            Log.d(TAG, "loadIntoListView: "+obj.getString("profilepic"));
            // Glide.with(this).load(obj.getString("profilepic")).into(binding.mProfileimageview);
            // binding.dobtv.setText(obj.getString("followupdate"));
//            binding.dobtv.setText(getDate(Long.parseLong(obj.getString("followupdate")),"dd/MM/yyyy"));
//            binding.statusIv.setText(obj.getString("status"));
//            binding.remarkTv.setText(obj.getString("remark"));
//            Log.d(TAG, "loadIntoListView: "+
//                    obj.getInt("id")+obj.getInt("sub_id")+obj.getString("name")+obj.getString("address")
//                    +obj.getString("phone")+obj.getString("pancard")+obj.getString("location")+obj.getString("city")
//                    +obj.getString("pincode")+obj.getString("remark")+obj.getString("followupdate")
//                    +obj.getString("status")+obj.getString("aadharcard"));



        }

        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Gifts")
                .child(session.getmobile());


        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    Log.d(TAG, "onDataChange: not exist");
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    Emoji emoji = new Emoji("0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0");
                    reference.child("Gifts").child(session.getmobile()).setValue(emoji);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onDataChange: error"+databaseError);
            }
        });

//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject obj = jsonArray.getJSONObject(i);
//            heroes[i] = obj.getString("name");
//            Log.d(TAG, "loadIntoListView: "+
//                    obj.getInt("id")+obj.getInt("sub_id")+obj.getString("name")+obj.getString("address")
//                    +obj.getString("phone")+obj.getString("pancard")+obj.getString("location")+obj.getString("city")
//                    +obj.getString("pincode")+obj.getString("remark")+obj.getString("followupdate")
//                    +obj.getString("status")+obj.getString("aadharcard"));
//            Log.d(TAG, "loadIntoListView: "+session.getusename()+" "+ obj.getInt("sub_id"));
//            if (session.getusename().equals(String.valueOf(obj.getString("sub_id")))){
//                clist.add(new Customer(obj.getInt("id"),obj.getInt("sub_id"),obj.getString("name"),obj.getString("address")
//                        ,obj.getString("phone"),obj.getString("pancard"),obj.getString("location"),obj.getString("city")
//                        ,obj.getString("pincode"),obj.getString("remark"),obj.getString("followupdate")
//                        ,obj.getString("status"),obj.getString("aadharcard")));
//            }
//
//        }
//        CustomerAdapter categoryAdapter= new CustomerAdapter(this,clist);
//        // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);
//        //binding.categorylistview.setAdapter(arrayAdapter);
//        binding.customerrecycleview.setAdapter(categoryAdapter);
    }
}
