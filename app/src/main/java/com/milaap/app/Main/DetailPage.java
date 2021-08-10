package com.milaap.app.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;


import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.milaap.app.Adapter.EmojiAdapter;
import com.milaap.app.Adapter.GridEmojiAdapter;
import com.milaap.app.Adapter.SliderAdapter;
import com.milaap.app.Model.Emoji;
import com.milaap.app.Model.FriendList;
import com.milaap.app.Model.Gift;
import com.milaap.app.Model.SliderData;
import com.milaap.app.Utils.Session;
import com.milaap.app.datingapp.R;
import com.milaap.app.Utils.User;
import com.milaap.app.datingapp.databinding.ActivityDetailPageBinding;
import com.smarteist.autoimageslider.SliderView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DetailPage extends AppCompatActivity {
    private static final String TAG = "DetailPage";
    ActivityDetailPageBinding binding;
    ArrayList<Gift> wordlist;
    ArrayList<Emoji> swordlist;
    User user;
    String img= "https://pbs.twimg.com/profile_images/967542394898952192/_M_eHegh_400x400.jpg";
    String url1 = "https://image.freepik.com/free-vector/dating-app-banner-mobile-phone-with-hearts-online-dating-social-networks_136277-525.jpg";
    String url2 = "https://image.freepik.com/free-vector/online-dating-app-concept_52683-37139.jpg";
    String url3 = "https://cdn8.quackquack.in/index_banner_imgv1.png";
    Session session;
    String[] imglist= {
            "https://msquarebharat.com/milaap/app/images/new/airplane.png","https://msquarebharat.com/milaap/app/images/new/banana.png","https://msquarebharat.com/milaap/app/images/new/beer.png","https://msquarebharat.com/milaap/app/images/new/bell.png",
            "https://msquarebharat.com/milaap/app/images/new/car.png","https://msquarebharat.com/milaap/app/images/new/chili.png","https://msquarebharat.com/milaap/app/images/new/chocolate.png","https://msquarebharat.com/milaap/app/images/new/couple.png",
            "https://msquarebharat.com/milaap/app/images/new/crown.png","https://msquarebharat.com/milaap/app/images/new/diamond.png","https://msquarebharat.com/milaap/app/images/new/drink.png","https://msquarebharat.com/milaap/app/images/new/fruit_basket.png",
            "https://msquarebharat.com/milaap/app/images/new/heel.png","https://msquarebharat.com/milaap/app/images/new/kingdom.png","https://msquarebharat.com/milaap/app/images/new/lip.png","https://msquarebharat.com/milaap/app/images/new/lipstick.png",
            "https://msquarebharat.com/milaap/app/images/new/love.png","https://msquarebharat.com/milaap/app/images/new/lovee.png","https://msquarebharat.com/milaap/app/images/new/perfume.png","https://msquarebharat.com/milaap/app/images/new/purse.png",
            "https://msquarebharat.com/milaap/app/images/new/ring.png","https://msquarebharat.com/milaap/app/images/new/ringg.png","https://msquarebharat.com/milaap/app/images/new/rose_bouquet.png","https://msquarebharat.com/milaap/app/images/new/unicorn.png",

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityDetailPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backBtn.setOnClickListener(v -> {
            onBackPressed();
        });
        session= new Session(this);
        binding.giftrecycleview.setHasFixedSize(true);
        binding.giftrecycleview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        wordlist= new ArrayList<>();
        swordlist= new ArrayList<>();
         user= (User) getIntent().getSerializableExtra("User");
        Log.d(TAG, "onCreate: "+ user);
        binding.name.setText(user.getUsername());
        binding.likebtn.setOnClickListener(v -> {
            startActivity( new Intent( this, PaymentActivity.class));
        });
        binding.videoCalBtn.setOnClickListener(v -> {
            startActivity( new Intent( this, PaymentActivity.class));

//            Dialog dialog = new Dialog(this,android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(R.layout.paymentpopup);
//            dialog.setCanceledOnTouchOutside(true);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            // dialog.setTitle(getItem(position).getAddress());
//            dialog.show();
//            ImageView imageView=   dialog.findViewById(R.id.closebtn);
//            imageView.setOnClickListener(v1 -> dialog.dismiss());
//
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
//            SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
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
        binding.commentbtn.setOnClickListener(v -> {
            startActivity( new Intent( this, PaymentActivity.class));
//            Dialog dialog = new Dialog(this,android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
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
//            SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
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
        binding.location.setText(user.getPlace());
        binding.description.setText(user.getDescription());
        binding.prefer.setText(user.getPreferSex());
//        binding.location.setText(user.getLatitude());
//        binding.location.setText(user.getLatitude());
//        binding.location.setText(user.getLatitude());
        if (user.isFishing()){
            binding.Fishing.setVisibility(View.VISIBLE);
        }
        if (user.isTravel()){
            binding.Travel.setVisibility(View.VISIBLE);
        }
        if (user.isMusic()){
            binding.Music.setVisibility(View.VISIBLE);
        }
        if (user.isSports()){
            binding.sports.setVisibility(View.VISIBLE);
        }

        String imgg= user.getProfileImageUrl();
        String[] arrayString = imgg.split(",");

        String email = arrayString[0];
        // String title = arrayString[1];
        email=  email.replaceAll("\"", "");
        email=  email.replaceAll("\\[", "");
        email=  email.replaceAll("]", "");
        String iurl= "https://msquarebharat.com/milaap/image/banner_top/"+email;
        Log.d(TAG, "getView: imgurl "+email);
        Glide.with(this).load(iurl).into(binding.profileImage);
        binding.viewmore.setOnClickListener(v -> {
            Intent intent= new Intent(this,GiftActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
        });
        setDaata();
        getlike();
       isFriend();
       binding.like.setOnClickListener(v -> {
           String timestamp= getTimestamp();
           FriendList friendList=new FriendList(timestamp,user.getPhone_number(),"offline",timestamp);
           FirebaseDatabase.getInstance().getReference()
                   .child(getString(R.string.dbname_friend_list))
                   .child(session.getmobile())
                   .child(user.getPhone_number())
                   .setValue(friendList);
           FirebaseDatabase.getInstance().getReference()
                   .child(getString(R.string.ilike))
                   .child(session.getmobile())
                   .child(user.getPhone_number())
                   .setValue(friendList);
           FriendList friendList1=new FriendList(timestamp,session.getmobile(),"offline",timestamp);
           FirebaseDatabase.getInstance().getReference()
                   .child(getString(R.string.dbname_friend_list))
                   .child(user.getPhone_number())
                   .child(session.getmobile())
                   .setValue(friendList1);
           FirebaseDatabase.getInstance().getReference()
                   .child(getString(R.string.likedme))
                   .child(user.getPhone_number())
                   .child(session.getmobile())
                   .setValue(friendList1);
           binding.like.setImageResource(R.drawable.ic_heart_red);
       });

    }

    private void getlike() {

    }
    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        return sdf.format(new Date());
    }
    private void isFriend(){
        Log.d(TAG, "isFollowing: checking if following this users.");


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(getString(R.string.dbname_friend_list))
                .child(session.getmobile())
                .orderByChild(getString(R.string.field_user_id)).equalTo(user.getPhone_number());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: found user:" + singleSnapshot.getValue());
                    binding.like.setImageResource(R.drawable.ic_heart_red);
                 //  binding.like.setImageDrawable(R.drawable.ic_baseline_favorite_24_red);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setDaata() {
//        wordlist.add(new Gift(R.drawable.logo,"0"));
//        wordlist.add(new Gift(R.drawable.drink,"0"));
//        wordlist.add(new Gift(R.drawable.car,"0"));
//        wordlist.add(new Gift(R.drawable.heel,"0"));
//        wordlist.add(new Gift(R.drawable.lovee,"0"));
//        wordlist.add(new Gift(R.drawable.drink,"0"));
//        wordlist.add(new Gift(R.drawable.car,"0"));
//        wordlist.add(new Gift(R.drawable.heel,"0"));
//        wordlist.add(new Gift(R.drawable.lovee,"0"));
//        wordlist.add(new Gift(R.drawable.logo,"0"));
//        wordlist.add(new Gift(R.drawable.logo,"0"));
//        wordlist.add(new Gift(R.drawable.logo,"0"));
//        wordlist.add(new Gift(R.drawable.logo,"0"));
//        wordlist.add(new Gift(R.drawable.logo,"0"));
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child("Gifts")
                .child(session.getmobile());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                swordlist.add(dataSnapshot.getValue(Emoji.class));
                Emoji emoji =dataSnapshot.getValue(Emoji.class);
                assert emoji != null;
                wordlist.add(new Gift(imglist[0],emoji.getAirplane()));
                wordlist.add(new Gift(imglist[1],emoji.getBanana()));
                wordlist.add(new Gift(imglist[2],emoji.getBeer()));
                wordlist.add(new Gift(imglist[3],emoji.getBell()));
                wordlist.add(new Gift(imglist[4],emoji.getCar()));
                wordlist.add(new Gift(imglist[5],emoji.getChili()));
                wordlist.add(new Gift(imglist[6],emoji.getChocolate()));
                wordlist.add(new Gift(imglist[7],emoji.getCouple()));
                wordlist.add(new Gift(imglist[8],emoji.getCrown()));
                wordlist.add(new Gift(imglist[9],emoji.getDiamond()));
                wordlist.add(new Gift(imglist[10],emoji.getDrink()));
                wordlist.add(new Gift(imglist[11],emoji.getFriut_basket()));
                wordlist.add(new Gift(imglist[12],emoji.getHeel()));
                wordlist.add(new Gift(imglist[13],emoji.getKingdom()));
                wordlist.add(new Gift(imglist[14],emoji.getLip()));
                wordlist.add(new Gift(imglist[15],emoji.getLipstick()));
                wordlist.add(new Gift(imglist[16],emoji.getLove()));
                wordlist.add(new Gift(imglist[17],emoji.getLovee()));
                wordlist.add(new Gift(imglist[18],emoji.getPerfume()));
                wordlist.add(new Gift(imglist[19],emoji.getPurse()));
                wordlist.add(new Gift(imglist[20],emoji.getRing()));
                wordlist.add(new Gift(imglist[21],emoji.getRingg()));
                wordlist.add(new Gift(imglist[22],emoji.getRose_bouquet()));
                wordlist.add(new Gift(imglist[23],emoji.getUnicorn()));
//                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
//                    Log.d(TAG, "onDataChange: found user: " +
//                            singleSnapshot.child(getString(R.string.field_user_id)).getValue());
//                    try{
//                       // Emoji gift= new Emoji(singleSnapshot.getValue(Emoji.class));
//                        swordlist.add(singleSnapshot.getValue(Emoji.class));
//                    }catch (NullPointerException e){
//                        Log.e(TAG, "onDataChange: "+e.getMessage() );
//                    }


                // }
                EmojiAdapter adapter = new EmojiAdapter(DetailPage.this, wordlist, false);
//        StateImageAdapter adapters = new StateImageAdapter(MainActivity.this,R.layout.personitem,
//                "", rowItems);

                binding.giftrecycleview.setAdapter(adapter);
                //  mFollowing.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                //get the photos
               // getPhotos();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        EmojiAdapter adapter = new EmojiAdapter(this, wordlist, false);
////        StateImageAdapter adapters = new StateImageAdapter(MainActivity.this,R.layout.personitem,
////                "", rowItems);
//
//        binding.giftrecycleview.setAdapter(adapter);

    }
}