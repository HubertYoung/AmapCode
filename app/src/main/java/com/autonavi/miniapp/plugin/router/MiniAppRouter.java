package com.autonavi.miniapp.plugin.router;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.nebulaapp.H5HomeListActivity;
import com.alipay.android.nebulaapp.MiniAppUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.service.common.SchemeService;
import com.alipay.mobile.nebula.util.H5Utils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.Router;
import com.mpaas.nebula.adapter.api.H5APServiceCallbackProvider;
import java.util.List;

@Router({"applets"})
public class MiniAppRouter extends esk {
    private static final String ALIPAY_SCHEME_PROTOCOL = "alipays://";
    private static final String AMAP_SCHEME_PROTOCOL = "amapuri://";
    private static final String MINI_APP_HOST = "applets";
    private static final String MINI_APP_PREFIX = "platformapi";

    public static boolean isMiniAppScheme(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        boolean startsWith = str.startsWith("amapuri://applets/platformapi");
        if (str.startsWith("alipays://platformapi") || startsWith) {
            return true;
        }
        return false;
    }

    public static void processAlipayScheme(String str) {
        processAlipayScheme(Uri.parse(str));
    }

    public static void processAlipayScheme(Uri uri) {
        ((SchemeService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(SchemeService.class.getName())).process(uri);
    }

    public static String convertAlipaySchemeToAmapScheme(String str) {
        if (TextUtils.isEmpty(str) || !str.startsWith(ALIPAY_SCHEME_PROTOCOL)) {
            return null;
        }
        return str.replaceFirst(ALIPAY_SCHEME_PROTOCOL, "amapuri://applets/");
    }

    public static String convertAmapSchemeToAlipayScheme(String str) {
        if (TextUtils.isEmpty(str) || !str.startsWith("amapuri://applets")) {
            return null;
        }
        return str.replaceFirst("amapuri://applets/", ALIPAY_SCHEME_PROTOCOL);
    }

    public static boolean processScheme(String str) {
        if (str.startsWith("amapuri://applets")) {
            if (!"amapuri://applets/platformapi/startTestPage".equals(str) || !bno.a) {
                return processAmapMiniAppScheme(Uri.parse(str));
            }
            AMapAppGlobal.getApplication().startActivity(new Intent(AMapAppGlobal.getApplication(), H5HomeListActivity.class));
            return true;
        } else if (str.startsWith(ALIPAY_SCHEME_PROTOCOL)) {
            return processAmapMiniAppScheme(Uri.parse(convertAlipaySchemeToAmapScheme(str)));
        } else {
            return false;
        }
    }

    private static boolean processAmapMiniAppScheme(Uri uri) {
        if (uri == null) {
            return false;
        }
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments == null || pathSegments.size() != 2) {
            return false;
        }
        if ("startapp".equalsIgnoreCase(pathSegments.get(1))) {
            return handleStartApp(uri);
        }
        if (!MINI_APP_PREFIX.equalsIgnoreCase(pathSegments.get(0)) || !"apserviceresult".equalsIgnoreCase(pathSegments.get(1))) {
            return false;
        }
        return handleAPServiceResult(uri);
    }

    private static boolean handleStartApp(Uri uri) {
        Bundle bundle = new Bundle();
        String str = null;
        for (String next : uri.getQueryParameterNames()) {
            if ("appid".equalsIgnoreCase(next)) {
                str = uri.getQueryParameter(next);
            } else {
                bundle.putString(next, uri.getQueryParameter(next));
            }
        }
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            MiniAppUtil.startMiniApp(AMapAppGlobal.getApplication(), null, str, bundle);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private static boolean handleAPServiceResult(Uri uri) {
        String queryParameter = uri.getQueryParameter("amapAppId");
        String queryParameter2 = uri.getQueryParameter("amapAppPage");
        MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        if (TextUtils.isEmpty(queryParameter) || !(microApplicationContext == null || microApplicationContext.findAppById(queryParameter) == null)) {
            H5APServiceCallbackProvider h5APServiceCallbackProvider = (H5APServiceCallbackProvider) H5Utils.getProvider(H5APServiceCallbackProvider.class.getName());
            if (h5APServiceCallbackProvider == null) {
                return false;
            }
            h5APServiceCallbackProvider.handleCallback(uri);
            return true;
        }
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(queryParameter2)) {
            bundle.putString("page", queryParameter2);
        }
        MiniAppUtil.startMiniApp(AMapAppGlobal.getApplication(), null, queryParameter, bundle);
        return true;
    }

    public boolean start(ese ese) {
        if (ese == null || !MiniAppUtil.isMiniAppEnable() || !ese.a().equalsIgnoreCase(MINI_APP_HOST)) {
            return false;
        }
        processScheme(ese.toString());
        return true;
    }
}
