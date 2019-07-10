package com.nic.RealTimeMonitoringSystem.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nic.RealTimeMonitoringSystem.constant.AppConstant;
import com.nic.RealTimeMonitoringSystem.model.RealTimeMonitoringSystem;

import java.util.ArrayList;


public class dbData {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;
    private Context context;

    public dbData(Context context){
        this.dbHelper = new DBHelper(context);
        this.context = context;
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        if(dbHelper != null) {
            dbHelper.close();
        }
    }

    /****** DISTRICT TABLE *****/
    public RealTimeMonitoringSystem insertDistrict(RealTimeMonitoringSystem realTimeMonitoringSystem) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.DISTRICT_CODE, realTimeMonitoringSystem.getDistictCode());
        values.put(AppConstant.DISTRICT_NAME, realTimeMonitoringSystem.getDistrictName());

        long id = db.insert(DBHelper.DISTRICT_TABLE_NAME,null,values);
        Log.d("Inserted_id_district", String.valueOf(id));

        return realTimeMonitoringSystem;
    }

    public ArrayList<RealTimeMonitoringSystem > getAll_District() {

        ArrayList<RealTimeMonitoringSystem > cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from "+DBHelper.DISTRICT_TABLE_NAME,null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    RealTimeMonitoringSystem  card = new RealTimeMonitoringSystem ();
                    card.setDistictCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.DISTRICT_CODE)));
                    card.setDistrictName(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.DISTRICT_NAME)));

                    cards.add(card);
                }
            }
        } catch (Exception e){
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally{
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    /****** BLOCKTABLE *****/
    public RealTimeMonitoringSystem insertBlock(RealTimeMonitoringSystem realTimeMonitoringSystem) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.DISTRICT_CODE, realTimeMonitoringSystem.getDistictCode());
        values.put(AppConstant.BLOCK_CODE, realTimeMonitoringSystem.getBlockCode());
        values.put(AppConstant.BLOCK_NAME, realTimeMonitoringSystem.getBlockName());

        long id = db.insert(DBHelper.BLOCK_TABLE_NAME,null,values);
        Log.d("Inserted_id_block", String.valueOf(id));

        return realTimeMonitoringSystem;
    }

    public ArrayList<RealTimeMonitoringSystem > getAll_Block() {

        ArrayList<RealTimeMonitoringSystem > cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from "+DBHelper.BLOCK_TABLE_NAME,null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    RealTimeMonitoringSystem  card = new RealTimeMonitoringSystem ();
                    card.setDistictCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.DISTRICT_CODE)));
                    card.setBlockCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.BLOCK_CODE)));
                    card.setBlockName(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.BLOCK_NAME)));

                    cards.add(card);
                }
            }
        } catch (Exception e){
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally{
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    /****** VILLAGE TABLE *****/
    public RealTimeMonitoringSystem insertVillage(RealTimeMonitoringSystem realTimeMonitoringSystem) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.DISTRICT_CODE, realTimeMonitoringSystem.getDistictCode());
        values.put(AppConstant.BLOCK_CODE, realTimeMonitoringSystem.getBlockCode());
        values.put(AppConstant.PV_CODE, realTimeMonitoringSystem.getPvCode());
        values.put(AppConstant.PV_NAME, realTimeMonitoringSystem.getPvName());

        long id = db.insert(DBHelper.VILLAGE_TABLE_NAME,null,values);
        Log.d("Inserted_id_village", String.valueOf(id));

        return realTimeMonitoringSystem;
    }

    public ArrayList<RealTimeMonitoringSystem > getAll_Village() {

        ArrayList<RealTimeMonitoringSystem > cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from "+DBHelper.VILLAGE_TABLE_NAME+" order by pvname asc",null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    RealTimeMonitoringSystem  card = new RealTimeMonitoringSystem ();
                    card.setDistictCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.DISTRICT_CODE)));
                    card.setBlockCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.BLOCK_CODE)));
                    card.setPvCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.PV_CODE)));
                    card.setPvName(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.PV_NAME)));

                    cards.add(card);
                }
            }
        } catch (Exception e){
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally{
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    public RealTimeMonitoringSystem insertScheme(RealTimeMonitoringSystem realTimeMonitoringSystem) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.KEY_SCHEME_SEQUENTIAL_ID, realTimeMonitoringSystem.getSchemeSequentialID());
        values.put(AppConstant.KEY_SCHEME_NAME, realTimeMonitoringSystem.getSchemeName());
        values.put(AppConstant.FINANCIAL_YEAR, realTimeMonitoringSystem.getFinancialYear());

        long id = db.insert(DBHelper.SCHEME_TABLE_NAME, null, values);
        Log.d("Inserted_id_Stage", String.valueOf(id));

        return realTimeMonitoringSystem;
    }

    public ArrayList<RealTimeMonitoringSystem> getAll_Scheme() {

        ArrayList<RealTimeMonitoringSystem> cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from " + DBHelper.SCHEME_TABLE_NAME, null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    RealTimeMonitoringSystem card = new RealTimeMonitoringSystem();
                    card.setSchemeSequentialID(cursor.getInt(cursor
                            .getColumnIndexOrThrow(AppConstant.KEY_SCHEME_SEQUENTIAL_ID)));
                    card.setSchemeName(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.KEY_SCHEME_NAME)));
                    card.setFinancialYear(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.FINANCIAL_YEAR)));
                    cards.add(card);
                }
            }
        } catch (Exception e) {
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    public RealTimeMonitoringSystem insertFinYear(RealTimeMonitoringSystem realTimeMonitoringSystem) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.FINANCIAL_YEAR, realTimeMonitoringSystem.getFinancialYear());

        long id = db.insert(DBHelper.FINANCIAL_YEAR_TABLE_NAME, null, values);
        Log.d("Inserted_id_FinYear", String.valueOf(id));

        return realTimeMonitoringSystem;
    }

    public ArrayList<RealTimeMonitoringSystem> getAll_FinYear() {

        ArrayList<RealTimeMonitoringSystem> cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from " + DBHelper.FINANCIAL_YEAR_TABLE_NAME, null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    RealTimeMonitoringSystem card = new RealTimeMonitoringSystem();
                    card.setFinancialYear(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.FINANCIAL_YEAR)));

                    cards.add(card);
                }
            }
        } catch (Exception e) {
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    public RealTimeMonitoringSystem insertStage(RealTimeMonitoringSystem realTimeMonitoringSystem) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.WORK_GROUP_ID, realTimeMonitoringSystem.getWorkGroupID());
        values.put(AppConstant.WORK_TYPE_ID, realTimeMonitoringSystem.getWorkTypeID());
        values.put(AppConstant.WORK_STAGE_ORDER, realTimeMonitoringSystem.getWorkStageOrder());
        values.put(AppConstant.WORK_STAGE_CODE, realTimeMonitoringSystem.getWorkStageCode());
        values.put(AppConstant.WORK_SATGE_NAME, realTimeMonitoringSystem.getWorkStageName());

        long id = db.insert(DBHelper.WORK_STAGE_TABLE, null, values);
        Log.d("Inserted_id_Stage", String.valueOf(id));

        return realTimeMonitoringSystem;
    }

    public ArrayList<RealTimeMonitoringSystem> getAll_Stage() {

        ArrayList<RealTimeMonitoringSystem> cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from " + DBHelper.WORK_STAGE_TABLE, null);
            // cursor = db.query(CardsDBHelper.TABLE_CARDS,
            //       COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    RealTimeMonitoringSystem card = new RealTimeMonitoringSystem();
                    card.setWorkGroupID(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_GROUP_ID)));
                    card.setWorkTypeID(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_TYPE_ID)));
                    card.setWorkStageOrder(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_STAGE_ORDER)));
                    card.setWorkStageCode(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_STAGE_CODE)));

                    card.setWorkStageName(cursor.getString(cursor
                            .getColumnIndexOrThrow(AppConstant.WORK_SATGE_NAME)));

                    cards.add(card);
                }
            }
        } catch (Exception e) {
            //   Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return cards;
    }

    public void deleteDistrictTable() {
        db.execSQL("delete from " + DBHelper.DISTRICT_TABLE_NAME);
    }

    public void deleteBlockTable() {
        db.execSQL("delete from " + DBHelper.BLOCK_TABLE_NAME);
    }

    public void deleteVillageTable() {
        db.execSQL("delete from " + DBHelper.VILLAGE_TABLE_NAME);
    }

    public void deleteFinYearTable() { db.execSQL("delete from " + DBHelper.FINANCIAL_YEAR_TABLE_NAME); }

    public void deleteSchemeTable() {
        db.execSQL("delete from " + DBHelper.SCHEME_TABLE_NAME);
    }

    public void deleteWorkStageTable() {
        db.execSQL("delete from " + DBHelper.WORK_STAGE_TABLE);
    }


    public void deleteAll() {
        deleteDistrictTable();
        deleteBlockTable();
        deleteVillageTable();
        deleteFinYearTable();
        deleteSchemeTable();
        deleteWorkStageTable();
    }



}
