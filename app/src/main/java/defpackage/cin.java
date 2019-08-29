package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.agroup.entity.TeamInfo;
import com.autonavi.minimap.agroup.network.GroupNetworkUtil$1;
import com.autonavi.minimap.agroup.network.GroupNetworkUtil$2;
import com.autonavi.minimap.agroup.network.GroupNetworkUtil$3;
import com.autonavi.minimap.agroup.network.GroupNetworkUtil$4;
import com.autonavi.minimap.agroup.network.GroupNetworkUtil$5;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.server.aos.serverkey;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cin reason: default package */
/* compiled from: GroupNetworkUtil */
public class cin {
    /* access modifiers changed from: private */
    public static final String a = "cin";
    private static String b;

    /* renamed from: cin$a */
    /* compiled from: GroupNetworkUtil */
    public interface a {
        void a(String str);

        void a(String str, JsFunctionCallback jsFunctionCallback);
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(NetworkParam.getAosTsPollingHttpsUrl());
        sb.append("/");
        b = sb.toString();
        if (cih.a().b()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(NetworkParam.getAosTsPollingHttpsUrl());
            sb2.append("/");
            b = sb2.toString();
            return;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(NetworkParam.getAosTsPollingUrl());
        sb3.append("/");
        b = sb3.toString();
    }

    public static boolean a(String str, a aVar, JsFunctionCallback jsFunctionCallback) {
        String[] strArr;
        String b2 = b();
        StringBuilder sb = new StringBuilder("fetchTeamStatus teamNumber:");
        sb.append(str);
        sb.append(", uid:");
        sb.append(b2);
        cjo.a();
        HashMap hashMap = new HashMap();
        hashMap.put("channel", serverkey.getAosChannel());
        if (!TextUtils.isEmpty(str)) {
            hashMap.put("teamNumber", str);
        }
        if (a(b2)) {
            hashMap.put(Oauth2AccessToken.KEY_UID, b2);
        }
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        AosGetRequest aosGetRequest = new AosGetRequest();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(b);
        sb2.append("ws/tservice/team/user/status");
        aosGetRequest.setUrl(sb2.toString());
        if (!TextUtils.isEmpty(str) && a(b2)) {
            strArr = new String[]{"teamNumber", Oauth2AccessToken.KEY_UID, "timestamp"};
        } else if (!TextUtils.isEmpty(str)) {
            strArr = new String[]{"teamNumber", "timestamp"};
        } else if (a(b2)) {
            strArr = new String[]{Oauth2AccessToken.KEY_UID, "timestamp"};
        } else {
            strArr = new String[]{"timestamp"};
        }
        aosGetRequest.addSignParams(Arrays.asList(strArr));
        aosGetRequest.addReqParams(hashMap);
        yq.a();
        yq.a((AosRequest) aosGetRequest, (AosResponseCallback<T>) new GroupNetworkUtil$1<T>(aVar, jsFunctionCallback));
        return true;
    }

    public static void a(String str, int i, defpackage.cik.a aVar) {
        Map map;
        StringBuilder sb = new StringBuilder("requestJoinGroup teamNumber:");
        sb.append(str);
        sb.append(", type:");
        sb.append(i);
        cjo.a();
        Context appContext = AMapPageUtil.getAppContext();
        if (appContext == null || aaw.c(appContext)) {
            String b2 = b();
            if (!a(b2)) {
                cjo.b();
                map = null;
            } else {
                String a2 = cju.a(LocationInstrument.getInstance().getLatestPosition(5));
                HashMap hashMap = new HashMap();
                hashMap.put("teamNumber", str);
                hashMap.put(Oauth2AccessToken.KEY_UID, b2);
                hashMap.put("type", String.valueOf(i));
                hashMap.put("locInfo", a2);
                hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
                StringBuilder sb2 = new StringBuilder("getJoinGroupParam request teamNumber:");
                sb2.append(str);
                sb2.append(", uid:");
                sb2.append(b2);
                sb2.append(", type:");
                sb2.append(i);
                cjo.a();
                map = hashMap;
            }
            if (map != null) {
                AosGetRequest aosGetRequest = new AosGetRequest();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(b);
                sb3.append("ws/tservice/team/join");
                aosGetRequest.setUrl(sb3.toString());
                aosGetRequest.addSignParam("teamNumber");
                aosGetRequest.addSignParam(Oauth2AccessToken.KEY_UID);
                aosGetRequest.addSignParam("type");
                aosGetRequest.addSignParam("locInfo");
                aosGetRequest.addSignParam("timestamp");
                aosGetRequest.addReqParams(map);
                yq.a();
                yq.a((AosRequest) aosGetRequest, (AosResponseCallback<T>) new GroupNetworkUtil$2<T>(aVar));
                return;
            }
            return;
        }
        ToastHelper.showToast(appContext.getString(R.string.network_error));
    }

