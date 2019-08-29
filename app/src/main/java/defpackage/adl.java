package defpackage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.amap.bundle.schoolbus.request.SchoolbusStatusRequest$1;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.schoolbus.SchoolBusRequestHolder;
import com.autonavi.minimap.schoolbus.param.RouteStatusRequest;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.HashMap;
import java.util.Map;

/* renamed from: adl reason: default package */
/* compiled from: SchoolbusStatusManager */
public class adl implements adm {
    private static volatile adl h;
    public String a = "default";
    public int b = -1;
    public JsFunctionCallback c;
    public a d = new a(this, 0);
    public volatile Map<String, Boolean> e = new HashMap();
    public boolean f;
    public Map<String, String> g;
    private String i = "";

    /* renamed from: adl$a */
    /* compiled from: SchoolbusStatusManager */
    public class a extends Handler {
        private a() {
        }

        /* synthetic */ a(adl adl, byte b) {
            this();
        }

        public final void handleMessage(Message message) {
            if (message.what == 1010) {
                GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                adl.a(adl.this, (String) "role", String.valueOf(message.getData().getInt("role")));
                adl.a(adl.this, (String) "routeId", message.getData().getString("routeId"));
                adl.a(adl.this, (String) "latitude", String.valueOf(latestPosition.getLatitude()));
                adl.a(adl.this, (String) "longitude", String.valueOf(latestPosition.getLongitude()));
                adl.a(adl.this, (Map) adl.this.g);
            }
        }
    }

    public static adl a() {
        if (h == null) {
            synchronized (adl.class) {
                try {
                    if (h == null) {
                        h = new adl();
                    }
                }
            }
        }
        return h;
    }

    private adl() {
    }

    public static void a(String str, String str2) {
        ku.a().b(str, str2);
    }

    public final void a(adk adk) {
        try {
            StringBuilder sb = new StringBuilder("onCallback :");
            sb.append(adk.b);
            sb.append("; currentRequestRouteId :");
            sb.append(this.a);
            a((String) "school_manager", sb.toString());
            if (this.b == 2 && (adk.d == 0 || adk.e == 1)) {
                StringBuilder sb2 = new StringBuilder("setPollingStatus false :");
                sb2.append(adk.c);
                a((String) "school_manager", sb2.toString());
                this.e.put(adk.c, Boolean.FALSE);
            }
            if (b(adk.c) && this.a != null && this.a.equals(adk.c)) {
                a((String) "school_manager", "sendMessageDelayedSuccessed value :".concat(String.valueOf(this.d.sendMessageDelayed(a(1010, adk.c, this.b), 8000))));
            }
        } catch (Throwable th) {
            StringBuilder sb3 = new StringBuilder("onCallback exception :");
            sb3.append(th.toString());
            a((String) "school_manager", sb3.toString());
            th.printStackTrace();
        }
    }

    private boolean b(String str) {
        if (this.e.containsKey(str)) {
            StringBuilder sb = new StringBuilder("checkPollingForRouteId value :");
            sb.append(this.e.get(str));
            a((String) "school_manager", sb.toString());
            return this.e.get(str).booleanValue();
        }
        a((String) "school_manager", "checkPollingForRouteId false:".concat(String.valueOf(str)));
        return false;
    }

    public final void a(String str) {
        if (str != null && str.length() > 0) {
            if (this.a.equals(str)) {
                if (!b(this.a)) {
                    this.e.put(this.a, Boolean.TRUE);
                    this.d.sendMessage(a(1010, this.a, this.b));
                }
                return;
            }
            this.i = this.a;
            this.a = str;
            this.e.put(this.i, Boolean.FALSE);
            if (!b(this.a)) {
                this.e.put(this.a, Boolean.TRUE);
                this.d.sendMessage(a(1010, this.a, this.b));
            }
        }
    }

    public final boolean b() {
        StringBuilder sb = new StringBuilder("isNeedOnbackground :");
        sb.append(this.a);
        sb.append(" needBack: ");
        sb.append(b(this.a));
        a((String) "school_manager", sb.toString());
        return b(this.a);
    }

    public final Message a(int i2, String str, int i3) {
        Message obtain = Message.obtain(this.d, i2);
        Bundle bundle = new Bundle();
        bundle.putString("routeId", str);
        bundle.putInt("role", i3);
        obtain.setData(bundle);
        return obtain;
    }

    static /* synthetic */ void a(adl adl, String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            if (adl.g == null) {
                adl.g = new HashMap();
            }
            if (str2 == null) {
                str2 = "";
            }
            adl.g.put(str, str2);
        }
    }

    static /* synthetic */ void a(adl adl, Map map) {
        a((String) "school_manager", "requestPollingStatus=".concat(String.valueOf(map)));
        if (map != null) {
            ado ado = new ado(map, adl.c, adl);
            RouteStatusRequest routeStatusRequest = new RouteStatusRequest();
            routeStatusRequest.f = ado.e;
            routeStatusRequest.g = ado.f;
            routeStatusRequest.d = ado.c;
            routeStatusRequest.e = ado.d;
            SchoolBusRequestHolder.getInstance().sendRouteStatus(routeStatusRequest, new SchoolbusStatusRequest$1(ado));
        }
    }
}
