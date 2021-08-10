package com.milaap.app.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.milaap.app.Adapter.SliderAdapter;
import com.milaap.app.Model.SliderData;
import com.milaap.app.Utils.HttpParse;
import com.milaap.app.Utils.Session;
import com.milaap.app.datingapp.R;
import com.milaap.app.datingapp.databinding.ActivityPaymentBinding;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = "PaymentActivity";
   ActivityPaymentBinding binding;
   Session session;
   String amount="0";
   Boolean isSubscription =true;

    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult ;
    String HttpURL = "https://msquarebharat.com/milaap/app/updatepayment.php";
    RadioButton genderradioButton;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session = new Session(this);
        binding.closebtn.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.button.setOnClickListener(v -> {

            startPayment(amount);
        });
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        genderradioButton = (RadioButton) findViewById(selectedId);
        if(selectedId==-1){
            Toast.makeText(PaymentActivity.this,"Nothing selected", Toast.LENGTH_SHORT).show();
        }
        else{

            Toast.makeText(PaymentActivity.this,genderradioButton.getText(), Toast.LENGTH_SHORT).show();
        }
        binding.pricelist.setVisibility(View.VISIBLE);
        binding.textbox.setVisibility(View.GONE);
        binding.radioMale.setOnClickListener(v -> {
            binding.pricelist.setVisibility(View.VISIBLE);
            binding.textbox.setVisibility(View.GONE);
            isSubscription= true;
        });
         binding.radioFemale.setOnClickListener(v -> {
             binding.pricelist.setVisibility(View.GONE);
             binding.textbox.setVisibility(View.VISIBLE);
             isSubscription= false;
        });
        binding.onemonth.setOnClickListener(v -> {
            binding.box1.setBackgroundColor( getResources().getColor(R.color.colorAccent));
            binding.box2.setBackgroundColor( getResources().getColor(R.color.grey));
            binding.box3.setBackgroundColor( getResources().getColor(R.color.grey));

            binding.oneone.setTextColor(getResources().getColor(R.color.white));
            binding.twoone.setTextColor(getResources().getColor(R.color.black));
            binding.threeone.setTextColor(getResources().getColor(R.color.black));

            binding.onetwo.setTextColor(getResources().getColor(R.color.white));
            binding.twotwo.setTextColor(getResources().getColor(R.color.black));
            binding.threetwo.setTextColor(getResources().getColor(R.color.black));
            amount="250";

        });
        binding.twomonth.setOnClickListener(v -> {
            binding.box1.setBackgroundColor( getResources().getColor(R.color.grey));
            binding.box2.setBackgroundColor( getResources().getColor(R.color.colorAccent));
            binding.box3.setBackgroundColor( getResources().getColor(R.color.grey));

            binding.oneone.setTextColor(getResources().getColor(R.color.black));
            binding.twoone.setTextColor(getResources().getColor(R.color.white));
            binding.threeone.setTextColor(getResources().getColor(R.color.black));

            binding.onetwo.setTextColor(getResources().getColor(R.color.black));
            binding.twotwo.setTextColor(getResources().getColor(R.color.white));
            binding.threetwo.setTextColor(getResources().getColor(R.color.black));
            amount="450";


        });
        binding.threemonth.setOnClickListener(v -> {
            binding.box1.setBackgroundColor( getResources().getColor(R.color.grey));
            binding.box2.setBackgroundColor( getResources().getColor(R.color.grey));
            binding.box3.setBackgroundColor( getResources().getColor(R.color.colorAccent));

            binding.oneone.setTextColor(getResources().getColor(R.color.black));
            binding.twoone.setTextColor(getResources().getColor(R.color.black));
            binding.threeone.setTextColor(getResources().getColor(R.color.white));

            binding.onetwo.setTextColor(getResources().getColor(R.color.black));
            binding.twotwo.setTextColor(getResources().getColor(R.color.black));
            binding.threetwo.setTextColor(getResources().getColor(R.color.white));
            amount= "850";

        });
       setSlider();

    }

    private void setSlider() {
        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
        String url1 = "https://image.freepik.com/free-vector/dating-app-banner-mobile-phone-with-hearts-online-dating-social-networks_136277-525.jpg";
        String url2 = "https://image.freepik.com/free-vector/online-dating-app-concept_52683-37139.jpg";
        String url3 = "https://cdn8.quackquack.in/index_banner_imgv1.png";

        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));

        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        binding.slider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        binding.slider.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
        binding.slider.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        binding.slider.setAutoCycle(true);

        // to start autocycle below method is used.
        binding.slider.startAutoCycle();

    }
    public void startPayment(String am) {
        //amount="799";
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        if(!isSubscription)
        {
            amount= binding.amount.getText().toString();
        }
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

    @Override
    public void onPaymentSuccess(String s) {
        try {
             String type;
            if(isSubscription)
            {
               type= "Subscription" ;
            } else {
                type= "Add Wallet" ;
            }
            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDate =  new SimpleDateFormat("dd/MM/yyyy");

            String date = simpleDate.format(currentTime);
            Calendar cal = Calendar.getInstance();
            Date today = cal.getTime();
            int y =0;
            if(amount.equals("799")){
                y=1;
            } else {
                y=2;
            }
            cal.add(Calendar.MONTH, y); // to get previous year add -1
            Date nextYear = cal.getTime();
            String nyear= simpleDate.format(nextYear);
            String edate= getCalculatedDate(date,"dd-MM-yyyy", 1);
            UpdateFollowUpDate(session.getmobile(), date,type,"yes",amount,nyear);
            Toast.makeText(this, "Payment Successful: " + s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        try {

            Toast.makeText(this, "Payment failed: " + i + " " + s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
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

                Toast.makeText(PaymentActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                if (httpResponseMsg.equals("Payment Successfully")){
                    //  Toast.makeText(CustomerDetailActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(PaymentActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(PaymentActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
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

    public  String getCalculatedDate(String date, String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.MONTH, days);
        try {
            return s.format(new Date(s.parse(date).getTime()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            Log.e("TAG", "Error in Parsing Date : " + e.getMessage());
        }
        return null;
    }
}