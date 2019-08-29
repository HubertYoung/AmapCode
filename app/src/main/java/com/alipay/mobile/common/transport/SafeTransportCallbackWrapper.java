package com.alipay.mobile.common.transport;

import com.alipay.mobile.common.transport.utils.LogCatUtil;

public class SafeTransportCallbackWrapper extends TransportCallbackAdapter {
    private TransportCallback a;

    public SafeTransportCallbackWrapper(TransportCallback transportCallback) {
        this.a = transportCallback;
    }

    public void onCancelled(Request request) {
        try {
            super.onCancelled(request);
        } catch (Throwable e) {
            LogCatUtil.warn("SafeTransportCallbackWrapper", "super.onCancelled fail", e);
        }
        try {
            this.a.onCancelled(request);
        } catch (Throwable e2) {
            LogCatUtil.warn("SafeTransportCallbackWrapper", "onCancelled fail", e2);
        }
    }

    public void onPreExecute(Request request) {
        try {
            super.onPreExecute(request);
        } catch (Throwable e) {
            LogCatUtil.warn("SafeTransportCallbackWrapper", "super.onPreExecute fail", e);
        }
        try {
            this.a.onPreExecute(request);
        } catch (Throwable e2) {
            LogCatUtil.warn("SafeTransportCallbackWrapper", "onPreExecute fail", e2);
        }
    }

    public void onPostExecute(Request request, Response response) {
        try {
            super.onPostExecute(request, response);
        } catch (Throwable e) {
            LogCatUtil.error("SafeTransportCallbackWrapper", "super.onPostExecute fail", e);
        }
        try {
            this.a.onPostExecute(request, response);
        } catch (Throwable e2) {
            LogCatUtil.error("SafeTransportCallbackWrapper", "onPostExecute fail", e2);
        }
    }

    public void onProgressUpdate(Request request, double percent) {
        try {
            super.onProgressUpdate(request, percent);
        } catch (Throwable e) {
            LogCatUtil.error("SafeTransportCallbackWrapper", "super.onProgressUpdate fail", e);
        }
        try {
            this.a.onProgressUpdate(request, percent);
        } catch (Throwable e2) {
            LogCatUtil.error("SafeTransportCallbackWrapper", "onProgressUpdate fail", e2);
        }
    }

    public void onFailed(Request request, int code, String msg) {
        try {
            super.onFailed(request, code, msg);
        } catch (Throwable e) {
            LogCatUtil.error("SafeTransportCallbackWrapper", "super.onFailed fail", e);
        }
        try {
            this.a.onFailed(request, code, msg);
        } catch (Throwable e2) {
            LogCatUtil.error("SafeTransportCallbackWrapper", "onFailed fail", e2);
        }
    }

    public TransportCallback getWrappedTransportCallback() {
        return this.a;
    }
}
