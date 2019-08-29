package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.minimap.route.train.model.TrainPlanBaseInfoItem;
import com.autonavi.minimap.route.train.model.TrainPlanBaseInfoItem.a;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ejh reason: default package */
/* compiled from: AosTrainPlanDetailResponser */
public final class ejh extends AbstractAOSParser {
    public ArrayList<TrainPlanBaseInfoItem> a;
    public boolean b;
    SimpleDateFormat c = new SimpleDateFormat("HH:mm");
    public boolean d;
    public String e;
    public int f = -1;
    public boolean g = true;
    private int h;
    private String i = "成功";
    private String j = "无火车方案";
    private String k = "请求参数错误";
    private String l = "后端服务异常";
    private String m = "其他错误";

    public final String getErrorDesc(int i2) {
        return null;
    }

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        parseHeader(bArr);
        StringBuilder sb = new StringBuilder("AosTrainPlanResponser");
        sb.append(new String(bArr));
        eao.e("tylorvan", sb.toString());
        this.a = new ArrayList<>();
        boolean z = true;
        if (this.errorCode == 1) {
            try {
                JSONObject jSONObject = new JSONObject(new String(bArr, "UTF-8")).getJSONObject("data");
                this.d = jSONObject.optInt("samecity") == 1;
                this.f = jSONObject.optInt("why");
                int i2 = this.f;
                String str = "";
                switch (i2) {
                    case 0:
                        str = this.i;
                        break;
                    case 1:
                        str = this.j;
                        break;
                    case 2:
                        str = this.k;
                        break;
                    case 3:
                        str = this.l;
                        break;
                    case 4:
                        str = this.m;
                        break;
                }
                if (i2 / 10 == 4) {
                    str = this.j;
                    if (i2 % 10 == 1) {
                        StringBuilder sb2 = new StringBuilder("AosTrain");
                        sb2.append(str);
                        sb2.append("-->请求参数非法");
                        eao.e("tylorvan", sb2.toString());
                    }
                    if (i2 % 10 == 2) {
                        StringBuilder sb3 = new StringBuilder("AosTrain");
                        sb3.append(str);
                        sb3.append("-->跨城火车无结果");
                        eao.e("tylorvan", sb3.toString());
                    }
                    if (i2 % 10 == 3) {
                        StringBuilder sb4 = new StringBuilder("AosTrain");
                        sb4.append(str);
                        sb4.append("-->服务异常");
                        eao.e("tylorvan", sb4.toString());
                    }
                }
                this.e = str;
                this.h = jSONObject.optInt(NewHtcHomeBadger.COUNT);
                if (jSONObject.optInt("service_switch", 1) != 1) {
                    z = false;
                }
                this.g = z;
                JSONArray jSONArray = jSONObject.getJSONArray("routelist");
                for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                    this.a.add(a(jSONArray.getJSONObject(i3)));
                }
                StringBuilder sb5 = new StringBuilder("mResultList");
                sb5.append(this.a.size());
                eao.e("tylorvan", sb5.toString());
            } catch (Exception e2) {
                e2.printStackTrace();
                StringBuilder sb6 = new StringBuilder("Exception");
                sb6.append(e2.getMessage());
                eao.e("tylorvan", sb6.toString());
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00de, code lost:
        r5 = 0.0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0151, code lost:
        r13 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0152, code lost:
        r13.printStackTrace();
        r2 = new java.lang.StringBuilder("Exception");
        r2.append(r13.getMessage());
        defpackage.eao.e("tylorvan", r2.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x016d, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x016e, code lost:
        r13 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x016f, code lost:
        r13.printStackTrace();
        r2 = new java.lang.StringBuilder("Exception");
        r2.append(r13.getMessage());
        defpackage.eao.e("tylorvan", r2.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x018a, code lost:
        return null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x016e A[ExcHandler: JSONException (r13v1 'e' org.json.JSONException A[CUSTOM_DECLARE]), Splitter:B:1:0x0009] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.autonavi.minimap.route.train.model.TrainPlanBaseInfoItem a(org.json.JSONObject r13) {
        /*
            r12 = this;
            com.autonavi.minimap.route.train.model.TrainPlanBaseInfoItem r0 = new com.autonavi.minimap.route.train.model.TrainPlanBaseInfoItem
            r0.<init>()
            r1 = 0
            java.lang.String r2 = "tag"
            r3 = -1
            int r2 = r13.optInt(r2, r3)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.sortTag = r2     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r2 = "time"
            int r2 = r13.optInt(r2)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.totalTimeCost = r2     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r2 = "segments"
            org.json.JSONArray r13 = r13.getJSONArray(r2)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r2 = 0
            org.json.JSONArray r13 = r13.getJSONArray(r2)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            org.json.JSONArray r13 = r13.getJSONArray(r2)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r3 = 1
            org.json.JSONObject r13 = r13.getJSONObject(r3)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r4 = "time"
            int r4 = r13.optInt(r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.trainRunningTime = r4     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r4 = "id"
            java.lang.String r4 = r13.optString(r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.trainPlanId = r4     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r4 = "type"
            int r4 = r13.optInt(r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.trainType = r4     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r4 = "trip"
            java.lang.String r4 = r13.optString(r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.trip = r4     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r4 = r0.trip     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            if (r4 == 0) goto L_0x005a
            java.lang.String r4 = "unknown"
            r0.trip = r4     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
        L_0x005a:
            java.lang.String r4 = "sstid"
            java.lang.String r4 = r13.optString(r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.trainDepartureStationId = r4     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r4 = "sst"
            java.lang.String r4 = r13.optString(r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.trainDepartureName = r4     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r4 = "scord"
            java.lang.String r4 = r13.optString(r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r5 = " "
            java.lang.String[] r4 = r4.split(r5)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            int r5 = r4.length     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r6 = 2
            r7 = 0
            if (r5 != r6) goto L_0x0092
            r5 = r4[r2]     // Catch:{ NumberFormatException -> 0x0089, JSONException -> 0x016e }
            double r9 = java.lang.Double.parseDouble(r5)     // Catch:{ NumberFormatException -> 0x0089, JSONException -> 0x016e }
            r4 = r4[r3]     // Catch:{ NumberFormatException -> 0x0089, JSONException -> 0x016e }
            double r4 = java.lang.Double.parseDouble(r4)     // Catch:{ NumberFormatException -> 0x0089, JSONException -> 0x016e }
            goto L_0x008b
        L_0x0089:
            r4 = r7
            r9 = r4
        L_0x008b:
            com.autonavi.common.model.GeoPoint r11 = new com.autonavi.common.model.GeoPoint     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r11.<init>(r9, r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.trainDepartureStationGeoPoint = r11     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
        L_0x0092:
            java.lang.String r4 = "sad"
            java.lang.String r4 = r13.optString(r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.trainDepartureStationAdcode = r4     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r4 = "sint"
            java.lang.String r4 = r13.optString(r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.trainDepartureTime = r4     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r4 = "sin"
            int r4 = r13.optInt(r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            if (r4 != r3) goto L_0x00ac
            r4 = 1
            goto L_0x00ad
        L_0x00ac:
            r4 = 0
        L_0x00ad:
            r0.isTrainDepartureAtFirstStation = r4     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r4 = "tstid"
            java.lang.String r4 = r13.optString(r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.trainArrivalStationId = r4     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r4 = "tst"
            java.lang.String r4 = r13.optString(r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.trainArrivalName = r4     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r4 = "tcord"
            java.lang.String r4 = r13.optString(r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r5 = " "
            java.lang.String[] r4 = r4.split(r5)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            int r5 = r4.length     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            if (r5 != r6) goto L_0x00e6
            r5 = r4[r2]     // Catch:{ NumberFormatException -> 0x00de, JSONException -> 0x016e }
            double r5 = java.lang.Double.parseDouble(r5)     // Catch:{ NumberFormatException -> 0x00de, JSONException -> 0x016e }
            r4 = r4[r3]     // Catch:{ NumberFormatException -> 0x00de, JSONException -> 0x016e }
            double r9 = java.lang.Double.parseDouble(r4)     // Catch:{ NumberFormatException -> 0x00de, JSONException -> 0x016e }
            r7 = r9
            goto L_0x00df
        L_0x00de:
            r5 = r7
        L_0x00df:
            com.autonavi.common.model.GeoPoint r4 = new com.autonavi.common.model.GeoPoint     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r4.<init>(r5, r7)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.trainArrivalStationGeoPoint = r4     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
        L_0x00e6:
            java.lang.String r4 = "tad"
            java.lang.String r4 = r13.optString(r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.trainArrivalStationAdcode = r4     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r4 = "tout"
            java.lang.String r4 = r13.optString(r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.trainArrivalTime = r4     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r4 = "tou"
            int r4 = r13.optInt(r4)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            if (r4 != r3) goto L_0x0101
            r2 = 1
        L_0x0101:
            r0.isTrainArrivalAtLastStation = r2     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.text.SimpleDateFormat r2 = r12.c     // Catch:{ Exception -> 0x0120 }
            java.lang.String r3 = r0.trainDepartureTime     // Catch:{ Exception -> 0x0120 }
            java.util.Date r2 = r2.parse(r3)     // Catch:{ Exception -> 0x0120 }
            long r2 = r2.getTime()     // Catch:{ Exception -> 0x0120 }
            r0.trainDepartureTimeToCompare = r2     // Catch:{ Exception -> 0x0120 }
            java.text.SimpleDateFormat r2 = r12.c     // Catch:{ Exception -> 0x0120 }
            java.lang.String r3 = r0.trainArrivalTime     // Catch:{ Exception -> 0x0120 }
            java.util.Date r2 = r2.parse(r3)     // Catch:{ Exception -> 0x0120 }
            long r2 = r2.getTime()     // Catch:{ Exception -> 0x0120 }
            r0.trainArrivalTimeToCompare = r2     // Catch:{ Exception -> 0x0120 }
            goto L_0x0126
        L_0x0120:
            r2 = 0
            r0.trainDepartureTimeToCompare = r2     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.trainArrivalTimeToCompare = r2     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
        L_0x0126:
            java.util.ArrayList r2 = a(r0, r13)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.viaStationInfos = r2     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r2 = "price"
            org.json.JSONObject r2 = r13.getJSONObject(r2)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.util.ArrayList r2 = b(r2)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.seatsRemainedList = r2     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.util.ArrayList<eiw> r2 = r0.seatsRemainedList     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.util.ArrayList r2 = defpackage.ejw.a(r2)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.seatsRemainedList = r2     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r2 = "restticket"
            int r2 = r13.optInt(r2)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.trainTicketRemainOfAllSeatType = r2     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            java.lang.String r2 = "dis"
            int r13 = r13.optInt(r2)     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            r0.trainDistance = r13     // Catch:{ JSONException -> 0x016e, NumberFormatException -> 0x0151 }
            return r0
        L_0x0151:
            r13 = move-exception
            r13.printStackTrace()
            java.lang.String r0 = "tylorvan"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Exception"
            r2.<init>(r3)
            java.lang.String r13 = r13.getMessage()
            r2.append(r13)
            java.lang.String r13 = r2.toString()
            defpackage.eao.e(r0, r13)
            return r1
        L_0x016e:
            r13 = move-exception
            r13.printStackTrace()
            java.lang.String r0 = "tylorvan"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Exception"
            r2.<init>(r3)
            java.lang.String r13 = r13.getMessage()
            r2.append(r13)
            java.lang.String r13 = r2.toString()
            defpackage.eao.e(r0, r13)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ejh.a(org.json.JSONObject):com.autonavi.minimap.route.train.model.TrainPlanBaseInfoItem");
    }

    private static ArrayList<a> a(TrainPlanBaseInfoItem trainPlanBaseInfoItem, JSONObject jSONObject) throws JSONException {
        ArrayList<a> arrayList = new ArrayList<>();
        try {
            String string = jSONObject.getString("viast");
            String string2 = jSONObject.getString("viastid");
            String string3 = jSONObject.getString("viastcord");
            String string4 = jSONObject.getString("viaint");
            String string5 = jSONObject.getString("viawait");
            if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string3) && !TextUtils.isEmpty(string4)) {
                if (!TextUtils.isEmpty(string5)) {
                    String[] split = string.split(Token.SEPARATOR);
                    String[] split2 = string2.split(Token.SEPARATOR);
                    String[] split3 = string3.split(Token.SEPARATOR);
                    String[] split4 = string4.split(Token.SEPARATOR);
                    String[] split5 = string5.split(Token.SEPARATOR);
                    int length = split.length;
                    if (length > 0 && length == split2.length && length == split3.length / 2 && length == split4.length && length == split5.length) {
                        arrayList = new ArrayList<>(length);
                        for (int i2 = 0; i2 < length; i2++) {
                            a newViaStationInfo = trainPlanBaseInfoItem.getNewViaStationInfo();
                            newViaStationInfo.a = split[i2];
                            newViaStationInfo.d = split2[i2];
                            newViaStationInfo.e = split4[i2];
                            newViaStationInfo.f = split5[i2];
                            int i3 = i2 * 2;
                            newViaStationInfo.b = Double.valueOf(Double.parseDouble(split3[i3]));
                            newViaStationInfo.c = Double.valueOf(Double.parseDouble(split3[i3 + 1]));
                            arrayList.add(newViaStationInfo);
                        }
                    }
                    return arrayList;
                }
            }
            return null;
        } catch (JSONException e2) {
            StringBuilder sb = new StringBuilder("Exception");
            sb.append(e2.getMessage());
            AMapLog.e("tylorvan", sb.toString());
            throw e2;
        }
    }

    private static ArrayList<eiw> b(JSONObject jSONObject) {
        ArrayList<eiw> arrayList = new ArrayList<>();
        if (jSONObject == null) {
            return null;
        }
        try {
            String string = jSONObject.getString("type");
            String string2 = jSONObject.getString("value");
            String string3 = jSONObject.getString(NewHtcHomeBadger.COUNT);
            String string4 = jSONObject.getString("tickettype");
            if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                if (!TextUtils.isEmpty(string3)) {
                    String[] split = string.split(Token.SEPARATOR);
                    String[] split2 = string2.split(Token.SEPARATOR);
                    String[] split3 = string3.split(Token.SEPARATOR);
                    String[] split4 = string4.split(Token.SEPARATOR);
                    int length = split.length;
                    if (length == split2.length && length == split3.length) {
                        if (length == split4.length) {
                            for (int i2 = 0; i2 < split.length; i2++) {
                                int parseInt = Integer.parseInt(split[i2]);
                                float parseFloat = Float.parseFloat(split2[i2]);
                                eiw eiw = new eiw(ejw.a(parseInt), Integer.parseInt(split3[i2]), parseFloat, parseInt, Integer.parseInt(split4[i2]));
                                arrayList.add(eiw);
                            }
                            return arrayList;
                        }
                    }
                    return null;
                }
            }
            return null;
        } catch (JSONException unused) {
            return null;
        } catch (NumberFormatException unused2) {
            return null;
        }
    }
}
