package com.milaap.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.milaap.app.Main.Cards;
import com.milaap.app.datingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PersonAdapter  extends BaseAdapter {
    Context context;
    private final ArrayList<Cards> imgURLs;
    LayoutInflater inflter;
    public PersonAdapter(Context applicationContext, ArrayList<Cards> imgURLs) {
        this.context = applicationContext;
        this.imgURLs = imgURLs;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return imgURLs.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.personitem, null); // inflate the layout
        ImageView icon = (ImageView) view.findViewById(R.id.profileImage);
        ImageView callbtn = (ImageView) view.findViewById(R.id.videoCalBtn);
        ImageView commentbtn = (ImageView) view.findViewById(R.id.commentbtn);
        ImageView likebtn = (ImageView) view.findViewById(R.id.likebtn);
        Picasso.get().load(imgURLs.get(i).getProfileImageUrl()).into(icon);
        // get the reference of ImageView

//        icon.setImageResource(logos[i]); // set logo images
        return view;
    }
}