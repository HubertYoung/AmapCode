package defpackage;

import android.content.Context;
import anet.channel.entity.ENV;
import anetwork.channel.http.NetworkSdkSetting;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.amap.bundle.logs.AMapLog;
import com.ut.device.UTDevice;
import java.util.ArrayList;

/* renamed from: yv reason: default package */
/* compiled from: NetworkSdkInitializer */
public final class yv {
    static volatile boolean a;
    private static boolean b;

    public static synchronized void a(Context context) {
        synchronized (yv.class) {
            if (!b) {
                b = true;
                Context applicationContext = context.getApplicationContext();
                dt.a(applicationContext);
                j.a(yr.a());
                ArrayList arrayList = new ArrayList();
                for (String add : yr.b()) {
                    arrayList.add(add);
                }
                cb.a = (String[]) arrayList.toArray(new String[0]);
                b c = aaf.c();
                if (c == null) {
                    throw new IllegalStateException("you must init network context first!");
                }
                String a2 = c.a();
                String b2 = c.b();
                if (b2.equalsIgnoreCase("test")) {
                    r.a(applicationContext, a2, ENV.TEST);
                    r.a(ENV.TEST);
                } else if (b2.equalsIgnoreCase("preview")) {
                    r.a(applicationContext, a2, ENV.PREPARE);
                    r.a(ENV.PREPARE);
                } else if (b2.equalsIgnoreCase("release")) {
                    r.a(applicationContext, a2, ENV.ONLINE);
                    r.a(ENV.ONLINE);
                }
                cl.a();
                cl.a(bno.a);
                j.e();
                NetworkSdkSetting.init(applicationContext);
                x.a(new zf());
                String utdid = UTDevice.getUtdid(applicationContext);
                m.a(utdid);
                StringBuilder sb = new StringBuilder("tid = ");
                sb.append(utdid);
                sb.append(", accsAppKey = ");
                sb.append(a2);
                sb.append(", mode = ");
                sb.append(b2);
                AMapLog.d(RPCDataItems.SWITCH_TAG_LOG, sb.toString());
                a = true;
            }
        }
    }

    public static synchronized void b(final Context context) {
        synchronized (yv.class) {
            new Thread("initNetworkSdk") {
                public final void run() {
                    yv.a(context);
                }
            }.start();
        }
    }
}
