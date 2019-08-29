package com.alipay.mobile.common.logging.util.crash;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import org.json.JSONObject;

public class CrashInfoDO {
    public int mCode = -1;
    public String mCrashProcessName = "";
    public String mCrashProductVersion = "";
    public String mCrashThreadName = "";
    public int mCrashTimes = 1;
    public int mCrashType = 0;
    public long mLastCrashTime = System.currentTimeMillis();
    public int mSdkVersion = 0;
    public int mSignal = -1;
    public boolean mStartupCrash = false;

    public int getCrashType() {
        return this.mCrashType;
    }

    public void setCrashType(int crashType) {
        this.mCrashType = crashType;
    }

    public int getSdkVersion() {
        return this.mSdkVersion;
    }

    public void setSdkVersion(int mSdkVersion2) {
        this.mSdkVersion = mSdkVersion2;
    }

    public String getCrashProcessName() {
        return this.mCrashProcessName;
    }

    public void setCrashProcessName(String mCrashProcessName2) {
        this.mCrashProcessName = mCrashProcessName2;
    }

    public String getCrashThreadName() {
        return this.mCrashThreadName;
    }

    public void setCrashThreadName(String mCrashThreadName2) {
        this.mCrashThreadName = mCrashThreadName2;
    }

    public String getCrashProductVersion() {
        return this.mCrashProductVersion;
    }

    public void setCrashProductVersion(String mCrashProductVersion2) {
        this.mCrashProductVersion = mCrashProductVersion2;
    }

    public boolean isStartupCrash() {
        return this.mStartupCrash;
    }

    public void setStartupCrash(boolean mStartupCrash2) {
        this.mStartupCrash = mStartupCrash2;
    }

    public int getSignal() {
        return this.mSignal;
    }

    public void setSignal(int mSignal2) {
        this.mSignal = mSignal2;
    }

    public int getCode() {
        return this.mCode;
    }

    public void setCode(int mCode2) {
        this.mCode = mCode2;
    }

    public int getCrashTimes() {
        return this.mCrashTimes;
    }

    public void setCrashTimes(int mCrashTimes2) {
        if (mCrashTimes2 < 0) {
            mCrashTimes2 = Math.abs(mCrashTimes2);
        }
        this.mCrashTimes = mCrashTimes2;
    }

    public long getLastCrashTime() {
        return this.mLastCrashTime;
    }

    public void setLastCrashTime(long mLastCrashTime2) {
        this.mLastCrashTime = mLastCrashTime2;
    }

    public void parse(JSONObject jsonObject) {
        try {
            setCrashType(jsonObject.optInt("CrashType"));
            setCrashProcessName(jsonObject.optString("CrashProcessName"));
            setCrashThreadName(jsonObject.optString("CrashThreadName"));
            setCrashProductVersion(jsonObject.optString("CrashProductVersion"));
            setSdkVersion(jsonObject.optInt("SdkVersion"));
            setStartupCrash(jsonObject.optBoolean("StartupCrash"));
            setSignal(jsonObject.optInt("Signal"));
            setCode(jsonObject.optInt("Code"));
            setCrashTimes(jsonObject.optInt("CrashTimes", 1));
            setLastCrashTime(jsonObject.optLong("LastCrashTime", System.currentTimeMillis()));
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "CrashInfoDO", e);
        }
    }

    public void merge(JSONObject jsonObject) {
        try {
            setCrashType(jsonObject.optInt("CrashType"));
            setCrashProcessName(jsonObject.optString("CrashProcessName"));
            setCrashThreadName(jsonObject.optString("CrashThreadName"));
            setCrashProductVersion(jsonObject.optString("CrashProductVersion"));
            setSdkVersion(jsonObject.optInt("SdkVersion"));
            setStartupCrash(jsonObject.optBoolean("StartupCrash"));
            setSignal(jsonObject.optInt("Signal"));
            setCode(jsonObject.optInt("Code"));
            setCrashTimes(getCrashTimes() + jsonObject.optInt("CrashTimes", 1));
            setLastCrashTime(jsonObject.optLong("LastCrashTime", System.currentTimeMillis()));
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "CrashInfoDO", e);
        }
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt("CrashType", Integer.valueOf(getCrashType()));
            jsonObject.putOpt("CrashProcessName", getCrashProcessName());
            jsonObject.putOpt("CrashThreadName", getCrashThreadName());
            jsonObject.putOpt("CrashProductVersion", getCrashProductVersion());
            jsonObject.putOpt("SdkVersion", Integer.valueOf(getSdkVersion()));
            jsonObject.putOpt("StartupCrash", Boolean.valueOf(isStartupCrash()));
            jsonObject.putOpt("Signal", Integer.valueOf(getSignal()));
            jsonObject.putOpt("Code", Integer.valueOf(getCode()));
            jsonObject.putOpt("CrashTimes", Integer.valueOf(getCrashTimes()));
            jsonObject.putOpt("LastCrashTime", Long.valueOf(getLastCrashTime()));
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "CrashInfoDO", e);
        }
        return jsonObject;
    }
}
