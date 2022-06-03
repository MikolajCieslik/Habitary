package com.example.habitary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.habitary.CreateTaskActivity;
import com.example.habitary.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CenteredTextFragment extends Fragment {

    private static final String EXTRA_TEXT = "text";

    public static CenteredTextFragment createFor(String text) {
        CenteredTextFragment fragment = new CenteredTextFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fABAdd);
        FloatingActionButton floatingActionButton2 = view.findViewById(R.id.fABAdd2);
        FloatingActionButton floatingActionButton3 = view.findViewById(R.id.fABAdd3);
        TextView tvAddTask = view.findViewById(R.id.tvAddTask);
        TextView tvAddHabit = view.findViewById(R.id.tvAddHabit);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(floatingActionButton2.getVisibility()==View.INVISIBLE && floatingActionButton3.getVisibility()==View.INVISIBLE)
                {
                    floatingActionButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_baseline_close_24,null));
                    floatingActionButton2.setVisibility(View.VISIBLE);
                    floatingActionButton3.setVisibility(View.VISIBLE);
                    tvAddTask.setVisibility(View.VISIBLE);
                    tvAddHabit.setVisibility(View.VISIBLE);
                    floatingActionButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), CreateTaskActivity.class);
                            startActivity(intent);
                            //getSupportFragmentManager().beginTransaction().replace(R.id.container, new CreateTaskFragment()).commit();
                        }
                    });
                }
                else{
                    floatingActionButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_input_add,null));
                    floatingActionButton2.setVisibility(View.INVISIBLE);
                    floatingActionButton3.setVisibility(View.INVISIBLE);
                    tvAddTask.setVisibility(View.INVISIBLE);
                    tvAddHabit.setVisibility(View.INVISIBLE);
                }
            }
        });
        /*Bundle args = getArguments();
        final String text = args != null ? args.getString(EXTRA_TEXT) : "";
        TextView textView = view.findViewById(R.id.text);
        textView.setText(text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Toast.makeText(v.getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
