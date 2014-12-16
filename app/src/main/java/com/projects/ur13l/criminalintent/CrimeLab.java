package com.projects.ur13l.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by ur13l on 15/12/14.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    private ArrayList<Crime> mCrimes;

    private CrimeLab(Context appContext){
        mAppContext=appContext;
        mCrimes = new ArrayList<Crime>();
        for(int i=0;i<100;i++){
            Crime c=new Crime();
            c.setTitle("Crime #"+i);
            c.setSolved(i%2 == 0);
            mCrimes.add(c);
        }
    }

    public static CrimeLab get(Context c){
        if(sCrimeLab==null){
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes(){
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for(Crime c:mCrimes){
            if(c.getId().equals(id)){
                return c;
            }
        }
        return null;

    }

}
