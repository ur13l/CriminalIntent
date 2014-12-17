package com.projects.ur13l.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ur13l on 17/12/14.
 */
public class DatePickerFragment extends DialogFragment{
    public static final String EXTRA_DATE =
            "com.projects.ur13l.criminalintent.date";

    private Date mDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        mDate = (Date) getArguments().getSerializable(EXTRA_DATE);

        //Create a calendar to the the year, month and day.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //Create te view to inflate the dialog interface
        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_date, null);

        DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year,month,day,new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDate = new GregorianCalendar(year,monthOfYear,dayOfMonth).getTime();
                getArguments().putSerializable(EXTRA_DATE,mDate);
            }
        });

        //Returning the dialog view with a title, view and buttons.
        return new AlertDialog.Builder(getActivity())
                .setView(v) //The view is attached between the Title and the Buttons.
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .create();
    }

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE,date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;

    }

    private void sendResult(int resultCode){
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_DATE,mDate);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(),resultCode,i);
    }

}
