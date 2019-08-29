package defpackage;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosStringResponse;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.component.mergerequest.MergeRequest;
import com.amap.bundle.network.component.mergerequest.MergeRequester$1;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.common.Callback;
import com.autonavi.minimap.net.Sign;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

/* renamed from: zz reason: default package */
/* compiled from: MergeRequester */
public final class zz {
    public Map<String, Callback> a = new HashMap();
    private List<MergeRequest> b = new ArrayList();
    private AosPostRequest c;
    private String d;
    private AosResponseCallbackOnUi<AosStringResponse> e = new MergeRequester$1(this);

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0050, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(com.amap.bundle.network.component.mergerequest.MergeRequest r3, com.autonavi.common.Callback r4) {
        /*
            r2 = this;
            monitor-enter(r2)
            java.lang.String r0 = r3.getKey()     // Catch:{ all -> 0x005a }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x005a }
            if (r0 != 0) goto L_0x0051
            java.lang.String r0 = r3.getUrl()     // Catch:{ all -> 0x005a }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x005a }
            if (r0 == 0) goto L_0x0016
            goto L_0x0051
        L_0x0016:
            java.lang.String r0 = r2.d     // Catch:{ all -> 0x005a }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x005a }
            if (r0 != 0) goto L_0x0033
            java.lang.String r0 = r2.d     // Catch:{ all -> 0x005a }
            java.lang.String r1 = r3.getUrl()     // Catch:{ all -> 0x005a }
            boolean r0 = r0.equals(r1)     // Catch:{ all -> 0x005a }
            if (r0 != 0) goto L_0x0033
            java.lang.String r3 = "MergeRequester"
            java.lang.String r4 = "url is diverse!"
            com.amap.bundle.logs.AMapLog.w(r3, r4)     // Catch:{ all -> 0x005a }
            monitor-exit(r2)
            return
        L_0x0033:
            java.util.List<com.amap.bundle.network.component.mergerequest.MergeRequest> r0 = r2.b     // Catch:{ all -> 0x005a }
            boolean r0 = r0.contains(r3)     // Catch:{ all -> 0x005a }
            if (r0 != 0) goto L_0x004f
            java.lang.String r0 = r3.getUrl()     // Catch:{ all -> 0x005a }
            r2.d = r0     // Catch:{ all -> 0x005a }
            java.util.List<com.amap.bundle.network.component.mergerequest.MergeRequest> r0 = r2.b     // Catch:{ all -> 0x005a }
            r0.add(r3)     // Catch:{ all -> 0x005a }
            java.util.Map<java.lang.String, com.autonavi.common.Callback> r0 = r2.a     // Catch:{ all -> 0x005a }
            java.lang.String r3 = r3.getKey()     // Catch:{ all -> 0x005a }
            r0.put(r3, r4)     // Catch:{ all -> 0x005a }
        L_0x004f:
            monitor-exit(r2)
            return
        L_0x0051:
            java.lang.String r3 = "MergeRequester"
            java.lang.String r4 = "invalid merge request!"
            com.amap.bundle.logs.AMapLog.w(r3, r4)     // Catch:{ all -> 0x005a }
            monitor-exit(r2)
            return
        L_0x005a:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zz.a(com.amap.bundle.network.component.mergerequest.MergeRequest, com.autonavi.common.Callback):void");
    }

    public final synchronized void a() {
        if (!this.b.isEmpty()) {
            if (!TextUtils.isEmpty(this.d)) {
                if (this.c != null) {
                    in.a().a((AosRequest) this.c);
                    this.c = null;
                }
                HashMap hashMap = new HashMap();
                hashMap.putAll(NetworkParam.getNetworkParamMap(null));
                a(hashMap);
                String b2 = b(hashMap);
                this.c = new AosPostRequest();
                this.c.setUrl(this.d);
                this.c.addReqParams(hashMap);
                this.c.setEncryptSignParam(b2);
                this.c.setCommonParamStrategy(-1);
                in.a().a((AosRequest) this.c, (AosResponseCallback<T>) this.e);
                return;
            }
        }
        AMapLog.w("MergeRequester", "do not has merge request!");
    }

    public final synchronized void b() {
        if (this.c != null) {
            in.a().a((AosRequest) this.c);
            this.c = null;
        }
    }

    private void a(Map<String, String> map) {
        JSONObject jSONObject = new JSONObject();
        StringBuilder sb = new StringBuilder();
        for (MergeRequest next : this.b) {
            try {
                JSONObject jSONObject2 = new JSONObject();
                for (Entry next2 : next.getReqParams().entrySet()) {
                    if (next2 != null && !TextUtils.isEmpty((CharSequence) next2.getKey())) {
                        jSONObject2.put((String) next2.getKey(), next2.getValue());
                    }
                }
                Map<String, String> a2 = aag.a(next.getPath());
                if (a2 != null) {
                    for (String next3 : a2.keySet()) {
                        String str = a2.get(next3);
                        if (!TextUtils.isEmpty(next3) && !TextUtils.isEmpty(str)) {
                            jSONObject2.put(next3, str);
                        }
                    }
                }
                if (jSONObject2.length() > 0) {
                    jSONObject.put(next.getKey(), jSONObject2);
                }
                if (!(sb.length() == 0 || ',' == sb.charAt(sb.length() - 1))) {
                    sb.append(",");
                }
                sb.append(next.getKey());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        map.put("dsp_svr_param", jSONObject.toString());
        map.put("dsp_svrctl_in", sb.toString());
    }

    private String b(Map<String, String> map) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (MergeRequest signParams : this.b) {
            for (String next : signParams.getSignParams()) {
                if (!TextUtils.isEmpty(next) && !arrayList.contains(next)) {
                    arrayList.add(next);
                }
            }
        }
        arrayList.remove("channel");
        StringBuilder sb = new StringBuilder();
        if (arrayList.size() > 0) {
            for (String str : arrayList) {
                String str2 = map.get(str);
                if (str2 == null) {
                    str2 = "";
                    map.put(str, str2);
                }
                sb.append(str2);
            }
        }
        return Sign.getSign(sb.toString());
    }
}
