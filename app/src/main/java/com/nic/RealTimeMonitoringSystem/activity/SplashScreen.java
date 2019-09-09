package com.nic.RealTimeMonitoringSystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.nic.RealTimeMonitoringSystem.R;
import com.nic.RealTimeMonitoringSystem.databinding.SplashScreenBinding;
import com.nic.RealTimeMonitoringSystem.helper.AppVersionHelper;
import com.nic.RealTimeMonitoringSystem.session.PrefManager;
import com.nic.RealTimeMonitoringSystem.utils.Utils;


public class SplashScreen extends AppCompatActivity implements
        AppVersionHelper.myAppVersionInterface {
    private TextView textView;
    private Button button;
    private static int SPLASH_TIME_OUT = 2000;
    private PrefManager prefManager;
    public SplashScreenBinding splashScreenBinding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashScreenBinding = DataBindingUtil.setContentView(this,R.layout.splash_screen);
        splashScreenBinding.setActivity(this);
        prefManager = new PrefManager(this);
//        if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("production")) {
//            if (Utils.isOnline()) {
//                checkAppVersion();
//            } else {
//                showSignInScreen();
//
//            }
//        } else {
//            showSignInScreen();
//        }
        if (Utils.isOnline()) {
            showSignInScreen();
        }else{
            Utils.showAlert(this,getResources().getString(R.string.no_internet));
        }
    }


    private void showSignInScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this, LoginScreen.class);

                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        }, SPLASH_TIME_OUT);
    }

    private void checkAppVersion() {
        new AppVersionHelper(this, SplashScreen.this).callAppVersionCheckApi();
    }

    @Override
    public void onAppVersionCallback(String value) {
        if (value.length() > 0 && "Update".equalsIgnoreCase(value)) {
            startActivity(new Intent(this, AppUpdateDialog.class));
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        } else {
            showSignInScreen();
        }

    }

}
