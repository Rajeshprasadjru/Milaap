package com.milaap.app.ViewModel;

import com.milaap.app.Utils.User;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModal extends ViewModel {

    MutableLiveData<ArrayList<User>> userLiveData;
    ArrayList<User> userArrayList;

    public void MainViewModel() {
        userLiveData = new MutableLiveData<>();

        // call your Rest API in init method
        init();
    }

    public MutableLiveData<ArrayList<User>> getUserMutableLiveData() {
        return userLiveData;
    }

    public void init(){
        populateList();
        userLiveData.setValue(userArrayList);
    }

    public void populateList(){
//
//        User user = new User(obj.getString("sex"), obj.getString("preferSex"), obj.getString("user_id"), obj.getString("phone_number"), obj.getString("email"), obj.getString("username"), obj.getString("sports").equals("true"), obj.getString("travel").equals("true"), obj.getString("music").equals("true"), obj.getString("fishing").equals("true"), obj.getString("description"), obj.getString("dateOfBirth"), obj.getString("profileImageUrl"), obj.getString("latitude"), obj.getString("longtitude"));
//        user.setEmail("Darknight");
//        user.setDescription("Best rating movie");
//
//        userArrayList = new ArrayList<>();
//        userArrayList.add(user);
//        userArrayList.add(user);
//        userArrayList.add(user);
//        userArrayList.add(user);
//        userArrayList.add(user);
//        userArrayList.add(user);
    }
}
