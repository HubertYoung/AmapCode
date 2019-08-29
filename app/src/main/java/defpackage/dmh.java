package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.statistics.LogManager;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dmh reason: default package */
/* compiled from: SchemeLogUtil */
public final class dmh {
    public static void a(Uri uri) {
        if (uri != null && uri.isHierarchical() && !TextUtils.isEmpty(uri.getHost())) {
            String queryParameter = uri.getQueryParameter("dyui_stat");
            if (!TextUtils.isEmpty(queryParameter)) {
                Uri parse = Uri.parse("?".concat(String.valueOf(queryParameter)));
                if (parse != null) {
                    String queryParameter2 = parse.getQueryParameter("pid");
                    String queryParameter3 = parse.getQueryParameter("bid");
                    Set<String> queryParameterNames = parse.getQueryParameterNames();
                    JSONObject jSONObject = new JSONObject();
                    try {
                        for (String next : queryParameterNames) {
                            if (!TextUtils.equals(next, "pid") && !TextUtils.equals(next, "bid")) {
                                jSONObject.put(next, parse.getQueryParameter(next));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    LogManager.actionLogV2(queryParameter2, queryParameter3, jSONObject);
                }
            }
        }
    }

    public static void b(Uri uri) {
        if (uri != null && uri.isHierarchical()) {
            String host = uri.getHost();
            if (!TextUtils.isEmpty(host)) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("url", "androidamap://".concat(String.valueOf(host)));
                    jSONObject.put("type", 0);
                    Set<String> queryParameterNames = uri.getQueryParameterNames();
                    if (queryParameterNames != null && queryParameterNames.size() > 0) {
                        for (String next : queryParameterNames) {
                            if (!TextUtils.isEmpty(next)) {
                                jSONObject.put(next, uri.getQueryParameter(next));
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                StringBuilder sb = new StringBuilder("Scheme log: ");
                sb.append(jSONObject.toString());
                DebugLog.debug(sb.toString());
                LogManager.actionLog(4003, 0, jSONObject);
            }
        }
    }
}
