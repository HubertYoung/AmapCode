package defpackage;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.status.NetworkStatusHelper.NetworkStatus;
import anet.channel.status.NetworkStatusHelper.a;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.TokenApiImpl;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.android.spdy.SpdyAgent;

/* renamed from: av reason: default package */
/* compiled from: QuicConnectionDetector */
public final class av {
    /* access modifiers changed from: private */
    public static String a;
    private static HashMap<String, Long> b = new HashMap<>();
    /* access modifiers changed from: private */
    public static AtomicBoolean c = new AtomicBoolean(false);
    private static bp d = new bp() {
        public final boolean a(bo boVar) {
            String str = boVar.e().protocol;
            return "quic".equals(str) || "quicplain".equals(str);
        }
    };
    /* access modifiers changed from: private */
    public static AtomicInteger e = new AtomicInteger(1);

    public static void a(NetworkStatus networkStatus) {
        if (!j.g()) {
            cl.b("awcn.QuicConnDetector", "startDetect", null, "quic global config close.");
        } else if (NetworkStatusHelper.h()) {
            if (TextUtils.isEmpty(a)) {
                cl.d("awcn.QuicConnDetector", "startDetect", null, "host is null");
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            Long l = b.get(networkStatus.getType());
            if (l == null || l.longValue() + TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL <= currentTimeMillis) {
                final List<bo> a2 = bu.a().a(a, d);
                if (a2.isEmpty()) {
                    cl.d("awcn.QuicConnDetector", "startDetect", null, "quic strategy is null.");
                    return;
                }
                b.put(networkStatus.getType(), Long.valueOf(currentTimeMillis));
                ck.a(new Runnable() {
                    public final void run() {
                        if (av.c.compareAndSet(false, true)) {
                            SpdyAgent.InitializeCerts();
                        }
                        final bo boVar = (bo) a2.get(0);
                        StringBuilder sb = new StringBuilder(AjxHttpLoader.DOMAIN_HTTPS);
                        sb.append(av.a);
                        String sb2 = sb.toString();
                        StringBuilder sb3 = new StringBuilder("QuicDetect");
                        sb3.append(av.e.getAndIncrement());
                        bi biVar = new bi(m.a(), new af(sb2, sb3.toString(), boVar));
                        biVar.a(257, (ah) new ah() {
                            public final void a(p pVar, int i, ag agVar) {
                                bm bmVar = new bm();
                                if (i == 1) {
                                    bmVar.a = true;
                                }
                                bu.a().a(av.a, boVar, bmVar);
                                pVar.a(false);
                            }
                        });
                        biVar.q.isCommitted = true;
                        biVar.a();
                    }
                }, c.c);
            }
        }
    }

    public static void a() {
        cl.d("awcn.QuicConnDetector", "registerListener", null, new Object[0]);
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(m.a());
        a = defaultSharedPreferences.getString("quic_detector_host", "");
        NetworkStatusHelper.a((a) new a() {
            public final void a(NetworkStatus networkStatus) {
                av.a(networkStatus);
            }
        });
        bu.a().a((br) new br() {
            public final void a(d dVar) {
                if (dVar != null && dVar.b != null) {
                    for (int i = 0; i < dVar.b.length; i++) {
                        String str = dVar.b[i].a;
                        a[] aVarArr = dVar.b[i].h;
                        if (aVarArr != null && aVarArr.length > 0) {
                            for (a aVar : aVarArr) {
                                String str2 = aVar.b;
                                if ("quic".equals(str2) || "quicplain".equals(str2)) {
                                    if (!str.equals(av.a)) {
                                        av.a = str;
                                        Editor edit = defaultSharedPreferences.edit();
                                        edit.putString("quic_detector_host", av.a);
                                        edit.apply();
                                    }
                                    av.a(NetworkStatusHelper.a());
                                    return;
                                }
                            }
                            continue;
                        }
                    }
                }
            }
        });
    }
}
