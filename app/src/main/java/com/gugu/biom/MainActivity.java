package com.gugu.biom;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gugu.biom.Data.DO;
import com.gugu.biom.Database.DBManager;
import com.gugu.biom.Fragments.FirstFragment;
import com.gugu.biom.Fragments.FourthFragment;
import com.gugu.biom.Fragments.SecondFragment;
import com.gugu.biom.Fragments.ThirdFragment;
import com.gugu.biom.LikeListView.ListAdapter;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ViewPager vp;
    TextView tv_left, tv_right; //지역메뉴 id 값
    LinearLayout ll1, ll2; //상단 레이아웃, 하단 레이아웃
    LinearLayout ll_image; // 상단 레이아웃 내부 왼쪽
    LinearLayout lll_1, lll_2, lll_3, lll_4; // 물방울 쪽 배경 레이아웃
    ImageView ivCurrent; //현재 날씨 이미지
    TextView tv_temp, tv_difference, tv_rainfall, tv_finedust, tv_o3;
    DO data;
    DBManager manager = null;


    ImageView iv_1, iv_2, iv_3, iv_4;

    ImageButton ibtnDrawerOpen;
    DrawerLayout drawerLayout;
    private ListView lvSetting = null;
    ListAdapter adapter;

    static int vpSetting = 3; //위치설정에 따른 갯수값

    static boolean isOnlyOne = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = new DO();
        manager = new DBManager(this);
        manager.dbOpen();

        vp = (ViewPager) findViewById(R.id.vp);


        iv_1 = (ImageView) findViewById(R.id.iv_1);
        iv_2 = (ImageView) findViewById(R.id.iv_2);
        iv_3 = (ImageView) findViewById(R.id.iv_3);
        iv_4 = (ImageView) findViewById(R.id.iv_4);

        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll_image = (LinearLayout) findViewById(R.id.ll_image);
        lll_1 = (LinearLayout) findViewById(R.id.lll_1);
        lll_2 = (LinearLayout) findViewById(R.id.lll_2);
        lll_3 = (LinearLayout) findViewById(R.id.lll_3);
        lll_4 = (LinearLayout) findViewById(R.id.lll_4);

        ivCurrent = (ImageView) findViewById(R.id.ivCurrent);
        tv_temp = (TextView) findViewById(R.id.tv_temp);
        tv_difference = (TextView) findViewById(R.id.tv_difference);
        tv_rainfall = (TextView) findViewById(R.id.tv_rainfall);
        tv_finedust = (TextView) findViewById(R.id.tv_finedust);
        tv_o3 = (TextView) findViewById(R.id.tv_o3);

        Bundle dbData = manager.selectDB("nowTemp");

        if(dbData == null) {
            Log.i("t98t98t98", "처음이야");
            Intent i = new Intent(this, UpdateService.class);
            startService(i);
        }
        else {
            Log.i("t98t98t98", "이미있음");
            double a = Double.parseDouble(dbData.getString("nowTemp"));

            if (a < 0.0) {
                tv_temp.setText("-" + Math.round(a));
            } else {
                tv_temp.setText("" + Math.round(a));
            }


            //*--------------------------
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent i = new Intent(this, UpdateService.class);
            PendingIntent p = PendingIntent.getService(MainActivity.this, 0, i, 0); //서비스로 알람


            final Calendar cal = Calendar.getInstance();
            int mHour = cal.get(Calendar.HOUR_OF_DAY);
            int mMinute = cal.get(Calendar.MINUTE);
            int mSecond = cal.get(Calendar.SECOND);

            Toast.makeText(this, "현재 시간" + mHour + ":" + mMinute + ":" + mSecond, Toast.LENGTH_SHORT).show();

            Log.i("t3t3", "" + cal.getTimeInMillis());

            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 1000 * 60, p);

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
                    Intent i = new Intent(getApplicationContext(), WebActivity.class);
                    startActivity(i);
                }
            });

            ibtnDrawerOpen = (ImageButton) findViewById(R.id.ibtnDrawer);
            ibtnDrawerOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
                    lvSetting = (ListView) findViewById(R.id.lvSetting);
                    lvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i == 2) {
                                Intent intent = new Intent(getApplicationContext(), AddLocationActivity.class);
                                startActivity(intent);
                            }

                        }
                    });

                    if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        drawerLayout.openDrawer(Gravity.LEFT);
                        adapter = new ListAdapter();
                        adapter.addItem("설정", "멀까이건");
                        adapter.addItem("알림설정");
                        adapter.addItem_a("위치추가");
                        adapter.addItem_aa("정보");
                        adapter.addItem_aaa("blaaaah");
                        lvSetting.setAdapter(adapter);

                    }
                }
            });

        }
    }

    private class pagerAdapter extends FragmentStatePagerAdapter {
        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
            switch (vpSetting) {
                case 1:
                    lll_1.setVisibility(View.VISIBLE);
                    iv_1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    lll_1.setVisibility(View.VISIBLE);
                    lll_2.setVisibility(View.VISIBLE);
                    if (vp.getCurrentItem() == 0) {
                        iv_1.setVisibility(View.VISIBLE);
                        iv_2.setVisibility(View.INVISIBLE);
                    } else if (vp.getCurrentItem() == 1) {
                        iv_1.setVisibility(View.INVISIBLE);
                        iv_2.setVisibility(View.VISIBLE);
                    }
                    break;
                case 3:
                    lll_1.setVisibility(View.VISIBLE);
                    lll_2.setVisibility(View.VISIBLE);
                    lll_3.setVisibility(View.VISIBLE);
                    if (vp.getCurrentItem() == 0) {
                        iv_1.setVisibility(View.VISIBLE);
                        iv_2.setVisibility(View.INVISIBLE);
                        iv_3.setVisibility(View.INVISIBLE);
                    } else if (vp.getCurrentItem() == 1) {
                        iv_1.setVisibility(View.INVISIBLE);
                        iv_2.setVisibility(View.VISIBLE);
                        iv_3.setVisibility(View.INVISIBLE);
                    } else if (vp.getCurrentItem() == 2) {
                        iv_1.setVisibility(View.INVISIBLE);
                        iv_2.setVisibility(View.INVISIBLE);
                        iv_3.setVisibility(View.VISIBLE);
                    } else if (vp.getCurrentItem() >= 3) {
                        iv_1.setVisibility(View.INVISIBLE);
                    }
                    break;
                case 4:
                    lll_1.setVisibility(View.VISIBLE);
                    lll_2.setVisibility(View.VISIBLE);
                    lll_3.setVisibility(View.VISIBLE);
                    lll_4.setVisibility(View.VISIBLE);
                    if (vp.getCurrentItem() == 0) {
                        iv_1.setVisibility(View.VISIBLE);
                        iv_2.setVisibility(View.INVISIBLE);
                        iv_3.setVisibility(View.INVISIBLE);
                        iv_4.setVisibility(View.INVISIBLE);
                    } else if (vp.getCurrentItem() == 1) {
                        iv_1.setVisibility(View.INVISIBLE);
                        iv_2.setVisibility(View.VISIBLE);
                        iv_3.setVisibility(View.INVISIBLE);
                        iv_4.setVisibility(View.INVISIBLE);

                    } else if (vp.getCurrentItem() == 2) {
                        iv_1.setVisibility(View.INVISIBLE);
                        iv_2.setVisibility(View.INVISIBLE);
                        iv_3.setVisibility(View.VISIBLE);
                        iv_4.setVisibility(View.INVISIBLE);

                    } else if (vp.getCurrentItem() == 3) {
                        iv_1.setVisibility(View.INVISIBLE);
                        iv_2.setVisibility(View.INVISIBLE);
                        iv_3.setVisibility(View.INVISIBLE);
                        iv_4.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    break;
            }
        }

        public pagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);

        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (vpSetting) {
                case 1:
                    if (position == 0) {
                        return new FirstFragment();
                    } else return null;
                case 2:
                    if (position == 0) {
                        return new FirstFragment();
                    } else if (position == 1) {
                        return new SecondFragment();
                    } else return null;
                case 3:
                    if (position == 0) {
                        return new FirstFragment();
                    } else if (position == 1) {
                        return new SecondFragment();
                    } else if (position == 2) {
                        return new ThirdFragment();
                    } else return null;
                case 4:
                    if (position == 0) {
                        return new FirstFragment();
                    } else if (position == 1) {
                        return new SecondFragment();
                    } else if (position == 2) {
                        return new ThirdFragment();
                    } else if (position == 3) {
                        return new FourthFragment();
                    } else return null;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return vpSetting;
        }
    }
}
