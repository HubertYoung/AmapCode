package com.huawei.android.pushagent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.huawei.android.pushagent.a.a.d;
import com.huawei.android.pushagent.a.a.e;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class PushReceiver extends BroadcastReceiver {
    private static int a = -1;

    public static class ACTION {
        public static final String ACTION_CLIENT_DEREGISTER = "com.huawei.android.push.intent.DEREGISTER";
        public static final String ACTION_NOTIFICATION_MSG_CLICK = "com.huawei.android.push.intent.CLICK";
        public static final String ACTION_PUSH_MESSAGE = "com.huawei.android.push.intent.RECEIVE";
    }

    public static class BOUND_KEY {
        public static final String deviceTokenKey = "deviceToken";
        public static final String pushMsgKey = "pushMsg";
        public static final String pushNotifyId = "pushNotifyId";
        public static final String pushStateKey = "pushState";
        public static final String receiveTypeKey = "receiveType";
    }

    class EventThread extends Thread {
        Context a;
        Bundle b;

        public EventThread(Context context, Bundle bundle) {
            super("EventRunable");
            this.a = context;
            this.b = bundle;
        }

        public void run() {
            try {
                if (this.b != null) {
                    int i = this.b.getInt(BOUND_KEY.receiveTypeKey);
                    if (i >= 0 && i < ReceiveType.values().length) {
                        switch (ReceiveType.values()[i]) {
                            case ReceiveType_Token:
                                PushReceiver.this.onToken(this.a, this.b.getString(BOUND_KEY.deviceTokenKey), this.b);
                                break;
                            case ReceiveType_Msg:
                                PushReceiver.this.onPushMsg(this.a, this.b.getByteArray(BOUND_KEY.pushMsgKey), this.b.getString(BOUND_KEY.deviceTokenKey));
                                return;
                            case ReceiveType_PushState:
                                PushReceiver.this.onPushState(this.a, this.b.getBoolean(BOUND_KEY.pushStateKey));
                                return;
                            case ReceiveType_NotifyClick:
                                PushReceiver.this.onNotifyClickMsg(this.a, this.b.getString(BOUND_KEY.pushMsgKey));
                                return;
                            case ReceiveType_ClickBtn:
                                PushReceiver.this.onNotifyBtnClick(this.a, this.b.getInt(BOUND_KEY.pushNotifyId), this.b.getString(BOUND_KEY.pushMsgKey), new Bundle());
                                return;
                            case ReceiveType_PluginRsp:
                                PushReceiver.this.onPluginRsp(this.a, this.b.getInt(KEY_TYPE.PLUGINREPORTTYPE, -1), this.b.getBoolean(KEY_TYPE.PLUGINREPORTRESULT, false), this.b.getBundle(KEY_TYPE.PLUGINREPORTEXTRA));
                                return;
                            default:
                                return;
                        }
                    }
                }
            } catch (Exception e) {
                new StringBuilder("call EventThread(ReceiveType cause:").append(e.toString());
            }
        }
    }

    static class HandlePushTokenThread extends Thread {
        Context a;
        String b;

        public HandlePushTokenThread(Context context, String str) {
            this.a = context;
            this.b = str;
        }

        public void run() {
            e eVar = new e(this.a, "push_client_self_info");
            eVar.a((String) "hasRequestToken", false);
            eVar.d("token_info");
            d.a(this.a, "push_client_self_info", "token_info", this.b);
        }
    }

    public static class KEY_TYPE {
        public static final String PKGNAME = "pkg_name";
        public static final String PLUGINREPORTEXTRA = "reportExtra";
        public static final String PLUGINREPORTRESULT = "isReportSuccess";
        public static final String PLUGINREPORTTYPE = "reportType";
        public static final String PUSHSTATE = "push_state";
        public static final String PUSH_BROADCAST_MESSAGE = "msg_data";
        public static final String PUSH_KEY_CLICK = "click";
        public static final String PUSH_KEY_CLICK_BTN = "clickBtn";
        public static final String PUSH_KEY_DEVICE_TOKEN = "device_token";
        public static final String PUSH_KEY_NOTIFY_ID = "notifyId";
        public static final String USERID = "userid";
    }

    enum ReceiveType {
        ReceiveType_Init,
        ReceiveType_Token,
        ReceiveType_Msg,
        ReceiveType_PushState,
        ReceiveType_NotifyClick,
        ReceiveType_PluginRsp,
        ReceiveType_ClickBtn
    }

    public static class SERVER {
        public static final String DEVICETOKEN = "device_token";
    }

    private static int a() {
        try {
            Class<?> cls = Class.forName("android.os.UserHandle");
            return ((Integer) cls.getDeclaredMethod("myUserId", new Class[0]).invoke(cls, new Object[0])).intValue();
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException unused) {
            return -999;
        }
    }

    private void a(Context context, Intent intent) {
        StringBuilder sb;
        String message;
        if (!new e(context, "push_switch").a("notify_msg_enable")) {
            try {
                Class<?> cls = Class.forName("com.huawei.android.pushselfshow.SelfShowReceiver");
                Object newInstance = cls.getConstructor(new Class[0]).newInstance(new Object[0]);
                cls.getDeclaredMethod("onReceive", new Class[]{Context.class, Intent.class}).invoke(newInstance, new Object[]{context, intent});
            } catch (ClassNotFoundException e) {
                sb = new StringBuilder("SelfShowReceiver class not found:");
                message = e.getMessage();
                sb.append(message);
            } catch (NoSuchMethodException e2) {
                sb = new StringBuilder("onReceive method not found:");
                message = e2.getMessage();
                sb.append(message);
            } catch (Exception e3) {
                sb = new StringBuilder("invokeSelfShow error:");
                message = e3.getMessage();
                sb.append(message);
            }
        }
    }

    private static boolean a(Context context, boolean z) {
        StringBuilder sb = new StringBuilder("existFrameworkPush:");
        sb.append(a);
        sb.append(",realCheck:");
        sb.append(z);
        if (!z) {
            return 1 == a;
        }
        try {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("/system/framework/");
            sb2.append("hwpush.jar");
            if (!new File(sb2.toString()).isFile()) {
                if (!SystemProperties.getBoolean("ro.config.push_enable", "CN".equals(SystemProperties.get("ro.product.locale.region")))) {
                    return false;
                }
                String str = SystemProperties.get("ro.build.version.emui", "-1");
                if (!TextUtils.isEmpty(str)) {
                    if (!str.contains("2.0")) {
                        if (str.contains("2.3")) {
                        }
                    }
                }
                return false;
            }
            List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(new Intent().setClassName("android", "com.huawei.android.pushagentproxy.PushService"), 128);
            return (queryIntentServices == null || queryIntentServices.size() == 0) ? false : true;
        } catch (Exception e) {
            new StringBuilder("get Apk version faild ,Exception e= ").append(e.toString());
            return false;
        }
    }

    private void b(Context context, Intent intent) {
        if (context != null && intent != null) {
            String stringExtra = intent.getStringExtra("msgIdStr");
            if (!TextUtils.isEmpty(stringExtra) && isFrameworkPushExist(context)) {
                Intent intent2 = new Intent("com.huawei.android.push.intent.MSG_RESPONSE");
                intent2.putExtra("msgIdStr", stringExtra);
                intent2.setPackage("android");
                intent2.setFlags(32);
                context.sendBroadcast(intent2);
            }
        }
    }

    public static final void enableReceiveNormalMsg(Context context, boolean z) {
        if (context != null) {
            new e(context, "push_switch").a((String) "normal_msg_enable", !z);
        }
    }

    public static final void enableReceiveNotifyMsg(Context context, boolean z) {
        if (context != null) {
            new e(context, "push_switch").a((String) "notify_msg_enable", !z);
        }
    }

    public static void getPushState(Context context) {
        new StringBuilder("enter PushEntity:getPushState() pkgName").append(context.getPackageName());
        Intent intent = new Intent("com.huawei.android.push.intent.GET_PUSH_STATE");
        intent.putExtra(KEY_TYPE.PKGNAME, context.getPackageName());
        intent.setFlags(32);
        if (isFrameworkPushExist(context)) {
            intent.setPackage("android");
        }
        context.sendOrderedBroadcast(intent, null);
    }

    public static final void getToken(Context context) {
        new StringBuilder("enter PushEntity:getToken() pkgName").append(context.getPackageName());
        Intent intent = new Intent("com.huawei.android.push.intent.REGISTER");
        intent.putExtra(KEY_TYPE.PKGNAME, context.getPackageName());
        int a2 = a();
        if (-999 != a2) {
            intent.putExtra("userid", String.valueOf(a2));
        }
        intent.setFlags(32);
        if (isFrameworkPushExist(context)) {
            intent.setPackage("android");
        }
        context.sendBroadcast(intent);
        new e(context, "push_client_self_info").a((String) "hasRequestToken", true);
    }

    public static synchronized boolean isFrameworkPushExist(Context context) {
        synchronized (PushReceiver.class) {
            new StringBuilder("existFrameworkPush:").append(a);
            if (-1 != a) {
                return 1 == a;
            }
            if (a(context, true)) {
                a = 1;
            } else {
                a = 0;
            }
            return 1 == a;
        }
    }

    public boolean canExit() {
        return true;
    }

    public void onNotifyBtnClick(Context context, int i, String str, Bundle bundle) {
    }

    public void onNotifyClickMsg(Context context, String str) {
    }

    public void onPluginRsp(Context context, int i, boolean z, Bundle bundle) {
    }

    public abstract void onPushMsg(Context context, byte[] bArr, String str);

    public void onPushState(Context context, boolean z) {
    }

    public final void onReceive(Context context, Intent intent) {
        StringBuilder sb;
        String unsupportedEncodingException;
        try {
            Bundle bundle = new Bundle();
            StringBuilder sb2 = new StringBuilder("enter PushMsgReceiver:onReceive(Intent:");
            sb2.append(intent.getAction());
            sb2.append(" pkgName:");
            sb2.append(context.getPackageName());
            sb2.append(")");
            String action = intent.getAction();
            if ("com.huawei.android.push.intent.REGISTRATION".equals(action) && intent.hasExtra("device_token")) {
                String str = new String(intent.getByteArrayExtra("device_token"), "UTF-8");
                if (!TextUtils.isEmpty(str)) {
                    boolean a2 = new e(context, "push_client_self_info").a("hasRequestToken");
                    String a3 = d.a(context, "push_client_self_info", "token_info");
                    if (a2 || !str.equals(a3)) {
                        new HandlePushTokenThread(context, str).start();
                        bundle.putString(BOUND_KEY.deviceTokenKey, str);
                        bundle.putByteArray(BOUND_KEY.pushMsgKey, null);
                        bundle.putInt(BOUND_KEY.receiveTypeKey, ReceiveType.ReceiveType_Token.ordinal());
                        if (intent.getExtras() != null) {
                            bundle.putAll(intent.getExtras());
                        }
                        new EventThread(context, bundle).start();
                    }
                }
            } else if (ACTION.ACTION_PUSH_MESSAGE.equals(action) && intent.hasExtra(KEY_TYPE.PUSH_BROADCAST_MESSAGE)) {
                b(context, intent);
                if (!new e(context, "push_switch").a("normal_msg_enable")) {
                    byte[] byteArrayExtra = intent.getByteArrayExtra(KEY_TYPE.PUSH_BROADCAST_MESSAGE);
                    bundle.putString(BOUND_KEY.deviceTokenKey, new String(intent.getByteArrayExtra("device_token"), "UTF-8"));
                    bundle.putByteArray(BOUND_KEY.pushMsgKey, byteArrayExtra);
                    bundle.putInt(BOUND_KEY.receiveTypeKey, ReceiveType.ReceiveType_Msg.ordinal());
                    new EventThread(context, bundle).start();
                }
            } else if (ACTION.ACTION_NOTIFICATION_MSG_CLICK.equals(action) && intent.hasExtra("click")) {
                bundle.putString(BOUND_KEY.pushMsgKey, intent.getStringExtra("click"));
                bundle.putInt(BOUND_KEY.receiveTypeKey, ReceiveType.ReceiveType_NotifyClick.ordinal());
                new EventThread(context, bundle).start();
            } else if (ACTION.ACTION_NOTIFICATION_MSG_CLICK.equals(action) && intent.hasExtra(KEY_TYPE.PUSH_KEY_CLICK_BTN)) {
                String stringExtra = intent.getStringExtra(KEY_TYPE.PUSH_KEY_CLICK_BTN);
                int intExtra = intent.getIntExtra(KEY_TYPE.PUSH_KEY_NOTIFY_ID, 0);
                bundle.putString(BOUND_KEY.pushMsgKey, stringExtra);
                bundle.putInt(BOUND_KEY.pushNotifyId, intExtra);
                bundle.putInt(BOUND_KEY.receiveTypeKey, ReceiveType.ReceiveType_ClickBtn.ordinal());
                new EventThread(context, bundle).start();
            } else if ("com.huawei.intent.action.PUSH_STATE".equals(action)) {
                bundle.putBoolean(BOUND_KEY.pushStateKey, intent.getBooleanExtra(KEY_TYPE.PUSHSTATE, false));
                bundle.putInt(BOUND_KEY.receiveTypeKey, ReceiveType.ReceiveType_PushState.ordinal());
                new EventThread(context, bundle).start();
            } else {
                if ("com.huawei.intent.action.PUSH".equals(action) && intent.hasExtra("selfshow_info")) {
                    a(context, intent);
                }
            }
        } catch (UnsupportedEncodingException e) {
            sb = new StringBuilder("call onReceive(intent:");
            sb.append(intent);
            sb.append(") cause:");
            unsupportedEncodingException = e.toString();
            sb.append(unsupportedEncodingException);
        } catch (Exception e2) {
            sb = new StringBuilder("call onReceive(intent:");
            sb.append(intent);
            sb.append(") cause:");
            unsupportedEncodingException = e2.toString();
            sb.append(unsupportedEncodingException);
        }
    }

    public abstract void onToken(Context context, String str);

    public void onToken(Context context, String str, Bundle bundle) {
        onToken(context, str);
    }
}
