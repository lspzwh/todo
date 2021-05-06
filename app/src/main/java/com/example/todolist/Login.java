package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.List;

import com.example.todolist.util.Person;

public class Login extends AppCompatActivity {
    private EditText name;
    private EditText key;
    private ImageView imageView;
    private TextView register;
    private Person person;
    private List<Person> people;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag=false;
                if(people.size()>0){
                    for(Person person1:people){
                        if(person1.getName().equals(name.getText().toString())&&person1.getKey().equals(key.getText().toString())){
                            Toast.makeText(Login.this,"登录成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Login.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }
                    if(!flag){
                        Toast.makeText(Login.this,"账号密码错误",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,register.class);
                startActivity(intent);
            }
        });
    }
    public void init(){
        name=findViewById(R.id.name);
        key=findViewById(R.id.key);
        imageView=findViewById(R.id.go);
        register=findViewById(R.id.register);
        textView=findViewById(R.id.cname);
        person=new Person();
        people=LitePal.findAll(Person.class);

    }
}