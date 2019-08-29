package com.alipay.mobile.h5container.api;

import android.view.LayoutInflater;
import android.view.View;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ui.ActivityHelper;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.HashMap;

public class H5ViewCache {
    /* access modifiers changed from: private */
    public static int[] VIEW_ID_TO_CACHE = {R.layout.h5_loading_fragment, R.layout.h5_web_content};
    /* access modifiers changed from: private */
    public static HashMap<Integer, View> sViewCache;

    public static void initViewCache() {
        if (sViewCache == null) {
            H5Utils.getExecutor(H5ThreadType.NORMAL).execute(new Runnable() {
                public final void run() {
                    int[] access$100;
                    try {
                        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                        if ((h5ConfigProvider == null || !"NO".equals(h5ConfigProvider.getConfig("h5_openViewCache"))) && ActivityHelper.isBackgroundRunning()) {
                            H5ViewCache.sViewCache = new HashMap();
                            LayoutInflater layoutInflater = LayoutInflater.from(LauncherApplicationAgent.getInstance().getApplicationContext());
                            for (int id : H5ViewCache.VIEW_ID_TO_CACHE) {
                                H5ViewCache.sViewCache.put(Integer.valueOf(id), layoutInflater.inflate(id, null));
                            }
                        }
                    } catch (Throwable e) {
                        H5ViewCache.sViewCache = null;
                        H5Log.e((String) "H5ViewCache, pre inflate view error!", e);
                    }
                }
            });
        }
    }

    public static View getCachedViewById(int viewId) {
        if (sViewCache == null) {
            return null;
        }
        return sViewCache.remove(Integer.valueOf(viewId));
    }
}
