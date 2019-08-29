package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.autonavi.indoor.constant.MessageCode;
import com.autonavi.jni.ae.pos.LocInfo;
import com.autonavi.jni.ae.pos.LocListener;
import com.autonavi.jni.ae.pos.LocNGMInfo;
import com.autonavi.jni.ae.route.observer.RouteObserver;
import com.autonavi.jni.ae.route.route.CalcRouteResult;

/* renamed from: nz reason: default package */
/* compiled from: NaviMessenger */
public final class nz {
    private final Handler a = new Handler(Looper.getMainLooper()) {
        public final void handleMessage(Message message) {
            Object obj;
            switch (message.what) {
                case 100:
                    if (NaviManager.a().g != null) {
                        int i = message.getData().getInt("type");
                        CalcRouteResult calcRouteResult = null;
                        Object[] objArr = message.obj != null ? (Object[]) message.obj : null;
                        if (objArr == null || objArr.length <= 0) {
                            obj = null;
                        } else {
                            if (objArr[0] != null) {
                                calcRouteResult = (CalcRouteResult) objArr[0];
                            }
                            obj = objArr[1];
                        }
                        boolean z = message.getData().getBoolean("isLocal");
                        for (RouteObserver onNewRoute : NaviManager.a().g) {
                            onNewRoute.onNewRoute(i, calcRouteResult, obj, z);
                        }
                        return;
                    }
                    break;
                case 101:
                    if (NaviManager.a().g != null) {
                        int i2 = message.getData().getInt("type");
                        int i3 = message.getData().getInt("errorCode");
                        boolean z2 = message.getData().getBoolean("isLocal");
                        Object obj2 = message.obj;
                        for (RouteObserver onNewRouteError : NaviManager.a().g) {
                            onNewRouteError.onNewRouteError(i2, i3, obj2, z2);
                        }
                        return;
                    }
                    break;
                case MessageCode.MSG_NLP_RESPONSED /*214*/:
                    if (NaviManager.a().f != null) {
                        for (LocListener updateNaviInfo : NaviManager.a().f) {
                            updateNaviInfo.updateNaviInfo((LocInfo) message.getData().getSerializable("LocInfo"));
                        }
                        return;
                    }
                    break;
                case 300:
                    a.a.a(message.getData().getString("str"), message.getData().getInt("type"), -1);
                    return;
                case 301:
                    int i4 = message.getData().getInt("type");
                    a.a;
                    ve.a(i4);
                    return;
                case 1105:
                    NaviManager.a().b(message.getData().getInt("key"), message.getData().getString("value"));
                    break;
                case 9999:
                    LocNGMInfo locNGMInfo = (LocNGMInfo) message.getData().getSerializable("LocNGMInfo");
                    if (locNGMInfo == null) {
                        ewt.b("HwMM", "locNGMInfo==null");
                        return;
                    }
                    long j = locNGMInfo.gpsTickTime;
                    double d = locNGMInfo.heading;
                    int i5 = locNGMInfo.isRerouting;
                    double d2 = locNGMInfo.dy;
                    double d3 = locNGMInfo.dx;
                    int i6 = locNGMInfo.flag;
                    try {
                        if (bno.a) {
                            StringBuilder sb = new StringBuilder("sendMapMatchingResult : timetag :");
                            sb.append(j);
                            sb.append(" heading:");
                            sb.append(d);
                            sb.append(" rerouted:");
                            sb.append(i5);
                            sb.append(" latitude : ");
                            sb.append(d2);
                            sb.append(" longitude:");
                            sb.append(d3);
                            sb.append(" flags:");
                            sb.append(i6);
                            ewt.b("HwMMgpsREC", sb.toString());
                        }
                        ews a2 = ews.a();
                        if (!(a2.c == null || !a2.c.isAlive() || a2.b == null)) {
                            try {
                                Handler handler = a2.b;
                                a aVar = new a(j, d, d3, d2, i5);
                                handler.obtainMessage(1, aVar).sendToTarget();
                                return;
                            } catch (Exception unused) {
                            }
                        }
                        return;
                    } catch (Exception e) {
                        ewt.b("HwMM", e.getMessage());
                        return;
                    }
            }
        }
    };

    public final void a(Message message) {
        this.a.sendMessage(message);
    }

    public static Message a(int i) {
        Message obtain = Message.obtain();
        obtain.what = i;
        return obtain;
    }
}
