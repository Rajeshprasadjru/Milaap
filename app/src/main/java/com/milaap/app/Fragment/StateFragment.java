package com.milaap.app.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.milaap.app.Adapter.StateImageAdapter;
import com.milaap.app.datingapp.R;
import com.milaap.app.Utils.Session;
import com.milaap.app.Utils.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StateFragment extends Fragment {
    private static final String TAG = "StateFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<User> clist;
    private Session session;
    GridView gridview ;
    public StateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StateFragment newInstance(String param1, String param2) {
        StateFragment fragment = new StateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
  View view= inflater.inflate(R.layout.fragment_state, container, false);
        clist= new ArrayList<>();
        session = new Session(getContext());
        gridview = (GridView) view.findViewById(R.id.staterecycleview);
        getJSON();
        return  view;
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
            if(obj.getString("place").equals(session.getplace()))
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
          StateImageAdapter adapters = new StateImageAdapter(getContext(),R.layout.personitem,
              "", clist);

        gridview.setAdapter(adapters);

    }
}