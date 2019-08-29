package test.tinyapp.alipay.com.testlibrary.service.performancepanel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.liteprocess.perf.H5PerformancePlugin;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.storage.H5SharedPreferenceStorage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import test.tinyapp.alipay.com.testlibrary.core.DataProvider.UserAction;
import test.tinyapp.alipay.com.testlibrary.core.b;
import test.tinyapp.alipay.com.testlibrary.service.performancepanel.biz.PerformanceDataProvider;
import test.tinyapp.alipay.com.testlibrary.service.performancepanel.biz.c;

/* compiled from: PerformancePanelTestService */
public final class a implements test.tinyapp.alipay.com.testlibrary.core.a {
    private static AtomicBoolean j = new AtomicBoolean(false);
    private String[] a = {"showPerformancePanel", "hidePerformancePanel"};
    private String[] b = {H5PerformancePlugin.PERFORMANCE_JS_API};
    private Set<String> c = new HashSet();
    private final b d = new test.tinyapp.alipay.com.testlibrary.service.performancepanel.biz.b();
    private H5Page e = null;
    private boolean f = false;
    private boolean g = false;
    /* access modifiers changed from: private */
    public c h;
    private String i;
    private AtomicBoolean k = new AtomicBoolean(false);
    private BroadcastReceiver l = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            boolean needRefreshPanel = false;
            if (a.c() && a.this.f()) {
                String action = intent.getAction();
                if ("com.tinyapp.alipay.action.switchTab".equals(action)) {
                    a.this.a(true);
                } else {
                    a.this.a(false);
                }
                if ("com.tinyapp.alipay.action.pageResume".equals(action)) {
                    Bundle bundle = intent.getExtras();
                    if (bundle.containsKey("page_resume_start_time")) {
                        PerformanceDataProvider.a(true);
                        PerformanceDataProvider.b(bundle.getLong("page_resume_start_time"));
                    }
                }
                if ("com.tinyapp.alipay.action.switchPage".equals(action) || "com.tinyapp.alipay.action.switchPageFromBackgroundToFront".equals(action) || "com.tinyapp.alipay.action.pageResume".equals(action)) {
                    needRefreshPanel = true;
                }
                UserAction userAction = ("com.tinyapp.alipay.action.switchPage".equals(action) || "com.tinyapp.alipay.action.pageResume".equals(action)) ? UserAction.ACTION_SWITCH_PAGE : UserAction.ACTION_NORMAL;
                if (needRefreshPanel) {
                    try {
                        a.this.h.a(userAction);
                    } catch (Exception e) {
                        Log.e("PerformancePanelService", e.getMessage());
                    }
                }
            }
        }
    };

    private boolean e() {
        return this.k.get();
    }

    /* access modifiers changed from: private */
    public void a(boolean isSwitchTab) {
        this.k.set(isSwitchTab);
    }

    private static void b(boolean showing) {
        j.set(showing);
    }

    public static boolean c() {
        return j.get();
    }

    public final void a() {
        this.c.addAll(Arrays.asList(this.a));
        this.c.addAll(Arrays.asList(this.b));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.tinyapp.alipay.action.switchTab");
        intentFilter.addAction("com.tinyapp.alipay.action.switchPage");
        intentFilter.addAction("com.tinyapp.alipay.action.switchPageFromBackgroundToFront");
        intentFilter.addAction("com.tinyapp.alipay.action.pageResume");
        LocalBroadcastManager.getInstance(H5Utils.getContext()).registerReceiver(this.l, intentFilter);
    }

    public final void b() {
        LocalBroadcastManager.getInstance(H5Utils.getContext()).unregisterReceiver(this.l);
        b(false);
    }

    public final boolean a(H5Event event, H5BridgeContext context) {
        Log.i("PerformancePanelService", "handle action");
        this.e = event.getH5page();
        if (!f()) {
            Log.i("PerformancePanelService", "not have permission");
            context.sendError(event, Error.FORBIDDEN);
        } else {
            Log.i("PerformancePanelService", "have permission");
            if (this.h == null) {
                this.h = new c(event.getActivity(), new test.tinyapp.alipay.com.testlibrary.service.performancepanel.a.b(), new PerformanceDataProvider());
            }
            a(this.e);
            String action = event.getAction();
            if ("showPerformancePanel".equals(action)) {
                this.h.a();
                b(true);
                a(false);
                PerformanceDataProvider.a(false);
                H5SharedPreferenceStorage.getInstance().setPerformancePanelVisible(this.i, true);
            } else if ("hidePerformancePanel".equals(action)) {
                this.h.b();
                b(false);
                a(false);
                H5SharedPreferenceStorage.getInstance().setPerformancePanelVisible(this.i, false);
            } else if (H5PerformancePlugin.PERFORMANCE_JS_API.equals(action)) {
                b(event);
            }
        }
        return false;
    }

    public final boolean a(H5Event event) {
        return this.c.contains(event.getAction());
    }

    public final List<String> d() {
        List actionsList = new ArrayList(this.a.length + this.b.length);
        actionsList.addAll(Arrays.asList(this.a));
        actionsList.addAll(Arrays.asList(this.b));
        return actionsList;
    }

    private void b(H5Event event) {
        JSONObject param = event.getParam();
        if (param != null && TextUtils.equals("pageLoaded", param.getString("state"))) {
            int loadTime = param.getIntValue("loadTime");
            if (loadTime == 0) {
                PerformanceDataProvider.a(SystemClock.elapsedRealtime());
                PerformanceDataProvider.a(false);
            } else {
                PerformanceDataProvider.a(loadTime);
            }
            if (e() && this.h != null) {
                this.h.a(UserAction.ACTION_SWITCH_TAB);
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean f() {
        if (this.f) {
            return this.g;
        }
        this.g = this.e != null && this.d.a(this.e);
        this.f = true;
        return this.g;
    }

    private void a(H5Page h5Page) {
        if (TextUtils.isEmpty(this.i)) {
            this.i = H5Utils.getString(h5Page.getParams(), (String) "appId");
            if (TextUtils.isEmpty(this.i)) {
                this.i = "20000067";
            }
        }
    }
}
