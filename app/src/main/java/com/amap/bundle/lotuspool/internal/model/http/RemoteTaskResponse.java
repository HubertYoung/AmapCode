package com.amap.bundle.lotuspool.internal.model.http;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.server.aos.serverkey;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RemoteTaskResponse extends AosResponse<List<xh>> {
    private static final String b = "RemoteTaskResponse";
    public int a = 1;

    /* access modifiers changed from: private */
    /* renamed from: a */
    public List<xh> parseResult() {
        List<xh> list;
        byte[] responseBodyData = getResponseBodyData();
        if (responseBodyData == null) {
            this.a = -1;
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(new String(responseBodyData, "UTF-8"));
            int optInt = jSONObject.optInt("code", 0);
            if (optInt != 1) {
                this.a = optInt;
                return null;
            }
            list = a(new JSONArray(serverkey.amapDecode(jSONObject.optJSONObject("data").optString("out"))));
            return list;
        } catch (Exception e) {
            if (bno.a) {
                AMapLog.d(b, DebugLog.getPrintStackTraceString(e));
            }
            this.a = -2;
            list = null;
        }
    }

    private static List<xh> a(@Nullable JSONArray jSONArray) throws JSONException {
        int length = jSONArray.length();
        if (length == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < length; i++) {
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (!TextUtils.isEmpty(optJSONObject.optString("dispatchId"))) {
                arrayList.add(new xh(optJSONObject));
            }
        }
        return arrayList;
    }
}
