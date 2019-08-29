package com.alipay.mobile.tinyappcommon.dynamicpanel;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.antui.dialog.AUListDialog;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.h5plugin.TinyAppStoragePlugin;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.callback.H5SimpleRpcListener;
import com.alipay.mobile.nebula.provider.H5ActionSheetProvider;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.provider.H5TinyAppProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5NavMenuItem;
import com.alipay.mobile.nebula.view.IH5TinyPopMenu;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.tinyapp.R;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcommon.api.TinyAppFavoriteService;
import com.alipay.mobile.tinyappcommon.api.TinyAppMixActionService;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.alipay.mobile.tinyappcommon.b;
import com.alipay.mobile.tinyappcommon.h5plugin.ApiDynamicPermissionPlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.H5VConsolePlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.TinyAppBackHomePlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.TinyAppSharePlugin;
import com.alipay.mobile.tinyappcommon.remotedebug.TinyAppRemoteDebugInterceptorImpl;
import com.alipay.mobile.tinyappcommon.storage.H5SharedPreferenceStorage;
import com.alipay.mobile.tinyappcommon.storage.TinyAppStorage;
import com.alipay.mobile.tinyappcommon.utils.WalletTinyappUtils;
import com.alipay.mobile.tinyappcommon.view.TitleBarRightButtonView;
import com.alipay.mobile.worker.remotedebug.RemoteDebugConstants;
import com.autonavi.minimap.ajx3.util.AjxDebugConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

