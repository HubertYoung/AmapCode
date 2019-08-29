package com.alipay.mobile.common.transport.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.alipay.mobile.framework.msg.MsgCodeConstants;

public class AppStartNetWorkingHelper {

    class ClientStartedReceiver extends BroadcastReceiver {
        private Context a;
        private Runnable b;

        public ClientStartedReceiver(Context mContext, Runnable runnable) {
            this.a = mContext;
            this.b = runnable;
        }

        public void onReceive(Context context, Intent intent) {
            try {
                this.b.run();
                try {
                } catch (Exception e) {
                    LogCatUtil.warn((String) "ClientStartedReceiver", "ClientStartedReceiver#onReceive exception : " + e.toString());
                }
            } catch (Exception e2) {
                LogCatUtil.error("AlipayHttpDnsInitHelper", "", e2);
                try {
                } catch (Exception e3) {
                    LogCatUtil.warn((String) "ClientStartedReceiver", "ClientStartedReceiver#onReceive exception : " + e3.toString());
                }
            } finally {
                try {
                    LocalBroadcastManager.getInstance(this.a).unregisterReceiver(this);
                } catch (Exception e4) {
                    LogCatUtil.warn((String) "ClientStartedReceiver", "ClientStartedReceiver#onReceive exception : " + e4.toString());
                }
            }
        }
    }

    public static final void runOnAppStart(Runnable runnable, Context context) {
        if (MiscUtils.isShowUserTip(context)) {
            try {
                LocalBroadcastManager.getInstance(context).registerReceiver(new ClientStartedReceiver(context, runnable), new IntentFilter(MsgCodeConstants.PIPELINE_FRAMEWORK_CLIENT_STARTED));
            } catch (Exception e) {
                LogCatUtil.warn((String) "AppStartNetWorkingHelper", (Throwable) e);
            }
        } else {
            runnable.run();
        }
    }
}
