package com.alipay.android.phone.mobilecommon.multimediabiz.biz.live;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.dynamicrelease.DynamicReleaseApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.ActivityApplication;
import com.alipay.mobile.framework.app.MicroApplication;
import com.autonavi.map.core.MapCustomizeManager;

public class LiveLaunchApp extends ActivityApplication {
    public static final String ACTION_CREATE_ARTVCCORE_VC = "createP2pViewController";
    public static final String ACTION_CREATE_VC = "createToyMachineVC";
    public static final String ACTION_LIVE_RECORD = "com.alipay.multimedia.live.record";
    public static final String ACTION_LIVE_SHOW = "com.alipay.multimedia.live.show";
    public static final String EXTRA_ACTION_TYPE = "actionType";
    private Bundle a;

    public String getEntryClassName() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        Logger.D("LiveLaunchApp", "onCreate", new Object[0]);
        this.a = bundle;
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        Logger.D("LiveLaunchApp", "onStart", new Object[0]);
        a(this.a);
    }

    /* access modifiers changed from: protected */
    public void onRestart(Bundle bundle) {
        Logger.D("LiveLaunchApp", "onRestart", new Object[0]);
        a(bundle);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        Logger.D("LiveLaunchApp", "onStop", new Object[0]);
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
        Logger.D("LiveLaunchApp", "onDestroy", new Object[0]);
        this.a = null;
    }

    private boolean a() {
        return DynamicReleaseApi.getInstance(getMicroApplicationContext().getApplicationContext()).isBundleExist("multimedia-live");
    }

    public static void startLiveBundle(MicroApplicationContext applicationContext, MicroApplication app, Context context, Bundle params) {
        if (params != null && "liveShow".equals(params.getString("actionType"))) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.eg.android.AlipayGphone", "com.alipay.multimedia.live.ui.LiveShowValidActivity_"));
            intent.setFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
            intent.putExtras(params);
            applicationContext.startActivity(app, intent);
        }
    }

    private void a(Bundle params) {
        String action = params.getString("actionType");
        if (params != null && ("createToyMachineVC".equals(action) || ACTION_CREATE_ARTVCCORE_VC.equals(action))) {
            startVidoConferenceActivity(params);
        } else if (a()) {
            startLiveBundle(getMicroApplicationContext(), this, (Context) getMicroApplicationContext().getTopActivity().get(), params);
        } else {
            Intent intent = new Intent(getMicroApplicationContext().getApplicationContext(), LiveDownloadActivity.class);
            intent.putExtras(params);
            intent.setFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
            getMicroApplicationContext().startActivity((MicroApplication) this, intent);
        }
    }

    public void startVidoConferenceActivity(Bundle params) {
        String targetActivity;
        boolean mrtcBundleAvailable = b();
        LoggerFactory.getTraceLogger().debug("LiveLaunchApp", "startVidoConferenceActivity mrtcBundleAvailable: " + mrtcBundleAvailable + "; params= " + params);
        if (mrtcBundleAvailable) {
            if ("createToyMachineVC".equals(params.getString("actionType"))) {
                targetActivity = "com.ant.phone.ARTVC.activity.ToyMachineActivity";
            } else if (ACTION_CREATE_ARTVCCORE_VC.equals(params.getString("actionType"))) {
                targetActivity = "com.ant.phone.ARTVC.activity.ARTVCActivity";
            } else {
                return;
            }
            a(LauncherApplicationAgent.getInstance().getMicroApplicationContext(), LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp(), targetActivity, params);
            return;
        }
        a(LauncherApplicationAgent.getInstance().getMicroApplicationContext(), LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp(), "com.alipay.android.phone.mobilecommon.multimediabiz.biz.live.ARTVCModuleDownloadActivity", params);
    }

    private static void a(MicroApplicationContext applicationContext, MicroApplication app, String targetActivity, Bundle params) {
        if (params != null && !TextUtils.isEmpty(targetActivity)) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.eg.android.AlipayGphone", targetActivity));
            intent.setFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
            intent.putExtras(params);
            applicationContext.startActivity(app, intent);
        }
    }

    private boolean b() {
        return DynamicReleaseApi.getInstance(getMicroApplicationContext().getApplicationContext()).isBundleExist("mobile-mrtc-mrtc");
    }
}
