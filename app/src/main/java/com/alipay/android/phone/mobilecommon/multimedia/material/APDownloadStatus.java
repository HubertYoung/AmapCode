package com.alipay.android.phone.mobilecommon.multimedia.material;

import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;

public class APDownloadStatus {
    public static final int STATUS_CANCEL = 2;
    public static final int STATUS_EXISTS = 5;
    public static final int STATUS_FAIL = 3;
    public static final int STATUS_PACKAGE_INFO_QUERYING = 6;
    public static final int STATUS_RUNNING = 1;
    public static final int STATUS_SUCCESS = 4;
    public static final int STATUS_UNINITIAL = 7;
    public static final int STATUS_WAIT = 0;
    private int mPercent;
    private long mProcessSize;
    private int mStatus;
    private long mTotalSize;

    public APDownloadStatus() {
        this(0);
    }

    public APDownloadStatus(int status) {
        this.mStatus = status;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void setStatus(int status) {
        this.mStatus = status;
    }

    public int getPercent() {
        return this.mPercent;
    }

    public void setPercent(int percent) {
        this.mPercent = percent;
    }

    public long getProcessSize() {
        return this.mProcessSize;
    }

    public void setProcessSize(long processSize) {
        this.mProcessSize = processSize;
    }

    public long getTotalSize() {
        return this.mTotalSize;
    }

    public void setTotalSize(long totalSize) {
        this.mTotalSize = totalSize;
    }

    public void fromFileStatus(APMultimediaTaskModel model) {
        if (model != null) {
            setStatus(model.getStatus());
            setPercent(model.getPercent());
            setProcessSize(model.getCurrentSize());
            setTotalSize(model.getTotalSize());
            return;
        }
        setStatus(7);
    }
}
