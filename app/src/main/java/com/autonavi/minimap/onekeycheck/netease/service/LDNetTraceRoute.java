package com.autonavi.minimap.onekeycheck.netease.service;

import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public class LDNetTraceRoute {
    private static LDNetTraceRoute instance;
    static boolean loaded = true;
    private boolean isCTrace = true;
    private a mListener;

    public interface a {
        void OnNetTraceFinished();

        void OnNetTraceUpdated(dsr dsr);
    }

    @KeepName
    public native void startJNICTraceRoute(String str);

    private LDNetTraceRoute() {
    }

    public static LDNetTraceRoute getInstance() {
        if (instance == null) {
            instance = new LDNetTraceRoute();
        }
        return instance;
    }

    public void initListenter(a aVar) {
        this.mListener = aVar;
    }

    public void setIsCTrace(boolean z) {
        this.isCTrace = z;
    }

    public void startTraceRoute(String str) {
        if (!this.isCTrace || !loaded) {
            if (this.mListener != null) {
                this.mListener.OnNetTraceFinished();
            }
            return;
        }
        try {
            startJNICTraceRoute(str);
        } catch (UnsatisfiedLinkError unused) {
            eao.b("LDNetTraceRoute", "traceRoute  fail");
        }
    }

    public void resetInstance() {
        if (instance != null) {
            instance = null;
        }
    }

    static {
        try {
            System.loadLibrary("tracepath");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void printTraceInfo(String str) {
        if (this.mListener != null) {
            this.mListener.OnNetTraceUpdated(new dsr("trace_jni_log", str));
        }
    }
}
