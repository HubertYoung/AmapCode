package com.alipay.mobile.common.logging.util.avail;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import org.json.JSONObject;

public class ExceptionData implements Comparable {
    public static final String E_LAUNCH_TIME = "LAUNCH_TIME";
    public static final String E_TYPE = "TYPE";
    public static final String TYPE_ANR = "ANR";
    public static final String TYPE_CRASH = "CRASH";
    public static final String TYPE_START_APP_FAIL = "START_APP_FAIL";
    public static final String TYPE_START_UP_DEAD = "START_UP_DEAD";
    private String a;
    private long b = 0;

    public String getExceptionType() {
        return this.a;
    }

    public void setExceptionType(String exceptionType) {
        this.a = exceptionType;
    }

    public long getClientLaunchTime() {
        return this.b;
    }

    public void setClientLaunchTime(long clientLaunchTime) {
        this.b = clientLaunchTime;
    }

    public void parse(JSONObject jsonObject) {
        try {
            setExceptionType(jsonObject.optString(E_TYPE));
            setClientLaunchTime(jsonObject.optLong(E_LAUNCH_TIME));
        } catch (Throwable wr) {
            LoggerFactory.getTraceLogger().warn((String) "ExceptionData", wr);
        }
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(E_TYPE, getExceptionType());
            jsonObject.putOpt(E_LAUNCH_TIME, Long.valueOf(getClientLaunchTime()));
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "ExceptionData", e);
        }
        return jsonObject;
    }

    public int compareTo(Object another) {
        long mine = this.b;
        long theirs = ((ExceptionData) another).b;
        if (mine > theirs) {
            return 1;
        }
        if (mine < theirs) {
            return -1;
        }
        return 0;
    }

    public static boolean isValidExceptionType(String type) {
        return type == null || TYPE_CRASH.equals(type) || TYPE_START_UP_DEAD.equals(type) || TYPE_ANR.equals(type) || TYPE_START_APP_FAIL.equals(type);
    }
}
