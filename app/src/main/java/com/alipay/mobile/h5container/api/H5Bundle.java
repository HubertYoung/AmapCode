package com.alipay.mobile.h5container.api;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class H5Bundle {
    public static final String TAG = "H5Bundle";
    private List<H5Listener> listeners;
    private Bundle params;

    public H5Bundle() {
        this.listeners = new ArrayList();
    }

    public H5Bundle(Bundle params2) {
        this();
        setParams(params2);
    }

    public List<H5Listener> getListeners() {
        return this.listeners;
    }

    public void addListener(H5Listener listener) {
        if (listener != null && !this.listeners.contains(listener)) {
            this.listeners.add(listener);
        }
    }

    public Bundle getParams() {
        if (this.params == null) {
            this.params = new Bundle();
        }
        return this.params;
    }

    public void setParams(Bundle params2) {
        this.params = params2;
    }
}
