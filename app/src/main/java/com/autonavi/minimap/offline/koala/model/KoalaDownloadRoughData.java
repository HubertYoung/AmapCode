package com.autonavi.minimap.offline.koala.model;

import android.support.annotation.Keep;

@Keep
public class KoalaDownloadRoughData {
    private ActionKind mActionKind;
    private long mActionTime;
    private long mDownloadBytes;
    private int mId;
    private Throwable mThrowable;
    private long mTotalBytes;
    private String mUrl;

    public enum ActionKind {
        START,
        PAUSE,
        RESUME
    }

    public KoalaDownloadRoughData setTotalBytes(long j) {
        this.mTotalBytes = j;
        return this;
    }

    public KoalaDownloadRoughData setDownloadBytes(long j) {
        this.mDownloadBytes = j;
        return this;
    }

    public KoalaDownloadRoughData setActionTime(long j) {
        this.mActionTime = j;
        return this;
    }

    public KoalaDownloadRoughData setId(int i) {
        this.mId = i;
        return this;
    }

    public KoalaDownloadRoughData setUrl(String str) {
        this.mUrl = str;
        return this;
    }

    public KoalaDownloadRoughData setActionKind(ActionKind actionKind) {
        this.mActionKind = actionKind;
        return this;
    }

    public KoalaDownloadRoughData setThrowable(Throwable th) {
        this.mThrowable = th;
        return this;
    }

    public long getActionTime() {
        return this.mActionTime;
    }

    public int getId() {
        return this.mId;
    }

    public ActionKind getActionKind() {
        return this.mActionKind;
    }

    public Throwable getThrowable() {
        return this.mThrowable;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public long getTotalBytes() {
        return this.mTotalBytes;
    }

    public long getDownloadBytes() {
        return this.mDownloadBytes;
    }
}
