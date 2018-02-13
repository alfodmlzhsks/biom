package com.gugu.biom;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gugu.biom.Fragments.FirstFragment;
import com.gugu.biom.Fragments.FourthFragment;
import com.gugu.biom.Fragments.SecondFragment;
import com.gugu.biom.Fragments.ThirdFragment;
import com.gugu.biom.Network.CenterAsyncTask;
import com.gugu.biom.Network.DO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ViewPager vp;
    TextView tv_left, tv_right; //지역메뉴 id 값
    LinearLayout ll1, ll2; //상단 레이아웃, 하단 레이아웃
    LinearLayout ll_image; // 상단 레이아웃 내부 왼쪽
    ImageView ivCurrent; //현재 날씨 이미지
    TextView tv_temp, tv_difference, tv_rainfall, tv_finedust, tv_o3;
    LocationManager lm;
    Geocoder geocoder;
    static String[] nowPoss;
    DO data;
    boolean isOnlyOne = true;
    CenterAsyncTask task = null;
    int[] xy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new DO();
        vp = (ViewPager) findViewById(R.id.vp);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll_image = (LinearLayout) findViewById(R.id.ll_image);
        ivCurrent = (ImageView) findViewById(R.id.ivCurrent);
        tv_temp = (TextView) findViewById(R.id.tv_temp);
        tv_difference = (TextView) findViewById(R.id.tv_difference);
        tv_rainfall = (TextView) findViewById(R.id.tv_rainfall);
        tv_finedust = (TextView) findViewById(R.id.tv_finedust);
        tv_o3 = (TextView) findViewById(R.id.tv_o3);
        geocoder = new Geocoder(this);

        ll1.setEnabled(false);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ll1.setEnabled(true);
            }
        }, 2000);

        //LocationManager 객체를 얻어온다
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, mLocationListener);
        //(등록할 위치 제공자, 통지사이의 최소 시간 간격(millisecond),통지사이의 최소 변경 거리(m),    );
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, mLocationListener);

        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), WeatherCenterActivity.class);
                i.putExtra("temp", tv_temp.getText().toString());
                startActivity(i);
            }
        });

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SnsActivity.class);
                startActivity(i);
            }
        });

        final Date date = new Date();
        final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int[] base_time = {2, 5, 8, 11, 14, 17, 20, 23};
                SimpleDateFormat format2 = new SimpleDateFormat("H");
                int nowTime = Integer.parseInt(format2.format(date));

                int index = 0;

                while(true) {
                    if(nowTime==0 || nowTime==1) {

                        //날짜도 어제날짜로 설정해줘야함
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

                        Calendar calendar = Calendar.getInstance();

                        calendar.add(Calendar.DATE, -1);  // 오늘 날짜에서 하루를 뺌.

                        String date = sdf.format(calendar.getTime());

                        data.setBase_time(String.format("%02d", 23)+"00");
                        data.setBase_date(date);

                        Log.i("t1t1", date);
                        break;
                    }
                    if(nowTime <= base_time[index]) {
                        Log.i("ttttt", String.format("%02d", base_time[index-1])+"00");

                        data.setBase_time(String.format("%02d", base_time[index-1])+"00");
                        data.setBase_date(format.format(date));
                        break;
                    }
                    index++;
                }
            }
        });

        data.set_type("json");

    }

    public void connectCheck() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if ((networkInfo != null) && networkInfo.isConnected()) {
            task = new CenterAsyncTask(data, this);
            task.execute("");
        } else {
            Toast.makeText(this, "인터넷연결상태를 확인하세요", Toast.LENGTH_LONG).show();
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
            double altitude = location.getAltitude();//고도
            float accuracy = location.getAccuracy();//정확도
            String provider = location.getProvider();//위치제공자자
            //GPS 위치제공자에 의한 위치변화, 오차범위가 좁다.
            //Network 위치제공자에 의한 위치변화
            //Network 위치는 GPS에 비해 정확도가 많이 떨어진다.
            Log.d("tttt", "" + longitude);
            Log.d("tttt", "" + latitude);

            if(isOnlyOne) {
                xy = Main2Activity.getXY(latitude, longitude);
                data.setNx(""+xy[0]);
                data.setNy(""+xy[1]);
                data.setAction("main");
                isOnlyOne = false;

                connectCheck();
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
                Log.d("tttt1", nowPoss[1]); //금천구
                Log.d("tttt1", nowPoss[2]); //시흥1동
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

    private class pagerAdapter extends FragmentStatePagerAdapter {
        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
            if (vp.getCurrentItem() == 0) {
                tv_left.setText("");
                tv_right.setText("하남");

            } else if (vp.getCurrentItem() == 1) {
                tv_left.setText("정왕");
                tv_right.setText("금천");

            } else if (vp.getCurrentItem() == 2) {
                tv_left.setText("하남");
                tv_right.setText("김포");

            } else if (vp.getCurrentItem() == 3) {
                tv_left.setText("금천");
                tv_right.setText("");
            }
        }

        public pagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);

        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:

                    return new FirstFragment();
                case 1:

                    return new SecondFragment();
                case 2:

                    return new ThirdFragment();
                case 3:

                    return new FourthFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        lm.removeUpdates(mLocationListener);
    }
}
