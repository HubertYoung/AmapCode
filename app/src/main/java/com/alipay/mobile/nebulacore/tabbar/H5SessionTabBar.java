package com.alipay.mobile.nebulacore.tabbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Builder;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider.ResponseListen;
import com.alipay.mobile.nebula.startParam.H5StartParamManager;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5TabbarUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5TabbarItem;
import com.alipay.mobile.nebula.view.H5TabbarLayout;
import com.alipay.mobile.nebula.view.H5TabbarLayout.H5TabListener;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackage;
import com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackagePool;
import com.alipay.mobile.nebulacore.core.H5SessionImpl;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.tabbar.H5SessionTabInfoParser.H5SessionTabInfoListener;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.alipay.mobile.nebulacore.ui.H5Fragment;
import com.alipay.mobile.nebulacore.ui.H5FragmentManager;
import com.alipay.mobile.nebulacore.ui.H5ViewHolder;
import com.alipay.mobile.tinyappcommon.h5plugin.H5TabBarPlugin;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;

public class H5SessionTabBar extends H5BaseTabBar {
    public static final String SWITCH_TAB = "switchTab";
    private RelativeLayout d;
    private SparseArray<Bundle> e = new SparseArray<>(4);
    private SparseArray<String> f = new SparseArray<>(4);
    /* access modifiers changed from: private */
    public H5SessionImpl g;
    /* access modifiers changed from: private */
    public boolean h = true;
    private boolean i = false;
    private long j = 0;
    private boolean k = true;
    private boolean l = true;

    public void setPageViewHolder(H5ViewHolder holder) {
    }

    public H5SessionTabBar(H5SessionImpl h5Session) {
        this.g = h5Session;
    }

    private void b() {
        JSONObject optimizeCfgObj = H5Utils.parseObject(H5Environment.getConfig("h5_tabBarOptimizeConfig"));
        this.k = TextUtils.equals("YES", H5Utils.getString(optimizeCfgObj, (String) "enableAutoRender", (String) "YES"));
        this.l = TextUtils.equals("YES", H5Utils.getString(optimizeCfgObj, (String) "enableWaitRender", (String) "YES"));
        H5Log.d(H5BaseTabBar.TAG, "enableAutoRender " + this.k + ", enableWaitRender " + this.l);
    }

    public void addTabBar() {
        if (this.context.get() instanceof H5Activity) {
            H5Activity h5Activity = (H5Activity) this.context.get();
            if (h5Activity != null) {
                this.d = (RelativeLayout) h5Activity.getSessionTabContainer();
            }
            LayoutParams params = new LayoutParams(-1, -1);
            if (this.d != null) {
                this.d.removeAllViews();
                this.d.addView(getContent(), params);
                return;
            }
            return;
        }
        H5Log.w(H5BaseTabBar.TAG, "not in h5activity");
    }

    private void a(int index, JSONObject item, Bundle startParams) {
        if (item != null && !item.isEmpty()) {
            String url = H5Utils.getString(item, (String) "url");
            JSONObject launchParams = H5Utils.getJSONObject(item, H5Param.LAUNCHER_PARAM, null);
            Bundle tabStartParams = new Bundle();
            tabStartParams.putAll(startParams);
            tabStartParams.putAll(H5Utils.toBundle(launchParams));
            String launchParamsTag = H5Utils.getString(item, (String) H5StartParamManager.launchParamsTag);
            Bundle launcher = H5StartParamManager.getInstance().getH5StartParam(H5Utils.getString(startParams, (String) "appId"), launchParamsTag);
            if (launcher != null && !launcher.isEmpty()) {
                H5Log.d(H5BaseTabBar.TAG, "launchParamsTag " + launcher);
                tabStartParams.putAll(launcher);
            }
            tabStartParams.putString("url", url);
            tabStartParams.putInt("tabanimstartoff", H5Utils.getInt(item, (String) H5Param.WAIT_RENDER));
            tabStartParams.putInt("tabanimduration", H5Utils.getInt(item, (String) "transDuration"));
            this.e.put(index, tabStartParams);
            this.f.put(index, H5Utils.getString(item, (String) "tag"));
        }
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(SWITCH_TAB);
        filter.addAction(H5TabBarPlugin.ACTION_SET_TAB_BAR);
    }

    public void onRelease() {
        super.onRelease();
        this.d = null;
        this.a = null;
        this.b = null;
        this.c = null;
        if (this.e != null) {
            this.e.clear();
        }
        if (this.f != null) {
            this.f.clear();
        }
    }

