package com.alipay.mobile.nebulacore.plugin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5ChannelProvider;
import com.alipay.mobile.nebula.provider.H5SchemeInterceptProvider;
import com.alipay.mobile.nebula.schemeIntercept.H5SchemeInterceptUtil;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.util.H5SchemeWhiteList;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.util.H5PPQueryThread;
import com.alipay.mobile.nebulacore.util.H5PPQueryThread.OnGetQueryResult;
import com.alipay.mobile.nebulacore.view.H5Toast;
import com.alipay.mobile.security.bio.api.BioDetector;
import com.alipay.mobile.tinyappcommon.subpackage.TinyAppSubPackagePlugin;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

public class H5UrlInterceptPlugin extends H5SimplePlugin {
    public static final String BROADCAST_TITLE_URL = "com.alipay.mobile.h5container.pageFinished";
    public static final String TAG = "H5UrlInterceptPlugin";
    private static final List<String> f;
    public static final String xiaochengxuUrlHeader = "https://render.alipay.com/p/s/h5misc/resource_error?url=";
    private String a;
    private boolean b = false;
    /* access modifiers changed from: private */
    public H5Page c;
    private Set<String> d = new HashSet();
    private String e;

    public void onInitialize(H5CoreNode coreNode) {
        this.c = (H5Page) coreNode;
        Bundle params = this.c.getParams();
        this.e = H5Utils.getString(params, (String) "appId");
        this.a = H5Utils.getString(params, (String) H5Param.OFFLINE_HOST);
        H5Log.d(TAG, "url interception.");
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (CommonEvents.H5_PAGE_SHOULD_LOAD_URL.equals(action)) {
            H5Log.d(TAG, "H5_PAGE_SHOULD_LOAD_URL");
            return a(event);
        } else if (CommonEvents.H5_PAGE_RECEIVED_TITLE.equals(action)) {
            Activity activity = event.getActivity();
            String title = H5Utils.getString(event.getParam(), (String) "title");
            if (activity == null) {
                return false;
            }
            activity.setTitle(title);
            return false;
        } else if (CommonEvents.H5_PAGE_SHOW_CLOSE.equals(action)) {
            TextUtils.isEmpty(H5Utils.getString(this.c.getParams(), (String) H5Param.PUBLIC_ID, (String) ""));
            return false;
        } else if (CommonEvents.H5_TOOLBAR_BACK.equals(action)) {
            H5Log.d(TAG, "H5_TOOLBAR_BACK");
            this.b = true;
            return false;
        } else if (CommonEvents.H5_PAGE_PHYSICAL_BACK.equals(action)) {
            H5Log.d(TAG, "H5_PAGE_PHYSICAL_BACK");
            this.b = true;
            return false;
        } else if (!CommonEvents.H5_PAGE_FINISHED.equals(action)) {
            return false;
        } else {
            JSONObject param = event.getParam();
            String url = H5Utils.getString(param, (String) "url");
            String title2 = H5Utils.getString(param, (String) "title");
            Intent intent = new Intent();
            intent.setAction(BROADCAST_TITLE_URL);
            intent.putExtra("title", title2);
            intent.putExtra("url", url);
            H5Log.d(TAG, "send page finished broadcast.");
            LocalBroadcastManager.getInstance(event.getActivity()).sendBroadcast(intent);
            return false;
        }
    }

