package com.xiaomi.push.service;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.amap.bundle.drive.ajx.module.ModuleHeadunitImpl;
import com.xiaomi.channel.commonutils.android.e;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.XMPushService.h;
import com.xiaomi.smack.l;
import com.xiaomi.smack.packet.c;
import com.xiaomi.smack.packet.d;
import com.xiaomi.smack.util.g;
import com.xiaomi.xmpush.thrift.a;
import com.xiaomi.xmpush.thrift.af;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.au;
import com.xiaomi.xmpush.thrift.r;
import com.xiaomi.xmpush.thrift.u;
import com.xiaomi.xmpush.thrift.z;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;

public class x {
    public static Intent a(byte[] bArr, long j) {
        af a = a(bArr);
        if (a == null) {
            return null;
        }
        Intent intent = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
        intent.putExtra("mipush_payload", bArr);
        intent.putExtra("mrt", Long.toString(j));
        intent.setPackage(a.f);
        return intent;
    }

    public static af a(Context context, af afVar) {
        return a(context, afVar, false, false, false);
    }

    public static af a(Context context, af afVar, boolean z, boolean z2, boolean z3) {
        z zVar = new z();
        zVar.b(afVar.h());
        u m = afVar.m();
        if (m != null) {
            zVar.a(m.b());
            zVar.a(m.d());
            if (!TextUtils.isEmpty(m.f())) {
                zVar.c(m.f());
            }
        }
        zVar.a(au.a(context, afVar));
        zVar.b(au.a(z, z2, z3));
        af a = af.a(afVar.j(), afVar.h(), zVar, a.AckMessage);
        u a2 = afVar.m().a();
        a2.a("mat", Long.toString(System.currentTimeMillis()));
        a.a(a2);
        return a;
    }

    public static af a(byte[] bArr) {
        af afVar = new af();
        try {
            au.a(afVar, bArr);
            return afVar;
        } catch (Throwable th) {
            b.a(th);
            return null;
        }
    }

    private static void a(XMPushService xMPushService, af afVar) {
        xMPushService.a((h) new y(4, xMPushService, afVar));
    }

    private static void a(XMPushService xMPushService, af afVar, String str) {
        xMPushService.a((h) new ac(4, xMPushService, afVar, str));
    }

    private static void a(XMPushService xMPushService, af afVar, String str, String str2) {
        ad adVar = new ad(4, xMPushService, afVar, str, str2);
        xMPushService.a((h) adVar);
    }

    public static void a(XMPushService xMPushService, af afVar, boolean z, boolean z2, boolean z3) {
        a(xMPushService, afVar, z, z2, z3, false);
    }

    public static void a(XMPushService xMPushService, af afVar, boolean z, boolean z2, boolean z3, boolean z4) {
        ae aeVar = new ae(4, xMPushService, afVar, z, z2, z3, z4);
        xMPushService.a((h) aeVar);
    }

