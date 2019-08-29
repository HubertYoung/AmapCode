package defpackage;

import com.danikula.videocache.ProxyCacheException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* renamed from: etq reason: default package */
/* compiled from: Pinger */
public final class etq {
    private final ExecutorService a = Executors.newSingleThreadExecutor();
    private final String b;
    private final int c;

    /* renamed from: etq$a */
    /* compiled from: Pinger */
    class a implements Callable<Boolean> {
        private a() {
        }

        /* synthetic */ a(etq etq, byte b) {
            this();
        }

        public final /* synthetic */ Object call() throws Exception {
            return Boolean.valueOf(etq.this.b());
        }
    }

    etq(String str, int i) {
        this.b = (String) etr.a(str);
        this.c = i;
    }

    public final boolean a() {
        int i = 0;
        int i2 = 70;
        while (i < 3) {
            try {
                if (((Boolean) this.a.submit(new a(this, 0)).get((long) i2, TimeUnit.MILLISECONDS)).booleanValue()) {
                    return true;
                }
                i++;
                i2 *= 2;
            } catch (InterruptedException | ExecutionException | TimeoutException unused) {
            }
        }
        String.format(Locale.US, "Error pinging server (attempts: %d, max timeout: %d). If you see this message, please, report at https://github.com/danikula/AndroidVideoCache/issues/134. Default proxies are: %s", new Object[]{Integer.valueOf(i), Integer.valueOf(i2 / 2), c()});
        return false;
    }

    private List<Proxy> c() {
        try {
            return ProxySelector.getDefault().select(new URI(d()));
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: 0000 */
    public final boolean b() throws ProxyCacheException {
        eto eto = new eto(d());
        try {
            byte[] bytes = "ping ok".getBytes();
            eto.a(0);
            byte[] bArr = new byte[bytes.length];
            eto.a(bArr);
            boolean equals = Arrays.equals(bytes, bArr);
            eto.b();
            return equals;
        } catch (ProxyCacheException unused) {
            eto.b();
            return false;
        } catch (Throwable th) {
            eto.b();
            throw th;
        }
    }

    private String d() {
        return String.format(Locale.US, "http://%s:%d/%s", new Object[]{this.b, Integer.valueOf(this.c), "ping"});
    }
}
