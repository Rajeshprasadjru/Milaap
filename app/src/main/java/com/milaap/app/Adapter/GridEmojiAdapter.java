package com.milaap.app.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.milaap.app.Model.Emoji;
import com.milaap.app.Model.Gift;
import com.milaap.app.Model.SliderData;
import com.milaap.app.Utils.Session;
import com.milaap.app.Utils.User;
import com.milaap.app.datingapp.R;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import de.hdodenhof.circleimageview.CircleImageView;

public class GridEmojiAdapter extends ArrayAdapter<Gift> {
    private static final String TAG = "GridImageAdapter";

    private Context mContext;
    private LayoutInflater mInflater;
    private int layoutResource;
    private String mAppend;
    private  String place;
    private ArrayList<Gift> imgURLs;
    private ArrayList<Gift> clist;
    Session session;
    String url1 = "https://image.freepik.com/free-vector/dating-app-banner-mobile-phone-with-hearts-online-dating-social-networks_136277-525.jpg";
    String url2 = "https://image.freepik.com/free-vector/online-dating-app-concept_52683-37139.jpg";
    String url3 = "https://cdn8.quackquack.in/index_banner_imgv1.png";


    public GridEmojiAdapter(Context context, int layoutResource, String append, ArrayList<Gift> imgURLs) {
        super(context, layoutResource, imgURLs);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        this.layoutResource = layoutResource;
        mAppend = append;
        this.imgURLs = imgURLs;
        session= new Session(mContext);
        clist= new ArrayList<>();
    }

    static class ViewHolder{
        ImageView pimage;
        TextView name;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        /*
        Viewholder build pattern (Similar to recyclerview)
         */
        final ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();
            holder.pimage = convertView.findViewById(R.id.gift);
//            holder.video =  convertView.findViewById(R.id.videoCalBtn);
//            holder.comment= convertView.findViewById(R.id.commentbtn);
          //  holder.andExoPlayerView= convertView.findViewById(R.id.andExoPlayerView);
//            holder.like= convertView.findViewById(R.id.likebtn);
            holder.name= convertView.findViewById(R.id.giftcount);
//            holder.place= convertView.findViewById(R.id.place);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
//       if(position==0){
//
//       }

       // String u= "https://msquarebharat.com/milaap/app/images/new/airplane.png";
       // holder.pimage.setImageResource(imgURLs.get(position).getImg());
        Glide.with(mContext).load(imgURLs.get(position).getImg()).into(holder.pimage);
        holder.name.setText(imgURLs.get(position).getCount());
      //  Log.d(TAG, "getView: "+imgURLs.get(position).getProfileImageUrl());
          //imgURL.trim();


        return convertView;
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
            Toast.makeText(mContext, ""+heroes[i], Toast.LENGTH_SHORT).show();
        //    if(obj.getString("latitude").equals(place))
          //  clist.add(new User(obj.getString("sex"),obj.getString("preferSex"),obj.getString("user_id"),obj.getString("phone_number"),obj.getString("email"),obj.getString("username"), obj.getString("sports").equals("true"), obj.getString("travel").equals("true"), obj.getString("music").equals("true"), obj.getString("fishing").equals("true"),obj.getString("description"),obj.getString("dateOfBirth"),obj.getString("profileImageUrl"),obj.getString("latitude"),obj.getDouble("longtitude")));
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
////        StateImageAdapter adapters = new StateImageAdapter(MainActivity.this,R.layout.personitem,
////                "", rowItems);
//
//        gridview.setAdapter(adapter);

    }
}


