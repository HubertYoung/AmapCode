package com.alipay.mobile.framework.service.common;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.MpaasPropertiesUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class SchemeService extends ExternalService {
    public static final String DT_LOG_MONITOR = "dtLogMonitor";
    public static final String DT_LOG_MONITOR_TIME = "dtLogMonitorTime";
    public static final String KEY_H5_URL = "version.update.h5url";
    public static final String SCHEME_INNER_SOURCE = "schemeInnerSource";
    public static final String SCHEME_REVEAL = "alipays";
    public static boolean allSkipAuth = false;
    public static String h5Url = "";

    public interface SchemeHandler {
        boolean canHandle(String str);

        int getPriority();

        boolean handle(Uri uri);
    }

    public abstract void cleanTagId();

    public abstract void extractTagId(Uri uri);

    public abstract String getAppId(Uri uri);

    public abstract String getLastScheme();

    public abstract String getLastTagId();

    public abstract Bundle getParams(Uri uri);

    public abstract String getSchemeParam(String str);

    public abstract String getTagByAppId(String str);

    public abstract boolean isSchemeInvoke();

    public abstract boolean isSupportScheme(Uri uri);

    public abstract int process(Uri uri);

    public abstract int process(Uri uri, String str);

    public abstract int process(Uri uri, boolean z);

    public abstract void registerSchemeHandler(SchemeHandler schemeHandler);

    public abstract void saveSchemeParam(String str, String str2);

    public abstract void setExternData(Bundle bundle);

    public abstract void setH5HoldListener(H5HoldListener h5HoldListener);

    public abstract void unRegisterSchemeHandler(SchemeHandler schemeHandler);

    public static String getH5Url() {
        try {
            String h5ErrorUrl = MpaasPropertiesUtil.getKeyFromManifest(LauncherApplicationAgent.getInstance().getApplicationContext().getApplicationContext(), KEY_H5_URL);
            if (!TextUtils.isEmpty(h5ErrorUrl)) {
                h5Url = h5ErrorUrl;
            }
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) "SchemeService", e);
        }
        return h5Url;
    }
}
