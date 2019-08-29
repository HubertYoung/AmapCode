package com.xiaomi.push.service;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build;
import android.os.Build.VERSION;
import com.autonavi.minimap.route.common.view.RouteBanner;
import com.xiaomi.channel.commonutils.android.j;
import com.xiaomi.network.Fallback;
import com.xiaomi.network.HostFilter;
import com.xiaomi.network.HostManager;
import com.xiaomi.network.HostManager.HostManagerFactory;
import com.xiaomi.network.HostManager.HttpGet;
import com.xiaomi.push.protobuf.a.C0081a;
import com.xiaomi.push.protobuf.b.C0082b;
import com.xiaomi.smack.util.d;
import com.xiaomi.stats.f;
import com.xiaomi.stats.h;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class au extends com.xiaomi.push.service.ba.a implements HostManagerFactory {
    private XMPushService a;
    private long b;

    static class a implements HttpGet {
        a() {
        }

        public String a(String str) {
            Builder buildUpon = Uri.parse(str).buildUpon();
            buildUpon.appendQueryParameter("sdkver", RouteBanner.REQUEST_KEY_RIDE);
            buildUpon.appendQueryParameter("osver", String.valueOf(VERSION.SDK_INT));
            StringBuilder sb = new StringBuilder();
            sb.append(Build.MODEL);
            sb.append(":");
            sb.append(VERSION.INCREMENTAL);
            buildUpon.appendQueryParameter("os", d.a(sb.toString()));
            buildUpon.appendQueryParameter("mi", String.valueOf(j.c()));
            String builder = buildUpon.toString();
            com.xiaomi.channel.commonutils.logger.b.c("fetch bucket from : ".concat(String.valueOf(builder)));
            URL url = new URL(builder);
            int port = url.getPort() == -1 ? 80 : url.getPort();
            try {
                long currentTimeMillis = System.currentTimeMillis();
                String a = com.xiaomi.channel.commonutils.network.d.a(j.a(), url);
                long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(url.getHost());
                sb2.append(":");
                sb2.append(port);
                h.a(sb2.toString(), (int) currentTimeMillis2, null);
                return a;
            } catch (IOException e) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(url.getHost());
                sb3.append(":");
                sb3.append(port);
                h.a(sb3.toString(), -1, e);
                throw e;
            }
        }
    }

    static class b extends HostManager {
        protected b(Context context, HostFilter hostFilter, HttpGet httpGet, String str) {
            super(context, hostFilter, httpGet, str);
        }

        public String getRemoteFallbackJSON(ArrayList<String> arrayList, String str, String str2, boolean z) {
            try {
                if (f.a().c()) {
                    str2 = ba.e();
                }
                return super.getRemoteFallbackJSON(arrayList, str, str2, z);
            } catch (IOException e) {
                h.a(0, com.xiaomi.push.thrift.a.GSLB_ERR.a(), 1, null, com.xiaomi.channel.commonutils.network.d.c(sAppContext) ? 1 : 0);
                throw e;
            }
        }
    }

    au(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    public static void a(XMPushService xMPushService) {
        au auVar = new au(xMPushService);
        ba.a().a((com.xiaomi.push.service.ba.a) auVar);
        synchronized (HostManager.class) {
            HostManager.setHostManagerFactory(auVar);
            HostManager.init(xMPushService, null, new a(), "0", "push", "2.2");
        }
    }

    public HostManager a(Context context, HostFilter hostFilter, HttpGet httpGet, String str) {
        return new b(context, hostFilter, httpGet, str);
    }

    public void a(C0081a aVar) {
    }

    public void a(C0082b bVar) {
        if (bVar.e() && bVar.d() && System.currentTimeMillis() - this.b > 3600000) {
            StringBuilder sb = new StringBuilder("fetch bucket :");
            sb.append(bVar.d());
            com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
            this.b = System.currentTimeMillis();
            HostManager instance = HostManager.getInstance();
            instance.clear();
            instance.refreshFallbacks();
            com.xiaomi.smack.a h = this.a.h();
            if (h != null) {
                Fallback fallbacksByHost = instance.getFallbacksByHost(h.d().e());
                if (fallbacksByHost != null) {
                    ArrayList<String> d = fallbacksByHost.d();
                    boolean z = true;
                    Iterator<String> it = d.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (it.next().equals(h.e())) {
                                z = false;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    if (z && !d.isEmpty()) {
                        com.xiaomi.channel.commonutils.logger.b.a((String) "bucket changed, force reconnect");
                        this.a.a(0, (Exception) null);
                        this.a.a(false);
                    }
                }
            }
        }
    }
}
