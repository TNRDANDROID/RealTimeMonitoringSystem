package com.nic.RealTimeMonitoringSystem.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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
    public RealTimeMonitoringSystem insertDistrict(RealTimeMonitoringSystem  odfMonitoringListValue) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.DISTRICT_CODE,odfMonitoringListValue.getDistictCode());
        values.put(AppConstant.DISTRICT_NAME,odfMonitoringListValue.getDistrictName());

        long id = db.insert(DBHelper.DISTRICT_TABLE_NAME,null,values);
        Log.d("Inserted_id_district", String.valueOf(id));

        return odfMonitoringListValue;
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
    public RealTimeMonitoringSystem  insertBlock(RealTimeMonitoringSystem  odfMonitoringListValue) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.DISTRICT_CODE,odfMonitoringListValue.getDistictCode());
        values.put(AppConstant.BLOCK_CODE,odfMonitoringListValue.getBlockCode());
        values.put(AppConstant.BLOCK_NAME,odfMonitoringListValue.getBlockName());

        long id = db.insert(DBHelper.BLOCK_TABLE_NAME,null,values);
        Log.d("Inserted_id_block", String.valueOf(id));

        return odfMonitoringListValue;
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
    public RealTimeMonitoringSystem  insertVillage(RealTimeMonitoringSystem  odfMonitoringListValue) {

        ContentValues values = new ContentValues();
        values.put(AppConstant.DISTRICT_CODE,odfMonitoringListValue.getDistictCode());
        values.put(AppConstant.BLOCK_CODE,odfMonitoringListValue.getBlockCode());
        values.put(AppConstant.PV_CODE,odfMonitoringListValue.getPvCode());
        values.put(AppConstant.PV_NAME,odfMonitoringListValue.getPvName());

        long id = db.insert(DBHelper.VILLAGE_TABLE_NAME,null,values);
        Log.d("Inserted_id_village", String.valueOf(id));

        return odfMonitoringListValue;
    }

    public ArrayList<RealTimeMonitoringSystem > getAll_Village() {

        ArrayList<RealTimeMonitoringSystem > cards = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from "+DBHelper.VILLAGE_TABLE_NAME,null);
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











}
