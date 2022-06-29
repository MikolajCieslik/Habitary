package com.example.habitary.fragment;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.habitary.R;


public class PomodoroFragment extends Fragment {

    TextView tvBreak;
    TextView tvTimer;
    Button btnStartPom;
    Button btnResetPom;

    long t;
    long countDownValue = 15000; // 25 minut
    long breakValue = 300000; // 5 minut
    short counterOfBreaks;
    boolean timerRunning;
    boolean isBreak = false;
    CountDownTimer cdTimer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.pomodoro_timer, container, false);
        btnStartPom = root.findViewById(R.id.btnStartPom);
        btnResetPom = root.findViewById(R.id.btnResetPom);
        tvTimer = root.findViewById(R.id.tvTimer);
        tvBreak = root.findViewById(R.id.tvBreak);
        tvBreak.setVisibility(View.INVISIBLE);
        t = countDownValue;

        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnStartPom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerRunning) {
                    cdTimer.cancel();
                    timerRunning = false;
                    btnStartPom.setText("START");
                }
                else {
                    timer(t);
                }
            }
        });

        btnResetPom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerRunning){
                    t = countDownValue;
                    counterOfBreaks = 0;
                    cdTimer.cancel();
                    timer(t);
                }
            }
        });
    }

    public void timer(long tValue) {
        t = tValue;
        cdTimer = new CountDownTimer(t, 1000) {
            @Override
            public void onTick(long l) {
                t = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
            }
        }.start();
        btnStartPom.setText("PAUSE");
        timerRunning = true;
    }

    public void updateTimer() {

        int minutes = (int) t / 60000;
        int seconds = (int) t % 60000 / 1000;

        if (minutes == 0 & seconds == 0) {
            if (!isBreak) {
                isBreak = true;
                counterOfBreaks++;
                tvBreak.setVisibility(View.VISIBLE);

                if (counterOfBreaks == 4) {
                    breakValue = 1500000;
                    counterOfBreaks = 0;
                } else { breakValue = 300000; };

                t = breakValue;
                timer(t);
            }
            else {
                isBreak = false;
                tvBreak.setVisibility(View.INVISIBLE);
                t = countDownValue;
                timer(t);
            }
        }

        String timeLeft;

        timeLeft = "" + minutes + ":";
        if (seconds < 10) {
            timeLeft += 0;
        }

        timeLeft+= seconds;

        tvTimer.setText(timeLeft);
    }
}
