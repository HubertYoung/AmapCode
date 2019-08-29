package com.autonavi.minimap.ajx3.scheme;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.AjxPathLoader;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.util.AjxPageUtil;
import org.json.JSONException;
import org.json.JSONObject;

@Router({"ajx", "ajx_smallbridge", "ajx-activity"})
public class Ajx3Router extends esk {
    private static final String JS_SERVICES_PATH = "path://amap_bundle_service/src/service.js";
    static final String NEW_HOST_AJX = "ajx";
    static final String NEW_HOST_AJX_ACTIVITY = "ajx-activity";
    static final String NEW_HOST_AJX_SMALL_BRIDGE = "ajx_smallbridge";
    protected Activity mActivity;

    private boolean doOpenAjxPage(Uri uri) {
        String queryParameter = uri.getQueryParameter("path");
        String queryParameter2 = uri.getQueryParameter("data");
        String queryParameter3 = uri.getQueryParameter("__bv__");
        if (!TextUtils.isEmpty(queryParameter3)) {
            StringBuilder sb = new StringBuilder();
            sb.append(queryParameter);
            sb.append("&__bv__=");
            sb.append(queryParameter3);
            queryParameter = sb.toString();
        }
        if (pageExist(JS_SERVICES_PATH)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("path", queryParameter);
                jSONObject.put("data", queryParameter2);
                startJsRouterServices(jSONObject.toString(), "");
                return true;
            } catch (JSONException unused) {
            }
        }
        PageBundle makePageBundle = AjxPageUtil.makePageBundle(AMapAppGlobal.getApplication(), queryParameter);
        makePageBundle.putObject("jsData", queryParameter2);
        startPageForResult(getPageByConfig(uri), makePageBundle, -1);
        return true;
    }

    private void startJsRouterServices(String str, String str2) {
        Ajx.getInstance().startService("jsRouterService", JS_SERVICES_PATH, str, str2);
    }

    public boolean pageExist(String str) {
        if (TextUtils.isEmpty(str) || !str.startsWith(AjxPathLoader.DOMAIN)) {
            return false;
        }
        return AjxFileInfo.isFileExists(Ajx.getInstance().lookupLoader(str).processingPath(PictureParams.make(null, str, false)));
    }

    private Class<? extends Ajx3Page> getPageByConfig(Uri uri) {
        IRedesignPageLoader iRedesignPageLoader = (IRedesignPageLoader) ank.a(IRedesignPageLoader.class);
        if (iRedesignPageLoader != null) {
            Class<? extends Ajx3Page> loadPage = iRedesignPageLoader.loadPage(uri);
            if (loadPage != null) {
                return loadPage;
            }
        }
        return Ajx3Page.class;
    }

    public boolean start(ese ese) {
        Uri uri = ese.a;
        String host = uri.getHost();
        if (uri != null && !TextUtils.isEmpty(host)) {
            if (TextUtils.equals(host, "ajx") || TextUtils.equals(host, NEW_HOST_AJX_SMALL_BRIDGE)) {
                return doOpenAjxPage(uri);
            }
            if (TextUtils.equals(host, NEW_HOST_AJX_ACTIVITY)) {
                String queryParameter = uri.getQueryParameter("effectiveVersion");
                if (!TextUtils.isEmpty(queryParameter)) {
                    if (!Ajx3SchemeHelper.checkEffectiveVersion(queryParameter.trim(), a.a().a)) {
                        return false;
                    }
                }
                return doOpenAjxPage(uri);
            }
        }
        return false;
    }
}
