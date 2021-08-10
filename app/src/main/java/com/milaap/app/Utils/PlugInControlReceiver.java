package com.milaap.app.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class PlugInControlReceiver extends BroadcastReceiver {

    @Override public void onReceive(final Context context, Intent intent) {

        String action = intent.getAction();
        Log.v("PlugInControlReceiver","action: "+action);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            if(action.equals("android.hardware.usb.action.USB_STATE")) {

                if(intent.getExtras().getBoolean("connected")){
                    Intent intent1 = new Intent("custom-message");
                    //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));

                    intent1.putExtra("item","connected");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);
                    Toast.makeText(context, "USB Connected", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent1 = new Intent("custom-message");
                    //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));

                    intent1.putExtra("item","disconnected");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);
                    Toast.makeText(context, "USB Disconnected", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if(action.equals(Intent.ACTION_POWER_CONNECTED)) {
                Intent intent1 = new Intent("custom-message");
                //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));

                intent1.putExtra("item","connected");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);
                Toast.makeText(context, "USB Connected", Toast.LENGTH_SHORT).show();
            }
            else if(action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
                Intent intent1 = new Intent("custom-message");
                //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));

                intent1.putExtra("item","disconnected");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);
                Toast.makeText(context, "USB Disconnected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
