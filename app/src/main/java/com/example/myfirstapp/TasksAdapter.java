package com.example.myfirstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyTasks> tasks;


    public TasksAdapter(Context context, ArrayList<MyTasks> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(tasks.get(position).getTitleTask());
        holder.title.setEnabled(tasks.get(position).getCheck());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View item){
            super(item);

            title = (TextView) item.findViewById(R.id.titleTask);
            checkBox = (CheckBox) item.findViewById(R.id.checkBoxDoes);
        }
    }
}
