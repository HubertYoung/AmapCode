package com.alipay.mobile.liteprocess.advice;

import android.content.Intent;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.liteprocess.LiteProcessServerManager;
import com.alipay.mobile.quinox.utils.LiteProcessInfo;
import com.sina.weibo.sdk.constant.WBConstants;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public abstract class StartActivityWithoutMicroAppAdvice implements InvocationHandler {
    /* access modifiers changed from: protected */
    public abstract boolean a();

    private boolean a(Intent intent) {
        if (intent == null || !a() || LiteProcessServerManager.g().isAllLiteProcessHide()) {
            return false;
        }
        try {
            if (LiteProcessServerManager.g().startActivityFromLiteProcessIfNeeded(LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopApplication(), intent)) {
                LoggerFactory.getTraceLogger().debug(LiteProcessInfo.TAG, getClass().getSimpleName() + " hook intent= " + intent.toUri(1));
                return true;
            }
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error((String) LiteProcessInfo.TAG, Log.getStackTraceString(t));
        }
        return false;
    }

    public Object invoke(Object proxy, Method method, Object[] args) {
        try {
            if (method.getName().equals(WBConstants.SHARE_START_ACTIVITY)) {
                return Boolean.valueOf(a(args[0]));
            }
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error((String) LiteProcessInfo.TAG, t);
        }
        return null;
    }
}
