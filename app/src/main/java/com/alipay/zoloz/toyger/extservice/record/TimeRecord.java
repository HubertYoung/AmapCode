package com.alipay.zoloz.toyger.extservice.record;

public class TimeRecord {
    private static TimeRecord instance = new TimeRecord();
    private long enterDetectionTime;
    private long entrySdkTime;
    private long initAlgStartTime;
    private long livebodyStartTime;
    private long uploadStartTime;

    public static TimeRecord getInstance() {
        return instance;
    }

    public long getEntrySdkTime() {
        return this.entrySdkTime;
    }

    public void setEntrySdkTime(long j) {
        this.entrySdkTime = j;
    }

    public long getEnterDetectionTime() {
        return this.enterDetectionTime;
    }

    public void setEnterDetectionTime(long j) {
        this.enterDetectionTime = j;
    }

    public long getUploadStartTime() {
        return this.uploadStartTime;
    }

    public void setUploadStartTime(long j) {
        this.uploadStartTime = j;
    }

    public long getLivebodyStartTime() {
        return this.livebodyStartTime;
    }

    public void setLivebodyStartTime(long j) {
        this.livebodyStartTime = j;
    }

    public long getInitAlgStartTime() {
        return this.initAlgStartTime;
    }

    public void setInitAlgStartTime(long j) {
        this.initAlgStartTime = j;
    }
}
