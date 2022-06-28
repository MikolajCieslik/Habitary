package com.example.habitary;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitary.authentication.ManageUserActivity;
import com.example.habitary.fragment.HabitsFragment;
import com.example.habitary.model.Habit;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HabitRVAdapter extends RecyclerView.Adapter<HabitRVAdapter.ViewTaskHolder> {

    Context context;
    ArrayList<Habit> habitArrayList;
    FirebaseFirestore firestore;

    public HabitRVAdapter(Context context, ArrayList<Habit> habitArrayList) {
        this.context = context;
        this.habitArrayList = habitArrayList;
    }

    @NonNull
    @Override
    public HabitRVAdapter.ViewTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.each_habit,parent,false);
        firestore = FirebaseFirestore.getInstance();



        return new ViewTaskHolder(view);
    }
    public Context getContext(){
        return context;
    }

    public void deleteHabit(int position)
    {
        Habit habit = habitArrayList.get(position);
        firestore.collection("Habits").document(habit.HabitsId).delete();
        habitArrayList.remove(position);
        notifyItemRemoved(position);
    }

    public void refHabit(){
        Habit habit = new Habit();
        firestore.collection("Habits").document(habit.HabitsId).update("finishFlag", false);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitRVAdapter.ViewTaskHolder holder, int position) {

        Habit habit = habitArrayList.get(position);

        holder.habitName.setText(habit.getName());
        holder.description.setText(habit.getDescription());
        holder.habitName.setChecked(habit.getFinishFlag());

        int position_now = position;
        int counter = habit.getStreakCounter();

        holder.habitName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    firestore.collection("Habits").document(habit.HabitsId).update("finishFlag", true);
                    firestore.collection("Habits").document(habit.HabitsId).update("streakCounter", counter+1);
                    Log.d(TAG, "true" );
                }else {
                    firestore.collection("Habits").document(habit.HabitsId).update("finishFlag", false);
                    Log.d(TAG, "false" );
                }
            }
        });
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateHabitActivity.class);
                intent.putExtra("id", habit.HabitsId);
                intent.putExtra("name", habit.getName());
                intent.putExtra("description", habit.getDescription());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return habitArrayList.size();
    }

    public static class ViewTaskHolder extends RecyclerView.ViewHolder{

        CheckBox habitName;
        TextView description;
        ImageButton imageButton;

        public ViewTaskHolder(@NonNull View itemView) {
            super(itemView);

            habitName = itemView.findViewById(R.id.habitName);
            description = itemView.findViewById(R.id.description);
            imageButton = itemView.findViewById(R.id.editButton);
        }
    }
}
