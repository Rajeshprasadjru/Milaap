package com.milaap.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.milaap.app.Model.Thistory;
import com.milaap.app.datingapp.R;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private static final String TAG = "FriendListAdapter";

    private Context mContext;
    private List<Thistory> mUsers;
    private boolean ischat;
    int selectedPosition=-1;


    String theLastMessage;
    private int messagecount = 0;

    public TransactionAdapter(Context mContext, List<Thistory> mUsers, boolean ischat) {
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.ischat = ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.historyitem, parent, false);
        return new  TransactionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Thistory user = mUsers.get(position);

       // String imgURL = Objects.requireNonNull(mUsers.get(position)).getBm();
        holder.type.setText(user.getType());
        holder.amount.setText(user.getAmount());
        holder.purpose.setText(user.getPurpose());
        holder.date.setText(user.getDate());


    }


    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView type,amount,purpose,date;

        public ViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            amount = itemView.findViewById(R.id.amount);
            purpose = itemView.findViewById(R.id.purpose);
            date = itemView.findViewById(R.id.date);


        }
    }


}
