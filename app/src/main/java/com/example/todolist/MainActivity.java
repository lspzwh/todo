package com.example.todolist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.todolist.Fragment.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.example.todolist.util.DateWork;
import com.example.todolist.util.Person;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Person person;
    private DateWork dateWork;

    private FloatingActionButton plus;
    private ShapeableImageView pic;
    private SharedPreferences sp;
    private ArrayList<Fragment> fragments;
    private FragmentManager fm;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        init();
        sp=getSharedPreferences("First",MODE_PRIVATE);
        person=new Person();
        dateWork=new DateWork();
        boolean isFirst = sp.getBoolean("isFirst",true);
        if(isFirst){
            if(!isIgnoringBatteryOptimizations())
            requestIgnoreBatteryOptimizations();
            person.setName("忍者");
            person.setKey("123456");
//            person.save();
            dateWork.setYear(2021);
            dateWork.setMonth(5);
            dateWork.setDay(6);
            dateWork.setHour(23);
            dateWork.setMin(0);
            dateWork.setFinish(0);
            dateWork.setContent("中忍考核");
//            dateWork.save();
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isFirst", false);
            editor.apply();
            if(person.save()&&dateWork.save()){
                Toast.makeText(MainActivity.this,"欢迎",Toast.LENGTH_SHORT).show();
            }
        }
        init();

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                boolean isFirst = sp.getBoolean("isFirst",true);
//                if(isFirst){
//                    person.setName("忍者");
//                    person.setKey("123456");
//                    person.save();
//                    SharedPreferences.Editor editor = sp.edit();
//                    editor.putBoolean("isFirst", false);
//                    editor.apply();
//                    if(person.save()){
//                        Toast.makeText(MainActivity.this,"chenggong",Toast.LENGTH_SHORT).show();
//                    }
//                }
                Intent intent=new Intent(MainActivity.this, Set.class);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.all:
                    fm.beginTransaction().hide(fragments.get(1)).show(fragments.get(0)).commit();
                    return true;
                case R.id.day:
                    fm.beginTransaction().hide(fragments.get(0)).show(fragments.get(1)).commit();
                    return true;
            }
            return false;
        });
    }
    public void init(){
        pic=findViewById(R.id.pic);
        bottomNavigationView=findViewById(R.id.Navi);
//        sp=getSharedPreferences("First",MODE_PRIVATE);
//        person=new Person();
//        dateWork=new DateWork();
        fragments=new ArrayList<>();
        fragments.add(AllList.getInstance());
        fragments.add(DayFragment.getInstance());
        fm=getSupportFragmentManager();
        fm.beginTransaction().add(R.id.frame,fragments.get(0),"list")
                .add(R.id.frame,fragments.get(1),"Day")
                .commit();
        fm.beginTransaction().hide(fragments.get(1)).commit();
        plus=findViewById(R.id.fab);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isIgnoringBatteryOptimizations() {
        boolean isIgnoring = false;
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(getPackageName());
        }
        return isIgnoring;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestIgnoreBatteryOptimizations() {
        try {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}