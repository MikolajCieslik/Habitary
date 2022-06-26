package com.example.habitary;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitary.model.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskRVAdapter extends RecyclerView.Adapter<TaskRVAdapter.ViewTaskHolder> {

    Context context;
    ArrayList<Task> taskArrayList;
    FirebaseFirestore firestore;

    public TaskRVAdapter(Context context, ArrayList<Task> taskArrayList) {
        this.context = context;
        this.taskArrayList = taskArrayList;
    }

    @NonNull
    @Override
    public TaskRVAdapter.ViewTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.tasks_rv,parent,false);
        firestore = FirebaseFirestore.getInstance();

        return new ViewTaskHolder(view);
    }

    public void deleteTask(int position)
    {
        Task task = taskArrayList.get(position);
        Log.d(TAG, "!!!!" + task.TasksId);
        //firestore.collection("Tasks").document(task.TaskId).delete();
        //taskArrayList.remove(position);
        //notifyItemChanged(position);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskRVAdapter.ViewTaskHolder holder, int position) {

        Task task = taskArrayList.get(position);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Log.d(TAG, "!!!!" + task.TasksId);
        holder.category.setText(task.getCategory());
        holder.taskName.setText(task.getName());
        holder.startDate.setText(timeFormat.format(task.getStartDate().toDate().getTime()));
        holder.endDate.setText(timeFormat.format(task.getEndDate().toDate().getTime()));
        holder.description.setText(task.getDescription());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "!!!!" + task.TasksId);
            }
        });

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
        Button deleteButton;

        public ViewTaskHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            taskName = itemView.findViewById(R.id.taskName);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            description = itemView.findViewById(R.id.description);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
