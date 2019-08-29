package com.alipay.mobile.h5container.api;

import android.os.Handler;
import com.alipay.mobile.nebula.util.H5Log;

public class H5AvailablePageData {
    public static final String TAG = "H5AvailablePageData";
    private long didFinishedLoadDate;
    private Handler handler = new Handler();
    private boolean isWaitForStopLoading;
    private boolean isWaitForStopLoadingWithLoc;
    private int loadingReqsWithLoc;
    private long pageStartTime;
    private long stopLoadingTime;
    private StopLoadingTimeRunnable stopLoadingTimeRunnable = new StopLoadingTimeRunnable();
    private long stopLoadingTimeWithLoc;
    private StopLoadingTimeWithLocRunnable stopLoadingTimeWithLocRunnable = new StopLoadingTimeWithLocRunnable();
    private long waitForStopTime;
    private long waitForStopTimeWithLoc;

    private class StopLoadingTimeRunnable implements Runnable {
        private StopLoadingTimeRunnable() {
        }

        public void run() {
            H5AvailablePageData.this.stopLoading();
        }
    }

    private class StopLoadingTimeWithLocRunnable implements Runnable {
        private StopLoadingTimeWithLocRunnable() {
        }

        public void run() {
            H5AvailablePageData.this.stopLoadingWithLoc();
        }
    }

    public H5AvailablePageData() {
        clear();
    }

    public long getPageStartTime() {
        return this.pageStartTime;
    }

    public void setPageStartTime(long pageStartTime2) {
        this.pageStartTime = pageStartTime2;
    }

    public long getStopLoadingTime() {
        return this.stopLoadingTime;
    }

    public long getStopLoadingTimeWithLoc() {
        return this.stopLoadingTimeWithLoc;
    }

    public void clear() {
        H5Log.d(TAG, "clear");
        this.didFinishedLoadDate = 0;
        this.loadingReqsWithLoc = 0;
        this.isWaitForStopLoading = false;
        this.isWaitForStopLoadingWithLoc = false;
        this.waitForStopTime = 0;
        this.stopLoadingTime = 0;
        this.waitForStopTimeWithLoc = 0;
        this.stopLoadingTimeWithLoc = 0;
    }

    public void reportDidFinishedLoadDate(long date) {
        H5Log.d(TAG, "reportDidFinishedLoadDate");
        this.didFinishedLoadDate = date;
        waitForStopLoading();
        waitForStopLoadingWithLoc();
    }

    public void reportReqStart() {
        H5Log.d(TAG, "reportReqStart");
        if (this.isWaitForStopLoading && this.stopLoadingTime != 0) {
            stopWaitForStopLoading();
        }
    }

    public void reportReqEnd() {
        H5Log.d(TAG, "reportReqEnd");
        if (this.stopLoadingTime != 0) {
            waitForStopLoading();
        }
    }

    public void reportLocStart() {
        H5Log.d(TAG, "reportLocStart");
        this.loadingReqsWithLoc++;
        if (this.isWaitForStopLoadingWithLoc && this.stopLoadingTimeWithLoc != 0) {
            stopWaitForStopLoadingWithLoc();
        }
    }

    public void reportLocEnd() {
        H5Log.d(TAG, "reportLocEnd");
        this.loadingReqsWithLoc--;
        if (this.loadingReqsWithLoc == 0 && this.stopLoadingTimeWithLoc != 0) {
            waitForStopLoadingWithLoc();
        }
    }

    public void waitForStopLoadingWithLoc() {
        if (this.didFinishedLoadDate != 0 && this.loadingReqsWithLoc == 0) {
            H5Log.d(TAG, "waitForStopLoadingWithLoc");
            this.isWaitForStopLoadingWithLoc = true;
            this.waitForStopTimeWithLoc = System.currentTimeMillis();
            this.handler.postDelayed(this.stopLoadingTimeWithLocRunnable, 1000);
        }
    }

    public void stopWaitForStopLoadingWithLoc() {
        H5Log.d(TAG, "stopWaitForStopLoadingWithLoc");
        this.isWaitForStopLoadingWithLoc = false;
        this.handler.removeCallbacks(this.stopLoadingTimeWithLocRunnable);
    }

    public void waitForStopLoading() {
        if (this.didFinishedLoadDate != 0) {
            H5Log.d(TAG, "waitForStopLoading");
            this.isWaitForStopLoading = true;
            this.waitForStopTime = System.currentTimeMillis();
            this.handler.postDelayed(this.stopLoadingTimeRunnable, 1000);
        }
    }

    public void stopWaitForStopLoading() {
        H5Log.d(TAG, "stopWaitForStopLoading");
        this.isWaitForStopLoading = false;
        this.handler.removeCallbacks(this.stopLoadingTimeRunnable);
    }

    public void stopLoading() {
        H5Log.d(TAG, "stopLoading");
        this.stopLoadingTime = this.waitForStopTime;
    }

    public void stopLoadingWithLoc() {
        H5Log.d(TAG, "stopLoadingWithLoc");
        this.stopLoadingTimeWithLoc = this.waitForStopTimeWithLoc;
    }
}
