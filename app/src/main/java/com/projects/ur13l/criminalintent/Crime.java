package com.projects.ur13l.criminalintent;

import java.util.UUID;

/**
 * Created by ur13l on 14/12/14.
 */
public class Crime {
    private UUID mId;
    private String mTitle;

    public Crime(){
        mId=UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

}
