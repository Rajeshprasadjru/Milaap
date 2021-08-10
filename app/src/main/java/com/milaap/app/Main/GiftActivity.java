package com.milaap.app.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.milaap.app.Adapter.GridEmojiAdapter;
import com.milaap.app.Model.Emoji;
import com.milaap.app.Model.Gift;
import com.milaap.app.Utils.HttpParse;
import com.milaap.app.Utils.Session;
import com.milaap.app.Utils.User;
import com.milaap.app.datingapp.R;
import com.milaap.app.datingapp.databinding.ActivityGiftBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class GiftActivity extends AppCompatActivity {
    private static final String TAG = "GiftActivity";
    ActivityGiftBinding binding;
    ArrayList<Gift> wordlist ;
    ArrayList<Gift> awordlist ;
    ArrayList<Emoji> swordlist ;
    ArrayList<Emoji> aswordlist ;
    Intent intent;
    int pos= 0;
    Session session;

    User user;
    String[] imglist= {
            "https://msquarebharat.com/milaap/app/images/new/airplane.png","https://msquarebharat.com/milaap/app/images/new/banana.png","https://msquarebharat.com/milaap/app/images/new/beer.png","https://msquarebharat.com/milaap/app/images/new/bell.png",
            "https://msquarebharat.com/milaap/app/images/new/car.png","https://msquarebharat.com/milaap/app/images/new/chili.png","https://msquarebharat.com/milaap/app/images/new/chocolate.png","https://msquarebharat.com/milaap/app/images/new/couple.png",
            "https://msquarebharat.com/milaap/app/images/new/crown.png","https://msquarebharat.com/milaap/app/images/new/diamond.png","https://msquarebharat.com/milaap/app/images/new/drink.png","https://msquarebharat.com/milaap/app/images/new/fruit_basket.png",
            "https://msquarebharat.com/milaap/app/images/new/heel.png","https://msquarebharat.com/milaap/app/images/new/kingdom.png","https://msquarebharat.com/milaap/app/images/new/lip.png","https://msquarebharat.com/milaap/app/images/new/lipstick.png",
            "https://msquarebharat.com/milaap/app/images/new/love.png","https://msquarebharat.com/milaap/app/images/new/lovee.png","https://msquarebharat.com/milaap/app/images/new/perfume.png","https://msquarebharat.com/milaap/app/images/new/purse.png",
            "https://msquarebharat.com/milaap/app/images/new/ring.png","https://msquarebharat.com/milaap/app/images/new/ringg.png","https://msquarebharat.com/milaap/app/images/new/rose_bouquet.png","https://msquarebharat.com/milaap/app/images/new/unicorn.png",

    };
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult ;
    String HttpURL = "https://msquarebharat.com/milaap/app/updategiftbalance.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGiftBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        wordlist = new ArrayList<>();
        awordlist = new ArrayList<>();
        swordlist = new ArrayList<>();
        aswordlist = new ArrayList<>();
        intent = getIntent();
        session = new Session(this);
        user= (User) getIntent().getSerializableExtra("user");
        Log.d(TAG, "onCreate: user "+user);
        binding.receivedbtn.setTextColor(getResources().getColor(R.color.red));
        binding.sentbtn.setTextColor(getResources().getColor(R.color.black));
        binding.receivedbtn.setOnClickListener(v -> {
            binding.receivedbtn.setTextColor(getResources().getColor(R.color.red));
            binding.sentbtn.setTextColor(getResources().getColor(R.color.black));

            binding.receivedrecycleview.setVisibility(View.VISIBLE);
            binding.sendrecycleview.setVisibility(View.GONE);
            binding.sendgift.setVisibility(View.VISIBLE);
        });
        binding.sentbtn.setOnClickListener(v -> {
            binding.receivedbtn.setTextColor(getResources().getColor(R.color.black));
            binding.sentbtn.setTextColor(getResources().getColor(R.color.red));

            binding.receivedrecycleview.setVisibility(View.GONE);
            binding.sendrecycleview.setVisibility(View.VISIBLE);
            binding.sendgift.setVisibility(View.GONE);
        });
        binding.sendgift.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this,android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.giftsendpopup);
            dialog.setCanceledOnTouchOutside(true);
           // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ArrayList<Gift> bwordlist = new ArrayList<>();
            // dialog.setTitle(getItem(position).getAddress());
            bwordlist.add(new Gift(imglist[0],"10"));
            bwordlist.add(new Gift(imglist[1],"20"));
            bwordlist.add(new Gift(imglist[2],"30"));
            bwordlist.add(new Gift(imglist[3],"50"));
            bwordlist.add(new Gift(imglist[4],"40"));
            bwordlist.add(new Gift(imglist[5],"100"));
            bwordlist.add(new Gift(imglist[6],"70"));
            bwordlist.add(new Gift(imglist[7],"150"));
            bwordlist.add(new Gift(imglist[8],"150"));
            bwordlist.add(new Gift(imglist[9],"320"));
            bwordlist.add(new Gift(imglist[10],"142"));
            bwordlist.add(new Gift(imglist[11],"325"));
            bwordlist.add(new Gift(imglist[12],"652"));
            bwordlist.add(new Gift(imglist[13],"25"));
            bwordlist.add(new Gift(imglist[14],"60"));
            bwordlist.add(new Gift(imglist[15],"256"));
            bwordlist.add(new Gift(imglist[16],"20"));
            bwordlist.add(new Gift(imglist[17],"20"));
            bwordlist.add(new Gift(imglist[18],"20"));
            bwordlist.add(new Gift(imglist[19],"19"));
            bwordlist.add(new Gift(imglist[20],"256"));
            bwordlist.add(new Gift(imglist[21],"41"));
            bwordlist.add(new Gift(imglist[22],"52"));
            bwordlist.add(new Gift(imglist[23],"255"));
            ImageView simage= dialog.findViewById(R.id.gift);
            TextView stext= dialog.findViewById(R.id.giftcount);


            GridView gridView= dialog.findViewById(R.id.receivedrecycleview);
            GridEmojiAdapter adapter = new GridEmojiAdapter(GiftActivity.this,R.layout.giftitem,
                    "", bwordlist);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(GiftActivity.this, "", Toast.LENGTH_SHORT).show();
                    Glide.with(GiftActivity.this).load(bwordlist.get(position).getImg()).into(simage);
                    stext.setText(bwordlist.get(position).getCount());
                    pos= position;
                }
            });
            Button btn= dialog.findViewById(R.id.sendgift);
            btn.setOnClickListener(v1 -> {
              if(pos==0){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("airplane").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                 UpdateFollowUpDate(session.getmobile(),"","Gift Sent","Airplane","10","");

              }else if(pos==1){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("banana").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","banana","20","");
              } else if(pos==2){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("beer").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","beer","20","");
              } else if(pos==3){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("bell").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","bell","20","");
              } else if(pos==4){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("car").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","car","20","");
              }else if(pos==5){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("chili").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","chili","20","");
              } else if(pos==6){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("chocolate").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","chocolate","20","");
              } else if(pos==7){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("couple").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","couple","20","");
              } else if(pos==8){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("crown").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","crown","20","");
              } else if(pos==9){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("diamond").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","diamond","20","");
              } else if(pos==10){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("drink").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","drink","20","");
              } else if(pos==11){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("friut_basket").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","fruit_basket","20","");
              } else if(pos==12){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("heel").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","heel","20","");
              } else if(pos==13){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("kingdom").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","kingdom","20","");
              } else if(pos==14){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("lip").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","lip","20","");
              } else if(pos==15){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("lipstick").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","lipstick","20","");
              } else if(pos==16){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("love").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","love","20","");
              } else if(pos==17){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("perfume").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","perfume","20","");
              } else if(pos==18){
                  FirebaseDatabase.getInstance().getReference()
                          .child("Gifts")
                          .child(user.getPhone_number())
                          .child("perfume").child(session.getmobile()).child(getString(R.string.field_user_id))
                          .setValue(session.getmobile());
                  UpdateFollowUpDate(session.getmobile(),"","Gift Sent","perfume","20","");
              }else if(pos==19){

              }else FirebaseDatabase.getInstance().getReference()
                      .child("Gifts")
                      .child(user.getPhone_number())
                      .child("purse").child(session.getmobile()).child(getString(R.string.field_user_id))
                      .setValue(session.getmobile());
                UpdateFollowUpDate(session.getmobile(),"","Gift Sent","purse","20","");
               if(pos==20){
                   FirebaseDatabase.getInstance().getReference()
                           .child("Gifts")
                           .child(user.getPhone_number())
                           .child("ring").child(session.getmobile()).child(getString(R.string.field_user_id))
                           .setValue(session.getmobile());
                   UpdateFollowUpDate(session.getmobile(),"","Gift Sent","ring","20","");
              }else if(pos==21){
                   FirebaseDatabase.getInstance().getReference()
                           .child("Gifts")
                           .child(user.getPhone_number())
                           .child("ringg").child(session.getmobile()).child(getString(R.string.field_user_id))
                           .setValue(session.getmobile());
                   UpdateFollowUpDate(session.getmobile(),"","Gift Sent","ringg","20","");
              }else if(pos==22){
                    FirebaseDatabase.getInstance().getReference()
                            .child("Gifts")
                            .child(user.getPhone_number())
                            .child("rose_bouquet").child(session.getmobile()).child(getString(R.string.field_user_id))
                            .setValue(session.getmobile());
                   UpdateFollowUpDate(session.getmobile(),"","Gift Sent","rose_bouquet","20","");
              } else if(pos==23){
                   FirebaseDatabase.getInstance().getReference()
                           .child("Gifts")
                           .child(user.getPhone_number())
                           .child("unicorn").child(session.getmobile()).child(getString(R.string.field_user_id))
                           .setValue(session.getmobile());
                   UpdateFollowUpDate(session.getmobile(),"","Gift Sent","unicorn","20","");
               }
            });
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

