package com.alipay.mobile.nebulauc.impl.network;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulauc.util.H5ConfigUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.uc.webview.export.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class H5PreConnectManager {
    private static final String TAG = "H5PreConnectManager";
    private static H5PreConnectManager sInstance;
    private static Boolean sIsPreConnectEnabled = null;
    private static Boolean sIsPreRequestEnabled = null;
    private static Map<H5Page, String> sPageUrlMap = new HashMap();
    private static int sPreConnectTimeout = 30;
    private static ConcurrentHashMap<String, Long> sPreConnectUrlList = null;
    private static JSONObject sPreRequestConfigHeaders = null;
    private static List<String> sPreRequestWhiteList = null;
    private static Map<String, AlipayRequest> sRequestMap = new HashMap();

    public static boolean isPreDownloadEnabled() {
        if (H5Utils.isDebug() && H5DevConfig.getBooleanConfig("h5_disable_uc_predownload", false)) {
            return false;
        }
        H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (provider == null) {
            return false;
        }
        if (sIsPreRequestEnabled == null) {
            JSONObject jsonObject = H5Utils.parseObject(provider.getConfigWithProcessCache("h5_enablePreConnectMainFrame"));
            if (jsonObject != null) {
                sIsPreRequestEnabled = Boolean.valueOf("YES".equalsIgnoreCase(H5Utils.getString(jsonObject, (String) "enabled")));
                if (sIsPreRequestEnabled.booleanValue()) {
                    JSONArray whiteListArray = H5Utils.getJSONArray(jsonObject, H5PermissionManager.whitelist, null);
                    if (whiteListArray != null) {
                        sPreRequestWhiteList = new ArrayList();
                        int size = whiteListArray.size();
                        for (int i = 0; i < size; i++) {
                            sPreRequestWhiteList.add(whiteListArray.getString(i));
                        }
                    }
                    sPreRequestConfigHeaders = H5Utils.getJSONObject(jsonObject, "headers", null);
                }
            }
        }
        if (sIsPreRequestEnabled == null) {
            sIsPreRequestEnabled = Boolean.valueOf(false);
        }
        return sIsPreRequestEnabled.booleanValue();
    }

    public static boolean isPreConnectEnabled() {
        H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (provider == null) {
            return false;
        }
        if (sIsPreConnectEnabled == null) {
            JSONObject object = H5Utils.parseObject(provider.getConfigWithProcessCache("h5_enablePreConnect"));
            if (object != null) {
                boolean enable = H5Utils.getBoolean(object, (String) "enable", false);
                if (enable) {
                    sPreConnectTimeout = H5Utils.getInt(object, (String) "timeout", 60);
                    sPreConnectUrlList = new ConcurrentHashMap<>(2);
                    JSONArray urlArray = H5Utils.getJSONArray(object, "urls", null);
                    for (int i = 0; i < urlArray.size(); i++) {
                        sPreConnectUrlList.put((String) urlArray.get(i), Long.valueOf(0));
                    }
                }
                sIsPreConnectEnabled = Boolean.valueOf(enable);
            }
        }
        if (sIsPreConnectEnabled == null) {
            sIsPreConnectEnabled = Boolean.valueOf(false);
        }
        return sIsPreConnectEnabled.booleanValue();
    }

    static void refreshPreConnect(String connectUrl) {
        if (connectUrl != null && sPreConnectUrlList != null) {
            for (Entry entry : sPreConnectUrlList.entrySet()) {
                if (connectUrl.startsWith((String) entry.getKey())) {
                    entry.setValue(Long.valueOf(System.currentTimeMillis()));
                }
            }
        }
    }

    public static H5PreConnectManager getInstance() {
        H5PreConnectManager h5PreConnectManager;
        synchronized (H5PreConnectManager.class) {
            try {
                if (sInstance == null) {
                    sInstance = new H5PreConnectManager();
                }
                h5PreConnectManager = sInstance;
            }
        }
        return h5PreConnectManager;
    }

    private Map<String, String> getHeaders(String url, H5Page h5Page) {
        Map headerMap = new HashMap();
        if (!(h5Page == null || h5Page.getWebView() == null || h5Page.getWebView().getSettings() == null)) {
            String ua = h5Page.getWebView().getSettings().getUserAgentString();
            if (!TextUtils.isEmpty(ua)) {
                headerMap.put(H5AppHttpRequest.HEADER_UA, ua);
            }
        }
        CookieManager manager = CookieManager.getInstance();
        if (manager != null) {
            String cookie = manager.getCookie(url);
            if (!TextUtils.isEmpty(cookie)) {
                headerMap.put("Cookie", cookie);
            }
        }
        headerMap.put(H5AppHttpRequest.HEADER_ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8,UC/163");
        headerMap.put("Accept-Encoding", "gzip, deflate, br");
        headerMap.put("Accept-Language", "zh-CN,en-US;q=0.8");
        if (sPreRequestConfigHeaders != null && sPreRequestConfigHeaders.size() > 0) {
            for (String key : sPreRequestConfigHeaders.keySet()) {
                String value = sPreRequestConfigHeaders.getString(key);
                headerMap.put(key, sPreRequestConfigHeaders.getString(key));
                H5Log.d(TAG, "put config header: " + key + Token.SEPARATOR + value);
            }
        }
        return headerMap;
    }

    private Map<String, String> getUCHeaders() {
        return new HashMap();
    }

    public void preRequest(String mainDocUrl, H5Page h5Page) {
        if (sPreRequestWhiteList != null) {
            for (String url : sPreRequestWhiteList) {
                if (mainDocUrl.startsWith(url)) {
                    H5Log.d(TAG, "not pre request by white list");
                    return;
                }
            }
        }
        String url2 = H5UrlHelper.stripAnchor(mainDocUrl);
        H5Log.d(TAG, "pre request main frame url " + mainDocUrl);
        AlipayRequest alipayRequest = (AlipayRequest) AlipayNetwork.getInstance().formatRequest(null, url2, "GET", false, getHeaders(url2, h5Page), getUCHeaders(), null, null, 0, 0, 0);
        if (!AlipayNetwork.getInstance().isUseSpdy()) {
            sRequestMap.put(url2, alipayRequest);
            sPageUrlMap.put(h5Page, url2);
            alipayRequest.applyStartParams(h5Page.getParams());
            alipayRequest.sendRequest(false, "NO", true);
        }
    }

    public void preConnect(String mainDocUrl, H5Page h5Page) {
        if (AlipayNetwork.getInstance() != null && H5ConfigUtil.isAlipayDomains(mainDocUrl) && sPreConnectUrlList != null) {
            H5Log.d(TAG, "pre connect with main frame url " + mainDocUrl);
            for (Entry entry : sPreConnectUrlList.entrySet()) {
                h5Page.getPageData().setPreConnectRequest(true);
                String url = (String) entry.getKey();
                if (System.currentTimeMillis() - ((Long) entry.getValue()).longValue() >= ((long) (sPreConnectTimeout * 1000))) {
                    H5Log.d(TAG, "pre connect with cdn url " + url);
                    AlipayRequest alipayRequest = (AlipayRequest) AlipayNetwork.getInstance().formatRequest(null, url, "GET", false, getHeaders(url, h5Page), getUCHeaders(), null, null, 0, 0, 0);
                    alipayRequest.applyStartParams(h5Page.getParams());
                    alipayRequest.setNeedPendingResponse(false);
                    alipayRequest.sendRequest(false, "NO", false);
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public AlipayRequest getRequest(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        AlipayRequest request = sRequestMap.remove(url);
        if (request == null) {
            return null;
        }
        H5Log.d(TAG, "preRequest hit url: " + url);
        return request;
    }

    public void clearPreRequest(H5Page h5Page) {
        if (h5Page != null) {
            String url = sPageUrlMap.remove(h5Page);
            if (url != null) {
                AlipayRequest alipayRequest = sRequestMap.remove(url);
                if (alipayRequest != null) {
                    alipayRequest.cancel();
                }
            }
        }
    }
}
