package com.autonavi.indoor.provider;

import android.content.Context;
import android.os.Handler;
import com.autonavi.indoor.util.L;
import com.autonavi.indoor.util.MessageHelper;
import java.util.ArrayList;

public class IProvider {
    public Context mContext;
    public Handler mInnerHandler = null;
    protected volatile boolean mIsListening = false;
    public final ArrayList<Handler> mOutterHandlers = new ArrayList<>();

    public boolean isInited() {
        return this.mContext != null;
    }

    public int init(Context context) {
        throw new RuntimeException("Subclass should implement this method");
    }

    public void registerListener(Handler handler) {
        if (handler != null) {
            checkConfiguration();
            synchronized (this) {
                synchronized (this.mOutterHandlers) {
                    if (!this.mOutterHandlers.contains(handler)) {
                        this.mOutterHandlers.add(handler);
                    } else if (L.isLogging) {
                        L.d((String) "Handler already exist");
                    }
                    if (this.mOutterHandlers.size() > 0 && !this.mIsListening) {
                        int start = start();
                        if (start != 0) {
                            MessageHelper.publishMessage(this.mOutterHandlers, start);
                        }
                    }
                }
            }
        }
    }

    public void unregisterListener(Handler handler) {
        checkConfiguration();
        synchronized (this) {
            synchronized (this.mOutterHandlers) {
                this.mOutterHandlers.remove(handler);
                if (this.mOutterHandlers.isEmpty()) {
                    stop();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public int start() {
        throw new RuntimeException("Subclass should implement this method");
    }

    /* access modifiers changed from: protected */
    public boolean stop() {
        throw new RuntimeException("Subclass should implement this method");
    }

    /* access modifiers changed from: protected */
    public void checkConfiguration() {
        if (this.mContext == null) {
            throw new IllegalStateException("LocationProvider must be init with Context before using");
        }
    }
}
