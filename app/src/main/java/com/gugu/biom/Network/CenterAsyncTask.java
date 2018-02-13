package com.gugu.biom.Network;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.gugu.biom.R;

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

        Log.i("t1t1", s);   //json결과값 확인

        return s;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            if(nowCheck.getAction().equals("main")) {   //MainActivity에서 요청한경우
                final FragmentDO fInfo = FragmentDO.getInstance();

                JSONObject root = new JSONObject(s);
                JSONObject response = root.getJSONObject("response");
                JSONObject body = response.getJSONObject("body");
                JSONObject items = body.getJSONObject("items");
                final JSONArray item = items.getJSONArray("item");

                int i=0;
                while(item.getJSONObject(i) != null) {
                    if(item.getJSONObject(i).getString("category").equals("T3H")) {
                        break;
                    }
                    i++;
                }

                String nowTemp = item.getJSONObject(i).getString("fcstValue");    //현재기온

                TextView tv_temp = ((Activity)context).findViewById(R.id.tv_temp);
                tv_temp.setText(nowTemp);

                final int[] temps = new int[6];

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i = 0;
                        int j = 0;

                        while(i < item.length()) {
                            try {
                                if(item.getJSONObject(i).getString("category").equals("T3H")) {
                                    temps[j] = Integer.parseInt(item.getJSONObject(i).getString("fcstValue"));

                                    switch(j) {
                                        case 0:
                                            fInfo.settFirst(temps[j]);
                                            break;
                                        case 1:
                                            fInfo.settSecond(temps[j]);
                                            break;
                                        case 2:
                                            fInfo.settThird(temps[j]);
                                            break;
                                        case 3:
                                            fInfo.settFourth(temps[j]);
                                            break;
                                        case 4:
                                            fInfo.settFifth(temps[j]);
                                            break;
                                        case 5:
                                            fInfo.settFifth(temps[j]);
                                            break;
                                        case 6:
                                            fInfo.settSixth(temps[j]);
                                            break;
                                        default:
                                            break;
                                    }
                                    j++;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            i++;

                        }
                    }
                }).start();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
