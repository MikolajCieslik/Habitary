package com.example.habitary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitary.model.Task;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskRVAdapter extends RecyclerView.Adapter<TaskRVAdapter.ViewTaskHolder> {

    Context context;
    ArrayList<Task> taskArrayList;

    public TaskRVAdapter(Context context, ArrayList<Task> taskArrayList) {
        this.context = context;
        this.taskArrayList = taskArrayList;
    }

    @NonNull
    @Override
    public TaskRVAdapter.ViewTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.tasks_rv,parent,false);

        return new ViewTaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskRVAdapter.ViewTaskHolder holder, int position) {

        Task task = taskArrayList.get(position);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        holder.category.setText(task.getCategory());
        holder.taskName.setText(task.getName());
        holder.startDate.setText(timeFormat.format(task.getStartDate().toDate().getTime()));
        holder.endDate.setText(timeFormat.format(task.getEndDate().toDate().getTime()));
        holder.description.setText(task.getDescription());
    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }

    public static class ViewTaskHolder extends RecyclerView.ViewHolder{

        TextView category;
        TextView taskName;
        TextView startDate;
        TextView endDate;
        TextView description;

        public ViewTaskHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            taskName = itemView.findViewById(R.id.taskName);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            description = itemView.findViewById(R.id.description);
        }
    }
}
