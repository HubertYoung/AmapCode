package com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.processor;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgProcessor {
    private static final Logger a = Logger.getLogger((String) "ProgProcessor");
    private static final int d = ConfigManager.getInstance().getCommonConfigItem().progConf.timeInterval;
    private ProgressHandler b;
    private HandlerThread c;
    private int e = CommonUtils.generateRandom(10, 12);
    private AtomicInteger f = new AtomicInteger(0);
    private ProcessCallback g;
    private boolean h = true;

    public interface ProcessCallback {
        void onProcessCallback(int i);
    }

    private class ProgressHandler extends Handler {
        private Looper b;

        ProgressHandler(Looper looper) {
            super(looper);
            this.b = looper;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ProgProcessor.this.b();
                    return;
                case 2:
                    a();
                    return;
                default:
                    return;
            }
        }

        private void a() {
            try {
                this.b.quit();
            } catch (Exception e) {
            }
        }
    }

    public ProgProcessor() {
    }

    public ProgProcessor(int min, int max) {
        this.e = CommonUtils.generateRandom(min, max);
    }

    public void setSwitch(boolean bOn) {
        this.h = bOn;
    }

    public void startProgress() {
        if (this.h) {
            a.d("startProgress", new Object[0]);
            a().sendEmptyMessageDelayed(1, (long) d);
        }
    }

    public void setCallBack(ProcessCallback callback) {
        this.g = callback;
    }

    public void removeProgressMessage() {
        if (this.h && a().hasMessages(1)) {
            a.d("removeProgressMessage mProgress=" + this.f.get(), new Object[0]);
            a().removeMessages(1);
        }
    }

    public void handlerQuit() {
        if (this.b != null) {
            a().removeMessages(1);
            a().sendEmptyMessage(2);
        }
    }

    private Handler a() {
        if (this.h && this.b == null) {
            this.c = new HandlerThread("prog_process");
            this.c.start();
            this.b = new ProgressHandler(this.c.getLooper());
        }
        return this.b;
    }

    /* access modifiers changed from: private */
    public void b() {
        if (this.f.get() <= this.e) {
            if (this.g != null) {
                this.g.onProcessCallback(this.f.getAndAdd(1));
            }
            this.b.sendEmptyMessageDelayed(1, (long) d);
            return;
        }
        this.b.removeMessages(1);
    }
}
