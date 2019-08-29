package com.alipay.mobile.nebula.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.alipay.mobile.nebula.util.H5Log;

public class H5Progress extends ProgressBar {
    public static final int DEFAULT_DURATION = 1200;
    public static final int MIN_DURATION = 300;
    public static final String TAG = "H5Progress";
    /* access modifiers changed from: private */
    public long curDuration;
    /* access modifiers changed from: private */
    public boolean isRunnableWorking = false;
    /* access modifiers changed from: private */
    public int lastProgress;
    /* access modifiers changed from: private */
    public int lastTarget;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler(Looper.getMainLooper());
    private UpdateRunnable mUpdateRunnable = new UpdateRunnable();
    private long minDuration;
    /* access modifiers changed from: private */
    public int nextVisibility;
    /* access modifiers changed from: private */
    public ProgressNotifier notifier;
    private long originTime;
    /* access modifiers changed from: private */
    public long startTime;
    /* access modifiers changed from: private */
    public int targetProgress;

    public interface ProgressNotifier {
        void onProgressBegin();

        void onProgressEnd();
    }

    class UpdateRunnable implements Runnable {
        private int deltaProgress;
        private int period;

        UpdateRunnable() {
        }

        public void setPeriodValue(int period2) {
            this.period = period2;
        }

        public void setdeltaProgressValue(int progress) {
            this.deltaProgress = progress;
        }

        public void run() {
            H5Progress.this.isRunnableWorking = true;
            int max = H5Progress.this.getMax();
            if (max == 0) {
                H5Progress.this.mHandler.removeCallbacks(this);
                H5Progress.this.isRunnableWorking = false;
                return;
            }
            long deltaTime = System.currentTimeMillis() - H5Progress.this.startTime;
            if ((H5Progress.this.curDuration * ((long) this.deltaProgress)) / ((long) max) == 0) {
                H5Progress.this.mHandler.removeCallbacks(this);
                H5Progress.this.isRunnableWorking = false;
                return;
            }
            int nextProgress = H5Progress.this.lastTarget + ((int) ((((long) this.deltaProgress) * deltaTime) / H5Progress.this.curDuration));
            if (nextProgress > H5Progress.this.targetProgress) {
                if (nextProgress > H5Progress.this.getMax() && H5Progress.this.notifier != null) {
                    H5Progress.this.notifier.onProgressEnd();
                }
                if (H5Progress.this.nextVisibility != -1) {
                    H5Progress.super.setVisibility(H5Progress.this.nextVisibility);
                    H5Progress.this.nextVisibility = -1;
                }
            } else {
                if (H5Progress.this.lastProgress == 0 && H5Progress.this.notifier != null) {
                    H5Progress.this.notifier.onProgressBegin();
                }
                H5Progress.this.setProgress(nextProgress);
                H5Progress.this.lastProgress = nextProgress;
            }
            if (nextProgress > H5Progress.this.targetProgress) {
                if (nextProgress > H5Progress.this.getMax()) {
                    H5Progress.this.reset();
                }
                H5Progress.this.mHandler.removeCallbacks(this);
                H5Progress.this.isRunnableWorking = false;
                return;
            }
            H5Progress.this.mHandler.postDelayed(this, (long) this.period);
        }
    }

    public H5Progress(Context context) {
        super(context);
        init();
    }

    public H5Progress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public H5Progress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setNotifier(ProgressNotifier notifier2) {
        this.notifier = notifier2;
    }

    private void init() {
        this.minDuration = 1200;
        setMax(100);
        this.nextVisibility = -1;
        reset();
    }

    /* access modifiers changed from: private */
    public void reset() {
        this.originTime = 0;
        this.targetProgress = 0;
        this.lastTarget = 0;
        this.lastProgress = 0;
    }

    public void setMinDuration(long duration) {
        this.minDuration = duration;
    }

    public void updateProgress(int progress) {
        long current = System.currentTimeMillis();
        if (this.originTime == 0) {
            this.originTime = current;
        }
        int max = getMax();
        int progress2 = (int) ((((double) progress) * 0.25d) + (((double) max) * 0.75d));
        if (progress2 >= this.lastProgress && progress2 <= max) {
            this.lastTarget = this.lastProgress;
            this.startTime = current;
            this.targetProgress = progress2;
            linearProgress();
        }
    }

    private void linearProgress() {
        if (isIndeterminate()) {
            H5Log.d(TAG, "isIndeterminate");
            return;
        }
        this.curDuration = this.minDuration;
        if (this.targetProgress == getMax() && this.lastTarget > getMax() / 2) {
            this.curDuration = 300;
        }
        int deltaProgress = this.targetProgress - this.lastTarget;
        if (deltaProgress > 0 && this.curDuration > 0) {
            int curPeriod = (int) (this.curDuration / ((long) deltaProgress));
            this.mHandler.removeCallbacks(this.mUpdateRunnable);
            this.mUpdateRunnable.setPeriodValue(curPeriod);
            this.mUpdateRunnable.setdeltaProgressValue(deltaProgress);
            this.mHandler.postDelayed(this.mUpdateRunnable, (long) curPeriod);
        }
    }

    public void setVisibility(int visibility) {
        H5Log.d(TAG, "setVisibility:" + visibility);
        if (!this.isRunnableWorking || visibility == 0) {
            super.setVisibility(visibility);
        } else {
            this.nextVisibility = visibility;
        }
    }

    public void hideProgress() {
        H5Log.d(TAG, "hideProgress");
        super.setVisibility(8);
    }
}
