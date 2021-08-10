package com.milaap.app.Introduction;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.milaap.app.Login.Login;
import com.milaap.app.Login.RegisterBasicInfo;
import com.milaap.app.Main.MainActivity;
import com.milaap.app.Matched.Matched_Activity;
import com.milaap.app.datingapp.R;
import com.milaap.app.Utils.Session;
import com.milaap.app.test.Usbtesting;


/**
 * DatingApp
 * https://github.com/quintuslabs/DatingApp
 * Created on 25-sept-2018.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */

public class IntroductionMain extends AppCompatActivity {
    private static final String TAG = "IntroductionMain";
    private Button signupButton;
    private Button loginButton;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_main);
        session= new Session(this);
        signupButton = findViewById(R.id.signup_button);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmailAddressEntryPage();
            }
        });

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginPage();
            }
        });
//        Intent intent = new Intent(IntroductionMain.this, Usbtesting.class);
//
//        startActivity(intent);
        Log.d(TAG, "onCreate:  Session"+ session.getmobile());
        if(!session.getmobile().equals("")){
            Intent intent1 = new Intent(IntroductionMain.this, MainActivity.class);

            startActivity(intent1);
            finish();
        }
    }

    public void openLoginPage() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void openEmailAddressEntryPage() {
        Intent intent = new Intent(this, RegisterBasicInfo.class);
        startActivity(intent);
    }
}
