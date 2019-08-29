package com.autonavi.map.fragmentcontainer.page.utils;

import java.util.HashMap;
import java.util.Map.Entry;

import defpackage.bid;

public final class ActivityStateDispatch {
    public final void onActivityStartCallback() {
        HashMap< bid, IActvitiyStateListener> actvitiyListenerList = AMapPageUtil.getActvitiyListenerList();
        if (actvitiyListenerList.size() > 0) {
            for (Entry next : actvitiyListenerList.entrySet()) {
                next.getKey();
                Object value = next.getValue();
                if (value != null && (value instanceof IActvitiyStateListener)) {
                    IActvitiyStateListener iActvitiyStateListener = (IActvitiyStateListener) value;
                    if (iActvitiyStateListener != null) {
                        iActvitiyStateListener.onActivityStart();
                    }
                }
            }
        }
    }

    public final void onActivityResumeCallback() {
        HashMap<bid, IActvitiyStateListener> actvitiyListenerList = AMapPageUtil.getActvitiyListenerList();
        if (actvitiyListenerList.size() > 0) {
            for (Entry next : actvitiyListenerList.entrySet()) {
                next.getKey();
                Object value = next.getValue();
                if (value != null && (value instanceof IActvitiyStateListener)) {
                    IActvitiyStateListener iActvitiyStateListener = (IActvitiyStateListener) value;
                    if (iActvitiyStateListener != null) {
                        iActvitiyStateListener.onActivityResume();
                    }
                }
            }
        }
    }

    public final void onActivityPauseCallback() {
        HashMap<bid, IActvitiyStateListener> actvitiyListenerList = AMapPageUtil.getActvitiyListenerList();
        if (actvitiyListenerList.size() > 0) {
            for (Entry next : actvitiyListenerList.entrySet()) {
                next.getKey();
                Object value = next.getValue();
                if (value != null && (value instanceof IActvitiyStateListener)) {
                    IActvitiyStateListener iActvitiyStateListener = (IActvitiyStateListener) value;
                    if (iActvitiyStateListener != null) {
                        iActvitiyStateListener.onActivityPause();
                    }
                }
            }
        }
    }

    public final void onActivityStopCallback() {
        HashMap<bid, IActvitiyStateListener> actvitiyListenerList = AMapPageUtil.getActvitiyListenerList();
        if (actvitiyListenerList.size() > 0) {
            for (Entry next : actvitiyListenerList.entrySet()) {
                next.getKey();
                Object value = next.getValue();
                if (value != null && (value instanceof IActvitiyStateListener)) {
                    IActvitiyStateListener iActvitiyStateListener = (IActvitiyStateListener) value;
                    if (iActvitiyStateListener != null) {
                        iActvitiyStateListener.onActivityStop();
                    }
                }
            }
        }
    }
}
