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
        jnitxt.setText(stringFromJNI());
    }

    public native String stringFromJNI();

}
