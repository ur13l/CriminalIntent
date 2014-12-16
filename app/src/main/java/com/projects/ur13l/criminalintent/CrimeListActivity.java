package com.projects.ur13l.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by ur13l on 15/12/14.
 */
public class CrimeListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
