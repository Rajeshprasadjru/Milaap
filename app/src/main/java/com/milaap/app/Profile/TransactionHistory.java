package com.milaap.app.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.bumptech.glide.Glide;
import com.milaap.app.Adapter.TransactionAdapter;
import com.milaap.app.Model.Thistory;
import com.milaap.app.Utils.Session;
import com.milaap.app.datingapp.R;
import com.milaap.app.datingapp.databinding.ActivityTransactionHistoryBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TransactionHistory extends AppCompatActivity {
    private static final String TAG = "TransactionHistory";
    ActivityTransactionHistoryBinding binding;
    ArrayList<Thistory> thistories ;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityTransactionHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session = new Session(this);
        thistories= new ArrayList<>();
        binding.menubtn.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.geetinggridviewenglish.setHasFixedSize(true);
        binding.geetinggridviewenglish.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
       getJSON("https://msquarebharat.com/milaap/app/getpayment.php");
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
                    URL url = new URL(urlWebService);

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    Log.d(TAG, "doInBackground:  id"+ session.getusename());
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

              thistories.add(new Thistory(obj.getString("type"),obj.getString("amount"),obj.getString("purpose"),obj.getString("date")));


        }
        Collections.reverse(thistories);
        TransactionAdapter transactionAdapter= new TransactionAdapter(TransactionHistory.this,thistories, false);
        binding.geetinggridviewenglish.setAdapter(transactionAdapter);

//
    }
}