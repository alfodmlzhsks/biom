package com.gugu.biom;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateService extends Service {

    public UpdateService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        int mSecond = c.get(Calendar.SECOND);


        Toast.makeText(getApplicationContext(), "현재 시간ㄹㄹ" + mHour + ":" + mMinute + ":" + mSecond, Toast.LENGTH_SHORT).show();

        new GPS(getApplicationContext(), "main");
        new GPS(getApplicationContext(), "center");   //초단기 실황가져오기위함

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
