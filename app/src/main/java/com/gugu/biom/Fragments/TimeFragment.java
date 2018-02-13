package com.gugu.biom.Fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.gugu.biom.Network.FragmentDO;
import com.gugu.biom.R;

import java.util.ArrayList;

/**
 * Created by gugu on 2018-01-26.
 */

public class TimeFragment extends Fragment {

    private LineChart mChart;
    private float fi = -2;
    private FragmentDO info = FragmentDO.getInstance();
    private int t1, t2, t3, t4, t5, t6;

    public TimeFragment() {
        super();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);

        t1 = info.gettFirst();
        t2 = info.gettSecond();
        t3 = info.gettThird();
        t4 = info.gettFourth();
        t5 = info.gettFifth();
        t6 = info.gettSixth();
        int avg = (t1+t2+t3+t4+t5+t6)/6;

        mChart = (LineChart) view.findViewById(R.id.chart1);

        // description 설정안하기
        mChart.getDescription().setEnabled(false);

        // 터치 허용여부
        mChart.setTouchEnabled(false);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // 크기확대 및 드래그 유무
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);
        // 두손 줌인 할지 말지 ㅇㅇ
        mChart.setPinchZoom(false);

        //배경색깔
//        mChart.setBackgroundColor(Color.WHITE);
        //화면에 표시될 그래프 점 개수(count), 그래프 값(range)
        // add data
        setData(6, 30);

        mChart.animateX(50);

        Legend l = mChart.getLegend();

        //아래쪽 범주없애버리려고
//        l.setForm(Legend.LegendForm.LINE);
//        l.setTypeface(mTfLight);
//        l.setTextSize(15f);
//        l.setTextColor(Color.RED);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
        l.setYOffset(-5000f);
        l.setXOffset(-2000f);

        //맨윗줄 범위
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        //왼쪽 범위
        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(avg+10f);    //최대값
        leftAxis.setAxisMinimum(avg-10f);  //최소값
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularityEnabled(false);
        leftAxis.setAxisLineColor(Color.WHITE);

        //오른쪽 범위
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTextColor(Color.WHITE);
        rightAxis.setAxisMaximum(avg+10f);  //최대값
        rightAxis.setAxisMinimum(avg-10f); //최소값
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
        rightAxis.setAxisLineColor(Color.WHITE);
        return view;
    }

    private void setData(int count, float range) {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

//        for (int i = 0; i < count; i++) {
//            float mult = range;
//            float val = -5;
//            yVals1.add(new Entry(i, val+fi));
//            fi = fi-2;
//        }



        //그래프 점 추가하는거임 -> (인덱스, 값)
        yVals1.add(new Entry(0, t1));
        yVals1.add(new Entry(1, t2));
        yVals1.add(new Entry(2, t3));
        yVals1.add(new Entry(3, t4));
        yVals1.add(new Entry(4, t5));
        yVals1.add(new Entry(5, t6));

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            //그래프 그리기
            set1 = new LineDataSet(yVals1, "DataSet 3");
            set1.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set1.setColor(Color.rgb(108,158,177));
            set1.setCircleColor(Color.rgb(108,158,177));
            set1.setLineWidth(1.3f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            set1.setDrawCircleHole(false);
            set1.setHighLightColor(Color.rgb(244, 117, 117));

            //LineData data = new LineData(set1, set2 ,,,,); 이런식으로 여러개 그릴수도 있음
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.rgb(107,107,107));
            data.setValueTextSize(12f);
            //그래프 데이터 설정하기
            mChart.setData(data);

        }
    }
}
