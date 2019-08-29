package defpackage;

import android.os.Handler;
import android.os.Looper;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.core.network.inter.response.InputStreamResponse;
import com.autonavi.core.network.inter.response.ResponseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: in reason: default package */
/* compiled from: AosService */
public class in {
    /* access modifiers changed from: private */
    public static volatile in c;
    private static Handler d;
    Map<AosRequest, b> a = Collections.synchronizedMap(new HashMap());
    public c b;
    private ExecutorService e = Executors.newSingleThreadExecutor();

    /* renamed from: in$a */
    /* compiled from: AosService */
    class a<T extends AosResponse> implements bpl<InputStreamResponse>, bpn {
        /* access modifiers changed from: private */
        public AosRequest b;
        /* access modifiers changed from: private */
        public AosResponseCallback<T> c;
        /* access modifiers changed from: private */
        public it d;

        public final /* synthetic */ void onSuccess(bpk bpk) {
            InputStreamResponse inputStreamResponse = (InputStreamResponse) bpk;
            if (!iv.a(inputStreamResponse) || this.b.isCanceled()) {
                if (bpv.a(3)) {
                    StringBuilder sb = new StringBuilder("send async success, result code: ");
                    sb.append(inputStreamResponse.getStatusCode());
                    sb.append(", aos url: ");
                    sb.append(this.b.getUrl());
                    bpv.b("AosService", sb.toString());
                }
                if (this.c != null && !this.b.isCanceled()) {
                    final AosResponse b2 = in.a(inputStreamResponse, (Class) bpt.a(this.c.getClass(), this.c instanceof AosResponseCallbackOnUi ? AosResponseCallbackOnUi.class : AosResponseCallback.class), this.b);
                    if (!(this.c instanceof AosResponseCallbackOnUi) || Looper.myLooper() == Looper.getMainLooper()) {
                        this.c.onSuccess(b2);
                    } else {
                        in.d().post(new Runnable() {
                            public final void run() {
                                if (!a.this.b.isCanceled()) {
                                    a.this.c.onSuccess(b2);
                                }
                            }
                        });
                    }
                }
                in.this.c(this.b);
                return;
            }
            if (bpv.a(3)) {
                StringBuilder sb2 = new StringBuilder("send async retry, aos url: ");
                sb2.append(this.b.getUrl());
                bpv.b("AosService", sb2.toString());
            }
            in.this.c(this.b);
            in.c.a(this.b, this.c);
        }

        public a(AosRequest aosRequest, AosResponseCallback<T> aosResponseCallback) {
            this.b = aosRequest;
            this.c = aosResponseCallback;
            if (this.c instanceof it) {
                this.d = (it) aosResponseCallback;
            }
        }

        public final void onFailure(bph bph, final ResponseException responseException) {
            if (bpv.a(3)) {
                StringBuilder sb = new StringBuilder("send async error, error code: ");
                sb.append(responseException.errorCode);
                sb.append("， msg: ");
                sb.append(responseException.getLocalizedMessage());
                sb.append(", aos url: ");
                sb.append(this.b.getUrl());
                bpv.b("AosService", sb.toString());
            }
            if (this.c != null && !this.b.isCanceled()) {
                if (!(this.c instanceof AosResponseCallbackOnUi) || Looper.myLooper() == Looper.getMainLooper()) {
                    this.c.onFailure(this.b, new AosResponseException(responseException));
                } else {
                    in.d().post(new Runnable() {
                        public final void run() {
                            if (!a.this.b.isCanceled()) {
                                a.this.c.onFailure(a.this.b, new AosResponseException(responseException));
                            }
                        }
                    });
                }
            }
            in.this.c(this.b);
        }

        public final void a(bph bph, long j, long j2) {
            if (this.d != null && !this.b.isCanceled()) {
                if (!(this.c instanceof AosResponseCallbackOnUi) || Looper.myLooper() == Looper.getMainLooper()) {
                    this.d.a(this.b, j, j2);
                } else {
                    Handler c2 = in.d();
                    final long j3 = j;
                    final long j4 = j2;
                    AnonymousClass3 r0 = new Runnable() {
                        public final void run() {
                            if (!a.this.b.isCanceled()) {
                                a.this.d.a(a.this.b, j3, j4);
                            }
                        }
                    };
                    c2.post(r0);
                }
            }
        }
    }

    /* renamed from: in$b */
    /* compiled from: AosService */
    static class b {
        final AosRequest a;
        final bph b;
        final a c;

        b(AosRequest aosRequest, bph bph, a aVar) {
            this.a = aosRequest;
            this.b = bph;
            this.c = aVar;
        }
    }

    /* renamed from: in$c */
    /* compiled from: AosService */
    public interface c {
        void a(String str);
    }

    private in() {
    }

