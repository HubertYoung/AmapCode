package com.sina.weibo.sdk.statistic;

import android.content.Context;
import android.content.SharedPreferences.Editor;

class PageLog {
    private static String FILE_SESSION = "session";
    private static long MIN_ENDTIME = 1000;
    private long mDuration;
    private long mEnd_time;
    protected String mPage_id;
    protected long mStart_time;
    protected LogType mType;

    public PageLog(Context context) {
        this.mStart_time = getSessionTime(context, LogBuilder.KEY_START_TIME);
        this.mEnd_time = getSessionTime(context, LogBuilder.KEY_END_TIME);
        this.mDuration = this.mEnd_time - this.mStart_time;
    }

    public PageLog(String str) {
        this.mPage_id = str;
        this.mStart_time = System.currentTimeMillis();
    }

    public PageLog(Context context, long j) {
        this.mStart_time = j;
        this.mEnd_time = MIN_ENDTIME;
        updateSession(context, null, Long.valueOf(this.mStart_time), Long.valueOf(this.mEnd_time));
    }

    public PageLog() {
    }

    public PageLog(String str, long j) {
        this.mPage_id = str;
        this.mStart_time = j;
    }

    public LogType getType() {
        return this.mType;
    }

    public void setType(LogType logType) {
        this.mType = logType;
    }

    public String getPage_id() {
        return this.mPage_id;
    }

    public long getStartTime() {
        return this.mStart_time;
    }

    public long getEndTime() {
        return this.mEnd_time;
    }

    public void setDuration(long j) {
        this.mDuration = j;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public void setmStart_time(long j) {
        this.mStart_time = j;
    }

    public static boolean isNewSession(Context context, long j) {
        long sessionTime = getSessionTime(context, LogBuilder.KEY_END_TIME);
        return sessionTime > MIN_ENDTIME ? j - sessionTime > StatisticConfig.kContinueSessionMillis : sessionTime != MIN_ENDTIME;
    }

    private static long getSessionTime(Context context, String str) {
        return context.getSharedPreferences(FILE_SESSION, 0).getLong(str, 0);
    }

    public static void updateSession(Context context, String str, Long l, Long l2) {
        Editor edit = context.getSharedPreferences(FILE_SESSION, 0).edit();
        if (l.longValue() != 0) {
            edit.putLong(LogBuilder.KEY_START_TIME, l.longValue());
        }
        edit.putLong(LogBuilder.KEY_END_TIME, l2.longValue());
        edit.commit();
    }
}
