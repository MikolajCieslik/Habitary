package com.example.habitary;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.habitary.model.Habit;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class CreateHabitActivity extends AppCompatActivity {
    Calendar calendar = Calendar.getInstance();

    TextView tvHabitName;
    EditText etHabitName;

    TextView tvAlertDate2;
    TextView tvAlertTime2;

    TextView tvDescriptionHabit;
    EditText etDescriptionHabit;

    ToggleButton tbMonday;
    ToggleButton tbTuesday;
    ToggleButton tbWednesday;
    ToggleButton tbThursday;
    ToggleButton tbFriday;
    ToggleButton tbSaturday;
    ToggleButton tbSunday;

    ImageView tvBack2;
    TextView tvSave2;

    ArrayList<String> frequency = new ArrayList<String>();

    int minute = calendar.get(Calendar.MINUTE);
    int hour = calendar.get(Calendar.HOUR_OF_DAY);

    int alertHour2;
    int alertMinute2;
    TimeZone timeZone = calendar.getTimeZone();


    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);
        db = FirebaseFirestore.getInstance();
        tvBack2 = findViewById(R.id.tvBack2);
        tvSave2 = findViewById(R.id.tvSave2);

        tvHabitName = findViewById(R.id.tvHabitName);
        etHabitName = findViewById(R.id.etHabitName);

        tvAlertDate2 = findViewById(R.id.tvAlertDate2);
        tvAlertTime2 = findViewById(R.id.tvAlertTime2);

        tvDescriptionHabit = findViewById(R.id.tvDescriptionHabit);
        etDescriptionHabit = findViewById(R.id.etDescriptionHabit);

        tbMonday = findViewById(R.id.tbMonday);
        tbTuesday = findViewById(R.id.tbTuesday);
        tbWednesday = findViewById(R.id.tbWednesday);
        tbThursday = findViewById(R.id.tbThursday);
        tbFriday = findViewById(R.id.tbFriday);
        tbSaturday = findViewById(R.id.tbSaturday);
        tbSunday = findViewById(R.id.tbSunday);

        alertHour2 = hour;
        alertMinute2 = minute+30;
        tvAlertTime2.setText(String.format( "%02d",alertHour2)+":"+String.format("%02d",alertMinute2));

        tvAlertTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog alertTimeDialog = new TimePickerDialog(CreateHabitActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        alertHour2 = hour;
                        alertMinute2 = minute;
                        tvAlertTime2.setText(String.format( "%02d",hour)+":"+String.format("%02d",minute));
                    }
                }, hour, minute, true);
                alertTimeDialog.show();
            }
        });
        tvBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        tbMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbMonday.isChecked()) { if (!frequency.contains("Monday")){ frequency.add("Monday"); }}
                else { if (frequency.contains("Monday")) { frequency.remove("Monday"); }}
            }
        });

        tbTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbTuesday.isChecked()) { if (!frequency.contains("Tuesday")){ frequency.add("Tuesday"); }}
                else { if (frequency.contains("Tuesday")) { frequency.remove("Tuesday"); }}
            }
        });

        tbWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbWednesday.isChecked()) {if (!frequency.contains("Wednesday")){ frequency.add("Wednesday"); }}
                else { if (frequency.contains("Wednesday")) { frequency.remove("Wednesday"); }}
            }
        });
        tbThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbThursday.isChecked()) { if (!frequency.contains("Thursday")){ frequency.add("Thursday"); }}
                else { if (frequency.contains("Thursday")) { frequency.remove("Thursday"); }}
            }
        });
        tbFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbFriday.isChecked()) { if (!frequency.contains("Friday")){ frequency.add("Friday"); }}
                else { if (frequency.contains("Friday")) { frequency.remove("Friday"); }}
            }
        });
        tbSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbSaturday.isChecked()) { if (!frequency.contains("Saturday")){ frequency.add("Saturday"); }}
                else { if (frequency.contains("Saturday")) { frequency.remove("Saturday"); }}
            }
        });
        tbSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbSunday.isChecked()) { if (!frequency.contains("Sunday")){ frequency.add("Sunday"); }}
                else { if (frequency.contains("Sunday")) { frequency.remove("Sunday"); }}
            }
        });

        tvSave2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Poprawić wprowadzanie daty
                String aDate = String.format("%02d", alertHour2) + ":" + String.format("%02d", alertMinute2)+"Z"+timeZone;
                DateFormat format = new SimpleDateFormat("HH:mm'Z'");
                try {
                    Date date = (Date)format.parse(aDate);
                    Timestamp alertHour2 = new Timestamp(date);
                    Habit habit = new Habit(String.valueOf(etHabitName.getText()), String.valueOf(etDescriptionHabit.getText()), frequency, alertHour2,"userLefik");
                    db.collection("Habits").document().set(habit);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.d("blond", "Nie wyslalo sie");
                }
            }
        });
    }
}