    public static in a() {
        if (c == null) {
            synchronized (in.class) {
                try {
                    if (c == null) {
                        c = new in();
                    }
                }
            }
        }
        return c;
    }

    public static void a(ir irVar) {
        ip.a(irVar);
    }

    public final <T extends AosResponse> void a(final AosRequest aosRequest, final AosResponseCallback<T> aosResponseCallback) {
        if (aosRequest != null) {
            this.e.execute(new Runnable() {
                public final void run() {
                    in a2 = in.a();
                    AosRequest aosRequest = aosRequest;
                    AosResponseCallback aosResponseCallback = aosResponseCallback;
                    if (aosRequest != null && !aosRequest.isCanceled()) {
                        bph buildHttpRequest = aosRequest.buildHttpRequest();
                        a aVar = null;
                        if (aosResponseCallback != null) {
                            aVar = new a(aosRequest, aosResponseCallback);
                        }
                        if (bpv.a(3)) {
                            StringBuilder sb = new StringBuilder("send async, aos url: ");
                            sb.append(aosRequest.getUrl());
                            sb.append("\nhttp url: ");
                            sb.append(buildHttpRequest.getUrl());
                            bpv.b("AosService", sb.toString());
                        }
                        if (!aosRequest.isCanceled()) {
                            a2.a.put(aosRequest, new b(aosRequest, buildHttpRequest, aVar));
                            box.a();
                            box.a(buildHttpRequest, (bpl<T>) aVar);
                        }
                    }
                }
            });
        }
    }

    public final void a(AosRequest aosRequest) {
        if (aosRequest != null) {
            if (bpv.a(3)) {
                StringBuilder sb = new StringBuilder("cancel request, url: ");
                sb.append(aosRequest.getUrl());
                bpv.b("AosService", sb.toString());
            }
            aosRequest.cancel();
            b bVar = this.a.get(aosRequest);
            if (bVar != null) {
                box.a();
                box.a(bVar.b);
            }
            c(aosRequest);
        }
    }

    public final InputStreamResponse b(AosRequest aosRequest) {
        bph buildHttpRequest = aosRequest.buildHttpRequest();
        if (bpv.a(3)) {
            StringBuilder sb = new StringBuilder("send sync, aos url: ");
            sb.append(aosRequest.getUrl());
            sb.append("\nhttp url: ");
            sb.append(buildHttpRequest.getUrl());
            bpv.b("AosService", sb.toString());
        }
        box.a();
        InputStreamResponse inputStreamResponse = (InputStreamResponse) box.a(buildHttpRequest, InputStreamResponse.class);
        if (inputStreamResponse == null) {
            if (bpv.a(3)) {
                StringBuilder sb2 = new StringBuilder("send sync complete, result is null!\naos url: ");
                sb2.append(aosRequest.getUrl());
                bpv.b("AosService", sb2.toString());
            }
            return null;
        }
        if (iv.a(inputStreamResponse)) {
            if (bpv.a(3)) {
                StringBuilder sb3 = new StringBuilder("send sync retry, aos url: ");
                sb3.append(aosRequest.getUrl());
                bpv.b("AosService", sb3.toString());
            }
            inputStreamResponse = b(aosRequest);
        }
        if (bpv.a(3)) {
            StringBuilder sb4 = new StringBuilder("send sync success, result code: ");
            sb4.append(inputStreamResponse.getStatusCode());
            sb4.append(", aos url: ");
            sb4.append(aosRequest.getUrl());
            bpv.b("AosService", sb4.toString());
        }
        return inputStreamResponse;
    }

    public static <T extends AosResponse> T a(InputStreamResponse inputStreamResponse, Class<T> cls, AosRequest aosRequest) {
        if (inputStreamResponse == null) {
            return null;
        }
        try {
            T t = (AosResponse) cls.newInstance();
            t.setInputStreamResponse(inputStreamResponse);
            t.setAosRequest(aosRequest);
            t.parse();
            bph request = inputStreamResponse.getRequest();
            if (request != null && request.requestStatistics.o <= 0) {
                request.requestStatistics.o = inputStreamResponse.getContentLength();
            }
            return t;
        } catch (InstantiationException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append(cls.getName());
            sb.append(" 必须有无参数public默认构造方法 ");
            throw new RuntimeException(sb.toString());
        } catch (IllegalAccessException unused2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(cls.getName());
            sb2.append(" 必须有无参数public默认构造方法 ");
            throw new RuntimeException(sb2.toString());
        }
    }

    /* access modifiers changed from: private */
    public void c(AosRequest aosRequest) {
        this.a.remove(aosRequest);
    }

    /* access modifiers changed from: private */
    public static Handler d() {
        if (d == null) {
            synchronized (in.class) {
                if (d == null) {
                    d = new Handler(Looper.getMainLooper());
                }
            }
        }
        return d;
    }
}
