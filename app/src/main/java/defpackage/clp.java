package defpackage;

import android.app.Application;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5PageData;
import com.uc.webview.export.WebView;
import com.uc.webview.export.extension.UCClient;
import com.uc.webview.export.extension.UCExtension;
import java.util.HashMap;
import java.util.Set;
import org.json.JSONObject;

/* renamed from: clp reason: default package */
/* compiled from: UCInit */
public final class clp extends cky {
    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "UCInit";
    }

    /* access modifiers changed from: 0000 */
    public final void a(Application application) {
        erv.a = new a() {
            public final void a() {
                ajm.a();
            }

            public final void a(WebView webView) {
                UCExtension uCExtension = webView == null ? null : webView.getUCExtension();
                if (uCExtension != null) {
                    uCExtension.setClient(new UCClient() {
                        public final void onWebViewEvent(WebView webView, int i, Object obj) {
                            if (i == 15) {
                                AnonymousClass1.a(obj);
                            }
                        }
                    });
                    if (bno.a) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("url", "https://wwww.baidu.com/");
                        hashMap.put("network_type", Integer.valueOf(2));
                        hashMap.put("hardware_ac", Boolean.FALSE);
                        hashMap.put("connected", Boolean.TRUE);
                        hashMap.put("http_status_code", Integer.valueOf(200));
                        hashMap.put(H5PageData.KEY_UC_T0, Integer.valueOf(21342));
                        hashMap.put("white_time", Integer.valueOf(3000));
                        hashMap.put("has_ajax", Boolean.FALSE);
                        a((Object) hashMap);
                    }
                }
            }

            /* JADX WARNING: type inference failed for: r2v0 */
            /* JADX WARNING: type inference failed for: r2v1, types: [org.json.JSONObject] */
            /* JADX WARNING: type inference failed for: r2v2, types: [java.lang.String] */
            /* JADX WARNING: type inference failed for: r2v3, types: [org.json.JSONObject] */
            /* JADX WARNING: type inference failed for: r2v4, types: [java.lang.String] */
            /* JADX WARNING: type inference failed for: r2v5, types: [org.json.JSONObject] */
            /* JADX WARNING: type inference failed for: r2v6 */
            /* JADX WARNING: type inference failed for: r2v7 */
            /* JADX WARNING: type inference failed for: r2v8 */
            /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v0
              assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], org.json.JSONObject, java.lang.String]
              uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], org.json.JSONObject, java.lang.String]
              mth insns count: 24
            	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
            	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
            	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
            	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
            	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
            	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
            	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
            	at jadx.core.ProcessClass.process(ProcessClass.java:30)
            	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
            	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
            	at jadx.core.ProcessClass.process(ProcessClass.java:35)
            	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
            	at jadx.api.JavaClass.decompile(JavaClass.java:62)
            	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
             */
            /* JADX WARNING: Unknown variable types count: 4 */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            static void a(java.lang.Object r3) {
                /*
                    java.lang.String r0 = "url"
                    boolean r1 = r3 instanceof java.util.HashMap
                    r2 = 0
                    if (r1 == 0) goto L_0x000e
                    java.util.HashMap r3 = (java.util.HashMap) r3
                    org.json.JSONObject r2 = a(r3)
                    goto L_0x001b
                L_0x000e:
                    if (r3 == 0) goto L_0x001b
                    if (r3 != 0) goto L_0x0013
                    goto L_0x0017
                L_0x0013:
                    java.lang.String r2 = r3.toString()
                L_0x0017:
                    org.json.JSONObject r2 = a(r2)
                L_0x001b:
                    if (r2 == 0) goto L_0x003e
                    boolean r3 = r2.has(r0)
                    if (r3 == 0) goto L_0x003e
                    java.lang.String r3 = "hardware_ac"
                    a(r2, r3)     // Catch:{ Throwable -> 0x0033 }
                    java.lang.String r3 = "connected"
                    a(r2, r3)     // Catch:{ Throwable -> 0x0033 }
                    java.lang.String r3 = "has_ajax"
                    a(r2, r3)     // Catch:{ Throwable -> 0x0033 }
                    goto L_0x0037
                L_0x0033:
                    r3 = move-exception
                    r3.printStackTrace()
                L_0x0037:
                    java.lang.String r3 = "P00431"
                    java.lang.String r0 = "B001"
                    com.amap.bundle.statistics.LogManager.actionLogV2(r3, r0, r2)
                L_0x003e:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: defpackage.clp.AnonymousClass1.a(java.lang.Object):void");
            }

            private static void a(JSONObject jSONObject, String str) throws Throwable {
                if (jSONObject.has(str)) {
                    String optString = jSONObject.optString(str);
                    if (!TextUtils.isEmpty(optString)) {
                        boolean z = true;
                        if ("true".equalsIgnoreCase(optString)) {
                            jSONObject.put(str, true);
                        } else if ("false".equalsIgnoreCase(optString)) {
                            jSONObject.put(str, false);
                        } else {
                            if (Integer.parseInt(optString) != 1) {
                                z = false;
                            }
                            jSONObject.put(str, z);
                        }
                    }
                }
            }

            private static JSONObject a(String str) {
                JSONObject jSONObject;
                JSONObject jSONObject2 = null;
                if (TextUtils.isEmpty(str)) {
                    return null;
                }
                try {
                    if (str.contains("^")) {
                        String[] split = str.split(";");
                        if (split != null && split.length > 0) {
                            jSONObject = new JSONObject();
                            for (String str2 : split) {
                                if (!TextUtils.isEmpty(str2) && str2.contains("^")) {
                                    String[] split2 = str2.split("\\^");
                                    if (split2 != null && split2.length == 2) {
                                        jSONObject.put(split2[0], split2[1]);
                                    }
                                }
                            }
                        }
                        return jSONObject2;
                    }
                    jSONObject = new JSONObject(str);
                    jSONObject2 = jSONObject;
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                return jSONObject2;
            }

            private static JSONObject a(HashMap hashMap) {
                String str;
                Set keySet = hashMap.keySet();
                if (keySet == null || keySet.size() <= 0) {
                    return null;
                }
                try {
                    JSONObject jSONObject = new JSONObject();
                    for (Object next : keySet) {
                        if (next == null) {
                            str = null;
                        } else {
                            str = next.toString();
                        }
                        if (!TextUtils.isEmpty(str)) {
                            jSONObject.put(str, hashMap.get(next));
                        }
                    }
                    return jSONObject;
                } catch (Throwable th) {
                    th.printStackTrace();
                    return null;
                }
            }
        };
    }
}
