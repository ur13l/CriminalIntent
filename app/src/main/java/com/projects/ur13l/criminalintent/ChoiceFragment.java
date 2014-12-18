package com.projects.ur13l.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by ur13l on 17/12/14.
 */
public class ChoiceFragment extends DialogFragment{
    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;
    Date mDate;
    int mSelectedRadio=1;
    public static CrimeFragment mCrimeFragment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        mDate = (Date)getArguments().getSerializable(DatePickerFragment.EXTRA_DATE);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_choice,null);

        final RadioGroup mChoiceRadioGroup = (RadioGroup) v.findViewById(R.id.choice_radio_group);
        RadioButton mDateRadioButton = (RadioButton) v.findViewById(R.id.radio_date);
        RadioButton mTimeRadioButton = (RadioButton) v.findViewById(R.id.radio_time);

        mChoiceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(mChoiceRadioGroup.getCheckedRadioButtonId() == R.id.radio_date)
                    mSelectedRadio = 1;
                if(mChoiceRadioGroup.getCheckedRadioButtonId() == R.id.radio_time)
                    mSelectedRadio = 2;
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v) //The view is attached between the Title and the Buttons.
                .setTitle(R.string.choice_title)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentManager fm = getActivity()
                                        .getSupportFragmentManager();
                                if (mSelectedRadio > 0) {
                                    DatePickerFragment d = DatePickerFragment.newInstance(mDate, mSelectedRadio);
                                    d.setTargetFragment(mCrimeFragment, REQUEST_DATE);
                                    d.show(fm, DIALOG_DATE);
                                } else {
                                    Toast.makeText(getActivity(),R.string.toast_choice,Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                .create();

    }


    public static ChoiceFragment newInstance(Date date, CrimeFragment crimeFragment){

        Bundle args = new Bundle();
        args.putSerializable(DatePickerFragment.EXTRA_DATE,date);
        mCrimeFragment=crimeFragment;
        ChoiceFragment c = new ChoiceFragment();
        c.setArguments(args);
        return c;
    }
}