    public boolean handleEvent(final H5Event event, final H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (SWITCH_TAB.equals(action)) {
            this.h = true;
            JSONObject params = event.getParam();
            final String tag = H5Utils.getString(params, (String) "tag");
            if (!H5Utils.getBoolean(params, (String) "recreate", false) || !c() || this.g == null || this.g.getH5SessionTabManager() == null) {
                a(event, bridgeContext, tag);
                return true;
            }
            H5Log.d(H5BaseTabBar.TAG, "tabBar recreate");
            this.g.getH5SessionTabManager().clearTabFragments();
            this.i = false;
            Iterator it = this.g.getPages().iterator();
            while (it.hasNext()) {
                H5Page item = (H5Page) it.next();
                if (!(item == null || item.getParams() == null)) {
                    Bundle itemParams = item.getParams();
                    if (TextUtils.equals(H5Utils.getString(itemParams, (String) H5Fragment.fragmentType, (String) "normal"), H5Fragment.subtab) && !H5Utils.getBoolean(params, (String) H5Param.LONG_ISPRERENDER, false)) {
                        item.sendExitEvent();
                        itemParams.putString(H5Fragment.fragmentType, "normal");
                    }
                }
            }
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5SessionTabBar.this.a(event, bridgeContext, tag);
                }
            }, 500);
            return true;
        } else if (!H5TabBarPlugin.ACTION_SET_TAB_BAR.equals(action)) {
            return super.handleEvent(event, bridgeContext);
        } else {
            JSONObject params2 = event.getParam();
            String actionType = H5Utils.getString(params2, (String) "actionType");
            if (TextUtils.equals(actionType, "redDot")) {
                createTabBadge(params2, null);
                return true;
            } else if (TextUtils.equals(actionType, "enable")) {
                this.h = true;
                return true;
            } else if (TextUtils.equals(actionType, "disable")) {
                this.h = false;
                return true;
            } else if (TextUtils.equals(actionType, "textColor")) {
                createTabTextColor(params2, null);
                return true;
            } else if (!TextUtils.equals(actionType, H5Param.MENU_ICON)) {
                return true;
            } else {
                H5Page h5Page = event.getH5page();
                if (h5Page == null) {
                    return true;
                }
                createTabIcon(params2, null, h5Page.getParams());
                return true;
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(H5Event event, H5BridgeContext bridgeContext, String tag) {
        if (this.g.getH5SessionTabManager() == null || this.g.getH5SessionTabManager().getCurrentIndex() != -1) {
            switchTab(event, event.getActivity(), bridgeContext);
            return;
        }
        final H5Page h5Page = (H5Page) event.getTarget();
        if (h5Page != null) {
            Bundle startParams = h5Page.getParams();
            String tabBarJson = H5Utils.getString(startParams, (String) "tabBarJson");
            if (!TextUtils.isEmpty(tabBarJson)) {
                final H5Event h5Event = event;
                final String str = tag;
                final H5BridgeContext h5BridgeContext = bridgeContext;
                H5SessionTabInfoParser.getOnlineData(tabBarJson, new H5SessionTabInfoListener() {
                    public void onGetSyncData(JSONObject data) {
                    }

                    public void onGetAsyncData(JSONObject data) {
                        H5SessionTabBar.this.a(data, h5Event, str, h5BridgeContext);
                    }

                    public void onShowDefaultTab() {
                        H5Utils.runOnMain(new Runnable() {
                            public void run() {
                                if (h5Page != null) {
                                    JSONObject param = new JSONObject();
                                    param.put((String) "delay", (Object) "0");
                                    h5Page.sendEvent("showLoading", param);
                                }
                            }
                        });
                    }
                }, H5Utils.getString(startParams, (String) "appId"));
                return;
            }
            final H5Event h5Event2 = event;
            final String str2 = tag;
            final H5BridgeContext h5BridgeContext2 = bridgeContext;
            H5SessionTabInfoParser.getOfflineData(this.g, new H5SessionTabInfoListener() {
                public void onGetSyncData(JSONObject data) {
                    H5SessionTabBar.this.a(data, h5Event2, str2, h5BridgeContext2);
                }

                public void onGetAsyncData(JSONObject data) {
                    H5SessionTabBar.this.a(data, h5Event2, str2, h5BridgeContext2);
                }

                public void onShowDefaultTab() {
                    H5Utils.runOnMain(new Runnable() {
                        public void run() {
                            if (h5Page != null) {
                                JSONObject param = new JSONObject();
                                param.put((String) "delay", (Object) "0");
                                h5Page.sendEvent("showLoading", param);
                            }
                        }
                    });
                }
            }, H5Utils.getString(startParams, (String) "appId"));
        }
    }

    /* access modifiers changed from: private */
    public void a(JSONObject data, H5Event event, String tag, H5BridgeContext bridgeContext) {
        if (!this.i) {
            this.i = true;
            final H5Event h5Event = event;
            final JSONObject jSONObject = data;
            final String str = tag;
            final H5BridgeContext h5BridgeContext = bridgeContext;
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5Page h5Page = (H5Page) h5Event.getTarget();
                    if (h5Page != null) {
                        h5Page.sendEvent(CommonEvents.HIDE_LOADING, null);
                        if (jSONObject == null) {
                            JSONObject result = new JSONObject();
                            result.put((String) "error", (Object) Integer.valueOf(10));
                            result.put((String) "errorMessage", (Object) "invalid config");
                            h5BridgeContext.sendBridgeResult(result);
                        } else if (!H5SessionTabBar.this.a(jSONObject, h5Event.getActivity(), h5Page.getParams(), true, str)) {
                            h5BridgeContext.sendError(h5Event, Error.INVALID_PARAM);
                        }
                    }
                }
            });
        }
    }

    public boolean createSessionTab(JSONObject param, Activity activity, Bundle startParams) {
        return a(param, activity, startParams, false, (String) "default");
    }

    /* access modifiers changed from: private */
    public boolean a(JSONObject param, Activity activity, Bundle startParams, boolean isFromJSAPI, String tag) {
        if (this.g.getH5SessionTabManager() == null) {
            return false;
        }
        WeakReference weakReference = new WeakReference(activity);
        this.context = weakReference;
        if (this.context.get() instanceof H5Activity) {
            int selectedIndex = -1;
            if (startParams != null) {
                String entryUrl = H5Utils.getString(startParams, (String) "url");
                String appId = H5Utils.getString(startParams, (String) "appId");
                boolean disableOnInit = H5Utils.getBoolean(param, (String) "disableOnInit", false);
                H5Log.d(H5BaseTabBar.TAG, "disableOnInit " + disableOnInit);
                if (disableOnInit && !isFromJSAPI) {
                    this.h = false;
                }
                JSONArray items = H5Utils.getJSONArray(param, "items", null);
                selectedIndex = H5TabbarUtils.ifUrlMatch(appId, entryUrl, startParams);
                if (items != null && !items.isEmpty()) {
                    int itemsLength = items.size();
                    int i2 = 0;
                    while (i2 < itemsLength && i2 < 5) {
                        JSONObject item = items.getJSONObject(i2);
                        if (item != null && !item.isEmpty()) {
                            item.put((String) "url", (Object) H5TabbarUtils.getAbsoluteUrl(item.getString("url"), startParams));
                            String icon = item.getString(H5Param.MENU_ICON);
                            String activeIcon = item.getString("activeIcon");
                            try {
                                if (H5ImageUtil.base64ToBitmap(icon) == null) {
                                    item.put((String) H5Param.MENU_ICON, (Object) H5TabbarUtils.getAbsoluteUrl(icon, startParams));
                                }
                            } catch (Throwable th) {
                                item.put((String) H5Param.MENU_ICON, (Object) H5TabbarUtils.getAbsoluteUrl(icon, startParams));
                            }
                            try {
                                if (H5ImageUtil.base64ToBitmap(activeIcon) == null) {
                                    item.put((String) "activeIcon", (Object) H5TabbarUtils.getAbsoluteUrl(activeIcon, startParams));
                                }
                            } catch (Throwable th2) {
                                item.put((String) "activeIcon", (Object) H5TabbarUtils.getAbsoluteUrl(activeIcon, startParams));
                            }
                            a(i2, item, startParams);
                        }
                        i2++;
                    }
                }
                if (isFromJSAPI) {
                    selectedIndex = a(tag);
                }
                H5Log.d(H5BaseTabBar.TAG, "selectedIndex " + selectedIndex);
                if (selectedIndex == -1) {
                    return false;
                }
            }
            param.put((String) "selectedIndex", (Object) Integer.valueOf(selectedIndex));
            super.createTabBar(param, null, startParams);
            final H5FragmentManager h5FragmentManager = ((H5Activity) this.context.get()).getH5FragmentManager();
            Bundle tabStartParams = this.e.get(selectedIndex);
            tabStartParams.putString(H5Fragment.fragmentType, H5Fragment.subtab);
            h5FragmentManager.addFragment(tabStartParams, false, false);
            this.g.getH5SessionTabManager().addTabFragment(selectedIndex, (H5Fragment) h5FragmentManager.peekFragment());
            this.g.getH5SessionTabManager().setCurrentIndex(selectedIndex);
            final String finalSessionId = H5Utils.getString(tabStartParams, (String) "sessionId");
            H5Log.d(H5BaseTabBar.TAG, "finalSessionId is " + finalSessionId);
            String autoPreRender = H5Utils.getString(param, (String) "autoPreRender");
            boolean isTinyApp = H5Utils.getBoolean(startParams, (String) "isTinyApp", false);
            b();
            H5Log.d(H5BaseTabBar.TAG, "autoPreRender " + autoPreRender + ", isTinyApp " + isTinyApp + ", enableAutoRender " + this.k);
            if (TextUtils.equals(autoPreRender, "YES") && !isTinyApp && this.k) {
                int size = this.e.size();
                for (int i3 = 0; i3 < size; i3++) {
                    int key = this.e.keyAt(i3);
                    if (key != selectedIndex) {
                        H5Log.d(H5BaseTabBar.TAG, "##h5prerender## add prerender in sessiontabbar");
                        Bundle tabParamsTmp = (Bundle) this.e.get(key).clone();
                        tabParamsTmp.putBoolean(H5Param.LONG_ISPRERENDER, true);
                        tabParamsTmp.putString(H5Fragment.fragmentType, H5Fragment.subtab);
                        h5FragmentManager.addPreFragment(tabParamsTmp, 0);
                    }
                }
            }
            H5TabbarLayout h5TabbarLayout = this.b;
            AnonymousClass5 r0 = new H5TabListener() {
                public void onTabItemClicked(int i, View view) {
                    if (H5SessionTabBar.this.b != null && H5SessionTabBar.this.h && !H5SessionTabBar.this.isFastClick() && H5SessionTabBar.this.g != null && H5SessionTabBar.this.g.getH5SessionTabManager() != null) {
                        String tag = (String) view.getTag();
                        H5Fragment currentFragment = H5SessionTabBar.this.g.getH5SessionTabManager().getCurrentFragment();
                        if (currentFragment != null) {
                            H5Page h5Page = currentFragment.getH5Page();
                            if (h5Page != null) {
                                H5Bridge h5Bridge = h5Page.getBridge();
                                if (h5Bridge != null) {
                                    JSONObject result = new JSONObject();
                                    result.put((String) "tag", (Object) tag);
                                    result.put((String) "index", (Object) Integer.valueOf(i));
                                    result.put((String) "pagePath", (Object) tag);
                                    result.put((String) "text", (Object) ((TextView) view.findViewById(R.id.h5_tabbaritem_txticon)).getText());
                                    JSONObject data = new JSONObject();
                                    data.put((String) "data", (Object) result);
                                    H5Log.d(H5BaseTabBar.TAG, "tabClick :" + data);
                                    h5Bridge.sendToWeb("tabClick", data, null);
                                    H5SessionTabBar.this.a(i, h5FragmentManager, finalSessionId);
                                }
                            }
                        }
                    }
                }
            };
            h5TabbarLayout.setTabListener(r0);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void a(int i2, H5FragmentManager h5FragmentManager, String finalSessionId) {
        this.g.getH5SessionTabManager().setCurrentIndex(i2);
        this.b.selectTab(i2);
        H5Fragment selectedFragment = this.g.getH5SessionTabManager().getTabFragmentByIndex(i2);
        if (selectedFragment == null) {
            Bundle startParams = this.e.get(i2);
            startParams.putString("sessionId", finalSessionId);
            startParams.putString(H5Fragment.fragmentType, H5Fragment.subtab);
            if (h5FragmentManager != null) {
                boolean withAnim = a(startParams);
                h5FragmentManager.addFragment(startParams, withAnim, false);
                H5Fragment subFragment = (H5Fragment) h5FragmentManager.peekFragment();
                this.g.getH5SessionTabManager().addTabFragment(i2, subFragment);
                H5Log.d(H5BaseTabBar.TAG, "add new sub fragment index " + i2 + ", obj " + subFragment + ", withAnim " + withAnim);
            }
        } else if (h5FragmentManager != null) {
            h5FragmentManager.attachFragment(selectedFragment, false, false);
            H5Log.d(H5BaseTabBar.TAG, "reuse sub fragment index " + i2 + ", obj " + selectedFragment);
        }
        a(i2, h5FragmentManager);
    }

    private boolean a(Bundle startParams) {
        b();
        return !(H5Utils.getInt(startParams, (String) "tabanimstartoff") == 0 && H5Utils.getInt(startParams, (String) "tabanimduration") == 0) && this.l;
    }

    public void switchTab(H5Event h5Event, Activity activity, H5BridgeContext h5BridgeContext) {
        if (activity == null) {
            H5Log.d(H5BaseTabBar.TAG, "activity == null");
            return;
        }
        H5Activity h5Activity = (H5Activity) activity;
        if (h5Activity.getSessionTabContainer() != null) {
            int index = a(H5Utils.getString(h5Event.getParam(), (String) "tag"));
            if (index != -1) {
                String sessionId = this.g.getId();
                H5FragmentManager h5FragmentManager = h5Activity.getH5FragmentManager();
                H5Fragment selectedFragment = this.g.getH5SessionTabManager().getTabFragmentByIndex(index);
                this.g.getH5SessionTabManager().setCurrentIndex(index);
                this.b.selectTab(index);
                if (selectedFragment == null) {
                    Bundle startParams = this.e.get(index);
                    startParams.putString("sessionId", sessionId);
                    startParams.putString(H5Fragment.fragmentType, H5Fragment.subtab);
                    if (h5FragmentManager != null) {
                        boolean withAnim = a(startParams);
                        h5FragmentManager.addFragment(startParams, withAnim, false);
                        H5Fragment subFragment = (H5Fragment) h5FragmentManager.peekFragment();
                        this.g.getH5SessionTabManager().addTabFragment(index, subFragment);
                        H5Log.d(H5BaseTabBar.TAG, "add new sub fragment index " + index + ", obj " + subFragment + ", withAnim " + withAnim);
                    }
                } else if (h5FragmentManager != null) {
                    h5FragmentManager.attachFragment(selectedFragment, false, false);
                    H5Log.d(H5BaseTabBar.TAG, "reuse sub fragment index " + index + ", obj " + selectedFragment);
                }
                a(index, h5FragmentManager);
                return;
            }
            H5Log.d(H5BaseTabBar.TAG, "invalid tag name");
            h5BridgeContext.sendError(h5Event, Error.INVALID_PARAM);
        }
    }

    private void a(int currentIndex, H5FragmentManager h5FragmentManager) {
        Map tabFragments = this.g.getH5SessionTabManager().getTabFragments();
        for (Integer index : tabFragments.keySet()) {
            if (!(index.intValue() == currentIndex || h5FragmentManager == null)) {
                H5Log.d(H5BaseTabBar.TAG, "detachOtherFragment index " + index);
                h5FragmentManager.detachFragment(tabFragments.get(index), false);
            }
        }
        Builder eventBuilder = new Builder();
        eventBuilder.action(CommonEvents.H5_SWITCH_SESSIONTAB);
        eventBuilder.target(this.g);
        Nebula.getDispatcher().dispatch(eventBuilder.build());
    }

    private int a(String tag) {
        int size = this.f.size();
        for (int i2 = 0; i2 < size; i2++) {
            int key = this.f.keyAt(i2);
            if (TextUtils.equals(this.f.get(key), tag)) {
                return key;
            }
        }
        return -1;
    }

    public void createDefaultSessionTab(Context context, int num) {
        if (num >= 2) {
            LinearLayout rootDefaultView = new LinearLayout(context);
            rootDefaultView.setOrientation(1);
            H5TabbarLayout defaultTabHost = new H5TabbarLayout(context);
            int i2 = 0;
            while (i2 < num && i2 < 5) {
                H5TabbarItem tabbarItem = new H5TabbarItem(context);
                TextView iconArea = (TextView) tabbarItem.getIconAreaView();
                iconArea.setText("".trim());
                Drawable defaultIcon = H5Environment.getResources().getDrawable(R.drawable.h5_sessiontab_defaultitem);
                int drawableSize = a();
                defaultIcon.setBounds(0, 0, drawableSize, drawableSize);
                iconArea.setCompoundDrawables(null, defaultIcon, null, null);
                defaultTabHost.addTab(tabbarItem.getRootView());
                i2++;
            }
            LinearLayout.LayoutParams rootLayoutParams4Line = new LinearLayout.LayoutParams(-1, 1);
            View borderLine = new View(context);
            borderLine.setBackgroundColor(-5526610);
            rootDefaultView.addView(borderLine, rootLayoutParams4Line);
            defaultTabHost.setBackgroundColor(-460551);
            rootDefaultView.addView(defaultTabHost, new LinearLayout.LayoutParams(-1, -1));
            if (context instanceof H5Activity) {
                this.d = (RelativeLayout) ((H5Activity) context).getSessionTabContainer();
                this.d.addView(rootDefaultView, new LayoutParams(-1, -1));
                this.d.setVisibility(0);
                return;
            }
            H5Log.w(H5BaseTabBar.TAG, "not in h5activity");
            return;
        }
        H5Log.w(H5BaseTabBar.TAG, "createDefaultSessionTab num < 2");
    }

    /* access modifiers changed from: protected */
    public final void a(String imageUrl, TextView iconArea, StateListDrawable sd, Context context, int drawableSize, boolean isCheckedState, Bundle startParams) {
        if (!TextUtils.isEmpty(imageUrl)) {
            H5ContentPackage h5ContentPackage = H5ContentPackagePool.getPackage(H5Utils.getString(startParams, (String) "sessionId"));
            if (h5ContentPackage != null) {
                byte[] data = h5ContentPackage.get(imageUrl);
                if (data != null) {
                    H5Log.d(H5BaseTabBar.TAG, "loadImageAsync offlinepkg preFetchPackage");
                    BitmapDrawable topDrawable = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(data, 0, data.length));
                    topDrawable.setBounds(0, 0, drawableSize, drawableSize);
                    if (isCheckedState) {
                        H5TabbarUtils.addCheckedState(sd, topDrawable);
                    } else {
                        H5TabbarUtils.addNormalState(sd, topDrawable);
                    }
                    iconArea.setCompoundDrawables(null, sd, null, null);
                    return;
                }
            }
            if (this.g == null || this.g.getWebProvider() == null) {
                super.a(imageUrl, iconArea, sd, context, drawableSize, isCheckedState, startParams);
                return;
            }
            String onlineHost = H5Utils.getString(startParams, (String) H5Param.ONLINE_HOST);
            if (TextUtils.isEmpty(onlineHost) || !imageUrl.startsWith(onlineHost)) {
                super.a(imageUrl, iconArea, sd, context, drawableSize, isCheckedState, startParams);
                return;
            }
            final Context context2 = context;
            final int i2 = drawableSize;
            final boolean z = isCheckedState;
            final StateListDrawable stateListDrawable = sd;
            final TextView textView = iconArea;
            final String str = imageUrl;
            final Bundle bundle = startParams;
            this.g.getWebProvider().getContent(imageUrl, (ResponseListen) new ResponseListen() {
                public void onGetResponse(WebResourceResponse webResourceResponse) {
                    H5Log.d(H5BaseTabBar.TAG, "loadImageAsync offlinepkg already exist package");
                    if (webResourceResponse == null || webResourceResponse.getData() == null) {
                        H5SessionTabBar.super.a(str, textView, stateListDrawable, context2, i2, z, bundle);
                        return;
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(webResourceResponse.getData());
                    if (bitmap != null) {
                        final BitmapDrawable topDrawable = new BitmapDrawable(context2.getResources(), bitmap);
                        topDrawable.setBounds(0, 0, i2, i2);
                        H5Utils.runOnMain(new Runnable() {
                            public void run() {
                                if (z) {
                                    H5TabbarUtils.addCheckedState(stateListDrawable, topDrawable);
                                } else {
                                    H5TabbarUtils.addNormalState(stateListDrawable, topDrawable);
                                }
                                textView.setCompoundDrawables(null, stateListDrawable, null, null);
                            }
                        });
                    }
                }
            });
        }
    }

    public synchronized boolean isFastClick() {
        boolean isFastClick;
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.j > 500) {
            isFastClick = false;
        } else {
            isFastClick = true;
        }
        this.j = currentTime;
        return isFastClick;
    }

    public void setHasShowTab(boolean hasShowTab) {
        this.i = hasShowTab;
    }

    private static boolean c() {
        if (BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("h5_enableReCreateTabBar"))) {
            return false;
        }
        return true;
    }
}
