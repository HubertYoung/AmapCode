package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.autonavi.core.network.inter.response.ResponseException;
import java.io.Closeable;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Arrays;

/* renamed from: boy reason: default package */
/* compiled from: NetworkClient */
public final class boy {
    public static bpd a;
    final boz b;
    public bqa c;
    bpd d;
    private final bpb e;

    public boy() {
        this(new bof());
    }

    public boy(@NonNull bpb bpb) {
        this.b = new boz(new Handler(Looper.getMainLooper()));
        this.e = bpb;
        if (bpb instanceof bpc) {
            bpv.c("ANet-NetworkClient", "setup upload progress callback!");
            ((bpc) bpb).a(new bpn() {
                public final void a(bph bph, long j, long j2) {
                    if (bpv.a(3)) {
                        StringBuilder sb = new StringBuilder("upload progress, totalProgress：");
                        sb.append(j);
                        sb.append(" nowProgress: ");
                        sb.append(j2);
                        sb.append("\nurl:");
                        sb.append(bph.getUrl());
                        bpv.b("ANet-NetworkClient", sb.toString());
                    }
                    boz boz = boy.this.b;
                    bpl bpl = boz.a.get(bph);
                    bpn bpn = null;
                    if (bpl instanceof bpn) {
                        bpn = (bpn) bpl;
                    }
                    if (bpn != null && !bph.isCancelled()) {
                        try {
                            if (bpn instanceof bpm) {
                                b bVar = new b(bpn, bph, j, j2);
                                boz.a((Runnable) bVar);
                                return;
                            }
                            bpn.a(bph, j, j2);
                        } catch (Exception e) {
                            ResponseException responseException = new ResponseException("upload progress callback error!");
                            responseException.errorCode = 10;
                            responseException.exception = e;
                            responseException.isCallbackError = true;
                            if (bpn instanceof bpl) {
                                boz.a((bpl) bpn, bph, responseException);
                            }
                        }
                    }
                }
            });
        }
    }

    public final void a(bpd bpd) {
        if (bpd != null) {
            this.d = bpd;
        }
    }

    public final void a(int i) {
        if (this.c == null) {
            synchronized (this) {
                if (this.c == null) {
                    this.c = new bqa((String) "network-client", i);
                    if (bpv.a(4)) {
                        bpv.c("ANet-NetworkClient", "initThreadPool: ".concat(String.valueOf(i)));
                    }
                }
            }
        }
    }

    public final void a(int[] iArr) {
        if (this.c == null) {
            synchronized (this) {
                if (this.c == null) {
                    this.c = new bqa((String) "network-client", iArr);
                }
                if (bpv.a(4)) {
                    StringBuilder sb = new StringBuilder("initThreadPool: ");
                    sb.append(Arrays.toString(iArr));
                    bpv.c("ANet-NetworkClient", sb.toString());
                }
            }
        }
    }

