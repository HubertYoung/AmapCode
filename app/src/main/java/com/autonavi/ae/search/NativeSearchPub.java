package com.autonavi.ae.search;

import com.autonavi.ae.bl.search.BLSearchEngine;
import com.autonavi.ae.search.model.GADAREAEXTRAINFO;

public class NativeSearchPub {
    public static synchronized int nativeCreateSearchpub() {
        synchronized (NativeSearchPub.class) {
        }
        return 0;
    }

    protected static synchronized int nativeDestroy() {
        synchronized (NativeSearchPub.class) {
        }
        return 2;
    }

    public static synchronized String GetVersion() {
        synchronized (NativeSearchPub.class) {
        }
        return "";
    }

    public static synchronized String GetDataVersion(int i) {
        synchronized (NativeSearchPub.class) {
        }
        return "";
    }

    public static synchronized boolean DbExists(int i) {
        synchronized (NativeSearchPub.class) {
        }
        return false;
    }

    public static synchronized GADAREAEXTRAINFO GetAdareaInfo(int i) {
        synchronized (NativeSearchPub.class) {
        }
        return null;
    }

    public static synchronized void SetDeps() {
        synchronized (NativeSearchPub.class) {
            BLSearchEngine.setDeps();
        }
    }
}
