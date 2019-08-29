package com.alipay.mobile.nebulacore.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5Context;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.provider.H5AnimatorResIdProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import java.lang.ref.WeakReference;

public class H5AnimatorUtil {
    private static final String a = H5Utils.getContext().getPackageName();
    private static boolean b = true;

    private static H5AnimatorResIdProvider a() {
        return (H5AnimatorResIdProvider) H5Utils.getProvider(H5AnimatorResIdProvider.class.getName());
    }

    public static boolean isNeedAnimFromConfig() {
        String needAnimConfig = H5Environment.getConfigWithProcessCache("h5_needAnim");
        if (TextUtils.isEmpty(needAnimConfig) || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(needAnimConfig)) {
            return b;
        }
        return false;
    }

    public static void setActivityStart(H5Context h5Context, Bundle bundle) {
        if (isNeedAnimFromConfig()) {
            try {
                if (a() != null) {
                    Context context = null;
                    if (!(h5Context == null || h5Context.getContext() == null)) {
                        context = h5Context.getContext();
                    }
                    H5Log.d("H5AnimatorUtil", "context " + context);
                    Activity topActivity = null;
                    if (context instanceof Activity) {
                        topActivity = (Activity) context;
                    }
                    if (topActivity != null) {
                        topActivity.overridePendingTransition(topActivity.getResources().getIdentifier(a().startActivityEnterAnim(), ResUtils.ANIM, a), topActivity.getResources().getIdentifier(a().startActivityEnterExitAnim(), ResUtils.ANIM, a));
                        return;
                    }
                    return;
                }
                WeakReference topRef = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity();
                if (topRef != null && topRef.get() != null) {
                    Activity topActivity2 = (Activity) topRef.get();
                    if (topActivity2 == null) {
                        return;
                    }
                    if (presentWithAnimation(bundle)) {
                        topActivity2.overridePendingTransition(topActivity2.getResources().getIdentifier("push_up_in", ResUtils.ANIM, a), topActivity2.getResources().getIdentifier("dismiss_out", ResUtils.ANIM, a));
                    } else {
                        topActivity2.overridePendingTransition(topActivity2.getResources().getIdentifier("h5_slide_in_right", ResUtils.ANIM, a), topActivity2.getResources().getIdentifier("h5_slide_out_left", ResUtils.ANIM, a));
                    }
                }
            } catch (Throwable e) {
                H5Log.d("H5AnimatorUtil", "overridePendingTransitionFromXml exception : " + e);
            }
        }
    }

    public static void setActivityNoAnimStart() {
        if (isNeedAnimFromConfig()) {
            try {
                WeakReference topRef = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity();
                if (topRef != null && topRef.get() != null) {
                    Activity topActivity = (Activity) topRef.get();
                    if (topActivity != null) {
                        topActivity.overridePendingTransition(0, topActivity.getResources().getIdentifier("h5_slide_out_left", ResUtils.ANIM, a));
                    }
                }
            } catch (Throwable e) {
                H5Log.d("H5AnimatorUtil", "overridePendingTransitionFromXml exception : " + e);
            }
        }
    }

    public static boolean presentWithAnimation(Bundle bundle) {
        return TextUtils.equals(H5Utils.getString(bundle, (String) H5Param.NEBULA_START_ANIMATION), "presentWithAnimation");
    }

    public static void setActivityFinish(Activity activityFinish, Bundle bundle) {
        if (isNeedAnimFromConfig()) {
            try {
                if (a() == null) {
                    WeakReference topRef = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity();
                    if (topRef != null && topRef.get() != null) {
                        Activity topActivity = (Activity) topRef.get();
                        if (topActivity == null) {
                            return;
                        }
                        if (presentWithAnimation(bundle)) {
                            topActivity.overridePendingTransition(0, topActivity.getResources().getIdentifier("push_down_out", ResUtils.ANIM, a));
                        } else {
                            topActivity.overridePendingTransition(topActivity.getResources().getIdentifier("h5_slide_in_left", ResUtils.ANIM, a), topActivity.getResources().getIdentifier("h5_slide_out_right", ResUtils.ANIM, a));
                        }
                    }
                } else if (activityFinish != null) {
                    activityFinish.overridePendingTransition(activityFinish.getResources().getIdentifier(a().finishActivityEnterAnim(), ResUtils.ANIM, a), activityFinish.getResources().getIdentifier(a().finishActivityEnterExitAnim(), ResUtils.ANIM, a));
                }
            } catch (Throwable e) {
                H5Log.d("H5AnimatorUtil", " exception : " + e);
            }
        }
    }

    public static void setActivityFadingStart() {
        if (isNeedTransAnim()) {
            try {
                WeakReference topRef = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity();
                if (topRef != null && topRef.get() != null) {
                    Activity topActivity = (Activity) topRef.get();
                    if (topActivity != null) {
                        topActivity.overridePendingTransition(topActivity.getResources().getIdentifier("h5_fading_in", ResUtils.ANIM, a), 0);
                    }
                }
            } catch (Throwable e) {
                H5Log.d("H5AnimatorUtil", "setActivityFadingStart exception : " + e);
            }
        }
    }

    public static void setActivityFadingFinish() {
        if (isNeedTransAnim()) {
            try {
                WeakReference topRef = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity();
                if (topRef != null && topRef.get() != null) {
                    Activity topActivity = (Activity) topRef.get();
                    if (topActivity != null) {
                        topActivity.overridePendingTransition(0, topActivity.getResources().getIdentifier("h5_fading_out", ResUtils.ANIM, a));
                    }
                }
            } catch (Throwable e) {
                H5Log.d("H5AnimatorUtil", "setActivityFadingFinish exception : " + e);
            }
        }
    }

    public static void setActivityPresentFinish() {
        if (isNeedTransAnim()) {
            try {
                WeakReference topRef = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity();
                if (topRef != null && topRef.get() != null) {
                    Activity topActivity = (Activity) topRef.get();
                    if (topActivity != null) {
                        topActivity.overridePendingTransition(0, topActivity.getResources().getIdentifier("push_down_out", ResUtils.ANIM, a));
                    }
                }
            } catch (Throwable e) {
                H5Log.d("H5AnimatorUtil", "setActivityFadingFinish exception : " + e);
            }
        }
    }

    public static boolean isNeedTransAnim() {
        String transAnimConfig = H5Environment.getConfig("h5_transAnim");
        if (TextUtils.isEmpty(transAnimConfig) || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(transAnimConfig)) {
            return true;
        }
        return false;
    }
}
