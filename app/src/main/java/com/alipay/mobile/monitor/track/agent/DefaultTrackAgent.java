package com.alipay.mobile.monitor.track.agent;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.monitor.track.TrackIntegrator;
import com.alipay.mobile.monitor.track.TrackPageConfig;
import com.alipay.mobile.monitor.track.interceptor.AutoClickInterceptor;
import java.util.Map;

public class DefaultTrackAgent implements TrackAgent {
    public static final String PARAM_APPID = "appId";
    public static final String PARAM_CONTROLID = "controlId";
    public static final String PARAM_PAGEID = "pageId";
    private AutoClickInterceptor a = new AutoClickInterceptor();

    public void onActivityCreate(Activity activity) {
        TrackIntegrator.getInstance().createActivity(activity);
    }

    public void onActivityResume(Activity activity) {
        TrackIntegrator.getInstance();
        TrackIntegrator.setLastActiveTime(System.currentTimeMillis());
        TrackIntegrator.getInstance().enterActivity(activity);
    }

    public void onActivityWindowFocusChanged(Activity activity, boolean hasFocus) {
        if (hasFocus) {
            TrackIntegrator.getInstance().displayActivity(activity);
        }
    }

    public void onActivityPause(Activity activity) {
        TrackIntegrator.getInstance().leaveActivity(activity);
    }

    public void onActivityDestroy(Activity activity) {
        TrackIntegrator.getInstance().destoryActivity(activity);
    }

    public void onFragmentResume(Fragment fragment) {
        if (fragment == null) {
            LoggerFactory.getTraceLogger().info(TrackIntegrator.TAG, "onFragmentResume fragment is null");
            return;
        }
        TrackIntegrator.getInstance();
        TrackIntegrator.setLastActiveTime(System.currentTimeMillis());
        LoggerFactory.getTraceLogger().info(TrackIntegrator.TAG, "onFragmentResume = " + fragment.getClass().getName());
        if (!(fragment instanceof TrackPageConfig)) {
            LoggerFactory.getTraceLogger().info(TrackIntegrator.TAG, "onFragmentResume default not track");
        } else if (!((TrackPageConfig) fragment).isTrackPage()) {
            LoggerFactory.getTraceLogger().info(TrackIntegrator.TAG, "onFragmentResume !isTrackPage");
        } else {
            TrackIntegrator.getInstance().enterFragment(fragment, true);
        }
    }

    public void onFragmentPause(Fragment fragment) {
        if (fragment == null) {
            LoggerFactory.getTraceLogger().info(TrackIntegrator.TAG, "onFragmentPause fragment is null");
            return;
        }
        LoggerFactory.getTraceLogger().info(TrackIntegrator.TAG, "onFragmentPause = " + fragment.getClass().getName());
        if (!(fragment instanceof TrackPageConfig)) {
            LoggerFactory.getTraceLogger().info(TrackIntegrator.TAG, "onFragmentPause default not track");
        } else if (!((TrackPageConfig) fragment).isTrackPage()) {
            LoggerFactory.getTraceLogger().info(TrackIntegrator.TAG, "onFragmentPause !isTrackPage");
        } else {
            TrackIntegrator.getInstance().leaveFragment(fragment, true);
        }
    }

    public void onFragmentHiddenChanged(Fragment fragment, boolean hidden) {
    }

    public void onFragmentDestory(Fragment fragment) {
        TrackIntegrator.getInstance().destoryFragment(fragment);
    }

    public void onViewClick(View view, boolean isAdapterItem, String xPath, Map<String, String> extParams) {
        String pageId = null;
        String appId = null;
        String controlId = null;
        if (extParams != null) {
            pageId = extParams.get("pageId");
            appId = extParams.get("appId");
            controlId = extParams.get(PARAM_CONTROLID);
        }
        this.a.reportViewTrackEvent(view, controlId, pageId, appId, xPath);
    }

    public void onActivityBackPressed(Activity activity) {
    }

    public void onFragmentBackPressed() {
    }

    public void onActivityFinish(Activity activity) {
    }

    public void onFragmentFinish() {
    }

    public void onTouchEvent(MotionEvent ev) {
    }
}
