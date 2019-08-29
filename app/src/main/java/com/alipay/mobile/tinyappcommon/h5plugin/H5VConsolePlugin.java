package com.alipay.mobile.tinyappcommon.h5plugin;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.alipay.mobile.framework.service.common.SecurityCacheService;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Bundle;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.h5plugin.TinyAppStoragePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.callback.H5UpdateAppCallback;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.ui.H5Fragment;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcommon.storage.H5SharedPreferenceStorage;
import com.alipay.mobile.tinyappcommon.storage.TinyAppStorage;
import com.alipay.mobile.tinyappcommon.view.ToggleButtonView;
import com.alipay.mobile.tinyappcommon.view.ToggleButtonView.a;
import com.autonavi.minimap.bundle.apm.internal.report.ReportManager;
import java.util.HashMap;

public class H5VConsolePlugin extends H5SimplePlugin {
    public static final String CLEAR_STORAGE_FOR_DEBUG = "clearStorage4Debug";
    public static final String DEBUG_PANEL_PACKAGE_APPID = "68687029";
    private static final String DEBUG_PANEL_PAGE_TAG = "DEBUG_PANEL_PAGE_TAG";
    public static final String ON_TINY_DEBUG_CONSOLE = "onTinyDebugConsole";
    private static final String PARAM_APP_ID = "appId";
    public static final String SHOULD_SHOW_VCONSOLE_BTN = "shouldShowConsoleBtn";
    public static final String SHOW_TOGGLE_BUTTON = "showToggleButton";
    protected static final String TAG = H5VConsolePlugin.class.getSimpleName();
    public static final String TOGGLE_DEBUG_PANEL = "toggleDebugPanel";
    private H5Page h5Page;
    private ToggleButtonView mToggleButton;

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(SHOULD_SHOW_VCONSOLE_BTN);
        filter.addAction(TOGGLE_DEBUG_PANEL);
        filter.addAction(SHOW_TOGGLE_BUTTON);
        filter.addAction(CLEAR_STORAGE_FOR_DEBUG);
    }

    public void onRelease() {
        this.h5Page = null;
        TinyAppStorage.getInstance().setDebugContainerView(null);
        TinyAppStorage.getInstance().setDebugPanelH5Page(null);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if (event.getTarget() instanceof H5Page) {
            this.h5Page = (H5Page) event.getTarget();
        }
        if (SHOULD_SHOW_VCONSOLE_BTN.equals(action)) {
            showVConsoleBtn(event, context);
        } else if (TOGGLE_DEBUG_PANEL.equals(action)) {
            toggleDebugPanel(event, context);
        } else if (SHOW_TOGGLE_BUTTON.equals(action)) {
            showToggleButton(event.getActivity());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "success", (Object) Boolean.valueOf(true));
            context.sendBridgeResult(jsonObject);
        } else if (CLEAR_STORAGE_FOR_DEBUG.equals(action)) {
            clearStorage4Debug(event);
        }
        return true;
    }

    private void showVConsoleBtn(H5Event event, H5BridgeContext context) {
        Boolean show = Boolean.valueOf(H5Utils.getBoolean(event.getParam(), (String) "showBtn", false));
        String currentAppId = H5Utils.getString(this.h5Page.getParams(), (String) "appId");
        H5Log.d(TAG, "showVConsoleBtn...show=" + show + ",appId=" + currentAppId);
        toggleButton(currentAppId, event.getActivity(), show.booleanValue());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put((String) "success", (Object) Boolean.valueOf(true));
        context.sendBridgeResult(jsonObject);
    }

    private void toggleButton(String appId, Activity activity, boolean show) {
        H5Log.d(TAG, "toggleButton...appId=" + appId + ",show=" + show);
        if (show) {
            showToggleButton(activity);
            H5SharedPreferenceStorage.getInstance().setVConsoleVisible(appId, true);
            return;
        }
        removeToggleButton(activity);
        H5SharedPreferenceStorage.getInstance().setVConsoleVisible(appId, false);
    }

    private void showToggleButton(final Activity activity) {
        if (activity != null) {
            if (this.mToggleButton != null) {
                this.mToggleButton.setVisibility(0);
                return;
            }
            ViewGroup rootView = (ViewGroup) activity.findViewById(16908290);
            this.mToggleButton = new ToggleButtonView(activity);
            this.mToggleButton.setBackgroundColor(Color.parseColor("#2A96E7"));
            this.mToggleButton.setText("调试面板");
            this.mToggleButton.setTextSize(15.0f);
            this.mToggleButton.setTextColor(-1);
            this.mToggleButton.setPadding(18, 12, 18, 12);
            this.mToggleButton.setViewContainer(rootView);
            this.mToggleButton.setMaxTopMargin(getTitleAndStatusBarHeight(activity));
            this.mToggleButton.setOnProxyClickListener(new a() {
                public void onClick(View view) {
                    H5VConsolePlugin.this.doToggleDebugPanel(activity, false);
                }
            });
            LayoutParams lp = new LayoutParams(-2, -2);
            lp.gravity = 85;
            lp.bottomMargin = 200;
            lp.rightMargin = 30;
            rootView.addView(this.mToggleButton, lp);
            doToggleDebugPanel(activity, true);
        }
    }

    private void removeToggleButton(Activity activity) {
        if (activity != null) {
            ViewGroup rootView = (ViewGroup) activity.findViewById(16908290);
            if (this.mToggleButton != null) {
                rootView.removeView(this.mToggleButton);
                this.mToggleButton = null;
            }
            if (TinyAppStorage.getInstance().getDebugContainerView() != null) {
                rootView.removeView(TinyAppStorage.getInstance().getDebugContainerView());
                TinyAppStorage.getInstance().setDebugContainerView(null);
                H5Page h5Page2 = TinyAppStorage.getInstance().getDebugPanelH5Page();
                if (h5Page2 != null) {
                    h5Page2.exitPage();
                    h5Page2.onRelease();
                }
                TinyAppStorage.getInstance().setDebugPanelH5Page(null);
            }
        }
    }

    private void toggleDebugPanel(H5Event event, H5BridgeContext context) {
        H5Log.d(TAG, "toggleDebugPanel...");
        doToggleDebugPanel(event.getActivity(), false);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put((String) "success", (Object) Boolean.valueOf(true));
        context.sendBridgeResult(jsonObject);
    }

    /* access modifiers changed from: private */
    public void doToggleDebugPanel(final Activity activity, final boolean preLoad) {
        if (activity != null) {
            FrameLayout containerView = TinyAppStorage.getInstance().getDebugContainerView();
            if (containerView == null) {
                final Runnable mainRunnable = new Runnable() {
                    public void run() {
                        H5VConsolePlugin.this.addDebugPanelView(activity, preLoad);
                    }
                };
                H5Utils.getExecutor(H5ThreadType.NORMAL).execute(new Runnable() {
                    public void run() {
                        H5AppProvider nebulaAppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
                        if (nebulaAppProvider == null) {
                            H5Log.e(H5VConsolePlugin.TAG, (String) "nebulaAppProvider==null");
                        } else if (nebulaAppProvider.getAppInfo(H5VConsolePlugin.DEBUG_PANEL_PACKAGE_APPID) == null) {
                            H5AppUtil.updateApp(H5VConsolePlugin.DEBUG_PANEL_PACKAGE_APPID, new H5UpdateAppCallback() {
                                public void onResult(boolean success, boolean isLimit) {
                                    if (success) {
                                        H5Log.d(H5VConsolePlugin.TAG, "ensure debug panel package ready 68687029");
                                        H5Utils.runOnMain(mainRunnable);
                                    }
                                    H5Log.d(H5VConsolePlugin.TAG, "ensure debug panel package failed 68687029");
                                }
                            });
                        } else {
                            H5Log.d(H5VConsolePlugin.TAG, "ensure debug panel package already exit 68687029");
                            H5Utils.runOnMain(mainRunnable);
                        }
                    }
                });
            } else if (containerView.getVisibility() == 0) {
                containerView.setVisibility(8);
            } else {
                containerView.setVisibility(0);
            }
        }
    }

    /* access modifiers changed from: private */
    public void addDebugPanelView(final Activity activity, final boolean preLoad) {
        ViewGroup rootView = (ViewGroup) activity.findViewById(16908290);
        Bundle param = new Bundle();
        param.putString("appId", DEBUG_PANEL_PACKAGE_APPID);
        param.putString(H5Param.CREATEPAGESENCE, "H5Activity");
        param.putString(H5Fragment.fragmentType, H5Fragment.subtab);
        param.putString(DEBUG_PANEL_PAGE_TAG, DEBUG_PANEL_PAGE_TAG);
        param.putString("parentAppId", H5Utils.getString(this.h5Page.getParams(), (String) "appId"));
        H5Bundle bundle = new H5Bundle();
        bundle.setParams(param);
        H5Page debugPanelH5Page = ((H5Service) H5Utils.findServiceByInterface(H5Service.class.getName())).createPage(activity, bundle);
        debugPanelH5Page.applyParamsIfNeed();
        LayoutParams lp = new LayoutParams(-1, getDebugPanelHeight(activity));
        lp.gravity = 80;
        View debugView = debugPanelH5Page.getContentView();
        debugView.bringToFront();
        FrameLayout debugContainerView = new FrameLayout(activity);
        debugContainerView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                H5VConsolePlugin.this.doToggleDebugPanel(activity, preLoad);
            }
        });
        debugContainerView.setBackgroundColor(Color.parseColor("#99191919"));
        debugContainerView.addView(debugView, lp);
        if (preLoad) {
            debugContainerView.setVisibility(8);
        }
        LayoutParams containerLp = new LayoutParams(-1, -1);
        containerLp.topMargin = getDebugPanelContainerTopMargin(activity);
        rootView.addView(debugContainerView, containerLp);
        debugPanelH5Page.setExtra(DEBUG_PANEL_PAGE_TAG, this.h5Page);
        TinyAppStorage.getInstance().setDebugContainerView(debugContainerView);
        TinyAppStorage.getInstance().setDebugPanelH5Page(debugPanelH5Page);
    }

    private int getTitleAndStatusBarHeight(Activity activity) {
        try {
            float titleBarHeight = activity.getResources().getDimension(R.dimen.h5_title_height);
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            return ((int) titleBarHeight) + frame.top;
        } catch (Throwable e) {
            H5Log.e(TAG, "getTitleAndStatusBarHeight...e=" + e);
            return H5DimensionUtil.dip2px(H5Utils.getContext(), 1.0f) * 73;
        }
    }

    private int getDebugPanelHeight(Activity activity) {
        if (activity == null) {
            return -1;
        }
        return (int) (((double) activity.getResources().getDisplayMetrics().heightPixels) * 0.75d);
    }

    private int getDebugPanelContainerTopMargin(Activity activity) {
        return getTitleAndStatusBarHeight(activity);
    }

    public static void sendWebWorkerLog(Object result) {
        if (result == null || !(result instanceof HashMap)) {
            H5Log.d(TAG, "sendWebWorkerLog..result type illegal");
            return;
        }
        H5Page debugH5Page = TinyAppStorage.getInstance().getDebugPanelH5Page();
        if (debugH5Page != null) {
            try {
                HashMap data = (HashMap) result;
                String content = (String) data.get("msg");
                if (TextUtils.isEmpty(content)) {
                    H5Log.d(TAG, "sendWebWorkerLog..content is null");
                    return;
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put((String) "type", (Object) switchLevel((String) data.get(H5PermissionManager.level)));
                H5Log.d(TAG, "sendWebWorkerLog...event=" + ((String) data.get("event")));
                jsonObject.put((String) "content", (Object) content);
                JSONObject consoleData = new JSONObject();
                consoleData.put((String) "data", (Object) jsonObject);
                debugH5Page.getBridge().sendToWeb(ON_TINY_DEBUG_CONSOLE, consoleData, null);
            } catch (Throwable e) {
                H5Log.e(TAG, "sendWebWorkerLog...e=" + e);
            }
        }
    }

    private static String switchLevel(String level) {
        if (TextUtils.isEmpty(level)) {
            return ReportManager.LOG_PATH;
        }
        char c = 65535;
        switch (level.hashCode()) {
            case 49:
                if (level.equals("1")) {
                    c = 0;
                    break;
                }
                break;
            case 50:
                if (level.equals("2")) {
                    c = 1;
                    break;
                }
                break;
            case 51:
                if (level.equals("3")) {
                    c = 2;
                    break;
                }
                break;
            case 52:
                if (level.equals("4")) {
                    c = 3;
                    break;
                }
                break;
            case 53:
                if (level.equals("5")) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return ReportManager.LOG_PATH;
            case 1:
                return ActionConstant.EXCEPTION_VIEW_TYPE_WARN;
            case 2:
                return "error";
            case 3:
                return "debug";
            case 4:
                return "info";
            default:
                return ReportManager.LOG_PATH;
        }
    }

    public static boolean debugAllowed(H5Page h5Page2) {
        if (h5Page2 == null) {
            return false;
        }
        if (h5Page2 == TinyAppStorage.getInstance().getDebugPanelH5Page()) {
            return true;
        }
        if (!TinyappUtils.isDebugVersion(h5Page2) || !H5SharedPreferenceStorage.getInstance().getAssistantPanelSwitch()) {
            return false;
        }
        return true;
    }

    private void clearStorage4Debug(H5Event event) {
        if (this.h5Page != null) {
            doClearLifestyleInfoStorage(event);
            try {
                this.h5Page.sendEvent(TinyAppStoragePlugin.CLEAR_TINY_LOCAL_STORAGE, null);
            } catch (Throwable e) {
                H5Log.e(TAG, "clearStorage4Debug...e=" + e);
            }
        }
    }

    private void doClearLifestyleInfoStorage(H5Event event) {
        String userId = TinyappUtils.getUserId();
        if (!TextUtils.isEmpty(userId)) {
            SecurityCacheService scs = (SecurityCacheService) H5Utils.findServiceByInterface(SecurityCacheService.class.getName());
            if (scs != null) {
                String appId = H5Utils.getString(event.getParam(), (String) "appId");
                if (TextUtils.isEmpty(appId)) {
                    H5Page h5Page2 = event.getH5page();
                    if (h5Page2 != null) {
                        appId = H5Utils.getString(h5Page2.getParams(), (String) "appId");
                    }
                }
                if (!TextUtils.isEmpty(appId)) {
                    scs.set(null, generateLifestyleInfoKey(userId, appId), "");
                }
            }
        }
    }

    private String generateLifestyleInfoKey(String userId, String appId) {
        return userId + "_" + appId + "_lifestyle";
    }
}
