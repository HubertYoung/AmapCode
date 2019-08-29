package org.altbeacon.beacon.distance;

import android.content.Context;
import com.taobao.accs.common.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import org.altbeacon.beacon.b.d;
import org.json.JSONArray;
import org.json.JSONObject;

public final class ModelSpecificDistanceCalculator implements c {
    Map<a, c> a;
    private a b;
    private c c;
    private a d;
    private a e;
    private String f;
    private Context g;
    private final ReentrantLock h;

    public ModelSpecificDistanceCalculator(Context context) {
        this(context, a.a());
    }

    private ModelSpecificDistanceCalculator(Context context, a model) {
        this.f = null;
        this.h = new ReentrantLock();
        this.e = model;
        this.g = context;
        a();
        this.c = a(model);
    }

    public final double a(int txPower, double rssi) {
        if (this.c != null) {
            return this.c.a(txPower, rssi);
        }
        d.c("ModelSpecificDistanceCalculator", "distance calculator has not been set", new Object[0]);
        return -1.0d;
    }

    private c a(a model) {
        this.h.lock();
        try {
            return b(model);
        } finally {
            this.h.unlock();
        }
    }

    private c b(a model) {
        d.a("ModelSpecificDistanceCalculator", "Finding best distance calculator for %s, %s, %s, %s", model.b(), model.c(), model.d(), model.e());
        if (this.a == null) {
            d.a("ModelSpecificDistanceCalculator", "Cannot get distance calculator because modelMap was never initialized", new Object[0]);
            return null;
        }
        int highestScore = 0;
        a bestMatchingModel = null;
        for (a candidateModel : this.a.keySet()) {
            if (candidateModel.a(model) > highestScore) {
                highestScore = candidateModel.a(model);
                bestMatchingModel = candidateModel;
            }
        }
        if (bestMatchingModel != null) {
            d.a("ModelSpecificDistanceCalculator", "found a match with score %s", Integer.valueOf(highestScore));
            d.a("ModelSpecificDistanceCalculator", "Finding best distance calculator for %s, %s, %s, %s", bestMatchingModel.b(), bestMatchingModel.c(), bestMatchingModel.d(), bestMatchingModel.e());
            this.d = bestMatchingModel;
        } else {
            this.d = this.b;
            d.c("ModelSpecificDistanceCalculator", "Cannot find match for this device.  Using default", new Object[0]);
        }
        return this.a.get(this.d);
    }

