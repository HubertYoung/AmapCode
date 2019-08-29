package com.alipay.mobile.h5container.api;

import android.app.Activity;
import android.content.Context;
import com.alibaba.fastjson.JSONObject;

public class H5Event {
    public static final String FROM_WORK = "fromWork";
    public static final String TYPE_CALL = "call";
    public static final String TYPE_CALL_BACK = "callback";
    /* access modifiers changed from: private */
    public String action;
    /* access modifiers changed from: private */
    public H5CallBack callBack;
    /* access modifiers changed from: private */
    public boolean canceled;
    /* access modifiers changed from: private */
    public boolean dispatcherOnWorkerThread;
    /* access modifiers changed from: private */
    public String eventSource;
    /* access modifiers changed from: private */
    public Object extra;
    /* access modifiers changed from: private */
    public String id;
    /* access modifiers changed from: private */
    public boolean keepCallback;
    /* access modifiers changed from: private */
    public JSONObject param;
    /* access modifiers changed from: private */
    public H5CoreNode target;
    /* access modifiers changed from: private */
    public String type;

    public static class Builder {
        /* access modifiers changed from: private */
        public String action;
        /* access modifiers changed from: private */
        public H5CallBack callBack;
        /* access modifiers changed from: private */
        public boolean canceled;
        /* access modifiers changed from: private */
        public boolean dispatcherOnWorkerThread;
        /* access modifiers changed from: private */
        public String eventSource;
        /* access modifiers changed from: private */
        public Object extra;
        /* access modifiers changed from: private */
        public String id = ("native_" + System.currentTimeMillis());
        /* access modifiers changed from: private */
        public boolean keepCallback = false;
        /* access modifiers changed from: private */
        public JSONObject param;
        /* access modifiers changed from: private */
        public H5CoreNode target;
        /* access modifiers changed from: private */
        public String type = "call";

        public Builder() {
        }

        public Builder(H5Event event) {
            this.action = event.action;
            this.id = event.id;
            this.canceled = event.canceled;
            this.callBack = event.callBack;
            this.type = event.type;
            this.keepCallback = event.keepCallback;
            this.param = event.param;
            this.target = event.target;
            this.extra = event.extra;
            this.dispatcherOnWorkerThread = event.dispatcherOnWorkerThread;
            this.eventSource = event.eventSource;
        }

        public Builder action(String action2) {
            this.action = action2;
            return this;
        }

        public Builder target(H5CoreNode target2) {
            this.target = target2;
            return this;
        }

        public Builder id(String id2) {
            this.id = id2;
            return this;
        }

        public Builder param(JSONObject param2) {
            this.param = param2;
            return this;
        }

        public Builder type(String type2) {
            this.type = type2;
            return this;
        }

        public Builder callback(H5CallBack callBack2) {
            this.callBack = callBack2;
            return this;
        }

        public Builder cancel(boolean cancel) {
            this.canceled = cancel;
            return this;
        }

        public Builder keepCallback(boolean keepCallBack) {
            this.keepCallback = keepCallBack;
            return this;
        }

        public Builder dispatcherOnWorkerThread(boolean dispatcherOnWorkerThread2) {
            this.dispatcherOnWorkerThread = dispatcherOnWorkerThread2;
            return this;
        }

        public Builder eventSource(String eventSource2) {
            this.eventSource = eventSource2;
            return this;
        }

        public Builder extra(Object extra2) {
            this.extra = extra2;
            return this;
        }

        public H5Event build() {
            return new H5Event(this);
        }
    }

    public enum Error {
        NONE,
        NOT_FOUND,
        INVALID_PARAM,
        UNKNOWN_ERROR,
        FORBIDDEN
    }

    private H5Event(Builder builder) {
        this.action = builder.action;
        this.id = builder.id;
        this.canceled = builder.canceled;
        this.callBack = builder.callBack;
        this.type = builder.type;
        this.keepCallback = builder.keepCallback;
        this.param = builder.param;
        this.target = builder.target;
        this.extra = builder.extra;
        this.dispatcherOnWorkerThread = builder.dispatcherOnWorkerThread;
        this.eventSource = builder.eventSource;
    }

    public String getEventSource() {
        return this.eventSource;
    }

    public void setEventSource(String eventSource2) {
        this.eventSource = eventSource2;
    }

    public boolean isDispatcherOnWorkerThread() {
        return this.dispatcherOnWorkerThread;
    }

    public H5CallBack getCallBack() {
        return this.callBack;
    }

    public void setCallBack(H5CallBack callBack2) {
        this.callBack = callBack2;
    }

    public final String getAction() {
        return this.action;
    }

    public void setAction(String action2) {
        this.action = action2;
    }

    public final boolean isCanceled() {
        return this.canceled;
    }

    public final void cancel() {
        this.canceled = true;
    }

    public final H5CoreNode getTarget() {
        return this.target;
    }

    public final void setTarget(H5CoreNode target2) {
        this.target = target2;
    }

    public final Activity getActivity() {
        if (!(this.target instanceof H5Page)) {
            return null;
        }
        H5Page page = (H5Page) this.target;
        if (page.getContext() == null) {
            return null;
        }
        Context context = page.getContext().getContext();
        if (!(context instanceof Activity)) {
            return null;
        }
        return (Activity) context;
    }

    public final H5Page getH5page() {
        if (!(this.target instanceof H5Page)) {
            return null;
        }
        return (H5Page) this.target;
    }

    public final String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public JSONObject getParam() {
        return this.param;
    }

    public void setParam(JSONObject param2) {
        this.param = param2;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public boolean isKeepCallback() {
        return this.keepCallback;
    }

    public Object getExtra() {
        return this.extra;
    }

    public void setExtra(Object extra2) {
        this.extra = extra2;
    }
}
