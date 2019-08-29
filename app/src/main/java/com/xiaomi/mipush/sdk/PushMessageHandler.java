package com.xiaomi.mipush.sdk;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.mipush.sdk.MiPushClient.MiPushClientCallback;
import com.xiaomi.xmpush.thrift.au;
import com.xiaomi.xmpush.thrift.f;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public class PushMessageHandler extends IntentService {
    private static List<MiPushClientCallback> a = new ArrayList();

    interface a extends Serializable {
    }

    public PushMessageHandler() {
        super("mipush message handler");
    }

    protected static void a() {
        synchronized (a) {
            a.clear();
        }
    }

    public static void a(long j, String str, String str2) {
        synchronized (a) {
            for (MiPushClientCallback onInitializeResult : a) {
                onInitializeResult.onInitializeResult(j, str, str2);
            }
        }
    }

    public static void a(Context context, MiPushMessage miPushMessage) {
        synchronized (a) {
            for (MiPushClientCallback next : a) {
                if (a(miPushMessage.getCategory(), next.getCategory())) {
                    next.onReceiveMessage(miPushMessage.getContent(), miPushMessage.getAlias(), miPushMessage.getTopic(), miPushMessage.isNotified());
                    next.onReceiveMessage(miPushMessage);
                }
            }
        }
    }

    public static void a(Context context, a aVar) {
        if (aVar instanceof MiPushMessage) {
            a(context, (MiPushMessage) aVar);
            return;
        }
        if (aVar instanceof MiPushCommandMessage) {
            MiPushCommandMessage miPushCommandMessage = (MiPushCommandMessage) aVar;
            String command = miPushCommandMessage.getCommand();
            String str = null;
            if ("register".equals(command)) {
                List<String> commandArguments = miPushCommandMessage.getCommandArguments();
                if (commandArguments != null && !commandArguments.isEmpty()) {
                    str = commandArguments.get(0);
                }
                a(miPushCommandMessage.getResultCode(), miPushCommandMessage.getReason(), str);
            } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command) || MiPushClient.COMMAND_UNSET_ALIAS.equals(command) || MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
                a(context, miPushCommandMessage.getCategory(), command, miPushCommandMessage.getResultCode(), miPushCommandMessage.getReason(), miPushCommandMessage.getCommandArguments());
            } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
                List<String> commandArguments2 = miPushCommandMessage.getCommandArguments();
                if (commandArguments2 != null && !commandArguments2.isEmpty()) {
                    str = commandArguments2.get(0);
                }
                Context context2 = context;
                a(context2, miPushCommandMessage.getCategory(), miPushCommandMessage.getResultCode(), miPushCommandMessage.getReason(), str);
            } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
                List<String> commandArguments3 = miPushCommandMessage.getCommandArguments();
                if (commandArguments3 != null && !commandArguments3.isEmpty()) {
                    str = commandArguments3.get(0);
                }
                Context context3 = context;
                b(context3, miPushCommandMessage.getCategory(), miPushCommandMessage.getResultCode(), miPushCommandMessage.getReason(), str);
            }
        }
    }

    protected static void a(Context context, String str, long j, String str2, String str3) {
        synchronized (a) {
            for (MiPushClientCallback next : a) {
                if (a(str, next.getCategory())) {
                    next.onSubscribeResult(j, str2, str3);
                }
            }
        }
    }

    protected static void a(Context context, String str, String str2, long j, String str3, List<String> list) {
        synchronized (a) {
            for (MiPushClientCallback next : a) {
                if (a(str, next.getCategory())) {
                    next.onCommandResult(str2, j, str3, list);
                }
            }
        }
    }

    private void a(Intent intent, ResolveInfo resolveInfo) {
        try {
            MessageHandleService.addJob(new com.xiaomi.mipush.sdk.MessageHandleService.a(intent, (PushMessageReceiver) Class.forName(resolveInfo.activityInfo.name).newInstance()));
            MessageHandleService.a(this, new Intent(getApplicationContext(), MessageHandleService.class));
        } catch (Throwable th) {
            b.a(th);
        }
    }

    protected static void a(MiPushClientCallback miPushClientCallback) {
        synchronized (a) {
            if (!a.contains(miPushClientCallback)) {
                a.add(miPushClientCallback);
            }
        }
    }

    protected static boolean a(String str, String str2) {
        return (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) || TextUtils.equals(str, str2);
    }

    protected static void b(Context context, String str, long j, String str2, String str3) {
        synchronized (a) {
            for (MiPushClientCallback next : a) {
                if (a(str, next.getCategory())) {
                    next.onUnsubscribeResult(j, str2, str3);
                }
            }
        }
    }

    public static boolean b() {
        return a.isEmpty();
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        try {
            if ("com.xiaomi.mipush.sdk.WAKEUP".equals(intent.getAction())) {
                if (c.a((Context) this).i()) {
                    aj.a((Context) this).a();
                    String stringExtra = intent.getStringExtra("waker_pkgname");
                    JSONObject jSONObject = new JSONObject();
                    if (TextUtils.isEmpty(stringExtra)) {
                        jSONObject.put("wakerPkgName", stringExtra);
                    }
                    jSONObject.put("selfPkgName", getPackageName());
                    MiTinyDataClient.upload(this, "system_running_log", "wake_up_app", 1, jSONObject.toString());
                }
            } else if ("com.xiaomi.mipush.SEND_TINYDATA".equals(intent.getAction())) {
                f fVar = new f();
                au.a(fVar, intent.getByteArrayExtra("mipush_payload"));
                StringBuilder sb = new StringBuilder("PushMessageHandler.onHandleIntent ");
                sb.append(fVar.m());
                b.c(sb.toString());
                MiTinyDataClient.upload(this, fVar);
            } else if ("com.xiaomi.mipush.sdk.HYBRID_NOTIFICATION_CLICK".equals(intent.getAction())) {
                MiPushMessage miPushMessage = (MiPushMessage) intent.getSerializableExtra("mipush_payload");
                String stringExtra2 = intent.getStringExtra("mipush_hybrid_app_pkg");
                MiPushClient.reportMessageClicked(getApplicationContext(), miPushMessage);
                MiPushClient4Hybrid.onNotificationMessageClicked(getApplicationContext(), stringExtra2, miPushMessage);
            } else if (1 != PushMessageHelper.getPushMode(this)) {
                Intent intent2 = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
                intent2.setPackage(getPackageName());
                intent2.putExtras(intent);
                List<ResolveInfo> queryBroadcastReceivers = getPackageManager().queryBroadcastReceivers(intent2, 32);
                ResolveInfo resolveInfo = null;
                if (queryBroadcastReceivers != null) {
                    Iterator<ResolveInfo> it = queryBroadcastReceivers.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        ResolveInfo next = it.next();
                        if (next.activityInfo != null && next.activityInfo.packageName.equals(getPackageName())) {
                            resolveInfo = next;
                            break;
                        }
                    }
                }
                if (resolveInfo != null) {
                    a(intent2, resolveInfo);
                } else {
                    b.d("cannot find the receiver to handler this message, check your manifest");
                }
            } else if (b()) {
                b.d("receive a message before application calling initialize");
            } else {
                a a2 = ah.a((Context) this).a(intent);
                if (a2 != null) {
                    a((Context) this, a2);
                }
            }
        } catch (Exception e) {
            b.a((Throwable) e);
        } catch (Throwable th) {
            b.a(th);
        }
    }
}
