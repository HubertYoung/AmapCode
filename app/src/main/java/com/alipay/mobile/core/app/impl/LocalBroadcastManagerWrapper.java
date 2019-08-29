package com.alipay.mobile.core.app.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.LocalBroadcastManager.Callback;
import com.alipay.mobile.framework.DescriptionManager;
import com.alipay.mobile.framework.FrameworkMonitor;
import com.alipay.mobile.framework.msg.BroadcastReceiverDescription;
import com.alipay.mobile.quinox.utils.SharedPreferenceUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class LocalBroadcastManagerWrapper {
    static String TAG = "LocalBroadcastManagerWrapper";
    private static LocalBroadcastManager c;
    private static LocalBroadcastManagerWrapper d;
    private boolean a;
    private final List<WeakReference<BroadcastReceiver>> b = new ArrayList();
    private final Set<String> e = new ConcurrentSkipListSet();
    private boolean f = true;

    private LocalBroadcastManagerWrapper(Context context) {
        c = LocalBroadcastManager.getInstance(context);
        try {
            LocalBroadcastManager.class.getDeclaredMethod("registerSubThreadReceiver", new Class[]{BroadcastReceiver.class, IntentFilter.class});
            this.a = true;
            this.f = SharedPreferenceUtil.getInstance().getDefaultSharedPreference(context).getBoolean("quinox_run_in_sub_thread", true);
            this.e.add("com.alipay.android.phone.businesscommon.receiver.NetworkChangeReceiver");
            this.e.add("com.alipay.android.phone.businesscommon.receiver.BackgroundReceiver");
            this.e.add("com.alipay.android.phone.businesscommon.receiver.CleanVavle");
            this.e.add("com.alipay.mobile.base.notification.widget.NotificationStarter");
            this.e.add("com.alipay.mobile.about.service.UpdateBroadcastReceiver");
            this.e.add("com.alipay.android.widget.security.msgreceiver.DeviceLockMsgReceiverNew");
            this.e.add("com.alipay.mobile.security.gesture.service.GestureBackToFrontReceiver");
            this.e.add("com.alipay.mobile.rome.pushservice.adapter.msg.AppActiveMsgReceiver");
            this.e.add("com.alipay.mobile.healthcommon.stepcounter.APStepUploadReceiver");
            this.e.add("com.alipay.android.phone.mobilesdk.permission.guide.info.InfoRpcReceiver");
            this.e.add("com.alipay.mobile.base.receiver.SensorMonitorReceiver");
            this.e.add("com.alipay.mobile.emotion.app.TabChangeListener");
            this.e.add("com.alipay.mobile.socialchatsdk.chat.receiver.ResourceNetChangeReceiver");
            this.e.add("com.alipay.mobile.nebulabiz.receiver.H5AppLoginReceiver");
            this.e.add("com.alipay.android.phone.wallet.inwallet.StandaloneBroadcastReceiver");
            this.e.add("com.alipay.mobile.onsitepaystatic.LoginAndPayBroadCastReceiver");
            this.e.add("com.alipay.android.phone.seauthenticator.iotauth.IOTCacheReceiver");
            this.e.add("com.alipay.android.phone.mobilesdk.abtest.impl.ClientExternalEventReceiver");
            this.e.add("com.alipay.android.phone.mobilecommon.dynamicrelease.RealTimeReceiver");
            this.e.add("com.alipay.android.resourcemanager.receiver.NetStatusReceiver");
            this.e.add("com.alipay.mobile.liteprocess.HostInfoReceiver");
            this.e.add("com.alipay.mobile.group.app.GroupReceiver");
            this.e.add("com.alipay.mobile.base.config.ConfigUpdateBroadCastReceiver");
            this.e.add("com.alipay.mobile.commonbiz.receiver.CommonBizReceiver");
            this.e.add("com.alipay.android.widget.security.msgreceiver.SecurityInitMsgReceiver");
            this.e.add("com.alipay.mobile.chatsdk.broadcastrecv.LogoutInBroadcastReceiver");
            this.e.add("com.alipay.mobile.security.MssSdkLoginReceiver");
            this.e.add("com.alipay.android.phone.mobilesdk.abtest.handler.ClientExternalEventReceiver");
            this.e.add("com.alipay.mobile.appstoreapp.receiver.ClientSetupReceiver");
            this.e.add("com.alipay.android.phone.wallet.sharetoken.ShareTokenCheckReceiver");
            this.e.add("com.alipay.android.phone.messageboxstatic.biz.login.LogInBroadcastReceiver");
        } catch (Throwable tr) {
            TraceLogger.w(TAG, tr);
            this.a = false;
        }
        if (this.a) {
            LocalBroadcastManager.getInstance(context).setCallback(new Callback() {
                public void createReceivers(Intent intent) {
                    if (intent != null && intent.getAction() != null) {
                        for (BroadcastReceiverDescription description : DescriptionManager.getInstance().findBroadcastReceiverDescription(intent.getAction())) {
                            try {
                                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) description.getClassLoader().loadClass(description.getClassName()).newInstance();
                                IntentFilter intentFilter = new IntentFilter();
                                for (String msgCode : description.getMsgCode()) {
                                    intentFilter.addAction(msgCode);
                                }
                                synchronized (description) {
                                    if (!description.hasRegisted()) {
                                        if (description.isSubThread()) {
                                            LocalBroadcastManagerWrapper.this.registerSubThreadReceiver(broadcastReceiver, intentFilter);
                                        } else {
                                            LocalBroadcastManagerWrapper.this.registerReceiver(broadcastReceiver, intentFilter);
                                        }
                                        description.setHasRegisted(true);
                                    }
                                }
                            } catch (Throwable e) {
                                FrameworkMonitor.getInstance(null).handleDescriptionInitFail(description, e);
                                TraceLogger.e(LocalBroadcastManagerWrapper.TAG, "Failed to create receiver: " + description.getClassName(), e);
                            }
                        }
                    }
                }

                public int checkReceivers(Intent intent) {
                    if (intent == null || intent.getAction() == null) {
                        return 0;
                    }
                    int num = 0;
                    for (BroadcastReceiverDescription hasRegisted : DescriptionManager.getInstance().findBroadcastReceiverDescription(intent.getAction())) {
                        if (!hasRegisted.hasRegisted()) {
                            num++;
                        }
                    }
                    return num;
                }
            });
        }
        TraceLogger.i(TAG, "Support SubThread Broadcast: " + this.a);
    }

    public static synchronized LocalBroadcastManagerWrapper getInstance(Context context) {
        LocalBroadcastManagerWrapper localBroadcastManagerWrapper;
        synchronized (LocalBroadcastManagerWrapper.class) {
            if (d == null) {
                d = new LocalBroadcastManagerWrapper(context);
            }
            localBroadcastManagerWrapper = d;
        }
        return localBroadcastManagerWrapper;
    }

    public boolean isSupportSubThreadBroadcast() {
        return this.a;
    }

    public void registerSubThreadReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        if (isSupportSubThreadBroadcast()) {
            synchronized (this.b) {
                this.b.add(new WeakReference(receiver));
            }
            c.registerSubThreadReceiver(receiver, filter);
            return;
        }
        registerReceiver(receiver, filter);
    }

    public void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        if (!this.f) {
            TraceLogger.i(TAG, (String) "config say we can not run in sub thread mode, ignore.");
        } else if (receiver != null && this.e.contains(receiver.getClass().getName())) {
            TraceLogger.i(TAG, "found receiver which can run in sub thread mode:" + receiver);
            registerSubThreadReceiver(receiver, filter);
            return;
        }
        synchronized (this.b) {
            this.b.add(new WeakReference(receiver));
        }
        c.registerReceiver(receiver, filter);
    }

    public void unregisterReceiver(BroadcastReceiver receiver) {
        c.unregisterReceiver(receiver);
    }

    public void registerReceiverDescription(BroadcastReceiverDescription description) {
    }

    public void close() {
        synchronized (this.b) {
            for (WeakReference weakReceiver : this.b) {
                if (weakReceiver.get() != null) {
                    c.unregisterReceiver((BroadcastReceiver) weakReceiver.get());
                }
            }
            this.b.clear();
        }
    }
}
