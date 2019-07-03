package com.nic.RealTimeMonitoringSystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.nic.RealTimeMonitoringSystem.api.Api;
import com.nic.RealTimeMonitoringSystem.api.ServerResponse;

public class HomePage extends AppCompatActivity implements Api.ServerResponseListener {
    @Override
    public void OnMyResponse(ServerResponse serverResponse) {

    }

    @Override
    public void OnError(VolleyError volleyError) {

    }
}
