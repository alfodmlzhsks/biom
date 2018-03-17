package com.gugu.biom;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.gugu.biom.Network.CenterAsyncTask;
import com.gugu.biom.Data.DO;
import com.gugu.biom.Data.FragmentDO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by gugu on 2018-02-22.
 */

public class GPS {

    boolean isOnlyOne = true;
    DO data = new DO();
    CenterAsyncTask task = null;
    Context context = null;
    LocationManager lm;
    int[] xy;
    Geocoder geocoder;
    static String[] nowPoss;
    String action = "";

    public GPS(Context context, String action) {
        geocoder = new Geocoder(context);
        xy = new int[2];
        this.context = context;
        this.action = action;
        getPosition(context);
    }


    public void getPosition(Context context) {
        try {

            //LocationManager 객체를 얻어온다
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            //GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, mLocationListener);
            //(등록할 위치 제공자, 통지사이의 최소 시간 간격(millisecond),통지사이의 최소 변경 거리(m),    );
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, mLocationListener);


            //현재기온 알아오기위한 거임
            final Calendar c = Calendar.getInstance();
            final Date date = new Date();
            final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");


            Integer tTime = c.get(Calendar.HOUR_OF_DAY);    //동네예보위해서
            Integer time = tTime - 1; //초단기실황위해서

            if (time == -1) {
                time = 23;
            }
            String nowTime = tTime.toString();
            String nowTime2 = time.toString();

            if (tTime / 10 == 0) {
                nowTime = "0" + nowTime;
            }
            if (time / 10 == 0) {
                nowTime2 = "0" + nowTime2;
            }


            Log.i("t2t2", nowTime + "");

            if (action.equals("main")) {

                data.setBase_time(nowTime2 + "00");        //초단기실황 요청위함, 현재시간 ex) 1300
                data.setBase_date(format.format(date)); //초단기실황 요청위함    ex)0 3 6 9 12 15 ~~
                data.setAction(action);
            } else if (action.equals("center")) {
                switch(tTime) {
                    case 2:
                    case 5:
                    case 8:
                    case 11:
                    case 14:
                    case 17:
                    case 20:
                    case 23:
                        data.setBase_time(nowTime+"00");
                        data.setBase_date(format.format(date));
                        data.setAction(action);
                        break;
                    default:
                        //이때는 connectCheck메소드가 실행안되야됨
                        break;
                }

            }

        } catch (
                SecurityException e)

        {

        }

    }

    public void connectCheck(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if ((networkInfo != null) && networkInfo.isConnected()) {
            task = new CenterAsyncTask(data, context);
            task.execute("");
        } else {
            Toast.makeText(context, "인터넷연결상태를 확인하세요", Toast.LENGTH_LONG).show();
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.

            Log.d("test", "onLocationChanged, location:" + location);
            double longitude = location.getLongitude();//경도
            double latitude = location.getLatitude();//위도

            if (isOnlyOne && (data.getAction() != null)) {
                xy = Main2Activity.getXY(latitude, longitude);
                data.setNx("" + xy[0]);
                data.setNy("" + xy[1]);

                isOnlyOne = false;
                Log.i("gngkgngk", "외골수");
                connectCheck(context);
            }

            List<Address> list = null;
            try {
                double d1 = latitude;
                double d2 = longitude;

                list = geocoder.getFromLocation(
                        d1, // 위도
                        d2, // 경도
                        10); // 얻어올 값의 개수
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
            }
            if (list != null) {
                nowPoss = list.get(0).getAddressLine(0).substring(5).split(" ");

                FragmentDO da = FragmentDO.getInstance();
                da.setNowPoss(nowPoss);
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {//(provider,status,extras)
            // 변경시
            Log.d("test", "onStatusChanged, provider:" + s + ", status:" + i + " ,Bundle:" + bundle);


        }

        @Override
        public void onProviderEnabled(String s) {
            //Enabled
            Log.d("test", "onProviderEnabled, provider:" + s);

        }

        @Override
        public void onProviderDisabled(String s) {
            //Disabled
            Log.d("test", "onProviderDisabled, provider:" + s);

        }
    };

}
