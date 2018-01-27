package com.gugu.biom;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gugu.biom.Fragments.InfoHumidityFragment;
import com.gugu.biom.Fragments.InfoWindFragment;
import com.gugu.biom.Fragments.TimeFragment;
import com.gugu.biom.Fragments.WeekFragmentBottom;
import com.gugu.biom.Fragments.WeekFragmentTop;

public class WeatherCenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_center);

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
