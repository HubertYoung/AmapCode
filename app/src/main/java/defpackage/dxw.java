package defpackage;

import android.os.Handler;
import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.Callback;
import com.autonavi.minimap.realtimebus.RealtimeBusRequestHolder;
import com.autonavi.minimap.realtimebus.param.LineStationRequest;
import com.autonavi.minimap.route.bus.realtimebus.RealTimeBusTask$1;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.List;
import org.json.JSONException;

/* renamed from: dxw reason: default package */
/* compiled from: RealTimeBusTask */
public abstract class dxw<ResultType> implements com.autonavi.common.Callback.a {
    public Callback a;
    public int b = 30000;
    public boolean c;
    public boolean d = true;
    public Handler e;
    public b f;
    protected String g;
    protected String h;
    protected String i;
    protected String j;
    protected String k;
    protected String l;
    protected boolean m;
    protected boolean n;
    private AosRequest o;

    /* renamed from: dxw$a */
    /* compiled from: RealTimeBusTask */
    public static class a extends dxw<dyk> {
        private final List<RealTimeBusAndStationMatchup> o;

        /* access modifiers changed from: protected */
        public final /* synthetic */ Object a(byte[] bArr) {
            return b(bArr);
        }

        public a(Handler handler, Callback<dyk> callback, List<RealTimeBusAndStationMatchup> list, String str, String str2, String str3) {
            super(handler, callback);
            this.o = list;
            this.g = str;
            this.h = str2;
            this.i = str3;
            this.n = true;
        }

        /* access modifiers changed from: protected */
        public final LineStationRequest a() {
            if (this.o == null || this.o.size() <= 0) {
                return null;
            }
            LineStationRequest lineStationRequest = new LineStationRequest();
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            boolean z = false;
            for (int i = 0; i < this.o.size(); i++) {
                RealTimeBusAndStationMatchup realTimeBusAndStationMatchup = this.o.get(i);
                if (realTimeBusAndStationMatchup.mBean != null && realTimeBusAndStationMatchup.mBean.isRealTimeBus()) {
                    if (z) {
                        sb.append(",");
                        sb2.append(",");
                    }
                    sb.append(realTimeBusAndStationMatchup.mBuslineID);
                    sb2.append(realTimeBusAndStationMatchup.mStationID);
                    z = true;
                }
            }
            lineStationRequest.b = this.o.get(0).adcode();
            lineStationRequest.c = sb.toString();
            lineStationRequest.d = sb2.toString();
            lineStationRequest.e = this.g;
            lineStationRequest.f = this.h;
            lineStationRequest.g = this.i;
            if (!TextUtils.isEmpty(this.k)) {
                lineStationRequest.k = this.k;
            }
            if (!TextUtils.isEmpty(this.l)) {
                lineStationRequest.j = this.l;
            }
            if (!TextUtils.isEmpty(this.j)) {
                lineStationRequest.h = this.j;
            }
            lineStationRequest.i = this.m;
            if (this.n) {
                lineStationRequest.l = "1";
            } else {
                lineStationRequest.l = "0";
            }
            return lineStationRequest;
        }

        private static dyk b(byte[] bArr) {
            dyk dyk = new dyk();
            try {
                dyk.parser(bArr);
            } catch (UnsupportedEncodingException e) {
                kf.a((Throwable) e);
            } catch (JSONException e2) {
                AMapLog.e("RealTimeBusTask", e2.toString());
            }
            return dyk;
        }
    }

    /* renamed from: dxw$b */
    /* compiled from: RealTimeBusTask */
    public static class b implements Runnable {
        private final WeakReference<dxw> a;
        private boolean b;

        public b(dxw dxw) {
            this.a = new WeakReference<>(dxw);
        }

        public final void run() {
            if (this.a.get() != null) {
                ((dxw) this.a.get()).a(this.b);
            }
            this.b = false;
        }
    }

    /* access modifiers changed from: protected */
    public abstract LineStationRequest a();

    public abstract ResultType a(byte[] bArr);

    dxw(Handler handler, Callback<ResultType> callback) {
        this.e = handler;
        this.a = callback;
        this.f = new b(this);
    }

    /* access modifiers changed from: 0000 */
    public final void a(boolean z) {
        LineStationRequest lineStationRequest;
        if (!this.d) {
            StringBuilder sb = new StringBuilder();
            sb.append(getClass().toString());
            sb.append("doRun");
            AMapLog.d("xk", sb.toString());
            if (z) {
                String str = this.i;
                this.i = "1";
                lineStationRequest = a();
                this.i = str;
            } else {
                lineStationRequest = a();
            }
            if (lineStationRequest != null) {
                RealtimeBusRequestHolder.getInstance().sendLineStation(lineStationRequest, new RealTimeBusTask$1(this, z));
                this.o = lineStationRequest;
                this.i = "0";
            }
        }
    }

    public void cancel() {
        if (this.o != null) {
            this.o.cancel();
        }
        this.e.removeCallbacks(this.f);
        this.d = true;
    }

    public boolean isCancelled() {
        return this.d;
    }
}
