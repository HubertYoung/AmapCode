package defpackage;

import com.amap.bundle.voiceservice.task.VoiceTaskBean;
import com.autonavi.data.service.IResultCallBack;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: aip reason: default package */
/* compiled from: ProtocolTaskManager */
public final class aip {
    private static aip b;
    public Queue<VoiceTaskBean> a = new LinkedBlockingQueue();

    private aip() {
    }

    public static synchronized aip a() {
        aip aip;
        synchronized (aip.class) {
            try {
                if (b == null) {
                    b = new aip();
                }
                aip = b;
            }
        }
        return aip;
    }

    private void a(VoiceTaskBean voiceTaskBean) {
        this.a.remove(voiceTaskBean);
    }

    private VoiceTaskBean a(int i) {
        for (VoiceTaskBean voiceTaskBean : this.a) {
            if (voiceTaskBean.getToken() == i) {
                return voiceTaskBean;
            }
        }
        return null;
    }

    public final void a(int i, String str) {
        VoiceTaskBean a2 = a(i);
        if (a2 != null) {
            IResultCallBack callback = a2.getCallback();
            String a3 = a(str);
            aiu.a(i, callback, a2.getPkgName(), a2.getMethodId(), a2.getRequestJson(), a3);
            try {
                JSONObject optJSONObject = new JSONObject(a3).optJSONObject("session");
                if ((optJSONObject != null ? optJSONObject.optInt("sessionEnd", 1) : 1) == 1) {
                    a(a2);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                a(a2);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(java.lang.String r3) {
        /*
            r0 = 0
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0037 }
            r1.<init>(r3)     // Catch:{ JSONException -> 0x0037 }
            java.lang.String r3 = "session"
            org.json.JSONObject r3 = r1.optJSONObject(r3)     // Catch:{ JSONException -> 0x0035 }
            r0 = 1
            if (r3 == 0) goto L_0x0021
            java.lang.String r2 = "sessionEnd"
            boolean r2 = r3.has(r2)     // Catch:{ JSONException -> 0x0035 }
            if (r2 != 0) goto L_0x003c
            java.lang.String r2 = "sessionEnd"
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ JSONException -> 0x0035 }
            r3.putOpt(r2, r0)     // Catch:{ JSONException -> 0x0035 }
            goto L_0x003c
        L_0x0021:
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0035 }
            r3.<init>()     // Catch:{ JSONException -> 0x0035 }
            java.lang.String r2 = "sessionEnd"
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ JSONException -> 0x0035 }
            r3.putOpt(r2, r0)     // Catch:{ JSONException -> 0x0035 }
            java.lang.String r0 = "session"
            r1.putOpt(r0, r3)     // Catch:{ JSONException -> 0x0035 }
            goto L_0x003c
        L_0x0035:
            r3 = move-exception
            goto L_0x0039
        L_0x0037:
            r3 = move-exception
            r1 = r0
        L_0x0039:
            r3.printStackTrace()
        L_0x003c:
            if (r1 != 0) goto L_0x0041
            java.lang.String r3 = ""
            return r3
        L_0x0041:
            java.lang.String r3 = r1.toString()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aip.a(java.lang.String):java.lang.String");
    }
}
