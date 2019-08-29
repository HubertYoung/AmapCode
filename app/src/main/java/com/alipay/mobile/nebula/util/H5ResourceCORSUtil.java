package com.alipay.mobile.nebula.util;

import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider.OnConfigChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class H5ResourceCORSUtil {
    private static final List<Pattern> sWhiteListHosts = new ArrayList();

    static {
        initConfig();
    }

    private static void initConfig() {
        H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (provider != null) {
            applyConfig(provider.getConfigWithNotifyChange("h5_CORSWhiteList", new OnConfigChangeListener() {
                public final void onChange(String newValue) {
                    H5ResourceCORSUtil.applyConfig(newValue);
                }
            }));
        }
    }

    /* access modifiers changed from: private */
    public static void applyConfig(String configText) {
        if (!TextUtils.isEmpty(configText)) {
            try {
                JSONArray array = H5Utils.parseArray(configText);
                if (array != null && array.size() > 0) {
                    synchronized (sWhiteListHosts) {
                        sWhiteListHosts.clear();
                        int size = array.size();
                        for (int i = 0; i < size; i++) {
                            try {
                                Pattern pattern = H5PatternHelper.compile(array.getString(i));
                                if (pattern != null) {
                                    sWhiteListHosts.add(pattern);
                                }
                            } catch (PatternSyntaxException e) {
                                H5Log.e("H5ResourceCORSUtil", "pattern error", e);
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                H5Log.e("H5ResourceCORSUtil", "parse config error", t);
            }
        }
    }

    public static boolean needAddHeader(String resourceUrl) {
        try {
            synchronized (sWhiteListHosts) {
                if (sWhiteListHosts.size() == 0) {
                    return false;
                }
                Uri uri = H5UrlHelper.parseUrl(resourceUrl);
                if (uri != null) {
                    String host = uri.getHost();
                    if (host != null) {
                        for (Pattern matcher : sWhiteListHosts) {
                            if (matcher.matcher(host).find()) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
        } catch (Throwable th) {
            return false;
        }
    }

    public static String getCORSUrl(String pageUrl) {
        try {
            Uri uri = H5UrlHelper.parseUrl(pageUrl);
            String result = uri.getScheme() + "://" + uri.getHost();
            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if ((h5ConfigProvider == null || !"true".equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_getCORSUrlWithoutPort"))) && uri.getPort() != -1) {
                return result + ":" + uri.getPort();
            }
            return result;
        } catch (Throwable th) {
            return null;
        }
    }
}
