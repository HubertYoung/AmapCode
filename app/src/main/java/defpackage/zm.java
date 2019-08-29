package defpackage;

import android.app.Application;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.sdk.packet.d;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import com.taobao.accs.common.Constants;
import java.util.HashMap;
import tv.danmaku.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;

/* renamed from: zm reason: default package */
/* compiled from: ApmFilter */
public final class zm implements bpd {
    public final bph a(bph bph) {
        return bph;
    }

    public final bpk a(final bpk bpk) {
        if (!(bpk == null || bpk.getStatusCode() == 200)) {
            zl.a();
            if (zl.b()) {
                bph request = bpk.getRequest();
                if (request != null) {
                    zl.a();
                    if (!zl.a(request.getUrl()) || !NetworkReachability.b()) {
                        return bpk;
                    }
                    ahm.a(new Runnable() {
                        public final void run() {
                            String str;
                            GeoPoint geoPoint;
                            try {
                                zr zrVar = new zr();
                                Application application = AMapAppGlobal.getApplication();
                                bpk bpk = bpk;
                                HashMap hashMap = new HashMap();
                                zs zsVar = new zs(application, bpk);
                                hashMap.put("isp", zs.a());
                                hashMap.put("networktype", zs.h());
                                hashMap.put("srchost", zs.b());
                                hashMap.put("wifiInfo", zs.c());
                                hashMap.put("teleInfo", zs.i());
                                f f = aaf.f();
                                if (f == null) {
                                    str = "";
                                } else {
                                    str = f.b();
                                }
                                hashMap.put("region", str);
                                String str2 = "";
                                if (zsVar.e != null) {
                                    str2 = zsVar.e.toString();
                                }
                                hashMap.put("strength", str2);
                                String str3 = "";
                                f f2 = aaf.f();
                                if (f2 == null) {
                                    geoPoint = null;
                                } else {
                                    geoPoint = f2.a();
                                }
                                if (geoPoint != null) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(geoPoint.getLongitude());
                                    sb.append(",");
                                    sb.append(geoPoint.getLatitude());
                                    str3 = sb.toString();
                                }
                                hashMap.put("lonlat", str3);
                                hashMap.put("SDKVersion", String.valueOf(VERSION.SDK_INT));
                                hashMap.put(Constants.KEY_MODEL, Build.MODEL);
                                hashMap.put(d.n, Build.MODEL);
                                hashMap.put("startTime", zsVar.a("appstartid"));
                                hashMap.put("stepid", zsVar.a("stepid"));
                                hashMap.put("channel", zsVar.a("channel"));
                                hashMap.put("tid", zsVar.a("tid"));
                                hashMap.put(LocationParams.PARA_COMMON_DIV, zsVar.a(LocationParams.PARA_COMMON_DIV));
                                hashMap.put(LocationParams.PARA_COMMON_DIC, zsVar.a(LocationParams.PARA_COMMON_DIC));
                                hashMap.put(LocationParams.PARA_COMMON_DIU, zsVar.a(LocationParams.PARA_COMMON_DIU));
                                hashMap.put(LocationParams.PARA_COMMON_DIU2, zsVar.a(LocationParams.PARA_COMMON_DIU2));
                                hashMap.put(LocationParams.PARA_COMMON_DIU3, zsVar.a(LocationParams.PARA_COMMON_DIU3));
                                hashMap.put("result", zsVar.a == null ? "" : String.valueOf(zsVar.a.getStatusCode()));
                                hashMap.put("host", "");
                                String str4 = "";
                                if (zsVar.d != null && !TextUtils.isEmpty(zsVar.d.ip_port)) {
                                    String[] split = zsVar.d.ip_port.split(":");
                                    if (split.length == 2) {
                                        str4 = split[0];
                                    }
                                }
                                hashMap.put(OnNativeInvokeListener.ARG_IP, str4);
                                String str5 = "";
                                if (zsVar.d != null && !TextUtils.isEmpty(zsVar.d.ip_port)) {
                                    String[] split2 = zsVar.d.ip_port.split(":");
                                    if (split2.length == 2) {
                                        str5 = split2[1];
                                    }
                                }
                                hashMap.put("port", str5);
                                String str6 = "";
                                if (zsVar.d != null) {
                                    str6 = zsVar.d.isSSL ? "1" : "0";
                                }
                                hashMap.put("isSSL", str6);
                                hashMap.put("isProxy", NetworkReachability.d() ? "1" : "0");
                                hashMap.put("isDNS", "");
                                hashMap.put("netType", zs.h());
                                String str7 = "";
                                if (zsVar.c != null) {
                                    str7 = String.valueOf(zsVar.c.h);
                                }
                                hashMap.put("retryTimes", str7);
                                hashMap.put("statusCode", zsVar.d());
                                hashMap.put("errorMsg", zsVar.e());
                                hashMap.put("url", zsVar.f());
                                String str8 = "";
                                if (zsVar.d != null) {
                                    str8 = String.valueOf(zsVar.d.dnsTime);
                                }
                                hashMap.put("dnsTime", str8);
                                hashMap.put("firstDataTime", zsVar.g());
                                hashMap.put("sendTime", zsVar.g());
                                String str9 = "";
                                if (zsVar.d != null) {
                                    str9 = String.valueOf(zsVar.d.sendSize);
                                }
                                hashMap.put("sendDataSize", str9);
                                String str10 = "";
                                if (zsVar.d != null) {
                                    str10 = String.valueOf(zsVar.d.recDataTime);
                                }
                                hashMap.put("recDataTime", str10);
                                String str11 = "";
                                if (zsVar.d != null) {
                                    str11 = String.valueOf(zsVar.d.totalSize);
                                }
                                hashMap.put("recDataSize", str11);
                                String str12 = "";
                                if (zsVar.d != null) {
                                    str12 = String.valueOf(zsVar.d.serverRT);
                                }
                                hashMap.put("serverRT", str12);
                                String str13 = "";
                                if (zsVar.d != null) {
                                    str13 = String.valueOf(zsVar.d.sendBeforeTime);
                                }
                                hashMap.put("waitingTime", str13);
                                hashMap.put("isSessionReuse", "");
                                String str14 = "";
                                if (zsVar.d != null) {
                                    str14 = String.valueOf(zsVar.d.oneWayTime_ANet);
                                }
                                hashMap.put("oneWayTime", str14);
                                hashMap.put("csid", zsVar.j());
                                String str15 = "";
                                if (zsVar.c != null) {
                                    str15 = String.valueOf(zsVar.c.l);
                                }
                                hashMap.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME, str15);
                                hashMap.put("method", zsVar.k());
                                HashMap hashMap2 = new HashMap();
                                zj.a(zj.a, hashMap2);
                                zj.a(zj.b, hashMap2);
                                zj.a(zj.c, hashMap2);
                                zj.a(zj.d, hashMap2);
                                hashMap.putAll(hashMap2);
                                zrVar.c = hashMap;
                                Application application2 = AMapAppGlobal.getApplication();
                                bpk bpk2 = bpk;
                                HashMap hashMap3 = new HashMap();
                                zs zsVar2 = new zs(application2, bpk2);
                                hashMap3.put("url", zsVar2.f());
                                hashMap3.put("method", zsVar2.k());
                                Object hashMap4 = zsVar2.b == null ? new HashMap() : zsVar2.b.getParams();
                                String str16 = "";
                                if (hashMap4 != null) {
                                    str16 = hashMap4.toString();
                                }
                                hashMap3.put("params", str16);
                                zrVar.a = hashMap3;
                                Application application3 = AMapAppGlobal.getApplication();
                                bpk bpk3 = bpk;
                                HashMap hashMap5 = new HashMap();
                                zs zsVar3 = new zs(application3, bpk3);
                                hashMap5.put("statusCode", zsVar3.d());
                                hashMap5.put("data", zsVar3.e());
                                zrVar.b = hashMap5;
                                zq.a().a(zrVar.toString());
                            } catch (Exception e) {
                                if (bno.a) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
                    return bpk;
                }
                return bpk;
            }
        }
        return bpk;
    }
}
