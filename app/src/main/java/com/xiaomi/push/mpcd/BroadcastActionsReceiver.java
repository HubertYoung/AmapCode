package com.xiaomi.push.mpcd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.misc.f;
import com.xiaomi.push.mpcd.job.e;
import com.xiaomi.push.service.an;
import com.xiaomi.xmpush.thrift.d;
import com.xiaomi.xmpush.thrift.g;
import com.xiaomi.xmpush.thrift.k;

public class BroadcastActionsReceiver extends BroadcastReceiver {
    public static final String a = String.valueOf(d.BroadcastActionRestarted.a());
    public static final String b = String.valueOf(d.BroadcastActionChanged.a());

    private void a(Context context, String str, String str2) {
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
            try {
                if (f.a(context, "12", 1)) {
                    k kVar = new k();
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(":");
                    sb.append(str2);
                    kVar.a(sb.toString());
                    kVar.a(System.currentTimeMillis());
                    kVar.a(d.BroadcastAction);
                    com.xiaomi.push.mpcd.job.f.a(context, kVar);
                }
            } catch (Throwable unused) {
            }
        }
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            try {
                String dataString = intent.getDataString();
                if (!TextUtils.isEmpty(dataString)) {
                    String[] split = dataString.split(":");
                    if (split.length >= 2 && !TextUtils.isEmpty(split[1])) {
                        String str = split[1];
                        long currentTimeMillis = System.currentTimeMillis();
                        boolean a2 = an.a(context).a(g.BroadcastActionCollectionSwitch.a(), true);
                        if (TextUtils.equals("android.intent.action.PACKAGE_RESTARTED", intent.getAction())) {
                            if (f.a(context, "12", 1) && a2) {
                                if (TextUtils.isEmpty(e.a)) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(e.a);
                                    sb.append(a);
                                    sb.append(":");
                                    e.a = sb.toString();
                                }
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(e.a);
                                sb2.append(str);
                                sb2.append("(");
                                sb2.append(currentTimeMillis);
                                sb2.append("),");
                                e.a = sb2.toString();
                            }
                        } else if (!TextUtils.equals("android.intent.action.PACKAGE_CHANGED", intent.getAction())) {
                            if (TextUtils.equals("android.intent.action.PACKAGE_ADDED", intent.getAction())) {
                                if (!intent.getExtras().getBoolean("android.intent.extra.REPLACING") && a2) {
                                    a(context, String.valueOf(d.BroadcastActionAdded.a()), str);
                                }
                            } else if (TextUtils.equals("android.intent.action.PACKAGE_REMOVED", intent.getAction())) {
                                if (!intent.getExtras().getBoolean("android.intent.extra.REPLACING") && a2) {
                                    a(context, String.valueOf(d.BroadcastActionRemoved.a()), str);
                                }
                            } else if (TextUtils.equals("android.intent.action.PACKAGE_REPLACED", intent.getAction())) {
                                if (a2) {
                                    a(context, String.valueOf(d.BroadcastActionReplaced.a()), str);
                                }
                            } else if (TextUtils.equals("android.intent.action.PACKAGE_DATA_CLEARED", intent.getAction()) && a2) {
                                a(context, String.valueOf(d.BroadcastActionDataCleared.a()), str);
                            }
                        } else if (f.a(context, "12", 1) && a2) {
                            if (TextUtils.isEmpty(e.b)) {
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(e.b);
                                sb3.append(b);
                                sb3.append(":");
                                e.b = sb3.toString();
                            }
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(e.b);
                            sb4.append(str);
                            sb4.append("(");
                            sb4.append(currentTimeMillis);
                            sb4.append("),");
                            e.b = sb4.toString();
                        }
                    }
                }
            } catch (Throwable unused) {
            }
        }
    }
}
