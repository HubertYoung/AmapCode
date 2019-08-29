package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LogContext;
import com.xiaomi.channel.commonutils.android.f;
import com.xiaomi.channel.commonutils.android.g;
import com.xiaomi.channel.commonutils.android.j;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.misc.h;
import com.xiaomi.channel.commonutils.string.d;
import com.xiaomi.mipush.sdk.MiTinyDataClient.a;
import com.xiaomi.push.mpcd.c;
import com.xiaomi.push.service.an;
import com.xiaomi.push.service.bc;
import com.xiaomi.push.service.k;
import com.xiaomi.push.service.module.PushChannelRegion;
import com.xiaomi.push.service.p;
import com.xiaomi.push.service.receivers.NetworkStatusReceiver;
import com.xiaomi.xmpush.thrift.ad;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.aj;
import com.xiaomi.xmpush.thrift.ao;
import com.xiaomi.xmpush.thrift.aq;
import com.xiaomi.xmpush.thrift.as;
import com.xiaomi.xmpush.thrift.r;
import com.xiaomi.xmpush.thrift.u;
import com.xiaomi.xmpush.thrift.w;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public abstract class MiPushClient {
    public static final String COMMAND_REGISTER = "register";
    public static final String COMMAND_SET_ACCEPT_TIME = "accept-time";
    public static final String COMMAND_SET_ACCOUNT = "set-account";
    public static final String COMMAND_SET_ALIAS = "set-alias";
    public static final String COMMAND_SUBSCRIBE_TOPIC = "subscribe-topic";
    public static final String COMMAND_UNREGISTER = "unregister";
    public static final String COMMAND_UNSET_ACCOUNT = "unset-account";
    public static final String COMMAND_UNSET_ALIAS = "unset-alias";
    public static final String COMMAND_UNSUBSCRIBE_TOPIC = "unsubscibe-topic";
    public static final String PREF_EXTRA = "mipush_extra";
    private static boolean isCrashHandlerSuggested = false;
    private static as mSyncMIIDHelper;
    /* access modifiers changed from: private */
    public static Context sContext;
    private static long sCurMsgId = System.currentTimeMillis();

    @Deprecated
    public static abstract class MiPushClientCallback {
        private String category;

        /* access modifiers changed from: protected */
        public String getCategory() {
            return this.category;
        }

        public void onCommandResult(String str, long j, String str2, List<String> list) {
        }

        public void onInitializeResult(long j, String str, String str2) {
        }

        public void onReceiveMessage(MiPushMessage miPushMessage) {
        }

        public void onReceiveMessage(String str, String str2, String str3, boolean z) {
        }

        public void onSubscribeResult(long j, String str, String str2) {
        }

        public void onUnsubscribeResult(long j, String str, String str2) {
        }

        /* access modifiers changed from: protected */
        public void setCategory(String str) {
            this.category = str;
        }
    }

    private static boolean acceptTimeSet(Context context, String str, String str2) {
        String acceptTime = getAcceptTime(context);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(",");
        sb.append(str2);
        return TextUtils.equals(acceptTime, sb.toString());
    }

    public static long accountSetTime(Context context, String str) {
        return context.getSharedPreferences("mipush_extra", 0).getLong("account_".concat(String.valueOf(str)), -1);
    }

    static synchronized void addAcceptTime(Context context, String str, String str2) {
        synchronized (MiPushClient.class) {
            Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(",");
            sb.append(str2);
            edit.putString(Constants.EXTRA_KEY_ACCEPT_TIME, sb.toString());
            ap.a(edit);
        }
    }

    static synchronized void addAccount(Context context, String str) {
        synchronized (MiPushClient.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().putLong("account_".concat(String.valueOf(str)), System.currentTimeMillis()).commit();
        }
    }

    static synchronized void addAlias(Context context, String str) {
        synchronized (MiPushClient.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().putLong("alias_".concat(String.valueOf(str)), System.currentTimeMillis()).commit();
        }
    }

    private static void addPullNotificationTime(Context context) {
        Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
        edit.putLong("last_pull_notification", System.currentTimeMillis());
        ap.a(edit);
    }

    private static void addRegRequestTime(Context context) {
        Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
        edit.putLong("last_reg_request", System.currentTimeMillis());
        ap.a(edit);
    }

    static synchronized void addTopic(Context context, String str) {
        synchronized (MiPushClient.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().putLong("topic_".concat(String.valueOf(str)), System.currentTimeMillis()).commit();
        }
    }

    public static long aliasSetTime(Context context, String str) {
        return context.getSharedPreferences("mipush_extra", 0).getLong("alias_".concat(String.valueOf(str)), -1);
    }

    public static void awakeApps(Context context, String[] strArr) {
        new Thread(new x(strArr, context)).start();
    }

    /* access modifiers changed from: private */
    public static void awakePushServiceByPackageInfo(Context context, PackageInfo packageInfo) {
        ServiceInfo[] serviceInfoArr = packageInfo.services;
        if (serviceInfoArr != null) {
            int length = serviceInfoArr.length;
            int i = 0;
            while (i < length) {
                ServiceInfo serviceInfo = serviceInfoArr[i];
                if (!serviceInfo.exported || !serviceInfo.enabled || !"com.xiaomi.mipush.sdk.PushMessageHandler".equals(serviceInfo.name) || context.getPackageName().equals(serviceInfo.packageName)) {
                    i++;
                } else {
                    try {
                        Thread.sleep(((long) ((Math.random() * 2.0d) + 1.0d)) * 1000);
                        Intent intent = new Intent();
                        intent.setClassName(serviceInfo.packageName, serviceInfo.name);
                        intent.setAction("com.xiaomi.mipush.sdk.WAKEUP");
                        intent.putExtra("waker_pkgname", context.getPackageName());
                        context.startService(intent);
                    } catch (Throwable unused) {
                    }
                    return;
                }
            }
        }
    }

    private static void checkNotNull(Object obj, Object obj2) {
    }

    private static void checkNotNull(Object obj, String str) {
        if (obj == null) {
            StringBuilder sb = new StringBuilder("param ");
            sb.append(str);
            sb.append(" is not nullable");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    protected static void clearExtras(Context context) {
        Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
        edit.clear();
        edit.commit();
    }

    public static void clearLocalNotificationType(Context context) {
        aj.a(context).f();
    }

    public static void clearNotification(Context context) {
        aj.a(context).a(-1);
    }

    public static void clearNotification(Context context, int i) {
        aj.a(context).a(i);
    }

    public static void clearNotification(Context context, String str, String str2) {
        aj.a(context).a(str, str2);
    }

    public static void disablePush(Context context) {
        aj.a(context).a(true);
    }

    private static void enableGeo(Context context, boolean z) {
        if (Math.abs(System.currentTimeMillis() - getGeoEnableTime(context, String.valueOf(z))) > 60000) {
            k.a(context, z);
            p.a(context, z);
            setGeoEnableTime(context, String.valueOf(z));
        }
    }

    public static void enablePush(Context context) {
        aj.a(context).a(false);
    }

    protected static synchronized String generatePacketID() {
        String sb;
        synchronized (MiPushClient.class) {
            try {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(d.a(4));
                sb2.append(sCurMsgId);
                sb = sb2.toString();
                sCurMsgId++;
            }
        }
        return sb;
    }

    protected static String getAcceptTime(Context context) {
        return context.getSharedPreferences("mipush_extra", 0).getString(Constants.EXTRA_KEY_ACCEPT_TIME, "00:00-23:59");
    }

    public static List<String> getAllAlias(Context context) {
        ArrayList arrayList = new ArrayList();
        for (String next : context.getSharedPreferences("mipush_extra", 0).getAll().keySet()) {
            if (next.startsWith("alias_")) {
                arrayList.add(next.substring(6));
            }
        }
        return arrayList;
    }

    public static List<String> getAllTopic(Context context) {
        ArrayList arrayList = new ArrayList();
        for (String next : context.getSharedPreferences("mipush_extra", 0).getAll().keySet()) {
            if (next.startsWith("topic_") && !next.contains("**ALL**")) {
                arrayList.add(next.substring(6));
            }
        }
        return arrayList;
    }

    public static List<String> getAllUserAccount(Context context) {
        ArrayList arrayList = new ArrayList();
        for (String next : context.getSharedPreferences("mipush_extra", 0).getAll().keySet()) {
            if (next.startsWith("account_")) {
                arrayList.add(next.substring(8));
            }
        }
        return arrayList;
    }

    private static boolean getDefaultSwitch() {
        return f.b();
    }

    private static long getGeoEnableTime(Context context, String str) {
        return context.getSharedPreferences("mipush_extra", 0).getLong("geo_".concat(String.valueOf(str)), -1);
    }

    protected static boolean getOpenFCMPush() {
        return e.a(sContext).d(d.ASSEMBLE_PUSH_FCM);
    }

    protected static boolean getOpenHmsPush() {
        return e.a(sContext).d(d.ASSEMBLE_PUSH_HUAWEI);
    }

    protected static boolean getOpenOPPOPush() {
        return e.a(sContext).d(d.ASSEMBLE_PUSH_COS);
    }

    public static String getRegId(Context context) {
        if (c.a(context).i()) {
            return c.a(context).e();
        }
        return null;
    }

    @Deprecated
    public static void initialize(Context context, String str, String str2, MiPushClientCallback miPushClientCallback) {
        checkNotNull((Object) context, (String) "context");
        checkNotNull((Object) str, (String) LogContext.STORAGE_APPID);
        checkNotNull((Object) str2, (String) "appToken");
        try {
            Context applicationContext = context.getApplicationContext();
            sContext = applicationContext;
            if (applicationContext == null) {
                sContext = context;
            }
            if (miPushClientCallback != null) {
                PushMessageHandler.a(miPushClientCallback);
            }
            if (j.b(context)) {
                r.a(context);
            }
            boolean z = c.a(sContext).l() != Constants.a();
            if (z || shouldSendRegRequest(sContext)) {
                if (z || !c.a(sContext).a(str, str2) || c.a(sContext).m()) {
                    String a = d.a(6);
                    c.a(sContext).h();
                    c.a(sContext).a(Constants.a());
                    c.a(sContext).a(str, str2, a);
                    a.a().b((String) MiTinyDataClient.PENDING_REASON_APPID);
                    clearExtras(sContext);
                    aj ajVar = new aj();
                    ajVar.a(generatePacketID());
                    ajVar.b(str);
                    ajVar.e(str2);
                    ajVar.d(context.getPackageName());
                    ajVar.f(a);
                    ajVar.c(com.xiaomi.channel.commonutils.android.a.a(context, context.getPackageName()));
                    ajVar.b(com.xiaomi.channel.commonutils.android.a.b(context, context.getPackageName()));
                    ajVar.g("3_6_2");
                    ajVar.a(30602);
                    ajVar.h(com.xiaomi.channel.commonutils.android.d.a(sContext));
                    ajVar.a(w.Init);
                    if (f.e()) {
                        String c = com.xiaomi.channel.commonutils.android.d.c(sContext);
                        String e = com.xiaomi.channel.commonutils.android.d.e(sContext);
                        if (!TextUtils.isEmpty(c)) {
                            if (f.b()) {
                                if (!TextUtils.isEmpty(e)) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(c);
                                    sb.append(",");
                                    sb.append(e);
                                    c = sb.toString();
                                }
                                ajVar.i(c);
                            }
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(d.a(c));
                            sb2.append(",");
                            sb2.append(com.xiaomi.channel.commonutils.android.d.f(sContext));
                            ajVar.k(sb2.toString());
                        }
                    }
                    ajVar.j(com.xiaomi.channel.commonutils.android.d.a());
                    int b = com.xiaomi.channel.commonutils.android.d.b();
                    if (b >= 0) {
                        ajVar.c(b);
                    }
                    aj.a(sContext).a(ajVar, z);
                    a.a(context);
                    bc.a(context).a((String) "mipush_extra", (String) "mipush_registed", true);
                } else {
                    if (1 == PushMessageHelper.getPushMode(context)) {
                        checkNotNull((Object) miPushClientCallback, (String) "callback");
                        miPushClientCallback.onInitializeResult(0, null, c.a(context).e());
                    } else {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(c.a(context).e());
                        PushMessageHelper.sendCommandMessageBroadcast(sContext, PushMessageHelper.generateCommandMessage("register", arrayList, 0, null, null));
                    }
                    aj.a(context).a();
                    if (c.a(sContext).a()) {
                        ai aiVar = new ai();
                        aiVar.b(c.a(context).c());
                        aiVar.c("client_info_update");
                        aiVar.a(generatePacketID());
                        aiVar.h = new HashMap();
                        Map<String, String> map = aiVar.h;
                        Context context2 = sContext;
                        map.put("app_version", com.xiaomi.channel.commonutils.android.a.a(context2, context2.getPackageName()));
                        Map<String, String> map2 = aiVar.h;
                        Context context3 = sContext;
                        map2.put(Constants.EXTRA_KEY_APP_VERSION_CODE, Integer.toString(com.xiaomi.channel.commonutils.android.a.b(context3, context3.getPackageName())));
                        aiVar.h.put("push_sdk_vn", "3_6_2");
                        aiVar.h.put("push_sdk_vc", Integer.toString(30602));
                        String g = c.a(sContext).g();
                        if (!TextUtils.isEmpty(g)) {
                            aiVar.h.put("deviceid", g);
                        }
                        aj.a(context).a(aiVar, com.xiaomi.xmpush.thrift.a.Notification, false, (u) null);
                        a.a(context);
                    }
                    if (!g.a(sContext, (String) "update_devId", false)) {
                        updateIMEI();
                        g.b(sContext, "update_devId", true);
                    }
                    if (shouldUseMIUIPush(sContext) && shouldPullNotification(sContext)) {
                        ai aiVar2 = new ai();
                        aiVar2.b(c.a(sContext).c());
                        aiVar2.c(r.PullOfflineMessage.W);
                        aiVar2.a(generatePacketID());
                        aiVar2.a(false);
                        aj.a(sContext).a(aiVar2, com.xiaomi.xmpush.thrift.a.Notification, false, (u) null, false);
                        addPullNotificationTime(sContext);
                    }
                }
                addRegRequestTime(sContext);
                scheduleOcVersionCheckJob();
                scheduleGeoFenceLocUploadJobs();
                scheduleDataCollectionJobs(context);
                aq.a(sContext);
                if (mSyncMIIDHelper == null) {
                    mSyncMIIDHelper = new as(sContext);
                }
                mSyncMIIDHelper.a(sContext);
                if ("syncing".equals(ab.a(sContext).a(ao.DISABLE_PUSH))) {
                    disablePush(sContext);
                }
                if ("syncing".equals(ab.a(sContext).a(ao.ENABLE_PUSH))) {
                    enablePush(sContext);
                }
                if ("syncing".equals(ab.a(sContext).a(ao.UPLOAD_HUAWEI_TOKEN))) {
                    syncAssemblePushToken(sContext);
                }
                if ("syncing".equals(ab.a(sContext).a(ao.UPLOAD_FCM_TOKEN))) {
                    syncAssembleFCMPushToken(sContext);
                }
                if ("syncing".equals(ab.a(context).a(ao.UPLOAD_COS_TOKEN))) {
                    syncAssembleCOSPushToken(context);
                }
                return;
            }
            aj.a(context).a();
            b.a((String) "Could not send  register message within 5s repeatly .");
        } catch (Exception e2) {
            b.d(e2.toString());
        } catch (Throwable th) {
            b.a(th);
        }
    }

    public static void pausePush(Context context, String str) {
        setAcceptTime(context, 0, 0, 0, 0, str);
    }

    static void reInitialize(Context context, w wVar) {
        if (c.a(context).i()) {
            String a = d.a(6);
            String c = c.a(context).c();
            String d = c.a(context).d();
            c.a(context).h();
            c.a(context).a(c, d, a);
            aj ajVar = new aj();
            ajVar.a(generatePacketID());
            ajVar.b(c);
            ajVar.e(d);
            ajVar.f(a);
            ajVar.d(context.getPackageName());
            ajVar.c(com.xiaomi.channel.commonutils.android.a.a(context, context.getPackageName()));
            ajVar.a(wVar);
            aj.a(context).a(ajVar, false);
        }
    }

    private static void registerNetworkReceiver(Context context) {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            intentFilter.addCategory("android.intent.category.DEFAULT");
            context.getApplicationContext().registerReceiver(new NetworkStatusReceiver(null), intentFilter);
        } catch (Throwable th) {
            b.a(th);
        }
    }

    public static void registerPush(Context context, String str, String str2) {
        registerPush(context, str, str2, new PushConfiguration());
    }

    public static void registerPush(Context context, String str, String str2, PushConfiguration pushConfiguration) {
        if (!NetworkStatusReceiver.a()) {
            registerNetworkReceiver(context);
        }
        if (TextUtils.equals("com.xiaomi.xmsf", context.getPackageName())) {
            PushChannelRegion region = pushConfiguration.getRegion();
            checkNotNull((Object) region, (Object) PushChannelRegion.China);
            com.xiaomi.push.service.a.a(context).a(region);
        }
        e.a(context).a(pushConfiguration);
        enableGeo(context, pushConfiguration.getGeoEnable());
        a.a();
        h.a(context).a((Runnable) new u(context, str, str2));
    }

    static synchronized void removeAcceptTime(Context context) {
        synchronized (MiPushClient.class) {
            Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
            edit.remove(Constants.EXTRA_KEY_ACCEPT_TIME);
            ap.a(edit);
        }
    }

    static synchronized void removeAccount(Context context, String str) {
        synchronized (MiPushClient.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().remove("account_".concat(String.valueOf(str))).commit();
        }
    }

    static synchronized void removeAlias(Context context, String str) {
        synchronized (MiPushClient.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().remove("alias_".concat(String.valueOf(str))).commit();
        }
    }

    static synchronized void removeAllAccounts(Context context) {
        synchronized (MiPushClient.class) {
            for (String removeAccount : getAllUserAccount(context)) {
                removeAccount(context, removeAccount);
            }
        }
    }

    static synchronized void removeAllAliases(Context context) {
        synchronized (MiPushClient.class) {
            for (String removeAlias : getAllAlias(context)) {
                removeAlias(context, removeAlias);
            }
        }
    }

    static synchronized void removeAllTopics(Context context) {
        synchronized (MiPushClient.class) {
            for (String removeTopic : getAllTopic(context)) {
                removeTopic(context, removeTopic);
            }
        }
    }

    static synchronized void removeTopic(Context context, String str) {
        synchronized (MiPushClient.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().remove("topic_".concat(String.valueOf(str))).commit();
        }
    }

    static void reportIgnoreRegMessageClicked(Context context, String str, u uVar, String str2, String str3) {
        ai aiVar = new ai();
        if (TextUtils.isEmpty(str3)) {
            b.d("do not report clicked message");
            return;
        }
        aiVar.b(str3);
        aiVar.c("bar:click");
        aiVar.a(str);
        aiVar.a(false);
        aj.a(context).a(aiVar, com.xiaomi.xmpush.thrift.a.Notification, false, true, uVar, true, str2, str3);
    }

    public static void reportMessageClicked(Context context, MiPushMessage miPushMessage) {
        u uVar = new u();
        uVar.a(miPushMessage.getMessageId());
        uVar.b(miPushMessage.getTopic());
        uVar.d(miPushMessage.getDescription());
        uVar.c(miPushMessage.getTitle());
        uVar.c(miPushMessage.getNotifyId());
        uVar.a(miPushMessage.getNotifyType());
        uVar.b(miPushMessage.getPassThrough());
        uVar.a(miPushMessage.getExtra());
        reportMessageClicked(context, miPushMessage.getMessageId(), uVar, null);
    }

    @Deprecated
    public static void reportMessageClicked(Context context, String str) {
        reportMessageClicked(context, str, null, null);
    }

    static void reportMessageClicked(Context context, String str, u uVar, String str2) {
        ai aiVar = new ai();
        if (TextUtils.isEmpty(str2)) {
            if (c.a(context).b()) {
                str2 = c.a(context).c();
            } else {
                b.d("do not report clicked message");
                return;
            }
        }
        aiVar.b(str2);
        aiVar.c("bar:click");
        aiVar.a(str);
        aiVar.a(false);
        aj.a(context).a(aiVar, com.xiaomi.xmpush.thrift.a.Notification, false, uVar);
    }

    public static void resumePush(Context context, String str) {
        setAcceptTime(context, 0, 0, 23, 59, str);
    }

    private static void scheduleDataCollectionJobs(Context context) {
        if (an.a(sContext).a(com.xiaomi.xmpush.thrift.g.DataCollectionSwitch.a(), getDefaultSwitch())) {
            c.a().a(new l(context));
            h.a(sContext).a((Runnable) new v(), 10);
        }
    }

    private static void scheduleGeoFenceLocUploadJobs() {
        if (k.e(sContext) && !TextUtils.equals("com.xiaomi.xmsf", sContext.getPackageName()) && an.a(sContext).a(com.xiaomi.xmpush.thrift.g.UploadGeoAppLocSwitch.a(), true) && !j.e()) {
            n.a(sContext, true);
            int max = Math.max(60, an.a(sContext).a(com.xiaomi.xmpush.thrift.g.UploadWIFIGeoLocFrequency.a(), 900));
            h.a(sContext).a(new n(sContext, max), max, max);
        }
    }

    private static void scheduleOcVersionCheckJob() {
        h.a(sContext).a(new aa(sContext), an.a(sContext).a(com.xiaomi.xmpush.thrift.g.OcVersionCheckFrequency.a(), 86400), 5);
    }

    public static void setAcceptTime(Context context, int i, int i2, int i3, int i4, String str) {
        Context context2 = context;
        int i5 = i;
        int i6 = i2;
        int i7 = i3;
        int i8 = i4;
        if (i5 < 0 || i5 >= 24 || i7 < 0 || i7 >= 24 || i6 < 0 || i6 >= 60 || i8 < 0 || i8 >= 60) {
            throw new IllegalArgumentException("the input parameter is not valid.");
        }
        long rawOffset = (long) (((TimeZone.getTimeZone("GMT+08").getRawOffset() - TimeZone.getDefault().getRawOffset()) / 1000) / 60);
        long j = ((((long) ((i5 * 60) + i6)) + rawOffset) + 1440) % 1440;
        long j2 = ((((long) ((i7 * 60) + i8)) + rawOffset) + 1440) % 1440;
        ArrayList arrayList = new ArrayList();
        arrayList.add(String.format("%1$02d:%2$02d", new Object[]{Long.valueOf(j / 60), Long.valueOf(j % 60)}));
        arrayList.add(String.format("%1$02d:%2$02d", new Object[]{Long.valueOf(j2 / 60), Long.valueOf(j2 % 60)}));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(String.format("%1$02d:%2$02d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
        arrayList2.add(String.format("%1$02d:%2$02d", new Object[]{Integer.valueOf(i3), Integer.valueOf(i4)}));
        if (!acceptTimeSet(context2, (String) arrayList.get(0), (String) arrayList.get(1))) {
            setCommand(context2, (String) COMMAND_SET_ACCEPT_TIME, arrayList, str);
        } else if (1 == PushMessageHelper.getPushMode(context)) {
            PushMessageHandler.a(context2, str, COMMAND_SET_ACCEPT_TIME, 0, null, arrayList2);
        } else {
            PushMessageHelper.sendCommandMessageBroadcast(context2, PushMessageHelper.generateCommandMessage(COMMAND_SET_ACCEPT_TIME, arrayList2, 0, null, null));
        }
    }

    public static void setAlias(Context context, String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            setCommand(context, (String) COMMAND_SET_ALIAS, str, str2);
        }
    }

    protected static void setCommand(Context context, String str, String str2, String str3) {
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
        }
        if (!COMMAND_SET_ALIAS.equalsIgnoreCase(str) || Math.abs(System.currentTimeMillis() - aliasSetTime(context, str2)) >= 3600000) {
            if (COMMAND_UNSET_ALIAS.equalsIgnoreCase(str) && aliasSetTime(context, str2) < 0) {
                StringBuilder sb = new StringBuilder("Don't cancel alias for ");
                sb.append(arrayList);
                sb.append(" is unseted");
                b.a(sb.toString());
            } else if (!COMMAND_SET_ACCOUNT.equalsIgnoreCase(str) || Math.abs(System.currentTimeMillis() - accountSetTime(context, str2)) >= 3600000) {
                if (!COMMAND_UNSET_ACCOUNT.equalsIgnoreCase(str) || accountSetTime(context, str2) >= 0) {
                    setCommand(context, str, arrayList, str3);
                    return;
                }
                StringBuilder sb2 = new StringBuilder("Don't cancel account for ");
                sb2.append(arrayList);
                sb2.append(" is unseted");
                b.a(sb2.toString());
            } else if (1 == PushMessageHelper.getPushMode(context)) {
                PushMessageHandler.a(context, str3, str, 0, null, arrayList);
            } else {
                PushMessageHelper.sendCommandMessageBroadcast(context, PushMessageHelper.generateCommandMessage(COMMAND_SET_ACCOUNT, arrayList, 0, null, null));
            }
        } else if (1 == PushMessageHelper.getPushMode(context)) {
            PushMessageHandler.a(context, str3, str, 0, null, arrayList);
        } else {
            PushMessageHelper.sendCommandMessageBroadcast(context, PushMessageHelper.generateCommandMessage(COMMAND_SET_ALIAS, arrayList, 0, null, null));
        }
    }

    protected static void setCommand(Context context, String str, ArrayList<String> arrayList, String str2) {
        if (!TextUtils.isEmpty(c.a(context).c())) {
            ad adVar = new ad();
            adVar.a(generatePacketID());
            adVar.b(c.a(context).c());
            adVar.c(str);
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                adVar.d(it.next());
            }
            adVar.f(str2);
            adVar.e(context.getPackageName());
            aj.a(context).a(adVar, com.xiaomi.xmpush.thrift.a.Command, (u) null);
        }
    }

    static synchronized void setGeoEnableTime(Context context, String str) {
        synchronized (MiPushClient.class) {
            Editor edit = context.getSharedPreferences("mipush_extra", 0).edit();
            edit.putLong("geo_".concat(String.valueOf(str)), System.currentTimeMillis());
            ap.a(edit);
        }
    }

    public static void setLocalNotificationType(Context context, int i) {
        aj.a(context).b(i & -1);
    }

    public static void setUserAccount(Context context, String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            setCommand(context, (String) COMMAND_SET_ACCOUNT, str, str2);
        }
    }

    private static boolean shouldPullNotification(Context context) {
        return Math.abs(System.currentTimeMillis() - context.getSharedPreferences("mipush_extra", 0).getLong("last_pull_notification", -1)) > 300000;
    }

    private static boolean shouldSendRegRequest(Context context) {
        return Math.abs(System.currentTimeMillis() - context.getSharedPreferences("mipush_extra", 0).getLong("last_reg_request", -1)) > 5000;
    }

    public static boolean shouldUseMIUIPush(Context context) {
        return aj.a(context).c();
    }

    public static void subscribe(Context context, String str, String str2) {
        if (!TextUtils.isEmpty(c.a(context).c()) && !TextUtils.isEmpty(str)) {
            if (Math.abs(System.currentTimeMillis() - topicSubscribedTime(context, str)) > 86400000) {
                ao aoVar = new ao();
                aoVar.a(generatePacketID());
                aoVar.b(c.a(context).c());
                aoVar.c(str);
                aoVar.d(context.getPackageName());
                aoVar.e(str2);
                aj.a(context).a(aoVar, com.xiaomi.xmpush.thrift.a.Subscription, (u) null);
            } else if (1 == PushMessageHelper.getPushMode(context)) {
                PushMessageHandler.a(context, str2, 0, null, str);
            } else {
                ArrayList arrayList = new ArrayList();
                arrayList.add(str);
                PushMessageHelper.sendCommandMessageBroadcast(context, PushMessageHelper.generateCommandMessage(COMMAND_SUBSCRIBE_TOPIC, arrayList, 0, null, null));
            }
        }
    }

    public static void syncAssembleCOSPushToken(Context context) {
        aj.a(context).a((String) null, ao.UPLOAD_COS_TOKEN, d.ASSEMBLE_PUSH_COS);
    }

    public static void syncAssembleFCMPushToken(Context context) {
        aj.a(context).a((String) null, ao.UPLOAD_FCM_TOKEN, d.ASSEMBLE_PUSH_FCM);
    }

    public static void syncAssemblePushToken(Context context) {
        aj.a(context).a((String) null, ao.UPLOAD_HUAWEI_TOKEN, d.ASSEMBLE_PUSH_HUAWEI);
    }

    public static long topicSubscribedTime(Context context, String str) {
        return context.getSharedPreferences("mipush_extra", 0).getLong("topic_".concat(String.valueOf(str)), -1);
    }

    public static void unregisterPush(Context context) {
        g.d(context);
        if (c.a(context).b()) {
            aq aqVar = new aq();
            aqVar.a(generatePacketID());
            aqVar.b(c.a(context).c());
            aqVar.c(c.a(context).e());
            aqVar.e(c.a(context).d());
            aqVar.d(context.getPackageName());
            aj.a(context).a(aqVar);
            PushMessageHandler.a();
            c.a(context).j();
            clearLocalNotificationType(context);
            clearNotification(context);
            if (mSyncMIIDHelper != null) {
                p.a(context).b(mSyncMIIDHelper);
            }
            clearExtras(context);
        }
    }

    public static void unsetAlias(Context context, String str, String str2) {
        setCommand(context, (String) COMMAND_UNSET_ALIAS, str, str2);
    }

    public static void unsetUserAccount(Context context, String str, String str2) {
        setCommand(context, (String) COMMAND_UNSET_ACCOUNT, str, str2);
    }

    public static void unsubscribe(Context context, String str, String str2) {
        if (c.a(context).b()) {
            if (topicSubscribedTime(context, str) < 0) {
                StringBuilder sb = new StringBuilder("Don't cancel subscribe for ");
                sb.append(str);
                sb.append(" is unsubscribed");
                b.a(sb.toString());
                return;
            }
            as asVar = new as();
            asVar.a(generatePacketID());
            asVar.b(c.a(context).c());
            asVar.c(str);
            asVar.d(context.getPackageName());
            asVar.e(str2);
            aj.a(context).a(asVar, com.xiaomi.xmpush.thrift.a.UnSubscription, (u) null);
        }
    }

    private static void updateIMEI() {
        new Thread(new w()).start();
    }
}
