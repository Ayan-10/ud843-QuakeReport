package com.example.android.quakereport;

public class Earthquake {

    private double mMag;

    private String mLoc;

    private long mTime;

    String mUrl;

    public Earthquake(double mag, String loc, long Time, String url){
        mMag=mag;
        mLoc=loc;
        mTime=Time;
        mUrl=url;
    }

    public double getMag() {
        return mMag;
    }

    public String getLoc() {
        return mLoc;
    }

    public long getTimeInMilliseconds() {
        return mTime;
    }

    public String getmUrl() {
        return mUrl;
    }
}