    public static void a(XMPushService xMPushService, String str, byte[] bArr, Intent intent, boolean z) {
        boolean z2;
        XMPushService xMPushService2 = xMPushService;
        byte[] bArr2 = bArr;
        Intent intent2 = intent;
        af a = a(bArr2);
        u m = a.m();
        if (c(a) && a((Context) xMPushService2, str)) {
            d(xMPushService2, a);
        } else if (!a(a) || a((Context) xMPushService2, str) || b(a)) {
            if ((ah.b(a) && com.xiaomi.channel.commonutils.android.a.e(xMPushService2, a.f)) || a((Context) xMPushService2, intent2)) {
                if (a.Registration == a.a()) {
                    String j = a.j();
                    Editor edit = xMPushService2.getSharedPreferences("pref_registered_pkg_names", 0).edit();
                    edit.putString(j, a.e);
                    edit.commit();
                }
                if (m != null && !TextUtils.isEmpty(m.h()) && !TextUtils.isEmpty(m.j()) && m.h != 1 && (ah.a(m.s()) || !ah.a((Context) xMPushService2, a.f))) {
                    String str2 = null;
                    if (m != null) {
                        if (m.j != null) {
                            str2 = m.j.get("jobkey");
                        }
                        if (TextUtils.isEmpty(str2)) {
                            str2 = m.b();
                        }
                        z2 = aj.a(xMPushService2, a.f, str2);
                    } else {
                        z2 = false;
                    }
                    if (z2) {
                        b.a("drop a duplicate message, key=".concat(String.valueOf(str2)));
                    } else {
                        ah.b a2 = ah.a((Context) xMPushService2, a, bArr2);
                        if (a2.b > 0 && !TextUtils.isEmpty(a2.a)) {
                            g.a(xMPushService2, a2.a, a2.b, true, System.currentTimeMillis());
                        }
                        if (!ah.b(a)) {
                            Intent intent3 = new Intent("com.xiaomi.mipush.MESSAGE_ARRIVED");
                            intent3.putExtra("mipush_payload", bArr2);
                            intent3.setPackage(a.f);
                            try {
                                List<ResolveInfo> queryBroadcastReceivers = xMPushService2.getPackageManager().queryBroadcastReceivers(intent3, 0);
                                if (queryBroadcastReceivers != null && !queryBroadcastReceivers.isEmpty()) {
                                    xMPushService2.sendBroadcast(intent3, e.a(a.f));
                                }
                            } catch (Exception unused) {
                                xMPushService2.sendBroadcast(intent3, e.a(a.f));
                            }
                        }
                    }
                    if (z) {
                        a(xMPushService2, a, false, true, false);
                    } else {
                        c(xMPushService2, a);
                    }
                } else if ("com.xiaomi.xmsf".contains(a.f) && !a.c() && m != null && m.s() != null && m.s().containsKey("ab")) {
                    c(xMPushService2, a);
                    StringBuilder sb = new StringBuilder("receive abtest message. ack it.");
                    sb.append(m.b());
                    b.c(sb.toString());
                } else if (a(xMPushService2, str, a, m)) {
                    xMPushService2.sendBroadcast(intent2, e.a(a.f));
                }
                if (a.a() == a.UnRegistration && !"com.xiaomi.xmsf".equals(xMPushService2.getPackageName())) {
                    xMPushService2.stopSelf();
                }
            } else if (!com.xiaomi.channel.commonutils.android.a.e(xMPushService2, a.f)) {
                a(xMPushService2, a);
            } else {
                b.a((String) "receive a mipush message, we can see the app, but we can't see the receiver.");
            }
        } else {
            e(xMPushService2, a);
        }
    }

