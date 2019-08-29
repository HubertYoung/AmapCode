package com.alipay.mobile.nebulacore.plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BaseFragment;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Bundle;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Data;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5PageLoader;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.model.H5Refer;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.provider.H5EnvProvider;
import com.alipay.mobile.nebula.provider.H5PreRpcProvider;
import com.alipay.mobile.nebula.provider.H5TinyAppProvider;
import com.alipay.mobile.nebula.startParam.H5StartParamManager;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ParamParser;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.util.H5SecurityUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.util.TestDataUtils;
import com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.core.H5PageImpl;
import com.alipay.mobile.nebulacore.core.H5SessionImpl;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.tabbar.H5SessionTabBar;
import com.alipay.mobile.nebulacore.tabbar.H5SessionTabManager;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.alipay.mobile.nebulacore.ui.H5Fragment;
import com.alipay.mobile.nebulacore.ui.H5FragmentManager;
import com.alipay.mobile.nebulacore.ui.H5NetworkCheckActivity;
import com.alipay.mobile.nebulacore.ui.H5TransActivity;
import com.alipay.mobile.nebulacore.util.H5AnimatorUtil;
import com.alipay.mobile.nebulacore.util.NebulaUtil;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.regex.Pattern;

public class H5SessionPlugin extends H5SimplePlugin {
    public static final String SHOW_NETWORK_CHECK_ACTIVITY = "showNetWorkCheckActivity";
    public static final String TAG = "H5SessionPlugin";
    /* access modifiers changed from: private */
    public H5SessionImpl a;
    private Boolean b = Boolean.valueOf(false);
    private JSONObject c;
    private long d = 0;
    private int e = 200;
    private boolean f;

    public H5SessionPlugin(H5SessionImpl session) {
        this.a = session;
    }

    private void a() {
        JSONObject spaceTimeConfigObj = H5Utils.parseObject(H5Environment.getConfigWithProcessCache("h5_pushWindowSpace"));
        try {
            this.e = H5Utils.getInt(spaceTimeConfigObj, (String) "spaceTime");
            this.f = H5Utils.getBoolean(spaceTimeConfigObj, (String) "enable", false);
            H5Log.d(TAG, "H5SessionPlugin get config SPACE_TIME " + this.e + ", enableAntiDuplicate4Tiny " + this.f);
        } catch (Exception e2) {
            H5Log.e(TAG, "catch exception ", e2);
        }
    }

