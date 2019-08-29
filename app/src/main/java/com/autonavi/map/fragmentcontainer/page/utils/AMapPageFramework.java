package com.autonavi.map.fragmentcontainer.page.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.HashMap;

public class AMapPageFramework {
    @Nullable
    @Deprecated
    public static bid getPageContext() {
        return AMapPageUtil.getPageContext();
    }

    @Nullable
    public static Context getAppContext() {
        return AMapPageUtil.getAppContext();
    }

    public static void setPageStateListener(bid bid, IPageStateListener iPageStateListener) {
        AMapPageUtil.setPageStateListener(bid, iPageStateListener);
    }

    public static void removePageStateListener(bid bid) {
        AMapPageUtil.removePageStateListener(bid);
    }

    public static void setActivityStateListener(bid bid, IActvitiyStateListener iActvitiyStateListener) {
        AMapPageUtil.setActivityStateListener(bid, iActvitiyStateListener);
    }

    @NonNull
    public static HashMap<bid, IActvitiyStateListener> getActvitiyListenerList() {
        return AMapPageUtil.getActvitiyListenerList();
    }

    public static void removeActivityStateListener(bid bid) {
        AMapPageUtil.removeActivityStateListener(bid);
    }
}
