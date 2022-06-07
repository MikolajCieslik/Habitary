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

import com.example.habitary.model.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CreateTaskActivity extends AppCompatActivity {


    Calendar calendar = Calendar.getInstance();
    TextView tvStartDay;
    TextView tvStartTime;
    TextView tvEndDay;
    TextView tvEndTime;
    TextView tvAlertDay;
    TextView tvAlertTime;
    TextView tvSave;
    ImageView tvBack;
    EditText etName;
    EditText etCategory;
    EditText etDescription;
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int minute = calendar.get(Calendar.MINUTE);
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    TimeZone timeZone = calendar.getTimeZone();
    int startHour;
    int startMinute;
    int startYear;
    int startMonth;
    int startDay;
    int alertHour;
    int alertMinute;
    int alertYear;
    int alertMonth;
    int alertDay;
    int endHour;
    int endMinute;
    int endYear;
    int endMonth;
    int endDay;



    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        db = FirebaseFirestore.getInstance();
        tvStartDay = findViewById(R.id.tvStartDay);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndDay = findViewById(R.id.tvEndDay);
        tvEndTime = findViewById(R.id.tvEndTime);
        tvAlertDay = findViewById(R.id.tvAlertDay);
        tvAlertTime = findViewById(R.id.tvAlertTime);
        tvSave = findViewById(R.id.tvSave);
        tvBack = findViewById(R.id.tvBack);
        etName = findViewById(R.id.etName);
        etCategory = findViewById(R.id.etCategory);
        etDescription = findViewById(R.id.etDescription);
        startYear = year;
        startMonth = month;
        startDay = day;
        startHour = hour;
        startMinute = minute;
        alertYear = year;
        alertMonth = month;
        alertDay = day;
        alertHour = hour;
        alertMinute = minute+30;
        endYear = year;
        endMonth = month;
        endDay = day;
        endHour = hour+1;
        endMinute = minute;
        tvStartDay.setText(String.valueOf(startDay)+"/"+String.valueOf(startMonth+1)+"/"+String.valueOf(startYear));
        tvStartTime.setText(String.format( "%02d",startHour)+":"+String.format("%02d",startMinute));
        tvAlertDay.setText(String.valueOf(alertDay)+"/"+String.valueOf(alertMonth+1)+"/"+String.valueOf(alertYear));
        tvAlertTime.setText(String.format( "%02d",alertHour)+":"+String.format("%02d",alertMinute));
        tvEndDay.setText(String.valueOf(endDay)+"/"+String.valueOf(endMonth+1)+"/"+String.valueOf(endYear));
        tvEndTime.setText(String.format( "%02d",endHour)+":"+String.format("%02d",endMinute));
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
                        startYear = year;
                        startMonth = month;
                        startDay = day;
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
                        startHour = hour;
                        startMinute = minute;
                    }
                },hour, minute,true);
                timeDialog.show();
            }
        });
        tvAlertDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog alertDayDialog = new DatePickerDialog(CreateTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        //TODO: Dodac sprawdzanie czy alert jest przez koncem i po starcie taska
                        alertYear = year;
                        alertMonth = month;
                        alertDay = day;
                        tvAlertDay.setText(String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
                    }
                },year, month, day);
                alertDayDialog.show();
            }
        });
        tvAlertTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog alertTimeDialog = new TimePickerDialog(CreateTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        alertHour = hour;
                        alertMinute = minute;
                        tvAlertTime.setText(String.format( "%02d",hour)+":"+String.format("%02d",minute));
                    }
                }, hour, minute, true);
                alertTimeDialog.show();
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
                        endYear = year;
                        endMonth = month;
                        endDay = day;
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
                        endHour = hour;
                        endMinute = minute;
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
                String sDate = startDay+"-"+startMonth+"-"+startYear+"T"+String.format( "%02d",startHour)+":"+String.format( "%02d",startMinute)+"Z"+timeZone;
                String aDate = alertDay+"-"+alertMonth+"-"+alertYear+"T"+String.format( "%02d",alertHour)+":"+String.format( "%02d",alertMinute)+"Z"+timeZone;
                String eDate = endDay+"-"+endMonth+"-"+endYear+"T"+String.format( "%02d",endHour)+":"+String.format( "%02d",endMinute)+"Z"+timeZone;
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm'Z'");
                try {
                    Date date = (Date)format.parse(sDate);
                    Timestamp startDate = new Timestamp(date);
                    date = (Date)format.parse(aDate);
                    Timestamp alertDate = new Timestamp(date);
                    date = (Date)format.parse(eDate);
                    Timestamp endDate = new Timestamp(date);
                    Task task = new Task(String.valueOf(etName.getText()), String.valueOf(etCategory.getText()), String.valueOf(etDescription.getText()), "adwedawdwadawda", startDate, alertDate, endDate);
                    db.collection("Tasks").document().set(task);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}