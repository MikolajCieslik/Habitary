package com.example.habitary.fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.habitary.HabitRVAdapter;
import com.example.habitary.Helper;
import com.example.habitary.R;
import com.example.habitary.TaskRVAdapter;
import com.example.habitary.model.Habit;
import com.example.habitary.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class MainViewFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseFirestore db2;
    FirebaseAuth mAuth;

    RecyclerView rv;
    ArrayList<Task> taskArrayList;
    TaskRVAdapter taskAdapter;
    public static final String COLLECTION = "Tasks";

    RecyclerView rv2;
    ArrayList<Habit> habitArrayList;
    HabitRVAdapter habitAdapter;
    public static final String COLLECTION_2 = "Habits";
    public static final String ttask = "Today's tasks";
    public static final String thabit = "Today's habits";
    int counter;
    int counterh;


    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat prettyFormat = new SimpleDateFormat("EEEE, d'th' MMMM yyyy"); //tylko 'th', nie ma obslugi first/second/third
    SimpleDateFormat weekdayExtractionFormat = new SimpleDateFormat("EEEE");

    String currentDate;
    String prettyDate;
    String weekday;
    TextView itemCurrentDate;
    TextView todaytask;
    TextView todayhabit;

    public MainViewFragment() {
        db = FirebaseFirestore.getInstance();
        currentDate = dateFormat.format(Calendar.getInstance().getTime());
        prettyDate = prettyFormat.format(Calendar.getInstance().getTime());
        weekday = weekdayExtractionFormat.format(Calendar.getInstance().getTime());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();
        db2 = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        View rootView = inflater.inflate(R.layout.fragment_main_view, container, false);
        itemCurrentDate = rootView.findViewById(R.id.timeView);

        itemCurrentDate.setText(prettyDate);

        rv = (RecyclerView) rootView.findViewById(R.id.rvTaski);
        //rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        taskArrayList = new ArrayList<>();
        taskAdapter = new TaskRVAdapter(getActivity(), taskArrayList);

        rv2 = (RecyclerView) rootView.findViewById(R.id.rvHabitsy);
        //rv2.setHasFixedSize(true);
        rv2.setLayoutManager(new LinearLayoutManager(getActivity()));
        habitArrayList = new ArrayList<>();
        habitAdapter = new HabitRVAdapter(getActivity(), habitArrayList);

        todaytask = rootView.findViewById(R.id.todayTasks);
        todayhabit = rootView.findViewById(R.id.todayHabits);

        todaytask.setText(ttask);
        todayhabit.setText(thabit);

        rv2.setAdapter(habitAdapter);

        rv.setAdapter(taskAdapter);

        EventChangeListener();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void EventChangeListener() {

        final FirebaseUser user = mAuth.getCurrentUser();
        counter = 0;

        db.collection(COLLECTION)
                .addSnapshotListener((value, error) -> {

                    if (error != null) {
                        Log.e("Firestore fail", error.getMessage());
                        return;
                    }
                    assert value != null;
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            DocumentReference documentReference = (DocumentReference) dc.getDocument().get("idUser");
                            String id = dc.getDocument().getId();
                            String startDate = dateFormat.format(dc.getDocument().getTimestamp("startDate").toDate());
                            if(documentReference.getPath().equals("Users/"+user.getUid())){
                                if (currentDate.equals(startDate)) {
                                    taskArrayList.add(dc.getDocument().toObject(Task.class).withId(id));
                                    counter++;
                                }
                            }
                        }

                        taskAdapter.notifyDataSetChanged();

                    }
                    if (counter == 0)
                    {
                        todaytask.setText("");
                    }

                });

        counterh = 0;

        db2.collection(COLLECTION_2)
                .addSnapshotListener((value, error) -> {

                    if (error != null) {
                        Log.e("Firestore fail", error.getMessage());
                        return;
                    }
                    assert value != null;
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            DocumentReference documentReference = (DocumentReference) dc.getDocument().get("idUser");
                            String id = dc.getDocument().getId();

                            List<String> frq = (List<String>) dc.getDocument().get("frequency");

                            if (documentReference.getPath().equals("Users/" + user.getUid())) {
                                if (frq.contains(weekday)) {

                                    habitArrayList.add(dc.getDocument().toObject(Habit.class).withId(id));
                                    habitAdapter.notifyDataSetChanged();
                                    counterh++;
                                }
                            }
                        }
                        Collections.reverse(habitArrayList);
                    }
                    if (counterh == 0)
                    {
                        todayhabit.setText("");
                    }

                });
    }
}