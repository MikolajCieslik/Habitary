package com.example.habitary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {


    Calendar calendar = Calendar.getInstance();
    TextView tvStartDay;
    TextView tvStartTime;
    TextView tvEndDay;
    TextView tvEndTime;
    TextView tvSave;
    ImageView tvBack;
    EditText etSelectDate;
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int minute = calendar.get(Calendar.MINUTE);
    int hour = calendar.get(Calendar.HOUR_OF_DAY);

    String name;
    String description;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        tvStartDay = findViewById(R.id.tvStartDay);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndDay = findViewById(R.id.tvEndDay);
        tvEndTime = findViewById(R.id.tvEndTime);
        tvSave = findViewById(R.id.tvSave);
        tvBack = findViewById(R.id.tvBack);
        etSelectDate = findViewById(R.id.etName);
        tvStartDay.setText(String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
        tvStartTime.setText(String.format( "%02d",hour)+":"+String.format("%02d",minute));
        tvEndDay.setText(String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
        tvEndTime.setText(String.format( "%02d",hour+1)+":"+String.format("%02d",minute));
        tvStartDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*OSTATNIA DZIALAJACA WERSJA

                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.show(getChildFragmentManager(), "datePicker");
                tvStartTime.setText(String.valueOf(bundle.getString("selectedDate")));*/

                DatePickerDialog dayDialog = new DatePickerDialog(CreateTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        tvStartDay.setText(String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
                    }
                },year, month, day);
                dayDialog.show();
            }
        });
        tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timeDialog = new TimePickerDialog(CreateTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        tvStartTime.setText(String.format( "%02d",hour)+":"+String.format("%02d",minute));
                    }
                },hour, minute,true);
                timeDialog.show();
            }
        });
        tvEndDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog endDayDialog = new DatePickerDialog(CreateTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        //TODO: Dodac sprawdzanie czy koniec zadania jest pozniej niz jego poczatek, w else prawdopodobnie Toast
                        tvEndDay.setText(String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
                    }
                },year, month, day);
                endDayDialog.show();
            }
        });
        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog endTimeDialog = new TimePickerDialog(CreateTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        tvEndTime.setText(String.format( "%02d",hour)+":"+String.format("%02d",minute));
                    }
                },hour, minute,true);
                endTimeDialog.show();
            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = new Toast(getApplicationContext());
                toast.setText("Task added");
                toast.show();
            }
        });
    }
}