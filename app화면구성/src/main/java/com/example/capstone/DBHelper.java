package com.example.capstone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=13;

    public DBHelper(Context context){
        super(context, "locationdb", null, DATABASE_VERSION);
    }

    @Override//어플이 인스톨된 후 최초로 이용되는순간 딱 한번만 호출 즉 테이블생성
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String locationSql="create table location ("+
//                "_id integer primary key autoincrement, " +
//                "x, " +
//                "y, " +
//                "lat, "+
//                "lon)";
        String loc1Sql="create table loc1 ("+
                "_id integer primary key autoincrement, " +
                "x, " +
                "y, " +
                "lat, "+
                "lon)";
        String loc2Sql="create table loc2 ("+
                "_id integer primary key autoincrement, " +
                "x, " +
                "y, " +
                "lat, "+
                "lon)";
        String loc3Sql="create table loc3 ("+
                "_id integer primary key autoincrement, " +
                "x, " +
                "y, " +
                "lat, "+
                "lon)";
        String loc4Sql="create table loc4 ("+
                "_id integer primary key autoincrement, " +
                "x, " +
                "y, " +
                "lat, "+
                "lon)";
        String loc5Sql="create table loc5 ("+
                "_id integer primary key autoincrement, " +
                "x, " +
                "y, " +
                "lat, "+
                "lon)";
        String loc6Sql="create table loc6 ("+
                "_id integer primary key autoincrement, " +
                "x, " +
                "y, " +
                "lat, "+
                "lon)";


//        sqLiteDatabase.execSQL(locationSql);
        sqLiteDatabase.execSQL(loc1Sql);
        sqLiteDatabase.execSQL(loc2Sql);
        sqLiteDatabase.execSQL(loc3Sql);
        sqLiteDatabase.execSQL(loc4Sql);
        sqLiteDatabase.execSQL(loc5Sql);
        sqLiteDatabase.execSQL(loc6Sql);
    }

    @Override//버전이 변경될때마다 호출됨
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i1 == DATABASE_VERSION){
//            sqLiteDatabase.execSQL("drop table location");
            sqLiteDatabase.execSQL("drop table loc1");
            sqLiteDatabase.execSQL("drop table loc2");
            sqLiteDatabase.execSQL("drop table loc3");
            sqLiteDatabase.execSQL("drop table loc4");
            sqLiteDatabase.execSQL("drop table loc5");
            sqLiteDatabase.execSQL("drop table loc6");


            onCreate(sqLiteDatabase);
        }
    }
}
