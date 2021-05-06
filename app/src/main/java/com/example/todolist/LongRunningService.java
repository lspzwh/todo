package com.example.todolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.todolist.util.DateWork;

import org.litepal.LitePal;

import java.util.Calendar;

public class LongRunningService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        DateWork dateWork1= LitePal.findLast(DateWork.class);
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,dateWork1.getYear());
        calendar.set(Calendar.DAY_OF_MONTH,dateWork1.getDay());
        calendar.set(Calendar.HOUR_OF_DAY,dateWork1.getHour());
        calendar.set(Calendar.MONTH,dateWork1.getMonth());
        calendar.set(Calendar.MINUTE,dateWork1.getMin());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        long cTime=calendar.getTimeInMillis();
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, cTime, pi);
        return super.onStartCommand(intent, flags, startId);

    }
    public void onDestroy() {
        super.onDestroy();
        //在Service结束后关闭AlarmManager
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.cancel(pi);

    }
}