    private static void a(XMPushService xMPushService, byte[] bArr, long j) {
        boolean z;
        af a = a(bArr);
        if (a != null) {
            if (TextUtils.isEmpty(a.f)) {
                b.a((String) "receive a mipush message without package name");
                return;
            }
            Long valueOf = Long.valueOf(System.currentTimeMillis());
            Intent a2 = a(bArr, valueOf.longValue());
            String a3 = ah.a(a);
            g.a(xMPushService, a3, j, true, System.currentTimeMillis());
            u m = a.m();
            if (m != null) {
                m.a("mrt", Long.toString(valueOf.longValue()));
            }
            if (a.SendMessage == a.a() && u.a((Context) xMPushService).a(a.f) && !ah.b(a)) {
                String str = "";
                if (m != null) {
                    str = m.b();
                }
                b.a("Drop a message for unregistered, msgid=".concat(String.valueOf(str)));
                a(xMPushService, a, a.f);
            } else if (a.SendMessage == a.a() && u.a((Context) xMPushService).c(a.f) && !ah.b(a)) {
                String str2 = "";
                if (m != null) {
                    str2 = m.b();
                }
                b.a("Drop a message for push closed, msgid=".concat(String.valueOf(str2)));
                a(xMPushService, a, a.f);
            } else if (a.SendMessage != a.a() || TextUtils.equals(xMPushService.getPackageName(), "com.xiaomi.xmsf") || TextUtils.equals(xMPushService.getPackageName(), a.f)) {
                if (!(m == null || m.b() == null)) {
                    b.a(String.format("receive a message, appid=%1$s, msgid= %2$s", new Object[]{a.h(), m.b()}));
                }
                if (m != null) {
                    Map<String, String> s = m.s();
                    if (s != null && s.containsKey(ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_HIDE) && "true".equalsIgnoreCase(s.get(ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_HIDE))) {
                        c(xMPushService, a);
                        return;
                    }
                }
                if (!(m == null || m.s() == null || !m.s().containsKey("__miid"))) {
                    String str3 = m.s().get("__miid");
                    Account a4 = e.a(xMPushService);
                    if (a4 == null || !TextUtils.equals(str3, a4.name)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str3);
                        sb.append(" should be login, but got ");
                        sb.append(a4);
                        b.a(sb.toString() == null ? "nothing" : a4.name);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str3);
                        sb2.append(" should be login, but got ");
                        sb2.append(a4);
                        a(xMPushService, a, (String) "miid already logout or anther already login", sb2.toString() == null ? "nothing" : a4.name);
                        return;
                    }
                }
                boolean z2 = m != null && a(m.s());
                if (z2) {
                    if (!k.e(xMPushService)) {
                        a(xMPushService, a, false, false, false, true);
                        return;
                    }
                    if (!(a(m) && k.d(xMPushService))) {
                        z = true;
                    } else if (b(xMPushService, a)) {
                        z = a(xMPushService, m, bArr);
                    } else {
                        return;
                    }
                    a(xMPushService, a, true, false, false);
                    if (!z) {
                        return;
                    }
                }
                a(xMPushService, a3, bArr, a2, z2);
            } else {
                StringBuilder sb3 = new StringBuilder("Receive a message with wrong package name, expect ");
                sb3.append(xMPushService.getPackageName());
                sb3.append(", received ");
                sb3.append(a.f);
                b.a(sb3.toString());
                StringBuilder sb4 = new StringBuilder("package should be ");
                sb4.append(xMPushService.getPackageName());
                sb4.append(", but got ");
                sb4.append(a.f);
                a(xMPushService, a, (String) "unmatched_package", sb4.toString());
            }
        }
    }

    private static boolean a(Context context, Intent intent) {
        try {
            List<ResolveInfo> queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 32);
            return queryBroadcastReceivers != null && !queryBroadcastReceivers.isEmpty();
        } catch (Exception unused) {
            return true;
        }
    }

    private static boolean a(Context context, String str) {
        Intent intent = new Intent("com.xiaomi.mipush.miui.CLICK_MESSAGE");
        intent.setPackage(str);
        Intent intent2 = new Intent("com.xiaomi.mipush.miui.RECEIVE_MESSAGE");
        intent2.setPackage(str);
        PackageManager packageManager = context.getPackageManager();
        try {
            return !packageManager.queryBroadcastReceivers(intent2, 32).isEmpty() || !packageManager.queryIntentServices(intent, 32).isEmpty();
        } catch (Exception e) {
            b.a((Throwable) e);
            return false;
        }
    }

    private static boolean a(XMPushService xMPushService, u uVar, byte[] bArr) {
        Map<String, String> s = uVar.s();
        String[] split = s.get("__geo_ids").split(",");
        ArrayList arrayList = new ArrayList();
        for (String str : split) {
            if (h.a((Context) xMPushService).a(str) != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("geo_id", str);
                contentValues.put("message_id", uVar.b());
                int parseInt = Integer.parseInt(s.get("__geo_action"));
                contentValues.put("action", Integer.valueOf(parseInt));
                contentValues.put("content", bArr);
                contentValues.put("deadline", Long.valueOf(Long.parseLong(s.get("__geo_deadline"))));
                if (TextUtils.equals(h.a((Context) xMPushService).c(str), "Enter") && parseInt == 1) {
                    return true;
                }
                arrayList.add(contentValues);
            }
        }
        if (!j.a((Context) xMPushService).a(arrayList)) {
            StringBuilder sb = new StringBuilder("geofence added some new geofence message failed messagi_id:");
            sb.append(uVar.b());
            b.c(sb.toString());
        }
        return false;
    }

    private static boolean a(XMPushService xMPushService, String str, af afVar, u uVar) {
        boolean z = true;
        if (uVar != null && uVar.s() != null && uVar.s().containsKey("__check_alive") && uVar.s().containsKey("__awake")) {
            ai aiVar = new ai();
            aiVar.b(afVar.h());
            aiVar.d(str);
            aiVar.c(r.AwakeSystemApp.W);
            aiVar.a(uVar.b());
            aiVar.h = new HashMap();
            boolean d = com.xiaomi.channel.commonutils.android.a.d(xMPushService.getApplicationContext(), str);
            aiVar.h.put("app_running", Boolean.toString(d));
            if (!d) {
                boolean parseBoolean = Boolean.parseBoolean(uVar.s().get("__awake"));
                aiVar.h.put("awaked", Boolean.toString(parseBoolean));
                if (!parseBoolean) {
                    z = false;
                }
            }
            try {
                af.a(xMPushService, af.a(afVar.j(), afVar.h(), aiVar, a.Notification));
                return z;
            } catch (l e) {
                b.a((Throwable) e);
            }
        }
        return z;
    }

    private static boolean a(af afVar) {
        return "com.xiaomi.xmsf".equals(afVar.f) && afVar.m() != null && afVar.m().s() != null && afVar.m().s().containsKey("miui_package_name");
    }

    public static boolean a(u uVar) {
        if (uVar == null) {
            return false;
        }
        Map<String, String> s = uVar.s();
        if (s == null) {
            return false;
        }
        return TextUtils.equals("1", s.get("__geo_local_check"));
    }

    private static boolean a(Map<String, String> map) {
        return map != null && map.containsKey("__geo_ids");
    }

    private static boolean b(XMPushService xMPushService, af afVar) {
        if (!k.a(xMPushService) || !k.c(xMPushService)) {
            return false;
        }
        if (!com.xiaomi.channel.commonutils.android.a.e(xMPushService, afVar.f)) {
            a(xMPushService, afVar);
            return false;
        }
        Map<String, String> s = afVar.m().s();
        return s != null && "12".contains(s.get("__geo_action")) && !TextUtils.isEmpty(s.get("__geo_ids"));
    }

    private static boolean b(af afVar) {
        Map<String, String> s = afVar.m().s();
        return s != null && s.containsKey("notify_effect");
    }

    private static void c(XMPushService xMPushService, af afVar) {
        xMPushService.a((h) new z(4, xMPushService, afVar));
    }

    private static boolean c(af afVar) {
        if (afVar.m() == null || afVar.m().s() == null) {
            return false;
        }
        return "1".equals(afVar.m().s().get("obslete_ads_message"));
    }

    private static void d(XMPushService xMPushService, af afVar) {
        xMPushService.a((h) new aa(4, xMPushService, afVar));
    }

    private static void e(XMPushService xMPushService, af afVar) {
        xMPushService.a((h) new ab(4, xMPushService, afVar));
    }

    public void a(Context context, aq.b bVar, boolean z, int i, String str) {
        if (!z) {
            s a = t.a(context);
            if (a != null && "token-expired".equals(str)) {
                try {
                    t.a(context, a.d, a.e, a.f);
                } catch (IOException e) {
                    b.a((Throwable) e);
                } catch (JSONException e2) {
                    b.a((Throwable) e2);
                }
            }
        }
    }

    public void a(XMPushService xMPushService, com.xiaomi.slim.b bVar, aq.b bVar2) {
        try {
            a(xMPushService, bVar.d(bVar2.i), (long) bVar.l());
        } catch (IllegalArgumentException e) {
            b.a((Throwable) e);
        }
    }

    public void a(XMPushService xMPushService, d dVar, aq.b bVar) {
        if (dVar instanceof c) {
            c cVar = (c) dVar;
            com.xiaomi.smack.packet.a p = cVar.p("s");
            if (p != null) {
                try {
                    a(xMPushService, ax.a(ax.a(bVar.i, cVar.k()), p.c()), (long) g.a(dVar.c()));
                    return;
                } catch (IllegalArgumentException e) {
                    b.a((Throwable) e);
                }
            }
            return;
        }
        b.a((String) "not a mipush message");
    }
}
