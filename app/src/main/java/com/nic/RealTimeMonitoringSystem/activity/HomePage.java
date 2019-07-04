package com.nic.RealTimeMonitoringSystem.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.VolleyError;
import com.nic.RealTimeMonitoringSystem.R;
import com.nic.RealTimeMonitoringSystem.api.Api;
import com.nic.RealTimeMonitoringSystem.api.ApiService;
import com.nic.RealTimeMonitoringSystem.api.ServerResponse;
import com.nic.RealTimeMonitoringSystem.constant.AppConstant;
import com.nic.RealTimeMonitoringSystem.dataBase.dbData;
import com.nic.RealTimeMonitoringSystem.databinding.HomeScreenBinding;
import com.nic.RealTimeMonitoringSystem.dialog.MyDialog;
import com.nic.RealTimeMonitoringSystem.model.RealTimeMonitoringSystem;
import com.nic.RealTimeMonitoringSystem.session.PrefManager;
import com.nic.RealTimeMonitoringSystem.utils.UrlGenerator;
import com.nic.RealTimeMonitoringSystem.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity implements Api.ServerResponseListener, View.OnClickListener, MyDialog.myOnClickListener {
    private HomeScreenBinding homeScreenBinding;
    private PrefManager prefManager;
    public dbData dbData = new dbData(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeScreenBinding = DataBindingUtil.setContentView(this, R.layout.home_screen);
        homeScreenBinding.setActivity(this);
        prefManager = new PrefManager(this);
        if (Utils.isOnline()) {
            fetchAllResponseFromApi();
        }
    }

    public void fetchAllResponseFromApi() {
        getVillageList();
        getDistrictList();
        getBlockList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_out:
//                dbData.open();
//                ArrayList<ODFMonitoringListValue> activityCount = dbData.getSavedActivity();
                if (!Utils.isOnline()) {
                    Utils.showAlert(this, "Logging out while offline may leads to loss of data!");
                } else {
//                    if (!(activityCount.size() > 0 )) {
//                        closeApplication();
//                    }else{
//                        Utils.showAlert(this,"Sync all the data before logout!");
//                    }
                    closeApplication();
                }
                break;
        }


    }

    public void getDistrictList() {
        try {
            new ApiService(this).makeJSONObjectRequest("DistrictList", Api.Method.POST, UrlGenerator.getOpenUrl(), districtListJsonParams(), "not cache", this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getBlockList() {
        try {
            new ApiService(this).makeJSONObjectRequest("BlockList", Api.Method.POST, UrlGenerator.getOpenUrl(), blockListJsonParams(), "not cache", this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getVillageList() {
        try {
            new ApiService(this).makeJSONObjectRequest("VillageList", Api.Method.POST, UrlGenerator.getOpenUrl(), villageListJsonParams(), "not cache", this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public JSONObject districtListJsonParams() throws JSONException {
        JSONObject dataSet = new JSONObject();
        dataSet.put(AppConstant.KEY_SERVICE_ID, AppConstant.KEY_DISTRICT_LIST_ALL);
        Log.d("object", "" + dataSet);
        return dataSet;
    }

    public JSONObject blockListJsonParams() throws JSONException {
        JSONObject dataSet = new JSONObject();
        dataSet.put(AppConstant.KEY_SERVICE_ID, AppConstant.KEY_BLOCK_LIST_ALL);
        Log.d("object", "" + dataSet);
        return dataSet;
    }

    public JSONObject villageListJsonParams() throws JSONException {
        JSONObject dataSet = new JSONObject();
        dataSet.put(AppConstant.KEY_SERVICE_ID, AppConstant.KEY_VILLAGE_LIST_ALL);
        Log.d("object", "" + dataSet);
        return dataSet;
    }

    @Override
    public void OnMyResponse(ServerResponse serverResponse) {
        try {
            JSONObject loginResponse = serverResponse.getJsonResponse();
            String urlType = serverResponse.getApi();
            String status = loginResponse.getString(AppConstant.KEY_STATUS);
            String response = loginResponse.getString(AppConstant.KEY_RESPONSE);
            JSONObject responseObj = serverResponse.getJsonResponse();

            if ("DistrictList".equals(urlType) && responseObj != null) {
                if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("OK")) {
                    new InsertDistrictTask().execute(responseObj.getJSONArray(AppConstant.JSON_DATA));
                } else if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("NO_RECORD")) {
                    Log.d("Record", responseObj.getString(AppConstant.KEY_MESSAGE));
                }
                Log.d("DistrictList", "" + responseObj.getJSONArray(AppConstant.JSON_DATA));
            }

            if ("BlockList".equals(urlType) && responseObj != null) {
                if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("OK")) {
                    new InsertBlockTask().execute(responseObj.getJSONArray(AppConstant.JSON_DATA));
                } else if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("NO_RECORD")) {
                    Log.d("Record", responseObj.getString(AppConstant.KEY_MESSAGE));
                }
                Log.d("BlockList", "" + responseObj.getJSONArray(AppConstant.JSON_DATA));
            }

            if ("VillageList".equals(urlType) && responseObj != null) {
                if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("OK")) {
                    new InsertVillageTask().execute(responseObj.getJSONArray(AppConstant.JSON_DATA));
                } else if (status.equalsIgnoreCase("OK") && response.equalsIgnoreCase("NO_RECORD")) {
                    Log.d("Record", responseObj.getString(AppConstant.KEY_MESSAGE));
                }
                Log.d("VillageList", "" + responseObj.getJSONArray(AppConstant.JSON_DATA));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class InsertDistrictTask extends AsyncTask<JSONArray, Void, Void> {

        @Override
        protected Void doInBackground(JSONArray... params) {
            dbData.open();
            ArrayList<RealTimeMonitoringSystem> districtlist_count = dbData.getAll_District();
            if (districtlist_count.size() <= 0) {
                if (params.length > 0) {
                    JSONArray jsonArray = new JSONArray();
                    jsonArray = params[0];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        RealTimeMonitoringSystem districtListValue = new RealTimeMonitoringSystem();
                        try {
                            districtListValue.setDistictCode(jsonArray.getJSONObject(i).getString(AppConstant.DISTRICT_CODE));
                            districtListValue.setDistrictName(jsonArray.getJSONObject(i).getString(AppConstant.DISTRICT_NAME));

                            dbData.insertDistrict(districtListValue);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
            return null;
        }

    }

    public class InsertBlockTask extends AsyncTask<JSONArray, Void, Void> {

        @Override
        protected Void doInBackground(JSONArray... params) {
            dbData.open();
            ArrayList<RealTimeMonitoringSystem> blocklist_count = dbData.getAll_Block();
            if (blocklist_count.size() <= 0) {
                if (params.length > 0) {
                    JSONArray jsonArray = new JSONArray();
                    jsonArray = params[0];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        RealTimeMonitoringSystem blocktListValue = new RealTimeMonitoringSystem();
                        try {
                            blocktListValue.setDistictCode(jsonArray.getJSONObject(i).getString(AppConstant.DISTRICT_CODE));
                            blocktListValue.setBlockCode(jsonArray.getJSONObject(i).getString(AppConstant.BLOCK_CODE));
                            blocktListValue.setBlockName(jsonArray.getJSONObject(i).getString(AppConstant.BLOCK_NAME));

                            dbData.insertBlock(blocktListValue);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
            return null;
        }

    }

    public class InsertVillageTask extends AsyncTask<JSONArray, Void, Void> {

        @Override
        protected Void doInBackground(JSONArray... params) {
            dbData.open();
            ArrayList<RealTimeMonitoringSystem> villagelist_count = dbData.getAll_Village();
            if (villagelist_count.size() <= 0) {
                if (params.length > 0) {
                    JSONArray jsonArray = new JSONArray();
                    jsonArray = params[0];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        RealTimeMonitoringSystem villageListValue = new RealTimeMonitoringSystem();
                        try {
                            villageListValue.setDistictCode(jsonArray.getJSONObject(i).getString(AppConstant.DISTRICT_CODE));
                            villageListValue.setBlockCode(jsonArray.getJSONObject(i).getString(AppConstant.BLOCK_CODE));
                            villageListValue.setPvCode(jsonArray.getJSONObject(i).getString(AppConstant.PV_CODE));
                            villageListValue.setPvName(jsonArray.getJSONObject(i).getString(AppConstant.PV_NAME));

                            dbData.insertVillage(villageListValue);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
            return null;
        }

    }

    @Override
    public void OnError(VolleyError volleyError) {

    }

    public void viewHousingWorks() {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }


    public void closeApplication() {
        new MyDialog(this).exitDialog(this, "Are you sure you want to Logout?", "Logout");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                new MyDialog(this).exitDialog(this, "Are you sure you want to exit ?", "Exit");
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onButtonClick(AlertDialog alertDialog, String type) {
        alertDialog.dismiss();
        if ("Exit".equalsIgnoreCase(type)) {
            onBackPressed();
        } else {

            Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("EXIT", false);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        }
    }
}
