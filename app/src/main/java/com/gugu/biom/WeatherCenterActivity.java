package com.gugu.biom;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gugu.biom.Data.FragmentDO;
import com.gugu.biom.Database.DBManager;
import com.gugu.biom.Fragments.InfoHumidityFragment;
import com.gugu.biom.Fragments.InfoWindFragment;
import com.gugu.biom.Fragments.TimeFragment;
import com.gugu.biom.Fragments.WeekFragmentBottom;
import com.gugu.biom.Fragments.WeekFragmentTop;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherCenterActivity extends AppCompatActivity {

    Intent i = null;
    TextView tvNowTemp = null;
    TextView tvNowPosition = null;
    TextView tvToday = null;
    FragmentDO data = null;
    DBManager manager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_center);

        manager = new DBManager(this);
        manager.dbOpen();

        i = getIntent();
        data = FragmentDO.getInstance();

//        Log.i("t9t9", data.getNowPoss()[0]);

        tvNowTemp = (TextView) findViewById(R.id.tvNowTemp);
        tvNowPosition = (TextView) findViewById(R.id.tvNowPosition);
        tvToday = (TextView) findViewById(R.id.tvToday);

        Bundle dbData = manager.selectDB("nowTemp");
        double a = Double.parseDouble(dbData.getString("nowTemp"));

        if (a < 0.0) {
            tvNowTemp.setText("-" + Math.round(a));
        } else {
            tvNowTemp.setText("" + Math.round(a));
        }

        final TextView[] tvTimes = {
                (TextView) findViewById(R.id.tvTime1),
                (TextView) findViewById(R.id.tvTime2),
                (TextView) findViewById(R.id.tvTime3),
                (TextView) findViewById(R.id.tvTime4),
                (TextView) findViewById(R.id.tvTime5),
                (TextView) findViewById(R.id.tvTime6)
        };


        Date date = new Date();


        SimpleDateFormat format = new SimpleDateFormat("H");
        final int nowTime = Integer.parseInt(format.format(date));

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                int[] base_time = {2, 5, 8, 11, 14, 17, 20, 23};
                int[] real_time = {0, 3, 6, 9, 12, 15, 18, 21};

                int index = 0;
                int count = 0;
                while (true) {

                    if (nowTime < base_time[index]) {

                        if (index == 7) {
                            index = 0;
                            break;
                        } else {
                            index++;
                            break;
                        }

                    }
                    index++;
                }

                while (count < 6) {
                    tvTimes[count].setText(real_time[index] + "시");
                    if (index == 7) index = -1;
                    index++;
                    count++;
                }
            }
        });

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.llTimeToken, new TimeFragment());
        ft.add(R.id.llWeekTokenTop, new WeekFragmentTop());
        ft.add(R.id.llWeekTokenBottom, new WeekFragmentBottom());
        ft.add(R.id.llInfoHumidity, new InfoHumidityFragment());
        ft.add(R.id.llInfoWind, new InfoWindFragment());
        ft.commit();
    }

}
