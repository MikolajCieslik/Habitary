package com.example.habitary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.habitary.model.Task;
import com.example.habitary.model.taskCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    Spinner spCategory;
    CheckBox cbAddCategory;
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
    final ArrayList<String> spinnerList = new ArrayList<>();

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    CollectionReference categoryReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
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
        spCategory = findViewById(R.id.spCategory);
        cbAddCategory = findViewById(R.id.cbAddCategory);
        startYear = year;
        startMonth = month;
        startDay = day;
        startHour = hour+1;
        startMinute = minute;
        alertYear = year;
        alertMonth = month;
        alertDay = day;
        alertHour = hour;
        alertMinute = minute+30;
        if(alertMinute>=60){
            alertMinute-=60;
        }
        endYear = year;
        endMonth = month;
        endDay = day;
        endHour = hour+2;
        if(endHour>=24){
            endHour-=24;
            endDay++;
            //todo:dodac sprawdzenie czy dzien nie wiekszy niz 31 lub 30 w zaleznosci od miesiaca
        }
        endMinute = minute;
        spinnerList.add("Work");
        spinnerList.add("Home");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerList);
        spCategory.setAdapter(adapter);
        categoryReference = db.collection("taskCategory");
        categoryReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        DocumentReference documentReference = (DocumentReference) document.get("userID");
                        if(documentReference.getPath().equals("Users/"+user.getUid())){
                            spinnerList.add(document.getString("category"));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        tvStartDay.setText(String.valueOf(startDay)+"/"+String.valueOf(startMonth+1)+"/"+String.valueOf(startYear));
        tvStartTime.setText(String.format( "%02d",startHour)+":"+String.format("%02d",startMinute));
        tvAlertDay.setText(String.valueOf(alertDay)+"/"+String.valueOf(alertMonth+1)+"/"+String.valueOf(alertYear));
        tvAlertTime.setText(String.format( "%02d",alertHour)+":"+String.format("%02d",alertMinute));
        tvEndDay.setText(String.valueOf(endDay)+"/"+String.valueOf(endMonth+1)+"/"+String.valueOf(endYear));
        tvEndTime.setText(String.format( "%02d",endHour)+":"+String.format("%02d",endMinute));
        tvStartDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        cbAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbAddCategory.isChecked()){

                    spCategory.setVisibility(View.INVISIBLE);
                    etCategory.setVisibility(View.VISIBLE);
                }
                else{
                    spCategory.setVisibility(View.VISIBLE);
                    etCategory.setVisibility(View.INVISIBLE);
                }
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sDate = startDay+"-"+(startMonth+1)+"-"+startYear+"T"+String.format( "%02d",startHour)+":"+String.format( "%02d",startMinute)+"Z"+timeZone;
                String aDate = alertDay+"-"+(alertMonth+1)+"-"+alertYear+"T"+String.format( "%02d",alertHour)+":"+String.format( "%02d",alertMinute)+"Z"+timeZone;
                String eDate = endDay+"-"+(endMonth+1)+"-"+endYear+"T"+String.format( "%02d",endHour)+":"+String.format( "%02d",endMinute)+"Z"+timeZone;
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm'Z'");
                try {
                    Date date = (Date)format.parse(sDate);
                    Timestamp startDate = new Timestamp(date);
                    date = (Date)format.parse(aDate);
                    Timestamp alertDate = new Timestamp(date);
                    date = (Date)format.parse(eDate);
                    Timestamp endDate = new Timestamp(date);
                    calendar = Calendar.getInstance();
                    DocumentReference documentReference = db.document("Users/"+user.getUid());
                    Date currentTime = (Date)format.parse(calendar.get(Calendar.DAY_OF_MONTH)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.YEAR)+"T"+
                            String.format("%02d",calendar.get(Calendar.HOUR_OF_DAY))+":"+String.format( "%02d",calendar.get(Calendar.MINUTE))+"Z"+timeZone);
                    if(startDate.toDate().after(alertDate.toDate()) && startDate.toDate().before(endDate.toDate()) && startDate.toDate().after(currentTime) && endDate.toDate().after(currentTime) && alertDate.toDate().after(currentTime)){
                        if(cbAddCategory.isChecked()){
                            if(etName.getText().toString().equals("")!=true && etCategory.getText().toString().equals("")!=true && etDescription.getText().toString().equals("")!=true){
                                Task task = new Task(String.valueOf(etName.getText()), String.valueOf(etCategory.getText()), String.valueOf(etDescription.getText()), documentReference, startDate, alertDate, endDate);
                                db.collection("Tasks").document().set(task);
                                taskCategory taskCategory = new taskCategory(String.valueOf(etCategory.getText()), documentReference);
                                db.collection("taskCategory").document().set(taskCategory);
                                Intent intent = new Intent(view.getContext(),MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast toast = new Toast(CreateTaskActivity.this);
                                toast.setText("Fill the name, category and description");
                                toast.show();
                            }
                        }
                        else{
                            if(etName.getText().toString().equals("")!=true && etDescription.getText().toString().equals("")!=true){
                                Task task = new Task(String.valueOf(etName.getText()), String.valueOf(spCategory.getSelectedItem()), String.valueOf(etDescription.getText()), documentReference, startDate, alertDate, endDate);
                                db.collection("Tasks").document().set(task);
                                Intent intent = new Intent(view.getContext(),MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast toast = new Toast(CreateTaskActivity.this);
                                toast.setText("Fill the name and description");
                                toast.show();
                            }
                        }
                    }
                    else{
                        Toast toast = new Toast(CreateTaskActivity.this);
                        toast.setText("Wrong dates");
                        toast.show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}