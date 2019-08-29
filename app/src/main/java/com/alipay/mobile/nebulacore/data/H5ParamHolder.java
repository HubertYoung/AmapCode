package com.alipay.mobile.nebulacore.data;

import android.os.Bundle;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5ParamHolder {
    public static final String TAG = "H5ParamHolder";
    private static Map<String, PageParams> a = new ConcurrentHashMap();

    public interface PageParamListener {
        void onPageParam(Bundle bundle);
    }

    private static class PageParams {
        public Bundle bundle;
        public PageParamListener listener;
        public long time;

        private PageParams() {
        }

        /* synthetic */ PageParams(byte b) {
            this();
        }
    }

    public static synchronized void addPageParam(String pageIndex) {
        synchronized (H5ParamHolder.class) {
            H5Log.d(TAG, "addPageParam ConcurrentHashMap:" + pageIndex);
            a.put(pageIndex, new PageParams(0));
        }
    }

    public static synchronized boolean hasPageParam(String pageIndex) {
        boolean result;
        synchronized (H5ParamHolder.class) {
            try {
                result = a.containsKey(pageIndex);
                H5Log.d(TAG, pageIndex + " hasPageParam " + result);
            }
        }
        return result;
    }

    public static synchronized void deliveryPageParam(String pageIndex, Bundle bundle) {
        synchronized (H5ParamHolder.class) {
            H5Utils.handleTinyAppKeyEvent((String) "package_prepare", (String) "H5ParamHolder.deliveryPageParam()");
            PageParams pageParams = a.get(pageIndex);
            if (pageParams == null) {
                H5Log.e((String) TAG, (String) "！！！ pageParams == null");
            } else if (pageParams.listener != null) {
                H5Log.d(TAG, "deliveryPageParam pageParams.listener!=null " + pageIndex);
                a.remove(pageIndex);
                pageParams.listener.onPageParam(bundle);
            } else {
                pageParams.bundle = bundle;
                H5Log.d(TAG, "deliveryPageParam pageParams.listener==null " + pageIndex);
            }
        }
    }

    public static synchronized void retrievePageParam(String pageIndex, PageParamListener listener) {
        synchronized (H5ParamHolder.class) {
            PageParams pageParams = a.get(pageIndex);
            H5Log.d(TAG, "retrievePageParam " + pageIndex + Token.SEPARATOR + listener + " pageParams:" + pageParams);
            if (!(pageParams == null || listener == null)) {
                if (pageParams.bundle == null) {
                    pageParams.listener = listener;
                    H5Log.d(TAG, "pageParams.bundle==null");
                } else {
                    a.remove(pageIndex);
                    listener.onPageParam(pageParams.bundle);
                }
            }
        }
    }
}
