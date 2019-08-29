package com.alipay.mobile.mpaasadapter;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ActivityApplication;
import com.alipay.mobile.framework.service.common.H5HoldListener;
import com.alipay.mobile.framework.service.common.SchemeService;
import com.alipay.mobile.framework.service.common.SchemeService.SchemeHandler;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SchemeServiceImpl extends SchemeService {
    private CopyOnWriteArrayList<SchemeHandler> a = new CopyOnWriteArrayList<>();
    private Comparator<SchemeHandler> b = new Comparator<SchemeHandler>() {
        public int compare(SchemeHandler lhs, SchemeHandler rhs) {
            return lhs.getPriority() - rhs.getPriority();
        }
    };

    public void registerSchemeHandler(SchemeHandler handler) {
        if (!this.a.contains(handler)) {
            this.a.add(handler);
            Collections.sort(this.a, this.b);
        }
    }

    public void unRegisterSchemeHandler(SchemeHandler handler) {
        if (this.a.contains(handler)) {
            this.a.remove(handler);
        }
    }

    public int process(Uri uri) {
        if (uri == null) {
            return 5;
        }
        Iterator<SchemeHandler> it = this.a.iterator();
        while (it.hasNext()) {
            SchemeHandler handler = it.next();
            if (handler.canHandle(uri.getScheme())) {
                if (handler.handle(uri)) {
                    return 0;
                }
                return 5;
            }
        }
        String scheme = uri.getScheme();
        if (TextUtils.isEmpty(scheme) || !scheme.startsWith(BehavorReporter.PROVIDE_BY_ALIPAY)) {
            return 1;
        }
        List paths = uri.getPathSegments();
        if (paths == null || paths.size() != 1) {
            return 1;
        }
        if (!"startapp".equalsIgnoreCase(paths.get(0))) {
            return 1;
        }
        String appId = null;
        Bundle params = new Bundle();
        for (String key : uri.getQueryParameterNames()) {
            if ("appid".equalsIgnoreCase(key)) {
                appId = uri.getQueryParameter(key);
            } else {
                params.putString(key, uri.getQueryParameter(key));
            }
        }
        if (TextUtils.isEmpty(appId)) {
            return 2;
        }
        String sourceAppId = null;
        try {
            ActivityApplication activityApplication = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopApplication();
            if (activityApplication != null) {
                sourceAppId = activityApplication.getAppId();
            }
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(sourceAppId, appId, params);
            return 0;
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) "SchemeServiceImpl", (Throwable) e);
            return 4;
        }
    }

    public int process(Uri uri, boolean isOutside) {
        return process(uri);
    }

    public int process(Uri uri, String schemeInnerSource) {
        return process(uri);
    }

    public String getTagByAppId(String appId) {
        return null;
    }

    public String getLastTagId() {
        return null;
    }

    public String getLastScheme() {
        return null;
    }

    public void cleanTagId() {
    }

    public void extractTagId(Uri uri) {
    }

    public String getAppId(Uri uri) {
        if (uri == null) {
            return null;
        }
        String id = uri.getQueryParameter("appid");
        if (TextUtils.isEmpty(id)) {
            return uri.getQueryParameter("appId");
        }
        return id;
    }

    public boolean isSchemeInvoke() {
        return false;
    }

    public void setExternData(Bundle bundle) {
    }

    public void setH5HoldListener(H5HoldListener h5HoldListener) {
    }

    public void saveSchemeParam(String key, String value) {
    }

    public String getSchemeParam(String key) {
        return null;
    }

    public Bundle getParams(Uri uri) {
        if (uri == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        for (String key : uri.getQueryParameterNames()) {
            bundle.putString(key, uri.getQueryParameter(key));
        }
        return bundle;
    }

    public boolean isSupportScheme(Uri uri) {
        if (uri == null || uri.getScheme() == null) {
            return false;
        }
        return uri.getScheme().startsWith(BehavorReporter.PROVIDE_BY_ALIPAY);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }
}
