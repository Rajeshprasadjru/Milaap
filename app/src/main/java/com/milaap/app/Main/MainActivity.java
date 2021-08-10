package com.milaap.app.Main;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.milaap.app.Adapter.GridImageAdapter;
import com.milaap.app.Adapter.SliderAdapter;
import com.milaap.app.Adapter.WordListAdapter;
import com.milaap.app.Fragment.GlobalFragment;
import com.milaap.app.Fragment.HomeFragment;
import com.milaap.app.Fragment.StateFragment;
import com.milaap.app.Model.SliderData;
import com.milaap.app.Notifications.Token;
import com.milaap.app.datingapp.R;
import com.milaap.app.Utils.PulsatorLayout;
import com.milaap.app.Utils.SectionPageAdapter;
import com.milaap.app.Utils.Session;
import com.milaap.app.Utils.TopNavigationViewHelper;
import com.milaap.app.Utils.User;
import com.razorpay.Checkout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;


/**
 * DatingApp
 * https://github.com/quintuslabs/DatingApp
 * Created on 25-sept-2018.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM = 2;
    final private int MY_PERMISSIONS_REQUEST_LOCATION = 123;
    ListView listView;
    ImageView vip;
    ArrayList<Cards> rowItems;
    FrameLayout cardFrame, moreFrame;
    private Context mContext = MainActivity.this;
    private NotificationHelper mNotificationHelper;
    private Cards cards_data[];
    private PhotoAdapter arrayAdapter;
    GridImageAdapter adapter1;
    SectionPageAdapter adapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    Session session;
    ArrayList<User> clist;
    TextView nearby;
    String url1 = "https://image.freepik.com/free-vector/dating-app-banner-mobile-phone-with-hearts-online-dating-social-networks_136277-525.jpg";
    String url2 = "https://image.freepik.com/free-vector/online-dating-app-concept_52683-37139.jpg";
    String url3 = "https://cdn8.quackquack.in/index_banner_imgv1.png";
    String[] country = {"State",
            "Global",
            "Andhra Pradesh",
            "Arunachal Pradesh",
            "Assam",
            "Bihar",
            "Chhattisgarh",
            "Goa",
            "Gujarat",
            "Haryana",
            "Himachal Pradesh",
            "Jammu and Kashmir",
            "Jharkhand",
            "Karnataka",
            "Kerala",
            "Madhya Pradesh",
            "Maharashtra",
            "Manipur",
            "Meghalaya",
            "Mizoram",
            "Nagaland",
            "Odisha",
            "Punjab",
            "Rajasthan",
            "Sikkim",
            "Tamil Nadu",
            "Telangana",
            "Tripura",
            "Uttarakhand",
            "Uttar Pradesh",
            "West Bengal",
            "Andaman and Nicobar Islands",
            "Chandigarh",
            "Dadra and Nagar Haveli",
            "Daman and Diu",
            "Delhi",
            "Lakshadweep",
            "Puducherry",
             "USA",
            "Bhutan",
            "Pakistan",
            "Bangladesh",
            "Afghanistan"};
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cardFrame = findViewById(R.id.card_frame);
        moreFrame = findViewById(R.id.more_frame);
        viewPager = (ViewPager) findViewById(R.id.viewpager_containers);
        vip = findViewById(R.id.vipbtn);
        // start pulsator
        PulsatorLayout mPulsator = findViewById(R.id.pulsator);
        mPulsator.start();
        mNotificationHelper = new NotificationHelper(this);


        setupTopNavigationView();
        session= new Session(this);

        rowItems = new ArrayList<Cards>();
        nearby = findViewById(R.id.nearby);
        nearby.setOnClickListener(v -> {
            startActivity(new Intent(this,NearByActivity.class));
        });
        clist = new ArrayList<>();
        Cards cards = new Cards("1", "Swati Tripathy", 21, "https://im.idiva.com/author/2018/Jul/shivani_chhabra-_author_s_profile.jpg", "Simple and beautiful Girl", "Acting", 200);
        rowItems.add(cards);
        cards = new Cards("2", "Ananaya Pandy", 20, "https://pbs.twimg.com/profile_images/967542394898952192/_M_eHegh_400x400.jpg", "cool Minded Girl", "Dancing", 800);
        rowItems.add(cards);
        cards = new Cards("3", "Anjali Kasyap", 22, "https://pbs.twimg.com/profile_images/967542394898952192/_M_eHegh_400x400.jpg", "Simple and beautiful Girl", "Singing", 400);
        rowItems.add(cards);
        cards = new Cards("4", "Preety Deshmukh", 19, "https://pbs.twimg.com/profile_images/485824669732200448/Wy__CJwU.jpeg", "dashing girl", "swiming", 1308);
        rowItems.add(cards);
        cards = new Cards("5", "Srutimayee Sen", 20, "https://dp.profilepics.in/profile_pictures/selfie-girls-profile-pics-dp/selfie-pics-dp-for-whatsapp-facebook-profile-25.jpg", "chulbuli nautankibaj ", "Drawing", 1200);
        rowItems.add(cards);
        cards = new Cards("6", "Dikshya Agarawal", 21, "https://pbs.twimg.com/profile_images/485824669732200448/Wy__CJwU.jpeg", "Simple and beautiful Girl", "Sleeping", 700);
        rowItems.add(cards);
        cards = new Cards("7", "Sudeshna Roy", 19, "https://talenthouse-res.cloudinary.com/image/upload/c_fill,f_auto,h_640,w_640/v1411380245/user-415406/submissions/hhb27pgtlp9akxjqlr5w.jpg", "Papa's Pari", "Art", 5000);
        rowItems.add(cards);
        cards = new Cards("8", "Ananaya Pandy", 20, "https://wallpapercave.com/wp/wp4809171.jpg", "cool Minded Girl", "Dancing", 800);
        rowItems.add(cards);
        cards = new Cards("9", "Anjali Kasyap", 22, "https://pbs.twimg.com/profile_images/967542394898952192/_M_eHegh_400x400.jpg", "Simple and beautiful Girl", "Singing", 400);
        rowItems.add(cards);
        cards = new Cards("10", "Preety Deshmukh", 19, "https://wallpapercave.com/wp/wp4809171.jpg", "dashing girl", "swiming", 1308);
        rowItems.add(cards);
        cards = new Cards("11", "Srutimayee Sen", 20, "https://dp.profilepics.in/profile_pictures/selfie-girls-profile-pics-dp/selfie-pics-dp-for-whatsapp-facebook-profile-25.jpg", "chulbuli nautankibaj ", "Drawing", 1200);
        rowItems.add(cards);
        cards = new Cards("12", "Dikshya Agarawal", 21, "https://pbs.twimg.com/profile_images/485824669732200448/Wy__CJwU.jpeg", "Simple and beautiful Girl", "Sleeping", 700);
        rowItems.add(cards);
        cards = new Cards("13", "Sudeshna Roy", 19, "https://wallpapercave.com/wp/wp4809171.jpg", "Papa's Pari", "Art", 5000);
        rowItems.add(cards);

       // arrayAdapter = new PhotoAdapter(this, R.layout.item, rowItems);
        setdata();
        checkRowItem();
        updateSwipeCard();
        updatedeliverylist();
        vip.setOnClickListener(v -> {
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
//            Button btn= dialog.findViewById(R.id.button);
//            btn.setOnClickListener(v1 -> {
//             startPayment("500");
//            });
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
     //   getJSON();
        setUpViewPager();
        viewPager.setCurrentItem(0);
        updateToken(FirebaseInstanceId.getInstance().getToken());
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
    }

    private void setdata() {


    }
    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(session.getmobile()).setValue(token1);
    }
    public void startPayment(String amount) {
        //amount="799";
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Milaap");
            options.put("description", "Subscription Fee");
            options.put("send_sms_hash",true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", amount+"00");
            JSONObject preFill = new JSONObject();
            preFill.put("email", session.getemail());
            preFill.put("contact", session.getmobile());

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String ItemName = intent.getStringExtra("item");

            Toast.makeText(MainActivity.this,ItemName +" " ,Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "onReceive: "+country);
//            Log.d(TAG, "onReceive: "+country[Arrays.asList(country).indexOf(ItemName)]);
//            Log.d(TAG, "onReceive: "+ItemName);
//            Log.d(TAG, "onReceive: "+Arrays.asList(country).indexOf(ItemName));


            viewPager.setCurrentItem( (Arrays.asList(country).indexOf(ItemName)));
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setUpViewPager(){
        adapter =new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragement(new HomeFragment());
        adapter.addFragement(new GlobalFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());
        adapter.addFragement(new StateFragment());



        viewPager.setAdapter(adapter);
        tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSmoothScrollingEnabled(true);

        tabLayout.setSelectedTabIndicatorColor(getColor(R.color.colorPrimary));


        tabLayout.getTabAt(0).setText("India");
        tabLayout.getTabAt(1).setText("Global");
        tabLayout.getTabAt(2).setText(country[2]);
        tabLayout.getTabAt(3).setText(country[3]);
        tabLayout.getTabAt(4).setText(country[4]);
        tabLayout.getTabAt(5).setText(country[5]);
        tabLayout.getTabAt(6).setText(country[6]);
        tabLayout.getTabAt(7).setText(country[7]);
        tabLayout.getTabAt(8).setText(country[8]);
        tabLayout.getTabAt(9).setText(country[9]);
        tabLayout.getTabAt(10).setText(country[10]);
        tabLayout.getTabAt(11).setText(country[11]);
        tabLayout.getTabAt(12).setText(country[12]);
        tabLayout.getTabAt(13).setText(country[13]);
        tabLayout.getTabAt(14).setText(country[14]);
        tabLayout.getTabAt(15).setText(country[15]);
        tabLayout.getTabAt(16).setText(country[16]);
        tabLayout.getTabAt(17).setText(country[17]);
        tabLayout.getTabAt(18).setText(country[18]);
        tabLayout.getTabAt(19).setText(country[19]);
        tabLayout.getTabAt(20).setText(country[20]);
        tabLayout.getTabAt(21).setText(country[21]);
        tabLayout.getTabAt(22).setText(country[22]);
        tabLayout.getTabAt(23).setText(country[23]);
        tabLayout.getTabAt(24).setText(country[24]);
        tabLayout.getTabAt(25).setText(country[25]);
        tabLayout.getTabAt(26).setText(country[26]);
        tabLayout.getTabAt(27).setText(country[27]);
        tabLayout.getTabAt(28).setText(country[28]);
        tabLayout.getTabAt(29).setText(country[29]);
        tabLayout.getTabAt(30).setText(country[30]);
        tabLayout.getTabAt(31).setText(country[31]);
        tabLayout.getTabAt(32).setText(country[32]);
        tabLayout.getTabAt(33).setText(country[33]);
        tabLayout.getTabAt(34).setText(country[34]);
        tabLayout.getTabAt(35).setText(country[35]);
        tabLayout.getTabAt(36).setText(country[36]);
        tabLayout.getTabAt(37).setText(country[37]);
        tabLayout.getTabAt(38).setText(country[38]);
        tabLayout.getTabAt(39).setText(country[39]);
        tabLayout.getTabAt(40).setText(country[40]);
        tabLayout.getTabAt(41).setText(country[41]);
        tabLayout.getTabAt(42).setText(country[42]);



        //custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(this).inflate(R.layout.tab_layout, tabLayout, false);

            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);
            tab.select();
        }

    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int position = tab.getPosition();
            session.setplace(country[position]);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    });




    }
    private void updatedeliverylist() {
        RecyclerView recyclerView = findViewById(R.id.placelist);
          recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL, false));
        ArrayList<String> wordlist = new ArrayList<>();
        wordlist.add("India");
        wordlist.add("Delhi");
        wordlist.add("Mumbai");
        wordlist.add("Chennai");
        wordlist.add("Punjab");
        wordlist.add("Kerala");
        wordlist.add("India");
        wordlist.add("Delhi");
        wordlist.add("Mumbai");
        wordlist.add("Chennai");
        wordlist.add("Punjab");
        wordlist.add("Kerala");
        WordListAdapter uploadImageAdapter= new WordListAdapter( this,wordlist,false);
        recyclerView.setAdapter(uploadImageAdapter);

    }
    private void checkRowItem() {
        if (rowItems.isEmpty()) {
            moreFrame.setVisibility(View.VISIBLE);
            cardFrame.setVisibility(View.GONE);
        }
    }

    private void updateLocation() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        updateLocation();
                    } else {
                        Toast.makeText(MainActivity.this, "Location Permission Denied. You have to give permission inorder to know the user range ", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void updateSwipeCard() {
        final SwipeFlingAdapterView flingContainer = findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Cards obj = (Cards) dataObject;
                checkRowItem();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Cards obj = (Cards) dataObject;

                //check matches
                checkRowItem();

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here


            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void sendNotification() {
        NotificationCompat.Builder nb = mNotificationHelper.getChannel1Notification(mContext.getString(R.string.app_name), mContext.getString(R.string.match_notification));

        mNotificationHelper.getManager().notify(1, nb.build());
    }


    public void DislikeBtn(View v) {
        if (rowItems.size() != 0) {
            Cards card_item = rowItems.get(0);

            String userId = card_item.getUserId();

            rowItems.remove(0);
            arrayAdapter.notifyDataSetChanged();

            Intent btnClick = new Intent(mContext, BtnDislikeActivity.class);
            btnClick.putExtra("url", card_item.getProfileImageUrl());
            startActivity(btnClick);
        }
    }

    public void LikeBtn(View v) {
        if (rowItems.size() != 0) {
            Cards card_item = rowItems.get(0);

            String userId = card_item.getUserId();

            //check matches

            rowItems.remove(0);
            arrayAdapter.notifyDataSetChanged();

            Intent btnClick = new Intent(mContext, BtnLikeActivity.class);
            btnClick.putExtra("url", card_item.getProfileImageUrl());
            startActivity(btnClick);
        }
    }


    /**
     * setup top tool bar
     */
    private void setupTopNavigationView() {
        Log.d(TAG, "setupTopNavigationView: setting up TopNavigationView");
     //   BottomNavigationViewEx tvEx = findViewById(R.id.topNavViewBar);
        BottomNavigationView tvEx = findViewById(R.id.bottomNavigationView);
        TopNavigationViewHelper.setupTopNavigationView(tvEx);
        TopNavigationViewHelper.enableNavigation(mContext, tvEx);
        Menu menu = tvEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


    @Override
    public void onBackPressed() {

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
        JSONArray jsonArray = new JSONArray(json);
        String[] heroes = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
           // heroes[i] = obj.getString("name");
            Toast.makeText(this, ""+heroes[i], Toast.LENGTH_SHORT).show();
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
        GridView gridview = (GridView) findViewById(R.id.homerecycleview);
//        gridview.setAdapter(new PersonAdapter(this,rowItems));
        GridImageAdapter adapter = new GridImageAdapter(MainActivity.this,R.layout.personitem,
                "", clist);
//        StateImageAdapter adapters = new StateImageAdapter(MainActivity.this,R.layout.personitem,
//                "", rowItems);

        gridview.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
