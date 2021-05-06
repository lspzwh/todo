package com.example.todolist.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.util.DateWork;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import Adapter.RvAdapter;

import static android.content.Context.MODE_PRIVATE;

public class AllList extends Fragment {
    private RecyclerView recyclerView;
    private Context context;
    private RvAdapter rvAdapter;
    private List<DateWork> dateWorks;
    private PopupWindow popupWindow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View view= inflater.inflate(R.layout.all_fragment,container,false);
     context=view.getContext();
     init(view);
     LinearLayoutManager manager=new LinearLayoutManager(context);
     manager.setOrientation(LinearLayoutManager.VERTICAL);
     rvAdapter=new RvAdapter(context,dateWorks);
     recyclerView.setAdapter(rvAdapter);
     recyclerView.setLayoutManager(manager);
     rvAdapter.setOnitemClickLintener(new RvAdapter.OnItemClick() {
         @Override
         public void onAllItemClick(int position) {
             Toast.makeText(context,Integer.toString(position),Toast.LENGTH_SHORT).show();
             showWindow(position);
         }

         @Override
         public void noItemClick(int p) {
                DateWork dateWork1=new DateWork();
                dateWork1=LitePal.find(DateWork.class,p);
                if(dateWork1.getFinish()==1){
                    dateWork1.setToDefault("isFinish");
                }else{
                    dateWork1.setFinish(1);
                }
                dateWork1.update(p);
             DateWork dateWork=LitePal.find(DateWork.class,p);
             if(dateWork.getFinish()==1){
                 Toast.makeText(context,"已完成",Toast.LENGTH_LONG).show();
             }

         }
     });
    return view;

    }
    public void init(View view){
        recyclerView=view.findViewById(R.id.list_item);
        dateWorks=new ArrayList<>();
        dateWorks= LitePal.findAll(DateWork.class);

    }

    public static AllList getInstance(){
        return new AllList();
    }
    public void showWindow(int p){
        View inflate=LayoutInflater.from(getContext()).inflate(R.layout.popwindow,null);
        popupWindow = new PopupWindow(inflate, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置位置
        popupWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(inflate,p);
    }
    public void setOnPopupViewClick(View view,int p){
        RelativeLayout popWindow=view.findViewById(R.id.popup);
        Button delete=view.findViewById(R.id.btn_delete);
        Button change=view.findViewById(R.id.btn_change);
        popWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateWorks.get(p-1).delete();
                dateWorks.remove(p-1);
                rvAdapter.notifyItemRemoved(p-1);
                rvAdapter.notifyItemRangeChanged(p-1,rvAdapter.getItemCount()-p+1);
                popupWindow.dismiss();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"在做了在做了",Toast.LENGTH_LONG).show();
            }
        });

        rvAdapter.notifyDataSetChanged();
    }
}
