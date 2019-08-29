package com.alipay.android.phone.inside.offlinecode.engine;

import android.webkit.WebView;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;

public class DPETask {
    public static final int TASK_STATUS_DONE = 3;
    public static final int TASK_STATUS_INIT = 1;
    public static final int TASK_STATUS_WORKING = 2;
    public JSEngineCallback callback;
    public JSONObject params;
    public String script;
    public int status = 1;
    public int taskId;
    private Timer timer;
    private WebView webView;

    public interface TimeOutListener {
        void onTimeOut();
    }

    public DPETask(WebView webView2) {
        this.webView = webView2;
        this.timer = new Timer(true);
    }

    public void setTimeOut(final TimeOutListener timeOutListener, long j) {
        this.timer.schedule(new TimerTask() {
            public void run() {
                timeOutListener.onTimeOut();
            }
        }, j);
    }

    public void done() {
        this.timer.cancel();
        this.status = 3;
    }
}
