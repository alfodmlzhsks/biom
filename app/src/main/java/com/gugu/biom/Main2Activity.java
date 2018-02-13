package com.gugu.biom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    static{
        System.loadLibrary("hello-jni");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView jnitxt = (TextView)findViewById(R.id.jnitxt);

//        int[] j = getXY(37.4535195, 126.90311870000005);
//        jnitxt.setText(""+j[0]+":"+j[1]);
    }

    public static int[] transXY(double lati, double longi) {

        return getXY(lati, longi);
    }

    public static native int[] getXY(double lati, double longi);



}
