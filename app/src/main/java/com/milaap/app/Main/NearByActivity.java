package com.milaap.app.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.Listener;
import com.milaap.app.Adapter.NearByAdapter;

import com.milaap.app.Utils.Session;
import com.milaap.app.Utils.User;
import com.milaap.app.datingapp.R;
import com.milaap.app.datingapp.databinding.ActivityNearByBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class NearByActivity extends AppCompatActivity implements Listener {
    private static final String TAG = "NearByActivity";
   ActivityNearByBinding byBinding;
    private ArrayList<User> clist;
    private Session session;
    EasyWayLocation easyWayLocation;

    private Double lati, longi;
    int count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        byBinding= ActivityNearByBinding.inflate(getLayoutInflater());
        setContentView(byBinding.getRoot());;
        clist= new ArrayList<>();
        session = new Session(this);
        byBinding.vipbtn.setOnClickListener(v -> {
          startActivity(new Intent(this,PaymentActivity.class));
        });
        byBinding.home.setOnClickListener(v -> {
            startActivity(new Intent(this,MainActivity.class));
        });
        easyWayLocation = new EasyWayLocation(this, false,false,this);
        if(!(lati ==null))
        getJSON();
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
                // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onPostExecute: "+s);
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://msquarebharat.com/milaap/app/getcustomer.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json).append("\n");
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
        clist.clear();
        JSONArray jsonArray = new JSONArray(json);
        String[] heroes = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            // heroes[i] = obj.getString("name");
            // Toast.makeText(getContext(), ""+heroes[i], Toast.LENGTH_SHORT).show();
           // if(obj.getString("place").equals(session.getplace()))

               double r= distance(lati,longi,Double.parseDouble(obj.getString("latitude")),Double.parseDouble(obj.getString("longtitude")));
            Log.d(TAG, "loadIntoListView: distance "+r);
              if(r<20000)
                clist.add(new User(obj.getString("user_id"),obj.getString("phone_number"),obj.getString("email"),obj.getString("username"),obj.getString("sports").equals("true"), obj.getString("travel").equals("true"), obj.getString("music").equals("true"), obj.getString("fishing").equals("true"),obj.getString("description"),obj.getString("sex"),obj.getString("preferSex"),obj.getString("dateOfBirth"),obj.getString("profileImageUrl"),Double.parseDouble(obj.getString("latitude")),Double.parseDouble(obj.getString("longtitude")),obj.getString("place"),obj.getString("stype")));
//            if(obj.getString("language").equals("Hindi")){
//                clist.add(new User(obj.getString("sex"),obj.getString("preferSex"),obj.getString("user_id"),obj.getString("phone_number"),obj.getString("email"),obj.getString("username"), obj.getString("sports").equals("true"), obj.getString("travel").equals("true"), obj.getString("music").equals("true"), obj.getString("fishing").equals("true"),obj.getString("description"),obj.getString("dateOfBirth"),obj.getString("profileImageUrl"),obj.getDouble("latitude"),obj.getDouble("longtitude")));
//            } else {
//                clist1.add(new GreetingItem(obj.getString("photo"),obj.getString("name"),"",obj.getInt("id")));
//            }

            // Toast.makeText(this, ""+clist, Toast.LENGTH_SHORT).show();
//            clist.add(new Employee(obj.getInt("id"),obj.getInt("sub_id"),obj.getString("name"),obj.getString("mobileno")
//                    ,obj.getString("password"),obj.getString("aadharcard"),obj.getString("pancard"),obj.getString("address"),obj.getString("category"),""));
        }
        Collections.reverse(clist);


//        GridView gridview = (GridView) findViewById(R.id.homerecycleview);
////        gridview.setAdapter(new PersonAdapter(this,rowItems));
//        GridImageAdapter adapter = new GridImageAdapter(MainActivity.this,R.layout.personitem,
//                "", clist);
        NearByAdapter adapters = new NearByAdapter(NearByActivity.this,R.layout.personitem,
                "", clist, lati,longi);

        byBinding.homerecycleview.setAdapter(adapters);

    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        double roundOff = Math.round(dist * 100.0) / 100.0;
        return (roundOff);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
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
        byBinding.progressBar.setVisibility(View.GONE);
        if(count==0){
            getJSON();
            count++;
        }
     //   Toast.makeText(mContext, ""+location, Toast.LENGTH_SHORT).show();
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
}