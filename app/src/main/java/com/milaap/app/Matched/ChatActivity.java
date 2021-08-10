package com.milaap.app.Matched;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.iid.FirebaseInstanceId;
import com.milaap.app.Adapter.FriendListAdapter;
import com.milaap.app.Adapter.MessageAdapter;
import com.milaap.app.Main.MainActivity;
import com.milaap.app.Model.Chat;
import com.milaap.app.Model.Chats;
import com.milaap.app.Model.FriendList;
import com.milaap.app.Notifications.Data;
import com.milaap.app.Notifications.MyResponse;
import com.milaap.app.Notifications.Sender;
import com.milaap.app.Notifications.Token;
import com.milaap.app.Utils.APIService;
import com.milaap.app.Utils.Session;
import com.milaap.app.datingapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    //widgets
    private ImageView mBackArrow, mCheckMark;
    private EditText mMessage;
    private TextView mDisplayName,mTimeStamp;
    private ListView mListView;

    //vars
    private FriendList mFriendlist;
      private ArrayList<Chat> mChat;
    private ArrayList<Chat> getmChat;
    private Context mContext;

   //   private ChatListAdapter madapter;
    private int mResults;

    CircleImageView profile_image;
    TextView username;

     FirebaseUser fuser;
     DatabaseReference reference;

    ImageButton btn_send;
    EditText text_send;
       MessageAdapter messageAdapter;
    List<Chats> mchat;

    RecyclerView recyclerView;

    Intent intent;

    ValueEventListener seenListener;

    String userid;

    APIService apiService;
   Session session;
    boolean notify = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mBackArrow = (ImageView) findViewById(R.id.backArrow);
        mCheckMark = (ImageView) findViewById(R.id.ivPostChat);
        mMessage = (EditText) findViewById(R.id.message_chat);
        //  mListView = (ListView) view.findViewById(R.id.listView);
        mTimeStamp=(TextView)findViewById(R.id.tvstatus);


        mDisplayName=(TextView)findViewById(R.id.tvdisplayname) ;
        //mChat = new ArrayList<>();
        mContext = this;
        session= new Session(mContext);
        // apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        // fuser = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        try{
            intent= getIntent();
             mFriendlist = intent.getParcelableExtra("userid");
               // setupFirebaseAuth();
            setupWidgets();

        }catch (NullPointerException e){
            Log.e(TAG, "onCreateView: NullPointerException: " + e.getMessage() );
        }


        if (mFriendlist.getStatus().equals("online")){
            mTimeStamp.setText(mFriendlist.getStatus());
        }else{
            //set the time it was posted
            int timestampDifference = getTimestampDifference(mFriendlist);
            if(timestampDifference<60){
                mTimeStamp.setText(timestampDifference+"min ago");
                //  holder.timeDetla.setText(timestampDifference + " DAYS AGO");
            }else if (timestampDifference>=60&& timestampDifference<=1440){
                int hour =timestampDifference/60;
                mTimeStamp.setText(hour+"hour ago");
                // holder.timeDetla.setText("TODAY");
            } else if(timestampDifference>=1440&& timestampDifference<=10080){

                int hour =(timestampDifference/60)/7;
                mTimeStamp.setText(hour+"weeks ago");
            } else {
                mTimeStamp.setText("long time ago");
            }
        }


        userid=mFriendlist.getUser_id();
        mCheckMark.setOnClickListener(v -> {
            notify = true;
            if(!mMessage.getText().toString().equals("")){
                Log.d(TAG, "onClick: attempting to submit new comment.");
                //addNewComment(mMessage.getText().toString());
                 sendMessage(session.getmobile(), userid, mMessage.getText().toString());
                mMessage.setText("");
                closeKeyboard();
            }else{
                Toast.makeText(mContext, "you can't post a blank comment", Toast.LENGTH_SHORT).show();
            }
        });

        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back");
               onBackPressed();
                //  ((MainActivity)getActivity()).showLayout();
