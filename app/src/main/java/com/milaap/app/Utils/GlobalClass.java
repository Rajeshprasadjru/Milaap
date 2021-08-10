package com.milaap.app.Utils;

import android.app.Application;

public class GlobalClass extends Application {

    private boolean is_OTG = false;

    public boolean is_OTG() {
        return is_OTG;
    }

    public void setIs_OTG(boolean is_OTG) {
        this.is_OTG = is_OTG;
    }

}