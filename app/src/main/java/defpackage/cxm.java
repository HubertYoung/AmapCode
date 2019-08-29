package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.autonavi.common.SuperId;
import com.autonavi.minimap.evaluate.draugorp.Drawing;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cxm reason: default package */
/* compiled from: QACaseInfo */
final class cxm {
    private static cwo a;

    static void a(@NonNull JSONObject jSONObject, @NonNull String str, int i) throws JSONException {
        JSONObject jSONObject2 = new JSONObject(str);
        JSONObject jSONObject3 = new JSONObject();
        if (jSONObject2.has("i")) {
            jSONObject3.put("id", jSONObject2.get("i"));
        } else if (jSONObject2.has(SuperId.BIT_1_MAIN_VOICE_ASSISTANT)) {
            jSONObject3.put("id", jSONObject2.get(SuperId.BIT_1_MAIN_VOICE_ASSISTANT));
        }
        if (jSONObject2.has("m")) {
            jSONObject3.put("description", jSONObject2.get("m"));
        } else {
            jSONObject3.put("description", "");
        }
        if (jSONObject2.has(SuperId.BIT_1_NEARBY_SEARCH)) {
            jSONObject3.put("viewname", jSONObject2.get(SuperId.BIT_1_NEARBY_SEARCH));
        } else {
            jSONObject3.put("viewname", "");
        }
        if (jSONObject2.has(LogItem.MM_C15_K4_TIME)) {
            jSONObject3.put("text", jSONObject2.get(LogItem.MM_C15_K4_TIME));
        } else {
            jSONObject3.put("text", "");
        }
        try {
            if (a == null) {
                a = bno.a ? new cwo() {
                    public final List a(Context context, int i, JSONObject jSONObject) {
                        List list = null;
                        if (!bno.a) {
                            return null;
                        }
                        new StringBuilder("第").append(eog.this.c);
                        eog.this.c++;
                        if (!(i == 11 || i == 5 || i == 6)) {
                            try {
                                eof.a(context);
                                List<String> b2 = eof.b();
                                try {
                                    new StringBuilder("resultset").append(Integer.valueOf(b2.size()));
                                    if (i == 10) {
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(jSONObject.getString("viewname"));
                                        sb.append("@@");
                                        sb.append(eog.a(jSONObject.getString("id")));
                                    } else {
                                        jSONObject.getString("viewname");
                                    }
                                    new eoi();
                                    boolean a = eoi.a();
                                    if (eog.this.b && a && eod.a(context)) {
                                        eog.this.b = false;
                                    }
                                    list = b2;
                                } catch (Exception e) {
                                    e = e;
                                    list = b2;
                                    e.printStackTrace();
                                    return list;
                                }
                            } catch (Exception e2) {
                                e = e2;
                                e.printStackTrace();
                                return list;
                            }
                        }
                        return list;
                    }
                } : null;
            }
            List<String> a2 = a.a(cxk.a().a, i, jSONObject3);
            if (a2 == null) {
                jSONObject.put("QA", "<n>");
                Drawing.drawLine2(jSONObject);
                return;
            }
            for (String next : a2) {
                if (next != null && next.trim().length() > 0) {
                    jSONObject.put("QA", next);
                    Drawing.drawLine2(jSONObject);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
