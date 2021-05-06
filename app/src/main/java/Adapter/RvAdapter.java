package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.util.DateWork;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvViewHolder>{
    private List<DateWork> list;
    private Context context;
    public RvAdapter(Context context, List<DateWork> list){
        this.list=list;
        this.context=context;
    }
    public interface OnItemClick {
        void onAllItemClick(int position);
        void noItemClick(int p);
    }
    private OnItemClick onItemClick;
    public void setOnitemClickLintener(OnItemClick onItemClick) {

        this.onItemClick = onItemClick;
    }


    @NonNull
    @Override
    public RvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RvViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RvViewHolder holder, int position) {
        DateWork dateWork=list.get(position);
        holder.content.setText(dateWork.getContent());
        String ct=dateWork.getYear()+"年"+dateWork.getYear()+"月"+dateWork.getDay()+"天"+dateWork.getHour()+":"+dateWork.getMin();
        holder.time.setText(ct);
        if(dateWork.getFinish()==0){
            holder.finish.setChecked(false);
        }else{
            holder.finish.setChecked(true);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onAllItemClick(position+1);
            }
        });
//        holder.finish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position=holder.getLayoutPosition()+1;
//                DateWork dateWork1=new DateWork();
//                dateWork1=LitePal.find(DateWork.class,position);
//                dateWork1.setFinish((dateWork1.getFinish()+1)%2);
//                dateWork1.update(position);
//                onItemClick.noItemClick(position);
//            }
//        });
        holder.finish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position=holder.getLayoutPosition()+1;
//                DateWork dateWork1=new DateWork();
//                dateWork1=LitePal.find(DateWork.class,position);
//                if(isChecked){
//                    dateWork1.setFinish(0);
//                }else {
//                    dateWork1.setFinish(1);
//                }
//                dateWork1.update(position);
                onItemClick.noItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RvViewHolder extends RecyclerView.ViewHolder {
        public CheckBox finish, delete;
        public TextView content, time;
        public RvViewHolder(@NonNull View itemView) {
            super(itemView);
            finish=itemView.findViewById(R.id.checkFinish);
            delete=itemView.findViewById(R.id.delete_item);
            content=itemView.findViewById(R.id.item_content);
            time=itemView.findViewById(R.id.item_time);
        }
    }
}