//        StateImageAdapter adapters = new StateImageAdapter(MainActivity.this,R.layout.personitem,
//                "", rowItems);

            //binding.sendrecycleview.setAdapter(adapter);
            dialog.show();
            ImageView imageView=   dialog.findViewById(R.id.closebtn);
            imageView.setOnClickListener(v1 -> dialog.dismiss());
        });
        setDaata();
        getFollowing();
        getFFollowing();
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
//        GridEmojiAdapter adapter = new GridEmojiAdapter(this,R.layout.giftitem,
//                "", wordlist);
////        StateImageAdapter adapters = new StateImageAdapter(MainActivity.this,R.layout.personitem,
////                "", rowItems);
//
//        binding.receivedrecycleview.setAdapter(adapter);

    }
    private void getFollowing(){
        Log.d(TAG, "getFollowing: searching for friends");



        //coment
        wordlist.add(new Gift(imglist[0],"0"));
        wordlist.add(new Gift(imglist[1],"0"));
        wordlist.add(new Gift(imglist[2],"0"));
        wordlist.add(new Gift(imglist[3],"0"));
        wordlist.add(new Gift(imglist[4],"0"));
        wordlist.add(new Gift(imglist[5],"0"));
        wordlist.add(new Gift(imglist[6],"0"));
        wordlist.add(new Gift(imglist[7],"0"));
        wordlist.add(new Gift(imglist[8],"0"));
        wordlist.add(new Gift(imglist[9],"0"));
        wordlist.add(new Gift(imglist[10],"0"));
        wordlist.add(new Gift(imglist[11],"0"));
        wordlist.add(new Gift(imglist[12],"0"));
        wordlist.add(new Gift(imglist[13],"0"));
        wordlist.add(new Gift(imglist[14],"0"));
        wordlist.add(new Gift(imglist[15],"0"));
        wordlist.add(new Gift(imglist[16],"0"));
        wordlist.add(new Gift(imglist[17],"0"));
        wordlist.add(new Gift(imglist[18],"0"));
        wordlist.add(new Gift(imglist[19],"0"));
        wordlist.add(new Gift(imglist[20],"0"));
        wordlist.add(new Gift(imglist[21],"0"));
        wordlist.add(new Gift(imglist[22],"0"));
        wordlist.add(new Gift(imglist[23],"0"));
        GridEmojiAdapter adapter = new GridEmojiAdapter(GiftActivity.this,R.layout.giftitem,
                                "", wordlist);
////        StateImageAdapter adapters = new StateImageAdapter(MainActivity.this,R.layout.personitem,
////                "", rowItems);
//
                        binding.receivedrecycleview.setAdapter(adapter);
        //commeny
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        Query query = reference
//                .child("Gifts")
//                .child(user.getPhone_number());
//        query.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    try{
//                        wordlist.clear();
//                        swordlist.add(dataSnapshot.getValue(Emoji.class));
//                        Emoji emoji =dataSnapshot.getValue(Emoji.class);
//                        assert emoji != null;
//                        wordlist.add(new Gift(imglist[0],emoji.getAirplane()));
//                        wordlist.add(new Gift(imglist[1],emoji.getBanana()));
//                        wordlist.add(new Gift(imglist[2],emoji.getBeer()));
//                        wordlist.add(new Gift(imglist[3],emoji.getBell()));
//                        wordlist.add(new Gift(imglist[4],emoji.getCar()));
//                        wordlist.add(new Gift(imglist[5],emoji.getChili()));
//                        wordlist.add(new Gift(imglist[6],emoji.getChocolate()));
//                        wordlist.add(new Gift(imglist[7],emoji.getCouple()));
//                        wordlist.add(new Gift(imglist[8],emoji.getCrown()));
//                        wordlist.add(new Gift(imglist[9],emoji.getDiamond()));
//                        wordlist.add(new Gift(imglist[10],emoji.getDrink()));
//                        wordlist.add(new Gift(imglist[11],emoji.getFriut_basket()));
//                        wordlist.add(new Gift(imglist[12],emoji.getHeel()));
//                        wordlist.add(new Gift(imglist[13],emoji.getKingdom()));
//                        wordlist.add(new Gift(imglist[14],emoji.getLip()));
//                        wordlist.add(new Gift(imglist[15],emoji.getLipstick()));
//                        wordlist.add(new Gift(imglist[16],emoji.getLove()));
//                        wordlist.add(new Gift(imglist[17],emoji.getLovee()));
//                        wordlist.add(new Gift(imglist[18],emoji.getPerfume()));
//                        wordlist.add(new Gift(imglist[19],emoji.getPurse()));
//                        wordlist.add(new Gift(imglist[20],emoji.getRing()));
//                        wordlist.add(new Gift(imglist[21],emoji.getRingg()));
//                        wordlist.add(new Gift(imglist[22],emoji.getRose_bouquet()));
//                        wordlist.add(new Gift(imglist[23],emoji.getUnicorn()));
////                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
////                    Log.d(TAG, "onDataChange: found user: " +
////                            singleSnapshot.child(getString(R.string.field_user_id)).getValue());
////                    try{
////                       // Emoji gift= new Emoji(singleSnapshot.getValue(Emoji.class));
////                        swordlist.add(singleSnapshot.getValue(Emoji.class));
////                    }catch (NullPointerException e){
////                        Log.e(TAG, "onDataChange: "+e.getMessage() );
////                    }
//
//
//                        // }
//                        GridEmojiAdapter adapter = new GridEmojiAdapter(GiftActivity.this,R.layout.giftitem,
//                                "", wordlist);
////        StateImageAdapter adapters = new StateImageAdapter(MainActivity.this,R.layout.personitem,
////                "", rowItems);
//
//                        binding.receivedrecycleview.setAdapter(adapter);
//                    } catch (NullPointerException| AssertionError e){
//                        Log.d(TAG, "onDataChange: "+e);
//
//                    }
//                } else {
//                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//                    Emoji emoji = new Emoji("0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0");
//                    reference.child("Gifts").child(user.getPhone_number()).setValue(emoji);
//                }
//
//
//              //  mFollowing.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                //get the photos
//                getPhotos();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
    private void getFFollowing(){
        Log.d(TAG, "getFollowing: searching for friends");
        awordlist.add(new Gift(imglist[0],"0"));
        awordlist.add(new Gift(imglist[1],"0"));
        awordlist.add(new Gift(imglist[2],"0"));
        awordlist.add(new Gift(imglist[3],"0"));
        awordlist.add(new Gift(imglist[4],"0"));
        awordlist.add(new Gift(imglist[5],"0"));
        awordlist.add(new Gift(imglist[6],"0"));
        awordlist.add(new Gift(imglist[7],"0"));
        awordlist.add(new Gift(imglist[8],"0"));
        awordlist.add(new Gift(imglist[9],"0"));
        awordlist.add(new Gift(imglist[10],"0"));
        awordlist.add(new Gift(imglist[11],"0"));
        awordlist.add(new Gift(imglist[12],"0"));
        awordlist.add(new Gift(imglist[13],"0"));
        awordlist.add(new Gift(imglist[14],"0"));
        awordlist.add(new Gift(imglist[15],"0"));
        awordlist.add(new Gift(imglist[16],"0"));
        awordlist.add(new Gift(imglist[17],"0"));
        awordlist.add(new Gift(imglist[18],"0"));
        awordlist.add(new Gift(imglist[19],"0"));
        awordlist.add(new Gift(imglist[20],"0"));
        awordlist.add(new Gift(imglist[21],"0"));
        awordlist.add(new Gift(imglist[22],"0"));
        awordlist.add(new Gift(imglist[23],"0"));
        GridEmojiAdapter adapter = new GridEmojiAdapter(GiftActivity.this,R.layout.giftitem,
                "", awordlist);
////        StateImageAdapter adapters = new StateImageAdapter(MainActivity.this,R.layout.personitem,
////                "", rowItems);
//
        binding.sendrecycleview.setAdapter(adapter);


//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        Query query = reference
//                .child("Gifts")
//                .child(session.getmobile());
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                aswordlist.add(dataSnapshot.getValue(Emoji.class));
//                Emoji emoji =dataSnapshot.getValue(Emoji.class);
//                assert emoji != null;
//                awordlist.add(new Gift(imglist[0],emoji.getAirplane()));
//                awordlist.add(new Gift(imglist[1],emoji.getBanana()));
//                awordlist.add(new Gift(imglist[2],emoji.getBeer()));
//                awordlist.add(new Gift(imglist[3],emoji.getBell()));
//                awordlist.add(new Gift(imglist[4],emoji.getCar()));
//                awordlist.add(new Gift(imglist[5],emoji.getChili()));
//                awordlist.add(new Gift(imglist[6],emoji.getChocolate()));
//                awordlist.add(new Gift(imglist[7],emoji.getCouple()));
//                awordlist.add(new Gift(imglist[8],emoji.getCrown()));
//                awordlist.add(new Gift(imglist[9],emoji.getDiamond()));
//                awordlist.add(new Gift(imglist[10],emoji.getDrink()));
//                awordlist.add(new Gift(imglist[11],emoji.getFriut_basket()));
//                awordlist.add(new Gift(imglist[12],emoji.getHeel()));
//                awordlist.add(new Gift(imglist[13],emoji.getKingdom()));
//                awordlist.add(new Gift(imglist[14],emoji.getLip()));
//                awordlist.add(new Gift(imglist[15],emoji.getLipstick()));
//                awordlist.add(new Gift(imglist[16],emoji.getLove()));
//                awordlist.add(new Gift(imglist[17],emoji.getLovee()));
//                awordlist.add(new Gift(imglist[18],emoji.getPerfume()));
//                awordlist.add(new Gift(imglist[19],emoji.getPurse()));
//                awordlist.add(new Gift(imglist[20],emoji.getRing()));
//                awordlist.add(new Gift(imglist[21],emoji.getRingg()));
//                awordlist.add(new Gift(imglist[22],emoji.getRose_bouquet()));
//                awordlist.add(new Gift(imglist[23],emoji.getUnicorn()));
////                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
////                    Log.d(TAG, "onDataChange: found user: " +
////                            singleSnapshot.child(getString(R.string.field_user_id)).getValue());
////                    try{
////                       // Emoji gift= new Emoji(singleSnapshot.getValue(Emoji.class));
////                        swordlist.add(singleSnapshot.getValue(Emoji.class));
////                    }catch (NullPointerException e){
////                        Log.e(TAG, "onDataChange: "+e.getMessage() );
////                    }
//
//
//                // }
//                GridEmojiAdapter adapter = new GridEmojiAdapter(GiftActivity.this,R.layout.giftitem,
//                        "", awordlist);
////        StateImageAdapter adapters = new StateImageAdapter(MainActivity.this,R.layout.personitem,
////                "", rowItems);
//
//                binding.sendrecycleview.setAdapter(adapter);
//                //  mFollowing.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                //get the photos
//                getPhotos();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
    public void UpdateFollowUpDate(final String emp_id, final String pdate,final String ptype,final String pstatus,final String amount,final String edate){

        class UserRegisterFunctionClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                // progressDialog = ProgressDialog.show(CreateCustomerActivity.this,"Registering...",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                // progressDialog.dismiss();
                Log.d(TAG, "onPostExecute: "+httpResponseMsg);

                Toast.makeText(GiftActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                if (httpResponseMsg.equals("Payment Successfully")){

                    //  Toast.makeText(CustomerDetailActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(GiftActivity.this, MainActivity.class));
                   finish();
                } else {
                    Toast.makeText(GiftActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("id",params[0]);
                hashMap.put("pdate",params[1]);
                hashMap.put("ptype",params[2]);

                hashMap.put("pstatus",params[3]);
                hashMap.put("amount",params[4]);
                hashMap.put("edate",params[5]);
//                hashMap.put("city",params[6]);
//                hashMap.put("pincode",params[7]);
//                hashMap.put("remark",params[8]);
//                hashMap.put("followupdate",params[9]);
//                hashMap.put("status",params[10]);
//                hashMap.put("aadharcard",params[11]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(emp_id,pdate,ptype,pstatus,amount,edate);
    }
    private void getPhotos() {


    }
}