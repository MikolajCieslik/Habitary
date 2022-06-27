package com.example.habitary.fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habitary.HelperTask;
import com.example.habitary.MainActivity;
import com.example.habitary.R;
import com.example.habitary.TaskRVAdapter;
import com.example.habitary.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class MyTasksFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    RecyclerView rv;
    ArrayList<Task> taskArrayList;
    TaskRVAdapter taskAdapter;
    public static final String COLLECTION = "Tasks";

    public MyTasksFragment() {
        db = FirebaseFirestore.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        View rootView = inflater.inflate(R.layout.fragment_my_tasks, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rvTasks);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        taskArrayList = new ArrayList<>();
        taskAdapter = new TaskRVAdapter(getActivity(), taskArrayList);


        rv.setAdapter(taskAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new HelperTask(taskAdapter));
        itemTouchHelper.attachToRecyclerView(rv);
        EventChangeListener();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void EventChangeListener() {

        final FirebaseUser user = mAuth.getCurrentUser();

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
                            if(documentReference.getPath().equals("Users/"+user.getUid())){
                                taskArrayList.add(dc.getDocument().toObject(Task.class).withId(id));
                            }
                        }

                        taskAdapter.notifyDataSetChanged();

                    }

                });
    }
}