    public void onRelease() {
        this.a = null;
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.GET_SESSION_DATA);
        filter.addAction(CommonEvents.SET_SESSION_DATA);
        filter.addAction(CommonEvents.EXIT_SESSION);
        filter.addAction(CommonEvents.POP_WINDOW);
        filter.addAction(CommonEvents.POP_TO);
        filter.addAction("pushWindow");
        filter.addAction("showFavorites");
        filter.addAction(CommonEvents.HIDE_FAVORITES);
        filter.addAction(SHOW_NETWORK_CHECK_ACTIVITY);
        filter.addAction("getSceneStackInfo");
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if ("showFavorites".equals(action)) {
            this.b = Boolean.valueOf(true);
        } else if (CommonEvents.HIDE_FAVORITES.equals(action)) {
            this.b = Boolean.valueOf(false);
        }
        return false;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (CommonEvents.SET_SESSION_DATA.equals(action)) {
            a(event);
            bridgeContext.sendSuccess();
        } else if (CommonEvents.GET_SESSION_DATA.equals(action)) {
            a(event, bridgeContext);
        } else if (CommonEvents.EXIT_SESSION.equals(action)) {
            b();
        } else if (CommonEvents.POP_TO.equals(action)) {
            b(event, bridgeContext);
        } else if (CommonEvents.POP_WINDOW.equals(action)) {
            b(event);
        } else if ("pushWindow".equals(action)) {
            String url = H5Utils.getString(event.getParam(), (String) "url", (String) null);
            if (TextUtils.isEmpty(url)) {
                url = H5Utils.getString(event.getParam(), (String) H5Param.URL, (String) null);
            }
            if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_enableInterceptJavascriptInPushWindow")) || TextUtils.isEmpty(url) || !url.startsWith("javascript:")) {
                if (!TextUtils.isEmpty(url)) {
                    Bundle pageParams = null;
                    if (event.getH5page() != null) {
                        pageParams = event.getH5page().getParams();
                    }
                    H5EnvProvider h5EnvProvider = (H5EnvProvider) H5Utils.getProvider(H5EnvProvider.class.getName());
                    if (h5EnvProvider != null && h5EnvProvider.goToSchemeService(url, pageParams)) {
                        H5Log.d(TAG, "stripLandingURL&Deeplink url " + url + " bingo deeplink");
                        return true;
                    } else if (H5Utils.isStripLandingURLEnable(url, "pushWindowNormal")) {
                        String realUrl = H5Utils.getStripLandingURL(url);
                        if (!TextUtils.equals(url, realUrl) && h5EnvProvider != null) {
                            boolean result = h5EnvProvider.goToSchemeService(realUrl, pageParams);
                            String appId = null;
                            String publicId = null;
                            String bizScenario = null;
                            if (event.getH5page() != null) {
                                appId = H5Utils.getString(event.getH5page().getParams(), (String) "appId");
                                publicId = H5Utils.getString(event.getH5page().getParams(), (String) H5Param.PUBLIC_ID);
                                bizScenario = H5Utils.getString(event.getH5page().getParams(), (String) H5Param.LONG_BIZ_SCENARIO);
                            }
                            H5Utils.landingMonitor(url, realUrl, true, "pushWindowNormal", appId, publicId, bizScenario);
                            if (result) {
                                H5Log.d(TAG, "stripLandingURL&Deeplink url " + url + " bingo deeplink in landing");
                                return true;
                            }
                        }
                    }
                }
                c(event, bridgeContext);
            } else {
                H5Log.d(TAG, "pushWindow intercept javascript success");
                return true;
            }
        } else if (SHOW_NETWORK_CHECK_ACTIVITY.equals(action)) {
            Intent intent = new Intent(H5Environment.getContext(), H5NetworkCheckActivity.class);
            JSONObject param = event.getParam();
            if (param != null) {
                intent.putExtra("error_code", param.getString("error_code"));
                intent.putExtra("url", param.getString("url"));
                intent.putExtra("reason", param.getString("reason"));
            }
            try {
                LauncherApplicationAgent.getInstance().getMicroApplicationContext().startActivity(LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp(), intent);
            } catch (Exception e2) {
                H5Log.e((String) TAG, "showNetWorkCheckActivity, " + e2.toString());
            }
        } else if ("getSceneStackInfo".equals(action)) {
            d(event, bridgeContext);
        }
        return true;
    }

    private void a(H5Event event, H5BridgeContext bridgeContext) {
        H5Data sessionData = this.a.getData();
        JSONObject param = event.getParam();
        if (param != null && sessionData != null) {
            JSONArray jaKeys = H5Utils.getJSONArray(param, "keys", null);
            if (jaKeys != null && !jaKeys.isEmpty()) {
                JSONObject resultData = new JSONObject();
                JSONObject values = new JSONObject();
                for (int index = 0; index < jaKeys.size(); index++) {
                    String key = jaKeys.getString(index);
                    values.put(key, (Object) sessionData.get(key));
                }
                resultData.put((String) "data", (Object) values);
                bridgeContext.sendBridgeResult(resultData);
            }
        }
    }

    private void a(H5Event event) {
        H5Data sessionData = this.a.getData();
        JSONObject param = event.getParam();
        if (param != null && sessionData != null) {
            JSONObject joData = H5Utils.getJSONObject(param, "data", null);
            if (joData != null && !joData.isEmpty()) {
                for (String key : joData.keySet()) {
                    sessionData.set(key, joData.getString(key));
                }
            }
        }
    }

    private void b() {
        H5Page h5Page = this.a.getTopPage();
        if (h5Page != null && H5AppUtil.enableDSL(h5Page.getParams()) && (h5Page.getContext().getContext() instanceof Activity)) {
            Activity activity = (Activity) h5Page.getContext().getContext();
            if (activity.isTaskRoot() && Nebula.doKeepAlive(activity, h5Page.getParams())) {
                return;
            }
        }
        this.a.exitSession();
    }

    private void b(H5Event event) {
        JSONObject param = event.getParam();
        if (param != null) {
            this.a.getData().set(H5Param.H5_SESSION_POP_PARAM, H5Utils.getJSONObject(param, "data", null).toJSONString());
        }
        H5Page page = this.a.getTopPage();
        if (page != null) {
            page.sendEvent(CommonEvents.H5_PAGE_CLOSE, null);
        }
    }

    private void b(H5Event event, H5BridgeContext bridgeContext) {
        if (!c(event)) {
            JSONObject result = new JSONObject();
            result.put((String) "error", (Object) Integer.valueOf(10));
            bridgeContext.sendBridgeResult(result);
        }
    }

    private boolean c(H5Event event) {
        JSONObject param = event.getParam();
        int index = Integer.MAX_VALUE;
        if (param != null && param.containsKey("index")) {
            index = H5Utils.getInt(param, (String) "index", Integer.MAX_VALUE);
        }
        if (index == Integer.MAX_VALUE) {
            index = a(H5Utils.getString(param, (String) "url", (String) null), false);
        }
        if (index == Integer.MAX_VALUE) {
            index = a(H5Utils.getString(param, (String) "urlPattern", (String) null), true);
        }
        if (index == Integer.MAX_VALUE) {
            H5Log.e((String) TAG, (String) "can't find page index");
            return false;
        } else if (!a(param, index, false, false)) {
            return true;
        } else {
            return false;
        }
    }

    private int a(String target, boolean isRegular) {
        int index = Integer.MAX_VALUE;
        Stack sessionPages = this.a.getPages();
        if (sessionPages == null) {
            return Integer.MAX_VALUE;
        }
        Stack sessionPagesWithOutPrerender = Nebula.getSessionPagesWithOutPrerender(sessionPages);
        if (TextUtils.isEmpty(target) || sessionPagesWithOutPrerender == null || sessionPagesWithOutPrerender.isEmpty()) {
            return Integer.MAX_VALUE;
        }
        int size = sessionPagesWithOutPrerender.size();
        int idx = 0;
        while (true) {
            if (idx > size - 1) {
                break;
            }
            String pageUrl = ((H5Page) sessionPagesWithOutPrerender.get(idx)).getUrl();
            if (!TextUtils.isEmpty(pageUrl)) {
                if (isRegular) {
                    Pattern pattern = H5PatternHelper.compile(target);
                    if (pattern != null && pattern.matcher(pageUrl).find()) {
                        index = idx;
                        break;
                    }
                } else if (target.equals(pageUrl)) {
                    index = idx;
                    break;
                }
            }
            idx++;
        }
        return index;
    }

    private void c(H5Event event, H5BridgeContext h5BridgeContext) {
        String currentUrl;
        if (H5Environment.getContext() != null) {
            H5PageLoader.h5Token = "H5" + H5SecurityUtil.getMD5((System.currentTimeMillis() + ((long) H5Utils.getUid(H5Environment.getContext()))));
        }
        JSONObject callParam = event.getParam();
        H5CoreNode target = event.getTarget();
        if (!(target instanceof H5Page)) {
            H5Log.w(TAG, "invalid target!");
            return;
        }
        H5Page h5Page = (H5Page) target;
        Bundle bundle = h5Page.getParams();
        if (!H5Utils.getBoolean(bundle, (String) "isTinyApp", false) || !TextUtils.isEmpty(H5Utils.getString(bundle, (String) "MINI-PROGRAM-WEB-VIEW-TAG"))) {
            currentUrl = h5Page.getUrl();
        } else {
            currentUrl = H5Utils.getString(bundle, (String) "url");
            a();
            if (this.f && isFastClick() && callParam != null && callParam.equals(this.c)) {
                H5Log.d(TAG, "in tinyapp pushWindow duplicated");
                return;
            } else if (callParam != null) {
                this.c = (JSONObject) callParam.clone();
            }
        }
        if (TextUtils.isEmpty(H5Refer.referUrl)) {
            H5Refer.referUrl = H5Logger.getContextParam(LogContext.STORAGE_REFVIEWID);
        } else if (!TextUtils.equals(currentUrl, H5Refer.referUrl) && H5Logger.enableStockTradeLog()) {
            H5Refer.referUrl = currentUrl;
        }
        H5Log.d("H5LoggerPlugins", "push window , g5PageData : " + h5Page.getPageData().hashCode() + " , public ReferUrl :  " + H5Refer.referUrl);
        if (!H5Utils.getBoolean(bundle, (String) H5Param.LONG_TRANSPARENT, false) || H5Utils.getBoolean(bundle, (String) H5Param.LONG_FULLSCREEN, false)) {
            if (h5Page != null) {
                H5Log.d(TAG, "sendToWeb page event pagePause");
                h5Page.getBridge().sendToWeb("pagePause", null, null);
            }
            Bundle oldParams = new Bundle();
            oldParams.putAll(bundle);
            String value = H5Environment.getConfigWithProcessCache("h5_MergeParamBlankList");
            if (!TextUtils.isEmpty(value)) {
                JSONArray jsonArray = H5Utils.parseArray(value);
                if (jsonArray != null && !jsonArray.isEmpty()) {
                    Iterator<Object> it = jsonArray.iterator();
                    while (it.hasNext()) {
                        Object key = it.next();
                        try {
                            if (key instanceof String) {
                                String removeKey = (String) key;
                                if (oldParams.containsKey(removeKey)) {
                                    oldParams.remove(removeKey);
                                    H5Log.d(TAG, "remove " + key + " for h5_MergeParamBlankList");
                                }
                            }
                        } catch (Throwable e2) {
                            H5Log.e(TAG, "exception detail", e2);
                        }
                    }
                }
            }
            H5ApiManager h5ApiManager = Nebula.getH5TinyAppService();
            if (h5ApiManager != null) {
                String tag = h5ApiManager.getWebViewTag();
                if (!TextUtils.isEmpty(tag) && oldParams.containsKey(tag) && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("H5_removeWebViewTag"))) {
                    oldParams.remove(tag);
                }
            }
            if (oldParams.containsKey("preRpc")) {
                H5Log.d(TAG, "in H5SessionPlugin delete preRpc startparam");
                oldParams.remove("preRpc");
            }
            if (oldParams.containsKey(H5Param.LONG_NAV_SEARCH_BAR_TYPE)) {
                oldParams.remove(H5Param.LONG_NAV_SEARCH_BAR_TYPE);
            }
            if (oldParams.containsKey("backgroundColor")) {
                oldParams.remove("backgroundColor");
            }
            if (oldParams.containsKey(H5Param.LONG_TRANSPARENT_TITLE)) {
                H5Log.d(TAG, "in H5SessionPlugin delete transparentTitle startparam");
                oldParams.remove(H5Param.LONG_TRANSPARENT_TITLE);
            }
            if (oldParams.containsKey(H5Param.LONG_TRANSPARENT_TITLE_TEXTAUTO)) {
                H5Log.d(TAG, "in H5SessionPlugin delete transparentTitleTextAuto startparam");
                oldParams.remove(H5Param.LONG_TRANSPARENT_TITLE_TEXTAUTO);
            }
            if (oldParams.containsKey(H5Param.LONG_TITLE_IMAGE)) {
                H5Log.d(TAG, "in H5SessionPlugin delete titleImage startparam");
                oldParams.remove(H5Param.LONG_TITLE_IMAGE);
            }
            if (oldParams.containsKey(H5Param.LONG_BOUNCE_TOP_COLOR)) {
                oldParams.remove(H5Param.LONG_BOUNCE_TOP_COLOR);
            }
            if (oldParams.containsKey(H5Fragment.fragmentType)) {
                H5Log.d(TAG, "in H5SessionPlugin delete fragmentType startparam");
                oldParams.remove(H5Fragment.fragmentType);
            }
            if (oldParams.containsKey(H5Param.CREATEPAGESENCE)) {
                H5Log.d(TAG, "in H5SessionPlugin delete createPageSence startparam");
                oldParams.remove(H5Param.CREATEPAGESENCE);
            }
            if (oldParams.containsKey(H5Param.PULL_REFRESH_STYLE)) {
                H5Log.d(TAG, "in H5SessionPlugin delete pullRefreshStyle startparam");
                oldParams.remove(H5Param.PULL_REFRESH_STYLE);
            }
            if (oldParams.containsKey("closeAllWindow")) {
                oldParams.remove("closeAllWindow");
            }
            if (H5Utils.getBoolean(oldParams, (String) "isTinyApp", false) && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("h5_handlerOnPushWindowParam"))) {
                H5TinyAppProvider h5TinyAppProvider = (H5TinyAppProvider) H5Utils.getProvider(H5TinyAppProvider.class.getName());
                if (h5TinyAppProvider != null) {
                    try {
                        h5TinyAppProvider.handlerOnPushWindowParam(oldParams);
                    } catch (Throwable throwable) {
                        H5Log.e((String) TAG, throwable);
                    }
                }
            }
            JSONObject param = H5Utils.getJSONObject(callParam, "param", null);
            int titleBarColor = H5Utils.getInt(callParam, (String) H5Param.LONG_TITLE_BAR_COLOR);
            boolean closeCurrentWindow = H5Utils.getBoolean(param, (String) "closeCurrentWindow", false);
            final boolean closeAllWindow = H5Utils.getBoolean(param, (String) "closeAllWindow", false);
            r0 = "popToIndex";
            int popToIndex = H5Utils.getInt(callParam, (String) "popToIndex", closeAllWindow ? 0 : Integer.MIN_VALUE);
            if (titleBarColor == -1) {
                callParam.put((String) H5Param.LONG_TITLE_BAR_COLOR, (Object) Integer.valueOf(titleBarColor | -16777216));
            }
            if (param != null && !param.isEmpty()) {
                Bundle newParams = new Bundle();
                H5Utils.toBundle(newParams, param);
                Bundle newParams2 = H5ParamParser.parse(newParams, false);
                for (String key2 : newParams2.keySet()) {
                    H5ParamParser.remove(oldParams, key2);
                }
                oldParams.putAll(newParams2);
            }
            oldParams.putBoolean("showFavorites", this.b.booleanValue());
            String url = H5Utils.getString(callParam, (String) "url", (String) null);
            if (TextUtils.isEmpty(url)) {
                H5Log.e((String) TAG, (String) "can't get url parameter!");
                return;
            }
            if (url.startsWith(MetaRecord.LOG_SEPARATOR)) {
                int anchorIndex = currentUrl.indexOf(35);
                if (anchorIndex != -1) {
                    url = currentUrl.substring(0, anchorIndex) + url;
                    H5Log.d(TAG, "stripAnchor url:" + url);
                }
            } else {
                url = H5Utils.getAbsoluteUrl(currentUrl, url, this.a.getParams());
            }
            H5Log.d(TAG, "pushWindow url " + url);
            oldParams.putString("url", url);
            if (a(url)) {
                oldParams.putString(H5Param.REFERER, currentUrl);
            }
            String launchParamsTag = H5Utils.getString(callParam, (String) H5StartParamManager.launchParamsTag);
            if (!TextUtils.isEmpty(launchParamsTag)) {
                Bundle launcher = H5StartParamManager.getInstance().getH5StartParam(H5Utils.getString(oldParams, (String) "appId"), launchParamsTag);
                if (launcher != null && !launcher.isEmpty()) {
                    H5Log.d(TAG, "launcher " + launcher);
                    oldParams.putAll(launcher);
                }
            }
            H5ParamParser.parseMagicOptions(oldParams, TAG);
            H5ParamParser.parse(oldParams, false);
            if (h5Page != null && h5Page.getContext() != null && h5Page.getContext().getContext() != null) {
                oldParams.putString(H5Param.FROM_TYPE, "pushWindow");
                if (NebulaUtil.enableRecordStartupParams()) {
                    a(h5Page.getPageData(), oldParams);
                }
                H5Log.d(TAG, "in H5SessionPlugin, oldParams is " + oldParams.toString());
                H5PreRpcProvider preRpcProvider = (H5PreRpcProvider) Nebula.getProviderManager().getProvider(H5PreRpcProvider.class.getName());
                if (preRpcProvider != null) {
                    preRpcProvider.setStartParams(oldParams);
                    preRpcProvider.preRpc();
                }
                int count = 0;
                if (H5Utils.getBoolean(oldParams, (String) H5Param.LONG_TRANSPARENT, false) || TextUtils.equals("YES", H5Utils.getString(oldParams, (String) H5Param.LONG_TRANSPARENT, (String) "NO"))) {
                    Intent intent = new Intent(h5Page.getContext().getContext(), H5TransActivity.class);
                    oldParams.remove(H5Param.LONG_TRANSPARENT);
                    oldParams.putBoolean(H5Param.LONG_TRANSPARENT, true);
                    oldParams.putBoolean("showLoadingView", true);
                    if (oldParams.containsKey(H5Param.ASYNCINDEX)) {
                        oldParams.remove(H5Param.ASYNCINDEX);
                    }
                    intent.putExtras(oldParams);
                    H5Environment.startActivity(h5Page.getContext(), intent);
                    if (H5Utils.getBoolean(oldParams, (String) H5Param.LONG_TRANS_ANIMATE, false)) {
                        H5AnimatorUtil.setActivityFadingStart();
                    }
                    if (closeCurrentWindow) {
                        h5Page.sendEvent(CommonEvents.H5_PAGE_CLOSE, null);
                        return;
                    }
                    return;
                }
                boolean delayRender = Nebula.isDelayRender(oldParams);
                oldParams.putBoolean(H5Param.LONG_DELAY_RENDER, delayRender);
                if (delayRender) {
                    Intent intent2 = new Intent(h5Page.getContext().getContext(), H5TransActivity.class);
                    oldParams.putBoolean(H5Param.LONG_DELAY_RENDER, true);
                    intent2.putExtras(oldParams);
                    H5Environment.startActivity(h5Page.getContext(), intent2);
                    if (closeCurrentWindow) {
                        h5Page.sendEvent(CommonEvents.H5_PAGE_CLOSE, null);
                        return;
                    }
                    return;
                }
                if (Nebula.DEBUG) {
                    TestDataUtils.storeJSParams("pageLoad|pushWindowPoint", Long.valueOf(System.currentTimeMillis()));
                }
                if (event.getActivity() instanceof H5Activity) {
                    H5FragmentManager h5FragmentManager = ((H5Activity) event.getActivity()).getH5FragmentManager();
                    boolean enableTranslateAnim = a(H5Utils.getBoolean(oldParams, (String) "isTinyApp", false), H5Utils.getBoolean(oldParams, (String) H5Param.isH5app, false), H5Utils.getString(oldParams, (String) "appId"));
                    h5FragmentManager.addFragment(oldParams, enableTranslateAnim, enableTranslateAnim);
                    count = h5FragmentManager.getFragmentCount();
                } else {
                    H5Bundle h5Bundle = new H5Bundle();
                    h5Bundle.setParams(oldParams);
                    Nebula.getService().startPage(h5Page.getContext(), h5Bundle);
                }
                if (closeCurrentWindow) {
                    H5Log.d(TAG, "do closeCurrentWindow");
                    if (TextUtils.equals(H5Fragment.subtab, H5Utils.getString(h5Page.getParams(), (String) H5Fragment.fragmentType, (String) "normal"))) {
                        AnonymousClass1 r0 = new Runnable() {
                            public void run() {
                                if (H5SessionPlugin.this.a != null) {
                                    H5SessionTabManager h5SessionTabManager = H5SessionPlugin.this.a.getH5SessionTabManager();
                                    for (H5Fragment h5Page : h5SessionTabManager.getTabFragments().values()) {
                                        H5Page page = h5Page.getH5Page();
                                        if (page != null) {
                                            page.sendEvent("h5PageClose_tab", null);
                                        }
                                    }
                                    if (h5SessionTabManager != null) {
                                        h5SessionTabManager.clearTabFragments();
                                    }
                                    if (H5SessionPlugin.this.a != null) {
                                        H5SessionTabBar sessionTabBar = H5SessionPlugin.this.a.getH5SessionTabBar();
                                        if (sessionTabBar != null) {
                                            sessionTabBar.setHasShowTab(false);
                                        }
                                    }
                                }
                            }
                        };
                        H5Utils.runOnMain(r0, 500);
                    } else {
                        final H5Page h5Page2 = h5Page;
                        AnonymousClass2 r02 = new Runnable() {
                            public void run() {
                                if (h5Page2 != null) {
                                    h5Page2.sendEvent(CommonEvents.H5_PAGE_CLOSE, null);
                                }
                            }
                        };
                        H5Utils.runOnMain(r02, 500);
                    }
                }
                H5Log.d(TAG, "count:" + count);
                if (Nebula.DEBUG && count >= 5) {
                    H5Environment.showToast(event.getActivity(), H5Environment.getResources().getString(R.string.h5_sessionwarningpart1) + count + H5Environment.getResources().getString(R.string.h5_sessionwarningpart2), 1);
                }
                if (popToIndex != Integer.MIN_VALUE) {
                    final JSONObject jSONObject = param;
                    final int i = popToIndex;
                    AnonymousClass3 r03 = new Runnable() {
                        public void run() {
                            H5SessionPlugin.this.a(jSONObject, i, true, closeAllWindow);
                        }
                    };
                    H5Utils.runOnMain(r03, 500);
                    return;
                }
                return;
            }
            return;
        }
        H5Log.d(TAG, "can not pushwindow in a transparent window");
        if (h5BridgeContext != null) {
            h5BridgeContext.sendError(2, (String) "透明窗口不能使用pushWindow");
        }
    }

    /* access modifiers changed from: private */
    public boolean a(JSONObject param, int minIndex, boolean isPush, boolean closeAllWindow) {
        if (this.a == null) {
            return false;
        }
        Stack sessionPages = this.a.getPages();
        if (sessionPages == null) {
            return false;
        }
        Stack sessionPagesWithOutPrerender = Nebula.getSessionPagesWithOutPrerender(sessionPages);
        int listSize = sessionPagesWithOutPrerender.size();
        if (minIndex < 0) {
            minIndex = (listSize + minIndex) - (isPush ? 2 : 1);
        }
        if (minIndex < 0 || minIndex >= listSize - 1) {
            H5Log.e((String) TAG, (String) "invalid page index");
            H5Page page = sessionPagesWithOutPrerender.peek();
            if (!(page == null || page.getContext() == null || page.getContext().getContext() == null)) {
                Activity activity = (Activity) page.getContext().getContext();
                if (Nebula.needPageKeepAlive(page, activity)) {
                    Nebula.doKeepAlive(activity, page.getParams());
                }
            }
            return true;
        }
        String dataStr = null;
        Object object = H5Utils.getValue(param, (String) "data", new Object());
        if (object != null) {
            try {
                if (object instanceof JSONObject) {
                    dataStr = ((JSONObject) object).toJSONString();
                } else if (object instanceof JSONArray) {
                    dataStr = ((JSONArray) object).toJSONString();
                } else if (object instanceof String) {
                    dataStr = (String) object;
                } else if (object instanceof Boolean) {
                    dataStr = String.valueOf((Boolean) object);
                } else if (object instanceof Double) {
                    dataStr = String.valueOf((Double) object);
                } else if (object instanceof Long) {
                    dataStr = String.valueOf((Long) object);
                } else if (object instanceof BigDecimal) {
                    dataStr = String.valueOf(((BigDecimal) object).doubleValue());
                } else if (object instanceof Integer) {
                    dataStr = String.valueOf((Integer) object);
                }
            } catch (Throwable t) {
                H5Log.e(TAG, "catch exception ", t);
            }
        }
        if (!TextUtils.isEmpty(dataStr)) {
            this.a.getData().set(H5Param.H5_SESSION_POP_PARAM, dataStr);
        }
        int maxIndex = listSize - (isPush ? 2 : 1);
        ArrayList arrayList = new ArrayList();
        if (closeAllWindow) {
            minIndex = -1;
            maxIndex = listSize - 2;
        } else if (!(this.a == null || this.a.getH5SessionTabManager() == null)) {
            int tabCount = this.a.getH5SessionTabManager().countTabFragments();
            if (tabCount > 0 && minIndex + 1 <= tabCount) {
                minIndex = tabCount - 1;
            }
        }
        for (int index = minIndex + 1; index <= maxIndex; index++) {
            arrayList.add((H5PageImpl) sessionPagesWithOutPrerender.get(index));
        }
        if (closeAllWindow) {
            H5SessionTabManager h5SessionTabManager = this.a.getH5SessionTabManager();
            if (h5SessionTabManager != null) {
                h5SessionTabManager.clearTabFragments();
            }
            H5SessionTabBar sessionTabBar = this.a.getH5SessionTabBar();
            if (sessionTabBar != null) {
                sessionTabBar.setHasShowTab(false);
            }
        }
        for (int index2 = 0; index2 < arrayList.size(); index2++) {
            H5PageImpl page2 = (H5PageImpl) arrayList.get(index2);
            if (!closeAllWindow || !TextUtils.equals(H5Fragment.subtab, H5Utils.getString(page2.getParams(), (String) H5Fragment.fragmentType, (String) "normal"))) {
                page2.sendEvent(CommonEvents.H5_PAGE_CLOSE, null);
            } else {
                page2.sendEvent("h5PageClose_tab", null);
            }
        }
        return false;
    }

    private void d(H5Event event, H5BridgeContext bridgeContext) {
        if (this.a == null) {
            H5Log.w(TAG, "getSceneStackInfo : h5session is null !");
            return;
        }
        H5CoreNode target = event.getTarget();
        if (!(target instanceof H5Page)) {
            H5Log.w(TAG, "getSceneStackInfo : invalid target!");
            return;
        }
        H5Page h5Page = (H5Page) target;
        Stack h5pages = this.a.getPages();
        if (h5pages == null) {
            H5Log.w(TAG, "getSceneStackInfo : H5Page Stack is null !");
            return;
        }
        Stack sessionPagesWithOutPrerender = Nebula.getSessionPagesWithOutPrerender(h5pages);
        int count = sessionPagesWithOutPrerender.size();
        int currentIndex = sessionPagesWithOutPrerender.indexOf(h5Page);
        H5Page realTopPage = null;
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service != null) {
            H5BaseFragment h5BaseFragment = h5Service.getTopH5BaseFragment();
            if (!(h5BaseFragment == null || h5BaseFragment.getH5Page() == null)) {
                realTopPage = h5BaseFragment.getH5Page();
            }
        }
        JSONArray detailArray = new JSONArray();
        Iterator it = sessionPagesWithOutPrerender.iterator();
        while (it.hasNext()) {
            JSONObject object = new JSONObject();
            String url = ((H5Page) it.next()).getUrl();
            object.put((String) "url", (Object) url);
            if (realTopPage == null || !TextUtils.equals(realTopPage.getUrl(), url)) {
                object.put((String) "isTop", (Object) Boolean.valueOf(false));
            } else {
                object.put((String) "isTop", (Object) Boolean.valueOf(true));
            }
            detailArray.add(object);
        }
        JSONObject result = new JSONObject();
        result.put((String) NewHtcHomeBadger.COUNT, (Object) Integer.valueOf(count));
        result.put((String) "currentIndex", (Object) Integer.valueOf(currentIndex));
        result.put((String) "detail", (Object) detailArray);
        H5Log.d(TAG, "count = " + count + " , currentIndex = " + currentIndex + " detail :" + detailArray);
        bridgeContext.sendBridgeResult(result);
    }

    public synchronized boolean isFastClick() {
        boolean isFastClick;
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.d > ((long) this.e)) {
            isFastClick = false;
        } else {
            isFastClick = true;
        }
        this.d = currentTime;
        return isFastClick;
    }

    private static boolean a(String openUrl) {
        if (TextUtils.isEmpty(openUrl)) {
            return false;
        }
        try {
            JSONArray domainArray = H5Utils.parseArray(H5Environment.getConfigWithProcessCache("h5_documentRefererWhitelist"));
            if (domainArray == null) {
                return false;
            }
            for (int i = 0; i < domainArray.size(); i++) {
                if (H5PatternHelper.matchRegex(domainArray.getString(i), openUrl)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable t) {
            H5Log.e(TAG, "exception detail.", t);
            return false;
        }
    }

    private void a(final H5PageData pageData, final Bundle param) {
        if (NebulaUtil.enableRecordStartupParams()) {
            H5ThreadPoolFactory.getSingleThreadExecutor().execute(new Runnable() {
                public void run() {
                    if (pageData != null && param != null && !param.isEmpty()) {
                        H5LogUtil.logNebulaTech(H5LogData.seedId("H5_TRANS_PUSHWINDOW").param2().addMapParam(NebulaUtil.getStartupParamsMap(param)).param4().addUniteParam(pageData));
                    }
                }
            });
        }
    }

    private static boolean a(boolean isTiny, boolean isH5, String appId) {
        JSONObject animConfig = H5Environment.getConfigJSONObject("h5_enablePushWindowAnim");
        JSONArray array = H5Utils.getJSONArray(animConfig, "appList", null);
        if (array != null && array.size() > 0) {
            for (int i = 0; i < array.size(); i++) {
                if (appId.equals(array.getString(i))) {
                    return true;
                }
            }
        }
        boolean enableTiny = H5Utils.getBoolean(animConfig, (String) "enableTiny", false);
        if (isTiny && enableTiny) {
            return true;
        }
        boolean enableH5 = H5Utils.getBoolean(animConfig, (String) "enableH5", false);
        if (isTiny || !isH5 || !enableH5) {
            return false;
        }
        return true;
    }
}
