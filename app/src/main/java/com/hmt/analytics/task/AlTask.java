package com.hmt.analytics.task;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.nebula.appcenter.config.H5NebulaAppConfigs;
import com.alipay.sdk.sys.a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AlTask {
    /* access modifiers changed from: private */
    public static final String a = "AlTask";

    public static native ArrayList<evq> al(Context context);

    public static void a(Context context, String str) {
        if (!TextUtils.isEmpty(str)) {
            System.load(str);
            List<evq> al = al(context.getApplicationContext());
            Collections.sort(al, new Comparator<evq>() {
                public final /* synthetic */ int compare(Object obj, Object obj2) {
                    return a((evq) obj, (evq) obj2);
                }

                private static int a(evq evq, evq evq2) {
                    try {
                        if (evq.e > evq2.e) {
                            return -1;
                        }
                        return evq.e == evq2.e ? 0 : 1;
                    } catch (Exception e) {
                        AlTask.a;
                        StringBuilder sb = new StringBuilder("Collected:");
                        sb.append(e.getMessage());
                        euw.a(sb.toString());
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(evq.e);
                        if (TextUtils.isEmpty(sb2.toString())) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(evq2.e);
                            if (!TextUtils.isEmpty(sb3.toString())) {
                                return 1;
                            }
                        }
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(evq.e);
                        if (!TextUtils.isEmpty(sb4.toString())) {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(evq2.e);
                            if (TextUtils.isEmpty(sb5.toString())) {
                                return -1;
                            }
                        }
                        return 0;
                    }
                }
            });
            if (al.size() > 50) {
                al = al.subList(0, 50);
            }
            JSONArray jSONArray = new JSONArray();
            try {
                for (evq evq : al) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("pn", evq.a);
                    jSONObject.put(a.i, evq.b);
                    jSONObject.put(a.k, evq.c);
                    jSONObject.put("fit", evq.d);
                    jSONObject.put("lut", evq.e);
                    jSONArray.put(jSONObject);
                }
                JSONObject a2 = evx.a(context);
                try {
                    a2.put(H5NebulaAppConfigs.APP_POOL_LIMIT_SHORT, jSONArray);
                } catch (JSONException e) {
                    euw.a(e.getMessage());
                }
                eve.a(context, a2, "client_data_list", evd.p);
            } catch (JSONException e2) {
                euw.a(e2.getMessage());
            }
        }
    }
}
