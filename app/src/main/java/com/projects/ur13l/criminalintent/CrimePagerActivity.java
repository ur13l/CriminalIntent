package com.projects.ur13l.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by ur13l on 16/12/14.
 */
public class CrimePagerActivity extends ActionBarActivity implements CrimeFragment.Callbacks{
    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;
   @Override
   public void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
       mViewPager.setId(R.id.viewPager);
       setContentView(mViewPager);

       mCrimes = CrimeLab.get(this).getCrimes();

       FragmentManager fm = getSupportFragmentManager();
       mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
           @Override
           public Fragment getItem(int i) {
               Crime crime = mCrimes.get(i);
               return CrimeFragment.newInstance(crime.getId());
           }

           @Override
           public int getCount() {
               return mCrimes.size();
           }
       });

       UUID crimeId = (UUID)getIntent()
               .getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
       for(int i=0;i<mCrimes.size();i++){
           if(mCrimes.get(i).getId().equals(crimeId)){
               mViewPager.setCurrentItem(i);
               setTitle(mCrimes.get(i).getTitle());
               break;
           }
       }

       mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

           @Override
           public void onPageScrolled(int i, float v, int i2) {

           }

           @Override
           public void onPageSelected(int i) {
                Crime crime = mCrimes.get(i);
                if(crime.getTitle() != null){
                    setTitle(crime.getTitle());
                }
           }

           @Override
           public void onPageScrollStateChanged(int i) {

           }
       });
   }

    public void onCrimeUpdated(Crime crime){

    }


}
