package com.autonavi.map.fragmentcontainer.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.map.core.MapManager;

import java.lang.ref.WeakReference;

import defpackage.brr;
import defpackage.bty;
import defpackage.buc;
import defpackage.buo;
import defpackage.cde;

public class DoNotUseTool {
    private static WeakReference<Activity> mActivity;
    private static buc mMapVirtualizationPageService;
    private static WeakReference<MapManager> sManagerWeakRef;
    private static WeakReference< cde > sSuspendManagerRef;

    @Deprecated
    public static MapManager getMapManager() {
        if (sManagerWeakRef != null) {
            return (MapManager) sManagerWeakRef.get();
        }
        return null;
    }

    @Nullable
    @Deprecated
    public static cde getSuspendManager() {
        if (sSuspendManagerRef != null) {
            return (cde) sSuspendManagerRef.get();
        }
        return null;
    }

    @Nullable
    @Deprecated
    public static bty getMapView() {
        MapManager mapManager = getMapManager();
        if (mapManager != null) {
            return mapManager.getMapView();
        }
        return null;
    }

    @Nullable
    @Deprecated
    public static Rect getPixel20Bound() {
        bty mapView = getMapView();
        if (mapView != null) {
            return mapView.H();
        }
        return null;
    }

    @Nullable
    @Deprecated
    public static GLGeoPoint getMapCenter() {
        bty mapView = getMapView();
        if (mapView != null) {
            return mapView.n();
        }
        return null;
    }

    @Deprecated
    public static void startScheme(Intent intent) {
        Activity activity = getActivity();
        if (activity != null) {
            (( brr ) activity).b(intent);
        }
    }

    @Nullable
    @Deprecated
    public static Activity getActivity() {
        if (mActivity != null) {
            return (Activity) mActivity.get();
        }
        return null;
    }

    @Nullable
    @Deprecated
    public static Context getContext() {
        if (mActivity == null) {
            return null;
        }
        return (Activity) mActivity.get();
    }

    @Deprecated
    public static void startActivity(Intent intent) {
        Activity activity = mActivity != null ? (Activity) mActivity.get() : null;
        if (activity != null) {
            activity.startActivity(intent);
        }
    }

    @Deprecated
    public static void setActivity(Activity activity) {
        if (activity != null) {
            mActivity = new WeakReference<>(activity);
        }
    }

    public static void setMapManager(@NonNull MapManager mapManager) {
        sManagerWeakRef = new WeakReference<>(mapManager);
    }

    public static void setSuspendManager(cde cde) {
        sSuspendManagerRef = new WeakReference<>(cde);
    }

    @Deprecated
    public static boolean haveStatusbarView() {
        Activity activity = getActivity();
        if (activity == null || !(activity instanceof buo )) {
            return false;
        }
        return ((buo) activity).k();
    }

    public static void setMapVirtualizationPageService(buc buc) {
        mMapVirtualizationPageService = buc;
    }

    public static buc getVirtualizationPageService() {
        return mMapVirtualizationPageService;
    }
}
