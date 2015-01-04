package com.projects.ur13l.criminalintent;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * Created by ur13l on 15/12/14.
 */
public abstract class SingleFragmentActivity extends ActionBarActivity {

    protected abstract Fragment createFragment();

    protected int getLayoutResId(){
        return R.layout.activity_fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer,fragment)
                    .commit();
        }
    }



}
