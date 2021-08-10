package com.milaap.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.milaap.app.datingapp.R;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {
    private static final String TAG = "FriendListAdapter";

    private Context mContext;
    private List<String> mUsers;
    private boolean ischat;
    int selectedPosition=-1;


    String theLastMessage;
    private int messagecount = 0;

    public WordListAdapter(Context mContext, List<String> mUsers, boolean ischat) {
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.ischat = ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.wordlist, parent, false);
        return new  WordListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final String user = mUsers.get(position);

       // String imgURL = Objects.requireNonNull(mUsers.get(position)).getBm();

        Log.d(TAG, "getView: "+user);
        File imgFile = new  File(user);
       holder.madImage.setText(user);
       holder.itemView.setOnClickListener(v -> {
           String ItemName = user;

           Intent intent = new Intent("custom-message");
           //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));

           intent.putExtra("item",ItemName);
           LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
       });

        if(selectedPosition==position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#b30000"));
            holder.madImage.setTextColor(Color.parseColor("#ffffff"));
        }
        else
        {

            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.madImage.setTextColor(Color.parseColor("#000000"));
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();

            }
        });

       // holder.madImage.setImageURI(user);
        //
        // holder.username.setText(singleSnapshot.getValue(UserAccountSettings.class).getUsername());

//        RequestOptions options = new RequestOptions()
//                .placeholder(R.drawable.ic_baseline_image_24)
//                .error(R.drawable.ic_baseline_image_24)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .priority(Priority.HIGH);
//        Glide.with(mContext).load(user).apply(options)
//                .into(holder.madImage);

        //set the profile image


    }


    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView madImage;

        public ViewHolder(View itemView) {
            super(itemView);
            madImage = itemView.findViewById(R.id.word);


        }
    }


}
