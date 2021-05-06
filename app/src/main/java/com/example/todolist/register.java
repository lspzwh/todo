package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.List;

import com.example.todolist.util.Person;

public class register extends AppCompatActivity {
    private EditText name;
    private EditText key;
    private EditText confirm;
    private ImageView go;
    private List<Person> people;
    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().equals("")){
                    if(!key.getText().toString().equals("") && !confirm.getText().toString().equals("")){
                        if(key.getText().toString().equals(confirm.getText().toString())){
                            if(people==null){
                                person.setName(name.getText().toString());
                                person.setKey(key.getText().toString());
                                person.save();
                                Toast.makeText(register.this,"注册成功",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(register.this,Login.class);
                                startActivity(intent);
                            }else{
                                boolean flag=true;
                                for(Person person1:people){
                                    if(person1.getName().equals(name.getText().toString())){
                                        Toast.makeText(register.this,"用户名已存在",Toast.LENGTH_SHORT).show();
                                        flag=false;
                                        break;
                                    }
                                    }
                                if(flag){
                                    person.setName(name.getText().toString());
                                    person.setKey(key.getText().toString());
                                    person.save();
                                    Toast.makeText(register.this,"注册成功",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(register.this,Login.class);
                                    startActivity(intent);
                                }
                            }
                        }else{
                            Toast.makeText(register.this,"密码不一致",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(register.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(register.this,"名称不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void init(){
        name=findViewById(R.id.register_name);
        key=findViewById(R.id.register_key);
        confirm=findViewById(R.id.confirm);
        go=findViewById(R.id.register_go);
        people=LitePal.findAll(Person.class);
        person=new Person();

    }
}