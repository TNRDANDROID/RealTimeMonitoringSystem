package com.nic.RealTimeMonitoringSystem.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.bumptech.glide.util.Util;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.nic.RealTimeMonitoringSystem.R;
import com.nic.RealTimeMonitoringSystem.adapter.CommonAdapter;
import com.nic.RealTimeMonitoringSystem.adapter.WorkListAdapter;
import com.nic.RealTimeMonitoringSystem.api.Api;
import com.nic.RealTimeMonitoringSystem.api.ApiService;
import com.nic.RealTimeMonitoringSystem.api.ServerResponse;
import com.nic.RealTimeMonitoringSystem.constant.AppConstant;
import com.nic.RealTimeMonitoringSystem.dataBase.DBHelper;
import com.nic.RealTimeMonitoringSystem.dataBase.dbData;
import com.nic.RealTimeMonitoringSystem.databinding.ActivityWorkListBinding;
import com.nic.RealTimeMonitoringSystem.model.RealTimeMonitoringSystem;
import com.nic.RealTimeMonitoringSystem.session.PrefManager;
import com.nic.RealTimeMonitoringSystem.support.MyDividerItemDecoration;
import com.nic.RealTimeMonitoringSystem.utils.UrlGenerator;
import com.nic.RealTimeMonitoringSystem.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WorkListScreen extends AppCompatActivity implements View.OnClickListener,Api.ServerResponseListener {
    private ActivityWorkListBinding activityWorkListBinding;
    private ShimmerRecyclerView recyclerView;
    private WorkListAdapter workListAdapter;
    public dbData dbData = new dbData(this);
    private SearchView searchView;
    String pref_Scheme, pref_finYear;
    private List<RealTimeMonitoringSystem> WorkList = new ArrayList<>();
    private List<RealTimeMonitoringSystem> Scheme = new ArrayList<>();
    private List<RealTimeMonitoringSystem> FinYearList = new ArrayList<>();
    private PrefManager prefManager;
    private SQLiteDatabase db;
    public static DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWorkListBinding = DataBindingUtil.setContentView(this, R.layout.activity_work_list);
        activityWorkListBinding.setActivity(this);
        try {
            dbHelper = new DBHelper(this);
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        prefManager = new PrefManager(this);
        prefManager.setPvCode(getIntent().getStringExtra(AppConstant.PV_CODE));
        setSupportActionBar(activityWorkListBinding.toolbar);
        // initRecyclerView();

        activityWorkListBinding.finyearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    pref_finYear = FinYearList.get(position).getFinancialYear();
                    prefManager.setFinancialyearName(pref_finYear);
                    //  loadOfflineSchemeListDBValues(pref_finYear);
                    getWorkList();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        activityWorkListBinding.schemeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    pref_Scheme = Scheme.get(position).getSchemeName();
                    prefManager.setSchemeName(pref_Scheme);
//                    prefManager.setKeySpinnerSelectedSchemeSeqId(Scheme.get(position).getSchemeID());
//                    JSONObject jsonObject = new JSONObject();
////                    try {
////                        jsonObject.put(AppConstant.KEY_SCHEME_SEQUENTIAL_ID,WorkList.get(position).getSchemeSequentialID());
////                        jsonObject.put(AppConstant.FINANCIAL_YEAR,WorkList.get(position).getFinancialYear());
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        loadOfflineFinYearListDBValues();
    }

    private void initRecyclerView() {
        activityWorkListBinding.workList.setVisibility(View.VISIBLE);
        recyclerView = activityWorkListBinding.workList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 20));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        new fetchScheduletask().execute();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                new fetchScheduletask().execute();
