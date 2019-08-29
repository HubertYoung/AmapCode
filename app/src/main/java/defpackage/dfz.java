package defpackage;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: dfz reason: default package */
/* compiled from: NaviTtsDownloader */
public final class dfz {
    static final ExecutorService a = Executors.newSingleThreadExecutor();
    /* access modifiers changed from: 0000 */
    public b b = new b(0);
    private a c;
    /* access modifiers changed from: private */
    public String d;
    /* access modifiers changed from: private */
    public String e;

    /* renamed from: dfz$a */
    /* compiled from: NaviTtsDownloader */
    public interface a {
        void a();

        void a(long j, long j2);

        void a(File file);

        void a(Throwable th);
    }

    /* renamed from: dfz$b */
    /* compiled from: NaviTtsDownloader */
    static class b implements com.autonavi.common.Callback.a {
        private volatile boolean a;

        private b() {
        }

        /* synthetic */ b(byte b) {
            this();
        }

        public final void cancel() {
            this.a = true;
        }

        public final boolean isCancelled() {
            return this.a;
        }
    }

    public dfz(String str, String str2, a aVar) {
        this.d = str;
        this.e = str2;
        this.c = aVar;
    }

    private void a(Throwable th) {
        if (this.c != null) {
            this.c.a(th);
        }
    }

    static /* synthetic */ void a(dfz dfz, String str, String str2) {
        bot bot = new bot();
        bot.a((int) HttpConstants.CONNECTION_TIME_OUT);
        HashMap hashMap = new HashMap(1);
        String a2 = dhd.a();
        if (!TextUtils.isEmpty(a2)) {
            hashMap.put(OfflineUtil.CDN_HEADER_MAC, a2);
        }
        if (!TextUtils.isEmpty(str2)) {
            File file = new File(str2);
            if (file.exists()) {
                StringBuilder sb = new StringBuilder("bytes=");
                sb.append(file.length());
                sb.append("-");
                hashMap.put("RANGE", sb.toString());
            }
        }
        bot.a((Map<String, String>) hashMap);
        try {
            bol bol = new bol(str, "GET", bot);
            if (dfz.c != null) {
                dfz.c.a();
            }
            bol.a();
            dga dga = new dga(str2);
            dga.b = false;
            dga.a = true;
            dga.c = dfz.b;
            dga.d = dfz.c;
            File a3 = dga.a(bol);
            if (a3 == null) {
                dfz.a((Throwable) new RuntimeException("result is null!"));
                return;
            }
            if (!dfz.b.isCancelled() && dfz.c != null) {
                dfz.c.a(a3);
            }
        } catch (Exception e2) {
            dfz.a((Throwable) e2);
        }
    }
}
