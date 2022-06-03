package com.example.habitary.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.habitary.R;

import java.util.Calendar;


public class CreateTaskFragment extends Fragment {

    Calendar calendar = Calendar.getInstance();
    TextView tvStartDay;
    TextView tvStartTime;
    TextView tvEndDay;
    TextView tvEndTime;
    EditText etSelectDate;
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int minute = calendar.get(Calendar.MINUTE);
    int hour = calendar.get(Calendar.HOUR_OF_DAY);

    public CreateTaskFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_task, container, false);
        tvStartDay = view.findViewById(R.id.tvStartDay);
        tvStartTime = view.findViewById(R.id.tvStartTime);
        tvEndDay = view.findViewById(R.id.tvEndDay);
        tvEndTime = view.findViewById(R.id.tvEndTime);
        etSelectDate = view.findViewById(R.id.etName);
        tvStartDay.setText(String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
        tvStartTime.setText(String.format( "%02d",hour)+":"+String.format("%02d",minute));
        tvEndDay.setText(String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
        tvEndTime.setText(String.format( "%02d",hour+1)+":"+String.format("%02d",minute));
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        tvStartDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*OSTATNIA DZIALAJACA WERSJA

                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.show(getChildFragmentManager(), "datePicker");
                tvStartTime.setText(String.valueOf(bundle.getString("selectedDate")));*/

                DatePickerDialog dayDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
                TimePickerDialog timeDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
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

                DatePickerDialog endDayDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        tvEndDay.setText(String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
                    }
                },year, month, day);
                endDayDialog.show();
            }
        });
        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog endTimeDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        tvEndTime.setText(String.format( "%02d",hour)+":"+String.format("%02d",minute));
                    }
                },hour, minute,true);
                endTimeDialog.show();
            }
        });
    }
}