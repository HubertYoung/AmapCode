package defpackage;

import android.graphics.Color;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ddv reason: default package */
/* compiled from: EtaInfoResponser */
public final class ddv extends AbstractAOSParser {
    public ddx a;
    String b = "{\n  \"code\": \"1\",\n  \"timestamp\": \"1489563391.83\",\n  \"version\": \"3.0-3.0.2142.1426\",\n  \"result\": \"false\",\n  \"message\": \"Successful.\",\n  \"datas\": {\n    \"info\": {\n      \"distance\": \"7.8公里\",\n      \"state\": \"缓行709米\",\n      \"travel_time\": \"31分钟\",\n      \"detail\": [\n        {\n          \"status\": \"0\",\n          \"color\": \"0091ff\",\n          \"ratio\": 0.032\n        },\n        {\n          \"status\": \"1\",\n          \"color\": \"00ba1f\",\n          \"ratio\": 0.115\n        },\n        {\n          \"status\": \"2\",\n          \"color\": \"ffba00\",\n          \"ratio\": 0.037\n        },\n        {\n          \"status\": \"1\",\n          \"color\": \"00ba1f\",\n          \"ratio\": 0.104\n        },\n        {\n          \"status\": \"2\",\n          \"color\": \"ffba00\",\n          \"ratio\": 0.053\n        },\n        {\n          \"status\": \"1\",\n          \"color\": \"00ba1f\",\n          \"ratio\": 0.655\n        }\n      ]\n    },\n    \"tips\": 2\n  }\n}";
    private String c;

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        parseHeader(bArr);
        this.c = parserMessage(UNKNOWN_ERROR, this.mDataObject);
        try {
            if (isSuccessRequest()) {
                new StringBuilder("parser: ").append(this.mDataObject);
                String optString = this.mDataObject.optString("code");
                if (!TextUtils.isEmpty(optString)) {
                    if ("1".equals(optString)) {
                        a(new JSONObject(this.mDataObject.optString("datas")));
                    }
                }
                throw new RuntimeException("invalid status code");
            }
        } catch (JSONException e) {
            this.errorCode = -2;
            this.c = PARSE_ERROR;
            kf.a((Throwable) e);
        } catch (Exception e2) {
            this.errorCode = -1;
            this.c = UNKNOWN_ERROR;
            kf.a((Throwable) e2);
        }
    }

    private void a(JSONObject jSONObject) throws JSONException {
        new StringBuilder("parseData: ").append(jSONObject);
        this.a = new ddx(jSONObject);
        this.a.a = jSONObject.optInt(ModulePoi.TIPS);
        JSONObject optJSONObject = jSONObject.optJSONObject("info");
        if (optJSONObject != null) {
            this.a.b = optJSONObject.optString("distance");
            this.a.d = optJSONObject.optString("state");
            this.a.c = optJSONObject.optString("travel_time");
            JSONArray optJSONArray = optJSONObject.optJSONArray("detail");
            if (optJSONArray != null) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < optJSONArray.length(); i++) {
                    ddy ddy = new ddy();
                    JSONObject optJSONObject2 = optJSONArray.optJSONObject(i);
                    if (optJSONObject2 != null) {
                        try {
                            ddy.a = Integer.parseInt(optJSONObject2.optString("status"));
                            StringBuilder sb = new StringBuilder(MetaRecord.LOG_SEPARATOR);
                            sb.append(optJSONObject2.optString("color"));
                            ddy.c = Color.parseColor(sb.toString());
                            ddy.b = optJSONObject2.optDouble("ratio");
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        arrayList.add(ddy);
                    }
                }
                this.a.e = arrayList;
            }
        }
    }

    public final String getErrorDesc(int i) {
        return this.c;
    }

    public final String toString() {
        if (this.mDataObject != null) {
            return this.mDataObject.toString();
        }
        return super.toString();
    }
}