//            }
//        }, 2000);
    }

    public class fetchScheduletask extends AsyncTask<Void, Void,
            ArrayList<RealTimeMonitoringSystem>> {
        @Override
        protected ArrayList<RealTimeMonitoringSystem> doInBackground(Void... params) {
            dbData.open();
            ArrayList<RealTimeMonitoringSystem> workList = new ArrayList<>();
            workList = dbData.getAllWorkLIst();
            Log.d("WORKLIST_COUNT", String.valueOf(workList.size()));
            return workList;
        }

        @Override
        protected void onPostExecute(ArrayList<RealTimeMonitoringSystem> workList) {
            super.onPostExecute(workList);
            workListAdapter = new WorkListAdapter(WorkListScreen.this, workList);
            recyclerView.setAdapter(workListAdapter);
            recyclerView.showShimmerAdapter();
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadCards();
                }
            }, 2000);
        }

        private void loadCards() {

            recyclerView.hideShimmerAdapter();
        }
    }

    public void loadOfflineFinYearListDBValues() {
        FinYearList.clear();
        Cursor FinYear = null;
        FinYear = db.rawQuery("select * from " + DBHelper.FINANCIAL_YEAR_TABLE_NAME, null);

        RealTimeMonitoringSystem finYearListValue = new RealTimeMonitoringSystem();
        finYearListValue.setFinancialYear("Select Financial year");
        FinYearList.add(finYearListValue);
        if (FinYear.getCount() > 0) {
            if (FinYear.moveToFirst()) {
                do {
                    RealTimeMonitoringSystem finyearList = new RealTimeMonitoringSystem();
                    String financialYear = FinYear.getString(FinYear.getColumnIndexOrThrow(AppConstant.FINANCIAL_YEAR));
                    finyearList.setFinancialYear(financialYear);
                    FinYearList.add(finyearList);
                } while (FinYear.moveToNext());
            }
        }

        activityWorkListBinding.finyearSpinner.setAdapter(new CommonAdapter(this, FinYearList, "FinYearList"));
    }

    public void loadOfflineSchemeListDBValues(String fin_year) {
        Cursor SchemeList = null;
        SchemeList = db.rawQuery("SELECT * FROM " + DBHelper.SCHEME_TABLE_NAME + " where fin_year = '" + fin_year + "'", null);

        Scheme.clear();
        RealTimeMonitoringSystem schemeListValue = new RealTimeMonitoringSystem();
        schemeListValue.setSchemeName("Select Scheme");
        Scheme.add(schemeListValue);
        if (SchemeList.getCount() > 0) {
            if (SchemeList.moveToFirst()) {
                do {
                    RealTimeMonitoringSystem schemeList = new RealTimeMonitoringSystem();
                    Integer schemeSequentialID = SchemeList.getInt(SchemeList.getColumnIndexOrThrow(AppConstant.KEY_SCHEME_SEQUENTIAL_ID));
                    String schemeName = SchemeList.getString(SchemeList.getColumnIndexOrThrow(AppConstant.KEY_SCHEME_NAME));
                    String financial_year = SchemeList.getString(SchemeList.getColumnIndexOrThrow(AppConstant.FINANCIAL_YEAR));
                    schemeList.setSchemeSequentialID(schemeSequentialID);
                    schemeList.setSchemeName(schemeName);
                    schemeList.setFinancialYear(financial_year);
                    Scheme.add(schemeList);

                } while (SchemeList.moveToNext());
            }
        }
        activityWorkListBinding.schemeSpinner.setAdapter(new CommonAdapter(this, Scheme, "SchemeList"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                workListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                workListAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }


    @Override
    public void onClick(View view) {

    }

    public void getWorkList() {
        try {
            new ApiService(this).makeJSONObjectRequest("WorkList", Api.Method.POST, UrlGenerator.getWorkListUrl(), workListJsonParams(), "not cache", this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject workListJsonParams() throws JSONException {
        String authKey = Utils.encrypt(prefManager.getUserPassKey(), getResources().getString(R.string.init_vector), Utils.workListBlockWiseJsonParams(this).toString());
        JSONObject dataSet = new JSONObject();
        dataSet.put(AppConstant.KEY_USER_NAME, prefManager.getUserName());
        dataSet.put(AppConstant.DATA_CONTENT, authKey);
        Log.d("workList", "" + authKey);
        return dataSet;
    }

    public void OnMyResponse(ServerResponse serverResponse) {
        try {
            String urlType = serverResponse.getApi();
            JSONObject responseObj = serverResponse.getJsonResponse();

            if ("WorkList".equals(urlType) && responseObj != null) {
                String key = responseObj.getString(AppConstant.ENCODE_DATA);
                String responseDecryptedSchemeKey = Utils.decrypt(prefManager.getUserPassKey(), key);
                JSONObject jsonObject = new JSONObject(responseDecryptedSchemeKey);
                if (jsonObject.getString("STATUS").equalsIgnoreCase("OK") && jsonObject.getString("RESPONSE").equalsIgnoreCase("OK")) {
                    new InsertWorkListTask().execute(jsonObject.getJSONObject(AppConstant.JSON_DATA));
                    initRecyclerView();
                } else if(jsonObject.getString("STATUS").equalsIgnoreCase("OK") && jsonObject.getString("RESPONSE").equalsIgnoreCase("NO_RECORD")){
                    dbData.open();
                    if(Utils.isOnline()){
                        dbData.deleteWorkListTable();
                    }
                    initRecyclerView();
                    Utils.showAlert(this,"NO RECORD FOUND!");
                }
                Log.d("SchemeList", "" + responseDecryptedSchemeKey);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnError(VolleyError volleyError) {

    }

    public class InsertWorkListTask extends AsyncTask<JSONObject, Void, Void> {

        @Override
        protected Void doInBackground(JSONObject... params) {

            dbData.open();
            if(Utils.isOnline()){
                dbData.deleteWorkListTable();
            }
            ArrayList<RealTimeMonitoringSystem> workList_count = dbData.getAllWorkLIst();
            if (workList_count.size() <= 0) {
                if (params.length > 0) {
                    JSONArray jsonArray = new JSONArray();
                    try {
                        jsonArray = params[0].getJSONArray(AppConstant.MAIN_WORK);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        RealTimeMonitoringSystem workList = new RealTimeMonitoringSystem();
                        try {
                            workList.setDistictCode(jsonArray.getJSONObject(i).getString(AppConstant.DISTRICT_CODE));
                            workList.setBlockCode(jsonArray.getJSONObject(i).getString(AppConstant.BLOCK_CODE));
                            workList.setPvCode(jsonArray.getJSONObject(i).getString(AppConstant.PV_CODE));
                            workList.setWorkId(jsonArray.getJSONObject(i).getInt(AppConstant.WORK_ID));
                            workList.setSchemeGroupID(jsonArray.getJSONObject(i).getInt(AppConstant.SCHEME_GROUP_ID));
                            workList.setSchemeID(jsonArray.getJSONObject(i).getInt(AppConstant.SCHEME_ID));
                            workList.setSchemeGroupName(jsonArray.getJSONObject(i).getString(AppConstant.SCHEME_GROUP_NAME));
                            workList.setSchemeName(jsonArray.getJSONObject(i).getString(AppConstant.KEY_SCHEME_NAME));
                            workList.setFinancialYear(jsonArray.getJSONObject(i).getString(AppConstant.FINANCIAL_YEAR));
                            workList.setAgencyName(jsonArray.getJSONObject(i).getString(AppConstant.AGENCY_NAME));
                            workList.setWorkGroupNmae(jsonArray.getJSONObject(i).getString(AppConstant.WORK_GROUP_NAME));
                            workList.setWorkName(jsonArray.getJSONObject(i).getString(AppConstant.WORK_NAME));
                            workList.setWorkGroupID(jsonArray.getJSONObject(i).getString(AppConstant.WORK_GROUP_ID));
                            workList.setWorkTypeID(jsonArray.getJSONObject(i).getString(AppConstant.WORK_TYPE));
                            workList.setCurrentStage(jsonArray.getJSONObject(i).getInt(AppConstant.CURRENT_STAGE_OF_WORK));
                            workList.setIntialAmount(jsonArray.getJSONObject(i).getString(AppConstant.AS_VALUE));
                            workList.setAmountSpendSoFar(jsonArray.getJSONObject(i).getString(AppConstant.AMOUNT_SPENT_SOFAR));
                            workList.setStageName(jsonArray.getJSONObject(i).getString(AppConstant.STAGE_NAME));
                            workList.setBeneficiaryName(jsonArray.getJSONObject(i).getString(AppConstant.BENEFICIARY_NAME));
                            workList.setBeneficiaryFatherName(jsonArray.getJSONObject(i).getString(AppConstant.BENEFICIARY_FATHER_NAME));
                            workList.setWorkTypeName(jsonArray.getJSONObject(i).getString(AppConstant.WORK_TYPE_NAME));
                            workList.setYnCompleted(jsonArray.getJSONObject(i).getString(AppConstant.YN_COMPLETED));
                            workList.setCdProtWorkYn(jsonArray.getJSONObject(i).getString(AppConstant.CD_PROT_WORK_YN));
                            workList.setStateCode(jsonArray.getJSONObject(i).getInt(AppConstant.STATE_CODE));
                            workList.setDistrictName(jsonArray.getJSONObject(i).getString(AppConstant.DISTRICT_NAME));
                            workList.setBlockName(jsonArray.getJSONObject(i).getString(AppConstant.BLOCK_NAME));
                            workList.setPvName(jsonArray.getJSONObject(i).getString(AppConstant.PV_NAME));
                            workList.setCommunity(jsonArray.getJSONObject(i).getString(AppConstant.COMMUNITY_NAME));
                            workList.setGender(jsonArray.getJSONObject(i).getString(AppConstant.GENDER));
                            workList.setLastVisitedDate(jsonArray.getJSONObject(i).getString(AppConstant.LAST_VISITED_DATE));

                            dbData.insertWorkList(workList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return null;
        }
    }
}

