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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.milaap.app.Main.PaymentActivity;
import com.milaap.app.Model.FriendList;
import com.milaap.app.Model.SliderData;
import com.milaap.app.datingapp.R;

import com.milaap.app.Utils.Session;
import com.milaap.app.Utils.User;
import com.smarteist.autoimageslider.SliderView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class GridImageAdapter extends ArrayAdapter<User> {
    private static final String TAG = "GridImageAdapter";

    private Context mContext;
    private LayoutInflater mInflater;
    private int layoutResource;
    private String mAppend;
    private  String place;
    private ArrayList<User> imgURLs;
    private ArrayList<User> clist;
    Session session;
    String url1 = "https://image.freepik.com/free-vector/dating-app-banner-mobile-phone-with-hearts-online-dating-social-networks_136277-525.jpg";
    String url2 = "https://image.freepik.com/free-vector/online-dating-app-concept_52683-37139.jpg";
    String url3 = "https://cdn8.quackquack.in/index_banner_imgv1.png";


    public GridImageAdapter(Context context, int layoutResource, String append, ArrayList<User> imgURLs) {
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
        ImageView pimage, video, comment, like;
        TextView name,place;

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
            holder.pimage = convertView.findViewById(R.id.profileImage);
            holder.video =  convertView.findViewById(R.id.videoCalBtn);
            holder.comment= convertView.findViewById(R.id.commentbtn);
          //  holder.andExoPlayerView= convertView.findViewById(R.id.andExoPlayerView);
            holder.like= convertView.findViewById(R.id.likebtn);
            holder.name= convertView.findViewById(R.id.name);
            holder.place= convertView.findViewById(R.id.place);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        Log.d(TAG, "getView: "+imgURLs);

        Log.d(TAG, "getView: "+imgURLs.get(position).getProfileImageUrl());
          //imgURL.trim();
          String a[]= {imgURLs.get(position).getProfileImageUrl()};
        Log.d(TAG, "getView: image list "+a[0]);
        String imgg= imgURLs.get(position).getProfileImageUrl();
        String[] arrayString = imgg.split(",");

        String email = arrayString[0];
       // String title = arrayString[1];
       email=  email.replaceAll("\"", "");
       email=  email.replaceAll("\\[", "");
       email=  email.replaceAll("]", "");
         String iurl= "https://msquarebharat.com/milaap/image/banner_top/"+email;
        Log.d(TAG, "getView: imgurl "+email);
        Glide.with(mContext).load(iurl).into(holder.pimage);
        holder.name.setText(imgURLs.get(position).getUsername());
        holder.place.setText(" "+imgURLs.get(position).getPlace());
        holder.video.setOnClickListener(v -> {
            mContext.startActivity( new Intent( mContext, PaymentActivity.class));
//            Dialog dialog = new Dialog(mContext,android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(R.layout.paymentpopup);
//            dialog.setCanceledOnTouchOutside(true);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            // dialog.setTitle(getItem(position).getAddress());
//            dialog.show();
//            ImageView imageView=   dialog.findViewById(R.id.closebtn);
//            imageView.setOnClickListener(v1 -> dialog.dismiss());
////            Window window = dialog.getWindow();
////            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//            // we are creating array list for storing our image urls.
//            ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
//
//            // initializing the slider view.
//            SliderView sliderView = dialog.findViewById(R.id.slider);
//
//            // adding the urls inside array list
//            sliderDataArrayList.add(new SliderData(url1));
//            sliderDataArrayList.add(new SliderData(url2));
//            sliderDataArrayList.add(new SliderData(url3));
//
//            // passing this array list inside our adapter class.
//            SliderAdapter adapter = new SliderAdapter(mContext, sliderDataArrayList);
//
//            // below method is used to set auto cycle direction in left to
//            // right direction you can change according to requirement.
//            sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
//
//            // below method is used to
//            // setadapter to sliderview.
//            sliderView.setSliderAdapter(adapter);
//
//            // below method is use to set
//            // scroll time in seconds.
//            sliderView.setScrollTimeInSec(3);
//
//            // to set it scrollable automatically
//            // we use below method.
//            sliderView.setAutoCycle(true);
//
//            // to start autocycle below method is used.
//            sliderView.startAutoCycle();

        });
        holder.comment.setOnClickListener(v -> {
            mContext.startActivity( new Intent( mContext, PaymentActivity.class));
//            Dialog dialog = new Dialog(mContext,android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(R.layout.paymentpopup);
//            dialog.setCanceledOnTouchOutside(true);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            // dialog.setTitle(getItem(position).getAddress());
//            dialog.show();
//            ImageView imageView=   dialog.findViewById(R.id.closebtn);
//            imageView.setOnClickListener(v1 -> dialog.dismiss());
////            Window window = dialog.getWindow();
////            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//            // we are creating array list for storing our image urls.
//            ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
//
//            // initializing the slider view.
//            SliderView sliderView = dialog.findViewById(R.id.slider);
//
//            // adding the urls inside array list
//            sliderDataArrayList.add(new SliderData(url1));
//            sliderDataArrayList.add(new SliderData(url2));
//            sliderDataArrayList.add(new SliderData(url3));
//
//            // passing this array list inside our adapter class.
//            SliderAdapter adapter = new SliderAdapter(mContext, sliderDataArrayList);
//
//            // below method is used to set auto cycle direction in left to
//            // right direction you can change according to requirement.
//            sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
//
//            // below method is used to
//            // setadapter to sliderview.
//            sliderView.setSliderAdapter(adapter);
//
//            // below method is use to set
//            // scroll time in seconds.
//            sliderView.setScrollTimeInSec(3);
//
//            // to set it scrollable automatically
//            // we use below method.
//            sliderView.setAutoCycle(true);
//
//            // to start autocycle below method is used.
//            sliderView.startAutoCycle();

        });

      holder.pimage.setOnClickListener(v -> {
          Intent intent = new Intent("custom-message");
          //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
          session.setplace(imgURLs.get(position).getPlace());
          intent.putExtra("item",imgURLs.get(position).getPlace());
          LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
//          Intent intent= new Intent(mContext, DetailPage.class);
//          mContext.startActivity(intent);
//          place="";
//          place= imgURLs.get(position).getLatitude();
//          getJSON();
//          Dialog dialog = new Dialog(mContext,android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
//          dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//          dialog.setContentView(R.layout.statepopup);
//          dialog.setCanceledOnTouchOutside(true);
//          //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//          // dialog.setTitle(getItem(position).getAddress());
//          dialog.show();
//          ImageView close = dialog.findViewById(R.id.sclosebtn);
//            close.setOnClickListener(v1 -> {
////                clist.clear();
////                place="";
//                dialog.dismiss();
//            }
//            );
//          GridView gridview = (GridView) dialog.findViewById(R.id.staterecycleview);
////        gridview.setAdapter(new PersonAdapter(this,rowItems));
//          ArrayList<Cards> rowItems;
//          rowItems = new ArrayList<Cards>();
//          Cards cards = new Cards("1", "Swati Tripathy", 21, "https://im.idiva.com/author/2018/Jul/shivani_chhabra-_author_s_profile.jpg", "Simple and beautiful Girl", "Acting", 200);
//          rowItems.add(cards);
//          cards = new Cards("2", "Ananaya Pandy", 20, "https://pbs.twimg.com/profile_images/967542394898952192/_M_eHegh_400x400.jpg", "cool Minded Girl", "Dancing", 800);
//          rowItems.add(cards);
//          cards = new Cards("3", "Anjali Kasyap", 22, "https://pbs.twimg.com/profile_images/967542394898952192/_M_eHegh_400x400.jpg", "Simple and beautiful Girl", "Singing", 400);
//          rowItems.add(cards);
//          cards = new Cards("4", "Preety Deshmukh", 19, "https://pbs.twimg.com/profile_images/485824669732200448/Wy__CJwU.jpeg", "dashing girl", "swiming", 1308);
//          rowItems.add(cards);
//          cards = new Cards("5", "Srutimayee Sen", 20, "https://dp.profilepics.in/profile_pictures/selfie-girls-profile-pics-dp/selfie-pics-dp-for-whatsapp-facebook-profile-25.jpg", "chulbuli nautankibaj ", "Drawing", 1200);
//          rowItems.add(cards);
//          cards = new Cards("6", "Dikshya Agarawal", 21, "https://pbs.twimg.com/profile_images/485824669732200448/Wy__CJwU.jpeg", "Simple and beautiful Girl", "Sleeping", 700);
//          rowItems.add(cards);
//          cards = new Cards("7", "Sudeshna Roy", 19, "https://talenthouse-res.cloudinary.com/image/upload/c_fill,f_auto,h_640,w_640/v1411380245/user-415406/submissions/hhb27pgtlp9akxjqlr5w.jpg", "Papa's Pari", "Art", 5000);
//          rowItems.add(cards);
//          cards = new Cards("8", "Ananaya Pandy", 20, "https://wallpapercave.com/wp/wp4809171.jpg", "cool Minded Girl", "Dancing", 800);
//          rowItems.add(cards);
//          cards = new Cards("9", "Anjali Kasyap", 22, "https://pbs.twimg.com/profile_images/967542394898952192/_M_eHegh_400x400.jpg", "Simple and beautiful Girl", "Singing", 400);
//          rowItems.add(cards);
//          cards = new Cards("10", "Preety Deshmukh", 19, "https://wallpapercave.com/wp/wp4809171.jpg", "dashing girl", "swiming", 1308);
//          rowItems.add(cards);
//          cards = new Cards("11", "Srutimayee Sen", 20, "https://dp.profilepics.in/profile_pictures/selfie-girls-profile-pics-dp/selfie-pics-dp-for-whatsapp-facebook-profile-25.jpg", "chulbuli nautankibaj ", "Drawing", 1200);
//          rowItems.add(cards);
//          cards = new Cards("12", "Dikshya Agarawal", 21, "https://pbs.twimg.com/profile_images/485824669732200448/Wy__CJwU.jpeg", "Simple and beautiful Girl", "Sleeping", 700);
//          rowItems.add(cards);
//          cards = new Cards("13", "Sudeshna Roy", 19, "https://wallpapercave.com/wp/wp4809171.jpg", "Papa's Pari", "Art", 5000);
//          rowItems.add(cards);
//
//
//          StateImageAdapter adapters = new StateImageAdapter(mContext,R.layout.personitem,
//                  "", clist);
//          gridview.setAdapter(adapters);
      });
        holder.like.setOnClickListener(v -> {

            //addNotification(mUser.getUser_id());
//            FirebaseDatabase.getInstance().getReference()
//                    .child(mContext.getString(R.string.ilike))
//                    .child(session.getmobile())
//                    .child(imgURLs.get(position).getPhone_number())
//                    .child(mContext.getString(R.string.field_user_id))
//                    .setValue(imgURLs.get(position).getPhone_number());
//            FirebaseDatabase.getInstance().getReference()
//                    .child(mContext.getString(R.string.likedme))
//                    .child(imgURLs.get(position).getPhone_number())
//                    .child(session.getmobile())
//                    .child(mContext.getString(R.string.field_user_id))
//                    .setValue(session.getmobile());

//            Intent intent= new Intent(mContext, DetailPage.class);
//            mContext.startActivity(intent);
            String timestamp= getTimestamp();
            FriendList friendList=new FriendList(timestamp,imgURLs.get(position).getPhone_number(),"offline",timestamp);
            FirebaseDatabase.getInstance().getReference()
                    .child(mContext.getString(R.string.dbname_friend_list))
                    .child(session.getmobile())
                    .child(imgURLs.get(position).getPhone_number())
                    .setValue(friendList);
            FirebaseDatabase.getInstance().getReference()
                    .child(mContext.getString(R.string.ilike))
                    .child(session.getmobile())
                    .child(imgURLs.get(position).getPhone_number())
                    .setValue(friendList);
            FriendList friendList1=new FriendList(timestamp,session.getmobile(),"offline",timestamp);
            FirebaseDatabase.getInstance().getReference()
                    .child(mContext.getString(R.string.dbname_friend_list))
                    .child(imgURLs.get(position).getPhone_number())
                    .child(session.getmobile())
                    .setValue(friendList1);
            FirebaseDatabase.getInstance().getReference()
                    .child(mContext.getString(R.string.likedme))
                    .child(imgURLs.get(position).getPhone_number())
                    .child(session.getmobile())
                    .setValue(friendList1);
        });
        return convertView;
    }
    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        return sdf.format(new Date());
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
            if(obj.getString("latitude").equals(place))
            clist.add(new User(obj.getString("user_id"),obj.getString("phone_number"),obj.getString("email"),obj.getString("username"),obj.getString("sports").equals("true"), obj.getString("travel").equals("true"), obj.getString("music").equals("true"), obj.getString("fishing").equals("true"),obj.getString("description"),obj.getString("sex"),obj.getString("preferSex"),obj.getString("dateOfBirth"),obj.getString("profileImageUrl"),Double.parseDouble(obj.getString("latitude")),Double.parseDouble(obj.getString("longtitude")),obj.getString("place"),obj.getString("wallet")));
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


