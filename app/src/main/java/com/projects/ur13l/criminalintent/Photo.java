package com.projects.ur13l.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ur13l on 28/12/14.
 */
public class Photo {
    private static final String JSON_FILENAME = "filename";

    private String mFilename;

    //Create a photo representing an existing file on disk
    public Photo(String filename){
        mFilename = filename;
    }

    public Photo(JSONObject json) throws JSONException{
        mFilename = json.getString(JSON_FILENAME);
    }

    public JSONObject toJSON() throws  JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_FILENAME, mFilename);
        return json;
    }

    public String getFilename() {
        return mFilename;
    }
}
