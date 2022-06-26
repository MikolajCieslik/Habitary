package com.example.habitary.fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitary.R;
import com.example.habitary.HabitRVAdapter;
import com.example.habitary.model.Habit;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class HabitsFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    RecyclerView rv;
    ArrayList<Habit> habitArrayList;
    HabitRVAdapter habitAdapter;
    public static final String COLLECTION = "Habits";

    public HabitsFragment() {
        db = FirebaseFirestore.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        View rootView = inflater.inflate(R.layout.fragment_habits, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rvHabits);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        habitArrayList = new ArrayList<>();
        habitAdapter = new HabitRVAdapter(getActivity(), habitArrayList);

        rv.setAdapter(habitAdapter);

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
                            if (documentReference.getPath().equals("Users/" + user.getUid())) {
                                habitArrayList.add(dc.getDocument().toObject(Habit.class).withId(id));
                                habitAdapter.notifyDataSetChanged();

                            }
                        }
                        //Collections.reverse(habitArrayList);
                    }

                });

    }
}