    public final <T extends bpk> T a(@NonNull bph bph, Class<T> cls) {
        T t;
        if (!bph.isValid()) {
            if (bpv.a(6)) {
                bpv.e("ANet-NetworkClient", "invalid request!");
            }
            return null;
        }
        bph.requestStatistics.k = System.currentTimeMillis();
        bph.requestStatistics.a("startSync");
        this.b.a(bph, null);
        try {
            String url = bph.getUrl();
            if (a != null) {
                a.a(bph);
                if (bpv.a(2)) {
                    StringBuilder sb = new StringBuilder("global filter request, before url:");
                    sb.append(url);
                    sb.append("\nafter url: ");
                    sb.append(bph.getUrl());
                    bpv.a("ANet-NetworkClient", sb.toString());
                }
            }
            if (this.d != null) {
                this.d.a(bph);
                if (bpv.a(2)) {
                    StringBuilder sb2 = new StringBuilder("local filter request, before url:");
                    sb2.append(url);
                    sb2.append("\nafter url: ");
                    sb2.append(bph.getUrl());
                    bpv.a("ANet-NetworkClient", sb2.toString());
                }
            }
            t = b(bph, cls);
        } catch (Exception e2) {
            bph.requestStatistics.a("-requestError");
            ResponseException exception2ResponseException = ResponseException.exception2ResponseException(e2);
            if (bpv.a(6)) {
                StringBuilder sb3 = new StringBuilder("sync send error, errorCode: ");
                sb3.append(exception2ResponseException.errorCode);
                sb3.append(", msg:");
                sb3.append(exception2ResponseException.getLocalizedMessage());
                sb3.append(", url: ");
                sb3.append(bph.getUrl());
                bpv.e("ANet-NetworkClient", sb3.toString());
            }
            t = null;
        } catch (Throwable th) {
            bph.requestStatistics.a("-finishRequest");
            this.b.a(bph);
            throw th;
        }
        if (t != null) {
            if (a != null) {
                a.a((bpk) t);
            }
            if (this.d != null) {
                this.d.a((bpk) t);
            }
        }
        bph.requestStatistics.a("-finishRequest");
        this.b.a(bph);
        return t;
    }

    public final <T extends bpk> void a(@NonNull final bph bph, @Nullable final bpl<T> bpl) {
        a(5);
        if (!bph.isValid()) {
            bpv.e("ANet-NetworkClient", "invalid request!");
            ResponseException responseException = new ResponseException("invalid request!");
            responseException.errorCode = 3;
            this.b.a(bpl, bph, responseException);
            return;
        }
        bph.requestStatistics.k = System.currentTimeMillis();
        bph.requestStatistics.a("startAsync");
        this.c.a(new Runnable() {
            public final void run() {
                InputStream inputStream;
                Type type;
                Class cls;
                bpk b2;
                boz boz;
                bpl bpl;
                bph bph;
                Closeable closeable;
                if (bph.isCancelled()) {
                    bph.requestStatistics.a("canceled");
                    if (bpv.a(3)) {
                        StringBuilder sb = new StringBuilder("request is canceled, url: ");
                        sb.append(bph.getUrl());
                        bpv.b("ANet-NetworkClient", sb.toString());
                    }
                    return;
                }
                boy.this.b.a(bph, bpl);
                try {
                    String url = bph.getUrl();
                    if (boy.a != null) {
                        boy.a.a(bph);
                        if (bpv.a(2)) {
                            StringBuilder sb2 = new StringBuilder("global filter request, before url:");
                            sb2.append(url);
                            sb2.append("\nafter url: ");
                            sb2.append(bph.getUrl());
                            bpv.a("ANet-NetworkClient", sb2.toString());
                        }
                    }
                    if (boy.this.d != null) {
                        boy.this.d.a(bph);
                        if (bpv.a(2)) {
                            StringBuilder sb3 = new StringBuilder("local filter request, before url:");
                            sb3.append(url);
                            sb3.append("\nafter url: ");
                            sb3.append(bph.getUrl());
                            bpv.a("ANet-NetworkClient", sb3.toString());
                        }
                    }
                    inputStream = null;
                    if (bpl != null) {
                        type = bpt.a(bpl.getClass(), bpl instanceof bpm ? bpm.class : bpl.class);
                    } else {
                        type = null;
                    }
                    if (type == null) {
                        cls = null;
                    } else {
                        try {
                            cls = (Class) type;
                        } catch (Exception e) {
                            boy.this.b.a(bpl, bph, ResponseException.exception2ResponseException(e));
                            bow.a(null);
                            boy.this.b.a(bph);
                            return;
                        }
                    }
                    b2 = boy.this.b(bph, cls);
                    if (b2 != null) {
                        if (boy.a != null) {
                            boy.a.a(b2);
                        }
                        if (boy.this.d != null) {
                            boy.this.d.a(b2);
                        }
                    }
                    boz = boy.this.b;
                    bpl = bpl;
                    bph = bph;
                    if (bpl != null && !bph.isCancelled()) {
                        if (bpv.a(3)) {
                            StringBuilder sb4 = new StringBuilder("post response, responseCode: ");
                            sb4.append(b2 != null ? b2.getStatusCode() : -1);
                            sb4.append(", url: ");
                            sb4.append(bph.getUrl());
                            bpv.b("ANet-NetworkDispatcher", sb4.toString());
                        }
                        if (bpl instanceof bpm) {
                            boz.a((Runnable) new c(bpl, b2));
                        } else {
                            bpl.onSuccess(b2);
                            if (b2 == null) {
                                closeable = null;
                            } else {
                                closeable = b2.getBodyInputStream();
                            }
                            bow.a(closeable);
                        }
                    }
                } catch (Exception e2) {
                    if (b2 != null) {
                        inputStream = b2.getBodyInputStream();
                    }
                    bow.a(inputStream);
                    ResponseException responseException = new ResponseException("dispatch response error!");
                    responseException.errorCode = 10;
                    responseException.exception = e2;
                    responseException.response = b2;
                    responseException.isCallbackError = true;
                    boz.a(bpl, bph, responseException);
                } catch (Throwable th) {
                    boy.this.b.a(bph);
                    throw th;
                }
                boy.this.b.a(bph);
            }
        }, bph.getPriority(), "async");
    }

