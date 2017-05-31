package com.myxlab.bustracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myxlab.bustracker.Model.BusStop;
import com.myxlab.bustracker.Model.POI;

import java.util.LinkedList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BUS_TRACKER.db";
    private static final String TABLE_NAME_POI = "POI";
    private static final String TABLE_NAME_BUS_STOPS = "BUS_STOPS";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LAT = "lat";
    private static final String COLUMN_LON = "lon";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_TYPE = "type";

    private static final String CREATE_TABLE_LOCATION = "CREATE TABLE "+ TABLE_NAME_POI +" (" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT, "+ COLUMN_LAT + " TEXT, "+ COLUMN_LON + " TEXT, "+ COLUMN_CODE + " TEXT, "+ COLUMN_TYPE + " TEXT" + ")";
    private static final String CREATE_TABLE_BS = "CREATE TABLE "+ TABLE_NAME_BUS_STOPS +" (" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT, "+ COLUMN_LAT + " TEXT, "+ COLUMN_LON + " TEXT, "+ COLUMN_CODE + " TEXT "+  ")";


    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_LOCATION);
        sqLiteDatabase.execSQL(CREATE_TABLE_BS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IS EXISTS" + TABLE_NAME_POI);
        sqLiteDatabase.execSQL("DROP TABLE IS EXISTS" + TABLE_NAME_BUS_STOPS);
        onCreate(sqLiteDatabase);
    }

    public List<POI> getPOIs(){
        List<POI> POIList = new LinkedList<>();
        String query = "Select * From " + TABLE_NAME_POI;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        POI poi;
        if (cursor.moveToFirst()){

            do {
                poi = new POI();
                poi.setName(cursor.getString(1));
                poi.setLat(cursor.getString(2));
                poi.setLon(cursor.getString(3));
                poi.setCode(cursor.getString(4));
                poi.setType(cursor.getString(5));
                POIList.add(poi);
            }while (cursor.moveToNext());
        }
        db.close();
        return POIList;
    }

    public void addPOI(POI poiData){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME,poiData.getName());
        contentValues.put(COLUMN_LAT,poiData.getLat());
        contentValues.put(COLUMN_LON,poiData.getLon());
        contentValues.put(COLUMN_CODE,poiData.getCode());
        contentValues.put(COLUMN_TYPE,poiData.getType());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME_POI, null, contentValues);
        db.close();
    }

    public POI getPOIs(String code){
        String query = "SELECT * FROM " + TABLE_NAME_POI + " WHERE "+ COLUMN_CODE + " LIKE \'" + code + "\' ORDER BY " + COLUMN_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        POI poi = null;
        if (cursor.moveToFirst()){
            poi = new POI();
            poi.setName(cursor.getString(1));
            poi.setLat(cursor.getString(2));
            poi.setLon(cursor.getString(3));
            poi.setCode(cursor.getString(4));
            poi.setType(cursor.getString(5));
        }
        db.close();
        cursor.close();
        return poi;
    }

    public List<POI> searchPOIs(String key){
        List<POI> POIList = new LinkedList<>();
        String query = "SELECT * FROM " + TABLE_NAME_POI + " WHERE "+ COLUMN_NAME + " LIKE \'%" + key + "%\' ORDER BY " + COLUMN_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        POI poi;
        if (cursor.moveToFirst()){

            do {
                poi = new POI();
                poi.setName(cursor.getString(1));
                poi.setLat(cursor.getString(2));
                poi.setLon(cursor.getString(3));
                poi.setCode(cursor.getString(4));
                poi.setType(cursor.getString(5));
                POIList.add(poi);
            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return POIList;
    }

    public List<POI> searchPOICat(String key, String type){
        List<POI> POIList = new LinkedList<>();
        String query;

        if (key.length() == 0){
            query = "SELECT * FROM " + TABLE_NAME_POI + " WHERE "+ COLUMN_TYPE + " = \'" + type + "\' ORDER BY " + COLUMN_NAME;
        } else {
            query = "SELECT * FROM " + TABLE_NAME_POI + " WHERE "+ COLUMN_TYPE + " = \'" + type +  "\' AND " + COLUMN_NAME + " LIKE \'%"+ key +"%\' ORDER BY " + COLUMN_NAME;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        POI poi;
        if (cursor.moveToFirst()){

            do {
                poi = new POI();
                poi.setName(cursor.getString(1));
                poi.setLat(cursor.getString(2));
                poi.setLon(cursor.getString(3));
                poi.setCode(cursor.getString(4));
                poi.setType(cursor.getString(5));
                POIList.add(poi);
            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return POIList;
    }

    public void delAllData(){
        String query = "DELETE FROM " + TABLE_NAME_POI;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public List<BusStop> getBusStopList(){
        List<BusStop> BusStopList = new LinkedList<>();
        String query = "Select * From " + TABLE_NAME_BUS_STOPS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        BusStop poi;
        if (cursor.moveToFirst()){

            do {
                poi = new BusStop();
                poi.setName(cursor.getString(1));
                poi.setLat(cursor.getDouble(2));
                poi.setLon(cursor.getDouble(3));
                poi.setCode(cursor.getString(4));
                BusStopList.add(poi);
            }while (cursor.moveToNext());
        }
        db.close();
        return BusStopList;
    }

    public void addBusStop(BusStop poiData){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME,poiData.getName());
        contentValues.put(COLUMN_LAT,poiData.getLat());
        contentValues.put(COLUMN_LON,poiData.getLon());
        contentValues.put(COLUMN_CODE,poiData.getCode());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME_BUS_STOPS, null, contentValues);
        db.close();
    }

    public BusStop getBusStops(String code){
        String query = "SELECT * FROM " + TABLE_NAME_BUS_STOPS + " WHERE "+ COLUMN_CODE + " LIKE \'" + code + "\' ORDER BY " + COLUMN_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        BusStop poi = null;
        if (cursor.moveToFirst()){
            poi = new BusStop();
            poi.setName(cursor.getString(1));
            poi.setLat(cursor.getDouble(2));
            poi.setLon(cursor.getDouble(3));
            poi.setCode(cursor.getString(4));

        }
        db.close();
        cursor.close();
        return poi;
    }

    public List<BusStop> searchBusStops(String key){
        List<BusStop> BusStopList = new LinkedList<>();
        String query = "SELECT * FROM " + TABLE_NAME_BUS_STOPS + " WHERE "+ COLUMN_NAME + " LIKE \'%" + key + "%\' ORDER BY " + COLUMN_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        BusStop poi;
        if (cursor.moveToFirst()){

            do {
                poi = new BusStop();
                poi.setName(cursor.getString(1));
                poi.setLat(cursor.getDouble(2));
                poi.setLon(cursor.getDouble(3));
                poi.setCode(cursor.getString(4));

                BusStopList.add(poi);
            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return BusStopList;
    }

    public List<BusStop> searchBusStopCat(String key, String code){
        List<BusStop> BusStopList = new LinkedList<>();
        String query;

        if (key.length() == 0){
            query = "SELECT * FROM " + TABLE_NAME_BUS_STOPS + " WHERE "+ COLUMN_CODE + " = \'" + code + "\' ORDER BY " + COLUMN_NAME;
        } else {
            query = "SELECT * FROM " + TABLE_NAME_BUS_STOPS + " WHERE "+ COLUMN_CODE + " = \'" + code +  "\' AND " + COLUMN_NAME + " LIKE \'%"+ key +"%\' ORDER BY " + COLUMN_NAME;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        BusStop poi;
        if (cursor.moveToFirst()){

            do {
                poi = new BusStop();
                poi.setName(cursor.getString(1));
                poi.setLat(cursor.getDouble(2));
                poi.setLon(cursor.getDouble(3));
                poi.setCode(cursor.getString(4));

                BusStopList.add(poi);
            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return BusStopList;
    }

    public void delAllBusStopsData(){
        String query = "DELETE FROM " + TABLE_NAME_BUS_STOPS;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

}
