package com.gugu.biom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gugu.biom.Fragments.FirstFragment;
import com.gugu.biom.Fragments.FourthFragment;
import com.gugu.biom.Fragments.SecondFragment;
import com.gugu.biom.Fragments.ThirdFragment;

public class MainActivity extends AppCompatActivity {

    ViewPager vp;
    TextView tv_left,tv_right; //지역메뉴 id 값
    LinearLayout ll1,ll2; //상단 레이아웃, 하단 레이아웃
    LinearLayout ll_image; // 상단 레이아웃 내부 왼쪽
    ImageView ivCurrent; //현재 날씨 이미지
    TextView tv_temp,tv_difference,tv_rainfall,tv_finedust,tv_o3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp = (ViewPager)findViewById(R.id.vp);
        tv_left = (TextView)findViewById(R.id.tv_left);
        tv_right = (TextView)findViewById(R.id.tv_right);
        ll1 = (LinearLayout)findViewById(R.id.ll1);
        ll2 = (LinearLayout)findViewById(R.id.ll2);
        ll_image = (LinearLayout)findViewById(R.id.ll_image);
        ivCurrent = (ImageView)findViewById(R.id.ivCurrent);
        tv_temp = (TextView)findViewById(R.id.tv_temp);
        tv_difference = (TextView)findViewById(R.id.tv_difference);
        tv_rainfall = (TextView)findViewById(R.id.tv_rainfall);
        tv_finedust = (TextView)findViewById(R.id.tv_finedust);
        tv_o3 = (TextView)findViewById(R.id.tv_o3);

        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), WeatherCenterActivity.class);
                startActivity(i);
            }
        });

        ll2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){

                    Intent i = new Intent(getApplicationContext(), SnsActivity.class);
                    startActivity(i);

                }
                return true;
            }
        });
    }
    private class pagerAdapter extends FragmentStatePagerAdapter
    {
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

            } else if (vp.getCurrentItem()==3){
                tv_left.setText("금천");
                tv_right.setText("");
            }
        }

        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);

        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch(position)
            {
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
        public int getCount()
        {
            return 4;
        }
    }
}
