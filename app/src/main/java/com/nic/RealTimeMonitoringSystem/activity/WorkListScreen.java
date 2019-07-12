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

import com.nic.RealTimeMonitoringSystem.R;
import com.nic.RealTimeMonitoringSystem.adapter.CommonAdapter;
import com.nic.RealTimeMonitoringSystem.adapter.WorkListAdapter;
import com.nic.RealTimeMonitoringSystem.constant.AppConstant;
import com.nic.RealTimeMonitoringSystem.dataBase.DBHelper;
import com.nic.RealTimeMonitoringSystem.dataBase.dbData;
import com.nic.RealTimeMonitoringSystem.databinding.ActivityWorkListBinding;
import com.nic.RealTimeMonitoringSystem.model.RealTimeMonitoringSystem;
import com.nic.RealTimeMonitoringSystem.session.PrefManager;
import com.nic.RealTimeMonitoringSystem.support.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class WorkListScreen extends AppCompatActivity implements View.OnClickListener {
    private ActivityWorkListBinding activityWorkListBinding;
    private RecyclerView recyclerView;
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
        setSupportActionBar(activityWorkListBinding.toolbar);
        initRecyclerView();

        activityWorkListBinding.finyearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    pref_finYear = FinYearList.get(position).getFinancialYear();
                    prefManager.setFinancialyearName(pref_finYear);
                    loadOfflineSchemeListDBValues(pref_finYear);
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
        recyclerView = activityWorkListBinding.workList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new fetchScheduletask().execute();
            }
        }, 2000);
    }

    public class fetchScheduletask extends AsyncTask<Void, Void,
            ArrayList<RealTimeMonitoringSystem>> {
        @Override
        protected ArrayList<RealTimeMonitoringSystem> doInBackground(Void... params) {
            dbData.open();
            ArrayList<RealTimeMonitoringSystem> villageList = new ArrayList<>();
            villageList = dbData.getAll_Village();
            Log.d("VILLAGE_COUNT", String.valueOf(villageList.size()));
            return villageList;
        }

        @Override
        protected void onPostExecute(ArrayList<RealTimeMonitoringSystem> villageList) {
            super.onPostExecute(villageList);
            workListAdapter = new WorkListAdapter(WorkListScreen.this, villageList);
            recyclerView.setAdapter(workListAdapter);
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
}

