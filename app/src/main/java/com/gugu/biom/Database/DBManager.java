package com.gugu.biom.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by gugu on 2018-02-23.
 */

public class DBManager {

    private final String DB_NAME = "biom.db";
    private final int DB_VERSION = 1;

    private Context mContext = null;
    private SQLiteDatabase db = null;
    private OpenHelper helper = null;

    private class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        //생성된 db가 없을경우에 한번호출되는거임
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table biom(" +
                    "nowTemp varchar(10)," +
                    "nowWeather varchar(10)," +
                    "tFirst varchar(10)," +
                    "tSecond varchar(10)," +
                    "tThird varchar(10)," +
                    "tFourth varchar(10)," +
                    "tFifth varchar(10)," +
                    "tSixth varchar(10));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

    }

    public DBManager(Context context) {
        this.mContext = context;
        this.helper = new OpenHelper(mContext, DB_NAME, null, DB_VERSION);
    }

    public void dbOpen() {
        this.db = helper.getWritableDatabase();
    }

    public void dbClose() {
        this.db.close();
    }

    public void insertDB(ContentValues values) {
        this.db.insert("biom", null, values);
    }

    public Bundle selectDB(String... cols) {
        Bundle data = new Bundle();
        String nowTemp = "";
        try {

            Cursor c = this.db.query("biom", cols, null, null, null, null, null);

            c.moveToLast();
            nowTemp = c.getString(c.getColumnIndex("nowTemp"));
        }catch(Exception e) {
            return null;
        }


        Log.i("rnrkrnrk", ""+nowTemp);

        data.putString("nowTemp", ""+nowTemp);

        return data;
    }

}
