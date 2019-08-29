package com.alipay.android.phone.inside.wallet.encrypt;

import android.content.Context;
import android.provider.Settings.Secure;
import com.alipay.android.phone.inside.commonbiz.ids.RunningConfig;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class BizDataProvider {
    static final String KEY_ANDROID_ID = "androidId";
    static final String KEY_ORI_DATA = "oriData";
    static final String KEY_USER_ID = "userId";
    static final String KEY_UTDID = "utdid";
    static final String TAG = "inside";
    private boolean isEncrypt = false;

    public BizDataProvider(boolean z) {
        this.isEncrypt = z;
    }

    public String packageBizData(Context context, Map<String, String> map) throws Exception {
        try {
            String buildBizDataImpl = buildBizDataImpl(context, map);
            return this.isEncrypt ? SgStaticEncrypt.encrypt(context, buildBizDataImpl) : buildBizDataImpl;
        } catch (Exception e) {
            LoggerFactory.f().b(TAG, "BizDataProvider::buildBizData", e);
            throw e;
        }
    }

    public String analysisBizData(Context context, String str) throws Exception {
        try {
            if (this.isEncrypt) {
                str = SgStaticEncrypt.decrypt(context, str);
                if (!isMatch(context, str)) {
                    LoggerFactory.f().b((String) TAG, (String) "BizDataProvider::analysisBizData > !isMatch");
                }
            }
            return analysisBizDataImpl(str);
        } catch (Exception e) {
            LoggerFactory.f().b(TAG, "BizDataProvider::analysisBizData", e);
            throw e;
        }
    }

    private String analysisBizDataImpl(String str) throws JSONException {
        return new JSONObject(str).optJSONObject(KEY_ORI_DATA).toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x003b A[Catch:{ Throwable -> 0x008d }] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x004f A[Catch:{ Throwable -> 0x008d }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0086 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:31:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean isMatch(android.content.Context r8, java.lang.String r9) {
        /*
            r7 = this;
            r0 = 1
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Throwable -> 0x008d }
            r1.<init>(r9)     // Catch:{ Throwable -> 0x008d }
            java.lang.String r9 = "userId"
            java.lang.String r2 = ""
            java.lang.String r9 = r1.optString(r9, r2)     // Catch:{ Throwable -> 0x008d }
            java.lang.String r2 = "utdid"
            java.lang.String r3 = ""
            java.lang.String r2 = r1.optString(r2, r3)     // Catch:{ Throwable -> 0x008d }
            java.lang.String r3 = "androidId"
            java.lang.String r4 = ""
            java.lang.String r1 = r1.optString(r3, r4)     // Catch:{ Throwable -> 0x008d }
            boolean r3 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x008d }
            r4 = 0
            if (r3 != 0) goto L_0x0034
            java.lang.String r3 = r7.getUserId()     // Catch:{ Throwable -> 0x008d }
            boolean r9 = android.text.TextUtils.equals(r9, r3)     // Catch:{ Throwable -> 0x008d }
            if (r9 == 0) goto L_0x0032
            goto L_0x0034
        L_0x0032:
            r9 = 0
            goto L_0x0035
        L_0x0034:
            r9 = 1
        L_0x0035:
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x008d }
            if (r3 != 0) goto L_0x0048
            java.lang.String r3 = r7.getUtdid()     // Catch:{ Throwable -> 0x008d }
            boolean r2 = android.text.TextUtils.equals(r2, r3)     // Catch:{ Throwable -> 0x008d }
            if (r2 == 0) goto L_0x0046
            goto L_0x0048
        L_0x0046:
            r2 = 0
            goto L_0x0049
        L_0x0048:
            r2 = 1
        L_0x0049:
            boolean r3 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x008d }
            if (r3 != 0) goto L_0x005c
            java.lang.String r8 = r7.getAndroidId(r8)     // Catch:{ Throwable -> 0x008d }
            boolean r8 = android.text.TextUtils.equals(r1, r8)     // Catch:{ Throwable -> 0x008d }
            if (r8 == 0) goto L_0x005a
            goto L_0x005c
        L_0x005a:
            r8 = 0
            goto L_0x005d
        L_0x005c:
            r8 = 1
        L_0x005d:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ Throwable -> 0x008d }
            java.lang.String r3 = "inside"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x008d }
            java.lang.String r6 = "EncryptProvider::isMatch > "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x008d }
            r5.append(r9)     // Catch:{ Throwable -> 0x008d }
            java.lang.String r6 = ","
            r5.append(r6)     // Catch:{ Throwable -> 0x008d }
            r5.append(r2)     // Catch:{ Throwable -> 0x008d }
            java.lang.String r6 = ","
            r5.append(r6)     // Catch:{ Throwable -> 0x008d }
            r5.append(r8)     // Catch:{ Throwable -> 0x008d }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x008d }
            r1.b(r3, r5)     // Catch:{ Throwable -> 0x008d }
            if (r9 != 0) goto L_0x0097
            if (r2 != 0) goto L_0x0097
            if (r8 == 0) goto L_0x008b
            goto L_0x0097
        L_0x008b:
            r0 = 0
            goto L_0x0097
        L_0x008d:
            r8 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r9 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r1 = "inside"
            r9.b(r1, r8)
        L_0x0097:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.wallet.encrypt.BizDataProvider.isMatch(android.content.Context, java.lang.String):boolean");
    }

    private String buildBizDataImpl(Context context, Map<String, String> map) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(KEY_ORI_DATA, buildOriData(map));
        if (this.isEncrypt) {
            jSONObject.put("userId", getUserId());
            jSONObject.put("utdid", getUtdid());
            jSONObject.put(KEY_ANDROID_ID, getAndroidId(context));
        }
        return jSONObject.toString();
    }

    private String getAndroidId(Context context) {
        try {
            return Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable th) {
            LoggerFactory.f().b((String) TAG, th);
            r4 = "";
            return "";
        }
    }

    private String getUtdid() {
        return RunningConfig.f();
    }

    private String getUserId() {
        return RunningConfig.e();
    }

    private JSONObject buildOriData(Map<String, String> map) {
        JSONObject jSONObject = new JSONObject();
        for (String next : map.keySet()) {
            try {
                jSONObject.put(next, map.get(next));
            } catch (Exception e) {
                LoggerFactory.f().b((String) TAG, (Throwable) e);
            }
        }
        return jSONObject;
    }
}