    private void a() {
        boolean mapLoaded = false;
        if (this.f != null) {
            mapLoaded = b();
        }
        if (!mapLoaded) {
            c();
        }
        this.c = a(this.e);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x003a A[SYNTHETIC, Splitter:B:12:0x003a] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003f A[SYNTHETIC, Splitter:B:15:0x003f] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0090 A[SYNTHETIC, Splitter:B:38:0x0090] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0095 A[SYNTHETIC, Splitter:B:41:0x0095] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00b3 A[SYNTHETIC, Splitter:B:50:0x00b3] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00b8 A[SYNTHETIC, Splitter:B:53:0x00b8] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean b() {
        /*
            r14 = this;
            r7 = 1
            r8 = 0
            java.io.File r0 = new java.io.File
            android.content.Context r9 = r14.g
            java.io.File r9 = r9.getFilesDir()
            java.lang.String r10 = "model-distance-calculations.json"
            r0.<init>(r9, r10)
            r1 = 0
            r4 = 0
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x00fa, IOException -> 0x0080 }
            r2.<init>(r0)     // Catch:{ FileNotFoundException -> 0x00fa, IOException -> 0x0080 }
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ FileNotFoundException -> 0x00fd, IOException -> 0x00f3, all -> 0x00ec }
            java.io.InputStreamReader r9 = new java.io.InputStreamReader     // Catch:{ FileNotFoundException -> 0x00fd, IOException -> 0x00f3, all -> 0x00ec }
            r9.<init>(r2)     // Catch:{ FileNotFoundException -> 0x00fd, IOException -> 0x00f3, all -> 0x00ec }
            r5.<init>(r9)     // Catch:{ FileNotFoundException -> 0x00fd, IOException -> 0x00f3, all -> 0x00ec }
        L_0x0025:
            java.lang.String r3 = r5.readLine()     // Catch:{ FileNotFoundException -> 0x0035, IOException -> 0x00f6, all -> 0x00ef }
            if (r3 == 0) goto L_0x0044
            java.lang.StringBuilder r9 = r6.append(r3)     // Catch:{ FileNotFoundException -> 0x0035, IOException -> 0x00f6, all -> 0x00ef }
            java.lang.String r10 = "\n"
            r9.append(r10)     // Catch:{ FileNotFoundException -> 0x0035, IOException -> 0x00f6, all -> 0x00ef }
            goto L_0x0025
        L_0x0035:
            r7 = move-exception
            r4 = r5
            r1 = r2
        L_0x0038:
            if (r4 == 0) goto L_0x003d
            r4.close()     // Catch:{ Exception -> 0x006a }
        L_0x003d:
            if (r1 == 0) goto L_0x0042
            r1.close()     // Catch:{ Exception -> 0x0075 }
        L_0x0042:
            r7 = r8
        L_0x0043:
            return r7
        L_0x0044:
            r5.close()     // Catch:{ Exception -> 0x0054 }
        L_0x0047:
            r2.close()     // Catch:{ Exception -> 0x005f }
        L_0x004a:
            java.lang.String r9 = r6.toString()     // Catch:{ JSONException -> 0x00d2 }
            r14.a(r9)     // Catch:{ JSONException -> 0x00d2 }
            r4 = r5
            r1 = r2
            goto L_0x0043
        L_0x0054:
            r9 = move-exception
            java.lang.String r10 = "ModelSpecificDistanceCalculator"
            java.lang.String r11 = "close reader exception"
            java.lang.Object[] r12 = new java.lang.Object[r8]
            org.altbeacon.beacon.b.d.b(r9, r10, r11, r12)
            goto L_0x0047
        L_0x005f:
            r9 = move-exception
            java.lang.String r10 = "ModelSpecificDistanceCalculator"
            java.lang.String r11 = "close inputStream exception"
            java.lang.Object[] r12 = new java.lang.Object[r8]
            org.altbeacon.beacon.b.d.b(r9, r10, r11, r12)
            goto L_0x004a
        L_0x006a:
            r7 = move-exception
            java.lang.String r9 = "ModelSpecificDistanceCalculator"
            java.lang.String r10 = "close reader exception"
            java.lang.Object[] r11 = new java.lang.Object[r8]
            org.altbeacon.beacon.b.d.b(r7, r9, r10, r11)
            goto L_0x003d
        L_0x0075:
            r7 = move-exception
            java.lang.String r9 = "ModelSpecificDistanceCalculator"
            java.lang.String r10 = "close inputStream exception"
            java.lang.Object[] r11 = new java.lang.Object[r8]
            org.altbeacon.beacon.b.d.b(r7, r9, r10, r11)
            goto L_0x0042
        L_0x0080:
            r7 = move-exception
        L_0x0081:
            java.lang.String r9 = "ModelSpecificDistanceCalculator"
            java.lang.String r10 = "Cannot open distance model file %s"
            r11 = 1
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ all -> 0x00b0 }
            r12 = 0
            r11[r12] = r0     // Catch:{ all -> 0x00b0 }
            org.altbeacon.beacon.b.d.b(r7, r9, r10, r11)     // Catch:{ all -> 0x00b0 }
            if (r4 == 0) goto L_0x0093
            r4.close()     // Catch:{ Exception -> 0x009a }
        L_0x0093:
            if (r1 == 0) goto L_0x0098
            r1.close()     // Catch:{ Exception -> 0x00a5 }
        L_0x0098:
            r7 = r8
            goto L_0x0043
        L_0x009a:
            r7 = move-exception
            java.lang.String r9 = "ModelSpecificDistanceCalculator"
            java.lang.String r10 = "close reader exception"
            java.lang.Object[] r11 = new java.lang.Object[r8]
            org.altbeacon.beacon.b.d.b(r7, r9, r10, r11)
            goto L_0x0093
        L_0x00a5:
            r7 = move-exception
            java.lang.String r9 = "ModelSpecificDistanceCalculator"
            java.lang.String r10 = "close inputStream exception"
            java.lang.Object[] r11 = new java.lang.Object[r8]
            org.altbeacon.beacon.b.d.b(r7, r9, r10, r11)
            goto L_0x0098
        L_0x00b0:
            r7 = move-exception
        L_0x00b1:
            if (r4 == 0) goto L_0x00b6
            r4.close()     // Catch:{ Exception -> 0x00bc }
        L_0x00b6:
            if (r1 == 0) goto L_0x00bb
            r1.close()     // Catch:{ Exception -> 0x00c7 }
        L_0x00bb:
            throw r7
        L_0x00bc:
            r9 = move-exception
            java.lang.String r10 = "ModelSpecificDistanceCalculator"
            java.lang.String r11 = "close reader exception"
            java.lang.Object[] r12 = new java.lang.Object[r8]
            org.altbeacon.beacon.b.d.b(r9, r10, r11, r12)
            goto L_0x00b6
        L_0x00c7:
            r9 = move-exception
            java.lang.String r10 = "ModelSpecificDistanceCalculator"
            java.lang.String r11 = "close inputStream exception"
            java.lang.Object[] r8 = new java.lang.Object[r8]
            org.altbeacon.beacon.b.d.b(r9, r10, r11, r8)
            goto L_0x00bb
        L_0x00d2:
            r9 = move-exception
            java.lang.String r10 = "ModelSpecificDistanceCalculator"
            java.lang.String r11 = "Cannot update distance models from online database at %s with JSON: %s"
            r12 = 2
            java.lang.Object[] r12 = new java.lang.Object[r12]
            java.lang.String r13 = r14.f
            r12[r8] = r13
            java.lang.String r13 = r6.toString()
            r12[r7] = r13
            org.altbeacon.beacon.b.d.b(r9, r10, r11, r12)
            r4 = r5
            r1 = r2
            r7 = r8
            goto L_0x0043
        L_0x00ec:
            r7 = move-exception
            r1 = r2
            goto L_0x00b1
        L_0x00ef:
            r7 = move-exception
            r4 = r5
            r1 = r2
            goto L_0x00b1
        L_0x00f3:
            r7 = move-exception
            r1 = r2
            goto L_0x0081
        L_0x00f6:
            r7 = move-exception
            r4 = r5
            r1 = r2
            goto L_0x0081
        L_0x00fa:
            r7 = move-exception
            goto L_0x0038
        L_0x00fd:
            r7 = move-exception
            r1 = r2
            goto L_0x0038
        */
        throw new UnsupportedOperationException("Method not decompiled: org.altbeacon.beacon.distance.ModelSpecificDistanceCalculator.b():boolean");
    }

    private void a(String jsonString) {
        this.h.lock();
        try {
            b(jsonString);
        } finally {
            this.h.unlock();
        }
    }

    private void b(String jsonString) {
        this.a = new HashMap();
        JSONArray array = new JSONObject(jsonString).getJSONArray("models");
        for (int i = 0; i < array.length(); i++) {
            JSONObject modelObject = array.getJSONObject(i);
            boolean defaultFlag = false;
            if (modelObject.has("default")) {
                defaultFlag = modelObject.getBoolean("default");
            }
            Double coefficient1 = Double.valueOf(modelObject.getDouble("coefficient1"));
            Double coefficient2 = Double.valueOf(modelObject.getDouble("coefficient2"));
            Double coefficient3 = Double.valueOf(modelObject.getDouble("coefficient3"));
            String version = modelObject.getString("version");
            String buildNumber = modelObject.getString("build_number");
            String model = modelObject.getString(Constants.KEY_MODEL);
            String manufacturer = modelObject.getString("manufacturer");
            b distanceCalculator = new b(coefficient1.doubleValue(), coefficient2.doubleValue(), coefficient3.doubleValue());
            a androidModel = new a(version, buildNumber, model, manufacturer);
            this.a.put(androidModel, distanceCalculator);
            if (defaultFlag) {
                this.b = androidModel;
            }
        }
    }

    private void c() {
        this.a = new HashMap();
        try {
            b((String) "{\n  \"models\":\n  [\n    {\n      \"coefficient1\": 0.42093,\n      \"coefficient2\": 6.9476,\n      \"coefficient3\": 0.54992,\n      \"version\":\"4.4.2\",\n      \"build_number\":\"KOT49H\",\n      \"model\":\"Nexus 4\",\n      \"manufacturer\":\"LGE\"\n    },\n    {\n      \"coefficient1\": 0.42093,\n      \"coefficient2\": 6.9476,\n      \"coefficient3\": 0.54992,\n      \"version\":\"4.4.2\",\n      \"build_number\":\"LPV79\",\n      \"model\":\"Nexus 5\",\n      \"manufacturer\":\"LGE\",\n      \"default\": true\n    },\n    {\n      \"coefficient1\": 0.9401940951,\n      \"coefficient2\": 6.170094565,\n      \"coefficient3\": 0.0,\n      \"version\":\"5.0.2\",\n      \"build_number\":\"LXG22.67-7.1\",\n      \"model\":\"Moto X Pro\",\n      \"manufacturer\":\"XT1115\",\n      \"default\": false\n    },\n    {\n      \"coefficient1\": 0.1862616782,\n      \"coefficient2\": 8.235367435,\n      \"coefficient3\": -0.45324519,\n      \"version\":\"6.0\",\n      \"build_number\":\"MPE24.49-18\",\n      \"model\":\"XT1092\",\n      \"manufacturer\":\"Motorola\",\n      \"default\": false\n    }\n  ]\n}\n");
        } catch (Exception e2) {
            d.b(e2, "ModelSpecificDistanceCalculator", "Cannot build model distance calculations", new Object[0]);
        }
    }
}
