package com.example.android.quakereport;

public class Earthquake {

    private String mMag;

    private String mLoc;

    private String mTime;

    public Earthquake(String mag, String loc, String time){
        mMag=mag;
        mLoc=loc;
        mTime=time;
    }

    public String getmMag() {
        return mMag;
    }

    public String getmLoc() {
        return mLoc;
    }

    public String getmTime() {
        return mTime;
    }
}
