package com.milaap.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.milaap.app.Model.Gift;
import com.milaap.app.datingapp.R;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.ViewHolder> {
    private static final String TAG = "FriendListAdapter";

    private Context mContext;
    private List<Gift> mUsers;
    private boolean ischat;
    int selectedPosition=-1;


    String theLastMessage;
    private int messagecount = 0;

    public EmojiAdapter(Context mContext, List<Gift> mUsers, boolean ischat) {
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.ischat = ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.giftitem, parent, false);
        return new  EmojiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Gift user = mUsers.get(position);

       // String imgURL = Objects.requireNonNull(mUsers.get(position)).getBm();

        Log.d(TAG, "getView: "+user);
      // holder.imageView.setImageResource(user.getImg());
        Glide.with(mContext).load(user.getImg()).into(holder.imageView);
        holder.madImage.setText(user.getCount());
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
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            madImage = itemView.findViewById(R.id.giftcount);
            imageView = itemView.findViewById(R.id.gift);


        }
    }


}
