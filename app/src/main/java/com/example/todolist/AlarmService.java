package com.example.todolist;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.todolist.util.DateWork;

import org.litepal.LitePal;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("NewApi")
public class AlarmService extends Service {
    static Timer timer=null;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId){
        DateWork dateWork1= LitePal.findLast(DateWork.class);
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.MONTH,dateWork1.getMonth()-1);
        calendar.set(Calendar.YEAR,dateWork1.getYear());
        calendar.set(Calendar.DAY_OF_MONTH,dateWork1.getDay());
        calendar.set(Calendar.HOUR_OF_DAY,dateWork1.getHour());
        calendar.set(Calendar.MINUTE,dateWork1.getMin());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        long V1=calendar.getTimeInMillis();
        long value2 = System.currentTimeMillis();
        long cTime=V1-value2;
        if (null == timer) {
            timer = new Timer();
        }
        Log.e("===========",String.valueOf(V1/1000/60)+"===================================================================");

        Log.e("===========",String.valueOf(value2/1000/60)+"===================================================================");

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String id = "channel_001";
                String name = "name";
                NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
                NotificationManager mn = (NotificationManager) AlarmService.this
                        .getSystemService(NOTIFICATION_SERVICE);
                mn.createNotificationChannel(mChannel);
                Notification.Builder builder = new Notification.Builder(
                        AlarmService.this);
                Intent notificationIntent = new Intent(AlarmService.this,
                        MainActivity.class);// 点击跳转位置
                PendingIntent contentIntent = PendingIntent.getActivity(
                        AlarmService.this, 0, notificationIntent, 0);
                builder.setContentIntent(contentIntent);
                builder.setSmallIcon(R.drawable.xingqiu);// 设置通知图标
                builder.setTicker("有任务截止");
                builder.setContentText(" "+dateWork1.getContent()+" ");
                builder.setAutoCancel(true);
                builder.setChannelId(id);
                builder.setVibrate(new long[] { 0, 2000, 1000, 2000 });
                builder.setDefaults(Notification.DEFAULT_ALL);
                Notification notification = builder.build();
                notification.flags = notification.FLAG_INSISTENT;
                mn.notify((int) System.currentTimeMillis(), notification);

            }
        },cTime);
        return super.onStartCommand(intent, flags, startId);
    }
}
