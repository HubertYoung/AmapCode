package defpackage;

import com.autonavi.minimap.bl.net.INetworkProvider;

/* renamed from: clg reason: default package */
/* compiled from: NetworkProviderImpl */
public final class clg implements INetworkProvider {
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x01a2, code lost:
        if (r11.equals("traffic_radio_front") != false) goto L_0x01dc;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String getUrlInfo(java.lang.String r11) {
        /*
            r10 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            if (r0 == 0) goto L_0x0009
            java.lang.String r11 = ""
            return r11
        L_0x0009:
            int r0 = r11.hashCode()
            r1 = 3
            r2 = 5
            r3 = 4
            r4 = 2
            r5 = -1
            r6 = 1
            r7 = 0
            switch(r0) {
                case -1990884566: goto L_0x004a;
                case -1597228116: goto L_0x0040;
                case -865690263: goto L_0x0036;
                case -300250386: goto L_0x002c;
                case -300238823: goto L_0x0022;
                case 909837573: goto L_0x0018;
                default: goto L_0x0017;
            }
        L_0x0017:
            goto L_0x0054
        L_0x0018:
            java.lang.String r0 = "escort_stop"
            boolean r0 = r11.equals(r0)
            if (r0 == 0) goto L_0x0054
            r0 = 0
            goto L_0x0055
        L_0x0022:
            java.lang.String r0 = "host_mps"
            boolean r0 = r11.equals(r0)
            if (r0 == 0) goto L_0x0054
            r0 = 1
            goto L_0x0055
        L_0x002c:
            java.lang.String r0 = "host_aos"
            boolean r0 = r11.equals(r0)
            if (r0 == 0) goto L_0x0054
            r0 = 2
            goto L_0x0055
        L_0x0036:
            java.lang.String r0 = "road_data"
            boolean r0 = r11.equals(r0)
            if (r0 == 0) goto L_0x0054
            r0 = 4
            goto L_0x0055
        L_0x0040:
            java.lang.String r0 = "data_backhaul"
            boolean r0 = r11.equals(r0)
            if (r0 == 0) goto L_0x0054
            r0 = 5
            goto L_0x0055
        L_0x004a:
            java.lang.String r0 = "traffic_signal"
            boolean r0 = r11.equals(r0)
            if (r0 == 0) goto L_0x0054
            r0 = 3
            goto L_0x0055
        L_0x0054:
            r0 = -1
        L_0x0055:
            switch(r0) {
                case 0: goto L_0x0073;
                case 1: goto L_0x006c;
                case 2: goto L_0x0065;
                case 3: goto L_0x0065;
                case 4: goto L_0x005e;
                case 5: goto L_0x005e;
                default: goto L_0x0058;
            }
        L_0x0058:
            boolean r0 = defpackage.bno.a
            if (r0 != 0) goto L_0x0078
            r0 = 0
            goto L_0x0085
        L_0x005e:
            java.lang.String r0 = "aos_http_url"
            java.lang.String r0 = defpackage.aaf.b(r0)
            goto L_0x008e
        L_0x0065:
            java.lang.String r0 = "aos_url"
            java.lang.String r0 = defpackage.aaf.b(r0)
            goto L_0x008e
        L_0x006c:
            java.lang.String r0 = "mps_url"
            java.lang.String r0 = defpackage.aaf.b(r0)
            goto L_0x008e
        L_0x0073:
            java.lang.String r0 = com.amap.bundle.network.request.param.NetworkParam.getOperationalServerUrl()
            goto L_0x008e
        L_0x0078:
            com.amap.bundle.mapstorage.MapSharePreference r0 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r8 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences
            r0.<init>(r8)
            java.lang.String r8 = "drive_joint_debugging_tools"
            boolean r0 = r0.getBooleanValue(r8, r7)
        L_0x0085:
            if (r0 == 0) goto L_0x008a
            java.lang.String r0 = "http://maps.aosdev.amap.com"
            goto L_0x008e
        L_0x008a:
            java.lang.String r0 = com.amap.bundle.network.request.param.NetworkParam.getDriveAosServerUrl()
        L_0x008e:
            java.lang.String r8 = "/"
            boolean r8 = r0.endsWith(r8)
            if (r8 == 0) goto L_0x009f
            int r8 = r0.length()
            int r8 = r8 - r6
            java.lang.String r0 = r0.substring(r7, r8)
        L_0x009f:
            java.lang.String r8 = ""
            int r9 = r11.hashCode()
            switch(r9) {
                case -2019561238: goto L_0x01d1;
                case -1990884566: goto L_0x01c6;
                case -1741239506: goto L_0x01bb;
                case -1597228116: goto L_0x01b0;
                case -1433602236: goto L_0x01a5;
                case -1356359261: goto L_0x019c;
                case -1347631746: goto L_0x0192;
                case -1345360445: goto L_0x0188;
                case -1296908826: goto L_0x017d;
                case -1294158500: goto L_0x0172;
                case -1208820961: goto L_0x0167;
                case -1054324746: goto L_0x015b;
                case -1040555521: goto L_0x014f;
                case -929959285: goto L_0x0143;
                case -865690263: goto L_0x0137;
                case -820387517: goto L_0x012c;
                case -644246351: goto L_0x0120;
                case -403315136: goto L_0x0114;
                case -300250386: goto L_0x0108;
                case -300238823: goto L_0x00fc;
                case 3522751: goto L_0x00f0;
                case 94935104: goto L_0x00e5;
                case 909837573: goto L_0x00d9;
                case 1067380484: goto L_0x00cd;
                case 1223440372: goto L_0x00c1;
                case 1393849530: goto L_0x00b5;
                case 1443512449: goto L_0x00aa;
                default: goto L_0x00a8;
            }
        L_0x00a8:
            goto L_0x01db
        L_0x00aa:
            java.lang.String r1 = "eta_report"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 2
            goto L_0x01dc
        L_0x00b5:
            java.lang.String r1 = "off_route_report"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 9
            goto L_0x01dc
        L_0x00c1:
            java.lang.String r1 = "weather"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 10
            goto L_0x01dc
        L_0x00cd:
            java.lang.String r1 = "three_d_cross"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 8
            goto L_0x01dc
        L_0x00d9:
            java.lang.String r1 = "escort_stop"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 13
            goto L_0x01dc
        L_0x00e5:
            java.lang.String r1 = "cross"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 7
            goto L_0x01dc
        L_0x00f0:
            java.lang.String r1 = "sapa"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 11
            goto L_0x01dc
        L_0x00fc:
            java.lang.String r1 = "host_mps"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 22
            goto L_0x01dc
        L_0x0108:
            java.lang.String r1 = "host_aos"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 23
            goto L_0x01dc
        L_0x0114:
            java.lang.String r1 = "motor_tmc"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 15
            goto L_0x01dc
        L_0x0120:
            java.lang.String r1 = "parking_alipay"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 21
            goto L_0x01dc
        L_0x012c:
            java.lang.String r1 = "vector"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 6
            goto L_0x01dc
        L_0x0137:
            java.lang.String r1 = "road_data"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 24
            goto L_0x01dc
        L_0x0143:
            java.lang.String r1 = "motor_eta_report"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 17
            goto L_0x01dc
        L_0x014f:
            java.lang.String r1 = "motor_route"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 14
            goto L_0x01dc
        L_0x015b:
            java.lang.String r1 = "motor_cross"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 16
            goto L_0x01dc
        L_0x0167:
            java.lang.String r1 = "tmc_car"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 0
            goto L_0x01dc
        L_0x0172:
            java.lang.String r1 = "escort"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 12
            goto L_0x01dc
        L_0x017d:
            java.lang.String r1 = "motor_restrict_area"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 20
            goto L_0x01dc
        L_0x0188:
            java.lang.String r1 = "traffic_radio_route"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 5
            goto L_0x01dc
        L_0x0192:
            java.lang.String r1 = "traffic_radio_panel"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 4
            goto L_0x01dc
        L_0x019c:
            java.lang.String r2 = "traffic_radio_front"
            boolean r11 = r11.equals(r2)
            if (r11 == 0) goto L_0x01db
            goto L_0x01dc
        L_0x01a5:
            java.lang.String r1 = "motor_off_route_report"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 18
            goto L_0x01dc
        L_0x01b0:
            java.lang.String r1 = "data_backhaul"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 25
            goto L_0x01dc
        L_0x01bb:
            java.lang.String r1 = "motor_speed_rank"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 19
            goto L_0x01dc
        L_0x01c6:
            java.lang.String r1 = "traffic_signal"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 26
            goto L_0x01dc
        L_0x01d1:
            java.lang.String r1 = "tmc_truck"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x01db
            r1 = 1
            goto L_0x01dc
        L_0x01db:
            r1 = -1
        L_0x01dc:
            switch(r1) {
                case 0: goto L_0x0231;
                case 1: goto L_0x022e;
                case 2: goto L_0x022b;
                case 3: goto L_0x0228;
                case 4: goto L_0x0225;
                case 5: goto L_0x0222;
                case 6: goto L_0x021d;
                case 7: goto L_0x0218;
                case 8: goto L_0x0215;
                case 9: goto L_0x0212;
                case 10: goto L_0x020f;
                case 11: goto L_0x020c;
                case 12: goto L_0x0209;
                case 13: goto L_0x0206;
                case 14: goto L_0x0203;
                case 15: goto L_0x0200;
                case 16: goto L_0x01fd;
                case 17: goto L_0x01fa;
                case 18: goto L_0x01f7;
                case 19: goto L_0x01f4;
                case 20: goto L_0x01f1;
                case 21: goto L_0x01ee;
                case 22: goto L_0x01eb;
                case 23: goto L_0x01eb;
                case 24: goto L_0x01e8;
                case 25: goto L_0x01e5;
                case 26: goto L_0x01e1;
                default: goto L_0x01df;
            }
        L_0x01df:
            goto L_0x0233
        L_0x01e1:
            java.lang.String r8 = "/ws/shield/trafficlights/realtime"
            goto L_0x0233
        L_0x01e5:
            java.lang.String r8 = "/ws/shield/location/collection/autodata"
            goto L_0x0233
        L_0x01e8:
            java.lang.String r8 = "/ws/mps/routingtiles"
            goto L_0x0233
        L_0x01eb:
            java.lang.String r8 = "/"
            goto L_0x0233
        L_0x01ee:
            java.lang.String r8 = "/ws/shield/amap/routing/misc"
            goto L_0x0233
        L_0x01f1:
            java.lang.String r8 = ""
            goto L_0x0233
        L_0x01f4:
            java.lang.String r8 = ""
            goto L_0x0233
        L_0x01f7:
            java.lang.String r8 = "/ws/shield/navigation/motor_off_route_report/"
            goto L_0x0233
        L_0x01fa:
            java.lang.String r8 = "/ws/shield/navigation/motor_traffic_report/"
            goto L_0x0233
        L_0x01fd:
            java.lang.String r8 = "/ws/shield/motor-route/cross/"
            goto L_0x0233
        L_0x0200:
            java.lang.String r8 = "/ws/shield/motor-route/etatrafficupdate/"
            goto L_0x0233
        L_0x0203:
            java.lang.String r8 = "/ws/shield/motor-route/route/"
            goto L_0x0233
        L_0x0206:
            java.lang.String r8 = "/ws/oss/escort/task/update/"
            goto L_0x0233
        L_0x0209:
            java.lang.String r8 = "/ws/shield/amap/routing/escort/"
            goto L_0x0233
        L_0x020c:
            java.lang.String r8 = "/ws/shield/open_di/jiache_deepinfo/"
            goto L_0x0233
        L_0x020f:
            java.lang.String r8 = "/ws/shield/traffic/dynamicinfo/queryWeatherByLink/"
            goto L_0x0233
        L_0x0212:
            java.lang.String r8 = "/ws/transfer/auth/navigation/off_route_report/"
            goto L_0x0233
        L_0x0215:
            java.lang.String r8 = "/ws/transfer/auth/traffic/threeD_engine/"
            goto L_0x0233
        L_0x0218:
            java.lang.String r8 = a()
            goto L_0x0233
        L_0x021d:
            java.lang.String r8 = a()
            goto L_0x0233
        L_0x0222:
            java.lang.String r8 = "/ws/shield/traffic/tunnel/"
            goto L_0x0233
        L_0x0225:
            java.lang.String r8 = "/ws/shield/traffic/tunnel/"
            goto L_0x0233
        L_0x0228:
            java.lang.String r8 = "/ws/shield/traffic/tunnel/"
            goto L_0x0233
        L_0x022b:
            java.lang.String r8 = "/ws/transfer/navigation/trafficreport/"
            goto L_0x0233
        L_0x022e:
            java.lang.String r8 = "/ws/shield/truck/etatrafficupdate/"
            goto L_0x0233
        L_0x0231:
            java.lang.String r8 = "/ws/transfer/navigation/etatrafficupdate/"
        L_0x0233:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r0)
            r11.append(r8)
            java.lang.String r11 = r11.toString()
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.clg.getUrlInfo(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0028  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x001d  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a() {
        /*
            lo r0 = defpackage.lo.a()
            java.lang.String r1 = "vectorCrossConfig"
            java.lang.String r0 = r0.a(r1)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x001a
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0016 }
            r1.<init>(r0)     // Catch:{ JSONException -> 0x0016 }
            goto L_0x001b
        L_0x0016:
            r0 = move-exception
            r0.printStackTrace()
        L_0x001a:
            r1 = 0
        L_0x001b:
            if (r1 == 0) goto L_0x0024
            java.lang.String r0 = "grayVector"
            int r0 = r1.optInt(r0)
            goto L_0x0025
        L_0x0024:
            r0 = 0
        L_0x0025:
            r1 = 1
            if (r0 != r1) goto L_0x002b
            java.lang.String r0 = "/ws/transfer/auth/gray_vector_cross/"
            goto L_0x002d
        L_0x002b:
            java.lang.String r0 = "/ws/transfer/auth/new_vector_cross/"
        L_0x002d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.clg.a():java.lang.String");
    }
}
