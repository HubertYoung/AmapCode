package com.autonavi.minimap.drive.navi.navitts.net;

import android.text.TextUtils;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONObject;

public final class RequestAllVoiceInfo {
    private static final ahn b = new ahn(1);
    public ReentrantLock a = new ReentrantLock();

    public class RequestAllVoiceCallback implements AosResponseCallback<AosByteResponse> {
        private dgx b;

        /* JADX WARNING: Can't wrap try/catch for region: R(10:42|43|44|45|46|47|48|49|50|(1:52)) */
        /* JADX WARNING: Code restructure failed: missing block: B:60:0x0103, code lost:
            if (com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo.a(r7.a).isHeldByCurrentThread() != false) goto L_0x0105;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:61:0x0105, code lost:
            com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo.a(r7.a).unlock();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:62:0x010e, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:0x010f, code lost:
            r8 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:65:0x011a, code lost:
            if (com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo.a(r7.a).isHeldByCurrentThread() != false) goto L_0x011c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:66:0x011c, code lost:
            com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo.a(r7.a).unlock();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:67:0x0125, code lost:
            throw r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:70:0x0130, code lost:
            if (com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo.a(r7.a).isHeldByCurrentThread() == false) goto L_0x0133;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:45:0x00cd */
        /* JADX WARNING: Missing exception handler attribute for start block: B:49:0x00d6 */
        /* JADX WARNING: Removed duplicated region for block: B:52:0x00e4 A[Catch:{ Exception -> 0x0126, all -> 0x010f }] */
        /* JADX WARNING: Removed duplicated region for block: B:63:0x010f A[ExcHandler: all (r8v5 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:45:0x00cd] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public /* synthetic */ void onSuccess(com.amap.bundle.aosservice.response.AosResponse r8) {
            /*
                r7 = this;
                com.amap.bundle.aosservice.response.AosByteResponse r8 = (com.amap.bundle.aosservice.response.AosByteResponse) r8
                r0 = 0
                if (r8 == 0) goto L_0x0020
                java.lang.Object r1 = r8.getResult()     // Catch:{ Exception -> 0x0020 }
                byte[] r1 = (byte[]) r1     // Catch:{ Exception -> 0x0020 }
                if (r1 == 0) goto L_0x0020
                java.lang.String r2 = new java.lang.String     // Catch:{ Exception -> 0x0020 }
                java.lang.String r3 = "UTF-8"
                r2.<init>(r1, r3)     // Catch:{ Exception -> 0x0020 }
                boolean r1 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x0020 }
                if (r1 != 0) goto L_0x0020
                org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x0020 }
                r1.<init>(r2)     // Catch:{ Exception -> 0x0020 }
                r0 = r1
            L_0x0020:
                r1 = 0
                if (r0 != 0) goto L_0x002d
                dgx r8 = r7.b
                if (r8 == 0) goto L_0x002c
                dgx r8 = r7.b
                r8.a(r1)
            L_0x002c:
                return
            L_0x002d:
                java.lang.String r2 = "pp_info"
                org.json.JSONObject r2 = r0.optJSONObject(r2)
                if (r2 != 0) goto L_0x0040
                java.lang.String r2 = "0"
                defpackage.dhc.a(r2)
                java.lang.String r2 = ""
                defpackage.dhc.b(r2)
                goto L_0x0052
            L_0x0040:
                java.lang.String r3 = "pp_switch"
                java.lang.String r3 = r2.optString(r3)
                defpackage.dhc.a(r3)
                java.lang.String r3 = "download_path"
                java.lang.String r2 = r2.optString(r3)
                defpackage.dhc.b(r2)
            L_0x0052:
                java.lang.String r2 = "voice"
                org.json.JSONObject r0 = r0.optJSONObject(r2)
                if (r0 == 0) goto L_0x0133
                java.lang.String r2 = "version"
                java.lang.String r2 = r0.optString(r2)     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                java.lang.String r3 = defpackage.dfo.e()     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                int r2 = com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo.b(r2, r3)     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                if (r2 >= 0) goto L_0x0082
                com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo r8 = com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo.this
                java.util.concurrent.locks.ReentrantLock r8 = r8.a
                boolean r8 = r8.isHeldByCurrentThread()
                if (r8 == 0) goto L_0x0081
                com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo r8 = com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo.this
                java.util.concurrent.locks.ReentrantLock r8 = r8.a
                r8.unlock()
            L_0x0081:
                return
            L_0x0082:
                java.lang.String r2 = "dialects"
                org.json.JSONArray r0 = r0.optJSONArray(r2)     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                r2 = 1
                if (r0 == 0) goto L_0x00e9
                java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                r3.<init>()     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                int r4 = r0.length()     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                r5 = 0
            L_0x0095:
                if (r5 >= r4) goto L_0x00a7
                org.json.JSONObject r6 = r0.optJSONObject(r5)     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                if (r6 == 0) goto L_0x00a4
                tw r6 = a(r6)     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                r3.add(r6)     // Catch:{ Exception -> 0x0126, all -> 0x010f }
            L_0x00a4:
                int r5 = r5 + 1
                goto L_0x0095
            L_0x00a7:
                java.lang.String r0 = "RequestAllVoiceInfo"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                java.lang.String r5 = "RequestAllVoiceCallback prepare end:"
                r4.<init>(r5)     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                int r5 = r3.size()     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                r4.append(r5)     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                defpackage.dhb.a(r0, r4)     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                int r0 = r3.size()     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                if (r0 <= 0) goto L_0x00e9
                dgm r0 = defpackage.dgm.a()     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                com.amap.bundle.drivecommon.voicesquare.AllVoiceDao r0 = r0.a     // Catch:{ Exception -> 0x00cd, all -> 0x010f }
                r0.deleteAll()     // Catch:{ Exception -> 0x00cd, all -> 0x010f }
            L_0x00cd:
                dgm r0 = defpackage.dgm.a()     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                com.amap.bundle.drivecommon.voicesquare.AllVoiceDao r0 = r0.a     // Catch:{ Exception -> 0x00d6, all -> 0x010f }
                r0.insertOrReplaceInTx(r3)     // Catch:{ Exception -> 0x00d6, all -> 0x010f }
            L_0x00d6:
                int r0 = defpackage.dhd.g()     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                defpackage.dfo.a(r0)     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                defpackage.dfo.l()     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                dgx r0 = r7.b     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                if (r0 == 0) goto L_0x00e9
                dgx r0 = r7.b     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                r0.a(r2)     // Catch:{ Exception -> 0x0126, all -> 0x010f }
            L_0x00e9:
                dgx r0 = r7.b     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                if (r0 == 0) goto L_0x00f9
                dgx r0 = r7.b     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                java.lang.Object r8 = r8.getResult()     // Catch:{ Exception -> 0x0126, all -> 0x010f }
                if (r8 == 0) goto L_0x00f6
                r1 = 1
            L_0x00f6:
                r0.a(r1)     // Catch:{ Exception -> 0x0126, all -> 0x010f }
            L_0x00f9:
                com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo r8 = com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo.this
                java.util.concurrent.locks.ReentrantLock r8 = r8.a
                boolean r8 = r8.isHeldByCurrentThread()
                if (r8 == 0) goto L_0x0133
            L_0x0105:
                com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo r8 = com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo.this
                java.util.concurrent.locks.ReentrantLock r8 = r8.a
                r8.unlock()
                return
            L_0x010f:
                r8 = move-exception
                com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo r0 = com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo.this
                java.util.concurrent.locks.ReentrantLock r0 = r0.a
                boolean r0 = r0.isHeldByCurrentThread()
                if (r0 == 0) goto L_0x0125
                com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo r0 = com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo.this
                java.util.concurrent.locks.ReentrantLock r0 = r0.a
                r0.unlock()
            L_0x0125:
                throw r8
            L_0x0126:
                com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo r8 = com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo.this
                java.util.concurrent.locks.ReentrantLock r8 = r8.a
                boolean r8 = r8.isHeldByCurrentThread()
                if (r8 == 0) goto L_0x0133
                goto L_0x0105
            L_0x0133:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.navi.navitts.net.RequestAllVoiceInfo.RequestAllVoiceCallback.onSuccess(com.amap.bundle.aosservice.response.AosResponse):void");
        }

        public RequestAllVoiceCallback(dgx dgx) {
            this.b = dgx;
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            if (this.b != null) {
                this.b.a(false);
            }
            if (RequestAllVoiceInfo.this.a.isHeldByCurrentThread()) {
                RequestAllVoiceInfo.this.a.unlock();
            }
        }

        private static tw a(JSONObject jSONObject) {
            double d;
            tw twVar = new tw();
            twVar.b = jSONObject.optString("hidden");
            twVar.c = jSONObject.optString("name");
            twVar.d = jSONObject.optString("url");
            twVar.f = jSONObject.optString("subname");
            twVar.h = jSONObject.optInt("recommend_flag");
            twVar.i = jSONObject.optString("md5");
            twVar.j = jSONObject.optString("subimage");
            twVar.k = jSONObject.optString("image");
            twVar.l = jSONObject.optString("name2");
            twVar.m = jSONObject.optString("try_url");
            twVar.n = jSONObject.optString("desc");
            twVar.o = 8;
            if (dfo.q()) {
                lu luVar = lt.a().d;
                if ((luVar.g != null ? luVar.g.booleanValue() : false) && TextUtils.equals(twVar.f, "linzhilingyuyin")) {
                    twVar.e = "4.0";
                    dfo.p();
                    d = Double.valueOf(jSONObject.optString("size").toUpperCase(Locale.US).replace(DiskFormatter.MB, "")).doubleValue() * 1024.0d * 1024.0d;
                    twVar.g = (long) d;
                    twVar.p = jSONObject.optString("src_code");
                    return twVar;
                }
            }
            twVar.e = jSONObject.optString("version");
            try {
                d = Double.valueOf(jSONObject.optString("size").toUpperCase(Locale.US).replace(DiskFormatter.MB, "")).doubleValue() * 1024.0d * 1024.0d;
            } catch (NumberFormatException unused) {
                d = 0.0d;
            }
            twVar.g = (long) d;
            twVar.p = jSONObject.optString("src_code");
            return twVar;
        }
    }

    /* access modifiers changed from: private */
    public static int b(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        if (TextUtils.isEmpty(str2)) {
            return 1;
        }
        if (str.equals(str2)) {
            return 0;
        }
        String replace = str.replace(".", "_");
        String replace2 = str2.replace(".", "_");
        String[] split = replace.split("_");
        String[] split2 = replace2.split("_");
        int length = split2.length;
        if (length < split.length) {
            length = split.length;
        }
        int i = 0;
        while (i < length) {
            if (i >= split.length) {
                return -1;
            }
            if (i >= split2.length) {
                return 1;
            }
            try {
                double parseDouble = Double.parseDouble(split2[i]);
                double parseDouble2 = Double.parseDouble(split[i]);
                if (parseDouble2 > parseDouble) {
                    return 1;
                }
                if (parseDouble > parseDouble2) {
                    return -1;
                }
                i++;
            } catch (NumberFormatException e) {
                kf.a((Throwable) e);
                return 2;
            }
        }
        return 0;
    }
}