    private boolean a(H5Event event) {
        try {
            H5Log.d(TAG, "isBackKeyPressed is " + this.b);
            String url = H5Utils.getString(event.getParam(), (String) "url");
            Uri uri = H5UrlHelper.parseUrl(url);
            if (uri == null) {
                H5Log.w(TAG, "load url intercepted for failed to parse url.");
                return true;
            }
            String scheme = uri.getScheme();
            String host = uri.getHost();
            H5Log.d(TAG, "url " + url + " scheme " + scheme + " host " + host);
            if (interceptXiaoChengXu(url, this.e, this.c, false)) {
                return true;
            }
            if (a(scheme, host)) {
                return true;
            }
            if (TextUtils.isEmpty(url) || ((!url.endsWith(".docx") && !url.endsWith(".doc")) || BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_open_doc")))) {
                Uri mainFrameUri = H5UrlHelper.parseUrl(this.c.getUrl());
                String mainFrameHost = null;
                if (mainFrameUri != null) {
                    mainFrameHost = mainFrameUri.getHost();
                }
                JSONObject comListDataTmp = H5Utils.parseObject(H5SchemeInterceptUtil.getCompetitiveListWarp().value);
                boolean isInCompetitiveAliWhiteList = false;
                if (comListDataTmp != null && mainFrameHost != null) {
                    JSONArray aliWhiteList = H5Utils.parseArray(comListDataTmp.getString("aliWhiteList"));
                    if (aliWhiteList != null) {
                        int i = 0;
                        while (true) {
                            if (i >= aliWhiteList.size()) {
                                break;
                            } else if (H5PatternHelper.matchRegex(aliWhiteList.getString(i), mainFrameHost)) {
                                isInCompetitiveAliWhiteList = true;
                                break;
                            } else {
                                i++;
                            }
                        }
                    }
                }
                H5Log.d(TAG, "isInCompetitiveAliWhiteList " + isInCompetitiveAliWhiteList);
                boolean interceptJump = H5Utils.getBoolean(this.c.getParams(), (String) H5Param.LONG_INTERCEPT_JUMP, true);
                H5Log.d(TAG, "interceptJump " + interceptJump);
                JSONObject comListData = null;
                if (interceptJump) {
                    comListData = H5Utils.parseObject(H5SchemeInterceptUtil.getCompetitiveListWarp().value);
                    H5Log.d(TAG, "comListData " + comListData);
                }
                if (!(isInCompetitiveAliWhiteList || comListData == null || host == null)) {
                    JSONArray competitiveLinkja = H5Utils.parseArray(comListData.getString("competitiveLinkList"));
                    H5Log.d(TAG, "competitiveLinkja:" + competitiveLinkja);
                    boolean matchesLink = false;
                    if (competitiveLinkja != null) {
                        int i2 = 0;
                        while (true) {
                            if (i2 >= competitiveLinkja.size()) {
                                break;
                            } else if (H5PatternHelper.matchRegex(competitiveLinkja.getString(i2), host)) {
                                matchesLink = true;
                                break;
                            } else {
                                i2++;
                            }
                        }
                    }
                    if (matchesLink) {
                        H5Log.d(TAG, "DEFAULT_COMPETITIVE_LINK_LIST");
                        this.c.loadUrl("https://ds.alipay.com/error/securityLink.htm?url=" + URLEncoder.encode(url, "utf-8"));
                        return true;
                    }
                }
                if (url.startsWith("file://")) {
                    if (!url.startsWith(this.a)) {
                        H5Log.w(TAG, "file url intercepted for safe strategy");
                        JSONObject newParam = new JSONObject();
                        String nextUrl = H5ContentProvider.UN_SAFE;
                        if (Nebula.DEBUG) {
                            nextUrl = nextUrl + "?url=" + H5UrlHelper.encode(url);
                        }
                        newParam.put((String) "url", (Object) nextUrl);
                        this.c.sendEvent(CommonEvents.H5_PAGE_LOAD_URL, newParam);
                        return true;
                    }
                }
                if ("about:blank".equals(url)) {
                    return false;
                }
                if ("about".equalsIgnoreCase(scheme) || "data".equalsIgnoreCase(scheme) || "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme) || "ftp".equalsIgnoreCase(scheme)) {
                    return false;
                }
                if ("file".equalsIgnoreCase(scheme)) {
                    String absPath = uri.getPath();
                    if (absPath != null && absPath.startsWith("/android_asset/")) {
                        H5Log.d(TAG, "load asset " + absPath);
                    }
                    return false;
                }
                if (!(comListData == null || scheme == null)) {
                    if (isInCompetitiveAliWhiteList || H5SchemeWhiteList.sSchemeMap.contains(scheme)) {
                        H5Log.d(TAG, "hardcode scheme whitelist");
                        if (!H5Utils.resolveExtApp(url)) {
                            return true;
                        }
                        return b(url, scheme);
                    }
                    H5SchemeInterceptProvider h5SchemeInterceptProvider = (H5SchemeInterceptProvider) Nebula.getProviderManager().getProvider(H5SchemeInterceptProvider.class.getName());
                    if (h5SchemeInterceptProvider == null || !H5SchemeInterceptUtil.getCompetitiveListWarp().useNew) {
                        JSONArray schemeBlackja = H5Utils.parseArray(comListData.getString("schemeBlacklist"));
                        boolean matchesBlackScheme = false;
                        if (schemeBlackja != null) {
                            int i3 = 0;
                            while (true) {
                                if (i3 >= schemeBlackja.size()) {
                                    break;
                                } else if (H5PatternHelper.matchRegex(schemeBlackja.getString(i3), scheme)) {
                                    matchesBlackScheme = true;
                                    break;
                                } else {
                                    i3++;
                                }
                            }
                        }
                        JSONArray schemeWhiteja = H5Utils.parseArray(comListData.getString("schemeWhiteList"));
                        boolean matchesWhiteScheme = false;
                        if (schemeWhiteja != null) {
                            int i4 = 0;
                            while (true) {
                                if (i4 >= schemeWhiteja.size()) {
                                    break;
                                } else if (H5PatternHelper.matchRegex(schemeWhiteja.getString(i4), scheme)) {
                                    matchesWhiteScheme = true;
                                    break;
                                } else {
                                    i4++;
                                }
                            }
                        }
                        long lastTouchTime = this.c.getLastTouch();
                        boolean jumpToPP = true;
                        JSONObject ppConfig = H5Utils.parseObject(H5Environment.getConfigWithProcessCache("h5_ppConfig"));
                        if (ppConfig != null) {
                            jumpToPP = "YES".equals(H5Utils.getString(ppConfig, (String) "jumpToPP"));
                        }
                        H5Log.d(TAG, "jumpToPP " + jumpToPP);
                        H5ChannelProvider h5ChannelProvider = (H5ChannelProvider) Nebula.getProviderManager().getProvider(H5ChannelProvider.class.getName());
                        boolean channel = false;
                        if (h5ChannelProvider != null) {
                            String channelId = h5ChannelProvider.getChannelId();
                            channel = "5136".equals(channelId);
                            H5Log.d(TAG, "WalletChannelId is " + channelId + ", isGooglePlayChannel " + channel);
                        }
                        boolean isGooglePlayChannel = channel;
                        H5Log.d(TAG, "isGooglePlayChannel:" + isGooglePlayChannel);
                        if (matchesBlackScheme) {
                            if (Math.abs(System.currentTimeMillis() - lastTouchTime) < 400) {
                                JSONObject jo = new JSONObject();
                                jo.put((String) "url", (Object) url);
                                jo.put((String) "scheme", (Object) scheme);
                                jo.put((String) "type", (Object) "blacklist");
                                this.c.sendEvent(CommonEvents.H5_PAGE_INTERCEPT_SCHEME, jo);
                                H5Log.d(TAG, "DEFAULT_SCHEME_BLACK_LIST_MANUAL");
                                this.c.loadUrl("https://ds.alipay.com/error/securityLink.htm?url=" + URLEncoder.encode(url, "utf-8"));
                                return true;
                            }
                            H5Log.d(TAG, "DEFAULT_SCHEME_BLACK_LIST_AUTO");
                            return true;
                        } else if (matchesWhiteScheme) {
                            H5Log.d(TAG, "DEFAULT_SCHEME_WHITE_LIST");
                            if (!jumpToPP || H5Utils.resolveExtApp(url)) {
                                if ("pp".equals(scheme) && url.contains("www.25pp.com/down")) {
                                    JSONObject params = new JSONObject();
                                    params.put((String) "type", (Object) "pp");
                                    params.put((String) TinyAppSubPackagePlugin.DOWNLOAD_URL, (Object) url);
                                    this.c.sendEvent(CommonEvents.H5_PAGE_JUMP_PP_DOWNLOAD, params);
                                }
                                if (!H5Utils.resolveExtApp(url)) {
                                    return true;
                                }
                            } else {
                                final boolean z = isGooglePlayChannel;
                                final String str = scheme;
                                AnonymousClass1 r0 = new OnGetQueryResult() {
                                    public void onQueryResult(String detailUrl, String packageName) {
                                        if (z) {
                                            if (!TextUtils.isEmpty(packageName)) {
                                                try {
                                                    H5Utils.startExtActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)));
                                                } catch (Exception e) {
                                                    H5Utils.runOnMain(new Runnable() {
                                                        public void run() {
                                                            H5Toast.showToast(H5Environment.getContext(), H5Environment.getResources().getString(R.string.h5_googleplaynotinstall), 0);
                                                        }
                                                    });
                                                }
                                            } else {
                                                H5Log.d(H5UrlInterceptPlugin.TAG, "googleplaychannel query packagename empty");
                                            }
                                            H5Log.d(H5UrlInterceptPlugin.TAG, "download whitelistapk but googleplay channel return");
                                        } else if (!TextUtils.isEmpty(detailUrl)) {
                                            H5UrlInterceptPlugin.this.c.loadUrl(detailUrl);
                                            JSONObject params = new JSONObject();
                                            params.put((String) "type", (Object) "scheme");
                                            params.put((String) "origin", (Object) str);
                                            params.put((String) "detailUrl", (Object) detailUrl);
                                            H5UrlInterceptPlugin.this.c.sendEvent(CommonEvents.H5_PAGE_JUMP_PP, params);
                                        }
                                    }
                                };
                                ThreadPoolExecutor executor = H5Utils.getExecutor(H5ThreadType.URGENT);
                                H5PPQueryThread h5PPQueryThread = H5PPQueryThread.getInstance();
                                h5PPQueryThread.setParams(url, 2, r0, this.c);
                                executor.execute(h5PPQueryThread);
                                return true;
                            }
                        } else {
                            if (this.b) {
                                this.d.clear();
                            }
                            long interceptTime = System.currentTimeMillis();
                            H5Log.d(TAG, "lastTouchTime is " + lastTouchTime + " ,interceptTime is " + interceptTime + " ,delta is " + Math.abs(interceptTime - lastTouchTime) + "tmpSchemeWhiteSet contains " + this.d.contains(scheme));
                            String nextUrl2 = "https://ds.alipay.com/error/redirectLink.htm?url=" + URLEncoder.encode(url, "utf-8");
                            if (Math.abs(interceptTime - lastTouchTime) < 400) {
                                JSONObject jo2 = new JSONObject();
                                jo2.put((String) "url", (Object) url);
                                jo2.put((String) "scheme", (Object) scheme);
                                jo2.put((String) "type", (Object) "greylist");
                                this.c.sendEvent(CommonEvents.H5_PAGE_INTERCEPT_SCHEME, jo2);
                                if (jumpToPP && !H5Utils.resolveExtApp(url) && !this.d.contains(scheme)) {
                                    H5Log.d(TAG, "DEFAULT_SCHEME_ELSE_MANUAL_PP");
                                    this.d.add(scheme);
                                    final boolean z2 = isGooglePlayChannel;
                                    final String str2 = scheme;
                                    final String str3 = nextUrl2;
                                    AnonymousClass2 r02 = new OnGetQueryResult() {
                                        public void onQueryResult(String detailUrl, String packageName) {
                                            if (z2) {
                                                if (!TextUtils.isEmpty(packageName)) {
                                                    try {
                                                        H5Utils.startExtActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)));
                                                    } catch (Exception e) {
                                                        H5Utils.runOnMain(new Runnable() {
                                                            public void run() {
                                                                H5Toast.showToast(H5Environment.getContext(), H5Environment.getResources().getString(R.string.h5_googleplaynotinstall), 0);
                                                            }
                                                        });
                                                    }
                                                } else {
                                                    H5Log.d(H5UrlInterceptPlugin.TAG, "googleplaychannel query packagename empty");
                                                }
                                                H5Log.d(H5UrlInterceptPlugin.TAG, "download whitelistapk but googleplay channel return");
                                            } else if (!TextUtils.isEmpty(detailUrl)) {
                                                H5UrlInterceptPlugin.this.c.loadUrl(detailUrl);
                                                JSONObject params = new JSONObject();
                                                params.put((String) "type", (Object) "scheme");
                                                params.put((String) "origin", (Object) str2);
                                                params.put((String) "detailUrl", (Object) detailUrl);
                                                H5UrlInterceptPlugin.this.c.sendEvent(CommonEvents.H5_PAGE_JUMP_PP, params);
                                            } else {
                                                H5UrlInterceptPlugin.this.c.loadUrl(str3);
                                            }
                                        }
                                    };
                                    ThreadPoolExecutor executor2 = H5Utils.getExecutor(H5ThreadType.URGENT);
                                    H5PPQueryThread h5PPQueryThread2 = H5PPQueryThread.getInstance();
                                    h5PPQueryThread2.setParams(url, 2, r02, this.c);
                                    executor2.execute(h5PPQueryThread2);
                                    this.b = false;
                                    return true;
                                } else if (!this.d.contains(scheme) && H5Utils.resolveExtApp(url)) {
                                    H5Log.d(TAG, "DEFAULT_SCHEME_ELSE_MANUAL_NORMAL");
                                    this.d.add(scheme);
                                    this.c.loadUrl(nextUrl2);
                                    this.b = false;
                                    return true;
                                } else if (!H5Utils.resolveExtApp(url)) {
                                    return true;
                                }
                            } else {
                                H5Log.d(TAG, "DEFAULT_SCHEME_ELSE_AUTO");
                                this.b = false;
                                return true;
                            }
                        }
                    } else {
                        return h5SchemeInterceptProvider.handlerOnScheme(url, this.c);
                    }
                }
                return b(url, scheme);
            }
            Nebula.openInBrowser(this.c, url, null);
            return true;
        } catch (Exception e2) {
            H5Log.e(TAG, "check url exception.", e2);
            return true;
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        f = arrayList;
        arrayList.add("tel");
        f.add("mailto");
        f.add("sms");
        f.add(BioDetector.EXT_KEY_GEO);
    }

    public static boolean interceptXiaoChengXu(String url, String appId, H5Page h5Page, boolean iframe) {
        if (url.startsWith(xiaochengxuUrlHeader)) {
            return false;
        }
        Uri uri = H5UrlHelper.parseUrl(url);
        String host = "";
        if (uri != null) {
            String scheme = uri.getScheme();
            if (scheme != null && f.contains(scheme.toLowerCase())) {
                return false;
            }
            host = uri.getHost();
        }
        boolean hasPermissionFile = false;
        if (Nebula.getH5TinyAppService() != null) {
            hasPermissionFile = Nebula.getH5TinyAppService().hasPermissionFile(appId, h5Page);
        }
        boolean shouldIntercept = false;
        if (hasPermissionFile) {
            shouldIntercept = iframe ? !Nebula.getH5TinyAppService().hasPermissionOnIframe(appId, host, H5ApiManager.validDomain, h5Page) : !Nebula.getH5TinyAppService().hasPermission(appId, host, H5ApiManager.validDomain, h5Page);
            if (shouldIntercept) {
                H5LogUtil.logNebulaTech(H5LogData.seedId("H5_AL_NETWORK_PERMISSON_ERROR").param1().add(String.valueOf(iframe), null).param3().add("BanMainURL", url));
                String errorUrl = new StringBuilder(xiaochengxuUrlHeader).append(H5UrlHelper.encode(url)).toString();
                if (iframe) {
                    h5Page.replace(errorUrl);
                } else {
                    h5Page.loadUrl(errorUrl);
                }
            }
        }
        return shouldIntercept;
    }

    private boolean a(String scheme, String host) {
        if (this.c == null || !"http".equals(scheme) || TextUtils.isEmpty(host)) {
            return false;
        }
        String config = H5Environment.getConfigWithProcessCache("h5_SSLVerifyDomain");
        if (TextUtils.isEmpty(config)) {
            return false;
        }
        JSONArray array = H5Utils.parseArray(config);
        if (array == null || array.size() == 0) {
            return false;
        }
        int index = 0;
        while (index < array.size()) {
            try {
                String rule = array.getString(index);
                if (H5PatternHelper.matchRegex(rule, host)) {
                    this.c.getWebView().loadUrl("javascript:location.replace('" + "https://ds.alipay.com/fd-in15xm06/index.html" + "');");
                    H5Log.w(TAG, "url intercepted by pattern " + rule);
                    return true;
                }
                index++;
            } catch (Throwable t) {
                H5Log.e(TAG, "match http host exception.", t);
            }
        }
        return false;
    }

    private boolean b(String url, String scheme) {
        if (TextUtils.isEmpty(scheme) || "javascript".equals(scheme)) {
            return false;
        }
        try {
            Intent launchIntent = Intent.parseUri(url, 1);
            launchIntent.addCategory("android.intent.category.BROWSABLE");
            launchIntent.setComponent(null);
            if (VERSION.SDK_INT >= 15) {
                launchIntent.setSelector(null);
            }
            try {
                H5Utils.startExtActivity(launchIntent);
                if (this.d.contains(scheme)) {
                    this.c.exitPage();
                }
                H5Log.d(TAG, "start ext app: " + scheme);
                return true;
            } catch (Exception e2) {
                H5Log.e(TAG, "exception detail", e2);
                return false;
            }
        } catch (URISyntaxException ex) {
            H5Log.w(TAG, "bad uri " + url + ": " + ex.getMessage());
            return false;
        }
    }

    public void onRelease() {
        H5Log.d(TAG, "onRelease");
        this.c = null;
        this.d.clear();
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.H5_PAGE_SHOULD_LOAD_URL);
        filter.addAction(CommonEvents.H5_PAGE_SHOW_CLOSE);
        filter.addAction(CommonEvents.H5_PAGE_RECEIVED_TITLE);
        filter.addAction(CommonEvents.H5_PAGE_PHYSICAL_BACK);
        filter.addAction(CommonEvents.H5_TOOLBAR_BACK);
        filter.addAction(CommonEvents.H5_PAGE_FINISHED);
    }
}