public class H5TinyPopMenu implements IH5TinyPopMenu {
    private static final String ABOUT = "关于";
    private static final String ABOUT_APPID = "66666718";
    private static final String ABOUT_ID = "1001";
    private static final String ADD_TO_DESKTOP = "添加到桌面";
    private static final String ADD_TO_DESKTOP_ACTION = "setShortCut";
    private static final String ADD_TO_DESKTOP_CALLBACK = "function (res) {\n      var tipKey = 'tip%s'\n      if (navigator.userAgent && navigator.userAgent.indexOf('iPhone') < 0) {\n        AlipayJSBridge.call('getAPDataStorage', {\n          type: \"user\",\n          business: \"setShortCut\",\n          key: tipKey,\n        }, function (result) {\n          if (result.data != '1') {\n            AlipayJSBridge.call('setAPDataStorage', {\n              type: \"user\",\n              business: \"setShortCut\",\n              key: tipKey,\n              value: '1'\n            }, function (result) {\n            });\n            AlipayJSBridge.call('alert', {\n              title: '提示',\n              message: '已尝试添加到桌面，如果在桌面未能找到该小程序的图标，请检查系统权限设置中是否开启了“创建桌面快捷方式”功能 ',\n              button: '确定'\n            }, function (e) {\n            });\n          } else {\n            if (res && res.error == 6) {\n              AlipayJSBridge.call('toast', {\n                content: '%s快捷方式已存在',\n                duration: 2000\n              }, function () {\n              });\n            } else if (res && res.error == 7) {\n              AlipayJSBridge.call('toast', {\n                content: '已取消添加',\n                duration: 2000\n              }, function () {\n              });\n            } else if (res && res.success) {\n              AlipayJSBridge.call('toast', {\n                content: '已添加',\n                duration: 2000\n              }, function () {\n              });\n            }\n          }\n        });\n      }\n    }";
    private static final String ADD_TO_DESKTOP_ID = "1004";
    private static final String ADD_TO_DESKTOP_PARAMS = "{appId:'%s',appName:'%s',iconBitmap:'%s'}";
    private static final String BACK_TO_HOME = "返回首页";
    public static final String BACK_TO_HOME_ACTION = "onBackHomeClick";
    private static final String BACK_TO_HOME_ID = "BACK_TO_HOME";
    private static final String CANCEL_FAVORITE = "取消收藏";
    private static final String CANCEL_FAVORITE_ID = "1011";
    public static final String CLOSE_PERFORMANCE_ID = "CLOSE_PERFORMANCE_ID";
    public static final String CLOSE_PERFORMANCE_PANEL = "关闭性能监控面板";
    public static final String CLOSE_VCONSOLE = "关闭调试";
    public static final String CLOSE_VCONSOLE_ID = "CLOSE_VCONSOLE_ID";
    public static final String DEVELOPER_CUSTOM_MENU = "setCustomPopMenu";
    private static final String FAVORITE = "收藏";
    private static final String FAVORITE_ID = "1005";
    private static final String FEED_BACK = "反馈";
    private static final String FEED_BACK_APPID = "2019031363544395";
    private static final String FEED_BACK_ID = "1012";
    private static final String FEED_BACK_QUERY = "appid=%s&appname=%s&appversion=%s&alipayid=%s&pageid=%s";
    public static final String HIDE_TITLE_BAR_BACK_TO_HOME = "hide_title_bar_back_to_home";
    private static final String ON_SHARE_EVENT = "onShare";
    public static final String OPEN_PERFORMANCE_ID = "OPEN_PERFORMANCE_ID";
    public static final String OPEN_PERFORMANCE_PANEL = "打开性能监控面板";
    public static final String OPEN_VCONSOLE = "打开调试";
    public static final String OPEN_VCONSOLE_ID = "OPEN_VCONSOLE_ID";
    private static final String OPTION_MENU_ID = "OPTION_MENU";
    private static final String SHARE = "分享";
    private static final String SHARE_ID = "1002";
    public static final String SHOW_BACK_TO_HOME_TITLE_BAR = "showBackToHomeTitleBar";
    public static final String SHOW_DESKTOP_POP_MENU = "showDesktopMenu";
    public static final String SHOW_FAVORITE_POP_MENU = "showFavoriteMenu";
    public static final String SHOW_SHARE_POP_MENU = "showShareMenu";
    private static final String TAG = "H5TinyPopMenu";
    public static final Object TAG_VIEW = new Object();
    public static final int TAG_VIEW_KEY = R.id.right_btn_container;
    public static final AtomicBoolean shouldShowAdd2Desktop = new AtomicBoolean(true);
    public static final AtomicBoolean shouldShowFavorite = new AtomicBoolean(true);
    /* access modifiers changed from: private */
    public H5ActionSheetProvider actionSheetProvider;
    private volatile String appDesc;
    private volatile String appIcon;
    private volatile String appName;
    /* access modifiers changed from: private */
    public List<String> developerCustomMenu = new ArrayList();
    /* access modifiers changed from: private */
    public Dialog dialog;
    /* access modifiers changed from: private */
    public H5Page h5Page;
    /* access modifiers changed from: private */
    public List<JsInvokeModel> invokeModelList = new ArrayList();
    /* access modifiers changed from: private */
    public volatile boolean isFavorite = false;
    private boolean isFirstPage = false;
    /* access modifiers changed from: private */
    public volatile boolean isQueryFavoriteStatus = false;
    /* access modifiers changed from: private */
    public volatile boolean isRelease = false;
    public String mAboutName;
    public String mAddToDeskTopName;
    /* access modifiers changed from: private */
    public String mAppId;
    private LocalBroadcastManager mBroadcastManager;
    private Context mContext;
    public JsInvokeModel mFavoriteJsInvokeModel;
    public String mFavoriteName;
    /* access modifiers changed from: private */
    public int mFavoritePosition = -1;
    public String mFeedbackName;
    /* access modifiers changed from: private */
    public boolean mH5OptionMenuTextFlag = false;
    private H5EventInterceptPlugin mH5Plugin;
    /* access modifiers changed from: private */
    public boolean mH5ShowOptionMenu = false;
    /* access modifiers changed from: private */
    public final HomeBackControl mHomeBackControl = new HomeBackControl();
    /* access modifiers changed from: private */
    public TinyAppMixActionService mMixActionService = TinyAppService.get().getMixActionService();
    public String mShareName;
    /* access modifiers changed from: private */
    public TinyAppFavoriteService mTinyAppFavoriteService = TinyAppService.get().getTinyAppFavoriteService();
    private BroadcastReceiver mTinyPopMenuReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                try {
                    String action = intent.getAction();
                    H5Log.d(H5TinyPopMenu.TAG, "receive broadcast to hide back to home.");
                    if (TextUtils.equals(H5TinyPopMenu.HIDE_TITLE_BAR_BACK_TO_HOME, action) && TextUtils.equals(intent.getStringExtra("appId"), H5TinyPopMenu.this.mAppId)) {
                        if (H5TinyPopMenu.this.mHomeBackControl != null) {
                            H5TinyPopMenu.this.mHomeBackControl.shouldShowBackHomeLessState(H5TinyPopMenu.this.titleBarTheme);
                        }
                    }
                } catch (Throwable e) {
                    H5Log.e((String) H5TinyPopMenu.TAG, e);
                }
            }
        }
    };
    private List<H5NavMenuItem> menuItemList;
    /* access modifiers changed from: private */
    public ArrayList<String> menuList = new ArrayList<>();
    private OnClickListener onClickListener = new OnClickListener() {
        public void onClick(View v) {
            Object tag = v.getTag();
            if (tag instanceof Integer) {
                int position = ((Integer) tag).intValue();
                JsInvokeModel jsInvokeModel = null;
                if (position >= 0 && position < H5TinyPopMenu.this.invokeModelList.size()) {
                    jsInvokeModel = (JsInvokeModel) H5TinyPopMenu.this.invokeModelList.get(position);
                }
                if (jsInvokeModel == null) {
                    H5Log.e((String) H5TinyPopMenu.TAG, (String) "jsInvokeModel==null");
                    return;
                }
                if (H5TinyPopMenu.BACK_TO_HOME_ID.equals(jsInvokeModel.getMid())) {
                    if (H5TinyPopMenu.this.h5Page != null) {
                        H5TinyPopMenu.this.h5Page.getBridge().sendDataWarpToWeb(H5TinyPopMenu.BACK_TO_HOME_ACTION, null, null);
                    } else {
                        return;
                    }
                } else if ("1002".equals(jsInvokeModel.getMid())) {
                    if (H5Utils.canTransferH5ToTiny(H5TinyPopMenu.this.mAppId)) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put((String) "page", (Object) H5TinyPopMenu.this.getAppUrl());
                        H5TinyPopMenu.this.h5Page.sendEvent(TinyAppSharePlugin.SHARE_TINY_APP_MSG, jsonObject);
                    } else if (H5TinyPopMenu.this.isCanUseNativeShare()) {
                        H5TinyPopMenu.this.doNativeShareAction();
                    } else {
                        H5Log.e((String) H5TinyPopMenu.TAG, (String) "share failed. isCanUseNativeShare is false.");
                    }
                } else if ("1001".equals(jsInvokeModel.getMid())) {
                    H5TinyPopMenu.this.startAbout();
                } else if (H5TinyPopMenu.FEED_BACK_ID.equals(jsInvokeModel.getMid())) {
                    H5TinyPopMenu.this.startFeedBack();
                } else if (H5TinyPopMenu.this.developerCustomMenu == null || !H5TinyPopMenu.this.developerCustomMenu.contains(jsInvokeModel.getMid())) {
                    if (CommonEvents.H5_TITLEBAR_OPTIONS.equals(jsInvokeModel.getMid())) {
                        try {
                            if (!(H5TinyPopMenu.this.h5Page == null || H5TinyPopMenu.this.h5Page.getBridge() == null)) {
                                H5TinyPopMenu.this.h5Page.getBridge().sendToWeb(CommonEvents.H5_TITLEBAR_OPTIONS, JSONObject.parseObject(jsInvokeModel.getParams()), null);
                            }
                        } catch (Throwable e) {
                            H5Log.e((String) H5TinyPopMenu.TAG, "optionMenu error...e=" + e);
                        }
                    } else {
                        if (H5TinyPopMenu.CANCEL_FAVORITE_ID.equals(jsInvokeModel.getMid())) {
                            H5TinyPopMenu.this.removeCancelFavoriteMenuItem();
                        } else if (H5TinyPopMenu.FAVORITE_ID.equals(jsInvokeModel.getMid())) {
                            H5TinyPopMenu.this.mFavoritePosition = H5TinyPopMenu.this.removeFavoriteMenuItem();
                        }
                        H5TinyPopMenu.this.generateNativeJsApiInvoke(jsInvokeModel);
                    }
                } else if (H5TinyPopMenu.this.h5Page != null) {
                    int index = H5TinyPopMenu.this.developerCustomMenu.indexOf(jsInvokeModel.getMid());
                    if (index >= 0 && index < H5TinyPopMenu.this.developerCustomMenu.size()) {
                        JSONObject params = new JSONObject();
                        JSONObject data = new JSONObject();
                        data.put((String) "index", (Object) Integer.valueOf(index));
                        params.put((String) "data", (Object) data);
                        if (H5TinyPopMenu.this.h5Page.getBridge() != null) {
                            H5TinyPopMenu.this.h5Page.getBridge().sendToWeb("customPopMenuClicked", params, null);
                        }
                    }
                }
                H5TinyPopMenu.this.doMonitorLog(jsInvokeModel);
            }
        }
    };
    private TitleBarRightButtonView rightButtonView;
    private boolean showAbout = true;
    private boolean showAddtodesktop = true;
    private boolean showCollect = true;
    private boolean showFeedback = true;
    private boolean showShare = true;
    /* access modifiers changed from: private */
    public TitleBarTheme titleBarTheme = null;

    private class H5EventInterceptPlugin extends H5SimplePlugin {
        private H5EventInterceptPlugin() {
        }

        public void onPrepare(H5EventFilter filter) {
            super.onPrepare(filter);
            filter.addAction(TinyAppBackHomePlugin.SHOW_BACK_HOME);
        }

        public boolean interceptEvent(H5Event event, H5BridgeContext context) {
            if (H5TinyPopMenu.this.h5Page != null && event != null && !TextUtils.isEmpty(H5TinyPopMenu.this.mAppId) && TinyAppBackHomePlugin.SHOW_BACK_HOME.equals(event.getAction())) {
                H5TinyPopMenu.this.mHomeBackControl.shouldShowBackHomeState(H5TinyPopMenu.this.titleBarTheme);
            }
            return super.interceptEvent(event, context);
        }
    }

    class JsInvokeModel {
        private String action;
        private String callback;
        private String mid;
        private String params;

        public JsInvokeModel(String action2, String params2, String callback2, String mid2) {
            this.action = action2;
            this.params = params2;
            this.callback = callback2;
            this.mid = mid2;
        }

        public String getMid() {
            return this.mid;
        }

        public String getAction() {
            return this.action;
        }

        public void setAction(String action2) {
            this.action = action2;
        }

        public String getParams() {
            return this.params;
        }

        public void setParams(String params2) {
            this.params = params2;
        }

        public String getCallback() {
            return this.callback;
        }

        public void setCallback(String callback2) {
            this.callback = callback2;
        }

        public boolean equals(Object object) {
            if (object instanceof JsInvokeModel) {
                return TextUtils.equals(this.mid, ((JsInvokeModel) object).mid);
            }
            return false;
        }
    }

    public enum TitleBarTheme {
        TITLE_BAR_THEME_BLUE,
        TITLE_BAR_THEME_WHITE
    }

    public H5TinyPopMenu() {
        if (H5Utils.getContext() != null) {
            this.mBroadcastManager = LocalBroadcastManager.getInstance(H5Utils.getContext());
            if (!(this.mBroadcastManager == null || this.mTinyPopMenuReceiver == null)) {
                this.mBroadcastManager.registerReceiver(this.mTinyPopMenuReceiver, new IntentFilter(HIDE_TITLE_BAR_BACK_TO_HOME));
            }
        }
        this.mH5Plugin = new H5EventInterceptPlugin();
    }

    /* access modifiers changed from: private */
    public void startAbout() {
        String version = "";
        try {
            H5AppProvider provider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
            if (provider != null) {
                AppInfo info = provider.getAppInfo(this.mAppId);
                if (info != null) {
                    try {
                        JSONObject extObj = JSON.parseObject(info.extend_info_jo);
                        if (extObj != null) {
                            version = extObj.getString(H5AppUtil.package_nick);
                        }
                    } catch (Exception e) {
                        H5Log.w(TAG, "parse extend info 2 json: " + e.getMessage());
                    }
                }
                H5Log.d(TAG, "info: " + info.toString());
            }
            Bundle startParams = new Bundle();
            startParams.putBoolean(MicroApplication.KEY_APP_CLEAR_TOP, false);
            startParams.putString("url", "/index.html#pages/detail/detail");
            startParams.putString("query", "tinyAppId=" + this.mAppId + "&tinyAppScene=ONLINE&page=pages/detail/detail&tinyAppVersion=" + version);
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(null, "66666718", startParams);
        } catch (Exception e2) {
            H5Log.e((String) TAG, "launch about app exception: " + e2.getMessage());
        }
    }

    /* access modifiers changed from: private */
    public void startFeedBack() {
        String query = "";
        try {
            H5AppProvider provider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
            if (provider != null) {
                AppInfo info = provider.getAppInfo(this.mAppId);
                if (info != null) {
                    query = String.format(FEED_BACK_QUERY, new Object[]{this.mAppId, info.name, info.version, WalletTinyappUtils.getUserId(), getCurrentPageUrl()});
                }
            }
            Bundle startParams = new Bundle();
            startParams.putBoolean(MicroApplication.KEY_APP_CLEAR_TOP, false);
            startParams.putString("url", "/index.html#pages/index/index");
            startParams.putString("query", query);
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(null, "2019031363544395", startParams);
        } catch (Exception e) {
            H5Log.e((String) TAG, "launch feedback app exception: " + e.getMessage());
        }
    }

    /* access modifiers changed from: private */
    public boolean isCanUseNativeShare() {
        if (this.mMixActionService == null) {
            return true;
        }
        if (!(TinyappUtils.versionCompare(TinyAppStorage.getInstance().getAppxVersion(this.mAppId), "1.7.4") >= 0) || !this.mMixActionService.isUseNativeShareSwitch()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void doNativeShareAction() {
        if (this.h5Page != null) {
            H5TinyAppProvider h5TinyAppProvider = (H5TinyAppProvider) Nebula.getProviderManager().getProvider(H5TinyAppProvider.class.getName());
            JSONObject param = null;
            if (h5TinyAppProvider != null) {
                param = h5TinyAppProvider.handlerOnShareData(this.h5Page);
            }
            if (param == null) {
                param = new JSONObject();
            }
            param.put((String) "useNativeShare", (Object) Boolean.valueOf(true));
            this.h5Page.getBridge().sendDataWarpToWeb(ON_SHARE_EVENT, param, null);
        }
    }

    /* access modifiers changed from: private */
    public void doMonitorLog(JsInvokeModel jsInvokeModel) {
        markSpmBehavor(jsInvokeModel);
        String seedId = null;
        String panelType = "";
        try {
            if (OPEN_VCONSOLE_ID.equals(jsInvokeModel.getMid()) || CLOSE_VCONSOLE_ID.equals(jsInvokeModel.getMid())) {
                seedId = "TINY_APP_ASSIST_PANEL";
                panelType = "debug";
            } else if (OPEN_PERFORMANCE_ID.equals(jsInvokeModel.getMid()) || CLOSE_PERFORMANCE_ID.equals(jsInvokeModel.getMid())) {
                seedId = "TINY_APP_ASSIST_PANEL";
                panelType = AjxDebugConfig.PERFORMANCE;
            }
            if (TextUtils.isEmpty(seedId)) {
                H5Log.d(TAG, "doMonitorLog.. seedId is null");
                return;
            }
            H5LogProvider logProvider = (H5LogProvider) H5Utils.getH5ProviderManager().getProvider(H5LogProvider.class.getName());
            if (logProvider != null) {
                String extParams = ("appId=" + this.mAppId) + "^";
                if (!TextUtils.isEmpty(panelType)) {
                    extParams = (extParams + "panel=" + panelType) + "^";
                }
                logProvider.h5BehaviorLogger("H5behavior", seedId, null, null, null, null, null, null, null, extParams + "scene=" + TinyappUtils.getScene(this.h5Page), null, 0, "", null);
            }
        } catch (Throwable e) {
            H5Log.e((String) TAG, "doMonitorLog..." + e);
        }
    }

    private void markSpmBehavor(JsInvokeModel jsInvokeModel) {
        String seedId = null;
        try {
            if (FAVORITE_ID.equals(jsInvokeModel.getMid())) {
                seedId = "a192.b5743.c12614.d23119";
            } else if (BACK_TO_HOME_ID.equals(jsInvokeModel.getMid())) {
                seedId = "a192.b5743.c12614.d25586";
            } else if ("1001".equals(jsInvokeModel.getMid())) {
                seedId = "a192.b5743.c12614.d23114";
            } else if (ADD_TO_DESKTOP_ID.equals(jsInvokeModel.getMid())) {
                seedId = "a192.b5743.c12614.d23120";
            } else if ("1002".equals(jsInvokeModel.getMid())) {
                seedId = "a192.b5743.c12614.d23118";
            } else if (OPTION_MENU_ID.equals(jsInvokeModel.getMid())) {
                seedId = "a192.b5743.c12614.d25585";
            }
            if (!TextUtils.isEmpty(seedId)) {
                b.a(seedId, "appId", this.mAppId);
            }
        } catch (Throwable e) {
            H5Log.e((String) TAG, "markSpmBehavor..." + e);
        }
    }

    public void showMenu() {
        if (this.actionSheetProvider != null) {
            shouldShowBackToHome();
            addH5MenuList();
            checkCanShowShareMenuItem();
            removeAddToDesktopMenuItemIfNeed();
            shouldShowFavoriteMenuItem();
            showDeveloperCustomMenu();
            configDebugMenu();
            shouldShowBackToHome();
            showMenuConfig();
            this.actionSheetProvider.setContextAndContent(this.mContext, this.menuList, null, null, this.onClickListener);
            this.dialog = this.actionSheetProvider.getAntUIActionSheet();
            if (this.dialog == null) {
                H5Log.e((String) TAG, (String) "showMenu dialog==null");
                return;
            }
            if (this.dialog instanceof AUListDialog) {
                ((AUListDialog) this.dialog).setMaxItems(6.0f);
            }
            if (this.dialog.isShowing()) {
                this.dialog.cancel();
            }
            this.dialog.show();
        }
        markSpmBehavor(new JsInvokeModel(null, null, null, OPTION_MENU_ID));
    }

    private void showMenuConfig() {
        if (!this.showAddtodesktop) {
            this.menuList.remove(TextUtils.isEmpty(this.mAddToDeskTopName) ? ADD_TO_DESKTOP : this.mAddToDeskTopName);
            this.invokeModelList.remove(new JsInvokeModel(null, null, null, ADD_TO_DESKTOP_ID));
        }
        if (!this.showShare) {
            removeShareMenuItem();
        }
        if (!this.showAbout) {
            removeAboutMenuItem();
        }
        if (!this.showCollect) {
            removeFavoriteMenuItem();
        }
        if (!this.showFeedback) {
            removeFeedbackMenuItem();
        }
    }

    private void removeAddToDesktopMenuItemIfNeed() {
        if (TinyAppMiniServicePlugin.appIsMiniService(this.h5Page) || !shouldShowAddToDesktopMenuItem() || H5Utils.canTransferH5ToTiny(this.mAppId)) {
            this.menuList.remove(TextUtils.isEmpty(this.mAddToDeskTopName) ? ADD_TO_DESKTOP : this.mAddToDeskTopName);
            this.invokeModelList.remove(new JsInvokeModel(null, null, null, ADD_TO_DESKTOP_ID));
        }
    }

    public void onRelease() {
        if (!(this.mBroadcastManager == null || this.mTinyPopMenuReceiver == null)) {
            this.mBroadcastManager.unregisterReceiver(this.mTinyPopMenuReceiver);
        }
        this.isRelease = true;
        if (this.actionSheetProvider != null) {
            this.actionSheetProvider.onRelease();
            this.actionSheetProvider = null;
        }
        this.dialog = null;
        this.h5Page = null;
    }

    public void init(H5Page h5Page2, String rpcData, final Context context, final ViewGroup optionContainer) {
        initView(context);
        setPage(h5Page2);
        registerExitSessionInterceptor();
        if (h5Page2.getPageData() != null) {
            initMenuConfig(h5Page2.getPageData().getAppId());
        }
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                H5TinyPopMenu.this.showCustomRightView(context, optionContainer);
                H5TinyPopMenu.this.mHomeBackControl.showBackToHomeIcon(H5TinyPopMenu.this.titleBarTheme);
            }
        });
    }

    private void registerExitSessionInterceptor() {
        if (this.isFirstPage && this.h5Page != null && "yes".equalsIgnoreCase(H5Utils.getString(this.h5Page.getParams(), (String) "onlyOptionMenu")) && H5Utils.canTransferH5ToTiny(this.mAppId)) {
            H5Session h5Session = this.h5Page.getSession();
            if (h5Session != null) {
                try {
                    H5Log.d(TAG, "registerExitSessionInterceptor...");
                    h5Session.getPluginManager().register((H5Plugin) Class.forName("com.alipay.mobile.tinyappservice.h5plugin.TinyAppExitSessionInterceptPlugin").newInstance());
                } catch (Exception e) {
                    H5Log.e((String) TAG, (String) "init...registerPlugin error");
                }
            }
        }
    }

    public void requestRpc(H5SimpleRpcListener h5SimpleRpcListener, H5Page h5Page2, Context context) {
    }

    public void onSwitchToWhiteTheme() {
        this.titleBarTheme = TitleBarTheme.TITLE_BAR_THEME_WHITE;
        if (this.rightButtonView != null) {
            this.rightButtonView.switchTheme(this.titleBarTheme);
        }
    }

    public void onSwitchToBlueTheme() {
        this.titleBarTheme = TitleBarTheme.TITLE_BAR_THEME_BLUE;
        if (this.rightButtonView != null) {
            this.rightButtonView.switchTheme(this.titleBarTheme);
        }
    }

    public void setPage(H5Page h5Page2) {
        this.h5Page = h5Page2;
        this.mHomeBackControl.setH5Page(h5Page2);
        registerPlugins(h5Page2);
        if (h5Page2 != null && h5Page2.getParams() != null) {
            initMenuList();
            checkDebugAndPerformanceVisible();
        }
    }

    private void initView(Context context) {
        this.mContext = context;
        this.mHomeBackControl.setContext(this.mContext);
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service != null) {
            this.actionSheetProvider = (H5ActionSheetProvider) h5Service.getProviderManager().getProviderUseCache(H5ActionSheetProvider.class.getName(), false);
        }
    }

    private String getCurrentPageUrl() {
        String pageUrl = Uri.parse(getAppUrl()).getFragment();
        if (TextUtils.isEmpty(pageUrl)) {
            return pageUrl;
        }
        int index = pageUrl.indexOf("?");
        if (index >= 0) {
            return pageUrl.substring(0, index);
        }
        return pageUrl;
    }

    /* access modifiers changed from: private */
    public String getAppUrl() {
        String currentUrl = this.h5Page.getUrl();
        if (TextUtils.isEmpty(currentUrl)) {
            return H5Utils.getString(this.h5Page.getParams(), (String) "url");
        }
        return currentUrl;
    }

    private void initMenuList() {
        String appId;
        this.menuList = new ArrayList<>();
        this.invokeModelList = new ArrayList();
        Bundle bundle = this.h5Page.getParams();
        if (TinyAppMiniServicePlugin.appIsMiniService(this.h5Page)) {
            appId = H5Utils.getString(bundle, (String) "parentAppId");
        } else {
            appId = H5Utils.getString(bundle, (String) "MINI-PROGRAM-WEB-VIEW-TAG");
            if (TextUtils.isEmpty(appId)) {
                appId = H5Utils.getString(bundle, (String) "appId");
            }
        }
        this.mAppId = appId;
        this.mHomeBackControl.setAppId(this.mAppId);
        TinyAppStorage.getInstance().setCurrentAppId(appId);
        this.menuList.add(SHARE);
        this.invokeModelList.add(new JsInvokeModel("", "", "", "1002"));
        this.mFavoritePosition = 1;
        addFavoriteMenuItem(this.mFavoritePosition);
        H5AppProvider provider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (provider != null) {
            AppInfo info = provider.getAppInfo(appId);
            if (info != null) {
                String appName2 = info.name;
                String paramStr = String.format(ADD_TO_DESKTOP_PARAMS, new Object[]{appId, appName2, info.icon_url});
                String callbackStr = String.format(ADD_TO_DESKTOP_CALLBACK, new Object[]{appId, appName2});
                this.menuList.add(ADD_TO_DESKTOP);
                this.invokeModelList.add(new JsInvokeModel(ADD_TO_DESKTOP_ACTION, paramStr, callbackStr, ADD_TO_DESKTOP_ID));
            }
        }
        this.menuList.add(FEED_BACK);
        this.invokeModelList.add(new JsInvokeModel("", "", "", FEED_BACK_ID));
        this.menuList.add(ABOUT);
        this.invokeModelList.add(new JsInvokeModel("", "", "", "1001"));
    }

    /* access modifiers changed from: private */
    public void generateNativeJsApiInvoke(JsInvokeModel jsInvokeModel) {
        if (this.h5Page != null) {
            String finalInvoke = String.format("javascript:(function(){function tinyApp_presetPopMenu_ready(callback){if(window.AlipayJSBridge){callback&&callback();}else{document.addEventListener('AlipayJSBridgeReady',callback,false);}}tinyApp_presetPopMenu_ready(AlipayJSBridge.call('%s', %s, %s));})();", new Object[]{jsInvokeModel.getAction(), jsInvokeModel.getParams(), jsInvokeModel.getCallback()});
            H5Log.d(TAG, "generateNativeJsApiInvoke finalInvoke " + finalInvoke);
            if (this.h5Page != null && this.h5Page.getWebView() != null) {
                this.h5Page.getWebView().loadUrl(finalInvoke);
            }
        }
    }

    private void checkCanShowShareMenuItem() {
        try {
            if (TinyAppMiniServicePlugin.appIsMiniService(this.h5Page)) {
                Object showShare2 = this.h5Page == null ? null : this.h5Page.getExtra(SHOW_SHARE_POP_MENU);
                if (showShare2 == null || !(showShare2 instanceof Boolean) || !((Boolean) showShare2).booleanValue()) {
                    H5Log.d(TAG, "checkCanShowShareMenuItem..mini service does not show share");
                    removeShareMenuItem();
                    return;
                }
                H5Log.d(TAG, "checkCanShowShareMenuItem..mini service show share");
            } else if (this.h5Page != null) {
                Object showShare3 = this.h5Page.getExtra(SHOW_SHARE_POP_MENU);
                if (((showShare3 instanceof Boolean) && !((Boolean) showShare3).booleanValue()) || H5Utils.canTransferH5ToTiny(this.mAppId)) {
                    H5Log.d(TAG, "checkCanShowShareMenuItem...DONOT show share menu");
                    removeShareMenuItem();
                }
            }
        } catch (Throwable e) {
            H5Log.e((String) TAG, "checkCanShowShareMenuItem..e=" + e);
        }
    }

    private void showDeveloperCustomMenu() {
        String about;
        try {
            if (this.h5Page != null) {
                List oldDeveloperMenu = this.developerCustomMenu;
                this.developerCustomMenu = (List) this.h5Page.getExtra(DEVELOPER_CUSTOM_MENU);
                if (this.developerCustomMenu != null && !this.developerCustomMenu.isEmpty()) {
                    if (TextUtils.isEmpty(this.mAboutName)) {
                        about = ABOUT;
                    } else {
                        about = this.mAboutName;
                    }
                    if (oldDeveloperMenu != null && oldDeveloperMenu.size() > 0) {
                        for (String developerMenu : oldDeveloperMenu) {
                            int lastIndex = this.menuList.lastIndexOf(developerMenu);
                            if (lastIndex >= 0 && lastIndex < this.menuList.size()) {
                                this.menuList.remove(lastIndex);
                            }
                            int lastIndex2 = this.invokeModelList.lastIndexOf(new JsInvokeModel("", "", "", developerMenu));
                            if (lastIndex2 >= 0 && lastIndex2 < this.invokeModelList.size()) {
                                this.invokeModelList.remove(lastIndex2);
                            }
                        }
                    }
                    for (String developerMenu2 : this.developerCustomMenu) {
                        int aboutIndex = this.menuList.indexOf(about);
                        int aboutInvokeModelIndex = this.invokeModelList.indexOf(new JsInvokeModel("", "", "", "1001"));
                        if (aboutIndex < 0 || aboutIndex >= this.menuList.size()) {
                            this.menuList.add(developerMenu2);
                        } else {
                            this.menuList.add(aboutIndex, developerMenu2);
                        }
                        if (aboutInvokeModelIndex < 0 || aboutInvokeModelIndex >= this.invokeModelList.size()) {
                            this.invokeModelList.add(new JsInvokeModel("", "", "", developerMenu2));
                        } else {
                            this.invokeModelList.add(aboutInvokeModelIndex, new JsInvokeModel("", "", "", developerMenu2));
                        }
                    }
                }
            }
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
        }
    }

    private void removeFeedbackMenuItem() {
        this.menuList.remove(TextUtils.isEmpty(this.mFeedbackName) ? FEED_BACK : this.mFeedbackName);
        this.invokeModelList.remove(new JsInvokeModel(null, null, null, FEED_BACK_ID));
    }

    private void removeShareMenuItem() {
        this.menuList.remove(TextUtils.isEmpty(this.mShareName) ? SHARE : this.mShareName);
        this.invokeModelList.remove(new JsInvokeModel(null, null, null, "1002"));
    }

    private void removeAboutMenuItem() {
        this.menuList.remove(TextUtils.isEmpty(this.mAboutName) ? ABOUT : this.mAboutName);
        this.invokeModelList.remove(new JsInvokeModel(null, null, null, "1001"));
    }

    /* access modifiers changed from: private */
    public void shouldShowFavoriteMenuItem() {
        int position;
        int position2;
        if (this.h5Page != null) {
            Object showFavorite = this.h5Page.getExtra("showFavoriteMenu");
            if ((showFavorite instanceof Boolean) && !((Boolean) showFavorite).booleanValue()) {
                H5Log.d(TAG, "checkCanShowFavoriteMenuItem...DONOT show favorite menu");
                removeFavoriteMenuItem();
                removeCancelFavoriteMenuItem();
            } else if (!shouldShowFavorite.get() || H5Utils.canTransferH5ToTiny(this.mAppId)) {
                removeFavoriteMenuItem();
                removeCancelFavoriteMenuItem();
            } else {
                if (!this.isFavorite && !this.invokeModelList.contains(new JsInvokeModel("", "", "", FAVORITE_ID))) {
                    removeCancelFavoriteMenuItem();
                    if (this.mFavoritePosition >= 0) {
                        if (this.mFavoritePosition == 0 && this.menuList.contains(BACK_TO_HOME)) {
                            this.mFavoritePosition = 1;
                        }
                        addFavoriteMenuItem(this.mFavoritePosition);
                    } else {
                        if (this.menuList.size() <= 0 || this.invokeModelList.size() <= 0) {
                            position2 = 0;
                        } else {
                            position2 = 1;
                        }
                        addFavoriteMenuItem(position2);
                    }
                }
                if (this.isFavorite && !this.invokeModelList.contains(new JsInvokeModel("", "", "", CANCEL_FAVORITE_ID))) {
                    removeFavoriteMenuItem();
                    if (this.mFavoritePosition >= 0) {
                        if (this.mFavoritePosition == 0 && this.menuList.contains(BACK_TO_HOME)) {
                            this.mFavoritePosition = 1;
                        }
                        addCancelFavoriteMenuItem(this.mFavoritePosition);
                        return;
                    }
                    if (this.menuList.size() <= 0 || this.invokeModelList.size() <= 0) {
                        position = 0;
                    } else {
                        position = 1;
                    }
                    addCancelFavoriteMenuItem(position);
                }
            }
        }
    }

    private void addCancelFavoriteMenuItem(int position) {
        if (position >= 0) {
            try {
                if (position <= this.menuList.size() && position <= this.invokeModelList.size()) {
                    this.menuList.add(position, CANCEL_FAVORITE);
                    this.invokeModelList.add(position, new JsInvokeModel(ApiDynamicPermissionPlugin.INTERNAL_API, "{method: 'cancelKeepFavorite'}", "function(result) {if ('success' in result) {if (result.success) {AlipayJSBridge.call('toast', {content: '已取消收藏'});} else {AlipayJSBridge.call('toast', {content: result.resultMsg});}} else {AlipayJSBridge.call('toast', {content: '取消收藏失败'});}}", CANCEL_FAVORITE_ID));
                }
            } catch (Throwable e) {
                H5Log.e((String) TAG, e);
            }
        }
    }

    private void addFavoriteMenuItem(int position) {
        if (position >= 0) {
            try {
                if (position <= this.menuList.size() && position <= this.invokeModelList.size()) {
                    this.menuList.add(position, TextUtils.isEmpty(this.mFavoriteName) ? FAVORITE : this.mFavoriteName);
                    if (this.mFavoriteJsInvokeModel != null) {
                        this.invokeModelList.add(position, this.mFavoriteJsInvokeModel);
                    } else {
                        this.invokeModelList.add(position, new JsInvokeModel(ApiDynamicPermissionPlugin.INTERNAL_API, "{method: 'add2Favorite'}", "function(result) {if ('success' in result) {if (result.success) {AlipayJSBridge.call('toast', {content: '收藏成功，可在我的小程序中查看'});} else {AlipayJSBridge.call('toast', {content: result.resultMsg});}} else {AlipayJSBridge.call('toast', {content: '收藏失败，请稍后再试'});}}", FAVORITE_ID));
                    }
                }
            } catch (Throwable e) {
                H5Log.e((String) TAG, e);
            }
        }
    }

    private boolean shouldShowAddToDesktopMenuItem() {
        if (this.h5Page == null) {
            return false;
        }
        Object showAddToDesktop = this.h5Page.getExtra("showDesktopMenu");
        if (!(showAddToDesktop instanceof Boolean) || ((Boolean) showAddToDesktop).booleanValue()) {
            return shouldShowAdd2Desktop.get();
        }
        H5Log.d(TAG, "checkCanShowAddToDesktopMenuItem...DONOT show add2desktop menu");
        return false;
    }

    /* access modifiers changed from: private */
    public int removeFavoriteMenuItem() {
        String name = TextUtils.isEmpty(this.mFavoriteName) ? FAVORITE : this.mFavoriteName;
        int favoriteNamePosition = this.menuList.indexOf(name);
        int favoriteInvokePosition = this.invokeModelList.indexOf(new JsInvokeModel(null, null, null, FAVORITE_ID));
        this.menuList.remove(name);
        this.invokeModelList.remove(new JsInvokeModel(null, null, null, FAVORITE_ID));
        if (favoriteNamePosition == favoriteInvokePosition) {
            return favoriteNamePosition;
        }
        return -1;
    }

    /* access modifiers changed from: private */
    public void removeCancelFavoriteMenuItem() {
        this.menuList.remove(CANCEL_FAVORITE);
        this.invokeModelList.remove(new JsInvokeModel(null, null, null, CANCEL_FAVORITE_ID));
    }

    /* access modifiers changed from: protected */
    public boolean shouldShowDebugMenu() {
        return TinyappUtils.isDebugVersion(this.h5Page) && H5SharedPreferenceStorage.getInstance().getAssistantPanelSwitch() && !TinyAppRemoteDebugInterceptorImpl.getInstance().isRemoteDebugConnected(this.mAppId);
    }

    private void configDebugMenu() {
        if (!TinyAppMiniServicePlugin.appIsMiniService(this.h5Page) && shouldShowDebugMenu()) {
            configDebugPanelMenu();
        }
    }

    private void configDebugPanelMenu() {
        boolean currentState = H5SharedPreferenceStorage.getInstance().getVConsoleVisible(this.mAppId);
        H5Log.d(TAG, "show debug menu...vconsole=" + currentState);
        if (currentState) {
            if (this.menuList.contains(OPEN_VCONSOLE)) {
                this.menuList.remove(OPEN_VCONSOLE);
                this.invokeModelList.remove(new JsInvokeModel(null, null, null, OPEN_VCONSOLE_ID));
            }
            if (!this.menuList.contains("关闭调试")) {
                this.menuList.add("关闭调试");
                this.invokeModelList.add(new JsInvokeModel(H5VConsolePlugin.SHOULD_SHOW_VCONSOLE_BTN, "{showBtn: false}", null, CLOSE_VCONSOLE_ID));
                return;
            }
            return;
        }
        if (this.menuList.contains("关闭调试")) {
            this.menuList.remove("关闭调试");
            this.invokeModelList.remove(new JsInvokeModel(null, null, null, CLOSE_VCONSOLE_ID));
        }
        if (!this.menuList.contains(OPEN_VCONSOLE)) {
            this.menuList.add(OPEN_VCONSOLE);
            this.invokeModelList.add(new JsInvokeModel(H5VConsolePlugin.SHOULD_SHOW_VCONSOLE_BTN, "{showBtn: true}", null, OPEN_VCONSOLE_ID));
        }
    }

    private void configPerformancePanelMenu() {
        if (H5SharedPreferenceStorage.getInstance().getPerformancePanelVisible(this.mAppId)) {
            if (this.menuList.contains(OPEN_PERFORMANCE_PANEL)) {
                this.menuList.remove(OPEN_PERFORMANCE_PANEL);
                this.invokeModelList.remove(new JsInvokeModel(null, null, null, OPEN_PERFORMANCE_ID));
            }
            if (this.menuList.contains(CLOSE_PERFORMANCE_PANEL)) {
                this.menuList.remove(CLOSE_PERFORMANCE_PANEL);
                this.invokeModelList.remove(new JsInvokeModel(null, null, null, CLOSE_PERFORMANCE_ID));
            }
            this.menuList.add(CLOSE_PERFORMANCE_PANEL);
            this.invokeModelList.add(new JsInvokeModel("hidePerformancePanel", bny.c, null, CLOSE_PERFORMANCE_ID));
            return;
        }
        if (this.menuList.contains(CLOSE_PERFORMANCE_PANEL)) {
            this.menuList.remove(CLOSE_PERFORMANCE_PANEL);
            this.invokeModelList.remove(new JsInvokeModel(null, null, null, CLOSE_PERFORMANCE_ID));
        }
        if (this.menuList.contains(OPEN_PERFORMANCE_PANEL)) {
            this.menuList.remove(OPEN_PERFORMANCE_PANEL);
            this.invokeModelList.remove(new JsInvokeModel(null, null, null, OPEN_PERFORMANCE_ID));
        }
        this.menuList.add(OPEN_PERFORMANCE_PANEL);
        this.invokeModelList.add(new JsInvokeModel("showPerformancePanel", bny.c, null, OPEN_PERFORMANCE_ID));
    }

    private void checkDebugAndPerformanceVisible() {
        if (this.h5Page != null) {
            try {
                Stack h5Pages = this.h5Page.getSession().getPages();
                if (h5Pages != null && h5Pages.size() == 1) {
                    H5Log.d(TAG, "checkDebugAndPerformanceVisible is First Page");
                    this.isFirstPage = true;
                }
                if (this.isFirstPage && !"true".equalsIgnoreCase(H5Utils.getString(this.h5Page.getParams(), (String) RemoteDebugConstants.IS_REMOTE_DEBUG_MODE))) {
                    checkToggleButtonVisible();
                    checkPerformancePanelVisible();
                }
            } catch (Throwable e) {
                H5Log.e((String) TAG, "checkDebugAndPerformanceVisible...e=" + e);
            }
        }
    }

    private void checkToggleButtonVisible() {
        boolean currentState = H5SharedPreferenceStorage.getInstance().getVConsoleVisible(this.mAppId);
        H5Log.d(TAG, "checkToggleButtonVisible...state=" + currentState);
        if (currentState && TinyappUtils.isDebugVersion(this.h5Page)) {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5TinyPopMenu.this.generateNativeJsApiInvoke(new JsInvokeModel(H5VConsolePlugin.SHOW_TOGGLE_BUTTON, null, null, null));
                }
            }, 300);
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5Page h5Page = TinyappUtils.getTopH5Page();
                    if (h5Page != null) {
                        h5Page.sendEvent(TinyAppStoragePlugin.SEND_TINY_LOCAL_STORAGE_TO_IDE, null);
                    }
                }
            }, 2000);
        }
    }

    private void checkPerformancePanelVisible() {
        boolean currentState = H5SharedPreferenceStorage.getInstance().getPerformancePanelVisible(this.mAppId);
        H5Log.d(TAG, "checkPerformancePanelVisible...state=" + currentState);
        if (currentState) {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5TinyPopMenu.this.generateNativeJsApiInvoke(new JsInvokeModel("showPerformancePanel", bny.c, null, null));
                }
            }, 510);
        }
    }

    /* access modifiers changed from: private */
    public JSONArray getRecentUseTinyAppList() {
        if (this.mMixActionService != null) {
            return this.mMixActionService.getRecentUserTinyAppList();
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void showCustomRightView(final Context context, final ViewGroup optionContainer) {
        if (context != null && optionContainer != null) {
            for (int i = 0; i < optionContainer.getChildCount(); i++) {
                View child = optionContainer.getChildAt(i);
                if (child != null) {
                    child.setVisibility(8);
                }
            }
            initTitleBarTheme();
            H5Log.d(TAG, "init..optionContainer=" + optionContainer);
            this.rightButtonView = new TitleBarRightButtonView(context, this.titleBarTheme);
            this.rightButtonView.setOptionMenuOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (!H5Utils.canTransferH5ToTiny(H5TinyPopMenu.this.mAppId)) {
                        H5TinyPopMenu.this.asyncShowMenu();
                    } else if (H5TinyPopMenu.this.mH5OptionMenuTextFlag || !H5TinyPopMenu.this.mH5ShowOptionMenu) {
                        H5TinyPopMenu.this.asyncShowMenu();
                    } else {
                        JSONObject param = new JSONObject();
                        param.put((String) "fromMenu", (Object) Boolean.valueOf(true));
                        param.put((String) "index", (Object) Integer.valueOf(0));
                        JSONObject data = new JSONObject();
                        data.put((String) "data", (Object) param);
                        if (!(H5TinyPopMenu.this.h5Page == null || H5TinyPopMenu.this.h5Page.getBridge() == null)) {
                            H5TinyPopMenu.this.h5Page.getBridge().sendToWeb(CommonEvents.H5_TITLEBAR_OPTIONS, data, null);
                        }
                    }
                    H5Log.d(H5TinyPopMenu.TAG, "OptionMenuClick");
                }
            });
            this.rightButtonView.setCloseButtonOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (TinyappUtils.isRemoteDebugConnected(H5TinyPopMenu.this.mAppId)) {
                        H5Log.d(H5TinyPopMenu.TAG, "onCloseButtonClick...exitRemoteDebug");
                        TinyAppRemoteDebugInterceptorImpl.getInstance().exitDebugMode();
                    } else if (H5TinyPopMenu.this.h5Page != null) {
                        H5TinyPopMenu.this.h5Page.sendEvent(CommonEvents.EXIT_SESSION, null);
                    }
                }
            });
            this.rightButtonView.setCloseButtonOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View v) {
                    if (H5TinyPopMenu.this.mMixActionService == null || H5TinyPopMenu.this.mMixActionService.shouldLongLickShowPanel()) {
                        H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                            public void run() {
                                try {
                                    final JSONArray recentUseList = H5TinyPopMenu.this.getRecentUseTinyAppList();
                                    if (recentUseList == null || recentUseList.size() <= 1) {
                                        H5Log.d(H5TinyPopMenu.TAG, "recent use list is empty");
                                    } else {
                                        H5Utils.runOnMain(new Runnable() {
                                            public void run() {
                                                if (H5TinyPopMenu.this.h5Page != null && optionContainer != null && context != null && !TextUtils.isEmpty(H5TinyPopMenu.this.mAppId) && !H5TinyPopMenu.this.isRelease) {
                                                    com.alipay.mobile.tinyappcommon.view.b popWindow = new com.alipay.mobile.tinyappcommon.view.b(context, recentUseList);
                                                    popWindow.a(H5TinyPopMenu.this.mAppId);
                                                    popWindow.a(H5TinyPopMenu.this.h5Page);
                                                    popWindow.a((View) optionContainer);
                                                }
                                            }
                                        });
                                    }
                                } catch (Throwable e) {
                                    H5Log.e((String) H5TinyPopMenu.TAG, e);
                                }
                            }
                        });
                    } else if (H5TinyPopMenu.this.h5Page != null) {
                        H5TinyPopMenu.this.h5Page.sendEvent(CommonEvents.EXIT_SESSION, null);
                    }
                    return true;
                }
            });
            float density = TinyappUtils.getDensity(context);
            LayoutParams lp = new LayoutParams(-2, -1);
            lp.gravity = 16;
            lp.topMargin = (int) (8.0f * density);
            lp.bottomMargin = (int) (8.0f * density);
            lp.leftMargin = (int) (12.0f * density);
            lp.rightMargin = (int) (2.0f * density);
            optionContainer.addView(this.rightButtonView, lp);
            this.rightButtonView.setTag(TAG_VIEW);
            this.rightButtonView.setTag(TAG_VIEW_KEY, this);
        }
    }

    private void initTitleBarTheme() {
        if (this.h5Page != null && this.titleBarTheme == null) {
            int titleBarColor = H5Utils.getInt(this.h5Page.getParams(), (String) H5Param.LONG_TITLE_BAR_COLOR, -16777216) | -16777216;
            if (titleBarColor == -16777216) {
                return;
            }
            if (titleBarColor != -1) {
                this.titleBarTheme = TitleBarTheme.TITLE_BAR_THEME_WHITE;
            } else {
                this.titleBarTheme = TitleBarTheme.TITLE_BAR_THEME_BLUE;
            }
        }
    }

    private void shouldShowBackToHome() {
        if (this.h5Page != null && !TinyAppMiniServicePlugin.appIsMiniService(this.h5Page)) {
            Object showBackHome = this.h5Page.getExtra(TinyAppBackHomePlugin.SHOW_BACK_HOME);
            Object showBackToHomepage = this.h5Page.getExtra(TinyAppBackHomePlugin.SHOW_BACK_TO_HOMEPAGE);
            if ((showBackHome instanceof Boolean) && ((Boolean) showBackHome).booleanValue()) {
                showBackToHome();
            }
            if ((showBackToHomepage instanceof Boolean) && ((Boolean) showBackToHomepage).booleanValue()) {
                showBackToHome();
            }
        }
    }

    private void showBackToHome() {
    }

    public void fireShareEvent(H5Event event, H5BridgeContext context) {
        if (this.h5Page == null || this.invokeModelList == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
        } else if (!this.h5Page.equals(event.getTarget())) {
            context.sendError(event, Error.UNKNOWN_ERROR);
        } else {
            for (JsInvokeModel jsInvokeModel : this.invokeModelList) {
                if ("1002".equals(jsInvokeModel.getMid())) {
                    if (isCanUseNativeShare()) {
                        doNativeShareAction();
                    } else {
                        generateNativeJsApiInvoke(jsInvokeModel);
                    }
                    doMonitorLog(jsInvokeModel);
                    return;
                }
            }
        }
    }

    public void setH5MenuList(List<H5NavMenuItem> menuItemList2, boolean showPopMenu) {
        H5Log.d(TAG, "setH5MenuList...");
        this.menuItemList = menuItemList2;
        if (showPopMenu) {
            asyncShowMenu();
        }
    }

    public void setH5ShowOptionMenuFlag() {
        H5Log.d(TAG, "setH5ShowOptionMenuFlag");
        this.mH5ShowOptionMenu = true;
    }

    public void setH5OptionMenuTextFlag() {
        H5Log.d(TAG, "setH5OptionMenuTextFlag");
        this.mH5OptionMenuTextFlag = true;
    }

    public void setTitleBarRightButtonViewShow(boolean canShow) {
        if (this.rightButtonView != null) {
            if (canShow) {
                this.rightButtonView.setVisibility(0);
            } else {
                this.rightButtonView.setVisibility(8);
            }
        }
    }

    private void addH5MenuList() {
        String action;
        String mid;
        if (this.menuItemList != null && !this.menuItemList.isEmpty()) {
            if (!H5Utils.canTransferH5ToTiny(this.mAppId)) {
                H5Log.d(TAG, "addH5MenuList...transfer h5 to tiny not open");
                return;
            }
            for (H5NavMenuItem item : this.menuItemList) {
                if (!TextUtils.isEmpty(item.name)) {
                    JSONObject data = new JSONObject();
                    data.put((String) "name", (Object) item.name);
                    data.put((String) "tag", (Object) item.tag);
                    data.put((String) "title", (Object) item.name);
                    data.put((String) H5Param.POP_MENU_TYPE, (Object) "popmenu");
                    if (!this.menuList.contains(item.name)) {
                        this.menuList.add(item.name);
                        if (CommonEvents.H5_TITLEBAR_OPTIONS.equals(item.tag)) {
                            JSONObject param = new JSONObject();
                            param.put((String) "fromMenu", (Object) Boolean.valueOf(true));
                            param.put((String) "index", (Object) Integer.valueOf(0));
                            data.put((String) "data", (Object) param);
                            action = CommonEvents.H5_TITLEBAR_OPTIONS;
                            mid = CommonEvents.H5_TITLEBAR_OPTIONS;
                        } else {
                            action = CommonEvents.H5_TOOLBAR_MENU_BT;
                            mid = item.name;
                        }
                        this.invokeModelList.add(new JsInvokeModel(action, data.toJSONString(), null, mid));
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void asyncShowMenu() {
        showMenu();
        queryFavoriteStatus(this.h5Page);
    }

    private void initMenuConfig(String appid) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = true;
        if (!TextUtils.isEmpty(appid)) {
            H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (provider != null) {
                JSONArray configAbout = getMenuItemConfigByName("config_menu_about", provider);
                JSONArray configShare = getMenuItemConfigByName("config_menu_share", provider);
                JSONArray configCollect = getMenuItemConfigByName("config_menu_collect", provider);
                JSONArray configADD2Desk = getMenuItemConfigByName("config_menu_addtodesktop", provider);
                JSONArray configFeedBack = getMenuItemConfigByName("config_menu_feedback", provider);
                if (configAbout != null) {
                    this.showAbout = !configAbout.contains(appid);
                }
                if (configShare != null) {
                    if (!configShare.contains(appid)) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    this.showShare = z3;
                }
                if (configCollect != null) {
                    if (!configCollect.contains(appid)) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    this.showCollect = z2;
                }
                if (configADD2Desk != null) {
                    if (!configADD2Desk.contains(appid)) {
                        z = true;
                    } else {
                        z = false;
                    }
                    this.showAddtodesktop = z;
                }
                if (configFeedBack != null) {
                    if (configFeedBack.contains(appid)) {
                        z4 = false;
                    }
                    this.showFeedback = z4;
                }
            }
        }
    }

    private JSONArray getMenuItemConfigByName(String key, H5ConfigProvider provider) {
        JSONObject configObj = H5Utils.parseObject(provider.getConfigWithProcessCache(key));
        if (configObj == null) {
            return null;
        }
        return H5Utils.getJSONArray(configObj, "black_list", null);
    }

    private void queryFavoriteStatus(H5Page h5Page2) {
        if (h5Page2 != null) {
            try {
                Object showFavorite = h5Page2.getExtra("showFavoriteMenu");
                if (!(showFavorite instanceof Boolean) || ((Boolean) showFavorite).booleanValue()) {
                    if (!shouldShowFavorite.get() || H5Utils.canTransferH5ToTiny(this.mAppId)) {
                        return;
                    }
                    H5Utils.getExecutor(H5ThreadType.URGENT).execute(new Runnable() {
                        public void run() {
                            if (!H5TinyPopMenu.this.isRelease && H5TinyPopMenu.this.mMixActionService != null && !H5TinyPopMenu.this.isQueryFavoriteStatus) {
                                H5TinyPopMenu.this.isQueryFavoriteStatus = true;
                                boolean temp = false;
                                if (H5TinyPopMenu.this.mTinyAppFavoriteService != null) {
                                    temp = H5TinyPopMenu.this.mTinyAppFavoriteService.isTinyAppFavorite(H5TinyPopMenu.this.mAppId);
                                }
                                if (H5TinyPopMenu.this.isFavorite != temp) {
                                    H5TinyPopMenu.this.isFavorite = temp;
                                    H5Utils.runOnMain(new Runnable() {
                                        public void run() {
                                            String name;
                                            H5TinyPopMenu.this.isQueryFavoriteStatus = false;
                                            try {
                                                if (H5TinyPopMenu.this.dialog != null && H5TinyPopMenu.this.dialog.isShowing() && !H5TinyPopMenu.this.isRelease) {
                                                    if (TextUtils.isEmpty(H5TinyPopMenu.this.mFavoriteName)) {
                                                        name = H5TinyPopMenu.FAVORITE;
                                                    } else {
                                                        name = H5TinyPopMenu.this.mFavoriteName;
                                                    }
                                                    int favoriteNamePosition = H5TinyPopMenu.this.menuList.indexOf(name);
                                                    int favoriteInvokePosition = H5TinyPopMenu.this.invokeModelList.indexOf(new JsInvokeModel(null, null, null, H5TinyPopMenu.FAVORITE_ID));
                                                    if (favoriteNamePosition == favoriteInvokePosition && favoriteNamePosition >= 0 && favoriteNamePosition < H5TinyPopMenu.this.menuList.size() && favoriteInvokePosition >= 0 && favoriteInvokePosition < H5TinyPopMenu.this.invokeModelList.size()) {
                                                        H5TinyPopMenu.this.mFavoritePosition = favoriteNamePosition;
                                                    }
                                                    H5TinyPopMenu.this.shouldShowFavoriteMenuItem();
                                                    if (H5TinyPopMenu.this.actionSheetProvider != null) {
                                                        H5TinyPopMenu.this.actionSheetProvider.updateActionSheetContent(H5TinyPopMenu.this.menuList);
                                                    }
                                                }
                                            } catch (Throwable e) {
                                                H5Log.e((String) H5TinyPopMenu.TAG, e);
                                            }
                                        }
                                    });
                                    return;
                                }
                                H5TinyPopMenu.this.isQueryFavoriteStatus = false;
                            }
                        }
                    });
                    return;
                }
                H5Log.d(TAG, "checkCanShowFavoriteMenuItem...DONOT show favorite menu");
            } catch (Throwable e) {
                H5Log.e((String) TAG, e);
            }
        }
    }

    private void registerPlugins(H5Page h5Page2) {
        if (h5Page2 != null && h5Page2.getPluginManager() != null && this.mH5Plugin != null) {
            h5Page2.getPluginManager().register((H5Plugin) this.mH5Plugin);
        }
    }
}
