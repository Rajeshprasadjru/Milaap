package com.milaap.app.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import com.milaap.app.datingapp.R;

public class BootUpReceiver extends BroadcastReceiver {

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    String TAG = "OTG   ";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
//        Log.e("USB", "Decive Connected -> " + action);
//Initilizing globel class to access USB ATTACH and DETACH state
        final GlobalClass globalVariable = (GlobalClass) context.getApplicationContext();

        if (action.equalsIgnoreCase("android.hardware.usb.action.USB_DEVICE_ATTACHED")) {

            UsbDevice device = (UsbDevice) intent
                    .getParcelableExtra(UsbManager.EXTRA_DEVICE);
            if (device != null) {
                int vendorID = device.getVendorId();
                int productID = device.getProductId();
                if (String.valueOf(productID).equalsIgnoreCase(context.getString(R.string.productID/*product id of your specific device*/)) && (String.valueOf(vendorID).equalsIgnoreCase(context.getString(R.string.vendorID/*vendor id of your specific device*/)))) {
                    //If Product and Vendor Id match then set boolean "true" in global variable
                    globalVariable.setIs_OTG(true);
                } else {
                    globalVariable.setIs_OTG(false);
                }
            }
        } else if (action.equalsIgnoreCase("android.hardware.usb.action.USB_DEVICE_DETACHED")) {
            //When ever device Detach set your global variable to "false"
            globalVariable.setIs_OTG(false);
        }
    }
}
