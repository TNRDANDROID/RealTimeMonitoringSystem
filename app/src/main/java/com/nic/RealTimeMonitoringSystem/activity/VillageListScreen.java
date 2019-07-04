package com.nic.RealTimeMonitoringSystem.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.nic.RealTimeMonitoringSystem.R;
import com.nic.RealTimeMonitoringSystem.databinding.VillageListActivityBinding;

public class VillageListScreen extends AppCompatActivity implements View.OnClickListener {
    private VillageListActivityBinding villageListActivityBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        villageListActivityBinding = DataBindingUtil.setContentView(this, R.layout.village_list_activity);
        villageListActivityBinding.setActivity(this);
        intializeUI();
    }
    public void intializeUI(){

    }

    @Override
    public void onClick(View view) {

    }
}