//                try{
//                    if(getCallingActivityFromBundle().equals(R.string.home_activity)){
//                        getActivity().getSupportFragmentManager().popBackStack();
//                        Log.d(TAG, "onClick: home activity");
//                        ((MainActivity)getActivity()).showLayout();
//                    }else {
//                        getActivity().getSupportFragmentManager().popBackStack();
//                    }
//                }catch (NullPointerException e){
//                    Log.e(TAG, "onClick: "+e.getMessage() );
//                }


            }
        });
        readMesagges(session.getmobile(), userid);
        seenMessage(userid);
       // getData();
         updateToken(FirebaseInstanceId.getInstance().getToken());
         getJSON(userid);
    }

    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(session.getmobile()).setValue(token1);
    }
    private int getTimestampDifference(FriendList photo){
        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");

        String difference = "";
        int diff=0;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));//google 'android list of timezones'
        Date today = c.getTime();
        sdf.format(today);
        Date timestamp;
        final String photoTimestamp = photo.getTime_stamp();
        try{
            timestamp = sdf.parse(photoTimestamp);
            // diff=Integer.parseInt(today.getTime()-timestamp.getTime())
            diff = Integer.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / (1000*60)) % 60));

        }catch (ParseException e){
            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage() );
            diff = 0;
        }
        return diff;
    }
    private void seenMessage(final String userid){
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chats chat = snapshot.getValue(Chats.class);
                    assert chat != null;
                    if (chat.getReceiver().equals(session.getmobile()) && chat.getSender().equals(userid)){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void sendMessage(String sender, final String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);
        hashMap.put(getString(R.string.field_timestamp),getTimestamp());

        reference.child("Chats").push().setValue(hashMap);


        // add user to chat fragment
        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(session.getmobile())
                .child(userid);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    chatRef.child("id").setValue(userid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(userid)
                .child(session.getmobile());
        chatRefReceiver.child("id").setValue(session.getmobile());

        final String msg = message;
       // sendNotifiaction(receiver, user.getDisplay_name(), msg);


        reference = FirebaseDatabase.getInstance().getReference(getString(R.string.dbname_friend_list))
                .child(sender).child(receiver);
        reference.child(getString(R.string.field_date_created)).setValue(getTimestamp());
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference(getString(R.string.dbname_friend_list))
                .child(receiver).child(sender);
        reference2.child(getString(R.string.field_date_created)).setValue(getTimestamp());

    }

    private void readMesagges(final String myid, final String userid){
        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chats chat = snapshot.getValue(Chats.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        mchat.add(chat);
                    }

                    messageAdapter = new MessageAdapter(mContext, mchat);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void currentUser(String userid){

        SharedPreferences.Editor editor = getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
        editor.putString("currentuser", userid);
        editor.apply();
    }
    @Override
    public void onResume() {
        super.onResume();
        // status("online");
        currentUser(userid);
    }

    @Override
    public void onPause() {
        super.onPause();
        reference.removeEventListener(seenListener);
        // status("offline");
        currentUser("none");
    }

    private void sendNotifiaction(String receiver, final String username, final String message){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(session.getmobile(), R.mipmap.ic_launcher, username+": "+message, "New Message",
                            "message",userid);
                    assert token != null;
                    Sender sender = new Sender(data, token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(@NotNull Call<MyResponse> call, @NotNull Response<MyResponse> response) {
                                    if (response.code() == 200){

                                        assert response.body() != null;
                                        if (response.body().success != 1){
                                            Toast.makeText(mContext, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                        else  Toast.makeText(mContext, "Success!", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        Log.d(TAG, "onResponse: "+response);
                                    }
                                }



                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {
                                    Log.e(TAG, "onFailure: notification execption", t.getCause());
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupWidgets(){
        //get the profile image and username
      //  mDisplayName.setText(singleSnapshot.getValue(UserAccountSettings.class).getDisplay_name());


//        ChatListAdapter adapter = new ChatListAdapter(mContext, R.layout.layout_chat, mChat);
//        mListView.setAdapter(adapter);


    }
    private void setdata( String uid) {
        getJSON(  uid);
    }
    private void getJSON( String uid) {

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
                    loadIntoListView(s, uid);

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
                    Log.d(TAG, "doInBackground:  id"+ uid);
                    String data  = URLEncoder.encode("id", "UTF-8") + "=" +
                            URLEncoder.encode(uid, "UTF-8");

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

    private void loadIntoListView(String json,  String uid) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] heroes = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            // heroes[i] = obj.getString("name");
            //  session.setusename(obj.getString("username"));
            mDisplayName.setText(obj.getString("username"));

//            String a[]= {imgURLs.get(position).getProfileImageUrl()};
//            Log.d(TAG, "getView: image list "+a[0]);
            String imgg= obj.getString("profileImageUrl");
            String[] arrayString = imgg.split(",");

            String email = arrayString[0];
            // String title = arrayString[1];
            email=  email.replaceAll("\"", "");
            email=  email.replaceAll("\\[", "");
            email=  email.replaceAll("]", "");
            String iurl= "https://msquarebharat.com/milaap/image/banner_top/"+email;
            Log.d(TAG, "getView: imgurl "+email);
           // Glide.with(mContext).load(iurl).into();


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
    private void closeKeyboard(){
        View view = getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd''HH:mm''", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        return sdf.format(new Date());
    }
}