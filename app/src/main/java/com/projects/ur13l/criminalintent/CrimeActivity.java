package com.projects.ur13l.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;


public class CrimeActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }

}
