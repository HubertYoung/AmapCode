package com.alipay.mobile.tinyappcommon.provider;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.transport.h5.H5HttpUrlRequest;
import com.alipay.mobile.common.transport.h5.H5HttpUrlResponse;
import com.alipay.mobile.common.transport.h5.H5NetworkManager;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.h5plugin.TinyAppStoragePlugin;
import com.alipay.mobile.nebula.appcenter.apphandler.H5StartAppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5TinyAppProvider;
import com.alipay.mobile.nebula.startParam.H5StartParamManager;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5CookieUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.alipay.mobile.tinyappcommon.api.TinyAppStartupInterceptor;
import com.alipay.mobile.tinyappcommon.appmanager.a;
import com.alipay.mobile.tinyappcommon.embedview.H5EmbedWebView;
import com.alipay.mobile.tinyappcommon.storage.H5SharedPreferenceStorage;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.alipay.mobile.tinyappcommon.utils.net.TinyAppCookieUtils;
import com.alipay.mobile.tinyappcustom.h5plugin.H5MiniProgramNavigationPlugin;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;

public class H5TinyAppProviderImpl implements H5TinyAppProvider {
    public H5TinyAppProviderImpl() {
        H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
            public void run() {
                if (((H5ApiManager) Nebula.getProviderManager().getProvider(H5ApiManager.class.getName())) == null) {
                    H5SharedPreferenceStorage.getInstance().initLoadStorage();
                }
            }
        });
    }

    public void handlerOnAppConfig(Bundle bundle) {
        String appConfigJsonUrl = H5Utils.getString(bundle, (String) "appConfigJson");
        if (!TextUtils.isEmpty(appConfigJsonUrl) && appConfigJsonUrl.startsWith("http")) {
            if (!H5Utils.isDebug()) {
                H5Log.d("H5TinyAppProviderImpl", "handlerOnAppConfig...non-debug-mode");
                return;
            }
            String appId = H5Utils.getString(bundle, (String) "appId");
            if (TextUtils.isEmpty(appId)) {
                H5Log.d("H5TinyAppProviderImpl", "handlerOnAppConfig...app-id-null");
            } else {
                a(appConfigJsonUrl, appId);
            }
        }
    }

    private static void a(String url, String appId) {
        Header[] allHeaders;
        try {
            H5NetworkManager h5NetworkManager = new H5NetworkManager(H5Utils.getContext());
            H5HttpUrlRequest h5HttpUrlRequest = new H5HttpUrlRequest(url);
            h5HttpUrlRequest.setRequestMethod("GET");
            h5HttpUrlRequest.setReqData(null);
            h5HttpUrlRequest.setTimeout(60000);
            h5HttpUrlRequest.linkType = 2;
            String cookieStr = H5CookieUtil.getCookie(url);
            if (!TextUtils.isEmpty(cookieStr)) {
                h5HttpUrlRequest.addHeader("Cookie", cookieStr);
                H5Log.d("H5TinyAppProviderImpl", "add cookie:" + cookieStr + " , for h5HttpUrlRequest");
            }
            H5HttpUrlResponse httpUrlResponse = (H5HttpUrlResponse) h5NetworkManager.enqueue(h5HttpUrlRequest).get();
            if (httpUrlResponse == null || httpUrlResponse.getHeader() == null) {
                H5Log.d("H5TinyAppProviderImpl", "downloadAppConfigJson...response error");
                return;
            }
            for (Header header : httpUrlResponse.getHeader().getAllHeaders()) {
                String headerName = header.getName();
                if (headerName != null) {
                    String headerValue = header.getValue();
                    H5Log.d("H5TinyAppProviderImpl", "name:" + headerName + " - value:" + headerValue);
                    if (headerName.equalsIgnoreCase("set-cookie")) {
                        H5CookieUtil.setCookie(url, headerValue);
                        H5Log.d("H5TinyAppProviderImpl", "insert cookie:" + headerValue + " , for " + url);
                    }
                }
            }
            H5StartParamManager.getInstance().put(appId, TinyappUtils.toByteArray(httpUrlResponse.getInputStream(), false));
            H5Log.d("H5TinyAppProviderImpl", "downloadAppConfigJson...put bytes");
        } catch (Exception e) {
            H5Log.e((String) "H5TinyAppProviderImpl", "downloadAppConfigJson...e=" + e);
        }
    }

    public void handlerOnWorkLog(String s, Object log) {
        a(log);
    }

    public JSONObject handlerOnShareData(H5Page h5Page) {
        if (h5Page == null) {
            return null;
        }
        JSONObject param = new JSONObject();
        if (TinyAppService.get().getMixActionService() != null) {
            param.put((String) "useNativeShare", (Object) Boolean.valueOf(TinyAppService.get().getMixActionService().isUseNativeShareSwitch()));
        }
        Object o = h5Page.getExtra(H5EmbedWebView.WEB_VIEW_PAGE_TAG);
        if (!(o instanceof H5Page)) {
            return param;
        }
        param.put((String) "webViewUrl", (Object) ((H5Page) o).getWebView().getUrl());
        return param;
    }

    public void handlerOnPushWindowParam(Bundle bundle) {
        if (bundle != null && bundle.containsKey("pullRefresh")) {
            bundle.remove("pullRefresh");
            H5Log.d("H5TinyAppProviderImpl", "handlerOnPushWindowParam remove pullRefresh");
        }
    }

    private static void a(final Object result) {
        if (result != null && (result instanceof HashMap)) {
            final String url = (String) ((HashMap) result).get("url");
            if ("OnReportException".equals((String) ((HashMap) result).get("event"))) {
                H5Log.e((String) "H5TinyAppProviderImpl", "reportErrorOnRender jsError " + result);
                H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                if (h5ConfigProvider != null && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_reportErrorOnRender"))) {
                    H5Utils.runOnMain(new Runnable() {
                        public final void run() {
                            H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
                            if (h5Service != null) {
                                H5Page h5Page = h5Service.getTopH5Page();
                                if (h5Page != null && h5Page.getWebView() != null) {
                                    Uri workUri = H5UrlHelper.parseUrl(url);
                                    Uri pageUri = H5UrlHelper.parseUrl(h5Page.getUrl());
                                    if (workUri != null && pageUri != null) {
                                        H5Log.d("H5TinyAppProviderImpl", "workUri " + workUri.getHost() + " pageUri:" + pageUri.getHost());
                                        if (TextUtils.equals(workUri.getHost(), pageUri.getHost())) {
                                            String error = "nebula work error：" + result;
                                            String tips = "console.error(\"" + error + "\")";
                                            String onError = "window.onerror(\"" + error + "\")";
                                            if (H5Utils.isDebug() || H5Utils.isInTinyProcess()) {
                                                h5Page.getWebView().evaluateJavascript(tips, null);
                                                h5Page.getWebView().evaluateJavascript(onError, null);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    public Bundle handlerAppResume(H5Page h5Page, Bundle bundle) {
        TinyAppStartupInterceptor interceptor = TinyAppService.get().getTinyAppStartupInterceptor();
        return interceptor == null ? bundle : interceptor.handlerAppResume(h5Page, bundle);
    }

    public Bundle handlerStartupParams(H5Page h5Page, Bundle bundle) {
        TinyAppStartupInterceptor interceptor = TinyAppService.get().getTinyAppStartupInterceptor();
        return interceptor == null ? bundle : interceptor.handlerStartupParams(h5Page, bundle);
    }

    public void handlerStartParamsReady(Context context, Bundle bundle) {
        TinyAppStartupInterceptor interceptor = TinyAppService.get().getTinyAppStartupInterceptor();
        if (interceptor != null) {
            interceptor.handlerStartParamsReady(context, bundle);
        }
    }

    public void startSyncApp(H5StartAppInfo startAppInfo, AppInfo appInfo, Bundle copyParam, String currentNbVersion, H5AppProvider h5AppProvider) {
        a.a(startAppInfo, appInfo, copyParam, currentNbVersion, h5AppProvider);
    }

    public void handlerH5EventToBundleForIpc(String action, H5Event h5Event, Bundle bundle) {
        if (h5Event != null && bundle != null) {
            H5Page h5Page = h5Event.getH5page();
            if (h5Page == null) {
                return;
            }
            if (H5MiniProgramNavigationPlugin.NAVIGATE_TO_MINI_PROGRAM.equals(action)) {
                String nbsn = H5Utils.getString(h5Page.getParams(), (String) "nbsn");
                if (!TextUtils.isEmpty(nbsn)) {
                    bundle.putString("nbsn", nbsn);
                }
            } else if (TinyAppStoragePlugin.SET_TINY_LOCAL_STORAGE.equals(action) || TinyAppStoragePlugin.GET_TINY_LOCAL_STORAGE.equals(action) || TinyAppStoragePlugin.REMOVE_TINY_LOCAL_STORAGE.equals(action) || TinyAppStoragePlugin.CLEAR_TINY_LOCAL_STORAGE.equals(action) || TinyAppStoragePlugin.GET_TINY_LOCAL_STORAGE_INFO.equals(action)) {
                String embed = H5Utils.getString(h5Page.getParams(), (String) "MINI-PROGRAM-WEB-VIEW-TAG");
                if (!TextUtils.isEmpty(embed)) {
                    bundle.putString("embed_webview_flag", embed);
                }
            }
        }
    }

    public void handlerBundleToH5EventForIpc(String action, Bundle bundle, H5Event h5Event, H5Page h5Page) {
        if (h5Event != null && bundle != null && h5Page != null) {
            Bundle pageParams = h5Page.getParams();
            if (pageParams == null) {
                return;
            }
            if (H5MiniProgramNavigationPlugin.NAVIGATE_TO_MINI_PROGRAM.equals(action)) {
                String nbsn = H5Utils.getString(bundle, (String) "nbsn");
                if (!TextUtils.isEmpty(nbsn)) {
                    pageParams.putString("nbsn", nbsn);
                }
            } else if (TinyAppStoragePlugin.SET_TINY_LOCAL_STORAGE.equals(action) || TinyAppStoragePlugin.GET_TINY_LOCAL_STORAGE.equals(action) || TinyAppStoragePlugin.REMOVE_TINY_LOCAL_STORAGE.equals(action) || TinyAppStoragePlugin.CLEAR_TINY_LOCAL_STORAGE.equals(action) || TinyAppStoragePlugin.GET_TINY_LOCAL_STORAGE_INFO.equals(action)) {
                String embed = H5Utils.getString(bundle, (String) "embed_webview_flag");
                if (!TextUtils.isEmpty(embed)) {
                    pageParams.putString("embed_webview_flag", embed);
                } else {
                    pageParams.remove("embed_webview_flag");
                }
            }
        }
    }

    public String getCookie(Bundle startParams, String url) {
        return TinyAppCookieUtils.getCookie(startParams, url);
    }

    public void setCookie(Bundle startParams, String url, String cookieValue) {
        TinyAppCookieUtils.setCookie(startParams, url, cookieValue);
    }

    public String getTemplateAppId(String instanceAppId, Bundle startupParams) {
        return TemplateTinyApp.getInstance().getTemplateAppId(instanceAppId, startupParams);
    }

    public void mergeTemplateConfigIfNeed(String appId, Bundle startupParams, Map<String, byte[]> resPkg) {
        TemplateTinyApp.getInstance().dynamicMergeTemplateConfig(appId, startupParams, resPkg);
    }
}
