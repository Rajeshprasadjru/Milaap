package com.milaap.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.milaap.app.Matched.ChatActivity;
import com.milaap.app.Model.Chats;
import com.milaap.app.Model.FriendList;
import com.milaap.app.Utils.User;
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
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    private static final String TAG = "FriendListAdapter";

    private Context mContext;
    private List<FriendList> mUsers;
    private boolean ischat;

    String theLastMessage;
     private int messagecount=0;

    public FriendListAdapter(Context mContext, List<FriendList> mUsers, boolean ischat){
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.ischat = ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_messages_listitem, parent, false);
        return new FriendListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final FriendList user = mUsers.get(position);
        //

        //set the profile image
       // final ImageLoader imageLoader = ImageLoader.getInstance();
        //get the profile image and username

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(mContext.getString(R.string.dbname_user_account_settings))
                .orderByChild(mContext.getString(R.string.field_user_id))
                .equalTo(user.getUser_id());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                    // currentUsername = singleSnapshot.getValue(UserAccountSettings.class).getUsername();

                   // Log.d(TAG, "onDataChange: found user: "
                          //  + singleSnapshot.getValue(UserAccountSettings.class).getUsername());

                   // holder.username.setText(singleSnapshot.getValue(UserAccountSettings.class).getUsername());
                    holder.displayname.setText(singleSnapshot.getValue(User.class).getUsername());

//                    imageLoader.displayImage(singleSnapshot.getValue(UserAccountSettings.class).getProfile_photo(),
//                            holder.profile_image);


                  //  holder.settings = singleSnapshot.getValue(UserAccountSettings.class);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //

//       // holder.username.setText(user.getUsername());
//        if (user.getImageURL().equals("default")){
//            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
//        } else {
//            Glide.with(mContext).load(user.getImageURL()).into(holder.profile_image);
//        }
        badgedetail(holder.badge,user.getUser_id());
        setdata(holder, user.getUser_id());
//
        if (ischat){
            //lastMessage(user.getUser_id(), holder.last_msg,holder.img_on,holder.img_off);
        } else {
            holder.last_msg.setVisibility(View.GONE);
        }

        if (ischat){
            if (user.getStatus().equals("online")){
                holder.img_on.setVisibility(View.VISIBLE);
                holder.img_off.setVisibility(View.GONE);
            } else {
                holder.img_on.setVisibility(View.GONE);
                holder.img_off.setVisibility(View.VISIBLE);
            }
        } else {
            holder.img_on.setVisibility(View.GONE);
            holder.img_off.setVisibility(View.GONE);
        }
       // lastStatus(user.getUser_id(), holder.last_msg,holder.img_on,holder.img_off);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("userid", user);
                   mContext.startActivity(intent);
//                ((MainActivity)mContext).onChatThreadSelected(user,mContext.getString(R.string.home_activity));
//                //going to need to do something else?
//                ((MainActivity)mContext).hideLayout();

//                Intent intent = new Intent(mContext, MessageActivity.class);
//                intent.putExtra("userid", user.getId());
//                mContext.startActivity(intent);
            }
        });
    }

    private void badgedetail(TextView badge, String user_id) {

        Log.d(TAG, "badgedetail: message count before "+messagecount);

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Chats");
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot singleSnapshot:snapshot.getChildren()){
                    Chats chat = singleSnapshot.getValue(Chats.class);
                    try{
                        if (chat.getReceiver().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())&&chat.getSender().equals(user_id) ){
                            if(!chat.isIsseen()){
                                messagecount++;
                            }
                        }
                    }catch (NullPointerException e){
                        Log.e(TAG, "onDataChange: "+e.getMessage() );
                    }
                }
                Log.d(TAG, "badgedetail: message count after "+messagecount);
                if (messagecount>0){
                   badge.setVisibility(View.VISIBLE);
                   badge.setText(Integer.toString(messagecount));
                    messagecount=0;

                }else {
                   badge.setVisibility(View.GONE);

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void setdata( ViewHolder holder,String uid) {
        getJSON( holder, uid);
    }
    private void getJSON( ViewHolder holder,String uid) {

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
                    loadIntoListView(s, holder, uid);

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

    private void loadIntoListView(String json, ViewHolder holder,String uid) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] heroes = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            // heroes[i] = obj.getString("name");
          //  session.setusename(obj.getString("username"));
            holder.displayname.setText(obj.getString("username"));

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
            Glide.with(mContext).load(iurl).into(holder.profile_image);


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
    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username, displayname;
        public ImageView profile_image;
        private ImageView img_on;
        private ImageView img_off;
        private TextView last_msg;
        private TextView badge;

        public ViewHolder(View itemView) {
            super(itemView);
            displayname = itemView.findViewById(R.id.display_name);
            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_photo);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
           last_msg = itemView.findViewById(R.id.username);
           badge=itemView.findViewById(R.id.text_badge);
        }
    }


//    private void lastStatus(final String userid, final TextView last_msg, final ImageView img_on, final ImageView img_off){
//        theLastMessage = "default";
//        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        Query query=reference.child("friend_list").child(firebaseUser.getUid())
//                .orderByChild("user_id").equalTo(userid);
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    FriendList chat = snapshot.getValue(FriendList.class);
//                    if (firebaseUser != null && chat != null) {
//                        if(chat.getStatus().equals("online")){
//                            img_on.setVisibility(View.VISIBLE);
//                            img_off.setVisibility(View.GONE);
//
//                        }else {
//                            img_on.setVisibility(View.GONE);
//                            img_off.setVisibility(View.VISIBLE);
//                        }
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//    //check for last message
//    private void lastMessage(final String userid, final TextView last_msg, final ImageView img_on, final ImageView img_off){
//        theLastMessage = "default";
//        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Chats chat = snapshot.getValue(Chats.class);
//                    if (firebaseUser != null && chat != null) {
//                        if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
//                                chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())) {
//                            theLastMessage = chat.getMessage();
//
//
//
//
//                        }
//                    }
//                }
//
//                switch (theLastMessage){
//                    case  "default":
//                        last_msg.setText("No Message");
//                        break;
//
//                    default:
//                        last_msg.setText(theLastMessage);
//                        break;
//                }
//
//                theLastMessage = "default";
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}
