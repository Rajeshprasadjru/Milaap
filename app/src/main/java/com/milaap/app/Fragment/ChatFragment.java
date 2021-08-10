package com.milaap.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.database.annotations.NotNull;
import com.milaap.app.Main.MainActivity;
import com.milaap.app.datingapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatFragment extends Fragment {
    private static final String TAG = "ChatFragment";

    public ChatFragment(){
        super();
        setArguments(new Bundle());
    }

    //firebase
   // private FirebaseAuth mAuth;
   // private FirebaseAuth.AuthStateListener mAuthListener;
//    private FirebaseDatabase mFirebaseDatabase;
//    private DatabaseReference myRef;

    //widgets
    private ImageView mBackArrow, mCheckMark;
    private EditText mMessage;
    private TextView mDisplayName,mTimeStamp;
    private ListView mListView;

    //vars
    //private FriendList mFriendlist;
  //  private ArrayList<Chat> mChat;
    //private ArrayList<Chat> getmChat;
    private Context mContext;

  //  private ChatListAdapter madapter;
    private int mResults;

    CircleImageView profile_image;
    TextView username;

   // FirebaseUser fuser;
   // DatabaseReference reference;

    ImageButton btn_send;
    EditText text_send;

   // MessageAdapter messageAdapter;
    //List<Chats> mchat;

    RecyclerView recyclerView;

    Intent intent;

    //ValueEventListener seenListener;

    String userid;

    //APIService apiService;

    boolean notify = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_chats, container, false);
        mBackArrow = (ImageView) view.findViewById(R.id.backArrow);
        mCheckMark = (ImageView) view.findViewById(R.id.ivPostChat);
        mMessage = (EditText) view.findViewById(R.id.message_chat);
      //  mListView = (ListView) view.findViewById(R.id.listView);
        mTimeStamp=(TextView)view.findViewById(R.id.tvstatus);

        mDisplayName=(TextView)view.findViewById(R.id.tvdisplayname) ;
        //mChat = new ArrayList<>();
        mContext = getActivity();
       // apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
       // fuser = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = (RecyclerView)view. findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        try{
           // mFriendlist = getPhotoFromBundle();
        //    setupFirebaseAuth();
            //setupWidgets();

        }catch (NullPointerException e){
            Log.e(TAG, "onCreateView: NullPointerException: " + e.getMessage() );
        }


