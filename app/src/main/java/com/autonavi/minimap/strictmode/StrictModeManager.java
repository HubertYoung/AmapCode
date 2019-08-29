package com.autonavi.minimap.strictmode;

import android.app.Activity;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.os.StrictMode.VmPolicy.Builder;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageButton;
import dalvik.system.BlockGuard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class StrictModeManager {
    private static final String TAG = "StrictModeManager";
    private static volatile StrictModeManager mInstances;
    public List<String> exceptionList = new ArrayList();
    ImageButton listButton;

    private void showIcon(Activity activity) {
    }

    private void showListView() {
    }

    public final void closeStrictMode() {
    }

    public static StrictModeManager getInstances() {
        if (mInstances == null) {
            synchronized (StrictModeManager.class) {
                if (mInstances == null) {
                    mInstances = new StrictModeManager();
                }
            }
        }
        return mInstances;
    }

    public final void openStrictMode(Activity activity) {
        StrictMode.setVmPolicy(new Builder().detectAll().penaltyLog().build());
        StrictMode.setThreadPolicy(new ThreadPolicy.Builder().detectAll().penaltyLog().build());
        BlockGuard.setThreadPolicy(new MyCustomAndroidBlockGuardPolicy());
    }

    private void refreshDate(ArrayList<HashMap<String, Object>> arrayList) {
        arrayList.clear();
        if (this.exceptionList != null && !this.exceptionList.isEmpty()) {
            for (String put : this.exceptionList) {
                HashMap hashMap = new HashMap();
                hashMap.put("value", put);
                arrayList.add(hashMap);
            }
        }
    }

    private int getPixelsFromDp(WindowManager windowManager, int i) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return (i * displayMetrics.densityDpi) / 160;
    }
}
