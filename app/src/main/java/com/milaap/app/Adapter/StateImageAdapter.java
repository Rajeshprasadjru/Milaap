package com.milaap.app.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.milaap.app.Main.DetailPage;
import com.milaap.app.Main.PaymentActivity;
import com.milaap.app.Model.SliderData;
import com.milaap.app.datingapp.R;
import com.milaap.app.Utils.User;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StateImageAdapter extends ArrayAdapter<User> {
    private static final String TAG = "GridImageAdapter";

    private Context mContext;
    private LayoutInflater mInflater;
    private int layoutResource;
    private String mAppend;
    private ArrayList<User> imgURLs;
    String url1 = "https://image.freepik.com/free-vector/dating-app-banner-mobile-phone-with-hearts-online-dating-social-networks_136277-525.jpg";
    String url2 = "https://image.freepik.com/free-vector/online-dating-app-concept_52683-37139.jpg";
    String url3 = "https://cdn8.quackquack.in/index_banner_imgv1.png";


    public StateImageAdapter(Context context, int layoutResource, String append, ArrayList<User> imgURLs) {
        super(context, layoutResource, imgURLs);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        this.layoutResource = layoutResource;
        mAppend = append;
        this.imgURLs = imgURLs;
    }

    static class ViewHolder{
        ImageView pimage, video, comment, like;
        TextView name, place;

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
            holder.place= convertView.findViewById(R.id.place);
            holder.name= convertView.findViewById(R.id.name);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }


        Log.d(TAG, "getView: "+imgURLs.get(position).getProfileImageUrl());
          //imgURL.trim();
        holder.name.setText(imgURLs.get(position).getUsername());
        holder.place.setText(" "+imgURLs.get(position).getPlace());

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
          Intent intent= new Intent(mContext, DetailPage.class);
          intent.putExtra("User", imgURLs.get(position));
          mContext.startActivity(intent);
      });
        holder.like.setOnClickListener(v -> {
            Intent intent= new Intent(mContext, DetailPage.class);
            mContext.startActivity(intent);
        });
        return convertView;
    } }