//        if (mFriendlist.getStatus().equals("online")){
//            mTimeStamp.setText(mFriendlist.getStatus());
//        }else{
//            //set the time it was posted
//            int timestampDifference = getTimestampDifference(mFriendlist);
//            if(timestampDifference<60){
//                mTimeStamp.setText(timestampDifference+"min ago");
//                //  holder.timeDetla.setText(timestampDifference + " DAYS AGO");
//            }else if (timestampDifference>=60&& timestampDifference<=1440){
//                int hour =timestampDifference/60;
//                mTimeStamp.setText(hour+"hour ago");
//                // holder.timeDetla.setText("TODAY");
//            } else if(timestampDifference>=1440&& timestampDifference<=10080){
//
//                int hour =(timestampDifference/60)/7;
//                mTimeStamp.setText(hour+"weeks ago");
//            } else {
//                mTimeStamp.setText("long time ago");
//            }
//        }
//
//
//        userid=mFriendlist.getUser_id();
        mCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify = true;
                if(!mMessage.getText().toString().equals("")){
                    Log.d(TAG, "onClick: attempting to submit new comment.");
                    //addNewComment(mMessage.getText().toString());
                   // sendMessage(fuser.getUid(), userid, mMessage.getText().toString());
                    mMessage.setText("");
                    closeKeyboard();
                }else{
                    Toast.makeText(getActivity(), "you can't post a blank comment", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back");
                ((MainActivity)getActivity()).getSupportFragmentManager().popBackStack();
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
        //readMesagges(fuser.getUid(), userid);
        //seenMessage(userid);
        //getData();
       // updateToken(FirebaseInstanceId.getInstance().getToken());
        return view;
    }

    private void updateToken(String token){
       // DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
       // Token token1 = new Token(token);
       // reference.child(fuser.getUid()).setValue(token1);
    }

//    private void seenMessage(final String userid){
//        reference = FirebaseDatabase.getInstance().getReference("Chats");
//        seenListener = reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                //    Chats chat = snapshot.getValue(Chats.class);
//                    if (chat.getReceiver().equals(fuser.getUid()) && chat.getSender().equals(userid)){
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put("isseen", true);
//                        snapshot.getRef().updateChildren(hashMap);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//    private void sendMessage(String sender, final String receiver, String message){
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("sender", sender);
//        hashMap.put("receiver", receiver);
//        hashMap.put("message", message);
//        hashMap.put("isseen", false);
//        hashMap.put(getString(R.string.field_timestamp),getTimestamp());
//
//        reference.child("Chats").push().setValue(hashMap);
//
//
//        // add user to chat fragment
//        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
//                .child(fuser.getUid())
//                .child(userid);
//
//        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (!dataSnapshot.exists()){
//                    chatRef.child("id").setValue(userid);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist")
//                .child(userid)
//                .child(fuser.getUid());
//        chatRefReceiver.child("id").setValue(fuser.getUid());
//
//        final String msg = message;
//
//        reference = FirebaseDatabase.getInstance().getReference(getString(R.string.dbname_user_account_settings)).child(fuser.getUid());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                UserAccountSettings user = dataSnapshot.getValue(UserAccountSettings.class);
//                if (notify) {
//                    sendNotifiaction(receiver, user.getDisplay_name(), msg);
//                }
//                notify = false;
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        reference = FirebaseDatabase.getInstance().getReference(getString(R.string.dbname_friend_list))
//                .child(sender).child(receiver);
//        reference.child(getString(R.string.field_date_created)).setValue(getTimestamp());
//        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference(getString(R.string.dbname_friend_list))
//                .child(receiver).child(sender);
//        reference2.child(getString(R.string.field_date_created)).setValue(getTimestamp());
//
//    }
//
//    private void readMesagges(final String myid, final String userid){
//        mchat = new ArrayList<>();
//
//        reference = FirebaseDatabase.getInstance().getReference("Chats");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                mchat.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Chats chat = snapshot.getValue(Chats.class);
//                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
//                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
//                        mchat.add(chat);
//                    }
//
//                    messageAdapter = new MessageAdapter(mContext, mchat);
//                    recyclerView.setAdapter(messageAdapter);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    private void currentUser(String userid){

        SharedPreferences.Editor editor = getActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
        editor.putString("currentuser", userid);
        editor.apply();
    }

//    private void status(String status){
//        ///set status
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        Query query = reference
//                .child(getString(R.string.dbname_friend_list))
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
//                    Log.d(TAG, "onDataChange: found user: " + singleSnapshot.child(getString(R.string.field_user_id)).getValue());
//                    FriendList friendList=new FriendList();
//                    try {
//                        friendList=singleSnapshot.getValue(FriendList.class);
//                        ////
//                        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference(getString(R.string.dbname_friend_list)).child(friendList.getUser_id()).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put("status", status);
//
//                        reference1.updateChildren(hashMap);
//                        ////
//
//                    }catch (NullPointerException e){
//                        Log.e(TAG, "onDataChange: "+ e.getMessage() );
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    @Override
    public void onResume() {
        super.onResume();
        // status("online");
        currentUser(userid);
    }

    @Override
    public void onPause() {
        super.onPause();
       // reference.removeEventListener(seenListener);
        // status("offline");
       // currentUser("none");
    }

