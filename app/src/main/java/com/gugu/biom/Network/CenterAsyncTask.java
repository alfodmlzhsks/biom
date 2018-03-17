package com.gugu.biom.Network;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.gugu.biom.Data.DO;
import com.gugu.biom.Database.DBManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gugu on 2018-01-27.
 */

public class CenterAsyncTask extends AsyncTask<String, String, String> {

    private LoadManager load;
    private Context context;
    private DO nowCheck;
    private DBManager manager = null;

    public CenterAsyncTask(DO data, Context context) {
        this.nowCheck = data;
        this.context = context;
        load = new LoadManager(data);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String s = load.connect();

        Log.i("t1t1", s);

        return s;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }


    //SQLite에다가 값을 저장만하고 앱킬때 그때 내장DB에서 불러와서 세팅하면되는거임
    //여기서는 그냥 저장만
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        ContentValues values = new ContentValues();

        manager = new DBManager(this.context);
        manager.dbOpen();



        try {
            if (nowCheck.getAction().equals("main")) {   //MainActivity에서 요청한경우
                JSONObject root = new JSONObject(s);

                JSONObject response = root.getJSONObject("response");

                JSONObject body = response.getJSONObject("body");


                JSONObject items = body.getJSONObject("items");
                JSONArray item = items.getJSONArray("item");

                String realTemp = item.getJSONObject(5).getString("obsrValue");

                values.put("nowTemp", realTemp);
                manager.insertDB(values);
            } else if (nowCheck.getAction().equals("center")) {

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
