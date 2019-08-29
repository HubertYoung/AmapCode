package com.amap.bundle.lotuspool.internal.model.http;

import android.util.Log;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.logs.AMapLog;
import org.json.JSONObject;

public class FeedbackResultsResponse extends AosResponse<Integer> {
    private static final String a = "FeedbackResultsResponse";

    /* access modifiers changed from: private */
    /* renamed from: a */
    public Integer parseResult() {
        int i;
        byte[] responseBodyData = getResponseBodyData();
        if (responseBodyData == null) {
            return Integer.valueOf(-1);
        }
        try {
            i = new JSONObject(new String(responseBodyData, "UTF-8")).optInt("code", 0);
        } catch (Exception e) {
            if (bno.a) {
                String str = a;
                StringBuilder sb = new StringBuilder("parseResult:");
                sb.append(Log.getStackTraceString(e));
                AMapLog.e(str, sb.toString());
            }
            i = -2;
        }
        return Integer.valueOf(i);
    }
}
