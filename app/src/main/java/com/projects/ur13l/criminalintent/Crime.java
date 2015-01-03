package com.projects.ur13l.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;
import java.util.Date;

/**
 * Created by ur13l on 14/12/14.
 */
public class Crime {
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";
    private static final String JSON_PHOTO = "photo";
    private static final String JSON_SUSPECT = "suspect";
    private static final String JSON_SUSPECT_PHONE = "suspect_phone";

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private Photo mPhoto;
    private boolean mSolved;
    private String mSuspect;
    private String mSuspectPhone;

    public String getSuspectPhone() {
        return mSuspectPhone;
    }

    public void setSuspectPhone(String mSuspectPhone) {
        this.mSuspectPhone = mSuspectPhone;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public Crime(){
        mId=UUID.randomUUID();
        mDate = new Date();

    }

    public Crime(JSONObject json) throws JSONException{
        mId = UUID.fromString(json.getString(JSON_ID));
        if(json.has(JSON_TITLE)){
            mTitle = json.getString(JSON_TITLE);
        }
        if(json.has(JSON_PHOTO)){
            mPhoto = new Photo(json.getJSONObject(JSON_PHOTO));
        }
        if(json.has(JSON_SUSPECT)){
            mSuspect = json.getString(JSON_SUSPECT);
        }
        if(json.has(JSON_SUSPECT_PHONE)){
            mSuspectPhone = json.getString(JSON_SUSPECT_PHONE);
        }
        mSolved = json.getBoolean(JSON_SOLVED);
        mDate = new Date(json.getLong(JSON_DATE));
    }

    public Photo getPhoto() {
        return mPhoto;
    }

    public void setmPhoto(Photo p) {
        this.mPhoto = p;
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle.toString());
        json.put(JSON_SOLVED, mSolved);
        json.put(JSON_DATE, mDate.getTime());
        if(mPhoto != null) {
            json.put(JSON_PHOTO, mPhoto.toJSON());

        }
        json.put(JSON_SUSPECT, mSuspect);
        json.put(JSON_SUSPECT_PHONE, mSuspectPhone);
        return json;
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

    public void setSuspect(String suspect){
        mSuspect = suspect;
    }

    public String getSuspect(){
        return mSuspect;
    }
    @Override
    public String toString(){
        return mTitle;
    }
}
