package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.aw;
import com.xiaomi.push.service.h;
import com.xiaomi.push.service.j;
import com.xiaomi.push.service.k;
import com.xiaomi.xmpush.thrift.a;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.au;
import com.xiaomi.xmpush.thrift.m;
import com.xiaomi.xmpush.thrift.r;
import com.xiaomi.xmpush.thrift.u;
import com.xiaomi.xmpush.thrift.v;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.thrift.f;

public class p {
    private static volatile p a;
    private final String b = "GeoFenceRegMessageProcessor.";
    private Context c;

    private p(Context context) {
        this.c = context;
    }

    public static p a(Context context) {
        if (a == null) {
            synchronized (p.class) {
                try {
                    if (a == null) {
                        a = new p(context);
                    }
                }
            }
        }
        return a;
    }

    private m a(ai aiVar, boolean z) {
        if (z && !k.a(this.c)) {
            return null;
        }
        if (z && !k.c(this.c)) {
            return null;
        }
        try {
            m mVar = new m();
            au.a(mVar, aiVar.m());
            return mVar;
        } catch (f e) {
            e.printStackTrace();
            return null;
        }
    }

    private v a(boolean z) {
        v vVar = new v();
        TreeSet treeSet = new TreeSet();
        if (z) {
            Iterator<m> it = h.a(this.c).a().iterator();
            while (it.hasNext()) {
                treeSet.add(it.next());
            }
        }
        vVar.a((Set<m>) treeSet);
        return vVar;
    }

    public static void a(Context context, boolean z) {
        ai aiVar = new ai(MiPushClient.generatePacketID(), false);
        aiVar.b(c.a(context).c());
        aiVar.c(r.GeoAuthorized.W);
        aiVar.h = new HashMap();
        aiVar.h.put("permission_to_location", String.valueOf(z));
        aj.a(context).a(aiVar, a.Notification, false, (u) null);
    }

    private void a(m mVar) {
        byte[] a2 = au.a(mVar);
        ai aiVar = new ai(MiPushClient.generatePacketID(), false);
        aiVar.c(r.GeoPackageUninstalled.W);
        aiVar.a(a2);
        aj.a(this.c).a(aiVar, a.Notification, true, (u) null);
        StringBuilder sb = new StringBuilder("GeoFenceRegMessageProcessor. report package not exist geo_fencing id:");
        sb.append(mVar.a());
        b.c(sb.toString());
    }

    private void a(m mVar, boolean z, boolean z2) {
        byte[] a2 = au.a(mVar);
        ai aiVar = new ai(MiPushClient.generatePacketID(), false);
        aiVar.c((z ? r.GeoRegsiterResult : r.GeoUnregsiterResult).W);
        aiVar.a(a2);
        if (z2) {
            aiVar.a("permission_to_location", aw.b);
        }
        aj.a(this.c).a(aiVar, a.Notification, true, (u) null);
        StringBuilder sb = new StringBuilder("GeoFenceRegMessageProcessor. report geo_fencing id:");
        sb.append(mVar.a());
        sb.append(Token.SEPARATOR);
        sb.append(z ? "geo_reg" : "geo_unreg");
        sb.append("  isUnauthorized:");
        sb.append(z2);
        b.c(sb.toString());
    }

    public static boolean a(Map<String, String> map) {
        if (map == null) {
            return false;
        }
        return TextUtils.equals("1", map.get("__geo_local_cache"));
    }

    private boolean d(ai aiVar) {
        return a(aiVar.i()) && k.d(this.c);
    }

    public void a(ai aiVar) {
        String str;
        boolean d = d(aiVar);
        m a2 = a(aiVar, d);
        if (a2 == null) {
            StringBuilder sb = new StringBuilder("GeoFenceRegMessageProcessor. registration convert geofence object failed notification_id:");
            sb.append(aiVar.c());
            str = sb.toString();
        } else if (!k.e(this.c)) {
            a(a2, true, true);
            return;
        } else if (!com.xiaomi.channel.commonutils.android.a.e(this.c, a2.g())) {
            if (d) {
                a(a2);
            }
            return;
        } else if (!d) {
            a(a2, true, false);
            return;
        } else {
            if (h.a(this.c).a(a2) == -1) {
                StringBuilder sb2 = new StringBuilder("GeoFenceRegMessageProcessor. insert a new geofence failed about geo_id:");
                sb2.append(a2.a());
                b.a(sb2.toString());
            }
            new q(this.c).a(a2);
            a(a2, true, false);
            str = "GeoFenceRegMessageProcessor. receive geo reg notification";
        }
        b.c(str);
    }

    public void b(ai aiVar) {
        boolean d = d(aiVar);
        m a2 = a(aiVar, d);
        if (a2 == null) {
            StringBuilder sb = new StringBuilder("GeoFenceRegMessageProcessor. unregistration convert geofence object failed notification_id:");
            sb.append(aiVar.c());
            b.c(sb.toString());
        } else if (!k.e(this.c)) {
            a(a2, false, true);
        } else if (!com.xiaomi.channel.commonutils.android.a.e(this.c, a2.g())) {
            if (d) {
                a(a2);
            }
        } else if (!d) {
            a(a2, false, false);
        } else {
            if (h.a(this.c).d(a2.a()) == 0) {
                StringBuilder sb2 = new StringBuilder("GeoFenceRegMessageProcessor. delete a geofence about geo_id:");
                sb2.append(a2.a());
                sb2.append(" falied");
                b.a(sb2.toString());
            }
            if (j.a(this.c).b(a2.a()) == 0) {
                StringBuilder sb3 = new StringBuilder("GeoFenceRegMessageProcessor. delete all geofence messages about geo_id:");
                sb3.append(a2.a());
                sb3.append(" failed");
                b.a(sb3.toString());
            }
            new q(this.c).a(a2.a());
            a(a2, false, false);
            b.c("GeoFenceRegMessageProcessor. receive geo unreg notification");
        }
    }

    public void c(ai aiVar) {
        if (k.e(this.c)) {
            boolean d = d(aiVar);
            if (d && !k.a(this.c)) {
                return;
            }
            if ((!d || k.c(this.c)) && com.xiaomi.channel.commonutils.android.a.e(this.c, aiVar.i)) {
                v a2 = a(d);
                byte[] a3 = au.a(a2);
                ai aiVar2 = new ai("-1", false);
                aiVar2.c(r.GeoUpload.W);
                aiVar2.a(a3);
                aj.a(this.c).a(aiVar2, a.Notification, true, (u) null);
                StringBuilder sb = new StringBuilder("GeoFenceRegMessageProcessor. sync_geo_data. geos size:");
                sb.append(a2.a().size());
                b.c(sb.toString());
            }
        }
    }
}
