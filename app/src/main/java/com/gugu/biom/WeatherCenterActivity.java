package com.gugu.biom;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_center);

        i = getIntent();

        tvNowTemp = (TextView) findViewById(R.id.tvNowTemp);
        tvNowPosition = (TextView) findViewById(R.id.tvNowPosition);
        tvToday = (TextView) findViewById(R.id.tvToday);

        final TextView[] tvTimes = {
                (TextView) findViewById(R.id.tvTime1),
                (TextView) findViewById(R.id.tvTime2),
                (TextView) findViewById(R.id.tvTime3),
                (TextView) findViewById(R.id.tvTime4),
                (TextView) findViewById(R.id.tvTime5),
                (TextView) findViewById(R.id.tvTime6)
        };

        tvNowTemp.setText(i.getStringExtra("temp"));
        tvNowPosition.setText("" + MainActivity.nowPoss[1] + " " + MainActivity.nowPoss[2]);

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        switch (date.getDay()) {
            case 0:
                tvToday.setText(new SimpleDateFormat("M월 dd일").format(date)+" 일요일");
                break;
            case 1:
                tvToday.setText(new SimpleDateFormat("M월 dd일").format(date)+" 월요일");
                break;
            case 2:
                tvToday.setText(new SimpleDateFormat("M월 dd일").format(date)+" 화요일");
                break;
            case 3:
                tvToday.setText(new SimpleDateFormat("M월 dd일").format(date)+" 수요일");
                break;
            case 4:
                tvToday.setText(new SimpleDateFormat("M월 dd일").format(date)+" 목요일");
                break;
            case 5:
                tvToday.setText(new SimpleDateFormat("M월 dd일").format(date)+" 금요일");
                break;
            case 6:
                tvToday.setText(new SimpleDateFormat("M월 dd일").format(date)+" 토요일");
                break;
            default:
                break;
        }


        SimpleDateFormat format = new SimpleDateFormat("H");
        final int nowTime = Integer.parseInt(format.format(date));

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int[] base_time = {2, 5, 8, 11, 14, 17, 20, 23};

                int index = 0;
                int count = 0;
                while (true) {

                    if (nowTime <= base_time[index]) {
                        break;
                    }
                    index++;
                }

                while (count < 6) {
                    tvTimes[count].setText(base_time[index] + "시");
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
