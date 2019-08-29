package com.autonavi.minimap.ajx3.widget.view.toast;

import android.os.Handler;
import android.os.Message;

public class SafelyHandlerWrapper extends Handler {
    private Handler impl;

    public SafelyHandlerWrapper(Handler handler) {
        this.impl = handler;
    }

    public void dispatchMessage(Message message) {
        try {
            this.impl.dispatchMessage(message);
        } catch (Exception unused) {
        }
    }

    public void handleMessage(Message message) {
        this.impl.handleMessage(message);
    }
}
