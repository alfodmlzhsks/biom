package com.gugu.biom.Network;

import android.util.Log;

import com.gugu.biom.Data.DO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by gugu on 2017-05-18.
 */

public class LoadManager {
    private URL url;
    private HttpURLConnection conn;
    private BufferedReader bis = null;
    private String data = "";
    private String m_url = "";

    public LoadManager(DO info) {
        try {
            if(info.getAction().equals("main")) {
                m_url = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastGrib?";
                Log.i("t9t9", "메인");
            }
            else if(info.getAction().equals("center")) {
                m_url = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData?";
                Log.i("t9t9", "센터");
                Log.i("t9t9", info.getBase_date());
                Log.i("t9t9", info.getBase_time());
                Log.i("t9t9", info.getNx());
                Log.i("t9t9", info.getNy());
            }
            Log.i("t1t1", ""+info.getNumOfRows());

            String strAddr = "serviceKey="+info.getKey()+"&base_date="+info.getBase_date()+"&base_time="+info.getBase_time()+"&nx="+info.getNx()+"&ny="+info.getNy()+"&numOfRows="+info.getNumOfRows()+"&pageNo="+info.getPageNo()+"&_type=json";
            url = new URL(m_url + strAddr);

            conn = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String connect() {
        String line = "";

        try {
            conn.connect();

            InputStream is = conn.getInputStream();
            bis = new BufferedReader(new InputStreamReader(is));

            while((line=bis.readLine())!=null) {
                data+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bis!=null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }
}
