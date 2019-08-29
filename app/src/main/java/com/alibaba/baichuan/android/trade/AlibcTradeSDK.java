package com.alibaba.baichuan.android.trade;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.baichuan.android.auth.AlibcAuth;
import com.alibaba.baichuan.android.trade.AlibcContext.Environment;
import com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.adapter.mtop.AlibcMtop;
import com.alibaba.baichuan.android.trade.adapter.security.AlibcSecurityGuard;
import com.alibaba.baichuan.android.trade.adapter.ut.AlibcUserTracker;
import com.alibaba.baichuan.android.trade.adapter.ut.UserTrackerCompoment;
import com.alibaba.baichuan.android.trade.adapter.ut.performance.Point4Init;
import com.alibaba.baichuan.android.trade.c.a.a.e;
import com.alibaba.baichuan.android.trade.callback.AlibcCallbackContext;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.baichuan.android.trade.config.AlibcConfig;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.model.InitResult;
import com.alibaba.baichuan.android.trade.model.InitState;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.util.HashMap;
import java.util.Map;

public class AlibcTradeSDK {
    private static String a = "com.alibaba.baichuan.android.trade.AlibcTradeSDK";
    public static InitResult initResult;
    public static InitState initState = new InitState();

    private static void a(Point4Init point4Init) {
        initResult = AlibcSecurityGuard.getInstance().init(point4Init);
        try {
            AlibcUserTracker.getInstance();
        } catch (Exception e) {
            AlibcLogger.e(a, "ut init exception", e);
        }
        if (initResult.isSuccess()) {
            point4Init.utInitTime = UserTrackerCompoment.getUTInitTime();
        }
        AlibcMtop.getInstance().init();
        AlibcLogin.getInstance().init();
        AlibcAuth.init();
        AlibcContext.initData();
        e.a().b();
        AlibcApplink.getInstance().initApplink();
    }

    private static void a(AlibcTradeInitCallback alibcTradeInitCallback, InitResult initResult2) {
        initState.setState(3);
        AlibcContext.executorService.b(new e(alibcTradeInitCallback, initResult2));
    }

    public static synchronized void asyncInit(Context context, AlibcTradeInitCallback alibcTradeInitCallback) {
        synchronized (AlibcTradeSDK.class) {
            if (c(alibcTradeInitCallback)) {
                initState.setState(1);
                AlibcContext.context = context.getApplicationContext();
                AlibcContext.executorService.a(new b(alibcTradeInitCallback));
            }
        }
    }

    /* access modifiers changed from: private */
    public static void b() {
        HashMap hashMap = new HashMap();
        String appKey = AlibcContext.getAppKey();
        if (!TextUtils.isEmpty(appKey)) {
            hashMap.put("appkey", appKey);
        }
        hashMap.put("ttid", AlibcConfig.getInstance().getWebTTID());
        hashMap.put(UserTrackerConstants.SDK_TYPE, "standard");
        AlibcUserTracker.getInstance().sendCustomHit(UserTrackerConstants.E_SDK_INIT, (String) "", (Map) hashMap);
        AlibcUserTracker.getInstance().sendInitHit4DAU();
    }

    /* access modifiers changed from: private */
    public static synchronized void b(AlibcTradeInitCallback alibcTradeInitCallback) {
        synchronized (AlibcTradeSDK.class) {
            Point4Init point4Init = new Point4Init();
            point4Init.timeBegin(UserTrackerConstants.PM_ALL_TIME);
            a(point4Init);
            if (initResult.isSuccess()) {
                point4Init.timeEnd(UserTrackerConstants.PM_ALL_TIME);
                UserTrackerCompoment.sendPerfomancePoint(point4Init);
                d(alibcTradeInitCallback);
                return;
            }
            a(alibcTradeInitCallback, initResult);
        }
    }

    private static boolean c(AlibcTradeInitCallback alibcTradeInitCallback) {
        if (initState.isInitializing()) {
            AlibcCallbackContext.pendingInitCallbacks.add(alibcTradeInitCallback);
            return false;
        } else if (!initState.isInitialized()) {
            return true;
        } else {
            AlibcContext.executorService.b(new c(alibcTradeInitCallback));
            return false;
        }
    }

    private static void d(AlibcTradeInitCallback alibcTradeInitCallback) {
        AlibcLogger.i(a, "AlibcTradeSDK初始化成功");
        initState.setState(2);
        AlibcContext.executorService.b(new d(alibcTradeInitCallback));
    }

    public static void destory() {
        AlibcCallbackContext.pendingInitCallbacks.clear();
    }

    public static void setChannel(String str, String str2) {
        AlibcConfig.getInstance().setChannel(str, str2);
    }

    public static void setEnvironment(Environment environment) {
        AlibcContext.environment = environment;
        AlibcContext.updateUrl();
    }

    public static boolean setForceH5(boolean z) {
        return AlibcConfig.getInstance().setIsForceH5(z);
    }

    public static boolean setISVCode(String str) {
        return AlibcConfig.getInstance().setIsvCode(str);
    }

    public static boolean setISVVersion(String str) {
        return AlibcConfig.getInstance().setIsvVersion(str);
    }

    public static void setIsAuthVip(boolean z) {
        AlibcContext.isVip = z;
    }

    public static boolean setShouldUseAlipay(boolean z) {
        return AlibcConfig.getInstance().setShouldUseAlipay(z);
    }

    public static boolean setSyncForTaoke(boolean z) {
        return AlibcConfig.getInstance().setIsSyncForTaoke(z);
    }

    public static void setTaokeParams(AlibcTaokeParams alibcTaokeParams) {
        AlibcConfig.getInstance().setTaokeParams(alibcTaokeParams);
    }
}
