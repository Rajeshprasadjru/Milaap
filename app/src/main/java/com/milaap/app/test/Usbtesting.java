package com.milaap.app.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.milaap.app.Main.MainActivity;
import com.milaap.app.datingapp.R;
import com.milaap.app.Utils.GlobalClass;

import java.util.Arrays;

public class Usbtesting extends AppCompatActivity {
    TextView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usbtesting);
        view = findViewById(R.id.textView2);


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
//        if (globalVariable.is_OTG()) {
//            //Perform your functionality
//            Toast.makeText(globalVariable, "conntected", Toast.LENGTH_SHORT).show();
//        }
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String ItemName = intent.getStringExtra("item");

           view.setText(ItemName);
        }
    };

}