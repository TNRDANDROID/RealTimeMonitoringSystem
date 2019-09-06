package com.nic.RealTimeMonitoringSystem.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.VolleyError;
import com.nic.RealTimeMonitoringSystem.R;
import com.nic.RealTimeMonitoringSystem.api.Api;
import com.nic.RealTimeMonitoringSystem.api.ServerResponse;
import com.nic.RealTimeMonitoringSystem.dataBase.DBHelper;
import com.nic.RealTimeMonitoringSystem.databinding.PendingScreenBinding;
import com.nic.RealTimeMonitoringSystem.session.PrefManager;

public class PendingScreen extends AppCompatActivity implements Api.ServerResponseListener {
    private PendingScreenBinding pendingScreenBinding; private PrefManager prefManager;
    private SQLiteDatabase db;
    public static DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pendingScreenBinding = DataBindingUtil.setContentView(this, R.layout.pending_screen);
        pendingScreenBinding.setActivity(this);
        try {
            dbHelper = new DBHelper(this);
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        prefManager = new PrefManager(this);
    }

    @Override
    public void OnMyResponse(ServerResponse serverResponse) {

    }

    @Override
    public void OnError(VolleyError volleyError) {

    }
}
