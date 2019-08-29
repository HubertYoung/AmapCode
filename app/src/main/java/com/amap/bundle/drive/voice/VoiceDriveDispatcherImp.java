package com.amap.bundle.drive.voice;

import android.text.TextUtils;
import android.util.Pair;
import com.amap.bundle.drive.voice.traffic.VoiceTrafficManger;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.bundle.voiceservice.dispatch.IVoiceDispatchMethod;
import com.amap.bundle.voiceservice.dispatch.IVoiceDriveDispatcher;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.common.model.RouteHeaderModel;
import com.autonavi.common.model.RouteHeaderModel.IDealVoiceAddMidPois;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VoiceDriveDispatcherImp implements IVoiceDriveDispatcher {
    asv a = new asv() {
        public final void a(int i, String str) {
            if (i == 10000) {
                try {
                    int optInt = new JSONObject(str).optInt("token");
                    if (VoiceDriveDispatcherImp.this.d == null) {
                        VoiceDriveDispatcherImp.this.d = sa.b(str);
                    }
                    if (VoiceDriveDispatcherImp.this.e == null) {
                        VoiceDriveDispatcherImp.this.e = sa.c(str);
                    }
                    if (VoiceDriveDispatcherImp.this.a(optInt, VoiceDriveDispatcherImp.this.a(sa.d(str)))) {
                        VoiceDriveDispatcherImp.this.d = null;
                        VoiceDriveDispatcherImp.this.e = null;
                        VoiceDriveDispatcherImp.this.f = null;
                        VoiceDriveDispatcherImp.this.i = null;
                        VoiceDriveDispatcherImp.this.g = -1;
                        VoiceDriveDispatcherImp.this.h = DriveUtil.getLastRoutingChoice();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Throwable th) {
                    VoiceDriveDispatcherImp.this.d = null;
                    VoiceDriveDispatcherImp.this.e = null;
                    VoiceDriveDispatcherImp.this.f = null;
                    VoiceDriveDispatcherImp.this.i = null;
                    VoiceDriveDispatcherImp.this.g = -1;
                    VoiceDriveDispatcherImp.this.h = DriveUtil.getLastRoutingChoice();
                    throw th;
                }
                VoiceDriveDispatcherImp.this.d = null;
                VoiceDriveDispatcherImp.this.e = null;
                VoiceDriveDispatcherImp.this.f = null;
                VoiceDriveDispatcherImp.this.i = null;
                VoiceDriveDispatcherImp.this.g = -1;
                VoiceDriveDispatcherImp.this.h = DriveUtil.getLastRoutingChoice();
            }
        }
    };
    private aig b = null;
    private aij c = null;
    /* access modifiers changed from: private */
    public POI d = null;
    /* access modifiers changed from: private */
    public POI e = null;
    /* access modifiers changed from: private */
    public ArrayList<Pair<Integer, POI>> f = null;
    /* access modifiers changed from: private */
    public int g = 0;
    /* access modifiers changed from: private */
    public String h = DriveUtil.getLastRoutingChoice();
    /* access modifiers changed from: private */
    public String i;

    class a implements rz {
        private int b;
        private String c;

        public a(int i, String str) {
            this.b = i;
            this.c = str;
        }

        public final void a() {
            VoiceDriveDispatcherImp.this.requestRoute(this.b, this.c);
        }

        public final void b() {
            sa.a(this.b, 10003);
        }
    }

    @IVoiceDispatchMethod(methodName = "handleFactoryVoiceCommand")
    public void handleFactoryVoiceCommand(int i2, String str) {
        AMapLog.d("VoiceDriveDispatcherImp", "handleVoiceCommand json = ".concat(String.valueOf(str)));
        try {
            JSONObject jSONObject = new JSONObject(str);
            jSONObject.optJSONObject("paramStr");
            StringBuilder sb = new StringBuilder("handleVoiceCommand11 paramobj.address = ");
            sb.append(jSONObject.optString("address"));
            AMapLog.d("VoiceDriveDispatcherImp", sb.toString());
        } catch (JSONException unused) {
            AMapLog.d("VoiceDriveDispatcherImp", "handleVoiceCommand json err");
        }
    }

    @IVoiceDispatchMethod(methodName = "handleVoiceCommand")
    public void handleVoiceCommand(String str, String str2) {
        Object obj;
        AMapLog.d("VoiceDriveDispatcherImp", "handleVoiceCommand json = ".concat(String.valueOf(str2)));
        try {
            JSONObject jSONObject = new JSONObject(str2);
            JSONObject optJSONObject = jSONObject.optJSONObject("voiceCommandResponse");
            jSONObject.optJSONObject("voiceAbility");
            String str3 = null;
            if (optJSONObject != null) {
                str3 = optJSONObject.optString("operate");
                obj = optJSONObject.optString("type");
            } else {
                obj = null;
            }
            int optInt = jSONObject.optInt("token_id");
            JSONObject jSONObject2 = new JSONObject();
            if (TextUtils.equals(str3, "setRouteParams")) {
                jSONObject2.put("params", obj);
                setRouteParamsInNavi(optInt, jSONObject2.toString());
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        ToastHelper.showToast("handleVoiceCommand json=".concat(String.valueOf(str2)), 5);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0050, code lost:
        r2 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(int r9, java.util.ArrayList<com.autonavi.common.model.POI> r10) {
        /*
            r8 = this;
            r0 = 0
            r1 = 1
            if (r10 == 0) goto L_0x005f
            int r2 = r10.size()
            if (r2 <= 0) goto L_0x005f
            r2 = 0
            r3 = 0
        L_0x000c:
            int r4 = r10.size()
            if (r2 >= r4) goto L_0x0050
            java.lang.Object r4 = r10.get(r2)
            com.autonavi.common.model.POI r4 = (com.autonavi.common.model.POI) r4
            com.autonavi.common.model.POI r5 = r8.d
            if (r5 == 0) goto L_0x0026
            com.autonavi.common.model.POI r5 = r8.d
            boolean r5 = defpackage.bnx.a(r5, r4)
            if (r5 == 0) goto L_0x0026
        L_0x0024:
            r2 = 1
            goto L_0x0051
        L_0x0026:
            com.autonavi.common.model.POI r5 = r8.e
            if (r5 == 0) goto L_0x0033
            com.autonavi.common.model.POI r5 = r8.e
            boolean r5 = defpackage.bnx.a(r5, r4)
            if (r5 == 0) goto L_0x0033
            goto L_0x0024
        L_0x0033:
            int r2 = r2 + 1
            r5 = r2
        L_0x0036:
            int r6 = r10.size()
            if (r5 >= r6) goto L_0x004d
            java.lang.Object r6 = r10.get(r5)
            com.autonavi.common.model.POI r6 = (com.autonavi.common.model.POI) r6
            boolean r6 = defpackage.bnx.a(r4, r6)
            if (r6 == 0) goto L_0x004a
            r3 = 1
            goto L_0x004d
        L_0x004a:
            int r5 = r5 + 1
            goto L_0x0036
        L_0x004d:
            if (r3 != 0) goto L_0x0050
            goto L_0x000c
        L_0x0050:
            r2 = 0
        L_0x0051:
            r4 = 10010(0x271a, float:1.4027E-41)
            if (r2 == 0) goto L_0x0059
            defpackage.sa.a(r9, r4)
            return r1
        L_0x0059:
            if (r3 == 0) goto L_0x0077
            defpackage.sa.a(r9, r4)
            return r1
        L_0x005f:
            com.autonavi.common.model.POI r2 = r8.d
            if (r2 == 0) goto L_0x0077
            com.autonavi.common.model.POI r2 = r8.e
            if (r2 == 0) goto L_0x0077
            com.autonavi.common.model.POI r2 = r8.d
            com.autonavi.common.model.POI r3 = r8.e
            boolean r2 = defpackage.bnx.a(r2, r3)
            if (r2 == 0) goto L_0x0077
            r10 = 10009(0x2719, float:1.4026E-41)
            defpackage.sa.a(r9, r10)
            return r1
        L_0x0077:
            com.autonavi.bundle.routecommon.model.RouteType r5 = com.autonavi.bundle.routecommon.model.RouteType.CAR
            int r2 = r8.g
            if (r2 != r1) goto L_0x00d5
            com.autonavi.common.model.POI r0 = r8.e
            if (r0 == 0) goto L_0x00a2
            java.lang.String r0 = "我的位置"
            com.autonavi.common.model.POI r2 = r8.e
            java.lang.String r2 = r2.getName()
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x00a2
            java.util.ArrayList<android.util.Pair<java.lang.Integer, com.autonavi.common.model.POI>> r0 = r8.f
            if (r0 == 0) goto L_0x009c
            java.util.ArrayList<android.util.Pair<java.lang.Integer, com.autonavi.common.model.POI>> r0 = r8.f
            int r0 = r0.size()
            if (r0 != 0) goto L_0x00a2
        L_0x009c:
            r10 = 10005(0x2715, float:1.402E-41)
            defpackage.sa.a(r9, r10)
            return r1
        L_0x00a2:
            com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            r2 = 5
            com.autonavi.common.model.GeoPoint r0 = r0.getLatestPosition(r2)
            if (r0 != 0) goto L_0x00b8
            com.amap.bundle.drive.voice.VoiceDriveDispatcherImp$a r10 = new com.amap.bundle.drive.voice.VoiceDriveDispatcherImp$a
            java.lang.String r0 = r8.i
            r10.<init>(r9, r0)
            defpackage.sa.a(r10)
            return r1
        L_0x00b8:
            com.autonavi.common.model.POI r2 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            r8.d = r2
            com.autonavi.common.model.POI r2 = r8.d
            r2.setPoint(r0)
            com.autonavi.common.model.POI r0 = r8.d
            java.lang.String r2 = "我的位置"
            r0.setName(r2)
            com.autonavi.common.model.POI r0 = r8.d
            com.autonavi.common.model.POI r2 = r8.e
            java.lang.String r3 = r8.h
            defpackage.sa.a(r0, r10, r2, r3, r9)
            return r1
        L_0x00d5:
            int r1 = r8.g
            if (r1 != 0) goto L_0x00e5
            com.autonavi.common.model.POI r2 = r8.d
            com.autonavi.common.model.POI r3 = r8.e
            java.lang.String r6 = r8.h
            r4 = r10
            r7 = r9
            defpackage.sa.a(r2, r3, r4, r5, r6, r7)
            goto L_0x00ea
        L_0x00e5:
            r10 = 10001(0x2711, float:1.4014E-41)
            defpackage.sa.a(r9, r10)
        L_0x00ea:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.voice.VoiceDriveDispatcherImp.a(int, java.util.ArrayList):boolean");
    }

    /* access modifiers changed from: private */
    @NotNull
    public ArrayList<POI> a(List<Pair<Integer, POI>> list) {
        POI[] poiArr = new POI[3];
        if (list != null && list.size() > 0) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                Pair pair = list.get(i2);
                poiArr[((Integer) pair.first).intValue()] = (POI) pair.second;
            }
        }
        if (this.f != null && this.f.size() > 0) {
            for (int i3 = 0; i3 < this.f.size(); i3++) {
                Pair pair2 = this.f.get(i3);
                poiArr[((Integer) pair2.first).intValue()] = (POI) pair2.second;
            }
        }
        ArrayList<POI> arrayList = new ArrayList<>();
        for (int i4 = 0; i4 < 3; i4++) {
            POI poi = poiArr[i4];
            if (poi != null) {
                arrayList.add(poi);
            }
        }
        return arrayList;
    }

    private static void a() {
        PageBundle pageBundle = new PageBundle();
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage((String) "amap.basemap.action.favorite_page", pageBundle);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(11:1|2|3|4|5|6|7|8|(3:10|(2:12|(2:14|15))(2:16|(2:18|(2:20|21))(2:22|(2:24|(2:26|27)(1:28))(2:29|(4:31|(1:33)(1:34)|35|(2:37|38)(2:39|(2:41|42)(3:43|(2:45|(1:47))|48)))(2:50|(1:52)(2:53|54)))))|49)(1:55)|56|(4:62|(4:64|(2:66|(2:68|69))(2:71|(2:73|(2:75|76))(2:77|(2:79|(2:81|82)(1:83))(2:84|(4:86|(1:88)(1:89)|90|(2:92|93)(2:94|(2:96|97)(4:98|(1:102)|103|104)))(2:105|(1:107)(2:108|109)))))|70|104)(1:110)|111|(6:117|(1:171)(3:121|(2:122|(5:124|(2:126|(2:208|128))(2:130|(2:132|(2:203|134))(2:135|(2:137|(2:207|139)(3:140|(2:160|210)(1:211)|161))(2:141|(2:143|(2:204|145)(2:146|(2:209|148)(5:149|(1:154)(1:153)|155|(0)(0)|161)))(2:156|(3:158|(0)(0)|161)(1:206)))))|129|(0)(0)|161)(2:205|163))|(1:165)(2:166|(2:168|169)(1:170)))|172|(1:174)(2:175|(2:177|178)(2:179|(2:181|182)(1:183)))|184|(2:190|191)(1:(1:(2:194|195)(2:196|197))(2:198|199)))(2:115|116))(2:60|61)) */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x0485, code lost:
        r2 = -2;
        r7 = 10001;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x002b */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x046f A[Catch:{ JSONException -> 0x050a }] */
    /* JADX WARNING: Removed duplicated region for block: B:211:0x047d A[SYNTHETIC] */
    @com.amap.bundle.voiceservice.dispatch.IVoiceDispatchMethod(methodName = "requestRoute")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void requestRoute(int r25, java.lang.String r26) {
        /*
            r24 = this;
            r0 = r24
            r1 = r25
            r2 = r26
            r3 = 0
            r0.d = r3     // Catch:{ JSONException -> 0x050a }
            r0.e = r3     // Catch:{ JSONException -> 0x050a }
            r0.f = r3     // Catch:{ JSONException -> 0x050a }
            r4 = 0
            r0.g = r4     // Catch:{ JSONException -> 0x050a }
            r0.i = r2     // Catch:{ JSONException -> 0x050a }
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x050a }
            r5.<init>(r2)     // Catch:{ JSONException -> 0x050a }
            boolean r6 = defpackage.sa.c()     // Catch:{ JSONException -> 0x050a }
            r7 = 10001(0x2711, float:1.4014E-41)
            java.lang.String r8 = "requestMode"
            java.lang.String r8 = r5.optString(r8)     // Catch:{ Exception -> 0x002b }
            int r8 = java.lang.Integer.parseInt(r8)     // Catch:{ Exception -> 0x002b }
            r0.g = r8     // Catch:{ Exception -> 0x002b }
            goto L_0x002e
        L_0x002b:
            defpackage.sa.a(r1, r7)     // Catch:{ JSONException -> 0x050a }
        L_0x002e:
            java.lang.String r8 = "startPoi"
            org.json.JSONObject r8 = r5.optJSONObject(r8)     // Catch:{ JSONException -> 0x050a }
            r10 = 5
            r11 = 10013(0x271d, float:1.4031E-41)
            r12 = 10012(0x271c, float:1.403E-41)
            if (r8 == 0) goto L_0x0183
            java.lang.String r14 = "poiType"
            java.lang.String r14 = r8.optString(r14)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r15 = "1"
            boolean r15 = r15.equals(r14)     // Catch:{ JSONException -> 0x050a }
            if (r15 == 0) goto L_0x005b
            com.autonavi.common.model.POI r8 = defpackage.sa.a()     // Catch:{ JSONException -> 0x050a }
            r0.d = r8     // Catch:{ JSONException -> 0x050a }
            com.autonavi.common.model.POI r8 = r0.d     // Catch:{ JSONException -> 0x050a }
            if (r8 != 0) goto L_0x0171
            a()     // Catch:{ JSONException -> 0x050a }
            defpackage.sa.a(r1, r12)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x005b:
            java.lang.String r15 = "2"
            boolean r15 = r15.equals(r14)     // Catch:{ JSONException -> 0x050a }
            if (r15 == 0) goto L_0x0074
            com.autonavi.common.model.POI r8 = defpackage.sa.b()     // Catch:{ JSONException -> 0x050a }
            r0.d = r8     // Catch:{ JSONException -> 0x050a }
            com.autonavi.common.model.POI r8 = r0.d     // Catch:{ JSONException -> 0x050a }
            if (r8 != 0) goto L_0x0171
            a()     // Catch:{ JSONException -> 0x050a }
            defpackage.sa.a(r1, r11)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x0074:
            java.lang.String r15 = "3"
            boolean r15 = r15.equals(r14)     // Catch:{ JSONException -> 0x050a }
            if (r15 == 0) goto L_0x00a4
            com.autonavi.sdk.location.LocationInstrument r8 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ JSONException -> 0x050a }
            com.autonavi.common.model.GeoPoint r8 = r8.getLatestPosition(r10)     // Catch:{ JSONException -> 0x050a }
            if (r8 != 0) goto L_0x008f
            com.amap.bundle.drive.voice.VoiceDriveDispatcherImp$a r3 = new com.amap.bundle.drive.voice.VoiceDriveDispatcherImp$a     // Catch:{ JSONException -> 0x050a }
            r3.<init>(r1, r2)     // Catch:{ JSONException -> 0x050a }
            defpackage.sa.a(r3)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x008f:
            com.autonavi.common.model.POI r15 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()     // Catch:{ JSONException -> 0x050a }
            r0.d = r15     // Catch:{ JSONException -> 0x050a }
            com.autonavi.common.model.POI r15 = r0.d     // Catch:{ JSONException -> 0x050a }
            r15.setPoint(r8)     // Catch:{ JSONException -> 0x050a }
            com.autonavi.common.model.POI r8 = r0.d     // Catch:{ JSONException -> 0x050a }
            java.lang.String r15 = "我的位置"
            r8.setName(r15)     // Catch:{ JSONException -> 0x050a }
            goto L_0x0171
        L_0x00a4:
            java.lang.String r15 = "0"
            boolean r15 = r15.equals(r14)     // Catch:{ JSONException -> 0x050a }
            if (r15 == 0) goto L_0x0173
            java.lang.String r15 = "poiId"
            java.lang.String r15 = r8.optString(r15)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r3 = "poiName"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r4 = "lon"
            java.lang.String r4 = r8.optString(r4)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r13 = "lat"
            java.lang.String r13 = r8.optString(r13)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r10 = "entry_lon"
            java.lang.String r10 = r8.optString(r10)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r11 = "entry_lat"
            java.lang.String r8 = r8.optString(r11)     // Catch:{ JSONException -> 0x050a }
            if (r3 != 0) goto L_0x00d5
            java.lang.String r3 = ""
            goto L_0x00d9
        L_0x00d5:
            java.lang.String r3 = r3.trim()     // Catch:{ JSONException -> 0x050a }
        L_0x00d9:
            java.lang.Double r11 = java.lang.Double.valueOf(r4)     // Catch:{ JSONException -> 0x050a }
            r16 = r10
            double r9 = r11.doubleValue()     // Catch:{ JSONException -> 0x050a }
            java.lang.Double r11 = java.lang.Double.valueOf(r13)     // Catch:{ JSONException -> 0x050a }
            r17 = r13
            double r12 = r11.doubleValue()     // Catch:{ JSONException -> 0x050a }
            boolean r9 = defpackage.sa.b(r9, r12)     // Catch:{ JSONException -> 0x050a }
            if (r9 == 0) goto L_0x00f7
            defpackage.sa.a(r1, r7)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x00f7:
            java.lang.Double r9 = java.lang.Double.valueOf(r4)     // Catch:{ JSONException -> 0x050a }
            double r9 = r9.doubleValue()     // Catch:{ JSONException -> 0x050a }
            r11 = r17
            java.lang.Double r12 = java.lang.Double.valueOf(r11)     // Catch:{ JSONException -> 0x050a }
            double r12 = r12.doubleValue()     // Catch:{ JSONException -> 0x050a }
            boolean r9 = defpackage.sa.a(r9, r12)     // Catch:{ JSONException -> 0x050a }
            if (r9 != 0) goto L_0x0115
            r9 = 10004(0x2714, float:1.4019E-41)
            defpackage.sa.a(r1, r9)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x0115:
            com.autonavi.common.model.POI r9 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()     // Catch:{ JSONException -> 0x050a }
            r0.d = r9     // Catch:{ JSONException -> 0x050a }
            r9 = r16
            java.lang.Double r10 = java.lang.Double.valueOf(r9)     // Catch:{ JSONException -> 0x050a }
            double r12 = r10.doubleValue()     // Catch:{ JSONException -> 0x050a }
            java.lang.Double r10 = java.lang.Double.valueOf(r8)     // Catch:{ JSONException -> 0x050a }
            r18 = r8
            double r7 = r10.doubleValue()     // Catch:{ JSONException -> 0x050a }
            boolean r7 = defpackage.sa.b(r12, r7)     // Catch:{ JSONException -> 0x050a }
            if (r7 != 0) goto L_0x0167
            java.lang.Double r7 = java.lang.Double.valueOf(r9)     // Catch:{ JSONException -> 0x050a }
            double r7 = r7.doubleValue()     // Catch:{ JSONException -> 0x050a }
            r10 = r18
            java.lang.Double r12 = java.lang.Double.valueOf(r10)     // Catch:{ JSONException -> 0x050a }
            double r12 = r12.doubleValue()     // Catch:{ JSONException -> 0x050a }
            boolean r7 = defpackage.sa.a(r7, r12)     // Catch:{ JSONException -> 0x050a }
            if (r7 == 0) goto L_0x0167
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ JSONException -> 0x050a }
            r7.<init>()     // Catch:{ JSONException -> 0x050a }
            com.autonavi.common.model.GeoPoint r8 = new com.autonavi.common.model.GeoPoint     // Catch:{ JSONException -> 0x050a }
            double r12 = java.lang.Double.parseDouble(r9)     // Catch:{ JSONException -> 0x050a }
            double r9 = java.lang.Double.parseDouble(r10)     // Catch:{ JSONException -> 0x050a }
            r8.<init>(r12, r9)     // Catch:{ JSONException -> 0x050a }
            r7.add(r8)     // Catch:{ JSONException -> 0x050a }
            com.autonavi.common.model.POI r8 = r0.d     // Catch:{ JSONException -> 0x050a }
            r8.setEntranceList(r7)     // Catch:{ JSONException -> 0x050a }
        L_0x0167:
            com.autonavi.common.model.POI r7 = r0.d     // Catch:{ JSONException -> 0x050a }
            r7.setId(r15)     // Catch:{ JSONException -> 0x050a }
            com.autonavi.common.model.POI r7 = r0.d     // Catch:{ JSONException -> 0x050a }
            defpackage.sa.a(r7, r3, r11, r4)     // Catch:{ JSONException -> 0x050a }
        L_0x0171:
            r13 = 0
            goto L_0x0185
        L_0x0173:
            java.lang.String r3 = "4"
            boolean r3 = r3.equals(r14)     // Catch:{ JSONException -> 0x050a }
            if (r3 == 0) goto L_0x017d
            r13 = 1
            goto L_0x0185
        L_0x017d:
            r3 = 10001(0x2711, float:1.4014E-41)
            defpackage.sa.a(r1, r3)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x0183:
            r13 = 0
            r14 = 0
        L_0x0185:
            com.autonavi.common.model.POI r3 = r0.d     // Catch:{ JSONException -> 0x050a }
            if (r3 != 0) goto L_0x0197
            java.lang.String r3 = "4"
            boolean r3 = r3.equals(r14)     // Catch:{ JSONException -> 0x050a }
            if (r3 != 0) goto L_0x0197
            r3 = 10004(0x2714, float:1.4019E-41)
            defpackage.sa.a(r1, r3)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x0197:
            java.lang.String r3 = "endPoi"
            org.json.JSONObject r3 = r5.optJSONObject(r3)     // Catch:{ JSONException -> 0x050a }
            if (r3 == 0) goto L_0x02f3
            java.lang.String r7 = "poiType"
            java.lang.String r7 = r3.optString(r7)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r8 = "1"
            boolean r8 = r8.equals(r7)     // Catch:{ JSONException -> 0x050a }
            if (r8 == 0) goto L_0x01c4
            com.autonavi.common.model.POI r3 = defpackage.sa.a()     // Catch:{ JSONException -> 0x050a }
            r0.e = r3     // Catch:{ JSONException -> 0x050a }
            com.autonavi.common.model.POI r3 = r0.e     // Catch:{ JSONException -> 0x050a }
            if (r3 != 0) goto L_0x01c0
            a()     // Catch:{ JSONException -> 0x050a }
            r8 = 10012(0x271c, float:1.403E-41)
            defpackage.sa.a(r1, r8)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x01c0:
            r20 = r5
            goto L_0x02dc
        L_0x01c4:
            r8 = 10012(0x271c, float:1.403E-41)
            java.lang.String r9 = "2"
            boolean r9 = r9.equals(r7)     // Catch:{ JSONException -> 0x050a }
            if (r9 == 0) goto L_0x01e1
            com.autonavi.common.model.POI r3 = defpackage.sa.b()     // Catch:{ JSONException -> 0x050a }
            r0.e = r3     // Catch:{ JSONException -> 0x050a }
            com.autonavi.common.model.POI r3 = r0.e     // Catch:{ JSONException -> 0x050a }
            if (r3 != 0) goto L_0x01c0
            a()     // Catch:{ JSONException -> 0x050a }
            r9 = 10013(0x271d, float:1.4031E-41)
            defpackage.sa.a(r1, r9)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x01e1:
            r9 = 10013(0x271d, float:1.4031E-41)
            java.lang.String r10 = "3"
            boolean r10 = r10.equals(r7)     // Catch:{ JSONException -> 0x050a }
            if (r10 == 0) goto L_0x0213
            com.autonavi.sdk.location.LocationInstrument r3 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ JSONException -> 0x050a }
            r10 = 5
            com.autonavi.common.model.GeoPoint r3 = r3.getLatestPosition(r10)     // Catch:{ JSONException -> 0x050a }
            if (r3 != 0) goto L_0x01ff
            com.amap.bundle.drive.voice.VoiceDriveDispatcherImp$a r3 = new com.amap.bundle.drive.voice.VoiceDriveDispatcherImp$a     // Catch:{ JSONException -> 0x050a }
            r3.<init>(r1, r2)     // Catch:{ JSONException -> 0x050a }
            defpackage.sa.a(r3)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x01ff:
            com.autonavi.common.model.POI r10 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()     // Catch:{ JSONException -> 0x050a }
            r0.e = r10     // Catch:{ JSONException -> 0x050a }
            com.autonavi.common.model.POI r10 = r0.e     // Catch:{ JSONException -> 0x050a }
            r10.setPoint(r3)     // Catch:{ JSONException -> 0x050a }
            com.autonavi.common.model.POI r3 = r0.e     // Catch:{ JSONException -> 0x050a }
            java.lang.String r10 = "我的位置"
            r3.setName(r10)     // Catch:{ JSONException -> 0x050a }
            goto L_0x01c0
        L_0x0213:
            java.lang.String r10 = "0"
            boolean r10 = r10.equals(r7)     // Catch:{ JSONException -> 0x050a }
            if (r10 == 0) goto L_0x02df
            java.lang.String r10 = "poiId"
            java.lang.String r10 = r3.optString(r10)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r11 = "poiName"
            java.lang.String r11 = r3.optString(r11)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r12 = "lon"
            java.lang.String r12 = r3.optString(r12)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r14 = "lat"
            java.lang.String r14 = r3.optString(r14)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r15 = "entry_lon"
            java.lang.String r15 = r3.optString(r15)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r8 = "entry_lat"
            java.lang.String r3 = r3.optString(r8)     // Catch:{ JSONException -> 0x050a }
            if (r11 != 0) goto L_0x0244
            java.lang.String r8 = ""
            goto L_0x0248
        L_0x0244:
            java.lang.String r8 = r11.trim()     // Catch:{ JSONException -> 0x050a }
        L_0x0248:
            java.lang.Double r11 = java.lang.Double.valueOf(r12)     // Catch:{ JSONException -> 0x050a }
            r19 = r10
            double r9 = r11.doubleValue()     // Catch:{ JSONException -> 0x050a }
            java.lang.Double r11 = java.lang.Double.valueOf(r14)     // Catch:{ JSONException -> 0x050a }
            r20 = r5
            double r4 = r11.doubleValue()     // Catch:{ JSONException -> 0x050a }
            boolean r4 = defpackage.sa.b(r9, r4)     // Catch:{ JSONException -> 0x050a }
            if (r4 == 0) goto L_0x0268
            r4 = 10001(0x2711, float:1.4014E-41)
            defpackage.sa.a(r1, r4)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x0268:
            java.lang.Double r4 = java.lang.Double.valueOf(r12)     // Catch:{ JSONException -> 0x050a }
            double r4 = r4.doubleValue()     // Catch:{ JSONException -> 0x050a }
            java.lang.Double r9 = java.lang.Double.valueOf(r14)     // Catch:{ JSONException -> 0x050a }
            double r9 = r9.doubleValue()     // Catch:{ JSONException -> 0x050a }
            boolean r4 = defpackage.sa.a(r4, r9)     // Catch:{ JSONException -> 0x050a }
            if (r4 != 0) goto L_0x0284
            r4 = 10005(0x2715, float:1.402E-41)
            defpackage.sa.a(r1, r4)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x0284:
            com.autonavi.common.model.POI r4 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()     // Catch:{ JSONException -> 0x050a }
            r0.e = r4     // Catch:{ JSONException -> 0x050a }
            java.lang.Double r4 = java.lang.Double.valueOf(r15)     // Catch:{ JSONException -> 0x050a }
            double r4 = r4.doubleValue()     // Catch:{ JSONException -> 0x050a }
            java.lang.Double r9 = java.lang.Double.valueOf(r3)     // Catch:{ JSONException -> 0x050a }
            double r9 = r9.doubleValue()     // Catch:{ JSONException -> 0x050a }
            boolean r4 = defpackage.sa.b(r4, r9)     // Catch:{ JSONException -> 0x050a }
            if (r4 != 0) goto L_0x02d0
            java.lang.Double r4 = java.lang.Double.valueOf(r15)     // Catch:{ JSONException -> 0x050a }
            double r4 = r4.doubleValue()     // Catch:{ JSONException -> 0x050a }
            java.lang.Double r9 = java.lang.Double.valueOf(r3)     // Catch:{ JSONException -> 0x050a }
            double r9 = r9.doubleValue()     // Catch:{ JSONException -> 0x050a }
            boolean r4 = defpackage.sa.a(r4, r9)     // Catch:{ JSONException -> 0x050a }
            if (r4 == 0) goto L_0x02d0
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ JSONException -> 0x050a }
            r4.<init>()     // Catch:{ JSONException -> 0x050a }
            com.autonavi.common.model.GeoPoint r5 = new com.autonavi.common.model.GeoPoint     // Catch:{ JSONException -> 0x050a }
            double r9 = java.lang.Double.parseDouble(r15)     // Catch:{ JSONException -> 0x050a }
            double r2 = java.lang.Double.parseDouble(r3)     // Catch:{ JSONException -> 0x050a }
            r5.<init>(r9, r2)     // Catch:{ JSONException -> 0x050a }
            r4.add(r5)     // Catch:{ JSONException -> 0x050a }
            com.autonavi.common.model.POI r2 = r0.e     // Catch:{ JSONException -> 0x050a }
            r2.setEntranceList(r4)     // Catch:{ JSONException -> 0x050a }
        L_0x02d0:
            com.autonavi.common.model.POI r2 = r0.e     // Catch:{ JSONException -> 0x050a }
            r3 = r19
            r2.setId(r3)     // Catch:{ JSONException -> 0x050a }
            com.autonavi.common.model.POI r2 = r0.e     // Catch:{ JSONException -> 0x050a }
            defpackage.sa.b(r2, r8, r14, r12)     // Catch:{ JSONException -> 0x050a }
        L_0x02dc:
            r3 = 10001(0x2711, float:1.4014E-41)
            goto L_0x02f8
        L_0x02df:
            r20 = r5
            java.lang.String r2 = "4"
            boolean r2 = r2.equals(r7)     // Catch:{ JSONException -> 0x050a }
            if (r2 == 0) goto L_0x02ed
            r3 = 10001(0x2711, float:1.4014E-41)
            r13 = 1
            goto L_0x02f8
        L_0x02ed:
            r3 = 10001(0x2711, float:1.4014E-41)
            defpackage.sa.a(r1, r3)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x02f3:
            r20 = r5
            r3 = 10001(0x2711, float:1.4014E-41)
            r7 = 0
        L_0x02f8:
            com.autonavi.common.model.POI r2 = r0.e     // Catch:{ JSONException -> 0x050a }
            if (r2 != 0) goto L_0x030a
            java.lang.String r2 = "4"
            boolean r2 = r2.equals(r7)     // Catch:{ JSONException -> 0x050a }
            if (r2 != 0) goto L_0x030a
            r2 = 10005(0x2715, float:1.402E-41)
            defpackage.sa.a(r1, r2)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x030a:
            java.lang.String r2 = "midPoi"
            r4 = r20
            org.json.JSONArray r2 = r4.optJSONArray(r2)     // Catch:{ JSONException -> 0x050a }
            if (r2 == 0) goto L_0x049c
            int r5 = r2.length()     // Catch:{ JSONException -> 0x050a }
            if (r5 <= 0) goto L_0x049c
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ JSONException -> 0x050a }
            r5.<init>()     // Catch:{ JSONException -> 0x050a }
            r0.f = r5     // Catch:{ JSONException -> 0x050a }
            r5 = 0
        L_0x0322:
            int r8 = r2.length()     // Catch:{ JSONException -> 0x050a }
            if (r5 >= r8) goto L_0x0489
            org.json.JSONObject r8 = r2.getJSONObject(r5)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r10 = "poiType"
            java.lang.String r10 = r8.optString(r10)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r11 = "1"
            boolean r11 = r11.equals(r10)     // Catch:{ JSONException -> 0x050a }
            if (r11 == 0) goto L_0x034d
            com.autonavi.common.model.POI r8 = defpackage.sa.a()     // Catch:{ JSONException -> 0x050a }
            if (r8 != 0) goto L_0x0349
            r23 = r6
            r22 = r13
            r2 = -2
            r7 = 10012(0x271c, float:1.403E-41)
            goto L_0x048f
        L_0x0349:
            r23 = r6
            goto L_0x046d
        L_0x034d:
            java.lang.String r11 = "2"
            boolean r11 = r11.equals(r10)     // Catch:{ JSONException -> 0x050a }
            if (r11 == 0) goto L_0x0364
            com.autonavi.common.model.POI r8 = defpackage.sa.b()     // Catch:{ JSONException -> 0x050a }
            if (r8 != 0) goto L_0x0349
            r23 = r6
            r22 = r13
            r2 = -2
            r7 = 10013(0x271d, float:1.4031E-41)
            goto L_0x048f
        L_0x0364:
            java.lang.String r11 = "3"
            boolean r11 = r11.equals(r10)     // Catch:{ JSONException -> 0x050a }
            if (r11 == 0) goto L_0x039d
            com.autonavi.sdk.location.LocationInstrument r8 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ JSONException -> 0x050a }
            r11 = 5
            com.autonavi.common.model.GeoPoint r8 = r8.getLatestPosition(r11)     // Catch:{ JSONException -> 0x050a }
            if (r8 != 0) goto L_0x0389
            com.amap.bundle.drive.voice.VoiceDriveDispatcherImp$a r2 = new com.amap.bundle.drive.voice.VoiceDriveDispatcherImp$a     // Catch:{ JSONException -> 0x050a }
            r12 = r26
            r2.<init>(r1, r12)     // Catch:{ JSONException -> 0x050a }
            defpackage.sa.a(r2)     // Catch:{ JSONException -> 0x050a }
            r23 = r6
            r22 = r13
            r2 = -2
            r7 = -2
            goto L_0x048f
        L_0x0389:
            r12 = r26
            com.autonavi.common.model.POI r9 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()     // Catch:{ JSONException -> 0x050a }
            r9.setPoint(r8)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r8 = "我的位置"
            r9.setName(r8)     // Catch:{ JSONException -> 0x050a }
            r23 = r6
            r8 = r9
            goto L_0x046d
        L_0x039d:
            r11 = 5
            r12 = r26
            java.lang.String r14 = "0"
            boolean r14 = r14.equals(r10)     // Catch:{ JSONException -> 0x050a }
            if (r14 == 0) goto L_0x045f
            java.lang.String r10 = "poiId"
            java.lang.String r10 = r8.optString(r10)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r14 = "poiName"
            java.lang.String r14 = r8.optString(r14)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r15 = "lon"
            java.lang.String r15 = r8.optString(r15)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r3 = "lat"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r11 = "entry_lon"
            java.lang.String r11 = r8.optString(r11)     // Catch:{ JSONException -> 0x050a }
            java.lang.String r7 = "entry_lat"
            java.lang.String r7 = r8.optString(r7)     // Catch:{ JSONException -> 0x050a }
            java.lang.Double r8 = java.lang.Double.valueOf(r15)     // Catch:{ JSONException -> 0x050a }
            r21 = r10
            double r9 = r8.doubleValue()     // Catch:{ JSONException -> 0x050a }
            java.lang.Double r8 = java.lang.Double.valueOf(r3)     // Catch:{ JSONException -> 0x050a }
            r22 = r13
            double r12 = r8.doubleValue()     // Catch:{ JSONException -> 0x050a }
            boolean r8 = defpackage.sa.b(r9, r12)     // Catch:{ JSONException -> 0x050a }
            if (r8 == 0) goto L_0x03ea
            r23 = r6
            goto L_0x0485
        L_0x03ea:
            java.lang.Double r8 = java.lang.Double.valueOf(r15)     // Catch:{ JSONException -> 0x050a }
            double r8 = r8.doubleValue()     // Catch:{ JSONException -> 0x050a }
            java.lang.Double r10 = java.lang.Double.valueOf(r3)     // Catch:{ JSONException -> 0x050a }
            double r12 = r10.doubleValue()     // Catch:{ JSONException -> 0x050a }
            boolean r8 = defpackage.sa.a(r8, r12)     // Catch:{ JSONException -> 0x050a }
            if (r8 != 0) goto L_0x0407
            r7 = 10006(0x2716, float:1.4021E-41)
            r23 = r6
            r2 = -2
            goto L_0x048f
        L_0x0407:
            com.autonavi.common.model.POI r8 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()     // Catch:{ JSONException -> 0x050a }
            java.lang.Double r9 = java.lang.Double.valueOf(r15)     // Catch:{ JSONException -> 0x050a }
            double r9 = r9.doubleValue()     // Catch:{ JSONException -> 0x050a }
            java.lang.Double r12 = java.lang.Double.valueOf(r3)     // Catch:{ JSONException -> 0x050a }
            double r12 = r12.doubleValue()     // Catch:{ JSONException -> 0x050a }
            boolean r9 = defpackage.sa.b(r9, r12)     // Catch:{ JSONException -> 0x050a }
            if (r9 != 0) goto L_0x0452
            java.lang.Double r9 = java.lang.Double.valueOf(r11)     // Catch:{ JSONException -> 0x050a }
            double r9 = r9.doubleValue()     // Catch:{ JSONException -> 0x050a }
            java.lang.Double r12 = java.lang.Double.valueOf(r7)     // Catch:{ JSONException -> 0x050a }
            double r12 = r12.doubleValue()     // Catch:{ JSONException -> 0x050a }
            boolean r9 = defpackage.sa.a(r9, r12)     // Catch:{ JSONException -> 0x050a }
            if (r9 == 0) goto L_0x0452
            java.util.ArrayList r9 = new java.util.ArrayList     // Catch:{ JSONException -> 0x050a }
            r9.<init>()     // Catch:{ JSONException -> 0x050a }
            com.autonavi.common.model.GeoPoint r10 = new com.autonavi.common.model.GeoPoint     // Catch:{ JSONException -> 0x050a }
            double r11 = java.lang.Double.parseDouble(r11)     // Catch:{ JSONException -> 0x050a }
            r23 = r6
            double r6 = java.lang.Double.parseDouble(r7)     // Catch:{ JSONException -> 0x050a }
            r10.<init>(r11, r6)     // Catch:{ JSONException -> 0x050a }
            r9.add(r10)     // Catch:{ JSONException -> 0x050a }
            r8.setEntranceList(r9)     // Catch:{ JSONException -> 0x050a }
            goto L_0x0454
        L_0x0452:
            r23 = r6
        L_0x0454:
            r6 = r21
            r8.setId(r6)     // Catch:{ JSONException -> 0x050a }
            defpackage.sa.c(r8, r14, r3, r15)     // Catch:{ JSONException -> 0x050a }
            r13 = r22
            goto L_0x046d
        L_0x045f:
            r23 = r6
            r22 = r13
            java.lang.String r3 = "4"
            boolean r3 = r3.equals(r10)     // Catch:{ JSONException -> 0x050a }
            if (r3 == 0) goto L_0x0485
            r8 = 0
            r13 = 1
        L_0x046d:
            if (r8 == 0) goto L_0x047d
            java.util.ArrayList<android.util.Pair<java.lang.Integer, com.autonavi.common.model.POI>> r3 = r0.f     // Catch:{ JSONException -> 0x050a }
            android.util.Pair r6 = new android.util.Pair     // Catch:{ JSONException -> 0x050a }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r5)     // Catch:{ JSONException -> 0x050a }
            r6.<init>(r7, r8)     // Catch:{ JSONException -> 0x050a }
            r3.add(r6)     // Catch:{ JSONException -> 0x050a }
        L_0x047d:
            int r5 = r5 + 1
            r6 = r23
            r3 = 10001(0x2711, float:1.4014E-41)
            goto L_0x0322
        L_0x0485:
            r2 = -2
            r7 = 10001(0x2711, float:1.4014E-41)
            goto L_0x048f
        L_0x0489:
            r23 = r6
            r22 = r13
            r2 = -2
            r7 = -1
        L_0x048f:
            if (r7 != r2) goto L_0x0492
            return
        L_0x0492:
            r2 = -1
            if (r7 == r2) goto L_0x0499
            defpackage.sa.a(r1, r7)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x0499:
            r13 = r22
            goto L_0x049e
        L_0x049c:
            r23 = r6
        L_0x049e:
            com.autonavi.bundle.routecommon.model.RouteType r2 = com.autonavi.bundle.routecommon.model.RouteType.CAR     // Catch:{ JSONException -> 0x050a }
            java.lang.String r2 = "-1"
            r0.h = r2     // Catch:{ JSONException -> 0x050a }
            java.lang.String r2 = "params"
            r3 = -1
            int r2 = r4.optInt(r2, r3)     // Catch:{ JSONException -> 0x050a }
            if (r2 != r3) goto L_0x04b4
            java.lang.String r2 = com.amap.bundle.drivecommon.tools.DriveUtil.getLastRoutingChoice()     // Catch:{ JSONException -> 0x050a }
            r0.h = r2     // Catch:{ JSONException -> 0x050a }
            goto L_0x04d2
        L_0x04b4:
            boolean r3 = defpackage.sa.b(r2)     // Catch:{ JSONException -> 0x050a }
            if (r3 != 0) goto L_0x04c0
            r2 = 10022(0x2726, float:1.4044E-41)
            defpackage.sa.a(r1, r2)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x04c0:
            boolean r3 = defpackage.sa.a(r2)     // Catch:{ JSONException -> 0x050a }
            if (r3 != 0) goto L_0x04cc
            r2 = 10034(0x2732, float:1.406E-41)
            defpackage.sa.a(r1, r2)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x04cc:
            java.lang.String r2 = defpackage.sa.c(r2)     // Catch:{ JSONException -> 0x050a }
            r0.h = r2     // Catch:{ JSONException -> 0x050a }
        L_0x04d2:
            boolean r2 = com.amap.bundle.network.util.NetworkReachability.b()     // Catch:{ JSONException -> 0x050a }
            if (r2 == 0) goto L_0x04de
            boolean r2 = com.amap.bundle.drivecommon.tools.DriveSpUtil.shouldRouteOffline()     // Catch:{ JSONException -> 0x050a }
            if (r2 == 0) goto L_0x04ee
        L_0x04de:
            java.lang.String r2 = r0.h     // Catch:{ JSONException -> 0x050a }
            java.lang.String r3 = "2"
            boolean r2 = r2.contains(r3)     // Catch:{ JSONException -> 0x050a }
            if (r2 == 0) goto L_0x04ee
            r2 = 10033(0x2731, float:1.4059E-41)
            defpackage.sa.a(r1, r2)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x04ee:
            if (r13 == 0) goto L_0x0500
            if (r23 != 0) goto L_0x04f8
            r2 = 9004(0x232c, float:1.2617E-41)
            defpackage.sa.a(r1, r2)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x04f8:
            asv r2 = r0.a     // Catch:{ JSONException -> 0x050a }
            r3 = r26
            defpackage.sa.a(r1, r3, r2)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x0500:
            java.util.ArrayList<android.util.Pair<java.lang.Integer, com.autonavi.common.model.POI>> r2 = r0.f     // Catch:{ JSONException -> 0x050a }
            java.util.ArrayList r2 = r0.a(r2)     // Catch:{ JSONException -> 0x050a }
            r0.a(r1, r2)     // Catch:{ JSONException -> 0x050a }
            return
        L_0x050a:
            r2 = 10020(0x2724, float:1.4041E-41)
            defpackage.sa.a(r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.voice.VoiceDriveDispatcherImp.requestRoute(int, java.lang.String):void");
    }

    @IVoiceDispatchMethod(methodName = "requestTruckRoute")
    public void requestTruckRoute(int i2, String str) {
        sa.a(i2, 9004);
    }

    @IVoiceDispatchMethod(methodName = "startNavi")
    public void startNavi(int i2, String str) {
        if (this.c != null) {
            this.c.a(i2);
        } else {
            sa.a(i2, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "enterRadarMode")
    public void enterRadarMode(int i2, String str) {
        if (this.c != null) {
            this.c.b(i2);
        } else {
            sa.a(i2, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "requestTrafficMessage")
    public void requestTrafficMessage(final int i2, String str) {
        try {
            String optString = new JSONObject(str).optString("requestMessage");
            if (!TextUtils.isEmpty(optString)) {
                VoiceTrafficManger.a();
                VoiceTrafficManger.a(optString, new sc() {
                    public final void a(String str) {
                        sa.a(i2, new Pair("message", str));
                    }

                    public final void a(int i) {
                        sa.a(i2, i);
                    }
                });
                return;
            }
            sa.a(i2, 10001);
        } catch (JSONException unused) {
            sa.a(i2, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "operateMap")
    public void operateMap(int i2, String str) {
        try {
            int optInt = new JSONObject(str).optInt("type");
            if (optInt != 0) {
                if (optInt != 1) {
                    sa.a(i2, 10001);
                } else if (this.b != null) {
                    this.b.b(i2);
                }
            } else if (this.b != null) {
                this.b.a(i2);
            } else {
                sa.a(i2, (int) SDKFactory.getCoreType);
            }
        } catch (JSONException unused) {
            sa.a(i2, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "searchAlongInNavi")
    public void searchAlongInNavi(int i2, String str) {
        try {
            int optInt = new JSONObject(str).optInt("type");
            if (!(optInt == 1 || optInt == 2 || optInt == 3)) {
                if (optInt != 4) {
                    sa.a(i2, 10001);
                    return;
                }
            }
            if (this.b != null) {
                this.b.a(i2, optInt);
            } else {
                sa.a(i2, (int) SDKFactory.getCoreType);
            }
        } catch (JSONException unused) {
            sa.a(i2, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "searchAlongInRoutePage")
    public void searchAlongInRoutePage(int i2, String str) {
        sa.a(i2, 9004);
    }

    @IVoiceDispatchMethod(methodName = "setRouteParamsInNavi")
    public void setRouteParamsInNavi(int i2, String str) {
        a(true, i2, str);
    }

    @IVoiceDispatchMethod(methodName = "setRouteParamsInCarRoutePage")
    public void setRouteParamsInCarRoutePage(int i2, String str) {
        a(false, i2, str);
    }

    private void a(boolean z, int i2, String str) {
        try {
            int optInt = new JSONObject(str).optInt("params", -1);
            if (!sa.b(optInt)) {
                sa.a(i2, 10022);
            } else if (!sa.a(optInt)) {
                sa.a(i2, 10034);
            } else {
                String c2 = sa.c(optInt);
                if ((!NetworkReachability.b() || DriveSpUtil.shouldRouteOffline()) && c2.contains("2")) {
                    sa.a(i2, (int) UCMPackageInfo.expectDirFile1S);
                } else if (TextUtils.isEmpty(c2)) {
                    sa.a(i2, 10000);
                } else if (z) {
                    if (this.b != null) {
                        this.b.a(i2, c2);
                    } else {
                        sa.a(i2, (int) SDKFactory.getCoreType);
                    }
                } else if (this.c != null) {
                    this.c.a(i2, c2);
                } else {
                    sa.a(i2, (int) SDKFactory.getCoreType);
                }
            }
        } catch (JSONException unused) {
            sa.a(i2, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "getHistoryRoutes")
    public void getHistoryRoutes(final int i2, String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt("type");
            int optInt2 = jSONObject.optInt("max");
            if ((optInt == 0 || optInt == 1) && optInt2 > 0) {
                RouteType routeType = null;
                switch (optInt) {
                    case 0:
                        routeType = RouteType.CAR;
                        break;
                    case 1:
                        routeType = RouteType.TRUCK;
                        break;
                }
                if (routeType == RouteType.TRUCK) {
                    sa.a(i2, 9004);
                } else {
                    sa.a(routeType, optInt2, (ry) new ry() {
                        public final void a(JSONArray jSONArray) {
                            sa.a(i2, new Pair("list", jSONArray));
                        }
                    });
                }
            } else {
                sa.a(i2, 10001);
            }
        } catch (JSONException unused) {
            sa.a(i2, 10029);
        }
    }

    @IVoiceDispatchMethod(methodName = "exitNavi")
    public void exitNavi(int i2, String str) {
        if (this.b != null) {
            this.b.e(i2);
        } else {
            sa.a(i2, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "switchRoute")
    public void switchRoute(int i2, String str) {
        try {
            int optInt = new JSONObject(str).optInt("index", -1);
            if (optInt < 0) {
                sa.a(i2, 10021);
            } else if (this.c != null) {
                this.c.a(i2, optInt);
            } else {
                sa.a(i2, (int) SDKFactory.getCoreType);
            }
        } catch (JSONException unused) {
            sa.a(i2, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "previewMap")
    public void previewMap(int i2, String str) {
        try {
            boolean optBoolean = new JSONObject(str).optBoolean("open");
            if (this.b != null) {
                this.b.a(i2, optBoolean);
            }
        } catch (JSONException unused) {
            sa.a(i2, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "setTraffic")
    public void setTraffic(int i2, String str) {
        try {
            boolean optBoolean = new JSONObject(str).optBoolean("open");
            if (this.b != null) {
                this.b.b(i2, optBoolean);
            } else {
                sa.a(i2, (int) SDKFactory.getCoreType);
            }
        } catch (JSONException unused) {
            sa.a(i2, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "setTrafficRoutePage")
    public void setTrafficRoutePage(int i2, String str) {
        sa.a(i2, 9004);
    }

    @IVoiceDispatchMethod(methodName = "refreshRouteInNavi")
    public void refreshRouteInNavi(int i2, String str) {
        if (this.b != null) {
            this.b.c(i2);
        } else {
            sa.a(i2, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "refreshRouteInTruckRoutePage")
    public void refreshRouteInTruckRoutePage(int i2, String str) {
        sa.a(i2, 9004);
    }

    @IVoiceDispatchMethod(methodName = "requestGuideInfo")
    public void requestGuideInfo(int i2, String str) {
        if (this.b != null) {
            this.b.d(i2);
        } else {
            sa.a(i2, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "adjustVolume")
    public void adjustVolume(int i2, String str) {
        if (this.b != null) {
            this.b.b(i2, str);
        } else {
            sa.a(i2, (int) SDKFactory.getCoreType);
        }
    }

    public void setNaviApiControlListener(aig aig) {
        this.b = aig;
    }

    public void setRouteApiControlListener(aij aij) {
        this.c = aij;
    }

    @IVoiceDispatchMethod(methodName = "getCurrentLocationInfo")
    public void getCurrentLocationInfo(int i2, String str) {
        try {
            ((apu) ank.a(apu.class)).e(i2);
        } catch (Exception unused) {
            sa.a(i2, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "swapStartEndPoi")
    public void swapStartEndPoi(int i2, String str) {
        bid pageContext = AMapPageUtil.getPageContext();
        boolean z = pageContext instanceof se;
        int i3 = SDKFactory.getCoreType;
        if (z) {
            se seVar = (se) pageContext;
            if (seVar.a()) {
                seVar.b();
                i3 = 10000;
            }
        }
        sa.a(i2, i3);
    }

    @IVoiceDispatchMethod(methodName = "addMidPois")
    public void addMidPois(int i2, String str) {
        int i3;
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext instanceof se) {
            final se seVar = (se) pageContext;
            i3 = RouteHeaderModel.dealVoiceAddMidPois(str, new IDealVoiceAddMidPois() {
                public final boolean isAddMidPoisEnable() {
                    return seVar.c();
                }

                public final List<POI> getExistingMidPois() {
                    return seVar.d();
                }

                public final POI getPOIHome() {
                    return sa.a();
                }

                public final POI getPOICompany() {
                    return sa.b();
                }

                public final POI getStartPOI() {
                    return seVar.e();
                }

                public final POI getEndPOI() {
                    return seVar.f();
                }

                public final void onSetMidPoisToPage(List<POI> list) {
                    seVar.a(list);
                }
            });
        } else {
            i3 = SDKFactory.getCoreType;
        }
        sa.a(i2, i3);
    }

    @IVoiceDispatchMethod(methodName = "requestNaviInfo")
    public void requestNaviInfo(int i2, String str) {
        try {
            int optInt = new JSONObject(str).optInt("type");
            if (optInt != 0) {
                if (optInt != 1) {
                    sa.a(i2, 10001);
                    return;
                }
            }
            if (this.b != null) {
                this.b.b(i2, optInt);
            } else {
                sa.a(i2, (int) SDKFactory.getCoreType);
            }
        } catch (JSONException unused) {
            sa.a(i2, (int) SDKFactory.getCoreType);
        }
    }

    @IVoiceDispatchMethod(methodName = "refreshRouteInCarRoutePage")
    public void refreshRouteInCarRoutePage(int i2, String str) {
        aia aia = (aia) defpackage.esb.a.a.a(aia.class);
        if (aia != null && aia.a("refreshRoute", i2)) {
            sa.a(i2, (int) UCMPackageInfo.getKernelFileIfMultiCoreFromDir);
        } else if (this.c != null) {
            this.c.c(i2);
        } else {
            sa.a(i2, (int) SDKFactory.getCoreType);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x003c A[Catch:{ Exception -> 0x0059 }] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0049 A[Catch:{ Exception -> 0x0059 }] */
    @com.amap.bundle.voiceservice.dispatch.IVoiceDispatchMethod(methodName = "hasTruckInfo")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void hasTruckInfo(int r4, java.lang.String r5) {
        /*
            r3 = this;
            java.lang.String r5 = com.amap.bundle.drivecommon.tools.DriveUtil.getTruckCarPlateNumber()     // Catch:{ Exception -> 0x0059 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0059 }
            if (r5 == 0) goto L_0x0018
            android.util.Pair r5 = new android.util.Pair     // Catch:{ Exception -> 0x0059 }
            java.lang.String r0 = "result"
            r1 = 2
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0059 }
            r5.<init>(r0, r1)     // Catch:{ Exception -> 0x0059 }
            goto L_0x0055
        L_0x0018:
            boolean r5 = defpackage.tk.a()     // Catch:{ Exception -> 0x0059 }
            r0 = 1
            r1 = 0
            if (r5 == 0) goto L_0x0039
            boolean r5 = defpackage.tk.c()     // Catch:{ Exception -> 0x0059 }
            if (r5 == 0) goto L_0x0037
            java.lang.String r5 = "0"
            java.lang.String r2 = defpackage.tk.b()     // Catch:{ Exception -> 0x0059 }
            java.lang.String r2 = defpackage.tk.a(r2)     // Catch:{ Exception -> 0x0059 }
            boolean r5 = r5.equals(r2)     // Catch:{ Exception -> 0x0059 }
            if (r5 == 0) goto L_0x0037
            goto L_0x0039
        L_0x0037:
            r5 = 0
            goto L_0x003a
        L_0x0039:
            r5 = 1
        L_0x003a:
            if (r5 == 0) goto L_0x0049
            android.util.Pair r5 = new android.util.Pair     // Catch:{ Exception -> 0x0059 }
            java.lang.String r1 = "result"
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0059 }
            r5.<init>(r1, r0)     // Catch:{ Exception -> 0x0059 }
            goto L_0x0055
        L_0x0049:
            android.util.Pair r5 = new android.util.Pair     // Catch:{ Exception -> 0x0059 }
            java.lang.String r0 = "result"
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0059 }
            r5.<init>(r0, r1)     // Catch:{ Exception -> 0x0059 }
        L_0x0055:
            defpackage.sa.a(r4, r5)     // Catch:{ Exception -> 0x0059 }
            return
        L_0x0059:
            r5 = 10020(0x2724, float:1.4041E-41)
            defpackage.sa.a(r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.voice.VoiceDriveDispatcherImp.hasTruckInfo(int, java.lang.String):void");
    }
}
