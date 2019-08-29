package defpackage;

import android.graphics.Color;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.amap.bundle.network.response.AbstractAOSParser;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bal reason: default package */
/* compiled from: EtaInfoResponser */
public final class bal extends AbstractAOSParser {
    public azt a;
    String b = "{\"code\": \"1\", \"timestamp\": \"1540541419.35\", \"version\": \"2.0-3.0.8299.1520\", \"result\": \"true\", \"message\": \"Successful.\", \"data\": {\"info\": [{\"distance\": 172, \"travel_time\": 27360, \"detail\": [{\"status\": \"0\", \"color\": \"0091ff\", \"ratio\": 1.0}]}], \"restrict\": {\"info\": \"\", \"plate_no\": \"1,6\", \"city_flag\": \"1\"}}}";
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
                        a(new JSONObject(this.mDataObject.optString("data")));
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
        this.a = new azt();
        JSONArray optJSONArray = jSONObject.optJSONArray("info");
        if (optJSONArray != null && optJSONArray.length() > 0) {
            JSONObject jSONObject2 = optJSONArray.getJSONObject(0);
            if (jSONObject2 != null) {
                this.a.b = jSONObject2.optInt("travel_time");
                JSONArray optJSONArray2 = jSONObject2.optJSONArray("detail");
                if (optJSONArray2 != null) {
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < optJSONArray2.length(); i++) {
                        azx azx = new azx();
                        JSONObject optJSONObject = optJSONArray2.optJSONObject(i);
                        if (optJSONObject != null) {
                            try {
                                azx.a = Integer.parseInt(optJSONObject.optString("status"));
                                StringBuilder sb = new StringBuilder(MetaRecord.LOG_SEPARATOR);
                                sb.append(optJSONObject.optString("color"));
                                azx.c = Color.parseColor(sb.toString());
                                azx.b = optJSONObject.optDouble("ratio");
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            arrayList.add(azx);
                        }
                    }
                    this.a.c = arrayList;
                }
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