    public final void a(@NonNull bph bph) {
        if (bpv.a(3)) {
            StringBuilder sb = new StringBuilder("cancel request, url:");
            sb.append(bph.getUrl());
            bpv.b("ANet-NetworkClient", sb.toString());
        }
        bph.requestStatistics.a("-cancel");
        this.b.b(bph);
    }

    /* access modifiers changed from: 0000 */
    public final <T extends bpk> T b(bph bph, Class<T> cls) throws Exception {
        bph.requestStatistics.f = bph.getUrl();
        bph.requestStatistics.a = bpu.a(bph.getMethod());
        if (bph instanceof bpj) {
            bph.requestStatistics.g = ((bpj) bph).getBody();
        }
        if (bpv.a(2)) {
            bpv.a("ANet-NetworkClient", bph.toString());
        }
        bpa a2 = this.e.a(bph);
        InputStream inputStream = null;
        if (cls == null) {
            if (a2 != null) {
                inputStream = a2.getBodyInputStream();
            }
            bow.a(inputStream);
            if (bpv.a(5)) {
                StringBuilder sb = new StringBuilder("http request do not need a response, url：");
                sb.append(bph.getUrl());
                bpv.d("ANet-NetworkClient", sb.toString());
            }
            throw new ResponseException("do not need a response!");
        }
        T a3 = bpu.a(bph, cls, a2);
        bph.requestStatistics.o = a2 == null ? 0 : a2.getContentLength();
        bph.requestStatistics.p = System.currentTimeMillis();
        a3.setRequest(bph);
        int statusCode = a2.getStatusCode();
        bph.requestStatistics.a("-statusCode".concat(String.valueOf(statusCode)));
        if (statusCode < 200 || (statusCode >= 400 && statusCode != bok.e)) {
            if (a3 != null) {
                inputStream = a3.getBodyInputStream();
            }
            bow.a(inputStream);
            ResponseException responseException = new ResponseException("server error, code = ".concat(String.valueOf(statusCode)));
            responseException.response = a3;
            responseException.errorCode = a3.getStatusCode();
            throw responseException;
        }
        if (bpv.a(3)) {
            StringBuilder sb2 = new StringBuilder("send complete, statusCode: ");
            sb2.append(a3.getStatusCode());
            sb2.append(", url: ");
            sb2.append(bph.getUrl());
            bpv.b("ANet-NetworkClient", sb2.toString());
        }
        return a3;
    }
}
