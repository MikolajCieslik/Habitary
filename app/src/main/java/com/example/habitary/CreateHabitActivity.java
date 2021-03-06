package com.example.habitary;

import static android.content.ContentValues.TAG;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.habitary.model.Habit;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
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

    TimeZone timeZone = calendar.getTimeZone();
    int minute = calendar.get(Calendar.MINUTE);
    int hour = calendar.get(Calendar.HOUR_OF_DAY);

    int alertHour2;
    int alertMinute2;


    FirebaseFirestore db;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

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
        alertMinute2 = minute;
        tvAlertTime2.setText(String.format( "%02d",alertHour2)+":"+String.format("%02d",alertMinute2));
        String id_get = "";
        String name;
        String description;
        Long alertDateStr;
        List <String> freq;
        boolean changed = false;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            if (bundle.getString("id") != null){
                id_get = bundle.getString("id");
                name = bundle.getString("name");
                description = bundle.getString("description");
                alertDateStr = bundle.getLong ("alertDate");
                Bundle extra = bundle.getBundle("frequency");
                freq = (List<String>) extra.getSerializable("frequency_inside");
                changed = true;
                Log.d(TAG, id_get );
                Log.d(TAG, name );
                Log.d(TAG, description );
                Log.d(TAG, alertDateStr.toString());
                Log.d(TAG, String.valueOf(freq));
                etHabitName.setText(name);
                etDescriptionHabit.setText(description);
                alertHour2 = (int) (alertDateStr / 3600);
                alertMinute2 = (int) (alertDateStr - 3600 * alertHour2)/60;
                tvAlertTime2.setText(String.format( "%02d",alertHour2)+":"+String.format("%02d",alertMinute2));
                if (freq.contains("Monday"))
                {
                    tbMonday.setChecked(true);
                    tbMonday.setBackgroundColor(Color.parseColor("#4e02ba"));
                    frequency.add("Monday");
                }
                if (freq.contains("Tuesday"))
                {
                    tbTuesday.setChecked(true);
                    tbTuesday.setBackgroundColor(Color.parseColor("#4e02ba"));
                    frequency.add("Tuesday");
                }
                if (freq.contains("Wednesday"))
                {
                    tbWednesday.setChecked(true);
                    tbWednesday.setBackgroundColor(Color.parseColor("#4e02ba"));
                    frequency.add("Wednesday");
                }
                if (freq.contains("Thursday"))
                {
                    tbThursday.setChecked(true);
                    tbThursday.setBackgroundColor(Color.parseColor("#4e02ba"));
                    frequency.add("Thursday");
                }
                if (freq.contains("Friday"))
                {
                    tbFriday.setChecked(true);
                    tbFriday.setBackgroundColor(Color.parseColor("#4e02ba"));
                    frequency.add("Friday");
                }
                if (freq.contains("Saturday"))
                {
                    tbSaturday.setChecked(true);
                    tbSaturday.setBackgroundColor(Color.parseColor("#4e02ba"));
                    frequency.add("Saturday");
                }
                if (freq.contains("Sunday"))
                {
                    tbSunday.setChecked(true);
                    tbSunday.setBackgroundColor(Color.parseColor("#4e02ba"));
                    frequency.add("Sunday");
                }
            }
        }
        final String id = id_get;

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
                if (tbMonday.isChecked()) { if (!frequency.contains("Monday")){ frequency.add("Monday");
                    tbMonday.setBackgroundColor(Color.parseColor("#4e02ba"));
                }}
                else { if (frequency.contains("Monday")) { frequency.remove("Monday");
                    tbMonday.setBackgroundColor(Color.parseColor("#6600EE"));
                }}
            }
        });

        tbTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbTuesday.isChecked()) { if (!frequency.contains("Tuesday")){ frequency.add("Tuesday");
                    tbTuesday.setBackgroundColor(Color.parseColor("#4e02ba"));
                }}
                else { if (frequency.contains("Tuesday")) { frequency.remove("Tuesday");
                    tbTuesday.setBackgroundColor(Color.parseColor("#6600EE"));
                }}
            }
        });

        tbWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbWednesday.isChecked()) {if (!frequency.contains("Wednesday")){ frequency.add("Wednesday");
                    tbWednesday.setBackgroundColor(Color.parseColor("#4e02ba"));
                }}
                else { if (frequency.contains("Wednesday")) { frequency.remove("Wednesday");
                    tbWednesday.setBackgroundColor(Color.parseColor("#6600EE"));
                }}
            }
        });
        tbThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbThursday.isChecked()) { if (!frequency.contains("Thursday")){ frequency.add("Thursday");
                    tbThursday.setBackgroundColor(Color.parseColor("#4e02ba"));
                }}
                else { if (frequency.contains("Thursday")) { frequency.remove("Thursday");
                    tbThursday.setBackgroundColor(Color.parseColor("#6600EE"));
                }}
            }
        });
        tbFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbFriday.isChecked()) { if (!frequency.contains("Friday")){ frequency.add("Friday");
                    tbFriday.setBackgroundColor(Color.parseColor("#4e02ba"));
                }}
                else { if (frequency.contains("Friday")) { frequency.remove("Friday");
                    tbFriday.setBackgroundColor(Color.parseColor("#6600EE"));
                }}
            }
        });
        tbSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbSaturday.isChecked()) { if (!frequency.contains("Saturday")){ frequency.add("Saturday");
                    tbSaturday.setBackgroundColor(Color.parseColor("#4e02ba"));
                }}
                else { if (frequency.contains("Saturday")) { frequency.remove("Saturday");
                    tbSaturday.setBackgroundColor(Color.parseColor("#6600EE"));
                }}
            }
        });
        tbSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbSunday.isChecked()) { if (!frequency.contains("Sunday")){ frequency.add("Sunday");
                    tbSunday.setBackgroundColor(Color.parseColor("#4e02ba"));
                }}
                else { if (frequency.contains("Sunday")) { frequency.remove("Sunday");
                    tbSaturday.setBackgroundColor(Color.parseColor("#6600EE"));
                }}
            }
        });

        boolean finalChanged = changed;
        tvSave2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aDate = String.format("%02d", alertHour2) + ":" + String.format("%02d", alertMinute2) + "Z" + timeZone;
                DateFormat format = new SimpleDateFormat("HH:mm'Z'");
                DocumentReference documentReference = db.document("Users/" + user.getUid());

                try {
                    if (frequency.size() == 0) {
                        throw new NullPointerException("You need at least 1 day of habit");
                    }
                    if (TextUtils.isEmpty(etHabitName.getText().toString())) {
                        throw new NullPointerException("Habit Name can not be null");
                    }

                    Date date = (Date) format.parse(aDate);
                    Timestamp alertHour2 = new Timestamp(date);
                    Habit habit = new Habit(String.valueOf(etHabitName.getText()), String.valueOf(etDescriptionHabit.getText()), frequency, alertHour2, documentReference);
                    if (finalChanged == true){
                        db.collection("Habits").document(id).delete();
                        db.collection("Habits").document().set(habit);
                    }else {
                        db.collection("Habits").document().set(habit);
                    }
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    startActivity(intent);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.d("Err", "Not pushed to the database");
                } catch (NullPointerException e) {
                    Toast.makeText(CreateHabitActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
