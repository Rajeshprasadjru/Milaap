package com.milaap.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.milaap.app.Adapter.FriendListAdapter;
import com.milaap.app.Matched.MatchUserAdapter;
import com.milaap.app.Matched.Users;
import com.milaap.app.Model.FriendList;
import com.milaap.app.Utils.Session;
import com.milaap.app.datingapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class IlikeFragment extends Fragment {
    private static final String TAG = "ilikefragement";

    //vars

   private ArrayList<FriendList> mFollowing;
//    private ListView mListView;
//    EditText search_name;
    RecyclerView recyclerView;
    private MatchUserAdapter mAdapter;
    private FriendListAdapter mAdapterr;
    List<Users> matchList = new ArrayList<>();
    Session session;
//    private FriendListAdapter mAdapter;
    private int mResults;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.listView_messages);
       mFollowing= new ArrayList<>();
       session= new Session(getContext());
       recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MatchUserAdapter(matchList, getContext());
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager1);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
       // recyclerView.setAdapter(mAdapter);

        prepareMatchData();
        getFollowing();
        return view;
    }

    private void prepareMatchData() {

        Users users = new Users("1", "Swati Tripathy", 21, "https://im.idiva.com/author/2018/Jul/shivani_chhabra-_author_s_profile.jpg", "Simple and beautiful Girl", "Acting", 200);
        matchList.add(users);
        users = new Users("2", "Ananaya Pandy", 20, "https://i0.wp.com/profilepicturesdp.com/wp-content/uploads/2018/06/beautiful-indian-girl-image-for-profile-picture-8.jpg", "cool Minded Girl", "Dancing", 800);
        matchList.add(users);
        users = new Users("3", "Anjali Kasyap", 22, "https://pbs.twimg.com/profile_images/967542394898952192/_M_eHegh_400x400.jpg", "Simple and beautiful Girl", "Singing", 400);
        matchList.add(users);
        users = new Users("4", "Preety Deshmukh", 19, "http://profilepicturesdp.com/wp-content/uploads/2018/07/fb-real-girls-dp-3.jpg", "dashing girl", "swiming", 1308);
        matchList.add(users);
        users = new Users("5", "Srutimayee Sen", 20, "https://dp.profilepics.in/profile_pictures/selfie-girls-profile-pics-dp/selfie-pics-dp-for-whatsapp-facebook-profile-25.jpg", "chulbuli nautankibaj ", "Drawing", 1200);
        matchList.add(users);
        users = new Users("6", "Dikshya Agarawal", 21, "https://pbs.twimg.com/profile_images/485824669732200448/Wy__CJwU.jpeg", "Simple and beautiful Girl", "Sleeping", 700);
        matchList.add(users);
        users = new Users("7", "Sudeshna Roy", 19, "https://talenthouse-res.cloudinary.com/image/upload/c_fill,f_auto,h_640,w_640/v1411380245/user-415406/submissions/hhb27pgtlp9akxjqlr5w.jpg", "Papa's Pari", "Art", 5000);
        matchList.add(users);

        mAdapter.notifyDataSetChanged();
    }

    private void getFollowing(){
        Log.d(TAG, "getFollowing: searching for following");
        mFollowing.clear();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.ilike))
                .child(session.getmobile());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: found user: " +
                            singleSnapshot.child(getString(R.string.field_user_id)).getValue());
                    FriendList friendList=new FriendList();
                    try {
                        mFollowing.add(singleSnapshot.getValue(FriendList.class));
                    }catch (NullPointerException e){
                        Log.e(TAG, "onDataChange: "+ e.getMessage() );
                    }


                }
                // mFollowing.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                //get the photos

                //  getPhotos();
                displaydetails();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void displaydetails(){
        try{
            Collections.sort(mFollowing, (t1, t2) -> t2.getDate_created().compareTo(t1.getDate_created()));
            mAdapterr = new FriendListAdapter(getActivity(), mFollowing,true);
            recyclerView.setAdapter(mAdapterr);
            mAdapter.notifyDataSetChanged();

        }catch (NullPointerException e){
            Log.e(TAG, "onDataChange: "+e.getMessage());
        }
    }
}
