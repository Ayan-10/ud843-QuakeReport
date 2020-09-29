package com.example.android.quakereport;

public class Earthquake {

    private double mMag;

    private String mLoc;

    private long mTimeInMilliseconds;

    String mUrl;

    public Earthquake(double mag, String loc, long TimeInMilliseconds, String url){
        mMag=mag;
        mLoc=loc;
        mTimeInMilliseconds=TimeInMilliseconds;
        mUrl=url;
    }

    public double getMag() {
        return mMag;
    }

    public String getLoc() {
        return mLoc;
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getmUrl() {
        return mUrl;
    }
}
