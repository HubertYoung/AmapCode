package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dnr reason: default package */
/* compiled from: GetDownloadStatusAction */
public class dnr extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        int i;
        JsAdapter a = a();
        if (a != null) {
            JSONArray optJSONArray = jSONObject.optJSONArray("urls");
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("_action", waVar.b);
                JSONArray jSONArray = new JSONArray();
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                        JSONObject jSONObject3 = new JSONObject();
                        String obj = optJSONArray.get(i2).toString();
                        if (TextUtils.isEmpty(obj)) {
                            i = 0;
                        } else {
                            drj a2 = drj.a();
                            String str = "noexist";
                            if (new File(drj.a(obj)).exists()) {
                                str = "complete";
                            } else if (drj.c.get(obj) != null) {
                                str = "downloading";
                            } else if (!TextUtils.isEmpty(a2.f)) {
                                str = a2.f;
                            }
                            if (!TextUtils.equals(str, "downloading")) {
                                if (!TextUtils.equals(str, AudioUtils.CMDPAUSE)) {
                                    i = TextUtils.equals(str, "complete") ? 4 : 0;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(i);
                                    sb.append("status");
                                    AMapLog.i("java methrod", sb.toString());
                                }
                            }
                            i = 3;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(i);
                            sb2.append("status");
                            AMapLog.i("java methrod", sb2.toString());
                        }
                        jSONObject3.put("status", i);
                        jSONObject3.put("url", obj);
                        drj.a();
                        jSONObject3.put("targetUrl", drj.a(obj));
                        jSONObject3.put("totalSize", drj.a().d);
                        jSONObject3.put("downSize", drj.a().e);
                        jSONArray.put(jSONObject3);
                    }
                }
                jSONObject2.put("statusList", jSONArray);
                a.callJs(waVar.a, jSONObject2.toString());
                AMapLog.i("res", jSONObject2.toString());
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
        }
    }
}
