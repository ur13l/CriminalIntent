package com.projects.ur13l.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * Created by ur13l on 14/12/14.
 */
public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private DateFormat mDf;

    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mCrime=new Crime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_crime,parent,false);
        mTitleField=(EditText)v.findViewById(R.id.crime_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Blank space
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Blank space
            }
        });

        mDateButton = (Button)v.findViewById(R.id.crime_date);

        mDf = new DateFormat();

        mDateButton.setText(mDf.format("E, MMM d, yyyy",mCrime.getDate()).toString());
        mDateButton.setEnabled(false);

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }
}
