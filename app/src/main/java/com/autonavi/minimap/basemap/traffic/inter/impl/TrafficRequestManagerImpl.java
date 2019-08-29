package com.autonavi.minimap.basemap.traffic.inter.impl;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosMultipartRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.archive.ArchiveRequestHolder;
import com.autonavi.minimap.archive.param.TrafficeventCommentRequest;
import com.autonavi.minimap.archive.param.TrafficeventUpdateRequest;
import com.autonavi.minimap.basemap.traffic.inter.ITrafficRequestManager;
import com.autonavi.minimap.transfer.TransferRequestHolder;
import com.autonavi.minimap.transfer.param.AosStateRequest;
import com.autonavi.server.TrafficAosUICallback;
import com.taobao.wireless.security.sdk.indiekit.IndieKitDefine;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.util.LinkedHashMap;

public class TrafficRequestManagerImpl implements ITrafficRequestManager {
    private final Context a = AMapAppGlobal.getApplication();

    @SuppressFBWarnings({"DMI_HARDCODED_ABSOLUTE_FILENAME"})
    public final AosRequest a(cso cso, TrafficAosUICallback trafficAosUICallback) {
        TrafficeventUpdateRequest trafficeventUpdateRequest = new TrafficeventUpdateRequest();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        trafficeventUpdateRequest.k = cso.b;
        trafficeventUpdateRequest.l = cso.c;
        trafficeventUpdateRequest.x = cso.d;
        trafficeventUpdateRequest.y = cso.e;
        if (!TextUtils.isEmpty(cso.f)) {
            trafficeventUpdateRequest.v = cso.f;
        }
        if (!TextUtils.isEmpty(cso.g)) {
            trafficeventUpdateRequest.w = cso.g;
        }
        if (!TextUtils.isEmpty(cso.h)) {
            trafficeventUpdateRequest.z = cso.h;
        }
        if (!TextUtils.isEmpty(cso.i)) {
            trafficeventUpdateRequest.A = cso.i;
        }
        if (!TextUtils.isEmpty(cso.j)) {
            trafficeventUpdateRequest.B = cso.j;
        }
        if (!TextUtils.isEmpty(cso.m)) {
            trafficeventUpdateRequest.J = cso.m;
        }
        if (!TextUtils.isEmpty(cso.n)) {
            trafficeventUpdateRequest.E = Integer.parseInt(cso.n);
        }
        if (!TextUtils.isEmpty(cso.p)) {
            trafficeventUpdateRequest.j = cso.p;
        }
        if (!TextUtils.isEmpty(cso.q)) {
            String[] split = cso.q.split(",");
            while (cso.q.split(",").length < 3) {
                StringBuilder sb = new StringBuilder();
                sb.append(cso.q);
                sb.append(",");
                sb.append(split[split.length - 1]);
                cso.q = sb.toString();
            }
            trafficeventUpdateRequest.m = cso.q;
        }
        if (!TextUtils.isEmpty(cso.r)) {
            String[] split2 = cso.r.split(",");
            while (cso.r.split(",").length < 3) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(cso.r);
                sb2.append(",");
                sb2.append(split2[split2.length - 1]);
                cso.r = sb2.toString();
            }
            trafficeventUpdateRequest.n = cso.r;
        }
        if (!TextUtils.isEmpty(cso.u)) {
            String[] split3 = cso.u.split(",");
            while (cso.u.split(",").length < 3) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(cso.u);
                sb3.append(",");
                sb3.append(split3[split3.length - 1]);
                cso.u = sb3.toString();
            }
            trafficeventUpdateRequest.s = cso.u;
        }
        if (!TextUtils.isEmpty(cso.v)) {
            String[] split4 = cso.v.split(",");
            while (cso.v.split(",").length < 3) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(cso.v);
                sb4.append(",");
                sb4.append(split4[split4.length - 1]);
                cso.v = sb4.toString();
            }
            trafficeventUpdateRequest.t = cso.v;
        }
        if (!TextUtils.isEmpty(cso.w)) {
            String[] split5 = cso.w.split(",");
            while (cso.w.split(",").length < 3) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append(cso.w);
                sb5.append(",");
                sb5.append(split5[split5.length - 1]);
                cso.w = sb5.toString();
            }
            trafficeventUpdateRequest.u = cso.w;
        }
        if (!TextUtils.isEmpty(cso.x)) {
            trafficeventUpdateRequest.F = cso.x;
        }
        if (!TextUtils.isEmpty(cso.y)) {
            trafficeventUpdateRequest.G = cso.y;
        }
        if (!TextUtils.isEmpty(cso.A)) {
            trafficeventUpdateRequest.I = cso.A;
        }
        StringBuilder sb6 = new StringBuilder();
        sb6.append(cso.o);
        trafficeventUpdateRequest.K = sb6.toString();
        linkedHashMap.put("client_network_class", NetworkReachability.c().toString());
        trafficeventUpdateRequest.p = cso.B;
        trafficeventUpdateRequest.o = cso.C;
        if (!TextUtils.isEmpty(cso.G)) {
            trafficeventUpdateRequest.O = cso.G;
        }
        try {
            trafficeventUpdateRequest.g = Integer.parseInt(cso.a);
            if (!TextUtils.isEmpty(cso.s)) {
                trafficeventUpdateRequest.q = Integer.parseInt(cso.s);
            }
            if (!TextUtils.isEmpty(cso.t)) {
                trafficeventUpdateRequest.r = Integer.parseInt(cso.t);
            }
            if (!TextUtils.isEmpty(cso.z)) {
                trafficeventUpdateRequest.H = Integer.parseInt(cso.z);
            }
            if (!TextUtils.isEmpty(cso.F)) {
                trafficeventUpdateRequest.N = Integer.parseInt(cso.F);
            }
            if (!TextUtils.isEmpty(cso.H)) {
                trafficeventUpdateRequest.P = Integer.parseInt(cso.H);
            }
        } catch (NumberFormatException e) {
            AMapLog.d("TrafficeRequest", e.getMessage());
        }
        trafficeventUpdateRequest.h = cso.I;
        trafficeventUpdateRequest.i = String.valueOf(cso.J);
        trafficeventUpdateRequest.addReqParams(linkedHashMap);
        trafficeventUpdateRequest.a((String) "file", cso.k);
        trafficeventUpdateRequest.a((String) "audio", cso.l);
        trafficeventUpdateRequest.a((String) "fake", new File("/fake"));
        ArchiveRequestHolder.getInstance().sendTrafficeventUpdate(trafficeventUpdateRequest, trafficAosUICallback);
        return trafficeventUpdateRequest;
    }

    public final AosRequest a(TrafficAosUICallback trafficAosUICallback) {
        AosStateRequest aosStateRequest = new AosStateRequest();
        aosStateRequest.b = NetworkParam.getDiu();
        aosStateRequest.c = NetworkParam.getDiv();
        TransferRequestHolder.getInstance().sendAosState(aosStateRequest, trafficAosUICallback);
        return aosStateRequest;
    }

    public static AosRequest a(String str, int i, String str2, String str3, String str4, TrafficAosUICallback trafficAosUICallback) {
        TrafficeventCommentRequest trafficeventCommentRequest = new TrafficeventCommentRequest();
        trafficeventCommentRequest.b = str;
        trafficeventCommentRequest.f = i;
        trafficeventCommentRequest.c = str2;
        trafficeventCommentRequest.d = str3;
        trafficeventCommentRequest.e = str4;
        ArchiveRequestHolder.getInstance().sendTrafficeventComment(trafficeventCommentRequest, trafficAosUICallback);
        return trafficeventCommentRequest;
    }

    public final AosRequest b(cso cso, TrafficAosUICallback trafficAosUICallback) {
        AosMultipartRequest aosMultipartRequest = new AosMultipartRequest();
        StringBuilder sb = new StringBuilder();
        sb.append(aaf.b(ConfigerHelper.AOS_SNS_URL_KEY));
        sb.append("ws/archive/traffic_alarm");
        aosMultipartRequest.setUrl(sb.toString());
        aosMultipartRequest.addSignParam("channel");
        aosMultipartRequest.addSignParam(LocationParams.PARA_FLP_AUTONAVI_LON);
        aosMultipartRequest.addSignParam("lat");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("precision", String.valueOf(cso.B));
        linkedHashMap.put(LocationParams.PARA_FLP_AUTONAVI_LON, cso.b);
        linkedHashMap.put("lat", cso.c);
        linkedHashMap.put("is_hurt", String.valueOf(cso.E));
        linkedHashMap.put("type", String.valueOf(cso.D));
        linkedHashMap.put(IndieKitDefine.SG_KEY_INDIE_KIT_USERNAME, cso.p);
        linkedHashMap.put("mobile", cso.g);
        aosMultipartRequest.addReqParams(linkedHashMap);
        aosMultipartRequest.a((String) "images", cso.k);
        yq.a();
        yq.a((AosRequest) aosMultipartRequest, (AosResponseCallback<T>) trafficAosUICallback);
        return aosMultipartRequest;
    }
}