    public static void a(String str, defpackage.cik.a aVar) {
        Map map;
        StringBuilder sb = new StringBuilder("requestChangeTeam(");
        sb.append(str);
        sb.append(")");
        cjo.a();
        Context appContext = AMapPageUtil.getAppContext();
        if (appContext == null || aaw.c(appContext)) {
            String b2 = b();
            if (!a(b2)) {
                map = null;
            } else {
                String a2 = cju.a(LocationInstrument.getInstance().getLatestPosition(5));
                HashMap hashMap = new HashMap();
                hashMap.put("teamNumber", str);
                hashMap.put(Oauth2AccessToken.KEY_UID, b2);
                hashMap.put("locInfo", a2);
                hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
                map = hashMap;
            }
            if (map != null) {
                AosGetRequest aosGetRequest = new AosGetRequest();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(b);
                sb2.append("ws/tservice/team/changeTeam");
                aosGetRequest.setUrl(sb2.toString());
                aosGetRequest.addSignParam("teamNumber");
                aosGetRequest.addSignParam(Oauth2AccessToken.KEY_UID);
                aosGetRequest.addSignParam("locInfo");
                aosGetRequest.addSignParam("timestamp");
                aosGetRequest.addReqParams(map);
                yq.a();
                yq.a((AosRequest) aosGetRequest, (AosResponseCallback<T>) new GroupNetworkUtil$3<T>(aVar));
                return;
            }
            return;
        }
        ToastHelper.showToast(appContext.getString(R.string.network_error));
    }

    public static boolean a(a aVar, JsFunctionCallback jsFunctionCallback) {
        cjo.a();
        HashMap hashMap = new HashMap();
        cjt a2 = cjt.a();
        HashMap hashMap2 = null;
        if (a2 != null) {
            String b2 = b();
            if (!a(b2)) {
                cjo.a();
            } else {
                TeamInfo c = a2.c();
                if (c != null) {
                    String str = c.teamId;
                    if (!TextUtils.isEmpty(str)) {
                        hashMap.put("channel", serverkey.getAosChannel());
                        hashMap.put(Oauth2AccessToken.KEY_UID, b2);
                        hashMap.put("teamId", str);
                        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
                        hashMap2 = hashMap;
                    }
                }
            }
        }
        if (hashMap2 == null) {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback("");
            }
            return false;
        }
        AosGetRequest aosGetRequest = new AosGetRequest();
        StringBuilder sb = new StringBuilder();
        sb.append(b);
        sb.append("ws/tservice/team/info/get");
        aosGetRequest.setUrl(sb.toString());
        aosGetRequest.addSignParam("teamId");
        aosGetRequest.addSignParam(Oauth2AccessToken.KEY_UID);
        aosGetRequest.addSignParam("timestamp");
        aosGetRequest.addReqParams(hashMap2);
        yq.a();
        yq.a((AosRequest) aosGetRequest, (AosResponseCallback<T>) new GroupNetworkUtil$4<T>(aVar, jsFunctionCallback));
        return true;
    }

    public static void a(String str, dku dku) {
        cjo.a();
        cjt a2 = cjt.a();
        if (a2 != null) {
            HashMap hashMap = new HashMap();
            TeamInfo c = a2.c();
            HashMap hashMap2 = null;
            if (c != null && !TextUtils.isEmpty(c.teamId)) {
                String b2 = b();
                if (!a(b2)) {
                    cjo.a();
                } else {
                    String a3 = cju.a(LocationInstrument.getInstance().getLatestPosition(5));
                    hashMap.put("teamId", c.teamId);
                    hashMap.put(Oauth2AccessToken.KEY_UID, b2);
                    hashMap.put("locInfo", a3);
                    hashMap.put("teamStamp", a2.d());
                    hashMap.put("memberStamp", a2.e());
                    hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
                    hashMap2 = hashMap;
                }
            }
            if (hashMap2 != null) {
                cjo.a();
                AosGetRequest aosGetRequest = new AosGetRequest();
                StringBuilder sb = new StringBuilder();
                sb.append(b);
                sb.append("ws/tservice/team/user/location/report");
                aosGetRequest.setUrl(sb.toString());
                aosGetRequest.addSignParam("teamId");
                aosGetRequest.addSignParam(Oauth2AccessToken.KEY_UID);
                aosGetRequest.addSignParam("locInfo");
                aosGetRequest.addSignParam("teamStamp");
                aosGetRequest.addSignParam("memberStamp");
                aosGetRequest.addSignParam("timestamp");
                aosGetRequest.addReqParams(hashMap2);
                yq.a();
                yq.a((AosRequest) aosGetRequest, (AosResponseCallback<T>) new GroupNetworkUtil$5<T>(dku, str));
            }
        }
    }

    public static String a(cjt cjt) {
        TeamInfo teamInfo;
        JSONObject jSONObject = new JSONObject();
        if (cjt == null) {
            teamInfo = null;
        } else {
            teamInfo = cjt.c();
        }
        if (teamInfo == null || TextUtils.isEmpty(teamInfo.teamId)) {
            return null;
        }
        String b2 = b();
        if (!a(b2)) {
            return null;
        }
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        try {
            jSONObject.put("channel", serverkey.getAosChannel());
            jSONObject.put(Oauth2AccessToken.KEY_UID, b2);
            jSONObject.put("teamId", teamInfo.teamId);
            if (latestPosition != null) {
                jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, latestPosition.getLongitude());
                jSONObject.put("lat", latestPosition.getLatitude());
            } else {
                jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, 0.0d);
                jSONObject.put("lat", 0.0d);
            }
            jSONObject.put("teamStamp", cjt.d());
            jSONObject.put("memberStamp", cjt.e());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    private static boolean a(String str) {
        return !TextUtils.isEmpty(str) && !str.equals("public");
    }

    private static String b() {
        cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
        return cuh != null ? cuh.k() : "";
    }
}
