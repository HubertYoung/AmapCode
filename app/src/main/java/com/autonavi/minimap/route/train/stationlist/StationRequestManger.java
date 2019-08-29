package com.autonavi.minimap.route.train.stationlist;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.Callback;
import com.autonavi.minimap.coach.CoachRequestHolder;
import com.autonavi.minimap.coach.param.ArrivalStationRequest;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import com.autonavi.minimap.route.coach.net.CoachArrivalCallback;
import java.io.UnsupportedEncodingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StationRequestManger {
    private static volatile StationRequestManger a;
    /* access modifiers changed from: private */
    public static ejr b;
    /* access modifiers changed from: private */
    public static ejr c;
    private static StationCallback d;
    private static StationCallback e;
    private static a f;
    private static Callback<JSONObject> g = new Callback<JSONObject>() {
        public final void callback(JSONObject jSONObject) {
            AMapLog.e("qiujunhui", String.valueOf(jSONObject));
            if (jSONObject == null) {
                StationRequestManger.a(false, null);
                return;
            }
            boolean optBoolean = jSONObject.optBoolean("result", false);
            int optInt = jSONObject.optInt("code", -1);
            jSONObject.optString("message", "");
            jSONObject.optString("version", "");
            JSONArray optJSONArray = jSONObject.optJSONArray("data");
            if (!optBoolean || optInt != 1) {
                StationRequestManger.a(false, null);
            } else if (optJSONArray == null) {
                StationRequestManger.a(false, null);
            } else {
                StationRequestManger.a(true, optJSONArray);
            }
        }

        public final void error(Throwable th, boolean z) {
            StationRequestManger.a(false, null);
        }
    };

    static class StationCallback extends FalconAosPrepareResponseCallback<JSONObject> {
        private int a = 0;
        /* access modifiers changed from: private */
        public boolean b = false;

        public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
            return b(aosByteResponse);
        }

        public final /* synthetic */ void a(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            if (bno.a) {
                StringBuilder sb = new StringBuilder("callback");
                sb.append(Thread.currentThread());
                sb.append(", id:");
                sb.append(Thread.currentThread().getId());
                sb.append(jSONObject);
                AMapLog.e("tylorvan", sb.toString());
            }
            this.b = false;
            if (jSONObject != null && StationRequestManger.b != null) {
                boolean optBoolean = jSONObject.optBoolean("result", false);
                int optInt = jSONObject.optInt("code", -1);
                jSONObject.optString("message", "");
                jSONObject.optString("version", "");
                JSONObject optJSONObject = jSONObject.optJSONObject("data");
                if (optBoolean && optInt == 1 && optJSONObject != null) {
                    if (this.a == 0) {
                        if (StationRequestManger.b.a(optJSONObject)) {
                            ejs.a().a(this.a, System.currentTimeMillis());
                        }
                    } else if (this.a == 1 && StationRequestManger.c.a(optJSONObject)) {
                        ejs.a().a(this.a, System.currentTimeMillis());
                    }
                }
            }
        }

        public StationCallback(int i) {
            this.a = i;
        }

        private static JSONObject b(AosByteResponse aosByteResponse) {
            byte[] bArr = (byte[]) aosByteResponse.getResult();
            if (bArr == null) {
                return null;
            }
            try {
                if (bArr.length > 0) {
                    return new JSONObject(new String(bArr, "UTF-8").trim());
                }
                return null;
            } catch (UnsupportedEncodingException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
            this.b = false;
        }
    }

    public interface a {
        void a(boolean z, JSONArray jSONArray);
    }

    private StationRequestManger() {
        b = new ejr(0);
        c = new ejr(1);
        d = new StationCallback(0);
        e = new StationCallback(1);
    }

    public static StationRequestManger a() {
        if (a == null) {
            synchronized (StationRequestManger.class) {
                try {
                    if (a == null) {
                        a = new StationRequestManger();
                    }
                }
            }
        }
        return a;
    }

    public static void a(a aVar) {
        f = aVar;
    }

    static ejr b(int i) {
        if (i == 0) {
            return b;
        }
        if (i == 1) {
            return c;
        }
        return null;
    }

    public final synchronized boolean a(String str) {
        CoachArrivalCallback coachArrivalCallback = new CoachArrivalCallback(g, str);
        ArrivalStationRequest arrivalStationRequest = new ArrivalStationRequest();
        arrivalStationRequest.b = str;
        CoachRequestHolder.getInstance().sendArrivalStation(arrivalStationRequest, coachArrivalCallback);
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x004d, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean a(int r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            r0 = 1
            if (r5 != 0) goto L_0x0009
            com.autonavi.minimap.route.train.stationlist.StationRequestManger$StationCallback r1 = d     // Catch:{ all -> 0x0007 }
            goto L_0x000f
        L_0x0007:
            r5 = move-exception
            goto L_0x004a
        L_0x0009:
            if (r5 != r0) goto L_0x000e
            com.autonavi.minimap.route.train.stationlist.StationRequestManger$StationCallback r1 = e     // Catch:{ all -> 0x0007 }
            goto L_0x000f
        L_0x000e:
            r1 = 0
        L_0x000f:
            r2 = 0
            if (r1 == 0) goto L_0x004c
            boolean r3 = r1.b     // Catch:{ all -> 0x0007 }
            if (r3 == 0) goto L_0x0019
            goto L_0x004c
        L_0x0019:
            ejs r3 = defpackage.ejs.a()     // Catch:{ all -> 0x0007 }
            boolean r3 = r3.c(r5)     // Catch:{ all -> 0x0007 }
            if (r3 != 0) goto L_0x0025
            monitor-exit(r4)
            return r2
        L_0x0025:
            r1.b = true     // Catch:{ all -> 0x0007 }
            if (r5 != 0) goto L_0x002e
            java.lang.String r2 = "train"
            goto L_0x0030
        L_0x002e:
            java.lang.String r2 = "coach"
        L_0x0030:
            com.autonavi.minimap.train.param.StaticStationsRequest r3 = new com.autonavi.minimap.train.param.StaticStationsRequest     // Catch:{ all -> 0x0007 }
            r3.<init>()     // Catch:{ all -> 0x0007 }
            r3.b = r2     // Catch:{ all -> 0x0007 }
            ejs r2 = defpackage.ejs.a()     // Catch:{ all -> 0x0007 }
            java.lang.String r5 = r2.b(r5)     // Catch:{ all -> 0x0007 }
            r3.c = r5     // Catch:{ all -> 0x0007 }
            com.autonavi.minimap.train.TrainRequestHolder r5 = com.autonavi.minimap.train.TrainRequestHolder.getInstance()     // Catch:{ all -> 0x0007 }
            r5.sendStaticStations(r3, r1)     // Catch:{ all -> 0x0007 }
            monitor-exit(r4)
            return r0
        L_0x004a:
            monitor-exit(r4)
            throw r5
        L_0x004c:
            monitor-exit(r4)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.train.stationlist.StationRequestManger.a(int):boolean");
    }

    static /* synthetic */ void a(boolean z, JSONArray jSONArray) {
        if (f != null) {
            f.a(z, jSONArray);
        }
    }
}
