package com.milaap.app.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.milaap.app.Main.GiftActivity;
import com.milaap.app.Utils.HttpParse;
import com.milaap.app.Utils.Session;
import com.milaap.app.datingapp.R;
import com.milaap.app.datingapp.databinding.ActivityReedeemBinding;

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

public class ReedeemActivity extends AppCompatActivity {
    private static final String TAG = "ReedeemActivity";
    Session session;
    ActivityReedeemBinding binding;
    String balance;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult ;
    String HttpURL = "https://msquarebharat.com/milaap/app/redeemrequest.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityReedeemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session= new Session(this);
        getJSON();
        binding.submitbtn.setOnClickListener(v -> {
            if(Integer.parseInt(binding.amountet.getText().toString())<500){
                Toast.makeText(this, "Min amount should be 500", Toast.LENGTH_SHORT).show();
            } else if( Integer.parseInt(balance)<Integer.parseInt(binding.amountet.getText().toString() )){
                Toast.makeText(this, "Your wallet is less than your redeem amount", Toast.LENGTH_SHORT).show();
            } else {
                UpdateFollowUpDate(session.getmobile(),"","Redeem Amount","",binding.amountet.getText().toString(),"");
            }

        });

    }
    public void UpdateFollowUpDate(final String emp_id, final String pdate,final String ptype,final String pstatus,final String amount,final String edate){

        class UserRegisterFunctionClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                // progressDialog = ProgressDialog.show(CreateCustomerActivity.this,"Registering...",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                // progressDialog.dismiss();
                Log.d(TAG, "onPostExecute: "+httpResponseMsg);

              //  Toast.makeText(ReedeemActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                if (httpResponseMsg.equals("Payment Successfully")){

                    //  Toast.makeText(CustomerDetailActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(GiftActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(ReedeemActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("id",params[0]);
                hashMap.put("pdate",params[1]);
                hashMap.put("ptype",params[2]);

                hashMap.put("pstatus",params[3]);
                hashMap.put("amount",params[4]);
                hashMap.put("edate",params[5]);
//                hashMap.put("city",params[6]);
//                hashMap.put("pincode",params[7]);
//                hashMap.put("remark",params[8]);
//                hashMap.put("followupdate",params[9]);
//                hashMap.put("status",params[10]);
//                hashMap.put("aadharcard",params[11]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(emp_id,pdate,ptype,pstatus,amount,edate);
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
              balance=obj.getString("wallet");
         //   binding.balanvetv.setText(obj.getString("wallet"));
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