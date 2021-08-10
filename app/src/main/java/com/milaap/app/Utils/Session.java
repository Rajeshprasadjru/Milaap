package com.milaap.app.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

public class Session {
    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setusename(String usename) {
        prefs.edit().putString("usename", usename).apply();
    }
    public void setemail(String usename) {
        prefs.edit().putString("email", usename).apply();
    }
    public void setmobile(String usename) {
        prefs.edit().putString("mobile", usename).apply();
    }
    public void setwebsite(String usename) {
        prefs.edit().putString("website", usename).apply();
    }
    public void setdesignation(String usename) { prefs.edit().putString("designation", usename).apply(); }
    public void setprofilepic(String usename) { prefs.edit().putString("profilepic", usename).apply(); }
    public void setrdate(String usename) { prefs.edit().putString("rdate", usename).apply(); }
    public void setpdate(String usename) { prefs.edit().putString("pdate", usename).apply(); }
    public void setpstatus(String usename) { prefs.edit().putString("pstatus", usename).apply(); }
    public void setptype(String usename) { prefs.edit().putString("ptype", usename).apply(); }
    public void setsedate(String usename) { prefs.edit().putString("sedate", usename).apply(); }
    public void setamount(String usename) { prefs.edit().putString("amount", usename).apply(); }
    public void setplace(String usename) { prefs.edit().putString("place", usename).apply(); }

    public String getusename() { return prefs.getString("usename",""); }
    public String getemail() { return prefs.getString("email",""); }
    public String getmobile() { return prefs.getString("mobile","");  }
    public String getwebsite() { return prefs.getString("website",""); }
    public String getdesignation() { return prefs.getString("designation","");  }
    public String getprofilepic() { return prefs.getString("profilepic","");}
    public String getrdate() { return prefs.getString("rdate","");}
    public String getpdate() { return prefs.getString("pdate","");}
    public String getpstatus() { return prefs.getString("pstatus","");}
    public String getptype() { return prefs.getString("ptype","");}
    public String getsedate() { return prefs.getString("sedate","");}
    public String getamount() { return prefs.getString("amount",""); }
    public String getplace() { return prefs.getString("place",""); }

    public void setUser(User user){
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString("MyObject", json);
        prefsEditor.apply();
    }
    public  User getUser(){
        Gson gson = new Gson();
        String json = prefs.getString("MyObject", "");
        User obj = gson.fromJson(json, User.class);
        return  obj;
    }
    public void logout() {
       prefs.edit().clear().apply();

    }
}
