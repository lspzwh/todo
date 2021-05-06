package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todolist.util.DateWork;
import com.example.todolist.util.Person;

import org.litepal.LitePal;

import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    private Button time_btn;
    private Button date_btn;
    private TextView time;
    private TextView date;
    private Button sure;
    private EditText content;
    private DateWork dateWork;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private  Intent intent;
    private static Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        init();

    }
    public void init(){
        time_btn=findViewById(R.id.time_btn);
        date_btn=findViewById(R.id.datebtn);
        time=findViewById(R.id.time);
        date=findViewById(R.id.date);
        sure=findViewById(R.id.sure);
        content=findViewById(R.id.content);
        dateWork=new DateWork();
        calendar=Calendar.getInstance();
        time_btn.setOnClickListener(this);
        date_btn.setOnClickListener(this);
        sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.time_btn:
                showtime();
                break;
            case R.id.datebtn:
                showdate();
                break;
            case R.id.sure:
                input();
                break;
        }

    }
    public void showtime(){
        timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String mtime = hourOfDay + "时" + minute + "分";
                dateWork.setHour(hourOfDay);
                dateWork.setMin(minute);
                time.setText(mtime);
            }
            },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
        timePickerDialog.show();
    }
    public void showdate(){
        datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String mdate = year + "年" + (month + 1) + "月" + dayOfMonth + "日";
                dateWork.setDay(dayOfMonth);
                dateWork.setMonth(month+1);
                dateWork.setYear(year);
                date.setText(mdate);
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    public void input(){
        if(!date.getText().toString().equals("")&& !time.getText().toString().equals("")){
            dateWork.setContent(content.getText().toString());
            dateWork.setFinish(0);
//            dateWork.save();
            if(dateWork.save()){
                Intent intent1=new Intent(MainActivity2.this,AlarmService.class);
                startService(intent1);

                Toast.makeText(this,"创建成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent);
            }

        }else{
            Toast.makeText(this,"时间日期不能为空",Toast.LENGTH_SHORT).show();
        }
    }

}