//    private void sendNotifiaction(String receiver, final String username, final String message){
//        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
//        Query query = tokens.orderByKey().equalTo(receiver);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Token token = snapshot.getValue(Token.class);
//                    Data data = new Data(fuser.getUid(), R.mipmap.ic_launcher, username+": "+message, "New Message",
//                            "message",userid);
//                    assert token != null;
//                    Sender sender = new Sender(data, token.getToken());
//
//                    apiService.sendNotification(sender)
//                            .enqueue(new Callback<MyResponse>() {
//                                @Override
//                                public void onResponse(@NotNull Call<MyResponse> call, @NotNull Response<MyResponse> response) {
//                                    if (response.code() == 200){
//
//                                        assert response.body() != null;
//                                        if (response.body().success != 1){
//                                            Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
//                                        }
//                                        else  Toast.makeText(getContext(), "Success!", Toast.LENGTH_LONG).show();
//                                    }
//                                    else
//                                    {
//                                        Log.d(TAG, "onResponse: "+response);
//                                    }
//                                }
//
//
//
//                                @Override
//                                public void onFailure(Call<MyResponse> call, Throwable t) {
//                                    Log.e(TAG, "onFailure: notification execption", t.getCause());
//                                }
//                            });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

//    public void getData(){
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        Query query = reference
//                .child(getString(R.string.db_chat_room))
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .orderByChild(getString(R.string.field_user_id))
//                .equalTo(mFriendlist.getUser_id());
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
//
//                    Chat chat = new Chat();
//                    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();
//
//                    chat.setMessage(objectMap.get(getString(R.string.field_message)).toString());
//                    chat.setTimestamp(objectMap.get(getString(R.string.field_timestamp)).toString());
//                    chat.setUser_id(objectMap.get(getString(R.string.field_user_id)).toString());
//
//                    mChat.add(chat);
//                }
//                displaychat();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void displaychat() {
//        getmChat = new ArrayList<>();
//        if (mChat != null) {
//            try {
//                Collections.sort(mChat, new Comparator<Chat>() {
//                    @Override
//                    public int compare(Chat o1, Chat o2) {
//                        return o2.getTimestamp().compareTo(o1.getTimestamp());
//                    }
//                });
//
//                int iterations = mChat.size();
//
//                if (iterations > 10) {
//                    iterations = 10;
//                }
//
//                mResults = 10;
//                for (int i = 0; i < iterations; i++) {
//                    getmChat.add(mChat.get(i));
//                }
//
//                madapter = new ChatListAdapter(getActivity(), R.layout.layout_chat, getmChat);
//                mListView.setAdapter(madapter);
//
//            } catch (NullPointerException e) {
//                Log.e(TAG, "displayPhotos: NullPointerException: " + e.getMessage());
//            } catch (IndexOutOfBoundsException e) {
//                Log.e(TAG, "displayPhotos: IndexOutOfBoundsException: " + e.getMessage());
//            }
//        }
//
//    }
//
//
//
//    public void displayMoreChat(){
//        Log.d(TAG, "displayMorePhotos: displaying more photos");
//
//        try{
//
//            if(mChat.size() > mResults && mChat.size() > 0){
//
//                int iterations;
//                if(mChat.size() > (mResults + 10)){
//                    Log.d(TAG, "displayMorePhotos: there are greater than 10 more photos");
//                    iterations = 10;
//                }else{
//                    Log.d(TAG, "displayMorePhotos: there is less than 10 more photos");
//                    iterations = mChat.size() - mResults;
//                }
//
//                //add the new photos to the paginated results
//                for(int i = mResults; i < mResults + iterations; i++){
//                    getmChat.add(mChat.get(i));
//                }
//                mResults = mResults + iterations;
//                madapter.notifyDataSetChanged();
//            }
//        }catch (NullPointerException e){
//            Log.e(TAG, "displayPhotos: NullPointerException: " + e.getMessage() );
//        }catch (IndexOutOfBoundsException e){
//            Log.e(TAG, "displayPhotos: IndexOutOfBoundsException: " + e.getMessage() );
//        }
//    }
//
//
//    private void setupWidgets(){
//        //get the profile image and username
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        Query query = reference
//                .child(mContext.getString(R.string.dbname_user_account_settings))
//                .orderByChild(mContext.getString(R.string.field_user_id))
//                .equalTo(mFriendlist.getUser_id());
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
//
//                    // currentUsername = singleSnapshot.getValue(UserAccountSettings.class).getUsername();
//
//                    Log.d(TAG, "onDataChange: found user: "
//                            + singleSnapshot.getValue(UserAccountSettings.class).getDisplay_name());
//
//                    mDisplayName.setText(singleSnapshot.getValue(UserAccountSettings.class).getDisplay_name());
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
////        ChatListAdapter adapter = new ChatListAdapter(mContext, R.layout.layout_chat, mChat);
////        mListView.setAdapter(adapter);
//
//
//    }

    private void closeKeyboard(){
        View view = getActivity().getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
//
//
//    private void addNewComment(String newMessage){
//        Log.d(TAG, "addNewComment: adding new comment: " + newMessage);
//
//        String commentID = myRef.push().getKey();
//
//        Chat chat = new Chat();
//        chat.setMessage(newMessage);
//        chat.setTimestamp(getTimestamp());
//        chat.setUser_id(mFriendlist.getUser_id());
//
//        //insert into senders node node
//        myRef.child(getString(R.string.db_chat_room))
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .child(commentID)
//                .setValue(chat);
//
//        Chat chat1 = new Chat();
//        chat1.setMessage(newMessage);
//        chat1.setTimestamp(getTimestamp());
//        chat1.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//        //insert into receivers node
//        myRef.child(getString(R.string.db_chat_room))
//                .child(mFriendlist.getUser_id())
//                .child(commentID)
//                .setValue(chat1);
//
//    }
//
//    private String getTimestamp(){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd''HH:mm''", Locale.getDefault());
//        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
//        return sdf.format(new Date());
//    }
//
//    /**
//     * retrieve the photo from the incoming bundle from profileActivity interface
//     * @return
//     */
//    private FriendList getPhotoFromBundle(){
//        Log.d(TAG, "getPhotoFromBundle: arguments: " + getArguments());
//
//        Bundle bundle = this.getArguments();
//        if(bundle != null) {
//            return bundle.getParcelable(getString(R.string.dbname_friend_list));
//        }else{
//            return null;
//        }
//    }
//    /**
//     * retrieve the photo from the incoming bundle from profileActivity interface
//     * @return
//     */
//    private String getCallingActivityFromBundle(){
//        Log.d(TAG, "getPhotoFromBundle: arguments: " + getArguments());
//
//        Bundle bundle = this.getArguments();
//        if(bundle != null) {
//            return bundle.getString(getString(R.string.home_activity));
//        }else{
//            return null;
//        }
//    }
//
//           /*
//    ------------------------------------ Firebase ---------------------------------------------
//     */
//
//    /**
//     * Setup the firebase auth object
//     */
//    private void setupFirebaseAuth(){
//        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");
//
//        mAuth = FirebaseAuth.getInstance();
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        myRef = mFirebaseDatabase.getReference();
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//
//
//                if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                    SharedPreferences sp= getActivity().getSharedPreferences("SP_USER",Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor=sp.edit();
//                    editor.putString("Current_USERID",user.getUid());
//                    editor.apply();
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//                // ...
//            }
//        };
//
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }
//
//    private int getTimestampDifference(FriendList photo){
//        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");
//
//        String difference = "";
//        int diff=0;
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
//        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));//google 'android list of timezones'
//        Date today = c.getTime();
//        sdf.format(today);
//        Date timestamp;
//        final String photoTimestamp = photo.getTime_stamp();
//        try{
//            timestamp = sdf.parse(photoTimestamp);
//            // diff=Integer.parseInt(today.getTime()-timestamp.getTime())
//            diff = Integer.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / (1000*60)) % 60));
//
//        }catch (ParseException e){
//            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage() );
//            diff = 0;
//        }
//        return diff;
//    }


}